#pragma once
#ifndef AGENDA_H
#define AGENDA_H
#include "kandidat.h"
#include "pozice.h"
#include "pohovor.h"
#include "enums.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h> 

void nactiSeznamKandidatu(char* nazevSouboru);
// postupnì naète jednotlivé kandidáty ze souboru
//a popøidává je za sebe do lineárního seznamu

void nactiSeznamPozic(char* nazevSouboru);
// postupnì naète jednotlivé pozice ze souboru a
//popøidává je za sebe do lineárního seznamu

void vypisSeznamKandidatu(); 
// vypíše kandidáty se seznamu na každý øádek jednoho (používá
//opakovanì funkci pro výpis jednoho kandidáta)

void pridejKandidata(stKandidat* kandidat);
// pøidá kandidáta na konec lineárního seznamu

stKandidat* odeberKandidataZeSeznamu(int cisloKandidata);
// odebere kandidata z lin. seznamu podle
//jeho èísla, vrací ho, a následnì dealokuje

stKandidat* najdiKandidataZeSeznamu(int cisloKandidata);
// najde kandidata z lin. seznamu podle jeho èísla

void zrusSeznamKandidatu();
// dealokuje celý lin. seznam(nestaèí jen první/aktuální 
// nastavit na NULL)

void vypisSeznamPozic();
// vypíše pozice se seznamu na každý øádek jednoho 
//(používá opakovanì funkci pro výpis jedné pozice)

void pridejPozice(stPozice* pozice);
// pøidá pozici na konec lineárního seznamu

stPozice* odeberPoziciZeSeznamu(int cisloPozice);
//odebere pozici z lin. sez. jeho èísla, vrací ji,
// a následnì dealokuje

stPozice* najdiPoziciZeSeznamu(int cisloPozice);
//najde pozici z lin. sez. podle èísla

void zrusSeznamPozic();
//dealokuje celý lin.sez.(nestaèí jen první/akt nastavit na NULL)

void alokujPolePohovoru();
//alokuje pole ukazatelù pro pohovory, výchozí dimenze=10, v pøípadì
//hrozícího pøeteèení se pole realokuje

void pridejPohovor(stPohovor* pohovor);
//pøidá pohovor do pole na

void zmenStavPohovor(int id, enum STAV_POHOVORU vysledek);
//zmìní stav pohovoru

void vypisPohovory();
//vypíše všechny pohovory

void zrusPohovory();
#endif // !AGENDA_H