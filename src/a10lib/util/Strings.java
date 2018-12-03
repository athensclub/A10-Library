package a10lib.util;

public final class Strings {

    private Strings() {
    }

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
	for(String s : comp) {
	   if(s.equals(str)) {
	       return s;
	   }
	}
	return null;
    }

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

    public static boolean endsWith(StringBuilder str, char c) {
	return str.length() == 0 ? false : (str.charAt(str.length() - 1) == c);
    }

    public static void deleteLastChar(StringBuilder str) {
	str.deleteCharAt(str.length() - 1);
    }

    public static boolean endsWith(StringBuilder str, String end) {
	return str.length() >= end.length() ? str.lastIndexOf(end) == str.length() - end.length() : false;
	/*
	 * if(str.length() < end.length()) { return false; } for(int i =
	 * str.length()-1;i>=str.length()-end.length();i--) { if(str.charAt(i) ==
	 * end.charAt(str.length() - 1 - i)) { return false; } } return true;
	 */
    }

}
