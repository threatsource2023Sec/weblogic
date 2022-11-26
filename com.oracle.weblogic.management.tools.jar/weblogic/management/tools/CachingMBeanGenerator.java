package weblogic.management.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.management.NotificationBroadcaster;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.utils.AssertionError;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.BadOutputException;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class CachingMBeanGenerator extends CodeGenerator {
   public static final String CLASS_SUFFIX = "_Stub";
   private static final String VERBOSE = "verbose";
   private static final String PACKAGE = "package";
   private static final Set excludeClassList = new HashSet(Arrays.asList((Object[])(new String[]{"weblogic.management.configuration.AdminMBean"})));
   private static final Set excludeOperationsList = new HashSet(Arrays.asList((Object[])(new String[]{"registerConfigMBean", "unRegisterConfigMBean"})));
   private boolean verbose;
   private Output currentOutput;
   private Method method;
   private MBeanReflector.Attribute attribute;
   private MBeanReflector reflector;
   private Set attributeSet;
   private String attributeName;
   private String attributeFieldName;

   public CachingMBeanGenerator(Getopt2 opts) {
      super(opts);
      opts.addFlag("verbose", "Verbose output.");
      opts.setUsageArgs("[directory|file]");
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
            if (!excludeClassList.contains(className)) {
               Class clazz = AttributeInfo.Helper.findClass(className);
               if (!Throwable.class.isAssignableFrom(clazz)) {
                  int lastDot = className.lastIndexOf(46);
                  String packageName;
                  if (lastDot != -1) {
                     packageName = className.substring(0, lastDot);
                  } else {
                     packageName = "";
                  }

                  Output output = new Output(clazz, packageName);
                  String ofn = output.getOutputFile().replace('/', File.separatorChar);
                  File outputFile = this.targetFile(ofn, output.getPackage());
                  if (file.lastModified() > outputFile.lastModified()) {
                     this.verbose(file + " has changed, regenerating.");
                     outputs.put(output, output);
                  }
               }
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
      String pkg = this.currentOutput.getPackage();
      return pkg == null ? "" : "package " + pkg + ";";
   }

   public String genClassName() {
      return this.currentOutput.getClassName();
   }

   public String genInterfaceName() {
      return this.currentOutput.getInterface().getName();
   }

   public String genOptionalSVUID() {
      try {
         return "private static final long serialVersionUID = " + this.currentOutput.getInterface().getDeclaredField("CACHING_STUB_SVUID").getLong((Object)null) + "L;";
      } catch (IllegalAccessException var2) {
         throw new AssertionError(var2);
      } catch (NoSuchFieldException var3) {
         return "";
      }
   }

   public String genAccessors() throws CodeGenerationException {
      StringBuffer buf = new StringBuffer();
      MBeanReflector.Attribute[] attributes = this.reflector.getAttributes();

      for(int i = 0; i < attributes.length; ++i) {
         this.attribute = attributes[i];
         buf.append(this.parse(this.getProductionRule("attributeDeclaration")));
         this.method = this.reflector.getAttributeGetMethod(this.attribute);
         if (this.method != null) {
            buf.append(this.parse(this.getProductionRule("getter")));
         }

         this.method = this.reflector.getAttributeSetMethod(this.attribute);
         if (this.method != null) {
            buf.append(this.parse(this.getProductionRule("setter")));
         }
      }

      return buf.toString();
   }

   public String genOperations() throws CodeGenerationException {
      StringBuffer buf = new StringBuffer();
      MBeanReflector.Operation[] operations = this.reflector.getOperations();

      for(int i = 0; i < operations.length; ++i) {
         this.method = operations[i].getMethod();
         if (this.method.getDeclaringClass() != NotificationBroadcaster.class && !excludeOperationsList.contains(this.method.getName())) {
            buf.append(this.parse(this.getProductionRule("operation")));
         }
      }

      return buf.toString();
   }

   public String genOperationBody() throws CodeGenerationException {
      return this.method.getReturnType() == Void.TYPE ? this.parse(this.getProductionRule("voidOperationBody")) : this.parse(this.getProductionRule("operationBody"));
   }

   public String genAttributeCacheInvalidatorFragment() throws CodeGenerationException {
      StringBuffer buf = new StringBuffer();
      MBeanReflector.Attribute[] attributes = this.reflector.getAttributes();

      for(int i = 0; i < attributes.length; ++i) {
         this.attribute = attributes[i];
         buf.append(this.parse(this.getProductionRule("attributeCacheInvalidator")));
      }

      return buf.toString();
   }

   public String genAttributeName() {
      return this.attribute.getName();
   }

   public String genAttributeFieldName() {
      return this.attribute.getFieldName();
   }

   public String genAttributeTempName() {
      return this.attribute.getFieldName() + "Temp";
   }

   public String genAttributeType() {
      return this.prettyPrintType(this.attribute.getType());
   }

   public String genAttributeIsCached() {
      return this.attribute.getFieldName() + "IsCached";
   }

   public String genMethodName() {
      return this.method.getName();
   }

   public String genParameterList() {
      StringBuffer buf = new StringBuffer();
      Class[] types = this.method.getParameterTypes();
      if (types.length > 0) {
         for(int i = 0; i < types.length; ++i) {
            if (i > 0) {
               buf.append(", ");
            }

            buf.append(this.prettyPrintType(types[i]) + " arg" + i);
         }
      }

      return buf.toString();
   }

   public String genTypeCastedExpressionforReturnedObject() {
      StringBuffer buf = new StringBuffer();
      Class returnedType = this.method.getReturnType();
      if (returnedType.isPrimitive()) {
         if (returnedType == Integer.TYPE) {
            buf.append("((Integer)returnedObject).intValue()");
         } else if (returnedType == Long.TYPE) {
            buf.append("((Long)returnedObject).longValue()");
         } else if (returnedType == Boolean.TYPE) {
            buf.append("((Boolean)returnedObject).booleanValue()");
         } else if (returnedType == Double.TYPE) {
            buf.append("((Double)returnedObject).doubleValue()");
         }
      } else if (returnedType.isArray()) {
         buf.append("(" + returnedType.getComponentType().getName() + "[]) returnedObject");
      } else {
         buf.append("(" + returnedType.getName() + ") returnedObject");
      }

      return buf.toString();
   }

   public String resetAttributeField() {
      if (this.attribute.getType() == Integer.TYPE) {
         return "0";
      } else if (this.attribute.getType() == Long.TYPE) {
         return "0";
      } else if (this.attribute.getType() == Double.TYPE) {
         return "0";
      } else {
         return this.attribute.getType() == Boolean.TYPE ? "false" : "null";
      }
   }

   public String genParameters() {
      StringBuffer buf = new StringBuffer();
      Class[] types = this.method.getParameterTypes();
      if (types.length > 0) {
         for(int i = 0; i < types.length; ++i) {
            if (i > 0) {
               buf.append(", ");
            }

            if (types[i].isPrimitive()) {
               buf.append("new " + AttributeInfo.Helper.wrapClass(types[i]).getName() + "(");
            }

            buf.append("arg" + i);
            if (types[i].isPrimitive()) {
               buf.append(")");
            }
         }
      }

      return buf.toString();
   }

   public String genReturnType() {
      return this.prettyPrintType(this.method.getReturnType());
   }

   public String genSignature() {
      StringBuffer buf = new StringBuffer();
      Class[] types = this.method.getParameterTypes();
      if (types.length > 0) {
         for(int i = 0; i < types.length; ++i) {
            if (i > 0) {
               buf.append(", ");
            }

            buf.append("\"" + types[i].getName() + "\"");
         }
      }

      return buf.toString();
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

   public String genThrowException() {
      StringBuffer buf = new StringBuffer();
      Class[] types = this.method.getExceptionTypes();
      if (types.length != 0) {
         int i;
         String exceptionClassName;
         for(i = 0; i < types.length; ++i) {
            exceptionClassName = types[i].getName();
            if (this.method.getDeclaringClass() == ServerRuntimeMBean.class && (this.method.getName().equals("shutdown") || this.method.getName().equals("forceShutdown"))) {
               buf.append("\n\tif (thr instanceof weblogic.rmi.extensions.RemoteRuntimeException) {return;}");
            }

            buf.append("\n\tif (thr instanceof RuntimeException) throw (RuntimeException)thr; ");
            buf.append("\n\tif (thr instanceof " + exceptionClassName + ") throw (" + exceptionClassName + ")thr;");
         }

         buf.append("\n\tif (thr instanceof MBeanException) {");
         buf.append("\n\t\tThrowable target = ((MBeanException)thr).getTargetException();");

         for(i = 0; i < types.length; ++i) {
            exceptionClassName = types[i].getName();
            buf.append("\n\t\tif (target instanceof " + exceptionClassName + ") throw (" + exceptionClassName + ")target;");
         }

         buf.append("\n\t}");
      }

      buf.append("\n\tthrow new ManagementRuntimeException(thr);");
      return buf.toString();
   }

   protected void extractOptionValues(Getopt2 opts) {
      this.verbose = opts.hasOption("verbose");
   }

   protected void prepare(CodeGenerator.Output output) throws BadOutputException {
      this.currentOutput = (Output)output;
      this.reflector = new MBeanReflector(this.currentOutput.getInterface());
   }

   private void addSourceFilesFromDir(File dir, List sourceFiles) throws Exception {
      this.verbose("Looking in " + dir.getCanonicalPath());
      String[] files = dir.list();

      for(int i = 0; i < files.length; ++i) {
         if (files[i].toLowerCase().endsWith("mbean.java")) {
            File file = new File(dir, files[i]);
            sourceFiles.add(file);
         }
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

      public Output(Class ifc, String packageName) {
         super(getFileName(ifc), "CachingMBean.j", packageName);
         this.ifc = ifc;
      }

      public static String getClassName(Class ifc) {
         String interfaceName = ifc.getName();
         int lastDot = interfaceName.lastIndexOf(46);
         return lastDot > -1 ? interfaceName.substring(lastDot + 1) + "_Stub" : interfaceName + "_Stub";
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
   }
}
