package cn.jetpack.ui.sofa

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cn.jetpack.databinding.FragmentSofaBinding
import cn.jetpack.model.SofaTab
import cn.jetpack.ui.home.HomeFragment
import cn.jetpack.utils.AppConfig
import cn.libnavannotation.FragmentDestination
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


@FragmentDestination(pageUrl = "main/tabs/sofa", asStarter = false)
class SofaFragment : Fragment() {

    private lateinit var mBinding: FragmentSofaBinding
    private lateinit var mViewPager2: ViewPager2
    private lateinit var mTabLayout: TabLayout
    private lateinit var mTabConfig: SofaTab
    private var mTabs = ArrayList<SofaTab.Tabs>()

    private lateinit var mLayoutMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSofaBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewPager2 = mBinding.viewPager
        mTabLayout = mBinding.tabLayout
        mTabConfig = AppConfig.getSoftTabConfig(requireContext())

        mTabConfig.tabs.forEach { tab ->
            if (tab.enable) mTabs.add(tab)
        }

//        禁止页面预加载
        mViewPager2.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT

        mViewPager2.adapter = object : FragmentStateAdapter(childFragmentManager, lifecycle) {
            override fun getItemCount() = mTabs.size

            override fun createFragment(position: Int): Fragment {
                return HomeFragment.newInstance(mTabs[position].tag)
            }

        }

        mTabLayout.tabGravity = mTabConfig.tabGravity

        mLayoutMediator = TabLayoutMediator(mTabLayout, mViewPager2, true)
        { tab, position -> tab.customView = makeTabView(position) }

        mLayoutMediator.attach()
        mViewPager2.registerOnPageChangeCallback(mPageChangeCallback)

        mViewPager2.post {
            mViewPager2.setCurrentItem(mTabConfig.select, false)
        }

    }

    private var mPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            val tabCount = mTabLayout.tabCount
            for (i in 0 until tabCount) {
                val tab = mTabLayout.getTabAt(i)
                val customView = tab!!.customView as TextView
                if (tab.position == position) {
                    customView.textSize = mTabConfig.activeSize.toFloat()
                    customView.typeface = Typeface.DEFAULT_BOLD
                } else {
                    customView.textSize = mTabConfig.normalSize.toFloat()
                    customView.typeface = Typeface.DEFAULT
                }
            }
        }
    }

    private fun makeTabView(position: Int): View {
        val textview = TextView(requireContext())
        val states = arrayOf(intArrayOf())
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf()



        return textview
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        val fragments = childFragmentManager.fragments
        fragments.forEach { fragment ->
            if (fragment.isAdded && fragment.isVisible) {
                fragment.onHiddenChanged(hidden)
            }
        }
    }

    override fun onDestroy() {
        mLayoutMediator.detach()
        mViewPager2.unregisterOnPageChangeCallback(mPageChangeCallback)
        super.onDestroy()
    }

}