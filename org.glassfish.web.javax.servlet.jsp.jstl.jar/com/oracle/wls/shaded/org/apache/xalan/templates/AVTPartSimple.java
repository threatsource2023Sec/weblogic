package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xml.utils.FastStringBuffer;
import com.oracle.wls.shaded.org.apache.xml.utils.PrefixResolver;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import java.util.Vector;

public class AVTPartSimple extends AVTPart {
   static final long serialVersionUID = -3744957690598727913L;
   private String m_val;

   public AVTPartSimple(String val) {
      this.m_val = val;
   }

   public String getSimpleString() {
      return this.m_val;
   }

   public void fixupVariables(Vector vars, int globalsSize) {
   }

   public void evaluate(XPathContext xctxt, FastStringBuffer buf, int context, PrefixResolver nsNode) {
      buf.append(this.m_val);
   }

   public void callVisitors(XSLTVisitor visitor) {
   }
}
