package com.mertyigit0.vocabcards.data.repository

import android.content.Context
import com.mertyigit0.vocabcards.data.local.PrefsHelper
import com.mertyigit0.vocabcards.data.model.Word

class WordRepository(private val context: Context) {

    private val allWords = listOf(
        Word("Apple", "Elma \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDDF9\uD83C\uDDF7", "Apfel", "Mela", "Manzana", "Pomme"),
        Word("Book", "Kitap \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDCD6", "Buch 🇩🇪", "Libro 🇪🇸", "Libro 🇲🇽", "Livre 🇫🇷"),
        Word("Car", "Araba \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDE97", "Auto 🇩🇪", "Macchina 🇮🇹", "Coche 🇪🇸", "Voiture 🇫🇷"),
        Word("Glass", "Bardak \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF77", "Glas 🇩🇪", "Bicchiere 🇮🇹", "Vaso 🇪🇸", "Verre 🇫🇷"),
        Word("Window", "Pencere \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDDFD", "Fenster 🇩🇪", "Finestra 🇮🇹", "Ventana 🇪🇸", "Fenêtre 🇫🇷"),
        Word("Door", "Kapı \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDD90", "Tür 🇩🇪", "Porta 🇮🇹", "Puerta 🇪🇸", "Porte 🇫🇷"),
        Word("Street", "Sokak \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDEB6", "Straße 🇩🇪", "Strada 🇮🇹", "Calle 🇪🇸", "Rue 🇫🇷"),
        Word("City", "Şehir \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDFD9", "Stadt 🇩🇪", "Città 🇮🇹", "Ciudad 🇪🇸", "Ville 🇫🇷"),
        Word("Map", "Harita \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDCCD", "Karte 🇩🇪", "Mappa 🇮🇹", "Mapa 🇪🇸", "Carte 🇫🇷"),
        Word("Bag", "Çanta \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDC5C", "Tasche 🇩🇪", "Borsa 🇮🇹", "Bolsa 🇪🇸", "Sac 🇫🇷"),
        Word("Shoes", "Ayakkabı \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDC5F", "Schuhe 🇩🇪", "Scarpe 🇮🇹", "Zapatos 🇪🇸", "Chaussures 🇫🇷"),
        Word("Shirt", "Gömlek \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDC55", "Hemd 🇩🇪", "Camicia 🇮🇹", "Camisa 🇪🇸", "Chemise 🇫🇷"),
        Word("Pants", "Pantolon \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDC56", "Hose 🇩🇪", "Pantaloni 🇮🇹", "Pantalones 🇪🇸", "Pantalon 🇫🇷"),
        Word("Lamp", "Lamba \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDCA1", "Lampe 🇩🇪", "Lampada 🇮🇹", "Lámpara 🇪🇸", "Lampe 🇫🇷"),
        Word("Clock", "Saat \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDD50", "Uhr 🇩🇪", "Orologio 🇮🇹", "Reloj 🇪🇸", "Horloge 🇫🇷"),
        Word("Mirror", "Ayna \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDD26", "Spiegel 🇩🇪", "Specchio 🇮🇹", "Espejo 🇪🇸", "Miroir 🇫🇷"),
        Word("Fan", "Vantilatör \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDCA8", "Ventilator 🇩🇪", "Ventilatore 🇮🇹", "Ventilador 🇪🇸", "Ventilateur 🇫🇷"),
        Word("Refrigerator", "Buzdolabı \uD83C\uDDF9\uD83C\uDDF7", "\uD83E\uDD6B", "Kühlschrank 🇩🇪", "Frigorifero 🇮🇹", "Refrigerador 🇪🇸", "Réfrigérateur 🇫🇷"),
        Word("Sink", "Lavabo \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDEAB", "Spüle 🇩🇪", "Lavello 🇮🇹", "Fregadero 🇪🇸", "Évier 🇫🇷"),
        Word("Toothbrush", "Diş Fırçası", "\uD83D\uDDF2", "Zahnbürste 🇩🇪", "Spazzolino 🇮🇹", "Cepillo de dientes 🇪🇸", "Brosse à dents 🇫🇷"),
        Word("Soap", "Sabun \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDEAA", "Seife 🇩🇪", "Sapone 🇮🇹", "Jabón 🇪🇸", "Savon 🇫🇷"),
        Word("Towel", "Havlu \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDD0A", "Handtuch 🇩🇪", "Asciugamano 🇮🇹", "Toalla 🇪🇸", "Serviette 🇫🇷"),
        Word("Conditioner", "Saç Kremi \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDC92", "Haarspülung 🇩🇪", "Balsamo 🇮🇹", "Acondicionador 🇪🇸", "Après-shampooing 🇫🇷"),
        Word("Cheese", "Peynir \uD83C\uDDF9\uD83C\uDDF7", "\uD83E\uDDC0", "Käse 🇩🇪", "Formaggio 🇮🇹", "Queso 🇪🇸", "Fromage 🇫🇷"),
        Word("Egg", "Yumurta \uD83C\uDDF9\uD83C\uDDF7", "\uD83E\uDD5A", "Ei 🇩🇪", "Uovo 🇮🇹", "Huevo 🇪🇸", "Œuf 🇫🇷"),
        Word("Chicken", "Tavuk \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF57", "Hähnchen 🇩🇪", "Pollo 🇮🇹", "Pollo 🇪🇸", "Poulet 🇫🇷"),
        Word("Fish", "Balık \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDC1F", "Fisch 🇩🇪", "Pesce 🇮🇹", "Pescado 🇪🇸", "Poisson 🇫🇷"),
        Word("Meat", "Et \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF56", "Fleisch 🇩🇪", "Carne 🇮🇹", "Carne 🇪🇸", "Viande 🇫🇷"),
        Word("Rice", "Pirinç \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF7D", "Reis 🇩🇪", "Riso 🇮🇹", "Arroz 🇪🇸", "Riz 🇫🇷"),
        Word("Fruit", "Meyve \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF4C", "Frucht 🇩🇪", "Frutta 🇮🇹", "Fruta 🇪🇸", "Fruit 🇫🇷"),
        Word("Juice", "Meyve Suyu \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF4E", "Saft 🇩🇪", "Succo 🇮🇹", "Jugo 🇪🇸", "Jus 🇫🇷"),
        Word("Tea", "Çay \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF75", "Tee 🇩🇪", "Tè 🇮🇹", "Té 🇪🇸", "Thé 🇫🇷"),
        Word("Money", "Para \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDCB0", "Geld 🇩🇪", "Soldi 🇮🇹", "Dinero 🇪🇸", "Argent 🇫🇷"),
        Word("One", "Bir \uD83C\uDDF9\uD83C\uDDF7", "\u0031\u20E3", "Eins 🇩🇪", "Uno 🇮🇹", "Uno 🇪🇸", "Un 🇫🇷"),
        Word("Two", "İki \uD83C\uDDF9\uD83C\uDDF7", "\u0032\u20E3", "Zwei 🇩🇪", "Due 🇮🇹", "Dos 🇪🇸", "Deux 🇫🇷"),
        Word("Cat", "Kedi \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDC31", "Katze 🇩🇪", "Gatto 🇮🇹", "Gato 🇪🇸", "Chat 🇫🇷"),
        Word("Cow", "İnek \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDC2C", "Kuh 🇩🇪", "Mucca 🇮🇹", "Vaca 🇪🇸", "Vache 🇫🇷"),
        Word("Banana", "Muz \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF4C", "Banane 🇩🇪", "Banana 🇮🇹", "Plátano 🇪🇸", "Banane 🇫🇷"),
        Word("Strawberry", "Çilek \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF53", "Erdbeere 🇩🇪", "Fragola 🇮🇹", "Fresa 🇪🇸", "Fraise 🇫🇷"),
        Word("Table", "Masa \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDCBB", "Tisch 🇩🇪", "Tavolo 🇮🇹", "Mesa 🇪🇸", "Table 🇫🇷"),
        Word("Pineapple", "Ananas \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF4D", "Ananas 🇩🇪", "Ananas 🇮🇹", "Piña 🇪🇸", "Ananas 🇫🇷"),
        Word("Watermelon", "Karpuz \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF49", "Wassermelone 🇩🇪", "Anguria 🇮🇹", "Sandía 🇪🇸", "Pastèque 🇫🇷"),
        Word("Shark", "Köpekbalığı \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDC1F", "Hai 🇩🇪", "Squalo 🇮🇹", "Tiburón 🇪🇸", "Requin 🇫🇷"),
        Word("Sun", "Güneş \uD83C\uDDF9\uD83C\uDDF7", "\u2600️", "Sonne 🇩🇪", "Sole 🇮🇹", "Sol 🇪🇸", "Soleil 🇫🇷"),
        Word("Moon", "Ay \uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDF19", "Mond 🇩🇪", "Luna 🇮🇹", "Luna 🇪🇸", "Lune 🇫🇷"),
        Word("Star", "Yıldız \uD83C\uDDF9\uD83C\uDDF7", "\u2B50", "Stern 🇩🇪", "Stella 🇮🇹", "Estrella 🇪🇸", "Étoile 🇫🇷"),
        Word("Cloud", "Bulut \uD83C\uDDF9\uD83C\uDDF7", "\u2601️", "Wolke 🇩🇪", "Nuvola 🇮🇹", "Nube 🇪🇸", "Nuage 🇫🇷"),
        Word("Key", "Anahtar \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDD11", "Schlüssel 🇩🇪", "Chiave 🇮🇹", "Llave 🇪🇸", "Clé 🇫🇷"),
        Word("Telephone", "Telefon \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDCF2", "Telefon 🇩🇪", "Telefono 🇮🇹", "Teléfono 🇪🇸", "Téléphone 🇫🇷"),
        Word("Television", "Televizyon \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDCFA", "Fernseher 🇩🇪", "Televisore 🇮🇹", "Televisión 🇪🇸", "Télévision 🇫🇷"),
        Word("Camera", "Kamera \uD83C\uDDF9\uD83C\uDDF7", "\uD83D\uDCF7", "Kamera 🇩🇪", "Macchina fotografica 🇮🇹", "Cámara 🇪🇸", "Appareil photo 🇫🇷")
    )


    fun getAllWords(): List<Word> = allWords

    fun getLearnedWords(): List<Word> {
        val learnedWordsSet = PrefsHelper.getLearnedWords(context)
        return allWords.filter { learnedWordsSet.contains(it.english) }
    }

    fun getRemainingWords(): List<Word> {
        val learnedWordsSet = PrefsHelper.getLearnedWords(context)
        return allWords.filter { !learnedWordsSet.contains(it.english) }
    }
}
