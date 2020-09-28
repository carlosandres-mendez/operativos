#include <math.h> // math library 
#include <stdio.h>// Standard Input/Output library 
#include <mpi.h>  // (Open)MPI library 
 
int main(int argc, char** argv) 
{ 
  /* MPI Variables */ 
  int num_processes; 
  int curr_rank; 
  int proc_name_len; 
  char proc_name[MPI_MAX_PROCESSOR_NAME]; 
 
  /* Initialize MPI */ 
  MPI_Init (&argc, &argv); 
 
  /* acquire number of processes */ 
  MPI_Comm_size(MPI_COMM_WORLD, &num_processes); 
 
  /* acquire rank of the current process */ 
  MPI_Comm_rank(MPI_COMM_WORLD, &curr_rank); 
 
  /* acquire processor name for the current thread */ 
  MPI_Get_processor_name(proc_name, &proc_name_len); 
 
  /* output rank, no of processes, and process name */ 
  printf("Calling process %d out of %d on %srn\n", curr_rank, num_processes, proc_name); 
 
  /* clean up, done with MPI */ 
  MPI_Finalize(); 
 
  return 0; 
} 
