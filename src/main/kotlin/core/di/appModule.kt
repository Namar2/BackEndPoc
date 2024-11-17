package core.di


import core.data.MongoClientProvider
import org.koin.dsl.module

val appModule = module {
    single { MongoClientProvider() }
}