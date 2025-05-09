class MedianFinder {

    private final int[] arr = new int[200002];
    int lower = -1;
    int lowerCount = 1;
    int upper = -1;
    int upperCount = 1;
    int counter = 0;

    public MedianFinder() {

    }
    
    public void addNum(int num) {
        int index = 100000 + num;
        arr[index]++;
        counter++;
        if (counter == 1) {
            lower = index;
            upper = index;
            return;
        }

        if (lower == upper) {
            if (lowerCount == upperCount) { // kilepunk
                if (index >= lower) {
                    upperRight();
                } else {
                    lowerLeft();
                }
            } else {    // hozzalepunk
                if (index >= lower) {
                    lowerCount = upperCount;
                } else {
                    upperCount = lowerCount;
                }
            }
        } else { // lower != upper
            if (index >= upper) {
                lower = upper;
                lowerCount = 1;
                return;
            }
            if (index == lower) {
                upper = lower;
                upperCount = arr[upper];
                lowerCount = arr[lower];
                return;
            }
            if (index < lower) {
                upper = lower;
                upperCount = arr[upper];
                return;
            }
            // index > lower && index < upper
            lower = index;
            lowerCount = 1;
            upper = index;
            upperCount = arr[upper];
        }
    }
    
    private void upperRight() {
        if (upperCount < arr[upper]) {
            upperCount++;
            return;
        }
        upper++;
        while (arr[upper] == 0) {
            upper++;
        }
        upperCount = 1;
    }

    private void lowerLeft() {
        if (lowerCount > 1) {
            lowerCount--;
            return;
        }
        lower--;
        while (arr[lower] == 0) {
            lower--;
        }
        lowerCount = arr[lower];
    }

    public double findMedian() {
        if (lower == upper) {
            return lower - 100000;
        }
        return ((double)(lower + upper - 100000 * 2)) / 2;
    }
}
