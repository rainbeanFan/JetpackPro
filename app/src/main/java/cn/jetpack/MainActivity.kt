package cn.jetpack

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import cn.commonlibrary.utils.StatusBar
import cn.jetpack.databinding.ActivityMainBinding
import cn.jetpack.utils.AppConfig
import cn.jetpack.utils.NavGraphBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)
        StatusBar.fitSystemBar(this)

        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        mNavController = NavHostFragment.findNavController(fragment!!)

//        NavGraphBuilder.build(this, fragment.childFragmentManager, mNavController, fragment.id)

//        mBinding.navView.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        val destConfig = AppConfig.getDestConfig(this)
//        val iterator = destConfig.entries.iterator()
//        while (iterator.hasNext()) {
//            val entry = iterator.next()
//            val value = entry.value
//            if (value != null && value.needLogin && value.destinationId == item.itemId) {
//
//                return false
//            }
//        }
//
//        mNavController.navigate(item.itemId)
//        return item.title.isNullOrBlank()

        return true

    }

}