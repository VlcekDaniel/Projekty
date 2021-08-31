#pragma once
#ifndef KANDIDAT_H
#define KANDIDAT_H
#include "enums.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
typedef struct kandidat {
	int id;
	char jmeno[50];
	enum OBOR obor;
	char tel[50];
	char mail[50];
	char jazyky[50];
	struct kandidat* dalsi;
} stKandidat;

stKandidat* vytvorKandidata(int id, char* jmeno,
	enum OBOR obor, char* tel, char* mail, char* jazyky);
// vrací adresu dynamické alokace kandidáta, ukazatel 
//dalsi se nastaví na NULL

void vypisKandidata(stKandidat* kandidat);
//vypíše kandidáta na obrazovku

#endif // !KANDIDAT_H