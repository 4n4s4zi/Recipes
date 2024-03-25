//Euclid's GCD algorithm
int gcd(int a, int b)
{
  if(!a) return b;
  return gcd(b%a, a);
}

//Extended Euclidean GCD algorithm
int egcd(int a, int b, int* x, int* y)
{
  if(!a)
  {
    *x = 0;
    *y = 1;
    return b;
  }
  int x1, y1, gcd;
  gcd = egcd(b%a, a, &x1, &y1);
  *x = y1 - (b/a) * x1;
  *y = x1;
  return gcd;
}
