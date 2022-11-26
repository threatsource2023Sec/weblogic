package org.apache.taglibs.standard.lang.jstl;

import java.text.MessageFormat;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluator;

public class Evaluator implements ExpressionEvaluator {
   static ELEvaluator sEvaluator = new ELEvaluator(new JSTLVariableResolver());

   public String validate(String pAttributeName, String pAttributeValue) {
      try {
         sEvaluator.parseExpressionString(pAttributeValue);
         return null;
      } catch (ELException var4) {
         return MessageFormat.format(Constants.ATTRIBUTE_PARSE_EXCEPTION, "" + pAttributeName, "" + pAttributeValue, var4.getMessage());
      }
   }

   public Object evaluate(String pAttributeName, String pAttributeValue, Class pExpectedType, Tag pTag, PageContext pPageContext, Map functions, String defaultPrefix) throws JspException {
      try {
         return sEvaluator.evaluate(pAttributeValue, pPageContext, pExpectedType, functions, defaultPrefix);
      } catch (ELException var9) {
         throw new JspException(MessageFormat.format(Constants.ATTRIBUTE_EVALUATION_EXCEPTION, "" + pAttributeName, "" + pAttributeValue, var9.getMessage(), var9.getRootCause()), var9.getRootCause());
      }
   }

   public Object evaluate(String pAttributeName, String pAttributeValue, Class pExpectedType, Tag pTag, PageContext pPageContext) throws JspException {
      return this.evaluate(pAttributeName, pAttributeValue, pExpectedType, pTag, pPageContext, (Map)null, (String)null);
   }

   public static String parseAndRender(String pAttributeValue) throws JspException {
      try {
         return sEvaluator.parseAndRender(pAttributeValue);
      } catch (ELException var2) {
         throw new JspException(MessageFormat.format(Constants.ATTRIBUTE_PARSE_EXCEPTION, "test", "" + pAttributeValue, var2.getMessage()));
      }
   }
}
