package com.example.animationsdk.example

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