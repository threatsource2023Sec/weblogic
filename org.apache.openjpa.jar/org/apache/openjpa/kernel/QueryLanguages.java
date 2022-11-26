package org.apache.openjpa.kernel;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.kernel.exps.ExpressionParser;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Services;
import org.apache.openjpa.util.InternalException;

public class QueryLanguages {
   public static final String LANG_SQL = "openjpa.SQL";
   public static final String LANG_METHODQL = "openjpa.MethodQL";
   private static Map _expressionParsers = new HashMap();

   public static ExpressionParser parserForLanguage(String language) {
      return (ExpressionParser)_expressionParsers.get(language);
   }

   static {
      Class[] classes = Services.getImplementorClasses(ExpressionParser.class, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(ExpressionParser.class)));

      for(int i = 0; i < classes.length; ++i) {
         ExpressionParser ep;
         try {
            ep = (ExpressionParser)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(classes[i]));
         } catch (PrivilegedActionException var4) {
            throw new InternalException(var4.getException());
         } catch (InstantiationException var5) {
            throw new InternalException(var5);
         } catch (IllegalAccessException var6) {
            throw new InternalException(var6);
         }

         _expressionParsers.put(ep.getLanguage(), ep);
      }

   }
}
