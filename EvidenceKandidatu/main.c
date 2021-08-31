#include "agenda.h"
#define _CRTDBG_MAP_ALLOC	
#include <crtdbg.h> 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "enums.h"
#include <stdbool.h>


void help();
bool jeTelefon(char* text);
bool jeMail(char* text);

int counterKandidat = 1;
int counterPozice = 1;
int counterPohovor = 1;

int main(int agrc, char* argv[]) {
		int choice = 0;
		char char_choice[3];
		int temp = 0;
		do {
			help();
			fgets(char_choice, 3, stdin);
			choice = atoi(char_choice);
			

			switch (choice)
			{
				temp = 0;
			case 1:
				do {
				printf("1. Nacti kandidaty \n");
				printf("2. Nacti pozice \n");
				printf("3. Zrus \n");
				fgets(char_choice, 3, stdin);
				choice = atoi(char_choice);
				switch (choice)
				{
				case 1:
					nactiSeznamKandidatu("kandidati.csv");
					printf("* Kandidati byli nacteni \n");
					temp = 1;
					break;
				case 2:
					nactiSeznamPozic("pozice.csv");
					printf("* Pozice byli nacteny \n");
					temp = 1;
					break;
				case 3:
					temp = 1;
					break;
				}
				} while (temp != 1);
				break;
			case 2:
				do {
					temp = 0;
					printf("1. Vypis kandidaty \n");
					printf("2. Vypis pozice \n");
					printf("3. Zrus \n");
					fgets(char_choice, 3, stdin);
					choice = atoi(char_choice);
					switch (choice)
					{
					case 1:
						vypisSeznamKandidatu();
						printf("\n");
						temp = 1;
						break;
					case 2:
						vypisSeznamPozic();
						printf("\n");
						temp = 1;
						break;
					case 3:
						temp = 1;
						break;
					}
				} while (temp != 1);
				break;
			case 3:
			{
				int spatnyKadnidat = 0;
				do {

					if (spatnyKadnidat == 1)
					{
						printf("* Nekorektni udaj opakujte zadavani: \n");
					}
					spatnyKadnidat = 0;
					printf("1. Pridej kandidata \n");
					printf("2. Pridej pozici \n");
					printf("3. Zrus \n");
					fgets(char_choice, 3, stdin);
					choice = atoi(char_choice);
					switch (choice)
					{
					case 1:
					{
						char oborString[4], jmeno[30], prijmeni[30], telefon[20], mail[40], jazyky[30];
						printf("Zadejte jmeno:");
						fgets(jmeno, 30, stdin);
						jmeno[strlen(jmeno) - 1] = '\0';
						printf("Zadejte prijmeni:");
						fgets(prijmeni, 30, stdin);
						prijmeni[strlen(prijmeni) - 1] = '\0';

						printf("Zadejte obor(0-10):");
						fgets(oborString, 4, stdin);
						oborString[strlen(oborString) - 1] = '\0';
						int obor = atoi(oborString);
						if (!(obor>=0&&obor<=10))
						{
							spatnyKadnidat = 1;
							break;
						}
						printf("Zadejte telefon(000 000 000):");
						fgets(telefon, 20, stdin);
						telefon[strlen(telefon) - 1] = '\0';
						if (!jeTelefon(telefon))
						{
							spatnyKadnidat = 1;
							break;
						}
						printf("Zadejte mail:");
						fgets(mail, 40, stdin);
						mail[strlen(mail) - 1] = '\0';
						if (!jeMail(mail))
						{
							spatnyKadnidat = 1;
							break;
						}
						printf("Zadejte jazyky:");
						fgets(jazyky, 30, stdin);
						strcat(jmeno, " ");
						pridejKandidata(vytvorKandidata(counterKandidat, strcat(jmeno, prijmeni), atoi(oborString), telefon, mail, jazyky));
						printf("* Kandidat pridan. \n");
						counterKandidat++;
						temp = 1;
						break;
					}
					case 2:
					{
						//TODO overeni vstupu
						char pozice[30], krajString[5], popis[50], nabidka[50], pozadavky[50], jazyky[40], maxPlatString[30];
						printf("Zadejte pozici:");
						fgets(pozice, 30, stdin);
						pozice[strlen(pozice) - 1] = '\0';
						printf("Zadejte kraj(0-13):");
						fgets(krajString, 5, stdin);
						krajString[strlen(krajString) - 1] = '\0';
						int kraj = atoi(krajString);
						if (!(kraj >= 0 && kraj <= 13))
						{
							spatnyKadnidat = 1;
							break;
						}
						printf("Zadejte popis:");
						fgets(popis, 50, stdin);
						popis[strlen(popis) - 1] = '\0';
						printf("Zadejte nabidka:");
						fgets(nabidka, 50, stdin);
						nabidka[strlen(nabidka) - 1] = '\0';
						printf("Zadejte pozadavky:");
						fgets(pozadavky, 30, stdin);
						pozadavky[strlen(pozadavky) - 1] = '\0';
						printf("Zadejte jazyky:");
						fgets(jazyky, 40, stdin);
						jazyky[strlen(jazyky) - 1] = '\0';
						printf("Zadejte maximalni plat:");
						fgets(maxPlatString, 30, stdin);
						pridejPozice(vytvorPozici(counterPozice, pozice, atoi(krajString), popis, pozadavky, nabidka, jazyky, atof(maxPlatString)));
						printf("* Pozice pridana. \n");
						counterPozice++;
						temp = 1;
						break;
					}
					case 3:
						temp = 1;
						break;
					}
				} while (temp != 1);
				break;
			}
			case 4:
				//TODO
				do {
					printf("1. Odeber kandidata \n");
					printf("2. Odeber pozici \n");
					printf("3. Zrus \n");
					fgets(char_choice, 3, stdin);
					choice = atoi(char_choice);
					switch (choice)
					{
					case 1: 
					{
						char idCislo[20];
						printf("Zadejte cislo kandidata:");
						fgets(idCislo, 10, stdin);
						free(odeberKandidataZeSeznamu(atoi(idCislo)));
						temp = 1;
						break;
					}
					case 2:
					{
						char idPozice[20];
						printf("Zadejte cislo pozice:");
						fgets(idPozice, 10, stdin);
						free(odeberPoziciZeSeznamu(atoi(idPozice)));
						temp = 1;
						break;
					}
					case 3:
						temp = 1;
						break;
					}
				} while (temp != 1);
				break;
			case 5:
				do {
					printf("1. Odstran seznam kandidatu \n");
					printf("2. Odstran seznam pozic \n");
					printf("3. Zrus \n");
					fgets(char_choice, 3, stdin);
					choice = atoi(char_choice);
					switch (choice)
					{
					case 1:
						zrusSeznamKandidatu();
						counterKandidat = 0;
						temp = 1;
						break;
					case 2:
						zrusSeznamPozic();
						counterPozice = 0;
						temp = 1;
						break;
					case 3:
						temp = 1;
						break;
					}
				} while (temp != 1);
				break;
			case 6:
				do {
					printf("1. Najdi kandidata \n");
					printf("2. Najdi pozici \n");
					printf("3. Zrus \n");
					fgets(char_choice, 3, stdin);
					choice = atoi(char_choice);
					switch (choice)
					{
					case 1:
					{
						char idCislo[20];
						printf("Zadejte cislo kandidata:");
						fgets(idCislo, 10, stdin);
						vypisKandidata(najdiKandidataZeSeznamu(atoi(idCislo)));
						
						temp = 1;
						break;
					}
					case 2:
					{
						char idPozice[20];
						printf("Zadejte cislo pozice:");
						fgets(idPozice, 10, stdin);
						vypisPozici(najdiPoziciZeSeznamu(atoi(idPozice)));
						temp = 1;
						break;
					}
					case 3:
						temp = 1;
						break;
					}
				} while (temp != 1);
				break;
			case 7:
			{
				char idPozice[20];
				printf("Zadejte cislo pozice:");
				fgets(idPozice, 10, stdin);
				char idCislo[20];
				printf("Zadejte cislo kandidata:");
				fgets(idCislo, 10, stdin);		
				stKandidat* kand = najdiKandidataZeSeznamu(atoi(idCislo));
				stPozice* poz = najdiPoziciZeSeznamu(atoi(idPozice));
				if (kand != NULL && poz != NULL)
				{
					pridejPohovor(vytvorPohovor(kand, poz));
					printf("* Pohovor pridan. \n");
				}
				break;
			}
			case 8:
			{
				char idPohovor[20];
				printf("Zadejte id pohovoru:");
				fgets(idPohovor, 10, stdin);
				char stavPohovor[20];
				printf("Zadejte novy stav pohovoru:");
				fgets(stavPohovor, 10, stdin);
				stavPohovor[strlen(stavPohovor) - 1] = '\0';
				int pohovor= atoi(stavPohovor);
				if (!(pohovor >= 0 && pohovor <= 6))
				{
					break;
				}
				zmenStavPohovor(atoi(idPohovor),atoi(stavPohovor));
				break;
			}
			case 9:
				vypisPohovory();
				break;
			case 10:
				zrusPohovory();
				zrusSeznamKandidatu();
				zrusSeznamPozic();
				choice = 10;
				break;
			}
		} while (choice != 10);
	
		if (_CrtDumpMemoryLeaks() != 0) {
			printf("Nebyla provedena dealokace");
		}
}

void help() {
	printf("1. Nacti data do seznamu kandidatu/pozic ze souboru CSV \n");
	printf("2. Vypis seznam kandidatu/pozic \n");
	printf("3. Pridej z klavesnice kandidata/pozici do seznamu \n");
	printf("4. Odeber kandidata / pozici ze seznamu \n");
	printf("5. Zrus seznam kandidatu/pozic \n");
	printf("6. Najdi kandidata/pozici \n");
	printf("7. Pridej pohovor \n");
	printf("8. Edituj stav pohovoru \n");
	printf("9. Vypis pohovory \n");
	printf("10. Ukoncit program \n");
}

bool jeTelefon (char* text) {
	if (text == NULL || text == "" || strlen(text) != 11)
	{
		return false;
	}
	for (size_t i = 0; i < strlen(text); i++)
	{
		if (i == 3 || i == 7)
			continue;
		if (text[i] < '0' || text[i]>'9') {
			return false;
		}
	}
	return true;
}


bool jeMail(char* text) {
	int obsahujeZavinac = 0;
	int obsahujeTecku = 0;
	if (text == NULL || text == "")
	{
		return false;
	}
	for (size_t i = 0; i < strlen(text); i++)
	{
		if (text[i]=='@') {
			obsahujeZavinac++;
		}
		if (text[i] == '.') {
			obsahujeTecku++;
		}
	}
	if (obsahujeTecku == 0 || obsahujeZavinac == 0) {
		return false;
	}
	return true;
}

