package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;

final class ValueOf extends Instruction {
   private Expression _select;
   private boolean _escaping = true;
   private boolean _isString = false;

   public void display(int indent) {
      this.indent(indent);
      Util.println("ValueOf");
      this.indent(indent + 4);
      Util.println("select " + this._select.toString());
   }

   public void parseContents(Parser parser) {
      this._select = parser.parseExpression(this, "select", (String)null);
      if (this._select.isDummy()) {
         this.reportError(this, parser, "REQUIRED_ATTR_ERR", "select");
      } else {
         String str = this.getAttribute("disable-output-escaping");
         if (str != null && str.equals("yes")) {
            this._escaping = false;
         }

      }
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      Type type = this._select.typeCheck(stable);
      if (type != null && !type.identicalTo(Type.Node)) {
         if (type.identicalTo(Type.NodeSet)) {
            this._select = new CastExpr(this._select, Type.Node);
         } else {
            this._isString = true;
            if (!type.identicalTo(Type.String)) {
               this._select = new CastExpr(this._select, Type.String);
            }

            this._isString = true;
         }
      }

      return Type.Void;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int setEscaping = cpg.addInterfaceMethodref(OUTPUT_HANDLER, "setEscaping", "(Z)Z");
      if (!this._escaping) {
         il.append(methodGen.loadHandler());
         il.append((CompoundInstruction)(new PUSH(cpg, false)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(setEscaping, 2)));
      }

      int characters;
      if (this._isString) {
         characters = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "characters", CHARACTERSW_SIG);
         il.append(classGen.loadTranslet());
         this._select.translate(classGen, methodGen);
         il.append(methodGen.loadHandler());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(characters)));
      } else {
         characters = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "characters", CHARACTERS_SIG);
         il.append(methodGen.loadDOM());
         this._select.translate(classGen, methodGen);
         il.append(methodGen.loadHandler());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(characters, 3)));
      }

      if (!this._escaping) {
         il.append(methodGen.loadHandler());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(setEscaping, 2)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)POP);
      }

   }
}
