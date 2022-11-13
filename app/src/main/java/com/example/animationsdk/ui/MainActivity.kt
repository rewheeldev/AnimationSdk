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
import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.api.shape.border.Border
import com.rewheeldev.glsdk.sdk.api.shape.grid.GridParams
import com.rewheeldev.glsdk.sdk.api.shape.line.LineParams
import com.rewheeldev.glsdk.sdk.api.shape.line.LinkLineTypes
import com.rewheeldev.glsdk.sdk.api.shape.point.PointParams
import com.rewheeldev.glsdk.sdk.api.shape.rectangle.RectangleParams
import com.rewheeldev.glsdk.sdk.api.shape.triangle.TriangleParams
import com.rewheeldev.glsdk.sdk.api.util.OpenGLConfigurationInfoManager
import com.rewheeldev.glsdk.sdk.internal.CameraView
import com.rewheeldev.glsdk.sdk.internal.CoordsPerVertex
import utils.Color


class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding
        get() {
            return _binding!!
        }
    val openGLConfigurationInfoManager = OpenGLConfigurationInfoManager()
    var _navHeaderMainBinding: NavHeaderMainBinding? = null
    val navHeaderMainBinding: NavHeaderMainBinding
        get() {
            return _navHeaderMainBinding!!
        }

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    var triangleCoordsPrevData = floatArrayOf(
        -25f, 25f, 10.0f,
        -0f, 0f, 30.0f,
        25f, 25f, 10.0f,
    )
    var triangleCoordsPrevData1 = floatArrayOf(


        25f, 25f, 10.0f,
        -0f, 0f, 30.0f,
        25f, -25f, 10.0f,

        )
    var triangleCoordsPrevData2 = floatArrayOf(

        25f, -25f, 10.0f,
        -0f, 0f, 30.0f,
        -25f, -25f, 10.0f,

        )
    var triangleCoordsPrevData3 = floatArrayOf(

        -25f, -25f, 10.0f,
        -0f, 0f, 30.0f,
        -25f, 25f, 10.0f,
    )
    val offset = 0.7f
//    var triangleCoordsPrevData2 = floatArrayOf(
//        0f + offset, 0f + offset, -25f + offset, -20f + offset, 20f + offset, -20f + offset
//    )

    //здесь мы создаем замыкающий начальный квадрат
//    var triangleCoordsPrevData3 = floatArrayOf(
//        -20f + offset, -20f + offset,
//        -20f + offset, -10f + offset,
//        -10f + offset, -10f + offset,
//        -10f + offset, -20f + offset,
//        -20f + offset, -20f + offset,
//    )

    val triangleCoords = Coords(triangleCoordsPrevData, CoordsPerVertex.VERTEX_3D)
    val triangleCoords1 = Coords(triangleCoordsPrevData1, CoordsPerVertex.VERTEX_3D)
    val triangleCoords2 = Coords(triangleCoordsPrevData2, CoordsPerVertex.VERTEX_3D)
    val triangleCoords3 = Coords(triangleCoordsPrevData3, CoordsPerVertex.VERTEX_3D)
//    val triangleCoords2 = Coords(triangleCoordsPrevData2, CoordsPerVertex.VERTEX_2D)

    //вершины которые будут отрисованы (в данном случае сетка из квадратов)
//    val triangleCoords5 = Coords(triangleCoordsPrevData3, CoordsPerVertex.VERTEX_2D)

    val rectangleCoordsVertices = floatArrayOf(
        0f, 0f,
        9f, 14f,
        0f, 14f,

        0f, 0f,
        9f, 0f,
        9f, 14f
    )

    val lineCoordsVertices = floatArrayOf(
        -25f, 10f,
        -35f, 15f,
        -35f, 15f,
        -45f, 25f
    )

    val rectangleCoords = Coords(rectangleCoordsVertices, CoordsPerVertex.VERTEX_2D)

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

        initScreen()

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, R.string.nav_open, R.string.nav_close
        )

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //region camera position
        setOnSeekBarChangeListener()
        //endregion
        openGLConfigurationInfoManager.initActivityManager(this)

        navHeaderMainBinding.tvOpenGlVersion.text =
            "OpenGL SE version: ${openGLConfigurationInfoManager.openGLVersion}"

        binding.sbCpX.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera.cameraPosition.x = progress * MULTIPLIER
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        navHeaderMainBinding.tvRedValue.text = resources.getString(R.string.red, 0.2)
        navHeaderMainBinding.sbRed.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = progress / 10.0f
                binding.mainLayout.backgroundColor.r = value
                navHeaderMainBinding.tvRedValue.text = resources.getString(R.string.red, value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        navHeaderMainBinding.tvGreenValue.text = resources.getString(R.string.green, 0.2)
        navHeaderMainBinding.sbGreen.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = progress / 10.0f
                binding.mainLayout.backgroundColor.g = value
                navHeaderMainBinding.tvGreenValue.text = resources.getString(R.string.green, value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        navHeaderMainBinding.tvBlueValue.text = resources.getString(R.string.blue, 0.2)
        navHeaderMainBinding.sbBlue.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = progress / 10.0f
                binding.mainLayout.backgroundColor.b = value
                navHeaderMainBinding.tvBlueValue.text = resources.getString(R.string.blue, value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        navHeaderMainBinding.tvAlphaValue.text = resources.getString(R.string.alpha, 0.2)
        navHeaderMainBinding.sbAlpha.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = progress / 10.0f
                binding.mainLayout.backgroundColor.a = value
                navHeaderMainBinding.tvAlphaValue.text = resources.getString(R.string.alpha, value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    private fun initScreen() {
        binding.mainLayout.initialize() {
            updateData()
            val shapeFactory = binding.mainLayout.getShapeFactory()

            binding.mainLayout.backgroundColor

            val triangleParams = TriangleParams(triangleCoords, Color.RED)
            val triangleParams1 = TriangleParams(triangleCoords1, Color.GREEN)
            val triangleParams2 = TriangleParams(triangleCoords2, Color.BLACK)
            val triangleParams3 = TriangleParams(triangleCoords3, Color.MAGENTA)

            val triangle = shapeFactory.createTriangle(triangleParams)
            val triangle1 = shapeFactory.createTriangle(triangleParams1)
            val triangle2 = shapeFactory.createTriangle(triangleParams2)
            val triangle3 = shapeFactory.createTriangle(triangleParams3)

            val gridBorder = Border(
                width = 0.0000000000000001f, color = Color.GREEN, type = LinkLineTypes.Strip
            )
            val gridParams = GridParams(
                columns = 5, rows = 10, stepSize = 6f, border = gridBorder
            )
            val grid = shapeFactory.createGrid(gridParams)

            val grid2Border = Border(width = 0.00000000001f, type = LinkLineTypes.Strip)
            val grid2Params = GridParams(
                x = -170f,
                y = -170f,
                z = 0.0001f,
                columns = 60,
                rows = 60,
                stepSize = 10f,
                border = grid2Border
            )
            val grid2 = shapeFactory.createGrid(grid2Params)

            val rectangleParams = RectangleParams(x = -30, y = 30, width = 30, height = -30)
            rectangleParams.color = Color(0f, 0.34f, 0.34f, 1f)
            val rectangle = shapeFactory.createRectangle(rectangleParams)

            val pointParams = PointParams(
                x = -30, y = 20, coordsPerVertex = CoordsPerVertex.VERTEX_2D,
                color = Color.GOLD
            )
            val point = shapeFactory.createPoint(pointParams)

            val lineParams = LineParams(
                coords = Coords(lineCoordsVertices, CoordsPerVertex.VERTEX_2D),
                color = Color.ROYAL,
                coordsPerVertex = CoordsPerVertex.VERTEX_3D,
            )
            val line = shapeFactory.createLine(lineParams)

            binding.mainLayout.getShapeController().add(grid2)
            binding.mainLayout.getShapeController().add(grid)
            binding.mainLayout.getShapeController().add(point)
            binding.mainLayout.getShapeController().add(line)

            binding.mainLayout.getShapeController().add(rectangle)
            binding.mainLayout.getShapeController().add(triangle)
            binding.mainLayout.getShapeController().add(triangle1)
            binding.mainLayout.getShapeController().add(triangle2)
            binding.mainLayout.getShapeController().add(triangle3)
        }
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