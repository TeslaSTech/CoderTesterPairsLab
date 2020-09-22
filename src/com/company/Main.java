package com.company;

/*
Valmik Revankar
Coder/Tester lab
9/18/2020
Extra: Integrity checking the file (to an extent).
*/

// imports
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
            // side note: this only checks if the block numbers are valid. it does NOT check for the
            // comma afterwards, nor for the name to be in the correct format.
            if (students != block1 + block2) {
                System.out.println("The file format is invalid.");
                System.exit(1);
            } else {
                System.out.println("The file format is valid, continuing...\n\n");
            }

            // initialize an array to make data processing easier

            String[] people = new String[students+1];

            // now we reset the Scanner
            read = new Scanner(classlist);

            // Turn the file into an array so we don't have to keep reading it
            for (int i = 0; i < students; i++) {
                people[i] = read.nextLine();
            }

            // Now this is where the actual data processing begins.
            // This snippet chops up all the data to prepare it to be processed.
            int startat = 0, runto = 0;
            char block = 'a';
            String[] selectionSet = {};
            if (blockChoice == 1 | blockChoice == 2) {
                switch (blockChoice) {
                    case 1 -> {
                        runto = block1;
                        block = '1';
                    }
                    case 2 -> {
                        startat = block1;
                        runto = block2 + block1;
                        block = '2';
                    }
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
            // Disabled because, as the Russian CS:GO kid said, "I don' need it"
            // [coming to get it]

            // Now it's time to assign coders and testers.
            String[] coders = new String[selectionSet.length];
            String[] testers = new String[selectionSet.length];
            // "why you... why you boolean me" - not s1mple
            boolean[] takenTester = new boolean[selectionSet.length];

            for (int i = 0; i < selectionSet.length; i++) coders[i] = selectionSet[i];

            // This is where the alphabetic sorting happens.
            // I chose to do it by coder.
            for (int i = 0; i < selectionSet.length; i++) {
                for (int j = i + 1; j < selectionSet.length; j++) {
                    if (coders[i].compareTo(coders[j])>0) {
                        String temp = coders[i];
                        coders[i] = coders[j];
                        coders[j] = temp;
                    }
                }
            }

            // Now we generate the pairs.
            // Did someone say "DNA building?"
            // Because if you did, please leave now.
            for (int i = 0; i < selectionSet.length; i++) {
                // This whole section is for avoiding duplicates.
                int tester = (int) Math.floor(Math.random()*selectionSet.length);
                if (coders[tester].equals(testers[tester])) {
                    while(coders[tester].equals(testers[tester])) {
                        tester = (int) Math.floor(Math.random()*selectionSet.length);
                    }
                }
                if (takenTester[tester]) {
                    while (takenTester[tester]) {
                        tester = (int) Math.floor(Math.random()*selectionSet.length);
                    }
                }
                // Once a new pair with zero duplicates has been chosen,
                // those slots are marked as "taken" by the program
                // so they don't get taken again
                takenTester[tester] = true;
                testers[i] = selectionSet[tester];
            }
            // And here is where the results are outputted to the screen.
            System.out.format("%-30s %-30s", "Coder", "Tester");
            System.out.println("\n-----------------------------------------------------------");
            for (int i = 0; i < coders.length; i++) {
                System.out.format("%-30s %-30s", coders[i], testers[i]);
                System.out.println();
            }

        } catch (FileNotFoundException fne) {
            // Certain Java functions tend to get a little angery™
            // and start throwing stuff called "exceptions" around.
            // Luckily, this catch statement used to be a professional baseball catcher.
            System.out.println(fne.toString());
        }
    }

    // This function outputs an array and its length.
    public static void Debug(String[] arrayIn) {
        System.out.print("[");
        for (int i = 0; i < arrayIn.length - 1; i++) {
            System.out.print(arrayIn[i] + ", ");
        }
        System.out.println(arrayIn[arrayIn.length-1] + "]");
        System.out.println("Array length: " + arrayIn.length);
    }
}