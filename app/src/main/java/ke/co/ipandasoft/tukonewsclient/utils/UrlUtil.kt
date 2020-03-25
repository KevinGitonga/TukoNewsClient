package ke.co.ipandasoft.tukonewsclient.utils



/**
 * 判断是否是http协议的url
 * @return true：scheme不是http或者https，表示需要尝试调起相关原生页面
 */
fun String.isOpenApp(): Boolean {
    return !(this.startsWith("http") || this.startsWith("https"))
}