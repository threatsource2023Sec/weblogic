package weblogic.cache.webapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CacheMonitor implements CacheListener, ServletContextListener {
   public static final String ATTRIBUTE = "weblogic.cache.CacheMonitor";
   private Node root = new Node();
   private static final int UPDATE = 1;
   private static final int ACCESS = 2;
   private static final int FLUSH = 3;

   public void contextInitialized(ServletContextEvent sce) {
      ServletContext sc = sce.getServletContext();
      List listeners = (List)sc.getAttribute("weblogic.cache.CacheListener");
      if (listeners == null) {
         listeners = new ArrayList();
         sc.setAttribute("weblogic.cache.CacheListener", listeners);
      }

      ((List)listeners).add(this);
      sc.setAttribute("weblogic.cache.CacheMonitor", this);
   }

   public void contextDestroyed(ServletContextEvent sce) {
   }

   public void cacheUpdateOccurred(CacheListener.CacheEvent ce) {
      this.update(1, ce);
   }

   public void cacheAccessOccurred(CacheListener.CacheEvent ce) {
      this.update(2, ce);
   }

   public void cacheFlushOccurred(CacheListener.CacheEvent ce) {
      this.update(3, ce);
   }

   private void update(int type, CacheListener.CacheEvent ce) {
      this.update(this.root, type, ce);
      String scopeKey = ce.getScopeType() + " " + ce.getScope();
      Node scope = this.getSubNode(this.root, scopeKey);
      this.update(scope, type, ce);
      String nameKey = ce.getName();
      if (nameKey != null) {
         Node name = this.getSubNode(scope, nameKey);
         this.update(name, type, ce);
         Object keySetKey = ce.getKeySet();
         if (keySetKey != null) {
            Node keySet = this.getSubNode(name, keySetKey);
            this.update(keySet, type, ce);
         }
      }
   }

   private void update(Node node, int type, CacheListener.CacheEvent ce) {
      synchronized(node) {
         switch (type) {
            case 1:
               node.updates++;
               node.updateTime = node.updateTime + ce.getTime();
               break;
            case 2:
               node.accesses++;
               node.accessTime = node.accessTime + ce.getTime();
               break;
            case 3:
               node.flushes++;
         }

      }
   }

   private Node getSubNode(Node root, Object key) {
      synchronized(root) {
         Node node = root.getNode(key);
         if (node == null) {
            node = new Node();
            root.subMap.put(key, node);
         }

         return node;
      }
   }

   public Node getRoot() {
      return this.root;
   }

   public static class Node {
      private int updates;
      private int accesses;
      private int flushes;
      private int updateTime;
      private int accessTime;
      private Map subMap = new HashMap();

      public int getUpdates() {
         return this.updates;
      }

      public int getAccesses() {
         return this.accesses;
      }

      public int getFlushes() {
         return this.flushes;
      }

      public int getUpdateTime() {
         return this.updateTime;
      }

      public int getAccessTime() {
         return this.accessTime;
      }

      public synchronized Object[] getKeys() {
         return this.subMap.keySet().toArray();
      }

      public synchronized Node getNode(Object key) {
         return (Node)this.subMap.get(key);
      }
   }
}
