package me.usrName.annotationprocessor


@Retention(AnnotationRetention.RUNTIME)
annotation class Entity(
    val propName: String,
)