package bupyc9.launcher.presenter

import bupyc9.launcher.view.IView

abstract class BasePresenter<V: IView>: IPresenter<V> {
    private var view: V? = null

    override fun attachView(view: V?) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun destroy() {

    }

    fun getView(): V? = view
}