package uk.org.codebox.readableregex;

/**
 * Class representing a 'quantifier' which specifies how many times a token must appear in order
 * to constitute a match.
 */
public class Quantifier {
    private final String baseText, suffix;
    
    private Quantifier(String baseText, final String suffix){
        this.baseText = baseText;
        this.suffix   = suffix;
    }
    
    /**
     * Applies the quantifier represented by this instance to the specified sequence of tokens.
     *  
     * @param tokens the series of tokens to be associated with the current instance
     * 
     * @return a new Token instance which has been associated with the current instance
     */
    public Token of(final Token... tokens){
        if (tokens.length == 1){
         // A single token may or may not require brackets
            return new Token(tokens[0], this);
            
        } else {
         // Multiple tokens will always require brackets
            return of(new Token(RegExBuilder.build(tokens), true, null));
        }
    }
    
    /**
     * Convenience method which creates a text Token from the specified String, and then associates
     * it with the current instance.
     * 
     * @param text the text to be converted into a Token
     * @return a text Token which has been associated with the current instance
     */
    public Token of(final String text){
        return of(Token.text(text));
    }

    /**
     * Creates a new greedy Quantifier based on the current instance.
     * 
     * @return a new greedy Quantifier
     */
    public Quantifier greedy(){
        return new Quantifier(baseText, "");
    }

    /**
     * Creates a new lazy Quantifier based on the current instance.
     * 
     * @return a new lazy Quantifier
     */
    public Quantifier lazy(){
        return new Quantifier(baseText, "?");
    }

    /**
     * Creates a new possessive Quantifier based on the current instance.
     * 
     * @return a new possessive Quantifier
     */
    public Quantifier possessive(){
        return new Quantifier(baseText, "+");
    }
    
    @Override
    public String toString() {
        return appendSuffix(baseText, suffix);
    }
    
    private static String appendSuffix(String txt, String suffix){
        return txt + (suffix == null ? "" : suffix);
    }
    
    /**
     * Creates a new Quantifier for matching one of more of the associated Tokens.
     * 
     * @return Quantifier for matching one of more of the associated Tokens
     */
    public static Quantifier oneOrMore(){ 
        return new Quantifier("+", "");
    }

    /**
     * Creates a new Quantifier for matching zero or one of the associated Tokens.
     * 
     * @return Quantifier for matching zero or one of the associated Tokens
     */
    public static Quantifier zeroOrOne(){ 
        return new Quantifier("?", "");
    }

    /**
     * Creates a new Quantifier for matching zero or more of the associated Tokens.
     * 
     * @return Quantifier for matching zero or more of the associated Tokens
     */
    public static Quantifier zeroOrMore(){
        return new Quantifier("*", "");
    }
    
    /**
     * Creates a new Quantifier for matching any number of Tokens that falls within the specified range.
     * 
     * @param from the minimum number of tokens that will be matched
     * @param to the maximum number of tokens that will be matched
     * 
     * @return Quantifier for matching any number of Tokens that falls within the specified range
     */
    public static Quantifier between(final int from, final int to){
        if (from < 0 || to < 0) {
            throw new IllegalArgumentException("A negative value was passed to the between() method, values must be >= 0");
        }
        if (from > to){
            throw new IllegalArgumentException(
                    String.format("The 'from' argument value of %d was larger than the 'to' argument value of %d"));
        }
        return new Quantifier("{" + from + "," + to + "}", "");
    }

    /**
     * Creates a new Quantifier for matching the specified number of Tokens.
     * 
     * @param count the number of occurrences of the token to be matched
     * 
     * @return Quantifier for matching the specified number of Tokens
     */
    public static Quantifier exactly(final int count){
        if (count < 0) {
            throw new IllegalArgumentException("A negative value was passed to the exactly() method, value must be >= 0");
        }
        return new Quantifier("{" + count + "}", "");
    }
    
    /**
     * Creates a new Quantifier for matching at least the specified number of Tokens.
     * 
     * @param count the minimum number of occurrences of the token to be matched
     * 
     * @return Quantifier for matching at least the specified number of Tokens
     */
    public static Quantifier atLeast(final int count){
        if (count < 0) {
            throw new IllegalArgumentException("A negative value was passed to the atLeast() method, value must be >= 0");
        }
        return new Quantifier("{" + count + ",}", "");
    }

}
