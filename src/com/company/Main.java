package com.company;

/*
Valmik Revankar
Tester/Coder Lab
9/18/2020
*/

// basic setup
import java.util.Random;
import java.util.Scanner;
import java.io.*;
public class Main {
    public static void main(String[] args) {
        // Setup
        try {
            File classlist = new File("src/com/company/classlist.txt");
            Scanner in = new Scanner(System.in);
            Scanner read = new Scanner(classlist);
            int students = 0, block1 = 0, block2 = 0;

            // block selection menu
            System.out.print("What block would you like to select testers for? \n1) Block 1\n2) Block 2\n3) Both blocks\nType the number for your desired choice: ");
            int blockChoice = in.nextInt();

            // how many people are in the file?
            while (read.hasNextLine()) {
                String temp = read.nextLine();
                students++;
                if (temp.charAt(0) == '1')
                    block1++;
                else if (temp.charAt(0) == '2')
                    block2++;
            }

            // check if the input is valid
            if (students != block1 + block2) {
                System.out.println("The file format is invalid.");
                System.exit(1);
            } else {
                System.out.println("The file format is valid, continuing...");
            }

            // initialize an array to make data processing easier

            String people[] = new String[students+1];

            // now we reset the Scanner
            read = new Scanner(classlist);

            // Turn the file into an array so we don't have to keep reading the file
            for (int i = 0; i < students; i++) {
                people[i] = read.nextLine();
            }

            // Now we set up the sorting. We divide the array into two smaller ones for each block
            // only if the user wanted one block only !! (this makes the program more optimized)
            int startat = 0, runto = 0;
            char block = 'a';
            String selectionSet[] = {};
            if (blockChoice == 1 | blockChoice == 2) {
                switch(blockChoice) {
                    case 1:
                        startat = 0;
                        runto = block1;
                        block = '1';
                        break;
                    case 2:
                        startat = block1;
                        runto = block2+block1;
                        block = '2';
                        break;
                }
                selectionSet = new String[runto-startat];
                int k = 0;
                for (int i = 0; i < runto; i++) {
                    if (people[i].charAt(0) == block) {
                        selectionSet[k] = people[i].substring(2);
                        k++;
                    }
                }
            } else if (blockChoice == 3) {
                selectionSet = new String[block1 + block2];
                for (int i = 0; i < block1+block2; i++) {
                    selectionSet[i] = people[i].substring(2);
                }
            }
            // Debug(selectionSet); // This is a small function that outputs the array

            boolean taken[] = new boolean[selectionSet.length];
            boolean exit = false, breaker = false;
            int person1 = 0, person2 = 0, person3 = 0;
            for (int i = 0; i < (taken.length / 2);) {
                while (breaker == false) {
                    person1 = (int) (Math.random()*taken.length);
                    person2 = (int) (Math.random()*taken.length);
                    if (person1 == person2) {
                        person1 = (int) (Math.random()*taken.length);
                        person2 = (int) (Math.random()*taken.length);
                        if (taken.length%2 != 0 && i == (taken.length/2)-2) {
                            person1 = (int) (Math.random()*taken.length);
                            person2 = (int) (Math.random()*taken.length);
                            person3 = (int) (Math.random()*taken.length);
                        }
                    } else if (person1 != person2) {
                        break;
                    } else {
                        break;
                    }
                }
                if (taken[person1] == false && taken[person2] == false) {
                    if (person1 != person2) {
                        if ((taken.length %2 == 0) || (taken.length %2 != 0 && i < (taken.length/2)-1)) {
                            System.out.println(selectionSet[person1] + " is the partner for " + selectionSet[person2] + ".");
                            taken[person1] = true;
                            taken[person2] = true;
                            i++;
                        } else {
                            if (person1 != person3 && person2 != person3) {
                                System.out.println(selectionSet[person1] + ", " + selectionSet[person2] + ", and " + selectionSet[person3] + " are working in a group of 3.");
                                i++;
                            }
                        }
                    }

                }


            }

        } catch (FileNotFoundException fne) {
            System.out.println(fne.toString());
        }



    }

    // This function outputs an array and its length.
    public static void Debug(String arrayIn[]) {
        System.out.print("[");
        for (int i = 0; i < arrayIn.length - 1; i++) {
            System.out.print(arrayIn[i] + ", ");
        }
        System.out.println(arrayIn[arrayIn.length-1] + "]");
        System.out.println("Array length: " + arrayIn.length);
    }
}