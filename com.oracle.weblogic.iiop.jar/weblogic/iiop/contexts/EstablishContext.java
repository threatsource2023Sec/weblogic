package weblogic.iiop.contexts;

import weblogic.corba.cos.security.GSSUtil;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.security.auth.login.PasswordCredential;

public class EstablishContext extends ContextBody {
   private long clientContextId;
   private IdentityToken identityToken;
   private byte[] clientAuthenticationToken;

   public EstablishContext() {
   }

   public EstablishContext(long id, byte[] clientAuthToken, IdentityToken clientIdentityToken) {
      this.clientContextId = id;
      this.identityToken = clientIdentityToken;
      this.clientAuthenticationToken = clientAuthToken;
   }

   protected EstablishContext(CorbaInputStream in) {
      this.clientContextId = in.read_longlong();
      this.skipAuthorizationToken(in);
      this.identityToken = new IdentityToken(in);
      this.clientAuthenticationToken = in.read_octet_sequence();
   }

   public static EstablishContext createWithAbsentIdentity(long clientContextId) {
      return new EstablishContext(clientContextId, (byte[])null, (IdentityToken)null);
   }

   public static EstablishContext createForUserPassword(long clientContextId, PasswordCredential pc, String targetName) {
      GSSUPImpl gssup = new GSSUPImpl(pc.getUsername(), targetName, pc.getPassword(), targetName);
      return new EstablishContext(clientContextId, gssup.getBytes(), (IdentityToken)null);
   }

   public static EstablishContext createForPrincipalIdentity(long clientContextId, String principal, String targetName) {
      byte[] principalName = GSSUtil.createGSSUPGSSNTExportedName(getPrincipalString(principal, targetName));
      return new EstablishContext(clientContextId, (byte[])null, new IdentityToken(2, true, principalName));
   }

   public static EstablishContext createForAnonymousIdentity(long clientContextId) {
      return new EstablishContext(clientContextId, (byte[])null, new IdentityToken(1, true, (byte[])null));
   }

   private static String getPrincipalString(String userName, String targetName) {
      return targetName == null ? userName : userName + "@" + targetName;
   }

   public long getClientContextId() {
      return this.clientContextId;
   }

   public IdentityToken getIdentityToken() {
      return this.identityToken;
   }

   public byte[] getClientAuthenticationToken() {
      return this.clientAuthenticationToken;
   }

   public void write(CorbaOutputStream out) {
      out.write_longlong(this.clientContextId);
      out.write_long(0);
      if (this.identityToken == null) {
         out.write_long(0);
         out.write_boolean(true);
      } else {
         this.identityToken.write(out);
      }

      out.write_octet_sequence(this.clientAuthenticationToken);
   }

   private void skipAuthorizationToken(CorbaInputStream in) {
      int len = in.read_long();

      for(int idx = 0; idx < len; ++idx) {
         in.read_long();
         in.read_octet_sequence();
      }

   }

   public String toString() {
      return "EstablishContext (clientContext = " + this.clientContextId + "\n   identityToken = " + this.identityToken + ")";
   }
}
