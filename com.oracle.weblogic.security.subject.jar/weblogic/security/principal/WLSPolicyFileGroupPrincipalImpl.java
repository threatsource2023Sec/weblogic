package weblogic.security.principal;

public class WLSPolicyFileGroupPrincipalImpl extends WLSAbstractPolicyFilePrincipal {
   public WLSPolicyFileGroupPrincipalImpl(String groupName) {
      super(groupName);
      this.setMatchGroup(true);
   }
}
