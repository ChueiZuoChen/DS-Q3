import model.LottoNumber;
import model.Player;
import java.util.Scanner;


public class LottoGame {
    public static void main(String[] args) {
        Player[] players = generateAllPlayersNumbers();
        int[] winningNumbers = new LottoNumber().getNumbers(8);
        sort(winningNumbers);
        findWinner(players, winningNumbers);
        Scanner sc = new Scanner(System.in);
        try {
            String input = "";
            while (!input.equals("exit")) {
                System.out.print("\n[1] Display statistics of winners.\n" +
                        "[2] Check my lotto status.\n" +
                        "[3] Show Initialized data.\n" +
                        "[4] Exit.\n" +
                        "Select Function Number: ");
                input = sc.next();
                switch (input) {
                    case "1":
                        printWinningNumbers(winningNumbers);
                        int[] winnerclass = findWinner(players, winningNumbers);
                        System.out.println("Winner class\tTotal number of winners");
                        for (int i = 0; i < winnerclass.length; i++) {
                            System.out.println(i + 1 + "st Class\t\t\t\t" + winnerclass[i]);
                        }
                        break;
                    case "2":
                        System.out.print("Input Player's ID: ");
                        int num = sc.nextInt();
                        display(players, num, winningNumbers);
                        break;
                    case "3":
                        showAllPlayersContent(players);
                        System.out.println();
                        showAllWinners(players);
                        break;
                    case "4":
                        input = "exit";
                        System.out.println("[SYSTEM] SHUT DOWN...");
                        break;
                    default:
                        System.out.println("[Error] Invlaid input, please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void showAllWinners(Player[] players) {
        System.out.println("[ Winners ]");
        StringBuilder sb1 = new StringBuilder("1st class winners: ");
        StringBuilder sb2 = new StringBuilder("2nd class winners: ");
        StringBuilder sb3 = new StringBuilder("3rd class winners: ");
        StringBuilder sb4 = new StringBuilder("4th class winners: ");
        for (int i = 0; i < players.length; i++) {
            switch (players[i].getClassNumber()) {
                case 1:
                    sb1.append(i).append(", ");
                    break;
                case 2:
                    sb2.append(i).append(", ");
                    break;
                case 3:
                    sb3.append(i).append(", ");
                    break;
                case 4:
                    sb4.append(i).append(", ");
                    break;
            }
        }
        System.out.println(sb1);
        System.out.println(sb2);
        System.out.println(sb3);
        System.out.println(sb4);
    }

    private static void printWinningNumbers(int[] winningNumbers) {
        System.out.println("\nWinning Number");
        System.out.println("PWNs\t\t\t\tSWNs");
        for (int i = 0; i < winningNumbers.length; i++) {
            if (i == 6) {
                System.out.print("\t");
            }
            System.out.print(winningNumbers[i] + " ");
        }
        System.out.println();
    }

    private static void display(Player[] players, int num, int[] winningNumbers) {
        System.out.println("Player's ID:\t" + num);
        System.out.println("Player's game-numbers:\t" + players[num].toString());
        System.out.print("PWNs:\t");
        for (int i = 0; i < 6; i++) {
            System.out.print(winningNumbers[i] + " ");
        }
        System.out.print("\nSWNs:\t");
        for (int i = 6; i < winningNumbers.length; i++) {
            System.out.print(winningNumbers[i] + " ");
        }
        System.out.println("\nPlayer's status:\t" + players[num].getStatus());
    }

    private static int[] findWinner(Player[] players, int[] winningNumbers) {
        int[] winnerClass = new int[4];
        int[] newPWNs = new int[6];
        int[] newSWNs = new int[2];
        for (int i = 0; i < winningNumbers.length - 2; i++) {
            newPWNs[i] = winningNumbers[i];
        }
        newSWNs[0] = winningNumbers[6];
        newSWNs[1] = winningNumbers[7];

        for (int i = 0; i < players.length; i++) {
            int pwnsCount = 0;
            int swnsCount = 0;
            for (int j = 0; j < players[i].getNumbers().length; j++) {
                if (myBinarySearch(newPWNs, players[i].getNumbers()[j]) >= 0) {
                    pwnsCount++;
                }
                if (myBinarySearch(newSWNs, players[i].getNumbers()[j]) >= 0) {
                    swnsCount++;
                }
                setClass(players[i], pwnsCount, swnsCount, winnerClass);
            }
        }
        return winnerClass;
    }

    //
    private static int myBinarySearch(int[] arr, int key) {
        int low = 0;
        int upper = arr.length - 1;
        while (low <= upper) {
            int mid = (low + upper) / 2;
            if (arr[mid] < key) {
                low = mid + 1;
            } else if (arr[mid] > key) {
                upper = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private static void setClass(Player player, int pwnsCount, int swnsCount, int[] winnerClass) {
        if ((pwnsCount < 3) && (swnsCount < 2)) {
            player.setClassNumber(0);
            player.setStatus("You are not a winner. Thanks for playing lotto. Good luck next time!");
        } else if ((pwnsCount < 3) && (swnsCount == 2)) {
            player.setStatus("You are a 4th class winner, congratulations!");
            player.setClassNumber(4);
            winnerClass[3]++;
        } else if (pwnsCount == 3) {
            player.setStatus("You are a 4th class winner, congratulations!");
            player.setClassNumber(4);
            winnerClass[3]++;
        } else if (pwnsCount == 4) {
            player.setStatus("You are a 3rd class winner, congratulations!");
            player.setClassNumber(3);
            winnerClass[2]++;
        } else if (pwnsCount == 5) {
            player.setStatus("You are a 2rd class winner, congratulations!");
            player.setClassNumber(2);
            winnerClass[1]++;
        } else if (pwnsCount == 6) {
            player.setStatus("You are a 3rd class winner, congratulations!");
            player.setClassNumber(1);
            winnerClass[0]++;
        }
    }


    private static void sort(int[] winningNumbers) {
        int[] PWNs = new int[6];
        int[] SWNs = new int[2];
        int count = 0;
        for (int i = 0; i < winningNumbers.length; i++) {
            if (i < 6) {
                PWNs[i] = winningNumbers[i];
            } else {
                SWNs[count] = winningNumbers[i];
                count++;
            }
        }
        int[] sortedPWNs = insertionSort(PWNs);
        int[] sortedSWNs = selectionSort(SWNs);
        count = 0;
        for (int i = 0; i < winningNumbers.length; i++) {
            if (i < 6) {
                winningNumbers[i] = sortedPWNs[i];
            } else {
                winningNumbers[i] = sortedSWNs[count];
                count++;
            }
        }
    }

    private static int[] selectionSort(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[j] < numbers[index]) {
                    index = j;
                }
            }
            int smallerNumber = numbers[index];
            numbers[index] = numbers[i];
            numbers[i] = smallerNumber;
        }
        return numbers;
    }

    public static int[] insertionSort(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int key = numbers[i];
            int index = i - 1;
            while ((index > -1) && (numbers[index] > key)) {
                numbers[index + 1] = numbers[index];
                index--;
            }
            numbers[index + 1] = key;
        }
        return numbers;
    }

    public static void merge(int[] arrayLeft, int[] arrayRight, int[] numbers, int leftSize, int rightSize) {
        int i = 0, l = 0, r = 0;
        while (l < leftSize && r < rightSize) {
            if (arrayLeft[l] < arrayRight[r]) {
                numbers[i++] = arrayLeft[l++];
            } else {
                numbers[i++] = arrayRight[r++];
            }
        }
        while (l < leftSize) {
            numbers[i++] = arrayLeft[l++];
        }
        while (r < rightSize) {
            numbers[i++] = arrayRight[r++];
        }
    }

    public static int[] mergeSort(int[] numbers, int size) {
        if (size < 2) {
            return numbers;
        }

        int mid = size / 2;
        int[] left_arr = new int[mid];
        int[] right_arr = new int[size - mid];
        int k = 0;
        for (int i = 0; i < size; ++i) {
            if (i < mid) {
                left_arr[i] = numbers[i];
            } else {
                right_arr[k] = numbers[i];
                k = k + 1;
            }
        }
        mergeSort(left_arr, mid);
        mergeSort(right_arr, size - mid);
        merge(left_arr, right_arr, numbers, mid, size - mid);
        return numbers;
    }

    public static Player[] generateAllPlayersNumbers() {
        Player[] players = new Player[1000];
        Player player;
        for (int i = 0; i < 1000; i++) {
            int[] numbers = new LottoNumber().getNumbers(6);
            int[] sortedNumbers = mergeSort(numbers, numbers.length);
            player = new Player();
            player.setNumbers(sortedNumbers);
            players[i] = player;
        }

        return players;
    }

    public static void showAllPlayersContent(Player[] players) {
        for (int i = 0; i < 1000; i++) {
            StringBuilder numberString = new StringBuilder();
            int[] number = players[i].getNumbers();
            for (int j = 0; j < 6; j++) {
                numberString.append(number[j]).append(", ");
            }
            System.out.println("Player " + i + ": " + numberString);
        }
    }
}
