package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import java.util.Vector;

final class UnsupportedElement extends SyntaxTreeNode {
   private Vector _fallbacks = null;
   private ErrorMsg _message = null;
   private boolean _isExtension = false;

   public UnsupportedElement(String uri, String prefix, String local, boolean isExtension) {
      super(uri, prefix, local);
      this._isExtension = isExtension;
   }

   public void setErrorMessage(ErrorMsg message) {
      this._message = message;
   }

   public void display(int indent) {
      this.indent(indent);
      Util.println("Unsupported element = " + this._qname.getNamespace() + ":" + this._qname.getLocalPart());
      this.displayContents(indent + 4);
   }

   private void processFallbacks(Parser parser) {
      Vector children = this.getContents();
      if (children != null) {
         int count = children.size();

         for(int i = 0; i < count; ++i) {
            SyntaxTreeNode child = (SyntaxTreeNode)children.elementAt(i);
            if (child instanceof Fallback) {
               Fallback fallback = (Fallback)child;
               fallback.activate();
               fallback.parseContents(parser);
               if (this._fallbacks == null) {
                  this._fallbacks = new Vector();
               }

               this._fallbacks.addElement(child);
            }
         }
      }

   }

   public void parseContents(Parser parser) {
      this.processFallbacks(parser);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      if (this._fallbacks != null) {
         int count = this._fallbacks.size();

         for(int i = 0; i < count; ++i) {
            Fallback fallback = (Fallback)this._fallbacks.elementAt(i);
            fallback.typeCheck(stable);
         }
      }

      return Type.Void;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      if (this._fallbacks != null) {
         int count = this._fallbacks.size();

         for(int i = 0; i < count; ++i) {
            Fallback fallback = (Fallback)this._fallbacks.elementAt(i);
            fallback.translate(classGen, methodGen);
         }
      } else {
         ConstantPoolGen cpg = classGen.getConstantPool();
         InstructionList il = methodGen.getInstructionList();
         int unsupportedElem = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary", "unsupported_ElementF", "(Ljava/lang/String;Z)V");
         il.append((CompoundInstruction)(new PUSH(cpg, this.getQName().toString())));
         il.append((CompoundInstruction)(new PUSH(cpg, this._isExtension)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESTATIC(unsupportedElem)));
      }

   }
}
