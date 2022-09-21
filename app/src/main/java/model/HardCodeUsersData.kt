package com.example.level2.model

typealias usersListener = (users: List<User>) -> Unit

class HardCodeUsersData {
    private var users = mutableListOf<User>()

    private val listeners = mutableSetOf<usersListener>()

    init {
        users.add(
            0,
            User(
                0,
                "https://www.meme-arsenal.com/memes/2077f9271f077a9065e718d4ad7d1636.jpg",
                "Рижик",
                "Важлива шишка",
                "м.Київ"
            )
        )
        users.add(
            1,
            User(
                1,
                "https://st.depositphotos.com/1072614/4661/i/600/depositphotos_46614201-stock-photo-housewife-cat.jpg",
                "Ельвіра",
                "Housewife",
                "м.Львів"
            )
        )
        users.add(
            2,
            User(
                2,
                "https://funart.pro/uploads/posts/2021-07/1625629841_1-funart-pro-p-kot-uchenii-zhivotnie-krasivo-foto-2.jpg",
                "Мірінда",
                "Доктор котячих наук",
                "Дніпро"
            )
        )
        users.add(
            3,
            User(
                3,
                "https://i.pinimg.com/originals/19/a8/9e/19a89e54cec871daee349e547e77056d.jpg",
                "Андрій",
                "Військовий",
                "Харків"
            )
        )


    }

    fun getSize(): Int {
        return users.size
    }

    fun getUsers(): List<User> {
        return users
    }


    fun addUser(name: String, occupy: String, photo: String, address: String) {
        users.add(users.size, User(users.size.toLong(), photo, name, occupy, address))

    }

    fun deleteUser(user: User) {
        val indexToDelete = users.indexOfFirst { it.id == user.id }
        if (indexToDelete != -1) {
            users.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun addListener(listener: usersListener) {
        listeners.add(listener)
        listener.invoke(users)
    }

    fun deleteListener(listener: usersListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach {
            it.invoke(users)
        }
    }
}