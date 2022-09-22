package com.example.level2.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsersViewModel : ViewModel() {

    private var userList : MutableLiveData<List<User>> = MutableLiveData()

    init {
        userList.value = UserData.getUsers()
    }

    fun getUsers(position: Int) = userList.value!![position]


}