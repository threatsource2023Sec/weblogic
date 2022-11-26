package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.el.ELText;
import java.io.IOException;
import java.util.Arrays;
import javax.faces.context.FacesContext;

public final class UIInstructions extends UILeaf {
   private final transient ELText txt;
   private final transient Instruction[] instructions;

   public UIInstructions(ELText txt, Instruction[] instructions) {
      this.txt = txt;
      this.instructions = instructions;
   }

   public void encodeBegin(FacesContext context) throws IOException {
      if (this.isRendered()) {
         int size = this.instructions.length;

         for(int i = 0; i < size; ++i) {
            this.instructions[i].write(context);
         }
      }

   }

   public String toString() {
      return this.txt != null ? this.txt.toString() : "UIInstructions[" + Arrays.asList(this.instructions) + "]";
   }
}
