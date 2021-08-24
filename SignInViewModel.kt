package com.example.basic_try.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.basic_try.UserDataEnt
import com.example.basic_try.UserDataRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignInViewModel(val userDataRepository: UserDataRepository,application: Application):
    AndroidViewModel(application) {

    val flag = MutableLiveData<Int>()

        @DelicateCoroutinesApi
        fun vmOnSignInClick(userName:String, passWord:String){

            //var retval:Int=-1
            if(userName!="" && passWord!="")
            {
                GlobalScope.launch {

                    val checkUser: UserDataEnt =
                        userDataRepository.authenticateUser(userName, passWord)
                    //Log.d("User Auth", "Value of User $checkUser")
                    //if no user exists then checkuser will be null
                    if (checkUser == null) {

                        //retval = 0
                        //flag.value=0
                        flag.postValue(0)
                    } else {
                        //retval = 1
                        //flag.value=1
                        flag.postValue(1)

                    }
                }
            }
            else
            {
                //retval =2
                //flag.value =2
                flag.postValue(2)
            }

            //Log.d("Username", "Username is $userName")
            //Log.d("Password", "Password is $passWord")
            //flagValue(retval)

        }

    /*private fun flagValue(retval: Int) {
        Log.d("Flag Value","$retval")
        flag.value=retval
    }*/
}