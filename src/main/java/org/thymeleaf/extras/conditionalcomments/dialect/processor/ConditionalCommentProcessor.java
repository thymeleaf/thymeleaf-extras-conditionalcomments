/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2012, The THYMELEAF team (http://www.thymeleaf.org)
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
package org.thymeleaf.extras.conditionalcomments.dialect.processor;

import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Comment;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.extras.conditionalcomments.parser.AttoTemplateParser;
import org.thymeleaf.extras.conditionalcomments.util.ConditionalCommentParsingResult;
import org.thymeleaf.extras.conditionalcomments.util.ConditionalCommentUtils;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.comment.AbstractCommentNodeProcessor;
import org.thymeleaf.util.DOMUtils;



/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.0
 *
 */
public class ConditionalCommentProcessor extends AbstractCommentNodeProcessor {

    
    public static final int PRECEDENCE = 1000;

    
    
    public ConditionalCommentProcessor() {
        super(ConditionalCommentNodeProcessorMatcher.INSTANCE);
    }


    
    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }


    
    @Override
    protected ProcessorResult processCommentNode(final Arguments arguments, final Comment commentNode) {

        final ConditionalCommentParsingResult parsingResult =
            ConditionalCommentUtils.parseConditionalComment(commentNode.unsafeGetContentCharArray());

        
        final StringBuilder strBuilder = new StringBuilder();
        
        strBuilder.append("[");
        strBuilder.append(parsingResult.getText(), parsingResult.getStartExpressionOffset(), parsingResult.getStartExpressionLen());
        strBuilder.append("]>");
        
        List<Node> nodes = null;

{
        final AttoTemplateParser parser = new AttoTemplateParser();

        final long start = System.nanoTime();
        nodes =
                parser.parseFragment(
                arguments.getConfiguration(), 
                parsingResult.getText(), parsingResult.getContentOffset(), parsingResult.getContentLen());
        
        final long end = System.nanoTime();
        
        System.out.println("\n\n*** PARSED in: " + (end - start) + "\n\n");
}
//{
//        final ITemplateParser parser = new XhtmlAndHtml5NonValidatingSAXTemplateParser(1);
//        
//        final long start = System.nanoTime();
//        nodes =
//                parser.parseFragment(
//                arguments.getConfiguration(), 
//                new String(parsingResult.getText(), parsingResult.getContentOffset(), parsingResult.getContentLen()));
//          
//        final long end = System.nanoTime();
//          
//        System.out.println("\n\n*** PARSED in: " + (end - start) + "\n\n");
//        
//}
    
        for (final Node node : nodes) {
            if (node instanceof Element) {
                final Element el = (Element) node;
                strBuilder.append("[[");
                strBuilder.append(DOMUtils.getHtml5For(node));
                strBuilder.append("]");
                strBuilder.append(el.getRepresentationInTemplate());
                strBuilder.append(",");
                strBuilder.append(el.hasChildren());
                strBuilder.append("]");
                if (el.hasChildren()) {
                    for (final Node child : el.getChildren()) {
                        strBuilder.append("{{");
                        strBuilder.append(DOMUtils.getHtml5For(child));
                        strBuilder.append("}");
                        strBuilder.append(child.getClass().getName());
                        strBuilder.append("}");
                    }
                }
            } else {
                strBuilder.append(DOMUtils.getHtml5For(node));
            }
        }
        
        strBuilder.append("<![");
        strBuilder.append(parsingResult.getText(), parsingResult.getEndExpressionOffset(), parsingResult.getEndExpressionLen());
        strBuilder.append("]");
        
        commentNode.setContent(strBuilder.toString());
        
        return ProcessorResult.OK;
        
    }
    
    
}
