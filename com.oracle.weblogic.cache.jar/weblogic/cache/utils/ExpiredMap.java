package weblogic.cache.utils;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.messaging.util.AbstractElement;
import weblogic.messaging.util.List;

public class ExpiredMap extends AbstractElement implements Map, Cloneable {
   private List list;
   private Map map;
   private int maxCapacity;
   private long ttl;

   public ExpiredMap(int max, Map delegate, long timeToLive) {
      this.maxCapacity = max;
      this.ttl = timeToLive;
      this.map = delegate;
      this.list = new List();
   }

   public synchronized int size() {
      return this.map.size();
   }

   public synchronized boolean isEmpty() {
      return this.map.isEmpty();
   }

   public synchronized boolean containsKey(Object key) {
      return this.map.containsKey(key);
   }

   public synchronized boolean equals(Object o) {
      return this.map.equals(o);
   }

   public synchronized int hashCode() {
      return this.map.hashCode();
   }

   public synchronized void putAll(Map map) {
      Iterator i = map.keySet().iterator();

      while(i.hasNext()) {
         Object key = i.next();
         this.put(key, map.get(key));
      }

   }

   public synchronized Object clone() {
      ExpiredMap result;
      try {
         result = (ExpiredMap)super.clone();
      } catch (CloneNotSupportedException var4) {
         throw new RuntimeException(var4.getMessage(), var4);
      }

      result.list = new List();
      result.map.clear();

      for(Node node = (Node)this.list.getFirst(); node != null; node = (Node)node.getNext()) {
         Node cloneNode = new Node(node.getKey(), node.getValue());
         result.list.add(cloneNode);
         result.map.put(node.getKey(), cloneNode);
         cloneNode.setExpiration(node.getExpiration());
      }

      return result;
   }

   public synchronized void clear() {
      this.map.clear();
      this.list.clear();
   }

   private void evictExpiredOrOverMaxCapacity() {
      long now = System.currentTimeMillis();
      Node node = (Node)this.list.getFirst();

      while(node != null && (node.getExpiration() <= now || this.list.size() > this.maxCapacity)) {
         Node current = node;
         node = (Node)node.getNext();
         this.evictOne(current);
      }

   }

   private void evictOne(Node node) {
      assert node.getList() == this.list;

      Node old = (Node)this.list.remove(node);

      assert old == node;

      old = (Node)this.map.remove(node.getKey());

      assert old == node;

   }

   public synchronized boolean containsValue(Object value) {
      long now = System.currentTimeMillis();
      Node node = (Node)this.list.getFirst();

      while(true) {
         while(node != null) {
            Node current = node;
            node = (Node)node.getNext();
            if (current.getExpiration() < now) {
               if (current.getValue() == value) {
                  this.list.remove(current);
                  current.append();
                  this.evictExpiredOrOverMaxCapacity();
                  return true;
               }

               this.evictOne(current);
            } else {
               for(node = (Node)this.list.getLast(); node != null; node = (Node)node.getPrev()) {
                  if (current.getValue() == value) {
                     this.list.remove(current);
                     current.append();
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public synchronized Object get(Object key) {
      Node node = (Node)this.map.get(key);
      if (node == null) {
         this.evictExpiredOrOverMaxCapacity();
         return null;
      } else {
         this.list.remove(node);
         node.append();
         this.evictExpiredOrOverMaxCapacity();
         return node.getValue();
      }
   }

   public synchronized Object put(Object key, Object value) {
      Node node = (Node)this.map.remove(key);
      if (node == null) {
         node = new Node(key, value);
         node.append();
         this.evictExpiredOrOverMaxCapacity();
         return this.map.put(key, node);
      } else {
         this.list.remove(node);
         node.append();
         this.evictExpiredOrOverMaxCapacity();
         return node.setValue(value);
      }
   }

   public synchronized Object putIfAbsent(Object key, Object value) {
      Node node = (Node)this.map.get(key);
      if (node == null) {
         node = new Node(key, value);
         node.append();
         this.evictExpiredOrOverMaxCapacity();
         return this.map.put(key, node);
      } else {
         this.list.remove(node);
         node.append();
         this.evictExpiredOrOverMaxCapacity();
         return node.getValue();
      }
   }

   public synchronized Object remove(Object key) {
      Node node = (Node)this.map.remove(key);
      if (node == null) {
         this.evictExpiredOrOverMaxCapacity();
         return null;
      } else {
         this.list.remove(node);
         this.evictExpiredOrOverMaxCapacity();
         return node.getValue();
      }
   }

   public synchronized Set keySet() {
      return new AbstractSet() {
         public Iterator iterator() {
            return new IteratorImpl() {
               Object nextImpl(Node current) {
                  return current.getKey();
               }
            };
         }

         public int size() {
            return ExpiredMap.this.size();
         }

         public boolean contains(Object v) {
            return ExpiredMap.this.containsValue(v);
         }
      };
   }

   public synchronized Collection values() {
      return new AbstractCollection() {
         public Iterator iterator() {
            return new IteratorImpl() {
               Object nextImpl(Node current) {
                  return current.getValue();
               }
            };
         }

         public int size() {
            return ExpiredMap.this.size();
         }

         public boolean contains(Object v) {
            return ExpiredMap.this.containsValue(v);
         }
      };
   }

   public synchronized Set entrySet() {
      return new AbstractSet() {
         public Iterator iterator() {
            return new IteratorImpl() {
               Object nextImpl(Node current) {
                  return current;
               }
            };
         }

         public boolean contains(Object o) {
            synchronized(ExpiredMap.this) {
               if (o instanceof Node && ExpiredMap.this.list.contains((Node)o)) {
                  return true;
               } else if (!(o instanceof Map.Entry)) {
                  return false;
               } else {
                  Map.Entry e = (Map.Entry)o;
                  Node node = (Node)ExpiredMap.this.map.get(e.getKey());
                  return node != null && node.equals(e);
               }
            }
         }

         public boolean remove(Object o) {
            synchronized(ExpiredMap.this) {
               if (!(o instanceof Map.Entry)) {
                  return false;
               } else {
                  Node node = (Node)ExpiredMap.this.map.remove(((Map.Entry)o).getKey());
                  if (node == null) {
                     return false;
                  } else {
                     ExpiredMap.this.list.remove(node);
                     return true;
                  }
               }
            }
         }

         public int size() {
            return ExpiredMap.this.size();
         }

         public void clear() {
            synchronized(ExpiredMap.this) {
               ExpiredMap.this.clear();
            }
         }
      };
   }

   private abstract class IteratorImpl implements Iterator {
      private Node node;

      private IteratorImpl() {
         this.node = (Node)ExpiredMap.this.list.getFirst();
      }

      public boolean hasNext() {
         synchronized(ExpiredMap.this) {
            return this.node != null;
         }
      }

      abstract Object nextImpl(Node var1);

      public Object next() {
         synchronized(ExpiredMap.this) {
            Node current = this.node;
            if (current == null) {
               throw new IllegalStateException("no next");
            } else {
               this.node = (Node)current.getNext();
               return this.nextImpl(current);
            }
         }
      }

      public void remove() {
         synchronized(ExpiredMap.this) {
            Node current = this.node;
            if (current == null) {
               throw new IllegalStateException("no next");
            } else {
               this.node = (Node)current.getNext();
               ExpiredMap.this.list.remove(current);
               ExpiredMap.this.map.remove(current.getKey());
            }
         }
      }

      // $FF: synthetic method
      IteratorImpl(Object x1) {
         this();
      }
   }

   private class Node extends AbstractElement implements Map.Entry {
      private final Object key;
      private Object value;
      private long expiration;

      private Node(Object key, Object value) {
         this.key = key;
         this.value = value;
         this.expiration = System.currentTimeMillis() + ExpiredMap.this.ttl;
      }

      private void append() {
         ExpiredMap.this.list.add(this);
         this.expiration = System.currentTimeMillis() + ExpiredMap.this.ttl;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object o) {
         Object old = this.value;
         this.value = o;
         return old;
      }

      public long getExpiration() {
         return this.expiration;
      }

      public void setExpiration(long argExpire) {
         this.expiration = argExpire;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            boolean var10000;
            label47: {
               Map.Entry e = (Map.Entry)o;
               if (this.key == null) {
                  if (e.getKey() != null) {
                     break label47;
                  }
               } else if (this.key != e.getKey() && !this.key.equals(e.getKey())) {
                  break label47;
               }

               if (this.value == null) {
                  if (e.getValue() != null) {
                     break label47;
                  }
               } else if (this.value != e.getValue() && !this.value.equals(e.getValue())) {
                  break label47;
               }

               var10000 = true;
               return var10000;
            }

            var10000 = false;
            return var10000;
         }
      }

      public int hashCode() {
         return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public String toString() {
         return super.toString() + " - key: " + this.key + " value: " + this.value;
      }

      // $FF: synthetic method
      Node(Object x1, Object x2, Object x3) {
         this(x1, x2);
      }
   }
}
