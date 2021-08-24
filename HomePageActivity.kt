package com.example.basic_try

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.basic_try.databinding.ActivityHomePageBinding
import com.example.basic_try.view_model_factory.HomePageViewModelFactory
import com.example.basic_try.view_models.HomePageViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomePageActivity : AppCompatActivity() {

    private lateinit var homePageBinding: ActivityHomePageBinding
    private lateinit var database: UserRoomDatabase
    private lateinit var dao: UserDao

    private lateinit var userDataRepository: UserDataRepository
    private lateinit var homePageViewModel: HomePageViewModel

    //private var check:Int =0
    private var actUserId:Int =0
    private var actDetailsId:Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageBinding = DataBindingUtil.setContentView(this,R.layout.activity_home_page)
        val usname= intent.getStringExtra("uname")
        var param : String
        if (usname!=null)
        {
            param=usname
        }
        else{
            param="kuchbhi"
        }
        val uName = "Welcome $param"
        homePageBinding.wlcmText.text = uName

        database = UserRoomDatabase.getDatabase(this)
        dao = database.userDao()
        userDataRepository = UserDataRepository(dao)
        homePageViewModel = ViewModelProvider(this,
            HomePageViewModelFactory(userDataRepository,application)).get(HomePageViewModel::class.java)

        /*homePageViewModel.check.observe(this, Observer { t->
            check =t
        })*/


        homePageViewModel.userID.observe(this, Observer { t->
            actUserId = t
        })

        homePageViewModel.detailsID.observe(this, Observer { t->
            actDetailsId = t
            if(actDetailsId!=0){
                showAllDetailsNew()
            }

        })

        homePageBinding.saveBtn.setOnClickListener {
            val firstName=homePageBinding.fName.text.toString()
            val lastName=homePageBinding.lName.text.toString()

            if(firstName!="" && lastName!=""){
                homePageViewModel.vmSavePersonalDetails(param,firstName,lastName)
                //savePersonalDetails(it,param)
            }
            else
            {
                Toast.makeText(this,"Please fill all Fields", Toast.LENGTH_SHORT).show()
            }
        }

        homePageBinding.showDetailsBtn.setOnClickListener {

            homePageViewModel.vmShowDetails(param)
            //showAllDetailsNew()
            //showAllDetails(it,param)
        }

    }

    private fun showAllDetailsNew() {
        val intent = Intent(this@HomePageActivity,ShowDetailsActivity::class.java)
        intent.putExtra("userId",actUserId)
        intent.putExtra("userDetailsId",actDetailsId)
        startActivity(intent)
        finish()
    }


    /*
    private fun savePersonalDetails(it: View?,param:String) {
        val firstName=homePageBinding.fName.text.toString()
        val lastName=homePageBinding.lName.text.toString()

        //here need to call insert for personal details but its constructor
        //requires user_id
        //1-> so either get user_id from usname and pass user_id make function in dao for that
        //2-> use username as connecting link between two tables but that requires changing db

        GlobalScope.launch {

            val userId:Int=database.userDao().getUserId(param)
            /*if userid already in personal details database then update that data
              else insert new data if doesn't exist already*/

            val detailsId:Int? = dao.getUserDetailsId(userId)

            if (detailsId!=null){
                dao.updatePersonalDetails(UserPersonalDetailsEnt(detailsId,firstName,lastName,userId))
            }
            else{
                dao.insertPersonalDetails(UserPersonalDetailsEnt(0,firstName,lastName,userId))
                //database.userDao().insertPersonalDetails(UserPersonalDetailsEnt(firstName,lastName,0))
            }

        }


    }

    private fun showAllDetails(it: View?, param: String) {
        GlobalScope.launch {

            val userId:Int=database.userDao().getUserId(param)
            val detailsId:Int=dao.getUserDetailsId(userId)
            Log.d("DetailsId value","Details Id is $detailsId")
            val intent = Intent(this@HomePageActivity,ShowDetailsActivity::class.java)
            intent.putExtra("userId",userId)
            intent.putExtra("userDetailsId",detailsId)
            startActivity(intent)
            finish()
        }
    }*/


}