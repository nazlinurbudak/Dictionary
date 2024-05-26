package com.nazlinurbudak.dictionary.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nazlinurbudak.dictionary.data.model.WordResponseItem
import com.nazlinurbudak.dictionary.databinding.WordRowItemBinding

class SearchAdapter : ListAdapter<WordResponseItem, SearchAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WordRowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: WordRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: WordResponseItem) = with(binding) {

            wordTextView.text = bindWordText(word.word)
            phoneticTextView.text = word.phonetic
            synonymsTextView.text = getSynonymsText(word)
            typeTextView.text = getTypesText(word)


        }

        private fun bindWordText(text: String?): String? {
            return text?.let {
                it.replaceFirstChar { char ->
                    char.uppercaseChar()
                }
            }
        }

        private fun getSynonymsText(word: WordResponseItem): String {
            val synonymsList = mutableListOf<String>()
            word.meanings.forEach { meaning ->
                meaning.synonyms.let {
                    synonymsList.addAll(it)
                }
            }
            return if (synonymsList.isNotEmpty()) {
                synonymsList.joinToString(", ")
            } else {
                "No synonyms available"
            }
        }

        private fun getTypesText(word: WordResponseItem): String {
            val typeList = mutableListOf<String>()
            word.meanings.forEach { types ->
                types.partOfSpeech.let {
                    typeList.addAll(listOf(it))
                }
            }
            return if (typeList.isNotEmpty()) {
                typeList.joinToString(", ")
            } else {
                "No type available"
            }
        }


    }


    class DiffCallback : DiffUtil.ItemCallback<WordResponseItem>() {
        override fun areItemsTheSame(oldItem: WordResponseItem, newItem: WordResponseItem) =
            oldItem.word == newItem.word

        override fun areContentsTheSame(oldItem: WordResponseItem, newItem: WordResponseItem) =
            oldItem == newItem

    }
}