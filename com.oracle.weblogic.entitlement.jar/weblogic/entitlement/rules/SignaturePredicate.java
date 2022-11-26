package weblogic.entitlement.rules;

import javax.security.auth.Subject;
import weblogic.security.SecurityLogger;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.service.ContextHandler;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.Resource;
import weblogic.security.utils.ESubjectImpl;

public class SignaturePredicate extends BasePredicate {
   public static final String GROUP_TYPE = "group";
   public static final String USERNAME_TYPE = "user";
   private static final String VERSION = "1.0";
   private static final PredicateArgument[] arguments = new PredicateArgument[]{new StringPredicateArgument("SignaturePredicateSignerTypeArgumentName", "SignaturePredicateSignerTypeArgumentDescription", (String)null), new StringPredicateArgument("SignaturePredicateSignedElementArgumentName", "SignaturePredicateSignedElementArgumentDescription", (String)null), new StringPredicateArgument("SignaturePredicateSignerNameArgumentName", "SignaturePredicateSignerNameArgumentDescription", (String)null)};
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityPredicate");
   private String signerType = "group";
   private String signerName = null;
   private String signerElement = null;

   public SignaturePredicate() {
      super("SignaturePredicateName", "SignaturePredicateDescription");
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length == 3) {
         String type = args[0];
         if ("user".equalsIgnoreCase(type)) {
            this.signerType = "user";
         } else {
            if (!"group".equalsIgnoreCase(type)) {
               throw new IllegalPredicateArgumentException(SecurityLogger.getTypeMustValueIs("group", "user", type));
            }

            this.signerType = "group";
         }

         if (args[1] == null) {
            throw new IllegalPredicateArgumentException(SecurityLogger.getSignatureTypeCanNotBeNull());
         } else {
            this.signerElement = "Integrity{" + args[1] + "}";
            if (args[2] == null) {
               throw new IllegalPredicateArgumentException(SecurityLogger.getSignedByCanNotBeNull());
            } else {
               this.signerName = args[2];
               if (log.isDebugEnabled()) {
                  log.debug("SignaturePredicate.init: signerType=" + this.signerType + ", signerName=" + this.signerName + ", signerElement=" + this.signerElement);
               }

            }
         }
      } else {
         throw new IllegalPredicateArgumentException(SecurityLogger.getThreeArgumentsRequired());
      }
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      if (log.isDebugEnabled()) {
         log.debug("SignaturePredicate.evaluate: matching " + this.signerType + " " + this.signerName);
      }

      if (context == null) {
         if (log.isDebugEnabled()) {
            log.debug("SignaturePredicate.evaluate: context is null, returning false");
         }

         return false;
      } else {
         Subject signer = (Subject)context.getValue(this.signerElement);
         if (signer == null) {
            if (log.isDebugEnabled()) {
               log.debug("SignaturePredicate.evaluate: no signer, returning false");
            }

            return false;
         } else {
            ESubjectImpl eSubject = new ESubjectImpl(signer);
            boolean signed = this.signerType == "user" ? eSubject.isUser(this.signerName) : eSubject.isMemberOf(this.signerName);
            if (log.isDebugEnabled()) {
               log.debug("SignaturePredicate.evaluate: returning " + signed);
            }

            return signed;
         }
      }
   }

   public boolean isSupportedResource(String resourceId) {
      return resourceId.startsWith("type=<webservices>");
   }

   public String getVersion() {
      return "1.0";
   }

   public int getArgumentCount() {
      return arguments.length;
   }

   public PredicateArgument getArgument(int index) {
      return arguments[index];
   }
}
