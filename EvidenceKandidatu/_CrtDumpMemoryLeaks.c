#define _CRTDBG_MAP_ALLOC	
#include <crtdbg.h> 
#include <stdio.h>
#include <stdlib.h>


int main (int agrc, char *argv[]){
	int i, *pole=(int*)calloc(10,4);

	for(i=0;i<10;i++)
		pole[i]=i;
	//free(pole);
  	//pokud neprovedeme dealokaci, ve výstupu Output bude info pamìti, která nebyla dealokována 

	if(_CrtDumpMemoryLeaks()!=0){
		printf("Nebyla provedena dealokace");
	}
	return 0;
}

