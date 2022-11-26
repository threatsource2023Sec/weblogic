package weblogic.management.configuration;

public class MailSessionValidator {
   public static void validateMailSession(MailSessionMBean mailSession) throws IllegalArgumentException {
      JNDIValidator.validateJNDINames(domainMailSessionMBeans(mailSession));
   }

   public static void validateUniqueJNDIName(MailSessionMBean mailSession, String jndiName) throws IllegalArgumentException {
      JNDIValidator.validateJNDIName(mailSession, domainMailSessionMBeans(mailSession));
   }

   public static void validateUniqueJNDIName(MailSessionMBean mailSession, TargetMBean[] targets) throws IllegalArgumentException {
      JNDIValidator.validateJNDIName(mailSession, domainMailSessionMBeans(mailSession));
   }

   private static MailSessionMBean[] domainMailSessionMBeans(MailSessionMBean mailSession) {
      DomainMBean parent = getDomainParent(mailSession);
      return parent == null ? null : parent.getMailSessions();
   }

   private static DomainMBean getDomainParent(MailSessionMBean topLevel) {
      return (DomainMBean)topLevel.getParent();
   }
}
