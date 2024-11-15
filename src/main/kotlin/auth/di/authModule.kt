package auth.di

import auth.data.AuthRepositoryImpl
import auth.domain.JwtProvider
import auth.domain.repository.AuthRepository
import auth.domain.useCase.CheckTokenValidityUseCase
import auth.domain.useCase.LoginUseCase
import auth.domain.useCase.LogoutUseCase
import org.koin.dsl.module

val authModule = module {
    single { JwtProvider() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single { LoginUseCase() }
    single { LogoutUseCase(get()) }
    single { CheckTokenValidityUseCase(get()) }
}