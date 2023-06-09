package com.example.assignmentapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.Fragment

import com.example.assignmentapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.add -> replaceFragment(FirstFragment()) ;
                R.id.show -> replaceFragment((SecondFragment()))


                else -> false
            }

        }



    }


    private  fun replaceFragment(fragment: Fragment): Boolean {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame,fragment)
        fragmentTransaction.commit()

        return   true
    }
}