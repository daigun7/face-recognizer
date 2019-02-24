package com.sgsoft.facerecognizer.common.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchedulerProvider {
    fun computation(): Scheduler = Schedulers.computation()
    fun io(): Scheduler = Schedulers.io()
    fun ui(): Scheduler = AndroidSchedulers.mainThread()
}