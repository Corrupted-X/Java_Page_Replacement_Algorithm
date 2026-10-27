import java.util.*;
import java.util.Scanner;
import java.util.Arrays;


public class Replacement{
	int[] string_seq;
	int frameSize;
	

	//Constructor
	public Replacement(int[] string_seq, int  frameSize)
	{
		this.string_seq = string_seq;
		this.frameSize = frameSize;
	}




	//VOID printFrame Description
	/********************************************************
	Purpose:  Print Frame Table
	Incoming: int[] frameTable
	Outgoing: Nothing
	**********************************************************/
	public static void printFrame(int[] frameTable)
	{
		System.out.println("Frame Table:");
		for(int i=0; i<frameTable.length; i++)
		{
		  System.out.printf("Frame " + i + ": Page " + frameTable[i] + "\n");
		}
		System.out.print("\n");
  }
	
	//VOID simulateLRU Description
	/********************************************************
	Purpose:  Preform LRU replacements
	Incoming: Nothing
	Outgoing: Nothing
	**********************************************************/
	public void simulateLRU()
	{
      int[] frameTable = new int[frameSize];         //Simulate: Each Frame
      Arrays.fill(frameTable, -1);
      int[] queue = new int[frameSize];              //Simulate: Queue scheme
      Arrays.fill(queue, -1);
      int numFaults=0, notInFrame=0;

      for(int i=0; i<string_seq.length; i++) //Sequence Size
      {
        notInFrame = 0;
        System.out.printf("Page Referenced: " + string_seq[i] + "\n");
        for(int j=0; j<frameTable.length; j++) //Frame Size
        { 
            if(frameTable[j] == -1) //First Page Inserts
            {
              System.out.printf("Page Fault occurred with reference: " + string_seq[i] + "\n");

              boolean inTable=false;
              for(int k=0; k<frameTable.length; k++)	//Check if value is already in Frame Table
              {
                if(string_seq[i] == frameTable[k])
                {
                  inTable = true;
                }
              }

              if(queue[j] > -1)
              {
                queue[j-1] = queue[j];
              }
              queue[j] = string_seq[i];       //Value in queue

              if(inTable != true){
                frameTable[j] = string_seq[i];            
                numFaults++;
                break; //Early out for first inserts
              }
            }
            else if(frameTable[j] == string_seq[i]) //Page already in frame
            {
              int found = -1;
              for(int k=0; k<queue.length; k++) //Update queue
              {
                if(queue[k] == string_seq[i])
                {
                  found = k;
                  break;
                }
              }

              if(found != -1){
                for(int k = found; k<queue.length - 1; k++)
                {
                  queue[k] = queue[k+1];
                }

                queue[queue.length - 1] = string_seq[i];
              }

              break;
            }
            else if(notInFrame+1 == frameSize)  //Page Fault Occurred 
            {
              System.out.printf("Page Fault occurred with reference: " + string_seq[i] + "\n");
              numFaults++;

              for(int k=0; k<frameSize; k++) //Loop to find location of least recently called page in frame table
              {
                if(frameTable[k] == queue[0])
                {
                  frameTable[k] = string_seq[i];
                  break;
                }
              }
              
              for(int k=0; k<queue.length; k++) //Update queue
              {
                if(k+1 == queue.length)
                {
                  queue[k] = string_seq[i];
                  break;
                }
                queue[k] = queue[k+1]; // End Update
              }
              break;
            }
            notInFrame++;
        }
        printFrame(queue);
      }
      System.out.printf("Number of Page Faults: " + numFaults + "\n");
  }

  
     //VOID simulateFIFO Description
	/********************************************************
	Purpose:  Preforms FIFO replacements
	Incoming: int[] string_seq, int frameSize
	Outgoing: Nothing
	**********************************************************/
	public void simulateFIFO()
	{
		int[] frameTable = new int[frameSize];    //Frame Table
		Arrays.fill(frameTable, -1);
		int trackFIFO = 0;                        //Index tracker, for FIFO swaps
		int numFaults = 0, notInFrame = 0;

		for(int i=0; i<string_seq.length; i++)
		{
			notInFrame = 0;
			System.out.printf("Page Referenced: " + string_seq[i] + "\n");
			for(int j=0; j<frameTable.length; j++)
			{
				if(frameTable[j] == -1) //First Inserts
				{
					boolean inTable=false;
					for(int k=0; k<frameTable.length; k++)	//Check if value is already in Frame Table
					{
						if(string_seq[i] == frameTable[k])
						{
						 inTable = true;
						}
					}
					
					if(inTable != true)
					{
            System.out.printf("Page Fault occurred with reference: " + string_seq[i] + "\n");
						numFaults++;
						frameTable[j] = string_seq[i]; //Value in queue
						break;
					}
				}
        else if(frameTable[j] == string_seq[i]) //In frame
        {
          break;
        }
				else if(notInFrame+1 == frameTable.length) //Page Fault
				{
					if(trackFIFO == frameTable.length)  //Reset Tracker
					{
						trackFIFO = 0;
					}

					System.out.printf("Page Fault occurred with reference: " + string_seq[i] + "\n");
					frameTable[trackFIFO] = string_seq[i];
					trackFIFO++;
          numFaults++;
					break;
				}
				notInFrame++;
			}
			printFrame(frameTable);
		}
	 	System.out.printf("Number of Page Faults: " + numFaults);
  }

  //VOID simulateOptimal Description
	/********************************************************
	Purpose:  Preforms Optimal replacements
	Incoming: int[] string_seq, int frameSize
	Outgoing: Nothing
	**********************************************************/
	public void simulateOptimal()
	{
		int[] frameTable = new int[frameSize];    //Frame Table
		Arrays.fill(frameTable, -1);
		int numFaults=0, notInFrame=0; 

		for(int i=0; i<string_seq.length; i++)
		{
			notInFrame=0;
			System.out.printf("Page Referenced: " + string_seq[i] + "\n");
			for(int j=0; j<frameTable.length; j++)
			{
				if(frameTable[j] == -1) //First Inserts
				{
					boolean inTable=false;
					for(int k=0; k<frameTable.length; k++)	//Chack if value is already in Frame Table
					{
						if(string_seq[i] == frameTable[k])
						{
						 inTable = true;
						}
					}
					
					if(inTable != true)
					{
						System.out.printf("Page Fault occurred with reference: " + string_seq[i] + "\n");
						numFaults++;
						frameTable[j] = string_seq[i]; //Value in queue
						break;
					}
				}else if(frameTable[j] == string_seq[i])
				{
					break;
				}
				else if(notInFrame+1 == frameTable.length)	//Page Fault
				{
					int swapIndex = -1, furthestIndex = -1;
					numFaults++;

					System.out.printf("Page Fault occurred with reference: " + string_seq[i] + "\n");
					for(int k=0; k<frameTable.length; k++)
					{
						int tempIndex = -1;
						for(int l = k + 1; l<string_seq.length; l++)
						{
							if(frameTable[k] == string_seq[l])
							{
								tempIndex = l;
								break;
							}
						}

						if(tempIndex == -1)	//Page isn't called again
						{
							swapIndex = k;
							break;
						}

						if(tempIndex > furthestIndex)
						{
							furthestIndex = tempIndex;
							swapIndex = k;
						}
					}

					frameTable[swapIndex] = string_seq[i];
				}
				notInFrame++;
			}
			printFrame(frameTable);
		}
		System.out.printf("Number of Page Faults: " + numFaults);
	}


}
