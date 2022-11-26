package weblogic.management.runtime;

import java.util.Date;
import javax.management.openmbean.CompositeData;
import weblogic.management.ManagementException;
import weblogic.messaging.saf.SAFException;

public interface SAFRemoteEndpointRuntimeMBean extends SAFStatisticsCommonMBean, SAFMessageCursorRuntimeMBean {
   String getURL();

   CompositeData getMessage(String var1) throws ManagementException;

   String getEndpointType();

   void pauseIncoming() throws SAFException;

   void resumeIncoming() throws SAFException;

   boolean isPausedForIncoming();

   void pauseForwarding() throws SAFException;

   void resumeForwarding() throws SAFException;

   boolean isPausedForForwarding();

   void expireAll();

   void purge() throws SAFException;

   String getMessages(String var1, Integer var2) throws ManagementException;

   long getDowntimeHigh();

   long getDowntimeTotal();

   long getUptimeHigh();

   long getUptimeTotal();

   Date getLastTimeConnected();

   Date getLastTimeFailedToConnect();

   Exception getLastException();

   String getOperationState();
}
