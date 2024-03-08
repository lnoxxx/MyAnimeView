package com.lnoxx.myanimeview.jikanApi.enumClasses

enum class AgesRating {
    G,
    PG,
    PG13,
    R17,
    R,
    RX,
    ;
    fun typeToQuery() = when(this){
        G -> "g"
        PG -> "pg"
        PG13 -> "pg13"
        R17 -> "r17"
        R -> "r"
        RX -> "rx"
    }
}