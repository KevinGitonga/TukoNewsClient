package ke.co.ipandasoft.tukonewsclient.data.models


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("attributes")
    val attributes: Attributes?,
    @SerializedName("children")
    val children: List<Any?>?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("tagName")
    val tagName: String?
)