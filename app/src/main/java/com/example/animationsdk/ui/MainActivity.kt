package com.example.animationsdk.ui

import android.app.ActivityManager
import android.content.pm.ConfigurationInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.animationsdk.R
import com.example.animationsdk.databinding.ActivityMainBinding
import com.example.animationsdk.databinding.NavHeaderMainBinding
import com.rewheeldev.glsdk.sdk.api.model.Colors
import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.api.model.Shape
import com.rewheeldev.glsdk.sdk.api.model.Shape.Companion.prepareCoordsForGrid
import com.rewheeldev.glsdk.sdk.api.shape.border.Border
import com.rewheeldev.glsdk.sdk.api.shape.line.LinkLineTypes
import com.rewheeldev.glsdk.sdk.internal.CameraView
import com.rewheeldev.glsdk.sdk.internal.CoordsPerVertex
import utils.Color


class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding
        get() {
            return _binding!!
        }
    var _navHeaderMainBinding: NavHeaderMainBinding? = null
    val navHeaderMainBinding: NavHeaderMainBinding
        get() {
            return _navHeaderMainBinding!!
        }

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    var triangleCoordsPrevData = floatArrayOf(
        0f, 0f, 0.0f, -25f, -25f, 0.0f, 25f, -25f, 0.0f
    )
    val offset = 0.7f
    var triangleCoordsPrevData2 = floatArrayOf(
        0f + offset, 0f + offset, -25f + offset, -20f + offset, 20f + offset, -20f + offset
    )

    //здесь мы создаем замыкающий начальный квадрат
    var triangleCoordsPrevData3 = floatArrayOf(
        -20f + offset, -20f + offset,
        -20f + offset, -10f + offset,
        -10f + offset, -10f + offset,
        -10f + offset, -20f + offset,
        -20f + offset, -20f + offset,
    )

    val triangleCoords = Coords(triangleCoordsPrevData, CoordsPerVertex.VERTEX_3D)
    val triangleCoords2 = Coords(triangleCoordsPrevData2, CoordsPerVertex.VERTEX_2D)

    //вершины которые будут отрисованы (в данном случае сетка из квадратов)
    val triangleCoords5 = Coords(triangleCoordsPrevData3, CoordsPerVertex.VERTEX_2D)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!supportES2()) {
            Toast.makeText(this, "OpenGL ES 2.0 is not supported", Toast.LENGTH_LONG).show()
            Log.e("TAG_1", "OpenGL ES 2.0 is not supported")
            finish()
            return
        }

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val header = binding.navigationView.getHeaderView(0)
        _navHeaderMainBinding = NavHeaderMainBinding.bind(header)
        setContentView(binding.root)

        navHeaderMainBinding.swCameraControllers.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.groupCameraControllers.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        val triangleCoords3 = Coords(CoordsPerVertex.VERTEX_2D)
        binding.mainLayout.initialize() {
            val triangle = Shape(triangleCoords, Colors(Color(1f, 0f, 0f, 1f)))
            val triangle2 = Shape(triangleCoords2)
            val gridBorder = Border(
                width = 0.0000000000000001f, color = Color.GREEN, type = LinkLineTypes.Strip
            )
            val grid = Shape(
                prepareCoordsForGrid(columns = 5, rows = 10, stepSize = 10f), border = gridBorder
            )
            val grid2Border = Border(
                width = 0.00000000001f, type = LinkLineTypes.Strip
            )
            val grid2 = Shape(
                coords = prepareCoordsForGrid(
                    x = -30f, y = -50f, z = 0.0001f, columns = 10, rows = 5, stepSize = 6f
                ), border = grid2Border
            )

            binding.mainLayout.getShapeController().add(grid)
            binding.mainLayout.getShapeController().add(grid2)
            binding.mainLayout.getShapeController().add(triangle)
        }

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, R.string.nav_open, R.string.nav_close
        )

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        updateData()
        //region camera position
        setOnSeekBarChangeListener()
        //endregion
    }

    private fun setOnSeekBarChangeListener() {
        binding.sbCpX.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera.cameraPosition.x = progress * MULTIPLIER
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.sbCpY.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera.cameraPosition.y = progress * MULTIPLIER
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.sbCpZ.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera.cameraPosition.z = progress * MULTIPLIER
                Log.d("TAG_1", "STEP: $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })


        binding.sbCpointX.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera.cameraDirectionPoint.x = progress * MULTIPLIER
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.sbCpointY.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera.cameraDirectionPoint.y = progress * MULTIPLIER
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.sbCpointZ.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera.cameraDirectionPoint.z = progress * MULTIPLIER
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })



        binding.sbVectorX.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera.upVector.x = progress * MULTIPLIER
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.sbVectorY.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera.upVector.y = progress * MULTIPLIER
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.sbVectorZ.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera.upVector.z = progress * MULTIPLIER
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    val camera = CameraView()
    private fun updateData() {
        binding.mainLayout.bindCamera(camera)
    }

    override fun onPause() {
        super.onPause()
        binding.mainLayout.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.mainLayout.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _navHeaderMainBinding = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun supportES2(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo: ConfigurationInfo = activityManager.deviceConfigurationInfo
        return configurationInfo.reqGlEsVersion >= 0x20000
    }

    companion object {
        const val MULTIPLIER = 0.01f
    }
}