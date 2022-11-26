package weblogic.xml.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.Getopt2;
import weblogic.utils.StringUtils;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class GeneratorCompiler extends CodeGenerator {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   static final String EMPTY_STRING = new String();
   static final String GEN_METHOD_PREFIX = "__gen_";
   static final String TEMPLATE_FILE_NAME = "generator.j";
   private GeneratingInstructions instrux;
   private Map pathTable = new HashMap();
   private GAction currentAction;
   private FunctionRef currentFunctionRef;
   private int currentCtxVar;
   private boolean generateMain = false;

   public GeneratorCompiler(Getopt2 opts) {
      super(opts);
      this.generateMain = opts.hasOption("generatemain");
   }

   public Enumeration outputs(Object[] inputs) throws Exception {
      Debug.assertion(inputs.length == 1);
      Output out = new Output();

      try {
         out.setGeneratingInstructions((GeneratingInstructions)inputs[0]);
      } catch (ClassCastException var4) {
         throw new AssertionError(var4);
      }

      return (new Vector(Arrays.asList((Object[])(new Output[]{out})))).elements();
   }

   protected void prepare(CodeGenerator.Output output) {
      this.instrux = ((Output)output).getGeneratingInstructions();
      Debug.assertion(this.instrux != null);
   }

   private static String getRootClassName(String fullClassName) {
      Debug.assertion(fullClassName != null);
      int i = fullClassName.lastIndexOf(46);
      return i == -1 ? fullClassName : fullClassName.substring(i + 1);
   }

   public String generator_package_decl() {
      return "package " + this.instrux.getProcessorPackage() + ";";
   }

   public String generator_class() {
      return this.instrux.getProcessorClass();
   }

   public String extends_super_class() {
      String superClassName = this.instrux.getProcessorSuperClass();
      return superClassName == null ? EMPTY_STRING : "extends " + superClassName;
   }

   public String public_id_decl() {
      return "private static final String __publicId = \n  \"" + this.instrux.getPublicId() + "\";";
   }

   public String sys_id_decl() {
      return "private static final String __sysId = \n  \"" + this.instrux.getDtdURL() + "\";";
   }

   public String init_paths() {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("static {\n");
      Collection actions = this.instrux.getAllGeneratingActions();
      Iterator ia = actions.iterator();
      int i = 0;

      while(ia.hasNext()) {
         ++i;
         GAction a = (GAction)ia.next();
         String path = a.getPath();
         sbuf.append("    __paths.put(\n      \"" + path + "\",\n      new Integer(" + i + ")\n    );\n");
         this.pathTable.put(path, new Integer(i));
      }

      sbuf.append("  }");
      return sbuf.toString();
   }

   public String gen_dispatch() {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("switch (pathId.intValue()) {\n");
      Iterator i = this.pathTable.entrySet().iterator();
      String pathId = null;

      while(i.hasNext()) {
         Map.Entry ent = (Map.Entry)i.next();
         String path = (String)ent.getKey();
         pathId = ent.getValue().toString();
         GAction a = this.instrux.getGeneratingAction(path);
         String paramsStr = this.getParamsStr(a.getParams());
         sbuf.append("      case ");
         sbuf.append(pathId);
         sbuf.append(": {\n        ");
         sbuf.append("__gen_");
         sbuf.append(pathId);
         sbuf.append("(gctx");
         sbuf.append(paramsStr);
         sbuf.append(");\n        break;\n      }\n");
      }

      sbuf.append("      default: {\n");
      sbuf.append("        throw new AssertionError(pathId.toString());\n");
      sbuf.append("      }\n");
      sbuf.append("    }\n");
      return sbuf.toString();
   }

   private String getParamsStr(Collection params) {
      StringBuffer sbuf = new StringBuffer();
      if (params.size() == 0) {
         return "";
      } else {
         for(int i = 0; i < params.size(); ++i) {
            sbuf.append(", params[" + i + "]");
         }

         return sbuf.toString();
      }
   }

   public String element_generation_methods() throws CodeGenerationException {
      StringBuffer sbuf = new StringBuffer();
      Collection actions = this.instrux.getAllGeneratingActions();
      Iterator ia = actions.iterator();

      while(ia.hasNext()) {
         this.currentAction = (GAction)ia.next();
         sbuf.append(this.parse(this.getProductionRule("element_generate_method")));
      }

      return sbuf.toString();
   }

   public String generate_method_name() {
      Integer pathId = (Integer)this.pathTable.get(this.currentAction.getPath());
      return this.computeMethodName(pathId);
   }

   public String generate_method_comment() {
      return "ELEMENT PATH: " + this.currentAction.getPath();
   }

   private String computeMethodName(Integer pathId) {
      return "__gen_" + pathId;
   }

   public String generate_method_params() {
      StringBuffer sbuf = new StringBuffer();
      Collection params = this.currentAction.getParams();

      for(int i = 0; i < params.size(); ++i) {
         sbuf.append(", Object param" + i);
      }

      return sbuf.toString();
   }

   public String param_variables() {
      List params = this.currentAction.getParams();
      StringBuffer sbuf = new StringBuffer();

      for(int i = 0; i < params.size(); ++i) {
         GAction.Param param = (GAction.Param)params.get(i);
         String className = param.getClazz().getName();
         sbuf.append(className + " " + param.getName() + " = (" + className + ")param" + i + ";\n");
      }

      return sbuf.toString();
   }

   public String delayed_write() {
      return this.currentAction.delayedWrite() ? "true" : "false";
   }

   public String generate_method_body() throws CodeGenerationException {
      this.replaceFunctionRefs(this.currentAction);
      String code = this.currentAction.getJavaCode();
      return code == null ? EMPTY_STRING : code;
   }

   public String xml_element_to_generate() {
      WriteXmlFunctionRef wxfref = (WriteXmlFunctionRef)this.currentFunctionRef;
      return "\"" + wxfref.getElementName() + "\"";
   }

   public String call_xml_generate_method_name() throws CodeGenerationException {
      WriteXmlFunctionRef wxfref = (WriteXmlFunctionRef)this.currentFunctionRef;
      String eltName = wxfref.getElementName();
      String eltContext = wxfref.getElementContext();
      String path = this.resolvePath(eltName, eltContext);
      if (path == null) {
         throw new CodeGenerationException("Could not locate a generator action for element=\"" + eltName + "\"" + (eltContext != null ? ", element-context=\"" + eltContext + "\"" : ""));
      } else {
         Integer pathId = (Integer)this.pathTable.get(path);
         return this.computeMethodName(pathId);
      }
   }

   public String call_xml_generate_method_params() {
      WriteXmlFunctionRef wxfref = (WriteXmlFunctionRef)this.currentFunctionRef;
      String[] args = (String[])((String[])wxfref.getArgs().toArray(new String[0]));
      return args.length == 0 ? EMPTY_STRING : "," + StringUtils.join(args, ", ");
   }

   public String ctx_var() {
      return "newctx" + Integer.toString(this.currentCtxVar);
   }

   public String get_text_value() {
      WriteTextFunctionRef wtfref = (WriteTextFunctionRef)this.currentFunctionRef;
      String fromVar = wtfref.getFromVar();
      return fromVar != null ? fromVar : "\"" + wtfref.getText() + "\"";
   }

   private String resolvePath(String elementName, String elementContext) throws CodeGenerationException {
      Collection actions = this.instrux.getAllGeneratingActions();
      Iterator i = actions.iterator();
      String pathFrag = "." + (elementContext == null ? elementName : elementContext + "." + elementName) + ".";
      List matches = new ArrayList();

      while(i.hasNext()) {
         GAction a = (GAction)i.next();
         String p = a.getPath();
         if (p.endsWith(pathFrag)) {
            matches.add(p);
         }
      }

      if (matches.size() == 0) {
         return null;
      } else if (matches.size() > 1) {
         throw new CodeGenerationException("Found more than one generator action for element=\"" + elementName + "\"" + (elementContext != null ? ", element-context=\"" + elementContext + "\"" : ""));
      } else {
         return (String)matches.get(0);
      }
   }

   private void replaceFunctionRefs(GAction ac) throws CodeGenerationException {
      ListIterator li = ac.getCodeFragments().listIterator();
      this.currentCtxVar = 1;

      while(li.hasNext()) {
         Object frag = li.next();
         if (frag instanceof FunctionRef) {
            FunctionRef fref = (FunctionRef)frag;
            li.remove();
            li.add(this.functionRefToCode(fref));
            ++this.currentCtxVar;
         }
      }

   }

   private String functionRefToCode(FunctionRef fref) throws CodeGenerationException {
      this.currentFunctionRef = fref;
      if (fref instanceof WriteXmlFunctionRef) {
         return this.parse(this.getProductionRule("write_xml_function_ref"));
      } else if (fref instanceof WriteTextFunctionRef) {
         return this.parse(this.getProductionRule("write_text_function_ref"));
      } else if (fref instanceof SetAttrValFunctionRef) {
         return this.parse(this.getProductionRule("set_attribute_function_ref"));
      } else {
         throw new AssertionError("unknown function type " + fref.getClass().getName());
      }
   }

   public static class Output extends CodeGenerator.Output implements Cloneable {
      private GeneratingInstructions gi;

      public Output() {
         this.setTemplate("generator.j");
      }

      public void setGeneratingInstructions(GeneratingInstructions g) {
         this.gi = g;
         this.setOutputFile(GeneratorCompiler.getRootClassName(this.gi.getProcessorClass()) + ".java");
         this.setPackage(this.gi.getProcessorPackage());
      }

      public GeneratingInstructions getGeneratingInstructions() {
         return this.gi;
      }
   }
}
