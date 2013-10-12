class Trie 
{
    class Node
    {
        char content;
        boolean isTerminal;
        Node parent;
        Node[] child = new Node[26];

        public Node(char c, Node pi)
        {
            parent = pi; content = c;
        }

        public Node getChild(char c) { return child[c-'a']; }

        public Node addChild(Node node) { child[node.content - 'a'] = node; }
    }

    Node root = new Node('\0', null);

    public void insert(String word)
    {
        Node current = root;
        for(int i=0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            Node sub = current.getChild(c);
            if (sub != null) current = sub;
            else
            {
                current.addChild(new Node(c, current));
                current = current.getChild(c);
            }
            if (i == word.length()-1) current.isTerminal = true;
        }
    }

    public boolean search(String word)
    {
        Node current = root;
        for(int i=0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            Node sub = current.getChild(c);
            if (sub == null) return false;
        }
        return current.isTerminal;
    }
}