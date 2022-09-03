/*+++  - это означает что мы перепроверили дописаную функию
 * //!!! - функция требующая реализации
 * UDM - условная еденица измерения карты
 * с_ - класс
 * f_ - функция
 * g_ -geter
 * s_ -seter
 * _ - поле класса
 * t_ - временная переменная
 * is_... - булевая функция
 * fs_ - публичная статичная функция
 * gs - гетер статичный */
package com.example.animationsdk.example

import java.util.*

open class c_Item {
    //region Переменные _...
    private val _map // ссылка на карту которой принадлежит обьект
            : c_map? = null
    private val _coord // координаты в UDM
            : c_coord
    var _background: c_backgroundEngine? = null
    private val _floor //нахождение на каком уровне эл.
            : Byte = 0

    //endregion
    //region Аксессоры
    //region g_...
    fun g_backgroundLink(): c_backgroundType {
        return _background!!._linkOnUnitBackground
    }

    //endregion
    //region Функции
    fun _moveItem() //движение обьекта, просчет координат
    {
    }

    fun f_takeABlow(value: Int /*сила удара */) /*Принять удар // */ {} //endregion

    //endregion
    //region s_...
    //endregion
    //endregion
    //region Конструкторы
    init {
        _coord = c_coord()
        //_background = new c_background();
    }
}

object c_map {
    var _sizeOneItemMapPx = 0f
}

class c_troop  //endregion
//region constructor
{
    //region properties _...
    //short _id;// идентификатор отряда (забыли зачем, но походу дурости ради )
    var _player //идентификатор команды (игрока) к которой принадлежит этот отряд
            : c_player? = null
    var _typeUnit // вид(тип) юнитов этого отряда
            : c_unit? = null
    var _countLiveUnits // количество живых юнитов в отряде
            : Short = 0
    var _centralCoord // Центральные координаты отряда
            : c_coord? = null

    //endregion
    //region function
    //region public
    fun is_haveLifeUnit(): Boolean {
        return false ///!!!
    }

    fun is_haveUnitInRadius(): Boolean {
        return false //!!!
    }

    //Получить новые центральные координаты для группы
    fun s_centralCoord(): c_coord? {
        //!!!
        //проверяем жива ли группа
        //получаю новые координаты отправки
        //проверяю доступны ли эти координаты
        //устанавливаем ей следующие центральные координаты
        return null
    } //endregion
    //endregion
    //region accessors
    //endregion
}

class c_ground : c_Item() {
    var _typeOfSurface: Byte = 0
    private val _countOfSeats: Byte = 0
    var c_unitsOn: List<c_unit> = ArrayList()
    fun f_addUnit(addedUnit: c_unit?): Boolean {
        //!!! Добавление (посадка) на эту клетку если это возможно в зависимости от количества занимаемы и оставшихся свободных мест
        return false
    }

    fun g_countOfFreeSeats() {
        //!!! пройтись циклом по добавленым юнитам и узнать сколько кто занимает места (то есть узнать его fat) и отнять после от вместимости эти занимаемые места
    }

    //region Enum ground types
    object e_types {
        const val grees: Byte = 0
        const val woter: Byte = 1
        const val rock: Byte = 2
        const val wood: Byte = 3
    } //endregion
} //endregion

open class c_coord : c_twinFloat {
    //region constructors
    constructor() {
        _XorLeft = 0f
        _YorTop = 0f
    }

    constructor(XorLeft: Float, YorTop: Float) {
        _XorLeft = XorLeft
        _YorTop = YorTop
    }

    constructor(coord: c_coord) {
        _XorLeft = coord._XorLeft
        _YorTop = coord._YorTop
    }

    //endregion
    //region func
    //region math func  + - / *
    fun f_plus(XorLeft: Float, YorTop: Float): c_coord /*возвращает сумму координат этого обьекта и принимаемых
    (обьект класса не меняет)*/ {
        return c_coord(_XorLeft + XorLeft, _YorTop + YorTop)
    }

    fun f_plus(coord: c_coord): c_coord {
        return f_plus(coord._XorLeft, coord._YorTop)
    }

    fun f_division(value: Float): c_coord /*деление */ {
        return f_division(value, value)
    }

    fun f_division(value: c_coord): c_coord /*деление */ {
        return f_division(value._XorLeft, value._YorTop)
    }

    fun f_division(XorLeft: Float, YorTop: Float): c_coord /*деление */ {
        return c_coord(_XorLeft / XorLeft, _YorTop / YorTop)
    }

    fun f_multiply(multipler: Float): c_coord /* умножение */ {
        return f_multiply(multipler, multipler)
    }

    fun f_multiply(multipler: c_coord): c_coord {
        return f_multiply(multipler._XorLeft, multipler._YorTop)
    }

    fun f_multiply(XorLeft: Float, YorTop: Float): c_coord {
        return c_coord(_XorLeft * XorLeft, _YorTop * YorTop)
    }

    fun f_minus(XorLeft: Float, YorTop: Float): c_coord {
        return c_coord(_XorLeft - XorLeft, _YorTop - YorTop)
    }

    fun f_minus(coord: c_coord): c_coord {
        return f_minus(coord._XorLeft, coord._YorTop)
    }

    fun f_distance(coord: c_coord): Float {
        return f_distance(coord, this)
    }

    fun f_distance(XorLeft: Float, YorTop: Float): Float {
        return f_distance(c_coord(XorLeft, YorTop), this)
    }

    fun is_coordInRadius(_Coord: c_coord, _Radius: Float): Boolean {
        return f_distance(_Coord) <= _Radius
    }

    fun f_angel(secondPoint: c_coord): Float {
        return f_angel(this, secondPoint)
    }

    //endregion
    //endregion
    //region Accessor
    //region s_... Seter
    fun s_coordUDM(coord: c_twinFloat) /*+++*/ {
        _XorLeft = coord._XorLeft
        _YorTop = coord._YorTop
    }

    fun s_coordUDM(XorLeft: Float, YorTop: Float) /*+++*/ {
        _XorLeft = XorLeft
        _YorTop = YorTop
    }

    fun s_coordPos(coord: c_twinFloat) /*+++*/ {
        _XorLeft = f_PositionToUDM(coord._XorLeft)
        _YorTop = f_PositionToUDM(coord._YorTop)
    }

    fun s_coordPos(XorLeft: Float, YorTop: Float) /*+++*/ {
        _XorLeft = f_PositionToUDM(XorLeft)
        _YorTop = f_PositionToUDM(YorTop)
    }

    fun s_coordPx(coord: c_twinFloat) /*+++*/ {
        _XorLeft = f_pxToUDM(coord._XorLeft)
        _YorTop = f_pxToUDM(coord._YorTop)
    }

    fun s_coordPx(XorLeft: Float, YorTop: Float) /*+++*/ {
        _XorLeft = f_pxToUDM(XorLeft)
        _YorTop = f_pxToUDM(YorTop)
    }

    //endregion
    //region g_.... Geter
    fun g_Px(): c_twinFloat /*+++*/ {
        return c_twinFloat(
            f_UDMToPx(_XorLeft), f_UDMToPx(
                _YorTop
            )
        )
    }

    fun g_position(): c_twinFloat /*+++*/ {
        return c_twinFloat(
            f_UDMToPosition(_XorLeft).toFloat(), f_UDMToPosition(
                _YorTop
            )
                .toFloat()
        )
    }

    fun g_clone(): c_coord {
        return c_coord(this)
    } //endregion

    //endregion
    companion object {
        //endregion
        fun f_distance(
            t1: c_coord,
            t2: c_coord
        ): Float /*просчитывает растояние от точки 1 до точки 2*/ {
            val a = t1.f_minus(t2)
            if (a._YorTop < 0) a._YorTop = a._YorTop * -1
            if (a._XorLeft < 0) a._XorLeft = a._XorLeft * -1
            return Math.sqrt((a._XorLeft * a._XorLeft + a._YorTop * a._YorTop).toDouble()).toFloat()
        }

        fun f_angel(firstPoint: c_coord, secondPoint: c_coord): Float {
            val dist = secondPoint.f_minus(firstPoint)
            val teta = Math.atan2(dist._YorTop.toDouble(), dist._XorLeft.toDouble())
            val angel = teta * (180 / Math.PI) //3.14);//
            return angel.toFloat() + 90
        }

        fun fs_calcDistPath(startPoint: c_coord?, endPoint: c_coord?, map: c_map?): Float {

            //!!! возращаем ОГРОМНУЮ функцию просчета пути

            //
            return 0f
        }

        private fun gs_passablePointsList(
            startPoint: c_coord,
            endPoint: c_coord
        ): ArrayList<c_coord> {
            val t_disstance = f_distance(startPoint, endPoint)
            val t_countSteps = t_disstance / c_map._sizeOneItemMapPx
            val t_difference = endPoint.f_minus(startPoint)
            val t_oneStep = t_difference.f_division(t_countSteps)
            val returnedList = ArrayList<c_coord>()
            var i = 0
            while (i < t_countSteps) {
                startPoint.f_plus(t_oneStep)
                returnedList.add(startPoint)
                i++
            }
            return returnedList
        }

        //region конверторы UDM, PX, Position
        fun f_pxToUDM(px: Float): Float {
            return px / g_oneUDMInPx()
        }

        fun f_UDMToPx(UDM: Float): Float {
            return UDM * g_oneUDMInPx()
        }

        fun f_UDMToPosition(UDM: Float): Int /*+++*/ {
            val t_floatResult = UDM / g_onePositionInUDM() //
            var t_returned = t_floatResult.toInt()
            if (t_floatResult > t_returned) {
                t_returned += 1
            }
            return t_returned
        }

        fun f_PositionToUDM(position: Float): Float /*преобразовывает позицию в UDM позицию*/ {
            return position * g_onePositionInUDM() + g_onePositionInUDM() / 2
        }

        fun g_oneUDMInPx(): Float /*количество пикселей в одном UDM*/ {
            return c_map._sizeOneItemMapPx / g_onePositionInUDM()
        }

        fun g_onePositionInUDM(): Float /*количество UDM в одном эл. позиции*/ {
            return 100f
        }
    }
}

open class c_twinFloat {
    //region properties .....
    var _XorLeft = 0f
    var _YorTop = 0f

    // endregion
    //region constructors
    constructor() {
        _XorLeft = 0f
        _YorTop = 0f
    }

    constructor(XorLeft: Float, YorTop: Float) {
        _XorLeft = XorLeft
        _YorTop = YorTop
    }

    constructor(coord: c_twinFloat) {
        _XorLeft = coord._XorLeft
        _YorTop = coord._YorTop
    } //endregion
}

object c_enum /* public static*/ {
    const val e_Exception: Byte = -128
}

class c_paint  //region Конструкторы
{
    //endregion
    //region Переменные, свойства, объекты, ...
    //endregion
    //region Аксессоры
    //region Get(g_...)
    //endregion
    //region Set (s_...)
    //endregion
    //endregion
    //region Функции (f_...)
    fun f_start() {}
    fun f_pause() {} //endregion
}

class c_activityTasck     //region Constructors
{
    //endregion
    //region _...  properties
    private val _path: c_path? = null
    var _ownerTroop // отряд владелец этим заданием
            : c_troop? = null
    var _targetCoord // инициализируется если цель только дойти до клетки на карте
            : c_coord? = null
    var _targetTroop // цель. отряд = null если заполнена цель клетка на карте
            : c_troop? = null

    //endregion
    //region f_ ... Function
    fun is_NeedAtack(): Boolean {
        if (_targetTroop == null) return false
        if (!_targetTroop!!.is_haveLifeUnit()) return false
        return if (is_NeedMove()) false else true
    }

    fun is_NeedMove(): Boolean {
        if (_targetCoord != null) return true else {
            if (_targetTroop == null) return false
            if (!_targetTroop!!.is_haveLifeUnit()) return false
            if (_targetTroop!!.is_haveUnitInRadius()) return false
        }
        return true
    }

    //region Public
    fun f_Tick(): Boolean {
        if (_ownerTroop == null) return false
        if (!_ownerTroop!!.is_haveLifeUnit()) return false // проверка если в отряде нет живых юнитов преждевременное завершение функции
        return if (_targetTroop == null) false else false
        //!!!
    }

    //endregion
    //endregion
    fun f_atack(idUnitGroupToAttack: Int, pathToAtack: c_path?, idEnemyGroup: Int) {
        //!!! заставляет атаковать вражескую групу определив их по идентификатору
    }
}

class c_path(startPoint: c_coord?, endPoint: c_coord?) {
    private val _pointsArray: List<c_pathPoint> = ArrayList()
    fun g_actualNextPoint(): c_coord? {
        return null //!!!
    }

    fun g_lengthPath(): Float {
        return 0f //!!!
    }

    private fun g_lengthPartPath(): Float {
        //!!!
        return 0f
    }

    inner class c_pathPoint : c_coord() {
        var _index: Short = 0
    }
}

class c_player  //endregion
//region constructor
//endregion
//region accessors
//endregion
{
    //region Поля класса
    var _nickName: String? = null
    private val _gold = 0
    var _unitsTypesDifferences: ArrayList<c_unit>? = null
}

class c_play  //класс хранящий параметры игры пренадлежащему конкретному играку
//region constructor
{
    //endregion
    //region properties
    var _map: c_map? = null
    var _typesOfUnits: ArrayList<c_unit>? = null
    var _players: ArrayList<c_player>? = null
    var _lifeUnits: ArrayList<c_unit>? = null
    var _troops: ArrayList<c_troop>? = null
    var _backgroundTypes: ArrayList<c_backgroundType>? = null
    private val _counterOfFreeSeats: Byte = 0
    var _timer: Timer? = null
    var _startTime: Long = 0 //endregion
}