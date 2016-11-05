# MyFilmDatabase
Pràctica IDI 2016-2017. Administració de pel·lícules.

## Enunciat Pràctica ProjecteLab
La pràctica que s’ha de desenvolupar consisteix en realitzar una aplicació relativament senzilla a partir d'un
esquelet donat i que requerirà que esmerceu esforços en **garantir la seva usabilitat**. Per això, caldrà que us
concentreu en elements com: **disseny de la interfície** (elements a posar, mides dels widgets, elecció de les
fonts dels colors, etc.), **disseny de la interacció** (flux de treball, nombre de clics, temps requerit, facilitat
per desfer canvis...), **robustesa**, **correcció**, etc. **Aquest elements es valoraran especialment a l'hora de
corregir la pràctica**.

La pràctica que teniu assignada apareix en un llistat al racó on s'indica quin esquelet heu de fer servir i
quines tasques heu de realitzar. **L'assignació s'ha generat de forma pseudo-aleatòria i no es pot canviar**.

En aquest document us adjuntem l’enunciat de les dues pràctiques. Cada pràctica té dos blocs de tasques i
cada estudiant de la parella haurà de fer les tasques del bloc que li toqui. La pràctica també tindrà
associada una senzilla feina de grup en la que haureu de treballar els dos membres de la parella.

**Qualsevol pregunta que tingueu, dirigiu-la als vostres professors de laboratori, però abans llegiu-vos tota
la documentació que us proporcionem.**

A partir del moment en que teniu la pràctica assignada, podeu començar a realitzar la seva implementació.
Us aconsellem, però, que abans de posar-vos a mirar l'esquelet de la pràctica que us ha tocat feu algun
exercici bàsic de programació en Android (com per exemple el *Mile/Km converter*).

### Normativa de lliurament de les pràctiques
La pràctica és **en parella**. Cada membre de la parella s'ha de responsabilitzar de la seva part.

Qualsevol còpia que es detecti, tal com diu la normativa, donarà lloc a un 0 de l'assignatura!

Les pràctiques han de partir de l'esquelet corresponent que us proporcionem i no han de necessitar
plugins ni llibreries de tercers. Si s’utilitza codi de tercers, heu de demanar-ho al professor de laboratori i
s’ha de deixar clar a la documentació la raó per fer-ho.

Els esquelets els podeu trobar al directori /assig/idi/android i es corresponen amb les següents
pràctiques:
- Pràctica P1: Fitxer MyFilmDatabase.tgz
- Pràctica P2: Fitxer MyBookDatabase.tgz

L’entrega de les pràctiques serà **fins el dia 11 de gener** via el Racó. Us aconsellem que no espereu a l'últim
moment per fer el lliurament, car **NO S'ACCEPTARAN LLIURAMENTS FORA DE TERMINI sota cap concepte**.

**L'assignació de pràctica i grup de tasques no es pot canviar**. **La realització d'una pràctica i/o tasques que
no siguin les assignades implicarà la no avaluació d'aquesta pràctica**.

Hi ha **2 lliuraments** a fer:
1. *Lliurament d'equip*: El **_codi font de l'aplicació_** juntament amb l'**apk** que ha de ser **lliurat pel
membre de la parella que fa el bloc de tasques A**.
◦ Aquest lliurament ha de ser únic per equip de pràctiques i el nom del fitxer a lliurar ha de
ser *<id-equip>P<num>.tar.gz*. Per exemple si l'equip és And119 i els ha tocat la pràctica 1
(de pel·lícules), el nom del fitxer ha de ser **And119P1.tar.gz**
2. *Lliurament individual*: Un **breu informe** que ha de ser lliurat per cada estudiant de forma
**individual**.
- Aquest informe ha d'incloure:
 - Una breu explicació de cadascuna de les 3 tasques implementades per l'estudiant
(màxim una pàgina per tasca). Cal que expliqueu breument la interacció usada en
la tasca.
 - Breu comentari (màxim una pàgina) valorant l'experiència de treball en equip amb
el vostre company de pràctica. A més podeu descriure les diverses problemàtiques
amb que us heu trobat.
- El nom del fitxer a lliurar ha de ser *<id-equip>P<num><A/B>.tar.gz*. Per exemple si l'equip
és And119, els ha tocat la pràctica 1 (de pel·lícules) i a l'estudiant li ha tocat fer el bloc B de
tasques, el nom del fitxer ha de ser **And035P2B.tar.gz**

**Qualsevol incompliment d'aquestes normes d'entrega suposarà una significativa penalització en la nota
final de la pràctica**.

### Avaluació
L'aplicació ha de ser plenament funcional utilitzant l’SDK d’Android i utilitzant una màquina virtual d'un
**Nexus 4 amb versió d’Android 5.1 i resolució de 768x1280**. La pràctica s'haurà de poder executar en
l'emulador Genymotion que teniu instal·lat a les màquines del laboratori (iniciant el sistema en Linux) i
usant la màquina virtual esmentada. **Si no es compleix aquesta condició, la pràctica no serà avaluada**.

L'avaluació de la pràctica es farà **de manera individual** tenint en compte els següents conceptes:
- valoració de les **3 tasques 75%**
- valoració del **treball de grup i l'informe 25%**

L’avaluació té en compte la usabilitat i correctesa de l’aplicació, així com l'existència de tot allò que és
necessari i sempre ha d'estar en qualsevol aplicació (com ara el *nom* o la *icona*).

### Enunciats de la Pràctica a realitzar

#### P1 - Administració de pel·lícules

L'esquelet d'aquesta pràctica permet gestionar una base de dades de pel·lícules (altes, baixes,
modificacions i consultes). La interfície és molt bàsica i no facilita la seva gestió.

La base de dades està composta per una única taula amb els camps següents:

  *Títol, País, Any d'estrena, Director/a, Protagonista, Nota de les crítiques (0...10).*

Inicialment l'aplicació s'ha d'engegar amb la informació ja introduïda de quatre pel·lícules amb les seves
respectives dades.

**Bloc de tasques A**:
- Fer una pantalla de visualització de totes les dades amb *RecyclerView* ordenat per any d'estrena.
- Fer una pantalla per donar d'alta elements de la base de dades.
- Afegir un procediment d'esborrat que es pugui accedir des de la llista d'elements de l'aplicació o
bé des d'una cerca per títol.

**Bloc de tasques B**:
- Fer un menú de l'aplicació transformant la vista inicial en una *Navigation Drawer Activity*. La vista
principal ha de ser una vista amb la llista de títols de les pel·lícules ordenada per títol.
- Fer una visualització que permeti cercar i mostrar els títols de les pel·lícules en les quals hi
participa un determinat actor/actriu.
- Procediment per modificar la nota que ha rebut a les crítiques.

Bloc de tasques comuns:
- Fer un *help* de l'aplicació.
- Fer un *about*.
- Fer que l'aplicació sigui consistent tal i com s'explica a l'assignatura.
- Assegurar-se que l'aplicació es comporta bé en mòbil, aprofitant l'espai limitat de la pantalla i
minimitzant el nombre de *clicks* per a la realització de les interaccions.

**Per a la realització de les tasques que cadascú té assignades no és necessari modificar el disseny de la base
de dades de l'esquelet.**
