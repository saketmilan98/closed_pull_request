package app.github.closedpullerq.ui.pulls.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import app.github.closedpullerq.R
import app.github.closedpullerq.databinding.FragmentPullsBinding
import app.github.closedpullerq.ui.pulls.adapter.PullsDataAdapter
import app.github.closedpullerq.ui.pulls.viewmodel.MainViewModel
import app.github.closedpullerq.utils.Status
import app.github.closedpullerq.utils.showToast

class PullsFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentPullsBinding

    private val pullDataAdapter by lazy { PullsDataAdapter(requireContext(),
        onItemClicked = { pullItem, position ->
            viewModel.setCurrentPullItem(pullItem)
            findNavController().navigate(R.id.action_pullsFragment_to_pullsDetailFragment)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPullsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarLayout.toolbarTitleTv.text = getString(R.string.app_name)
        binding.toolbarLayout.toolbarBackIv.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.ic_github_icon, requireActivity().theme))
        binding.pullListRv.adapter = pullDataAdapter

        if(viewModel.pullsInfo.value == null){
            lifecycleScope.launchWhenCreated {
                viewModel.getPullsInfo("closed")
            }
        }

        viewModel.pullsInfo.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    it.data?.let { mainInfo ->
                        pullDataAdapter.setPullItemList(mainInfo)
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    requireContext().showToast("Api Call Failed: ${it.message}")
                }
            }
        })
    }
}