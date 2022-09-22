package com.example.level2.model

object UserData {

    fun getUsers() = listOf(
        User(
            0,
            "https://www.meme-arsenal.com/memes/2077f9271f077a9065e718d4ad7d1636.jpg",
            "Рижик",
            "Важлива шишка-кішка",
            "м.Київ"
        ),
        User(
            1,
            "https://st.depositphotos.com/1072614/4661/i/600/depositphotos_46614201-stock-photo-housewife-cat.jpg",
            "Ельвіра",
            "Домогосподарка",
            "м.Львів"
        ),
        User(
            2,
            "https://funart.pro/uploads/posts/2021-07/1625629841_1-funart-pro-p-kot-uchenii-zhivotnie-krasivo-foto-2.jpg",
            "Мірінда",
            "Доктор котячих наук",
            "Дніпро"
        ),
        User(
            3,
            "https://i.pinimg.com/originals/19/a8/9e/19a89e54cec871daee349e547e77056d.jpg",
            "Андрій",
            "Бойовий кіт",
            "Харків"
        )
    )

    fun getSize(): Int {
        return getUsers().size
    }
}