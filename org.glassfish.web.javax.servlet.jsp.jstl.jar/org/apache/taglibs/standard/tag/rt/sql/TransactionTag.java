package org.apache.taglibs.standard.tag.rt.sql;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.tag.common.sql.TransactionTagSupport;

public class TransactionTag extends TransactionTagSupport {
   private String isolationRT;

   public void setDataSource(Object dataSource) {
      this.rawDataSource = dataSource;
      this.dataSourceSpecified = true;
   }

   public void setIsolation(String isolation) {
      this.isolationRT = isolation;
   }

   public int doStartTag() throws JspException {
      if (this.isolationRT != null) {
         super.setIsolation(this.isolationRT);
      }

      return super.doStartTag();
   }
}
