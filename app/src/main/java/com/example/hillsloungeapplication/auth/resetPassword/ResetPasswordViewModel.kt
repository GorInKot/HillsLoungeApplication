package com.example.hillsloungeapplication.auth.resetPassword

import androidx.lifecycle.ViewModel
import com.example.hillsloungeapplication.auth.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Callback

class ResetPasswordViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val verificationCodes = mutableMapOf<String, String>() // Храним коды подтверждения

    private fun isEmail(input: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    fun findUser(input: String, callback: (Boolean, String?, String?) -> Unit) {
        val field = if (isEmail(input)) "email" else "phone"
        db.collection("users")
            .whereEqualTo(field, input)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val user = documents.documents[0]
                    val uid = user.id
                    val email = user.getString("email")
                    callback(true, uid, email)
                } else {
                    callback(false, null, null)
                }
            }
            .addOnFailureListener {
                callback(false, null, null)
            }
    }
    fun sendEmail(to: String, subject: String, message: String, callback: (Boolean) -> Unit) {
        try {
            // интеграция с сервисом отравки сообщений
            callback(true)
        } catch (e:Exception) {
            callback(false)
        }
    }

    fun generateVerificationCode():String {
        return (1000000..999999).random().toString()
    }


    fun sendVerificationCode(email: String, callback: (Boolean, String) -> Unit) {
        val code = generateVerificationCode()
        verificationCodes[email] = code

        val subject = "Восстановление пароля"
        val message = "Ваш код подтверждения: $code"

        sendEmail(email, subject, message) { success ->
            if (success) {
                callback(true, "Код отправлен на email")
            } else {
                callback(false, "Ошибка отправки кода")
            }
        }
    }

    fun verifyCode(email: String, enteredCode: String, callback: (Boolean, String?) -> Unit) {
        val correcCode = verificationCodes[email]
        if (correcCode != null && correcCode == enteredCode) {
            callback(true, "Код верный")
        } else {
            callback(false, "Неверный код")
        }
    }

    fun updatePassword(uid: String, newPassword: String, callback: (Boolean, String) -> Unit) {
        auth.signInAnonymously()
            .addOnSuccessListener {
                val user = auth.currentUser
                user?.updatePassword(newPassword)
                    ?.addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            callback(true, "Пароль успешно обновлен")
                        } else {
                            callback(false, "Ошибка обновления пароля")
                        }
                    }
            }
            .addOnFailureListener {
                callback(false, "Ошибка авторизацияя")
            }
    }


//    fun resetPassword(input: String, callback: (Boolean, String) -> Unit) {
//        findUserByEmailOrPhone(input) {uid, email ->
//            if (uid != null) {
//                if (isEmail(input) && email != null) {
//                    sendPasswordResetEmail(email,callback)
//                } else if (isPhoneNumber(input)) {
//                    resetPasswordForPhone(uid, input, callback)
//                }
//            } else {
//                callback(false, "Пользователь не найдет")
//            }
//        }
//    }
//
//    private fun sendPasswordResetEmail(uid: String, phone: String, callback: (Boolean, String) -> Unit) {
//        val newPassword = generateNewPassword()
//        auth.signInAnonymously()
//            .addOnSuccessListener {
//                val user = auth.currentUser
//                user?.updatePassword(newPassword)
//                    ?.addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            sendSmsWithNewPassword(phone, newPassword, callback)
//                        } else {
//                            callback(false, "Ошибка обновления пароля")
//                        }
//                    }
//            }
//            .addOnFailureListener {
//                callback(false, "Ошибка авторизации")
//            }
//    }
//
//    private fun sendSmsWithNewPassword(phone: String,newPassword: String, callback: (Boolean, String) -> Unit) {
//        try {
//            val client = Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN)
//            Message.creator(
//                PhoneNumber(phone),
//                PhoneNumber(TWILIO_PHONE_NUMBER),
//                "ваш новый пароль: $newPassword"
//            ).create()
//            callback(true, "Новый пароль отправлен по SMS")
//        } catch (e: Exception) {
//            callback(false, "Ошибка отправки SMS")
//        }
//    }
//
//    private fun generateNewPassword(): String {
//        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
//        return (1..8)
//            .map { allowedChars.random() }
//            .joinToString("")
//    }
//
//    private fun isEmail(input: String): Boolean {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
//    }
//
//    private fun PhoneNumber(input: String): Boolean {
//        return android.util.Patterns.PHONE.matcher(input).matches()
//    }
//
//    private fun findUserByEmailOrPhone(input: String, callback: (String?, String?) -> Unit) {
//        val field = if(isEmail(input)) "email" else "phone"
//        db.collection("users")
//            .whereEqualTo(field, input)
//            .get()
//            .addOnSuccessListener { documents ->
//                if (!documents.isEmpty) {
//                    val user = documents.documents[0]
//                    val uid = user.id
//                    val email = user.getString("email")
//                    callback(uid, email)
//                } else {
//                    callback(null, null)
//                }
//            }
//            .addOnFailureListener {
//                callback(null, null)
//            }
    }

//    private val userRepository = UserRepository()


