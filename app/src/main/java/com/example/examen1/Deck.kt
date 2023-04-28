package com.example.examen1
import com.example.examen1.Card

class Deck// Inicializar variables
/**
 * Constructor for objects of class Deck
 */() {
    private var deck: ArrayList<Card?>? = null

    init {
        deck = ArrayList<Card?>(52)
        for (i in 1..13) {
            deck!!.add(Card(i, "d"))
            deck!!.add(Card(i, "p"))
            deck!!.add(Card(i, "c"))
            deck!!.add(Card(i, "t"))
        }
    }

    fun iniciarBaraja(){
        for (i in 1..13) {
            deck!!.add(Card(i, "d"))
            deck!!.add(Card(i, "p"))
            deck!!.add(Card(i, "c"))
            deck!!.add(Card(i, "t"))
        }
    }

    fun mostrarCartas() {
        val array = arrayOfNulls<Card>(
            deck!!.size
        )
        deck!!.toArray(array)
        println("\u000c")
        var i = 0
        for (item in array) {
            println(" " + item.toString())
            i++
        }
    }

    fun revolverBaraja() {
        var numero: Int
        for (i in deck!!.indices) {
            numero = (Math.random() * (deck!!.size - 1)).toInt()
            val cartaAux = deck!![i]
            deck!![i] = deck!![numero]
            deck!![numero] = cartaAux
        }
    }

    fun quitarCarta(): Card? {
        val i = deck!!.size - 1
        val carta = deck!![i]
        deck?.removeAt(i)
        return carta
    }

    fun quitarCarta(i: Int): Card? {
        val carta = deck!![i]
        deck!!.removeAt(i)
        return carta
    }

    fun quitarCartas(): ArrayList<*>? {
        val mano: ArrayList<Card?>
        mano = ArrayList<Card?>(5)
        for (i in 0..4) {
            val c = deck!!.size - 1
            mano.add(deck!![c])
            deck!!.removeAt(c)
        }
        return mano
    }

    fun limpiarBaraja(){
        deck?.clear()
    }
}