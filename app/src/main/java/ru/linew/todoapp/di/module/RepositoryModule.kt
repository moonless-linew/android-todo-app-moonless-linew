package ru.linew.todoapp.di.module

import dagger.Binds
import dagger.Module
import ru.linew.todoapp.data.repository.TodoItemsRepositoryImpl
import ru.linew.todoapp.di.scope.AppScope
import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository

@Module
interface RepositoryModule {

    @AppScope
    @Binds
    fun bindTodoRepository(todoRepository: TodoItemsRepositoryImpl): TodoItemsRepository

}
