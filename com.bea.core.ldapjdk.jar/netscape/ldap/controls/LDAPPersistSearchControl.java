package netscape.ldap.controls;

import java.io.ByteArrayInputStream;
import netscape.ldap.LDAPControl;
import netscape.ldap.ber.stream.BERBoolean;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEREnumerated;
import netscape.ldap.ber.stream.BERInteger;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.client.JDAPBERTagDecoder;

public class LDAPPersistSearchControl extends LDAPControl {
   private int m_changeTypes = 1;
   private boolean m_changesOnly = false;
   private boolean m_returnECs = false;
   public static final int ADD = 1;
   public static final int DELETE = 2;
   public static final int MODIFY = 4;
   public static final int MODDN = 8;
   public static final String PERSISTENTSEARCH = "2.16.840.1.113730.3.4.3";

   public LDAPPersistSearchControl() {
      super("2.16.840.1.113730.3.4.3", true, (byte[])null);
   }

   public LDAPPersistSearchControl(int var1, boolean var2, boolean var3, boolean var4) {
      super("2.16.840.1.113730.3.4.3", var4, (byte[])null);
      this.m_value = this.createPersistSearchSpecification(var1, var2, var3);
      this.m_changeTypes = var1;
      this.m_changesOnly = var2;
      this.m_returnECs = var3;
   }

   public int getChangeTypes() {
      return this.m_changeTypes;
   }

   public boolean getChangesOnly() {
      return this.m_changesOnly;
   }

   public boolean getReturnControls() {
      return this.m_returnECs;
   }

   public void setChangeTypes(int var1) {
      this.m_changeTypes = var1;
   }

   public void setChangesOnly(boolean var1) {
      this.m_changesOnly = var1;
   }

   public void setReturnControls(boolean var1) {
      this.m_returnECs = var1;
   }

   /** @deprecated */
   public LDAPEntryChangeControl parseResponse(byte[] var1) {
      LDAPEntryChangeControl var2 = new LDAPEntryChangeControl();
      ByteArrayInputStream var3 = new ByteArrayInputStream(var1);
      new BERSequence();
      JDAPBERTagDecoder var5 = new JDAPBERTagDecoder();
      int[] var6 = new int[]{0};

      try {
         BERSequence var7 = (BERSequence)BERElement.getElement(var5, var3, var6);
         BEREnumerated var8 = (BEREnumerated)var7.elementAt(0);
         var2.setChangeType(var8.getValue());
         BERInteger var11;
         if (var7.size() > 1) {
            if (var7.elementAt(1) instanceof BEROctetString) {
               BEROctetString var9 = (BEROctetString)var7.elementAt(1);
               var2.setPreviousDN(new String(var9.getValue(), "UTF8"));
            } else if (var7.elementAt(1) instanceof BERInteger) {
               var11 = (BERInteger)var7.elementAt(1);
               var2.setChangeNumber(var11.getValue());
            }
         }

         if (var7.size() > 2) {
            var11 = (BERInteger)var7.elementAt(2);
            var2.setChangeNumber(var11.getValue());
         }

         return var2;
      } catch (Exception var10) {
         return null;
      }
   }

   /** @deprecated */
   public static LDAPEntryChangeControl parseResponse(LDAPControl[] var0) {
      LDAPPersistSearchControl var1 = new LDAPPersistSearchControl();

      for(int var2 = 0; var0 != null && var2 < var0.length; ++var2) {
         if (var0[var2].getID().equals("2.16.840.1.113730.3.4.7")) {
            return var1.parseResponse(var0[var2].getValue());
         }
      }

      return null;
   }

   private byte[] createPersistSearchSpecification(int var1, boolean var2, boolean var3) {
      BERSequence var4 = new BERSequence();
      var4.addElement(new BERInteger(var1));
      var4.addElement(new BERBoolean(var2));
      var4.addElement(new BERBoolean(var3));
      return this.flattenBER(var4);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("{PersistSearchCtrl:");
      var1.append(" isCritical=");
      var1.append(this.isCritical());
      var1.append(" returnEntryChangeCtrls=");
      var1.append(this.m_returnECs);
      var1.append(" changesOnly=");
      var1.append(this.m_changesOnly);
      var1.append(" changeTypes=");
      var1.append(typesToString(this.m_changeTypes));
      var1.append("}");
      return var1.toString();
   }

   static String typesToString(int var0) {
      String var1 = "";
      if ((var0 & 1) != 0) {
         var1 = var1 + (var1.length() > 0 ? "+ADD" : "ADD");
      }

      if ((var0 & 2) != 0) {
         var1 = var1 + (var1.length() > 0 ? "+DEL" : "DEL");
      }

      if ((var0 & 4) != 0) {
         var1 = var1 + (var1.length() > 0 ? "+MOD" : "MOD");
      }

      if ((var0 & 8) != 0) {
         var1 = var1 + (var1.length() > 0 ? "+MODDN" : "MODDN");
      }

      return var1;
   }
}
