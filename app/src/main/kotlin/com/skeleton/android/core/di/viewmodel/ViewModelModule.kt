package com.skeleton.android.core.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.skeleton.android.features.chat.*
import com.skeleton.android.features.user.DoLoginViewModel
import com.skeleton.android.features.user.GetUsersViewModel
import com.skeleton.android.features.user.UpdateStatusViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    // =============================================================================================
    @Binds
    @IntoMap
    @ViewModelKey(GetUsersViewModel::class)
    abstract fun bindsUsersViewModel(getUsersViewModel: GetUsersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DoLoginViewModel::class)
    abstract fun bindsDoLoginViewModel(doLoginViewModel: DoLoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateStatusViewModel::class)
    abstract fun bindsUpdateStatusViewModel(updateStatusViewModel: UpdateStatusViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetChatsViewModel::class)
    abstract fun bindsGetChatsViewModel(getChatsViewModel: GetChatsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatExistViewModel::class)
    abstract fun bindsChatExistViewModel(chatExistViewModel: ChatExistViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddChatViewModel::class)
    abstract fun bindsCreateChatViewModel(addChatViewModel: AddChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetMessagesViewModel::class)
    abstract fun bindsGetMessagesViewModel(messagesViewModel: GetMessagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddMessageViewModel::class)
    abstract fun bindsAddCMessageViewModel(addMessageViewModel: AddMessageViewModel): ViewModel

}