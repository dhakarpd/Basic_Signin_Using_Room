package com.example.basic_try.view_models

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.basic_try.ShowDetailsActivity
import com.example.basic_try.UserDataRepository
import com.example.basic_try.UserPersonalDetailsEnt
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomePageViewModel(private val userDataRepository: UserDataRepository, application: Application) :
    AndroidViewModel(application){

    val userID = MutableLiveData<Int>()
    val detailsID = MutableLiveData<Int>()
    //val check = MutableLiveData<Int>()

        fun vmSavePersonalDetails(userName:String,firstName:String,lastName:String){

           /* if(firstName!="" && lastName!=""){
                check.value = 1
            }
            else
            {
                check.value = 0
            }*/

            GlobalScope.launch {

                val userId:Int=userDataRepository.getUserId(userName)
                /*if userid already in personal details database then update that data
                  else insert new data if doesn't exist already*/

                val detailsId:Int? = userDataRepository.getUserDetailsId(userId)

                if (detailsId!=null){
                    userDataRepository.updatePersonalDetails(UserPersonalDetailsEnt(detailsId,firstName,lastName,userId))
                }
                else{
                    userDataRepository.insertPersonalDetails(UserPersonalDetailsEnt(0,firstName,lastName,userId))
                    //database.userDao().insertPersonalDetails(UserPersonalDetailsEnt(firstName,lastName,0))
                }

            }

        }


        @DelicateCoroutinesApi
        fun vmShowDetails(uName: String){
            GlobalScope.launch {

                val userId:Int=userDataRepository.getUserId(uName)
                val detailsId:Int?=userDataRepository.getUserDetailsId(userId)
                userID.postValue(userId)
                if(detailsId!=null){
                    detailsID.postValue(detailsId!!)
                }
                else
                {
                    detailsID.postValue(0)
                }

                //userID.postValue(userDataRepository.getUserId(userName))
                //detailsID.postValue(userDataRepository.getUserDetailsId(userID.value!!))
                //Log.d("DetailsId value","Details Id is $detailsId")

            }
        }
}