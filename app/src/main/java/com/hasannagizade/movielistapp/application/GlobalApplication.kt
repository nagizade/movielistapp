package com.hasannagizade.movielistapp.application

import android.app.Application
import android.os.Build
import androidx.lifecycle.LifecycleObserver
import com.hasannagizade.movielistapp.BuildConfig
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GlobalApplication : Application(), LifecycleObserver {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(androidContext = this@GlobalApplication)
            properties(
                mapOf(
                    "host" to BuildConfig.API_URL,
                    "version" to "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})",
                    "versionCode" to "${BuildConfig.VERSION_CODE}",
                )
            )
            modules(appComponent)
        }

        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
        }
    }
}