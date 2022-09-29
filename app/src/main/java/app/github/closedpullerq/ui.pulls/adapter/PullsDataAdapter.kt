package app.github.closedpullerq.ui.pulls.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.github.closedpullerq.R
import app.github.closedpullerq.databinding.PullsItemRvLayoutBinding
import app.github.closedpullerq.ui.pulls.model.PullsDataClassItem
import app.github.closedpullerq.utils.inputFormat
import app.github.closedpullerq.utils.outputFormat
import java.text.SimpleDateFormat
import java.util.*

class PullsDataAdapter(val context : Context, val onItemClicked: (PullsDataClassItem, Int) -> Unit) : RecyclerView.Adapter<PullsDataAdapter.CartViewHolder>() {

    private var pullDataa: ArrayList<PullsDataClassItem>? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartViewHolder {
        val cartRvLayoutBinding: PullsItemRvLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.pulls_item_rv_layout, viewGroup, false
        )
        return CartViewHolder(cartRvLayoutBinding)
    }

    override fun onBindViewHolder(cartViewHolder: CartViewHolder, i: Int) {
        val currentItem: PullsDataClassItem = pullDataa!![i]
        cartViewHolder.pullsListItemBinding.pullsItem = currentItem
        val parsedDate: Date = inputFormat.parse(currentItem.closed_at) as Date
        cartViewHolder.pullsListItemBinding.tv3.text = outputFormat.format(parsedDate)

        cartViewHolder.itemView.setOnClickListener {
            onItemClicked(currentItem, i)
        }
    }

    override fun getItemCount(): Int {
        return if (pullDataa != null) {
            pullDataa!!.size
        } else {
            0
        }
    }

    fun setPullItemList(sizes: ArrayList<PullsDataClassItem>?) {
        this.pullDataa = sizes
        notifyDataSetChanged()
    }

    inner class CartViewHolder(cartItemRvLayoutBinding: PullsItemRvLayoutBinding) :
        RecyclerView.ViewHolder(cartItemRvLayoutBinding.root) {
        val pullsListItemBinding: PullsItemRvLayoutBinding

        init {
            pullsListItemBinding = cartItemRvLayoutBinding
        }
    }
}