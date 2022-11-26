package netscape.ldap;

import java.io.Serializable;
import java.util.Vector;

public class LDAPModificationSet implements Serializable {
   static final long serialVersionUID = 4650238666753391214L;
   private int current = 0;
   private Vector modifications = new Vector();

   public LDAPModificationSet() {
      this.current = 0;
   }

   public int size() {
      return this.modifications.size();
   }

   public LDAPModification elementAt(int var1) {
      return (LDAPModification)this.modifications.elementAt(var1);
   }

   public void removeElementAt(int var1) {
      this.modifications.removeElementAt(var1);
   }

   public synchronized void add(int var1, LDAPAttribute var2) {
      LDAPModification var3 = new LDAPModification(var1, var2);
      this.modifications.addElement(var3);
   }

   public synchronized void remove(String var1) {
      for(int var2 = 0; var2 < this.modifications.size(); ++var2) {
         LDAPModification var3 = (LDAPModification)this.modifications.elementAt(var2);
         LDAPAttribute var4 = var3.getAttribute();
         if (var1.equalsIgnoreCase(var4.getName())) {
            this.modifications.removeElementAt(var2);
            break;
         }
      }

   }

   public String toString() {
      String var1 = "LDAPModificationSet: {";

      for(int var2 = 0; var2 < this.modifications.size(); ++var2) {
         var1 = var1 + (LDAPModification)this.modifications.elementAt(var2);
         if (var2 < this.modifications.size() - 1) {
            var1 = var1 + ", ";
         }
      }

      var1 = var1 + "}";
      return var1;
   }
}
