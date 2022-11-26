package org.python.google.common.graph;

import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.AbstractIterator;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.UnmodifiableIterator;

final class DirectedGraphConnections implements GraphConnections {
   private static final Object PRED = new Object();
   private final Map adjacentNodeValues;
   private int predecessorCount;
   private int successorCount;

   private DirectedGraphConnections(Map adjacentNodeValues, int predecessorCount, int successorCount) {
      this.adjacentNodeValues = (Map)Preconditions.checkNotNull(adjacentNodeValues);
      this.predecessorCount = Graphs.checkNonNegative(predecessorCount);
      this.successorCount = Graphs.checkNonNegative(successorCount);
      Preconditions.checkState(predecessorCount <= adjacentNodeValues.size() && successorCount <= adjacentNodeValues.size());
   }

   static DirectedGraphConnections of() {
      int initialCapacity = 4;
      return new DirectedGraphConnections(new HashMap(initialCapacity, 1.0F), 0, 0);
   }

   static DirectedGraphConnections ofImmutable(Set predecessors, Map successorValues) {
      Map adjacentNodeValues = new HashMap();
      adjacentNodeValues.putAll(successorValues);
      Iterator var3 = predecessors.iterator();

      while(var3.hasNext()) {
         Object predecessor = var3.next();
         Object value = adjacentNodeValues.put(predecessor, PRED);
         if (value != null) {
            adjacentNodeValues.put(predecessor, new PredAndSucc(value));
         }
      }

      return new DirectedGraphConnections(ImmutableMap.copyOf((Map)adjacentNodeValues), predecessors.size(), successorValues.size());
   }

   public Set adjacentNodes() {
      return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
   }

   public Set predecessors() {
      return new AbstractSet() {
         public UnmodifiableIterator iterator() {
            final Iterator entries = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
            return new AbstractIterator() {
               protected Object computeNext() {
                  while(true) {
                     if (entries.hasNext()) {
                        Map.Entry entry = (Map.Entry)entries.next();
                        if (!DirectedGraphConnections.isPredecessor(entry.getValue())) {
                           continue;
                        }

                        return entry.getKey();
                     }

                     return this.endOfData();
                  }
               }
            };
         }

         public int size() {
            return DirectedGraphConnections.this.predecessorCount;
         }

         public boolean contains(@Nullable Object obj) {
            return DirectedGraphConnections.isPredecessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
         }
      };
   }

   public Set successors() {
      return new AbstractSet() {
         public UnmodifiableIterator iterator() {
            final Iterator entries = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
            return new AbstractIterator() {
               protected Object computeNext() {
                  while(true) {
                     if (entries.hasNext()) {
                        Map.Entry entry = (Map.Entry)entries.next();
                        if (!DirectedGraphConnections.isSuccessor(entry.getValue())) {
                           continue;
                        }

                        return entry.getKey();
                     }

                     return this.endOfData();
                  }
               }
            };
         }

         public int size() {
            return DirectedGraphConnections.this.successorCount;
         }

         public boolean contains(@Nullable Object obj) {
            return DirectedGraphConnections.isSuccessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
         }
      };
   }

   public Object value(Object node) {
      Object value = this.adjacentNodeValues.get(node);
      if (value == PRED) {
         return null;
      } else {
         return value instanceof PredAndSucc ? ((PredAndSucc)value).successorValue : value;
      }
   }

   public void removePredecessor(Object node) {
      Object previousValue = this.adjacentNodeValues.get(node);
      if (previousValue == PRED) {
         this.adjacentNodeValues.remove(node);
         Graphs.checkNonNegative(--this.predecessorCount);
      } else if (previousValue instanceof PredAndSucc) {
         this.adjacentNodeValues.put(node, ((PredAndSucc)previousValue).successorValue);
         Graphs.checkNonNegative(--this.predecessorCount);
      }

   }

   public Object removeSuccessor(Object node) {
      Object previousValue = this.adjacentNodeValues.get(node);
      if (previousValue != null && previousValue != PRED) {
         if (previousValue instanceof PredAndSucc) {
            this.adjacentNodeValues.put(node, PRED);
            Graphs.checkNonNegative(--this.successorCount);
            return ((PredAndSucc)previousValue).successorValue;
         } else {
            this.adjacentNodeValues.remove(node);
            Graphs.checkNonNegative(--this.successorCount);
            return previousValue;
         }
      } else {
         return null;
      }
   }

   public void addPredecessor(Object node, Object unused) {
      Object previousValue = this.adjacentNodeValues.put(node, PRED);
      if (previousValue == null) {
         Graphs.checkPositive(++this.predecessorCount);
      } else if (previousValue instanceof PredAndSucc) {
         this.adjacentNodeValues.put(node, previousValue);
      } else if (previousValue != PRED) {
         this.adjacentNodeValues.put(node, new PredAndSucc(previousValue));
         Graphs.checkPositive(++this.predecessorCount);
      }

   }

   public Object addSuccessor(Object node, Object value) {
      Object previousValue = this.adjacentNodeValues.put(node, value);
      if (previousValue == null) {
         Graphs.checkPositive(++this.successorCount);
         return null;
      } else if (previousValue instanceof PredAndSucc) {
         this.adjacentNodeValues.put(node, new PredAndSucc(value));
         return ((PredAndSucc)previousValue).successorValue;
      } else if (previousValue == PRED) {
         this.adjacentNodeValues.put(node, new PredAndSucc(value));
         Graphs.checkPositive(++this.successorCount);
         return null;
      } else {
         return previousValue;
      }
   }

   private static boolean isPredecessor(@Nullable Object value) {
      return value == PRED || value instanceof PredAndSucc;
   }

   private static boolean isSuccessor(@Nullable Object value) {
      return value != PRED && value != null;
   }

   private static final class PredAndSucc {
      private final Object successorValue;

      PredAndSucc(Object successorValue) {
         this.successorValue = successorValue;
      }
   }
}
