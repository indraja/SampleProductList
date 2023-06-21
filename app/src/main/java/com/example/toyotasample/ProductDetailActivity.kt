package com.example.toyotasample

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toyotasample.pojos.Product

class ProductDetailActivity : AppCompatActivity() {
    //   lateinit var viewModel: ProductListViewModel
    lateinit var productImg: ImageView
    lateinit var productTitle: TextView
    lateinit var productDesc: TextView
    lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)
        productImg = findViewById(R.id.prodImage)
        productTitle = findViewById(R.id.prodTitle)
        productDesc = findViewById(R.id.prodDesc)

        val productBundleData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("key_product", Product::class.java)
        } else {
            intent.getParcelableExtra<Product>("key_product")
        }

        val options: RequestOptions = RequestOptions()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)

        Glide.with(this)
            .load(productBundleData?.thumbnail).apply(options).into(productImg)

        productTitle.text = productBundleData?.title
        productDesc.text = productBundleData?.description
    }


}