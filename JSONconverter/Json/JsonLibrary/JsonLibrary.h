#pragma once
#include <string>
#include "pch.h"
#include "platform.h"

#include <iostream>

#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#ifdef _DEBUG
#define DEBUG_CLIENTBLOCK new ( _CLIENT_BLOCK,__FILE__,__LINE__)
#define new DEBUG_CLIENTBLOCK
#endif

// - �ablona s parametrem datov�ho typu ulo�en�ch hodnot
// - nen� povoleno u�it� STL kontejner� ani jin�ch knihoven pro ukl�d�n� dat
// - realizace mus� vyu��vat dynamicky alokovan� pole, spojov� seznam nebo jinou vhodnou V�mi implementovanou ADS 
template<typename T>
class DynamicArray {
public:
	DynamicArray();
	~DynamicArray();

	// - p�id� element na konec pole
	void append(const T& element);

	void editElementAtIndex(T& element, int index);

	// - v�jimky p�i neplatn�m nebo nekorektn�m indexu
	const T& getElementAt(int index) const;

	// - sma�e vybran� element
	void removeElementAtIndex(int index);

	// - vrac� velikost (po�et prvk�) v poli
	int getSize() const;

	// - zdvojn�sob� kapacitu pole
	void expandArray();
private:
	T* dynamicArray;
	int size;
	int numberOfElements;
};

class Value;
///////////////////////////////////////////////////////////////////////////////
// - definuje p�r kl�� (�et�zec) a hodnota (JSON hodnota) pro reprezentaci hodnot JSON objektu
class DLL_SPEC KeyValuePair {
public:
	KeyValuePair();
	KeyValuePair(std::string key, Value* value);
	~KeyValuePair();
	// - vr�t� kl��
	std::string getKey() const;
	// - vr�t� hodnotu
	Value* getValue() const;
	std::string serialize() const;
private:
	std::string key;
	Value* valueKeys;
};

///////////////////////////////////////////////////////////////////////////////
// JSON hodnota - reprezentuje abstraktn�ho p�edka pro z�kladn� datov� typy v JSON (string, number, object, array, bool, null)
class DLL_SPEC Value
{
public:
	// serializuje hodnotu do podoby JSON reprezentace
	virtual std::string serialize() const = 0;
	virtual ~Value();
};

///////////////////////////////////////////////////////////////////////////////

// - reprezentuje hodnotu typu JSON null
class DLL_SPEC NullValue :
	public Value
{
public:
	std::string serialize() const;

};

///////////////////////////////////////////////////////////////////////////////

// - reprezentuje hodnotu typu JSON bool
class DLL_SPEC BoolValue :
	public Value
{
public:
	BoolValue(bool value);
	std::string serialize() const;
	// - vrac� bool hodnotu
	bool get() const;
private:
	bool valueBool;
};

///////////////////////////////////////////////////////////////////////////////
// 
// - reprezentuje hodnotu typu JSON ��slo
class DLL_SPEC NumberValue :
	public Value
{
public:
	NumberValue(double value);
	~NumberValue();
	std::string serialize() const;
	// - vrac� ��selnou hodnotu 
	double get() const;
private:
	double valueNumber;
};

///////////////////////////////////////////////////////////////////////////////

// - reprezentuje hodnotu typu JSON �et�zec (string)
class DLL_SPEC StringValue :
	public Value
{
public:
	~StringValue();
	StringValue(std::string value);
	std::string serialize() const;
	// - vrac� �et�zcovou hodnotu
	std::string get() const;
private:
	std::string value;
};

///////////////////////////////////////////////////////////////////////////////

// - reprezentuje hodnotu typu JSON pole
class DLL_SPEC ArrayValue :
	public Value
{
public:
	ArrayValue();
	~ArrayValue();

	// - p�id� element na konec pole
	void append(Value* element);
	// - sma�e element na indexu
	void removeOnIndex(int index);
	// - edit
	void edit(Value* element,int index);
	Value* getElementAtIndex(int index);
	std::string serialize() const;
private:
	// - atribut DynamicArray<Value*> pro uchov�n� jednotliv�ch element� v poli
	DynamicArray<Value*> arrayArrayValue;
};

///////////////////////////////////////////////////////////////////////////////

// - reprezentuje hodnotu typu JSON objekt
class DLL_SPEC ObjectValue :
	public Value
{
public:

	ObjectValue();
	~ObjectValue();

	// - p�id� kl��-element do objektu
	void append(const KeyValuePair& pair);
	std::string serialize() const;
private:
	// - atribut DynamicArray<KeyValuePair> pro uchov�n� jednotliv�ch hodnot a kl��� v objektu
	DynamicArray<KeyValuePair> arrayObjectValue;
};

///////////////////////////////////////////////////////////////////////////////

// - t��da pro pr�ci s JSON
class DLL_SPEC JSON
{
public:
	// - provede deserializaci �et�zce na vytvo�en� objekty
	// - p�e�tu znak a rozhodnu se
	// -- '{' - za��n�m ��st objekt
	// -------- �tu znaky, pak mus� b�t dvojte�ka, potom vol�m rekurzivn� deserialize(); n�sleduje ��rka nebo '}', podle situace se �ten� opakuje
	// -- '[' - za��n�m ��st pole
	// -------- vol�m rekurzivn� deserialize(); n�sleduje ��rka nebo ']', podle situace se �ten� opakuje
	// -- '"' - za��n�m ��st �et�zec - pozor na escapovan� uvozovky
	// -- [-0123456789] - za��n�m ��st ��slo - na�tu v�echny ��slice (pozor na mo�nou desetinnou te�ku)
	// -- 'n' - 'null'
	// -- 't' - 'true'
	// -- 'f' - 'false'
	// -- cokoliv jin�ho - vyvol�v�m v�jimku
	// - nen� p��pustn� vracet nullptr
	// - deserializace mus� b�t rozumn� implementov�na - nen� p��pustn� zde napsat jednu extr�mn� dlouhou metodu
	static Value* deserialize(const std::string& string);
	
	// - provede serializaci do JSON �et�zce
	static std::string serialize(const Value* value);
	static std::string readFile(std::string name);
	static void saveFile(std::string name, std::string json);
private:
	static Value* deserialize(const std::string& string, int& pointer);
	static Value* readObject(const std::string& text, int& pointer);
	static Value* readArray(const std::string& text, int& pointer);
	static Value* readText(const std::string& text, int& pointer);
	static Value* readNumber(const std::string& text, int& pointer);
	static Value* readNull(const std::string& text, int& pointer);
	static Value* readTrue(const std::string& text, int& pointer);
	static Value* readFalse(const std::string& text, int& pointer);
};
///////////////////////////////////////////////////////////////////////////////

