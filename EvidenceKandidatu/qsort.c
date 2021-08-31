#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

#define N 20
int compar(const void *a, const void *b);

void main()
{
	int i,pole[N];
	//char *poleStr[5]={"sad","zewra","aafdsg","sfda","asdcv"};

	//qsort(poleStr,5,sizeof(char**),compar);

	srand(time(NULL));

	for(i=0;i<N;i++){
		pole[i]=rand()%100;
	}
	for(i=0;i<N;i++){
		printf("%d ",pole[i]);
	}

	printf("\n");
	qsort(pole,N,sizeof(int),compar);

	for(i=0;i<N;i++){
		printf("%d ",pole[i]);
	}

}
//int compar(const int *a, const int *b){
//
//	
//	if((*a)%2!=0)
//		return -1;
//	else 
//		return 1;
//}
int compar(const int *a, const int *b){

	if(*a>*b)
		return -1;
	else if(*a<*b)
		return 1;
	else
		return 0;
}
//int compar(const char **a, const char **b){
//
//	 return strcmp( *a, *b );
//}	