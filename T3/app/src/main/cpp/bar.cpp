#include <stdlib.h>
#include <time.h>

extern "C" {
    int randomNumber() {
        srand((unsigned int) time(NULL));
        return rand() % 100 + 1;
    }
}