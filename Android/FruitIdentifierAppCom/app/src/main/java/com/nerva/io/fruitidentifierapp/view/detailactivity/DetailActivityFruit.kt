package com.nerva.io.fruitidentifierapp.view.detailactivity

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nerva.io.fruitidentifierapp.R
import com.nerva.io.fruitidentifierapp.databinding.ActivityDetailFruitBinding
import com.nerva.io.fruitidentifierapp.model.entity.EntityFruit
import com.nerva.io.fruitidentifierapp.viewModel.VMDetailFruit

class DetailActivityFruit : AppCompatActivity(){
    private lateinit var binding : ActivityDetailFruitBinding
    private val vModel : VMDetailFruit by viewModels()
    companion object{
        const val EXTRAFRUIT = "extrafruit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            binding.itemDescription.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        val extras = intent.extras
        if(extras != null){
            val name = extras.getString(EXTRAFRUIT)
            if(name != null){
                vModel.setSelectedFruit(name)
                detailFruit(vModel.getFruit())
            }
        }
    }

    private fun detailFruit(entityFruit : EntityFruit){
        val maturity = "Maturity Fruit"
        val description = "Description Fruit"

        binding.apply {
            itemName.text = entityFruit.name
            itemBenefit.text = entityFruit.benefit
            itemNutrition.text = entityFruit.nutrition
            itemSource.text = entityFruit.source
            tMaturity.text = maturity
            itemMaturity.text = entityFruit.maturity
            tDescription.text = description
            itemDescription.text = entityFruit.description

            Glide.with(this@DetailActivityFruit)
                .load(entityFruit.image)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(imageFruit)
        }
    }
}