/**
 * Tz-Shiuan Lin
 * ID: 1411593
 * FindPath.c
 * CS101-pa4
 */
#include <stdio.h>
#include <string.h>
#include "Graph.h"

#define MAX_LEN 1000

int string2Int(char* string){
    int len = (int)strlen(string);
    int result = 0;
    for(int i=0; i<len; i++){
        result = result * 10 + ( string[i] - '0' );
    }
    return result;
}

void string2Pair(char* string, int *first, int *second){
    *first = string2Int(strtok(string, " "));
    *second = string2Int(strtok(NULL, "\n"));
}

void viewShortPath(FILE* out, Graph G, int sou, int des){
    List L = newList();
    BFS(G, sou);
    int dist = getDist(G, des);
    if (dist == INF) {
        fprintf(out, "The distance from %d to %d is infinity\n", sou,des);
        fprintf(out, "No %d-%d path exists ", sou,des);
    } else {
        fprintf(out, "The distance from %d to %d is %d\n", sou,des,dist);
        fprintf(out, "A shortest %d-%d path is: ", sou,des);
        getPath(L, G, des);
        printList(out, L);
    }
    fprintf(out, "\n\n");
    freeList(&L);
}

int main(int argc, char* argv[]){
    
    // FileIO example from CS101 example directory.
    FILE *in, *out;
    char line[MAX_LEN];
    char tokenlist[MAX_LEN][MAX_LEN];
    char* token;
    int counter = 0;
    
    // check command line for correct number of arguments
    if(argc != 3){
        printf("Usage: %s <input file> <output file>\n", argv[0]);
        exit(1);
    }
    
    // open files for reading and writing
    in = fopen(argv[1], "r");
    out = fopen(argv[2], "w");
    if(in == NULL){
        printf("Unable to open file %s for reading\n", argv[1]);
        exit(1);
    }
    if(out==NULL){
        printf("Unable to open file %s for writing\n", argv[2]);
        exit(1);
    }
    
    /* read each line of input file, then count and print tokens */
    while( fgets(line, MAX_LEN, in) != NULL)  {
        // ignore line breaks.
        token = strcmp(line, "\n") == 0? line : strtok(line, "\n");
        tokenlist[counter][0] = '\0';
        while(token != NULL){
            strcat(tokenlist[counter], token);
            if (strcmp(line, "\n") == 0) {
                break;
            }
            strcat(tokenlist[counter], "\n");
            token = strtok(NULL, "");
        }
        //printf("tk[%d] %s", counter, tokenlist[counter]);
        counter++;
        if (counter == MAX_LEN) {
            printf("Reach array max. Program stop reading");
            break;
        }
    }
    
    char *pos;
    // remove line break
    if (( pos = strchr(tokenlist[0], '\n')) != NULL){
        *pos = '\0';
    }
    int vertex = string2Int(tokenlist[0]);
    int searchStart = 0;
    int first = 0, second = 0;

    Graph G = newGraph(vertex);
    for (int i = 1; i <= counter; i++) {
        string2Pair(tokenlist[i], &first, &second);
        if (first == 0 && second == 0) {
            searchStart = ++i;
            break;
        }
        addEdge(G, first, second);
    }
    printGraph(out, G);

    for (;;) {
        string2Pair(tokenlist[searchStart++], &first, &second);
        if (first == 0 && second == 0) {
            break;
        }
        viewShortPath(out, G, first, second);
    }
    
    fclose(in);
    fclose(out);
    freeGraph(&G);
    puts("DONE");
    return(0);
}
