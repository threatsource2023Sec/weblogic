package weblogic;

/** @deprecated */
@Deprecated
public class ejbc {
   public static void main(String[] args) throws Exception {
      System.out.println("\nDEPRECATED: The weblogic.ejbc compiler is deprecated and will be removed in a future version of WebLogic Server.  Please use weblogic.appc instead.\n");
      String s = System.getProperty("java.protocol.handler.pkgs");
      if (s == null) {
         s = "weblogic.utils";
      } else {
         s = s + "|weblogic.utils";
      }

      System.setProperty("java.protocol.handler.pkgs", s);

      try {
         (new ejbc20(args)).run();
      } catch (Error var3) {
         var3.printStackTrace();
         throw var3;
      }
   }
}
