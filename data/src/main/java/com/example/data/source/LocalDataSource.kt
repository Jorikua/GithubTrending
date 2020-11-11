package com.example.data.source

import android.content.Context
import com.example.data.R
import com.example.data.model.LanguageResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.buffer
import okio.source

class LocalDataSource(
    private val context: Context,
    private val moshi: Moshi
) {

    private val spokenLanguages = mutableListOf<LanguageResponse>()
    private val programmingLanguages = mutableListOf<LanguageResponse>()

    fun getSpokenLanguages(): List<LanguageResponse> {
        if (spokenLanguages.isEmpty()) {
            context.resources.openRawResource(R.raw.spoken_languages).source().buffer().use {
                val languages =
                    Types.newParameterizedType(List::class.java, LanguageResponse::class.java)
                val list =
                    moshi.adapter<List<LanguageResponse>>(languages).fromJson(it) ?: emptyList()
                spokenLanguages.addAll(list)
            }
        }

        return spokenLanguages
    }

    fun getProgrammingLanguages(): List<LanguageResponse> {
        if (programmingLanguages.isEmpty()) {
            context.resources.openRawResource(R.raw.programming_languages).source().buffer().use {
                val languages =
                    Types.newParameterizedType(List::class.java, LanguageResponse::class.java)
                val list =
                    moshi.adapter<List<LanguageResponse>>(languages).fromJson(it) ?: emptyList()
                programmingLanguages.addAll(list)
            }
        }

        return programmingLanguages
    }
}