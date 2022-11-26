package weblogic.entitlement.engine.cache;

import java.util.Iterator;
import java.util.Set;
import javax.security.auth.Subject;
import weblogic.security.spi.Resource;
import weblogic.utils.collections.SecondChanceCacheMap;

public class ResourceDecisionCache {
   private SecondChanceCacheMap cache;
   private ResourceTreeMap resourceMap;
   private ThreadLocal keys;

   public ResourceDecisionCache(int cacheSize) {
      this.cache = new SecondChanceCacheMap(cacheSize);
      this.resourceMap = new ResourceTreeMap();
      this.keys = new ThreadLocal() {
         protected Object initialValue() {
            return new Key((Resource)null, (Subject)null);
         }
      };
   }

   public Object lookupDecision(Resource resource, Subject subject) {
      Key key = (Key)this.keys.get();
      key.setValues(resource, subject);
      return this.cache.get(key);
   }

   public int size() {
      return this.cache.size();
   }

   public void cacheDecision(Resource resource, Subject subject, Object decision) {
      Key k = new Key(resource, subject);
      synchronized(this.resourceMap) {
         Key kickedKey = (Key)this.cache.insert(k, decision);
         if (kickedKey != null) {
            this.resourceMap.removeKey(kickedKey);
         }

         if (resource != null) {
            this.resourceMap.insertKey(resource, k);
         }

      }
   }

   public void uncacheDecision(Resource resource, Subject subject) {
      Key k = new Key(resource, subject);
      synchronized(this.resourceMap) {
         this.cache.remove(k);
         this.resourceMap.removeKey(k);
      }
   }

   public void uncache(String resource) {
      Set keys;
      synchronized(this.resourceMap) {
         keys = this.resourceMap.resourceChanged(resource);
      }

      if (keys != null) {
         Iterator it = keys.iterator();

         while(it.hasNext()) {
            Key k = (Key)it.next();
            this.cache.remove(k);
         }
      }

   }
}
