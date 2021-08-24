package com.example.basic_try

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.basic_try.databinding.ActivityShowDetailsBinding
import com.example.basic_try.view_model_factory.ShowDetailsViewModelFactory
import com.example.basic_try.view_models.ShowDetailsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShowDetailsActivity : AppCompatActivity() {

    private lateinit var showDetailsBinding: ActivityShowDetailsBinding
    private lateinit var dao: UserDao

    private lateinit var userDataRepository: UserDataRepository
    private lateinit var showDetailsViewModel: ShowDetailsViewModel

    private lateinit var userName:String
    private lateinit var personalDetails:UserPersonalDetailsEnt


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDetailsBinding =DataBindingUtil.setContentView(this,R.layout.activity_show_details)
        dao = UserRoomDatabase.getDatabase(this).userDao()
        userDataRepository = UserDataRepository(dao)

        showDetailsViewModel = ViewModelProvider(this,
            ShowDetailsViewModelFactory(userDataRepository,application)).get(ShowDetailsViewModel::class.java)

        showDetailsViewModel.usname.observe(this, Observer { newusname->
            userName = newusname
        })

        showDetailsViewModel.perDet.observe(this, Observer { newperdet->
            personalDetails = newperdet
            showDet()
        })


        val userId:Int = intent.getIntExtra("userId",1)
        val detailsId:Int = intent.getIntExtra("userDetailsId",1)
        Log.d("User Details Id","Usid is $detailsId")
        showDetailsViewModel.vmShowDetails(userId, detailsId)

        //showDet()
        /*GlobalScope.launch {
            val userName:String =dao.getUserName(userId)
            val personalDetails = dao.getUserDetails(detailsId)
            showDetailsBinding.showUserName.text = userName
            showDetailsBinding.showFirstName.text = personalDetails.firstName
            showDetailsBinding.showLastName.text = personalDetails.lastName
        }*/


    }

    private fun showDet() {
        Log.d("Activity User Name","Act")
        showDetailsBinding.showUserName.text = userName
        showDetailsBinding.showFirstName.text = personalDetails.firstName
        showDetailsBinding.showLastName.text = personalDetails.lastName
    }
}