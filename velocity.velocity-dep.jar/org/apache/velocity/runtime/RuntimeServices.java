package org.apache.velocity.runtime;

import java.io.Reader;
import java.util.Properties;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.Template;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.runtime.resource.ContentResource;
import org.apache.velocity.util.introspection.Introspector;
import org.apache.velocity.util.introspection.Uberspect;

public interface RuntimeServices extends RuntimeLogger {
   void init() throws Exception;

   void setProperty(String var1, Object var2);

   void setConfiguration(ExtendedProperties var1);

   void addProperty(String var1, Object var2);

   void clearProperty(String var1);

   Object getProperty(String var1);

   void init(Properties var1) throws Exception;

   void init(String var1) throws Exception;

   SimpleNode parse(Reader var1, String var2) throws ParseException;

   SimpleNode parse(Reader var1, String var2, boolean var3) throws ParseException;

   Template getTemplate(String var1) throws ResourceNotFoundException, ParseErrorException, Exception;

   Template getTemplate(String var1, String var2) throws ResourceNotFoundException, ParseErrorException, Exception;

   ContentResource getContent(String var1) throws ResourceNotFoundException, ParseErrorException, Exception;

   ContentResource getContent(String var1, String var2) throws ResourceNotFoundException, ParseErrorException, Exception;

   String getLoaderNameForResource(String var1);

   String getString(String var1, String var2);

   Directive getVelocimacro(String var1, String var2);

   boolean addVelocimacro(String var1, String var2, String[] var3, String var4);

   boolean isVelocimacro(String var1, String var2);

   boolean dumpVMNamespace(String var1);

   String getString(String var1);

   int getInt(String var1);

   int getInt(String var1, int var2);

   boolean getBoolean(String var1, boolean var2);

   ExtendedProperties getConfiguration();

   Object getApplicationAttribute(Object var1);

   Uberspect getUberspect();

   Introspector getIntrospector();
}
