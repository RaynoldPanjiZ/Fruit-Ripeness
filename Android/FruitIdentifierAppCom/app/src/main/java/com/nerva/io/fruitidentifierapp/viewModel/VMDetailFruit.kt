package com.nerva.io.fruitidentifierapp.viewModel

import androidx.lifecycle.ViewModel
import com.nerva.io.fruitidentifierapp.model.entity.EntityFruit
import com.nerva.io.fruitidentifierapp.model.utils.Dummy

class VMDetailFruit : ViewModel(){
    private lateinit var namefruit : String

    fun setSelectedFruit(fruit : String){
        this.namefruit = fruit
    }

    fun getFruit() : EntityFruit{
        lateinit var entityFruit : EntityFruit
        val fruitEntity = Dummy.dummyFruit()
        for (fruit2 in fruitEntity){
            if(fruit2.name == namefruit){
                entityFruit = fruit2
            }
        }
        return entityFruit
    }
}