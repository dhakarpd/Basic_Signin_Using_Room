package com.example.basic_try

import androidx.room.*

/*,
    indices = [Index(value = ["user_name"], unique = true)]*/
@Entity(tableName = "login_data")
data class UserDataEnt(
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    @ColumnInfo(name = "user_name")val userName:String,
    @ColumnInfo(name = "password")val password: String)

/*,
    foreignKeys = arrayOf(ForeignKey(entity = UserDataEnt::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.CASCADE)
    )*/

@Entity(tableName = "personal_details")
data class UserPersonalDetailsEnt(
    @PrimaryKey(autoGenerate = true) var detailsId:Int = 0,
    @ColumnInfo(name = "first_name")val firstName:String,
    @ColumnInfo(name = "last_name")val lastName:String,
    @ColumnInfo(name = "user_id")val id: Int)