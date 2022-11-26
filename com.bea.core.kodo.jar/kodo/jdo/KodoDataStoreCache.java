package kodo.jdo;

import java.util.Collection;
import javax.jdo.datastore.DataStoreCache;

public interface KodoDataStoreCache extends DataStoreCache {
   String NAME_DEFAULT = "default";

   boolean contains(Object var1);

   boolean containsAll(Collection var1);

   boolean containsAll(Object[] var1);
}
