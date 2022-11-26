package netscape.ldap.controls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSortKey;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEREnumerated;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;
import netscape.ldap.client.JDAPBERTagDecoder;

public class LDAPSortControl extends LDAPControl {
   public static final String SORTREQUEST = "1.2.840.113556.1.4.473";
   public static final String SORTRESPONSE = "1.2.840.113556.1.4.474";
   private String m_failedAttribute = null;
   private int m_resultCode = 0;
   private LDAPSortKey[] m_keys;

   public LDAPSortControl(String var1, boolean var2, byte[] var3) throws LDAPException, IOException {
      super(var1, var2, var3);
      if (!var1.equals("1.2.840.113556.1.4.474")) {
         throw new LDAPException("oid must be LDAPSortControl.SORTRESPONSE", 89);
      } else {
         ByteArrayInputStream var4 = new ByteArrayInputStream(var3);
         new BERSequence();
         JDAPBERTagDecoder var6 = new JDAPBERTagDecoder();
         int[] var7 = new int[]{0};
         BERSequence var8 = (BERSequence)BERElement.getElement(var6, var4, var7);
         this.m_resultCode = ((BEREnumerated)var8.elementAt(0)).getValue();
         if (var8.size() != 1) {
            BEROctetString var9 = (BEROctetString)var8.elementAt(1);

            try {
               this.m_failedAttribute = new String(var9.getValue(), "UTF8");
            } catch (UnsupportedEncodingException var11) {
            }

         }
      }
   }

   public String getFailedAttribute() {
      return this.m_failedAttribute;
   }

   public int getResultCode() {
      return this.m_resultCode;
   }

   public LDAPSortControl(LDAPSortKey var1, boolean var2) {
      super("1.2.840.113556.1.4.473", var2, (byte[])null);
      LDAPSortKey[] var3 = new LDAPSortKey[]{var1};
      this.m_value = this.createSortSpecification(this.m_keys = var3);
   }

   public LDAPSortControl(LDAPSortKey[] var1, boolean var2) {
      super("1.2.840.113556.1.4.473", var2, (byte[])null);
      this.m_value = this.createSortSpecification(this.m_keys = var1);
   }

   /** @deprecated */
   public static String parseResponse(LDAPControl[] var0, int[] var1) {
      String var2 = null;
      LDAPControl var3 = null;

      for(int var4 = 0; var0 != null && var4 < var0.length; ++var4) {
         if (var0[var4].getID().equals("1.2.840.113556.1.4.474")) {
            var3 = var0[var4];
            break;
         }
      }

      if (var3 != null) {
         ByteArrayInputStream var12 = new ByteArrayInputStream(var3.getValue());
         new BERSequence();
         JDAPBERTagDecoder var6 = new JDAPBERTagDecoder();
         int[] var7 = new int[]{0};

         try {
            BERSequence var8 = (BERSequence)BERElement.getElement(var6, var12, var7);
            int var9 = ((BEREnumerated)var8.elementAt(0)).getValue();
            if (var1 != null && var1.length > 0) {
               var1[0] = var9;
            }

            BEROctetString var10 = (BEROctetString)var8.elementAt(1);
            var2 = new String(var10.getValue(), "UTF8");
         } catch (Exception var11) {
         }
      }

      return var2;
   }

   private byte[] createSortSpecification(LDAPSortKey[] var1) {
      BERSequence var2 = new BERSequence();

      for(int var3 = 0; var3 < var1.length; ++var3) {
         BERSequence var4 = new BERSequence();
         var4.addElement(new BEROctetString(var1[var3].getKey()));
         if (var1[var3].getMatchRule() != null) {
            var4.addElement(new BERTag(128, new BEROctetString(var1[var3].getMatchRule()), true));
         }

         if (var1[var3].getReverse()) {
            var4.addElement(new BERTag(129, new BEREnumerated(129), true));
         }

         var2.addElement(var4);
      }

      return this.flattenBER(var2);
   }

   public String toString() {
      return this.getID() == "1.2.840.113556.1.4.473" ? this.reqToString() : this.rspToString();
   }

   String reqToString() {
      StringBuffer var1 = new StringBuffer("{SortCtrl:");
      var1.append(" isCritical=");
      var1.append(this.isCritical());
      var1.append(" ");

      for(int var2 = 0; var2 < this.m_keys.length; ++var2) {
         var1.append(this.m_keys[var2]);
      }

      var1.append("}");
      return var1.toString();
   }

   String rspToString() {
      StringBuffer var1 = new StringBuffer("{SortResponseCtrl:");
      var1.append(" isCritical=");
      var1.append(this.isCritical());
      if (this.m_failedAttribute != null) {
         var1.append(" failedAttr=");
         var1.append(this.m_failedAttribute);
      }

      var1.append(" resultCode=");
      var1.append(this.m_resultCode);
      var1.append("}");
      return var1.toString();
   }
}
