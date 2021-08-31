#pragma once
#ifndef POZICE_H
#define POZICE_H
#include "enums.h"
#include <stdio.h>
typedef struct pozice {
	int id;
	char pozice[100];
	char popis[50];
	char pozadavky[50];
	char nabidka[50];
	char jazyky[50];
	float maxPlat;
	enum KRAJ kraj;
	struct pozice* dalsi;
} stPozice;

stPozice* vytvorPozici(int id, char* pozice, enum KRAJ kraj,
	char* popis, char* pozadavky, char*
	nabidka, char* jazyky, float maxPlat);
	// vrací adresu dynamické alokace pozice, 
	//ukazatel dalsi senastaví na NULL

	void vypisPozici(stPozice* pozice); 
	//vypíše pozice na obrazovku

#endif // !POZICE_H