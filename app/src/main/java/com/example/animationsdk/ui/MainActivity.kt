package com.example.animationsdk.ui

import android.app.ActivityManager
import android.content.pm.ConfigurationInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.animationsdk.R
import com.example.animationsdk.databinding.ActivityMainBinding
import com.example.animationsdk.databinding.NavHeaderMainBinding
import com.rewheeldev.glsdk.sdk.api.model.Coord
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
import kotlin.math.cos
import kotlin.math.sin


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
        setCameraOnSeekBarChangeListeners()
        //endregion
        openGLConfigurationInfoManager.initActivityManager(this)

        navHeaderMainBinding.tvOpenGlVersion.text =
            "OpenGL SE version: ${openGLConfigurationInfoManager.openGLVersion}"

        bgRed()
        bgGreen()
        bgBlue()
        bgAlpha()

        addControllers()
    }

    val cameraSpeed = 5f

    fun addControllers() {
        zoomIn()
        zoomOut()

        binding.btnDown.setOnClickListener {
            camera.cameraPosition.x -= cameraSpeed * camera.cameraDirectionPoint.x
            camera.cameraPosition.y -= cameraSpeed * camera.cameraDirectionPoint.y
            camera.cameraPosition.z -= cameraSpeed * camera.cameraDirectionPoint.z
            Log.d("TAG_1", "DOWN | cameraPosition: ${camera.cameraPosition}")
        }
        binding.btnUp.setOnClickListener {
            camera.cameraPosition.x += cameraSpeed * camera.cameraDirectionPoint.x
            camera.cameraPosition.y += cameraSpeed * camera.cameraDirectionPoint.y
            camera.cameraPosition.z += cameraSpeed * camera.cameraDirectionPoint.z
            Log.d("TAG_1", "UP | cameraPosition: ${camera.cameraPosition}")
        }
        binding.btnLeft.setOnClickListener {
            //https://registry.khronos.org/OpenGL-Refpages/gl4/html/cross.xhtml
            val resultCross = cross(
                floatArrayOf(
                    camera.cameraDirectionPoint.x,
                    camera.cameraDirectionPoint.y,
                    camera.cameraDirectionPoint.z
                ),
                floatArrayOf(
                    camera.upVector.x,
                    camera.upVector.y,
                    camera.upVector.z
                )
            )
            camera.cameraPosition.x -= cameraSpeed * resultCross[0]
            camera.cameraPosition.y -= cameraSpeed * resultCross[1]
            camera.cameraPosition.z -= cameraSpeed * resultCross[2]
            Log.d("TAG_1", "LEFT | cameraPosition: ${camera.cameraPosition}")
        }
        binding.btnRight.setOnClickListener {
            //https://registry.khronos.org/OpenGL-Refpages/gl4/html/cross.xhtml
            val resultCross = cross(
                floatArrayOf(
                    camera.cameraDirectionPoint.x,
                    camera.cameraDirectionPoint.y,
                    camera.cameraDirectionPoint.z
                ),
                floatArrayOf(
                    camera.upVector.x,
                    camera.upVector.y,
                    camera.upVector.z
                )
            )
            camera.cameraPosition.x += cameraSpeed * resultCross[0]
            camera.cameraPosition.y += cameraSpeed * resultCross[1]
            camera.cameraPosition.z += cameraSpeed * resultCross[2]
            Log.d("TAG_1", "RIGHT | cameraPosition: ${camera.cameraPosition}")
        }

    }

    fun cross(x: FloatArray, y: FloatArray): FloatArray {
        val resultX = x[1] * y[2] - y[1] * x[2]
        val resultY = x[2] * y[0] - y[2] * x[0]
        val resultZ = x[0] * y[1] - y[0] * x[1]
        return floatArrayOf(resultX, resultY, resultZ)
    }


    fun zoomIn() {
        binding.btnZoomIn.setOnClickListener {
            //do nothing
        }

    }

    fun zoomOut() {
        binding.btnZoomOut.setOnClickListener {
            //do nothing
        }
    }

    private fun bgAlpha() {

        bgColorUI(
            tvValue = navHeaderMainBinding.tvAlphaValue,
            resId = R.string.alpha,
            startValue = binding.mainLayout.backgroundColor.a,
            seekBar = navHeaderMainBinding.sbAlpha,
            newValue = { value ->
                binding.mainLayout.backgroundColor.a = value
            }
        )
    }


    private fun bgBlue() {
        bgColorUI(
            tvValue = navHeaderMainBinding.tvBlueValue,
            resId = R.string.blue,
            startValue = binding.mainLayout.backgroundColor.b,
            seekBar = navHeaderMainBinding.sbBlue,
            newValue = { value ->
                binding.mainLayout.backgroundColor.b = value
            }
        )
    }

    private fun bgGreen() {
        bgColorUI(
            tvValue = navHeaderMainBinding.tvGreenValue,
            resId = R.string.green,
            startValue = binding.mainLayout.backgroundColor.g,
            seekBar = navHeaderMainBinding.sbGreen,
            newValue = { value ->
                binding.mainLayout.backgroundColor.g = value
            }
        )
    }

    private fun bgRed() {
        bgColorUI(
            tvValue = navHeaderMainBinding.tvRedValue,
            resId = R.string.red,
            startValue = binding.mainLayout.backgroundColor.r,
            seekBar = navHeaderMainBinding.sbRed,
            newValue = { value ->
                binding.mainLayout.backgroundColor.r = value
            }
        )
    }

    private fun cameraUI(
        stringResId: Int,
        startCameraValue: Float,
        valueTV: TextView,
        seekBar: SeekBar,
        setValue: (Float) -> Unit
    ) {
        val tmp = (startCameraValue * 10).toInt()
        Log.d("TAG_1", "tmp: $tmp")
        seekBar.progress = (startCameraValue * 10).toInt()
        valueTV.text = resources.getString(stringResId, startCameraValue)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = progress * MULTIPLIER
                valueTV.text = resources.getString(stringResId, value)
                setValue(value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //do nothing
            }
        })
    }

    private fun bgColorUI(
        tvValue: TextView,
        resId: Int,
        startValue: Float,
        seekBar: SeekBar,
        newValue: (Float) -> Unit
    ) {
        tvValue.text = resources.getString(resId, startValue)
        seekBar.progress = (startValue * 10).toInt()
        seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = progress / 10.0f
                newValue(value)
                tvValue.text = resources.getString(resId, value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }


    private fun initScreen() {
        binding.mainLayout.initialize() {
            binding.mainLayout.bindCamera(camera)
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
                z = 0.0003f,
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

//            rotateCamera()
        }
    }

    val radius = 1.0

    fun rotateCamera() {
        Thread {
            repeat(10_000) {
                val camX = sin(System.currentTimeMillis() * radius) * 300
                val camZ = cos(System.currentTimeMillis() * radius) * 300
//                val camX = System.currentTimeMillis() * radius % 1000
//                val camZ = System.currentTimeMillis() * radius % 1000
                runOnUiThread {
                    camera.cameraPosition.x = camX.toFloat()
                    camera.cameraPosition.z = camZ.toFloat()
                }
                Log.d("TAG_1", "cameraPosition: ${camera.cameraPosition}")
                Thread.sleep(600)
            }

        }.start()

    }

    private fun setCameraOnSeekBarChangeListeners() {

        //region cameraPosition
        cameraUI(
            stringResId = R.string.x,
            startCameraValue = camera.cameraPosition.x,
            valueTV = binding.tvCpX,
            seekBar = binding.sbCpX,

            ) { value ->
            camera.cameraPosition.x = value
        }

        cameraUI(
            stringResId = R.string.y,
            startCameraValue = camera.cameraPosition.y,
            valueTV = binding.tvCpY,
            seekBar = binding.sbCpY,

            ) { value ->
            camera.cameraPosition.y = value
        }

        cameraUI(
            stringResId = R.string.z,
            startCameraValue = camera.cameraPosition.z,
            valueTV = binding.tvCpZ,
            seekBar = binding.sbCpZ,

            ) { value ->
            camera.cameraPosition.z = value
        }
        //endregion
        //region cameraDirectionPoint

        cameraUI(
            stringResId = R.string.x,
            startCameraValue = camera.cameraDirectionPoint.x,
            valueTV = binding.tvCpointX,
            seekBar = binding.sbCpointX,

            ) { value ->
            camera.cameraDirectionPoint.x = value
        }

        cameraUI(
            stringResId = R.string.y,
            startCameraValue = camera.cameraDirectionPoint.y,
            valueTV = binding.tvCpointY,
            seekBar = binding.sbCpointY,

            ) { value ->
            camera.cameraDirectionPoint.y = value
        }

        cameraUI(
            stringResId = R.string.z,
            startCameraValue = camera.cameraDirectionPoint.z,
            valueTV = binding.tvCpointZ,
            seekBar = binding.sbCpointZ,

            ) { value ->
            camera.cameraDirectionPoint.z = value
        }
        //endregion
        //region upVector
        cameraUI(
            stringResId = R.string.x,
            startCameraValue = camera.upVector.x,
            valueTV = binding.tvVectorX,
            seekBar = binding.sbVectorX,

            ) { value ->
            camera.upVector.x = value
        }
        cameraUI(
            stringResId = R.string.y,
            startCameraValue = camera.upVector.y,
            valueTV = binding.tvVectorY,
            seekBar = binding.sbVectorY,

            ) { value ->
            camera.upVector.y = value
        }
        cameraUI(
            stringResId = R.string.z,
            startCameraValue = camera.upVector.z,
            valueTV = binding.tvVectorZ,
            seekBar = binding.sbVectorZ,

            ) { value ->
            camera.upVector.z = value
        }
        //endregion
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

    val camera = CameraView(
//        cameraPosition = Coord(x = 0f, y = 5.94f, z = 196.26f),
//        cameraDirectionPoint = Coord(x = 2.55f, y = 99.409996f)
        cameraPosition = Coord(x = 0f, y = 0.0f, z = 196.26f),
        cameraDirectionPoint = Coord(x = 0.0f, y = 0.0f, z = -1.0f),
        upVector = Coord(x = 0.0f, y = 1.0f, z = 0.0f)
    )


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