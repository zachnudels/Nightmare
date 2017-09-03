import java.io.*;
import java.util.*;
import java.util.concurrent.*;
public class Main{

  float parTime;


  static final ForkJoinPool fjPool = new ForkJoinPool();

// Method to invoke ParallelFilter
  // Timing is done in this method since the array must already have been initialised
  double[] parFilter(double[] arr, int fSize){
    double[] ans = new double[arr.length];
    int border = (fSize-1)/2;
    ans = fjPool.invoke(new ParallelFilter(0,arr.length,arr,ans,fSize,border,2000));
    return ans;
  }
  public static void main(String[] args) throws IOException{
    Main main=new Main();

//Accept arguments from terminal
    String fileName = args[0];
    int fSize = Integer.parseInt(args[1]);
    boolean trying = false;


// Read file and write into initialised arrayList
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    int lim = Integer.parseInt(br.readLine());
    ArrayList<String> lines = new ArrayList<String>(lim);
    String line = br.readLine();

    // Determine if argument for number of lines to filter was piped in
    try{
      lim=Integer.parseInt(args[2]);
    }catch (Exception e){
      trying = true;
    }

    if(trying){
      while(line!=null){
        lines.add(line);
        line=br.readLine();
      }
    }else{
      lim = Integer.parseInt(args[2]);
      for (int x=0;x<lim;x++){
        lines.add(line);
        line=br.readLine();
      }
    }

// Seperate lines into lineNumbers and actual data save actual data into new array
    double[] arr = new double[lines.size()];
    String[] tmp = new String[2];
    for (int i=0;i<arr.length;i++){
      tmp=lines.get(i).split("\\s");
      arr[i] = Double.parseDouble(tmp[1]);
    }

// Initialise arrays for filtering outputs
    double[] seqArr = new double[arr.length];
    double[] parArr = new double[arr.length];


/****************** SEQUENTIAL METHOD ***************************************/

// Create new filter object and invoke filter method on data array and time

    MedianFilter mf = new MedianFilter(arr, fSize);
    seqArr = mf.filter();

// Write new array to new file with line numbers
  String writeName = "Resources/SeqMainData"+fSize+"_"+arr.length+".txt";
    File f = new File(writeName);
    if (!f.exists())
      f.createNewFile();
    BufferedWriter bw = new BufferedWriter(new FileWriter(writeName, false));
    for(int i=0; i<seqArr.length;i++){
      bw.write(Integer.toString(i)+" "+Double.toString(seqArr[i]));
      bw.newLine();
    }
    bw.flush();
    bw.close();

/****************** PARALLEL METHOD ***************************************/

  // Invoke filter method on data array and time
      parArr = main.parFilter(arr, fSize);

      // Determine if race conditions leading to incorrect results
      for(int g=0;g<arr.length;g++){
        if(parArr[g]!=seqArr[g]){
          System.out.println("ERROR!");
          return;
        }
      }


  // Write new array to new file with line numbers
    String parWriteName = "Resources/ParMainData"+fSize+"_"+arr.length+".txt";
      File f1 = new File(parWriteName);
      if (!f1.exists())
        f1.createNewFile();
      BufferedWriter bw1 = new BufferedWriter(new FileWriter(parWriteName, false));
      for(int j=0;j<parArr.length;j++){
        bw1.write(Integer.toString(j)+" "+Double.toString(parArr[j]));
        bw1.newLine();
      }
      bw1.flush();
      bw1.close();
  }
}
