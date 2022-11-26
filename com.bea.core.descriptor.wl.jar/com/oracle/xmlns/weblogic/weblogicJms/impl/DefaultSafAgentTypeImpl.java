package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlDouble;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DefaultSafAgentType;
import javax.xml.namespace.QName;

public class DefaultSafAgentTypeImpl extends XmlComplexContentImpl implements DefaultSafAgentType {
   private static final long serialVersionUID = 1L;
   private static final QName NOTES$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "notes");
   private static final QName BYTESMAXIMUM$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "bytes-maximum");
   private static final QName MESSAGESMAXIMUM$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "messages-maximum");
   private static final QName MAXIMUMMESSAGESIZE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "maximum-message-size");
   private static final QName DEFAULTRETRYDELAYBASE$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-retry-delay-base");
   private static final QName DEFAULTRETRYDELAYMAXIMUM$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-retry-delay-maximum");
   private static final QName DEFAULTRETRYDELAYMULTIPLIER$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-retry-delay-multiplier");
   private static final QName WINDOWSIZE$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "window-size");
   private static final QName LOGGINGENABLED$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "logging-enabled");
   private static final QName DEFAULTTIMETOLIVE$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-time-to-live");
   private static final QName MESSAGEBUFFERSIZE$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "message-buffer-size");
   private static final QName PAGINGDIRECTORY$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "paging-directory");
   private static final QName WINDOWINTERVAL$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "window-interval");

   public DefaultSafAgentTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NOTES$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$0, 0);
         return target;
      }
   }

   public boolean isNilNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NOTES$0) != 0;
      }
   }

   public void setNotes(String notes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NOTES$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NOTES$0);
         }

         target.setStringValue(notes);
      }
   }

   public void xsetNotes(XmlString notes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NOTES$0);
         }

         target.set(notes);
      }
   }

   public void setNilNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NOTES$0);
         }

         target.setNil();
      }
   }

   public void unsetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NOTES$0, 0);
      }
   }

   public long getBytesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTESMAXIMUM$2, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetBytesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(BYTESMAXIMUM$2, 0);
         return target;
      }
   }

   public boolean isSetBytesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BYTESMAXIMUM$2) != 0;
      }
   }

   public void setBytesMaximum(long bytesMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTESMAXIMUM$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BYTESMAXIMUM$2);
         }

         target.setLongValue(bytesMaximum);
      }
   }

   public void xsetBytesMaximum(XmlLong bytesMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(BYTESMAXIMUM$2, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(BYTESMAXIMUM$2);
         }

         target.set(bytesMaximum);
      }
   }

   public void unsetBytesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BYTESMAXIMUM$2, 0);
      }
   }

   public long getMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESMAXIMUM$4, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGESMAXIMUM$4, 0);
         return target;
      }
   }

   public boolean isSetMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGESMAXIMUM$4) != 0;
      }
   }

   public void setMessagesMaximum(long messagesMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESMAXIMUM$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGESMAXIMUM$4);
         }

         target.setLongValue(messagesMaximum);
      }
   }

   public void xsetMessagesMaximum(XmlLong messagesMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGESMAXIMUM$4, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(MESSAGESMAXIMUM$4);
         }

         target.set(messagesMaximum);
      }
   }

   public void unsetMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGESMAXIMUM$4, 0);
      }
   }

   public int getMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$6, 0);
         return target;
      }
   }

   public boolean isSetMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXIMUMMESSAGESIZE$6) != 0;
      }
   }

   public void setMaximumMessageSize(int maximumMessageSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXIMUMMESSAGESIZE$6);
         }

         target.setIntValue(maximumMessageSize);
      }
   }

   public void xsetMaximumMessageSize(XmlInt maximumMessageSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXIMUMMESSAGESIZE$6);
         }

         target.set(maximumMessageSize);
      }
   }

   public void unsetMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXIMUMMESSAGESIZE$6, 0);
      }
   }

   public long getDefaultRetryDelayBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTRETRYDELAYBASE$8, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetDefaultRetryDelayBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(DEFAULTRETRYDELAYBASE$8, 0);
         return target;
      }
   }

   public boolean isSetDefaultRetryDelayBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTRETRYDELAYBASE$8) != 0;
      }
   }

   public void setDefaultRetryDelayBase(long defaultRetryDelayBase) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTRETRYDELAYBASE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTRETRYDELAYBASE$8);
         }

         target.setLongValue(defaultRetryDelayBase);
      }
   }

   public void xsetDefaultRetryDelayBase(XmlLong defaultRetryDelayBase) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(DEFAULTRETRYDELAYBASE$8, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(DEFAULTRETRYDELAYBASE$8);
         }

         target.set(defaultRetryDelayBase);
      }
   }

   public void unsetDefaultRetryDelayBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTRETRYDELAYBASE$8, 0);
      }
   }

   public long getDefaultRetryDelayMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTRETRYDELAYMAXIMUM$10, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetDefaultRetryDelayMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(DEFAULTRETRYDELAYMAXIMUM$10, 0);
         return target;
      }
   }

   public boolean isSetDefaultRetryDelayMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTRETRYDELAYMAXIMUM$10) != 0;
      }
   }

   public void setDefaultRetryDelayMaximum(long defaultRetryDelayMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTRETRYDELAYMAXIMUM$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTRETRYDELAYMAXIMUM$10);
         }

         target.setLongValue(defaultRetryDelayMaximum);
      }
   }

   public void xsetDefaultRetryDelayMaximum(XmlLong defaultRetryDelayMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(DEFAULTRETRYDELAYMAXIMUM$10, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(DEFAULTRETRYDELAYMAXIMUM$10);
         }

         target.set(defaultRetryDelayMaximum);
      }
   }

   public void unsetDefaultRetryDelayMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTRETRYDELAYMAXIMUM$10, 0);
      }
   }

   public double getDefaultRetryDelayMultiplier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTRETRYDELAYMULTIPLIER$12, 0);
         return target == null ? 0.0 : target.getDoubleValue();
      }
   }

   public XmlDouble xgetDefaultRetryDelayMultiplier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlDouble target = null;
         target = (XmlDouble)this.get_store().find_element_user(DEFAULTRETRYDELAYMULTIPLIER$12, 0);
         return target;
      }
   }

   public boolean isSetDefaultRetryDelayMultiplier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTRETRYDELAYMULTIPLIER$12) != 0;
      }
   }

   public void setDefaultRetryDelayMultiplier(double defaultRetryDelayMultiplier) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTRETRYDELAYMULTIPLIER$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTRETRYDELAYMULTIPLIER$12);
         }

         target.setDoubleValue(defaultRetryDelayMultiplier);
      }
   }

   public void xsetDefaultRetryDelayMultiplier(XmlDouble defaultRetryDelayMultiplier) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlDouble target = null;
         target = (XmlDouble)this.get_store().find_element_user(DEFAULTRETRYDELAYMULTIPLIER$12, 0);
         if (target == null) {
            target = (XmlDouble)this.get_store().add_element_user(DEFAULTRETRYDELAYMULTIPLIER$12);
         }

         target.set(defaultRetryDelayMultiplier);
      }
   }

   public void unsetDefaultRetryDelayMultiplier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTRETRYDELAYMULTIPLIER$12, 0);
      }
   }

   public int getWindowSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WINDOWSIZE$14, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetWindowSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(WINDOWSIZE$14, 0);
         return target;
      }
   }

   public boolean isSetWindowSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WINDOWSIZE$14) != 0;
      }
   }

   public void setWindowSize(int windowSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WINDOWSIZE$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(WINDOWSIZE$14);
         }

         target.setIntValue(windowSize);
      }
   }

   public void xsetWindowSize(XmlInt windowSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(WINDOWSIZE$14, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(WINDOWSIZE$14);
         }

         target.set(windowSize);
      }
   }

   public void unsetWindowSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WINDOWSIZE$14, 0);
      }
   }

   public boolean getLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGGINGENABLED$16, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(LOGGINGENABLED$16, 0);
         return target;
      }
   }

   public boolean isSetLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGGINGENABLED$16) != 0;
      }
   }

   public void setLoggingEnabled(boolean loggingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGGINGENABLED$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOGGINGENABLED$16);
         }

         target.setBooleanValue(loggingEnabled);
      }
   }

   public void xsetLoggingEnabled(XmlBoolean loggingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(LOGGINGENABLED$16, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(LOGGINGENABLED$16);
         }

         target.set(loggingEnabled);
      }
   }

   public void unsetLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGGINGENABLED$16, 0);
      }
   }

   public long getDefaultTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTTIMETOLIVE$18, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetDefaultTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(DEFAULTTIMETOLIVE$18, 0);
         return target;
      }
   }

   public boolean isSetDefaultTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTTIMETOLIVE$18) != 0;
      }
   }

   public void setDefaultTimeToLive(long defaultTimeToLive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTTIMETOLIVE$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTTIMETOLIVE$18);
         }

         target.setLongValue(defaultTimeToLive);
      }
   }

   public void xsetDefaultTimeToLive(XmlLong defaultTimeToLive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(DEFAULTTIMETOLIVE$18, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(DEFAULTTIMETOLIVE$18);
         }

         target.set(defaultTimeToLive);
      }
   }

   public void unsetDefaultTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTTIMETOLIVE$18, 0);
      }
   }

   public long getMessageBufferSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGEBUFFERSIZE$20, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetMessageBufferSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGEBUFFERSIZE$20, 0);
         return target;
      }
   }

   public boolean isSetMessageBufferSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEBUFFERSIZE$20) != 0;
      }
   }

   public void setMessageBufferSize(long messageBufferSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGEBUFFERSIZE$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGEBUFFERSIZE$20);
         }

         target.setLongValue(messageBufferSize);
      }
   }

   public void xsetMessageBufferSize(XmlLong messageBufferSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGEBUFFERSIZE$20, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(MESSAGEBUFFERSIZE$20);
         }

         target.set(messageBufferSize);
      }
   }

   public void unsetMessageBufferSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEBUFFERSIZE$20, 0);
      }
   }

   public String getPagingDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PAGINGDIRECTORY$22, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPagingDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PAGINGDIRECTORY$22, 0);
         return target;
      }
   }

   public boolean isNilPagingDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PAGINGDIRECTORY$22, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPagingDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PAGINGDIRECTORY$22) != 0;
      }
   }

   public void setPagingDirectory(String pagingDirectory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PAGINGDIRECTORY$22, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PAGINGDIRECTORY$22);
         }

         target.setStringValue(pagingDirectory);
      }
   }

   public void xsetPagingDirectory(XmlString pagingDirectory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PAGINGDIRECTORY$22, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PAGINGDIRECTORY$22);
         }

         target.set(pagingDirectory);
      }
   }

   public void setNilPagingDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PAGINGDIRECTORY$22, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PAGINGDIRECTORY$22);
         }

         target.setNil();
      }
   }

   public void unsetPagingDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PAGINGDIRECTORY$22, 0);
      }
   }

   public long getWindowInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WINDOWINTERVAL$24, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetWindowInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(WINDOWINTERVAL$24, 0);
         return target;
      }
   }

   public boolean isSetWindowInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WINDOWINTERVAL$24) != 0;
      }
   }

   public void setWindowInterval(long windowInterval) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WINDOWINTERVAL$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(WINDOWINTERVAL$24);
         }

         target.setLongValue(windowInterval);
      }
   }

   public void xsetWindowInterval(XmlLong windowInterval) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(WINDOWINTERVAL$24, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(WINDOWINTERVAL$24);
         }

         target.set(windowInterval);
      }
   }

   public void unsetWindowInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WINDOWINTERVAL$24, 0);
      }
   }
}
