package com.example.pizzaapp.utils

sealed class Resources<T> (val data : T?= null,
                           val message : String? = null ){

    class Success<T>(data: T) : Resources<T>(data)
    class  Loading<T>() : Resources<T>()
    class  Error<T>(message:String) :Resources<T>(message = message)
    class  UnSpecified<T>():Resources<T>()
}