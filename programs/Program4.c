#include<stdio.h>
#include<stdlib.h>
 
int main(){
   int a;
   a = 0;
   while(a < 10){
   if(a < 5)
   {a++;}
   else
   {a += 2;}
   printf("Podaj liczbe");
   }
   int b;
   b = 2;
   a = a/b;
   printf("Liczba a wynosi %i", a);
   for(int i = 0; i < 10; i++){
   printf("Liczba mniejsza od 10");
   }
   return 0;
};
