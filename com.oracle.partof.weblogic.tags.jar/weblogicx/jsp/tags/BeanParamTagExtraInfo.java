package weblogicx.jsp.tags;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class BeanParamTagExtraInfo extends TagExtraInfo {
   private static final boolean debug = false;

   static void p(String s) {
   }

   public VariableInfo[] getVariableInfo(TagData td) {
      p("getVariableInfo");
      VariableInfo[] ret = new VariableInfo[]{new VariableInfo("index", "java.lang.Integer", true, 0), new VariableInfo("arrayLength", "java.lang.Integer", true, 0)};
      return ret;
   }
}
