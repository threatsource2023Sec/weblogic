package weblogicx.jsp.tags;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class RepeatTagInfo extends TagExtraInfo {
   public VariableInfo[] getVariableInfo(TagData data) {
      String idValue = data.getAttributeString("id");
      if (idValue == null) {
         throw new Error("TagLibrary inconsistency");
      } else {
         String typeValue = data.getAttributeString("type");
         VariableInfo info = new VariableInfo(idValue, typeValue == null ? "java.lang.Object" : typeValue, true, 0);
         VariableInfo[] back = new VariableInfo[]{info};
         return back;
      }
   }

   public boolean isValid(TagData data) {
      return true;
   }
}
