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
package org.thymeleaf.extras.conditionalcomments.dialect;

import java.io.StringWriter;
import java.io.Writer;

import junit.framework.TestCase;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;





public final class ConditionalCommentsDialectTest extends TestCase {
    
    
    private static final IContext EMPTY_CONTEXT = new Context();
    
    
    
    public void testDialect() throws Exception {

        final TemplateEngine engine = initTemplateEngine();
        
        {
            final Writer writer = new StringWriter();
            
            engine.process("test1.html", EMPTY_CONTEXT, writer);
            System.out.println("\n\n********\n" + writer.toString() + "\n\n********\n\n");
            
        }
        
    }
  
    
    
    

    private static TemplateEngine initTemplateEngine() throws Exception {
        
        final TemplateEngine engine = new TemplateEngine();
        
        final ConditionalCommentsDialect conditionalCommentsDialect = new ConditionalCommentsDialect();
        engine.addDialect(conditionalCommentsDialect);
        
        final ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setTemplateMode("HTML5");
        engine.setTemplateResolver(classLoaderTemplateResolver);
       
        return engine;
        
    }
    
    
    
}
