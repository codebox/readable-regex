package uk.org.codebox.readableregex;

/**
 * Class representing part of a 'character token' in a regular expression. A character token
 * matches a single character, and is written using a pair of square brackets enclosing values
 * that determine which characters will be matched. 
 * 
 * Character tokens are composed of one of more parts, with each part representing either a list, 
 * or a range, of characters.
 */
public abstract class CharacterTokenPart{
    protected final boolean isFirstPartInToken, isLastPartInToken;

    protected CharacterTokenPart(final boolean isFirstPartInToken, final boolean isLastPartInToken) {
        this.isFirstPartInToken = isFirstPartInToken;
        this.isLastPartInToken  = isLastPartInToken;
    }
    
    protected abstract CharacterTokenPart copyAndSetFirstPartInToken();
    protected abstract CharacterTokenPart copyAndSetLastPartInToken();
    
    private static class SimpleCharacterTokenPart extends CharacterTokenPart{
        private final Character[] characters;
        protected SimpleCharacterTokenPart(final Character[] characters, final boolean isFirstPartInToken, 
                final boolean isLastPartInToken) {
            super(isFirstPartInToken, isLastPartInToken);
            this.characters = characters;
        }

        @Override
        public String toString() {
            final int charactersCount = characters.length;
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < charactersCount; i++) {
                final boolean isFirstCharInPart = (i==0);
                final boolean isLastCharInPart  = (i==charactersCount-1);
                
                final boolean isFirstCharInToken = isFirstCharInPart && isFirstPartInToken; 
                final boolean isLastCharInToken  = isLastCharInPart && isLastPartInToken;
                
                final String escapedChar = escapeCharacter(characters[i], isFirstCharInToken, isLastCharInToken);
                
                sb.append(escapedChar);
            }
            return sb.toString();
        }
        
        @Override
        protected CharacterTokenPart copyAndSetFirstPartInToken() {
            return new SimpleCharacterTokenPart(characters, true, isLastPartInToken);
        }
        @Override
        protected CharacterTokenPart copyAndSetLastPartInToken() {
            return new SimpleCharacterTokenPart(characters, isFirstPartInToken, true);
        }
    }
    
    /**
     * Constructs a CharacterTokenPart instance that represents a list of characters.
     * 
     * @param characters the characters that will be matched by this part of the token
     * 
     * @return a CharacterTokenPart instance that represents a list of characters
     */
    public static CharacterTokenPart characters(final Character... characters){
        return new SimpleCharacterTokenPart(characters, false, false);
    }

    private static class RangeCharacterTokenPart extends CharacterTokenPart{
        private char from, to;
        protected RangeCharacterTokenPart(final char from, final char to, final boolean isFirstPartInToken, 
                final boolean isLastPartInToken) {
            super(isFirstPartInToken, isLastPartInToken);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return escapeCharacter(from, isFirstPartInToken, false) + "-" + escapeCharacter(to, false, isLastPartInToken);
        }
        
        @Override
        protected CharacterTokenPart copyAndSetFirstPartInToken() {
            return new RangeCharacterTokenPart(from, to, true, isLastPartInToken);
        }
        @Override
        protected CharacterTokenPart copyAndSetLastPartInToken() {
            return new RangeCharacterTokenPart(from, to, isFirstPartInToken, true);
        }
    }
    
    /**
     * Constructs a CharacterTokenPart instance that represents a range of characters.
     * 
     * @param from first character in the range of characters that will be matched 
     * @param to last character in the range of characters that will be matched
     * 
     * @return a CharacterTokenPart instance that represents a range of characters
     */
    public static CharacterTokenPart range(final char from, final char to) {
        return new RangeCharacterTokenPart(from, to, false, false);
    }
    
    private static String escapeCharacter(final char c, final boolean isFirstCharInToken, final boolean isLastCharInToken){
        final char[] specialCharacters;
        
        if (isFirstCharInToken){
            specialCharacters = new char[]{'\\', ']', '^'};

        } else {
            specialCharacters = new char[]{'\\', ']', '-'};
        }
        
        return Utils.escapeSpecial(c, specialCharacters);
    }

}