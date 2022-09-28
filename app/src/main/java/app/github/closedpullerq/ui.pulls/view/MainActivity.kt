package app.github.closedpullerq.ui.pulls.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import app.github.closedpullerq.databinding.ActivityMainBinding
import app.github.closedpullerq.R
import app.github.closedpullerq.ui.pulls.viewmodel.MainViewModel
import app.github.closedpullerq.utils.Status
import app.github.closedpullerq.utils.showToast

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            mainViewModel.getPullsInfo("closed")
        }
        mainViewModel.pullsInfo.observe(this, Observer {
            when(it.status){
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    it.data?.let { mainInfo ->
                        supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, PullsFragment()).commit()
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    showToast("Api Call Failed: ${it.message}")
                }
            }
        })

    }
}