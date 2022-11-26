package weblogic.application.internal.library;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.application.internal.library.util.AbstractTraversal;
import weblogic.application.internal.library.util.RONode;
import weblogic.application.internal.library.util.Traversal;
import weblogic.application.library.LibraryDefinition;

class LibraryLookup extends AbstractTraversal implements Traversal {
   private final RegistryKey[] edges;
   private final boolean exactMatch;
   private int edgeIndex = -1;
   private RONode matchNode = null;

   public LibraryLookup(RegistryKey[] edges, boolean exactMatch) {
      this.edges = edges;
      this.exactMatch = exactMatch;
   }

   public RegistryKey getNextEdge(RONode node) {
      ++this.edgeIndex;
      if (this.edgeIndex != this.edges.length && node.hasChildren()) {
         RegistryKey nextEdge = this.edges[this.edgeIndex];
         if (node.getDepth() == 2) {
            nextEdge = this.getSpecVersionEdge(node, nextEdge);
         } else if (node.getDepth() == 3) {
            nextEdge = this.getImplVersionEdge(node, nextEdge);
         }

         return nextEdge != null && node.hasEdge(nextEdge) ? nextEdge : null;
      } else {
         return null;
      }
   }

   private RegistryKey getSpecVersionEdge(RONode node, RegistryKey requestedSpecVersion) {
      return this.exactMatch && requestedSpecVersion != null ? requestedSpecVersion : this.getHighestVersion(node, requestedSpecVersion);
   }

   private RegistryKey getHighestVersion(RONode node, RegistryKey requestedVersion) {
      RegistryKey highest = (RegistryKey)node.getHighestEdge();
      if (requestedVersion == null) {
         return highest;
      } else {
         return highest.compareTo(requestedVersion) >= 0 ? highest : requestedVersion;
      }
   }

   private RegistryKey getImplVersionEdge(RONode node, RegistryKey requestedImplVersion) {
      if (this.exactMatch && requestedImplVersion != null) {
         return requestedImplVersion;
      } else {
         boolean implVersionsComparable = this.areComparable(node.getEdges());
         if (requestedImplVersion == null) {
            if (implVersionsComparable) {
               return this.getHighestVersion(node, (RegistryKey)null);
            }

            if (node.getNumChildren() == 1) {
               return (RegistryKey)node.getEdges().iterator().next();
            }
         }

         return requestedImplVersion != null && this.isComparable(requestedImplVersion) && implVersionsComparable ? this.getHighestVersion(node, requestedImplVersion) : requestedImplVersion;
      }
   }

   private boolean isComparable(RegistryKey k) {
      return k.isComparable();
   }

   private boolean areComparable(Set availableEdges) {
      Iterator iter = availableEdges.iterator();

      do {
         if (!iter.hasNext()) {
            return true;
         }
      } while(((RegistryKey)iter.next()).isComparable());

      return false;
   }

   public boolean match() {
      return this.getMatch() != null;
   }

   public LibraryDefinition getMatch() {
      return this.matchNode == null ? null : (LibraryDefinition)this.matchNode.getValue();
   }

   public void visitLeaf(RONode node, List path) {
      if (this.edgeIndex == this.edges.length - 1 || this.remainingEdgesAreNull()) {
         this.matchNode = node;
      }

   }

   private boolean remainingEdgesAreNull() {
      for(int i = this.edgeIndex + 1; i < this.edges.length; ++i) {
         if (this.edges[i] != null) {
            return false;
         }
      }

      return true;
   }
}
