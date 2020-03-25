package ke.co.ipandasoft.tukonewsclient.data.models


data class NewsCategoryResponse(
    val results: List<NewsCategory>

)


fun NewsCategoryResponse.mapToCategories(): List<NewsCategory> {
    val newsCats = this.results
    val newsCatsMutable = mutableListOf<NewsCategory>()
    for (newsCat in newsCats) {
        newsCatsMutable.add(
            NewsCategory(
                id = newsCat.id,
                name = newsCat.name,
                slug = newsCat.slug
            )
        )
    }
    return newsCatsMutable
}


