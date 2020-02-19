package TravellingSalesman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TSP_A_Star {
    static void printGraph(BufferOutput bo, Graph graph) throws IOException{
        ArrayList<Vertex> vertices = graph.getVertices();
        int N = vertices.size();
        bo.writeString("Vertices : " + vertices);
        bo.flush();
        ArrayList<Edge<Vertex, Vertex>> edges = graph.getEdges();
        Collections.sort(edges);
        bo.writeString("\nEdges : \n");
        bo.flush();
        for(int i = 0 ; i < edges.size() ; ++i) {
            Edge edge = edges.get(i);
            bo.writeString(edge.toString());
            bo.writeString("\n");
            bo.flush();
        }
        bo.writeString("\n");
        bo.flush();

        for(int i = 0 ; i < N ; ++i) {
            Vertex curr = vertices.get(i);
            bo.writeString("No of neighbours of " + curr.toString() + " are : ");
            bo.writeInt(graph.getEdges(curr).size());
            bo.writeString("\n");
            bo.flush();
        }
    }

    static void solve(BufferInput bi, BufferOutput bo) throws IOException {
        bo.writeString("Enter no.of cities : ");
        bo.flush();
        int N = bi.nextInt();

        bo.writeString("Enter minimum edge weight : ");
        bo.flush();
        double edgeMin = bi.nextDouble();

        bo.writeString("Enter maximum edge weight : ");
        bo.flush();
        double edgeMax = bi.nextDouble();

        Graph graph = Graph.randomGraph(N, edgeMin, edgeMax);
        printGraph(bo, graph);

        Graph mst = SpanningTree.getMST(graph);
        printGraph(bo, graph);
    }

    public static void main(String[] args) throws IOException {
        BufferInput bufferInput = new BufferInput();
        BufferOutput bufferOutput = new BufferOutput();

        int T = bufferInput.nextInt();
        while(T-- > 0)
        TSP_A_Star.solve(bufferInput, bufferOutput);

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