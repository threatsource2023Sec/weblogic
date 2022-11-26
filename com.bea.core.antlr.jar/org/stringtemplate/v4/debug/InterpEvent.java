package org.stringtemplate.v4.debug;

import org.stringtemplate.v4.InstanceScope;

public class InterpEvent {
   public InstanceScope scope;
   public final int outputStartChar;
   public final int outputStopChar;

   public InterpEvent(InstanceScope scope, int outputStartChar, int outputStopChar) {
      this.scope = scope;
      this.outputStartChar = outputStartChar;
      this.outputStopChar = outputStopChar;
   }

   public String toString() {
      return this.getClass().getSimpleName() + "{" + "self=" + this.scope.st + ", start=" + this.outputStartChar + ", stop=" + this.outputStopChar + '}';
   }
}
