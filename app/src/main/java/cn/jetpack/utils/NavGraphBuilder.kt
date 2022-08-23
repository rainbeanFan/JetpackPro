package cn.jetpack.utils

import android.content.ComponentName
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator

class NavGraphBuilder {

    companion object {

        fun build(controller: NavController) {
            val provider = controller.navigatorProvider

            val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)

            val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)

            val navGraph = NavGraph(NavGraphNavigator(provider))

            val destConfig = AppConfig.getDestConfig()

            destConfig.values.forEach {
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

                if (it.asStarter) {
                    navGraph.startDestination = it.destinationId
                }
            }
            controller.graph = navGraph
        }

    }

}