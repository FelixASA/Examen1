package com.example.examen1


class Jugador() {
    private var mano: ArrayList<Card?>? = null

    init {
        mano = ArrayList<Card?>(5)
    }

    fun llenarMano(deck: Deck) {
        mano = deck.quitarCartas() as ArrayList<Card?>?
    }

    fun recibirCartas(cartas: ArrayList<Card?>?) {
        mano = cartas
    }

    fun pedirCarta(carta: Card?) {
        mano!!.add(carta)
    }

    fun recibirCarta(deck: Deck) {
        mano?.add(deck.quitarCarta())
    }

    fun cantidadCartas(): Int {
        return mano!!.size
    }

    fun mostrarMano(): String? {
        var cartas = ""
        for (carta in mano!!) {
            cartas += "${carta.toString()},"
        }
        return cartas
    }

    fun mostrarCartaMasAlta() {
        var cartaMasAlta = mano!![0]
        for (i in 1 until mano!!.size) {
            if (mano!![i]!!.getValor() > cartaMasAlta!!.getValor()) {
                cartaMasAlta = mano!![i]
            }
        }
        println("Su carta más alta es: $cartaMasAlta")
    }

    fun sonCartasMismaFigura(): Boolean {
        var mismaFigura = true
        var card = mano!![0]
        for (i in 1 until mano!!.size) {
            if (mano!![i]!!.getFigura() != card!!.getFigura()) {
                mismaFigura = false
            }
            card = mano!![i]
        }
        return mismaFigura
    }

    fun getMano(): ArrayList<Card?>? {
        return mano
    }

    fun setMano(mano: ArrayList<Card?>?) {
        this.mano = mano
    }

    fun hayBlackjack(): Boolean {
        var hayAs = false
        var hayMono = false
        if (mano!!.size == 2) {
            for (card in mano!!) {
                if (card!!.getValor() == 1) {
                    hayAs = true
                }
                if (card.getValor() >= 10) {
                    hayMono = true
                }
            }
        }
        return hayAs && hayMono
    }

    fun obtenerPuntaje(): Int {
        var puntaje = 0
        var numAces = 0
        for(carta: Card? in mano!!) {
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
        mano?.clear()
    }
}