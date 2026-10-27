/*
Name: Patrick Vandergrift
Date: 10/15/2026
Description: Simulating Page Replacement Algorithms
*/

import java.util.*;
import java.util.Scanner;
import java.util.Arrays;

public class Main {

    //DRIVER
    public static void main(String[] args) 
    { 
      int[] string_seq = new int[13];
      int frameSize;
      
      System.out.print("Please Enter a Reference String (No value of -1): ");
      Scanner input = new Scanner(System.in);
      for(int i=0; i<string_seq.length; i++) //Sequence String
      {
        string_seq[i] = input.nextInt();
      }

      System.out.print("Please Enter the Number of Page Frames: ");
      frameSize = input.nextInt();  //Frame Size

      Replacement swap = new Replacement(string_seq, frameSize);

      System.out.println("FIFO Algorithm");
//      swap.simulateFIFO();

      System.out.println("LRU Algorithm");
//      swap.simulateLRU();

      System.out.println("Optimal Algorithm");
      swap.simulateOptimal();
  }

}
