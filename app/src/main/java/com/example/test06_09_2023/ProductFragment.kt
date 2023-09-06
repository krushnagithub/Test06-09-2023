package com.example.test06_09_2023

import ProductAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test06_09_2023.databinding.FragmentProductBinding
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private var productList = ArrayList<Product>()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        initView()
        setUpListenrs()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchUserList()
    }
    private fun setUpListenrs() {
        productAdapter.productDetailOnclickListenrs =
            object : ProductAdapter.ProductDetailOnclickListenrs {
                override fun OnDetailClick(
                    productAdapter: ProductAdapter,
                    view: View,
                    product: Product,
                    adapterPosition: Int
                ) {
                    val productDetailFragment = ProductDetail()

                    val bundle = Bundle()
                    bundle.putSerializable("product", product)
                    productDetailFragment.arguments = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, productDetailFragment)
                        .addToBackStack(null)
                        .commit()
                    mt("${product.title} clicked")

                }
            }
    }

    private fun initView() {
        binding.RecyclerViewProduct.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        productAdapter = ProductAdapter(productList)
        binding.RecyclerViewProduct.adapter = productAdapter
    }

    private fun fetchUserList() {
        CoroutineScope(Dispatchers.IO).launch {
            val users = getDogBreeds()
            withContext(Dispatchers.Main) {
                users?.let {
                    productList.clear()
                    productList.addAll(users)
                    productAdapter.notifyDataSetChanged()
                    Log.d("ProductFragment", "Product list size: ${productList.size}")
                }
            }
        }
    }

    private fun getDogBreeds(): ArrayList<Product>? {
        val url = URL("https://dummyjson.com/products")
        val httpUrlCon = url.openConnection() as HttpURLConnection
        httpUrlCon.connect()

        if (httpUrlCon.responseCode == 200) {
            val bufferedReader = BufferedReader(InputStreamReader(httpUrlCon.inputStream))
            val response = bufferedReader.readText()
            bufferedReader.close()
            httpUrlCon.disconnect()
            Log.e("res", response)

            val jObj = JSONObject(response)

            if (jObj.has("products")) {
                val jProducts = jObj.getJSONArray("products")

                val products = ArrayList<Product>()

                for (i in 0 until jProducts.length()) {
                    val jProduct = jProducts.getJSONObject(i)

                    val imagesArray = jProduct.getJSONArray("images")
                    val images = ArrayList<String>()

                    for (j in 0 until imagesArray.length()) {
                        images.add(imagesArray.getString(j))
                    }

                    products.add(
                        Product(
                            jProduct.getString("title"),
                            jProduct.getString("description"),
                            jProduct.getInt("price"),
                            jProduct.getString("brand"),
                            jProduct.getString("thumbnail"),
                            images
                        )
                    )
                }

                return products
            }
        }

        return null
    }
    private fun mt(text:String){
        Toast.makeText(activity,text,Toast.LENGTH_SHORT).show()
    }
}
