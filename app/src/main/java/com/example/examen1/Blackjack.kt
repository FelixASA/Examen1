package com.example.examen1

class Blackjack() {
    val crupier = Crupier()
    val baraja = Deck()
    val jugador = Jugador()

    init {
        iniciarJuego()
    }



    fun iniciarJuego(){
        crupier.barajearMazo(baraja)
        for (i in 0..1){
            jugador.recibirCarta(baraja)
            crupier.recibirCarta(baraja)
        }
    }

    fun obtenerCartasJugador(): ArrayList<Card?>? {
        return jugador.getMano()
    }
    fun obtenerCartasCrupier(): ArrayList<Card?>? {
        return crupier.getCartas()
    }

    fun pedirCarta(){
        jugador.recibirCarta(baraja)
    }

    fun darCartaCrupier(){
        crupier.recibirCarta(baraja)
    }

    fun reiniciarJuego(){
        baraja.limpiarBaraja()
        jugador.limpiarMano()
        crupier.limpiarMano()
        baraja.iniciarBaraja()
        iniciarJuego()
    }

    fun juego(){
        crupier.barajearMazo(baraja)
        for (i in 0..1){
            jugador.recibirCarta(baraja)
            crupier.recibirCarta(baraja)
        }
        var sumaJugador: Int
        var sumaCrupier: Int
        println("Crupier: " + crupier.getCartas()?.get(0).toString());
        do {
            sumaJugador = 0;
            sumaCrupier = 0;
            for (card: Card? in jugador.getMano()!!){
                if (card?.getValor()!! > 10){
                    sumaJugador += 10
                } else {
                    sumaJugador += card.getValor()
                }
            }

            for (card: Card? in crupier.getCartas()!!) {
                if (card?.getValor()!! > 10) {
                    sumaCrupier += 10;
                } else {
                    sumaCrupier += card.getValor();
                }
            }
            if (jugador.hayBlackjack() || crupier.hayBlackjack()) {
                if (jugador.hayBlackjack()) sumaJugador = 21;
                if (crupier.hayBlackjack()) sumaCrupier = 21;
                break;
            }

            var opcion: String = "";
            if (sumaJugador < 21 && jugador.getMano()!!.size < 6) {
                println("Tus cartas son:\n" + jugador.mostrarMano());
                println("Pedir carta?(Si/No)");
                opcion = "No"
                if (opcion.equals("Si")) {
                    jugador.recibirCarta(baraja);
                    if (sumaCrupier < 21 && crupier.getCartas()!!.size < 5) {
                        crupier.recibirCarta(baraja);
                    }
                }

            }
        } while (opcion == "Si");
        println("Tus cartas son:\n" + jugador.mostrarMano());
        println("Cartas Crupier: ");
        for (card: Card? in crupier.getCartas()!!) {
            println(card.toString());
        }
        println("\nCrupier: $sumaCrupier");
        println("\nJugador: $sumaJugador");

        if ((sumaJugador > 21 && sumaCrupier > 21) || sumaJugador == sumaCrupier) {
            System.out.println("Empate!");
        } else if (sumaJugador <= 21 && sumaJugador > sumaCrupier || sumaCrupier > 21) {
            System.out.println("Felicidades, ha ganado!");
        } else if (sumaJugador > 21 || sumaJugador < sumaCrupier) {
            System.out.println("Usted ha perdido!");
        }
    }
}