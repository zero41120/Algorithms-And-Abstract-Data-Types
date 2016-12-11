/**
 * Tz-Shiuan Lin
 * ID: 1411593
 * Graph.c
 * CS101-pa4
 */

#include "Graph.h"

/*** Constructors-Destructors ***/
Graph newGraph(int n){
    // Declare a Graph (struct pointer)
    Graph G = malloc(sizeof(struct GraphObj));
    
    // #vertices = n, #edge = 0, current vertex = nil
    G->order = n;
    G->size = 0;
    G->source = NIL;
    
    // New parent, color, distance, and adjList;
    G->colorA = calloc(n + 1, sizeof(int));
    G->distA = calloc(n + 1, sizeof(int));
    G->parentA = calloc(n + 1, sizeof(int));
    G->adjList = calloc(n + 1,sizeof(List));

    // Initialize lists
    for (int i = 0; i <= n ; i++) {
        G->colorA[i] = WHITE;
        G->distA[i] = INF;
        G->parentA[i] = NIL;
        G->adjList[i] = newList();
    }

    return G;
}
void freeGraph(Graph* pG){
    if (pG != NULL && *pG != NULL) {
        free((*pG)->colorA);
        free((*pG)->distA);
        free((*pG)->parentA);
        for(int i = 0; i <= (*pG)->order; i++){
            freeList(&(*pG)->adjList[i]);
        }
        free((*pG)->adjList);
        free(*pG);
        *pG = NULL;
    }
}
/*** Access functions ***/
int getOrder(Graph G){
    return G->order;
}
int getSize(Graph G){
    return G->size;
}
int getSource(Graph G){
    return G->source;
}
int getParent(Graph G, int u){
    return G->parentA[u];
}
int getDist(Graph G, int u){
    if (G->source == NIL) {
        puts("Error: BFS is not called");
        exit(1);
    }
    int counter = 0;
    int trace = G->parentA[u];
    while (trace != NIL) {
        trace = G->parentA[trace];
        counter++;
    }
    if(counter == 0){
	if(u == G->source){
	    counter = 0;
	} else {
	    counter = INF;
	}
    }
    return counter;
}
void getPath(List L, Graph G, int u){
    if (G->source == NIL) {
        puts("Error: BFS is not called");
        exit(1);
    }
    int trace = u;
    while (trace != NIL) {
        prepend(L, trace);
        trace = G->parentA[trace];
    }
}

/*** Manipulation procedures ***/
void insertAdjList(List L, int data){
    if (length(L) == 0) {
        append(L, data);
        return;
    }
    moveFront(L);
    int trace = get(L);
    if (data < trace) {
        prepend(L, data);
        return;
    }
    while (index(L) != -1) {
        
        if (trace < data && data < get(L)) {
            insertBefore(L, data);
            return;
        }
        trace = get(L);
        moveNext(L);
    }
    append(L, data);
}
void makeNull(Graph G){
    for(int i = 0; i <= G->order; i++){
        clear(G->adjList[i]);
    }
    G->size = 0;
}
void addEdge(Graph G, int u, int v){
    if (u > G->order || 1 >u || v > G->order || 1 > v) {
        puts("Insert edge out of bound.");
        exit(1);
    }
    G->size++;
    insertAdjList(G->adjList[u], v);
    insertAdjList(G->adjList[v], u);
}
void addArc(Graph G, int u, int v){
    if (u > G->order || 1 >u || v > G->order || 1 > v) {
        puts("Insert arc out of bound.");
        exit(1);
    }
    insertAdjList(G->adjList[u], v);
}

/**
 * This function alters the parent list to represent a BFS tree.
 */
int dequeue(List L){
    moveFront(L);
    int source = get(L);
    delete(L);
    return source;
}
void enqueue(List L, int data){
    append(L, data);
}
void BFS(Graph G, int s){
    for (int i = 1; i <= G->order; i++){
        G->colorA[i] = WHITE;
        G->distA[i] = INF;
        G->parentA[i] = NIL;
    }
    G->source = s;
    G->colorA[s] = GREY;
    G->distA[s] = 0;
    G->parentA[s] = NIL;
    List queue = newList();
    enqueue(queue, s);
    while (length(queue) != 0) {
        int x = dequeue(queue);
        moveFront(G->adjList[x]);
        while (index(G->adjList[x]) != -1) {
            int y = get(G->adjList[x]);
            if (G->colorA[y] == WHITE) {
                G->colorA[y] = GREY;
                G->distA[y] = G->distA[x] +1;
                G->parentA[y] = x;
                enqueue(queue, y);
            }
            moveNext(G->adjList[x]);
        }
        G->colorA[x] = BLACK;
    }
    freeList(&queue);
}
/*** Other operations ***/
void printGraph(FILE* out, Graph G){
    if(out == NULL){
        printf("Output file not found");
        exit(1);
    }
    for (int i = 1; i <= G->order; i++) {
        fprintf(out, "%d: ", i);
        printList(out, G->adjList[i]);
        fprintf(out, "\n");
    }
    fprintf(out, "\n");
}









