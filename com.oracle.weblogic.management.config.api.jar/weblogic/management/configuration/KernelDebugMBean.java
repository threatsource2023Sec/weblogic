package weblogic.management.configuration;

import java.util.Properties;

public interface KernelDebugMBean extends DebugMBean {
   boolean getDebugAbbreviation();

   void setDebugAbbreviation(boolean var1);

   boolean getDebugConnection();

   void setDebugConnection(boolean var1);

   boolean getDebugMessaging();

   void setDebugMessaging(boolean var1);

   boolean getDebugRouting();

   void setDebugRouting(boolean var1);

   boolean getDebugLocalRemoteJVM();

   void setDebugLocalRemoteJVM(boolean var1);

   boolean getDebugLoadBalancing();

   void setDebugLoadBalancing(boolean var1);

   boolean getDebugWorkContext();

   void setDebugWorkContext(boolean var1);

   boolean getDebugFailOver();

   void setDebugFailOver(boolean var1);

   boolean getForceGCEachDGCPeriod();

   void setForceGCEachDGCPeriod(boolean var1);

   boolean getDebugDGCEnrollment();

   void setDebugDGCEnrollment(boolean var1);

   boolean getLogDGCStatistics();

   void setLogDGCStatistics(boolean var1);

   boolean getDebugSSL();

   void setDebugSSL(boolean var1);

   boolean getDebugRC4();

   void setDebugRC4(boolean var1);

   boolean getDebugRSA();

   void setDebugRSA(boolean var1);

   boolean getDebugMuxer();

   void setDebugMuxer(boolean var1);

   boolean getDebugMuxerDetail();

   void setDebugMuxerDetail(boolean var1);

   boolean getDebugMuxerTimeout();

   void setDebugMuxerTimeout(boolean var1);

   boolean getDebugMuxerConnection();

   void setDebugMuxerConnection(boolean var1);

   boolean getDebugMuxerException();

   void setDebugMuxerException(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugIIOP();

   void setDebugIIOP(boolean var1);

   boolean getDebugIIOPTransport();

   void setDebugIIOPTransport(boolean var1);

   boolean getDebugIIOPMarshal();

   void setDebugIIOPMarshal(boolean var1);

   boolean getDebugIIOPSecurity();

   void setDebugIIOPSecurity(boolean var1);

   boolean getDebugIIOPOTS();

   void setDebugIIOPOTS(boolean var1);

   boolean getDebugIIOPReplacer();

   void setDebugIIOPReplacer(boolean var1);

   boolean getDebugIIOPConnection();

   void setDebugIIOPConnection(boolean var1);

   boolean getDebugIIOPStartup();

   void setDebugIIOPStartup(boolean var1);

   boolean getDebugSelfTuning();

   void setDebugSelfTuning(boolean var1);

   Properties getDebugParameters();

   void setDebugParameters(Properties var1);
}
