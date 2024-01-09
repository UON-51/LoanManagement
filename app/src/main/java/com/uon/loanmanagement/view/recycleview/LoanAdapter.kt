package com.uon.loanmanagement.view.recycleview


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uon.loanmanagement.databinding.ItemLoanBinding
import com.uon.loanmanagement.model.LoanEntity
import com.uon.loanmanagement.viewmodel.LoanViewModel
import java.text.SimpleDateFormat
import java.util.Date

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
            //todo go to edit fragment view

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

@BindingAdapter("convertLongToDate")
fun convertLongToDate(textView:TextView,date:Long){
    //todo To get local formatting use `getDateInstance()`, `getDateTimeInstance()`, or `getTimeInstance()`, or use `new SimpleDateFormat(String template, Locale locale)` with for example `Locale.US` for ASCII dates.
    textView.text = SimpleDateFormat("yyyy/MM/dd").format(Date(date))
}