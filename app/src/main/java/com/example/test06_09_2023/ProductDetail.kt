package com.example.test06_09_2023

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.test06_09_2023.databinding.FragmentProductBinding
import com.example.test06_09_2023.databinding.FragmentProductDetailBinding


class ProductDetail : Fragment() {

    private lateinit var binding:FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
binding= FragmentProductDetailBinding.inflate(inflater,container,false)
        return binding.root

}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = arguments?.getSerializable("product") as? Product

        if (product != null) {
            Glide.with(requireContext())
                .load(product.thumbnail)
                .into(binding.imageThumbnail)
            binding.textTitle.text = "Title: ${product.title}"
            binding.textDescription.text = "Description: ${product.description}"
            binding.textPrice.text = "Price: ${product.price}"
            binding.textBrand.text = "Brand: ${product.brand}"
        }
    }
}

