package org.apache.taglibs.standard.lang.support;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public interface ExpressionEvaluator {
   String validate(String var1, String var2);

   Object evaluate(String var1, String var2, Class var3, Tag var4, PageContext var5) throws JspException;
}
