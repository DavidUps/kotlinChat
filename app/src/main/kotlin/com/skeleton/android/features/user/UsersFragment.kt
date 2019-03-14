package com.skeleton.android.features.user

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.skeleton.android.R
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.extension.failure
import com.skeleton.android.core.extension.observe
import com.skeleton.android.core.extension.viewModel
import com.skeleton.android.core.functional.DialogCallback
import com.skeleton.android.core.navigation.Navigator
import com.skeleton.android.core.platform.BaseFragment
import com.skeleton.android.features.chat.AddChatViewModel
import com.skeleton.android.features.chat.Chat
import com.skeleton.android.features.chat.ChatExistViewModel
import kotlinx.android.synthetic.main.fragment_user.*
import javax.inject.Inject

class UsersFragment : BaseFragment() {

    @Inject
    lateinit var userAdapter: UserAdapter

    @Inject
    lateinit var navigator: Navigator

    private lateinit var getGetUsersViewModel: GetUsersViewModel
    private lateinit var updateStatusViewModel: UpdateStatusViewModel
    private lateinit var chatExistViewModel: ChatExistViewModel
    private lateinit var addChatViewModel: AddChatViewModel

    override fun layoutId() = R.layout.fragment_user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        getGetUsersViewModel = viewModel(viewModelFactory) {
            observe(users, ::renderUserList)
            failure(failure, ::handleFailure)
        }

        updateStatusViewModel = viewModel(viewModelFactory) {
            failure(failure, ::handleFailure)
        }

        chatExistViewModel = viewModel(viewModelFactory) {
            failure(failure, ::handleFailure)
        }

        addChatViewModel = viewModel(viewModelFactory) {
            observe(trigger, ::renderAddChat)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiView()
    }

    private fun initiView() {
        getUser()
        rvUser.adapter = userAdapter

        rvUser.layoutManager = LinearLayoutManager(activity)
        rvUser.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
    }

    private fun getUser() {
        showProgress()
        getGetUsersViewModel.users()
    }

    private fun renderUserList(list: List<UserView>?) {
        userAdapter.collection = list.orEmpty()
        userAdapter.clickListener = {
            navigator.showChatFragment(activity!!, Chat.empty().toChatView(), it)
        }
        hideProgress()
    }

    private fun renderAddChat(b: Boolean?) {

    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.CustomError -> renderFailure(failure.errorCode, failure.errorMessage)
        }
    }

    private fun renderFailure(errorCode: Int, errorMessage: String?) {
        showError(errorCode, errorMessage, object : DialogCallback {
            override fun onAccept() {
            }

            override fun onDecline() {
                onBackPressed()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        updateStatusViewModel.updateStatus(true)
    }

    override fun onPause() {
        super.onPause()
        updateStatusViewModel.updateStatus(false)
    }
}

