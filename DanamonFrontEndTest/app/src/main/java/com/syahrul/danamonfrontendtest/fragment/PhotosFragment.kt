package com.syahrul.danamonfrontendtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syahrul.danamonfrontendtest.adapter.PhotoListAdapter
import com.syahrul.danamonfrontendtest.databinding.FragmentPhotosBinding
import com.syahrul.danamonfrontendtest.model.Result
import com.syahrul.danamonfrontendtest.viewmodel.UserViewModel

class PhotosFragment : BaseViewBindingFragment<FragmentPhotosBinding>() {

    companion object {
        fun instantiate() = PhotosFragment()

        var currentPage = 1
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhotosBinding
        get() = FragmentPhotosBinding::inflate

    private lateinit var userViewModel: UserViewModel
    private lateinit var photoListAdapter: PhotoListAdapter

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = getFragmentViewModel()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getPhotoListResult().observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Success -> {
                    setIsLoading(false)
                    val photoList = it.data
                    if(currentPage == 1){
                        photoListAdapter = PhotoListAdapter(requireContext(), ArrayList(photoList))

                        binding.rcvPhotoList.layoutManager = LinearLayoutManager(requireContext())
                        binding.rcvPhotoList.adapter = photoListAdapter
                    } else {
                        photoListAdapter.refreshData(photoList)
                    }
                }
                is Result.Error -> {
                    setIsLoading(false)
                    showToastMessage("${it.exception}")
                }
                else -> {
                    setIsLoading(true)
                }
            }
        })

        loadMore()

        binding.rcvPhotoList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                if (!isLoading) {
                    if (linearLayoutManager != null &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() === photoListAdapter.getPhotoListSize() - 1) {
                        currentPage++
                        loadMore()
                    }
                }
            }
        })

    }

    private fun loadMore(){
        userViewModel.getPhotoList(currentPage, 10)
    }

    private fun setIsLoading(loading: Boolean){
        isLoading = loading
        binding.progressBar.visibility = if(isLoading) VISIBLE else GONE
    }

}