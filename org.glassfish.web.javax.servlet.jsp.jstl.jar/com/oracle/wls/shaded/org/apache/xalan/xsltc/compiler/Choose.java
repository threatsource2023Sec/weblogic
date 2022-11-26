package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

final class Choose extends Instruction {
   public void display(int indent) {
      this.indent(indent);
      Util.println("Choose");
      this.indent(indent + 4);
      this.displayContents(indent + 4);
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      List whenElements = new ArrayList();
      Otherwise otherwise = null;
      Enumeration elements = this.elements();
      ErrorMsg error = null;
      int line = this.getLineNumber();

      while(elements.hasMoreElements()) {
         Object element = elements.nextElement();
         if (element instanceof When) {
            whenElements.add(element);
         } else if (element instanceof Otherwise) {
            if (otherwise == null) {
               otherwise = (Otherwise)element;
            } else {
               error = new ErrorMsg("MULTIPLE_OTHERWISE_ERR", this);
               this.getParser().reportError(3, error);
            }
         } else if (element instanceof Text) {
            ((Text)element).ignore();
         } else {
            error = new ErrorMsg("WHEN_ELEMENT_ERR", this);
            this.getParser().reportError(3, error);
         }
      }

      if (whenElements.size() == 0) {
         error = new ErrorMsg("MISSING_WHEN_ERR", this);
         this.getParser().reportError(3, error);
      } else {
         InstructionList il = methodGen.getInstructionList();
         BranchHandle nextElement = null;
         List exitHandles = new ArrayList();
         InstructionHandle exit = null;

         Expression test;
         InstructionHandle truec;
         for(Iterator whens = whenElements.iterator(); whens.hasNext(); test.backPatchTrueList(truec.getNext())) {
            When when = (When)whens.next();
            test = when.getTest();
            truec = il.getEnd();
            if (nextElement != null) {
               nextElement.setTarget(il.append(NOP));
            }

            test.translateDesynthesized(classGen, methodGen);
            if (test instanceof FunctionCall) {
               FunctionCall call = (FunctionCall)test;

               try {
                  Type type = call.typeCheck(this.getParser().getSymbolTable());
                  if (type != Type.Boolean) {
                     test._falseList.add(il.append((BranchInstruction)(new IFEQ((InstructionHandle)null))));
                  }
               } catch (TypeCheckError var18) {
               }
            }

            truec = il.getEnd();
            if (!when.ignore()) {
               when.translateContents(classGen, methodGen);
            }

            exitHandles.add(il.append((BranchInstruction)(new GOTO((InstructionHandle)null))));
            if (!whens.hasNext() && otherwise == null) {
               test.backPatchFalseList(exit = il.append(NOP));
            } else {
               nextElement = il.append((BranchInstruction)(new GOTO((InstructionHandle)null)));
               test.backPatchFalseList(nextElement);
            }
         }

         if (otherwise != null) {
            nextElement.setTarget(il.append(NOP));
            otherwise.translateContents(classGen, methodGen);
            exit = il.append(NOP);
         }

         Iterator exitGotos = exitHandles.iterator();

         while(exitGotos.hasNext()) {
            BranchHandle gotoExit = (BranchHandle)exitGotos.next();
            gotoExit.setTarget(exit);
         }

      }
   }
}
