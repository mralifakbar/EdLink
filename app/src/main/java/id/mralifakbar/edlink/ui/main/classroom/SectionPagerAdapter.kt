package id.mralifakbar.edlink.ui.main.classroom

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ClassAcademicFragment()
            1 -> fragment = ClassGeneralFragment()
        }
        return fragment as Fragment
    }

}