package a10lib.util;

import java.util.Collection;
import java.util.Iterator;

import a10lib.compiler.Regex;

public final class Strings {

    private Strings() {
    }

    /**
     * Take a c-like string in form of string as input and return java string as
     * output.
     * <p>
     * For Example, If input string in java string form is <code>"\"hi!\\n\""</code>
     * the return string in java string form is <code>"hi\n"</code>
     * 
     * </p>
     * 
     * @return
     */
    public static String parseString(String string) {
	StringBuilder result = new StringBuilder();
	boolean escape = false;
	for (int i = 0; i < string.length(); i++) {
	    char c = string.charAt(i);
	    if (i == 0 || i == string.length() - 1) {
		if (c != '"') {
		    throw new IllegalArgumentException("String does not begin or end with \"");
		}
	    } else if (escape) {
		result.append(Characters.getEscape(c));
		escape = false;
	    } else {
		if (c == '\\') {
		    escape = true;
		} else if (c == '"') {
		    throw new IllegalArgumentException("Unexpected character: \" at index: " + i);
		} else {
		    result.append(c);
		}
	    }
	}
	return result.toString();
    }

    /**
     * Check if the given string is representing a number.Number is defined by
     * {@link Regex#NUMBER_REGEX}
     * 
     * @param str
     * @return
     */
    public static boolean isNumber(StringBuilder str) {
	return str.toString().matches(Regex.NUMBER_REGEX);
    }

    /**
     * Get whether all the character in the given string is english alphabet or not
     * 
     * @param str
     * @return
     */
    public static boolean isAllAlphabet(StringBuilder str) {
	for (int i = 0; i < str.length(); i++) {
	    if (!Characters.isAlphabet(str.charAt(i))) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Convert a given collection to string,adding new line to each element
     * 
     * @param c
     * @return
     */
    public static String toStringNewLine(Collection<?> c) {
	StringBuilder result = new StringBuilder("[");
	Iterator<?> it = c.iterator();
	while (it.hasNext()) {
	    Object o = it.next();
	    result.append(o.toString());
	    if (it.hasNext()) {
		result.append(",\n");
	    }
	}
	return result.append(']').toString();
    }

    /**
     * Add {@code '\t'} in front every line in the given string
     * 
     * @param str
     *                string to be appended
     * @return the string that added {@code '\t'} in front in every line
     */
    public static String addTabInFrontEveryLine(String str) {
	StringBuilder result = new StringBuilder();
	for (String line : str.split("\n")) {
	    result.append('\t').append(line).append('\n');
	}
	return result.deleteCharAt(result.length() - 1).toString();
    }

    /**
     * Remove all white space in the string builder.White space is detected by using
     * Character.isWHiteSpace(char)
     * 
     * @param sb
     */
    public static void removeBlankSpace(StringBuilder sb) {
	int j = 0;
	for (int i = 0; i < sb.length(); i++) {
	    if (!Character.isWhitespace(sb.charAt(i))) {
		sb.setCharAt(j++, sb.charAt(i));
	    }
	}
	sb.delete(j, sb.length());
    }

    /***
     * Get the string that is equals to the given string
     * 
     * @param str:
     *            String that is used to match with every string
     * @param comp:
     *            Array of strings that will be used to compare to given string
     * @return The string that is equals to the given string
     */
    public static String equalsAny(String str, String... comp) {
	for (String s : comp) {
	    if (s.equals(str)) {
		return s;
	    }
	}
	return null;
    }

    /**
     * Check if given string is representing integer
     * 
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
	if (str.length() < 1) {
	    return false;
	}
	for (int i = 0; i < str.length(); i++) {
	    if (!Character.isDigit(str.charAt(i))) {
		return false;
	    }
	}
	return true;

    }

    /**
     * Check if given string builder is representing integer
     * 
     * @param sb
     * @return
     */
    public static boolean isInteger(StringBuilder sb) {
	if (sb.length() < 1) {
	    return false;
	}
	for (int i = 0; i < sb.length(); i++) {
	    if (!Character.isDigit(sb.charAt(i))) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Check if element character of string and string builder are equal
     * 
     * @param str
     * @param sb
     * @return
     */
    public static boolean equals(String str, StringBuilder sb) {
	if (str == null) {
	    return sb == null;
	}
	if (sb == null) {
	    return str == null;
	}
	if (str.length() != sb.length()) {
	    return false;
	}
	for (int i = 0; i < str.length(); i++) {
	    if (str.charAt(i) != sb.charAt(i)) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Check if the given string builder ends with given character
     * 
     * @param str
     * @param c
     * @return
     */
    public static boolean endsWith(StringBuilder str, char c) {
	return str.length() == 0 ? false : (str.charAt(str.length() - 1) == c);
    }

    /**
     * Remove the last character of the string builder and return it
     * 
     * @param str
     * @return
     */
    public static char removeLastChar(StringBuilder str) {
	char c = str.charAt(str.length() - 1);
	str.deleteCharAt(str.length() - 1);
	return c;
    }

    /**
     * Replace the first occurrence of the old string with the replacement.
     * @param sb
     * @param old
     * @param replacement
     * @return
     */
    public static StringBuilder replaceFirst(StringBuilder sb,String old,String replacement) {
	int i = sb.indexOf(old);
	if(i == -1) {
	    throw new IllegalArgumentException("Unable to find occurence of string: " + old);
	}
	return sb.replace(i, i+old.length(), replacement);
    }

    /**
     * Replace all ocurrence of the old string with the replacement
     * @param sb
     * @param old
     * @param replacement
     * @return
     */
    public static StringBuilder replaceAll(StringBuilder sb,String old,String replacement) {
	int i = -1;
	while((i = sb.indexOf(old)) != -1) {
	    sb.replace(i, i+old.length(), replacement);
	}
	return sb;
    }
    
    /**
     * Check if the given string builder end with the given string
     * 
     * @param str
     * @param end
     * @return
     */
    public static boolean endsWith(StringBuilder str, String end) {
	return str.toString().endsWith(end);
    }

}
