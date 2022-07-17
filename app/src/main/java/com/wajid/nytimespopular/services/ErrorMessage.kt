package com.wajid.nytimespopular.services

import com.wajid.nytimespopular.R


data class ErrorMessage(
    val messageString: String? = null,
    val messageId: Int = R.string.alert_internet
) {

    fun parse(provider: (id: Int) -> String): String {
        return messageString ?: provider.invoke(messageId)
    }

}