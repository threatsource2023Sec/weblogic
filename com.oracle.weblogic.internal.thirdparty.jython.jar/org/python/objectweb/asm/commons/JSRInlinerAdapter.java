package org.python.objectweb.asm.commons;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.python.objectweb.asm.Label;
import org.python.objectweb.asm.MethodVisitor;
import org.python.objectweb.asm.Opcodes;
import org.python.objectweb.asm.tree.AbstractInsnNode;
import org.python.objectweb.asm.tree.InsnList;
import org.python.objectweb.asm.tree.InsnNode;
import org.python.objectweb.asm.tree.JumpInsnNode;
import org.python.objectweb.asm.tree.LabelNode;
import org.python.objectweb.asm.tree.LocalVariableNode;
import org.python.objectweb.asm.tree.LookupSwitchInsnNode;
import org.python.objectweb.asm.tree.MethodNode;
import org.python.objectweb.asm.tree.TableSwitchInsnNode;
import org.python.objectweb.asm.tree.TryCatchBlockNode;

public class JSRInlinerAdapter extends MethodNode implements Opcodes {
   private final Map subroutineHeads;
   private final BitSet mainSubroutine;
   final BitSet dualCitizens;
   // $FF: synthetic field
   static Class class$org$objectweb$asm$commons$JSRInlinerAdapter = class$("org.python.objectweb.asm.commons.JSRInlinerAdapter");

   public JSRInlinerAdapter(MethodVisitor var1, int var2, String var3, String var4, String var5, String[] var6) {
      this(327680, var1, var2, var3, var4, var5, var6);
      if (this.getClass() != class$org$objectweb$asm$commons$JSRInlinerAdapter) {
         throw new IllegalStateException();
      }
   }

   protected JSRInlinerAdapter(int var1, MethodVisitor var2, int var3, String var4, String var5, String var6, String[] var7) {
      super(var1, var3, var4, var5, var6, var7);
      this.subroutineHeads = new HashMap();
      this.mainSubroutine = new BitSet();
      this.dualCitizens = new BitSet();
      this.mv = var2;
   }

   public void visitJumpInsn(int var1, Label var2) {
      super.visitJumpInsn(var1, var2);
      LabelNode var3 = ((JumpInsnNode)this.instructions.getLast()).label;
      if (var1 == 168 && !this.subroutineHeads.containsKey(var3)) {
         this.subroutineHeads.put(var3, new BitSet());
      }

   }

   public void visitEnd() {
      if (!this.subroutineHeads.isEmpty()) {
         this.markSubroutines();
         this.emitCode();
      }

      if (this.mv != null) {
         this.accept(this.mv);
      }

   }

   private void markSubroutines() {
      BitSet var1 = new BitSet();
      this.markSubroutineWalk(this.mainSubroutine, 0, var1);
      Iterator var2 = this.subroutineHeads.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry var3 = (Map.Entry)var2.next();
         LabelNode var4 = (LabelNode)var3.getKey();
         BitSet var5 = (BitSet)var3.getValue();
         int var6 = this.instructions.indexOf(var4);
         this.markSubroutineWalk(var5, var6, var1);
      }

   }

   private void markSubroutineWalk(BitSet var1, int var2, BitSet var3) {
      this.markSubroutineWalkDFS(var1, var2, var3);
      boolean var4 = true;

      while(var4) {
         var4 = false;
         Iterator var5 = this.tryCatchBlocks.iterator();

         while(var5.hasNext()) {
            TryCatchBlockNode var6 = (TryCatchBlockNode)var5.next();
            int var7 = this.instructions.indexOf(var6.handler);
            if (!var1.get(var7)) {
               int var8 = this.instructions.indexOf(var6.start);
               int var9 = this.instructions.indexOf(var6.end);
               int var10 = var1.nextSetBit(var8);
               if (var10 != -1 && var10 < var9) {
                  this.markSubroutineWalkDFS(var1, var7, var3);
                  var4 = true;
               }
            }
         }
      }

   }

   private void markSubroutineWalkDFS(BitSet var1, int var2, BitSet var3) {
      while(true) {
         AbstractInsnNode var4 = this.instructions.get(var2);
         if (var1.get(var2)) {
            return;
         }

         var1.set(var2);
         if (var3.get(var2)) {
            this.dualCitizens.set(var2);
         }

         var3.set(var2);
         int var6;
         if (var4.getType() == 7 && var4.getOpcode() != 168) {
            JumpInsnNode var5 = (JumpInsnNode)var4;
            var6 = this.instructions.indexOf(var5.label);
            this.markSubroutineWalkDFS(var1, var6, var3);
         }

         int var7;
         LabelNode var8;
         if (var4.getType() == 11) {
            TableSwitchInsnNode var9 = (TableSwitchInsnNode)var4;
            var6 = this.instructions.indexOf(var9.dflt);
            this.markSubroutineWalkDFS(var1, var6, var3);

            for(var7 = var9.labels.size() - 1; var7 >= 0; --var7) {
               var8 = (LabelNode)var9.labels.get(var7);
               var6 = this.instructions.indexOf(var8);
               this.markSubroutineWalkDFS(var1, var6, var3);
            }
         }

         if (var4.getType() == 12) {
            LookupSwitchInsnNode var10 = (LookupSwitchInsnNode)var4;
            var6 = this.instructions.indexOf(var10.dflt);
            this.markSubroutineWalkDFS(var1, var6, var3);

            for(var7 = var10.labels.size() - 1; var7 >= 0; --var7) {
               var8 = (LabelNode)var10.labels.get(var7);
               var6 = this.instructions.indexOf(var8);
               this.markSubroutineWalkDFS(var1, var6, var3);
            }
         }

         switch (this.instructions.get(var2).getOpcode()) {
            case 167:
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 191:
               return;
            case 168:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            default:
               ++var2;
               if (var2 >= this.instructions.size()) {
                  return;
               }
         }
      }
   }

   private void emitCode() {
      LinkedList var1 = new LinkedList();
      var1.add(new JSRInlinerAdapter$Instantiation(this, (JSRInlinerAdapter$Instantiation)null, this.mainSubroutine));
      InsnList var2 = new InsnList();
      ArrayList var3 = new ArrayList();
      ArrayList var4 = new ArrayList();

      while(!var1.isEmpty()) {
         JSRInlinerAdapter$Instantiation var5 = (JSRInlinerAdapter$Instantiation)var1.removeFirst();
         this.emitSubroutine(var5, var1, var2, var3, var4);
      }

      this.instructions = var2;
      this.tryCatchBlocks = var3;
      this.localVariables = var4;
   }

   private void emitSubroutine(JSRInlinerAdapter$Instantiation var1, List var2, InsnList var3, List var4, List var5) {
      LabelNode var6 = null;
      int var7 = 0;

      LabelNode var11;
      for(int var8 = this.instructions.size(); var7 < var8; ++var7) {
         AbstractInsnNode var9 = this.instructions.get(var7);
         JSRInlinerAdapter$Instantiation var10 = var1.findOwner(var7);
         if (var9.getType() == 8) {
            var11 = (LabelNode)var9;
            LabelNode var21 = var1.rangeLabel(var11);
            if (var21 != var6) {
               var3.add(var21);
               var6 = var21;
            }
         } else if (var10 == var1) {
            if (var9.getOpcode() != 169) {
               if (var9.getOpcode() == 168) {
                  var11 = ((JumpInsnNode)var9).label;
                  BitSet var20 = (BitSet)this.subroutineHeads.get(var11);
                  JSRInlinerAdapter$Instantiation var13 = new JSRInlinerAdapter$Instantiation(this, var1, var20);
                  LabelNode var14 = var13.gotoLabel(var11);
                  var3.add(new InsnNode(1));
                  var3.add(new JumpInsnNode(167, var14));
                  var3.add(var13.returnLabel);
                  var2.add(var13);
               } else {
                  var3.add(var9.clone(var1));
               }
            } else {
               var11 = null;

               for(JSRInlinerAdapter$Instantiation var12 = var1; var12 != null; var12 = var12.previous) {
                  if (var12.subroutine.get(var7)) {
                     var11 = var12.returnLabel;
                  }
               }

               if (var11 == null) {
                  throw new RuntimeException("Instruction #" + var7 + " is a RET not owned by any subroutine");
               }

               var3.add(new JumpInsnNode(167, var11));
            }
         }
      }

      Iterator var15 = this.tryCatchBlocks.iterator();

      while(true) {
         TryCatchBlockNode var16;
         LabelNode var18;
         LabelNode var19;
         do {
            if (!var15.hasNext()) {
               var15 = this.localVariables.iterator();

               while(var15.hasNext()) {
                  LocalVariableNode var17 = (LocalVariableNode)var15.next();
                  var18 = var1.rangeLabel(var17.start);
                  var19 = var1.rangeLabel(var17.end);
                  if (var18 != var19) {
                     var5.add(new LocalVariableNode(var17.name, var17.desc, var17.signature, var18, var19, var17.index));
                  }
               }

               return;
            }

            var16 = (TryCatchBlockNode)var15.next();
            var18 = var1.rangeLabel(var16.start);
            var19 = var1.rangeLabel(var16.end);
         } while(var18 == var19);

         var11 = var1.gotoLabel(var16.handler);
         if (var18 == null || var19 == null || var11 == null) {
            throw new RuntimeException("Internal error!");
         }

         var4.add(new TryCatchBlockNode(var18, var19, var11, var16.type));
      }
   }

   private static void log(String var0) {
      System.err.println(var0);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         String var1 = var2.getMessage();
         throw new NoClassDefFoundError(var1);
      }
   }
}
