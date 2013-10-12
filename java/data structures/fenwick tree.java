class FenwickTree
{
    int[] tree;

    public FenwickTree(int n)
    {
        tree = new int[n+1];
    }

    public FenwickTree(int[] A)
    {
        tree = new int[A.length+1];
        for (int i = 1; i <= A.length; ++i) update(i, A[i - 1]);
    }

    public int sum(int i)
    {
        int value = 0;
        for (; i > 0; i -= (i & -i)) value += tree[i];
        return value;
    }

    public int sum(int i, int j)
    {
        return i > 1 ? sum(j) - sum(i - 1) : sum(j);
    }

    public void update(int i, int value)
    {
        for (; i < tree.length; i += (i & -i)) tree[i] += value;
    }

    public void update(int i, int j, int value)
    {
        update(i, value); update(j + 1, -value);
    }
}