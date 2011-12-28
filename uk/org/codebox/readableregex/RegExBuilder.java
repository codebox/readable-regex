package uk.org.codebox.readableregex;

/**
 * Used to construct strings of text that can be compiled into Pattern objects representing 
 * regular expressions.
 */
public class RegExBuilder {
    private Token[] tokens;
    
    /**
     * Initialises the instance.
     * 
     * @param tokens a series of Token instances, which will be used to construct the text of
     * the regular expression
     */
    public RegExBuilder(final Token... tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return Utils.appendObjects(tokens);
    }

    /**
     * Convenience method which instantiates a RegExBuilder instance using the specified list of
     * Token objects, and invokes its toString() method, and returns the result.
     * 
     * @param tokens a series of Token instances, which will be used to construct the text of
     * the regular expression
     * 
     * @return the text of the regular expression
     */
    public static String build(final Token... tokens) {
        return new RegExBuilder(tokens).toString();
    }
}
