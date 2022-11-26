package weblogic;

import java.lang.reflect.Constructor;
import weblogic.utils.compiler.Tool;

public abstract class jspc {
   private static Tool makeJspc(String[] args) throws Exception {
      Class jspc20 = Class.forName("weblogic.servlet.jsp.jspc20");
      Constructor ctor = jspc20.getConstructor(String[].class);
      return (Tool)ctor.newInstance(args);
   }

   public static void main(String[] args) throws Exception {
      Tool compiler = makeJspc(args);
      compiler.run();
   }
}
