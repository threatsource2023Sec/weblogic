package weblogic.diagnostics.collections;

import java.rmi.Remote;

public interface BulkIterator extends Remote {
   boolean hasNext();

   Object[] fetchNext(int var1);
}
