package bupyc9.launcher.view.applist

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import bupyc9.launcher.presenter.BasePresenter

/**
 * @author Afanasyev Pavel <afanasev@worksolutions.ru>
 */
class AppListPresenter(private val mContext: Context): BasePresenter<IAppList.View>(), IAppList.Presenter {
    override fun viewIsReady() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val items = mContext.packageManager.queryIntentActivities(intent, 0)

        items.sortBy { it.loadLabel(mContext.packageManager).toString() }

        getView()?.setItems(items)
    }

    override fun openApp(item: ResolveInfo) {
        val activityInfo = item.activityInfo
        val intent = Intent(Intent.ACTION_MAIN)
        intent.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mContext.startActivity(intent)
    }
}