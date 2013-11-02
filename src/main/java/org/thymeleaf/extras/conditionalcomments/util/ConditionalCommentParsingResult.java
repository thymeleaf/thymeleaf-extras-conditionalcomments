/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2013, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.extras.conditionalcomments.util;






/**
 * <p>
 *   Specification of the diverse fragments of a conditional comment,
 *   once parsed.
 * </p>
 * <p>
 *   All fragments are specified as <tt>(offset, len)</tt> pairs
 *   on the original buffer (<tt>text</tt>). In order to build <tt>String</tt>
 *   objects for these fragments, only <tt>new String(text, offset, len)</tt>
 *   is required.
 * </p>
 * <p>
 *   Format is:
 * </p>
 * <pre><code>
 *    &lt;!--[start-condition]&gt;content&lt;![end-condition]--&gt;
 * </code></pre>
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.0
 *
 */
public final class ConditionalCommentParsingResult {

    
    private final String text;
    private final int startExpressionOffset;
    private final int startExpressionLen;
    private final int contentOffset;
    private final int contentLen;
    private final int endExpressionOffset;
    private final int endExpressionLen;
    
    
    
    
    public ConditionalCommentParsingResult(final String text,
            final int startExpressionOffset, final int startExpressionLen,
            final int contentOffset, final int contentLen, final int endExpressionOffset,
            final int endExpressionLen) {
        
        super();
        
        this.text = text;
        this.startExpressionOffset = startExpressionOffset;
        this.startExpressionLen = startExpressionLen;
        this.contentOffset = contentOffset;
        this.contentLen = contentLen;
        this.endExpressionOffset = endExpressionOffset;
        this.endExpressionLen = endExpressionLen;
        
    }




    public String getText() {
        return this.text;
    }

    public int getStartExpressionOffset() {
        return this.startExpressionOffset;
    }

    public int getStartExpressionLen() {
        return this.startExpressionLen;
    }

    public int getContentOffset() {
        return this.contentOffset;
    }

    public int getContentLen() {
        return this.contentLen;
    }

    public int getEndExpressionOffset() {
        return this.endExpressionOffset;
    }

    public int getEndExpressionLen() {
        return this.endExpressionLen;
    }
    
}
