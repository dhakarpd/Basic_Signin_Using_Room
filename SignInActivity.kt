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
import com.example.basic_try.databinding.ActivitySignInBinding
import com.example.basic_try.view_model_factory.SignInViewModelFactory
import com.example.basic_try.view_models.SignInViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {

    private lateinit var signInBinding:ActivitySignInBinding
    private lateinit var dao: UserDao
    private var flagSignIn =-1
    private lateinit var userDataRepository: UserDataRepository
    private lateinit var signInViewModel: SignInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        signInBinding=DataBindingUtil.setContentView(this,R.layout.activity_sign_in)

        dao = UserRoomDatabase.getDatabase(this).userDao()
        userDataRepository = UserDataRepository(dao)
        signInViewModel = ViewModelProvider(this,
            SignInViewModelFactory(userDataRepository,application)).get(SignInViewModel::class.java)

        signInViewModel.flag.observe(this, Observer { newFlag->
            flagSignIn = newFlag
            onSignInClickNew(flagSignIn)
        })

        signInBinding.signInBtn.setOnClickListener {

            val userName:String = signInBinding.signInUserName.text.toString()
            val passWord:String = signInBinding.signInPassword.text.toString()
            signInViewModel.vmOnSignInClick(userName, passWord)
            //onSignInClick(it)
            //onSignInClickNew(flagSignIn,userName)
        }
    }

    private fun onSignInClickNew(flag: Int) {

        when(flag){
            0->{
                Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_SHORT).show()
                signInBinding.signInUserName.setText("")
                signInBinding.signInPassword.setText("")
            }
            1->{
                val userName:String = signInBinding.signInUserName.text.toString()
                goToHomePage(userName)
            }
            2->{
                Toast.makeText(this,"Please Fill All Fields",Toast.LENGTH_SHORT).show()
            }
            else->{
                Toast.makeText(this,"Unexpected Behaviour",Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun goToHomePage(userName:String){
        val intent = Intent(this,HomePageActivity::class.java)
        intent.putExtra("uname",userName)
        startActivity(intent)
        finish()
    }



    /*fun loginFailed(){
        //https://stackoverflow.com/questions/66191692/how-to-make-a-toast-message-in-a-coroutine
        Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_SHORT).show()
        signInBinding.signInUserName.setText("")
        signInBinding.signInPassword.setText("")
    }*/

    /*
    @DelicateCoroutinesApi
    private fun onSignInClick(it: View?) {
        val userName = signInBinding.signInUserName.text.toString()
        val passWord = signInBinding.signInPassword.text.toString()
        //var isAuth=true

        GlobalScope.launch {
            val checkUser: UserDataEnt = dao.authenticateUser(userName, passWord)
            Log.d("User Auth", "Value of User $checkUser")
            if (checkUser == null) {
                //isAuth=false
                //loginFailed()
                signInBinding.signInUserName.setText("")
                signInBinding.signInPassword.setText("")
            } else {
                goToHomePage(userName)  //won't this lead to running of coroutine for unnecessary time
            }
        }
    }*/

        //job.join()
        /*if (isAuth==true)
        {
            val intent = Intent(this,HomePageActivity::class.java)
            intent.putExtra("uname",userName)
            startActivity(intent)
            finish()
        }
        else
        {
            Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_SHORT).show()
            signInBinding.signInUserName.setText("")
            signInBinding.signInPassword.setText("")
        }*/


}