package com.example.basic_try.view_models

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.basic_try.HomePageActivity
import com.example.basic_try.UserDataEnt
import com.example.basic_try.UserDataRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainViewModel(private val userDataRepository: UserDataRepository,application: Application):
    AndroidViewModel(application){

    val flag = MutableLiveData<Int>()

        fun vmOnSubmitClick(uname:String,pwd:String,confpwd:String){
            if(uname!="" && pwd!="" && confpwd!="")
            {
                if (pwd==confpwd)
                {
                    //Toast.makeText(this,"Hello $uname ur password is $pwd",Toast.LENGTH_SHORT).show()
                    GlobalScope.launch {
                        userDataRepository.insertUser(UserDataEnt(0,uname,pwd))
                    }
                    flag.value=1
                    //return 1
                }
                else{
                    flag.value=0
                    //return 0

                }
            }
            else
            {
                flag.value=2
                //return 2
            }
        }

}