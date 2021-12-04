package com.sarco.todoapp.fragments.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sarco.todoapp.R
import com.sarco.todoapp.data.models.Priority
import com.sarco.todoapp.data.models.ToDoData
import com.sarco.todoapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var dataList = emptyList<ToDoData>()

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(mContext),
            parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvTitleText.text = dataList[position].title
        holder.binding.tvDescription.text = dataList[position].description

        holder.binding.rowBackground.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }

        when (dataList[position].priority) {
            Priority.HIGH -> {
                holder.binding.cvPriorityIndicator
                    .setCardBackgroundColor(
                        ContextCompat
                            .getColor(holder.itemView.context, R.color.red)
                    )
            }
            Priority.MEDIUM -> {
                holder.binding.cvPriorityIndicator
                    .setCardBackgroundColor(
                        ContextCompat
                            .getColor(holder.itemView.context, R.color.yellow)
                    )
            }
            Priority.LOW -> {
                holder.binding.cvPriorityIndicator
                    .setCardBackgroundColor(
                        ContextCompat
                            .getColor(holder.itemView.context, R.color.green)
                    )
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(toDoData: List<ToDoData>){
        this.dataList = toDoData
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}