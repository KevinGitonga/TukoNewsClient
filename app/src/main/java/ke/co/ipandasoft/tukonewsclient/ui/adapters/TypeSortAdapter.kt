package ke.co.ipandasoft.tukonewsclient.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.os.Vibrator
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.utils.ItemTouchHelperAdapter
import kotlinx.android.synthetic.main.item_sort_category.view.*
import timber.log.Timber
import java.util.*


class TypeSortAdapter(private val types: ArrayList<NewsCategory>,private val activity:Activity) : RecyclerView.Adapter<TypeSortAdapter.TypeSortViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeSortViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sort_category, parent, false)
        return TypeSortViewHolder(view)
    }

    override fun getItemCount() = types.size

    override fun onBindViewHolder(holder: TypeSortViewHolder, position: Int) {
        holder.bind(position)
    }


    fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(types, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class TypeSortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @Suppress("DEPRECATION")
        @SuppressLint("MissingPermission")
        fun bind(position: Int) {

            itemView.tv_type.text = types[position].name
            itemView.setOnClickListener {
                Toast.makeText(activity,"DRAG TO RE-ORGANISE",Toast.LENGTH_SHORT)
            }
            itemView.setOnLongClickListener {
                val service = activity.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                service.vibrate(100)
                false
            }
        }
    }
}