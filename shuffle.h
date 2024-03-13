#include <stdlib.h>
#include <time.h>

/*Modified Fisher-Yates shuffle.*/
void shuffle(int a[], int n) {

    int i;
    srand(time(NULL));
    for(i=n-1; i>0; i--) {
        int r = rand()%i;
        int t = a[i];
        a[i] = a[r];
        a[r] = t;
    }
}
