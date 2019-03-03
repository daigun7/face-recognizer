package com.sgsoft.facerecognizer.common.usecase

interface BaseUseCase<T, R> {
    fun execute(param: T): R
}