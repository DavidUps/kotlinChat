package com.skeleton.android.features.chat

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.skeleton.android.R
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.*
import com.skeleton.android.core.functional.DialogCallback
import com.skeleton.android.core.platform.BaseFragment
import com.skeleton.android.features.user.UserView
import kotlinx.android.synthetic.main.fragment_chat.*
import javax.inject.Inject

class ChatFragment: BaseFragment(){
    override fun layoutId(): Int = R.layout.fragment_chat

    @Inject
    lateinit var messageAdapter: MessageAdapter

    private lateinit var chatExistViewModel: ChatExistViewModel
    private lateinit var getMessagesViewModel: GetMessagesViewModel
    private lateinit var addMessageViewModel: AddMessageViewModel

    private lateinit var chatID: String
    private lateinit var with: String
    private lateinit var withID: String

    companion object {
        fun newInstance(chat: ChatView, user: UserView): ChatFragment{
            val fragment = ChatFragment()
            val args = Bundle()
            args.putParcelable("chat", chat)
            args.putParcelable("user", user)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        chatExistViewModel = viewModel(viewModelFactory){
            failure(failure, ::handleFailure)
        }

        getMessagesViewModel = viewModel(viewModelFactory){
            observe(messageList, ::renderMessage)
            failure(failure, ::handleFailure)
        }

        addMessageViewModel = viewModel(viewModelFactory){
            failure(failure, ::handleFailure)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments
        if (arguments != null){
            var chat: ChatView = arguments.getParcelable("chat")
            chatID = chat.chatID
            var user: UserView = arguments.getParcelable("user")

            if (user.userID.isNullOrEmpty()){
                withID = chat.withID
                with = chat.with
            } else{
                withID = user.userID
                with = user.name
            }

        }

        initLayout()
        initListeners()

    }


    private fun initLayout() {

        rvMessageList.adapter = messageAdapter
        rvMessageList.layoutManager = LinearLayoutManager(activity)

        isChatExist()
    }


    private fun initListeners() {
        btnSendMessage onClick this::sendMessage
    }

    private fun isChatExist() {
        showProgress()
        chatExistViewModel.chatExist(chatID, object: FirebaseAuthenticacionCallback {
            override fun onSucces(boolean: Boolean) {
                if (boolean) {
                    getMessagesViewModel.getMessages(chatID)
                }
                else{
                    hideProgress()
                }
            }

            override fun onError(failure: Failure) {
                Failure.ServerError()
            }

        })
    }

    private fun renderMessage(list: List<MessageView>?) {
        messageAdapter.collection = list.orEmpty()
        hideProgress()
    }

    private fun sendMessage(){
        showProgress()
        addMessageViewModel.addMessage(etMessage.text.toString(), chatID, with, withID)
        etMessage.setText("")
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
