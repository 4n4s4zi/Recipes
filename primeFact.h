#include <stdio.h>

/*Prints the prime factorization of ints greater than 1*/
void primeFact(int n) {

    int i;

    if(n<2) {
        printf("Error: Input is less than 2.\n");
        return;
    }
    printf("%d: ", n);
    for(i=2; n!=1; i++) {
        while(!(n%i)) {
            printf("%d ", i);
            n/=i;
        }
    }
    printf("\n");
}
