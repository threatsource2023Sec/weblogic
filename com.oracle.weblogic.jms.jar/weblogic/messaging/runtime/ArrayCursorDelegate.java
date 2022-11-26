package weblogic.messaging.runtime;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public class ArrayCursorDelegate extends CursorDelegate {
   protected transient Object[] entryArray;

   public ArrayCursorDelegate(CursorRuntimeImpl runtimeDelegate, OpenDataConverter converter, int timeout) {
      super(runtimeDelegate, converter, timeout);
      this.startPosition = this.endPosition = -1L;
   }

   public CompositeData[] getNext(int count) throws OpenDataException {
      int actualCount = count;
      if ((long)((int)this.getSize() - 1) - this.endPosition < (long)count) {
         actualCount = (int)this.getSize() - 1 - (int)this.endPosition;
      }

      return this.returnItems(this.endPosition + 1L, actualCount);
   }

   public CompositeData[] getPrevious(int count) throws OpenDataException {
      int actualCount = count;
      if (this.startPosition < (long)count) {
         actualCount = (int)this.startPosition;
      }

      return this.returnItems(this.startPosition - (long)actualCount, actualCount);
   }

   public CompositeData[] getItems(long start, int count) throws OpenDataException {
      this.updateAccessTime();
      if (start >= 0L && start < (long)this.entryArray.length) {
         this.startPosition = this.endPosition = start - 1L;
         return this.getNextItems(count);
      } else {
         throw new IndexOutOfBoundsException("Value of start argument (" + start + ") is invalid.  Start index must be between 0 and " + this.entryArray.length + " - 1.");
      }
   }

   protected CompositeData[] getPreviousItems(int count) throws OpenDataException {
      int actualCount = count;
      if (this.startPosition < (long)count) {
         actualCount = (int)this.startPosition;
      }

      return this.returnItems(this.startPosition - (long)actualCount, actualCount);
   }

   protected CompositeData[] getNextItems(int count) throws OpenDataException {
      int actualCount = count;
      if ((long)((int)this.getSize() - 1) - this.endPosition < (long)count) {
         actualCount = (int)this.getSize() - 1 - (int)this.endPosition;
      }

      return this.returnItems(this.endPosition + 1L, actualCount);
   }

   public Long getCursorSize() {
      this.updateAccessTime();
      return new Long(this.getSize());
   }

   private long getSize() {
      return (long)this.entryArray.length;
   }

   private CompositeData[] returnItems(long start, int count) throws OpenDataException {
      this.updateAccessTime();
      if (count == 0) {
         return null;
      } else {
         CompositeData[] entries = new CompositeData[count];

         for(int i = 0; i < count; ++i) {
            entries[i] = this.openDataConverter.createCompositeData(this.entryArray[i]);
         }

         this.startPosition = start;
         this.endPosition = this.startPosition + (long)count - 1L;
         return entries;
      }
   }
}
