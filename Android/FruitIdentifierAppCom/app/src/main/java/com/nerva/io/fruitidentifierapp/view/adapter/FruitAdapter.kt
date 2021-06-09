package com.nerva.io.fruitidentifierapp.view.adapter

import android.content.Intent
import android.graphics.text.LineBreaker
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nerva.io.fruitidentifierapp.R
import com.nerva.io.fruitidentifierapp.databinding.ActivityItemRowsFruitBinding
import com.nerva.io.fruitidentifierapp.model.entity.EntityFruit
import com.nerva.io.fruitidentifierapp.view.detailactivity.DetailActivityFruit

class FruitAdapter : RecyclerView.Adapter<FruitAdapter.FruitViewHolder>(){
    private var listFruit = ArrayList<EntityFruit>()
    private lateinit var itemRowsFruitBinding: ActivityItemRowsFruitBinding

    class FruitViewHolder (private val binding : ActivityItemRowsFruitBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(entityFruit : EntityFruit){
            with(binding){
                if(VERSION.SDK_INT >= VERSION_CODES.Q){
                    itemMaturity.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
                }
                itemName.text = entityFruit.name
                itemSource.text = entityFruit.source
                itemMaturity.text = entityFruit.maturity
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, DetailActivityFruit::class.java)
                    intent.putExtra(DetailActivityFruit.EXTRAFRUIT, entityFruit.name)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(entityFruit.image)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(imageFruit)
            }
        }
    }
    fun setFruit(fruit : List<EntityFruit>?){
        if(fruit == null) return
        this.listFruit.trimToSize()
        this.listFruit.addAll(fruit)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FruitViewHolder {
        itemRowsFruitBinding = ActivityItemRowsFruitBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup,false)
        return FruitViewHolder((itemRowsFruitBinding))
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        val fruit = listFruit[position]
        holder.bind(fruit)
    }

    override fun getItemCount(): Int = listFruit.size
}