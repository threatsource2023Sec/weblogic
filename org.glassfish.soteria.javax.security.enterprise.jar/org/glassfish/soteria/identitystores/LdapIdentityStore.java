package org.glassfish.soteria.identitystores;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InvalidSearchControlsException;
import javax.naming.directory.InvalidSearchFilterException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.IdentityStorePermission;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;
import javax.security.enterprise.identitystore.IdentityStore.ValidationType;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition.LdapSearchScope;

public class LdapIdentityStore implements IdentityStore {
   private static final String DEFAULT_USER_FILTER = "(&(%s=%s)(|(objectclass=user)(objectclass=person)(objectclass=inetOrgPerson)(objectclass=organizationalPerson))(!(objectclass=computer)))";
   private static final String DEFAULT_GROUP_FILTER = "(&(%s=%s)(|(objectclass=group)(objectclass=groupofnames)(objectclass=groupofuniquenames)))";
   private static final Logger LOGGER = Logger.getLogger("LDAP_IDSTORE_DEBUG");
   private final LdapIdentityStoreDefinition ldapIdentityStoreDefinition;
   private final Set validationTypes;

   private static void debug(String method, String message, Throwable thrown) {
      if (thrown != null) {
         LOGGER.logp(Level.FINEST, LdapIdentityStore.class.getName(), method, message, thrown);
      } else {
         LOGGER.logp(Level.FINEST, LdapIdentityStore.class.getName(), method, message);
      }

   }

   public LdapIdentityStore(LdapIdentityStoreDefinition ldapIdentityStoreDefinition) {
      this.ldapIdentityStoreDefinition = ldapIdentityStoreDefinition;
      this.validationTypes = Collections.unmodifiableSet(new HashSet(Arrays.asList(ldapIdentityStoreDefinition.useFor())));
   }

   public CredentialValidationResult validate(Credential credential) {
      return credential instanceof UsernamePasswordCredential ? this.validate((UsernamePasswordCredential)credential) : CredentialValidationResult.NOT_VALIDATED_RESULT;
   }

   public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {
      LdapContext searchContext = this.createSearchLdapContext();

      CredentialValidationResult var4;
      try {
         String callerDn = this.getCallerDn(searchContext, usernamePasswordCredential.getCaller());
         var4 = this.validateCallerAndGetGroups(searchContext, callerDn, usernamePasswordCredential);
      } finally {
         closeContext(searchContext);
      }

      return var4;
   }

   private String getCallerDn(LdapContext searchContext, String callerName) {
      String callerDn = null;
      if (!this.ldapIdentityStoreDefinition.callerBaseDn().isEmpty() && this.ldapIdentityStoreDefinition.callerSearchBase().isEmpty()) {
         callerDn = String.format("%s=%s,%s", this.ldapIdentityStoreDefinition.callerNameAttribute(), callerName, this.ldapIdentityStoreDefinition.callerBaseDn());
      } else {
         callerDn = this.searchCaller(searchContext, callerName);
      }

      return callerDn;
   }

   private CredentialValidationResult validateCallerAndGetGroups(LdapContext searchContext, String callerDn, UsernamePasswordCredential usernamePasswordCredential) {
      if (callerDn == null) {
         return CredentialValidationResult.INVALID_RESULT;
      } else {
         LdapContext callerContext = this.createCallerLdapContext(callerDn, new String(usernamePasswordCredential.getPassword().getValue()));
         if (callerContext == null) {
            return CredentialValidationResult.INVALID_RESULT;
         } else {
            closeContext(callerContext);
            Set groups = null;
            if (this.validationTypes().contains(ValidationType.PROVIDE_GROUPS)) {
               groups = this.retrieveGroupsForCallerDn(searchContext, callerDn);
            }

            return new CredentialValidationResult((String)null, usernamePasswordCredential.getCaller(), callerDn, (String)null, groups);
         }
      }
   }

   public Set getCallerGroups(CredentialValidationResult validationResult) {
      SecurityManager securityManager = System.getSecurityManager();
      if (securityManager != null) {
         securityManager.checkPermission(new IdentityStorePermission("getGroups"));
      }

      LdapContext searchContext = this.createSearchLdapContext();

      Set var5;
      try {
         String callerDn = validationResult.getCallerDn();
         if (callerDn == null || callerDn.isEmpty()) {
            callerDn = this.getCallerDn(searchContext, validationResult.getCallerPrincipal().getName());
         }

         var5 = this.retrieveGroupsForCallerDn(searchContext, callerDn);
      } finally {
         closeContext(searchContext);
      }

      return var5;
   }

   private Set retrieveGroupsForCallerDn(LdapContext searchContext, String callerDn) {
      if (callerDn != null && !callerDn.isEmpty()) {
         return this.ldapIdentityStoreDefinition.groupSearchBase().isEmpty() && !this.ldapIdentityStoreDefinition.groupMemberOfAttribute().isEmpty() ? this.retrieveGroupsFromCallerObject(callerDn, searchContext) : this.retrieveGroupsBySearching(callerDn, searchContext);
      } else {
         return Collections.emptySet();
      }
   }

   private Set retrieveGroupsBySearching(String callerDn, LdapContext searchContext) {
      List searchResults = this.searchGroups(searchContext, callerDn);
      Set groups = new HashSet();

      try {
         Iterator var5 = searchResults.iterator();

         while(true) {
            Attribute attribute;
            do {
               if (!var5.hasNext()) {
                  return groups;
               }

               SearchResult searchResult = (SearchResult)var5.next();
               attribute = searchResult.getAttributes().get(this.ldapIdentityStoreDefinition.groupNameAttribute());
            } while(attribute == null);

            Iterator var8 = Collections.list(attribute.getAll()).iterator();

            while(var8.hasNext()) {
               Object group = var8.next();
               if (group != null) {
                  groups.add(group.toString());
               }
            }
         }
      } catch (NamingException var10) {
         throw new IdentityStoreRuntimeException(var10);
      }
   }

   private Set retrieveGroupsFromCallerObject(String callerDn, LdapContext searchContext) {
      try {
         Attributes attributes = searchContext.getAttributes(callerDn, new String[]{this.ldapIdentityStoreDefinition.groupMemberOfAttribute()});
         Attribute memberOfAttribute = attributes.get(this.ldapIdentityStoreDefinition.groupMemberOfAttribute());
         Set groups = new HashSet();
         if (memberOfAttribute != null) {
            Iterator var6 = Collections.list(memberOfAttribute.getAll()).iterator();

            while(var6.hasNext()) {
               Object group = var6.next();
               if (group != null) {
                  String groupName = getGroupNameFromDn(group.toString(), this.ldapIdentityStoreDefinition.groupNameAttribute());
                  if (groupName != null) {
                     groups.add(groupName);
                  }
               }
            }
         }

         return groups;
      } catch (NamingException var9) {
         throw new IdentityStoreRuntimeException(var9);
      }
   }

   private static String getGroupNameFromDn(String dnString, String groupNameAttribute) throws NamingException {
      LdapName dn = new LdapName(dnString);
      Attribute attribute = dn.getRdn(dn.size() - 1).toAttributes().get(groupNameAttribute);
      if (attribute == null) {
         throw new IdentityStoreConfigurationException("Group name attribute '" + groupNameAttribute + "' not found for DN: " + dnString);
      } else {
         return attribute.get(0).toString();
      }
   }

   private String searchCaller(LdapContext searchContext, String callerName) {
      String filter = null;
      if (this.ldapIdentityStoreDefinition.callerSearchFilter() != null && !this.ldapIdentityStoreDefinition.callerSearchFilter().trim().isEmpty()) {
         filter = String.format(this.ldapIdentityStoreDefinition.callerSearchFilter(), callerName);
      } else {
         filter = String.format("(&(%s=%s)(|(objectclass=user)(objectclass=person)(objectclass=inetOrgPerson)(objectclass=organizationalPerson))(!(objectclass=computer)))", this.ldapIdentityStoreDefinition.callerNameAttribute(), callerName);
      }

      List callerDn = search(searchContext, this.ldapIdentityStoreDefinition.callerSearchBase(), filter, this.getCallerSearchControls());
      if (callerDn.size() > 1) {
      }

      return callerDn.size() == 1 ? ((SearchResult)callerDn.get(0)).getNameInNamespace() : null;
   }

   private List searchGroups(LdapContext searchContext, String callerDn) {
      String filter = null;
      if (this.ldapIdentityStoreDefinition.groupSearchFilter() != null && !this.ldapIdentityStoreDefinition.groupSearchFilter().trim().isEmpty()) {
         filter = String.format(this.ldapIdentityStoreDefinition.groupSearchFilter(), callerDn);
      } else {
         filter = String.format("(&(%s=%s)(|(objectclass=group)(objectclass=groupofnames)(objectclass=groupofuniquenames)))", this.ldapIdentityStoreDefinition.groupMemberAttribute(), callerDn);
      }

      return search(searchContext, this.ldapIdentityStoreDefinition.groupSearchBase(), filter, this.getGroupSearchControls());
   }

   private static List search(LdapContext searchContext, String searchBase, String searchFilter, SearchControls controls) {
      try {
         return Collections.list(searchContext.search(searchBase, searchFilter, controls));
      } catch (NameNotFoundException var5) {
         throw new IdentityStoreConfigurationException("Invalid searchBase", var5);
      } catch (InvalidSearchFilterException var6) {
         throw new IdentityStoreConfigurationException("Invalid search filter", var6);
      } catch (InvalidSearchControlsException var7) {
         throw new IdentityStoreConfigurationException("Invalid search controls", var7);
      } catch (Exception var8) {
         throw new IdentityStoreRuntimeException(var8);
      }
   }

   private SearchControls getCallerSearchControls() {
      SearchControls controls = new SearchControls();
      controls.setSearchScope(convertScopeValue(this.ldapIdentityStoreDefinition.callerSearchScope()));
      controls.setCountLimit((long)this.ldapIdentityStoreDefinition.maxResults());
      controls.setTimeLimit(this.ldapIdentityStoreDefinition.readTimeout());
      return controls;
   }

   private SearchControls getGroupSearchControls() {
      SearchControls controls = new SearchControls();
      controls.setSearchScope(convertScopeValue(this.ldapIdentityStoreDefinition.groupSearchScope()));
      controls.setCountLimit((long)this.ldapIdentityStoreDefinition.maxResults());
      controls.setTimeLimit(this.ldapIdentityStoreDefinition.readTimeout());
      controls.setReturningAttributes(new String[]{this.ldapIdentityStoreDefinition.groupNameAttribute()});
      return controls;
   }

   private static int convertScopeValue(LdapIdentityStoreDefinition.LdapSearchScope searchScope) {
      if (searchScope == LdapSearchScope.ONE_LEVEL) {
         return 1;
      } else {
         return searchScope == LdapSearchScope.SUBTREE ? 2 : 1;
      }
   }

   private LdapContext createSearchLdapContext() {
      try {
         return createLdapContext(this.ldapIdentityStoreDefinition.url(), this.ldapIdentityStoreDefinition.bindDn(), this.ldapIdentityStoreDefinition.bindDnPassword());
      } catch (AuthenticationException var2) {
         throw new IdentityStoreConfigurationException("Bad bindDn or bindPassword for: " + this.ldapIdentityStoreDefinition.bindDn(), var2);
      }
   }

   private LdapContext createCallerLdapContext(String bindDn, String bindDnPassword) {
      try {
         return createLdapContext(this.ldapIdentityStoreDefinition.url(), bindDn, bindDnPassword);
      } catch (AuthenticationException var4) {
         return null;
      }
   }

   private static LdapContext createLdapContext(String url, String bindDn, String bindCredential) throws AuthenticationException {
      Hashtable environment = new Hashtable();
      environment.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
      environment.put("java.naming.provider.url", url);
      environment.put("java.naming.security.authentication", "simple");
      environment.put("java.naming.security.principal", bindDn);
      environment.put("java.naming.security.credentials", bindCredential);

      try {
         return new InitialLdapContext(environment, (Control[])null);
      } catch (AuthenticationException var5) {
         throw var5;
      } catch (CommunicationException var6) {
         throw new IdentityStoreConfigurationException("Bad connection URL: " + url, var6);
      } catch (Exception var7) {
         throw new IdentityStoreRuntimeException(var7);
      }
   }

   private static void closeContext(LdapContext ldapContext) {
      try {
         if (ldapContext != null) {
            ldapContext.close();
         }
      } catch (NamingException var2) {
      }

   }

   public int priority() {
      return this.ldapIdentityStoreDefinition.priority();
   }

   public Set validationTypes() {
      return this.validationTypes;
   }
}
