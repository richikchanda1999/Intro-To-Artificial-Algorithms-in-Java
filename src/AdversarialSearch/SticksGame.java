package AdversarialSearch;

import java.util.Scanner;

public class SticksGame {
    static final int MIN_STICKS = 1, MAX_STICKS = 3;
    
    public static State playerMax(int numOfSticks, int sticks, int player, int alpha, int beta) {
        if (numOfSticks == 1 && player == 1) return new State(1, 1, -1, beta);
        else if (numOfSticks == 1 && player == 2) return new State(1, 2, alpha, 1);
        else {
            int i = 0;
            for (int j = MIN_STICKS; j <= MAX_STICKS; ++j) {
                i = MAX_STICKS - j + 1;
                if (numOfSticks > i && player == 1) {
                    State state = playerMax(numOfSticks - i, i, 2, alpha, beta);
                    if (state.alpha < state.beta) alpha = state.beta;
                    if (alpha == 1 || alpha > beta) break;
                } else if (numOfSticks > i && player == 2) {
                    State state = playerMax(numOfSticks - i, i, 1, alpha, beta);
                    if (state.alpha < state.beta) beta = state.beta;
                    if (beta == -1 || alpha > beta) break;
                }
            }
            return new State(i, player, alpha, beta);
        }
    }

    private static int playAI(int numOfSticks) {
        State state = playerMax(numOfSticks, 0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        numOfSticks -= state.sticks;
        System.out.println("AI picked " + state.sticks + " sticks");
        System.out.println("Remaining sticks --- " + numOfSticks);
        return numOfSticks;
    }

    private static int playHuman(int numOfSticks) {
        int n = getStickInput();
        numOfSticks -= n;
        System.out.println("Remaining sticks --- " + numOfSticks);
        return numOfSticks;
    }

    public static void HUMANvsAI(int numOfSticks) {
        int tossWinner = getToss();
        System.out.println(tossWinner == 1 ? "AI won the toss" : "You won the toss");

        while (true) {
            if (tossWinner == 1) {
                numOfSticks = playAI(numOfSticks);
                if (numOfSticks == 0) {
                    System.out.println("You won!\nAI lost!");
                    break;
                }
                numOfSticks = playHuman(numOfSticks);
                if (numOfSticks == 0) {
                    System.out.println("AI won!\nYou lost!");
                    break;
                }
            } else {
                numOfSticks = playHuman(numOfSticks);
                if (numOfSticks == 0) {
                    System.out.println("AI won!\nYou lost!");
                    break;
                }
                numOfSticks = playAI(numOfSticks);
                if (numOfSticks == 0) {
                    System.out.println("You won!\nAI lost!");
                    break;
                }
            }
        }
    }

    public static int getStickInput() {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("How many sticks do you want to pick? (" + MIN_STICKS + " - " + MAX_STICKS + ") ");
            int n = sc.nextInt();
            if (n >= MIN_STICKS && n <= MAX_STICKS) return n;
            else
                System.out.println("No. of sticks should be within [" + MIN_STICKS + ", " + MAX_STICKS + "]");
        } while (true);
    }

    public static void AIvsAI(int numOfSticks) {
        int tossWinner = getToss();
        int tossLoser = tossWinner == 1 ? 2 : 1;
        System.out.println("Player " + tossWinner + " won the toss");

        while (true) {
            numOfSticks = playAI(numOfSticks);
            if (numOfSticks == 0) {
                System.out.println("Player " + tossLoser + " won!");
                System.out.println("Player " + tossWinner + " lost!");
                break;
            }

            numOfSticks = playAI(numOfSticks);
            if (numOfSticks == 0) {
                System.out.println("Player " + tossWinner + " won!");
                System.out.println("Player " + tossLoser + " lost!");
                break;
            }
        }
    }

    static int getToss() {
        return Math.random() >= 0.5 ? 2 : 1;
    }

    public static void main(String[] args) {
        // Initial setup
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("----------------- Welcome to the game of sticks -----------------");

            System.out.print("How many sticks are there on the board initally? ");
            int totalSticks = scanner.nextInt();
            while (totalSticks < 0) {
                System.out.println("Please enter a positive number between 10 and 100.");
                System.out.print("How many sticks are there on the board initally? ");
                totalSticks = scanner.nextInt();
            }

            System.out.println("Options:");
            System.out.println("Play against the computer (1)");
            System.out.println("Watch the computers play (2)");
            System.out.println("Exit (3)");
            System.out.print("Which option do you take? ");
            int option = scanner.nextInt();

            // Choose which game: 1 = human v AI, 2 = AI v AI
            if (option == 1)
                HUMANvsAI(totalSticks);
            else if (option == 2) AIvsAI(totalSticks);
            else if (option == 3) break;
            else System.out.println("Bad choice.");

        } while (true);

    }
}

class State {
    int sticks, player, alpha, beta;

    public State(int sticks, int player, int alpha, int beta) {
        this.sticks = sticks;
        this.player = player;
        this.alpha = alpha;
        this.beta = beta;
    }
}