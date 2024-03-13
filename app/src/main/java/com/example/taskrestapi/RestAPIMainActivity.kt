package com.example.taskrestapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.taskrestapi.databinding.ActivityRestApiMainBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestAPIMainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRestApiMainBinding

    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val data = MutableLiveData<List<JsonApiResponse_DataClass>>()
    val delete= MutableLiveData<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRestApiMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.input.setOnClickListener {
            startActivity(Intent(this@RestAPIMainActivity, RestAPIMainActivity2::class.java))
        }

        val jsonApi = retrofit.create(JsonApi_Interface::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)


        binding.getButton.setOnClickListener {
            getData(jsonApi)
        }
        binding.postButton.setOnClickListener {
            postRequest(jsonApi)
        }
        binding.putButton.setOnClickListener {
            putPostRequest(jsonApi)
        }
        binding.patchButton.setOnClickListener {
            patchtPostRequest(jsonApi)
        }

        //getData(jsonApi)
        //postRequest(jsonApi)
        //putPostRequest(jsonApi)
        //patchtPostRequest(jsonApi)

        data.observe(this) {
            val adapter = RecyclerView_Adapter(it)
            recyclerView.adapter = adapter
        }

        binding.deleteButton.setOnClickListener {
            deletePostRequest(jsonApi,101)
        }
        //deletePostRequest(jsonApi,4)
        delete.observe(this) {
            if (it != null) {
                Toast.makeText(this@RestAPIMainActivity, it.toString(), Toast.LENGTH_SHORT).show()
                //Log.d("TAG", "onCreate: $it")
            }
        }
    }

    fun getData(jsonApi: JsonApi_Interface) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = jsonApi.getPosts()
            if (response.isSuccessful) {
                data.postValue(response.body())
            }
        }
    }

    fun postRequest(jsonApi: JsonApi_Interface){
        CoroutineScope(Dispatchers.IO).launch {
            val response=jsonApi.postDataToServer(JsonApiResponse_DataClass(1,101,"This is Title","This is Body"))

            if(response.isSuccessful){
                response.body()?.let {
                    data.postValue(listOf(it))
                }

            }else{ }
        }
    }

    fun putPostRequest(jsonApi: JsonApi_Interface){
        CoroutineScope(Dispatchers.IO).launch {
            val response=jsonApi.putPostRequest(4, JsonApiResponse_DataClass(1,101,null,"Body"))

            if(response.isSuccessful){
                response.body()?.let {
                    data.postValue(listOf(it))
                }

            }else{ }
        }
    }

    fun patchtPostRequest(jsonApi: JsonApi_Interface){
        CoroutineScope(Dispatchers.IO).launch {
            val response=jsonApi.patchPostRequest(101, JsonApiResponse_DataClass(1,101,"ABC ","Body"))

            if(response.isSuccessful){
                response.body()?.let {
                    data.postValue(listOf(it))
                }

            }else{ }
        }
    }

    fun deletePostRequest(jsonApi: JsonApi_Interface,id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            val response=jsonApi.deletePostRequest(id)
            if(response.isSuccessful){
                Log.d("TAG", "patchPostRequest: ${response.code()}.")  //200 means request completed
                delete.postValue(response.code())
            }else{ }
        }
    }
}