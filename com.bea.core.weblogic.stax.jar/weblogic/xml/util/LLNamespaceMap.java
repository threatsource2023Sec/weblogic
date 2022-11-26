package weblogic.xml.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LLNamespaceMap extends HashMap {
   private Map namespaceMap;
   private LLNamespaceMap predecessor;

   public LLNamespaceMap(Map namespaceMap, LLNamespaceMap predecessor) {
      this.namespaceMap = namespaceMap;
      this.predecessor = predecessor;
   }

   public Map getNamespaceMap() {
      return this.namespaceMap;
   }

   private LLNamespaceMap getPredecessor() {
      return this.predecessor;
   }

   public void setPredecessor(LLNamespaceMap predecessor) {
      this.predecessor = predecessor;
   }

   public void clear() {
      this.namespaceMap.clear();

      for(LLNamespaceMap predecessor = this.getPredecessor(); predecessor != null; predecessor = predecessor.getPredecessor()) {
         predecessor.getNamespaceMap().clear();
      }

   }

   public boolean containsKey(Object key) {
      boolean ret = false;
      ret = this.namespaceMap.containsKey(key);
      if (!ret && this.getPredecessor() != null) {
         ret = this.getPredecessor().containsKey(key);
      }

      return ret;
   }

   public boolean containsValue(Object value) {
      boolean ret = false;
      ret = this.namespaceMap.containsValue(value);
      if (!ret && this.getPredecessor() != null) {
         ret = this.getPredecessor().containsValue(value);
      }

      return ret;
   }

   public Set entrySet() {
      HashSet result = new HashSet();
      HashSet keys = new HashSet();
      Iterator it = this.getNamespaceMap().entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry entry = (Map.Entry)it.next();
         result.add(entry);
         keys.add(entry.getKey());
      }

      for(LLNamespaceMap predecessor = this.getPredecessor(); predecessor != null; predecessor = predecessor.getPredecessor()) {
         Iterator it = predecessor.getNamespaceMap().entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String name = (String)entry.getKey();
            if (!keys.contains(name)) {
               result.add(entry);
               keys.add(entry.getKey());
            }
         }
      }

      return result;
   }

   public Object get(Object key) {
      Object value = this.namespaceMap.get(key);
      if (value == null && this.getPredecessor() != null) {
         value = this.getPredecessor().get(key);
      }

      return value;
   }

   public Set keySet() {
      HashSet result = new HashSet();
      Iterator it = this.getNamespaceMap().entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry entry = (Map.Entry)it.next();
         Object name = entry.getKey();
         result.add(name);
      }

      for(LLNamespaceMap predecessor = this.getPredecessor(); predecessor != null; predecessor = predecessor.getPredecessor()) {
         Iterator it = predecessor.getNamespaceMap().entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String name = (String)entry.getKey();
            if (!result.contains(name)) {
               result.add(name);
            }
         }
      }

      return result;
   }

   public Object put(Object key, Object value) {
      return this.namespaceMap.put(key, value);
   }

   public void putAll(Map map) {
      this.namespaceMap.putAll(map);
   }

   public Object remove(Object key) {
      Object obj = this.namespaceMap.remove(key);
      if (obj == null && this.getPredecessor() != null) {
         obj = this.getPredecessor().remove(key);
      }

      return obj;
   }

   public int size() {
      return this.entrySet().size();
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   private Map getNamespaceMaps(LLNamespaceMap map) {
      Map result = map.getNamespaceMap();

      for(LLNamespaceMap predecessor = map.getPredecessor(); predecessor != null; predecessor = predecessor.getPredecessor()) {
         Iterator it = predecessor.getNamespaceMap().entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String name = (String)entry.getKey();
            String value = (String)entry.getValue();
            if (!result.containsKey(name)) {
               result.put(name, value);
            }
         }
      }

      return result;
   }

   public String toString() {
      return this.getNamespaceMaps(this).toString();
   }
}
