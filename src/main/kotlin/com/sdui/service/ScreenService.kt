package com.sdui.service

import com.sdui.model.ScreenResponse
import com.sdui.repository.ScreenRepository
import org.springframework.stereotype.Service

class ScreenNotFoundException(screenName: String) :
    RuntimeException("Screen not found: $screenName")

@Service
class ScreenService(private val repository: ScreenRepository) {

    fun getScreen(screenName: String): ScreenResponse {
        val components = repository.findByName(screenName)
            ?: throw ScreenNotFoundException(screenName)
        return ScreenResponse(screenName = screenName, components = components)
    }
}
