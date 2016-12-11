//  Tz-Shiuan Lin
//  1411593
//  Lex.c
//  CS101-pa2
//


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "List.h"

#define MAX_LEN 160

void compareMove(List A, char tokenlist[MAX_LEN][MAX_LEN], int i){
    if (strcmp(tokenlist[i],tokenlist[get(A)]) > 0) {
        if (index(A) == (length(A) - 1)) {
            append(A, i);
        } else {
            moveNext(A);
            compareMove(A, tokenlist, i);
        }
    } else {
        insertBefore(A, i);
    }
}

int main(int argc, const char * argv[]) {
    
    // FileIO example from CS101 example directory.
    FILE *in, *out;
    char line[MAX_LEN];
    char tokenlist[MAX_LEN][MAX_LEN];
    char* token;
    int counter = 0;
    
    // check command line for correct number of arguments
    if( argc != 3 ){
        printf("Usage: %s <input file> <output file>\n", argv[0]);
        exit(1);
    }
    // open files for reading and writing
    in = fopen(argv[1], "r");
    out = fopen(argv[2], "w");
    if( in==NULL ){
        printf("Unable to open file %s for reading\n", argv[1]);
        exit(1);
    }
    if( out==NULL ){
        printf("Unable to open file %s for writing\n", argv[2]);
        exit(1);
    }
    
    /* read each line of input file, then count and print tokens */
    while( fgets(line, MAX_LEN, in) != NULL)  {
        token = strcmp(line, "\n") == 0? line : strtok(line, "\n");
        tokenlist[counter][0] = '\0';
        while( token!=NULL ){
            strcat(tokenlist[counter], token);
            if (strcmp(line, "\n") == 0) {
                break;
            }
            strcat(tokenlist[counter], "\n");
            token = strtok(NULL, "");
        }
        printf("tk[%d] %s", counter, tokenlist[counter]);
        counter++;
        if (counter == MAX_LEN) {
            printf("Reach array max. Program stop reading");
            break;
        }
    }
    
    /* insert into output */
    List A = newList();
    append(A, 0);
    for (int i = 1; i < counter; i++) {
        moveFront(A);
        compareMove(A, tokenlist, i);
    }

    for (moveFront(A); index(A) != -1; moveNext(A)) {
        fprintf(out, "%s", tokenlist[get(A)]);
    }
    
    freeList(&A);
    /* close files */
    fclose(in);
    fclose(out);

    return 0;
}

