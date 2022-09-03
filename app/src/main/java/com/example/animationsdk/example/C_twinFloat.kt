package com.example.animationsdk.example

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