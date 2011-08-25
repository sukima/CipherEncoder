/**
 * Copyright (c) 2008 Devin Weaver
 * Licensed under the Educational Community License version 1.0
 * See the file COPYING with this distrobution for details.
 */
package org.tritarget.client;

/**
 * Encapsulates all the logic used to encode and decode using the
 * Transposition cipher.
 */
public class TranspositionCipher {
    private String key;
    private int[] order_map;

    /**
     * Construct a new object with a key.
     * @param key the cipher key.
     */
    // TranspositionCipher(String) {{{
    public TranspositionCipher(String key) {
        this.key = normalizeString(key);
        this.order_map = new int[this.key.length()];
        int i;
        int pos = 0;
        char last_char = 'A';
        // Numbers coming out in wrong order for order_map
        while (pos < this.key.length())
        {
            for (i = 0; i < this.key.length(); i++)
            {
                if (this.key.charAt(i) == last_char)
                {
                    this.order_map[pos] = i;
                    pos++;
                }
            }
            last_char++;
        }
    }
    // }}}

    /**
     * Contruct a table based on key size and text size.
     * @param char_count is the number of character in the text to encode.
     * @return a table of proper size.
     */
    // buildMatrix(int) {{{
    private char[][] buildTable(int char_count) {
        char[][] table = new char[this.key.length()][];
        // iterate over the table to set up the lengths.
        int rows = (char_count / this.key.length());
        int overflow_count = (char_count % this.key.length());
        for (int i = 0; i < table.length; i++)
        {
            if (i < overflow_count)
            {
                table[i] = new char[rows + 1];
            }
            else
            {
                table[i] = new char[rows];
            }
        }
        return table;
    }
    // }}}

    /**
     * Encode any string with the Transposition cipher. Text normalization is done
     * automatically.
     * @param text the text to encode.
     * @return the encoded text as a string.
     */
    // encode(String) {{{
    public String encode(String text) {
        text = normalize(text);
        char[][] table = buildTable(text.length());
        String output = "";
        int i, j, row, col;
        // Add the characters one at a time left to right.
        row = 0;
        col = 0;
        for (i = 0; i < text.length(); i++)
        {
            table[col][row] = text.charAt(i);
            col++;
            if (col >= table.length)
            {
                col = 0;
                row++;
            }
        }
        // Read the table top to botom in order of order_map.
        // Assemble in a string (left to right)
        i = 0;
        for (col = 0; col < this.order_map.length; col++)
        {
            for (row = 0; row < table[this.order_map[col]].length; row++)
            {
                output += table[this.order_map[col]][row];
                i++;
                if (i >= 5)
                {
                    output += " ";
                    i = 0;
                }
            }
        }
        return output;
    }
    // }}}

    /**
     * Decode any string with the Transposition cipher. Text normalization is done
     * automatically.
     * @param text the text to decode.
     * @return the decoded text as a string.
     */
    // decode(String) {{{
    public String decode(String text) {
        text = normalizeString(text);
        char[][] table = buildTable(text.length());
        String output = "";
        int col, row, i, key_index;
        // construct the table top to botom in order of order_map.
        // Assemble in a string (left to right)
        i = 0;
        key_index = 0;
        while (i < text.length())
        {
            for (row = 0; row < table[this.order_map[key_index]].length; row++)
            {
                table[this.order_map[key_index]][row] = text.charAt(i);
                i++;
            }
            key_index++;
        }
        // read table left to right.
        i = 0;
        row = 0;
        col = 0;
        for (i = 0; i < text.length(); i++)
        {
            output += table[col][row];
            col++;
            if (col >= table.length)
            {
                col = 0;
                row++;
            }
            /*
            if ((i + 1) % 5 == 0)
            {
                output += " ";
            }
            */
        }
        return output;
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
        return normalizeString(text);
    }
    // }}}

    /**
     * Normalize the strings with valid character set. These two methods are
     * split to allow finer control based on need.
     * @param text the String to normalize.
     * @return the properly formated form.
     */
    // normalizeString(String) {{{
    private static String normalizeString(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }
    // }}}

    /**
     * Get a String representing the key and order for this object.
     * @return the String representation.
     */
    // toString() {{{
    public String toString() {
        String output = "";
        int i,j,x;
        for (i = 0; i < this.key.length(); i++)
        {
            for (j = 0; j < this.order_map.length; j++)
            {
                if (this.order_map[j] == i)
                {
                    break;
                }
            }
            output += "" + this.key.charAt(i) + ":" + j + " ";
        }
        return output;
    }
    // }}}
}
/* vim:set et sw=4 fdm=marker: */
