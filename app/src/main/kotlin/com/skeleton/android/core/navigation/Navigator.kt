package com.skeleton.android.core.navigation

import android.content.Context
import android.support.v4.app.FragmentActivity
import com.skeleton.android.features.chat.ChatFragment
import com.skeleton.android.features.chat.ChatListFragment
import com.skeleton.android.features.chat.ChatView
import com.skeleton.android.features.user.UserLoginFragment
import com.skeleton.android.features.user.UserView
import com.skeleton.android.features.user.UsersFragment
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor() {

    // Activities ==================================================================================
    fun showInitial(context: Context) {
        showMainActivity(context)
    }

    private fun showMainActivity(context: Context) = context.startActivity(MainActivity.callingIntent(context))

    // =============================================================================================

    // Fragments ===================================================================================

    fun showUserLoginFragment(activity: FragmentActivity) {
        (activity as MainActivity).replaceFragment(UserLoginFragment(), "LoginFragment")
    }

    fun showUsersFragment(activity: FragmentActivity) {
        (activity as MainActivity).replaceFragment(UsersFragment(), "UsersFragment")
    }

    fun showChatListFragment(activity: FragmentActivity){
        (activity as MainActivity).replaceFragment(ChatListFragment(), "ChatListFragment")
    }

    fun showChatFragment(activity: FragmentActivity, chat: ChatView, user: UserView){
        (activity as MainActivity).replaceFragment(ChatFragment.newInstance(chat, user), "ChatFragment")
    }
}
