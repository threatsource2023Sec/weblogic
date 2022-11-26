package weblogic.diagnostics.context;

public interface CorrelationIntegrationManagerForTest extends CorrelationIntegrationManager {
   Correlation getCorrelationFromMap();

   Correlation newCorrelation(DiagnosticContextImpl var1);

   Correlation newCorrelationFromBinaryDC(byte[] var1);

   DiagnosticContextImpl newDiagnosticContext(byte[] var1);

   DiagnosticContextImpl newDiagnosticContext(CorrelationImpl var1);

   void setIncomingDCImplsSeen(boolean var1);

   void correlationPropagatedIn(CorrelationImpl var1);

   void setIsUnmarshalled(CorrelationImpl var1, boolean var2);

   void setIsUnmarshalled(DiagnosticContextImpl var1, boolean var2);

   String wrap(Correlation var1);

   Correlation unwrap(String var1);
}
