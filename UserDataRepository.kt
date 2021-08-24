package com.example.basic_try

class UserDataRepository(private val dao: UserDao) {

    suspend fun insertUser(userDataEnt: UserDataEnt){
        return dao.insertUser(userDataEnt)
    }

    suspend fun insertPersonalDetails(userPersonalDetailsEnt: UserPersonalDetailsEnt){
        return dao.insertPersonalDetails(userPersonalDetailsEnt)
    }

    suspend fun updatePersonalDetails(userPersonalDetailsEnt: UserPersonalDetailsEnt){
        return dao.updatePersonalDetails(userPersonalDetailsEnt)
    }

    suspend fun authenticateUser(uname: String, pwd: String): UserDataEnt{
        return dao.authenticateUser(uname,pwd)
    }

    suspend fun getUserId(uname: String): Int{
        return dao.getUserId(uname)
    }

    suspend fun getUserName(userId:Int):String{
        return dao.getUserName(userId)
    }

    suspend fun getUserDetailsId(userId:Int):Int{
        return dao.getUserDetailsId(userId)
    }

    suspend fun getUserDetails(detailsId: Int):UserPersonalDetailsEnt{
        return dao.getUserDetails(detailsId)
    }
}