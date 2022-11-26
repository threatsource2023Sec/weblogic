package weblogic.jms.backend;

import java.util.ArrayList;
import javax.jms.JMSException;
import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.runtime.MessageCursorDelegate;
import weblogic.messaging.runtime.CursorRuntimeImpl;
import weblogic.messaging.runtime.OpenDataConverter;

public class JMSMessageCursorDelegate extends MessageCursorDelegate {
   private BEDestinationImpl destination;

   public JMSMessageCursorDelegate(CursorRuntimeImpl runtimeDelegate, OpenDataConverter messageMetaDataConverter, Cursor cursor, OpenDataConverter messageBodyConverter, BEDestinationImpl destination, int timeout) {
      super(runtimeDelegate, messageMetaDataConverter, cursor, messageBodyConverter, timeout);
      this.destination = destination;
   }

   public JMSMessageCursorDelegate(CursorRuntimeImpl runtimeDelegate, OpenDataConverter messageMetaDataConverter, Cursor cursor, OpenDataConverter messageBodyConverter, int timeout) {
      super(runtimeDelegate, messageMetaDataConverter, cursor, messageBodyConverter, timeout);
   }

   public long sort(long start, String[] fields, Boolean[] ascending) throws ModuleException {
      this.updateAccessTime();
      MessageElement preSortElement = null;
      if (start != -1L) {
         this.cursorIterator.seek(start);
      }

      ArrayList keyList = new ArrayList();
      if (fields != null) {
         for(int i = 0; i < fields.length; ++i) {
            boolean ascendingOrder = true;
            if (ascending != null && i < ascending.length && !ascending[i]) {
               ascendingOrder = false;
            }

            keyList.add(new BECursorDestinationKey(this.destination, new CursorDestinationKeyBean(fields[i], ascendingOrder)));
         }
      }

      this.cursorIterator.setComparator(new JMSMessageCursorComparator(keyList));
      this.cursorIterator.rewind();
      if (preSortElement != null) {
         this.cursorIterator.seek((MessageElement)preSortElement);
         return this.cursorIterator.getPosition();
      } else {
         return 0L;
      }
   }

   protected boolean compareMessageID(Message message, String messageID) {
      if (message != null && message instanceof javax.jms.Message) {
         javax.jms.Message jmsMessage = (javax.jms.Message)message;

         try {
            return jmsMessage.getJMSMessageID().equals(messageID);
         } catch (JMSException var5) {
            return false;
         }
      } else {
         return false;
      }
   }

   class CursorDestinationKeyBean implements DestinationKeyBean {
      String name;
      String property;
      String order = "Ascending";

      CursorDestinationKeyBean(String property, boolean ascending) {
         this.property = this.name = property;
         if (!ascending) {
            this.order = "Descending";
         }

      }

      public String getKeyType() {
         return "String";
      }

      public void setKeyType(String s) {
      }

      public boolean isSetType() {
         return false;
      }

      public void unsetType() {
      }

      public String getProperty() {
         return this.property;
      }

      public void setProperty(String s) {
         this.property = s;
      }

      public boolean isSetProperty() {
         return this.property != null;
      }

      public void unsetProperty() {
         this.property = null;
      }

      public String getSortOrder() {
         return this.order;
      }

      public void setSortOrder(String s) {
         this.order = s;
      }

      public boolean isSetSortOrder() {
         return this.order != null;
      }

      public void unsetSortOrder() {
         this.order = null;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String s) {
         this.name = s;
      }

      public boolean isSetName() {
         return this.name != null;
      }

      public void unsetName() {
         this.name = null;
      }

      public String getNotes() {
         return null;
      }

      public long getId() {
         return 0L;
      }

      public void setNotes(String notes) {
      }

      public boolean isSet(String property) {
         return true;
      }

      public void unSet(String property) {
      }
   }
}
