

package com.mycompany.sudokusolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuSolver {
    
    private Set<Integer>[][] whatCanBeThisTile;
    private Set<Integer>[] whatsAlreadyInColumn;
    private Set<Integer>[] whatsAlreadyInRow;
    private Set<Integer>[] whatsAlreadyInBox;
    private int howManyTilesUnknown;
   
    
    public int[][] solveSudoku (int[][] grid){
        isGivenGridCorrect(grid);
        initializeParameters(grid);
        updateParametersUpToGrid(grid);
        int counter = 0;
        while(howManyTilesUnknown>0 && counter<1000){
            updateParametersUpToGrid(grid);
            System.out.println(counter);
            calculate(grid);
            counter++;
        }
        
        
     return grid;   
    }
    private void calculate(int[][] grid){
        calculateRows(grid);
        calculateColumns(grid);
        calculateBoxes(grid);

        
    }
    private void calculateBoxes(int[][] grid) {
        for (int i=0; i<9; i++){
            int x = i / 3;
            x = x * 3;
            int y = i % 3;
            y = y *3;
            boolean numbersThatAreThere[] = new boolean[10];
            //see for missing numbers in box
            for (int j=x; j<x+3; j++){
               for (int k=y; k <y+3; k++){
                   numbersThatAreThere[grid[j][k]] = true;
               }
            }
            for (int j=1; j<10; j++){
                if (numbersThatAreThere[j] == true) continue; 
                boolean whereCannotThisNumberGo[] = new boolean[9];
                for (int k=x; k<x+3 ; k++){
                    for( int m=y; m<y+3; m++){
                        int indexInBox = (k-x)*3 +(m-y);
                    
                        if(grid[k][m]!=0){
                            whereCannotThisNumberGo[indexInBox] = true;
                        }
                         else{
                            whereCannotThisNumberGo[indexInBox] = ( searchRow(j, k, m, grid) 
                                    || searchColumn(j, k , m, grid) );

                        }
                    }
                }
                
                
                
                
                
                int tmpCounter = 0 ;
                int value = 0;
                for (int k=0; k<9; k++){
                    if(whereCannotThisNumberGo[k] == false){
                        tmpCounter++;
                        value = k;
                    }
                }
                if (tmpCounter == 1){
                    grid[x+(value/3)][y+(value%3)] = j;
                    howManyTilesUnknown--;
                }
            }
            
        }
    }
    private void calculateColumns (int[][] grid){
        for (int i=0; i<9; i++){
            
            boolean numbersThatAreThere[] = new boolean[10];
            //see for missing numbers in row
            for (int j=0; j<9; j++){
                numbersThatAreThere[grid[j][i]] = true;
            }
            for (int j=1; j<10; j++){
                if (numbersThatAreThere[j] == true) continue; 
                boolean whereCannotThisNumberGo[] = new boolean[9];
                for (int k=0; k<9 ; k++){
                    if(grid[k][i]!=0){
                        whereCannotThisNumberGo[k] = true;
                    }
                     else{
                        whereCannotThisNumberGo[k] = ( searchRow(j, i, k, grid) 
                                || searchBox(j, i , k, grid) );
                                
                    }
                }
                
                
                
                
                
                int tmpCounter = 0 ;
                int value = 0;
                for (int k=0; k<9; k++){
                    if(whereCannotThisNumberGo[k] == false){
                        tmpCounter++;
                        value = k;
                    }
                }
                if (tmpCounter == 1){
                    grid[value][i] = j;
                    howManyTilesUnknown--;
                }
            }
            
        }
    }
    
    private boolean searchColumn(int number, int x, int y, int[][] grid){
        boolean doesNumberAppear = false;
        for (int i=0; i<9; i++){
            if(grid[i][y]==number) {
                doesNumberAppear = true;
                break;
            }
        }
        return doesNumberAppear;
    }
    private boolean searchRow(int number, int x, int y, int[][] grid){
        boolean doesNumberAppear = false;
        for (int i=0; i<9; i++){
            if(grid[x][i]==number) {
                doesNumberAppear = true;
                break;
            }
        }
        return doesNumberAppear;
    }
            
            
    private boolean searchBox(int number, int x, int y, int[][] grid){
        boolean doesNumberAppear = false;
        int boxNumber = evaluateBoxNumber(x,y);
        y = boxNumber % 3;
        y = y*3;
        x = boxNumber / 3;
        x = x * 3;
        for (int i = x; i<x+3; i++){
            for (int j = y; j<y+3; j++){
                if(grid[i][j]==number){
                    doesNumberAppear = true;
                    break;
                }
            }
        }
        return doesNumberAppear;
    }
    private void calculateRows(int[][] grid){
        
        
        for (int i=0; i<9; i++){
            
            boolean numbersThatAreThere[] = new boolean[10];
            //see for missing numbers in row
            for (int j=0; j<9; j++){
                numbersThatAreThere[grid[i][j]] = true;
            }
            for (int j=1; j<10; j++){
                if (numbersThatAreThere[j] == true) continue; 
                boolean whereCannotThisNumberGo[] = new boolean[9];
                for (int k=0; k<9 ; k++){
                    if(grid[i][k]!=0){
                        whereCannotThisNumberGo[k] = true;
                    }
                    else{
                        whereCannotThisNumberGo[k] = ( searchColumn(j, i, k, grid) 
                                || searchBox(j, i , k, grid) );
                                
                    }
                }
                
                
                
                
                int tmpCounter = 0 ;
                int value = 0;
                for (int k=0; k<9; k++){
                    if(whereCannotThisNumberGo[k] == false){
                        tmpCounter++;
                        value = k;
                    }
                }
                if (tmpCounter == 1){
                    grid[i][value] = j;
                    howManyTilesUnknown--;
                }
            }
            
        }
    }
    private void updateParametersUpToGrid(int[][] grid){
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if(grid[i][j]!=0){
                    useThisTileInformation(grid[i][j], i,j);
                }
                else {
                    checkWhatCanBeThisTile(i,j);
                    if(whatCanBeThisTile[i][j].size() == 1){
                        for (int k : whatCanBeThisTile[i][j]){
                            grid[i][j] = k;
                            howManyTilesUnknown--;
                            //System.out.println("grid[i][j]=" +k);
                           // System.out.println("i=" +i);
                            //System.out.println("j=" +j);
                           // System.out.println("howMantTilesUnknown=" +howManyTilesUnknown);
                        }
                        useThisTileInformation(grid[i][j], i,j);
                    }
                }
            }
        }
    }
    
    private void checkWhatCanBeThisTile (int x,int y){
        checkBoxForThisTile(x,y);
        checkRowForThisTile(x,y);
        checkColumnForThisTile(x,y);
    }
    private void checkBoxForThisTile(int x,int y){
        int boxNumber = evaluateBoxNumber(x,y);
      
        for (int i : whatsAlreadyInBox[boxNumber]){
             whatCanBeThisTile[x][y].remove(i);
             System.out.println("BOX: Dla x=" + x +" i y=" +y +" usunieto:" + i );
        }
        
        
    }
    private void checkRowForThisTile(int x,int y){
        for (int i :whatsAlreadyInRow[x]){
            whatCanBeThisTile[x][y].remove(i);
            System.out.println("ROW: Dla x=" + x +" i y=" +y +" usunieto:" + i );
        }
       
    }
    private void checkColumnForThisTile(int x,int y){
        for (int i :whatsAlreadyInColumn[y]){
            whatCanBeThisTile[x][y].remove(i);
            System.out.println("COL: Dla x=" + x +" i y=" +y +" usunieto:" + i );
        }
       
    }
    
    private void useThisTileInformation(int value, int x, int y){
        //update row
        whatsAlreadyInRow[x].add(value);
        whatsAlreadyInColumn[y].add(value);
        int boxNumber = evaluateBoxNumber(x,y);
        whatsAlreadyInBox[boxNumber].add(value);
    }
    
    private int evaluateBoxNumber(int x,int y){
        int boxNumber = 0;
        boxNumber += y/3;
        int tmp = x/3;
        boxNumber += tmp*3;
        return boxNumber;
    }
    private void checkInitiallyHowManyTilesUnknown(int [][] grid){
        howManyTilesUnknown = 81;
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if(grid[i][j]!=0){
                    howManyTilesUnknown--;
                }
            }
        }
    }
    private void initializeParameters(int[][] grid){
        
        checkInitiallyHowManyTilesUnknown(grid);
        whatCanBeThisTile = new HashSet[9][9];
        whatsAlreadyInColumn = new HashSet[9];
        whatsAlreadyInRow = new HashSet[9];
        whatsAlreadyInBox = new HashSet[9];
        
        for (int i=0; i<9;i++){
            
            whatsAlreadyInColumn[i] = new HashSet();
            whatsAlreadyInRow[i] = new HashSet();
            whatsAlreadyInBox[i] = new HashSet();
            for (int j=1; j<10; j++){
                //whatsAlreadyInColumn[i].add(j);
                //whatsAlreadyInRow[i].add(j);
               // whatsAlreadyInBox[i].add(j);
                whatCanBeThisTile[i][j-1] = new HashSet();
                for (int k=1;k<10;k++){
                    whatCanBeThisTile[i][j-1].add(k);
                }
            }
        }
    }
    
    private void isGivenGridCorrect(int[][] grid){
        
        //check if number are just 1-9 and 0 for no number
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[i].length; j++){
                if(grid[i][j]>9 || grid[i][j]<0)
                    throw new IllegalArgumentException();
            }
        }
        
    }
}
