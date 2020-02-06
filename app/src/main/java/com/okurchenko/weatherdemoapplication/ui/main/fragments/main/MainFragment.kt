package com.okurchenko.weatherdemoapplication.ui.main.fragments.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.okurchenko.weatherdemoapplication.R
import com.okurchenko.weatherdemoapplication.databinding.MainFragmentBinding
import com.okurchenko.weatherdemoapplication.ui.main.fragments.BaseFragment
import com.okurchenko.weatherdemoapplication.ui.main.fragments.main.adapter.AirForecastAdapter
import com.okurchenko.weatherdemoapplication.ui.main.fragments.main.adapter.WeatherForecastAdapter
import com.okurchenko.weatherdemoapplication.utils.ConnectionListener
import com.okurchenko.weatherdemoapplication.utils.SnackBarBuilder
import kotlin.math.abs

private const val REQUEST_CODE_FOREGROUND = 1567

class MainFragment : BaseFragment(), AppBarLayout.OnOffsetChangedListener, ConnectionListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel> { factory }
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindContentView(inflater, R.layout.main_fragment, container)
        binding.collapsingToolBar.addOnOffsetChangedListener(this)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.swipeToRefreshLayout.setOnRefreshListener {
            viewModel.forceUpdateAll()
            binding.swipeToRefreshLayout.isRefreshing = false
        }
        initAdapters()
        subscribeToViewModelUpdate()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
    }

    override fun onResume() {
        super.onResume()
        networkUtilsListener.registerNetworkListener(this)
    }

    override fun onPause() {
        super.onPause()
        networkUtilsListener.unRegisterNetworkListener(this)
    }

    override fun onNetworkStateChanged(connectionAvailable: Boolean) {
        if (!connectionAvailable) {
            showOfflineWorkSnack()
        } else {
            safeDismissSnackBar()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_FOREGROUND -> handlePermission(grantResults)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val isCollapsed = abs(offset).toFloat() == appBarLayout.totalScrollRange.toFloat()
        binding.toolBar.visibility = if (isCollapsed) View.VISIBLE else View.INVISIBLE
    }

    private fun initAdapters() {
        binding.mainContent.weatherForecastLayout.weatherForecastData.adapter =
            WeatherForecastAdapter()
        binding.mainContent.airForecastLayout.airForecastData.adapter =
            AirForecastAdapter()
    }

    private fun subscribeToViewModelUpdate() {
        viewModel.getDeviceLocation().observe(viewLifecycleOwner, Observer {
            viewModel.updateCurrentWeatherData.invoke()
            viewModel.updateWeatherForecast.invoke()
        })
        viewModel.getAirQualityData().observe(viewLifecycleOwner, Observer {
            viewModel.updateAirAction.invoke()
            viewModel.updateAirQualityForecast.invoke()
        })
        viewModel.getCurrentWeatherData().observe(viewLifecycleOwner, Observer {
            val placeholderImage = binding.mainHeaderContent.placeholderImage
            viewModel.fetchHeaderContent(placeholderImage.width, placeholderImage.height)
        })
    }

    private fun requestPermission() {
        if (hasLocationPermission()) {
            viewModel.fetchLocation()
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_FOREGROUND)
        }
    }

    private fun hasLocationPermission(): Boolean =
        context?.run {
            ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } ?: false

    private fun handlePermission(grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            viewModel.fetchLocation()
        } else {
            showPermissionDeniedSnack()
        }
    }

    private fun showPermissionDeniedSnack() {
        val snackBarBuilder = SnackBarBuilder.Builder()
            .setText("Open Settings")
            .setDuration(Snackbar.LENGTH_LONG)
            .setActionText("Settings")
            .setActionListener(View.OnClickListener { print("Shit happens") })
        showSnackBar(snackBarBuilder.build())
    }

    private fun showOfflineWorkSnack() {
        val snackBarBuilder = SnackBarBuilder.Builder()
            .setText(getString(R.string.offline_label))
            .setDuration(Snackbar.LENGTH_INDEFINITE)
            .setIcon(android.R.drawable.ic_dialog_alert)
        showSnackBar(snackBarBuilder.build())
    }
}
