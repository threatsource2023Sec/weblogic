package org.glassfish.soteria.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.IdentityStore;

public class DatabaseIdentityStoreDefinitionAnnotationLiteral extends AnnotationLiteral implements DatabaseIdentityStoreDefinition {
   private static final long serialVersionUID = 1L;
   private final String dataSourceLookup;
   private final String callerQuery;
   private final String groupsQuery;
   private final Class hashAlgorithm;
   private final String[] hashAlgorithmParameters;
   private final int priority;
   private final String priorityExpression;
   private final IdentityStore.ValidationType[] useFor;
   private final String useForExpression;
   private boolean hasDeferredExpressions;

   public DatabaseIdentityStoreDefinitionAnnotationLiteral(String dataSourceLookup, String callerQuery, String groupsQuery, Class hashAlgorithm, String[] hashAlgorithmParameters, int priority, String priorityExpression, IdentityStore.ValidationType[] useFor, String useForExpression) {
      this.dataSourceLookup = dataSourceLookup;
      this.callerQuery = callerQuery;
      this.groupsQuery = groupsQuery;
      this.hashAlgorithm = hashAlgorithm;
      this.hashAlgorithmParameters = hashAlgorithmParameters;
      this.priority = priority;
      this.priorityExpression = priorityExpression;
      this.useFor = useFor;
      this.useForExpression = useForExpression;
   }

   public static DatabaseIdentityStoreDefinition eval(DatabaseIdentityStoreDefinition in) {
      if (!hasAnyELExpression(in)) {
         return in;
      } else {
         DatabaseIdentityStoreDefinitionAnnotationLiteral out = new DatabaseIdentityStoreDefinitionAnnotationLiteral(AnnotationELPProcessor.evalImmediate(in.dataSourceLookup()), AnnotationELPProcessor.evalImmediate(in.callerQuery()), AnnotationELPProcessor.evalImmediate(in.groupsQuery()), in.hashAlgorithm(), in.hashAlgorithmParameters(), AnnotationELPProcessor.evalImmediate(in.priorityExpression(), in.priority()), AnnotationELPProcessor.emptyIfImmediate(in.priorityExpression()), (IdentityStore.ValidationType[])AnnotationELPProcessor.evalImmediate((String)in.useForExpression(), (Object)in.useFor()), AnnotationELPProcessor.emptyIfImmediate(in.useForExpression()));
         out.setHasDeferredExpressions(hasAnyELExpression(out));
         return out;
      }
   }

   public static boolean hasAnyELExpression(DatabaseIdentityStoreDefinition in) {
      return AnnotationELPProcessor.hasAnyELExpression(in.dataSourceLookup(), in.callerQuery(), in.groupsQuery(), in.priorityExpression(), in.useForExpression());
   }

   public String dataSourceLookup() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.dataSourceLookup) : this.dataSourceLookup;
   }

   public String callerQuery() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.callerQuery) : this.callerQuery;
   }

   public String groupsQuery() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.groupsQuery) : this.groupsQuery;
   }

   public Class hashAlgorithm() {
      return this.hashAlgorithm;
   }

   public String[] hashAlgorithmParameters() {
      return this.hashAlgorithmParameters;
   }

   public int priority() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.priorityExpression, this.priority) : this.priority;
   }

   public String priorityExpression() {
      return this.priorityExpression;
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
