package org.stringtemplate.v4.compiler;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.misc.ErrorManager;
import org.stringtemplate.v4.misc.ErrorType;
import org.stringtemplate.v4.misc.Interval;
import org.stringtemplate.v4.misc.Misc;

public class CompilationState {
   CompiledST impl = new CompiledST();
   StringTable stringtable = new StringTable();
   int ip = 0;
   TokenStream tokens;
   ErrorManager errMgr;

   public CompilationState(ErrorManager errMgr, String name, TokenStream tokens) {
      this.errMgr = errMgr;
      this.tokens = tokens;
      this.impl.name = name;
      this.impl.prefix = Misc.getPrefix(name);
   }

   public int defineString(String s) {
      return this.stringtable.add(s);
   }

   public void refAttr(Token templateToken, CommonTree id) {
      String name = id.getText();
      if (this.impl.formalArguments != null && this.impl.formalArguments.get(name) != null) {
         FormalArgument arg = (FormalArgument)this.impl.formalArguments.get(name);
         int index = arg.index;
         this.emit1(id, (short)3, index);
      } else if (Interpreter.predefinedAnonSubtemplateAttributes.contains(name)) {
         this.errMgr.compileTimeError(ErrorType.REF_TO_IMPLICIT_ATTRIBUTE_OUT_OF_SCOPE, templateToken, id.token);
         this.emit(id, (short)44);
      } else {
         this.emit1(id, (short)2, name);
      }

   }

   public void setOption(CommonTree id) {
      Interpreter.Option O = (Interpreter.Option)Compiler.supportedOptions.get(id.getText());
      this.emit1(id, (short)6, O.ordinal());
   }

   public void func(Token templateToken, CommonTree id) {
      Short funcBytecode = (Short)Compiler.funcs.get(id.getText());
      if (funcBytecode == null) {
         this.errMgr.compileTimeError(ErrorType.NO_SUCH_FUNCTION, templateToken, id.token);
         this.emit(id, (short)43);
      } else {
         this.emit(id, funcBytecode);
      }

   }

   public void emit(short opcode) {
      this.emit((CommonTree)null, opcode);
   }

   public void emit(CommonTree opAST, short opcode) {
      this.ensureCapacity(1);
      if (opAST != null) {
         int i = opAST.getTokenStartIndex();
         int j = opAST.getTokenStopIndex();
         int p = ((CommonToken)this.tokens.get(i)).getStartIndex();
         int q = ((CommonToken)this.tokens.get(j)).getStopIndex();
         if (p >= 0 && q >= 0) {
            this.impl.sourceMap[this.ip] = new Interval(p, q);
         }
      }

      this.impl.instrs[this.ip++] = (byte)opcode;
   }

   public void emit1(CommonTree opAST, short opcode, int arg) {
      this.emit(opAST, opcode);
      this.ensureCapacity(2);
      writeShort(this.impl.instrs, this.ip, (short)arg);
      this.ip += 2;
   }

   public void emit2(CommonTree opAST, short opcode, int arg, int arg2) {
      this.emit(opAST, opcode);
      this.ensureCapacity(4);
      writeShort(this.impl.instrs, this.ip, (short)arg);
      this.ip += 2;
      writeShort(this.impl.instrs, this.ip, (short)arg2);
      this.ip += 2;
   }

   public void emit2(CommonTree opAST, short opcode, String s, int arg2) {
      int i = this.defineString(s);
      this.emit2(opAST, opcode, i, arg2);
   }

   public void emit1(CommonTree opAST, short opcode, String s) {
      int i = this.defineString(s);
      this.emit1(opAST, opcode, i);
   }

   public void insert(int addr, short opcode, String s) {
      this.ensureCapacity(3);
      int instrSize = 3;
      System.arraycopy(this.impl.instrs, addr, this.impl.instrs, addr + instrSize, this.ip - addr);
      int save = this.ip;
      this.ip = addr;
      this.emit1((CommonTree)null, opcode, s);
      this.ip = save + instrSize;

      Bytecode.Instruction I;
      for(int a = addr + instrSize; a < this.ip; a += I.nopnds * 2 + 1) {
         byte op = this.impl.instrs[a];
         I = Bytecode.instructions[op];
         if (op == 18 || op == 19) {
            int opnd = BytecodeDisassembler.getShort(this.impl.instrs, a + 1);
            writeShort(this.impl.instrs, a + 1, (short)(opnd + instrSize));
         }
      }

   }

   public void write(int addr, short value) {
      writeShort(this.impl.instrs, addr, value);
   }

   protected void ensureCapacity(int n) {
      if (this.ip + n >= this.impl.instrs.length) {
         byte[] c = new byte[this.impl.instrs.length * 2];
         System.arraycopy(this.impl.instrs, 0, c, 0, this.impl.instrs.length);
         this.impl.instrs = c;
         Interval[] sm = new Interval[this.impl.sourceMap.length * 2];
         System.arraycopy(this.impl.sourceMap, 0, sm, 0, this.impl.sourceMap.length);
         this.impl.sourceMap = sm;
      }

   }

   public void indent(CommonTree indent) {
      this.emit1(indent, (short)39, indent.getText());
   }

   public static void writeShort(byte[] memory, int index, short value) {
      memory[index + 0] = (byte)(value >> 8 & 255);
      memory[index + 1] = (byte)(value & 255);
   }
}
