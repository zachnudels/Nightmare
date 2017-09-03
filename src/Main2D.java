import java.io.*;
import java.util.*;
import java.util.concurrent.*;
public class Main2D{

  float parTime;


  static final ForkJoinPool fjPool = new ForkJoinPool();

// Method to invoke ParallelFilter
  // Timing is done in this method since the array must already have been initialised
  double[][] parFilter(double[][] arr, int[] fSize){
    double[][] ans = new double[arr[0].length][arr.length];
    ans = fjPool.invoke(new Parallel2DFilter(0,arr[0].length,arr,ans,fSize,2000));
    return ans;
  }
  public static void main(String[] args) throws IOException{
    Main2D main=new Main2D();
    Querygen qg = new Querygen(4,4);
    double[][] arr = qg.dArr;
    int[] fSize= new int[]{2,2};

    // for(int x2=0;x2<arr[0].length;x2++){
    //   for(int y2=0;y2<arr.length;y2++){
    //     System.out.print(arr[x2][y2]+" ");
    //   }
    //   System.out.println("");
    // }

// Initialise arrays for filtering outputs
    double[][] seqArr = new double[arr[0].length][arr.length];
    double[][] parArr = new double[arr[0].length][arr.length];


/****************** SEQUENTIAL METHOD ***************************************/

// Create new filter object and invoke filter method on data array and time

    TwoDFilter mf = new TwoDFilter(arr, fSize);
    seqArr = mf.filter();

// Write new array to new file with line numbers
  String writeName = "Resources/Seq2DMainData"+fSize[0]+"_"+arr.length+".txt";
    File f = new File(writeName);
    if (!f.exists())
      f.createNewFile();
    BufferedWriter bw = new BufferedWriter(new FileWriter(writeName, false));
    for(int y=0;y<seqArr.length;y++){
      for(int x=0; x<seqArr[0].length;x++){
        bw.write(Double.toString(seqArr[x][y])+" ");
      }
      bw.newLine();
    }
    bw.flush();
    bw.close();

/****************** PARALLEL METHOD ***************************************/

  // Invoke filter method on data array and time
      parArr = main.parFilter(arr, fSize);

      // Determine if race conditions leading to incorrect results
      for(int h=0; h<arr.length; h++){
        for(int g=0;g<arr[0].length;g++){
          if(parArr[g][h]!=seqArr[g][h]){
            System.out.println("ERROR!");
            return;
          }
        }
      }


  // Write new array to new file with line numbers
    String parWriteName = "Resources/Par2DMainData"+fSize[0]+"_"+arr.length+".txt";
      File f1 = new File(parWriteName);
      if (!f1.exists())
        f1.createNewFile();
      BufferedWriter bw1 = new BufferedWriter(new FileWriter(parWriteName, false));
      for(int y1=0;y1<parArr.length;y1++){
        for(int x1=0; x1<parArr[0].length;x1++){
          bw1.write(Double.toString(parArr[x1][y1])+" ");
        }
        bw1.newLine();
      }
      bw1.flush();
      bw1.close();
  }
}
