import java.util.*;
import java.util.concurrent.*;
public class ParallelFilter extends RecursiveTask<double[]>{
  int fSize;
  int lo;
  int hi;
  int border;
  double[] arr;
  double[] newArr;
  public static int SEQUENTIAL_CUTOFF;

  public ParallelFilter(int lo, int hi, double[] arr, double[] newArr, int fSize, int border, int SC){
    this.fSize=fSize;
    this.border=border;
    this.hi=hi;
    this.lo=lo;
    this.arr=arr;
    this.newArr=newArr;
    this.SEQUENTIAL_CUTOFF=SC;
  }

  public double findMedian(ArrayList<Double> checker){
// Sort list and return the middle element
    Collections.sort(checker);
    return checker.get((fSize-1)/2);
  }

  public double[] compute(){

    if(hi-lo<=SEQUENTIAL_CUTOFF){
      ArrayList<Double> checker = new ArrayList<Double>();
  // for each element (barring the border elements) from lo to hi
      for(int i=lo;i<(hi);i++){
        if(i<border){
          newArr[i]=arr[i];
          continue;
        }
        else if(i>arr.length-border-1){
          newArr[i]=arr[i];
          continue;
        }
  // construct an array of the numbers fSize with the i^th element as the center
        for(int j=(i-border);j<(i+border+1);j++){
          checker.add(arr[j]);
        }
  // Replace the i^th element with the median of its filter
    double newEl = findMedian(checker);
        newArr[i]=newEl;
        checker.clear();
      }
    }
    else{
      int mid = (hi+lo)/2;
      ParallelFilter left = new ParallelFilter(lo,mid,arr,newArr,fSize,border, SEQUENTIAL_CUTOFF);
      ParallelFilter right = new ParallelFilter(mid,hi,arr,newArr,fSize,border, SEQUENTIAL_CUTOFF);
      left.fork();
      right.compute();
      left.join();
    }


    return newArr;

  }


}
