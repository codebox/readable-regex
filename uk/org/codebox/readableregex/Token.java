package uk.org.codebox.readableregex;

import java.util.regex.Pattern;

/**
 * Class representing part of a regular expression.
 */
public class Token {
    private Quantifier quantifier;
    private boolean requiresBrackets;
    private String tokenString;
    
    protected Token(String tokenString, boolean requiresBrackets, Quantifier quantifier) {
        this.tokenString      = tokenString;
        this.requiresBrackets = requiresBrackets;
        this.quantifier       = quantifier;
    }
    
    protected Token(Token token, Quantifier quantifier) {
        this.tokenString      = token.tokenString;
        this.requiresBrackets = token.requiresBrackets;
        this.quantifier       = quantifier;        
    }

    private static char[] SPECIAL_CHARS = new char[]{'\\', '[', '^', '$', '.', '|', '?', '*', '+', '(', ')', '{', '}'};
    
    
    @Override
    public final String toString(){
        if (quantifier == null){
            return tokenString;
        } else {
            if (requiresBrackets){
                return "(" + tokenString + ")" + quantifier.toString();
            } else {
                return tokenString + quantifier.toString();    
            }
        }
    }
    
    /**
     * Encloses the specified series of tokens in a pair of brackets, creating a capturing group.
     * 
     * @param tokens the tokens to be enclosed
     * 
     * @return a new Token instance
     */
    public static Token groupOf(final Token... tokens) {
        return new Token(
            "(" + RegExBuilder.build(tokens) + ")", false, null
        );
    }
    
    /**
     * Encloses the specified series of tokens in a pair of brackets, creating a non-capturing group.
     * 
     * @param tokens the tokens to be enclosed
     * 
     * @return a new Token instance
     */
    public static Token nonCapturingGroup(final Token... tokens) {
        return new Token(
            "(?:" + RegExBuilder.build(tokens) + ")", false, null
        );
    }

    /**
     * Encloses the specified series of tokens in a pair of brackets, creating an independent non-capturing group.
     * 
     * @param tokens the tokens to be enclosed
     * 
     * @return a new Token instance
     */
    public static Token independentNonCapturingGroup(final Token... tokens) {
        return new Token(
            "(?>" + RegExBuilder.build(tokens) + ")", false, null
        );
    }
    
    /**
     * Creates a token that specifies a zero-width positive lookahead.
     * 
     * @param tokens the tokens to be used to define the lookahead
     * 
     * @return a new Token instance
     */
    public static Token positiveLookAhead(final Token... tokens) {
        return new Token(
            "(?=" + RegExBuilder.build(tokens) + ")", false, null
        );
    }

    /**
     * Creates a token that specifies a zero-width negative lookahead.
     * 
     * @param tokens the tokens to be used to define the lookahead
     * 
     * @return a new Token instance
     */
    public static Token negativeLookAhead(final Token... tokens) {
        return new Token(
            "(?!" + RegExBuilder.build(tokens) + ")", false, null
        );
    }
    
    /**
     * Creates a token that specifies a zero-width positive look-behind.
     * 
     * @param tokens the tokens to be used to define the look-behind
     * 
     * @return a new Token instance
     */
    public static Token positiveLookBehind(final Token... tokens) {
        return new Token(
            "(?<=" + RegExBuilder.build(tokens) + ")", false, null
        );
    }

    /**
     * Creates a token that specifies a zero-width negative look-behind.
     * 
     * @param tokens the tokens to be used to define the look-behind
     * 
     * @return a new Token instance
     */
    public static Token negativeLookBehind(final Token... tokens) {
        return new Token(
            "(?<!" + RegExBuilder.build(tokens) + ")", false, null
        );
    }

    /**
     * Creates a reference to a previously defined capturing group.
     * 
     * @param groupNumber the one-based index identifying the group
     * 
     * @return a new Token instance
     */
    public static Token group(final int groupNumber) {
        return new Token("\\" + groupNumber, false, null);
    }
    
    /**
     * Creates a token that matches any sequence of characters, of any length.
     * 
     * @return a new Token instance
     */
    public static Token anything(){ 
        return new Token(".*", false, null);
    }
    
    private static final Pattern OCTAL_PATTERN = Pattern.compile("([0-7]|[0-7][0-7]|[0-2][0-7][0-7])");
    
    /**
     * Creates a token matching a single character represented by the specified octal value.
     * 
     * @param octalNumber an octal number up to 3 characters in length with a maximum value of 277
     * 
     * @return a new Token instance
     */
    public static Token octalCharacter(final String octalNumber){
        if (!OCTAL_PATTERN.matcher(octalNumber).matches()){
            throw new IllegalArgumentException("Bad octal value");
        }
        return new Token("\\0" + octalNumber, false, null);
    }
    
    private static final Pattern HEX_PATTERN = Pattern.compile("[a-fA-F0-9]{2}([a-fA-F0-9]{2})?");
    /**
     * Creates a token matching a single character represented by the specified hex value.
     * 
     * @param hexNumber a hex number either 2 or 4 characters in length
     * 
     * @return a new Token instance
     */
    public static Token hexCharacter(final String hexNumber){
        if (!HEX_PATTERN.matcher(hexNumber).matches()){
            throw new IllegalArgumentException("Bad hex value");
        }
        return new Token("\\x" + hexNumber.toUpperCase(), false, null);
    }

    private static final Pattern UNICODE_PATTERN = Pattern.compile("[a-fA-F0-9]{4}");
    /**
     * Creates a token matching a single Unicode character represented by the specified hex value.
     * 
     * @param unicodeNumber a hex number of 4 characters in length
     * 
     * @return a new Token instance
     */
    public static Token unicodeCharacter(final String unicodeNumber){
        if (!UNICODE_PATTERN.matcher(unicodeNumber).matches()){
            throw new IllegalArgumentException("Bad unicode value");
        }
        return new Token("\\u" + unicodeNumber.toUpperCase(), false, null);
    }

    /**
     * Creates a token that matches a single occurrence of the Tab character.
     * 
     * @return a new Token instance
     */
    public static Token tab(){ 
        return new Token("\\t", false, null);
    }

    /**
     * Creates a token that matches a single occurrence of the New Line character.
     * 
     * @return a new Token instance
     */
    public static Token newline(){ 
        return new Token("\\n", false, null);
    }

    /**
     * Creates a token that matches a single occurrence of the Carriage Return character.
     * 
     * @return a new Token instance
     */
    public static Token carriageReturn(){ 
        return new Token("\\r", false, null);
    }

    /**
     * Creates a token that matches a single occurrence of the Form Feed character.
     * 
     * @return a new Token instance
     */
    public static Token formFeed(){ 
        return new Token("\\f", false, null);
    }

    /**
     * Creates a token that matches a single occurrence of the Alert character.
     * 
     * @return a new Token instance
     */
    public static Token alertCharacter(){ 
        return new Token("\\a", false, null);
    }
    
    /**
     * Creates a token that matches a single occurrence of the Escape character.
     * 
     * @return a new Token instance
     */
    public static Token escapeCharacter(){ 
        return new Token("\\e", false, null);
    }

    /**
     * Creates a token that matches a single occurrence of the specified control character.
     * 
     * @param character letter for which the control character will be created
     * 
     * @return a new Token instance
     */
    public static Token controlCharacter(char character){
        if (! Character.isLetter(character)){
            throw new IllegalArgumentException("Only letters are permitted for control characters");
        }
        final char upperCaseLetter = Character.toUpperCase(character);
        
        return new Token("\\c" + upperCaseLetter, false, null);
    }

    /**
     * Creates a token that matches a single digit character.
     * 
     * @return a new Token instance
     */
    public static Token anyDigit(){ 
        return anyOneOf(CharacterTokenPart.range('0', '9'));
    }
    
    /**
     * Creates a token that matches a single letter character of either upper or lower case.
     * 
     * @return a new Token instance
     */
    public static Token anyLetter(){ 
        return anyOneOf(CharacterTokenPart.range('a', 'z'), CharacterTokenPart.range('A', 'Z'));
    }

    /**
     * Creates a token that matches a literal piece of text, special characters will be escaped automatically.
     * 
     * @param text literal text to be matched
     * 
     * @return a new Token instance
     */
    public static Token text(final String text) {
        return new Token(
            Utils.escapeSpecial(text, SPECIAL_CHARS), text.length() > 1, null
        );
    }
    
    /**
     * Creates a token that matches any single character that does not correspond to any of 
     * the specified CharacterTokenPart instances.
     * 
     * @param characterTokens an array of CharacterTokenPart objects which must not be matched
     * 
     * @return a new Token instance
     */
    public static Token anyCharacterExcept(final CharacterTokenPart... characterTokens){
        final String tokenString;
        setFirstAndLastParts(characterTokens);
        String txt = Utils.appendObjects(characterTokens);
        tokenString = "[^" + txt + "]";
        
        return new Token(tokenString, false, null);
    }
    
    /**
     * Creates a token that matches any single character that corresponds to any of the 
     * specified CharacterTokenPart instances.
     * 
     * @param characterTokens an array of CharacterTokenPart objects, one of which must be matched
     * 
     * @return a new Token instance
     */
    public static Token anyOneOf(final CharacterTokenPart... characterTokens){
        final String tokenString;
        setFirstAndLastParts(characterTokens);
        String txt = Utils.appendObjects(characterTokens);
        tokenString = "[" + txt + "]";
        
        return new Token(tokenString, false, null);
    }
    
    private static void setFirstAndLastParts(final CharacterTokenPart... characterTokens){
        final int count = characterTokens.length;
        for(int i=0; i<count; i++){
            if (i==0){
                characterTokens[i] = characterTokens[i].copyAndSetFirstPartInToken();
            }
            if (i==count-1){
                characterTokens[i]= characterTokens[i].copyAndSetLastPartInToken();
            }
        }
    }
    
    
    
    /**
     * Creates a token that matches exactly one of the specified tokens.
     * 
     * @param tokens an array of Tokens, one of which must be matched
     * 
     * @return a new Token instance
     */
    public static Token or(final Token... tokens) {
        final StringBuilder sb = new StringBuilder();
        
        for(Token token : tokens){
            if (sb.length() > 0){
                sb.append("|");
            }
            sb.append(token.toString());
        }
        
        final boolean requiresBrackets;
        if (tokens.length > 1){
            requiresBrackets = true;
        } else {
            requiresBrackets = tokens[0].requiresBrackets;
        }
        
        return new Token(sb.toString(), requiresBrackets, null);
    }
    
    /**
     * An alias for the 'or' method.
     * 
     * @param tokens an array of Tokens, one of which must be matched
     * 
     * @return a new Token instance
     */
    public static Token oneOf(final Token... tokens) {
        return or(tokens);
    }
}
