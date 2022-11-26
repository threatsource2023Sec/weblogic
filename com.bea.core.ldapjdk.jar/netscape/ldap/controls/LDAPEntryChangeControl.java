package netscape.ldap.controls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEREnumerated;
import netscape.ldap.ber.stream.BERInteger;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.client.JDAPBERTagDecoder;

public class LDAPEntryChangeControl extends LDAPControl {
   private int m_changeNumber = -1;
   private int m_changeTypes = -1;
   private String m_previousDN = null;
   public static final String ENTRYCHANGED = "2.16.840.1.113730.3.4.7";

   public LDAPEntryChangeControl() {
      super("2.16.840.1.113730.3.4.7", false, (byte[])null);
   }

   public LDAPEntryChangeControl(String var1, boolean var2, byte[] var3) throws LDAPException, IOException {
      super("2.16.840.1.113730.3.4.7", false, var3);
      if (!var1.equals("2.16.840.1.113730.3.4.7")) {
         throw new LDAPException("oid must be LDAPEntryChangeControl.ENTRYCHANGED", 89);
      } else {
         ByteArrayInputStream var4 = new ByteArrayInputStream(this.m_value);
         new BERSequence();
         JDAPBERTagDecoder var6 = new JDAPBERTagDecoder();
         int[] var7 = new int[]{0};
         BERSequence var8 = (BERSequence)BERElement.getElement(var6, var4, var7);
         BEREnumerated var9 = (BEREnumerated)var8.elementAt(0);
         this.m_changeTypes = var9.getValue();
         BERInteger var13;
         if (var8.size() > 1) {
            if (var8.elementAt(1) instanceof BEROctetString) {
               BEROctetString var10 = (BEROctetString)var8.elementAt(1);

               try {
                  this.m_previousDN = new String(var10.getValue(), "UTF8");
               } catch (UnsupportedEncodingException var12) {
               }
            } else if (var8.elementAt(1) instanceof BERInteger) {
               var13 = (BERInteger)var8.elementAt(1);
               this.m_changeNumber = var13.getValue();
            }
         }

         if (var8.size() > 2) {
            var13 = (BERInteger)var8.elementAt(2);
            this.m_changeNumber = var13.getValue();
         }

      }
   }

   public void setChangeNumber(int var1) {
      this.m_changeNumber = var1;
   }

   public void setChangeType(int var1) {
      this.m_changeTypes = var1;
   }

   public void setPreviousDN(String var1) {
      this.m_previousDN = var1;
   }

   public int getChangeNumber() {
      return this.m_changeNumber;
   }

   public int getChangeType() {
      return this.m_changeTypes;
   }

   public String getPreviousDN() {
      return this.m_previousDN;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("{EntryChangedCtrl:");
      var1.append(" isCritical=");
      var1.append(this.isCritical());
      var1.append(" changeTypes=");
      var1.append(LDAPPersistSearchControl.typesToString(this.m_changeTypes));
      var1.append(" previousDN=");
      var1.append(this.m_previousDN);
      var1.append(" changeNumber=");
      var1.append(this.m_changeNumber);
      var1.append("}");
      return var1.toString();
   }
}
