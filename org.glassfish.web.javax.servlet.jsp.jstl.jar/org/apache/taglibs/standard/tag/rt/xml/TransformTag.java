package org.apache.taglibs.standard.tag.rt.xml;

import javax.servlet.jsp.JspTagException;
import javax.xml.transform.Result;
import org.apache.taglibs.standard.tag.common.xml.TransformSupport;

public class TransformTag extends TransformSupport {
   public void setXml(Object xml) throws JspTagException {
      this.xml = xml;
   }

   public void setDoc(Object xml) throws JspTagException {
      this.xml = xml;
   }

   public void setXmlSystemId(String xmlSystemId) throws JspTagException {
      this.xmlSystemId = xmlSystemId;
   }

   public void setDocSystemId(String xmlSystemId) throws JspTagException {
      this.xmlSystemId = xmlSystemId;
   }

   public void setXslt(Object xslt) throws JspTagException {
      this.xslt = xslt;
   }

   public void setXsltSystemId(String xsltSystemId) throws JspTagException {
      this.xsltSystemId = xsltSystemId;
   }

   public void setResult(Result result) throws JspTagException {
      this.result = result;
   }
}
