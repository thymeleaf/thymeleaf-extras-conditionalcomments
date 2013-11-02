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
package org.thymeleaf.extras.conditionalcomments.dialect.processor;

import org.thymeleaf.dom.Comment;
import org.thymeleaf.dom.Node;
import org.thymeleaf.extras.conditionalcomments.util.ConditionalCommentUtils;
import org.thymeleaf.processor.ICommentNodeProcessorMatcher;
import org.thymeleaf.processor.ProcessorMatchingContext;





/**
 * <p>
 *   Implementation of {@link ICommentNodeProcessorMatcher} matching Comment nodes that
 *   look like conditional comments.
 * </p>
 * <p>
 *   In order to determine whether a comment is a conditional comment or not,
 *   {@link ConditionalCommentUtils#isConditionalComment(String)} is used.
 * </p>
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.0
 *
 */
public final class ConditionalCommentNodeProcessorMatcher implements ICommentNodeProcessorMatcher {
    
    
    public static ConditionalCommentNodeProcessorMatcher INSTANCE = new ConditionalCommentNodeProcessorMatcher();
    

    /**
     * <p>
     *   Create a new matcher of this class.
     * </p>
     */
    public ConditionalCommentNodeProcessorMatcher() {
        super();
    }
    
    

    /**
     * <p>
     *   Matches the specified {@link Node} if it is an instance of {@link Comment}
     *   AND it conforms to {@link ConditionalCommentUtils#isConditionalComment(String)}.
     * </p>
     * 
     * @param node the node to be checked
     * @param context the processor matching context
     * @return true if node is a conditional comment, false if not
     */
    public boolean matches(final Node node, final ProcessorMatchingContext context) {
        
        if (node == null || !(node instanceof Comment)) {
            // fail fast
            return false;
        }
        
        final Comment comment = (Comment) node;
        return ConditionalCommentUtils.isConditionalComment(comment.getContent());
        
    }



    /**
     * <p>
     *   Applies to {@link Comment} nodes.
     * </p>
     */
    public final Class<? extends Comment> appliesTo() {
        return Comment.class;
    }
    
    
}
