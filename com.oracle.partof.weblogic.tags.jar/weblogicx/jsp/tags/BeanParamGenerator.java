package weblogicx.jsp.tags;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;
import weblogic.servlet.jsp.BeanUtils;
import weblogic.utils.Getopt2;
import weblogic.utils.ParsingException;
import weblogic.utils.UnsyncStringBuffer;
import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.compiler.IndentingWriter;

public class BeanParamGenerator extends CodeGenerator {
   static final String TEMPLATE_PATH = "/weblogicx/jsp/tags/beanparam.j";
   Hashtable rules;
   Hashtable classes = new Hashtable();
   UnsyncStringBuffer buf = new UnsyncStringBuffer();
   boolean failOnUnmatched = true;
   boolean tracing = true;
   private Stack stack = new Stack();
   private Vector knownClasses = new Vector();
   Getopt2 opts;
   String packageName;
   String classname;
   Class currentClass;
   private String[] typeNames = new String[]{"boolean", "byte", "short", "char", "int", "float", "long", "double", "Object"};
   private String currentTypeName = "<ERROR>";

   static void p(String s) {
      System.err.println("[BeanParamGen]: " + s);
   }

   public BeanParamGenerator(Getopt2 opts) {
      super(opts);
      this.opts = opts;
      opts.addOption("package", "packageName", "The java package into which this BeanParamInterpreter should be placed");
      opts.addOption("classname", "className", "The generated classname");
   }

   private boolean ignorePropertyType(String propTypeName) {
      return propTypeName.equals("java.lang.Class") || propTypeName.equals("java.util.Hashtable") || propTypeName.equals("java.util.Vector") || propTypeName.equals("java.util.Enumeration") || propTypeName.equals("javax.naming.Name") || propTypeName.startsWith("com.sun.java.util.collections.");
   }

   private String primitiveConvertCode(Class type, String paramName) {
      if (type == Boolean.TYPE) {
         return "new Boolean(" + paramName + ")";
      } else if (type == Byte.TYPE) {
         return "new Byte(" + paramName + ")";
      } else if (type == Character.TYPE) {
         return "new Character(" + paramName + ")";
      } else if (type == Short.TYPE) {
         return "new Short(" + paramName + ")";
      } else if (type == Integer.TYPE) {
         return "new Integer(" + paramName + ")";
      } else if (type == Long.TYPE) {
         return "new Long(" + paramName + ")";
      } else if (type == Float.TYPE) {
         return "new Float(" + paramName + ")";
      } else if (type == Double.TYPE) {
         return "new Double(" + paramName + ")";
      } else {
         throw new IllegalArgumentException("not a primitive type: " + type.getName());
      }
   }

   protected PrintWriter makeOutputStream(File f) throws IOException {
      return new PrintWriter(new IndentingWriter(new OutputStreamWriter(new FileOutputStream(f))));
   }

   void code(String s) {
      this.buf.append(s);
   }

   String getCode() {
      String ret = this.buf.toString();
      this.buf.setLength(0);
      return ret;
   }

   static String classname2methodname(String prefix, String cname) {
      cname = cname.replace('.', '_');
      return prefix + "_" + cname;
   }

   void pushClass(Class c) {
      if (!this.stack.contains(c) && !this.knownClasses.contains(c.getName())) {
         this.stack.push(c);
      }

   }

   void processAllClasses() throws Exception {
      while(!this.stack.isEmpty()) {
         Class c = (Class)this.stack.pop();
         this.processClass(c);
      }

   }

   public String checkDefaultConstructorBlock() throws Exception {
      this.code("static {\n");
      this.code("Object o = null;\n");
      Enumeration e = this.knownClasses.elements();

      while(e.hasMoreElements()) {
         String cname = (String)e.nextElement();
         this.code("o = new " + cname + "();\n");
      }

      this.code("}\n");
      return this.getCode();
   }

   private void addKnownClass(Class c) {
      if (!this.knownClasses.contains(c.getName())) {
         this.knownClasses.addElement(c.getName());
      }

   }

   private String processClass(Class c) throws Exception {
      this.currentClass = c;
      this.addKnownClass(c);
      p("processing class: " + c.getName());
      return this.generateGetter() + this.generateSetter();
   }

   private String generateGetter() throws Exception {
      return this.parse((String)this.rules.get("bean_get_method"));
   }

   private String generateSetter() throws Exception {
      return this.parse((String)this.rules.get("bean_set_method"));
   }

   public Enumeration outputs(Object[] inputs) throws IOException, ParsingException {
      try {
         String[] beanTypes = (String[])((String[])inputs);
         this.rules = new Hashtable();
         this.classname = this.opts.getOption("classname", "MyBeanInterpreter");
         this.packageName = this.opts.getOption("package", "weblogicx.jsp.tags");
         CodeGenerator.Output out = new CodeGenerator.Output(this.classname + ".java", "/weblogicx/jsp/tags/beanparam.j", this.packageName);
         this.processTemplate(this.rules, out, "/weblogicx/jsp/tags/beanparam.j");

         for(int i = 0; i < beanTypes.length; ++i) {
            p("processing input: " + beanTypes[i]);
            Class c = Class.forName(beanTypes[i]);
            this.pushClass(c);
         }

         Vector v = new Vector();
         v.addElement(out);
         return v.elements();
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new ParsingException(var6.toString());
      }
   }

   public String typeArrayAddMethods() throws Exception {
      UnsyncStringBuffer sb = new UnsyncStringBuffer();

      for(int i = 0; i < this.typeNames.length; ++i) {
         this.currentTypeName = this.typeNames[i];
         sb.append(this.parse((String)this.rules.get("type_array_add_method")));
      }

      this.currentTypeName = "<ERROR>";
      return sb.toString();
   }

   public String typeArrayAdd() {
      return this.currentTypeName + "ArrayAdd";
   }

   public String typeArrayShiftMethods() throws Exception {
      UnsyncStringBuffer sb = new UnsyncStringBuffer();

      for(int i = 0; i < this.typeNames.length; ++i) {
         this.currentTypeName = this.typeNames[i];
         sb.append(this.parse((String)this.rules.get("type_array_shift_method")));
      }

      this.currentTypeName = "<ERROR>";
      return sb.toString();
   }

   public String typeArrayShift() {
      return this.currentTypeName + "ArrayShift";
   }

   public String typeArrayDeleteMethods() throws Exception {
      UnsyncStringBuffer sb = new UnsyncStringBuffer();

      for(int i = 0; i < this.typeNames.length; ++i) {
         this.currentTypeName = this.typeNames[i];
         sb.append(this.parse((String)this.rules.get("type_array_delete_method")));
      }

      this.currentTypeName = "<ERROR>";
      return sb.toString();
   }

   public String typeArrayDelete() {
      return this.currentTypeName + "ArrayDelete";
   }

   public String type() {
      return this.currentTypeName;
   }

   public String publicGetLoop() throws Exception {
      this.code("if (false) {\nreturn null;\n");
      Enumeration e = this.knownClasses.elements();

      while(e.hasMoreElements()) {
         String className = (String)e.nextElement();
         this.code("} else if (val instanceof " + className + ") {\n");
         this.code(className + " o = (" + className + ")val;\n");
         this.code("return " + classname2methodname("get", className) + "(o, remainingName);\n");
      }

      this.code("} else {\n");
      this.code("throw new IllegalArgumentException(\"dont understand class: \" + val.getClass().getName() + \" stored under name \" + topName);\n");
      this.code("} // close else\n");
      return this.getCode();
   }

   public String publicSetLoop() throws Exception {
      this.code("if (false) {\n");
      Enumeration e = this.knownClasses.elements();

      while(e.hasMoreElements()) {
         String className = (String)e.nextElement();
         this.code("} else if (val instanceof " + className + ") {\n");
         this.code(className + " o = (" + className + ")val;\n");
         this.code("XParams xp = new XParams(name + \".\", rq);\n");
         this.code(classname2methodname("set", className) + "(o, xp);\n");
      }

      this.code("} else {\n");
      this.code("throw new IllegalArgumentException(\"dont understand class: \" + val.getClass().getName());\n");
      this.code("} // close else\n");
      return this.getCode();
   }

   public String currentBeanSetterLoop() throws Exception {
      Class c = this.currentClass;
      this.code("if (false) {\n");
      BeanInfo bi = Introspector.getBeanInfo(c);
      PropertyDescriptor[] pds = bi.getPropertyDescriptors();

      for(int i = 0; pds != null && i < pds.length; ++i) {
         PropertyDescriptor pd = pds[i];
         String propName = pd.getName().toLowerCase();
         Class type = pd.getPropertyType();
         if (type != null) {
            String propTypeName = type.getName();
            if (!this.ignorePropertyType(propTypeName)) {
               Method m;
               if (BeanUtils.isStringConvertible(type)) {
                  this.code("} else if (name.equals(\"" + propName + "\")) {\n");
                  m = pd.getWriteMethod();
                  if (m == null) {
                     this.code("throw new IllegalArgumentException(\"there is no write method for property '" + propName + "' of bean type '" + c.getName() + "'\");\n");
                  } else {
                     if (this.tracing) {
                        this.code("System.out.println(\"[set]: setting primitive prop \" + name +\" to val \" + val + \" on type \" + bean.getClass().getName());\n");
                     }

                     this.code("bean." + m.getName() + "(" + BeanUtils.convert(type, "val") + ");\n");
                  }
               } else if (!type.isArray()) {
                  this.code("} else if (name.startsWith(\"" + propName + ".\")) {\n");
                  m = pd.getReadMethod();
                  if (m == null) {
                     this.code("throw new IllegalArgumentException(\"there is no read method for property '" + propName + "' of bean type '" + c.getName() + "'\");\n");
                  } else {
                     this.code("// invoke sub-setter for this complex type...\n");
                     this.code("XParams subx = new XParams(\"" + propName + ".\", x);\n");
                     if (this.tracing) {
                        this.code("subx.setTracing(true);\n");
                     }

                     this.code(propTypeName + " subbean = bean." + m.getName() + "();\n");
                     if (this.tracing) {
                        this.code("System.out.println(\"[set]: calling complex (non-array) prop \" + name +\" to val \" + val + \" on type \" + bean.getClass().getName());\n");
                     }

                     this.code(classname2methodname("set", propTypeName) + "(subbean, subx);\n");
                     this.pushClass(type);
                  }
               } else {
                  this.code("} else if (name.startsWith(\"" + propName + "[\")) {\n");
                  if (!BeanUtils.isStringConvertible(type.getComponentType())) {
                     this.code("//complex array type\n");
                  } else {
                     this.code("// primitive array type\n");
                  }

                  this.code("if (!arraySyntax)\n");
                  this.code("throw new IllegalArgumentException(\"array syntax required for this attribute, not '\" + name + \"'\");\n");
                  m = pd.getReadMethod();
                  if (m == null) {
                     this.code("throw new IllegalArgumentException(\"there is no read method for property '" + propName + "' of bean type '" + c.getName() + "'\");\n");
                  } else {
                     if (!BeanUtils.isStringConvertible(type.getComponentType())) {
                        this.code("// invoke sub-setter for this complex type...\n");
                        this.code("XParams subx = new XParams(\"" + propName + ".\", x);\n");
                        if (this.tracing) {
                           this.code("subx.setTracing(true);\n");
                        }

                        if (this.tracing) {
                           this.code("System.out.println(\"[set]: setting complex (array) prop \" + name +\" to val \" + val + \" on type \" + bean.getClass().getName());\n");
                        }

                        Class simpleType = type.getComponentType();
                        String simpleTypeName = simpleType.getName();
                        this.code(simpleTypeName + " subbean = bean." + m.getName() + "()[arrayIndex];\n");
                        this.code(classname2methodname("set", simpleTypeName) + "(subbean, subx);\n");
                        this.pushClass(simpleType);
                     } else {
                        this.code(type.getComponentType().getName() + " newval = ");
                        this.code(BeanUtils.convert(type.getComponentType(), "val"));
                        this.code(";\n");
                        this.code(type.getComponentType().getName() + "[] proparray = bean." + m.getName() + "();\n");
                        this.code("proparray[arrayIndex] = newval;\n");
                        if (pd.getWriteMethod() != null) {
                           this.code("bean." + pd.getWriteMethod().getName() + "(proparray);\n");
                        }
                     }

                     this.code("} else if (name.startsWith(\"" + propName + ".\") || name.equals(\"" + propName + "\")) {\n");
                     this.code("throw new IllegalArgumentException(\"setting property '" + propName + "' on type '" + c.getName() + "' requires array syntax, illegal param name used was '\" + name + \"'\");\n");
                  }
               }
            }
         }
      }

      this.code("} else { // param doesn't match any know property on this bean type!\n");
      if (this.failOnUnmatched) {
         this.code("throw new IllegalArgumentException(\"param '\" + name + \" matches no known property on this bean type " + c.getName() + "\");\n");
      }

      this.code("} // close else...\n");
      return this.getCode();
   }

   public String currentBeanGetterLoop() throws Exception {
      Class c = this.currentClass;
      this.code("if (false) {\nreturn null;\n");
      BeanInfo bi = Introspector.getBeanInfo(c);
      PropertyDescriptor[] pds = bi.getPropertyDescriptors();

      for(int i = 0; pds != null && i < pds.length; ++i) {
         PropertyDescriptor pd = pds[i];
         String propName = pd.getName().toLowerCase();
         Class type = pd.getPropertyType();
         if (type != null) {
            String propTypeName = type.getName();
            if (!this.ignorePropertyType(propTypeName) && (!type.isArray() || !this.ignorePropertyType(type.getComponentType().getName()))) {
               Method m = pd.getReadMethod();
               this.code("} else if (componentName.equals(\"" + propName + "\")) {\n");
               if (m == null) {
                  this.code("throw new IllegalArgumentException(\"there is no read method for property '" + propName + "' of bean type '" + c.getName() + "'\");\n");
               } else if (type.isArray()) {
                  Class componentType = type.getComponentType();
                  String componentTypeName = componentType.getName();
                  if (componentType.isPrimitive()) {
                     this.code("// array of primitive type\n");
                     this.code("if (!atEnd) throw new IllegalArgumentException(\"bad id '\" + id + \"', cannot resolve remaining name '\" + remainingName + \"' since property \" + componentName + \" is a primitive type\");\n");
                     this.code(componentType + "[] array = bean." + m.getName() + "();\n");
                     this.code("if (arraySyntax) {\n");
                     this.code("if (array == null || array.length == 0) return null;\n");
                     this.code(componentTypeName + " ret = array[arrayIndex];\n");
                     this.code("return ");
                     this.code(this.primitiveConvertCode(componentType, "ret") + ";\n");
                     this.code("} else {\n");
                     this.code("// want the array itself\n");
                     this.code("return array;\n");
                     this.code("}\n");
                  } else {
                     this.code("// array of Object type\n");
                     this.code(componentTypeName + "[] beanarray = bean." + m.getName() + "();\n");
                     this.code("if (arraySyntax) {\n");
                     this.code(componentTypeName + " subbean = null;\n");
                     this.code("if (beanarray != null && beanarray.length > arrayIndex) subbean = beanarray[arrayIndex];\n");
                     this.code("if (atEnd) {\n");
                     this.code("return subbean;\n");
                     this.code("} else {\n");
                     this.code("// invoke sub-getter for this object\n");
                     this.code("if (subbean == null) throw new IllegalArgumentException(\"cannot access array specified by '\" + id + \"' because component '\" + componentName + \"' is null\");\n");
                     this.code("return ");
                     this.code(classname2methodname("get", componentTypeName));
                     this.code("(subbean, remainingName);\n");
                     this.code("}\n");
                     this.code("} else {\n");
                     this.code("// want the array itself...\n");
                     this.code("return beanarray;\n");
                     this.code("}\n");
                     this.pushClass(componentType);
                  }
               } else if (type.isPrimitive()) {
                  this.code("// non-array primitive type\n");
                  this.code("if (!atEnd) throw new IllegalArgumentException(\"bad id '\" + id + \"', cannot resolve remaining name '\" + remainingName + \"' since property \" + componentName + \" is a primitive type\");\n");
                  this.code(propTypeName + " ret = bean." + m.getName() + "();\n");
                  this.code("return ");
                  this.code(this.primitiveConvertCode(type, "ret") + ";\n");
               } else {
                  this.code("// non-array object type\n");
                  this.code(type.getName() + " subbean = bean." + m.getName() + "();\n");
                  this.code("if (atEnd) return subbean;\n");
                  this.code("if (subbean == null) throw new IllegalArgumentException(\"cannot get id='\" + id + \"' because component '\" + componentName + \"' is null\");\n");
                  this.code("return ");
                  this.code(classname2methodname("get", type.getName()));
                  this.code("(subbean, remainingName);\n");
                  this.pushClass(type);
               }
            }
         }
      }

      this.code("} else { // param doesn't match any know property on this bean type!\n");
      if (this.failOnUnmatched) {
         this.code("throw new IllegalArgumentException(\"id '\" + id + \"' (componentName='\" + componentName + \"') matches no known property on this bean type " + c.getName() + "\");\n");
      } else {
         this.code(" // no match, failOnUnmatched = false, so return 'null'...\n");
         this.code("return null;\n");
      }

      this.code("} // close else...\n");
      return this.getCode();
   }

   public String get_method_name() {
      return classname2methodname("get", this.currentClass.getName());
   }

   private void recursiveAllArrayProps(Class c, HashMap ret, String prefix) throws Exception {
      BeanInfo bi = Introspector.getBeanInfo(c);
      PropertyDescriptor[] pds = bi.getPropertyDescriptors();

      for(int i = 0; pds != null && i < pds.length; ++i) {
         PropertyDescriptor pd = pds[i];
         String propName = pd.getName().toLowerCase();
         Class type = pd.getPropertyType();
         if (type != null) {
            if (type.isArray()) {
               Class componentType = type.getComponentType();
               if (this.ignorePropertyType(componentType.getName()) || ret.put(prefix + "." + propName, componentType) != null) {
                  continue;
               }

               if (!componentType.isPrimitive() && !componentType.getName().equals("java.lang.String")) {
                  this.recursiveAllArrayProps(componentType, ret, prefix + "." + propName);
               }
            }

            if (!this.ignorePropertyType(type.getName()) && !type.isPrimitive() && !type.getName().equals("java.lang.String") && ret.get(prefix + "." + propName) == null) {
               this.recursiveAllArrayProps(type, ret, prefix + "." + propName);
            }
         }
      }

   }

   public String arrayComponentTypeLoop() throws Exception {
      Enumeration e = this.knownClasses.elements();
      this.code("if (false) { // begin arrayCompTypeLoop\n");

      while(e.hasMoreElements()) {
         String className = (String)e.nextElement();
         HashMap arrayProps = new HashMap();
         this.code("} else if (o instanceof " + className + ") {\n");
         this.recursiveAllArrayProps(Class.forName(className), arrayProps, "");
         Iterator i = arrayProps.keySet().iterator();
         this.code("if (false) {\n");

         while(i.hasNext()) {
            String propId = (String)i.next();
            Class c = (Class)arrayProps.get(propId);
            this.code("} else if (remainingName.equals(\"" + propId + "\")) {\n");
            this.code("return " + c.getName() + ".class;\n");
         }

         this.code("} else {\n");
         this.code("throw new IllegalArgumentException(\"no array prop matching '\" + id + \"' in type " + className + "\");\n");
         this.code("}\n");
      }

      this.code("} // match final elseif || if-false...\n");
      return this.getCode();
   }

   public String beanFillerCode() throws Exception {
      this.code("if (false) {\n");
      Enumeration e = this.knownClasses.elements();

      while(e.hasMoreElements()) {
         String className = (String)e.nextElement();
         this.code("} else if (newObjectType == " + className + ".class) {\n");
         this.code(className + " bean = (" + className + ")newObj;\n");
         this.code(classname2methodname("set", className));
         this.code("(bean, xp);\n");
      }

      this.code("} else {\n");
      this.code("throw new IllegalArgumentException(\"I have no setter for type \" + newObjectType.getName());\n");
      this.code("}\n");
      return this.getCode();
   }

   public String allArrayPropSetters() throws Exception {
      this.code("if (false) {\n");
      Enumeration e = this.knownClasses.elements();

      while(e.hasMoreElements()) {
         String className = (String)e.nextElement();
         Class c = Class.forName(className);
         this.code("} else if (o instanceof " + className + ") {\n");
         BeanInfo bi = Introspector.getBeanInfo(c);
         PropertyDescriptor[] pds = bi.getPropertyDescriptors();
         this.code("if (false) {\n");

         for(int i = 0; pds != null && i < pds.length; ++i) {
            PropertyDescriptor pd = pds[i];
            String propName = pd.getName().toLowerCase();
            Class type = pd.getPropertyType();
            if (type != null && type.isArray()) {
               Class componentType = type.getComponentType();
               if (!this.ignorePropertyType(componentType.getName())) {
                  this.code("} else if (paramName.equals(\"" + propName + "\")) {\n");
                  this.code(className + " x = (" + className + ")o;\n");
                  this.code(componentType.getName() + "[] newarray = (" + componentType.getName() + "[])array;\n");
                  Method m = pd.getWriteMethod();
                  if (m == null) {
                     this.code("throw new IllegalArgumentException(\"no write method for prop " + propName + " of type " + className + "\");\n");
                  } else {
                     this.code("x." + m.getName() + "(newarray);\n");
                     this.code("return;\n");
                  }
               }
            }
         }

         this.code("}\n");
      }

      this.code("} else {\n");
      this.code("throw new IllegalArgumentException(\"unknown class type: \" + o.getClass().getName());\n");
      this.code("}\n");
      return this.getCode();
   }

   public String set_method_name() {
      return classname2methodname("set", this.currentClass.getName());
   }

   public String currentBeanType() {
      return this.currentClass.getName();
   }

   public String packageStatement() {
      return "package " + this.packageName + ";";
   }

   public String generated_class_name() {
      return this.classname;
   }

   public String classBody() throws Exception {
      p("classBody called");
      UnsyncStringBuffer sb = new UnsyncStringBuffer();

      while(!this.stack.isEmpty()) {
         Class c = (Class)this.stack.pop();
         sb.append(this.processClass(c));
      }

      return sb.toString();
   }

   public static void main(String[] a) throws Exception {
      Getopt2 opts = new Getopt2();
      BeanParamGenerator bpg = new BeanParamGenerator(opts);
      opts.grok(a);
      String[] out = bpg.generate();
      p("generated output '" + out[0] + "'");
   }
}
