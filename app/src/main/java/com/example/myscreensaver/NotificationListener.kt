package com.example.myscreensaver

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import org.koin.android.ext.android.get

class NotificationListener : NotificationListenerService() {
    private val notificationController = get<NotificationController>()

    override fun onListenerConnected() {
        notificationController.onNotificationsPosted(getActiveNotifications())
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        if (sbn != null) {
            notificationController.onNotificationRemoved(sbn)
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn != null) {
            notificationController.onNotificationPosted(sbn)
        }
    }
}