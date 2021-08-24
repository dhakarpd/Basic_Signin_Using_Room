package com.example.basic_try.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.basic_try.UserDataRepository
import com.example.basic_try.UserPersonalDetailsEnt
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShowDetailsViewModel(private val userDataRepository: UserDataRepository, application: Application):
    AndroidViewModel(application) {

    val usname = MutableLiveData<String>()
    val perDet = MutableLiveData<UserPersonalDetailsEnt>()

        fun vmShowDetails(userId:Int,detailsId:Int){

            Log.d("User Id","User Id is $userId")
            Log.d("Deatils Id","Details Id  is $detailsId")

            GlobalScope.launch {
                //usname.value = userDataRepository.getUserName(userId)
                //perDet.value = userDataRepository.getUserDetails(detailsId)

                val userName:String =userDataRepository.getUserName(userId)
                val personalDetails = userDataRepository.getUserDetails(detailsId)
                Log.d("User Name","User Id is $userName")
                Log.d("Personal Details","Personal Details is ${personalDetails.firstName}")

                usname.postValue(userName)
                perDet.postValue(personalDetails)
                //sendValues(userName,personalDetails)
                /*showDetailsBinding.showUserName.text = userName
                showDetailsBinding.showFirstName.text = personalDetails.firstName
                showDetailsBinding.showLastName.text = personalDetails.lastName*/
            }

        }

    private fun sendValues(userName: String, personalDetails: UserPersonalDetailsEnt) {

        usname.value = userName
        perDet.value = personalDetails

    }
}