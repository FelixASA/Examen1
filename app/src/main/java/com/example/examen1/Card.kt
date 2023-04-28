package com.example.examen1

class Card {
    private var valor = 0
    private var figura: String? = null

    constructor() {
        valor = 0
        figura = "t"
    }

    constructor(valor: Int, figura: String?) {
        // initialise instance variables
        this.valor = valor
        this.figura = figura
    }

    override fun toString(): String {
        val valorC: String
        valorC = when (valor) {
            1 -> "14"

            else -> valor.toString() + ""
        }
        return figura + valorC
    }

    fun setValor(valor: Int) {
        this.valor = valor
    }

    fun getValor(): Int {
        if (valor == 1) return 14
        return valor
    }

    fun getPuntos(): Int {
        if(valor == 1){
            return 11
        } else if( valor > 10){
            return 10
        }
        return valor
    }

    fun setFigura(figura: String?) {
        this.figura = figura
    }

    fun getFigura(): String? {
        return figura
    }
}