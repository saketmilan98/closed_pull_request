package app.github.closedpullerq.ui.pulls.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import app.github.closedpullerq.R
import app.github.closedpullerq.databinding.FragmentPullsDetailBinding
import app.github.closedpullerq.ui.pulls.adapter.PullsDataAdapter
import app.github.closedpullerq.ui.pulls.model.PullsDataClassItem
import app.github.closedpullerq.ui.pulls.viewmodel.MainViewModel
import app.github.closedpullerq.utils.inputFormat
import app.github.closedpullerq.utils.loadImage
import app.github.closedpullerq.utils.outputFormat
import java.util.*

class PullsDetailFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentPullsDetailBinding
    lateinit var currentItem : PullsDataClassItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPullsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarLayout.toolbarTitleTv.text = getString(R.string.pull_request_info)
        binding.toolbarLayout.toolbarBackIv.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.pullsItem = viewModel.getCurrentPullItem()
        currentItem = viewModel.getCurrentPullItem()
        binding.iv3.loadImage(currentItem.user.avatar_url)
        binding.tv9.text = "Created: ${outputFormat.format(inputFormat.parse(currentItem.created_at) as Date)}"
        binding.tv10.text = "Closed: ${outputFormat.format(inputFormat.parse(currentItem.closed_at) as Date)}"


        //binding.pullListRv.adapter = pullDataAdapter
        //setPullRvData()
    }

}