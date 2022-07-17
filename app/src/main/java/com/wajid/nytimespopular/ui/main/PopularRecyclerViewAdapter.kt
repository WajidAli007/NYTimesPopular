package com.wajid.nytimespopular.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wajid.nytimespopular.R
import com.wajid.nytimespopular.databinding.ItemPopularArticlesListBinding
import com.wajid.nytimespopular.services.networkModels.popularArticles.NyPopularResultsItem
import com.wajid.nytimespopular.ui.webView.WebViewActivity

class PopularRecyclerViewAdapter(
    private val list: List<NyPopularResultsItem>
) :
    RecyclerView.Adapter<PopularRecyclerViewAdapter.TrendingRepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingRepoViewHolder {
        val binding = DataBindingUtil.inflate<ItemPopularArticlesListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_popular_articles_list,
            parent,
            false
        )
        return TrendingRepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendingRepoViewHolder, position: Int) {
        holder.populateData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class TrendingRepoViewHolder(private val binding: ItemPopularArticlesListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateData(dataItem: NyPopularResultsItem) {
            val context = binding.root.context
            if (!dataItem.media.isNullOrEmpty() && !dataItem.media[0]?.mediaMetadata.isNullOrEmpty()) {
                Glide.with(context)
                    .load(dataItem.media[0]?.mediaMetadata?.get(0)?.url)
                    .centerCrop()
                    .into(binding.ivArticleIcon)
            }

            binding.tvPopularArticleTitle.text = dataItem.title
            binding.tvArticleAuthors.text = dataItem.byline
            binding.tvDatePublished.text = dataItem.publishedDate

            binding.root.setOnClickListener {
                if (!dataItem.url.isNullOrEmpty()) {
                    binding.root.context.startActivity(
                        WebViewActivity.getIntent(binding.root.context, dataItem.url)
                    )
                } else {
                    Toast.makeText(
                        binding.root.context,
                        "No URL attached with this article",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}