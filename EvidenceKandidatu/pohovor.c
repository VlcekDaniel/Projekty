#include "pohovor.h"

int citac = 1;

stPohovor* vytvorPohovor(stKandidat* kandidat, stPozice* idPozice)
{
    if (kandidat || idPozice != NULL) {
        stPohovor* poh = malloc(sizeof(stPohovor));
        poh->id = citac;
        citac++;
        poh->kandidat = kandidat;
        poh->pozice = idPozice;
        poh->vysledek = nenastaveno;
        return poh;
    }
}

void vypisPohovor(stPohovor* pohovor)
{
    printf("%i ", pohovor->id);
    printf("Kandidat: %s ", pohovor->kandidat->jmeno);
    printf("Obor: %i ", pohovor->kandidat->obor);
    printf("Pozice: %s ", pohovor->pozice->pozice);
    printf("Kraj: %i ", pohovor->pozice->kraj);
    printf("Stav: ");
    switch (pohovor->vysledek)
    {
    case 0:
        printf("nenastaveno");
        break;
    case 1:
        printf("nastaveno");
        break;
    case 2:
        printf("zaslano_CV");
        break;
    case 3:
        printf("prijat");
        break;
    case 4:
        printf("neprijat");
        break;
    case 5:
        printf("odmitl");
        break;
    case 6:
        printf("pozastaven");
        break;
    default:
        break;
    }
    printf("\n");
}
