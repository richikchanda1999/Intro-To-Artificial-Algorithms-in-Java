package TravellingSalesman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class TSPMain {
    static void solve(BufferInput bi, BufferOutput bo) throws IOException {
        int N = 4;
        int cost[][] = new int[N][N];
        cost[0][0] = cost[1][1] = cost[2][2] = cost[3][3] = 0;
        cost[0][1] = cost[1][0] = 9;
        cost[0][2] = cost[2][0] = 4;
        cost[0][3] = cost[3][0] = 6;
        cost[1][2] = cost[2][1] = 4;
        cost[1][3] = cost[3][1] = 7;
        cost[2][3] = cost[3][2] = 1;

        bo.writeString("Generated graph is :\n");
        bo.flush();
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                bo.writeInt(cost[i][j]);
                bo.writeString(" ");
            }
            bo.writeString("\n");
            bo.flush();
        }

        TSP_A_Star tsp_a_star = new TSP_A_Star(N);
        tsp_a_star.solve(cost, N, 0);
        tsp_a_star.printPath();
    }

    public static void main(String[] args) throws IOException {
        BufferInput bufferInput = new BufferInput();
        BufferOutput bufferOutput = new BufferOutput();

        //int T = bufferInput.nextInt();
        //while(T-- > 0)
        TSPMain.solve(bufferInput, bufferOutput);

        bufferOutput.flush();
        bufferOutput.close();
        bufferInput.close();
    }

    static class BufferInput {

        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public BufferInput() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64];
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    break;
                }
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public String nextString() throws IOException {
            byte c = read();
            while (Character.isWhitespace(c)) {
                c = read();
            }
            StringBuilder builder = new StringBuilder();
            builder.append((char) c);
            c = read();
            while (!Character.isWhitespace(c)) {
                builder.append((char) c);
                c = read();
            }
            return builder.toString();
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg) {
                c = read();
            }
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg) {
                return -ret;
            }
            return ret;
        }

        public int[] nextIntArray(int n) throws IOException {
            int arr[] = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = nextInt();
            }
            return arr;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg) {
                c = read();
            }
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg) {
                return -ret;
            }
            return ret;
        }

        public long[] nextLongArray(int n) throws IOException {
            long arr[] = new long[n];
            for (int i = 0; i < n; i++) {
                arr[i] = nextLong();
            }
            return arr;
        }

        public char nextChar() throws IOException {
            byte c = read();
            while (Character.isWhitespace(c)) {
                c = read();
            }
            return (char) c;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg) {
                c = read();
            }

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg) {
                return -ret;
            }
            return ret;
        }

        public double[] nextDoubleArray(int n) throws IOException {
            double arr[] = new double[n];
            for (int i = 0; i < n; i++) {
                arr[i] = nextDouble();
            }
            return arr;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1) {
                buffer[0] = -1;
            }
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) {
                fillBuffer();
            }
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null) {
                return;
            }
            din.close();
        }
    }

    static class BufferOutput {

        private DataOutputStream dout;
        final private int BUFFER_SIZE = 1 << 16;
        private byte[] buffer;
        private int pointer = 0;

        public BufferOutput() {
            buffer = new byte[BUFFER_SIZE];
            dout = new DataOutputStream(System.out);
        }

        public BufferOutput(OutputStream out) {

            buffer = new byte[BUFFER_SIZE];
            dout = new DataOutputStream(out);
        }

        public void writeBytes(byte arr[]) throws IOException {

            int bytesToWrite = arr.length;

            if (pointer + bytesToWrite >= BUFFER_SIZE) {
                flush();
            }

            for (int i = 0; i < bytesToWrite; i++) {
                buffer[pointer++] = arr[i];
            }
        }

        public void writeString(String str) throws IOException {
            writeBytes(str.getBytes());
        }

        public void writeInt(int n) throws IOException {
            writeString(String.valueOf(n));
        }

        public void writeLong(long l) throws IOException {
            writeString(String.valueOf(l));
        }

        public void writeDouble(double d) throws IOException {
            writeString(String.valueOf(d));
        }

        public void flush() throws IOException {
            dout.write(buffer, 0, pointer);
            dout.flush();
            pointer = 0;
        }

        public void close() throws IOException {
            dout.close();
        }
    }
}