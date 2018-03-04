package bupyc9.launcher.view.applist

import android.content.pm.ResolveInfo
import bupyc9.launcher.presenter.IPresenter
import bupyc9.launcher.view.IView

/**
 * @author Afanasyev Pavel <afanasev@worksolutions.ru>
 */
interface IAppList {
    interface View: IView {
        fun setItems(items: MutableList<ResolveInfo>)
    }

    interface Presenter: IPresenter<View> {
        fun openApp(item: ResolveInfo)
    }
}