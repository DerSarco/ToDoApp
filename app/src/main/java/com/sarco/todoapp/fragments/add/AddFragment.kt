package com.sarco.todoapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sarco.todoapp.R
import com.sarco.todoapp.data.models.Priority
import com.sarco.todoapp.data.models.ToDoData
import com.sarco.todoapp.data.viewModel.ToDoViewModel
import com.sarco.todoapp.databinding.FragmentAddBinding
import kotlinx.coroutines.processNextEventInCurrentThread

class AddFragment : Fragment() {

    private lateinit var mBinding: FragmentAddBinding

    private val mTodoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentAddBinding.inflate(inflater, container, false)


        setHasOptionsMenu(true)
        return mBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add){
            insertDataToDB()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDB() {
        val mTitle = mBinding.etTitle.text.toString()
        val mPriority = mBinding.prioritySpinner.selectedItem.toString()
        val mDescription = mBinding.etDescription.text.toString()

        val validation = verifyDataFromUser(mTitle, mDescription)

        if(validation){
            //INsert data to data db
            val newData = ToDoData(
                0,
                mTitle,
                parsePriority(mPriority),
                mDescription
            )
            mTodoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Succesfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

    }

    private fun verifyDataFromUser(title:String, description: String): Boolean{
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    private fun parsePriority(priority: String): Priority{
        return when(priority){
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            "Low Priority" -> Priority.LOW
            else ->  Priority.HIGH
        }
    }
}