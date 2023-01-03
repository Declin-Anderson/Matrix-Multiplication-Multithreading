import java.util.ArrayList;
import java.util.Random;

public class MatrixMultiplicationOperator {
  
  // Size of Matrix
  final int Dim = 1000;
  
  // Initializing the matrices
  ArrayList<ArrayList<Long>> matrixA = new ArrayList<ArrayList<Long>>(Dim);
  ArrayList<ArrayList<Long>> matrixB = new ArrayList<ArrayList<Long>>(Dim);
  ArrayList<ArrayList<Long>> matrixC = new ArrayList<ArrayList<Long>>(Dim); // Parallel
  ArrayList<ArrayList<Long>> matrixD = new ArrayList<ArrayList<Long>>(Dim); // Sequential
  
  // Constructor to create the matrices
  MatrixMultiplicationOperator() {
    Random rand = new Random();
    
    // Creating the indexes in the array
    for (int i = 0; i < Dim; i++) {
      matrixA.add(new ArrayList<Long>());
      matrixB.add(new ArrayList<Long>());
      matrixC.add(new ArrayList<Long>());
      matrixD.add(new ArrayList<Long>());
    }
    
    // Filling the matrices with random values
    for (int i = 0; i < Dim; i++) {
      for (int j = 0; j < Dim; j++) {
        long tmp1 = Math.abs(rand.nextLong()) % 9999;
        long tmp2 = Math.abs(rand.nextLong()) % 9999;
        matrixA.get(i).add(tmp1);
        matrixB.get(i).add(tmp2);
        matrixC.get(i).add((long) 0.0);
        matrixD.get(i).add((long) 0.0); 
      }
    }
  }
  
  // Performs Matrix Multiplication Parallel
  public void multiplyMatrixes(int from, int to) {
    for (int row = from; row <= to; ++row) {
      for (int column = 0; column < Dim; ++column) {
        long temp = 0;
        for(int transfer = 0; transfer < Dim; ++transfer)
          temp += matrixA.get(row).get(transfer) * matrixB.get(transfer).get(column);
        matrixC.get(row).set(column, temp);
      }
    }
  } 
  
//Performs Matrix Multiplication Sequential
 public void multiplySequentialMatrixes() {
   for (int row = 0; row < Dim; ++row) {
     for (int column = 0; column < Dim; ++column) {
       long temp = 0;
       for(int transfer = 0; transfer < Dim; ++transfer)
         temp += matrixA.get(row).get(transfer) * matrixB.get(transfer).get(column);
       matrixD.get(row).set(column, temp);
     }
   }
 }
}