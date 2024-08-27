class Cabina(val id: Int) {
    var numeroLlamadas = 0
    var duracionTotal = 0
    var costoTotal = 0

    fun registrarLlamada(tipo: String, duracion: Int) {
        numeroLlamadas++
        duracionTotal += duracion
        costoTotal += when (tipo) {
            "local" -> duracion * 50
            "larga distancia" -> duracion * 350
            "celular" -> duracion * 150
            else -> 0
        }
    }

    fun reiniciar() {
        numeroLlamadas = 0
        duracionTotal = 0
        costoTotal = 0
    }

    fun mostrarInformacion() {
        println("Cabina $id:")
        println("Número de llamadas realizadas: $numeroLlamadas")
        println("Duración total de llamadas (minutos): $duracionTotal")
        println("Costo total de llamadas (pesos): $costoTotal")
    }
}

fun mostrarConsolidado(cabinas: List<Cabina>) {
    var totalLlamadas = 0
    var totalDuracion = 0
    var totalCosto = 0

    for (cabina in cabinas) {
        totalLlamadas += cabina.numeroLlamadas
        totalDuracion += cabina.duracionTotal
        totalCosto += cabina.costoTotal
    }

    val costoPromedioPorMinuto = if (totalDuracion > 0) totalCosto.toDouble() / totalDuracion else 0.0

    println("Consolidado Total:")
    println("Número total de llamadas realizadas: $totalLlamadas")
    println("Duración total de llamadas (minutos): $totalDuracion")
    println("Costo total en pesos de la línea: $totalCosto")
    println("Costo promedio por minuto: $costoPromedioPorMinuto")
}

fun main() {
    val cabinas = mutableListOf<Cabina>()
    println("Ingrese el número de cabinas:")
    val numeroDeCabinas = readln().toInt()

    for (i in 1..numeroDeCabinas) {
        cabinas.add(Cabina(i))
    }

    while (true) {
        println("\n--- Menú ---")
        println("1. Registrar una llamada de acuerdo con la cabina")
        println("2. Mostrar la información detallada de todas las cabinas")
        println("3. Mostrar un consolidado total de la información de las cabinas")
        println("4. Reiniciar el uso de la cabina telefónica")
        println("5. Salir")
        print("Seleccione una opción: ")
        when (readln().toInt()) {
            1 -> {
                print("Ingrese el número de cabina (1 a $numeroDeCabinas): ")
                val cabinaId = readln().toInt()
                if (cabinaId in 1..numeroDeCabinas) {
                    val cabina = cabinas[cabinaId - 1]
                    print("Tipo de llamada (local, larga distancia, celular): ")
                    val tipo = readln().lowercase().trim()
                    print("Duración de la llamada en minutos: ")
                    val duracion = readln().toInt()
                    cabina.registrarLlamada(tipo, duracion)
                    println("Llamada registrada exitosamente.")
                } else {
                    println("Número de cabina inválido.")
                }
            }
            2 -> {
                println("Mostrando información de todas las cabinas:")
                for (cabina in cabinas) {
                    cabina.mostrarInformacion()
                }
            }
            3 -> mostrarConsolidado(cabinas)
            4 -> {
                print("Ingrese el número de cabina a reiniciar (1 a $numeroDeCabinas): ")
                val cabinaId = readln().toInt()
                if (cabinaId in 1..numeroDeCabinas) {
                    cabinas[cabinaId - 1].reiniciar()
                    println("Cabina $cabinaId reiniciada exitosamente.")
                } else {
                    println("Número de cabina inválido.")
                }
            }
            5 -> {
                println("Saliendo...")
                return
            }
            else -> println("Opción inválida.")
        }
    }
}