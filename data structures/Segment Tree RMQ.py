class SegmentTreeNode:
        def __init__(self, l, r):
            (self.left, self.right) = l, r

        def merge(self, leftChild, rightChild):
            if leftChild is None:
                self.value = rightChild.value
            elif rightChild is None:
                self.value = leftChild.value 
            else:
                self.value = min(leftChild.value, rightChild.value)

class SegmentTree:
    def __init__(self, A):
        self.length = len(A)
        totalNodes = 2 * (2 ** math.ceil(math.log(self.length, 2)))
        self.__tree = [None] * totalNodes
        self.__build(1, 0, self.length - 1, A)

    def __build(self, node, l, r, A):
        if l == r: #leaf node
            self.__tree[node] = SegmentTreeNode(l, r)
            self.__tree[node].value = A[l]
            return
        (leftChild, rightChild, mid) = 2 * node, 2 * node + 1, (l + r) // 2
        self.__build(leftChild, l, mid, A)
        self.__build(rightChild, mid+1, r, A)
        self.__tree[node] = SegmentTreeNode(l, r)
        self.__tree[node].merge(self.__tree[leftChild], self.__tree[rightChild])

    def __query(self, node, l, r, i, j):
        if l >= i and r <= j: 
            return self.__tree[node]
        elif j < l or i > r: 
            return None
        else:
            (leftChild, rightChild, mid) = 2 * node, 2 * node + 1, (l + r) // 2
            L = self.__query(leftChild, l, mid, i, j)
            R = self.__query(rightChild, mid+1, r, i, j)
            temp = SegmentTreeNode(-1, -1) #dummy node
            temp.merge(L, R)
            return temp

    def query(self, i, j):
        return self.__query(1, 0, self.length-1, i, j).value

    def __update(self, node, l, r, i, v):
        if l == i and r == i:
            self.__tree[node].value = v
        elif i < l or i > r:
            return None
        else:
            (leftChild, rightChild, mid) = 2 * node, 2 * node + 1, (l + r) // 2
            self.__update(leftChild, l, mid, i, v)
            self.__update(rightChild, mid+1, r, i, v)
            self.__tree[node].merge(self.__tree[leftChild], self.__tree[rightChild])

    def update(self, i, value):
        self.__update(1, 0, self.length, i, value)