#include "pozice.h"

stPozice* vytvorPozici(int id, char* pozice,enum KRAJ kraj, char* popis, char* pozadavky, char* nabidka, char* jazyky, float maxPlat)
{
    struct pozice* poz = malloc(sizeof(stPozice));
    poz->id = id;
    strcpy(poz->pozice,pozice);
    poz->kraj = kraj;
    strcpy(poz->popis, popis);
    strcpy(poz->pozadavky, pozadavky);
    strcpy(poz->nabidka, nabidka);
    strcpy(poz->jazyky, jazyky);
    poz->maxPlat = maxPlat;
    poz->dalsi = NULL;
    return poz;
}

void vypisPozici(stPozice* pozice)
{
    printf("%i ", pozice->id);
    printf("%s ", pozice->pozice);
    switch (pozice->kraj)
    {
    case 0:
        printf("Praha");
        break;
    case 1:
        printf("Stredocesky");
        break;
    case 2:
        printf("Jihocesky");
        break;
    case 3:
        printf("Plzensky");
        break;
    case 4:
        printf("Karlovarsky");
        break;
    case 5:
        printf("Ustecky");
        break;
    case 6:
        printf("Liberecky");
        break;
    case 7:
        printf("Kralovehradecky");
        break;
    case 8:
        printf("Pardubicky");
        break;
    case 9:
        printf("Vysocina");
        break;
    case 10:
        printf("Jihomoravsky");
        break;
    case 11:
        printf("Olomoucky");
        break;
    case 12:
        printf("Zlinsky");
        break;
    case 13:
        printf("Moravskoslezsky");
        break;
    default:
        break;
    }
    printf(" %s ",pozice->popis);
    printf("%s ", pozice->pozadavky);
    printf("%s ", pozice->nabidka);
    printf("%s ", pozice->jazyky);
    printf("%f ", pozice->maxPlat);
    printf("\n");
}
