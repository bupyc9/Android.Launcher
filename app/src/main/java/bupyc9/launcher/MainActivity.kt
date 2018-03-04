package bupyc9.launcher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import bupyc9.launcher.view.applist.AppListFragment

class MainActivity : AppCompatActivity(), IActivity {
    companion object {
        @JvmStatic
        private val FRAGMENT_TAG = "fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setFragment(AppListFragment.newInstance())
        }
    }

    override fun setFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fm_container, fragment, FRAGMENT_TAG)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}