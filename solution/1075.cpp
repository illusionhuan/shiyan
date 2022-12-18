#include <iostream>
#include <string.h>
#include <algorithm>
#include <stdio.h>
#include <math.h>
using namespace std;
double xia(double x,double n)
{
 if(n==1) return 1.0*sqrt(x+1);
 else 
 return sqrt(n+xia(x,n-1));
}
int main()
{
 double x,n;
 cin>>x>>n;
 printf("%.2lf",xia(x,n));
}
 