package com.example.toyotasample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toyotasample.ui.ProductListAdapter
import com.example.toyotasample.ui.ProductListViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ProductListViewModel
    lateinit var productList: RecyclerView
    lateinit var listError: TextView
    lateinit var loadingView: ProgressBar

    lateinit var productListAdapter: ProductListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listError = findViewById(R.id.listError)
        loadingView = findViewById(R.id.loadingView)
        productList = findViewById(R.id.productListRv)

        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]

        viewModel.refresh()
        val productList: RecyclerView = findViewById(R.id.productListRv)
        productList.apply {
            layoutManager = LinearLayoutManager(context)
            productListAdapter = ProductListAdapter(ProductListAdapter.OnClickListener { product ->
                val intent = Intent(applicationContext, ProductDetailActivity::class.java)
                val extras = Bundle()
                extras.putParcelable("key_product", product)
                intent.putExtras(extras)
                startActivity(intent)
            }, arrayListOf())
            adapter = productListAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.products.observe(this) { countries ->
            countries?.let {
                productList.visibility = View.VISIBLE
                productListAdapter.updateProducts(it)
            }
        }
        viewModel.productsLoadError.observe(this, Observer { isError ->
            listError.visibility = if (isError == "") View.GONE else View.VISIBLE
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    productList.visibility = View.GONE
                }
            }
        })
    }

}