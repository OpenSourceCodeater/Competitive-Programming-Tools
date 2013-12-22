class FenwickTree:
    def __init__(self, n = 0, values = None):
        N = n if(values is None) else len(values)
        self.size = N;
        self.__BIT = [0] * (N + 1)
        if (values is not None):
            for (i, n) in enumerate(A):
                self.update(i+1, n)

    def __lowbit(self, n):
        return n & -n;

    def __sum(self, i):
        value = 0
        while(i > 0):
            value += self.__BIT[i];
            i -= self.__lowbit(i);
        return value;

    def sum(self, i, j = -1):
        if(i > 1 and i < j):
            return self.__sum(j) - self.__sum(i-1)
        else:
            return self.__sum(i)

    def update(self, i, value):
        while(i <= self.size):
            self.__BIT[i] += value
            i += self.__lowbit(i)