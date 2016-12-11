/**
 * Tz-Shiuan Lin
 * ID: 1411593
 * GraphTest.c
 * CS101-pa3
 */

#include <stdio.h>
#include "Graph.h"
int main(int argc, char* argv[]){
    Graph G = NULL;
    
    // Build graph G
    G = newGraph(9);
    List L = newList();
    List K = newList();
    List J = newList();
    List M = newList();
    
    // Location near home:
    addEdge(G, 1, 3);
    addEdge(G, 5, 8);
    addEdge(G, 5, 7);

    addEdge(G, 1, 4);
    addEdge(G, 1, 2);
    addEdge(G, 6, 7);

    addEdge(G, 1, 5);
    addEdge(G, 3, 7);
    
    addEdge(G, 3, 4);
    addEdge(G, 4, 5);
    addEdge(G, 2, 6);
    
    addEdge(G, 5, 9);
    
    addEdge(G, 6, 8);
    addEdge(G, 8, 9);
    printf("Number of vertex (order) should be 9: %d\n", getOrder(G));
    printf("Number of edge (size) should be 14: %d\n", getSize(G));
    printGraph(stdout, G);
    
    BFS(G, 1);
    printf("Last BFS source should be 1: %d\n", getSource(G));
    printf("Parent is 5: %d\n", getParent(G, 9));
    printf("Parent is 1: %d\n", getParent(G, 5));
    printf("Parent is -1(NIL): %d\n", getParent(G, 1));

    

    getPath(L, G, 9);
    printf("Path is 1 5 9: ");
    printList(stdout, L);
    printf("\n");
    printf("Dist is 2: %d\n", getDist(G, 9));
    
    BFS(G, 7);
    printf("Last BFS source should be 7: %d\n", getSource(G));
    printf("Parent is -1(NIL): %d\n", getParent(G, 7));
    
    getPath(K, G, 4);
    printf("Path is 7 3 4: ");
    printList(stdout, K);
    printf("\n");
    printf("Dist is 2: %d\n", getDist(G, 4));

    makeNull(G);
    addArc(G, 1, 2);
    addArc(G, 1, 3);
    addArc(G, 2, 6);
    addArc(G, 3, 4);
    addArc(G, 5, 4);
    addArc(G, 5, 2);
    addArc(G, 5, 6);
    printGraph(stdout, G);
    BFS(G, 1);
    printf("Last BFS source should be 1: %d\n", getSource(G));
    printf("Number of vertex (order) should be 9: %d\n", getOrder(G));
    printf("Number of edge (size) should be 7: %d\n", getSize(G));
    getPath(J, G, 9);
    printf("Path is -1(NIL): ");
    printList(stdout, J);
    getPath(M, G, 4);
    printf("Path is 1 3 4: ");
    printList(stdout, M);
    
    freeList(&M);
    freeList(&J);
    freeList(&L);
    freeList(&K);
    freeGraph(&G);
    
    return(0);
}