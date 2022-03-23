package com.hasannagizade.movielistapp.data.model

class Pagination {
    var nextPage = 1
    var hasNext = false
    var isLoading = false

    fun incrementOrSkip(hasNext: Boolean) {
        if (hasNext)
            nextPage += 1
    }
}