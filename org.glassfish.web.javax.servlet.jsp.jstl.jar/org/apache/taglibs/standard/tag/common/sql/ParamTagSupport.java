package org.apache.taglibs.standard.tag.common.sql;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.sql.SQLExecutionTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.taglibs.standard.resources.Resources;

public abstract class ParamTagSupport extends BodyTagSupport {
   protected Object value;

   public int doEndTag() throws JspException {
      SQLExecutionTag parent = (SQLExecutionTag)findAncestorWithClass(this, SQLExecutionTag.class);
      if (parent == null) {
         throw new JspTagException(Resources.getMessage("SQL_PARAM_OUTSIDE_PARENT"));
      } else {
         Object paramValue = null;
         if (this.value != null) {
            paramValue = this.value;
         } else if (this.bodyContent != null) {
            paramValue = this.bodyContent.getString().trim();
            if (((String)paramValue).trim().length() == 0) {
               paramValue = null;
            }
         }

         parent.addSQLParameter(paramValue);
         return 6;
      }
   }
}
