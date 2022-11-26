package org.python.google.common.graph;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.HashMultiset;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.Multiset;
import org.python.google.errorprone.annotations.concurrent.LazyInit;

final class DirectedMultiNetworkConnections extends AbstractDirectedNetworkConnections {
   @LazyInit
   private transient Reference predecessorsReference;
   @LazyInit
   private transient Reference successorsReference;

   private DirectedMultiNetworkConnections(Map inEdges, Map outEdges, int selfLoopCount) {
      super(inEdges, outEdges, selfLoopCount);
   }

   static DirectedMultiNetworkConnections of() {
      return new DirectedMultiNetworkConnections(new HashMap(2, 1.0F), new HashMap(2, 1.0F), 0);
   }

   static DirectedMultiNetworkConnections ofImmutable(Map inEdges, Map outEdges, int selfLoopCount) {
      return new DirectedMultiNetworkConnections(ImmutableMap.copyOf(inEdges), ImmutableMap.copyOf(outEdges), selfLoopCount);
   }

   public Set predecessors() {
      return Collections.unmodifiableSet(this.predecessorsMultiset().elementSet());
   }

   private Multiset predecessorsMultiset() {
      Multiset predecessors = (Multiset)getReference(this.predecessorsReference);
      if (predecessors == null) {
         predecessors = HashMultiset.create(this.inEdgeMap.values());
         this.predecessorsReference = new SoftReference(predecessors);
      }

      return (Multiset)predecessors;
   }

   public Set successors() {
      return Collections.unmodifiableSet(this.successorsMultiset().elementSet());
   }

   private Multiset successorsMultiset() {
      Multiset successors = (Multiset)getReference(this.successorsReference);
      if (successors == null) {
         successors = HashMultiset.create(this.outEdgeMap.values());
         this.successorsReference = new SoftReference(successors);
      }

      return (Multiset)successors;
   }

   public Set edgesConnecting(final Object node) {
      return new MultiEdgesConnecting(this.outEdgeMap, node) {
         public int size() {
            return DirectedMultiNetworkConnections.this.successorsMultiset().count(node);
         }
      };
   }

   public Object removeInEdge(Object edge, boolean isSelfLoop) {
      Object node = super.removeInEdge(edge, isSelfLoop);
      Multiset predecessors = (Multiset)getReference(this.predecessorsReference);
      if (predecessors != null) {
         Preconditions.checkState(predecessors.remove(node));
      }

      return node;
   }

   public Object removeOutEdge(Object edge) {
      Object node = super.removeOutEdge(edge);
      Multiset successors = (Multiset)getReference(this.successorsReference);
      if (successors != null) {
         Preconditions.checkState(successors.remove(node));
      }

      return node;
   }

   public void addInEdge(Object edge, Object node, boolean isSelfLoop) {
      super.addInEdge(edge, node, isSelfLoop);
      Multiset predecessors = (Multiset)getReference(this.predecessorsReference);
      if (predecessors != null) {
         Preconditions.checkState(predecessors.add(node));
      }

   }

   public void addOutEdge(Object edge, Object node) {
      super.addOutEdge(edge, node);
      Multiset successors = (Multiset)getReference(this.successorsReference);
      if (successors != null) {
         Preconditions.checkState(successors.add(node));
      }

   }

   @Nullable
   private static Object getReference(@Nullable Reference reference) {
      return reference == null ? null : reference.get();
   }
}
