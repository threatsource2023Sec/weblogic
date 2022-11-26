package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;

class OutlineableChunkEnd extends MarkerInstruction {
   public static final Instruction OUTLINEABLECHUNKEND = new OutlineableChunkEnd();
   // $FF: synthetic field
   static Class class$org$apache$xalan$xsltc$compiler$util$OutlineableChunkEnd;

   private OutlineableChunkEnd() {
   }

   public String getName() {
      return (class$org$apache$xalan$xsltc$compiler$util$OutlineableChunkEnd == null ? (class$org$apache$xalan$xsltc$compiler$util$OutlineableChunkEnd = class$("com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.OutlineableChunkEnd")) : class$org$apache$xalan$xsltc$compiler$util$OutlineableChunkEnd).getName();
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
