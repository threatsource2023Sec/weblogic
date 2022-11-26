package com.bea.core.repackaged.jdt.internal.compiler.codegen;

public abstract class Label {
   public CodeStream codeStream;
   public int position = -1;
   public static final int POS_NOT_SET = -1;

   public Label() {
   }

   public Label(CodeStream codeStream) {
      this.codeStream = codeStream;
   }

   public abstract void place();
}
