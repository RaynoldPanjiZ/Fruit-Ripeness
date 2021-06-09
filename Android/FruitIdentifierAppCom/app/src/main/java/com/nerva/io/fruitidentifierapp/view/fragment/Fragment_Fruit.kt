package com.nerva.io.fruitidentifierapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nerva.io.fruitidentifierapp.databinding.ActivityFragmentFruitBinding
import com.nerva.io.fruitidentifierapp.view.adapter.FruitAdapter
import com.nerva.io.fruitidentifierapp.viewModel.VMFruit

class Fragment_Fruit : Fragment(){
    private lateinit var frgfruitbinding : ActivityFragmentFruitBinding
    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?, savedInstanceState: Bundle?): View? {
        frgfruitbinding = ActivityFragmentFruitBinding.inflate(layoutInflater, viewGroup, false)
        return frgfruitbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null){
            val vModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[VMFruit::class.java]
            val fruit = vModel.getFruit()
            val adapterFruit = FruitAdapter()
            adapterFruit.setFruit(fruit)

            showLoading(true)
            with(frgfruitbinding.recyclerFruit){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = adapterFruit
                showLoading(false)
            }
        }
    }

    private fun showLoading(state : Boolean){
        if(state){
            frgfruitbinding.progresbarFruit.visibility = View.VISIBLE
        }else{
            frgfruitbinding.progresbarFruit.visibility = View.GONE
        }
    }
}