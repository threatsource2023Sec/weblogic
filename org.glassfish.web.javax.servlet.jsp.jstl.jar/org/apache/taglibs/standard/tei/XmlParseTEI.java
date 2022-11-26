package org.apache.taglibs.standard.tei;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;

public class XmlParseTEI extends TagExtraInfo {
   private static final String VAR = "var";
   private static final String VAR_DOM = "varDom";
   private static final String SCOPE = "scope";
   private static final String SCOPE_DOM = "scopeDom";

   public boolean isValid(TagData us) {
      if (Util.isSpecified(us, "var") && Util.isSpecified(us, "varDom")) {
         return false;
      } else if (!Util.isSpecified(us, "var") && !Util.isSpecified(us, "varDom")) {
         return false;
      } else if (Util.isSpecified(us, "scope") && !Util.isSpecified(us, "var")) {
         return false;
      } else {
         return !Util.isSpecified(us, "scopeDom") || Util.isSpecified(us, "varDom");
      }
   }
}
