package weblogic.xml.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.Getopt2;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.CombinedIterator;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class ProcessorCompiler extends CodeGenerator {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   static final String EMPTY_STRING = new String();
   static final String PREPROC_METHOD_PREFIX = "__pre_";
   static final String POSTPROC_METHOD_PREFIX = "__post_";
   static final String TEMPLATE_FILE_NAME = "processor.j";
   private ProcessingInstructions instrux;
   private Map actionTable = new HashMap();
   private Map actionIDMap = new HashMap();
   private PAction currentAction;
   private PActionCode currentActionCode;
   private boolean generateMain = false;

   public ProcessorCompiler(Getopt2 opts) {
      super(opts);
      this.generateMain = opts.hasOption("generatemain");
   }

   public Enumeration outputs(Object[] inputs) throws Exception {
      Debug.assertion(inputs.length == 1);
      Output out = new Output();

      try {
         out.setProcessingInstructions((ProcessingInstructions)inputs[0]);
      } catch (ClassCastException var4) {
         throw new AssertionError(var4);
      }

      return (new Vector(Arrays.asList((Object[])(new Output[]{out})))).elements();
   }

   protected void prepare(CodeGenerator.Output output) {
      this.instrux = ((Output)output).getProcessingInstructions();
      Debug.assertion(this.instrux != null);
   }

   public String main_method() throws CodeGenerationException {
      return this.generateMain ? this.parse(this.getProductionRule("main_method_code")) : new String();
   }

   public String processor_package_decl() {
      return "package " + this.instrux.getProcessorPackage() + ";";
   }

   public String processor_class() {
      return this.instrux.getProcessorClass();
   }

   public String extends_super_class() {
      String superClassName = this.instrux.getProcessorSuperClass();
      return superClassName == null ? EMPTY_STRING : "extends " + superClassName;
   }

   public String public_id_decl() {
      return this.instrux.getLocalDTDResourceName() != null ? "private static final String publicId = \n  \"" + this.instrux.getPublicId() + "\";" : EMPTY_STRING;
   }

   public String get_processor_driver() {
      return this.instrux.getLocalDTDResourceName() != null ? "new ProcessorDriver(this, publicId, localDTDResourceName, validate)" : "new ProcessorDriver(this, validate)";
   }

   public String local_dtd_decl() {
      String localDtd = this.instrux.getLocalDTDResourceName();
      return localDtd != null ? "private static final String localDTDResourceName = \n  \"" + localDtd + "\";" : EMPTY_STRING;
   }

   public String init_paths() {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("  static {\n");
      Collection actions = this.instrux.getAllProcessingActions();
      Iterator ia = actions.iterator();

      while(ia.hasNext()) {
         PAction a = (PAction)ia.next();
         int actionId = a.getId();
         String[] paths = a.getPaths();

         for(int i = 0; i < paths.length; ++i) {
            String path = paths[i];
            sbuf.append("    paths.put(\n      \"" + path + "\",\n      new Integer(" + actionId + ")\n    );\n");
            this.actionTable.put(path, new Integer(actionId));
            this.actionIDMap.put(new Integer(actionId), a);
         }
      }

      sbuf.append("}");
      return sbuf.toString();
   }

   public String preproc_dispatch() {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("    switch (id.intValue()) {\n");
      Iterator i = this.actionIDMap.keySet().iterator();

      while(i.hasNext()) {
         Integer actionId = (Integer)i.next();
         sbuf.append("      case " + actionId + ": {\n");
         sbuf.append("        __pre_" + actionId + "(pctx);\n");
         if (((PAction)this.actionIDMap.get(actionId)).isStartAction()) {
            sbuf.append("       //phase=\"element-start\"\n");
            sbuf.append("        __post_" + actionId + "(pctx);\n");
         }

         sbuf.append("        break;\n");
         sbuf.append("      }\n");
      }

      sbuf.append("      default: {\n");
      sbuf.append("        throw new AssertionError(id.toString());\n");
      sbuf.append("      }\n");
      sbuf.append("    }\n");
      return sbuf.toString();
   }

   public String postproc_dispatch() {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("    switch (id.intValue()) {\n");
      Iterator i = this.actionIDMap.keySet().iterator();

      while(i.hasNext()) {
         Integer actionId = (Integer)i.next();
         sbuf.append("      case " + actionId + ": {\n");
         if (!((PAction)this.actionIDMap.get(actionId)).isStartAction()) {
            sbuf.append("        __post_" + actionId + "(pctx);\n");
         } else {
            sbuf.append("        //phase=\"element-start\"\n");
         }

         sbuf.append("        break;\n");
         sbuf.append("      }\n");
      }

      sbuf.append("      default: {\n");
      sbuf.append("        throw new AssertionError(id.toString());\n");
      sbuf.append("      }\n");
      sbuf.append("    }\n");
      return sbuf.toString();
   }

   public String element_processing_dispatches() throws CodeGenerationException {
      StringBuffer sbuf = new StringBuffer();
      Collection actions = this.instrux.getAllProcessingActions();
      Iterator ia = actions.iterator();

      while(ia.hasNext()) {
         this.currentAction = (PAction)ia.next();
         this.currentActionCode = new PActionCode(this.currentAction);
         sbuf.append(this.parse(this.getProductionRule("element_processing_dispatch")));
      }

      return sbuf.toString();
   }

   public String element_path() {
      Debug.assertion(this.currentAction != null);
      String[] paths = this.currentAction.getPaths();
      StringBuffer sbuf = new StringBuffer();

      for(int i = 0; i < paths.length; ++i) {
         sbuf.append("\n   *   " + paths[i]);
      }

      return sbuf.toString();
   }

   public String document_scope_binding_getters() {
      Debug.assertion(this.currentAction != null);
      Debug.assertion(this.currentActionCode != null);
      StringBuffer sbuf = new StringBuffer();
      Collection bindings = this.currentAction.getBindings();
      if (bindings != null && bindings.size() > 0) {
         Iterator bi = bindings.iterator();

         while(bi.hasNext()) {
            Binding b = (Binding)bi.next();
            if (b.hasDocumentScope()) {
               String className = b.getClassName();
               String varName = b.getVariableName();
               sbuf.append("private " + className + " " + varName + ";\n");
               sbuf.append(getAGetter(className, varName) + "\n");
            }
         }
      }

      return sbuf.toString();
   }

   public String element_preproc_method() {
      Debug.assertion(this.currentAction != null);
      int pathId = this.currentAction.getId();
      return "__pre_" + pathId;
   }

   public String element_postproc_method() {
      Debug.assertion(this.currentAction != null);
      int pathId = this.currentAction.getId();
      return "__post_" + pathId;
   }

   public String throws_processing_exceptions() {
      Debug.assertion(this.currentAction != null);
      Debug.assertion(this.currentActionCode != null);
      StringBuffer sbuf = new StringBuffer();
      String javaCode = this.currentActionCode.getJavaCode();
      if (javaCode != null) {
         sbuf.append("SAXProcessorException");
      }

      if (this.currentAction.getValidation() != null || javaCode != null && javaCode.indexOf("SAXValidationException") >= 0) {
         if (sbuf.length() == 0) {
            sbuf.append("SAXValidationException");
         } else {
            sbuf.append(", SAXValidationException");
         }
      }

      return sbuf.length() > 0 ? "throws " + sbuf.toString() : new String();
   }

   public String new_bindings() {
      Debug.assertion(this.currentAction != null);
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("// bindings: ");
      Collection bindings = this.currentAction.getBindings();
      if (bindings.size() == 0) {
         sbuf.append("none\n");
      } else {
         sbuf.append("\n");
         Iterator bi = bindings.iterator();

         while(bi.hasNext()) {
            Binding b = (Binding)bi.next();
            String className = b.getClassName();
            String varName = b.getVariableName();
            if (b.hasDocumentScope()) {
               if (b.isInitialize()) {
                  sbuf.append("    " + varName + " = new " + className + "();\n");
               }
            } else {
               sbuf.append("    " + className + " " + varName + " =");
               if (b.isInitialize()) {
                  sbuf.append(" new " + className + "();\n");
               } else {
                  sbuf.append(" null;\n");
               }

               sbuf.append("    pctx.addBoundObject(" + varName + ", \"" + varName + "\");\n");
            }
         }
      }

      return sbuf.toString();
   }

   public String fetch_function_values() throws CodeGenerationException {
      StringBuffer sbuf = new StringBuffer();
      Debug.assertion(this.currentActionCode != null);
      Iterator i = this.currentActionCode.getFunctionRefs().entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry ent = (Map.Entry)i.next();
         FunctionRef fref = (FunctionRef)ent.getKey();
         String varName = (String)ent.getValue();
         String functionCall = getFunctionCallSyntax(fref);
         sbuf.append("String " + varName + " = " + functionCall + ";\n");
      }

      return sbuf.toString();
   }

   public String fetch_bound_objects() {
      Debug.assertion(this.currentAction != null);
      StringBuffer sbuf = new StringBuffer();
      Iterator bi = this.getAllBindingsIterator(this.currentAction);

      while(bi.hasNext()) {
         Binding b = (Binding)bi.next();
         if (!b.hasDocumentScope()) {
            String varName = b.getVariableName();
            String className = b.getClassName();
            sbuf.append("    " + className + " " + varName + " = (" + className + ")pctx.getBoundObject(\"" + varName + "\");\n");
         }
      }

      return sbuf.toString();
   }

   public String validations() throws CodeGenerationException {
      Debug.assertion(this.currentAction != null);
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("// validations: ");
      Validation validation = this.currentAction.getValidation();
      if (validation == null) {
         sbuf.append("none\n");
      } else {
         sbuf.append('\n');
         String valueVarName = this.element_value_variable();
         if (!validation.isNullable()) {
            sbuf.append("    if (" + valueVarName + ".length() == 0) ");
            sbuf.append(getValidationErrorThrow(this.currentAction + " must be a non-empty string"));
            sbuf.append('\n');
         }

         List validVals = validation.getValidValues();
         if (validVals != null && validVals.size() > 0) {
            sbuf.append("    if (");

            for(int i = 0; i < validVals.size(); ++i) {
               String value = (String)validVals.get(i);
               if (i > 0) {
                  sbuf.append(" && ");
               }

               sbuf.append("!\"" + value + "\".equals(" + valueVarName + ")");
            }

            sbuf.append(") ");
            String possibleValues = StringUtils.join((String[])((String[])validVals.toArray(new String[0])), ",");
            sbuf.append(getValidationErrorThrow(this.currentAction + " must be one of the values: " + possibleValues) + "\n");
         }

         Validation v = this.currentAction.getValidation();
         if (v != null && v.getMethodName() != null) {
            sbuf.append(this.parse(this.getProductionRule("invoke_validation_method")));
         }
      }

      return sbuf.toString();
   }

   public String validation_method_name() {
      Debug.assertion(this.currentAction != null);
      Validation v = this.currentAction.getValidation();
      Debug.assertion(v != null);
      String methodName = v.getMethodName();
      Debug.assertion(methodName != null);
      return methodName;
   }

   public String element_value_variable() {
      Debug.assertion(this.currentActionCode != null);
      String varName = this.currentActionCode.getFunctionValueVarName(new XPathFunctionRef("VALUE", ""));
      return varName;
   }

   public String post_actions() {
      Debug.assertion(this.currentActionCode != null);
      this.currentActionCode.replaceFunctionRefs();
      String javaCode = this.currentActionCode.getJavaCode();
      return javaCode == null ? new String() : javaCode;
   }

   protected Iterator getAllBindingsIterator(PAction action) {
      Debug.assertion(this.instrux != null);
      List l = new ArrayList();
      String[] paths = action.getPaths();

      for(int pi = 0; pi < paths.length; ++pi) {
         String path = paths[pi];
         int i = path.length() - 1;

         while(i > 0) {
            path = path.substring(0, i);
            i = path.lastIndexOf(46);
            PAction a = this.instrux.getProcessingAction(path + ".");
            if (a != null) {
               l.add(a.getBindings().iterator());
            }
         }
      }

      return new CombinedIterator(l);
   }

   protected static String getValidationErrorThrow(String msg) {
      return "throw new SAXValidationException(\"" + msg + "\");";
   }

   protected static String getAGetter(String className, String varName) {
      Debug.assertion(className != null);
      Debug.assertion(varName != null);
      String rootClassName = getRootClassName(className);
      return "public " + className + " get" + rootClassName + "() { return " + varName + "; }";
   }

   protected static String getPackageName(String className) {
      Debug.assertion(className != null);
      int i = className.lastIndexOf(46);
      return i == -1 ? null : className.substring(0, i);
   }

   protected static String getRootClassName(String fullClassName) {
      Debug.assertion(fullClassName != null);
      int i = fullClassName.lastIndexOf(46);
      return i == -1 ? fullClassName : fullClassName.substring(i + 1);
   }

   protected static String getFunctionCallSyntax(FunctionRef fref) throws CodeGenerationException {
      String fname = fref.getName();
      if ("VALUE".equalsIgnoreCase(fname) && fref instanceof XPathFunctionRef) {
         String expr = ((XPathFunctionRef)fref).getExpr();
         return expr.length() > 0 ? "Functions.value(pctx,\"" + expr + "\")" : "Functions.value(pctx)";
      } else {
         throw new CodeGenerationException("Unrecognized function call: " + fref);
      }
   }

   public static class Output extends CodeGenerator.Output implements Cloneable {
      private ProcessingInstructions pi;

      public Output() {
         this.setTemplate("processor.j");
      }

      public void setProcessingInstructions(ProcessingInstructions p) {
         this.pi = p;
         this.setOutputFile(ProcessorCompiler.getRootClassName(p.getProcessorClass()) + ".java");
         this.setPackage(p.getProcessorPackage());
      }

      public ProcessingInstructions getProcessingInstructions() {
         return this.pi;
      }
   }
}
