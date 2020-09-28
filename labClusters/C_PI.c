#include <math.h> // math library 
#include <stdio.h>// Standard Input/Output library 
 
int main(void) 
{ 
  long num_rects = 300000;//1000000000; 
  long i; 
  double x,height,width,area; 
  double sum; 
   width = 1.0/(double)num_rects; // width of a segment 
 
   sum = 0; 
   for(i = 0; i < num_rects; i++) 
   { 
     x = (i+0.5) * width; // x: distance to center of i(th) segment 
     height = 4/(1.0 + x*x); 
     sum += height; // sum of individual segment heights 
   } 
 
// approximate area of segment (Pi value)  
   area = width * sum; 
 
   printf("\n"); 
   printf(" Calculated Pi = %.16fn\n", area); 
   printf("          M_PI = %.16fn\n", M_PI); 
   printf("Relative error = %.16fn\n", fabs(area - M_PI)); 
 
   return 0; 
} 
