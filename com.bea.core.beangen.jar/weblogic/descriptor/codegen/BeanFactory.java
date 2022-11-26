package weblogic.descriptor.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class BeanFactory {
   private String factoryClassName;
   private String resourceFileName;
   Map implementationMap;
   Set roleInfoSet;
   private static final String ROLE_ALLOWED = "roleAllowed";
   private static final String ROLE_PERMIT_ALL = "rolePermitAll";
   private static final String ROLE_EXCLUDED = "roleExcluded";
   private static final String OWNER = "owner";
   private static final String TS_EXTENSION = ".tstamp";
   OutputStreamWriter outputStreamWriter;
   long timeStamp;

   public BeanFactory(String factoryClassName) {
      this.factoryClassName = factoryClassName;
      this.implementationMap = new TreeMap();
      this.roleInfoSet = new TreeSet();
   }

   public void putImplementation(String interfaceName, String implementationName) {
      this.implementationMap.put(interfaceName, implementationName);
   }

   public void putRoleInfo(AnnotatableClass aClass) {
      Annotation annot = this.getAnnotation(aClass);
      if (annot != null) {
         this.roleInfoSet.add(aClass.getQualifiedName());
      }

   }

   public void generate(File classFile, File tsFile) throws IOException {
      boolean generateExtTsFile = tsFile != null;
      if (generateExtTsFile) {
         this.resourceFileName = tsFile.getName();
      }

      if (!classFile.getParentFile().exists()) {
         classFile.getParentFile().mkdirs();
      }

      this.outputStreamWriter = new OutputStreamWriter(new FileOutputStream(classFile));
      this.generateClass(generateExtTsFile);
      this.outputStreamWriter.flush();
      this.outputStreamWriter.close();
      if (tsFile != null) {
         if (!tsFile.getParentFile().exists()) {
            tsFile.getParentFile().mkdirs();
         }

         this.outputStreamWriter = new OutputStreamWriter(new FileOutputStream(tsFile));
         this.writeln("" + (new Date(this.timeStamp)).getTime());
         this.outputStreamWriter.flush();
         this.outputStreamWriter.close();
      }

   }

   public void setTimeStamp(long timeStamp) {
      this.timeStamp = timeStamp;
   }

   private Annotation getAnnotation(AnnotatableClass aClass) {
      Annotation annot = aClass.getAnnotation("roleAllowed");
      if (annot != null) {
         return annot;
      } else {
         annot = aClass.getAnnotation("rolePermitAll");
         if (annot != null) {
            return annot;
         } else {
            annot = aClass.getAnnotation("roleExcluded");
            if (annot != null) {
               return annot;
            } else {
               annot = aClass.getAnnotation("owner");
               if (annot != null) {
                  return annot;
               } else {
                  int i;
                  if (aClass.hasMethods()) {
                     AnnotatableMethod[] mthds = aClass.getMethods();

                     for(i = 0; i < mthds.length; ++i) {
                        AnnotatableMethod mthd = mthds[i];

                        try {
                           annot = mthd.getAnnotation("roleExcluded");
                           if (annot == null) {
                              annot = mthd.getAnnotation("rolePermitAll");
                           }

                           if (annot == null) {
                              annot = mthd.getAnnotation("roleAllowed");
                           }

                           if (annot == null) {
                              annot = mthd.getAnnotation("owner");
                           }

                           if (annot != null) {
                              return annot;
                           }
                        } catch (Exception var7) {
                           System.out.println("Unable to get role annotation info, skipping method " + mthd);
                           var7.printStackTrace();
                        }
                     }
                  }

                  AnnotatableClass[] intfs = aClass.getInterfaces();
                  if (intfs != null && intfs.length != 0) {
                     for(i = 0; i < intfs.length; ++i) {
                        annot = this.getAnnotation(intfs[i]);
                        if (annot != null) {
                           return annot;
                        }
                     }
                  }

                  return null;
               }
            }
         }
      }
   }

   private void writeln(String string) throws IOException {
      this.outputStreamWriter.write(string);
      this.outputStreamWriter.write(10);
   }

   private void writeln(int count) throws IOException {
      while(count-- != 0) {
         this.outputStreamWriter.write(10);
      }

   }

   private void generateClass(boolean generateExtTsFile) throws IOException {
      int indexOfClass = this.factoryClassName.lastIndexOf(46);
      String className;
      if (indexOfClass != -1) {
         className = this.factoryClassName.substring(indexOfClass + 1);
         String packageName = this.factoryClassName.substring(0, indexOfClass);
         this.writeln("package " + packageName + ";");
         this.writeln(2);
      } else {
         className = this.factoryClassName;
      }

      if (generateExtTsFile) {
         this.writeln("import  java.io.*;");
      }

      this.writeln("import  java.util.ArrayList;");
      this.writeln("import  java.util.Map;");
      this.writeln("import  java.util.HashMap;");
      this.writeln("import  java.util.Set;");
      this.writeln("import  weblogic.utils.codegen.ImplementationFactory;");
      this.writeln("import  weblogic.utils.codegen.RoleInfoImplementationFactory;");
      this.writeln(4);
      this.writeln("/**");
      this.writeln(" * This is a generated class that provides a mapping from ");
      this.writeln(" * interface classes to implementation classes");
      this.writeln(" */");
      this.writeln("public class " + className + " implements RoleInfoImplementationFactory {");
      this.generateInitializer(className);
      this.writeln(2);
      this.generateGetInstance();
      this.writeln(2);
      this.generateFactoryMethod();
      this.writeln(2);
      this.generateTypesMethod();
      this.writeln(2);
      this.generateRoleInfosMethod(generateExtTsFile);
      this.writeln(2);
      this.writeln("}");
   }

   private void generateInitializer(String className) throws IOException {
      Set entrySet = this.implementationMap.entrySet();
      this.writeln("  private static final Map interfaceMap;");
      this.writeln("  private static final ArrayList roleInfoList;");
      this.writeln("  private static final " + className + " SINGLETON;");
      this.writeln("  static {");
      this.writeln("    interfaceMap = new HashMap(" + entrySet.size() + ");");
      Iterator iterator = entrySet.iterator();

      while(iterator.hasNext()) {
         Map.Entry entry = (Map.Entry)iterator.next();
         String interfaceName = (String)entry.getKey();
         String implementationName = (String)entry.getValue();
         this.writeln("    interfaceMap.put(\"" + interfaceName + "\",\"" + implementationName + "\");");
      }

      this.writeln("    roleInfoList = new ArrayList(" + this.roleInfoSet.size() + ");");
      iterator = this.roleInfoSet.iterator();

      while(iterator.hasNext()) {
         String roleInfoInterface = (String)iterator.next();
         this.writeln("    roleInfoList.add(\"" + roleInfoInterface + "\");");
      }

      this.writeln("    SINGLETON = new " + className + "();");
      this.writeln("  }");
   }

   private void generateGetInstance() throws IOException {
      this.writeln("  public static final ImplementationFactory getInstance() {");
      this.writeln("    return SINGLETON; ");
      this.writeln("  }");
   }

   private void generateFactoryMethod() throws IOException {
      this.writeln("  public String getImplementationClassName( String interfaceName ) {");
      this.writeln("    return (String)interfaceMap.get(interfaceName);");
      this.writeln("  }");
   }

   private void generateTypesMethod() throws IOException {
      this.writeln("  public String[] getInterfaces() {");
      this.writeln("    Set keySet = interfaceMap.keySet();");
      this.writeln("    return (String[])keySet.toArray(new String[keySet.size()]);");
      this.writeln("  }");
   }

   private void generateRoleInfosMethod(boolean generateExtTsFile) throws IOException {
      this.writeln("  public String[] getInterfacesWithRoleInfo() {");
      this.writeln("    return (String[])roleInfoList.toArray(new String[roleInfoList.size()]);");
      this.writeln("  }");
      this.writeln("");
      this.writeln("  public String getRoleInfoImplementationFactoryTimestamp() {");
      if (!generateExtTsFile) {
         this.writeln("    return \"" + (new Date(this.timeStamp)).getTime() + "\";");
      } else {
         this.writeln("    try {");
         this.writeln("      InputStream is = this.getClass().getResourceAsStream(\"" + this.resourceFileName + "\");");
         this.writeln("      return new BufferedReader(new InputStreamReader(is)).readLine();");
         this.writeln("    } catch (IOException ioe) { throw new RuntimeException(ioe); }");
      }

      this.writeln("  }");
   }
}
