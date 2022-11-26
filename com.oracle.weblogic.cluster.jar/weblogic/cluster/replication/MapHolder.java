package weblogic.cluster.replication;

import java.util.Map;
import javax.naming.NamingException;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.internal.NamingNode;

public class MapHolder implements Aggregatable {
   private Map map;

   public MapHolder(Map map) {
      this.map = map;
   }

   public Map getMap() {
      return this.map;
   }

   public void onBind(NamingNode store, String name, Aggregatable other) throws NamingException {
      if (other != null && other instanceof MapHolder) {
         this.map = ((MapHolder)other).getMap();
      }

   }

   public void onRebind(NamingNode store, String name, Aggregatable other) throws NamingException {
      if (other != null && other instanceof MapHolder) {
         this.map = ((MapHolder)other).getMap();
      }

   }

   public boolean onUnbind(NamingNode store, String name, Aggregatable other) throws NamingException {
      if (other == null) {
         this.map = null;
         return true;
      } else {
         return false;
      }
   }
}
