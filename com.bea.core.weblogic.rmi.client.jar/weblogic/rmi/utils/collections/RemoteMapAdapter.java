package weblogic.rmi.utils.collections;

import java.io.IOException;
import java.util.Map;
import weblogic.utils.collections.DelegatingMap;

public class RemoteMapAdapter extends DelegatingMap implements RemoteMap {
   public RemoteMapAdapter(Map m) {
      super(m);
   }

   public Map snapshot() throws IOException {
      return this.getDelegate();
   }
}
