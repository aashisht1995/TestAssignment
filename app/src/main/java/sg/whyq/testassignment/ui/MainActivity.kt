package sg.whyq.testassignment.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sg.whyq.testassignment.R
import sg.whyq.testassignment.database.UserDataRepository
import sg.whyq.testassignment.databinding.ActivityMainBinding
import sg.whyq.testassignment.ui.adapters.UserListAdapter
import sg.whyq.testassignment.ui.models.UserData
import sg.whyq.testassignment.ui.viewmodel.UsersListViewModel
import sg.whyq.testassignment.utills.CommonFunctions
import sg.whyq.testassignment.utills.ConnectionDetector
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UsersListViewModel
    private lateinit var userListAdapter: UserListAdapter

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity

    private lateinit var productInventoryRepository: UserDataRepository

    private var pageNumber = 1
    var loadMore = false

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mContext = this@MainActivity
        mActivity = this@MainActivity

        productInventoryRepository = UserDataRepository(application, mContext)

        viewModel = ViewModelProvider(this)[UsersListViewModel::class.java]

        prepareRecyclerView()
        setObserver()

        if (ConnectionDetector.isConnectingToInternet(mContext)) {
            getUsersList(pageNumber)
            addListener()
        } else {
            CommonFunctions.showSnackBar(mActivity, getString(R.string.check_internet_connection))

            val preSavedData = productInventoryRepository.getAllUserEntries()
            userListAdapter.setData(preSavedData)

            if (preSavedData.size > 0) {
                showUserSectionLayout()
            } else showErrorMessageLayout(getString(R.string.no_record_found))
        }
    }

    private fun addListener() {
        binding.recyclerViewUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView!!, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                } else {
                    if (loadMore) {
                        pageNumber += 1
                        showHideProgressBar(true)
                        viewModel.getPopularMovies(pageNumber, mActivity)
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView!!, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }
        })
    }

    private fun getUsersList(pageNumber: Int) {
        viewModel.getPopularMovies(pageNumber, mActivity)
    }

    private fun setObserver() {
        viewModel.getUserDataLiveData().observe(this) { dataArrayList ->
            userListAdapter.setData(dataArrayList)

            if (dataArrayList.size > 0) {
                showUserSectionLayout()
            }

            insertUserDataInEntries(dataArrayList)

        }

        viewModel.getErrorLiveData().observe(this) {
            showErrorMessageLayout(it!!)
        }

        viewModel.getFirstProgress().observe(this) {
            if (!it) binding.progressBar.visibility = View.GONE
            else binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.getLoadMoreVariable().observe(this) {
            loadMore = it
        }

        viewModel.getLoadMoreProgress().observe(this) {
            showHideProgressBar(it)
        }
    }

    private fun prepareRecyclerView() {

        userListAdapter = UserListAdapter(mContext)

        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(mContext)
        binding.recyclerViewUsers.adapter = userListAdapter
    }

    private fun showUserSectionLayout() {
        binding.constraintLayoutData.visibility = View.VISIBLE
        binding.constraintLayoutNoData.visibility = View.GONE

        binding.progressBar.visibility = View.GONE
    }

    private fun showErrorMessageLayout(message: String) {
        binding.textViewNoUserRecords.text = CommonFunctions.fromHtml(message)
        binding.constraintLayoutNoData.visibility = View.VISIBLE
        binding.constraintLayoutData.visibility = View.GONE

        binding.progressBar.visibility = View.GONE
    }

    private fun showHideProgressBar(visibility: Boolean) {
        if (visibility) binding.progressBarUsersChild.visibility = View.VISIBLE
        else binding.progressBarUsersChild.visibility = View.GONE
    }

    private fun insertUserDataInEntries(dataList: ArrayList<UserData>) {
        launch {

            try {
                for (items in dataList) {

                    if (productInventoryRepository.isUserExist(items.id)) {
                        productInventoryRepository.update(items)
                    } else
                        productInventoryRepository.insert(items)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}