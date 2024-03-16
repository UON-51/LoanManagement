package com.uon.loanmanagement.view.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uon.loanmanagement.databinding.ItemLoanBinding
import com.uon.loanmanagement.model.LoanEntity
import com.uon.loanmanagement.viewmodel.LoanViewModel

class LoanAdapter(private val viewModel: LoanViewModel) : RecyclerView.Adapter<LoanAdapter.LoanViewHolder>() {
    private val items: MutableList<LoanEntity> = mutableListOf()

    fun setItems(newItems: List<LoanEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
        Log.d("RoomTest", "Adapter items updated: $items")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoanBinding.inflate(inflater, parent, false)
        return LoanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener{
            Log.d("RecycleViewTest",item.toString())
            viewModel.loanSelected(item)
        }

    }

    override fun getItemCount(): Int = items.size

    class LoanViewHolder(private val binding: ItemLoanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LoanEntity) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

}

