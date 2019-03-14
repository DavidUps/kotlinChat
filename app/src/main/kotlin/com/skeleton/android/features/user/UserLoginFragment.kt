package com.skeleton.android.features.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.skeleton.android.R
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.failure
import com.skeleton.android.core.extension.observe
import com.skeleton.android.core.extension.onClick
import com.skeleton.android.core.extension.viewModel
import com.skeleton.android.core.functional.DialogCallback
import com.skeleton.android.core.navigation.Navigator
import com.skeleton.android.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_userlogin.*
import javax.inject.Inject

class UserLoginFragment: BaseFragment(){

    override fun layoutId(): Int = R.layout.fragment_userlogin

    @Inject
    lateinit var navigator: Navigator

    private lateinit var doLoginViewModel: DoLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        doLoginViewModel = viewModel(viewModelFactory){
            observe(trigger, ::renderDoLogin)
            failure(failure, ::handleFailure)
        }

        if (FirebaseAuth.getInstance().currentUser != null){
            navigator.showUsersFragment(activity!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }
    private fun initListeners() {
        btnLoggin onClick this::doLogin
    }

    private fun doLogin() {
        showProgress()
        doLoginViewModel.doLogin(Authentication(etName.text.toString(), etPassword.text.toString()))
    }

    private fun renderDoLogin(boolean: Boolean?) {
        hideProgress()
        if (boolean!!) {
            navigator.showChatListFragment(activity!!)
        } else {
            Toast.makeText(context, "Login incorrecto", Toast.LENGTH_SHORT).show()
        }
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