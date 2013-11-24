class Output
{
    final int SIZE = 4096; // 4096; 8192
    Writer out;
    char[] cb = new char[SIZE];
    int nChars = SIZE, nextChar = 0;
    char lineSeparator = '\n';

    public Output(OutputStream stream) { out = new OutputStreamWriter(stream); }

    void flushBuffer() throws IOException
    {
        if (nextChar == 0) return;
        out.write(cb, 0, nextChar);
        nextChar = 0;
    }

    void write(int c) throws IOException
    {
        if (nextChar >= nChars) flushBuffer();
        cb[nextChar++] = (char) c;
    }

    void write(String s, int off, int len) throws IOException
    {
        int b = off, t = off + len;
        while (b < t)
        {
            int a = nChars - nextChar, a1 = t - b;
            int d = a < a1 ? a : a1;
            s.getChars(b, b + d, cb, nextChar);
            b += d;
            nextChar += d;
            if (nextChar >= nChars) flushBuffer();
        }
    }

    void write(String s) throws IOException { write(s, 0, s.length()); }

    public void print(Object obj) throws IOException { write(String.valueOf(obj)); }

    public void println(Object obj) throws IOException
    {
        write(String.valueOf(obj));
        write(lineSeparator);
    }

    public void printf(String format, Object... obj) throws IOException { write(String.format(format, obj)); }

    public void printElements(Object... obj) throws IOException
    {
        for (int i = 0; i < obj.length; i++)
        {
            if (i > 0) print(" ");
            print(obj[i]);
        }
        print(lineSeparator);
    }

    public void close() throws IOException
    {
        flushBuffer();
        out.close();
    }
}