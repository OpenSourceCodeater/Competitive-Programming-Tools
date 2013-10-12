class Input
{
    final int SIZE = 8192; //4096; 8192; 65536;
    InputStream in;
    byte[] buf = new byte[SIZE];
    int last, current, total;

    public Input(InputStream stream) throws IOException
    {
        in = stream; last = read();
    }

    int read() throws IOException
    {
        if (total == -1) return -1;
        if (current >= total)
        {
            current = 0; total = in.read(buf);
            if (total <= 0) return -1;
        }
        return buf[current++];
    }

    void advance() throws IOException
    {
        while (true)
        {
            if (last == -1) return;
            if (!isValidChar(last)) last = read();
            else break;
        }
    }

    boolean isValidChar(int c) { return c > 32 && c < 127; }

    public boolean endOfFile() throws IOException { advance(); return last == -1; }

    public String nextString() throws IOException
    {
        advance();
        if (last == -1) throw new EOFException();
        StringBuilder s = new StringBuilder();
        while (true)
        {
            s.appendCodePoint(last); last = read();
            if (!isValidChar(last)) break;
        }
        return s.toString();
    }

    public String nextLine() throws IOException
    {
        if (last == -1) throw new EOFException();
        StringBuilder s = new StringBuilder();
        while (true)
        {
            s.appendCodePoint(last); last = read();
            if (last == '\n' || last == -1) break;
        }
        return s.toString();
    }

    public String nextLine(boolean ignoreIfEmpty) throws IOException
    {
        if (!ignoreIfEmpty) return nextLine();
        String s = nextLine();
        while (s.trim().length() == 0) s = nextLine();
        return s;
    }

    public int nextInt() throws IOException
    {
        advance();
        if (last == -1) throw new EOFException();
        int n = 0, s = 1;
        if (last == '-')
        {
            s = -1; last = read();
            if (last == -1) throw new EOFException();
        }
        while (true)
        {
            n = n * 10 + last - '0'; last = read();
            if (!isValidChar(last)) break;
        }
        return n * s;
    }

    public long nextLong() throws IOException
    {
        advance();
        if (last == -1) throw new EOFException();
        int s = 1;
        if (last == '-')
        {
            s = -1; last = read();
            if (last == -1) throw new EOFException();
        }
        long n = 0;
        while (true)
        {
            n = n * 10 + last - '0'; last = read();
            if (!isValidChar(last)) break;
        }
        return n * s;
    }

    public BigInteger nextBigInt() throws IOException { return new BigInteger(nextString()); }

    public char nextChar() throws IOException
    {
        advance();
        char c = (char) last;
        last = 0;
        return c;
    }

    public double nextDouble() throws IOException
    {
        advance();
        if (last == -1) throw new EOFException();
        int s = 1;
        if (last == '-')
        {
            s = -1; last = read();
            if (last == -1) throw new EOFException();
        }
        double n = 0;
        while (true)
        {
            n = n * 10 + last - '0'; last = read();
            if (!isValidChar(last) || last == '.') break;
        }
        if (last == '.')
        {
            last = read();
            if (last == -1) throw new EOFException();
            double m = 1;
            while (true)
            {
                m = m / 10;
                n = n + (last - '0') * m; last = read();
                if (!isValidChar(last)) break;
            }
        }
        return n * s;
    }

    public BigDecimal nextBigDecimal() throws IOException { return new BigDecimal(nextString()); }

    public int[] nextIntArray(int len) throws IOException
    {
        int[] A = new int[len];
        for (int i = 0; i < len; i++) A[i] = nextInt();
        return A;
    }

    public long[] nextLongArray(int len) throws IOException
    {
        long[] A = new long[len];
        for (int i = 0; i < len; i++) A[i] = nextLong();
        return A;
    }

    public int[][] nextIntTable(int rows, int cols) throws IOException
    {
        int[][] T = new int[rows][];
        for (int i = 0; i < rows; i++) T[i] = nextIntArray(cols);
        return T;
    }
}