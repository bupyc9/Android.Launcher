package bupyc9.launcher

import android.support.v4.app.Fragment

interface IActivity {
    fun setFragment(fragment: Fragment, addToBackStack: Boolean = false)
}