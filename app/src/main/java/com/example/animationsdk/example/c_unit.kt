package com.example.animationsdk.example

class c_unit : c_Item {
    //Юнит получает координаты и стоит не пропуская никого (юнит как препятствие (непроходимое))
    //
    //region переменные _...
    //region Подя экземпляра
    private var _hp // кол-го жизни
            : Short = 0
    private var _armor // кол-го брони
            : Short = 0
    private var _moveSpeed //скорость движения в UDM
            = 0f
    private var _fat // размер моба количество мест которое занимает в одной клетке карты
            : Byte = 0
    private var _rangeAtack //дальность атаки
            = 0f
    private var _activityRadius //радиус активности
            = 0f
    private var _speedAtack // скорость атаки юнита или башни
            : Short = 0
    private var _multiplierMoveSpeed //удвоение скорости передвижения
            = 0f
    private var _multiplierAtackSpeed //удвоение скорости атаки
            = 0f
    private var _multiplierRangeAtack // удвоение дальности атаки
            = 0f
    private var _multiplerOfVisibilityOfAtack // удвоение видимости юнита для атаки
            = 0f
    private var _isAtackedMe // проверочная переменная атакуют ли меня на данный момент времени
            = false
    private var _damage //дамаг наносимый при смерти
            : Short = 0
    private var _damageOfAtack //дамаг наносимый при ударе
            : Short = 0
    private var _costPerKill // стоимость за убийство
            : Short = 0

    //endregion
    //region обьявление обьектов класса
    private var _name //имя моба //???
            : String? = null
    private var _automaticTasck: c_activityTasck? = null
    private var _playerTasck: c_activityTasck? = null
    private var _troop // отряд к которому пренадлежит этот юнит
            : c_troop? = null
    private var _type // тип юнита
            : Byte = 0
    private var _coord: c_coord? = null

    //endregion
    //endregion
    //region конструкторы
    constructor() {
        f_reInitDifferencesUnit(e_types.archer)
        /*this._hp = 100;
      this._armor = 10;
      this._damage = 10;
      this._damageOfAtack = 10;
      this._moveSpeed = 1;
      this._rangeAtack = 10;
      this._speedAtack = 1;
      this._multiplierAtackSpeed = 1;
      this._multiplierMoveSpeed = 1;
      this._multiplierRangeAtack = 1;
      this._fat = 1;
      this._troop = null;
      this._background = null;
      this._type = e_types.archer;
      // this._name =e_nameUnits.ARCHER;//!!! разобраться какое имя ему следует присвоиить по умолчанию
      this._costPerKill = 10;*/
    }

    constructor(
        _background: c_backgroundEngine?,
        hp: Short,
        armor: Short,
        moveSpeed: Float,
        rangeAtack: Float,
        speedAtack: Short,
        troop: c_troop?,
        damageOfAtack: Short,
        damage: Short,
        fat: Byte,
        types: Byte,
        name: String?,
        costPerKill: Short,
        isLife: Boolean
    ) {
        _hp = hp
        _armor = armor
        _damage = damage
        _damageOfAtack = damageOfAtack
        _moveSpeed = moveSpeed
        _rangeAtack = rangeAtack
        _speedAtack = speedAtack
        _multiplierAtackSpeed = 1f
        _multiplierMoveSpeed = 1f
        _multiplierRangeAtack = 1f
        _fat = fat
        _troop = troop
        this._background = _background
        _type = types
        _name = name
        _costPerKill = costPerKill
    }

    constructor(hp: Short, moveSpeed: Float, damage: Short, isLife: Boolean) {
        _hp = hp
        _moveSpeed = moveSpeed
        _damage = damage
    }

    //endregion
    //region Enum unit types
    object e_types {
        const val archer: Byte = 0
        const val spider: Byte = 1
        const val small_spider: Byte = 2
    }

    //endregion
    //region Enum unit states
    object e_states {
        //так-же состояния должны быть отсортированы по приоритетам. У кого выше цифра может перебивать
        const val none: Byte = 0
        const val stend: Byte = 10
        const val run: Byte = 20
        const val runAndAtack: Byte = 30
        const val atack: Byte = 40
        const val magicAtack: Byte = 50
        const val tackABlow: Byte = 60
        const val dead: Byte = 70
    }

    //endregion
    //region Enum unit name
    object e_nameUnits {
        const val ARCHER = "ARCHER"
        const val SPIDER = "SPIDER"
        const val SMALL_SPIDER = "SMALL_SPIDER"
    }

    //endregion
    //region Аксессоры
    //region Getters
    fun g_hp(): Short {
        return _hp
    }

    fun g_armor(): Short {
        return _armor
    }

    fun g_moveSpeed(): Float {
        return _moveSpeed
    }

    fun g_fat(): Byte {
        return _fat
    }

    fun g_rangeAtack(): Float {
        return _rangeAtack
    }

    fun g_activityRadius(): Float {
        return _activityRadius
    }

    fun g_speedAtack(): Short {
        return _speedAtack
    }

    fun g_multiplierMoveSpeed(): Float {
        return _multiplierMoveSpeed
    }

    fun g_multiplierAtackSpeed(): Float {
        return _multiplierAtackSpeed
    }

    fun g_multiplierRangeAtack(): Float {
        return _multiplierRangeAtack
    }

    fun g_multiplerOfVisibilityOfAtack(): Float {
        return _multiplerOfVisibilityOfAtack
    }

    fun is_isAtackedMe(): Boolean {
        return _isAtackedMe
    }

    fun g_damage(): Short {
        return _damage
    }

    fun g_damageOfAtsck(): Short {
        return _damageOfAtack
    }

    fun g_name(): String? {
        return _name
    }

    fun g_costPerKill(): Short {
        return _costPerKill
    }

    fun g_automaticTasck(): c_activityTasck? {
        return _automaticTasck
    }

    fun g_playerTasck(): c_activityTasck? {
        return _playerTasck
    }

    fun g_troop(): c_troop? {
        return _troop
    }

    fun g_types(): Byte {
        return _type
    }

    fun g_coord(): c_coord? {
        return _coord
    }

    //endregion
    //region Setters
    fun s_hp(_hp: Short) {
        this._hp = _hp
    }

    fun s_armor(_armor: Short) {
        this._armor = _armor
    }

    fun s_moveSpeed(_moveSpeed: Float) {
        this._moveSpeed = _moveSpeed
    }

    fun s_fat(_fat: Byte) {
        this._fat = _fat
    }

    fun s_rangeAtack(_rangeAtack: Float) {
        this._rangeAtack = _rangeAtack
    }

    fun s_activityRadius(_activityRadius: Float) {
        this._activityRadius = _activityRadius
    }

    fun s_speedAtack(_speedAtack: Short) {
        this._speedAtack = _speedAtack
    }

    fun s_multiplierMoveSpeed(_multiplierMoveSpeed: Float) {
        this._multiplierMoveSpeed = _multiplierMoveSpeed
    }

    fun s_multiplierAtackSpeed(_multiplierAtackSpeed: Float) {
        this._multiplierAtackSpeed = _multiplierAtackSpeed
    }

    fun s_multiplierRangeAtack(_multiplierRangeAtack: Float) {
        this._multiplierRangeAtack = _multiplierRangeAtack
    }

    fun s_multiplerOfVisibilityOfAtack(_multiplerOfVisibilityOfAtack: Float) {
        this._multiplerOfVisibilityOfAtack = _multiplerOfVisibilityOfAtack
    }

    fun s_isAtackedMe(_isAtackedMe: Boolean) {
        this._isAtackedMe = _isAtackedMe
    }

    fun s_damage(_damage: Short) {
        this._damage = _damage
    }

    fun s_damageOfAtsck(_damageOfAtsck: Short) {
        _damageOfAtack = _damageOfAtsck
    }

    fun s_name(_name: String?) {
        this._name = _name
    }

    fun s_costPerKill(_costPerKill: Short) {
        this._costPerKill = _costPerKill
    }

    //endregion
    //endregion
    //region Functions
    //region Public
    fun f_sendUnit(sendedCoord: c_coord?) {
        //этот метод просчитывает кротчайшие пути, сохраняет лучший и начинает плавное движение по нему
        // метод завершает свое действие по достижению цели
        // приорететно проверяем на каком растоянии от центральных координат отряда находится юнит по необходимости сокращаем
        // до допустимого Растояния (растояние это кротчайший путь)
    }

    fun f_Tick() {
        //!!! организовать вызов тик для каждого юнита
    }

    //endregion
    //region Private
    private fun is_NeedAtack(): Boolean {
        if (_playerTasck!!.is_NeedAtack()) return true
        return if (_automaticTasck!!.is_NeedAtack()) true else false
    }

    fun is_Life(): Boolean { //если персонаж еще жив он может получить урон
        return if (_hp.toInt() == 0) {
            false
        } else {
            false
        }
    }

    fun f_reInitDifferencesUnit(type: Byte) { /*конструктор который служит фиксатором измениний для стандартных полей юнита*/
        _hp = 0
        _armor = 0
        _moveSpeed = 0f
        _fat = 0
        _rangeAtack = 0f
        _activityRadius = 0f
        _speedAtack = 0
        _multiplierMoveSpeed = 0f
        _multiplierAtackSpeed = 0f
        _multiplierRangeAtack = 0f
        _multiplerOfVisibilityOfAtack = 0f
        _isAtackedMe = false
        _damage = 0
        _damageOfAtack = 0
        _costPerKill = 0
        _name = ""
        _automaticTasck = null
        _playerTasck = null
        _troop = null
        _type = type
        this._coord = null
    } //endregion
    //endregion
}

