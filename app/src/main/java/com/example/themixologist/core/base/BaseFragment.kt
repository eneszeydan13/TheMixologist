package com.example.themixologist.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

// VB: ViewBinding Type, VM: ViewModel Type
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    // Force child classes to provide the ViewModel
    protected abstract val viewModel: VM

    // Abstract function to inflate binding
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeState()
    }

    // Standard hooks for child fragments
    open fun initViews(){}

    open fun observeState() {
        // Here you could observe BaseViewModel events generically
        // e.g. handling global error snackbars
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevents memory leaks
    }
}