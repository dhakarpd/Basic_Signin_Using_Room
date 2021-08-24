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
import com.example.basic_try.databinding.ActivityMainBinding
import com.example.basic_try.view_model_factory.MainViewModelFactory
import com.example.basic_try.view_models.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var regBinding:ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var flagMain: Int = 0
    private lateinit var database: UserRoomDatabase
    private lateinit var userDataRepository: UserDataRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        regBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        database = UserRoomDatabase.getDatabase(this)

        userDataRepository = UserDataRepository(database.userDao())
        mainViewModel = ViewModelProvider(this,
            MainViewModelFactory(userDataRepository,application)).get(MainViewModel::class.java)

        mainViewModel.flag.observe(this, Observer { newFlag->
            flagMain = newFlag

        })

        regBinding.submitBtn.setOnClickListener {
            val uname:String = regBinding.userName.text.toString()
            val pwd:String = regBinding.passWord.text.toString()
            val confpwd:String = regBinding.reTypePassword.text.toString()
            mainViewModel.vmOnSubmitClick(uname, pwd, confpwd)
            //onSubmitClick(it)
            onSubmitClickNew(flagMain,uname)
        }

        regBinding.goToSignInBtn.setOnClickListener {
            onGoToSignInClick(it)
        }


    }



    /*private fun onSubmitClick(it: View?) {
        /*database.userDao().getAllUsersData().observe(this, Observer {
            Log.d("Hello Brother",it.toString())
        })*/

        val uname:String = regBinding.userName.text.toString()
        val pwd:String = regBinding.passWord.text.toString()
        val confpwd:String = regBinding.reTypePassword.text.toString()

        if(uname!="" && pwd!="" && confpwd!="")
        {
            if (pwd==confpwd)
            {
                //Toast.makeText(this,"Hello $uname ur password is $pwd",Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    database.userDao().insertUser(UserDataEnt(0,uname,pwd))
                }
                val intent = Intent(this,HomePageActivity::class.java)
                intent.putExtra("uname",uname)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Password and Re-Entered Password don't match",Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(this,"Please fill all Fields",Toast.LENGTH_SHORT).show()
        }

    }*/

    private fun onSubmitClickNew(flag:Int,uname:String){
        /*
            flag =1 -> Successfully data inserted
            flag =0 -> pwd and confpwd did not match
            flag =2 -> all fields not filled
        */

        when(flag){
            1->{
                val intent = Intent(this,HomePageActivity::class.java)
                intent.putExtra("uname",uname)
                startActivity(intent)
                finish()
            }
            0->{
                Toast.makeText(this,"Password and Re-Entered Password don't match",Toast.LENGTH_SHORT).show()
            }
            2->{
                Toast.makeText(this,"Please fill all Fields",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun onGoToSignInClick(it: View?) {
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}