package za.co.sashen13.flagexplorer.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import za.co.sashen13.flagexplorer.data.repository.CountryRepositoryImpl
import za.co.sashen13.flagexplorer.domain.repository.CountryRepository
import za.co.sashen13.flagexplorer.domain.usecases.GetAllCountriesUseCase
import za.co.sashen13.flagexplorer.domain.usecases.IGetAllCountriesUseCase
import za.co.sashen13.flagexplorer.presentation.home.HomeViewModel

object HomeConfig {
    val module = module {
        viewModel { HomeViewModel(get()) }

        factory<IGetAllCountriesUseCase> { GetAllCountriesUseCase(get()) }

        single<CountryRepository> {
            CountryRepositoryImpl(get())
        }
    }
}