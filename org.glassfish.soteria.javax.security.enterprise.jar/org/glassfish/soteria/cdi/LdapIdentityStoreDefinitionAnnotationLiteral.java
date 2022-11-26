package org.glassfish.soteria.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;

public class LdapIdentityStoreDefinitionAnnotationLiteral extends AnnotationLiteral implements LdapIdentityStoreDefinition {
   private static final long serialVersionUID = 1L;
   private final String bindDn;
   private final String bindDnPassword;
   private final String callerBaseDn;
   private final String callerNameAttribute;
   private final String callerSearchBase;
   private final String callerSearchFilter;
   private final LdapIdentityStoreDefinition.LdapSearchScope callerSearchScope;
   private final String callerSearchScopeExpression;
   private final String groupMemberAttribute;
   private final String groupMemberOfAttribute;
   private final String groupNameAttribute;
   private final String groupSearchBase;
   private final String groupSearchFilter;
   private final LdapIdentityStoreDefinition.LdapSearchScope groupSearchScope;
   private final String groupSearchScopeExpression;
   private final int maxResults;
   private final String maxResultsExpression;
   private final int priority;
   private final String priorityExpression;
   private final int readTimeout;
   private final String readTimeoutExpression;
   private final String url;
   private final IdentityStore.ValidationType[] useFor;
   private final String useForExpression;
   private boolean hasDeferredExpressions;

   public LdapIdentityStoreDefinitionAnnotationLiteral(String bindDn, String bindDnPassword, String callerBaseDn, String callerNameAttribute, String callerSearchBase, String callerSearchFilter, LdapIdentityStoreDefinition.LdapSearchScope callerSearchScope, String callerSearchScopeExpression, String groupMemberAttribute, String groupMemberOfAttribute, String groupNameAttribute, String groupSearchBase, String groupSearchFilter, LdapIdentityStoreDefinition.LdapSearchScope groupSearchScope, String groupSearchScopeExpression, int maxResults, String maxResultsExpression, int priority, String priorityExpression, int readTimeout, String readTimeoutExpression, String url, IdentityStore.ValidationType[] useFor, String useForExpression) {
      this.bindDn = bindDn;
      this.bindDnPassword = bindDnPassword;
      this.callerBaseDn = callerBaseDn;
      this.callerNameAttribute = callerNameAttribute;
      this.callerSearchBase = callerSearchBase;
      this.callerSearchFilter = callerSearchFilter;
      this.callerSearchScope = callerSearchScope;
      this.callerSearchScopeExpression = callerSearchScopeExpression;
      this.groupMemberAttribute = groupMemberAttribute;
      this.groupMemberOfAttribute = groupMemberOfAttribute;
      this.groupNameAttribute = groupNameAttribute;
      this.groupSearchBase = groupSearchBase;
      this.groupSearchFilter = groupSearchFilter;
      this.groupSearchScope = groupSearchScope;
      this.groupSearchScopeExpression = groupSearchScopeExpression;
      this.maxResults = maxResults;
      this.maxResultsExpression = maxResultsExpression;
      this.priority = priority;
      this.priorityExpression = priorityExpression;
      this.readTimeout = readTimeout;
      this.readTimeoutExpression = readTimeoutExpression;
      this.url = url;
      this.useFor = useFor;
      this.useForExpression = useForExpression;
   }

   public static LdapIdentityStoreDefinition eval(LdapIdentityStoreDefinition in) {
      if (!hasAnyELExpression(in)) {
         return in;
      } else {
         try {
            LdapIdentityStoreDefinitionAnnotationLiteral out = new LdapIdentityStoreDefinitionAnnotationLiteral(in.bindDn(), in.bindDnPassword(), in.callerBaseDn(), in.callerNameAttribute(), in.callerSearchBase(), in.callerSearchFilter(), in.callerSearchScope(), in.callerSearchScopeExpression(), in.groupMemberAttribute(), in.groupMemberOfAttribute(), in.groupNameAttribute(), in.groupSearchBase(), in.groupSearchFilter(), in.groupSearchScope(), in.groupSearchScopeExpression(), in.maxResults(), in.maxResultsExpression(), in.priority(), in.priorityExpression(), in.readTimeout(), in.readTimeoutExpression(), in.url(), in.useFor(), in.useForExpression());
            out.setHasDeferredExpressions(hasAnyELExpression(out));
            return out;
         } catch (Throwable var2) {
            var2.printStackTrace();
            throw var2;
         }
      }
   }

   public static boolean hasAnyELExpression(LdapIdentityStoreDefinition in) {
      return AnnotationELPProcessor.hasAnyELExpression(in.bindDn(), in.bindDnPassword(), in.callerNameAttribute(), in.callerSearchBase(), in.callerSearchFilter(), in.callerSearchScopeExpression(), in.groupMemberAttribute(), in.groupMemberOfAttribute(), in.groupNameAttribute(), in.groupSearchBase(), in.groupSearchFilter(), in.groupSearchScopeExpression(), in.maxResultsExpression(), in.priorityExpression(), in.readTimeoutExpression(), in.url(), in.useForExpression());
   }

   public String bindDn() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.bindDn) : this.bindDn;
   }

   public String bindDnPassword() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.bindDnPassword) : this.bindDnPassword;
   }

   public String callerBaseDn() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.callerBaseDn) : this.callerBaseDn;
   }

   public String callerNameAttribute() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.callerNameAttribute) : this.callerNameAttribute;
   }

   public String callerSearchBase() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.callerSearchBase) : this.callerSearchBase;
   }

   public String callerSearchFilter() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.callerSearchFilter) : this.callerSearchFilter;
   }

   public LdapIdentityStoreDefinition.LdapSearchScope callerSearchScope() {
      return this.hasDeferredExpressions ? (LdapIdentityStoreDefinition.LdapSearchScope)AnnotationELPProcessor.evalELExpression((String)this.callerSearchScopeExpression, (Object)this.callerSearchScope) : this.callerSearchScope;
   }

   public String callerSearchScopeExpression() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.callerSearchScopeExpression) : this.callerSearchScopeExpression;
   }

   public String groupMemberAttribute() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.groupMemberAttribute) : this.groupMemberAttribute;
   }

   public String groupMemberOfAttribute() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.groupMemberOfAttribute) : this.groupMemberOfAttribute;
   }

   public String groupNameAttribute() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.groupNameAttribute) : this.groupNameAttribute;
   }

   public String groupSearchBase() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.groupSearchBase) : this.groupSearchBase;
   }

   public String groupSearchFilter() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.groupSearchFilter) : this.groupSearchFilter;
   }

   public LdapIdentityStoreDefinition.LdapSearchScope groupSearchScope() {
      return this.hasDeferredExpressions ? (LdapIdentityStoreDefinition.LdapSearchScope)AnnotationELPProcessor.evalELExpression((String)this.groupSearchScopeExpression, (Object)this.groupSearchScope) : this.groupSearchScope;
   }

   public String groupSearchScopeExpression() {
      return this.groupSearchScopeExpression;
   }

   public int maxResults() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.maxResultsExpression, this.maxResults) : this.maxResults;
   }

   public String maxResultsExpression() {
      return this.maxResultsExpression;
   }

   public int priority() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.priorityExpression, this.priority) : this.priority;
   }

   public String priorityExpression() {
      return this.priorityExpression;
   }

   public int readTimeout() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.readTimeoutExpression, this.readTimeout) : this.readTimeout;
   }

   public String readTimeoutExpression() {
      return this.readTimeoutExpression;
   }

   public String url() {
      return this.url;
   }

   public IdentityStore.ValidationType[] useFor() {
      return this.hasDeferredExpressions ? (IdentityStore.ValidationType[])AnnotationELPProcessor.evalELExpression((String)this.useForExpression, (Object)this.useFor) : this.useFor;
   }

   public String useForExpression() {
      return this.useForExpression;
   }

   public boolean isHasDeferredExpressions() {
      return this.hasDeferredExpressions;
   }

   public void setHasDeferredExpressions(boolean hasDeferredExpressions) {
      this.hasDeferredExpressions = hasDeferredExpressions;
   }
}
