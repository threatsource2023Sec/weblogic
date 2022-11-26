package org.apache.taglibs.standard.tei;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;

public class ImportTEI extends TagExtraInfo {
   private static final String VAR = "var";
   private static final String VAR_READER = "varReader";

   public boolean isValid(TagData us) {
      return !Util.isSpecified(us, "var") || !Util.isSpecified(us, "varReader");
   }
}
