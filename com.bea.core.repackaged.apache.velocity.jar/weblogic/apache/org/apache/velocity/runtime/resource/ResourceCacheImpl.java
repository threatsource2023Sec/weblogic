package weblogic.apache.org.apache.velocity.runtime.resource;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;

public class ResourceCacheImpl implements ResourceCache {
   protected Map cache = new Hashtable();
   protected RuntimeServices rsvc = null;

   public void initialize(RuntimeServices rs) {
      this.rsvc = rs;
      this.rsvc.info("ResourceCache : initialized. (" + this.getClass() + ")");
   }

   public Resource get(Object key) {
      return (Resource)this.cache.get(key);
   }

   public Resource put(Object key, Resource value) {
      return (Resource)this.cache.put(key, value);
   }

   public Resource remove(Object key) {
      return (Resource)this.cache.remove(key);
   }

   public Iterator enumerateKeys() {
      return this.cache.keySet().iterator();
   }
}
