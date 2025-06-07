package za.co.sashen13.flagexplorer.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import za.co.sashen13.flagexplorer.domain.usecases.GetCountryDetailsUseCase
import za.co.sashen13.flagexplorer.domain.usecases.IGetCountryDetailsUseCase
import za.co.sashen13.flagexplorer.presentation.detail.DetailsViewModel

object DetailsConfig {
    val module = module {
        viewModel { DetailsViewModel(get(), get()) }

        factory<IGetCountryDetailsUseCase> { GetCountryDetailsUseCase(get()) }
    }
}