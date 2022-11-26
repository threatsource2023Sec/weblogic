package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ANEWARRAY;
import com.oracle.wls.shaded.org.apache.bcel.generic.BasicType;
import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.FieldGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GETFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.GETSTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESPECIAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.ISTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEWARRAY;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUTFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUTSTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.TargetLostException;
import com.oracle.wls.shaded.org.apache.bcel.util.InstructionFinder;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import com.oracle.wls.shaded.org.apache.xml.utils.SystemIDResolver;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public final class Stylesheet extends SyntaxTreeNode {
   private String _version;
   private QName _name;
   private String _systemId;
   private Stylesheet _parentStylesheet;
   private Vector _globals = new Vector();
   private Boolean _hasLocalParams = null;
   private String _className;
   private final Vector _templates = new Vector();
   private Vector _allValidTemplates = null;
   private Vector _elementsWithNamespacesUsedDynamically = null;
   private int _nextModeSerial = 1;
   private final Hashtable _modes = new Hashtable();
   private Mode _defaultMode;
   private final Hashtable _extensions = new Hashtable();
   public Stylesheet _importedFrom = null;
   public Stylesheet _includedFrom = null;
   private Vector _includedStylesheets = null;
   private int _importPrecedence = 1;
   private int _minimumDescendantPrecedence = -1;
   private Hashtable _keys = new Hashtable();
   private SourceLoader _loader = null;
   private boolean _numberFormattingUsed = false;
   private boolean _simplified = false;
   private boolean _multiDocument = false;
   private boolean _callsNodeset = false;
   private boolean _hasIdCall = false;
   private boolean _templateInlining = false;
   private Output _lastOutputElement = null;
   private Properties _outputProperties = null;
   private int _outputMethod = 0;
   public static final int UNKNOWN_OUTPUT = 0;
   public static final int XML_OUTPUT = 1;
   public static final int HTML_OUTPUT = 2;
   public static final int TEXT_OUTPUT = 3;

   public int getOutputMethod() {
      return this._outputMethod;
   }

   private void checkOutputMethod() {
      if (this._lastOutputElement != null) {
         String method = this._lastOutputElement.getOutputMethod();
         if (method != null) {
            if (method.equals("xml")) {
               this._outputMethod = 1;
            } else if (method.equals("html")) {
               this._outputMethod = 2;
            } else if (method.equals("text")) {
               this._outputMethod = 3;
            }
         }
      }

   }

   public boolean getTemplateInlining() {
      return this._templateInlining;
   }

   public void setTemplateInlining(boolean flag) {
      this._templateInlining = flag;
   }

   public boolean isSimplified() {
      return this._simplified;
   }

   public void setSimplified() {
      this._simplified = true;
   }

   public void setHasIdCall(boolean flag) {
      this._hasIdCall = flag;
   }

   public void setOutputProperty(String key, String value) {
      if (this._outputProperties == null) {
         this._outputProperties = new Properties();
      }

      this._outputProperties.setProperty(key, value);
   }

   public void setOutputProperties(Properties props) {
      this._outputProperties = props;
   }

   public Properties getOutputProperties() {
      return this._outputProperties;
   }

   public Output getLastOutputElement() {
      return this._lastOutputElement;
   }

   public void setMultiDocument(boolean flag) {
      this._multiDocument = flag;
   }

   public boolean isMultiDocument() {
      return this._multiDocument;
   }

   public void setCallsNodeset(boolean flag) {
      if (flag) {
         this.setMultiDocument(flag);
      }

      this._callsNodeset = flag;
   }

   public boolean callsNodeset() {
      return this._callsNodeset;
   }

   public void numberFormattingUsed() {
      this._numberFormattingUsed = true;
      Stylesheet parent = this.getParentStylesheet();
      if (null != parent) {
         parent.numberFormattingUsed();
      }

   }

   public void setImportPrecedence(int precedence) {
      this._importPrecedence = precedence;
      Enumeration elements = this.elements();

      while(elements.hasMoreElements()) {
         SyntaxTreeNode child = (SyntaxTreeNode)elements.nextElement();
         if (child instanceof Include) {
            Stylesheet included = ((Include)child).getIncludedStylesheet();
            if (included != null && included._includedFrom == this) {
               included.setImportPrecedence(precedence);
            }
         }
      }

      if (this._importedFrom != null) {
         if (this._importedFrom.getImportPrecedence() < precedence) {
            Parser parser = this.getParser();
            int nextPrecedence = parser.getNextImportPrecedence();
            this._importedFrom.setImportPrecedence(nextPrecedence);
         }
      } else if (this._includedFrom != null && this._includedFrom.getImportPrecedence() != precedence) {
         this._includedFrom.setImportPrecedence(precedence);
      }

   }

   public int getImportPrecedence() {
      return this._importPrecedence;
   }

   public int getMinimumDescendantPrecedence() {
      if (this._minimumDescendantPrecedence == -1) {
         int min = this.getImportPrecedence();
         int inclImpCount = this._includedStylesheets != null ? this._includedStylesheets.size() : 0;

         for(int i = 0; i < inclImpCount; ++i) {
            int prec = ((Stylesheet)this._includedStylesheets.elementAt(i)).getMinimumDescendantPrecedence();
            if (prec < min) {
               min = prec;
            }
         }

         this._minimumDescendantPrecedence = min;
      }

      return this._minimumDescendantPrecedence;
   }

   public boolean checkForLoop(String systemId) {
      if (this._systemId != null && this._systemId.equals(systemId)) {
         return true;
      } else {
         return this._parentStylesheet != null ? this._parentStylesheet.checkForLoop(systemId) : false;
      }
   }

   public void setParser(Parser parser) {
      super.setParser(parser);
      this._name = this.makeStylesheetName("__stylesheet_");
   }

   public void setParentStylesheet(Stylesheet parent) {
      this._parentStylesheet = parent;
   }

   public Stylesheet getParentStylesheet() {
      return this._parentStylesheet;
   }

   public void setImportingStylesheet(Stylesheet parent) {
      this._importedFrom = parent;
      parent.addIncludedStylesheet(this);
   }

   public void setIncludingStylesheet(Stylesheet parent) {
      this._includedFrom = parent;
      parent.addIncludedStylesheet(this);
   }

   public void addIncludedStylesheet(Stylesheet child) {
      if (this._includedStylesheets == null) {
         this._includedStylesheets = new Vector();
      }

      this._includedStylesheets.addElement(child);
   }

   public void setSystemId(String systemId) {
      if (systemId != null) {
         this._systemId = SystemIDResolver.getAbsoluteURI(systemId);
      }

   }

   public String getSystemId() {
      return this._systemId;
   }

   public void setSourceLoader(SourceLoader loader) {
      this._loader = loader;
   }

   public SourceLoader getSourceLoader() {
      return this._loader;
   }

   private QName makeStylesheetName(String prefix) {
      return this.getParser().getQName(prefix + this.getXSLTC().nextStylesheetSerial());
   }

   public boolean hasGlobals() {
      return this._globals.size() > 0;
   }

   public boolean hasLocalParams() {
      if (this._hasLocalParams == null) {
         Vector templates = this.getAllValidTemplates();
         int n = templates.size();

         for(int i = 0; i < n; ++i) {
            Template template = (Template)templates.elementAt(i);
            if (template.hasParams()) {
               this._hasLocalParams = Boolean.TRUE;
               return true;
            }
         }

         this._hasLocalParams = Boolean.FALSE;
         return false;
      } else {
         return this._hasLocalParams;
      }
   }

   protected void addPrefixMapping(String prefix, String uri) {
      if (!prefix.equals("") || !uri.equals("http://www.w3.org/1999/xhtml")) {
         super.addPrefixMapping(prefix, uri);
      }
   }

   private void extensionURI(String prefixes, SymbolTable stable) {
      if (prefixes != null) {
         StringTokenizer tokens = new StringTokenizer(prefixes);

         while(tokens.hasMoreTokens()) {
            String prefix = tokens.nextToken();
            String uri = this.lookupNamespace(prefix);
            if (uri != null) {
               this._extensions.put(uri, prefix);
            }
         }
      }

   }

   public boolean isExtension(String uri) {
      return this._extensions.get(uri) != null;
   }

   public void declareExtensionPrefixes(Parser parser) {
      SymbolTable stable = parser.getSymbolTable();
      String extensionPrefixes = this.getAttribute("extension-element-prefixes");
      this.extensionURI(extensionPrefixes, stable);
   }

   public void parseContents(Parser parser) {
      SymbolTable stable = parser.getSymbolTable();
      this.addPrefixMapping("xml", "http://www.w3.org/XML/1998/namespace");
      Stylesheet sheet = stable.addStylesheet(this._name, this);
      if (sheet != null) {
         ErrorMsg err = new ErrorMsg("MULTIPLE_STYLESHEET_ERR", this);
         parser.reportError(3, err);
      }

      if (this._simplified) {
         stable.excludeURI("http://www.w3.org/1999/XSL/Transform");
         Template template = new Template();
         template.parseSimplified(this, parser);
      } else {
         this.parseOwnChildren(parser);
      }

   }

   public final void parseOwnChildren(Parser parser) {
      SymbolTable stable = parser.getSymbolTable();
      String excludePrefixes = this.getAttribute("exclude-result-prefixes");
      String extensionPrefixes = this.getAttribute("extension-element-prefixes");
      stable.pushExcludedNamespacesContext();
      stable.excludeURI("http://www.w3.org/1999/XSL/Transform");
      stable.excludeNamespaces(excludePrefixes);
      stable.excludeNamespaces(extensionPrefixes);
      Vector contents = this.getContents();
      int count = contents.size();

      int i;
      SyntaxTreeNode child;
      for(i = 0; i < count; ++i) {
         child = (SyntaxTreeNode)contents.elementAt(i);
         if (child instanceof VariableBase || child instanceof NamespaceAlias) {
            parser.getSymbolTable().setCurrentNode(child);
            child.parseContents(parser);
         }
      }

      for(i = 0; i < count; ++i) {
         child = (SyntaxTreeNode)contents.elementAt(i);
         if (!(child instanceof VariableBase) && !(child instanceof NamespaceAlias)) {
            parser.getSymbolTable().setCurrentNode(child);
            child.parseContents(parser);
         }

         if (!this._templateInlining && child instanceof Template) {
            Template template = (Template)child;
            String name = "template$dot$" + template.getPosition();
            template.setName(parser.getQName(name));
         }
      }

      stable.popExcludedNamespacesContext();
   }

   public void processModes() {
      if (this._defaultMode == null) {
         this._defaultMode = new Mode((QName)null, this, "");
      }

      this._defaultMode.processPatterns(this._keys);
      Enumeration modes = this._modes.elements();

      while(modes.hasMoreElements()) {
         Mode mode = (Mode)modes.nextElement();
         mode.processPatterns(this._keys);
      }

   }

   private void compileModes(ClassGenerator classGen) {
      this._defaultMode.compileApplyTemplates(classGen);
      Enumeration modes = this._modes.elements();

      while(modes.hasMoreElements()) {
         Mode mode = (Mode)modes.nextElement();
         mode.compileApplyTemplates(classGen);
      }

   }

   public Mode getMode(QName modeName) {
      if (modeName == null) {
         if (this._defaultMode == null) {
            this._defaultMode = new Mode((QName)null, this, "");
         }

         return this._defaultMode;
      } else {
         Mode mode = (Mode)this._modes.get(modeName);
         if (mode == null) {
            String suffix = Integer.toString(this._nextModeSerial++);
            this._modes.put(modeName, mode = new Mode(modeName, this, suffix));
         }

         return mode;
      }
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      int count = this._globals.size();

      for(int i = 0; i < count; ++i) {
         VariableBase var = (VariableBase)this._globals.elementAt(i);
         var.typeCheck(stable);
      }

      return this.typeCheckContents(stable);
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      this.translate();
   }

   private void addDOMField(ClassGenerator classGen) {
      FieldGen fgen = new FieldGen(1, Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;"), "_dom", classGen.getConstantPool());
      classGen.addField(fgen.getField());
   }

   private void addStaticField(ClassGenerator classGen, String type, String name) {
      FieldGen fgen = new FieldGen(12, Util.getJCRefType(type), name, classGen.getConstantPool());
      classGen.addField(fgen.getField());
   }

   public void translate() {
      this._className = this.getXSLTC().getClassName();
      ClassGenerator classGen = new ClassGenerator(this._className, "com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "", 33, (String[])null, this);
      this.addDOMField(classGen);
      this.compileTransform(classGen);
      Enumeration elements = this.elements();

      while(elements.hasMoreElements()) {
         Object element = elements.nextElement();
         if (element instanceof Template) {
            Template template = (Template)element;
            this.getMode(template.getModeName()).addTemplate(template);
         } else if (element instanceof AttributeSet) {
            ((AttributeSet)element).translate(classGen, (MethodGenerator)null);
         } else if (element instanceof Output) {
            Output output = (Output)element;
            if (output.enabled()) {
               this._lastOutputElement = output;
            }
         }
      }

      this.checkOutputMethod();
      this.processModes();
      this.compileModes(classGen);
      this.compileStaticInitializer(classGen);
      this.compileConstructor(classGen, this._lastOutputElement);
      if (!this.getParser().errorsFound()) {
         this.getXSLTC().dumpClass(classGen.getJavaClass());
      }

   }

   private void compileStaticInitializer(ClassGenerator classGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = new InstructionList();
      MethodGenerator staticConst = new MethodGenerator(9, com.oracle.wls.shaded.org.apache.bcel.generic.Type.VOID, (com.oracle.wls.shaded.org.apache.bcel.generic.Type[])null, (String[])null, "<clinit>", this._className, il, cpg);
      this.addStaticField(classGen, "[Ljava/lang/String;", "_sNamesArray");
      this.addStaticField(classGen, "[Ljava/lang/String;", "_sUrisArray");
      this.addStaticField(classGen, "[I", "_sTypesArray");
      this.addStaticField(classGen, "[Ljava/lang/String;", "_sNamespaceArray");
      int charDataFieldCount = this.getXSLTC().getCharacterDataCount();

      for(int i = 0; i < charDataFieldCount; ++i) {
         this.addStaticField(classGen, "[C", "_scharData" + i);
      }

      Vector namesIndex = this.getXSLTC().getNamesIndex();
      int size = namesIndex.size();
      String[] namesArray = new String[size];
      String[] urisArray = new String[size];
      int[] typesArray = new int[size];

      int i;
      for(i = 0; i < size; ++i) {
         String encodedName = (String)namesIndex.elementAt(i);
         int index;
         if ((index = encodedName.lastIndexOf(58)) > -1) {
            urisArray[i] = encodedName.substring(0, index);
         }

         ++index;
         if (encodedName.charAt(index) == '@') {
            typesArray[i] = 2;
            ++index;
         } else if (encodedName.charAt(index) == '?') {
            typesArray[i] = 13;
            ++index;
         } else {
            typesArray[i] = 1;
         }

         if (index == 0) {
            namesArray[i] = encodedName;
         } else {
            namesArray[i] = encodedName.substring(index);
         }
      }

      staticConst.markChunkStart();
      il.append((CompoundInstruction)(new PUSH(cpg, size)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ANEWARRAY(cpg.addClass("java.lang.String"))));
      i = cpg.addFieldref(this._className, "_sNamesArray", "[Ljava/lang/String;");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTSTATIC(i)));
      staticConst.markChunkEnd();

      int urisArrayRef;
      for(urisArrayRef = 0; urisArrayRef < size; ++urisArrayRef) {
         String name = namesArray[urisArrayRef];
         staticConst.markChunkStart();
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(i)));
         il.append((CompoundInstruction)(new PUSH(cpg, urisArrayRef)));
         il.append((CompoundInstruction)(new PUSH(cpg, name)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)AASTORE);
         staticConst.markChunkEnd();
      }

      staticConst.markChunkStart();
      il.append((CompoundInstruction)(new PUSH(cpg, size)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ANEWARRAY(cpg.addClass("java.lang.String"))));
      urisArrayRef = cpg.addFieldref(this._className, "_sUrisArray", "[Ljava/lang/String;");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTSTATIC(urisArrayRef)));
      staticConst.markChunkEnd();

      int typesArrayRef;
      for(typesArrayRef = 0; typesArrayRef < size; ++typesArrayRef) {
         String uri = urisArray[typesArrayRef];
         staticConst.markChunkStart();
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(urisArrayRef)));
         il.append((CompoundInstruction)(new PUSH(cpg, typesArrayRef)));
         il.append((CompoundInstruction)(new PUSH(cpg, uri)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)AASTORE);
         staticConst.markChunkEnd();
      }

      staticConst.markChunkStart();
      il.append((CompoundInstruction)(new PUSH(cpg, size)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new NEWARRAY(BasicType.INT)));
      typesArrayRef = cpg.addFieldref(this._className, "_sTypesArray", "[I");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTSTATIC(typesArrayRef)));
      staticConst.markChunkEnd();

      int namespaceArrayRef;
      for(int i = 0; i < size; ++i) {
         namespaceArrayRef = typesArray[i];
         staticConst.markChunkStart();
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(typesArrayRef)));
         il.append((CompoundInstruction)(new PUSH(cpg, i)));
         il.append((CompoundInstruction)(new PUSH(cpg, namespaceArrayRef)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)IASTORE);
         staticConst.markChunkEnd();
      }

      Vector namespaces = this.getXSLTC().getNamespaceIndex();
      staticConst.markChunkStart();
      il.append((CompoundInstruction)(new PUSH(cpg, namespaces.size())));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ANEWARRAY(cpg.addClass("java.lang.String"))));
      namespaceArrayRef = cpg.addFieldref(this._className, "_sNamespaceArray", "[Ljava/lang/String;");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTSTATIC(namespaceArrayRef)));
      staticConst.markChunkEnd();

      for(int i = 0; i < namespaces.size(); ++i) {
         String ns = (String)namespaces.elementAt(i);
         staticConst.markChunkStart();
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(namespaceArrayRef)));
         il.append((CompoundInstruction)(new PUSH(cpg, i)));
         il.append((CompoundInstruction)(new PUSH(cpg, ns)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)AASTORE);
         staticConst.markChunkEnd();
      }

      Vector namespaceAncestors = this.getXSLTC().getNSAncestorPointers();
      int prefixURIPairsIdxArrayRef;
      int prefixURIPairsRef;
      if (namespaceAncestors != null && namespaceAncestors.size() != 0) {
         this.addStaticField(classGen, "[I", "_sNamespaceAncestorsArray");
         staticConst.markChunkStart();
         il.append((CompoundInstruction)(new PUSH(cpg, namespaceAncestors.size())));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new NEWARRAY(BasicType.INT)));
         int namespaceAncestorsArrayRef = cpg.addFieldref(this._className, "_sNamespaceAncestorsArray", "[I");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTSTATIC(namespaceAncestorsArrayRef)));
         staticConst.markChunkEnd();

         for(prefixURIPairsIdxArrayRef = 0; prefixURIPairsIdxArrayRef < namespaceAncestors.size(); ++prefixURIPairsIdxArrayRef) {
            prefixURIPairsRef = (Integer)namespaceAncestors.get(prefixURIPairsIdxArrayRef);
            staticConst.markChunkStart();
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(namespaceAncestorsArrayRef)));
            il.append((CompoundInstruction)(new PUSH(cpg, prefixURIPairsIdxArrayRef)));
            il.append((CompoundInstruction)(new PUSH(cpg, prefixURIPairsRef)));
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)IASTORE);
            staticConst.markChunkEnd();
         }
      }

      Vector prefixURIPairsIdx = this.getXSLTC().getPrefixURIPairsIdx();
      int toCharArray;
      if (prefixURIPairsIdx != null && prefixURIPairsIdx.size() != 0) {
         this.addStaticField(classGen, "[I", "_sPrefixURIsIdxArray");
         staticConst.markChunkStart();
         il.append((CompoundInstruction)(new PUSH(cpg, prefixURIPairsIdx.size())));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new NEWARRAY(BasicType.INT)));
         prefixURIPairsIdxArrayRef = cpg.addFieldref(this._className, "_sPrefixURIsIdxArray", "[I");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTSTATIC(prefixURIPairsIdxArrayRef)));
         staticConst.markChunkEnd();

         for(prefixURIPairsRef = 0; prefixURIPairsRef < prefixURIPairsIdx.size(); ++prefixURIPairsRef) {
            toCharArray = (Integer)prefixURIPairsIdx.get(prefixURIPairsRef);
            staticConst.markChunkStart();
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(prefixURIPairsIdxArrayRef)));
            il.append((CompoundInstruction)(new PUSH(cpg, prefixURIPairsRef)));
            il.append((CompoundInstruction)(new PUSH(cpg, toCharArray)));
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)IASTORE);
            staticConst.markChunkEnd();
         }
      }

      Vector prefixURIPairs = this.getXSLTC().getPrefixURIPairs();
      if (prefixURIPairs != null && prefixURIPairs.size() != 0) {
         this.addStaticField(classGen, "[Ljava/lang/String;", "_sPrefixURIPairsArray");
         staticConst.markChunkStart();
         il.append((CompoundInstruction)(new PUSH(cpg, prefixURIPairs.size())));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ANEWARRAY(cpg.addClass("java.lang.String"))));
         prefixURIPairsRef = cpg.addFieldref(this._className, "_sPrefixURIPairsArray", "[Ljava/lang/String;");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTSTATIC(prefixURIPairsRef)));
         staticConst.markChunkEnd();

         for(toCharArray = 0; toCharArray < prefixURIPairs.size(); ++toCharArray) {
            String prefixOrURI = (String)prefixURIPairs.get(toCharArray);
            staticConst.markChunkStart();
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(prefixURIPairsRef)));
            il.append((CompoundInstruction)(new PUSH(cpg, toCharArray)));
            il.append((CompoundInstruction)(new PUSH(cpg, prefixOrURI)));
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)AASTORE);
            staticConst.markChunkEnd();
         }
      }

      prefixURIPairsRef = this.getXSLTC().getCharacterDataCount();
      toCharArray = cpg.addMethodref("java.lang.String", "toCharArray", "()[C");

      for(int i = 0; i < prefixURIPairsRef; ++i) {
         staticConst.markChunkStart();
         il.append((CompoundInstruction)(new PUSH(cpg, this.getXSLTC().getCharacterData(i))));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(toCharArray)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTSTATIC(cpg.addFieldref(this._className, "_scharData" + i, "[C"))));
         staticConst.markChunkEnd();
      }

      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)RETURN);
      classGen.addMethod(staticConst);
   }

   private void compileConstructor(ClassGenerator classGen, Output output) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = new InstructionList();
      MethodGenerator constructor = new MethodGenerator(1, com.oracle.wls.shaded.org.apache.bcel.generic.Type.VOID, (com.oracle.wls.shaded.org.apache.bcel.generic.Type[])null, (String[])null, "<init>", this._className, il, cpg);
      il.append(classGen.loadTranslet());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESPECIAL(cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "<init>", "()V"))));
      constructor.markChunkStart();
      il.append(classGen.loadTranslet());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(cpg.addFieldref(this._className, "_sNamesArray", "[Ljava/lang/String;"))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(cpg.addFieldref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "namesArray", "[Ljava/lang/String;"))));
      constructor.markChunkEnd();
      constructor.markChunkStart();
      il.append(classGen.loadTranslet());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(cpg.addFieldref(this._className, "_sUrisArray", "[Ljava/lang/String;"))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(cpg.addFieldref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "urisArray", "[Ljava/lang/String;"))));
      constructor.markChunkEnd();
      constructor.markChunkStart();
      il.append(classGen.loadTranslet());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(cpg.addFieldref(this._className, "_sTypesArray", "[I"))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(cpg.addFieldref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "typesArray", "[I"))));
      constructor.markChunkEnd();
      constructor.markChunkStart();
      il.append(classGen.loadTranslet());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETSTATIC(cpg.addFieldref(this._className, "_sNamespaceArray", "[Ljava/lang/String;"))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(cpg.addFieldref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "namespaceArray", "[Ljava/lang/String;"))));
      constructor.markChunkEnd();
      constructor.markChunkStart();
      il.append(classGen.loadTranslet());
      il.append((CompoundInstruction)(new PUSH(cpg, 101)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(cpg.addFieldref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "transletVersion", "I"))));
      constructor.markChunkEnd();
      if (this._hasIdCall) {
         constructor.markChunkStart();
         il.append(classGen.loadTranslet());
         il.append((CompoundInstruction)(new PUSH(cpg, Boolean.TRUE)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(cpg.addFieldref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "_hasIdCall", "Z"))));
         constructor.markChunkEnd();
      }

      if (output != null) {
         constructor.markChunkStart();
         output.translate(classGen, constructor);
         constructor.markChunkEnd();
      }

      if (this._numberFormattingUsed) {
         constructor.markChunkStart();
         DecimalFormatting.translateDefaultDFS(classGen, constructor);
         constructor.markChunkEnd();
      }

      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)RETURN);
      classGen.addMethod(constructor);
   }

   private String compileTopLevel(ClassGenerator classGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      com.oracle.wls.shaded.org.apache.bcel.generic.Type[] argTypes = new com.oracle.wls.shaded.org.apache.bcel.generic.Type[]{Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;"), Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;"), Util.getJCRefType(TRANSLET_OUTPUT_SIG)};
      String[] argNames = new String[]{"document", "iterator", "handler"};
      InstructionList il = new InstructionList();
      MethodGenerator toplevel = new MethodGenerator(1, com.oracle.wls.shaded.org.apache.bcel.generic.Type.VOID, argTypes, argNames, "topLevel", this._className, il, classGen.getConstantPool());
      toplevel.addException("com.oracle.wls.shaded.org.apache.xalan.xsltc.TransletException");
      LocalVariableGen current = toplevel.addLocalVariable("current", com.oracle.wls.shaded.org.apache.bcel.generic.Type.INT, (InstructionHandle)null, (InstructionHandle)null);
      int setFilter = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "setFilter", "(Lorg/apache/xalan/xsltc/StripFilter;)V");
      int gitr = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
      il.append(toplevel.loadDOM());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(gitr, 1)));
      il.append(toplevel.nextNode());
      current.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ISTORE(current.getIndex()))));
      Vector varDepElements = new Vector(this._globals);
      Enumeration elements = this.elements();

      while(elements.hasMoreElements()) {
         Object element = elements.nextElement();
         if (element instanceof Key) {
            varDepElements.add(element);
         }
      }

      varDepElements = this.resolveDependencies(varDepElements);
      int count = varDepElements.size();

      for(int i = 0; i < count; ++i) {
         TopLevelElement tle = (TopLevelElement)varDepElements.elementAt(i);
         tle.translate(classGen, toplevel);
         if (tle instanceof Key) {
            Key key = (Key)tle;
            this._keys.put(key.getName(), key);
         }
      }

      Vector whitespaceRules = new Vector();
      elements = this.elements();

      while(elements.hasMoreElements()) {
         Object element = elements.nextElement();
         if (element instanceof DecimalFormatting) {
            ((DecimalFormatting)element).translate(classGen, toplevel);
         } else if (element instanceof Whitespace) {
            whitespaceRules.addAll(((Whitespace)element).getRules());
         }
      }

      if (whitespaceRules.size() > 0) {
         Whitespace.translateRules(whitespaceRules, classGen);
      }

      if (classGen.containsMethod("stripSpace", "(Lorg/apache/xalan/xsltc/DOM;II)Z") != null) {
         il.append(toplevel.loadDOM());
         il.append(classGen.loadTranslet());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(setFilter, 2)));
      }

      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)RETURN);
      classGen.addMethod(toplevel);
      return "(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + TRANSLET_OUTPUT_SIG + ")V";
   }

   private Vector resolveDependencies(Vector input) {
      Vector result = new Vector();

      boolean changed;
      label32:
      do {
         if (input.size() <= 0) {
            return result;
         }

         changed = false;
         int i = 0;

         while(true) {
            while(true) {
               if (i >= input.size()) {
                  continue label32;
               }

               TopLevelElement vde = (TopLevelElement)input.elementAt(i);
               Vector dep = vde.getDependencies();
               if (dep != null && !result.containsAll(dep)) {
                  ++i;
               } else {
                  result.addElement(vde);
                  input.remove(i);
                  changed = true;
               }
            }
         }
      } while(changed);

      ErrorMsg err = new ErrorMsg("CIRCULAR_VARIABLE_ERR", input.toString(), this);
      this.getParser().reportError(3, err);
      return result;
   }

   private String compileBuildKeys(ClassGenerator classGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      com.oracle.wls.shaded.org.apache.bcel.generic.Type[] argTypes = new com.oracle.wls.shaded.org.apache.bcel.generic.Type[]{Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;"), Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;"), Util.getJCRefType(TRANSLET_OUTPUT_SIG), com.oracle.wls.shaded.org.apache.bcel.generic.Type.INT};
      String[] argNames = new String[]{"document", "iterator", "handler", "current"};
      InstructionList il = new InstructionList();
      MethodGenerator buildKeys = new MethodGenerator(1, com.oracle.wls.shaded.org.apache.bcel.generic.Type.VOID, argTypes, argNames, "buildKeys", this._className, il, classGen.getConstantPool());
      buildKeys.addException("com.oracle.wls.shaded.org.apache.xalan.xsltc.TransletException");
      Enumeration elements = this.elements();

      while(elements.hasMoreElements()) {
         Object element = elements.nextElement();
         if (element instanceof Key) {
            Key key = (Key)element;
            key.translate(classGen, buildKeys);
            this._keys.put(key.getName(), key);
         }
      }

      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)RETURN);
      classGen.addMethod(buildKeys);
      return "(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + TRANSLET_OUTPUT_SIG + "I)V";
   }

   private void compileTransform(ClassGenerator classGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      com.oracle.wls.shaded.org.apache.bcel.generic.Type[] argTypes = new com.oracle.wls.shaded.org.apache.bcel.generic.Type[]{Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;"), Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;"), Util.getJCRefType(TRANSLET_OUTPUT_SIG)};
      String[] argNames = new String[]{"document", "iterator", "handler"};
      InstructionList il = new InstructionList();
      MethodGenerator transf = new MethodGenerator(1, com.oracle.wls.shaded.org.apache.bcel.generic.Type.VOID, argTypes, argNames, "transform", this._className, il, classGen.getConstantPool());
      transf.addException("com.oracle.wls.shaded.org.apache.xalan.xsltc.TransletException");
      LocalVariableGen current = transf.addLocalVariable("current", com.oracle.wls.shaded.org.apache.bcel.generic.Type.INT, (InstructionHandle)null, (InstructionHandle)null);
      String applyTemplatesSig = classGen.getApplyTemplatesSig();
      int applyTemplates = cpg.addMethodref(this.getClassName(), "applyTemplates", applyTemplatesSig);
      int domField = cpg.addFieldref(this.getClassName(), "_dom", "Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;");
      il.append(classGen.loadTranslet());
      if (this.isMultiDocument()) {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new NEW(cpg.addClass("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.MultiDOM"))));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
      }

      il.append(classGen.loadTranslet());
      il.append(transf.loadDOM());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "makeDOMAdapter", "(Lorg/apache/xalan/xsltc/DOM;)Lorg/apache/xalan/xsltc/dom/DOMAdapter;"))));
      int gitr;
      if (this.isMultiDocument()) {
         gitr = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.MultiDOM", "<init>", "(Lorg/apache/xalan/xsltc/DOM;)V");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESPECIAL(gitr)));
      }

      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(domField)));
      gitr = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
      il.append(transf.loadDOM());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(gitr, 1)));
      il.append(transf.nextNode());
      current.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ISTORE(current.getIndex()))));
      il.append(classGen.loadTranslet());
      il.append(transf.loadHandler());
      int index = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "transferOutputSettings", "(" + OUTPUT_HANDLER_SIG + ")V");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(index)));
      String keySig = this.compileBuildKeys(classGen);
      cpg.addMethodref(this.getClassName(), "buildKeys", keySig);
      Enumeration toplevel = this.elements();
      if (this._globals.size() > 0 || toplevel.hasMoreElements()) {
         String topLevelSig = this.compileTopLevel(classGen);
         int topLevelIdx = cpg.addMethodref(this.getClassName(), "topLevel", topLevelSig);
         il.append(classGen.loadTranslet());
         il.append(classGen.loadTranslet());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(domField)));
         il.append(transf.loadIterator());
         il.append(transf.loadHandler());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(topLevelIdx)));
      }

      il.append(transf.loadHandler());
      il.append(transf.startDocument());
      il.append(classGen.loadTranslet());
      il.append(classGen.loadTranslet());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(domField)));
      il.append(transf.loadIterator());
      il.append(transf.loadHandler());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(applyTemplates)));
      il.append(transf.loadHandler());
      il.append(transf.endDocument());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)RETURN);
      classGen.addMethod(transf);
   }

   private void peepHoleOptimization(MethodGenerator methodGen) {
      String pattern = "`aload'`pop'`instruction'";
      InstructionList il = methodGen.getInstructionList();
      InstructionFinder find = new InstructionFinder(il);
      Iterator iter = find.search("`aload'`pop'`instruction'");

      while(iter.hasNext()) {
         InstructionHandle[] match = (InstructionHandle[])((InstructionHandle[])iter.next());

         try {
            il.delete(match[0], match[1]);
         } catch (TargetLostException var8) {
         }
      }

   }

   public int addParam(Param param) {
      this._globals.addElement(param);
      return this._globals.size() - 1;
   }

   public int addVariable(Variable global) {
      this._globals.addElement(global);
      return this._globals.size() - 1;
   }

   public void display(int indent) {
      this.indent(indent);
      Util.println("Stylesheet");
      this.displayContents(indent + 4);
   }

   public String getNamespace(String prefix) {
      return this.lookupNamespace(prefix);
   }

   public String getClassName() {
      return this._className;
   }

   public Vector getTemplates() {
      return this._templates;
   }

   public Vector getAllValidTemplates() {
      if (this._includedStylesheets == null) {
         return this._templates;
      } else {
         if (this._allValidTemplates == null) {
            Vector templates = new Vector();
            int size = this._includedStylesheets.size();

            for(int i = 0; i < size; ++i) {
               Stylesheet included = (Stylesheet)this._includedStylesheets.elementAt(i);
               templates.addAll(included.getAllValidTemplates());
            }

            templates.addAll(this._templates);
            if (this._parentStylesheet != null) {
               return templates;
            }

            this._allValidTemplates = templates;
         }

         return this._allValidTemplates;
      }
   }

   protected void addTemplate(Template template) {
      this._templates.addElement(template);
   }
}
