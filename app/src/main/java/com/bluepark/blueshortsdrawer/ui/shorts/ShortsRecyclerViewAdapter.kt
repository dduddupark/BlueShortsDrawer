package com.bluepark.blueshortsdrawer.ui.shorts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bluepark.blueshortsdrawer.R
import com.bluepark.blueshortsdrawer.databinding.FragmentShortsItemBinding
import timber.log.Timber

data class Shorts(
    val name: String? = ""
)

class ShortsRecyclerViewAdapter : RecyclerView.Adapter<ShortsRecyclerViewAdapter.ViewHolderPage>() {

    private var items: ArrayList<Shorts?> = arrayListOf()

    var position = -1

    private lateinit var binding: FragmentShortsItemBinding

    fun addShorts(shorts: Shorts?) {
        Timber.d("position aaa = $position")
        items.add(shorts)
        position++
        notifyItemChanged(position)
        Timber.d("position bbb = $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPage {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.fragment_shorts_item, parent, false)
        return ViewHolderPage(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderPage, position: Int) {
        holder.setData(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolderPage(private val binding: FragmentShortsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(shorts: Shorts?) {
            binding.shorts = shorts
        }
    }
}