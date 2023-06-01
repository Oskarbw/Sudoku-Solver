/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sudokusolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author oskar
 */
public class MainClasss {
    
    public static void main (String args[]) throws FileNotFoundException{
        
        int[][] grid = getGridFromFile();
        System.out.println(" Pobrano grid z pliku");
        SudokuSolver ss = new SudokuSolver();
        System.out.println(" Utworzono klase sudoku solver");
        grid = ss.solveSudoku(grid);
        System.out.println(" wywolano solvesudoku");
        writeGridToFile(grid);
        System.out.println(" zapisano do pliku i KONIEC");
        
        
        
    }
    
    private static int[][] getGridFromFile() throws FileNotFoundException{
        int[][] grid = new int[9][9];
        String text = new String();
        File data = new File("dataforsudoku.txt");
        Scanner scanner = new Scanner(data);
        for (int i=0; i<9; i++){
            text = scanner.nextLine();
            for (int j=0; j<9; j++){
                grid[i][j] = Character.getNumericValue(text.charAt(j));
                
            }
        }
        return grid;
    }
    
    private static void writeGridToFile(int[][] grid) throws FileNotFoundException{
        PrintWriter writer = new PrintWriter("results.txt");
        
        for (int i=0; i<9; i++){
            for (int j=0; j<8; j++){
            writer.print(grid[i][j]);
            }
            writer.println(grid[i][8]);
        }
        writer.println("-------------");
        writer.close();
        
    }
}
