import java.util.ArrayList;
import java.util.*;
public class MedianFilter{
  int fSize;
  double[] arr;
  double[] newArr;
  int border;

  public MedianFilter(double[] arr, int fSize){
    this.fSize=fSize;
    this.arr=arr;
    this.border=(fSize-1)/2;
  }

  public double findMedian(ArrayList<Double> checker){
// Sort list and return the middle element
    Collections.sort(checker);
    return checker.get((fSize-1)/2);
  }

  public double[] filter(){
    ArrayList<Double> checker = new ArrayList<Double>();
    newArr = new double[arr.length];
// for each element (barring the border elements)
    for(int i=0;i<(arr.length);i++){
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
    return newArr;
  }


}
