//import java.io.*;
//import java.util.*;
//
//public class EightQueenUCS {
//    PriorityQueue<Board> open, closed;
//    BufferedWriter bw;
//    final int MAX = 8;
//
//    public EightQueenUCS() {
//        open = new PriorityQueue<>();
//        closed = new PriorityQueue<>();
//        bw = new BufferedWriter(new OutputStreamWriter(System.out));
//    }
//
//    public void init() {
//        int[] startState = new int[MAX];
//        Arrays.fill(startState, -1);
//        open.add(new Board(startState, 0));
//    }
//
//    public void search() {
//        while (!open.isEmpty()) {
//            Board n = open.poll();
//            printState(n);
//            if (isGoalState(n)) {
//                printState(n);
//                break;
//            } else {
//                closed.add(n);
//            }
//            //printState(n);
//            expand(n);
//        }
//    }
//
//    public void expand(Board n) {
//        int[] queens = n.getQueens();
//        int neg = -1;
//        for(int i = 0 ; i < queens.length ; ++i) {
//            if(queens[i] == -1) {
//                neg = i;
//                break;
//            }
//        }
//        if(neg != -1) {
//            for(int i = 0 ; i < MAX ; ++i) {
////                int[] queensCopy = Arrays.copyOf(queens, queens.length);
//                queens[neg] = i;
//                if(isValid(queens, neg + 1)) {
//                    System.out.println(Arrays.toString(queens) + " " + neg + " " + n.getCost());
//                    Board temp = new Board(queens, n.getCost() + 1);
//                    open.add(temp);
//                    if(!open.contains(temp) && !closed.contains(temp)) {
//                        temp.setCost(n.getCost() + 1);
//                        open.add(temp);
//                    } else {
//                        Board ret = get(temp.getQueens());
//                        if(ret != null) {
//                            temp.setCost(Math.min(ret.getCost(), n.getCost() + 1));
//                            if(temp.getCost() != ret.getCost()) { //This means cost has been revised
//                                if(open.contains(temp)) {
//                                    open.remove(temp);
//                                    open.add(temp);
//                                } else {
//                                    closed.remove(temp);
//                                    open.add(temp);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//    }
//
//    public Board get(int[] queens) {
//        for(Board b : open) if(Arrays.equals(b.getQueens(), queens)) return b;
//        for(Board b : closed) if(Arrays.equals(b.getQueens(), queens)) return b;
//        return null;
//    }
//
//    public boolean isGoalState(Board n) {
//        int[] queens = n.getQueens();
//        for (int i : queens) if (i == -1) return false;
//        return true;
//    }
//
//    public void printState(Board n) {
//        try {
//            int[] queens = n.getQueens();
//            for (int i = 0; i < MAX; ++i) {
//                for (int j = 0; j < MAX; ++j) {
//                    if (j == queens[i]) bw.write("1");
//                    else bw.write("0");
//                    bw.flush();
//                }
//                bw.write("\n");
//                bw.flush();
//            }
//        } catch (IOException ex) {
//        }
//    }
//
//    public boolean isValid(int[] queens, int k) {
//        //System.out.println(Arrays.toString(queens));
//        for(int i = 0 ; i < k ; ++i) {
//            for(int j = i + 1 ; j < k ; ++j) {
//                if(Math.abs(queens[j] - queens[i]) == (j - i)) return false;
//                if(queens[i] == queens[j]) return false;
//            }
//        }
//        return true;
//    }
//
//    public static void main(String[] args) {
//        EightQueenUCS eightQueenUCS = new EightQueenUCS();
//        eightQueenUCS.init();
//        eightQueenUCS.search();
//    }
//
//}
//
//class Board implements Comparable<Board> {
//    private int queens[];
//    private int cost;
//
//    public Board(int[] queens, int cost) {
//        this.queens = queens;
//        this.cost = cost;
//    }
//
//    public int getCost() {
//        return cost;
//    }
//
//    public void setCost(int cost) {
//        this.cost = cost;
//    }
//
//    public int[] getQueens() {
//        return queens;
//    }
//
//    public void setQueens(int[] queens) {
//        this.queens = queens;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Board)) return false;
//        Board board = (Board) o;
//        return Arrays.equals(getQueens(), board.getQueens());
//    }
//
//    @Override
//    public int hashCode() {
//        return Arrays.hashCode(getQueens());
//    }
//
//    @Override
//    public int compareTo(Board board) {
//        return this.getCost() - board.getCost();
//    }
//}