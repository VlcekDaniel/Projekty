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

// - šablona s parametrem datového typu uložených hodnot
// - není povoleno užití STL kontejnerù ani jiných knihoven pro ukládání dat
// - realizace musí využívat dynamicky alokované pole, spojový seznam nebo jinou vhodnou Vámi implementovanou ADS 
template<typename T>
class DynamicArray {
public:
	DynamicArray();
	~DynamicArray();

	// - pøidá element na konec pole
	void append(const T& element);

	void editElementAtIndex(T& element, int index);

	// - výjimky pøi neplatném nebo nekorektním indexu
	const T& getElementAt(int index) const;

	// - smaže vybraný element
	void removeElementAtIndex(int index);

	// - vrací velikost (poèet prvkù) v poli
	int getSize() const;

	// - zdvojnásobí kapacitu pole
	void expandArray();
private:
	T* dynamicArray;
	int size;
	int numberOfElements;
};

class Value;
///////////////////////////////////////////////////////////////////////////////
// - definuje pár klíè (øetìzec) a hodnota (JSON hodnota) pro reprezentaci hodnot JSON objektu
class DLL_SPEC KeyValuePair {
public:
	KeyValuePair();
	KeyValuePair(std::string key, Value* value);
	~KeyValuePair();
	// - vrátí klíè
	std::string getKey() const;
	// - vrátí hodnotu
	Value* getValue() const;
	std::string serialize() const;
private:
	std::string key;
	Value* valueKeys;
};

///////////////////////////////////////////////////////////////////////////////
// JSON hodnota - reprezentuje abstraktního pøedka pro základní datové typy v JSON (string, number, object, array, bool, null)
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
	// - vrací bool hodnotu
	bool get() const;
private:
	bool valueBool;
};

///////////////////////////////////////////////////////////////////////////////
// 
// - reprezentuje hodnotu typu JSON èíslo
class DLL_SPEC NumberValue :
	public Value
{
public:
	NumberValue(double value);
	~NumberValue();
	std::string serialize() const;
	// - vrací èíselnou hodnotu 
	double get() const;
private:
	double valueNumber;
};

///////////////////////////////////////////////////////////////////////////////

// - reprezentuje hodnotu typu JSON øetìzec (string)
class DLL_SPEC StringValue :
	public Value
{
public:
	~StringValue();
	StringValue(std::string value);
	std::string serialize() const;
	// - vrací øetìzcovou hodnotu
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

	// - pøidá element na konec pole
	void append(Value* element);
	// - smaže element na indexu
	void removeOnIndex(int index);
	// - edit
	void edit(Value* element,int index);
	Value* getElementAtIndex(int index);
	std::string serialize() const;
private:
	// - atribut DynamicArray<Value*> pro uchování jednotlivých elementù v poli
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

	// - pøidá klíè-element do objektu
	void append(const KeyValuePair& pair);
	std::string serialize() const;
private:
	// - atribut DynamicArray<KeyValuePair> pro uchování jednotlivých hodnot a klíèù v objektu
	DynamicArray<KeyValuePair> arrayObjectValue;
};

///////////////////////////////////////////////////////////////////////////////

// - tøída pro práci s JSON
class DLL_SPEC JSON
{
public:
	// - provede deserializaci øetìzce na vytvoøené objekty
	// - pøeètu znak a rozhodnu se
	// -- '{' - zaèínám èíst objekt
	// -------- ètu znaky, pak musí být dvojteèka, potom volám rekurzivnì deserialize(); následuje èárka nebo '}', podle situace se ètení opakuje
	// -- '[' - zaèínám èíst pole
	// -------- volám rekurzivnì deserialize(); následuje èárka nebo ']', podle situace se ètení opakuje
	// -- '"' - zaèínám èíst øetìzec - pozor na escapované uvozovky
	// -- [-0123456789] - zaèínám èíst èíslo - naètu všechny èíslice (pozor na možnou desetinnou teèku)
	// -- 'n' - 'null'
	// -- 't' - 'true'
	// -- 'f' - 'false'
	// -- cokoliv jiného - vyvolávám výjimku
	// - není pøípustné vracet nullptr
	// - deserializace musí být rozumnì implementována - není pøípustné zde napsat jednu extrémnì dlouhou metodu
	static Value* deserialize(const std::string& string);
	
	// - provede serializaci do JSON øetìzce
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

