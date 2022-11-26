package weblogic.jms.backend;

import javax.jms.JMSException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.dispatcher.Invocable;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;

public interface BEConsumerCommon extends Invocable {
   BEDestinationImpl getDestination();

   String getName();

   void setName(String var1);

   String getSubscriptionName();

   boolean isDurable();

   boolean isActive();

   boolean isUsed();

   String getSelector();

   String getClientID();

   int getClientIdPolicy();

   boolean getNoLocal();

   int getSubscriptionSharingPolicy();

   long getMessagesUnackedCount();

   long getMessagesReceivedCount();

   long getBytesUnackedCount();

   long getBytesCurrentCount();

   int getSize();

   int getHighSize();

   long getLastMessagesReceivedTime();

   long getSubscriptionLimitDeletedCount();

   void delete(boolean var1, boolean var2) throws JMSException;

   void closeDurableSubscription() throws JMSException;

   BEDurableSubscriberRuntimeMBeanImpl getDurableSubscriberMbean();

   void dumpRef(JMSDiagnosticImageSource var1, XMLStreamWriter var2) throws XMLStreamException, DiagnosticImageTimeoutException;

   void dump(JMSDiagnosticImageSource var1, XMLStreamWriter var2) throws XMLStreamException, DiagnosticImageTimeoutException;

   CompositeData getCompositeData() throws OpenDataException;
}
