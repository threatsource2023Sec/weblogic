package org.python.google.common.graph;

import org.python.google.common.base.Optional;

abstract class AbstractGraphBuilder {
   final boolean directed;
   boolean allowsSelfLoops = false;
   ElementOrder nodeOrder = ElementOrder.insertion();
   Optional expectedNodeCount = Optional.absent();

   AbstractGraphBuilder(boolean directed) {
      this.directed = directed;
   }
}
