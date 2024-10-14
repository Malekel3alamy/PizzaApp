package com.example.pizzaapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizzaapp.databinding.RvItemBinding
import com.example.pizzaapp.model.products.Products

class BaseCategoryAdapter:RecyclerView.Adapter<BaseCategoryAdapter.MyViewHolder>() {

    class MyViewHolder(val binding :RvItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(product:Products){
            Log.d("Loading",product.toString())
            Glide.with(itemView).load(product.product_image).into(binding.foodIv)

            val priceAfterDiscount = product.price.toFloat() - product.discount.toFloat()
            binding.newPriceTv.text = priceAfterDiscount.toString() +" EGP"
            binding.oldPriceTv.text = product.price
            if (product.favorite == 1){
                binding.iconBookmarkFilledIv.visibility = View.VISIBLE

            }else{
                binding.iconBookmarkFilledIv.visibility = View.INVISIBLE
            }

            binding.foodNameTv.text = product.name
            binding.foodRb.rating = product.rate.toFloat()


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun getItemCount(): Int {
  return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val  product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onProductClick?.invoke(product)
        }
    }


    private val differCallback = object : DiffUtil.ItemCallback<Products>(){
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean{
            return oldItem == newItem
        }


    }

    var onProductClick : ((Products) -> Unit)? = null
    val differ = AsyncListDiffer(this,differCallback)
}