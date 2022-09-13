package cn.jetpack.utils

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import cn.commonlibrary.global.AppGlobal
import cn.jetpack.navigator.FixFragmentNavigator

class NavGraphBuilder {

    companion object {

        fun build(activity: FragmentActivity, childFragmentManager: FragmentManager, controller: NavController,containerId:Int) {
            val provider = controller.navigatorProvider
            val navGraph = NavGraph(NavGraphNavigator(provider))

            val fragmentNavigator = FixFragmentNavigator(activity, childFragmentManager, containerId)
            provider.addNavigator(fragmentNavigator)

            val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)

            val destConfig = AppConfig.getDestConfig(activity)

            destConfig!!.values.forEach {
                if (it != null) {
                    if (it.isFragment) {
                        val destination = fragmentNavigator.createDestination()
                        destination.className = it.className
                        destination.id = it.destinationId
                        destination.addDeepLink(it.pagUrl)
                        navGraph.addDestination(destination)
                    } else {
                        val destination = activityNavigator.createDestination()
                        destination.id = it.destinationId
                        destination.addDeepLink(it.pagUrl)
                        destination.setComponentName(
                            ComponentName(AppGlobal.getApplication().packageName, it.className)
                        )
                        navGraph.addDestination(destination)
                    }
                }

                if (it != null) {
                    if (it.asStarter) {
                        navGraph.startDestination = it.destinationId
                    }
                }
            }
            controller.graph = navGraph
        }

    }

}