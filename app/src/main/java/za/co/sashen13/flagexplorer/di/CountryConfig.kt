package za.co.sashen13.flagexplorer.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import za.co.sashen13.flagexplorer.data.repository.CountryRepositoryImpl
import za.co.sashen13.flagexplorer.domain.repository.CountryRepository
import za.co.sashen13.flagexplorer.domain.usecases.GetAllCountriesUseCase
import za.co.sashen13.flagexplorer.domain.usecases.GetCountryDetailsUseCase
import za.co.sashen13.flagexplorer.domain.usecases.IGetAllCountriesUseCase
import za.co.sashen13.flagexplorer.domain.usecases.IGetCountryDetailsUseCase
import za.co.sashen13.flagexplorer.presentation.detail.DetailsViewModel
import za.co.sashen13.flagexplorer.presentation.home.HomeViewModel

object CountryConfig {
    val module = module {
        viewModel { HomeViewModel(get()) }
        viewModel { DetailsViewModel(get(), get()) }

        factory<IGetAllCountriesUseCase> { GetAllCountriesUseCase(get()) }
        factory<IGetCountryDetailsUseCase> { GetCountryDetailsUseCase(get()) }

        single<CountryRepository> {
            CountryRepositoryImpl(get())
        }
    }
}

//TODO - combine home and details into CountryConfig