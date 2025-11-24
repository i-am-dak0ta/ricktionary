package com.dak0ta.ricktionary.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class DaggerViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val javaClass = modelClass.java
        var creator = creators[javaClass]

        if (creator == null) {
            creator = creators.entries.firstOrNull { (key, _) ->
                javaClass.isAssignableFrom(key)
            }?.value
        }

        requireNotNull(creator) {
            "Unknown ViewModel class: ${modelClass.qualifiedName}"
        }

        return try {
            creator.get() as T
        } catch (e: ReflectiveOperationException) {
            throw IllegalStateException("Failed to create ViewModel ${modelClass.simpleName}", e)
        } catch (e: ClassCastException) {
            throw IllegalStateException("Invalid ViewModel type: ${modelClass.simpleName}", e)
        }
    }
}
