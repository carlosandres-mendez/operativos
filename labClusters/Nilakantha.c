#include <mpi.h>  // (Open)MPI library 
#include <math.h> // math library 
#include <stdio.h>// Standard Input/Output library 
 
int main(int argc, char*argv[]) 
{ 
  int total_iter; 
  int n,rank,length,numprocs,i,k; 
  double sum,sum0,rank_integral,A,B,C; 
  char hostname[MPI_MAX_PROCESSOR_NAME]; 
 
  MPI_Init(&argc, &argv);// initiates MPI 
  MPI_Comm_size(MPI_COMM_WORLD, &numprocs); // acquire number of processes 
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);     // acquire current process id 
  MPI_Get_processor_name(hostname, &length);// acquire hostname 
 
  if(rank == 0) 
  {   
     printf("n"); 
     printf("#######################################################");   
     printf("nn"); 
     printf("Master node name: %sn", hostname);  
     printf("n"); 
     printf("Enter the number of intervals:n"); 
     printf("n"); 
     scanf("%d",&n); 
     printf("n"); 
  } 
 
  // broadcast to all processes, the number of segments you want 
  MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD); 
 
  // this loop increments the maximum number of iterations, thus providing 
  // additional work for testing computational speed of the processors    
  for(total_iter = 1; total_iter < n; total_iter++)  
  { 
    sum0 = 0.0; 
    for(i = rank + 1; i <= total_iter; i += numprocs) 
    {  
      k = i-1; 
      A = (double)pow(-1,k+2); 
      B = 4.0/(double)((k+1)*(k+2)*(k+3)); 
      C = A * B; 
 
      sum0 += C; 
    } 
 
    rank_integral = sum0;// Partial sum for a given rank 
 
    // collect and add the partial sum0 values from all processes 
    MPI_Reduce(&rank_integral, &sum, 1, MPI_DOUBLE, MPI_SUM, 0, MPI_COMM_WORLD); 
 
  } // End of for(total_iter = 1; total_iter < n; total_iter++) 
 
  if(rank == 0) 
  { 
    printf("nn"); 
    printf("*** Number of processes: %dn",numprocs); 
    printf("nn"); 
    printf("     Calculated pi = %.30fn", (3+sum)); 
    printf("              M_PI = %.30fn", M_PI);     
    printf("    Relative Error = %.30fn", fabs((3+sum)-M_PI)); 
  } 
  // clean up, done with MPI 
  MPI_Finalize(); 
 
  return 0;   
}// End of int main(int argc, char*argv[])
