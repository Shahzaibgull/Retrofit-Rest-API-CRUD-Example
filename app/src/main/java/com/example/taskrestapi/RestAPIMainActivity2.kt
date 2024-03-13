package com.example.taskrestapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.taskrestapi.databinding.ActivityRestApiMain2Binding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestAPIMainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityRestApiMain2Binding

    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val data = MutableLiveData<List<JsonApiResponse_DataClass>>()
    val delete= MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRestApiMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

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


        //var ID=binding.etID.text.toString().toInt()
        binding.deleteButton.setOnClickListener {
            deletePostRequest(jsonApi,101)

        }
        //deletePostRequest(jsonApi,4)
        delete.observe(this) {
            if (it != null) {
                Toast.makeText(this@RestAPIMainActivity2, it.toString(), Toast.LENGTH_SHORT).show()
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

        val UserID=binding.etUserID.text.toString().toInt()
        val ID=binding.etID.text.toString().toInt()
        val Title=binding.etTitle.text.toString()
        val Body=binding.etBody.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response=jsonApi.postDataToServer(JsonApiResponse_DataClass(UserID,ID,Title,Body))

            if(response.isSuccessful){
                response.body()?.let {
                    data.postValue(listOf(it))
                }

            }else{ }
        }
        binding.etUserID.text?.clear()
        binding.etID.text?.clear()
        binding.etTitle.text?.clear()
        binding.etBody.text?.clear()
    }

    fun putPostRequest(jsonApi: JsonApi_Interface){

        val UserID=binding.etUserID.text.toString().toInt()
        val ID=binding.etID.text.toString().toInt()
        val Title=binding.etTitle.text.toString()
        val Body=binding.etBody.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response=jsonApi.putPostRequest(ID, JsonApiResponse_DataClass(UserID,ID,null,Body))

            if(response.isSuccessful){
                response.body()?.let {
                    data.postValue(listOf(it))
                }

            }else{ }
        }
        binding.etUserID.text?.clear()
        binding.etID.text?.clear()
        binding.etTitle.text?.clear()
        binding.etBody.text?.clear()

    }

    fun patchtPostRequest(jsonApi: JsonApi_Interface){

        val UserID=binding.etUserID.text.toString().toInt()
        val ID=binding.etID.text.toString().toInt()
        val Title=binding.etTitle.text.toString()
        val Body=binding.etBody.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response=jsonApi.patchPostRequest(ID, JsonApiResponse_DataClass(UserID,ID,Title,Body))

            if(response.isSuccessful){
                response.body()?.let {
                    data.postValue(listOf(it))
                }

            }else{ }
        }
        binding.etUserID.text?.clear()
        binding.etID.text?.clear()
        binding.etTitle.text?.clear()
        binding.etBody.text?.clear()
    }

    fun deletePostRequest(jsonApi: JsonApi_Interface,id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            val response=jsonApi.deletePostRequest(id)
            if(response.isSuccessful){
                Log.d("TAG", "patchPostRequest: ${response.code()}.")
                delete.postValue(response.code())
            }else{ }
        }
    }
}