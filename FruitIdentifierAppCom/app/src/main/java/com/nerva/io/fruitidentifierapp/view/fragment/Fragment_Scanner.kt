package com.nerva.io.fruitidentifierapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nerva.io.fruitidentifierapp.databinding.ActivityFragmentScannerBinding
import com.nerva.io.fruitidentifierapp.view.activity.ScannerActivity
import kotlinx.android.synthetic.main.activity_fragment_scanner.*

@Suppress("UNREACHABLE_CODE")
class Fragment_Scanner : Fragment(){
    private lateinit var binding : ActivityFragmentScannerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityFragmentScannerBinding.inflate(layoutInflater, container, false)
        return binding.root
        val Scanner = ScannerActivity()
        Scanner.intent
        binding.take.setOnClickListener{
           val intent = Intent(scanner.context,ScannerActivity::class.java)
            scanner.context.startActivity(intent)
        }
    }
}