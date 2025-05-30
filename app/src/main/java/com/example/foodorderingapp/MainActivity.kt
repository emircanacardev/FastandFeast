package com.example.foodorderingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodorderingapp.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View Binding'i kullanarak layout'u şişiriyoruz ve kök görünümünü ayarlıyoruz.
        // Bu, setContentView(R.layout.activity_main) çağrısını gereksiz kılar.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kenardan kenara görünüm için sistem çubuklarını ayarlama
        // findViewById(R.id.main) yerine binding.root kullanıldı.
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // NavHostFragment'ı bulma ve NavController'ı alma
        // nav_host yerine nav_host_fragment ID'si kullanıldı (activity_main.xml'deki ID ile eşleşmeli)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Eğer bir BottomNavigationView veya NavigationView kullanıyorsanız,
        // bunları navController ile bağlayabilirsiniz.
        // setupWithNavController(binding.bottomNavigationView, navController)
    }
}
