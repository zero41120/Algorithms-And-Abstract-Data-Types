/**
 * Tz-Shiuan Lin
 * ID: 1411593
 * Graph.h
 * CS101-pa5
 */

#ifndef Graph_h
#define Graph_h

#include <stdio.h>
#include <limits.h>
#include "List.h"

#define NIL (-1)
#define NOP (0)
#define INF (INT_MAX)

typedef enum {
    WHITE, GREY, BLACK
} e_color;
struct GraphObj{
    List *adjList;
    int *colorA;
    int *parentA;
    int *distA;
    int *discoverA;
    int *finishA;
    int size;   // Number of edges
    int order;  // Number of vertices
    int source; // Most recent BFSed vertex
};

typedef struct GraphObj* Graph;

/*** Constructors-Destructors ***/
Graph newGraph(int n);
void freeGraph(Graph* pG);
/*** Access functions ***/
int getOrder(Graph G);
int getSize(Graph G);
int getSource(Graph G);
int getParent(Graph G, int u);
int getDiscover(Graph G, int u);
int getFinish(Graph G, int u);
int getDist(Graph G, int u);
void getPath(List L, Graph G, int u);
/*** Manipulation procedures ***/
void makeNull(Graph G);
void addEdge(Graph G, int u, int v);
void addArc(Graph G, int u, int v);
void BFS(Graph G, int s);
void DFS(Graph G, List S);
/*** Other operations ***/
void printGraph(FILE* out, Graph G);
void printCell(FILE* out, Graph G);
Graph copyGraph(Graph G);
Graph transpose(Graph G);

#endif /* Graph_h */







