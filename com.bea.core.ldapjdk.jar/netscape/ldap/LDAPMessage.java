package netscape.ldap;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERInteger;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;
import netscape.ldap.client.opers.JDAPAddResponse;
import netscape.ldap.client.opers.JDAPBindResponse;
import netscape.ldap.client.opers.JDAPCompareResponse;
import netscape.ldap.client.opers.JDAPDeleteResponse;
import netscape.ldap.client.opers.JDAPExtendedResponse;
import netscape.ldap.client.opers.JDAPModifyRDNResponse;
import netscape.ldap.client.opers.JDAPModifyResponse;
import netscape.ldap.client.opers.JDAPProtocolOp;
import netscape.ldap.client.opers.JDAPSearchResponse;
import netscape.ldap.client.opers.JDAPSearchResult;
import netscape.ldap.client.opers.JDAPSearchResultReference;

public class LDAPMessage implements Serializable {
   static final long serialVersionUID = -1364094245850026720L;
   public static final int BIND_REQUEST = 0;
   public static final int BIND_RESPONSE = 1;
   public static final int UNBIND_REQUEST = 2;
   public static final int SEARCH_REQUEST = 3;
   public static final int SEARCH_RESPONSE = 4;
   public static final int SEARCH_RESULT = 5;
   public static final int MODIFY_REQUEST = 6;
   public static final int MODIFY_RESPONSE = 7;
   public static final int ADD_REQUEST = 8;
   public static final int ADD_RESPONSE = 9;
   public static final int DEL_REQUEST = 10;
   public static final int DEL_RESPONSE = 11;
   public static final int MODIFY_RDN_REQUEST = 12;
   public static final int MODIFY_RDN_RESPONSE = 13;
   public static final int COMPARE_REQUEST = 14;
   public static final int COMPARE_RESPONSE = 15;
   public static final int ABANDON_REQUEST = 16;
   public static final int SEARCH_RESULT_REFERENCE = 19;
   public static final int EXTENDED_REQUEST = 23;
   public static final int EXTENDED_RESPONSE = 24;
   private int m_msgid;
   private JDAPProtocolOp m_protocolOp = null;
   private LDAPControl[] m_controls = null;

   LDAPMessage(int var1, JDAPProtocolOp var2) {
      this.m_msgid = var1;
      this.m_protocolOp = var2;
   }

   LDAPMessage(int var1, JDAPProtocolOp var2, LDAPControl[] var3) {
      this.m_msgid = var1;
      this.m_protocolOp = var2;
      this.m_controls = var3;
   }

   static LDAPMessage parseMessage(BERElement var0) throws IOException {
      Object var2 = null;
      LDAPControl[] var3 = null;
      if (var0.getType() != 48) {
         throw new IOException("SEQUENCE in jdap message expected");
      } else {
         BERSequence var4 = (BERSequence)var0;
         BERInteger var5 = (BERInteger)var4.elementAt(0);
         int var1 = var5.getValue();
         BERElement var6 = var4.elementAt(1);
         if (var6.getType() != -1) {
            throw new IOException("TAG in protocol operation is expected");
         } else {
            BERTag var7 = (BERTag)var6;
            switch (var7.getTag() & 31) {
               case 1:
                  var2 = new JDAPBindResponse(var6);
                  break;
               case 2:
               case 6:
               case 8:
               case 10:
               case 12:
               case 14:
               case 16:
               case 17:
               case 18:
               case 20:
               case 21:
               case 22:
               case 23:
               default:
                  throw new IOException("Unknown protocol operation");
               case 3:
               case 5:
                  var2 = new JDAPSearchResult(var6);
                  break;
               case 4:
                  var2 = new JDAPSearchResponse(var6);
                  break;
               case 7:
                  var2 = new JDAPModifyResponse(var6);
                  break;
               case 9:
                  var2 = new JDAPAddResponse(var6);
                  break;
               case 11:
                  var2 = new JDAPDeleteResponse(var6);
                  break;
               case 13:
                  var2 = new JDAPModifyRDNResponse(var6);
                  break;
               case 15:
                  var2 = new JDAPCompareResponse(var6);
                  break;
               case 19:
                  var2 = new JDAPSearchResultReference(var6);
                  break;
               case 24:
                  var2 = new JDAPExtendedResponse(var6);
            }

            if (var4.size() >= 3) {
               var7 = (BERTag)var4.elementAt(2);
               if (var7.getTag() == 160) {
                  BERSequence var8 = (BERSequence)var7.getValue();
                  var3 = new LDAPControl[var8.size()];

                  for(int var9 = 0; var9 < var8.size(); ++var9) {
                     var3[var9] = LDAPControl.parseControl(var8.elementAt(var9));
                  }
               }
            }

            if (var2 instanceof JDAPSearchResponse) {
               return new LDAPSearchResult(var1, (JDAPSearchResponse)var2, var3);
            } else if (var2 instanceof JDAPSearchResultReference) {
               return new LDAPSearchResultReference(var1, (JDAPSearchResultReference)var2, var3);
            } else {
               return (LDAPMessage)(var2 instanceof JDAPExtendedResponse ? new LDAPExtendedResponse(var1, (JDAPExtendedResponse)var2, var3) : new LDAPResponse(var1, (JDAPProtocolOp)var2, var3));
            }
         }
      }
   }

   public int getMessageID() {
      return this.m_msgid;
   }

   public int getType() {
      return this.m_protocolOp.getType();
   }

   JDAPProtocolOp getProtocolOp() {
      return this.m_protocolOp;
   }

   public LDAPControl[] getControls() {
      return this.m_controls;
   }

   void write(OutputStream var1) throws IOException {
      BERSequence var2 = new BERSequence();
      BERInteger var3 = new BERInteger(this.m_msgid);
      var2.addElement(var3);
      BERElement var4 = this.m_protocolOp.getBERElement();
      if (var4 == null) {
         throw new IOException("Bad BER element");
      } else {
         var2.addElement(var4);
         if (this.m_controls != null) {
            BERSequence var5 = new BERSequence();

            for(int var6 = 0; var6 < this.m_controls.length; ++var6) {
               var5.addElement(this.m_controls[var6].getBERElement());
            }

            BERTag var7 = new BERTag(160, var5, true);
            var2.addElement(var7);
         }

         var2.write(var1);
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("[LDAPMessage] ");
      var1.append(this.m_msgid);
      var1.append(" ");
      var1.append(this.m_protocolOp.toString());

      for(int var2 = 0; this.m_controls != null && var2 < this.m_controls.length; ++var2) {
         var1.append(" ");
         var1.append(this.m_controls[var2].toString());
      }

      return var1.toString();
   }

   StringBuffer toTraceString() {
      StringBuffer var1 = new StringBuffer(" op=");
      var1.append(this.m_msgid);
      var1.append(" ");
      var1.append(this.m_protocolOp.toString());

      for(int var2 = 0; this.m_controls != null && var2 < this.m_controls.length; ++var2) {
         var1.append(" ");
         var1.append(this.m_controls[var2].toString());
      }

      return var1;
   }
}
