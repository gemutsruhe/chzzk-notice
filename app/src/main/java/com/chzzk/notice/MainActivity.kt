package com.chzzk.notice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.chzzk.notice.databinding.ActivityMainBinding
import com.chzzk.notice.databinding.CellCategoryBinding
import com.chzzk.notice.ui.theme.ChzzkNoticeTheme
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private var workManager: WorkManager = WorkManager.getInstance(application)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermission()
        var categoryListLiveData: LiveData<List<Category>>

        //OneTimeWorkRequest.Builder()
        //val workRequest = OneTimeWorkRequestBuilder<ObserveCategoryWorker>()
        //   .build()

        //workManager.enqueue(workRequest)

        /*workManager.enqueue(workRequest).state.observe(this) { workInfo ->
            if(workInfo.)

        }*/
        val workRequest = OneTimeWorkRequest.Builder(ObserveCategoryWorker::class.java).build()
        workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer { workInfo ->
            if(workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                categoryListLiveData = workInfo.outputData.keyValueMap["categoryList"] as LiveData<List<Category>>
                Thread {
                    val categoryList = categoryListLiveData.value

                    if (categoryList != null) {
                        for(category in categoryList) {
                            var categoryView = CellCategoryBinding.inflate(layoutInflater)
                            categoryView.categoryNameTextView.text = category.name
                            categoryView.categoryUrlTextView.text = category.url
                            binding.categoryListLayout.addView(categoryView.root)
                        }
                    }
                }.start()
            }
        })
    }

    private fun requestPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {

            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

            }

        }
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedMessage("")
            .setPermissions(android.Manifest.permission.INTERNET)
            .check()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChzzkNoticeTheme {
        Greeting("Android")
    }
}