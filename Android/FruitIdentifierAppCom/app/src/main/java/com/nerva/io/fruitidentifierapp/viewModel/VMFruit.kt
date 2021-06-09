package com.nerva.io.fruitidentifierapp.viewModel

import androidx.lifecycle.ViewModel
import com.nerva.io.fruitidentifierapp.model.entity.EntityFruit
import com.nerva.io.fruitidentifierapp.model.utils.Dummy

class VMFruit : ViewModel(){
    fun getFruit() : List<EntityFruit> = Dummy.dummyFruit()
}