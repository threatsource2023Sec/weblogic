package org.apache.taglibs.standard.lang.jstl.test;

import java.beans.PropertyEditorSupport;

public class Bean2Editor extends PropertyEditorSupport {
   public void setAsText(String pText) throws IllegalArgumentException {
      if ("badvalue".equals(pText)) {
         throw new IllegalArgumentException("Bad value " + pText);
      } else {
         this.setValue(new Bean2(pText));
      }
   }
}
