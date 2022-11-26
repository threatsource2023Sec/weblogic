package com.solarmetric.profile;

import java.io.Serializable;

public class ProfilingStackItem implements Serializable {
   private static final long serialVersionUID = 1L;
   private ProfilingEvent _ev;
   private Node _node;

   public ProfilingStackItem(ProfilingEvent ev, Node node) {
      this._ev = ev;
      this._node = node;
   }

   public Node getNode() {
      return this._node;
   }

   public ProfilingEvent getProfilingEvent() {
      return this._ev;
   }
}
