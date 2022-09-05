fun main() {
    val products = mutableListOf<Product>()
    val users = mutableListOf<User>()

    val ctx = Context(products, users)

    for (i in 0..3) {
        ctx.users.add(User("hans",30+i))
    }
    for (i in 0..ctx.users.size-1) {
        println(ctx.users[i].name)
        println(ctx.users[i].age)
    }

}
