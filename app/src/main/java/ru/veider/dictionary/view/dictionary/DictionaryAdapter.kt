package ru.veider.dictionary.view.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.veider.dictionary.R
import ru.veider.dictionary.databinding.DictionaryListItemBinding
import ru.veider.dictionary.model.data.Dictionary

class DictionaryAdapter(private val list: List<Dictionary>) : RecyclerView.Adapter<DictionaryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dictionary_list_item, parent, false) as View
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int =
            list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item : Dictionary){
            if (layoutPosition != RecyclerView.NO_POSITION){
                val binder = DictionaryListItemBinding.bind(itemView)
                binder.word.text = item.text
                val string = StringBuilder()
                for (i in 0..item.meanings.size-1){
                    item.meanings[i].translation.let {
                        string.append(it.text)
                        if (i< item.meanings.size-1)
                            string.append(" / ")
                    }
                }
                binder.meaning.text = string
            }
        }
    }
}

