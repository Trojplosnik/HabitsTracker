package com.example.habitstracker




import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.habitstracker.databinding.ActivityMainBinding
import java.lang.IllegalStateException


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding is null")

    private var _navController: NavController? = null
    private val navController
        get() = _navController ?: throw IllegalStateException("NavController is null")




    private var _appBarConfiguration: AppBarConfiguration? = null
    private val appBarConfiguration
        get() = _appBarConfiguration ?: throw IllegalStateException("AppBarConfiguration is null")




    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentHolder)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.bringToFront()

        setNavController()
    }


    private fun setNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentHolder) as NavHostFragment
        _navController = navHostFragment.navController

        _appBarConfiguration = AppBarConfiguration(
            navController.graph,
            binding.navigationDrawerLayout
        )
        binding.navigationView.setupWithNavController(navController)
    }
}
