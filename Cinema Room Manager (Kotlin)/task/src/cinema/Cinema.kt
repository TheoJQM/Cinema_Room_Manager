package cinema

private val cinemaRoom = mutableListOf<MutableList<String>>()
private var purchasedTickets : Int = 0
private var nbSeats: Int = 0
private var income : Int = 0
private var listSeats = mutableListOf<Pair<Int,Int>>()

private const val REGULARPRICE = 10
private const val SMALLPRICE = 8

fun main() {

    // Get the number of rows and seats per row
    println("Enter the number of rows:")
    val nbRows = readln().toInt()
    println("Enter the number of seats in each row:")
    val nbSeatsPerRow = readln().toInt()
    nbSeats = nbRows * nbSeatsPerRow

    // Create the cinema room
    for ( row in 1..nbRows) {
        val rowList = mutableListOf<String>()
        rowList.add(row.toString())
        repeat(nbSeatsPerRow) {
            rowList.add("S")
        }
        cinemaRoom.add(rowList)
    }

    // Print the menu
    println("")
    printMenu()

    var userChoice = readln().toInt()
    while (userChoice != 0) {
        when(userChoice) {
            1 -> printCinema(nbSeatsPerRow)
            2 -> buyTicket(nbSeatsPerRow)
            3 -> printStatistics(nbSeatsPerRow)
        }

        printMenu()
        userChoice = readln().toInt()
    }
}

fun printMenu() {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun printCinema(nbSeatsPerRow: Int) {
    println("")
    println("Cinema:")
    print("  ")
    repeat(nbSeatsPerRow) {
        print("${it + 1} ")
    }
    println("")
    for (row in 0 until cinemaRoom.size) {
        println(cinemaRoom[row].joinToString(" "))
    }
    println("")
}

fun buyTicket(nbSeatsPerRow: Int) {
    val nbSeatsSmallRoom = 60
    val nbRows = cinemaRoom.size

    //Get the user seat
    println("")
    println("Enter a row number:")
    var userRow = readln().toInt()
    println("Enter a seat number in that row:")
    var userSeat = readln().toInt()

    // Check if the seat is already taken
    while (listSeats.contains(Pair(userRow, userSeat)) || userRow > nbRows || userSeat > nbSeatsPerRow) {
        if (userRow > nbRows || userSeat > nbSeatsPerRow) println("\nWrong input!\n") else println("\nThat ticket has already been purchased!\n")
        println("Enter a row number:")
        userRow = readln().toInt()
        println("Enter a seat number in that row:")
        userSeat = readln().toInt()
    }

    listSeats.add(Pair(userRow,userSeat))

    val price : Int
    if (nbSeats <= nbSeatsSmallRoom) {
        price = REGULARPRICE
    } else {
        if (nbRows % 2 == 0) {
            price = if (userRow <= nbRows / 2) REGULARPRICE else SMALLPRICE
        } else {
            price = if (userRow <=(nbRows -1) / 2) REGULARPRICE else SMALLPRICE

        }
    }
    cinemaRoom[userRow-1][userSeat] = "B"
    purchasedTickets++
    income += price
    println("\nTicket price: \$$price\n")

}

fun printStatistics(nbSeatsPerRow: Int) {
    val percentage = purchasedTickets.toDouble() * 100/ nbSeats.toDouble()
    val formatPercentage = "%.2f".format(percentage)
    val totalIncome = totalIncome(nbSeatsPerRow)
    println("")
    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: $formatPercentage%")
    println("Current income: \$$income")
    println("Total income: \$$totalIncome\n")
}

fun totalIncome(nbSeatsPerRow: Int) : Int {
    val nbSeatsSmallRoom = 60
    val nbRows = cinemaRoom.size

    if (nbSeats <= nbSeatsSmallRoom) {
        return nbSeats * REGULARPRICE
    } else {
        if (nbRows % 2 == 0) {
            return (nbRows / 2 * nbSeatsPerRow * REGULARPRICE) + (nbRows / 2 * nbSeatsPerRow * SMALLPRICE)
        } else {
            return ((nbRows - 1) / 2 * nbSeatsPerRow * REGULARPRICE) + ((nbRows + 1) / 2 * nbSeatsPerRow * SMALLPRICE)
        }
    }
}