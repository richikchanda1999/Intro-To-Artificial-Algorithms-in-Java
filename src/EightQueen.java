import java.io.*;
import java.util.*;

public class EightQueen {
    private final int MAX = 8;
    private PriorityQueue<State> open, closed;
    private int count = 1;
    public EightQueen() {
        open = new PriorityQueue<>();
        closed = new PriorityQueue<>();
    }

    public void expand(State n) {
        Board board = n.getBoard();
        int cost = n.getCost();
        int queens[] = board.getQueens();
        int index = board.getCurrent();

        if(index != MAX) {
            for(int i = 0 ; i < MAX ; ++i) {
                int[] temp = Arrays.copyOf(queens, queens.length);
                temp[index] = i;
                Board b = new Board(temp, index + 1);
                open.add(new State(b, n.getCost() + 1));
            }
        }
    }

    public boolean isValid(Board board) {
        if(board.getCurrent() != MAX) return false;
        int[] queens = board.getQueens();
        for(int i = 0 ; i < MAX ; ++i) {
            for(int j = 0 ; j < MAX ; ++j) {
                if(i == j) continue;
                if(queens[i] == queens[j]) return false;
                if(Math.abs(queens[i] - queens[j]) == Math.abs(i - j)) return false;
            }
        }
        return true;
    }

    public boolean isGoalState(State n) {
        Board board = n.getBoard();
        int[] queens = board.getQueens();
        if(!isValid(board)) return false;
        return true;
    }

    public void printState(State n) {
        int queens[] = n.getBoard().getQueens();
        for(int i = 0 ; i < MAX ; ++i) {
            for(int j = 0 ; j < MAX ; ++j) {
                if(j == queens[i]) System.out.print("1 ");
                else System.out.print("0 ");
            }
            System.out.println();
        }
    }

    public void findSolution() {
        int startQueens[] = new int[MAX];
        Arrays.fill(startQueens, -1);
        State startState = new State(new Board(startQueens, 0), 0);
        open.add(startState);
        while(!open.isEmpty()) {
            State n = open.poll();
            if(isGoalState(n)) {
                System.out.println();
                System.out.println("Solution : " + count++ + ", Cost : " + n.getCost());
                printState(n);
                System.out.println();
            } else {
                expand(n);
            }
        }
    }
}

class State implements Comparable<State> {
    private Board board;
    private int cost;

    public State(Board board, int cost) {
        this.board = board;
        this.cost = cost;
    }

    public Board getBoard() {
        return board;
    }

    public int getCost() {
        return cost;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(State state) {
        return cost - state.getCost();
    }
}

class Board implements Comparable<Board> {
    private int queens[];
    private int curr;

    public Board(int[] queens, int curr) {
        this.queens = queens;
        this.curr = curr;
    }

    public Board(int[] queens) {
        this(queens, 0);
    }

    public int[] getQueens() {
        return queens;
    }

    public void setQueens(int[] queens) {
        this.queens = queens;
    }

    public int getCurrent() {
        return curr;
    }

    public void setCurrent(int curr) {
        this.curr = curr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;
        return Arrays.equals(getQueens(), board.getQueens());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getQueens());
    }

    @Override
    public int compareTo(Board board) {
        return Arrays.compare(queens, board.getQueens());
    }
}