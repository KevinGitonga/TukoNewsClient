package ke.co.ipandasoft.tukonewsclient.ui.base

import androidx.lifecycle.ViewModel


abstract class BaseViewModel : ViewModel() {
    /**Inject Singlton ErrorManager
     * Use this errorManager to get the Errors
     */
    abstract fun start()

    abstract fun stop()

}
