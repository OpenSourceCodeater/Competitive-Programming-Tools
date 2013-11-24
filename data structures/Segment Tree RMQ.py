class SegmentTreeNode:
        def __init__(self, l, r):
            self.left = l
            self.right = r

        def merge(self, left, right):
            if left is None:
                self.value = right.value
            elif right is None:
                self.value = left.value 
            else:
                self.value = min(left.value, right.value)

class SegmentTree:
    def __init__(self, A):
        N = len(A)
        self.length = N
        power = math.ceil(math.log(N, 2))
        total = 2 ** (power + 1)
        self.__tree = [None] * total
        self.__build(1, 0, N - 1, A)

    def __build(self, node, l, r, A):
        if l == r: #leaf node
            self.__tree[node] = SegmentTreeNode(l, r)
            self.__tree[node].value = A[l]
            return
        leftChild = 2 * node
        rightChild = leftChild + 1
        mid = (l + r) // 2
        self.__build(leftChild, l, mid, A)
        self.__build(rightChild, mid + 1, r, A)
        self.__tree[node] = SegmentTreeNode(l, r)
        L = self.__tree[leftChild]
        R = self.__tree[rightChild]
        self.__tree[node].merge(L, R)

    def __query(self, node, l, r, i, j):
        if l >= i and r <= j: 
            return self.__tree[node]
        elif j < l or i > r: 
            return None
        else:
            leftChild = 2 * node
            rightChild = leftChild + 1
            mid = (l + r) // 2
            L = self.__query(leftChild, l, mid, i, j)
            R = self.__query(rightChild, mid + 1, r, i, j)
            temp = SegmentTreeNode(-1, -1) #dummy node
            temp.merge(L, R)
            return temp

    def query(self, i, j):
        return self.__query(1, 0, self.length - 1, i, j)

    def __update(self, node, l, r, i, v):
        if l == i and r == i:
            self.__tree[node].value = v
        elif i < l or i > r:
            return None
        else:
            leftChild = 2 * node
            rightChild = leftChild + 1
            mid = (l + r) // 2
            self.__update(leftChild, l, mid, i, v)
            self.__update(rightChild, mid + 1, r, i, v)
            L = self.__tree[leftChild]
            R = self.__tree[rightChild]
            self.__tree[node].merge(L, R)

    def update(self, i, value):
        self.__update(1, 0, self.length, i, value)
