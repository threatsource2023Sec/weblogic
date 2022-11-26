package org.apache.taglibs.standard.tag.el.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;

public class ExpressionUtil {
   public static Object evalNotNull(String tagName, String attributeName, String expression, Class expectedType, Tag tag, PageContext pageContext) throws JspException {
      if (expression != null) {
         Object r = ExpressionEvaluatorManager.evaluate(attributeName, expression, expectedType, tag, pageContext);
         if (r == null) {
            throw new NullAttributeException(tagName, attributeName);
         } else {
            return r;
         }
      } else {
         return null;
      }
   }
}
