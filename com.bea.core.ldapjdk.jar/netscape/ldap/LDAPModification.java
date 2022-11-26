package netscape.ldap;

import java.io.Serializable;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEREnumerated;
import netscape.ldap.ber.stream.BERSequence;

public class LDAPModification implements Serializable {
   static final long serialVersionUID = 4836472112866826595L;
   public static final int ADD = 0;
   public static final int DELETE = 1;
   public static final int REPLACE = 2;
   private int operation;
   private LDAPAttribute attribute;

   public LDAPModification(int var1, LDAPAttribute var2) {
      this.operation = var1;
      this.attribute = var2;
   }

   public int getOp() {
      return this.operation;
   }

   public LDAPAttribute getAttribute() {
      return this.attribute;
   }

   public BERElement getBERElement() {
      BERSequence var1 = new BERSequence();
      var1.addElement(new BEREnumerated(this.operation));
      var1.addElement(this.attribute.getBERElement());
      return var1;
   }

   public String toString() {
      String var1 = "LDAPModification: ";
      if (this.operation == 0) {
         var1 = var1 + "ADD, ";
      } else if (this.operation == 1) {
         var1 = var1 + "DELETE, ";
      } else if (this.operation == 2) {
         var1 = var1 + "REPLACE, ";
      } else {
         var1 = var1 + "INVALID OP, ";
      }

      var1 = var1 + this.attribute;
      return var1;
   }
}
