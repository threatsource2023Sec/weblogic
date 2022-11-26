package weblogic.jms.extensions;

import weblogic.jms.common.JMSMessageId;

public interface WLAcknowledgeInfo {
   JMSMessageId getMessageId();

   long getSequenceNumber();

   boolean getClientResponsibleForAcknowledge();
}
