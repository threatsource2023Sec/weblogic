package org.apache.taglibs.standard.lang.support;

import java.util.HashMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.lang.jstl.Coercions;
import org.apache.taglibs.standard.lang.jstl.ELException;
import org.apache.taglibs.standard.lang.jstl.Logger;

public class ExpressionEvaluatorManager {
   public static final String EVALUATOR_CLASS = "org.apache.taglibs.standard.lang.jstl.Evaluator";
   private static HashMap nameMap = new HashMap();
   private static Logger logger;

   public static Object evaluate(String attributeName, String expression, Class expectedType, Tag tag, PageContext pageContext) throws JspException {
      ExpressionEvaluator target = getEvaluatorByName("org.apache.taglibs.standard.lang.jstl.Evaluator");
      return target.evaluate(attributeName, expression, expectedType, tag, pageContext);
   }

   public static Object evaluate(String attributeName, String expression, Class expectedType, PageContext pageContext) throws JspException {
      ExpressionEvaluator target = getEvaluatorByName("org.apache.taglibs.standard.lang.jstl.Evaluator");
      return target.evaluate(attributeName, expression, expectedType, (Tag)null, pageContext);
   }

   public static ExpressionEvaluator getEvaluatorByName(String name) throws JspException {
      Object oEvaluator = nameMap.get(name);
      if (oEvaluator != null) {
         return (ExpressionEvaluator)oEvaluator;
      } else {
         try {
            synchronized(nameMap) {
               oEvaluator = nameMap.get(name);
               if (oEvaluator != null) {
                  return (ExpressionEvaluator)oEvaluator;
               } else {
                  ExpressionEvaluator e = (ExpressionEvaluator)Class.forName(name).newInstance();
                  nameMap.put(name, e);
                  return e;
               }
            }
         } catch (ClassCastException var6) {
            throw new JspException("invalid ExpressionEvaluator: " + var6.toString(), var6);
         } catch (ClassNotFoundException var7) {
            throw new JspException("couldn't find ExpressionEvaluator: " + var7.toString(), var7);
         } catch (IllegalAccessException var8) {
            throw new JspException("couldn't access ExpressionEvaluator: " + var8.toString(), var8);
         } catch (InstantiationException var9) {
            throw new JspException("couldn't instantiate ExpressionEvaluator: " + var9.toString(), var9);
         }
      }
   }

   public static Object coerce(Object value, Class classe) throws JspException {
      try {
         return Coercions.coerce(value, classe, logger);
      } catch (ELException var3) {
         throw new JspException(var3);
      }
   }

   static {
      logger = new Logger(System.out);
   }
}
