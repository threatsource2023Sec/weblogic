package weblogic.messaging.kernel.runtime;

import java.util.ArrayList;
import java.util.Comparator;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.runtime.CursorDelegate;
import weblogic.messaging.runtime.CursorRuntimeImpl;
import weblogic.messaging.runtime.OpenDataConverter;

public class MessageCursorDelegate extends CursorDelegate {
   protected OpenDataConverter messageOpenDataConverter;
   protected transient CursorIterator cursorIterator;
   protected transient ArrayList lastPageList;

   public MessageCursorDelegate(CursorRuntimeImpl runtimeDelegate, OpenDataConverter converter, Cursor cursor, OpenDataConverter messageConverter, int timeout) {
      super(runtimeDelegate, converter, timeout);
      this.messageOpenDataConverter = messageConverter;
      this.cursorIterator = new CursorIterator(cursor);
      this.startPosition = this.endPosition = 0L;
   }

   public CompositeData[] getNext(int count) throws OpenDataException {
      this.updateAccessTime();
      if (this.endPosition >= this.cursorIterator.size()) {
         return null;
      } else {
         int c = 0;
         this.lastPageList = new ArrayList();
         this.startPosition = this.endPosition;
         this.cursorIterator.seek(this.startPosition);

         do {
            this.lastPageList.add(this.cursorIterator.currentElement());
            ++c;
         } while(this.cursorIterator.next() != null && c < count);

         this.endPosition = this.startPosition + (long)c;
         return this.getCompositeDataArray(this.lastPageList);
      }
   }

   public CompositeData[] getPrevious(int count) throws OpenDataException {
      this.updateAccessTime();
      if (this.startPosition <= 0L) {
         this.startPosition = 0L;
         return null;
      } else {
         int c = 1;
         this.lastPageList = new ArrayList();
         this.endPosition = this.startPosition;
         this.cursorIterator.seek(this.startPosition - 1L);
         this.lastPageList.add(0, this.cursorIterator.currentElement());

         while(this.cursorIterator.previous() != null && c < count) {
            this.lastPageList.add(0, this.cursorIterator.currentElement());
            ++c;
         }

         this.startPosition = this.endPosition - (long)c;
         return this.getCompositeDataArray(this.lastPageList);
      }
   }

   public CompositeData[] getItems(long start, int count) throws OpenDataException {
      this.updateAccessTime();
      if (this.cursorIterator.size() == 0L) {
         return null;
      } else if (start >= 0L && start < this.cursorIterator.size()) {
         this.cursorIterator.seek(start);
         this.startPosition = this.endPosition = this.cursorIterator.getPosition();
         return this.getNext(count);
      } else {
         throw new IndexOutOfBoundsException("Value of start argument (" + start + ") is invalid.  Start index must be between 0 and " + this.cursorIterator.size() + " - 1.");
      }
   }

   public Long getCursorSize() {
      this.updateAccessTime();
      return new Long(this.cursorIterator.size());
   }

   public void close() {
      super.close();
      this.cursorIterator.close();
   }

   public CompositeData getMessage(String messageID) throws OpenDataException {
      this.updateAccessTime();
      MessageElement message = this.findMessage(new MessageIDMessageKey(messageID));
      return message == null ? null : this.messageOpenDataConverter.createCompositeData(message);
   }

   public CompositeData getMessage(long handle) throws OpenDataException {
      this.updateAccessTime();
      MessageElement message = this.findMessage(new HandleMessageKey(handle));
      return message == null ? null : this.messageOpenDataConverter.createCompositeData(message);
   }

   protected CompositeData[] getCompositeDataArray(ArrayList list) throws OpenDataException {
      CompositeData[] result = new CompositeData[list.size()];

      for(int i = 0; i < list.size(); ++i) {
         result[i] = this.openDataConverter.createCompositeData((MessageElement)list.get(i));
      }

      return result;
   }

   protected boolean compareMessageID(Message message, String messageID) {
      return message != null && message.getMessageID().toString().equals(messageID);
   }

   protected MessageElement findMessage(MessageKey key) {
      if (this.lastPageList != null) {
         for(int i = 0; i < this.lastPageList.size(); ++i) {
            MessageElement element = (MessageElement)this.lastPageList.get(i);
            if (key.matches(element)) {
               return element;
            }
         }
      }

      this.cursorIterator.rewind();

      while(this.cursorIterator.hasNext()) {
         MessageElement element = this.cursorIterator.currentElement();
         if (key.matches(element)) {
            return element;
         }

         this.cursorIterator.next();
      }

      return null;
   }

   public class CursorIterator {
      private Cursor cursor;
      private long currentPosition;
      private MessageElement currentElement;

      public CursorIterator(Cursor cursor) {
         this.cursor = cursor;
         this.rewind();
      }

      public void close() {
         this.cursor.close();
      }

      public boolean hasPrevious() {
         return this.currentPosition != 0L;
      }

      public boolean hasNext() {
         if (this.size() == 0L) {
            return false;
         } else {
            return this.currentPosition != this.size() - 1L;
         }
      }

      public MessageElement previous() {
         if (this.currentPosition == 0L) {
            return null;
         } else {
            try {
               MessageElement element = this.cursor.previous();
               if (element != null && element.getMessage().equals(this.currentElement.getMessage())) {
                  element = this.cursor.previous();
               }

               if (element != null) {
                  --this.currentPosition;
               }

               this.currentElement = element;
            } catch (KernelException var2) {
               this.currentElement = null;
            } catch (Exception var3) {
               this.currentElement = null;
            }

            return this.currentElement;
         }
      }

      public MessageElement next() {
         if (this.currentPosition == (long)(this.cursor.size() - 1)) {
            return null;
         } else {
            try {
               MessageElement element = this.cursor.next();
               if (element != null && element.getMessage().equals(this.currentElement.getMessage())) {
                  element = this.cursor.next();
               }

               if (element != null) {
                  ++this.currentPosition;
               }

               this.currentElement = element;
            } catch (KernelException var2) {
               this.currentElement = null;
            } catch (Exception var3) {
               this.currentElement = null;
            }

            return this.currentElement;
         }
      }

      public long getPosition() {
         return this.currentPosition;
      }

      public MessageElement currentElement() {
         return this.currentElement;
      }

      public MessageElement seek(long pos) {
         if (pos < this.size() && pos >= 0L) {
            while(this.getPosition() < pos) {
               this.next();
            }

            while(this.getPosition() > pos) {
               this.previous();
            }

            return this.currentElement;
         } else {
            return null;
         }
      }

      public MessageElement seek(MessageElement element) {
         this.rewind();

         while(this.currentElement() != element) {
            if (this.next() == null) {
               return null;
            }
         }

         return element;
      }

      public MessageElement rewind() {
         while(true) {
            try {
               if (this.cursor.previous() != null) {
                  continue;
               }

               this.currentElement = this.cursor.next();
            } catch (KernelException var2) {
            }

            this.currentPosition = 0L;
            return this.currentElement;
         }
      }

      public long size() {
         return (long)this.cursor.size();
      }

      public void setComparator(Comparator comparator) {
         this.cursor.setElementComparator(comparator);
      }
   }

   class MessageIDMessageKey implements MessageKey {
      String messageID;

      MessageIDMessageKey(String messageID) {
         this.messageID = messageID;
      }

      public boolean matches(MessageElement element) {
         Message message = element.getMessage();
         return MessageCursorDelegate.this.compareMessageID(message, this.messageID);
      }
   }

   class HandleMessageKey implements MessageKey {
      long handle;

      HandleMessageKey(long handle) {
         this.handle = handle;
      }

      public boolean matches(MessageElement element) {
         return element != null && element.getInternalSequenceNumber() == this.handle;
      }
   }

   interface MessageKey {
      boolean matches(MessageElement var1);
   }
}
