package com.example.animationsdk.example

import java.util.*

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