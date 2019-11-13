package com.example.myscreensaver.di

import com.example.myscreensaver.NotificationController
import org.koin.dsl.module

fun modules() = listOf(
    notificationModule
)

val notificationModule = module {
    single { NotificationController() }
}