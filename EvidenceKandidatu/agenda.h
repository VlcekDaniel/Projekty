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
// postupn� na�te jednotliv� kandid�ty ze souboru
//a pop�id�v� je za sebe do line�rn�ho seznamu

void nactiSeznamPozic(char* nazevSouboru);
// postupn� na�te jednotliv� pozice ze souboru a
//pop�id�v� je za sebe do line�rn�ho seznamu

void vypisSeznamKandidatu(); 
// vyp�e kandid�ty se seznamu na ka�d� ��dek jednoho (pou��v�
//opakovan� funkci pro v�pis jednoho kandid�ta)

void pridejKandidata(stKandidat* kandidat);
// p�id� kandid�ta na konec line�rn�ho seznamu

stKandidat* odeberKandidataZeSeznamu(int cisloKandidata);
// odebere kandidata z lin. seznamu podle
//jeho ��sla, vrac� ho, a n�sledn� dealokuje

stKandidat* najdiKandidataZeSeznamu(int cisloKandidata);
// najde kandidata z lin. seznamu podle jeho ��sla

void zrusSeznamKandidatu();
// dealokuje cel� lin. seznam(nesta�� jen prvn�/aktu�ln� 
// nastavit na NULL)

void vypisSeznamPozic();
// vyp�e pozice se seznamu na ka�d� ��dek jednoho 
//(pou��v� opakovan� funkci pro v�pis jedn� pozice)

void pridejPozice(stPozice* pozice);
// p�id� pozici na konec line�rn�ho seznamu

stPozice* odeberPoziciZeSeznamu(int cisloPozice);
//odebere pozici z lin. sez. jeho ��sla, vrac� ji,
// a n�sledn� dealokuje

stPozice* najdiPoziciZeSeznamu(int cisloPozice);
//najde pozici z lin. sez. podle ��sla

void zrusSeznamPozic();
//dealokuje cel� lin.sez.(nesta�� jen prvn�/akt nastavit na NULL)

void alokujPolePohovoru();
//alokuje pole ukazatel� pro pohovory, v�choz� dimenze=10, v p��pad�
//hroz�c�ho p�ete�en� se pole realokuje

void pridejPohovor(stPohovor* pohovor);
//p�id� pohovor do pole na

void zmenStavPohovor(int id, enum STAV_POHOVORU vysledek);
//zm�n� stav pohovoru

void vypisPohovory();
//vyp�e v�echny pohovory

void zrusPohovory();
#endif // !AGENDA_H