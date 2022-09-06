package cn.jetpack.navigator

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.ArrayDeque

@Navigator.Name("fixfragment")
class FixFragmentNavigator(
    val context: Context, private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        if (manager.isStateSaved) {
            return null
        }
        var className = destination.className
        if (className[0].toString() == ".") {
            className = context.packageName + className
        }

        val ft = manager.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.enterAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popEnterAnim ?: -1

        if ((enterAnim != -1) or (exitAnim != -1) or (popEnterAnim != -1) or (popExitAnim != -1)) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        val fragment = manager.primaryNavigationFragment
        if (fragment != null) {
            ft.hide(fragment)
        }

        var frag: Fragment? = null
        val tag = destination.id.toString()

        frag = manager.findFragmentByTag(tag)
        if (frag != null) {
            ft.show(frag)
        } else {
            frag = instantiateFragment(context, manager, className, args)
            frag.arguments = args
            ft.add(containerId, frag)
        }

        ft.setPrimaryNavigationFragment(frag)

        @IdRes val destId = destination.id
        var mBackStack: ArrayDeque<Int>? = null
        try {
            val field = FragmentNavigator::class.java.getDeclaredField("mBackStack")
            field.isAccessible = true
            mBackStack = field.get(this) as ArrayDeque<Int>?

        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        val initialNavigation = mBackStack?.isEmpty()

        val isSingleTopReplacement = ((navOptions != null) && (initialNavigation != true)
                && (navOptions.shouldLaunchSingleTop()) && (mBackStack?.peekLast() == destId))

        var isAdded = false
        if (initialNavigation == true) {
            isAdded = true
        } else if (isSingleTopReplacement) {
            if (mBackStack?.size!! > 1) {
                manager.popBackStack(
                    generateBackStackName(mBackStack!!.size, mBackStack!!.peekLast()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                ft.addToBackStack(generateBackStackName(mBackStack!!.size, destId))
            }
            isAdded = false
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack!!.size + 1, destId))
            isAdded = true
        }

        if (navigatorExtras is Extras) {
            navigatorExtras.sharedElements.forEach {
                ft.addSharedElement(it.key, it.value)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()

        if (isAdded) {
            mBackStack?.add(destId)
            return destination
        } else {
            return null
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "${backStackIndex}-$destId"
    }

}