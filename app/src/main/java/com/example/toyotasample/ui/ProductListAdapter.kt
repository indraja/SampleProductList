package com.example.toyotasample.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toyotasample.R
import com.example.toyotasample.pojos.Product


class ProductListAdapter(private val onClickListener: OnClickListener, var products: ArrayList<Product>): RecyclerView.Adapter<ProductListAdapter.ProductsVH>() {
    lateinit var mContext: Context
    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ProductsVH {
        mContext = parent.context

       return ProductsVH(LayoutInflater.from(parent.context).inflate(R.layout.product_list_rv_item, parent, false))

    }
    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductsVH, position: Int) {
        holder.bind(mContext, products[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(products[position])
        }
    }
    class ProductsVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val prodImage : ImageView = itemView.findViewById(R.id.prodImage)
        private val prodTitle: TextView = itemView.findViewById(R.id.prodTitle)
        private val prodDesc: TextView = itemView.findViewById(R.id.prodDesc)

        fun bind(context: Context, product: Product) {
            prodTitle.text = product.title
            prodDesc.text = product.description
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)

            Glide.with(context)
                .load(product.thumbnail).apply(options).into(prodImage)
        }
    }

    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }
}