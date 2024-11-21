package com.example.fitlife.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LogInScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithEmailAndPassword(email: String, password:String, home: ()-> Unit)
    = viewModelScope.launch {
        try{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {task->
                    if (task.isSuccessful){
                        Log.d("FitLife", "signInWithEmailAndPassword logIn!!")
                        home()
                    }
                    else{
                        Log.d("FitLife", "signInWithEmailAndPassword: ${task.result.toString()}")

                    }
                }
        }
        catch(ex:Exception){
            Log.d("FitLife", "signInWithEmailAndPassword: ${ex.message}")
        }

    }

    fun createUserWhitEmailAndPassword(email:String, password: String, home: () -> Unit){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        home()
                    }else{
                        Log.d("FitLife", "createUserWhitEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?){
        val userId = auth.currentUser?.uid
        val user = mutableMapOf<String, Any>()

        user["user_id"] = userId.toString()
        user["displayName"] = userId.toString()
        FirebaseFirestore.getInstance().collection("user")
            .add(user)
            .addOnSuccessListener {
                Log.d("FitLife", "createUserWhitEmailAndPassword: ${it.id}")
            }.addOnFailureListener{

            }

    }
}

