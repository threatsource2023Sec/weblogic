package org.apache.taglibs.standard.tag.el.xml;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import org.apache.taglibs.standard.tag.common.xml.ParseSupport;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;
import org.xml.sax.XMLFilter;

public class ParseTag extends ParseSupport {
   private String xml_;
   private String systemId_;
   private String filter_;

   public ParseTag() {
      this.init();
   }

   public int doStartTag() throws JspException {
      this.evaluateExpressions();
      return super.doStartTag();
   }

   public void release() {
      super.release();
      this.init();
   }

   public void setFilter(String filter_) {
      this.filter_ = filter_;
   }

   public void setXml(String xml_) {
      this.xml_ = xml_;
   }

   public void setSystemId(String systemId_) {
      this.systemId_ = systemId_;
   }

   private void init() {
      this.filter_ = this.xml_ = this.systemId_ = null;
   }

   private void evaluateExpressions() throws JspException {
      this.xml = ExpressionUtil.evalNotNull("parse", "xml", this.xml_, Object.class, this, this.pageContext);
      this.systemId = (String)ExpressionUtil.evalNotNull("parse", "systemId", this.systemId_, String.class, this, this.pageContext);

      try {
         this.filter = (XMLFilter)ExpressionUtil.evalNotNull("parse", "filter", this.filter_, XMLFilter.class, this, this.pageContext);
      } catch (NullAttributeException var2) {
         this.filter = null;
      }

   }
}
