import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test06_09_2023.Product
import com.example.test06_09_2023.R

class ProductAdapter(val productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface ProductDetailOnclickListenrs{
        fun OnDetailClick(productAdapter: ProductAdapter,view: View,product: Product,adapterPosition: Int)
    }
    var productDetailOnclickListenrs:ProductDetailOnclickListenrs?=null

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageThumbnail: ImageView = itemView.findViewById(R.id.imageThumbnail)
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        private val textPrice: TextView = itemView.findViewById(R.id.textPrice)
        private val textBrand: TextView = itemView.findViewById(R.id.textBrand)

        init {
            itemView.setOnClickListener {
                productDetailOnclickListenrs?.OnDetailClick(this@ProductAdapter, itemView,productList[adapterPosition],adapterPosition)

            }
        }

        fun bind(product: Product) {
            Glide.with(itemView.context)
                .load(product.thumbnail)
                .into(imageThumbnail)
            textTitle.text = "Title: ${product.title}"
            textDescription.text = "Description: ${product.description}"
            textPrice.text = "Price: ${product.price}"
            textBrand.text = "Brand: ${product.brand}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.productview, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount() = productList.size

    fun setUserList(products: List<Product>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }
}
