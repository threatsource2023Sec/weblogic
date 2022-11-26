package weblogic.cluster.singleton;

import org.jvnet.hk2.annotations.Contract;
import weblogic.server.ServiceFailureException;

@Contract
public interface MemberLivenessDetector {
   void startDetector() throws ServiceFailureException;

   void stopDetector() throws ServiceFailureException;

   long getCheckInterval();

   boolean isStarted();
}
