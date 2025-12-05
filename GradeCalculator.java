package com.mycompany.gradecalculator;

import java.util.Scanner;

public class GradeCalculator {
    // Create Scanner
    static Scanner input = new Scanner(System.in);

    // Main Method
    public static void main(String[] args) {
        System.out.print("Enter number of courses: ");
        int courseCount = getValidInt(1, 100);

        String[] courseNames = new String[courseCount];
        double[] courseAverages = new double[courseCount];
        
        // Loop for the amount of courses going to be inputted
        for (int c = 0; c < courseCount; c++) {
            System.out.print("Enter course name: ");
            String courseName = input.nextLine();
            System.out.print("Enter section number: ");
            String section = input.nextLine();
            courseNames[c] = courseName + " - Section " + section;
            System.out.print("Enter number of students in " + courseNames[c] + ": ");
            int studentCount = getValidInt(1, 100);

            // Declare variables and arrays
            String[] names = new String[studentCount];
            String[] ids = new String[studentCount];
            double[] averages = new double[studentCount];
            String[] letterGrades = new String[studentCount];
            double[] scholarships = new double[studentCount];
            double[] extraCredits = new double[studentCount];

            int above80 = 0;
            int below70 = 0;
            double totalAverage = 0, highest = -1, lowest = 101;

            // Loop for each student in course asking for grades
            for (int i = 0; i < studentCount; i++) {
                System.out.println("\n--- Student " + (i + 1) + " ---");
                System.out.print("Enter student first name: ");
                String firstName = input.nextLine();
                System.out.print("Enter student last name: ");
                String lastName = input.nextLine();
                names[i] = lastName + ", " + firstName;

                System.out.print("Enter student ID: ");
                ids[i] = input.nextLine();
                
                // Method call for obtaining average of each grade category
                double quiz = getAverage("Quiz/Discussion (15%)");
                double exam = getAverage("Exam (20%)");
                double program = getAverage("Program (30%)");
                double slo = getAverage("SLO (35%)");
                
                // Calculates the average for the student
                double avg = quiz * 0.15 + exam * 0.20 + program * 0.30 + slo * 0.35;
                
                // Applies the extra credit
                System.out.print("Extra credit to apply (0-5): ");
                double extra = getValidDouble(0, 5);
                avg += extra;
                extraCredits[i] = extra;

                averages[i] = avg;
                letterGrades[i] = getLetterGrade(avg);
                
                // Calculates whether the student is eligible for scholarship
                // Calculates students with average above 80, average below 80, and highest and lowest average
                if (avg >= 85) scholarships[i] = calculateScholarship(avg);
                if (avg >= 80) above80++;
                if (avg <= 70) below70++;
                if (avg > highest) highest = avg;
                if (avg < lowest) lowest = avg;

                totalAverage += avg;
            }

            // Calculates the course average
            courseAverages[c] = totalAverage / studentCount;

            // Output for the entire course
            System.out.println("\n" + courseNames[c]);
            System.out.println("---------------------------------------------------------------------------------------------");
            double totalScholarship = 0;
            
            // Parallel array output
            for (int i = 0; i < studentCount; i++) {
                System.out.printf("%s | ID: %s | Avg: %.2f | Grade: %s | Extra Credit: %.1f | Scholarship: $%.2f\n",names[i], ids[i], averages[i], letterGrades[i], extraCredits[i], scholarships[i]);
                totalScholarship += scholarships[i];
            }
            System.out.printf("Course Average: %.2f\n", courseAverages[c]);
            System.out.println("Students above 80: " + above80);
            System.out.println("Students below 70: " + below70);
            System.out.printf("Highest Student Average: %.2f\n", highest);
            System.out.printf("Lowest Student Average: %.2f\n", lowest);
            System.out.printf("Total Scholarship Awarded: $%.2f\n", totalScholarship);
            System.out.println("");
        }
        
        // Final output at the end of the program listing all courses and their averages
        System.out.println("\n=== Final Report ===");
        for (int i = 0; i < courseCount; i++) {
            System.out.printf("%s | Average: %.2f\n", courseNames[i], courseAverages[i]);
        }
    }

    // Method for obtaining the average of a grade category
    public static double getAverage(String category) {
        double sum = 0;
        int count = 0;
        int index = 1;
        String line = "";
        System.out.println("Enter grades for " + category + ". Type 'stop' to end.");
        while (!line.equalsIgnoreCase("stop")) {
            System.out.print("Grade " + index + ": ");
            line = input.nextLine();
            try {
                double score = Double.parseDouble(line);
                if (score >= 0 && score <= 100) {
                    sum += score;
                    count++;
                    index++;
                } else {
                    System.out.println("Grade must be between 0 and 100.");
                }
                System.out.println("Enter grades for " + category + ". Type 'stop' to end.");
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number or 'stop'.");
            }
        }
        return count > 0 ? sum / count : 0;
    }

    // Exception handling method for obtaining an integer
    public static int getValidInt(int min, int max) {
        int value = min - 1;
        while (value < min || value > max) {
            try {
                value = Integer.parseInt(input.nextLine());
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max + ".");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Enter an integer.");
            }
        }
        return value;
    }

    // Method for obtaining a valid double
    public static double getValidDouble(double min, double max) {
        double value = min - 1;
        while (value < min || value > max) {
            try {
                value = Double.parseDouble(input.nextLine());
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max + ".");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
        return value;
    }

    // Switch method for calculating letter grade for the students
    public static String getLetterGrade(double avg) {
        switch ((int) avg / 10) {
            case 10:
            case 9:
                return "A";
            case 8:
                return "B";
            case 7:
                return "C";
            case 6:
                return "D";
            default:
                return "F";
        }
    }

    // Method for calculating scholarships for the students
    public static double calculateScholarship(double avg) {
        if (avg >= 95) {
            return 750;
        } else if (avg >= 90) {
            return 500;
        } else {
            return 250;
        }
    }
}
