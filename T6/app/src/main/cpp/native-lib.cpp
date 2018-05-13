
extern "C"
void toGrayScale(int *pixels, int len) {
    for (int i = 0; i < len; i++) {
        //getting individual color values from each pixel
        int A = (pixels[i] >> 24) & 0xFF;
        int R = (pixels[i] >> 16) & 0xFF;
        int G = (pixels[i] >> 8) & 0xFF;
        int B = pixels[i] & 0xFF;

        //averaging Red, Green and Blue value to get gray scale value
        int gray = (R + G + B) / 3;

        //assign same gray value to Red, Green and Blue.
        //alpha value is unchanged
        pixels[i] = (A << 24) | (gray << 16) | (gray << 8) | gray;
    }
}