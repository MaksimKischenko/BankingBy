package com.production.smsbankinganalitics.view_models.data.repositories

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExampleSmsRepository {
    companion object {
        val noParsedSmsMap: Map<String, LocalDateTime> = mapOf(
            "Zachisleno 1063.00 BYN na Visa#7777; EXAMPLE BANK>MINSK>BY; MCC 0; 25.05.2024 10:20:20; Dostupno: 1203.63" to LocalDateTime.parse("25.05.2024 10:20:20", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
            "Oplata v internete 12.60 BYN Visa#7777; UBER>INTERNET>BY; MCC 4121; 26.05.2024 09:12:07; Dostupno: 1190.4" to LocalDateTime.parse("26.05.2024 09:12:07", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
            "Oplata 2.90 BYN Visa#7777; SHOP \"SANTA-272\" BAPB>MINSK>BY; MCC 5411; 27.05.2024 12:49:31; Dostupno: 1187.5" to LocalDateTime.parse("27.05.2024 12:49:31", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
            "Oplata 1.36 BYN Visa#7777; SHOP \"SANTA-272\" BAPB>MINSK>BY; MCC 5411; 27.05.2024 14:31:27; Dostupno: 1185.87" to LocalDateTime.parse("27.05.2024 14:31:27", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
            "Oplata 8.71 BYN Visa#7777; GIPPO TRADE CENTRE>MINSK>BY; MCC 5411; 28.05.2024 18:51:37; Dostupno: 1177.16" to LocalDateTime.parse("28.05.2024 18:51:37", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        )
    }
}