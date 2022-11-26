package org.stringtemplate.v4;

import java.util.ArrayList;
import java.util.List;

public class InstanceScope {
   public final InstanceScope parent;
   public final ST st;
   public int ip;
   public List events = new ArrayList();
   public List childEvalTemplateEvents = new ArrayList();
   public boolean earlyEval;

   public InstanceScope(InstanceScope parent, ST st) {
      this.parent = parent;
      this.st = st;
      this.earlyEval = parent != null && parent.earlyEval;
   }
}
