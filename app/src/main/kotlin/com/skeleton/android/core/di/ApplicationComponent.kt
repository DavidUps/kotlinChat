package com.skeleton.android.core.di

import com.skeleton.android.AndroidApplication
import com.skeleton.android.core.di.viewmodel.ViewModelModule
import com.skeleton.android.core.navigation.SplashActivity
import com.skeleton.android.features.chat.ChatFragment
import com.skeleton.android.features.chat.ChatListFragment
import com.skeleton.android.features.user.UsersFragment
import com.skeleton.android.features.user.UserLoginFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(splashActivity: SplashActivity)
    fun inject(usersFragment: UsersFragment)
    fun inject(userLoginFragment: UserLoginFragment)
    fun inject(chatListFragment: ChatListFragment)
    fun inject(chatFragment: ChatFragment)
}