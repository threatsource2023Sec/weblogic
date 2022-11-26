package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;

class OutlineableChunkStart extends MarkerInstruction {
   public static final Instruction OUTLINEABLECHUNKSTART = new OutlineableChunkStart();
   // $FF: synthetic field
   static Class class$org$apache$xalan$xsltc$compiler$util$OutlineableChunkStart;

   private OutlineableChunkStart() {
   }

   public String getName() {
      return (class$org$apache$xalan$xsltc$compiler$util$OutlineableChunkStart == null ? (class$org$apache$xalan$xsltc$compiler$util$OutlineableChunkStart = class$("com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.OutlineableChunkStart")) : class$org$apache$xalan$xsltc$compiler$util$OutlineableChunkStart).getName();
   }

   public String toString() {
      return this.getName();
   }

   public String toString(boolean verbose) {
      return this.getName();
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
