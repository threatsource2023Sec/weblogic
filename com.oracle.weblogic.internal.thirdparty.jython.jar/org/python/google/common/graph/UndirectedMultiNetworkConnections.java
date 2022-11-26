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

final class UndirectedMultiNetworkConnections extends AbstractUndirectedNetworkConnections {
   @LazyInit
   private transient Reference adjacentNodesReference;

   private UndirectedMultiNetworkConnections(Map incidentEdges) {
      super(incidentEdges);
   }

   static UndirectedMultiNetworkConnections of() {
      return new UndirectedMultiNetworkConnections(new HashMap(2, 1.0F));
   }

   static UndirectedMultiNetworkConnections ofImmutable(Map incidentEdges) {
      return new UndirectedMultiNetworkConnections(ImmutableMap.copyOf(incidentEdges));
   }

   public Set adjacentNodes() {
      return Collections.unmodifiableSet(this.adjacentNodesMultiset().elementSet());
   }

   private Multiset adjacentNodesMultiset() {
      Multiset adjacentNodes = (Multiset)getReference(this.adjacentNodesReference);
      if (adjacentNodes == null) {
         adjacentNodes = HashMultiset.create(this.incidentEdgeMap.values());
         this.adjacentNodesReference = new SoftReference(adjacentNodes);
      }

      return (Multiset)adjacentNodes;
   }

   public Set edgesConnecting(final Object node) {
      return new MultiEdgesConnecting(this.incidentEdgeMap, node) {
         public int size() {
            return UndirectedMultiNetworkConnections.this.adjacentNodesMultiset().count(node);
         }
      };
   }

   public Object removeInEdge(Object edge, boolean isSelfLoop) {
      return !isSelfLoop ? this.removeOutEdge(edge) : null;
   }

   public Object removeOutEdge(Object edge) {
      Object node = super.removeOutEdge(edge);
      Multiset adjacentNodes = (Multiset)getReference(this.adjacentNodesReference);
      if (adjacentNodes != null) {
         Preconditions.checkState(adjacentNodes.remove(node));
      }

      return node;
   }

   public void addInEdge(Object edge, Object node, boolean isSelfLoop) {
      if (!isSelfLoop) {
         this.addOutEdge(edge, node);
      }

   }

   public void addOutEdge(Object edge, Object node) {
      super.addOutEdge(edge, node);
      Multiset adjacentNodes = (Multiset)getReference(this.adjacentNodesReference);
      if (adjacentNodes != null) {
         Preconditions.checkState(adjacentNodes.add(node));
      }

   }

   @Nullable
   private static Object getReference(@Nullable Reference reference) {
      return reference == null ? null : reference.get();
   }
}
