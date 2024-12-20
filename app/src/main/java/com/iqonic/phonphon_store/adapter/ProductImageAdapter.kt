package com.iqonic.phonphon_store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.iqonic.phonphon_store.R
import kotlinx.android.synthetic.main.layout_itemimage.view.*
import com.iqonic.phonphon_store.utils.extensions.loadImageFromUrl


class ProductImageAdapter(private var mImg: ArrayList<String>) : PagerAdapter() {

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_itemimage, parent, false).apply {
            imgSlider.loadImageFromUrl(mImg[position])
        }
        parent.addView(view)
        return view
    }

    override fun isViewFromObject(v: View, `object`: Any): Boolean = v === `object` as View

    override fun getCount(): Int = mImg.size

    override fun destroyItem(parent: ViewGroup, position: Int, `object`: Any) = parent.removeView(`object` as View)

}