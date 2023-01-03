
public class MatrixMultiplicationApp {
  public static void main(String[] args) throws InterruptedException { 
    // Creating a instance of the Operator Class
    MatrixMultiplicationOperator MultOpr = new MatrixMultiplicationOperator();
    
    // Printing Number the size of the Matrices
    System.out.println("The matrices are " + MultOpr.Dim + " by " + MultOpr.Dim + "\n");
    
    // Sequential process
    long startTime = System.nanoTime();
    
    // Running the Sequential
    MultOpr.multiplySequentialMatrixes();
    
    // Ending the timer and printing the duration for parallel process
    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    System.out.println("Sequential Process for Matrix Multiplication was: " + duration + " milliseconds\n");
    
    // Splitting the work equal among the Threads
    int workSpilt = MultOpr.Dim / 4;
    int dim1 = workSpilt;
    int dim2 = workSpilt * 2;
    int dim3 = workSpilt * 3;
    int dim4 = MultOpr.Dim - 1;
    
    // Creating what the threads will run
    Runnable r1 = new MatrixMultiplicationWrapper(MultOpr, 0, (dim1 - 1));
    Runnable r2 = new MatrixMultiplicationWrapper(MultOpr, dim1 , (dim2 - 1));
    Runnable r3 = new MatrixMultiplicationWrapper(MultOpr, dim2 , (dim3 -1));
    Runnable r4 = new MatrixMultiplicationWrapper(MultOpr, dim3 , dim4);
    
    // Initializing the threads
    Thread t1 = new Thread(r1);
    Thread t2 = new Thread(r2);
    Thread t3 = new Thread(r3);
    Thread t4 = new Thread(r4);
    
    // Starting the timer for running the threads
    startTime = System.nanoTime();
    
    // Starting the thread processes
    t1.start(); t2.start();   t3.start(); t4.start(); 
    t1.join(); t2.join(); t3.join(); t4.join();
    
    // Ending the timer and printing the duration for parallel process
    endTime = System.nanoTime();
    duration = (endTime - startTime) / 1000000;
    System.out.println("Parallel Process for Matrix Multiplication was: " + duration + " milliseconds");
    
    /*
     * Testing to make sure the multiplication is correct for both sequential and parallel and same output
     * 
     * System.out.println(MultOpr.matrixA);
     * System.out.println(MultOpr.matrixB);
     * System.out.println(MultOpr.matrixC);
     * System.out.println(MultOpr.matrixD);
    */
  }
}