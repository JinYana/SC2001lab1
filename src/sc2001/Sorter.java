package sc2001;
public class Sorter {
    public long comparisons = 0;
    public void resetComparisons(){
        comparisons = 0;
    }
    public void MergeSort(int[]arr, int left, int right){
        if(left < right){
            int mid = (left + right) / 2;
            MergeSort(arr, left, mid);
            MergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);

        }
    }

    public void HybridSort(int[] arr, int left, int right, int S){

        if((right - left + 1) <= S){
            InsertionSort(arr, left,right);
        }else{
            int mid = (left + right) / 2;
            HybridSort(arr, left, mid, S);
            HybridSort(arr, mid + 1, right, S);
            merge(arr,left,mid, right);
        }




    }

    public void InsertionSort(int[] arr, int left, int right){
        for(int i = left + 1; i <= right; i++ ){
            int key = arr[i];
            int j = i - 1;

            while(j >= left){
                comparisons++;
                if(arr[j] > key){
                    arr[j + 1] = arr[j];
                    j--;
                }
                else{
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }

    private void merge(int[] arr, int left, int mid, int right){
       int n1 = mid - left + 1;
       int n2 = right - mid;

       int[] L = new int[n1];
       int[] R = new int[n2];

       System.arraycopy(arr, left, L, 0, n1);
       System.arraycopy(arr, mid + 1, R, 0, n2);

       int i = 0, j =0, k = left;

       while(i < n1 && j < n2){
           comparisons++;
           if(L[i] < R[j]){
               arr[k] = L[i];
               i++;
               k++;
           }
           else{
               arr[k] = R[j];
               j++;
               k++;
           }
       }

       while(i < n1){
           arr[k] = L[i];
           i++;
           k++;

       }

        while(j < n2){
            arr[k] = R[j];
            j++;
            k++;

        }
    }
}
