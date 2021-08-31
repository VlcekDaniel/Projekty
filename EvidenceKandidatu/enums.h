#pragma once
#ifndef ENUMS_H
#define ENUMS_H
enum OBOR
{
	Administrativa, Ekonomie, Pravo, IT_All, Zdravotnictvi, 
	Obchod, Vyroba, Stavebnictvi, Skolstvi,Doprava, Management
};
enum KRAJ
{
	Praha, Stredocesky, Jihocesky, Plzensky, Karlovarsky, Ustecky, 
	Liberecky, Kralovehradecky, Pardubicky, Vysocina,
	Jihomoravsky, Olomoucky, Zlinsky, Moravskoslezsky
};
enum STAV_POHOVORU
{
	nenastaveno, nastaveno, zaslano_CV, prijat, neprijat, odmitl,
	pozastaven
};
#endif // !ENUMS_H