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


package com.example.animationsdk.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class c_Item {
    //region Переменные _...
    private c_map _map; // ссылка на карту которой принадлежит обьект
    private c_coord _coord; // координаты в UDM
    c_backgroundEngine _background;
    private byte _floor; //нахождение на каком уровне эл.
    //endregion
    //region Аксессоры

    //region g_...
    public c_backgroundType g_backgroundLink() {
        return _background._linkOnUnitBackground;
    }
    //endregion

    //region s_...
    //endregion

    //endregion
    //region Конструкторы
    public c_Item() {
        _coord = new c_coord();
        //_background = new c_background();
    }

    //endregion
    //region Функции
    public void _moveItem() //движение обьекта, просчет координат
    {

    }

    public void f_takeABlow(int value/*сила удара */)/*Принять удар // */ {

    }


    //endregion
}

class c_map {

    public static float _sizeOneItemMapPx;

}

class c_troop {
    //region properties _...
    //short _id;// идентификатор отряда (забыли зачем, но походу дурости ради )
    c_player _player;//идентификатор команды (игрока) к которой принадлежит этот отряд
    c_unit _typeUnit; // вид(тип) юнитов этого отряда
    short _countLiveUnits;// количество живых юнитов в отряде
    c_coord _centralCoord;// Центральные координаты отряда

    //endregion
    //region constructor
    public c_troop() {

    }

    //endregion
    //region function
    //region public
    public boolean is_haveLifeUnit() {
        return false;///!!!
    }

    public boolean is_haveUnitInRadius() {
        return false;//!!!
    }

    //Получить новые центральные координаты для группы
    public c_coord s_centralCoord() {
        //!!!
        //проверяем жива ли группа
        //получаю новые координаты отправки
        //проверяю доступны ли эти координаты
        //устанавливаем ей следующие центральные координаты
        return null;
    }
    //endregion
    //endregion
    //region accessors

    //endregion

}

class c_ground extends c_Item {
    byte _typeOfSurface;
    private byte _countOfSeats;
    List<c_unit> c_unitsOn = new ArrayList<c_unit>();

    boolean f_addUnit(c_unit addedUnit) {
        //!!! Добавление (посадка) на эту клетку если это возможно в зависимости от количества занимаемы и оставшихся свободных мест
        return false;
    }

    void g_countOfFreeSeats() {
        //!!! пройтись циклом по добавленым юнитам и узнать сколько кто занимает места (то есть узнать его fat) и отнять после от вместимости эти занимаемые места
    }

    //region Enum ground types
    public static class e_types {
        public static final byte grees = 0;
        public static final byte woter = 1;
        public static final byte rock = 2;
        public static final byte wood = 3;
    }
    //endregion

}

//endregion
class c_coord extends c_twinFloat {
    //region constructors
    public c_coord() {
        _XorLeft = 0;
        _YorTop = 0;
    }

    public c_coord(float XorLeft, float YorTop) {
        _XorLeft = XorLeft;
        _YorTop = YorTop;
    }

    public c_coord(c_coord coord) {
        _XorLeft = coord._XorLeft;
        _YorTop = coord._YorTop;
    }

    //endregion
    //region func
    //region math func  + - / *
    public c_coord f_plus(float XorLeft, float YorTop)/*возвращает сумму координат этого обьекта и принимаемых
    (обьект класса не меняет)*/ {
        return new c_coord(this._XorLeft + XorLeft, this._YorTop + YorTop);
    }

    public c_coord f_plus(c_coord coord) {
        return f_plus(coord._XorLeft, coord._YorTop);
    }

    public c_coord f_division(float value)/*деление */ {
        return f_division(value, value);
    }

    public c_coord f_division(c_coord value)/*деление */ {
        return f_division(value._XorLeft, value._YorTop);
    }

    public c_coord f_division(float XorLeft, float YorTop)/*деление */ {
        return new c_coord(this._XorLeft / XorLeft, this._YorTop / YorTop);
    }

    public c_coord f_multiply(float multipler)/* умножение */ {
        return f_multiply(multipler, multipler);
    }

    public c_coord f_multiply(c_coord multipler) {
        return f_multiply(multipler._XorLeft, multipler._YorTop);
    }

    public c_coord f_multiply(float XorLeft, float YorTop) {
        return new c_coord(this._XorLeft * XorLeft, this._YorTop * YorTop);
    }

    public c_coord f_minus(float XorLeft, float YorTop) {
        return new c_coord(this._XorLeft - XorLeft, this._YorTop - YorTop);
    }

    public c_coord f_minus(c_coord coord) {
        return f_minus(coord._XorLeft, coord._YorTop);
    }

    //endregion
    public static float f_distance(c_coord t1, c_coord t2) /*просчитывает растояние от точки 1 до точки 2*/ {
        c_coord a = t1.f_minus(t2);
        if (a._YorTop < 0) a._YorTop = a._YorTop * -1;
        if (a._XorLeft < 0) a._XorLeft = a._XorLeft * -1;
        return (float) Math.sqrt(a._XorLeft * a._XorLeft + a._YorTop * a._YorTop);
    }

    public float f_distance(c_coord coord) {
        return f_distance(coord, this);
    }

    public float f_distance(float XorLeft, float YorTop) {
        return f_distance(new c_coord(XorLeft, YorTop), this);
    }

    public boolean is_coordInRadius(c_coord _Coord, float _Radius) {
        return f_distance(_Coord) <= _Radius;
    }

    public static float f_angel(c_coord firstPoint, c_coord secondPoint) {
        c_coord dist = secondPoint.f_minus(firstPoint);
        double teta = Math.atan2(dist._YorTop, dist._XorLeft);
        double angel = teta * (180 / Math.PI);//3.14);//
        return (float) angel + 90;
    }

    public float f_angel(c_coord secondPoint) {
        return f_angel(this, secondPoint);
    }

    public static float fs_calcDistPath(c_coord startPoint, c_coord endPoint, c_map map) {

        //!!! возращаем ОГРОМНУЮ функцию просчета пути

        //

        return 0;
    }

    private static ArrayList<c_coord> gs_passablePointsList(c_coord startPoint, c_coord endPoint) {
        float t_disstance = c_coord.f_distance(startPoint, endPoint);
        float t_countSteps = t_disstance / c_map._sizeOneItemMapPx;
        c_coord t_difference = endPoint.f_minus(startPoint);
        c_coord t_oneStep = t_difference.f_division(t_countSteps);
        ArrayList<c_coord> returnedList = new ArrayList<c_coord>();
        c_coord t_currentPoint = startPoint;
        for (int i = 0; i < t_countSteps; i++) {
            t_currentPoint.f_plus(t_oneStep);
            returnedList.add(t_currentPoint);
        }
        return returnedList;
    }

    //region конверторы UDM, PX, Position
    public static float f_pxToUDM(float px) {
        return px / g_oneUDMInPx();
    }

    public static float f_UDMToPx(float UDM) {
        return UDM * g_oneUDMInPx();
    }

    public static int f_UDMToPosition(float UDM)/*+++*/ {
        float t_floatResult = UDM / g_onePositionInUDM();//
        int t_returned = (int) t_floatResult;
        if (t_floatResult > t_returned) {
            t_returned += 1;
        }
        return t_returned;
    }

    public static float f_PositionToUDM(float position)/*преобразовывает позицию в UDM позицию*/ {
        float t_floatResult = (position * g_onePositionInUDM() + (g_onePositionInUDM() / 2));
        return t_floatResult;
    }

    static public float g_oneUDMInPx() /*количество пикселей в одном UDM*/ {
        return c_map._sizeOneItemMapPx / g_onePositionInUDM();
    }

    public static float g_onePositionInUDM()/*количество UDM в одном эл. позиции*/ {
        return 100;
    }

    //endregion
    //endregion
    //region Accessor
    //region s_... Seter
    public void s_coordUDM(c_twinFloat coord)/*+++*/ {
        _XorLeft = coord._XorLeft;
        _YorTop = coord._YorTop;
    }

    public void s_coordUDM(float XorLeft, float YorTop)/*+++*/ {
        _XorLeft = XorLeft;
        _YorTop = YorTop;
    }

    public void s_coordPos(c_twinFloat coord)/*+++*/ {
        _XorLeft = f_PositionToUDM(coord._XorLeft);
        _YorTop = f_PositionToUDM(coord._YorTop);
    }

    public void s_coordPos(float XorLeft, float YorTop)/*+++*/ {
        _XorLeft = f_PositionToUDM(XorLeft);
        _YorTop = f_PositionToUDM(YorTop);
    }

    public void s_coordPx(c_twinFloat coord)/*+++*/ {
        _XorLeft = f_pxToUDM(coord._XorLeft);
        _YorTop = f_pxToUDM(coord._YorTop);
    }

    public void s_coordPx(float XorLeft, float YorTop)/*+++*/ {
        _XorLeft = f_pxToUDM(XorLeft);
        _YorTop = f_pxToUDM(YorTop);
    }

    //endregion
    //region g_.... Geter
    public c_twinFloat g_Px()/*+++*/ {
        return new c_twinFloat(f_UDMToPx(this._XorLeft), f_UDMToPx(this._YorTop));
    }

    public c_twinFloat g_position()/*+++*/ {
        return new c_twinFloat(f_UDMToPosition(this._XorLeft), f_UDMToPosition(this._YorTop));
    }

    public c_coord g_clone() {
        return new c_coord(this);
    }
    //endregion
    //endregion
}

class c_twinFloat {
    //region properties .....
    public float _XorLeft = 0;
    public float _YorTop = 0;

    // endregion
    //region constructors
    public c_twinFloat() {
        _XorLeft = 0;
        _YorTop = 0;
    }

    public c_twinFloat(float XorLeft, float YorTop) {
        _XorLeft = XorLeft;
        _YorTop = YorTop;
    }

    public c_twinFloat(c_twinFloat coord) {
        _XorLeft = coord._XorLeft;
        _YorTop = coord._YorTop;
    }
    //endregion
}

class c_enum /* public static*/ {
    public static final byte e_Exception = -128;
}

class c_paint {
    //region Конструкторы
    public c_paint() {
    }

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
    public void f_start() {
    }

    public void f_pause() {
    }
    //endregion

}

class c_activityTasck {
    //region Constructors
    public c_activityTasck() {
        _path = null;

    }

    //endregion
    //region _...  properties
    private c_path _path;
    c_troop _ownerTroop; // отряд владелец этим заданием
    c_coord _targetCoord; // инициализируется если цель только дойти до клетки на карте
    c_troop _targetTroop; // цель. отряд = null если заполнена цель клетка на карте

    //endregion
    //region f_ ... Function
    boolean is_NeedAtack() {
        if (_targetTroop == null) return false;
        if (!_targetTroop.is_haveLifeUnit()) return false;
        if (is_NeedMove()) return false;
        return true;
    }

    boolean is_NeedMove() {
        if (_targetCoord != null) return true;
        else {
            if (_targetTroop == null) return false;
            if (!_targetTroop.is_haveLifeUnit()) return false;
            if (_targetTroop.is_haveUnitInRadius()) return false;
        }
        return true;
    }

    //region Public
    public boolean f_Tick() {
        if (_ownerTroop == null) return false;
        if (!_ownerTroop.is_haveLifeUnit())
            return false;// проверка если в отряде нет живых юнитов преждевременное завершение функции
        if (_targetTroop == null) return false;
        return false;//!!!
    }
    //endregion
    //endregion

    void f_atack(int idUnitGroupToAttack, c_path pathToAtack, int idEnemyGroup) {
        //!!! заставляет атаковать вражескую групу определив их по идентификатору
    }

}

class c_path {
    public c_path(c_coord startPoint, c_coord endPoint) {

    }

    private List<c_pathPoint> _pointsArray = new ArrayList<c_pathPoint>();

    public c_coord g_actualNextPoint() {
        return null;//!!!
    }

    public float g_lengthPath() {
        return 0;//!!!
    }

    private float g_lengthPartPath() {
        //!!!
        return 0;
    }

    class c_pathPoint extends c_coord {
        public short _index;
    }
}

class c_player {
    //region Поля класса
    public String _nickName;
    private int _gold;
    ArrayList<c_unit> _unitsTypesDifferences;


    //endregion
    //region constructor
    public c_player() {
        //_unitsTypesDifferences.add(new c_unit(true,c_unit.e_types.archer));
    }
    //endregion
    //region accessors
    //endregion
}

class c_play {
    //класс хранящий параметры игры пренадлежащему конкретному играку
    //region constructor
    public c_play() {
    }

    //endregion
    //region properties
    c_map _map;
    ArrayList<c_unit> _typesOfUnits;
    ArrayList<c_player> _players;
    ArrayList<c_unit> _lifeUnits;
    ArrayList<c_troop> _troops;
    ArrayList<c_backgroundType> _backgroundTypes;
    private byte _counterOfFreeSeats;
    Timer _timer;
    long _startTime;
//endregion

}

