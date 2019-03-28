package com.project.newsfeed.utility

import android.content.SearchRecentSuggestionsProvider


/**
 * Created by ErichPS on 28/03/2019.
 */

class SearchSuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        val AUTHORITY = "com.project.newsFeed.SearchSuggestionProvider"
        val MODE = DATABASE_MODE_QUERIES
    }
}