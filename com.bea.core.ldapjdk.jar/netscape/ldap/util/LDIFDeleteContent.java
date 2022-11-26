package netscape.ldap.util;

public class LDIFDeleteContent extends LDIFBaseContent {
   static final long serialVersionUID = -6581979396116035503L;

   public int getType() {
      return 2;
   }

   public String toString() {
      String var1 = "";
      if (this.getControls() != null) {
         var1 = var1 + this.getControlString();
      }

      return "LDIFDeleteContent {" + var1 + "}";
   }
}
