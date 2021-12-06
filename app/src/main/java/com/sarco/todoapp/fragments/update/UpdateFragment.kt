package com.sarco.todoapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sarco.todoapp.R
import com.sarco.todoapp.data.models.Priority
import com.sarco.todoapp.data.models.ToDoData
import com.sarco.todoapp.data.viewModel.ToDoViewModel
import com.sarco.todoapp.databinding.FragmentUpdateBinding
import com.sarco.todoapp.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private lateinit var mBinding: FragmentUpdateBinding
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: ToDoViewModel by viewModels()

    private val args by navArgs<UpdateFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUpdateBinding.inflate(inflater, container, false)

//        this will work anyway
        mBinding.args = args

        mBinding.prioritySpinnerUpdate.onItemSelectedListener = mSharedViewModel.listener

        setHasOptionsMenu(true)
        return mBinding.root
    }
// Inflate the layout for this fragment


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    //Show alert dialog to confirm removal
    private fun confirmItemRemoval() {

        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("Yes"){_, _ ->
            mTodoViewModel.deleteData(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Successfully deleted!",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete '${args.currentItem.title}'")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}' ?")
        builder.create().show()

    }


    private fun updateItem() {
        val title = mBinding.etTitleUpdate.text.toString()
        val description = mBinding.etDescriptionUpdate.text.toString()
        val getPriority = mBinding.prioritySpinnerUpdate.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)

        if (validation) {
            val updateItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )
            mTodoViewModel.updateData(updateItem)
            Toast.makeText(
                requireContext(),
                "Successfully added!",
                Toast.LENGTH_SHORT
            ).show()
            //navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(
                requireContext(),
                "Please fill all fields",
                Toast.LENGTH_SHORT
            ).show()

        }

    }
}