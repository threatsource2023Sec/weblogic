package weblogic.iiop.contexts;

import java.io.UnsupportedEncodingException;
import weblogic.corba.cos.security.GSSUtil;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;

public class GSSUPImpl {
   private String userName;
   private String userNameScope;
   private String password;
   private String targetName;

   public GSSUPImpl(byte[] clientAuthToken) throws GSSUPDecodeException {
      byte[] innerToken = GSSUtil.getGSSUPInnerToken(clientAuthToken);
      if (innerToken == null) {
         throw new GSSUPDecodeException("Invalid or unsupported GSS Token");
      } else {
         CorbaInputStream in = IiopProtocolFacade.createInputStream(innerToken);
         in.consumeEndian();
         byte[] userUTF8 = in.read_octet_sequence();
         byte[] passUTF8 = in.read_octet_sequence();
         byte[] target = in.read_octet_sequence();

         try {
            this.userName = new String(userUTF8, "UTF8");
            int idx = this.userName.lastIndexOf(64);
            if (idx == 0) {
               this.userName = "";
               this.userNameScope = this.userName.substring(idx + 1);
            } else if (idx > 0) {
               if (this.userName.charAt(idx - 1) != '\\') {
                  this.userNameScope = this.userName.substring(idx + 1);
                  this.userName = GSSUtil.getUnquotedGSSUserName(this.userName.substring(0, idx));
               } else {
                  this.userName = GSSUtil.getUnquotedGSSUserName(this.userName);
               }
            }

            if (passUTF8 != null && passUTF8.length > 0) {
               this.password = new String(passUTF8, "UTF8");
            } else {
               this.password = "";
            }
         } catch (UnsupportedEncodingException var8) {
            throw new GSSUPDecodeException("Error decoding UTF8 user and password", var8);
         }

         this.targetName = GSSUtil.extractGSSUPGSSNTExportedName(target);
      }
   }

   GSSUPImpl(String userName, String userNameScope, String password, String targetName) {
      this.userName = GSSUtil.getQuotedGSSUserName(userName);
      this.userNameScope = userNameScope;
      this.password = password;
      this.targetName = targetName;
   }

   public String getUserName() {
      return this.userName;
   }

   public String getUserNameScope() {
      return this.userNameScope;
   }

   public String getPassword() {
      return this.password;
   }

   public char[] getPasswordChars() {
      return this.password.toCharArray();
   }

   public String getTargetName() {
      return this.targetName;
   }

   public byte[] getBytes() {
      try {
         String name = this.userName;
         if (this.userNameScope != null) {
            name = name + "@" + this.userNameScope;
         }

         byte[] userUTF8 = name.getBytes("UTF8");
         byte[] passUTF8 = this.password.getBytes("UTF8");
         byte[] targetExported = GSSUtil.createGSSUPGSSNTExportedName(this.targetName);
         CorbaOutputStream out = IiopProtocolFacade.createOutputStream();
         out.putEndian();
         out.write_octet_sequence(userUTF8);
         out.write_octet_sequence(passUTF8);
         out.write_octet_sequence(targetExported);
         byte[] innerToken = out.getBuffer();
         return GSSUtil.getGSSUPToken(innerToken);
      } catch (UnsupportedEncodingException var7) {
         return null;
      }
   }

   public String toString() {
      return "GSSUPImpl (user= " + this.userName + ", scope=" + this.userNameScope + ", target= " + this.targetName + ")";
   }
}
