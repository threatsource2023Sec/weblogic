package org.apache.taglibs.standard.tei;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;

public class ForEachTEI extends TagExtraInfo {
   private static final String ITEMS = "items";
   private static final String BEGIN = "begin";
   private static final String END = "end";

   public boolean isValid(TagData us) {
      return Util.isSpecified(us, "items") || Util.isSpecified(us, "begin") && Util.isSpecified(us, "end");
   }
}
