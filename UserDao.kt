package com.example.basic_try

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userDataEnt: UserDataEnt)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPersonalDetails(userPersonalDetailsEnt: UserPersonalDetailsEnt)

    @Update
    suspend fun updatePersonalDetails(userPersonalDetailsEnt: UserPersonalDetailsEnt)

    //@Query("select * from login_data")
    //fun getAllUsersData():LiveData<List<UserDataEnt>>

    @Query("select * from login_data where user_name =:uname and password=:pwd")
    suspend fun authenticateUser(uname: String, pwd: String): UserDataEnt

    //@Query("SELECT * FROM login_data inner join personal_details on login_data.id=personal_details.user_id")
    //fun getAllUsersDataAndPersonalDetails():LiveData<List<UserAndPersonalDetails>>

    //@Query("select * from personal_details")
    //fun getData():LiveData<List<UserPersonalDetailsEnt>>

    @Query("select id from login_data where user_name =:uname")
    suspend fun getUserId(uname: String): Int

    @Query("select user_name from login_data where id =:userId")
    suspend fun getUserName(userId:Int):String

    @Query("select detailsId from personal_details where user_id =:userId")
    suspend fun getUserDetailsId(userId:Int):Int

    @Query("select * from personal_details where detailsId=:detailsId")
    suspend fun getUserDetails(detailsId: Int):UserPersonalDetailsEnt
}