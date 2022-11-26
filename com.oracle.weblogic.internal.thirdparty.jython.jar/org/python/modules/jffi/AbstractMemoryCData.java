package org.python.modules.jffi;

import org.python.core.PyType;

public abstract class AbstractMemoryCData extends CData implements Pointer {
   protected DirectMemory memory;

   AbstractMemoryCData(PyType subtype, CType type, DirectMemory memory) {
      super(subtype, type);
      this.memory = memory;
   }

   public boolean __nonzero__() {
      return !this.getMemory().isNull();
   }

   protected void initReferenceMemory(Memory m) {
      m.putAddress(0L, this.memory);
   }

   public final DirectMemory getMemory() {
      return this.hasReferenceMemory() ? this.getReferenceMemory().getMemory(0L) : this.memory;
   }
}
