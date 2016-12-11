/**
 * Tz-Shiuan Lin
 * ID: 1411593
 * Graph.c
 * CS101-pa5
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
    
    // New all arraies.
    G->colorA = calloc(n + 1, sizeof(int));
    G->distA = calloc(n + 1, sizeof(int));
    G->parentA = calloc(n + 1, sizeof(int));
    G->adjList = calloc(n + 1,sizeof(List));
    G->finishA = calloc(n + 1, sizeof(int));
    G->discoverA = calloc(n + 1, sizeof(int));


    // Initialize lists
    for (int i = 0; i <= n ; i++) {
        G->colorA[i] = WHITE;
        G->adjList[i] = newList();
        G->distA[i] = INF;
        G->parentA[i] = NIL;
        G->discoverA[i] = NIL;
        G->finishA[i] = NIL;
    }

    return G;
}
void freeGraph(Graph* pG){
    if (pG != NULL && *pG != NULL) {
        free((*pG)->colorA);
        free((*pG)->distA);
        free((*pG)->parentA);
        free((*pG)->discoverA);
        free((*pG)->finishA);
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
    if (u > G->order || 1 >u) {
        puts("Search index out of bound.");
        exit(1);
    }
    return G->parentA[u];
}

int getDiscover(Graph G, int u){
    if (u > G->order || 1 >u) {
        puts("Search index out of bound.");
        exit(1);
    }
    return G->discoverA[u];
}
int getFinish(Graph G, int u){
    if (u > G->order || 1 >u) {
        puts("Search index out of bound.");
        exit(1);
    }
    return G->finishA[u];
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
    if (counter == 0) {
        if (u == G->source) {
            return 0;
        } else {
            return INF;
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
    while (get_index(L) != -1) {
        
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
        do_clear(G->adjList[i]);
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
        while (get_index(G->adjList[x]) != -1) {
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

void visit(Graph G, List queue, int vertex, int *time){
    G->colorA[vertex] = GREY;
    G->discoverA[vertex] = (++(*time));
    moveFront(G->adjList[vertex]);
    while (get_index(G->adjList[vertex]) != -1) {
        if (G->colorA[get(G->adjList[vertex])] == WHITE) {
            G->parentA[get(G->adjList[vertex])] = vertex;
            visit(G, queue, get(G->adjList[vertex]), time);
        }
        moveNext(G->adjList[vertex]);
    }
    G->colorA[vertex] = BLACK;
    G->finishA[vertex] = (++(*time));
    enqueue(queue, vertex);
}

void DFS(Graph G, List Sequence){
    if (length(Sequence) != G->order) {
        printf("DFS Sequence out of bound");
        exit(1);
    }
    for (int i = 1; i <= G->order; i++){
        G->colorA[i] = WHITE;
        G->parentA[i] = NOP;
    }
    int time = 0;
    List queue = newList();
    moveFront(Sequence);
    while (get_index(Sequence) != -1) {
        if (G->colorA[get(Sequence)] == WHITE){
            visit(G, queue, get(Sequence), &time);
        }
        moveNext(Sequence);
    }
    do_clear(Sequence);
    for (int i = 1; i <= G->order; i++) {
        prepend(Sequence, dequeue(queue));
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

void printCell(FILE* out, Graph G){
    for(int i=1; i <= G->order; i++){
        fprintf(out, "%d: %2d %2d %2d\n", i, getDiscover(G, i), getFinish(G, i), getParent(G, i));
    }
}

Graph copyGraph(Graph G){
    Graph T = newGraph(G->order);
    for (int i = 0; i <= T->order ; i++) {
        T->colorA[i] = G->colorA[i];
        T->adjList[i] = copyList(G->adjList[i]);
        T->distA[i] = G->distA[i];
        T->parentA[i] = G->parentA[i];
        T->discoverA[i] = G->discoverA[i];
        T->finishA[i] = G->finishA[i];
    }
    return T;
}

Graph transpose(Graph G){
    Graph T = newGraph(G->order);
    for (int i = 0; i <= T->order ; i++) {
        T->colorA[i] = G->colorA[i];
        T->distA[i] = G->distA[i];
        T->parentA[i] = G->parentA[i];
        T->discoverA[i] = G->discoverA[i];
        T->finishA[i] = G->finishA[i];
        
        moveFront(G->adjList[i]);
        while (get_index(G->adjList[i]) != -1) {
            addArc(T, get(G->adjList[i]), i);
            moveNext(G->adjList[i]);
        }
    }
    return T;
}







