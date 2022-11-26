package weblogic.management.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import weblogic.management.WebLogicMBean;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.BadOutputException;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class XMLElementMBeanImplGenerator extends CodeGenerator {
   private static final String VERBOSE = "verbose";
   private static final String PACKAGE = "package";
   private boolean verbose;
   private Output m_currentOutput;
   private Method method;
   private MBeanReflector.Attribute attribute;
   private MBeanReflector m_reflector;
   private Set attributeSet;
   private String attributeName;
   private String attributeFieldName;
   private TagParser m_currentTagParser;

   public XMLElementMBeanImplGenerator(Getopt2 opts) {
      super(opts);
      opts.addFlag("verbose", "Verbose output.");
      opts.setUsageArgs("[directory|file]");
   }

   public static String genDefaultValue(TagParser currentTagParser, Method getter, Output output) throws CodeGenerationException {
      TaggedMethod[] methods = currentTagParser.getMethodsWithTag("@default");
      Class type = getter.getReturnType();

      for(int i = 0; i < methods.length; ++i) {
         if (-1 != methods[i].getMethodSignature().indexOf(getter.getName())) {
            String tagValue = methods[i].getTagValue("@default");
            if (type.isAssignableFrom(String.class)) {
               return " = " + tagValue;
            }

            if (type.isAssignableFrom(Boolean.TYPE)) {
               return " = " + Boolean.valueOf(tagValue);
            }

            if (type.isAssignableFrom(Integer.TYPE)) {
               try {
                  return " = " + Integer.parseInt(tagValue);
               } catch (NumberFormatException var8) {
                  throw new CodeGenerationException("Error parsing int for " + getter + " of the " + output.getClassName() + " interface.", var8);
               }
            }

            throw new CodeGenerationException("Can't set default value for " + getter + ". Its type isn't supported");
         }
      }

      return "";
   }

   public String getterMethodName() {
      return "get" + this.attribute.getName();
   }

   public Enumeration outputs(Object[] inputs) throws Exception {
      try {
         Hashtable outputs = new Hashtable();
         List sourceFiles = new ArrayList();

         File file;
         for(int i = 0; i < inputs.length; ++i) {
            file = new File((String)inputs[i]);
            if (!file.exists()) {
               throw new FileNotFoundException(file.getPath());
            }

            if (file.isDirectory()) {
               this.addSourceFilesFromDir(file, sourceFiles);
            } else {
               sourceFiles.add(file);
            }
         }

         Iterator it = sourceFiles.iterator();

         while(it.hasNext()) {
            file = (File)it.next();
            String className = file.getPath().replace(File.separatorChar, '.');
            className = className.substring(0, className.length() - 5);
            Class clazz = AttributeInfo.Helper.findClass(className);
            if (!Throwable.class.isAssignableFrom(clazz)) {
               int lastDot = className.lastIndexOf(46);
               String packageName;
               if (lastDot != -1) {
                  packageName = className.substring(0, lastDot);
               } else {
                  packageName = "";
               }

               Output output = new Output(clazz, packageName, file.getAbsolutePath());
               String ofn = output.getOutputFile().replace('/', File.separatorChar);
               this.targetFile(ofn, output.getPackage());
               this.verbose(file + " has changed, regenerating.");
               outputs.put(output, output);
            }
         }

         return outputs.elements();
      } catch (Throwable var13) {
         var13.printStackTrace();
         return null;
      }
   }

   public String genAuthor() {
      return "@author";
   }

   public String genPackageDeclaration() {
      String pkg = this.m_currentOutput.getPackage();
      return pkg == null ? "" : "package " + pkg + ";";
   }

   public String genClassName() {
      return this.m_currentOutput.getClassName();
   }

   public String genInterfaceName() {
      return this.m_currentOutput.getInterface().getName();
   }

   public String genAttributes() throws CodeGenerationException {
      StringBuffer buf = new StringBuffer();
      HashSet attributes = this.getAttributes();
      Iterator it = attributes.iterator();

      while(it.hasNext()) {
         this.attribute = (MBeanReflector.Attribute)it.next();
         if (this.isAttributeArray()) {
            buf.append(this.parse(this.getProductionRule("attributeFieldArrayDeclaration")));
         } else {
            buf.append(this.parse(this.getProductionRule("attributeFieldDeclaration")));
         }
      }

      return buf.toString();
   }

   public String genAccessors() throws CodeGenerationException {
      StringBuffer buf = new StringBuffer();
      HashSet attributes = this.getAttributes();
      Iterator it = attributes.iterator();

      while(it.hasNext()) {
         this.attribute = (MBeanReflector.Attribute)it.next();
         this.method = this.m_reflector.getAttributeGetMethod(this.attribute);
         if (this.method != null) {
            if (this.isAttributeArray()) {
               buf.append(this.parse(this.getProductionRule("arrayGetter")));
            } else if (this.isAttributeBoolean()) {
               buf.append(this.parse(this.getProductionRule("isGetter")));
            } else {
               buf.append(this.parse(this.getProductionRule("getter")));
            }
         }

         this.method = this.m_reflector.getAttributeSetMethod(this.attribute);
         if (this.method != null) {
            if (this.isAttributeArray()) {
               buf.append(this.parse(this.getProductionRule("arraySetter")));
            } else {
               buf.append(this.parse(this.getProductionRule("setter")));
            }
         }

         if (this.isAttributeArray()) {
            try {
               this.method = this.m_reflector.getAttributeAddMethod(this.attribute);
            } catch (Exception var6) {
               throw new CodeGenerationException("Error getting add method", var6);
            }

            if (this.method != null) {
               buf.append(this.parse(this.getProductionRule("arrayAdder")));
            }

            try {
               this.method = this.m_reflector.getAttributeRemoveMethod(this.attribute);
            } catch (Exception var5) {
               throw new CodeGenerationException("Error getting remove method", var5);
            }

            if (this.method != null) {
               buf.append(this.parse(this.getProductionRule("arrayRemover")));
            }
         }
      }

      return buf.toString();
   }

   public String genChildrenRegistration() throws CodeGenerationException {
      StringBuffer buf = new StringBuffer();
      HashSet attributes = this.getAttributes();
      Iterator it = attributes.iterator();

      while(it.hasNext()) {
         this.attribute = (MBeanReflector.Attribute)it.next();
         if (this.isAttributeMBean()) {
            if (this.isAttributeArray()) {
               buf.append(this.parse(this.getProductionRule("arrayRegister")));
            } else {
               buf.append(this.parse(this.getProductionRule("childRegister")));
            }
         }
      }

      return buf.toString();
   }

   public String emptyStringCheck() {
      return this.isAttributeString() ? "if (value != null && value.trim().length() == 0) value = null;\n" : "";
   }

   public String attributeIsSetExpression() {
      if (!this.isAttributePrimitive()) {
         return "(value != null)";
      } else {
         return this.isAttributeBoolean() ? "true" : "(value != " + this.unsetNumberLiteral() + ")";
      }
   }

   public String genOperations() throws CodeGenerationException {
      StringBuffer buf = new StringBuffer();
      MBeanReflector.Operation[] operations = this.m_reflector.getOperations();

      for(int i = 0; i < operations.length; ++i) {
         this.method = operations[i].getMethod();
         buf.append(this.parse(this.getProductionRule("operation")));
      }

      return buf.toString();
   }

   public String genAttributeName() {
      return this.attribute.getName();
   }

   public String genAttributeFieldName() {
      return this.attribute.getFieldName();
   }

   public String genAttributeFieldSetTesterName() {
      return "isSet_" + this.genAttributeFieldName();
   }

   public String genDefaultValue() throws CodeGenerationException {
      Method getter = this.m_reflector.getAttributeGetMethod(this.attribute);
      return genDefaultValue(this.m_currentTagParser, getter, this.m_currentOutput);
   }

   public String genAttributeType() {
      return this.prettyPrintType(this.attribute.getType());
   }

   public String genAttributeTypeMinusArrayBrackets() {
      return this.attribute.getType().getComponentType().getName();
   }

   public String genMethodName() {
      return this.method.getName();
   }

   public String genReturnType() {
      return this.prettyPrintType(this.method.getReturnType());
   }

   public String genThrowsClause() {
      StringBuffer buf = new StringBuffer();
      Class[] types = this.method.getExceptionTypes();
      if (types.length != 0) {
         buf.append("throws ");

         for(int i = 0; i < types.length; ++i) {
            if (i > 0) {
               buf.append(", ");
            }

            buf.append(types[i].getName());
         }
      }

      return buf.toString();
   }

   public String toXML() {
      String result = null;

      try {
         result = ToXML.toXML(this.m_currentTagParser);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return result;
   }

   protected void extractOptionValues(Getopt2 opts) {
      this.verbose = opts.hasOption("verbose");
   }

   protected void prepare(CodeGenerator.Output output) throws BadOutputException {
      this.m_currentOutput = (Output)output;
      Output myOutput = (Output)output;
      this.m_currentTagParser = new TagParser(myOutput.getAbsolutePath());

      try {
         this.m_currentTagParser.parse();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      this.m_reflector = new MBeanReflector(this.m_currentOutput.getInterface());
   }

   private boolean isAttributeString() {
      return this.attribute.getType().getName().equals("java.lang.String");
   }

   private boolean isAttributePrimitive() {
      if (this.isAttributeArray()) {
         return false;
      } else {
         String t = this.attribute.getType().getName();
         return "boolean".equals(t) || "byte".equals(t) || "short".equals(t) || "char".equals(t) || "int".equals(t) || "float".equals(t) || "long".equals(t) || "double".equals(t);
      }
   }

   private boolean isAttributeMBean() {
      return this.isAttributeArray() ? this.genAttributeTypeMinusArrayBrackets().endsWith("MBean") : this.attribute.getType().getName().endsWith("MBean");
   }

   private boolean isAttributeArray() {
      return this.attribute.getType().isArray();
   }

   private boolean isAttributeBoolean() {
      return this.attribute.getType().getName().equals("boolean");
   }

   private HashSet getAttributes() {
      HashSet attributes = new HashSet(Arrays.asList((Object[])this.m_reflector.getAttributes()));
      MBeanReflector ref = new MBeanReflector(WebLogicMBean.class);
      Collection filter = Arrays.asList((Object[])ref.getAttributes());
      attributes.removeAll(filter);
      return attributes;
   }

   private void addSourceFilesFromDir(File dir, List sourceFiles) throws Exception {
      this.verbose("Looking in " + dir.getCanonicalPath());
      String[] files = dir.list();

      for(int i = 0; i < files.length; ++i) {
         if (files[i].toLowerCase(Locale.US).endsWith("mbean.java")) {
            File file = new File(dir, files[i]);
            sourceFiles.add(file);
         }
      }

   }

   private String unsetNumberLiteral() {
      String t = this.attribute.getType().getName();
      if (!"byte".equals(t) && !"short".equals(t) && !"int".equals(t)) {
         if ("char".equals(t)) {
            return "0xffff";
         } else if ("float".equals(t)) {
            return "-1.0";
         } else if ("long".equals(t)) {
            return "-1L";
         } else {
            return "double".equals(t) ? "-1.0D" : "__FIXME__THIS__IS__BROKEN__!!!";
         }
      } else {
         return "-1";
      }
   }

   private String prettyPrintType(Class type) {
      return type.isArray() ? type.getComponentType().getName() + "[]" : type.getName();
   }

   private void verbose(String msg) {
      if (this.verbose) {
         this.info(msg);
      }

   }

   private void info(String msg) {
      System.out.println("<MBean Compiler>" + msg);
   }

   private static class Output extends CodeGenerator.Output {
      private Class ifc;
      private String clazz;
      private String absolutePath;

      public Output(Class ifc, String packageName, String absolutePath) {
         super(getFileName(ifc), "XMLElementMBeanImpl.j", packageName);
         this.ifc = ifc;
         this.absolutePath = absolutePath;
      }

      public static String getClassName(Class ifc) {
         String interfaceName = ifc.getName();
         int lastDot = interfaceName.lastIndexOf(46);
         return lastDot > -1 ? interfaceName.substring(lastDot + 1) + "Impl" : interfaceName + "Impl";
      }

      private static String getFileName(Class ifc) {
         return getClassName(ifc) + ".java";
      }

      public String getClassName() {
         return getClassName(this.ifc);
      }

      public Class getInterface() {
         return this.ifc;
      }

      public String getAbsolutePath() {
         return this.absolutePath;
      }
   }
}
