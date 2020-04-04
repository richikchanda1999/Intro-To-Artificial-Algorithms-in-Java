package UniformCostSearch;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EightQueenRandom {
    private int MAX;

    public EightQueenRandom(int MAX) {
        this.MAX = MAX;
    }

    // Implementing Fisher–Yates shuffle
    public int[] shuffleArray(int[] ar)
    {
        // If running on Java 6 or older, use `new RandomGenerators()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

    public boolean isGoal(int[] arr) {
        for(int i = 0 ; i < MAX ; ++i) {
            for(int j = 0 ; j < MAX ; ++j) {
                if(i == j) continue;
                if(arr[i] == arr[j]) return false;
                if(Math.abs(arr[i] - arr[j]) == Math.abs(i - j)) return false;
            }
        }
        return true;
    }

    public long solve(BufferInput bi, BufferOutput bo) throws IOException {
        int arr[] = new int[MAX];
        for(int i = 1 ; i <= MAX ; ++i) arr[i - 1] = i;
        //arr = shuffleArray(arr);

        long count = 0;
        int prev_i = -1, prev_j = -1;
        boolean modified = true, isGoalState = isGoal(arr);
        while(true) {
            if(modified) {
                //bo.writeString(Arrays.toString(arr));
                //bo.writeString("\n");
                isGoalState = isGoal(arr);
                ++count;
            }
            if(isGoalState) break;
            Random random = ThreadLocalRandom.current();
            int i = random.nextInt(MAX);
            int j = random.nextInt(MAX);
//            do {
//                j = random.nextInt(MAX);
//                if(j != i) break;
//            }while(true);

//            while(j == i) {
//                RandomGenerators rnd = new RandomGenerators();
//                j = (j + rnd.nextInt(MAX)) % MAX;
//            }

            while(prev_i == i && prev_j == j) {
                Random rnd = new Random();
                int add = rnd.nextInt(MAX);
                i = (i + add) % MAX;
                j = (j + add) % MAX;
            }
            if(prev_i != i || prev_j != j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                modified = true;
                prev_i = i;
                prev_j = j;
            } else modified = false;
        }

//        bo.writeString(Arrays.toString(arr));
//        bo.writeString("\n");
//        bo.writeString("Count : ");
//        bo.writeLong(count);
//        bo.writeString("\n");
//        bo.flush();

        return count;
    }

    public static void main(String[] args) throws IOException {
        BufferInput bufferInput = new BufferInput();
        BufferOutput bufferOutput = new BufferOutput();

//        int T = bufferInput.nextInt();
//        while(T-- > 0) {
//            bufferOutput.writeString("Enter no. of queens : ");
//            bufferOutput.flush();
//            int N = bufferInput.nextInt();
//
//            UniformCostSearch.EightQueenRandom eqr = new UniformCostSearch.EightQueenRandom(N);
//            eqr.solve(bufferInput, bufferOutput);
//        }

        int MAX = 10, samples = 1000;
        for(int i = 4 ; i <= MAX ; ++i) {
            double avg = 0.0;
            for(int j = 1 ; j <= samples ; ++j) {
                EightQueenRandom eqr = new EightQueenRandom(i);
                avg += eqr.solve(bufferInput, bufferOutput);
            }
            avg /= samples;
            bufferOutput.writeString("Average for ");
            bufferOutput.writeInt(i);
            bufferOutput.writeString(" queens : ");
            bufferOutput.writeDouble(avg);
            bufferOutput.writeString("\n");
            bufferOutput.flush();
        }

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