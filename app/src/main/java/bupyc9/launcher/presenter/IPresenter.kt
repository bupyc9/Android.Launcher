package bupyc9.launcher.presenter

import bupyc9.launcher.view.IView

interface IPresenter<in V: IView> {
    fun attachView(view: V?)
    fun detachView()
    fun destroy()
    fun viewIsReady()
}