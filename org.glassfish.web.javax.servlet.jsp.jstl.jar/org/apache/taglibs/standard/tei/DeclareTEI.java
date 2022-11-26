package org.apache.taglibs.standard.tei;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class DeclareTEI extends TagExtraInfo {
   public VariableInfo[] getVariableInfo(TagData data) {
      VariableInfo id = new VariableInfo(data.getAttributeString("id"), data.getAttributeString("type") == null ? "java.lang.Object" : data.getAttributeString("type"), true, 2);
      return new VariableInfo[]{id};
   }
}
