package cinema;

import java.util.Scanner;

public class Cinema {

    private static void displaySeats(char[][] seats){
        System.out.printf("%d %d\n", seats.length, seats[0].length);
        System.out.print("Cinema:\n  ");
        for(int j = 1; j <= seats[0].length; j++){
            System.out.printf("%d ", j);
        }
        System.out.println();
        for(int i = 1; i <= seats.length; i++){
            System.out.printf("%d ", i);
            for(int j = 1; j <= seats[0].length; j++){
                System.out.printf("%s ", seats[i - 1][j - 1]);
            }
            System.out.println();
        }
    }

    private static char[][] createMatrix(int rows, int seatsPerRow){
        char[][] matrix = new char[rows][seatsPerRow];
        for(char[] array : matrix){
            for(int i = 0; i < seatsPerRow; i++){
                array[i] = 'S';
            }
        }
        return matrix;
    }

    private static int getPrice(int row, char[][] seats){
        if(seats.length * seats[0].length <= 60)
            return 10;
        else
        if(row <= seats.length / 2)
            return 10;
        else
            return 8;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();

        //create the cinema room
        char[][] seats = createMatrix(rows, seatsPerRow);

        showMenu(seats, scanner);
    }

    private static void showMenu(char[][] seats, Scanner scanner) {
        boolean running = true;
        while(running){
            System.out.println("1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit");
            int choice = scanner.nextInt();
            System.out.println();
            switch(choice){
                case 1:
                    displaySeats(seats);
                    break;
                case 2:
                    int x, y;
                    while(true) {
                        System.out.print("Enter a row number:\n");
                        x = scanner.nextInt();
                        System.out.print("Enter a seat number in that row:\n");
                        y = scanner.nextInt();

                        //Check if seat is already taken
                        if(x > seats.length || y > seats[0].length) {
                            System.out.println("Wrong input!\n");
                            continue;
                        }
                        if(seats[x - 1][y - 1] == 'B') {
                            System.out.println("That ticket has already been purchased!\n");
                            continue;
                        }
                        break;
                    }
                    //Get price of seat and modify that seat
                    int price = getPrice(x, seats);
                    System.out.printf("Ticket price: $%d\n\n", price);
                    seats[x - 1][y - 1] = 'B';
                    break;
                case 3:
                    showStatistics(seats);
                    break;
                default:
                    running = false;
                    break;
            }
            System.out.println();
        }
    }

    private static int getTotalIncome(char[][] seats){
        if(seats.length * seats[0].length <= 60){
            return seats.length * seats[0].length * 10;
        }
        return seats.length / 2 * seats[0].length * 10 + (seats.length - seats.length / 2) * seats[0].length * 8;
    }

    private static void showStatistics(char[][] seats) {
        //Nr. of seats
        int cnt = 0;
        for(char[] row : seats){
            for(char seat : row){
                if(seat == 'B') cnt++;
            }
        }
        System.out.printf("Number of purchased tickets: %d\n", cnt);

        //Percentage
        System.out.printf("Percentage: %.2f", (double)cnt * 100 / (double)(seats.length * seats[0].length));
        System.out.println("%");

        //Income
        System.out.printf("Current income: $%d\n", getIncome(seats));

        //Possible income
        System.out.printf("Total income: $%d\n", getTotalIncome(seats));
    }

    private static int getIncome(char[][] seats) {
        int sum = 0;
        for(int i = 0; i < seats.length; i++){
            for(char seat : seats[i]){
                if(seat == 'B'){
                    if(i + 1 <= seats.length / 2){
                        sum += 10;
                    }
                    else
                        sum += seats.length * seats[0].length <= 60 ? 10 : 8;
                }
            }
        }
        return sum;
    }
}
