/**
 * Tz-Shiuan Lin
 * ID: 1411593
 * Graph.c
 * CS101-pa5
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
        fprintf(out, "No %d-%d path exists", sou,des);
    } else {
        fprintf(out, "The distance from %d to %d is %d\n", sou,des,dist);
        fprintf(out, "A shortest %d-%d path is: ", sou,des);
        getPath(L, G, des);
        printList(out, L);
    }
    fprintf(out, "\n\n");
    freeList(&L);
}

void getSCC(Graph G, List toShow, int root, List topo){
    append(toShow, root);
    moveFront(topo);
    while (get_index(topo) != -1) {
        int i = get(topo);
        int parent = getParent(G, i);
        if (parent == root) {
            getSCC(G, toShow, i, topo);
            moveBack(topo);// break;
        }
        moveNext(topo);
    }
}

void viewSCC(FILE* out, Graph G){
    List Sequnece = newList();
    for(int i=1; i <= G->order; i++) {
        append(Sequnece, i);
    }
    DFS(G, Sequnece);
    fprintf(stdout, "DFS(G) on 1 to order\n");
    fprintf(stdout, "x:  d  f  p\n");
    int i, n = G->order;
    for(i=1; i<=n; i++){
        fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(G, i), getFinish(G, i), getParent(G, i));
    }
    Graph T = transpose(G);
    DFS(T, Sequnece);
    fprintf(stdout, "G topo");
    printList(stdout, Sequnece);
    fprintf(stdout, "DFS(T) on G topo\n");
    fprintf(stdout, "x:  d  f  p\n");
    for(i=1; i<=n; i++){
        fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(G, i), getFinish(G, i), getParent(G, i));
    }
    int SCCCount = 0;
    for (int i = 1; i <= G->order; i++) {
        int x = getParent(T, i);
        if (x == NOP) {
            SCCCount++;
        }
    }
    fprintf(out, "\nG contains %d strongly connected components:\n", SCCCount);
    
    int counter = 0;
    List mainLoop = copyList(Sequnece);
    moveBack(mainLoop);
    while (get_index(mainLoop) != -1) {
        int i = get(mainLoop);
        int root = getParent(T, i);
        if (root == NOP) {
            List toShow = newList();
            getSCC(T, toShow, i, Sequnece);
            fprintf(out, "Components %d: ", ++counter);
            printList(out, toShow);
            fprintf(out, "\n");
            freeList(&toShow);
        }
        movePrev(mainLoop);
    }
    freeList(&Sequnece);
    freeList(&mainLoop);
    freeGraph(&T);
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
        fclose(in);
        fclose(out);
        exit(1);
    }
    if(out==NULL){
        printf("Unable to open file %s for writing\n", argv[2]);
        fclose(in);
        fclose(out);
        exit(1);
    }
    
    //read each line of input file
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
    int first = 0, second = 0;
    
    Graph G = newGraph(vertex);
    for (int i = 1; i <= counter; i++) {
        string2Pair(tokenlist[i], &first, &second);
        if (first == 0 && second == 0) {
            break;
        }
        addArc(G, first, second);
    }
    fprintf(out,"Adjacency list representation of G:\n");
    printGraph(out, G);
    viewSCC(out, G);
    
    fclose(in);
    fclose(out);
    freeGraph(&G);
    puts("DONE");
    return(0);
}
