/**
 * Copyright (c) 2008 Devin Weaver
 * Licensed under the Educational Community License version 1.0
 * See the file COPYING with this distrobution for details.
 */
package com.tritarget.client;

/**
 * Encapsulates all the logic used to encode and decode using the
 * Double Playfair cipher.
 */
public class DoublePlayfairCipher {
    private char[][] matrix1;
    private char[][] matrix2;
    /** The characters allowed in a string of text used in the Playfair cipher. */
    public static final String VALID_CHARS = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // Skip J

    /**
     * Construct a new object with double keys. The order the keys are used is
     * important to remember.
     * @param key1 the first key.
     * @param key2 the second key.
     */
    // DoublePlayfairCipher(String, String) {{{
    public DoublePlayfairCipher(String key1, String key2) {
        this.matrix1 = keyToMatrix(key1);
        this.matrix2 = keyToMatrix(key2);
    }
    // }}}

    /**
     * Encode any string with the Playfair cipher. Text normalization is done
     * automatically.
     * @param text the text to encode.
     * @return the encoded text as a string.
     */
    // encode(String) {{{
    public String encode(String text) {
        text = normalize(text);
        String output = "";
        char[] chunk = new char[2];
        int pos = 0;
        while (pos < text.length())
        {
            if (pos > 0)
            {
                output += " ";
            }
            chunk[0] = text.charAt(pos);
            chunk[1] = text.charAt(pos+1);
            // Double Playfair does the encoding twice.
            chunk = playfairEncode(chunk);
            chunk = playfairEncode(chunk);
            output += "" + chunk[0] + chunk[1];
            pos += 2;
        }
        return output;
    }
    // }}}

    /**
     * Decode any string with the Playfair cipher. Text normalization is done
     * automatically.
     * @param text the text to decode.
     * @return the decoded text as a string.
     * @throws RuntimeException if the size of the text is incorrect.
     */
    // decode(String) {{{
    public String decode(String text) {
        text = normalizeString(text);
        if (text.length() % 2 != 0)
        {
            throw new RuntimeException("Input text is one character too short.");
        }
        String output = "";
        char[] chunk = new char[2];
        int pos = 0;
        while (pos < text.length())
        {
            if (pos > 0)
            {
                output += " ";
            }
            chunk[0] = text.charAt(pos);
            chunk[1] = text.charAt(pos+1);
            // Double Playfair does the encoding twice.
            chunk = playfairDecode(chunk);
            chunk = playfairDecode(chunk);
            output += "" + chunk[0] + chunk[1];
            pos += 2;
        }
        return output;
    }
    // }}}

    /**
     * Encode a chunk with the double playfair cipher.
     * @param chunk an array of two chars to encode.
     * @return the encoded chunk as an array of two chars.
     */
    // playfairEncode(char[]) {{{
    private char[] playfairEncode(char[] chunk) {
        int row, col;
        int[] left_loc = new int[2];
        int[] right_loc = new int[2];
        char[] ret_chunk = new char[2];
        for (row = 0; row < 5; row++)
        {
            for (col = 0; col < 5; col++)
            {
                if (chunk[0] == this.matrix1[row][col])
                {
                    left_loc[0] = row;
                    left_loc[1] = col;
                    break;
                }
            }
        }
        for (row = 0; row < 5; row++)
        {
            for (col = 0; col < 5; col++)
            {
                if (chunk[1] == this.matrix2[row][col])
                {
                    right_loc[0] = row;
                    right_loc[1] = col;
                    break;
                }
            }
        }
        // the two fall on the same row. Apply exception rule.
        if (left_loc[0] == right_loc[0])
        {
            if (right_loc[1] == 0)
            {
                // column is on the left wrap around to the other side of the matrix.
               ret_chunk[0] = this.matrix2[right_loc[0]][4];
            }
            else
            {
                ret_chunk[0] = this.matrix2[right_loc[0]][right_loc[1]-1];
            }
            if (left_loc[1] == 0)
            {
                // column is on the left wrap around to the other side of the matrix.
               ret_chunk[1] = this.matrix1[left_loc[0]][4];
            }
            else
            {
                ret_chunk[1] = this.matrix1[left_loc[0]][left_loc[1]-1];
            }
        }
        else
        {
            ret_chunk[0] = this.matrix2[left_loc[0]][right_loc[1]];
            ret_chunk[1] = this.matrix1[right_loc[0]][left_loc[1]];
        }
        return ret_chunk;
    }
    // }}}

    /**
     * Decode a chunk with the double playfair cipher.
     * @param chunk an array of two chars to decode.
     * @return the decoded chunk as an array of two chars.
     */
    // playfairDecode(char[]) {{{
    private char[] playfairDecode(char[] chunk) {
        int row, col;
        int[] left_loc = new int[2];
        int[] right_loc = new int[2];
        char[] ret_chunk = new char[2];
        for (row = 0; row < 5; row++)
        {
            for (col = 0; col < 5; col++)
            {
                if (chunk[0] == this.matrix2[row][col])
                {
                    left_loc[0] = row;
                    left_loc[1] = col;
                    break;
                }
            }
        }
        for (row = 0; row < 5; row++)
        {
            for (col = 0; col < 5; col++)
            {
                if (chunk[1] == this.matrix1[row][col])
                {
                    right_loc[0] = row;
                    right_loc[1] = col;
                    break;
                }
            }
        }
        // the two fall on the same row. Apply exception rule.
        if (left_loc[0] == right_loc[0])
        {
            if (right_loc[1] == 4)
            {
                // column is on the right wrap around to the other side of the matrix.
               ret_chunk[0] = this.matrix1[right_loc[0]][0];
            }
            else
            {
                ret_chunk[0] = this.matrix1[right_loc[0]][right_loc[1]+1];
            }
            if (left_loc[1] == 4)
            {
                // column is on the left wrap around to the other side of the matrix.
               ret_chunk[1] = this.matrix2[left_loc[0]][0];
            }
            else
            {
                ret_chunk[1] = this.matrix2[left_loc[0]][left_loc[1]+1];
            }
        }
        else
        {
            ret_chunk[0] = this.matrix1[left_loc[0]][right_loc[1]];
            ret_chunk[1] = this.matrix2[right_loc[0]][left_loc[1]];
        }
        return ret_chunk;
    }
    // }}}

    /**
     * Convert a String into the format needed by the cipher.
     * @param text the String to normalize.
     * @return the properly formated form.
     */
    // normalize(String) {{{
    public static String normalize(String text) {
        text = text.replaceAll("0", "ZERO");
        text = text.replaceAll("1", "ONE");
        text = text.replaceAll("2", "TWO");
        text = text.replaceAll("3", "THREE");
        text = text.replaceAll("4", "FOUR");
        text = text.replaceAll("5", "FIVE");
        text = text.replaceAll("6", "SIX");
        text = text.replaceAll("7", "SEVEN");
        text = text.replaceAll("8", "EIGHT");
        text = text.replaceAll("9", "NINE");
        text = normalizeString(text);
        if (text.length() % 2 != 0)
        {
            text += 'X';
        }
        return text;
    }
    // }}}

    /**
     * Normalize the strings with valid character set. These two methods are
     * split to allow finer control based on need (text vs. keys).
     * @param text the String to normalize.
     * @return the properly formated form.
     */
    // normalizeString(String) {{{
    private static String normalizeString(String text) {
        char x;
        String output = "";
        text = text.toUpperCase().replace('J', 'I');
        for (int i = 0; i < text.length() ; i++)
        {
            x = text.charAt(i);
            if (VALID_CHARS.indexOf(x) >= 0)
            {
                output += x;
            }
        }
        return output;
    }
    // }}}

    /**
     * Build a Playfair matrix with a specified key.
     * @param key the key to build from.
     * @return the Playfair matrix as a 5x5 char array.
     */
    // keyToMatrix(String) {{{
    public static char[][] keyToMatrix(String key) {
        char matrix[][] = new char[5][5];
        int keycount = 0;
        char x;
        int row, col;
        String charlist = "";
        // Make uppercase, turn all J's into I's, Append filler alphabit to the end.
        key = normalizeString(key) + VALID_CHARS;

        for (row = 0; row < 5; row++)
        {
            for (col = 0; col < 5; col++)
            {
                do
                {
                    // If IndexOutOfBoundsException then there is a fatal math
                    // error. Constraints on the nature of the matrix should
                    // prevent this condition. If it happens we want a Runtime
                    // error to end the application. IndexOutOfBoundsException
                    // will propagate up the stack and die. No need to try /
                    // catch this.
                    x = key.charAt(keycount);
                    keycount++;
                }
                while (charlist.indexOf(x) >= 0);
                charlist += x;
                matrix[row][col] = x;
            }
        }
        return matrix;
    }
    // }}}

    /**
     * Print out the key matrix used for this Double Playfair cipher.
     * @return a string printout of the matrix.
     */
    // toString() {{{
    public String toString() {
        int row, col;
        String output = "";
        for (row = 0; row < 5; row++)
        {
            for (col = 0; col < 5; col++)
            {
                output += this.matrix1[row][col] + " ";
            }
            output += "|";
            for (col = 0; col < 5; col++)
            {
                output += " " + this.matrix2[row][col];
            }
            output += "\n";
        }
        return output;
    }
    // }}}
}
/* vim:set et sw=4 fdm=marker: */
