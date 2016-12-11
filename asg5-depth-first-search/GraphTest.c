/**
 * Tz-Shiuan Lin
 * ID: 1411593
 * GraphTest.c
 * CS101-pa5
 */

#include <stdio.h>
#include "Graph.h"

int main(int argc, char* argv[]){
    // Modified from provided file
    // Three new function: DFS, transpose, copyGraph
    int i, n=8;
    List S = newList();
    Graph G = newGraph(n);
    Graph T=NULL, C=NULL;
    
    for(i=1; i<=n; i++) append(S, i);
    
    // Data from pa5 assignment sheet
    addArc(G, 1,2);
    addArc(G, 2,3);
    addArc(G, 2,5);
    addArc(G, 2,6);
    addArc(G, 3,4);
    addArc(G, 3,7);
    addArc(G, 4,3);
    addArc(G, 4,8);
    addArc(G, 5,1);
    addArc(G, 5,6);
    addArc(G, 6,7);
    addArc(G, 7,6);
    addArc(G, 7,8);
    addArc(G, 8,8);
    printGraph(stdout, G);
    //    1: 2
    //    2: 3 5 6
    //    3: 4 7
    //    4: 3 8
    //    5: 1 6
    //    6: 7
    //    7: 6 8
    //    8: 8
    
    DFS(G, S);
    fprintf(stdout, "DFS\n");
    fprintf(stdout, "x:  d  f  p\n");
    for(i=1; i<=n; i++){
        fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(G, i), getFinish(G, i), getParent(G, i));
    }
    fprintf(stdout, "\n");
    printList(stdout, S);
    fprintf(stdout, "\n");
    
    T = transpose(G);
    C = copyGraph(G);
    fprintf(stdout, "Show copy\n");
    printGraph(stdout, C);
    fprintf(stdout, "Show transpose\n");
    printGraph(stdout, T);
    fprintf(stdout, "\n");
    
    DFS(T, S);
    fprintf(stdout, "DFS on transpose\n");
    fprintf(stdout, "x:  d  f  p\n");
    for(i=1; i<=n; i++){
        fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(T, i), getFinish(T, i), getParent(T, i));
    }
    fprintf(stdout, "\n");
    printList(stdout, S);
    fprintf(stdout, "\n");
    
    freeList(&S);
    freeGraph(&G);
    freeGraph(&T);
    freeGraph(&C);
    return(0);
}