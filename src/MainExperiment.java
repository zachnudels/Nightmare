import java.io.*;
import java.util.*;
import java.util.concurrent.*;
public class MainExperiment{
  static long startTime = 0;
  float parTime;

	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f;
	}

  static final ForkJoinPool fjPool = new ForkJoinPool();

// Method to invoke ParallelFilter
  // Timing is done in this method since the array must already have been initialised
  double[] parFilter(double[] arr, int fSize, int seqCut){
    double[] ans = new double[arr.length];
    int border = (fSize-1)/2;
    System.gc();
    tick();
    ans = fjPool.invoke(new ParallelFilter(0,arr.length,arr,ans,fSize,border, seqCut));
    parTime=tock();
    return ans;
  }
  public static void main(String[] args) throws IOException{
    MainExperiment main=new MainExperiment();

//Accept arguments from terminal
    int fSize = Integer.parseInt(args[0]);
    int arrSize = Integer.parseInt(args[1]);
    int seqCut = Integer.parseInt(args[2]);

    Querygen qn = new Querygen(arrSize);
    double[] arr = qn.arr;
    double[] seqArr = new double[arr.length];
    double[] parArr = new double[arr.length];
    ArrayList<Float> seqTimes = new ArrayList<Float>();
    ArrayList<Float> parTimes = new ArrayList<Float>();
    int iteration =10;


/****************** SEQUENTIAL METHOD ***************************************/

// Create new filter object and invoke filter method on data array and time
  for (int m=0;m<iteration;m++){
    System.gc();
    tick();
    MedianFilter mf = new MedianFilter(arr, fSize);
    seqArr = mf.filter();
    float time = tock();
    seqTimes.add(time);
  }


  float seqAve=0.0f;
  for (int p=1;p<seqTimes.size();p++){
    seqAve+=seqTimes.get(p);
  }
  seqAve/=seqTimes.size();


// Write new array to new file with line numbers
  String writeName = "Resources/SeqMainResult.csv";
  int nrOfProcessors = Runtime.getRuntime().availableProcessors();
  String seqWrite = nrOfProcessors+" "+fSize+" "+arrSize+" "+seqCut+" "+seqAve;
    File f = new File(writeName);
    if (!f.exists())
      f.createNewFile();
    BufferedWriter bw = new BufferedWriter(new FileWriter(writeName, true));
    bw.write(seqWrite);
      bw.newLine();
    bw.flush();
    bw.close();
    // System.out.println(seqAve);


/****************** PARALLEL METHOD ***************************************/


  // Create new filter object and invoke filter method on data array and time
  for(int n=0; n<iteration; n++){
      parArr = main.parFilter(arr, fSize, seqCut);
      // Determine if race conditions leading to incorrect results
      for(int g=0;g<arr.length;g++){
        if(parArr[g]!=seqArr[g]){
          System.out.println("ERROR!");
          return;
        }
      }
      parTimes.add(main.parTime);
    }


    float parAve=0.0f;
    for (int q=1;q<parTimes.size();q++){
      parAve+=parTimes.get(q);
    }
    parAve/=parTimes.size();


    // Write new array to new file with line numbers
      writeName = "Resources/ParMainResult.csv";
      nrOfProcessors = Runtime.getRuntime().availableProcessors();
      String parWrite = nrOfProcessors+" "+fSize+" "+arrSize+" "+seqCut+" "+parAve;
        File f1 = new File(writeName);
        if (!f1.exists())
          f1.createNewFile();
        BufferedWriter bw1 = new BufferedWriter(new FileWriter(writeName, true));
        bw1.write(parWrite);
          bw1.newLine();
        bw1.flush();
        bw1.close();
      // System.out.println(parAve);
  }
}
