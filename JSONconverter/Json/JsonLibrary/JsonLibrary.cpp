
#include <utility>
#include <limits.h>
#include <stdexcept>
#include <string>
#include "pch.h"
#include <fstream>
#include <iostream>
#include <cmath>
#include "JsonLibrary.h"

///////////////////////////////////////////////////////////////////////////////
// - definuje pár klíè (øetìzec) a hodnota (JSON hodnota) pro reprezentaci hodnot JSON objektu

KeyValuePair::KeyValuePair()
{
    this->key = "";
    this->valueKeys = nullptr;
}

KeyValuePair::KeyValuePair(std::string key, Value* value)
{
    this->key = key;
    this->valueKeys = value;
}

KeyValuePair::~KeyValuePair()
{

}

std::string KeyValuePair::getKey() const
{
    return key;
}

Value* KeyValuePair::getValue() const
{
    return valueKeys;
}

std::string KeyValuePair::serialize() const
{
    std::string out;   
    out.append(key); 
    out.append(":");
    out.append(valueKeys->serialize());
    return out;
}

///////////////////////////////////////////////////////////////////////////////
// -BOOL value

BoolValue::BoolValue(bool value)
{
    valueBool = value;
}

std::string BoolValue::serialize() const
{
    if (valueBool) {
        return "true";
    }
    else {
        return "false";
    }
}

bool BoolValue::get() const
{
    return false;
}

///////////////////////////////////////////////////////////////////////////////
// -Number value

NumberValue::~NumberValue()
{

}

NumberValue::NumberValue(double value)
{
    valueNumber = value;
}

std::string NullValue::serialize() const
{
    return "null";
}

std::string NumberValue::serialize() const
{
    return std::to_string(valueNumber);
}

double NumberValue::get() const
{
    return valueNumber;
}

///////////////////////////////////////////////////////////////////////////////
// -STRING value

StringValue::StringValue(std::string value)
{
    this->value ="\""  + value + "\"";
}

StringValue::~StringValue()
{

}

std::string StringValue::serialize() const
{
    return value;
}

std::string StringValue::get() const
{
    return value;
}

///////////////////////////////////////////////////////////////////////////////
// -ARRAY value

ArrayValue::ArrayValue()
{
}

ArrayValue::~ArrayValue()
{  
    for (size_t i = 0; i < arrayArrayValue.getSize(); i++)
    {
        delete arrayArrayValue.getElementAt(i);
    }
}

void ArrayValue::removeOnIndex(int index)
{
    arrayArrayValue.removeElementAtIndex(index);
}

void ArrayValue::edit(Value* elementNew,int index)
{
    arrayArrayValue.editElementAtIndex(elementNew,index);
}

Value* ArrayValue::getElementAtIndex(int index)
{
    return arrayArrayValue.getElementAt(index);
}

void ArrayValue::append(Value* element)
{
    arrayArrayValue.append(element);
}

std::string ArrayValue::serialize() const
{
    std::string out;
    out.append("[");
    for (int i = 0; i < arrayArrayValue.getSize(); i++)
    {
        out.append(arrayArrayValue.getElementAt(i)->serialize());
        if (i != arrayArrayValue.getSize() - 1)
        {
            out.append(",");
        }
    }
    out.append("]");
    return out;
}

///////////////////////////////////////////////////////////////////////////////
// -OBJECT value

ObjectValue::ObjectValue()
{
   
}

ObjectValue::~ObjectValue()
{

    for (size_t i = 0; i < arrayObjectValue.getSize(); i++)
    {
        delete arrayObjectValue.getElementAt(i).getValue();
    }
}


void ObjectValue::append(const KeyValuePair& pair)
{
    arrayObjectValue.append(pair);
}


std::string ObjectValue::serialize() const
{
    std::string out;
    out.append("{");
    for (int i = 0; i < arrayObjectValue.getSize(); i++)
    {
        out.append(arrayObjectValue.getElementAt(i).serialize()); 
        if (i != arrayObjectValue.getSize()-1)
        {
            out.append(",");
        }
    }
    out.append("}");
    return out;
}

///////////////////////////////////////////////////////////////////////////////
// -JSON

Value* JSON::deserialize(const std::string& text)
{
    int pointer = 0;
    return deserialize(text, pointer);
}


std::string JSON::serialize(const Value* value)
{
    std::string out = "";
    out.append(value->serialize());  
    return out;
}

Value* JSON::deserialize(const std::string& text, int& pointer)
{
    if (text[pointer]=='{')
    {
        Value* value = readObject(text,pointer);
        return value;
    }
    if (text[pointer] == '[')
    {
        Value* value = readArray(text,pointer);
        return value;
    }
    if (text[pointer] == '\"')
    {
        Value* value =  readText(text,pointer);
        return value;
    }
    if (text[pointer] == '-'|| text[pointer] == '0'|| text[pointer] == '1'|| text[pointer] == '2'|| text[pointer] == '3'||
        text[pointer] == '4'|| text[pointer] == '5'|| text[pointer] == '6'|| text[pointer] == '7'|| text[pointer] == '8'||
        text[pointer] == '9'|| text[pointer] == '.')
    {
        Value* value =readNumber(text,pointer);
        return value;
    }
    if (text[pointer] == 'n')
    {
        Value* value = readNull(text, pointer);
        return value;
    }
    if (text[pointer] == 't')
    {
        Value* value = readTrue(text, pointer);
        return value;
    }
    if (text[pointer] == 'f')
    {
        Value* value = readFalse(text,pointer);
        return value;
    }
    else {
        throw std::exception("invalid char");
    }
}

Value* JSON::readObject(const std::string& text, int& pointer)
{
    ObjectValue* objectValue = new ObjectValue{};

    while (text[pointer]!='}')
    {
        pointer++;
        if (text[pointer] == '}') {
            break;
        }
        StringValue* key =((StringValue*)deserialize(text, pointer));
        pointer++;
        Value* value = deserialize(text,pointer);
        objectValue->append(KeyValuePair{key->get(),value});
        delete key;
    }
    pointer++;
    return objectValue;
}


Value* JSON::readArray(const std::string& text, int& pointer)
{
    ArrayValue* arrayValue = new ArrayValue{};
    while (text[pointer] != ']')
    {
        pointer++;
        if (text[pointer] == ']') {
            break;
        }
        Value* value = deserialize(text, pointer);
        arrayValue->append(value);
    }
    pointer++;
    return arrayValue;
}

Value* JSON::readText(const std::string& text, int& pointer)
{
    pointer++;
    std::string a;
    while (text[pointer] != '"')
    {
        if (text[pointer] == '\\') {
            break;
        }
        a.push_back(text[pointer]);
        pointer++;
    }
    pointer++;
    return new StringValue(a);
}

Value* JSON::readNumber(const std::string& text, int& pointer)
{
    std::string number;
    while (text[pointer] == '-' || text[pointer] == '0' || text[pointer] == '1' || text[pointer] == '2' || text[pointer] == '3' ||
        text[pointer] == '4' || text[pointer] == '5' || text[pointer] == '6' || text[pointer] == '7' || text[pointer] == '8' ||
        text[pointer] == '9' || text[pointer] == '.')
    {
        number.push_back(text[pointer]);
        pointer++;
    }
    return new NumberValue(round(stoi(number)));
}

Value* JSON::readNull(const std::string& text, int& pointer)
{
    pointer += 4;
    return new NullValue();
}

Value* JSON::readTrue(const std::string& text, int& pointer)
{
    pointer += 4;
    return new BoolValue(true);
}

Value* JSON::readFalse(const std::string& text, int& pointer)
{
    pointer += 5;
    return new BoolValue(false);  
}

std::string JSON::readFile(std::string name)
{
    int i = 0;
    char ch;
    std::string myArray;
    std::string pom;
    std::ifstream MyReadFile(name);
    getline(MyReadFile, myArray);
    return myArray;
}


void JSON::saveFile(std::string name, std::string json)
{
    std::ofstream myfile;
    myfile.open(name);
    myfile << json;
    myfile.close();
}

///////////////////////////////////////////////////////////////////////////////

template<typename T>
DynamicArray<T>::DynamicArray()
{
    size = 10;
    numberOfElements = 0;
    dynamicArray = new T[size];
}

template<typename T>
DynamicArray<T>::~DynamicArray()
{
    delete[] dynamicArray;
}

template<typename T>
void DynamicArray<T>::append(const T& element)
{
    dynamicArray[numberOfElements] = element;
    numberOfElements++;

    if (numberOfElements == size - 1) {
        expandArray();
    }
}

template<typename T>
void DynamicArray<T>::editElementAtIndex(T& element,int index) {
    dynamicArray[index] = element;
}

template<typename T>
const T& DynamicArray<T>::getElementAt(int index) const
{
    return dynamicArray[index];
}

template<typename T>
void DynamicArray<T>::removeElementAtIndex(int index) 
{
    
    if (dynamicArray[index] == dynamicArray[numberOfElements - 1])
    {
        dynamicArray[index] = nullptr;
    }
    else {
        dynamicArray[index] = dynamicArray[numberOfElements - 1];
    }
    numberOfElements--;
}

template<typename T>
int DynamicArray<T>::getSize() const
{
    return numberOfElements;
}

template<typename T>
void DynamicArray<T>::expandArray()
{
    T* tempArray = new T[size * 2];
    for (int i = 0; i < size * 2; i++)
    {
        if (i < size) {
            tempArray[i] = dynamicArray[i];
        }
    }
    size = size * 2;
    delete[] dynamicArray;
    dynamicArray = tempArray;
}

Value::~Value()
{
}
