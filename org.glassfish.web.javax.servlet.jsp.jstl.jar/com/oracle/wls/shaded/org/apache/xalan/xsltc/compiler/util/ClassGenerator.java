package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.classfile.Method;
import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ClassGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.Constants;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.Parser;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.Stylesheet;

public class ClassGenerator extends ClassGen {
   protected static final int TRANSLET_INDEX = 0;
   private Stylesheet _stylesheet;
   private final Parser _parser;
   private final Instruction _aloadTranslet;
   private final String _domClass;
   private final String _domClassSig;
   private final String _applyTemplatesSig;
   private final String _applyTemplatesSigForImport;

   public ClassGenerator(String class_name, String super_class_name, String file_name, int access_flags, String[] interfaces, Stylesheet stylesheet) {
      super(class_name, super_class_name, file_name, access_flags, interfaces);
      this._stylesheet = stylesheet;
      this._parser = stylesheet.getParser();
      this._aloadTranslet = new ALOAD(0);
      if (stylesheet.isMultiDocument()) {
         this._domClass = "com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.MultiDOM";
         this._domClassSig = "Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/dom/MultiDOM;";
      } else {
         this._domClass = "com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.DOMAdapter";
         this._domClassSig = "Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/dom/DOMAdapter;";
      }

      this._applyTemplatesSig = "(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + Constants.TRANSLET_OUTPUT_SIG + ")V";
      this._applyTemplatesSigForImport = "(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + Constants.TRANSLET_OUTPUT_SIG + "I" + ")V";
   }

   public final Parser getParser() {
      return this._parser;
   }

   public final Stylesheet getStylesheet() {
      return this._stylesheet;
   }

   public final String getClassName() {
      return this._stylesheet.getClassName();
   }

   public Instruction loadTranslet() {
      return this._aloadTranslet;
   }

   public final String getDOMClass() {
      return this._domClass;
   }

   public final String getDOMClassSig() {
      return this._domClassSig;
   }

   public final String getApplyTemplatesSig() {
      return this._applyTemplatesSig;
   }

   public final String getApplyTemplatesSigForImport() {
      return this._applyTemplatesSigForImport;
   }

   public boolean isExternal() {
      return false;
   }

   public void addMethod(MethodGenerator methodGen) {
      Method[] methodsToAdd = methodGen.getGeneratedMethods(this);

      for(int i = 0; i < methodsToAdd.length; ++i) {
         this.addMethod(methodsToAdd[i]);
      }

   }
}
