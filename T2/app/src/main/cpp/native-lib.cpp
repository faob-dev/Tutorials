extern "C" {
    int add(int a, int b) {
        return a + b;
    }

    int subtract(int a, int b) {
        return a - b;
    }

    int divide(int a, int b) {
        return a / b;
    }

    int multiply(int a, int b) {
        return a * b;
    }

    char* libName(){
        return (char *) "My Math Shared Library";
    }
}