/**
 * Copyright (c) 2008 Devin Weaver
 * Licensed under the Educational Community License version 1.0
 * See the file COPYING with this distrobution for details.
 */
package org.tritarget.client;

import com.google.gwt.user.client.ui.HTML;

/**
 * Static class for the Instructions Box HTML template.
 */
public final class InstructionBox {
    public static HTML getHtml() {
        return new HTML("<div class=\"instructions-box\">"
                        + "You must fill in all the text fields before the buttons will activate."
                        + "</div>");
    }
}
