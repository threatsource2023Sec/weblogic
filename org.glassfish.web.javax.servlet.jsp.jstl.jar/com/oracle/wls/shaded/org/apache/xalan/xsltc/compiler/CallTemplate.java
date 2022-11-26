package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import com.oracle.wls.shaded.org.apache.xml.utils.XML11Char;
import java.util.Vector;

final class CallTemplate extends Instruction {
   private QName _name;
   private Object[] _parameters = null;
   private Template _calleeTemplate = null;

   public void display(int indent) {
      this.indent(indent);
      System.out.print("CallTemplate");
      Util.println(" name " + this._name);
      this.displayContents(indent + 4);
   }

   public boolean hasWithParams() {
      return this.elementCount() > 0;
   }

   public void parseContents(Parser parser) {
      String name = this.getAttribute("name");
      if (name.length() > 0) {
         if (!XML11Char.isXML11ValidQName(name)) {
            ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
            parser.reportError(3, err);
         }

         this._name = parser.getQNameIgnoreDefaultNs(name);
      } else {
         this.reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
      }

      this.parseChildren(parser);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      Template template = stable.lookupTemplate(this._name);
      if (template != null) {
         this.typeCheckContents(stable);
         return Type.Void;
      } else {
         ErrorMsg err = new ErrorMsg("TEMPLATE_UNDEF_ERR", this._name, this);
         throw new TypeCheckError(err);
      }
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      Stylesheet stylesheet = classGen.getStylesheet();
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      if (stylesheet.hasLocalParams() || this.hasContents()) {
         this._calleeTemplate = this.getCalleeTemplate();
         if (this._calleeTemplate != null) {
            this.buildParameterList();
         } else {
            int push = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "pushParamFrame", "()V");
            il.append(classGen.loadTranslet());
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(push)));
            this.translateContents(classGen, methodGen);
         }
      }

      String className = stylesheet.getClassName();
      String methodName = Util.escape(this._name.toString());
      il.append(classGen.loadTranslet());
      il.append(methodGen.loadDOM());
      il.append(methodGen.loadIterator());
      il.append(methodGen.loadHandler());
      il.append(methodGen.loadCurrentNode());
      StringBuffer methodSig = new StringBuffer("(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + TRANSLET_OUTPUT_SIG + "I");
      if (this._calleeTemplate != null) {
         Vector calleeParams = this._calleeTemplate.getParameters();
         int numParams = this._parameters.length;

         for(int i = 0; i < numParams; ++i) {
            SyntaxTreeNode node = (SyntaxTreeNode)this._parameters[i];
            methodSig.append("Ljava/lang/Object;");
            if (node instanceof Param) {
               il.append(ACONST_NULL);
            } else {
               node.translate(classGen, methodGen);
            }
         }
      }

      methodSig.append(")V");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(cpg.addMethodref(className, methodName, methodSig.toString()))));
      if (this._calleeTemplate == null && (stylesheet.hasLocalParams() || this.hasContents())) {
         int pop = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "popParamFrame", "()V");
         il.append(classGen.loadTranslet());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(pop)));
      }

   }

   public Template getCalleeTemplate() {
      Template foundTemplate = this.getXSLTC().getParser().getSymbolTable().lookupTemplate(this._name);
      return foundTemplate.isSimpleNamedTemplate() ? foundTemplate : null;
   }

   private void buildParameterList() {
      Vector defaultParams = this._calleeTemplate.getParameters();
      int numParams = defaultParams.size();
      this._parameters = new Object[numParams];

      int count;
      for(count = 0; count < numParams; ++count) {
         this._parameters[count] = defaultParams.elementAt(count);
      }

      count = this.elementCount();

      for(int i = 0; i < count; ++i) {
         Object node = this.elementAt(i);
         if (node instanceof WithParam) {
            WithParam withParam = (WithParam)node;
            QName name = withParam.getName();

            for(int k = 0; k < numParams; ++k) {
               Object object = this._parameters[k];
               if (object instanceof Param && ((Param)object).getName().equals(name)) {
                  withParam.setDoParameterOptimization(true);
                  this._parameters[k] = withParam;
                  break;
               }

               if (object instanceof WithParam && ((WithParam)object).getName().equals(name)) {
                  withParam.setDoParameterOptimization(true);
                  this._parameters[k] = withParam;
                  break;
               }
            }
         }
      }

   }
}
