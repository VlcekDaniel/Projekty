#include "Automobil.h"

Automobil::Automobil(int id, std::string znacka, double vykon, bool jeNaBenzin)
{
    this->id = id;
    this->znacka = znacka;
    this->vykon = vykon;
    this->jeNaBenzin = jeNaBenzin;
}

Automobil::~Automobil()
{
}

std::string Automobil::getString()
{
    std::string out;
    out.append("{");
    out.append("\"Automobil\":{");
    out.append("\"ID\":" + std::to_string(id) +"," + "\"Znacka\":\"" + znacka + "\"," + "\"Vykon\":" +std::to_string(vykon) + "," +"\"JeNaBenzin\":" +(jeNaBenzin ? "true" : "false)"));
    out.append("}");
    out.append("}");
    return out;
}
