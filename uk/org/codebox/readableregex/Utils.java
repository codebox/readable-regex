package uk.org.codebox.readableregex;

/**
 * Contains utility methods for the RegExBuilder library.
 */
public class Utils {
    /**
     * Escapes the characters contained in the String argument.
     * 
     * @param text the text to be checked for characters that require escaping
     * @param specialCharacters the list of characters that require escaping
     * 
     * @return the resulting String
     */
    public static String escapeSpecial(final String text, final char[] specialCharacters){
        String tempText = text;
        for(char specialCharacter : specialCharacters){
            tempText = tempText.replace("" + specialCharacter, "\\" + specialCharacter);
        }
        return tempText;
    }
    
    /**
     * Escapes the character argument if it is one of the specified 'special characters'.
     *  
     * @param c the character to be escaped
     * @param specialCharacters the list of characters that require escaping
     * 
     * @return a String containing the character value, escaped if required
     */
    public static String escapeSpecial(char c, char[] specialCharacters){
        return escapeSpecial("" + c, specialCharacters);
    }

    /**
     * Constructs a String by concatenating together the values returned by the toString() methods
     * of each of the specified objects.
     * 
     * @param <T> type parameter to keep the compiler happy
     * @param objects array of objects used to construct the string
     * 
     * @return the resulting String
     */
    public static <T> String appendObjects(T... objects){
        final StringBuilder sb = new StringBuilder();

        for(T object : objects){
            sb.append(object.toString());
        }
        return sb.toString();
    }

}
