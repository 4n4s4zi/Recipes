/* DESCRIPTION:
 * -reads sudoku puzzles from an input file
 * -outputs each original puzzle followed by a solution
 *  or an error message:
 *  "Error" if the format of a line of input is invalid or
 *  "No solution" if the puzzle is valid but has no solution
 */

#include <stdio.h>

/*
 * Function: printBoard
 * --------------------
 * prints the sudoku board on one line
 *
 * board: sudoku puzzle (9x9 int array)
 * return: void
 */
void printBoard(int board[9][9])
{
  int i, j; /*counters for loops*/
  for(i=0; i<9; i++)
  {
    for(j=0; j<9; j++)
    {
      printf("%c", board[i][j]);
    }
  }
  printf("\n\n");
}

/*
 * Function: isValid
 * -----------------
 * -checks if a given space on the board is a valid location to place
 *  a number by serching the row, column, and subsquare for a duplicate
 *
 * board: sudoku puzzle (9x9 int array)
 * a: row index of location to check (int)
 * b: column index (int)
 * n: number to be tried (int)
 * return: 1 if valid, 0 if not (int)
 */
int isValid(int board[9][9], int a, int b, int n)
{
  int i, j; /*conters for loops*/
  int a1, b1;
  a1=3*(a/3); /*calculations for checking a subsquare*/
  b1=3*(b/3);

  /*check row*/
  for(j=0; j<9; j++)
  {
    if(board[a][j] == n)
    {
      return 0;
    }
  }
  /*check column*/
  for(i=0; i<9; i++)
  {
    if(board[i][b] == n)
    {
      return 0;
    }
  }
  /*check subsquare*/
  for(i=0; i<3; i++)
  {
    for(j=0; j<3; j++)
    {
      if(board[a1+i][b1+j] == n)
      {
        return 0;
      }
    }
  }

  return 1;
}

/*
 * Function: solve
 * ---------------
 * -uses recursion and backtracking to try all possibilities for each open
 *  square until a solution is reached.
 *
 * board: sudoku puzzle (9x9 integer array)
 * return: 1 if a number is placed or if the puzzle is solved,
 * 0 if a dead end is reached or if the puzzle has no solution (int)
 */
int solve(int board [9][9])
{
  int i, j, n; /*counters for loops*/
  for(i=0; i<9; i++)
  {
    for(j=0; j<9; j++)
    {
      if(board[i][j] == '.')
      {
        for(n='1'; n<='9'; n++)
        {
          if(isValid(board, i, j, n))
          {
            board[i][j] = n;
            if(!solve(board))
            {
              board[i][j] = '.';
            }
            else
            {
              return 1;
            }
          }
        }
        return 0;
      }
    }
  }

  return 1;
}

/*
 * Function: checkBoard
 * --------------------
 * -checks if there are any invalid positions a 9x9 sudoku puzzle by
 * searching every row, column, and subquare.
 *
 * board: sudoku puzzle (9x9 integer array)
 * return: 1 if the there are no invalid positions, 0 if one is found (int)
 */
int checkBoard(int board[9][9])
{
  int i, j, a, b, n; /*counters for loops*/
  int v1, v2; /*values used for checking subsquares*/
  int digits[9] = {0,0,0,0,0,0,0,0,0}; /*array for checking if duplicates exists*/

  /*check each row*/
  for(i=0; i<9; i++)
  {
    for(j=0; j<9; j++)
    {
      if(board[i][j] != '.')
      {
        digits[board[i][j]-'1']++;
      }
    }
    /*check if a number was found more than once*/
    for(n=0; n<9; n++)
    {
      if(digits[n]>1)
      {
        return 0;
      }
      digits[n]=0;
    }
  }
  /*check each column*/
  for(j=0; j<9; j++)
  {
    for(i=0; i<9; i++)
    {
      if(board[i][j] != '.')
      {
        digits[board[i][j]-'1']++;
      }
    }
    /*check if a number was found more than once*/
    for(n=0; n<9; n++)
    {
      if(digits[n]>1)
      {
        return 0;
      }
      digits[n]=0;
    }
  }
  /*check each subsquare*/
  for(a=0; a<3; a++)
  {
    for(b=0; b<3; b++)
    {
      for(i=0; i<3; i++)
      {
        v1=(3*a)+i;
        for(j=0; j<3; j++)
        {
          v2=(3*b)+j;
          if(board[v1][v2] != '.')
          {
            digits[board[v1][v2]-'1']++;
          }
        }
      }
      /*check if a number was found more than once*/
      for(n=0; n<9; n++)
      {
        if(digits[n]>1)
        {
          return 0;
        }
        digits[n]=0;
      }
    }
  }

  return 1;
}

/*
 * Main:
 * -reads/formats input and uses functions to solve each sudoku puzzle
 *  or output an error message
 *
 * return: 0 (int)
 */
int main()
{
  int board[9][9];
  int c, z, e; /*c is a char from input, z is a counter, e is an error counter*/
  z=e=0;

  while((c=getchar()) != EOF)
  {
    printf("%c", c);
    if(((c>'0') && (c<='9')) || (c=='.'))
    {
      board[z/9][z%9] = c;
      z++;
    }
    else if(c == '\n')
    {
      if((z == 81) && (e == 0) && checkBoard(board))
      {
        if(solve(board))
        {
          printBoard(board);
        }
        else
        {
          printf("No solution\n\n");
        }
      }
      else
      {
        printf("Error\n\n");
      }
      z=e=0;
    }
    else
    {
      e++;
    }
  }

  return 0;
}
