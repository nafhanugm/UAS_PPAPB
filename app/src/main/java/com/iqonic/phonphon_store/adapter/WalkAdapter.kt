package com.iqonic.phonphon_store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.iqonic.phonphon_store.R
import kotlinx.android.synthetic.main.layout_walk.view.*

class WalkAdapter : PagerAdapter() {

    private val mImg = arrayOf(
        R.drawable.ic_walk_1,
        R.drawable.ic_trends,
        R.drawable.ic_walk_3
    )

    override fun isViewFromObject(v: View, `object`: Any): Boolean {
        return v === `object` as View
    }

    override fun getCount(): Int {
        return mImg.size
    }

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_walk, parent, false)

        view.ivWalk.setImageResource(mImg[position])

        parent.addView(view)
        return view
    }

    override fun destroyItem(parent: ViewGroup, position: Int, `object`: Any) {
        parent.removeView(`object` as View)
    }

}