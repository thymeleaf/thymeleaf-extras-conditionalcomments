/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
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

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Comment;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Node;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.extras.conditionalcomments.parser.ConditionalCommentAttoTemplateParser;
import org.thymeleaf.extras.conditionalcomments.util.ConditionalCommentParsingResult;
import org.thymeleaf.extras.conditionalcomments.util.ConditionalCommentUtils;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.comment.AbstractCommentNodeProcessor;
import org.thymeleaf.templatemode.ITemplateModeHandler;
import org.thymeleaf.templatewriter.ITemplateWriter;



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
            ConditionalCommentUtils.parseConditionalComment(commentNode.getContent());

        
        final StringWriter writer = new StringWriter();
        
        /*
         * Rebuild the conditional comment start expression
         */
        writer.write("[");
        writer.write(parsingResult.getText(), parsingResult.getStartExpressionOffset(), parsingResult.getStartExpressionLen());
        writer.write("]>");
        
        final ConditionalCommentAttoTemplateParser parser = new ConditionalCommentAttoTemplateParser();

        final List<Node> nodes = 
                parser.parseFragment(
                    arguments.getConfiguration(), 
                    parsingResult.getText(), 
                    parsingResult.getContentOffset(), 
                    parsingResult.getContentLen());
        
        final String templateMode = 
                arguments.getTemplateResolution().getTemplateMode();
        final ITemplateModeHandler templateModeHandler =
                arguments.getConfiguration().getTemplateModeHandler(templateMode);
        final ITemplateWriter templateWriter = templateModeHandler.getTemplateWriter();
        
        
        final Document document = 
                new Document("[Conditional Comment at line: " + commentNode.getLineNumber() + "]");
        document.setChildren(nodes);
        
        document.process(arguments);
        
        try {
            templateWriter.write(arguments, writer, document);
        } catch (final IOException e) {
            throw new TemplateProcessingException(
                    "Error writing result of processing conditional comment at line " +
                    commentNode.getLineNumber(), e);
        }
        
        
        /*
         * Rebuild the conditional comment end expression
         */
        writer.write("<![");
        writer.write(parsingResult.getText(), parsingResult.getEndExpressionOffset(), parsingResult.getEndExpressionLen());
        writer.write("]");
        
        /*
         * Re-set the comment content, once processed
         */
        commentNode.setContent(writer.toString());
        
        return ProcessorResult.OK;
        
    }
    
    
}
