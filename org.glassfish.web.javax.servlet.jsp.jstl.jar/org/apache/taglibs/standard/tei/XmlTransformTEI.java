package org.apache.taglibs.standard.tei;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;

public class XmlTransformTEI extends TagExtraInfo {
   private static final String XSLT = "xslt";
   private static final String RESULT = "result";
   private static final String VAR = "var";

   public boolean isValid(TagData us) {
      if (!Util.isSpecified(us, "xslt")) {
         return false;
      } else {
         return !Util.isSpecified(us, "var") || !Util.isSpecified(us, "result");
      }
   }
}
