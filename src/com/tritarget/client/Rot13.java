/**
 * Copyright (c) 2008 Devin Weaver
 * Licensed under the Educational Community License version 1.0
 * See the file COPYING with this distrobution for details.
 */
package com.tritarget.client;

/**
 * Rot13 cipher. Static access only.
 */
public class Rot13 {
    /**
     * Encode and Decode with Rot13 cipher.
     * @param text the text to encode/decode.
     * @return the encoded/decoded text.
     */
    // encode(String) {{{
    public static String encode(String text) {
        char x;
        int i,j;
        String input = text.toUpperCase();
        String output = "";
        for (i = 0; i < input.length(); i++)
        {
            x = input.charAt(i);
            if (x >= 'A' && x <= 'Z')
            {
                j = (int) text.charAt(i);
                if (x >= 'N')
                {
                    j = j - 13;
                    output += "" + (char) j;
                }
                else
                {
                    j = j + 13;
                    output += "" + (char) j;
                }
            }
            else
            {
                output += text.charAt(i);
            }
        }
        return output;
    }
    // }}}
}
/* vim:set et sw=4 fdm=marker: */
