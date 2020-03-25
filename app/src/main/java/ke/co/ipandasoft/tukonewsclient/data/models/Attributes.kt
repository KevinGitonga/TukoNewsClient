package ke.co.ipandasoft.tukonewsclient.data.models


import com.google.gson.annotations.SerializedName

data class Attributes(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("src")
    val src: String?,
    @SerializedName("width")
    val width: Int?
)