#include "agenda.h"

static stKandidat* prvniKandi = NULL, * aktKandi = NULL;
static stPozice* prvniPozice = NULL, * aktPozice = NULL;
static stPohovor** polePohovoru = NULL;
int counterID = 0;
static int aktualniPocetPohovoru = 0;
static int kapacitaPole = 2;


bool jeTelefonniCislo(char* text);

void nactiSeznamKandidatu(char* nazevSouboru)
{
	FILE* file;
	char line[2048];
	file = fopen(nazevSouboru, "r");
	fgets(line, 2048, file);
	if (file == NULL) {
		printf("Soubor %s neexistuje!\n", nazevSouboru);
	}	
	while (fgets(line, 2048, file))
	{		
		char* jazykyKandidata[100];
		char* mailKandidata[100];
		int idKandidata = atoi(strtok(line,";"));		
		char* jmenoKandidata = strtok(NULL, ";");		
		enum OBOR obor = atoi(strtok(NULL, ";"));	
		char* telefonKandidata = strtok(NULL, ";");		
		if (jeTelefonniCislo(telefonKandidata)) {
			strcpy(mailKandidata ,strtok(NULL, ";"));
			if (strchr(mailKandidata, '@') == NULL) {
				strcpy(jazykyKandidata, mailKandidata);
				strcpy(mailKandidata,"");
			}
			else {
				strcpy(jazykyKandidata, strtok(NULL, ";"));
			}
			
		}
		else {
			if (strchr(telefonKandidata,'@') == NULL) {
				strcpy(jazykyKandidata, telefonKandidata);
				strcpy(telefonKandidata, "");
				strcpy(mailKandidata, "");
			}
			else {
				strcpy(mailKandidata, telefonKandidata);
				strcpy(telefonKandidata, "");
				strcpy(jazykyKandidata, strtok(NULL, ";"));
			}
			
		}

		if (prvniKandi == NULL)
			{
				prvniKandi = vytvorKandidata(idKandidata, jmenoKandidata, obor,
				telefonKandidata, mailKandidata, jazykyKandidata);
				aktKandi = prvniKandi;
			}
		else
		{
			aktKandi->dalsi = vytvorKandidata(idKandidata, jmenoKandidata, obor,
			telefonKandidata, mailKandidata, jazykyKandidata);
			aktKandi = aktKandi->dalsi;
		}
	}	
}

void nactiSeznamPozic(char* nazevSouboru)
{
	FILE* file;
	char line[2048];
	file = fopen(nazevSouboru, "r");
	fgets(line, 2048, file);
	if (file == NULL) {
		printf("Soubor %s neexistuje!\n", nazevSouboru);
	}
	while (fgets(line, 2048, file))
	{
		int idPozice = atoi(strtok(line, ";"));
		char* pozice = strtok(NULL, ";");
		char* popis = strtok(NULL, ";");
		char* pozadavky = strtok(NULL, ";");
		char* nabidka = strtok(NULL, ";");
		char* jazyky = strtok(NULL, ";");
		float maxPlat = atof(strtok(NULL, ";"));
		enum KRAJ kraj = atoi(strtok(NULL, ";"));

		if (prvniPozice == NULL)
		{
			prvniPozice = vytvorPozici(idPozice, pozice, kraj, popis,
				pozadavky, nabidka, jazyky, maxPlat);
			aktPozice = prvniPozice;
		}
		else
		{
			aktPozice->dalsi = vytvorPozici(idPozice, pozice, kraj, popis,
				pozadavky, nabidka, jazyky, maxPlat);
			aktPozice = aktPozice->dalsi;
		}
	}
}

void vypisSeznamKandidatu()
{
	aktKandi = prvniKandi;
	while (aktKandi!=NULL)
	{
		vypisKandidata(aktKandi);
		aktKandi = aktKandi->dalsi;
	}
}

void pridejKandidata(stKandidat* kandidat)
{
	if (prvniKandi == NULL)
	{
		prvniKandi = vytvorKandidata(kandidat->id, kandidat->jmeno, 
			kandidat->obor,kandidat->tel, kandidat->mail, kandidat->jazyky);
		aktKandi = prvniKandi;
	}
	else
	{
		aktKandi->dalsi = vytvorKandidata(kandidat->id, kandidat->jmeno, 
			kandidat->obor,kandidat->tel, kandidat->mail, kandidat->jazyky);
		aktKandi = aktKandi->dalsi;
	}
}

stKandidat* odeberKandidataZeSeznamu(int cisloKandidata)
{
	stKandidat* odeber = najdiKandidataZeSeznamu(cisloKandidata);
	aktKandi = prvniKandi;
	if (odeber == NULL)
	{
		return false;
	}
	while (aktKandi != NULL)
	{
		if (aktKandi == odeber) {
			prvniKandi = prvniKandi->dalsi;
			printf("* Kandidat byl odebran. \n");
			return odeber;
		}
		if (aktKandi->dalsi==odeber) {
			aktKandi->dalsi = odeber->dalsi;
			printf("* Kandidat byl odebran. \n");
			return odeber;
		}
		aktKandi = aktKandi->dalsi;
	}
}

stKandidat* najdiKandidataZeSeznamu(int cisloKandidata)
{
	aktKandi = prvniKandi;
	while (aktKandi != NULL)
	{
		if (aktKandi->id == cisloKandidata) {
			return aktKandi;
		}
		aktKandi = aktKandi->dalsi;
	}
	printf("* Kandidat nebyl nalezen. \n");
	return NULL;
}

void zrusSeznamKandidatu()
{
	stKandidat* tmp;
	while (prvniKandi != NULL)
	{
		tmp = prvniKandi;
		prvniKandi = prvniKandi->dalsi;
		free(tmp);
	}
	free(prvniKandi);
}
void vypisSeznamPozic()
{
	aktPozice = prvniPozice;
	while (aktPozice!=NULL)
	{
		vypisPozici(aktPozice);
		aktPozice = aktPozice->dalsi;
	}
}

void pridejPozice(stPozice* pozice)
{
	if (prvniPozice == NULL)
	{
		prvniPozice = vytvorPozici(pozice->id,pozice->pozice, pozice->kraj,pozice->popis,
			pozice->pozadavky, pozice->nabidka, pozice->jazyky, pozice->maxPlat);
		aktPozice = prvniPozice;
	}
	else
	{
		aktPozice->dalsi = vytvorPozici(pozice->id, pozice->pozice, pozice->kraj, pozice->popis,
			pozice->pozadavky, pozice->nabidka, pozice->jazyky, pozice->maxPlat);
		aktPozice = aktPozice->dalsi;
	}
}

stPozice* odeberPoziciZeSeznamu(int cisloPozice)
{
	stPozice* odeber = najdiPoziciZeSeznamu(cisloPozice);
	aktPozice = prvniPozice;
	if (odeber == NULL)
	{
		return false;
	}
	while (aktPozice != NULL)
	{	
		if (aktPozice == odeber) {
			prvniPozice = prvniPozice->dalsi;
			printf("* Pozice byla odebrana. \n");
			return odeber;

		}
		if (aktPozice->dalsi==odeber) {
			aktPozice->dalsi = odeber->dalsi;
			printf("* Pozice byla odebrana. \n");
			return odeber;			
		}
		aktPozice = aktPozice->dalsi;
	}
}

stPozice* najdiPoziciZeSeznamu(int cisloPozice)
{
	aktPozice = prvniPozice;
	while (aktPozice != NULL)
	{
		if (aktPozice->id == cisloPozice) {
			return aktPozice;
		}
		aktPozice = aktPozice->dalsi;
	}
	printf("* Pozice nebyla nalezena. \n");
	return NULL;
}

void zrusSeznamPozic()
{
	stPozice* tmp;
	while (prvniPozice != NULL)
	{
		tmp = prvniPozice;
		prvniPozice = prvniPozice->dalsi;
		free(tmp);
	}
}

void alokujPolePohovoru()
{
	if (polePohovoru == NULL)
	{
		polePohovoru = calloc(kapacitaPole, sizeof(stPohovor));
	}else
	if (aktualniPocetPohovoru + 1 == kapacitaPole) {
		printf("* Kapacita pole byla zvysena \n");
		kapacitaPole *= 2;
		polePohovoru = realloc(polePohovoru, sizeof(stPozice) * kapacitaPole);
	}
}


void pridejPohovor(stPohovor* pohovor)
{	
	alokujPolePohovoru();	
	polePohovoru[aktualniPocetPohovoru]=pohovor;
	aktualniPocetPohovoru++;
}

void zmenStavPohovor(int id,enum STAV_POHOVORU vysledek)
{
	for (size_t i = 0; i < aktualniPocetPohovoru; i++)
	{
		if (polePohovoru[i]->id == id) {
			polePohovoru[i]->vysledek = vysledek;
		}
	}
}

void vypisPohovory()
{
	for (size_t i = 0; i < aktualniPocetPohovoru; i++)
	{
		vypisPohovor(polePohovoru[i]);
	}
}

void zrusPohovory()
{
	for (int i = 0; i < aktualniPocetPohovoru; i++)
	{
		free(polePohovoru[i]);
	}
	free(polePohovoru);
}


bool jeTelefonniCislo(char * text) {
	if (text == NULL || text == ""|| strlen(text)!=11)
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