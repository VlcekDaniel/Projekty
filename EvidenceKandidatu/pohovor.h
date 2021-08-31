#pragma once
#ifndef POHOVOR_H
#define POHOVOR_H
#include "kandidat.h"
#include "pozice.h"
#include "enums.h"
typedef struct pohovor {
	int id;
	stKandidat* kandidat;
	stPozice* pozice;
	enum VYSLEDEK_POHOVORU vysledek;
} stPohovor;

stPohovor* vytvorPohovor(stKandidat* kandidat, stPozice* idPozice);
// vrac� adresu dynamick�alokace pozice, stav je nastaven na 
//�nenastaveno�, id pohovoru roste automaticky(1, 2, ..

void vypisPohovor(stPohovor* pohovor);
		//vyp�e pohovor(v�etn� kandid�ta a pozice)

#endif // !POHOVOR_H