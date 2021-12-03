package com.sarco.todoapp.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarco.todoapp.R
import com.sarco.todoapp.data.viewModel.ToDoViewModel
import com.sarco.todoapp.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var mFragment : FragmentListBinding

    private val mTodoViewModel: ToDoViewModel by viewModels()

    private val adapter: ListAdapter by lazy {
        ListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mFragment =  FragmentListBinding.inflate(inflater, container, false)

        val recyclerView = mFragment.recyclerView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mTodoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })

        mFragment.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //setMenu

        setHasOptionsMenu(true)

        return mFragment.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}