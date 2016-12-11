/**
 * Tz-Shiuan Lin
 * ID: 1411593
 * List.h
 * CS101-pa5
 */

#ifndef List_h
#define List_h

#include<stdio.h>
#include <stdlib.h>

typedef struct ListObj* List;


/* Constructors-Destructors */
List newList(void);
void freeList(List *pL);

/* Access functions */
int length(List L);
int get_index(List L);
int front(List L);
int back(List L);  
int get(List L);
int equals(List A, List B);

/* Manipulation procedures */
void do_clear(List L);
void moveFront(List L);
void moveBack(List L);
void movePrev(List L);
void moveNext(List L);
void prepend(List L, int data);
void append(List L, int data);
void insertBefore(List L, int data);
void insertAfter(List L, int data);
void deleteFront(List L);
void deleteBack(List L);
void delete(List L);

/* Other operations */
void printList(FILE* out, List L);
List copyList(List L);

#endif /* List_h */
