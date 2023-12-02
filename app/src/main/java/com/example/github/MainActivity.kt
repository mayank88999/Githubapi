package com.example.github

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    lateinit var IssueList : List<IssueItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = IssueAdapter()
        recyclerView.adapter = adapter // Inside your activity or wherever you're making the API call

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GitHubService::class.java)

        val call = service.getClosedIssues()

        call.enqueue(object : Callback<List<IssueItem>> {
            override fun onResponse(call: Call<List<IssueItem>>, response: Response<List<IssueItem>>) {
                if (response.isSuccessful) {
                    val issues: List<IssueItem>? = response.body()

                    issues?.let { issueList ->
                        IssueList = issues
                        adapter.addAllIssue(issueList)
                    }
                } else {
                    Log.e("API", "Failed to fetch issues: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<IssueItem>>, t: Throwable) {
                Log.e("NETWORK_ERROR", "Error fetching issues: ${t.message}", t)
            }
        })
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.addAllIssue(filter(newText,IssueList))
                return false
            }

        })

    }
    private fun filter(text: String,IssueList:List<IssueItem>):List<IssueItem> {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<IssueItem> = ArrayList()

        // running a for loop to compare elements.
        for (item in IssueList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            return filteredlist
        }
        return IssueList
    }

}
