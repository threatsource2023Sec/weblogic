package org.apache.taglibs.standard.tag.rt.core;

import org.apache.taglibs.standard.tag.common.core.OutSupport;

public class OutTag extends OutSupport {
   public void setValue(Object value) {
      this.value = value;
   }

   public void setDefault(String def) {
      this.def = def;
   }

   public void setEscapeXml(boolean escapeXml) {
      this.escapeXml = escapeXml;
   }
}
