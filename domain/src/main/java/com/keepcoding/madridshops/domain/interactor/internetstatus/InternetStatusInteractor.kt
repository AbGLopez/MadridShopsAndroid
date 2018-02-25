package com.keepcoding.madridshops.domain.interactor.internetstatus

import android.content.Context
import com.keepcoding.madridshops.domain.interactor.CodeClosure
import com.keepcoding.madridshops.domain.interactor.ErrorClosure



interface InternetStatusInteractor {
    fun execute(context: Context, success: CodeClosure, error: ErrorClosure) {

    }
}