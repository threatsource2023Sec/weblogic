package weblogic.descriptor.descriptorgen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;
import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.JamServiceFactory;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import weblogic.descriptor.codegen.AnnotatableAttribute;
import weblogic.descriptor.codegen.AnnotatableClass;
import weblogic.descriptor.codegen.AnnotatableMethod;
import weblogic.descriptor.codegen.Annotation;
import weblogic.descriptor.codegen.CodeGenerator;
import weblogic.descriptor.codegen.Production;
import weblogic.descriptor.codegen.Utils;

public class XSD2Java extends CodeGenerator {
   public static final String DEFAULT_TEMPLATE = "weblogic/descriptor/descriptorgen/XSD2Java.template";
   protected JClass[] originals;
   protected static HashMap mBeans = new HashMap();
   static String[][] classNameFilter = new String[][]{{"com.bea.xml.XmlAnyURI", "java.lang.String"}, {"com.bea.xml.XmlBoolean", "boolean"}, {"com.bea.xml.XmlInt", "int"}, {"com.bea.xml.XmlLong", "long"}, {"com.bea.xml.XmlInteger", "java.math.BigInteger"}, {"com.bea.xml.XmlDecimal", "java.math.BigDecimal"}, {"com.bea.xml.XmlFloat", "float"}, {"com.bea.xml.XmlDouble", "double"}, {"com.bea.xml.XmlNMTOKEN", "java.lang.String"}, {"com.bea.xml.XmlString", "java.lang.String"}, {"com.bea.xml.XmlQName", "javax.xml.namespace.QName"}, {"com.bea.xml.XmlToken", "java.lang.String"}, {"com.bea.xml.StringEnumAbstractBase", "java.lang.String"}};
   static JClass[][] classFilter;
   static String[][] targetNameFilter = new String[][]{{"Activationspec", "ActivationSpec"}, {"activationspec", "activationSpec"}, {"Adminobject", "AdminObject"}, {"adminobject", "adminObject"}, {"Resourceadapter", "ResourceAdapter"}, {"resourceadapter", "resourceAdapter"}, {"Messageadapter", "MessageAdapter"}, {"messageadapter", "messageAdapter"}, {"Messagelistener", "MessageListener"}, {"messagelistener", "messageListener"}, {"Taglib", "TagLib"}, {"Rtexprvalue", "RtExprValue"}, {"Managedconnectionfactory", "ManagedConnectionFactory"}, {"managedconnectionfactory", "managedConnectionFactory"}, {"Connectionfactory", "ConnectionFactory"}, {"connectionfactory", "connectionFactory"}};
   static JamClassLoader systemCL = JamServiceFactory.getInstance().createSystemJamClassLoader();

   public XSD2Java(XSD2JavaOptions options) {
      super(options);
      this.init();
   }

   void init() {
      try {
         classFilter = new JClass[classNameFilter.length][3];

         for(int i = 0; i < classNameFilter.length; ++i) {
            classFilter[i][0] = systemCL.loadClass(classNameFilter[i][0]);
            classFilter[i][1] = systemCL.loadClass(classNameFilter[i][1]);
            if (classFilter[i][1].isPrimitiveType()) {
               classFilter[i][2] = systemCL.loadClass(this.primitiveArrayName(classNameFilter[i][1]));
            } else {
               classFilter[i][2] = systemCL.loadClass(Array.newInstance(Class.forName(classNameFilter[i][1]), 0).getClass().getName());
            }
         }

      } catch (Exception var2) {
         throw new AssertionError("class filter init failed: " + var2);
      }
   }

   private String primitiveArrayName(String n) {
      if (n.equals("byte")) {
         return "[B";
      } else if (n.equals("char")) {
         return "[C";
      } else if (n.equals("double")) {
         return "[D";
      } else if (n.equals("float")) {
         return "[F";
      } else if (n.equals("int")) {
         return "[I";
      } else if (n.equals("long")) {
         return "[J";
      } else if (n.equals("short")) {
         return "[S";
      } else if (n.equals("boolean")) {
         return "[Z";
      } else {
         throw new AssertionError(n + " is not a primitice type");
      }
   }

   public void setOriginals(JClass[] originals) {
      this.originals = originals;
   }

   public JClass[] getOriginals() {
      return this.originals;
   }

   public static void setMBeans(HashMap mBeansParam) {
      mBeans = mBeansParam;
   }

   public static HashMap getMBeans() {
      return mBeans;
   }

   public void generate() throws Exception {
      super.generate();
   }

   public AbstractCollection getContextCollection() throws IOException {
      return new XSDContextCollection(this);
   }

   public Production getProduction(JClass jClass) {
      return new Production(jClass, this) {
         MyAnnotatableTarget mytarget;

         public Object getAnnotatableTarget() {
            if (this.mytarget == null) {
               this.mytarget = XSD2Java.this.new MyAnnotatableTarget(this.sourceJClass, this.findMatchingOriginal(this.sourceJClass));
            }

            return this.mytarget;
         }

         public AnnotatableClass getAnnotatableSource() {
            if (this._source == null) {
               this._source = XSD2Java.this.new MyAnnotatableClass(this.sourceJClass, this.findMatchingOriginal(this.sourceJClass));
            }

            return this._source;
         }

         AnnotatableClass findMatchingOriginal(JClass sourceJClass) {
            JClass[] originals = XSD2Java.this.getOriginals();
            String sourceName = (XSD2Java.this.new MyAnnotatableClass(sourceJClass)).getQualifiedName();

            for(int i = 0; i < originals.length; ++i) {
               if (originals[i] != null) {
                  String originalName = (new AnnotatableClass(originals[i])).getQualifiedName();
                  if (sourceName.equals(originalName)) {
                     JClass retVal = originals[i];
                     return new AnnotatableClass(retVal);
                  }
               }
            }

            return null;
         }
      };
   }

   static String filterName(String s) {
      for(int i = 0; i < targetNameFilter.length; ++i) {
         if (s.length() >= targetNameFilter[i][0].length() && s.indexOf(targetNameFilter[i][0]) != -1) {
            return s.replaceAll(targetNameFilter[i][0], targetNameFilter[i][1]);
         }
      }

      return s;
   }

   static void addAnnotationsNoDuplicates(ArrayList list, Annotation[] as) {
      boolean addIt = true;

      for(int i = 0; i < as.length; ++i) {
         Iterator j = list.iterator();

         while(j.hasNext()) {
            Annotation a = (Annotation)j.next();
            if (a.equals(as[i])) {
               addIt = false;
               break;
            }
         }

         if (addIt) {
            list.add(as[i]);
         }
      }

   }

   static void addCommentsNoDuplicates(ArrayList list, String[] as) {
      boolean addIt = true;

      for(int i = 0; i < as.length; ++i) {
         Iterator j = list.iterator();

         while(j.hasNext()) {
            String a = (String)j.next();
            if (a.equalsIgnoreCase(as[i])) {
               addIt = false;
               break;
            }
         }

         if (addIt) {
            list.add(as[i]);
         }
      }

   }

   class MyAnnotatableAttribute extends AnnotatableAttribute {
      int minAttrMethodLength = "Type".length();
      int minArrayMethodLength = "Array".length();

      public MyAnnotatableAttribute(JMethod jMethod) {
         super(jMethod);
      }

      public MyAnnotatableAttribute(JParameter jParameter) {
         super(jParameter);
      }

      public String getName() {
         return XSD2Java.filterName(super.getName());
      }

      public String getPropertyName() {
         return XSD2Java.filterName(super.getPropertyName());
      }

      protected String getRootAttributeName(JMethod jMethod) {
         String n = super.getRootAttributeName(jMethod);
         n = this.toLower(n);
         if (n.endsWith("Type")) {
            n = n.substring(0, n.lastIndexOf("Type"));
         }

         if (n.endsWith("Array")) {
            n = n.substring(0, n.lastIndexOf("Array"));
         }

         return XSD2Java.filterName(n);
      }

      protected AnnotatableClass newAnnotatableClass(JClass jClass) {
         return XSD2Java.this.new MyAnnotatableClass(jClass);
      }

      protected AnnotatableMethod newAnnotatableMethod(JMethod jMethod) {
         return XSD2Java.this.new MyAnnotatableMethod(jMethod);
      }
   }

   class MyAnnotatableMethod extends AnnotatableMethod {
      AnnotatableMethod orig;
      String myName;
      AnnotatableMethod matchingMBeanMethod;
      int minAttrMethodLength;
      int minArrayMethodLength;
      Annotation[] myAnnotations;

      public MyAnnotatableMethod(JMethod jMethod) {
         this(jMethod, (AnnotatableMethod)null);
      }

      public MyAnnotatableMethod(JMethod jMethod, AnnotatableMethod orig) {
         super(jMethod);
         this.minAttrMethodLength = "set".length() + "Type".length();
         this.minArrayMethodLength = "set".length() + "Array".length();
         this.orig = orig;
         this.matchingMBeanMethod = this.findMatchingMBeanMethod();
      }

      AnnotatableMethod findMatchingMBeanMethod() {
         String key = this.getQualifiedName();
         key = key.substring(0, key.indexOf("("));
         return (AnnotatableMethod)XSD2Java.mBeans.get(key);
      }

      public String getName() {
         if (this.myName == null) {
            super.getName();
            boolean isBean = (XSD2Java.this.new MyAnnotatableClass(this.getJMethod().getReturnType())).getQualifiedName().startsWith("weblogic.j2ee.descriptor");
            if (this.isGetterMethod(this.getJMethod()) && (XSD2Java.this.new MyAnnotatableClass(this.getJMethod().getReturnType())).getQualifiedName().equals("boolean")) {
               this.name = "is" + this.name.substring(3);
            }

            if (isBean && this.name.endsWith("Type")) {
               this.name = this.name + "Bean";
            }

            if (this.name.endsWith("Array") && this.name.length() > this.minArrayMethodLength) {
               this.name = this.name.substring(0, this.name.lastIndexOf("Array"));
               this.name = Utils.plural(this.name);
            }

            this.myName = XSD2Java.filterName(this.name);
         }

         return this.myName;
      }

      public Annotation getAnnotation(String tag) {
         Annotation[] aa = this.getAnnotations();

         for(int i = 0; i < aa.length; ++i) {
            if (aa[i].getName().equals(tag)) {
               return aa[i];
            }
         }

         return null;
      }

      public Annotation[] getAnnotations() {
         if (this.myAnnotations == null) {
            ArrayList list = new ArrayList();
            if (this.orig != null && this.orig.hasAnnotations()) {
               list.addAll(Arrays.asList((Object[])this.orig.getAnnotations()));
            }

            if (this.matchingMBeanMethod != null && this.matchingMBeanMethod.hasAnnotations()) {
               if (list.isEmpty()) {
                  list.addAll(Arrays.asList((Object[])this.matchingMBeanMethod.getAnnotations()));
               } else {
                  XSD2Java.addAnnotationsNoDuplicates(list, this.matchingMBeanMethod.getAnnotations());
               }
            }

            Annotation[] xbeans = super.getAnnotations();
            if (xbeans.length > 0) {
               if (list.isEmpty()) {
                  list.addAll(Arrays.asList((Object[])xbeans));
               } else {
                  XSD2Java.addAnnotationsNoDuplicates(list, xbeans);
               }
            }

            this.myAnnotations = new Annotation[list.size()];
            list.toArray(this.myAnnotations);
         }

         return this.myAnnotations;
      }

      public boolean hasAnnotation(String tag) {
         return this.getAnnotation(tag) != null;
      }

      public boolean hasAnnotations() {
         return this.getAnnotations().length > 0;
      }

      public boolean hasComments() {
         return this.getComments().length > 0;
      }

      public String[] getComments() {
         if (this.comments == null) {
            ArrayList list = new ArrayList();
            if (this.orig != null && this.orig.hasComments()) {
               list.addAll(Arrays.asList((Object[])this.orig.getComments()));
            } else {
               list.addAll(Arrays.asList((Object[])super.getComments()));
            }

            if (this.matchingMBeanMethod != null && this.matchingMBeanMethod.hasComments()) {
               if (list.isEmpty()) {
                  list.addAll(Arrays.asList((Object[])this.matchingMBeanMethod.getComments()));
               } else {
                  XSD2Java.addCommentsNoDuplicates(list, this.matchingMBeanMethod.getComments());
               }
            }

            this.comments = new String[list.size()];
            list.toArray(this.comments);
         }

         return this.comments;
      }

      protected AnnotatableAttribute newAnnotatableAttribute(JMethod jMethod) {
         return XSD2Java.this.new MyAnnotatableAttribute(jMethod);
      }

      protected AnnotatableAttribute newAnnotatableAttribute(JParameter pa) {
         return XSD2Java.this.new MyAnnotatableAttribute(pa);
      }

      protected AnnotatableClass newAnnotatableClass(JClass jClass) {
         return XSD2Java.this.new MyAnnotatableClass(jClass);
      }
   }

   class MyAnnotatableClass extends AnnotatableClass {
      boolean isBean = false;
      AnnotatableClass orig;
      AnnotatableClass matchingMBean;
      Annotation[] myAnnotations;

      MyAnnotatableClass(JClass jClass, AnnotatableClass orig) {
         super(jClass);
         this.orig = orig;
         this.init();
      }

      MyAnnotatableClass(JClass jClass) {
         super(jClass);
         this.init();
      }

      void init() {
         if (this.jClass.isArrayType()) {
            JClass ctype = this.jClass.getArrayComponentType();

            for(int ix = 0; ix < XSD2Java.classFilter.length; ++ix) {
               if (XSD2Java.classFilter[ix][0].isAssignableFrom(ctype)) {
                  this.jClass = XSD2Java.classFilter[ix][2];
                  break;
               }
            }
         } else {
            for(int i = 0; i < XSD2Java.classFilter.length; ++i) {
               if (XSD2Java.classFilter[i][0].isAssignableFrom(this.jClass)) {
                  this.jClass = XSD2Java.classFilter[i][1];
                  break;
               }
            }
         }

         this.isBean = this.jClass.getQualifiedName().startsWith("com.sun.java.xml.ns.j2Ee") || this.jClass.getQualifiedName().startsWith("com.bea.ns.weblogic.x90") || this.jClass.getQualifiedName().startsWith("com.bea.ns.weblogic.x60");
         this.matchingMBean = this.findMatchingMBean();
      }

      AnnotatableClass findMatchingMBean() {
         return (AnnotatableClass)XSD2Java.mBeans.get(this.getQualifiedName());
      }

      public String getQualifiedName() {
         if (this.qualifiedName == null) {
            super.getQualifiedName();
            if (this.isBean) {
               this.qualifiedName = this.qualifiedName.replaceFirst("com.sun.java.xml.ns.j2Ee", "weblogic.j2ee.descriptor");
               this.qualifiedName = this.qualifiedName.replaceFirst("com.bea.ns.weblogic.x90", "weblogic.j2ee.descriptor.wl");
               this.qualifiedName = this.qualifiedName.replaceFirst("com.bea.ns.weblogic.x60", "weblogic.j2ee.descriptor.wl60");
            }

            if (this.qualifiedName.endsWith("Type")) {
               this.qualifiedName = this.qualifiedName.substring(0, this.qualifiedName.lastIndexOf("Type"));
               if (this.isBean) {
                  this.qualifiedName = this.qualifiedName + "Bean";
               }
            }

            if (this.getJClass().isArrayType() && this.qualifiedName.endsWith("Type[]")) {
               this.qualifiedName = this.qualifiedName.substring(0, this.qualifiedName.lastIndexOf("Type[]"));
               if (this.isBean) {
                  this.qualifiedName = this.qualifiedName + "Bean[]";
               }
            }

            this.qualifiedName = XSD2Java.filterName(this.qualifiedName);
         }

         return this.qualifiedName;
      }

      public String getName() {
         super.getName();
         if (this.name.endsWith("Type")) {
            this.name = this.name.substring(0, this.name.lastIndexOf("Type"));
            if (this.isBean) {
               this.name = this.name + "Bean";
            }
         }

         if (this.getJClass().isArrayType() && this.name.endsWith("Type[]")) {
            this.name = this.name.substring(0, this.name.lastIndexOf("Type[]"));
            if (this.isBean) {
               this.name = this.name + "Bean[]";
            }
         }

         return XSD2Java.filterName(this.name);
      }

      public String getPackage() {
         if (this.packageName == null) {
            super.getPackage();
            if (this.packageName.startsWith("com.sun.java.xml.ns.j2Ee")) {
               this.packageName = this.packageName.replaceFirst("com.sun.java.xml.ns.j2Ee", "weblogic.j2ee.descriptor");
            }

            if (this.packageName.startsWith("com.bea.ns.weblogic.x90")) {
               this.packageName = this.packageName.replaceFirst("com.bea.ns.weblogic.x90", "weblogic.j2ee.descriptor.wl");
            }

            if (this.packageName.startsWith("com.bea.ns.weblogic.x60")) {
               this.packageName = this.packageName.replaceFirst("com.bea.ns.weblogic.x60", "weblogic.j2ee.descriptor.wl60");
            }
         }

         return this.packageName;
      }

      public ArrayList getMethodsList() {
         JMethod[] ma = this.getJClass().getDeclaredMethods();
         ArrayList l = new ArrayList();

         for(int ix = 0; ix < ma.length; ++ix) {
            if (!ma[ix].getSimpleName().startsWith("isSet")) {
               AnnotatableMethod m = this.newAnnotatableMethod(ma[ix]);
               if (m.isSetter() || m.isGetter()) {
                  l.add(m);
               }
            }
         }

         if (this.orig != null) {
            AnnotatableAttribute[] attrs = this.orig.getAttributes();
            AnnotatableMethod[] ms = this.orig.getMethods();

            int j;
            int i;
            for(j = 0; j < attrs.length; ++j) {
               for(i = 0; i < ms.length; ++i) {
                  if (ms[i] != null && (ms[i].getName().endsWith(attrs[j].getPropertyName()) || ms[i].getName().endsWith(attrs[j].getSingularPropertyName()))) {
                     ms[i] = null;
                  }
               }
            }

            j = 0;

            for(i = 0; i < ms.length; ++i) {
               if (ms[i] != null) {
                  System.out.println("Warning: couldn't match: " + ms[i].getQualifiedName());
                  ++j;
               }
            }

            if (j > 0) {
               System.out.println("\n Check for adding " + j + " elements to " + this);
            }
         }

         return l;
      }

      public Annotation getAnnotation(String tag) {
         Annotation[] aa = this.getAnnotations();

         for(int i = 0; i < aa.length; ++i) {
            if (aa[i].getName().equals(tag)) {
               return aa[i];
            }
         }

         return null;
      }

      public Annotation[] getAnnotations() {
         if (this.myAnnotations == null) {
            ArrayList list = new ArrayList();
            if (this.orig != null && this.orig.hasAnnotations()) {
               list.addAll(Arrays.asList((Object[])this.orig.getAnnotations()));
            }

            if (this.matchingMBean != null && this.matchingMBean.hasAnnotations()) {
               if (list.isEmpty()) {
                  list.addAll(Arrays.asList((Object[])this.matchingMBean.getAnnotations()));
               } else {
                  XSD2Java.addAnnotationsNoDuplicates(list, this.matchingMBean.getAnnotations());
               }
            }

            Annotation[] xbeans = super.getAnnotations();
            if (xbeans.length > 0) {
               if (list.isEmpty()) {
                  list.addAll(Arrays.asList((Object[])xbeans));
               } else {
                  XSD2Java.addAnnotationsNoDuplicates(list, xbeans);
               }
            }

            this.myAnnotations = new Annotation[list.size()];
            list.toArray(this.myAnnotations);
         }

         return this.myAnnotations;
      }

      public boolean hasAnnotation(String tag) {
         return this.getAnnotation(tag) != null;
      }

      public boolean hasAnnotations() {
         return this.getAnnotations().length > 0;
      }

      public boolean hasComments() {
         return this.getComments().length > 0;
      }

      public String[] getComments() {
         if (this.comments == null) {
            ArrayList list = new ArrayList();
            if (this.orig != null && this.orig.hasComments()) {
               list.addAll(Arrays.asList((Object[])this.orig.getComments()));
            } else {
               list.addAll(Arrays.asList((Object[])super.getComments()));
            }

            if (this.matchingMBean != null && this.matchingMBean.hasComments()) {
               if (list.isEmpty()) {
                  list.addAll(Arrays.asList((Object[])this.matchingMBean.getComments()));
               } else {
                  XSD2Java.addCommentsNoDuplicates(list, this.matchingMBean.getComments());
               }
            }

            this.comments = new String[list.size()];
            list.toArray(this.comments);
         }

         return this.comments;
      }

      protected AnnotatableClass newAnnotatableClass(JClass jClass) {
         return XSD2Java.this.new MyAnnotatableClass(jClass);
      }

      protected AnnotatableMethod newAnnotatableMethod(JMethod jMethod) {
         MyAnnotatableMethod temp = XSD2Java.this.new MyAnnotatableMethod(jMethod);
         return XSD2Java.this.new MyAnnotatableMethod(jMethod, this.findMatchingOriginal(temp));
      }

      AnnotatableMethod findMatchingOriginal(MyAnnotatableMethod m1) {
         if (this.orig == null) {
            return null;
         } else {
            AnnotatableMethod[] m = this.orig.getMethods();

            for(int i = 0; i < m.length; ++i) {
               AnnotatableMethod m2 = m[i];
               if (m1.equals(m[i])) {
                  return m2;
               }
            }

            return null;
         }
      }

      public AnnotatableClass[] getInterfaces() {
         if (this.interfaces == null) {
            super.getInterfaces();
            if (this.interfaces.length == 0) {
               return this.interfaces;
            }

            ArrayList a = new ArrayList();
            a.addAll(Arrays.asList((Object[])this.interfaces));

            for(int ix = 0; ix < this.interfaces.length; ++ix) {
               if (this.interfaces[ix].getQualifiedName().equals("com.bea.xml.XmlObject")) {
                  a.remove(ix);
               }
            }

            if (this.hasAnnotations()) {
               Annotation[] ants = this.getAnnotations();

               for(int i = 0; i < ants.length; ++i) {
                  if (ants[i] != null && ants[i].getName().endsWith("extends")) {
                     a.add(ants[i].getAnnotatableClassValue());
                  }
               }
            }

            this.interfaces = new AnnotatableClass[a.size()];
            a.toArray(this.interfaces);
         }

         return this.interfaces;
      }
   }

   class MyAnnotatableTarget extends MyAnnotatableClass {
      MyAnnotatableTarget(JClass jClass) {
         super(jClass);
      }

      MyAnnotatableTarget(JClass jClass, AnnotatableClass orig) {
         super(jClass, orig);
      }
   }
}
