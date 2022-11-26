package org.glassfish.soteria.cdi;

import javax.el.ELProcessor;
import javax.enterprise.util.AnnotationLiteral;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;

public class RememberMeAnnotationLiteral extends AnnotationLiteral implements RememberMe {
   private static final long serialVersionUID = 1L;
   private final int cookieMaxAgeSeconds;
   private final String cookieMaxAgeSecondsExpression;
   private final boolean cookieSecureOnly;
   private final String cookieSecureOnlyExpression;
   private final boolean cookieHttpOnly;
   private final String cookieHttpOnlyExpression;
   private final String cookieName;
   private final boolean isRememberMe;
   private final String isRememberMeExpression;
   private final ELProcessor elProcessor;
   private boolean hasDeferredExpressions;

   public RememberMeAnnotationLiteral(int cookieMaxAgeSeconds, String cookieMaxAgeSecondsExpression, boolean cookieSecureOnly, String cookieSecureOnlyExpression, boolean cookieHttpOnly, String cookieHttpOnlyExpression, String cookieName, boolean isRememberMe, String isRememberMeExpression, ELProcessor elProcessor) {
      this.cookieMaxAgeSeconds = cookieMaxAgeSeconds;
      this.cookieMaxAgeSecondsExpression = cookieMaxAgeSecondsExpression;
      this.cookieSecureOnly = cookieSecureOnly;
      this.cookieSecureOnlyExpression = cookieSecureOnlyExpression;
      this.cookieHttpOnly = cookieHttpOnly;
      this.cookieHttpOnlyExpression = cookieHttpOnlyExpression;
      this.cookieName = cookieName;
      this.isRememberMe = isRememberMe;
      this.isRememberMeExpression = isRememberMeExpression;
      this.elProcessor = elProcessor;
   }

   public static RememberMe eval(RememberMe in, ELProcessor elProcessor) {
      if (!hasAnyELExpression(in)) {
         return in;
      } else {
         try {
            RememberMeAnnotationLiteral out = new RememberMeAnnotationLiteral(AnnotationELPProcessor.evalImmediate(elProcessor, in.cookieMaxAgeSecondsExpression(), in.cookieMaxAgeSeconds()), AnnotationELPProcessor.emptyIfImmediate(in.cookieMaxAgeSecondsExpression()), AnnotationELPProcessor.evalImmediate(elProcessor, in.cookieSecureOnlyExpression(), in.cookieSecureOnly()), AnnotationELPProcessor.emptyIfImmediate(in.cookieSecureOnlyExpression()), AnnotationELPProcessor.evalImmediate(elProcessor, in.cookieHttpOnlyExpression(), in.cookieHttpOnly()), AnnotationELPProcessor.emptyIfImmediate(in.cookieHttpOnlyExpression()), AnnotationELPProcessor.evalImmediate(elProcessor, in.cookieName()), AnnotationELPProcessor.evalImmediate(elProcessor, in.isRememberMeExpression(), in.isRememberMe()), AnnotationELPProcessor.evalImmediate(elProcessor, in.isRememberMeExpression()), elProcessor);
            out.setHasDeferredExpressions(hasAnyELExpression(out));
            return out;
         } catch (Throwable var3) {
            var3.printStackTrace();
            throw var3;
         }
      }
   }

   public static boolean hasAnyELExpression(RememberMe in) {
      return AnnotationELPProcessor.hasAnyELExpression(in.cookieMaxAgeSecondsExpression(), in.cookieSecureOnlyExpression(), in.cookieHttpOnlyExpression(), in.cookieName(), in.isRememberMeExpression());
   }

   public boolean cookieHttpOnly() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.elProcessor, this.cookieHttpOnlyExpression, this.cookieHttpOnly) : this.cookieHttpOnly;
   }

   public String cookieHttpOnlyExpression() {
      return this.cookieHttpOnlyExpression;
   }

   public int cookieMaxAgeSeconds() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.elProcessor, this.cookieMaxAgeSecondsExpression, this.cookieMaxAgeSeconds) : this.cookieMaxAgeSeconds;
   }

   public String cookieMaxAgeSecondsExpression() {
      return this.cookieMaxAgeSecondsExpression;
   }

   public boolean cookieSecureOnly() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.elProcessor, this.cookieSecureOnlyExpression, this.cookieSecureOnly) : this.cookieSecureOnly;
   }

   public String cookieSecureOnlyExpression() {
      return this.cookieSecureOnlyExpression;
   }

   public String cookieName() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.elProcessor, this.cookieName) : this.cookieName;
   }

   public boolean isRememberMe() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.elProcessor, this.isRememberMeExpression, this.isRememberMe) : this.isRememberMe;
   }

   public String isRememberMeExpression() {
      return this.isRememberMeExpression;
   }

   public boolean isHasDeferredExpressions() {
      return this.hasDeferredExpressions;
   }

   public void setHasDeferredExpressions(boolean hasDeferredExpressions) {
      this.hasDeferredExpressions = hasDeferredExpressions;
   }
}
