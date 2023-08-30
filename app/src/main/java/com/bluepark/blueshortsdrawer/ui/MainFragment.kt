package com.bluepark.blueshortsdrawer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bluepark.blueshortsdrawer.R
import com.bluepark.blueshortsdrawer.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        //drawer 안을 클릭 할 수 있게 하기 위헤
        binding.fragmentNews.rootView.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }

        binding.layoutDrawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                //클릭으로 drawer가 닫히는걸 방지하기 위해
                binding.layoutDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    launch {
                        closeDrawer.collect {
                            //drawer 닫으면서 slide action 다시 가능하게
                            binding.layoutDrawer.closeDrawer(GravityCompat.START)
                            binding.layoutDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                        }
                    }
                }
            }
        }
    }
}