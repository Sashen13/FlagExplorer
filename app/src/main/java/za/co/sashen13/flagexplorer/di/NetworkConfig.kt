package za.co.sashen13.flagexplorer.di

import org.koin.dsl.module
import za.co.sashen13.flagexplorer.data.remote.FlagExplorerServiceInstance

object NetworkConfig {
    val module = module {
        single { FlagExplorerServiceInstance().service }
    }
}
