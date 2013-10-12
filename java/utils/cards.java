class CardUtils
{
    static final char[] suites = {'C', 'D', 'H', 'S'};
    static final int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    public static Card[] getNewDeck()
    {
        Card[] cards = new Card[53];
        int i = 0;
        for (char s : suites)
            for (int v : values)
                cards[i++] = new Card(v, s);
        return cards;
    }
}

class Card
{
    public int value, suite;

    public Card(int v, int s)
    {
        value = v;
        suite = s;
    }

    public String toString()
    {
        return valueString() + " of " + suiteString();
    }

    private String suiteString()
    {
        if (suite == 'C') return "Clubs";
        if (suite == 'D') return "Diamonds";
        if (suite == 'H') return "Hearts";
        return "Spades";
    }

    private String valueString()
    {
        if (value == 1) return "Ace";
        if (value <= 10) return String.valueOf(value);
        if (value == 11) return "Jack";
        if (value == 12) return "Queen";
        if (value == 13) return "King";
        return "Ace";
    }
}
