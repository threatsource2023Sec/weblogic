package netscape.ldap.controls;

import java.io.ByteArrayInputStream;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEREnumerated;
import netscape.ldap.ber.stream.BERInteger;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.client.JDAPBERTagDecoder;

public class LDAPVirtualListResponse extends LDAPControl {
   public static final String VIRTUALLISTRESPONSE = "2.16.840.1.113730.3.4.10";
   private int m_firstPosition = 0;
   private int m_contentCount = 0;
   private int m_resultCode = -1;
   private String m_context = null;

   LDAPVirtualListResponse() {
      super("2.16.840.1.113730.3.4.10", true, (byte[])null);
   }

   public LDAPVirtualListResponse(String var1, boolean var2, byte[] var3) throws LDAPException {
      super("2.16.840.1.113730.3.4.10", var2, var3);
      if (!var1.equals("2.16.840.1.113730.3.4.10")) {
         throw new LDAPException("oid must be LDAPVirtualListResponse.VIRTUALLISTRESPONSE", 89);
      } else {
         this.parseResponse();
      }
   }

   public LDAPVirtualListResponse(byte[] var1) {
      super("2.16.840.1.113730.3.4.10", true, (byte[])null);
      this.m_value = var1;
      this.parseResponse();
   }

   public int getContentCount() {
      return this.m_contentCount;
   }

   public int getFirstPosition() {
      return this.m_firstPosition;
   }

   public int getResultCode() {
      return this.m_resultCode;
   }

   public String getContext() {
      return this.m_context;
   }

   private void parseResponse() {
      ByteArrayInputStream var1 = new ByteArrayInputStream(this.getValue());
      new BERSequence();
      JDAPBERTagDecoder var3 = new JDAPBERTagDecoder();
      int[] var4 = new int[]{0};

      try {
         BERSequence var5 = (BERSequence)BERElement.getElement(var3, var1, var4);
         this.m_firstPosition = ((BERInteger)var5.elementAt(0)).getValue();
         this.m_contentCount = ((BERInteger)var5.elementAt(1)).getValue();
         this.m_resultCode = ((BEREnumerated)var5.elementAt(2)).getValue();
         if (var5.size() > 3) {
            BEROctetString var6 = (BEROctetString)var5.elementAt(3);
            this.m_context = new String(var6.getValue(), "UTF8");
         }
      } catch (Exception var7) {
         this.m_firstPosition = this.m_contentCount = this.m_resultCode = -1;
         this.m_context = null;
      }

   }

   /** @deprecated */
   public static LDAPVirtualListResponse parseResponse(LDAPControl[] var0) {
      LDAPVirtualListResponse var1 = null;

      for(int var2 = 0; var0 != null && var2 < var0.length; ++var2) {
         if (var0[var2].getID().equals("2.16.840.1.113730.3.4.10")) {
            var1 = new LDAPVirtualListResponse(var0[var2].getValue());
            var1.parseResponse();
            break;
         }
      }

      if (var1 != null) {
         var1.parseResponse();
      }

      return var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("{VirtListResponseCtrl:");
      var1.append(" isCritical=");
      var1.append(this.isCritical());
      var1.append(" firstPosition=");
      var1.append(this.m_firstPosition);
      var1.append(" contentCount=");
      var1.append(this.m_contentCount);
      var1.append(" resultCode=");
      var1.append(this.m_resultCode);
      if (this.m_context != null) {
         var1.append(" conext=");
         var1.append(this.m_context);
      }

      var1.append("}");
      return var1.toString();
   }
}
