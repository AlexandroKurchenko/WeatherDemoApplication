package com.okurchenko.weatherdemoapplication.interactors

abstract class UseCase<Q : UseCase.Params?, T> {
    abstract suspend fun execute(params: Q): T
    abstract class Params
}

abstract class NoParamsUseCase<T> {
    abstract suspend fun execute(): T
}

abstract class LiveDataUseCase<Q : LiveDataUseCase.Params?, T> {
    abstract fun execute(params: Q): T
    abstract class Params
}