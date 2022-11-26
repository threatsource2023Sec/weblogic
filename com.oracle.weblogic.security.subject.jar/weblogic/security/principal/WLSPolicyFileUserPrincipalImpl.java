package weblogic.security.principal;

public class WLSPolicyFileUserPrincipalImpl extends WLSAbstractPolicyFilePrincipal {
   public WLSPolicyFileUserPrincipalImpl(String userName) {
      super(userName);
      this.setMatchUser(true);
   }
}
