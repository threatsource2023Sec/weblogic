package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicWebservices.ReliabilityConfigType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class ReliabilityConfigTypeImpl extends XmlComplexContentImpl implements ReliabilityConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName CUSTOMIZED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "customized");
   private static final QName INACTIVITYTIMEOUT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "inactivity-timeout");
   private static final QName BASERETRANSMISSIONINTERVAL$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "base-retransmission-interval");
   private static final QName RETRANSMISSIONEXPONENTIALBACKOFF$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "retransmission-exponential-backoff");
   private static final QName NONBUFFEREDSOURCE$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "non-buffered-source");
   private static final QName ACKNOWLEDGEMENTINTERVAL$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "acknowledgement-interval");
   private static final QName SEQUENCEEXPIRATION$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "sequence-expiration");
   private static final QName BUFFERRETRYCOUNT$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "buffer-retry-count");
   private static final QName BUFFERRETRYDELAY$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "buffer-retry-delay");
   private static final QName NONBUFFEREDDESTINATION$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "non-buffered-destination");
   private static final QName MESSAGINGQUEUEJNDINAME$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "messaging-queue-jndi-name");
   private static final QName MESSAGINGQUEUEMDBRUNASPRINCIPALNAME$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "messaging-queue-mdb-run-as-principal-name");

   public ReliabilityConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         return target;
      }
   }

   public boolean isSetCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMIZED$0) != 0;
      }
   }

   public void setCustomized(boolean customized) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CUSTOMIZED$0);
         }

         target.setBooleanValue(customized);
      }
   }

   public void xsetCustomized(XmlBoolean customized) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CUSTOMIZED$0);
         }

         target.set(customized);
      }
   }

   public void unsetCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMIZED$0, 0);
      }
   }

   public String getInactivityTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(INACTIVITYTIMEOUT$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInactivityTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INACTIVITYTIMEOUT$2) != 0;
      }
   }

   public void setInactivityTimeout(String inactivityTimeout) {
      this.generatedSetterHelperImpl(inactivityTimeout, INACTIVITYTIMEOUT$2, 0, (short)1);
   }

   public String addNewInactivityTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(INACTIVITYTIMEOUT$2);
         return target;
      }
   }

   public void unsetInactivityTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INACTIVITYTIMEOUT$2, 0);
      }
   }

   public String getBaseRetransmissionInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(BASERETRANSMISSIONINTERVAL$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetBaseRetransmissionInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BASERETRANSMISSIONINTERVAL$4) != 0;
      }
   }

   public void setBaseRetransmissionInterval(String baseRetransmissionInterval) {
      this.generatedSetterHelperImpl(baseRetransmissionInterval, BASERETRANSMISSIONINTERVAL$4, 0, (short)1);
   }

   public String addNewBaseRetransmissionInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(BASERETRANSMISSIONINTERVAL$4);
         return target;
      }
   }

   public void unsetBaseRetransmissionInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BASERETRANSMISSIONINTERVAL$4, 0);
      }
   }

   public boolean getRetransmissionExponentialBackoff() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETRANSMISSIONEXPONENTIALBACKOFF$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRetransmissionExponentialBackoff() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RETRANSMISSIONEXPONENTIALBACKOFF$6, 0);
         return target;
      }
   }

   public boolean isSetRetransmissionExponentialBackoff() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETRANSMISSIONEXPONENTIALBACKOFF$6) != 0;
      }
   }

   public void setRetransmissionExponentialBackoff(boolean retransmissionExponentialBackoff) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETRANSMISSIONEXPONENTIALBACKOFF$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RETRANSMISSIONEXPONENTIALBACKOFF$6);
         }

         target.setBooleanValue(retransmissionExponentialBackoff);
      }
   }

   public void xsetRetransmissionExponentialBackoff(XmlBoolean retransmissionExponentialBackoff) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RETRANSMISSIONEXPONENTIALBACKOFF$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(RETRANSMISSIONEXPONENTIALBACKOFF$6);
         }

         target.set(retransmissionExponentialBackoff);
      }
   }

   public void unsetRetransmissionExponentialBackoff() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETRANSMISSIONEXPONENTIALBACKOFF$6, 0);
      }
   }

   public boolean getNonBufferedSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONBUFFEREDSOURCE$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetNonBufferedSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONBUFFEREDSOURCE$8, 0);
         return target;
      }
   }

   public boolean isSetNonBufferedSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONBUFFEREDSOURCE$8) != 0;
      }
   }

   public void setNonBufferedSource(boolean nonBufferedSource) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONBUFFEREDSOURCE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NONBUFFEREDSOURCE$8);
         }

         target.setBooleanValue(nonBufferedSource);
      }
   }

   public void xsetNonBufferedSource(XmlBoolean nonBufferedSource) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONBUFFEREDSOURCE$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(NONBUFFEREDSOURCE$8);
         }

         target.set(nonBufferedSource);
      }
   }

   public void unsetNonBufferedSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONBUFFEREDSOURCE$8, 0);
      }
   }

   public String getAcknowledgementInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(ACKNOWLEDGEMENTINTERVAL$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAcknowledgementInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACKNOWLEDGEMENTINTERVAL$10) != 0;
      }
   }

   public void setAcknowledgementInterval(String acknowledgementInterval) {
      this.generatedSetterHelperImpl(acknowledgementInterval, ACKNOWLEDGEMENTINTERVAL$10, 0, (short)1);
   }

   public String addNewAcknowledgementInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(ACKNOWLEDGEMENTINTERVAL$10);
         return target;
      }
   }

   public void unsetAcknowledgementInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACKNOWLEDGEMENTINTERVAL$10, 0);
      }
   }

   public String getSequenceExpiration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(SEQUENCEEXPIRATION$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSequenceExpiration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEQUENCEEXPIRATION$12) != 0;
      }
   }

   public void setSequenceExpiration(String sequenceExpiration) {
      this.generatedSetterHelperImpl(sequenceExpiration, SEQUENCEEXPIRATION$12, 0, (short)1);
   }

   public String addNewSequenceExpiration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(SEQUENCEEXPIRATION$12);
         return target;
      }
   }

   public void unsetSequenceExpiration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SEQUENCEEXPIRATION$12, 0);
      }
   }

   public int getBufferRetryCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BUFFERRETRYCOUNT$14, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetBufferRetryCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(BUFFERRETRYCOUNT$14, 0);
         return target;
      }
   }

   public boolean isSetBufferRetryCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BUFFERRETRYCOUNT$14) != 0;
      }
   }

   public void setBufferRetryCount(int bufferRetryCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BUFFERRETRYCOUNT$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BUFFERRETRYCOUNT$14);
         }

         target.setIntValue(bufferRetryCount);
      }
   }

   public void xsetBufferRetryCount(XmlInt bufferRetryCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(BUFFERRETRYCOUNT$14, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(BUFFERRETRYCOUNT$14);
         }

         target.set(bufferRetryCount);
      }
   }

   public void unsetBufferRetryCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BUFFERRETRYCOUNT$14, 0);
      }
   }

   public String getBufferRetryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(BUFFERRETRYDELAY$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetBufferRetryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BUFFERRETRYDELAY$16) != 0;
      }
   }

   public void setBufferRetryDelay(String bufferRetryDelay) {
      this.generatedSetterHelperImpl(bufferRetryDelay, BUFFERRETRYDELAY$16, 0, (short)1);
   }

   public String addNewBufferRetryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(BUFFERRETRYDELAY$16);
         return target;
      }
   }

   public void unsetBufferRetryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BUFFERRETRYDELAY$16, 0);
      }
   }

   public boolean getNonBufferedDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONBUFFEREDDESTINATION$18, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetNonBufferedDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONBUFFEREDDESTINATION$18, 0);
         return target;
      }
   }

   public boolean isSetNonBufferedDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONBUFFEREDDESTINATION$18) != 0;
      }
   }

   public void setNonBufferedDestination(boolean nonBufferedDestination) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONBUFFEREDDESTINATION$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NONBUFFEREDDESTINATION$18);
         }

         target.setBooleanValue(nonBufferedDestination);
      }
   }

   public void xsetNonBufferedDestination(XmlBoolean nonBufferedDestination) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONBUFFEREDDESTINATION$18, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(NONBUFFEREDDESTINATION$18);
         }

         target.set(nonBufferedDestination);
      }
   }

   public void unsetNonBufferedDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONBUFFEREDDESTINATION$18, 0);
      }
   }

   public String getMessagingQueueJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(MESSAGINGQUEUEJNDINAME$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessagingQueueJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGINGQUEUEJNDINAME$20) != 0;
      }
   }

   public void setMessagingQueueJndiName(String messagingQueueJndiName) {
      this.generatedSetterHelperImpl(messagingQueueJndiName, MESSAGINGQUEUEJNDINAME$20, 0, (short)1);
   }

   public String addNewMessagingQueueJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(MESSAGINGQUEUEJNDINAME$20);
         return target;
      }
   }

   public void unsetMessagingQueueJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGINGQUEUEJNDINAME$20, 0);
      }
   }

   public String getMessagingQueueMdbRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(MESSAGINGQUEUEMDBRUNASPRINCIPALNAME$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessagingQueueMdbRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGINGQUEUEMDBRUNASPRINCIPALNAME$22) != 0;
      }
   }

   public void setMessagingQueueMdbRunAsPrincipalName(String messagingQueueMdbRunAsPrincipalName) {
      this.generatedSetterHelperImpl(messagingQueueMdbRunAsPrincipalName, MESSAGINGQUEUEMDBRUNASPRINCIPALNAME$22, 0, (short)1);
   }

   public String addNewMessagingQueueMdbRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(MESSAGINGQUEUEMDBRUNASPRINCIPALNAME$22);
         return target;
      }
   }

   public void unsetMessagingQueueMdbRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGINGQUEUEMDBRUNASPRINCIPALNAME$22, 0);
      }
   }
}
