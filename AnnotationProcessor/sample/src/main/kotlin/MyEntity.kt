import me.usrName.annotationprocessor.Entity


@Entity("myentity")
data class MyEntity(
    val name: String = "",
    val price: Double = 0.0,
    ) {}


