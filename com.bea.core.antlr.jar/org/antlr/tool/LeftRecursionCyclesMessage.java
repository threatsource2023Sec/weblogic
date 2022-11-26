package org.antlr.tool;

import java.util.Collection;
import org.stringtemplate.v4.ST;

public class LeftRecursionCyclesMessage extends Message {
   public Collection cycles;

   public LeftRecursionCyclesMessage(Collection cycles) {
      super(210);
      this.cycles = cycles;
   }

   public String toString() {
      ST st = this.getMessageTemplate();
      st.add("listOfCycles", this.cycles);
      return super.toString(st);
   }
}
