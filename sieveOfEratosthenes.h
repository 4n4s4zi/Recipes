#include <stdlib.h>

/*~~~~~~~~~~~~~~~~~~~~~
  Sieve of Eratosthenes
  ~~~~~~~~~~~~~~~~~~~~~
  Prints all prime numbers less than n
*/
void SOE(unsigned long n) {

    unsigned long i, j, *a = (unsigned long*) malloc(n*sizeof(unsigned long));

    for(i=0; i<n; i++) a[i]=i;
    for(i=2; i<n; i++) {
        if(a[i]) {
            for(j=i*i; j<n; j+=i) a[j]=0;
        }
    }
    for(i=2; i<n; i++) {
        if(a[i]) printf("%lu\n", a[i]);
    }
    free(a);
}
