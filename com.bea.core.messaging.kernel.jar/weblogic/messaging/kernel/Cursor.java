package weblogic.messaging.kernel;

import java.util.Comparator;

public interface Cursor {
   int VISIBLE = 1;
   int SEND = 2;
   int RECEIVE = 4;
   int TRANSACTION = 8;
   int ORDERED = 16;
   int DELAYED = 32;
   int EXPIRED = 64;
   int REDELIVERY_COUNT_EXCEEDED = 128;
   int PAUSED = 256;
   int SEQUENCED = 512;
   int UNIT_OF_WORK_COMPONENT = 1024;
   int DELETED = 1073741824;
   int ALL = Integer.MAX_VALUE;

   void close();

   MessageElement next() throws KernelException;

   MessageElement next(boolean var1) throws KernelException;

   MessageElement previous() throws KernelException;

   void setComparator(Comparator var1) throws UnsupportedOperationException;

   void setElementComparator(Comparator var1) throws UnsupportedOperationException;

   int size();
}
