package ru.linew.todoapp.presentation.feature.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.model.TodoItem

class TodoListFragmentViewModel @AssistedInject constructor(val repository: TodoItemsRepository): ViewModel() {
    @AssistedFactory
    interface TodoListFragmentViewModelFactory{
        fun create(): TodoListFragmentViewModel
    }
    @Suppress("UNCHECKED_CAST")
    class Factory(private val factory: TodoListFragmentViewModelFactory) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }

    private val _todos = MutableLiveData<List<TodoItem>>()
    val todos: LiveData<List<TodoItem>>
        get() = _todos
    fun setupViewModelListener(){
        viewModelScope.launch {
            _todos.postValue(repository.provideListOfTodo())
        }
    }
}