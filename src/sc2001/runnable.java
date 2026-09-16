package sc2001;


import java.util.Arrays;
import java.util.Random;

public class runnable {

    public static int[] generateData(int size, int x){
        int[] arr = new int[size];

        Random rand = new Random();
        for(int i = 0; i < size; i++){
            arr[i] = rand.nextInt(x);
        }
        return arr;
    }
    public static void main(String[] args) {
        int maxRandomValue_X = 100000;
        int fixedS = 20;
        Sorter sorter = new Sorter();
        int[] rangelist = {1000, 10000, 100000, 1000000, 10000000};

        for(int i = 0; i < rangelist.length; i++){
            int[] testData = generateData(rangelist[i], maxRandomValue_X);
            sorter.resetComparisons();
            sorter.HybridSort(testData, 0, testData.length -1, fixedS);
            System.out.println((rangelist[i] + "," + sorter.comparisons));
        }

        System.out.println("\nFixed n, Varying S");
        int fixedN = 100000;
        int[] originalData = generateData(fixedN, maxRandomValue_X);
        System.out.println("S,KeyComparisons");

        for (int S = 2; S <= 100; S += 2) {
            int[] arrayCopy = Arrays.copyOf(originalData, originalData.length);
            sorter.resetComparisons();
            sorter.HybridSort(arrayCopy, 0, arrayCopy.length - 1, S);
            System.out.println(S + "," + sorter.comparisons);
        }

        System.out.println("\nCPU Time & Comparison against Original Merge Sort");
        int tenMillion = 10000000;
        int[] massiveArray = generateData(tenMillion, maxRandomValue_X);
        int[] massiveArrayCopy = Arrays.copyOf(massiveArray, massiveArray.length);


        int optimalS = 24;


        sorter.resetComparisons();
        long startTime = System.nanoTime();
        sorter.MergeSort(massiveArray, 0, massiveArray.length - 1);
        long endTime = System.nanoTime();
        System.out.println("Original Merge Sort - Comparisons: " + sorter.comparisons +
                " | CPU Time (ms): " + (endTime - startTime) / 1000000);


        sorter.resetComparisons();
        startTime = System.nanoTime();
        sorter.HybridSort(massiveArrayCopy, 0, massiveArrayCopy.length - 1, optimalS);
        endTime = System.nanoTime();
        System.out.println("Hybrid Sort (S=" + optimalS + ") - Comparisons: " + sorter.comparisons +
                " | CPU Time (ms): " + (endTime - startTime) / 1000000);


        System.out.println("\nPart D: CPU Time Benchmark for Specific Thresholds");

// Generate one master array so every threshold sorts the exact same numbers
        int massiveSize = 10000000;
        int[] masterArray = generateData(massiveSize, maxRandomValue_X);

        int[] testThresholds = {2, 12, 24, 48, 60, 70, 80, 90, 100};

// JVM Warm-up (Crucial for accurate CPU timing)
        int[] warmupArray = Arrays.copyOf(masterArray, 100000);
        sorter.HybridSort(warmupArray, 0, warmupArray.length - 1, 10);

        for (int S : testThresholds) {
            // 1. Create a fresh, unsorted copy for a fair race
            int[] raceArray = Arrays.copyOf(masterArray, masterArray.length);

            // 2. Reset counter and start timer
            sorter.resetComparisons();
            long startsTime = System.nanoTime();

            // 3. Run the hybrid sort
            sorter.HybridSort(raceArray, 0, raceArray.length - 1, S);

            // 4. Stop timer and calculate milliseconds
            long endsTime = System.nanoTime();
            long durationInMillis = (endsTime - startsTime) / 1000000;

            // 5. Output results
            System.out.println("Threshold S = " + S +
                    "\t| CPU Time: " + durationInMillis + " ms" +
                    "\t| Comparisons: " + sorter.comparisons);
        }

    }

}
