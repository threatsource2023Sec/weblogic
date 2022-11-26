package weblogic;

import java.lang.reflect.Method;

public class MsgEditor {
   public static void main(String[] args) {
      try {
         Class starterClass = Class.forName("weblogic.i18ntools.gui.MessageEditor");
         Class[] paramTypes = new Class[]{String[].class};
         Method mainMethod = starterClass.getMethod("main", paramTypes);
         Object[] params = new Object[]{args};
         mainMethod.invoke((Object)null, params);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }
}
