class SegmentTree
    {
        int N;
        Node[] tree;

        class Node
        {
            public int start, end;
            public long sum, add;

            public Node(int l, int r)
            {
                start = l;
                end = r;
            }

			// recomputes the value of a node based on the value of its childs
            public void merge(Node l, Node r)
            {
                if (l == null) sum = r.sum + r.add * (r.end - r.start + 1);
                else if (r == null) sum = l.sum + l.add * (l.end - l.start + 1);
                else sum = l.sum + l.add * (l.end - l.start + 1) + r.sum + r.add * (r.end - r.start + 1);
            }

			// updates the value and flags the sons to be updated later (lazy update)
            public void split(int l, int r)
            {
                sum += add * (end - start + 1);
                if (end != start)
                {
                    Node leftChild = tree[l];
                    leftChild.add += add;
                    Node rightChild = tree[r];
                    rightChild.add += add;
                }
                add = 0;
            }
        }

        public SegmentTree(int[] A)
        {
            N = A.length;
            int len = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) A.length) / Math.log(2.0)) + 1)));
            tree = new Node[len];
            build(1, 0, A.length - 1, A);
        }

		// builds the tree based on merge operations
        private void build(int node, int l, int r, int[] A)
        {
            if (l == r)
            {
                tree[node] = new Node(l, r);
                tree[node].sum = A[l];
                return;
            }
            int nL = node << 1, nR = nL + 1, mid = (l + r) >> 1;
            build(nL, l, mid, A);
            build(nR, mid + 1, r, A);
            tree[node] = new Node(l, r);
            tree[node].merge(tree[nL], tree[nR]);
        }

		// updates the interval [i, j] by adding val to every value in the interval
        public void update(int i, int j, int val)
        {
            update(1, 0, N - 1, i, j, val);
        }

        private void update(int node, int l, int r, int i, int j, int val)
        {
            int nL = node << 1, nR = nL + 1, mid = (l + r) >> 1;
            if (i <= l && j >= r)
            {
                tree[node].add += val;
                tree[node].split(nL, nR);
                return;
            }
            if (j < l || i > r) return;
            update(nL, l, mid, i, j, val);
            update(nR, mid + 1, r, i, j, val);
            tree[node].merge(tree[nL], tree[nR]);
        }

		// returns the sum of the values in interval [i, j]
        public long query(int i, int j)
        {
            return query(1, 0, N - 1, i, j).sum;
        }

        private Node query(int node, int l, int r, int i, int j)
        {
            int nL = node << 1, nR = nL + 1, mid = (l + r) >> 1;
            if (l >= i && r <= j)
            {
                if (tree[node].add > 0) tree[node].split(nL, nR);
                return tree[node];
            }
            if (j < l || i > r) return null;
            if (tree[node].add > 0) tree[node].split(nL, nR);
            Node a = query(nL, l, mid, i, j);
            Node b = query(nR, mid + 1, r, i, j);
            Node temp = new Node(-1, -1);
            temp.merge(a, b);
            return temp;
        }
    }