import java.util.ArrayList;
import java.util.*;
public class TwoDFilter{
  int[] fSize;
  double[][] arr;
  double[][] newArr;

  public TwoDFilter(double[][] arr, int[] fSize){
    this.fSize=fSize;
    this.arr=arr;
  }

  public double findMedian(ArrayList<Double> checker){
// Sort list and return the middle element
    Collections.sort(checker);
    return checker.get((fSize[0]*fSize[1])/2);
  }

  public double[][] filter(){
    newArr = new double[arr[0].length][arr.length];
    ArrayList<Double> checker = new ArrayList<Double>(fSize[0]*fSize[1]);
    // System.out.println(fSize[0]*fSize[1]);
    int edgeX=(int) Math.floor(fSize[0]/2);
    int edgeY=(int) Math.floor(fSize[1]/2);
    // System.out.println(edgeX+" "+edgeY);
    for(int x=0;x<arr[0].length;x++){
      for(int y=0;y<arr.length;y++){
          if(x>=edgeX && x<arr[0].length-edgeX && y>=edgeY && y<arr.length-edgeY){
          // System.out.println("X+Y "+x+" "+y);
          for(int fx=0;fx<fSize[0];fx++){
            for(int fy=0;fy<fSize[1];fy++){
              // System.out.println((x+fx-edgeX)+" "+(y+fy-edgeY));
              checker.add(arr[x+fx-edgeX][y+fy-edgeY]);
            }
          }
            newArr[x][y]=findMedian(checker);
          // System.out.println(newArr[x][y]);
        }else{
          newArr[x][y]=arr[x][y];
        }
      }
    }
    return newArr;
  }


}
