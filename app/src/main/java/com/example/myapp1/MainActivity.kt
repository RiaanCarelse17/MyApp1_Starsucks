package com.example.myapp1

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp1.databinding.ActivityMainBinding
import com.example.myapp1.databinding.ActivityMainWithNavDrawerBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    var order = Order()
    var intent = IntentHelper()
    private lateinit var binding: ActivityMainWithNavDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val binding = ActivityMainWithNavDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners
        binding.ss1.setOnClickListener(this)
        binding.ss2.setOnClickListener(this)
        binding.ss3.setOnClickListener(this)
        binding.ss4.setOnClickListener(this)
        binding.ss5.setOnClickListener(this)
        binding.ss6.setOnClickListener(this)

        setSupportActionBar(binding.navToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        var toggleOnOff = ActionBarDrawerToggle(this,
           binding.drawerLayout, binding.navToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggleOnOff)
        toggleOnOff.syncState()

        binding.navView.bringToFront()
        binding.navView.setNavigationItemSelectedListener(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_photo -> intent.openIntent(this, "", CoffeeSnapsActivity::class.java)
    }
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ss_1 -> order.productName = "Soy Latte"
            R.id.ss_2 -> order.productName = "Chocco Frapp"
            R.id.ss_3 -> order.productName = "Bottled Americano"
            R.id.ss_4 -> order.productName = "Rainbow Frapp"
            R.id.ss_5 -> order.productName = "Caramel Frapp"
            R.id.ss_6 -> order.productName = "Black Forest Frapp"
        }
        Toast.makeText(this@MainActivity, "MMM " + order.productName, Toast.LENGTH_SHORT).show()
        intent.openIntent(applicationContext, order.productName, OrderDetailsActivity::class.java)
    }
}
