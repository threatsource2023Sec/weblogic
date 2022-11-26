package weblogic.ejb.container.compliance;

public final class ComplianceCheckerFactory {
   private ComplianceCheckerFactory() {
   }

   public static ComplianceChecker getComplianceChecker() {
      return EJBComplianceChecker.INSTANCE;
   }
}
