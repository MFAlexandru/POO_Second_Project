# Proiect Energy System Etapa 2

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Margarit Alexandru, 322CA

## Rulare teste

Clasa Test#main
  * ruleaza solutia pe testele din checker/, comparand rezultatele cu cele de referinta
  * ruleaza checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations

## Implementare

### Entitati

Customer - reprezinta consumatorul si are datle sale.

Distributor - are datele distribuitorului.

Producer - tine datele producatorului.

ProducerObserver - desi Producer nu extinde Observable el este un observable
		iar aceasta clasa are riolul de a observa producatorii.

Fiecare din cele de mai sus au si Factory-uri care sunt self-explanatory.

Avem si mai multe clase in directorul format ce ajuta la afisarea datelor

Pe langa acestea avem si EnergyStrategy care este o interfata pentru stategiile
de alegere a energiei.(PriceStrategy, QuantityStrategy, GreenStrategy)

### Flow

Intr-o runda normala:

	1. Se citeste update-ul pentru Distributors si Consumers folosind JSONObjects si 
		Arrays.

	2. Aplicam Schimbarile tuturor membrilor care sunt retinuti in Array-uri

	3. Se inregistreaza Monthly updates

	4. Consumatorii cauta distribuitori si platesc taxe

	5. Distribuitorii platesc taxe dupa care citesc schimbarile si folosesc 
		observerii pentru a determina care din ei au nevoie de un nou producer.

### Diff fata de runda 1

Avem cele doua noi patternuri de Obsever si Strategy care au fost folosite la entitati.
Am folosit Observerul pentru a identifica cand are un Distribuitor nevoie de update
si Strategy pentru dioferitele strategi de a alege Producatori.

### Git
	MFAlexandru
	Repoul este Tema_POO_2
	link : https://github.com/MFAlexandru/Tema_POO_2#rulare-teste