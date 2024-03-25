#include <stdio.h>

void main(int argc, char* args[]) {

    int c1, c2;
    FILE *f1, *f2, *out;

    if((argc == 3)
       && (f1 = fopen(args[1], "r"))
       && (f2 = fopen(args[2], "r"))
       && (out = fopen("out", "w")))
    {
        while(((c1 = getc(f1)) != EOF) && ((c2 = getc(f2)) != EOF)) {
            printf("%c:%d\n", c1^c2, c1^c2);
            putc((c1^c2), out);
        }
    }
    else printf("Error\n");
    fclose(f1);
    fclose(f2);
    fclose(out);
}
