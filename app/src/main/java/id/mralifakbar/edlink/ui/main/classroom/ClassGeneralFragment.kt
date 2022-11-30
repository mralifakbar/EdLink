package id.mralifakbar.edlink.ui.main.classroom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.mralifakbar.edlink.MainActivity
import id.mralifakbar.edlink.R
import id.mralifakbar.edlink.databinding.FragmentAccountBinding
import id.mralifakbar.edlink.databinding.FragmentClassAcademicBinding
import id.mralifakbar.edlink.databinding.FragmentClassGeneralBinding
import id.mralifakbar.edlink.ui.splashscreen.SplashMiddleActivity


class ClassGeneralFragment : Fragment() {

    private lateinit var binding: FragmentClassGeneralBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fabAddClass.setOnClickListener {
                startActivity(Intent(requireContext(), CreateClassActivity::class.java))
            }
        }
    }
}