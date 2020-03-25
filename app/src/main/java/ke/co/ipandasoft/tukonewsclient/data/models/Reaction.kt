package ke.co.ipandasoft.tukonewsclient.data.models


import com.google.gson.annotations.SerializedName

data class Reaction(
    @SerializedName("key")
    val key: String?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("value")
    val value: Int?
)