package com.sarco.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarco.todoapp.R
import com.sarco.todoapp.data.viewModel.ToDoViewModel
import com.sarco.todoapp.databinding.FragmentListBinding
import com.sarco.todoapp.fragments.SharedViewModel

class ListFragment : Fragment() {

    private lateinit var mBinding : FragmentListBinding

    private val mTodoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel : SharedViewModel by viewModels()

    private val adapter: ListAdapter by lazy {
        ListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding =  FragmentListBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        mBinding.mSharedViewModel = mSharedViewModel
        setupRecyclerView()

        //Observing live data
        mTodoViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })


        //setMenu
        setHasOptionsMenu(true)

        return mBinding.root

    }

    private fun setupRecyclerView() {
        mBinding.recyclerView.adapter = adapter
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_deleteAll){
            confirmRemoveAll()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoveAll() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("Yes"){_, _ ->
            mTodoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully deleted all data",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to remove all data")
        builder.create().show()
    }

}