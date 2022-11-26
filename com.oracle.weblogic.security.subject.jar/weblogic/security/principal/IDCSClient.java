package weblogic.security.principal;

public interface IDCSClient extends OAuthClient, IdentityDomainPrincipal {
   String getName();
}
