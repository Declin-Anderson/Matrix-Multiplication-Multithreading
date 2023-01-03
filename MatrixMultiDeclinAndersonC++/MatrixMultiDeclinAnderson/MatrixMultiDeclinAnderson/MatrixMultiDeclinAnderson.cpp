#include <iomanip>
#include <iostream>       // std::cout
#include <thread>         // std::thread
#include <mutex>          // std::mutex
#include <ctime>    
#include <chrono>  // measure the tile via using some functions included here
#include <vector>
#include <ctime>

using namespace std;
using namespace std::chrono; // for the   high_resolution_clock
unsigned long long int total = 0;
const unsigned long long int Dim = 1000;
vector<vector<unsigned long long int>> matrixA;
vector<vector<unsigned long long int>> matrixB;
vector<vector<unsigned long long int>> matrixC; //calculated sequentially
vector<vector<unsigned long long int>> matrixD; //calculated parallel

// creating the methods
void sequential_Matrix_Multiplication();
void parallel_Matrix_Multiplication(int from, int to);
void constructMatrices();
void printMatrix(vector<vector<unsigned long long int>> matrix);

int main()
{
    srand(time(nullptr));
    constructMatrices();
    //initiializing vectors
    int count = 0;
    for (int i = 0; i < Dim; i++)
    {
        for (int j = 0; j < Dim; j++)
        { // just arbitrary numbers
            matrixA[i][j] = rand() % 1000;
            matrixB[i][j] = rand() % 1000;
            matrixC[i][j] = 0;
            matrixD[i][j] = 0;
        }
    }

    // Printing the size of the matrices
    cout << "The size of the matrices are " << Dim << " by " << Dim << endl << endl;

    //sequential matrix mulitplication and timer
    cout << "Start Sequential Multiplication " << endl;
    auto start = high_resolution_clock::now();
    sequential_Matrix_Multiplication();
    auto stop = high_resolution_clock::now();
    auto duration = duration_cast<milliseconds>(stop - start);
    cout << "Sequential time in milliseconds: " << duration.count() << endl << endl;

    // Splitting up the work that needs to be done by each thread
    int from = 0;
    int to = 0;
    int workload = Dim / 4;
    int dim1 = workload;
    int dim2 = workload * 2;
    int dim3 = workload * 3;
    int dim4 = Dim - 1;

    // Starting the timer for the threads
    cout << "Parallel Multiplication: " << endl;
    start = high_resolution_clock::now();

    //creating threads and dividing the workload
    std::thread th1(parallel_Matrix_Multiplication, 0, dim1);
    std::thread th2(parallel_Matrix_Multiplication, (dim1 + 1), dim2);
    std::thread th3(parallel_Matrix_Multiplication, (dim2 + 1), dim3);
    std::thread th4(parallel_Matrix_Multiplication, (dim3 + 1), dim4);

    th1.join();
    th2.join();
    th3.join();
    th4.join();

    // Prints the time it took for the threads to process in parallel
    stop = high_resolution_clock::now();
    duration = duration_cast<milliseconds>(stop - start);
    cout << "Parallel time in milliseconds: " << duration.count() << endl;

    /*
    * For testing Purposes to make sure multiplication is right and same output for both sequential and parallel
    * 
    * cout << endl;
    * printMatrix(matrixA);
    * printMatrix(matrixB);
    * printMatrix(matrixC);
    * printMatrix(matrixD);
    */
}

// Fills the matrice by the calculations in sequential order
void sequential_Matrix_Multiplication() {
    //preconditions: matrixA, matrixB, and matrixC are not null and all have the same size
    for (int row = 0; row < Dim; ++row) {
        for (int column = 0; column < Dim; ++column) {
            for (int transfer = 0; transfer < Dim; ++transfer)
                matrixC[row][column] += (matrixA[row][transfer] * matrixB[transfer][column]);

        }
    }
}

// Fills the matrice by the calculations for only what the thread will do
void parallel_Matrix_Multiplication(int from, int to) {
    //preconditions: matrixA, matrixB, and matrixD are not null and all have the same size
    for (int row = from; row <= to; ++row) {
        for (int column = 0; column < Dim; ++column) {
            for (int transfer = 0; transfer < Dim; ++transfer)
                matrixD[row][column] += (matrixA[row][transfer] * matrixB[transfer][column]);
        }
    }
}

// Creates the matrices
void constructMatrices() {
    //pre conditions: all matrices are declared as vector of vectors: vector<vector<unsigned long long int>> 
    for (int i = 0; i < Dim; i++) {
        matrixA.push_back(vector<unsigned long long int>(Dim)); // Add an empty row
        matrixB.push_back(vector<unsigned long long int>(Dim)); // Add an empty row
        matrixC.push_back(vector<unsigned long long int>(Dim)); // Add an empty row
        matrixD.push_back(vector<unsigned long long int>(Dim)); // Add an empty row
    }
}

// Print the Matrix (Use only for small ones)
void printMatrix(vector<vector<unsigned long long int>> matrix)
{
    for (int i = 0; i < Dim; i++)
    {
        for (int j = 0; j < Dim; j++)
        {
            cout << matrix[i][j] << " ";
        }
        cout << endl;
    }
    cout << "---------------------" << endl;
}