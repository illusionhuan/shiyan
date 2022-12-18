#include <iostream>
#include <string.h>
#include <algorithm>
#include <stdio.h>
#include <math.h>
using namespace std;
double xia(double x,double n)
{
    if(n==1) return 1.0*(x/(x+1));
    else 
        return 1.0*(x/(n+xia(x,n-1)));
}
int main()
{
    int x,n;
    cin>>x>>n;
    printf("%.2lf",xia(x,n));
}
 