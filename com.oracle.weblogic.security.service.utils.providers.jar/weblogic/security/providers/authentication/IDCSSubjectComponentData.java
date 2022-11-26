package weblogic.security.providers.authentication;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class IDCSSubjectComponentData {
   private final String userName;
   private final String tenant;
   private final String userID;
   private final String filter;
   private final String clientName;
   private final String clientID;
   private final String clientTenant;
   private final Set groups;
   private final Set approles;
   private final Set principals;
   private final Set privateCredentials;
   private final Set publicCredentials;
   private final String scope;
   private final String resourceTenant;

   private static Set copyToImmutableSet(Set set) {
      return null != set ? Collections.unmodifiableSet(new HashSet(set)) : null;
   }

   public IDCSSubjectComponentData(String userName, String userID, Set groups, Set approles, Set principals, Set privateCredentials, Set publicCredentials, String tenant, String filter, String clientName, String clientID, String clientTenant, String scope, String resourceTenant) {
      this.userName = userName;
      this.userID = userID;
      this.groups = copyToImmutableSet(groups);
      this.approles = copyToImmutableSet(approles);
      this.principals = copyToImmutableSet(principals);
      this.privateCredentials = copyToImmutableSet(privateCredentials);
      this.publicCredentials = copyToImmutableSet(publicCredentials);
      this.tenant = tenant;
      this.filter = filter;
      this.clientName = clientName;
      this.clientID = clientID;
      this.clientTenant = clientTenant;
      this.scope = scope;
      this.resourceTenant = resourceTenant;
   }

   public String getUserName() {
      return this.userName;
   }

   public String getUserID() {
      return this.userID;
   }

   public String getTenant() {
      return this.tenant;
   }

   public String getFilter() {
      return this.filter;
   }

   public Set getGroups() {
      return this.groups;
   }

   public Set getAppRoles() {
      return this.approles;
   }

   public Set getPrincipals() {
      return this.principals;
   }

   public Set getPrivateCredentials() {
      return this.privateCredentials;
   }

   public Set getPublicCredentials() {
      return this.publicCredentials;
   }

   public String getClientName() {
      return this.clientName;
   }

   public String getClientID() {
      return this.clientID;
   }

   public String getClientTenant() {
      return this.clientTenant;
   }

   public String getScope() {
      return this.scope;
   }

   public String getResourceTenant() {
      return this.resourceTenant;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(256);
      sb.append(this.getClass().getName());
      sb.append(" instance, userName=");
      sb.append(this.userName);
      sb.append(" instance, tenant=");
      sb.append(this.tenant);
      sb.append(" instance, filter=");
      sb.append(this.filter);
      sb.append(" instance, groups=");
      sb.append(this.groups);
      sb.append(" instance, approles=");
      sb.append(this.approles);
      sb.append(" instance, clientName=");
      sb.append(this.clientName);
      sb.append(" instance, clientID=");
      sb.append(this.clientID);
      sb.append(" instance, clientTenant=");
      sb.append(this.clientTenant);
      sb.append(" instance, scope=");
      sb.append(this.scope);
      sb.append(" instance, resourceTenant=");
      sb.append(this.resourceTenant);
      return sb.toString();
   }
}
