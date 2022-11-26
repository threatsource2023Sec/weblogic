package org.glassfish.soteria.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

public class LoginToContinueAnnotationLiteral extends AnnotationLiteral implements LoginToContinue {
   private static final long serialVersionUID = 1L;
   private final String loginPage;
   private final boolean useForwardToLogin;
   private final String useForwardToLoginExpression;
   private final String errorPage;
   private boolean hasDeferredExpressions;

   public LoginToContinueAnnotationLiteral(String loginPage, boolean useForwardToLogin, String useForwardToLoginExpression, String errorPage) {
      this.loginPage = loginPage;
      this.useForwardToLogin = useForwardToLogin;
      this.useForwardToLoginExpression = useForwardToLoginExpression;
      this.errorPage = errorPage;
   }

   public static LoginToContinue eval(LoginToContinue in) {
      if (!hasAnyELExpression(in)) {
         return in;
      } else {
         try {
            LoginToContinueAnnotationLiteral out = new LoginToContinueAnnotationLiteral(AnnotationELPProcessor.evalImmediate(in.loginPage()), AnnotationELPProcessor.evalImmediate(in.useForwardToLoginExpression(), in.useForwardToLogin()), AnnotationELPProcessor.emptyIfImmediate(in.useForwardToLoginExpression()), AnnotationELPProcessor.evalImmediate(in.errorPage()));
            out.setHasDeferredExpressions(hasAnyELExpression(out));
            return out;
         } catch (Throwable var2) {
            var2.printStackTrace();
            throw var2;
         }
      }
   }

   public static boolean hasAnyELExpression(LoginToContinue in) {
      return AnnotationELPProcessor.hasAnyELExpression(in.loginPage(), in.errorPage(), in.useForwardToLoginExpression());
   }

   public String loginPage() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.loginPage) : this.loginPage;
   }

   public boolean useForwardToLogin() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.useForwardToLoginExpression, this.useForwardToLogin) : this.useForwardToLogin;
   }

   public String useForwardToLoginExpression() {
      return this.useForwardToLoginExpression;
   }

   public String errorPage() {
      return this.hasDeferredExpressions ? AnnotationELPProcessor.evalELExpression(this.errorPage) : this.errorPage;
   }

   public boolean isHasDeferredExpressions() {
      return this.hasDeferredExpressions;
   }

   public void setHasDeferredExpressions(boolean hasDeferredExpressions) {
      this.hasDeferredExpressions = hasDeferredExpressions;
   }
}
