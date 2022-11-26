package weblogic.management.runtime;

import java.io.Serializable;

public interface WsrmRequestInfo extends Serializable {
   String getMessageId();

   long getSeqNum();

   String getSoapAction();

   long getTimestamp();

   boolean isAckFlag();

   String getResponseMessageId();

   long getResponseTimestamp();
}
