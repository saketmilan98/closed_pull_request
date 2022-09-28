package app.github.closedpullerq.ui.pulls.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.github.closedpullerq.network.ApiInterface
import app.github.closedpullerq.network.RetrofitClient
import app.github.closedpullerq.ui.pulls.model.PullsDataClass
import app.github.closedpullerq.utils.Resource

class MainViewModel : ViewModel() {
    private val retrofit = RetrofitClient.getInstance()
    private val apiInterface = retrofit.create(ApiInterface::class.java)

    private val _pullsInfo: MutableLiveData<Resource<PullsDataClass>> = MutableLiveData()
    val pullsInfo: LiveData<Resource<PullsDataClass>> = _pullsInfo

    suspend fun getPullsInfo(pullState : String){
        _pullsInfo.postValue(Resource.loading(null))
        try{
            val response = apiInterface.getPullList(pullState)
            if (response.isSuccessful) {
                _pullsInfo.postValue(Resource.success(response.body()))
            } else {
                _pullsInfo.postValue(Resource.error(response.errorBody().toString(), null))
            }
        }
        catch (e:Exception){
            _pullsInfo.postValue(Resource.error(e.localizedMessage?:"unknown error", null))
        }
    }
}