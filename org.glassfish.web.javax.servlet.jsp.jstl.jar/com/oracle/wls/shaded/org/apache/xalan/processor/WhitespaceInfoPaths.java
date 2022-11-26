package com.oracle.wls.shaded.org.apache.xalan.processor;

import com.oracle.wls.shaded.org.apache.xalan.templates.Stylesheet;
import com.oracle.wls.shaded.org.apache.xalan.templates.WhiteSpaceInfo;
import java.util.Vector;

public class WhitespaceInfoPaths extends WhiteSpaceInfo {
   static final long serialVersionUID = 5954766719577516723L;
   private Vector m_elements;

   public void setElements(Vector elems) {
      this.m_elements = elems;
   }

   Vector getElements() {
      return this.m_elements;
   }

   public void clearElements() {
      this.m_elements = null;
   }

   public WhitespaceInfoPaths(Stylesheet thisSheet) {
      super(thisSheet);
      this.setStylesheet(thisSheet);
   }
}
