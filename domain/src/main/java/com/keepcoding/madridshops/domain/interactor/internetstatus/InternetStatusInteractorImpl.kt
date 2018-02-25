package com.keepcoding.madridshops.domain.interactor.internetstatus

import android.content.Context
import com.keepcoding.madridshops.domain.interactor.CodeClosure
import com.keepcoding.madridshops.domain.interactor.ErrorClosure


class InternetStatusInteractorImpl() : InternetStatusInteractor {
    override fun execute(context: Context, success: CodeClosure, error: ErrorClosure) {
        success()
    }

}

