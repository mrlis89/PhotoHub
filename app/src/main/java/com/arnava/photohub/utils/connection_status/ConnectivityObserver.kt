package com.arnava.photohub.utils.connection_status

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {

        Available, Unavailable, Losing, Lost

    }

}