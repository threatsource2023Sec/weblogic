package weblogic.jms.common;

import java.util.HashSet;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface TimedSecurityParticipant {
   String JMS_SECURITY_TIMER_MANAGER = "weblogic.jms.security.checkers";
   String JMS_SECURITY_THREAD_POOL = "weblogic.kernel.Default";
   String SEND = "send";
   String RECV = "receive";
   String BROWSE = "browse";

   void securityLapsed();

   boolean isClosed();

   HashSet getAcceptedDestinations();

   AuthenticatedSubject getSubject();
}
