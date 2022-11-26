package org.apache.taglibs.standard.tag.common.sql;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.sql.SQLExecutionTag;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.resources.Resources;

public abstract class DateParamTagSupport extends TagSupport {
   private static final String TIMESTAMP_TYPE = "timestamp";
   private static final String TIME_TYPE = "time";
   private static final String DATE_TYPE = "date";
   protected String type;
   protected Date value;

   public DateParamTagSupport() {
      this.init();
   }

   private void init() {
      this.value = null;
      this.type = null;
   }

   public int doEndTag() throws JspException {
      SQLExecutionTag parent = (SQLExecutionTag)findAncestorWithClass(this, SQLExecutionTag.class);
      if (parent == null) {
         throw new JspTagException(Resources.getMessage("SQL_PARAM_OUTSIDE_PARENT"));
      } else {
         if (this.value != null) {
            this.convertValue();
         }

         parent.addSQLParameter(this.value);
         return 6;
      }
   }

   private void convertValue() throws JspException {
      if (this.type != null && !this.type.equalsIgnoreCase("timestamp")) {
         if (this.type.equalsIgnoreCase("time")) {
            if (!(this.value instanceof Time)) {
               this.value = new Time(this.value.getTime());
            }
         } else {
            if (!this.type.equalsIgnoreCase("date")) {
               throw new JspException(Resources.getMessage("SQL_DATE_PARAM_INVALID_TYPE", (Object)this.type));
            }

            if (!(this.value instanceof java.sql.Date)) {
               this.value = new java.sql.Date(this.value.getTime());
            }
         }
      } else if (!(this.value instanceof Timestamp)) {
         this.value = new Timestamp(this.value.getTime());
      }

   }
}
