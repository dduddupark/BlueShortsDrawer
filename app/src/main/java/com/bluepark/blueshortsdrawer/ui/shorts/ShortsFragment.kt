package com.bluepark.blueshortsdrawer.ui.shorts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.bluepark.blueshortsdrawer.R
import com.bluepark.blueshortsdrawer.databinding.FragmentShortsBinding
import com.bluepark.blueshortsdrawer.ui.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.abs

class ShortsFragment : Fragment() {

    private lateinit var binding: FragmentShortsBinding

    companion object {
        fun newInstance() = ShortsFragment()
    }

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by activityViewModels<ShortsViewModel>()

    private lateinit var adapter: ShortsRecyclerViewAdapter

    private var x1 = 0f
    private var x2 = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_shorts, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()

        adapter = ShortsRecyclerViewAdapter()
        binding.viewPager.adapter = adapter
        viewModel.getList()
    }

    private fun initView() {
        binding.btnSwipe.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> x1 = event.x
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    if (abs(x2 - x1) > 150) {
                        mainViewModel.closeDrawer()
                    }
                }
            }
            return@OnTouchListener false
        })

        binding.btnClose.setOnClickListener {
            mainViewModel.closeDrawer()
        }

        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Timber.d("blue onPageSelected = $position, ${adapter.position}")
                if (position == adapter.position) {
                    viewModel.getShorts()
                }
            }
        })
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    launch {
                        shorts.collectLatest {
                            Timber.d("it = $it")
                            adapter.addShorts(it)
                        }
                    }
                }
            }
        }
    }
}