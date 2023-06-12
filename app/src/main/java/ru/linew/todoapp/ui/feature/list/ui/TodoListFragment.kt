package ru.linew.todoapp.ui.feature.list.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.MaterialElevationScale
import ru.linew.todoapp.R
import ru.linew.todoapp.data.mapper.toUiLayer
import ru.linew.todoapp.data.repository.TodoItemsRepositoryImpl
import ru.linew.todoapp.databinding.FragmentTodoListBinding
import ru.linew.todoapp.ui.feature.list.interactor.TodoItemsRepository
import ru.linew.todoapp.ui.feature.list.model.TodoItem
import ru.linew.todoapp.ui.feature.list.ui.recycler.TodoListAdapter

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    private val binding: FragmentTodoListBinding by viewBinding()
    private val repository: TodoItemsRepository = TodoItemsRepositoryImpl()
    private val itemClickCallback: (View, TodoItem) -> Unit = { view: View, _: TodoItem ->
        val extras = FragmentNavigatorExtras(view to getString(R.string.card_edit_transition))
        findNavController().navigate(
            R.id.action_todoListFragment_to_todoAddFragment,
            null,
            null,
            extras
        )
    }
    private val adapter = TodoListAdapter(itemClickCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        binding.todoList.adapter = adapter
        adapter.submitList(repository.provideListOfTodo().map { it.toUiLayer() })
        //val touchHelper = ItemTouchHelper(SwipeToDeleteCallback(requireContext()))
        //touchHelper.attachToRecyclerView(binding.todoList)
        var visibilityState = true
        binding.visibilityIcon.apply {
            setOnClickListener {
                if (!visibilityState) {
                    setImageResource(R.drawable.visibility)
                    visibilityState = true
                } else {
                    setImageResource(R.drawable.visibility_off)
                    visibilityState = false
                }
            }
        }
        binding.addTodo.setOnClickListener {
            findNavController().navigate(R.id.action_todoListFragment_to_todoAddFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exitTransition = null
        reenterTransition = null

    }
}