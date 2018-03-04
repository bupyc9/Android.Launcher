package bupyc9.launcher.view.applist


import android.content.pm.ResolveInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bupyc9.launcher.R
import kotlinx.android.synthetic.main.fragment_app_list.*

class AppListFragment : Fragment(), IAppList.View {
    companion object {
        @JvmStatic
        private val TAG = AppListFragment::class.java.simpleName

        @JvmStatic
        private val COUNT_COLUMNS = 4

        @JvmStatic
        fun newInstance(): AppListFragment {
            return AppListFragment()
        }
    }

    private lateinit var mAdapter: AppListAdapter
    private lateinit var mPresenter: AppListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter = AppListAdapter(mutableListOf(), activity)
        mPresenter = AppListPresenter(activity)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_app_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app_list.layoutManager = GridLayoutManager(activity, COUNT_COLUMNS)
        app_list.adapter = mAdapter
        mAdapter.onClickListener = { _, item ->
            mPresenter.openApp(item)
        }

        app_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mAdapter.filter.filter(value)
            }
        })

        mPresenter.attachView(this)
        mPresenter.viewIsReady()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.destroy()
    }

    override fun setItems(items: MutableList<ResolveInfo>) {
        Log.d(TAG, "setItems: count - ${items.size}")
        mAdapter.addAll(items)
    }
}
