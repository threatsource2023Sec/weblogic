package org.glassfish.soteria.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

public class BasicAuthenticationMechanismDefinitionAnnotationLiteral extends AnnotationLiteral implements BasicAuthenticationMechanismDefinition {
   private static final long serialVersionUID = 1L;
   private final String realmName;
   private boolean hasDeferredExpressions;

   public BasicAuthenticationMechanismDefinitionAnnotationLiteral(String realmName) {
      this.realmName = realmName;
   }

   public static BasicAuthenticationMechanismDefinition eval(BasicAuthenticationMechanismDefinition in) {
      if (!hasAnyELExpression(in)) {
         return in;
      } else {
         BasicAuthenticationMechanismDefinitionAnnotationLiteral out = new BasicAuthenticationMechanismDefinitionAnnotationLiteral(AnnotationELPProcessor.evalImmediate(in.realmName()));
         out.setHasDeferredExpressions(hasAnyELExpression(out));
         return out;
      }
   }

   public static boolean hasAnyELExpression(BasicAuthenticationMechanismDefinition in) {
      return AnnotationELPProcessor.hasAnyELExpression(in.realmName());
   }

   public String realmName() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.realmName) : this.realmName;
   }

   public boolean isHasDeferredExpressions() {
      return this.hasDeferredExpressions;
   }

   public void setHasDeferredExpressions(boolean hasDeferredExpressions) {
      this.hasDeferredExpressions = hasDeferredExpressions;
   }
}
