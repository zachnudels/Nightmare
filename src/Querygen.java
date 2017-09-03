import java.util.*;
public class Querygen{
  double[] arr;
  double[][] dArr;

  public Querygen(int size){
    Random rand=new Random();
    this.arr = new double[size];
    for(int i=0;i<size;i++){
      arr[i]=rand.nextDouble();
    }
  }

  public Querygen(int width, int height){
    Random rand = new Random();
    this.dArr=new double[width][height];
    for(int x=0;x<width;x++){
      for (int y=0;y<height;y++){
        dArr[x][y]=rand.nextDouble();
      }
    }
  }

}
