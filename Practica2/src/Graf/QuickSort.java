package Graf;

public class QuickSort {
    public static void quickSort(Vertex[] vertexs, int i, int j){
        if(i < j){
            int p = partition(vertexs, i, j);
            quickSort(vertexs, i, p);
            quickSort(vertexs, p + 1, j);
        }
    }

    public static int partition(Vertex[] vertexs, int i, int j){
        int left = i;
        int right = j;
        int mid = (i + j) / 2;
        Vertex pivot = vertexs[mid];
        while(true){
            while(vertexs[left].id < pivot.id){
                left++;
            }
            while(vertexs[right].id > pivot.id){
                right--;
            }
            if(left >= right){
                return right;
            }
            Vertex aux = vertexs[right];
            vertexs[right] = vertexs[left];
            vertexs[left] = aux;
            left++;
            right--;
        }
    }
}
