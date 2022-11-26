package org.python.google.common.graph;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.AbstractIterator;
import org.python.google.common.collect.UnmodifiableIterator;

abstract class MultiEdgesConnecting extends AbstractSet {
   private final Map outEdgeToNode;
   private final Object targetNode;

   MultiEdgesConnecting(Map outEdgeToNode, Object targetNode) {
      this.outEdgeToNode = (Map)Preconditions.checkNotNull(outEdgeToNode);
      this.targetNode = Preconditions.checkNotNull(targetNode);
   }

   public UnmodifiableIterator iterator() {
      final Iterator entries = this.outEdgeToNode.entrySet().iterator();
      return new AbstractIterator() {
         protected Object computeNext() {
            while(true) {
               if (entries.hasNext()) {
                  Map.Entry entry = (Map.Entry)entries.next();
                  if (!MultiEdgesConnecting.this.targetNode.equals(entry.getValue())) {
                     continue;
                  }

                  return entry.getKey();
               }

               return this.endOfData();
            }
         }
      };
   }

   public boolean contains(@Nullable Object edge) {
      return this.targetNode.equals(this.outEdgeToNode.get(edge));
   }
}
