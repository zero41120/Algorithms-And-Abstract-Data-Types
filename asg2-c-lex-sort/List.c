//  Tz-Shiuan Lin
//  1411593
//  List.c
//  CS101-pa2
//


#include "List.h"

typedef struct NodeObj{
    struct NodeObj *beforeNode;
    struct NodeObj *afterNode;
    int data;
} NodeObj;

typedef NodeObj* Node;

typedef struct ListObj{
    Node front;
    Node back;
    Node cursor;
    int length;
    int index;
} ListObj;


/* Constructors-Destructors */
List newList(void){
    List new_list = malloc(sizeof(ListObj));
    new_list->back = NULL;
    new_list->front = NULL;
    new_list->cursor = NULL;
    new_list->length = 0;
    new_list->index = -1;
    
    return new_list;
}
void freeList(List *pL){
    
    if(pL != NULL && *pL != NULL) {
        while (length(*pL) > 0) {
            moveFront(*pL);
            delete(*pL);
        }
        free(*pL);
    }
}

/* Access functions */
int length(List L){
    return L->length;
}

int index(List L){
    return L->index;
}

int front(List L){
    if (L->length <= 0) {
        printf("Empty_list_error : List is empty");
        exit(0);
    } else {
        return L->front->data;
    }
}

int back(List L){
    if (L->length <= 0) {
        printf("Empty_list_error : List is empty");
        exit(0);
    }
    return L->back->data;
}

int get(List L){
    if (L->length <= 0 && L->index < 0) {
        printf("Cursor_error : Cursor is not defined");
        exit(0);
    }
    return L->cursor->data;
}

int equals(List A, List B){
    if (A->length != B->length) {
        return 0;
    }
    moveFront(A);
    moveFront(B);
    while (index(A) != -1){
        if (get(A) == get(B)) {
            moveNext(A);
            moveNext(B);
        } else {
            return 0;
        }
    }
    return 1;
}

/* Manipulation procedures */
void clear(List L){
    while (length(L) > 0) {
        moveFront(L);
        delete(L);
    }
    L->cursor = NULL;
    L->back = NULL;
    L->front = NULL;
    L->length = 0;
    L->index = -1;
}

void moveFront(List L){
    if (L->length > 0) {
        L->cursor = L->front;
        L->index = 0;
    }
}

void moveBack(List L){
    L->cursor = L->back;
    L->index = L->length - 1;
}

void movePrev(List L){
    if (L->length < 0 || L->index == 0) {
        L->cursor = NULL;
        L->index = -1;
    } else {
        L->cursor = L->cursor->beforeNode;
        L->index = L->index -1;
    }
}
void moveNext(List L){
    if (L->length < 0 || L->index == L->length -1) {
        L->cursor = NULL;
        L->index = -1;
    } else {
        L->cursor = L->cursor->afterNode;
        L->index = L->index + 1;
    }
    
}
void prepend(List L, int data){
    Node newFront = malloc(sizeof(NodeObj));
    newFront->data = data;
    newFront->beforeNode = NULL;
    newFront->afterNode = L->front;
    if (L->length > 0) {
        L->front->beforeNode = newFront;
    } else {
        L->back = newFront;
    }
    L->front = newFront;
    if (L->index == 0) {
        L->cursor = newFront;
    } else if(L->index > 0){
        L->index = L->index + 1;
    }
    L->length = L->length + 1;
}
void append(List L, int data){
    Node newBack = malloc(sizeof(NodeObj));
    newBack->data = data;
    newBack->afterNode = NULL;
    newBack->beforeNode = L->back;
    if (L->length > 0) {
        L->back->afterNode = newBack;
    } else {
        L->front = newBack;
    }
    L->back = newBack;
    L->length = L->length + 1;
}
void insertBefore(List L, int data){
    if (L->index == -1) {
        printf("Cursor_error : Cursor is not defined");
        exit(0);
    }
    Node newBefore = malloc(sizeof(NodeObj));
    newBefore->afterNode = L->cursor;
    newBefore->beforeNode = L->cursor->beforeNode;
    newBefore->data = data;
    if (L->index != 0) {
        L->cursor->beforeNode->afterNode = newBefore;
    } else {
        L->front = newBefore;
    }
    L->cursor->beforeNode = newBefore;
    L->index = L->index + 1;
    L->length = L->length + 1;
}

void insertAfter(List L, int data){
    if (L->index == -1) {
        printf("Cursor_error : Cursor is not defined");
        exit(0);
    }
    Node newAfter = malloc(sizeof(NodeObj));
    newAfter->beforeNode = L->cursor;
    newAfter->afterNode = L->cursor->afterNode;
    newAfter->data = data;
    if (L->index == (L->length - 1)) {
        L->front = newAfter;
    }
    L->cursor->afterNode = newAfter;
    L->length = L->length + 1;
}
void deleteFront(List L){
    L->front->afterNode->beforeNode = NULL;
    L->front = L->front->afterNode;
    if (L->index == 0) {
        L->index = -1;
        L->cursor = NULL;
    }
    L->length = L->length - 1;
}
void deleteBack(List L){
    if (L->index == (L->length - 1)) {
        L->index = -1;
        L->cursor = NULL;
    }
    L->back->beforeNode->afterNode = NULL;
    L->back = L->back->beforeNode;
    L->length = L->length - 1;
}
void delete(List L){
    if (L->index == -1) {
        printf("Cursor_error : Cursor is not defined");
        exit(0);
    }
    Node before = NULL;
    Node after = NULL;
    if (L->cursor->beforeNode != NULL) {
        before = L->cursor->beforeNode;
    }
    if (L->cursor->afterNode) {
        after = L->cursor->afterNode;
    }
    if (before != NULL) {
        before->afterNode = after;
    }
    if (after != NULL) {
        after->beforeNode = before;
    }
    free(L->cursor);
    if (index(L) == 0) {
        L->front = after;
    }
    
    L->cursor = NULL;
    L->index = -1;
    L->length = L->length - 1;
}

/* Other operations */
void printList(FILE* out, List L){
    if(out == NULL){
        printf("Output file not found");
    }
    moveFront(L);
    while (L->index != -1) {
        fprintf(out, "%d ", L->cursor->data);
        moveNext(L);
    }
}

List copyList(List L){
    List newList = malloc(sizeof(ListObj));
    newList->back = NULL;
    newList->front = NULL;
    newList->cursor = NULL;
    newList->length = 0;
    newList->index = -1;

    Node oldNode = L->front;
    while (oldNode != NULL){
        append(newList, oldNode->data);
        oldNode = oldNode->afterNode;
    }
    return newList;
}

List concatList(List A, List B){
    List newList = (List)malloc(sizeof(List));
    Node newNode = malloc(sizeof(NodeObj));
    moveFront(A);
    newNode->data = A->cursor->data;
    newNode->afterNode = newNode->beforeNode = NULL;
    moveNext(A);
    while (A->index != -1) {
        append(newList, A->cursor->data);
        moveNext(A);
    }
    moveFront(B);
    while (B->index != -1) {
        append(newList, B->cursor->data);
        moveNext(B);
    }
    newList->cursor = NULL;
    newList->index = -1;
    return  newList;
}


/* NODE FUNCTION */
Node newNode(int data){
    Node newNode = malloc(sizeof(NodeObj));
    return newNode;
}










