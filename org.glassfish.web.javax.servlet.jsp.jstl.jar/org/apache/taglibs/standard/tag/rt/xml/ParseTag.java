package org.apache.taglibs.standard.tag.rt.xml;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.xml.ParseSupport;
import org.xml.sax.XMLFilter;

public class ParseTag extends ParseSupport {
   public void setXml(Object xml) throws JspTagException {
      this.xml = xml;
   }

   public void setDoc(Object xml) throws JspTagException {
      this.xml = xml;
   }

   public void setSystemId(String systemId) throws JspTagException {
      this.systemId = systemId;
   }

   public void setFilter(XMLFilter filter) throws JspTagException {
      this.filter = filter;
   }
}
