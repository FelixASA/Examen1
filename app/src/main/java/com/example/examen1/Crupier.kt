package com.example.examen1

import com.example.examen1.Card


class Crupier() {
    private var cartas: ArrayList<Card?>? = null

    init {
        cartas = ArrayList<Card?>()
    }

    /**
     * Metodo para barajear un mazo
     * @param mazo Mazo que se va a barajear
     */
    fun barajearMazo(mazo: Deck) {
        mazo.revolverBaraja()
    }

    fun recibirCarta(deck: Deck) {
        cartas!!.add(deck.quitarCarta())
    }

    fun getCartas(): ArrayList<Card?>? {
        return cartas
    }

    fun setCartas(cartas: ArrayList<Card?>?) {
        this.cartas = cartas
    }

    fun mostrarMano(): String? {
        var cartas2 = ""
        for (carta in cartas!!) {
            cartas2 += "${carta.toString()},"
        }
        return cartas2
    }

    fun hayBlackjack(): Boolean {
        var hayAs = false
        var hayMono = false
        if (cartas!!.size == 2) {
            for (card in cartas!!) {
                if (card?.getValor() === 1) {
                    hayAs = true
                }
                if (card?.getValor()!! >= 10) {
                    hayMono = true
                }
            }
        }
        return hayAs && hayMono
    }
    fun obtenerPuntaje(): Int {
        var puntaje = 0
        var numAces = 0
        for(carta: Card? in cartas!!) {
            if (carta?.getPuntos() == 11) numAces +=1
            puntaje += carta!!.getPuntos()
        }
        for ( i in 0..numAces){
            if(puntaje > 21 && numAces != 0){
                puntaje -= 10
                numAces--
            }
        }
        return puntaje
    }

    fun limpiarMano(){
        cartas?.clear()
    }
}