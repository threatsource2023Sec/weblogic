package org.python.objectweb.asm.commons;

import java.util.AbstractMap;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.python.objectweb.asm.tree.AbstractInsnNode;
import org.python.objectweb.asm.tree.LabelNode;

class JSRInlinerAdapter$Instantiation extends AbstractMap {
   final JSRInlinerAdapter$Instantiation previous;
   public final BitSet subroutine;
   public final Map rangeTable;
   public final LabelNode returnLabel;
   // $FF: synthetic field
   final JSRInlinerAdapter this$0;

   JSRInlinerAdapter$Instantiation(JSRInlinerAdapter var1, JSRInlinerAdapter$Instantiation var2, BitSet var3) {
      this.this$0 = var1;
      this.rangeTable = new HashMap();
      this.previous = var2;
      this.subroutine = var3;

      for(JSRInlinerAdapter$Instantiation var4 = var2; var4 != null; var4 = var4.previous) {
         if (var4.subroutine == var3) {
            throw new RuntimeException("Recursive invocation of " + var3);
         }
      }

      if (var2 != null) {
         this.returnLabel = new LabelNode();
      } else {
         this.returnLabel = null;
      }

      LabelNode var9 = null;
      int var5 = 0;

      for(int var6 = var1.instructions.size(); var5 < var6; ++var5) {
         AbstractInsnNode var7 = var1.instructions.get(var5);
         if (var7.getType() == 8) {
            LabelNode var8 = (LabelNode)var7;
            if (var9 == null) {
               var9 = new LabelNode();
            }

            this.rangeTable.put(var8, var9);
         } else if (this.findOwner(var5) == this) {
            var9 = null;
         }
      }

   }

   public JSRInlinerAdapter$Instantiation findOwner(int var1) {
      if (!this.subroutine.get(var1)) {
         return null;
      } else if (!this.this$0.dualCitizens.get(var1)) {
         return this;
      } else {
         JSRInlinerAdapter$Instantiation var2 = this;

         for(JSRInlinerAdapter$Instantiation var3 = this.previous; var3 != null; var3 = var3.previous) {
            if (var3.subroutine.get(var1)) {
               var2 = var3;
            }
         }

         return var2;
      }
   }

   public LabelNode gotoLabel(LabelNode var1) {
      JSRInlinerAdapter$Instantiation var2 = this.findOwner(this.this$0.instructions.indexOf(var1));
      return (LabelNode)var2.rangeTable.get(var1);
   }

   public LabelNode rangeLabel(LabelNode var1) {
      return (LabelNode)this.rangeTable.get(var1);
   }

   public Set entrySet() {
      return null;
   }

   public LabelNode get(Object var1) {
      return this.gotoLabel((LabelNode)var1);
   }
}
