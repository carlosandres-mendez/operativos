#include <mpi.h>  // (Open)MPI library 
#include <math.h> // math library 
#include <stdio.h>// Standard Input/Output library 
 
int main(int argc, char*argv[]) 
{ 
  int total_iter; 
  int n, rank, length, numprocs, i; 
  double pi, width, sum, x, rank_integral; 
  char hostname[MPI_MAX_PROCESSOR_NAME]; 
  MPI_Init(&argc, &argv);// initiates MPI 
  MPI_Comm_size(MPI_COMM_WORLD, &numprocs);// acquire number of processes 
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);// acquire current process id 
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
  sum=0.0; 
  width = 1.0 / (double)total_iter; // width of a segment 
  // width = 1.0 / (double)n; // width of a segment 
  for(i = rank + 1; i <= total_iter; i += numprocs) 
  // for(i = rank + 1; i <= n; i += numprocs) 
  { 
    x = width * ((double)i - 0.5); // x: distance to center of i(th) segment 
    sum += 4.0/(1.0 + x*x);// sum of individual segment height for a given 
                           // rank 
  } 
// approximate area of segment (Pi value) for a given rank 
  rank_integral = width * sum;  
 
// collect and add the partial area (Pi) values from all processes 
   MPI_Reduce(&rank_integral, &pi, 1, MPI_DOUBLE, MPI_SUM,
   0, MPI_COMM_WORLD); 
}// End of for(total_iter = 1; total_iter < n; total_iter++) 
 
if(rank == 0) 
{ 
   printf("\n"); 
   printf("*** Number of processes: %dn\n",numprocs); 
   //printf("nn"); 
   printf("     Calculated pi = %.30fn\n", pi); 
   printf("              M_PI = %.30fn\n", M_PI);     
   printf("    Relative Error = %.30fn", fabs(pi-M_PI)); 
   // printf("Process %d on %s has the partial result of %.16f n",rank,hostname, rank_integral); 
} 
 
// clean up, done with MPI 
MPI_Finalize(); 
 
return 0;   
}// End of int main(int argc, char*argv[])
