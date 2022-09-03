//package com.example.animationsdk.ui.gl
//
//import android.content.Context
//import android.content.res.Resources
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.opengl.GLES20
//import android.opengl.GLSurfaceView
//import android.opengl.GLUtils
//import android.util.Log
//import com.example.animationsdk.R
//import java.nio.*
//import javax.microedition.khronos.egl.EGLConfig
//import javax.microedition.khronos.opengles.GL10
//import javax.microedition.khronos.opengles.GL11
//
//
//class BitmapRenderer(val context: Context, resources: Resources) : GLSurfaceView.Renderer {
//    val orcBitmaps = mutableListOf<Bitmap>()
//    private val textures = IntArray(256)
//    private val resources: Resources
//    private lateinit var mTriangle: Triangle
////    private lateinit var mSquare: Square2
//
//    var index = 2
////    private val mFVertexBuffer: FloatBuffer? = null
//    var mTextureBuffer: FloatBuffer? = null
//    var textureCoords = floatArrayOf(
//        0.0f, 0.0f,
//        1.0f, 0.0f,
//        0.0f, 1.0f,
//        1.0f, 1.0f
//    )
//
//    init {
//        val tbb = ByteBuffer.allocateDirect(textureCoords.size * 4)
//        tbb.order(ByteOrder.nativeOrder())
//        mTextureBuffer = tbb.asFloatBuffer()
//        mTextureBuffer?.put(textureCoords)
//        mTextureBuffer?.position(0)
//    }
//
//    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
//        Log.d("TAG_2", "onSurfaceCreated(gl: ${config})")
////        gl.glEnable(GL10.GL_TEXTURE_2D)
////        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
////        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
////        gl.glGenTextures(1, textures, 0)
////        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[index % orcBitmaps.size])
////        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())
////        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR.toFloat())
////        gl.glTexParameterf(
////            GL10.GL_TEXTURE_2D,
////            GL10.GL_TEXTURE_WRAP_S,
////            GL10.GL_CLAMP_TO_EDGE.toFloat()
////        )
////        gl.glTexParameterf(
////            GL10.GL_TEXTURE_2D,
////            GL10.GL_TEXTURE_WRAP_T,
////            GL10.GL_CLAMP_TO_EDGE.toFloat()
////        )
////        GLUtils.texImage2D(
////            GL10.GL_TEXTURE_2D,
////            0,
////            orcBitmaps[index % orcBitmaps.size],
////            0
////        )
//
////        // initialize a triangle
////        mTriangle = Triangle()
////        // initialize a square
////        mSquare = Square2()
//
////        orcBitmaps.forEach {
////
////        }
////        GLUtils.texImage2D(
////            GL10.GL_TEXTURE_2D,
////            0,
////            BitmapFactory.decodeResource(resources, R.mipmap.sym_def_app_icon),
////            0
////        )
//
////        val resid: Int = book.BouncySquare.R.drawable.hedly //1
//
//        createTexture(gl, context, R.drawable.star)
//
//    }
//
//    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
//        Log.d("TAG_2", "onSurfaceChanged()")
////        gl.glEnable(GL10.GL_TEXTURE_2D)
////        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
////        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
////        gl.glGenTextures(1, textures, 0)
////        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[index % orcBitmaps.size])
////        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())
////        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR.toFloat())
////        gl.glTexParameterf(
////            GL10.GL_TEXTURE_2D,
////            GL10.GL_TEXTURE_WRAP_S,
////            GL10.GL_CLAMP_TO_EDGE.toFloat()
////        )
////        gl.glTexParameterf(
////            GL10.GL_TEXTURE_2D,
////            GL10.GL_TEXTURE_WRAP_T,
////            GL10.GL_CLAMP_TO_EDGE.toFloat()
////        )
////        GLUtils.texImage2D(
////            GL10.GL_TEXTURE_2D,
////            0,
////            orcBitmaps[index % orcBitmaps.size],
////            0
////        )
////        gl.glViewport(0, 0, width, height)
//
//    }
//
//    private val rotationMatrix = FloatArray(16)
//
//    override fun onDrawFrame(gl: GL10) {
//        Log.d("TAG_2", "onDrawFrame(), Thread name: ${Thread.currentThread().name}")
////        val scratch = FloatArray(16)
////        // Create a rotation transformation for the triangle
////        val time = SystemClock.uptimeMillis() % 4000L
////        val angle = 0.090f * time.toInt()
////        Matrix.setRotateM(rotationMatrix, 0, angle, 0f, 0f, -1.0f)
////
////        // Combine the rotation matrix with the projection and camera view
////        // Note that the vPMatrix factor *must be first* in order
////        // for the matrix multiplication product to be correct.
////        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0)
//
//        // Draw triangle
////        mTriangle.draw()
//
////        gl.glEnable(GL10.GL_TEXTURE_2D)
////        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
////        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
////        gl.glGenTextures(1, textures, 0)
////        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[index % orcBitmaps.size])
////        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())
////        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR.toFloat())
////        gl.glTexParameterf(
////            GL10.GL_TEXTURE_2D,
////            GL10.GL_TEXTURE_WRAP_S,
////            GL10.GL_CLAMP_TO_EDGE.toFloat()
////        )
////        gl.glTexParameterf(
////            GL10.GL_TEXTURE_2D,
////            GL10.GL_TEXTURE_WRAP_T,
////            GL10.GL_CLAMP_TO_EDGE.toFloat()
////        )
////        GLUtils.texImage2D(
////            GL10.GL_TEXTURE_2D,
////            0,
////            orcBitmaps[index % orcBitmaps.size],
////            0
//////        )
////        gl.glActiveTexture(GL10.GL_TEXTURE1)
////        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[index])
////        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, VERTEX_BUFFER)
////        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, TEXCOORD_BUFFER)
//////        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4)
////        gl.glDrawArrays(GL10.GL_TEXTURE_2D, 0, 6)
//////        index++
////        if (index >= orcBitmaps.size - 1) {
////            index = 0
////        }
////        Thread.sleep(20)
//
////        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
////        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
////        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, mTextureBuffer);
////        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
////
////        gl.glEnable(GL10.GL_TEXTURE_2D); //1
////        gl.glEnable(GL10.GL_BLEND); //2
////
////        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_SRC_COLOR); //3
////        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]); //4
////
////        gl.glTexCoordPointer(2, GL10.GL_FLOAT,0, mTextureBuffer); //5
////        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY); //6
////        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4); //7
////
////        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
////        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
////        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY); //8
////        Thread.sleep(100);
//
//        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f) //1
//
//        gl.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
//        gl.glMatrixMode(GL11.GL_MODELVIEW)
//        gl.glEnableClientState(GL11.GL_VERTEX_ARRAY)
//        //SQUARE 1
//
//        //SQUARE 1
//        gl.glLoadIdentity()
//        gl.glTranslatef(0.0f, Math.sin(mTransY).toFloat(), -3.0f) //2
//
//        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f)
//        mSquare.draw(gl)
//
//        //SQUARE 2
//
//
//        //SQUARE 2
//        gl.glLoadIdentity() //3
//
//        gl.glTranslatef((Math.sin(mTransY) / 2.0f).toFloat(), 0.0f, -2.9f)
//        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f)
//        mSquare.draw(gl)
//        mTransY += .075f
//    }
//
//    public fun createTexture(gl: GL10, contextRegf: Context, resource: Int): Int {
////        Line 1 loads the Android bitmap, letting the loader handle any image format that
////Android can read in. Table 5–2 lists the supported formats.
////        val image = BitmapFactory.decodeResource(
////            contextRegf.resources,
////            attr.resource
////        ) // 1
////glGenTextures() in line 2 gets an unused texture “name,” which in reality is
////just a number. This ensures that each texture you use has a unique identifier. If
////you want to reuse identifiers, then you’d call glDeleteTextures().
//        gl.glGenTextures(1, textures, 0) // 2
////After that, the texture is bound to the current 2D texture in the next line,
////exchanging the new texture for the previous one. In OpenGL ES, there is only one
////of these texture targets, so it must always be GL_TEXTURE_2D, whereas grownup OpenGL has several.
//// See Table 5–3 for all available parameters. Binding also
////makes this texture active, because only one texture is active at a time. This also
////directs OpenGL where to put any of the new image data. See table 5–2.
//        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0])
//        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, orcBitmaps.first(), 0); // 4
//        gl.glTexParameterf(
//            GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
//            GL10.GL_LINEAR.toFloat()
//        ); // 5a
//        gl.glTexParameterf(
//            GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
//            GL10.GL_LINEAR.toFloat()
//        ); // 5b
//        orcBitmaps.first().recycle(); //6
//        return resource;
//    }
//
//    companion object {
//        private val VERTEX_COORDINATES = floatArrayOf(
//            -1.0f, +1.0f, 0.0f,
//            +1.0f, +1.0f, 0.0f,
//            -1.0f, -1.0f, 0.0f,
//            +1.0f, -1.0f, 0.0f
//        )
//        private val TEXTURE_COORDINATES = floatArrayOf(
//            0.0f, 0.0f,
//            1.0f, 0.0f,
//            0.0f, 1.0f,
//            1.0f, 1.0f
//        )
//        private val TEXCOORD_BUFFER: Buffer =
//            ByteBuffer.allocateDirect(TEXTURE_COORDINATES.size * 4)
//                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(TEXTURE_COORDINATES).rewind()
//        private val VERTEX_BUFFER: Buffer = ByteBuffer.allocateDirect(VERTEX_COORDINATES.size * 4)
//            .order(ByteOrder.nativeOrder()).asFloatBuffer().put(VERTEX_COORDINATES).rewind()
//    }
//
//
//    init {
//        this.resources = resources
//        initBitmap(context, orcBitmaps)
//
//    }
//
//
//    fun initBitmap(
//        context: Context,
//        orcBitmaps: MutableList<Bitmap>
//    ) {
//        val orcBitmap =
//            BitmapFactory.decodeResource(
//                context.resources,
//                com.example.animationsdk.R.drawable.orc_archer_0
//            )
//        val partImgSizeX = orcBitmap.width / 32
//        val partImgSizeY = orcBitmap.height / 8
//        Log.d(
//            "TAG_1",
//            "orcBitmap.width:${orcBitmap.width}, partImgSizeX = $partImgSizeX, orcBitmap.height: ${orcBitmap.height}, partImgSizeY = $partImgSizeY"
//        )
//
//        for (row in 0 until 8) {
//            Log.i("TAG_1", "row: $row * partImgSizeY: $partImgSizeY = ${row * partImgSizeY}")
//            for (colm in 0 until 32) {
//                Log.i("TAG_1", "colm: $colm * partImgSizeX: $partImgSizeX = ${colm * partImgSizeX}")
//                var bitmap = Bitmap.createBitmap(
//                    orcBitmap,
//                    colm * partImgSizeX,
//                    row * partImgSizeY,
//                    partImgSizeX,
//                    partImgSizeY
//                )
////                bitmapExplosion = bitmap
//                bitmap = Bitmap.createScaledBitmap(
//                    bitmap,
//                    128,
//                    128,
//                    false
//                )
//                orcBitmaps.add(bitmap)
//            }
//        }
//
//        Log.d("TAG_1", "orc bitmaps size: ${orcBitmaps.size}")
//    }
//}
//
//
//const val COORDS_PER_VERTEX = 3
//var triangleCoords = floatArrayOf(     // in counterclockwise order:
//    0.0f, 0.622008459f, 0.0f,      // top
//    -0.5f, -0.311004243f, 0.0f,    // bottom left
//    0.5f, -0.311004243f, 0.0f      // bottom right
//)
//
//fun loadShader(type: Int, shaderCode: String): Int {
//
//    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
//    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
//    return GLES20.glCreateShader(type).also { shader ->
//
//        // add the source code to the shader and compile it
//        GLES20.glShaderSource(shader, shaderCode)
//        GLES20.glCompileShader(shader)
//    }
//}
//
//
//class Triangle {
//    private val vertexShaderCode =
//        "attribute vec4 vPosition;" +
//                "void main() {" +
//                "  gl_Position = vPosition;" +
//                "}"
//
//    private val fragmentShaderCode =
//        "precision mediump float;" +
//                "uniform vec4 vColor;" +
//                "void main() {" +
//                "  gl_FragColor = vColor;" +
//                "}"
//
//    private var mProgram: Int
//
//    init {
//
//        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
//        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
//
//        // create empty OpenGL ES Program
//        mProgram = GLES20.glCreateProgram().also {
//
//            // add the vertex shader to program
//            GLES20.glAttachShader(it, vertexShader)
//
//            // add the fragment shader to program
//            GLES20.glAttachShader(it, fragmentShader)
//
//            // creates OpenGL ES program executables
//            GLES20.glLinkProgram(it)
//        }
//    }
//
//    // Set color with red, green, blue and alpha (opacity) values
//    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
//
//    private var vertexBuffer: FloatBuffer =
//        // (number of coordinate values * 4 bytes per float)
//        ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
//            // use the device hardware's native byte order
//            order(ByteOrder.nativeOrder())
//
//            // create a floating point buffer from the ByteBuffer
//            asFloatBuffer().apply {
//                // add the coordinates to the FloatBuffer
//                put(triangleCoords)
//                // set the buffer to read the first coordinate
//                position(0)
//            }
//        }
//    private var positionHandle: Int = 0
//    private var mColorHandle: Int = 0
//
//    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX
//    private val vertexStride: Int = COORDS_PER_VERTEX * 4 // 4 bytes per vertex
//
//    fun draw() {
//        // Add program to OpenGL ES environment
//        GLES20.glUseProgram(mProgram)
//
//        // get handle to vertex shader's vPosition member
//        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {
//
//            // Enable a handle to the triangle vertices
//            GLES20.glEnableVertexAttribArray(it)
//
//            // Prepare the triangle coordinate data
//            GLES20.glVertexAttribPointer(
//                it,
//                COORDS_PER_VERTEX,
//                GLES20.GL_FLOAT,
//                false,
//                vertexStride,
//                vertexBuffer
//            )
//
//            // get handle to fragment shader's vColor member
//            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
//
//                // Set color for drawing the triangle
//                GLES20.glUniform4fv(colorHandle, 1, color, 0)
//            }
//
//            // Draw the triangle
//            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
//
//            // Disable vertex array
//            GLES20.glDisableVertexAttribArray(it)
//        }
//    }
//}
//
//var squareCoords = floatArrayOf(
//    -0.5f, 0.5f, 0.0f,      // top left
//    -0.5f, -0.5f, 0.0f,      // bottom left
//    0.5f, -0.5f, 0.0f,      // bottom right
//    0.5f, 0.5f, 0.0f       // top right
//)
//
//class Square2 {
//
//    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3) // order to draw vertices
//
//    // initialize vertex byte buffer for shape coordinates
//    private val vertexBuffer: FloatBuffer =
//        // (# of coordinate values * 4 bytes per float)
//        ByteBuffer.allocateDirect(squareCoords.size * 4).run {
//            order(ByteOrder.nativeOrder())
//            asFloatBuffer().apply {
//                put(squareCoords)
//                position(0)
//            }
//        }
//
//    // initialize byte buffer for the draw list
//    private val drawListBuffer: ShortBuffer =
//        // (# of coordinate values * 2 bytes per short)
//        ByteBuffer.allocateDirect(drawOrder.size * 2).run {
//            order(ByteOrder.nativeOrder())
//            asShortBuffer().apply {
//                put(drawOrder)
//                position(0)
//            }
//        }
//}