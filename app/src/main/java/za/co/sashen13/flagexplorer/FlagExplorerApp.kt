package za.co.sashen13.flagexplorer

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import za.co.sashen13.flagexplorer.di.DetailsConfig
import za.co.sashen13.flagexplorer.di.HomeConfig
import za.co.sashen13.flagexplorer.di.NetworkConfig

class FlagExplorerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FlagExplorerApp)
            modules(
                NetworkConfig.module,
                HomeConfig.module,
                DetailsConfig.module
            )
        }
    }
}