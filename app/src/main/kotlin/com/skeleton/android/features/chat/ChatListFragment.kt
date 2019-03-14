package com.skeleton.android.features.chat

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.failure
import com.skeleton.android.core.extension.observe
import com.skeleton.android.core.extension.onClick
import com.skeleton.android.core.extension.viewModel
import com.skeleton.android.core.functional.DialogCallback
import com.skeleton.android.core.navigation.Navigator
import com.skeleton.android.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_chatlist.*
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration
import com.skeleton.android.R
import com.skeleton.android.features.user.User

class ChatListFragment: BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_chatlist

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var chatsAdapter: ChatAdapter

    private lateinit var getChatsViewModel: GetChatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        getChatsViewModel = viewModel(viewModelFactory){
            observe(chats, ::renderChats)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListeners()
    }

    private fun initView() {
        getChats()
        rvChats.adapter = chatsAdapter
        rvChats.layoutManager = LinearLayoutManager(activity)
        rvChats.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
    }

    private fun initListeners() {
        favUsers onClick this::openUsersFragment
    }

    private fun getChats() {
        showProgress()
        getChatsViewModel.chats()
    }

    private fun renderChats(chats: List<ChatView>?) {
        chatsAdapter.collection = chats.orEmpty()

        chatsAdapter.clickListener = {
            navigator.showChatFragment(activity!!, it, User.empty().toUserView())
        }

        hideProgress()
    }

    private fun openUsersFragment() {
        navigator.showUsersFragment(activity!!)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.CustomError -> renderFailure(failure.errorCode, failure.errorMessage)
        }
    }

    private fun renderFailure(errorCode: Int, errorMessage: String?) {
        showError(errorCode, errorMessage, object : DialogCallback {
            override fun onAccept() {
                hideProgress()
            }

            override fun onDecline() {
                onBackPressed()
            }

        })
    }
}