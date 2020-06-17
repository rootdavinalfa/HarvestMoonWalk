package xyz.dvnlabs.hm_btnwalk.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppSingleton : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppSingleton)
            androidLogger()
            modules(appModule)
        }
    }
}