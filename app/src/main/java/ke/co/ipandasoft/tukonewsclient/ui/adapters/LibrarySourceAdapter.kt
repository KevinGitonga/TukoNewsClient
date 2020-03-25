package ke.co.ipandasoft.tukonewsclient.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.data.models.LibrarySource
import kotlinx.android.synthetic.main.item_library_source.view.*


class LibrarySourceAdapter(val items: MutableList<LibrarySource>,val context: Context):
    RecyclerView.Adapter<LibrarySourceAdapter.SourceHolder>() {

    private var onItemClickListener:OnItemClickListener?=null



    inner class SourceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val libraryAuthorTitle=itemView.tv_name_author
        val libraryUrl=itemView.tv_library_address
        val libraryLayout=itemView.libraryLinear
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceHolder {
        val inflater = LayoutInflater.from(context)
        val view=inflater.inflate(R.layout.item_library_source,parent,false)
        return SourceHolder(view)
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onBindViewHolder(holder: SourceHolder, position: Int) {
        val libraryItem= items[position]
        holder.libraryAuthorTitle.text=libraryItem.name
        holder.libraryUrl.text=libraryItem.address
        holder.libraryLayout.setOnClickListener { onItemClickListener!!.onItemClick(libraryItem,position) }

    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener=onItemClickListener
    }


}

