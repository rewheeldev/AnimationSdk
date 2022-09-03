package com.example.animationsdk.example;

public class c_unit extends c_Item {

    //Юнит получает координаты и стоит не пропуская никого (юнит как препятствие (непроходимое))
    //
    //region переменные _...
    //region Подя экземпляра
    private short _hp; // кол-го жизни
    private short _armor; // кол-го брони
    private float _moveSpeed;//скорость движения в UDM
    private byte _fat; // размер моба количество мест которое занимает в одной клетке карты
    private float _rangeAtack; //дальность атаки
    private float _activityRadius; //радиус активности
    private short _speedAtack; // скорость атаки юнита или башни
    private float _multiplierMoveSpeed; //удвоение скорости передвижения
    private float _multiplierAtackSpeed; //удвоение скорости атаки
    private float _multiplierRangeAtack; // удвоение дальности атаки
    private float _multiplerOfVisibilityOfAtack; // удвоение видимости юнита для атаки
    private boolean _isAtackedMe; // проверочная переменная атакуют ли меня на данный момент времени
    private short _damage; //дамаг наносимый при смерти
    private short _damageOfAtack; //дамаг наносимый при ударе
    private short _costPerKill; // стоимость за убийство
    //endregion
    //region обьявление обьектов класса
    private String _name;//имя моба //???
    private c_activityTasck _automaticTasck;
    private c_activityTasck _playerTasck;
    private c_troop _troop; // отряд к которому пренадлежит этот юнит
    private byte _type; // тип юнита
    private c_coord _coord;

    //endregion
    //endregion
    //region конструкторы
    public c_unit() {
        f_reInitDifferencesUnit(e_types.archer);
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

    public c_unit(c_backgroundEngine _background, short hp, short armor, float moveSpeed, float rangeAtack, short speedAtack, c_troop troop, short damageOfAtack, short damage, byte fat, byte types, String name, short costPerKill, boolean isLife) {
        this._hp = hp;
        this._armor = armor;
        this._damage = damage;
        this._damageOfAtack = damageOfAtack;
        this._moveSpeed = moveSpeed;
        this._rangeAtack = rangeAtack;
        this._speedAtack = speedAtack;
        this._multiplierAtackSpeed = 1;
        this._multiplierMoveSpeed = 1;
        this._multiplierRangeAtack = 1;
        this._fat = fat;
        this._troop = troop;
        this._background = _background;
        this._type = types;
        this._name = name;
        this._costPerKill = costPerKill;
    }

    public c_unit(short hp, float moveSpeed, short damage, boolean isLife) {
        this._hp = hp;
        this._moveSpeed = moveSpeed;
        this._damage = damage;
    }

    //endregion
    //region Enum unit types
    public static final class e_types {
        public static final byte archer = 0;
        public static final byte spider = 1;
        public static final byte small_spider = 2;
    }

    //endregion
    //region Enum unit states
    public static class e_states {//так-же состояния должны быть отсортированы по приоритетам. У кого выше цифра может перебивать
        public static final byte none = 0;
        public static final byte stend = 10;
        public static final byte run = 20;
        public static final byte runAndAtack = 30;
        public static final byte atack = 40;
        public static final byte magicAtack = 50;
        public static final byte tackABlow = 60;
        public static final byte dead = 70;
    }

    //endregion
    //region Enum unit name
    public static final class e_nameUnits {
        public static final String ARCHER = "ARCHER";
        public static final String SPIDER = "SPIDER";
        public static final String SMALL_SPIDER = "SMALL_SPIDER";
    }
    //endregion
    //region Аксессоры
    //region Getters

    public short g_hp() {
        return _hp;
    }

    public short g_armor() {
        return _armor;
    }

    public float g_moveSpeed() {
        return _moveSpeed;
    }

    public byte g_fat() {
        return _fat;
    }

    public float g_rangeAtack() {
        return _rangeAtack;
    }

    public float g_activityRadius() {
        return _activityRadius;
    }

    public short g_speedAtack() {
        return _speedAtack;
    }

    public float g_multiplierMoveSpeed() {
        return _multiplierMoveSpeed;
    }

    public float g_multiplierAtackSpeed() {
        return _multiplierAtackSpeed;
    }

    public float g_multiplierRangeAtack() {
        return _multiplierRangeAtack;
    }

    public float g_multiplerOfVisibilityOfAtack() {
        return _multiplerOfVisibilityOfAtack;
    }

    public boolean is_isAtackedMe() {
        return _isAtackedMe;
    }

    public short g_damage() {
        return _damage;
    }

    public short g_damageOfAtsck() {
        return _damageOfAtack;
    }

    public String g_name() {
        return _name;
    }

    public short g_costPerKill() {
        return _costPerKill;
    }

    public c_activityTasck g_automaticTasck() {
        return _automaticTasck;
    }

    public c_activityTasck g_playerTasck() {
        return _playerTasck;
    }

    public c_troop g_troop() {
        return _troop;
    }

    public byte g_types() {
        return _type;
    }

    public c_coord g_coord() {
        return _coord;
    }

    //endregion
    //region Setters

    public void s_hp(short _hp) {
        this._hp = _hp;
    }

    public void s_armor(short _armor) {
        this._armor = _armor;
    }

    public void s_moveSpeed(float _moveSpeed) {
        this._moveSpeed = _moveSpeed;
    }

    public void s_fat(byte _fat) {
        this._fat = _fat;
    }

    public void s_rangeAtack(float _rangeAtack) {
        this._rangeAtack = _rangeAtack;
    }

    public void s_activityRadius(float _activityRadius) {
        this._activityRadius = _activityRadius;
    }

    public void s_speedAtack(short _speedAtack) {
        this._speedAtack = _speedAtack;
    }

    public void s_multiplierMoveSpeed(float _multiplierMoveSpeed) {
        this._multiplierMoveSpeed = _multiplierMoveSpeed;
    }

    public void s_multiplierAtackSpeed(float _multiplierAtackSpeed) {
        this._multiplierAtackSpeed = _multiplierAtackSpeed;
    }

    public void s_multiplierRangeAtack(float _multiplierRangeAtack) {
        this._multiplierRangeAtack = _multiplierRangeAtack;
    }

    public void s_multiplerOfVisibilityOfAtack(float _multiplerOfVisibilityOfAtack) {
        this._multiplerOfVisibilityOfAtack = _multiplerOfVisibilityOfAtack;
    }

    public void s_isAtackedMe(boolean _isAtackedMe) {
        this._isAtackedMe = _isAtackedMe;
    }

    public void s_damage(short _damage) {
        this._damage = _damage;
    }

    public void s_damageOfAtsck(short _damageOfAtsck) {
        this._damageOfAtack = _damageOfAtsck;
    }

    public void s_name(String _name) {
        this._name = _name;
    }

    public void s_costPerKill(short _costPerKill) {
        this._costPerKill = _costPerKill;
    }

    //endregion
    //endregion
    //region Functions
    //region Public
    public void f_sendUnit(c_coord sendedCoord) {
        //этот метод просчитывает кротчайшие пути, сохраняет лучший и начинает плавное движение по нему
        // метод завершает свое действие по достижению цели
        // приорететно проверяем на каком растоянии от центральных координат отряда находится юнит по необходимости сокращаем
        // до допустимого Растояния (растояние это кротчайший путь)
    }

    public void f_Tick() {
        //!!! организовать вызов тик для каждого юнита
    }
    //endregion

    //region Private
    private boolean is_NeedAtack() {
        if (_playerTasck.is_NeedAtack()) return true;
        if (_automaticTasck.is_NeedAtack()) return true;
        return false;
    }


    public boolean is_Life() { //если персонаж еще жив он может получить урон
        if (_hp == 0) {
            return false;
        } else {
            return false;
        }
    }

    void f_reInitDifferencesUnit(byte type) { /*конструктор который служит фиксатором измениний для стандартных полей юнита*/
        this._hp = 0;
        this._armor = 0;
        this._moveSpeed = 0;
        this._fat = 0;
        this._rangeAtack = 0;
        this._activityRadius = 0;
        this._speedAtack = 0;
        this._multiplierMoveSpeed = 0;
        this._multiplierAtackSpeed = 0;
        this._multiplierRangeAtack = 0;
        this._multiplerOfVisibilityOfAtack = 0;
        this._isAtackedMe = false;
        this._damage = 0;
        this._damageOfAtack = 0;
        this._costPerKill = 0;
        this._name = "";
        this._automaticTasck = null;
        this._playerTasck = null;
        this._troop = null;
        this._type = type;
        this._coord = null;
    }
    //endregion
    //endregion
}