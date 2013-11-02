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

import junit.framework.TestCase;





public final class ConditionalCommentUtilsTest extends TestCase {
    
    
    /*
     * FORMAT:
     * 
     *   <!--[if IE lt 8]> <html blahblahblah> <![endif]-->
     */

    public void testIsConditionalComment() throws Exception {

        {
            final String text = 
                    "[if lt IE 8]>\n" +
                    "   <link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\">\n" +
                    "<![endif]";
            final String se = "if lt IE 8";
            final String c = "\n   <link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\">\n";
            final String ee = "endif";
            
            
            checkParsing(text, se, c, ee);
        }

        {
            final String text = 
                    "[]>\n" +
                    "   <link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\">\n" +
                    "<![endif]";
            final String se = "if lt IE 8";
            final String c = "\n   <link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\">\n";
            final String ee = "endif";
            
            
            checkParsing(text, se, c, ee, true);
        }

        {
            final String text = 
                    "[if lt IE 8]><link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\"><![endif]";
            final String se = "if lt IE 8";
            final String c = "<link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\">";
            final String ee = "endif";
            
            
            checkParsing(text, se, c, ee);
        }


        {
            final String text = 
                    "[if lt IE 8]<link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\"><![endif]";
            final String se = "if lt IE 8";
            final String c = "<link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\">";
            final String ee = "endif";
            
            
            checkParsing(text, se, c, ee, true);
        }


        {
            final String text = 
                    "[if lt IE 8><link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\"><![endif]";
            final String se = "if lt IE 8";
            final String c = "<link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\">";
            final String ee = "endif";
            
            
            checkParsing(text, se, c, ee, true);
        }

        {
            final String text = 
                    "[if lt IE 8]><link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\"><[endif]";
            final String se = "if lt IE 8";
            final String c = "<link rel=\"stylesheet\" href=\"/resources/blueprint/ie.css\" type=\"text/css\" media=\"screen, projection\">";
            final String ee = "endif";
            
            
            checkParsing(text, se, c, ee, true);
        }

        
    }
  
    

    
    
    private void checkParsing(final String text, 
            final String expectedStartExpression, final String expectedContent, final String expectedEndExpression)
            throws Exception {
        checkParsing(text, expectedStartExpression, expectedContent, expectedEndExpression, false);
    }
    
    private void checkParsing(final String text, 
            final String expectedStartExpression, final String expectedContent, final String expectedEndExpression,
            final boolean shouldFail) {
        
        final ConditionalCommentParsingResult result =
                ConditionalCommentUtils.parseConditionalComment(text);
        if (result == null) {
            if (!shouldFail) {
                assertTrue("Text \""+ text + "\" did not parse OK but should have", false);
            }
        } else if (shouldFail) {
            assertFalse("Text \""+ text + "\" parsed OK but should have failed", true);
        }

        if (result != null) {
            final String obtainedStartExpression =
                    result.getText().substring(result.getStartExpressionOffset(), result.getStartExpressionOffset() + result.getStartExpressionLen());
            final String obtainedContent =
                    result.getText().substring(result.getContentOffset(), result.getContentOffset() + result.getContentLen());
            final String obtainedEndExpression =
                    result.getText().substring(result.getEndExpressionOffset(), result.getEndExpressionOffset() + result.getEndExpressionLen());
            
            assertEquals(expectedStartExpression, obtainedStartExpression);
            assertEquals(expectedContent, obtainedContent);
            assertEquals(expectedEndExpression, obtainedEndExpression);
        }
        
    }
    
    
    
}
