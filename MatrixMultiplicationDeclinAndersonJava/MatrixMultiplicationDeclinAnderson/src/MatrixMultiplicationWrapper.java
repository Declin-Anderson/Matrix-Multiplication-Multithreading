
// Implementing Runnable
public class MatrixMultiplicationWrapper implements Runnable {
  private MatrixMultiplicationOperator mmop;
  private int from, to;
  
  // Creating the method to be run by the threads
  public MatrixMultiplicationWrapper (MatrixMultiplicationOperator c, int from, int to) {
    this.mmop = c; 
    this.from = from; 
    this.to = to;
  }
  
  // What the threads will do
  public void run() {
    mmop.multiplyMatrixes(from, to);
  }
}
