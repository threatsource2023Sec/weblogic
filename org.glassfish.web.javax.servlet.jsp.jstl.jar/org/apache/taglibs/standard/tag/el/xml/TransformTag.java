package org.apache.taglibs.standard.tag.el.xml;

import javax.servlet.jsp.JspException;
import javax.xml.transform.Result;
import org.apache.taglibs.standard.tag.common.xml.TransformSupport;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;

public class TransformTag extends TransformSupport {
   private String xml_;
   private String xmlSystemId_;
   private String xslt_;
   private String xsltSystemId_;
   private String result_;

   public TransformTag() {
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

   public void setXml(String xml_) {
      this.xml_ = xml_;
   }

   public void setXmlSystemId(String xmlSystemId_) {
      this.xmlSystemId_ = xmlSystemId_;
   }

   public void setXslt(String xslt_) {
      this.xslt_ = xslt_;
   }

   public void setXsltSystemId(String xsltSystemId_) {
      this.xsltSystemId_ = xsltSystemId_;
   }

   public void setResult(String result_) {
      this.result_ = result_;
   }

   private void init() {
      this.xml_ = this.xmlSystemId = this.xslt_ = this.xsltSystemId_ = this.result_ = null;
   }

   private void evaluateExpressions() throws JspException {
      this.xml = ExpressionUtil.evalNotNull("transform", "xml", this.xml_, Object.class, this, this.pageContext);
      this.xmlSystemId = (String)ExpressionUtil.evalNotNull("transform", "xmlSystemId", this.xmlSystemId_, String.class, this, this.pageContext);
      this.xslt = ExpressionUtil.evalNotNull("transform", "xslt", this.xslt_, Object.class, this, this.pageContext);
      this.xsltSystemId = (String)ExpressionUtil.evalNotNull("transform", "xsltSystemId", this.xsltSystemId_, String.class, this, this.pageContext);
      this.result = (Result)ExpressionUtil.evalNotNull("transform", "result", this.result_, Result.class, this, this.pageContext);
   }
}
