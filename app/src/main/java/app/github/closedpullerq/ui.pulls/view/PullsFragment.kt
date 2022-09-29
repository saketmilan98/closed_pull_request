package app.github.closedpullerq.ui.pulls.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import app.github.closedpullerq.R
import app.github.closedpullerq.databinding.FragmentPullsBinding
import app.github.closedpullerq.ui.pulls.adapter.PullsDataAdapter
import app.github.closedpullerq.ui.pulls.viewmodel.MainViewModel

class PullsFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentPullsBinding

    private val pullDataAdapter by lazy { PullsDataAdapter(requireContext(),
        onItemClicked = { pullItem, position ->
            viewModel.setCurrentPullItem(pullItem)
            requireActivity().supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, PullsDetailFragment()).addToBackStack(null).commit()
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
        binding.toolbarLayout.toolbarBackIv.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.pullListRv.adapter = pullDataAdapter
        setPullRvData()
    }

    fun setPullRvData(){
        pullDataAdapter.setPullItemList(viewModel.pullsInfo.value?.data)
    }

}