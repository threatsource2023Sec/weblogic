package weblogicx.jsp.tags;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class QueryTagInfo extends TagExtraInfo {
   public VariableInfo[] getVariableInfo(TagData data) {
      String idValue = data.getAttributeString("id");
      if (idValue == null) {
         idValue = "rs";
      }

      VariableInfo info = new VariableInfo(idValue, "java.sql.ResultSet", true, 0);
      VariableInfo[] back = new VariableInfo[]{info};
      return back;
   }

   public boolean isValid(TagData data) {
      return true;
   }
}
