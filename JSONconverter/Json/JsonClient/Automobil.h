#pragma once
#include <string>
#ifndef AUTOMOBIL_H
#define AUTOMOBIL_H
class Automobil
{
public:
	Automobil(int id, std::string znacka, double vykon, bool jeNaBenzin);
	~Automobil();
	std::string getString();
private:
	int id;
	std::string znacka;
	int pocetKol;
	double vykon;
	bool jeNaBenzin;
	//std::string poleDruhuKaroserii[];
};
#endif // !AUTOMOBIL_H