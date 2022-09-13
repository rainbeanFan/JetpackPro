package cn.jetpack.ui.sofa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cn.jetpack.databinding.FragmentSofaBinding
import cn.jetpack.model.SofaTab
import cn.jetpack.utils.AppConfig
import cn.libnavannotation.FragmentDestination
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


@FragmentDestination(pageUrl = "main/tabs/sofa", asStarter = false)
class SofaFragment: Fragment() {

    private lateinit var mBinding:FragmentSofaBinding
    private lateinit var mViewPager2: ViewPager2
    private lateinit var mTabLayout: TabLayout
    private lateinit var mTabConfig:SofaTab
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

        mTabConfig.tabs.forEach {  tab ->
            if (tab.enable) mTabs.add(tab)
        }

//        禁止页面预加载
        mViewPager2.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT

        mViewPager2.adapter = object : FragmentStateAdapter(childFragmentManager,lifecycle) {
            override fun getItemCount() = mTabs.size

            override fun createFragment(position: Int): Fragment {
                TODO("Not yet implemented")
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }

}