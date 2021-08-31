#include "kandidat.h"

stKandidat* vytvorKandidata(int id, char* jmeno,enum OBOR obor, 
    char* tel, char* mail, char* jazyky)
{
    stKandidat *kand = malloc(sizeof(stKandidat));
    kand->id = id;
    strcpy(kand->jmeno, jmeno);
    kand->obor = obor;
    strcpy(kand->tel, tel);
    strcpy(kand->mail, mail);
    strcpy(kand->jazyky, jazyky);
    kand->dalsi = NULL;
    return kand;
}

void vypisKandidata(stKandidat* kandidat)
{
    printf("%i ",kandidat->id);
    printf("%s ",kandidat->jmeno);

    switch (kandidat->obor)
    {
    case 0:
        printf("Administrativa ");
        break;
    case 1:
        printf("Ekonomie ");
        break;
    case 2:
        printf("Pravo ");
        break;
    case 3:
        printf("IT_All ");
        break;
    case 4:
        printf("Zdravotnictvi ");
        break;
    case 5:
        printf("Obchod ");
        break;
    case 6:
        printf("Vyroba ");
        break;
    case 7:
        printf("Stavebnictvi ");
        break;
    case 8:
        printf("Skolstvi ");
        break;
    case 9:
        printf("Doprava ");
        break;
    case 10:
        printf("Management ");
        break;
    default:
        break;
    }

    if (strcmp(kandidat->tel, "") == 0) {
        printf("%s", kandidat->tel);
    }
    else {
        printf("%s ", kandidat->tel);
    }
    if (strcmp(kandidat->mail,"")==0){
        printf("%s", kandidat->mail);
    }
    else {
        printf("%s ", kandidat->mail);
    }
    kandidat->jazyky[strlen(kandidat->jazyky) - 1] = '\0';
    printf(kandidat->jazyky);
    printf("\n");
}
