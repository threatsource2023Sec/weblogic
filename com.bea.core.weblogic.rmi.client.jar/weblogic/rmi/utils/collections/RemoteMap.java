package weblogic.rmi.utils.collections;

import java.io.IOException;
import java.rmi.Remote;
import java.util.Map;

public interface RemoteMap extends Remote {
   int size() throws IOException;

   boolean isEmpty() throws IOException;

   boolean containsKey(Object var1) throws IOException;

   boolean containsValue(Object var1) throws IOException;

   Object get(Object var1) throws IOException;

   Object put(Object var1, Object var2) throws IOException;

   Object remove(Object var1) throws IOException;

   void putAll(Map var1) throws IOException;

   void clear() throws IOException;

   Map snapshot() throws IOException;
}
