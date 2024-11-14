package user.di

import org.invendiv.user.data.UserRepositoryImpl
import org.invendiv.user.jobs.UserCountJob
import org.invendiv.user.domain.repository.UserRepository
import org.invendiv.user.domain.useCase.AddUserUseCase
import org.invendiv.user.domain.useCase.FetchUsersUseCase
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    factory { AddUserUseCase(get()) }
    factory { FetchUsersUseCase(get()) }
    single { UserCountJob(get()) }
}