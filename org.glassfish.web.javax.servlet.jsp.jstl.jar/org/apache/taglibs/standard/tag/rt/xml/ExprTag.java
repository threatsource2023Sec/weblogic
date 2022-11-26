package org.apache.taglibs.standard.tag.rt.xml;

import org.apache.taglibs.standard.tag.common.xml.ExprSupport;

public class ExprTag extends ExprSupport {
   public void setEscapeXml(boolean escapeXml) {
      this.escapeXml = escapeXml;
   }
}
