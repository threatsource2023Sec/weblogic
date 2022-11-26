package weblogic.management.runtime;

import java.io.Serializable;
import weblogic.management.ManagementException;

public interface WsrmSequenceInfo extends Serializable {
   String NEW = "NEW";
   String CREATING = "CREATING";
   String CREATED = "CREATED";
   /** @deprecated */
   @Deprecated
   String LAST_MESSAGE_PENDING = "LAST_MESSAGE_PENDING";
   /** @deprecated */
   @Deprecated
   String LAST_MESSAGE = "LAST_MESSAGE";
   String CLOSING = "CLOSING";
   String CLOSED = "CLOSED";
   String TERMINATING = "TERMINATING";
   String TERMINATED = "TERMINATED";

   String getId();

   String getLogicalStoreName();

   String getPhysicalStoreName();

   boolean isSource();

   String getDestinationId();

   boolean isOffer();

   String getMainSequenceId();

   String getState();

   long getCreationTime();

   long getLastActivityTime();

   long getMaxAge();

   long getLastAckdMessageNum();

   long getUnackdCount();

   WsrmRequestInfo[] getRequests() throws ManagementException;
}
