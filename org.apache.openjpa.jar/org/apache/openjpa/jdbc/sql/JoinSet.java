package org.apache.openjpa.jdbc.sql;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.lang.ObjectUtils;

class JoinSet {
   private final List _graph = new ArrayList();
   private int _size = 0;
   private List _sorted = null;

   public JoinSet() {
   }

   public JoinSet(JoinSet copy) {
      for(int i = 0; i < copy._graph.size(); ++i) {
         if (copy._graph.get(i) == null) {
            this._graph.add((Object)null);
         } else {
            this._graph.add(((Node)copy._graph.get(i)).clone());
         }
      }

      this._size = copy._size;
      this._sorted = copy._sorted;
   }

   public Join getRecordedJoin(Join join) {
      if (join == null) {
         return null;
      } else {
         Node node = this.getNode(join, join.getIndex1());
         return node == null ? null : node.join;
      }
   }

   private Node getNode(Join join, int idx) {
      if (this._graph.size() <= idx) {
         return null;
      } else {
         for(Node node = (Node)this._graph.get(idx); node != null; node = node.next) {
            if (node.join.equals(join)) {
               return node;
            }
         }

         return null;
      }
   }

   public Join last() {
      if (this._size == 0) {
         return null;
      } else {
         Node node;
         for(node = (Node)this._graph.get(this._graph.size() - 1); node.next != null; node = node.next) {
         }

         return node.join;
      }
   }

   public Iterator joinIterator() {
      if (this._size < 2) {
         return this.iterator();
      } else if (this._sorted != null) {
         return this._sorted.iterator();
      } else {
         List sorted = new ArrayList(this._size);
         LinkedList queue = new LinkedList();
         BitSet seen = new BitSet(this._graph.size() * this._graph.size() + this._graph.size());

         for(int i = 0; i < this._graph.size(); ++i) {
            Node n;
            int sidx;
            for(n = (Node)this._graph.get(i); n != null; n = n.next) {
               sidx = this.getSeenIndex(n.join);
               if (!seen.get(sidx)) {
                  seen.set(sidx);
                  queue.add(n);
               }
            }

            if (!queue.isEmpty()) {
               while(!queue.isEmpty()) {
                  n = (Node)queue.removeFirst();
                  int idx = n.forward ? n.join.getIndex2() : n.join.getIndex1();
                  if (!seen.get(idx)) {
                     sorted.add(n.forward ? n.join : n.join.reverse());
                     seen.set(idx);
                  }

                  for(n = (Node)this._graph.get(idx); n != null; n = n.next) {
                     sidx = this.getSeenIndex(n.join);
                     if (!seen.get(sidx)) {
                        seen.set(sidx);
                        queue.add(n);
                     }
                  }
               }
            }
         }

         this._sorted = sorted;
         return this._sorted.iterator();
      }
   }

   private int getSeenIndex(Join join) {
      return join.getIndex1() * this._graph.size() + join.getIndex2() + this._graph.size();
   }

   public boolean add(Join join) {
      if (join.getType() == 1) {
         if (!this.contains(join)) {
            this.addNode(join);
            return true;
         } else {
            return false;
         }
      } else {
         Node node = this.getNode(join, join.getIndex1());
         if (node != null) {
            node.join = join;
            this.getNode(join, join.getIndex2()).join = join;
            this._sorted = null;
         } else {
            this.addNode(join);
         }

         return true;
      }
   }

   public boolean addAll(JoinSet js) {
      if (js.isEmpty()) {
         return false;
      } else {
         boolean added = false;

         for(Iterator itr = js.iterator(); itr.hasNext(); added = this.add((Join)itr.next()) || added) {
         }

         return added;
      }
   }

   private void addNode(Join join) {
      this._sorted = null;
      int size = Math.max(join.getIndex1(), join.getIndex2()) + 1;

      while(this._graph.size() < size) {
         this._graph.add((Object)null);
      }

      Node node = (Node)this._graph.get(join.getIndex1());
      if (node == null) {
         this._graph.set(join.getIndex1(), new Node(join, true));
      } else {
         while(node.next != null) {
            node = node.next;
         }

         node.next = new Node(join, true);
      }

      node = (Node)this._graph.get(join.getIndex2());
      if (node == null) {
         this._graph.set(join.getIndex2(), new Node(join, false));
      } else {
         while(node.next != null) {
            node = node.next;
         }

         node.next = new Node(join, false);
      }

      ++this._size;
   }

   public Iterator iterator() {
      return new Iterator() {
         private Node _next = null;
         private int _idx = -1;

         public boolean hasNext() {
            if (this._next != null) {
               return true;
            } else {
               do {
                  if (++this._idx >= JoinSet.this._graph.size()) {
                     return false;
                  }

                  for(this._next = (Node)JoinSet.this._graph.get(this._idx); this._next != null && !this._next.forward; this._next = this._next.next) {
                  }
               } while(this._next == null);

               return true;
            }
         }

         public Object next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               Join j = this._next.join;

               do {
                  this._next = this._next.next;
               } while(this._next != null && !this._next.forward);

               return j;
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public boolean remove(Join join) {
      if (join != null && this._graph.size() > join.getIndex1()) {
         if (this.remove(join, join.getIndex1())) {
            --this._size;
            return this.remove(join, join.getIndex2());
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean remove(Join join, int idx) {
      Node node = (Node)this._graph.get(idx);

      for(Node prev = null; node != null; node = node.next) {
         if (node.join.equals(join)) {
            if (prev != null) {
               prev.next = node.next;
            } else {
               this._graph.set(idx, node.next);

               while(!this._graph.isEmpty() && this._graph.get(idx) == null && idx == this._graph.size() - 1) {
                  this._graph.remove(idx--);
               }
            }

            return true;
         }

         prev = node;
      }

      return false;
   }

   public boolean removeAll(JoinSet js) {
      boolean remd = false;

      for(Iterator itr = js.iterator(); itr.hasNext(); remd = this.remove((Join)itr.next()) || remd) {
      }

      return remd;
   }

   public boolean retainAll(JoinSet js) {
      boolean remd = false;
      Iterator itr = this.iterator();

      while(itr.hasNext()) {
         Join join = (Join)itr.next();
         if (!js.contains(join)) {
            remd = this.remove(join);
         }
      }

      return remd;
   }

   public void clear() {
      this._graph.clear();
      this._sorted = null;
      this._size = 0;
   }

   public boolean contains(Join join) {
      return this.getRecordedJoin(join) != null;
   }

   public boolean containsAll(JoinSet js) {
      if (js._size <= this._size && js._graph.size() <= this._graph.size()) {
         Iterator itr = js.iterator();

         do {
            if (!itr.hasNext()) {
               return true;
            }
         } while(this.contains((Join)itr.next()));

         return false;
      } else {
         return false;
      }
   }

   public boolean isEmpty() {
      return this._size == 0;
   }

   public int size() {
      return this._size;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof JoinSet) ? false : this._graph.equals(((JoinSet)other)._graph);
      }
   }

   public int hashCode() {
      return this._graph.hashCode();
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[");
      Iterator itr = this.iterator();

      while(itr.hasNext()) {
         buf.append("<").append(itr.next()).append(">");
         if (itr.hasNext()) {
            buf.append(", ");
         }
      }

      return buf.append("]").toString();
   }

   private static class Node implements Cloneable {
      public Join join;
      public Node next;
      public boolean forward;

      public Node(Join join, boolean forward) {
         this.join = join;
         this.forward = forward;
      }

      public int hashCode() {
         int rs = 17;
         rs = 37 * rs + this.join.hashCode();
         if (this.next != null) {
            rs = 37 * rs + this.next.hashCode();
         }

         return rs;
      }

      public boolean equals(Object other) {
         if (!(other instanceof Node)) {
            return false;
         } else {
            Node node = (Node)other;
            return ObjectUtils.equals(this.join, node.join) && ObjectUtils.equals(this.next, node.next);
         }
      }

      public Object clone() {
         try {
            Node node = (Node)super.clone();
            if (node.next != null) {
               node.next = (Node)node.next.clone();
            }

            return node;
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }

      public String toString() {
         return this.join + "(" + (this.forward ? "forward" : "backward") + ")" + "; next: " + this.next;
      }
   }
}
