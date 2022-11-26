package weblogic.messaging.saf.internal;

import java.util.ArrayList;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.runtime.MessageCursorDelegate;
import weblogic.messaging.runtime.CursorRuntimeImpl;
import weblogic.messaging.runtime.OpenDataConverter;

public final class SAFMessageCursorDelegate extends MessageCursorDelegate {
   public SAFMessageCursorDelegate(CursorRuntimeImpl runtimeDelegate, OpenDataConverter converter, Cursor cursor, OpenDataConverter payloadConverter, int timeout) {
      super(runtimeDelegate, converter, cursor, payloadConverter, timeout);
   }

   public long sort(long start, String[] fields, Boolean[] ascending) {
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

            keyList.add(new SAFCursorKey(fields[i], ascendingOrder));
         }
      }

      this.cursorIterator.setComparator(new SAFMessageCursorComparator(keyList));
      this.cursorIterator.rewind();
      if (preSortElement != null) {
         this.cursorIterator.seek((MessageElement)preSortElement);
         return this.cursorIterator.getPosition();
      } else {
         return 0L;
      }
   }
}
