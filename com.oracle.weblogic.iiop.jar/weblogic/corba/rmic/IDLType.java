package weblogic.corba.rmic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import weblogic.corba.utils.CorbaUtils;
import weblogic.iiop.IDLUtils;
import weblogic.utils.Debug;
import weblogic.utils.compiler.CodeGenerationException;

public abstract class IDLType {
   private static boolean debug = false;
   private static boolean verbose = false;
   protected static Hashtable m_usedTypes = new Hashtable();
   private static final boolean DEBUG = false;
   protected Class m_class;
   protected Class m_enclosingClass;
   Hashtable m_methods;
   Hashtable m_attributes;
   Hashtable m_extraLines;
   String m_directory;
   private static TypeOverride typeOverrides = new TypeOverride();

   public IDLType(Class c, Class ec) {
      this(c, ec, (String)null);
   }

   public IDLType(Class c, Class enclosingClass, String directory) {
      this.m_methods = new Hashtable();
      this.m_attributes = new Hashtable();
      this.m_extraLines = new Hashtable();
      Debug.assertion(null != c);
      this.m_class = c;
      this.m_enclosingClass = enclosingClass;
      this.m_directory = directory;
      this.init();
   }

   public String getDirectory() {
      return this.m_directory;
   }

   public String getFileName() {
      return IDLUtils.javaTypeToPath(this.m_directory, this.m_class);
   }

   public static Hashtable getUsedTypes() {
      return m_usedTypes;
   }

   public static void resetUsedTypes() {
      m_usedTypes = new Hashtable();
   }

   static void getAll(Class theInterface, Hashtable out, boolean recurse) {
      getAll(theInterface, out, recurse, true);
   }

   static void getAll(Class theInterface, Hashtable out, boolean recurse, boolean include_methods) {
      Class[] classes = IDLUtils.getClasses(theInterface, recurse, include_methods);
      Class[] var5 = classes;
      int var6 = classes.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Class c = var5[var7];
         if (!IDLOptions.getNoAbstract() || !CorbaUtils.isAbstractInterface(c)) {
            IDLType t = createType(c, theInterface);
            out.put(t.getClassName(), t);
         }
      }

   }

   public static IDLType createType(Class c, Class ec) {
      IDLType result = null;
      if (null != IDLTypeSpecial.TRAITS.getValidClass(c, ec)) {
         result = new IDLTypeSpecial(c, ec);
      } else if (null != IDLTypeEntity.TRAITS.getValidClass(c, ec)) {
         result = new IDLTypeEntity(c, ec);
      } else if (null != IDLTypeRemote.TRAITS.getValidClass(c, ec)) {
         result = new IDLTypeRemote(c, ec);
      } else if (null != IDLTypeEx.TRAITS.getValidClass(c, ec)) {
         IDLTypeValueType enclosed = new IDLTypeValueType(c, ec);
         result = new IDLTypeEx(c, ec, enclosed);
         m_usedTypes.put(enclosed.getFileName(), enclosed);
      } else if (null != IDLTypeValueType.TRAITS.getValidClass(c, ec)) {
         result = new IDLTypeValueType(c, ec);
      } else if (null != IDLTypeSequence.TRAITS.getValidClass(c, ec)) {
         result = new IDLTypeSequence(c, ec);
      } else if (null != IDLTypeNonConformant.TRAITS.getValidClass(c, ec)) {
         result = new IDLTypeNonConformant(c, ec);
      } else if (null != IDLTypeAbstractInterface.TRAITS.getValidClass(c, ec)) {
         result = new IDLTypeAbstractInterface(c, ec);
      }

      m_usedTypes.put(((IDLType)result).getFileName(), result);
      return (IDLType)result;
   }

   public static void registerOverride(String method, String args) {
      typeOverrides.registerMethodOverride(method, args);
   }

   public static void clearOverrides() {
      typeOverrides = new TypeOverride();
   }

   public boolean isRequired() throws CodeGenerationException {
      if (!IDLOptions.getNoValueTypes() && !IDLOptions.getNoAbstract() && typeOverrides.isEmpty()) {
         return true;
      } else if (IDLOptions.getNoValueTypes() && typeOverrides.isEmpty()) {
         return !IDLUtils.mustSkipClass(this.getClass(), this.getOpeningDeclaration());
      } else {
         return typeOverrides.isClassRequired(this.m_class) || !IDLUtils.mustSkipClass(this.getClass(), this.getOpeningDeclaration());
      }
   }

   public boolean isAssignableFrom(IDLType t) throws CodeGenerationException {
      return this.getJavaClass().isAssignableFrom(t.getJavaClass());
   }

   public void init() {
      this.initMethods();
      this.initAttributes();
   }

   public void initAttributes() {
      this.m_attributes = new Hashtable();

      try {
         if (!(this instanceof IDLTypeValueType)) {
            IDLUtils.getAttributesFromMethods(this.m_class, this.m_attributes);
         }

         IDLUtils.getAttributesFromFields(this.m_class, this.m_attributes);
      } catch (CodeGenerationException var5) {
         if (debug) {
            var5.printStackTrace();
         }
      }

      if (IDLOptions.getNoValueTypes() || IDLOptions.getNoAbstract()) {
         Enumeration e = this.m_attributes.elements();

         try {
            while(true) {
               IDLField a;
               do {
                  if (!e.hasMoreElements()) {
                     return;
                  }

                  a = (IDLField)e.nextElement();
               } while(!IDLUtils.mustSkipClass(a.getType()) && null == IDLUtils.getNonConformantType(a.getType()));

               if (!typeOverrides.isClassRequired(a.getType())) {
                  Object o = this.m_attributes.remove(a.getMangledName());
                  Debug.assertion(null != o);
               }
            }
         } catch (Exception var4) {
            if (debug) {
               var4.printStackTrace();
            }
         }
      }

   }

   public void initMethods() {
      this.m_methods = new Hashtable();
      Method[] methods = this.m_class.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         try {
            Method current = methods[i];
            boolean isMethodOk = true;
            if (IDLOptions.getNoValueTypes() && this.methodContainsValueTypes(current) && !typeOverrides.isMethodRequired(current)) {
               isMethodOk = false;
            }

            boolean isMethodValid = IDLUtils.isValid(current);
            if (isMethodOk && isMethodValid) {
               boolean isForAttribute = IDLUtils.isMethodForAnAttribute(this.m_class, current);
               if (!isForAttribute) {
                  IDLMethod newMethod = new IDLMethod(this.m_class, current.getName(), IDLUtils.getMangledMethodName(current, methods), current.getReturnType(), current.getParameterTypes(), current.getExceptionTypes());
                  this.m_methods.put(newMethod.getMangledName(), newMethod);
               }
            }
         } catch (CodeGenerationException var8) {
            if (debug) {
               var8.printStackTrace();
            }
         }
      }

   }

   private static void p(String s) {
      System.out.println("<IDLType>: " + s);
   }

   boolean methodContainsValueTypes(Method m) {
      Class returnType = m.getReturnType();
      if (IDLUtils.mustSkipClass(returnType)) {
         return true;
      } else {
         Class[] c = m.getParameterTypes();

         for(int i = 0; i < c.length; ++i) {
            if (IDLUtils.mustSkipClass(c[i])) {
               return true;
            }
         }

         return false;
      }
   }

   public Hashtable getMethods() {
      return this.m_methods;
   }

   public Hashtable getAttributes() {
      return this.m_attributes;
   }

   public Hashtable getExtraLines() {
      return this.m_extraLines;
   }

   public Class getJavaClass() {
      return this.m_class;
   }

   public Class getEnclosingClass() {
      return this.m_enclosingClass;
   }

   public Class[] getInheritedClasses() {
      Class[] itf = this.getJavaClass().getInterfaces();
      Vector vInh = new Vector();
      Class sc = this.getJavaClass().getSuperclass();
      if (sc != null && CorbaUtils.isValid(sc) && !sc.equals(Object.class)) {
         vInh.addElement(sc);
      }

      Class[] inh = itf;
      int var5 = itf.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class anItf = inh[var6];
         if (CorbaUtils.isValid(anItf)) {
            vInh.addElement(anItf);
         }
      }

      inh = new Class[vInh.size()];
      vInh.copyInto(inh);
      return inh;
   }

   public boolean canHaveSubtype(IDLType t) {
      return true;
   }

   public String getPackageName() {
      return null;
   }

   public String getClassName() {
      return this.m_class.getName();
   }

   public String beforeMainDeclaration() {
      return "";
   }

   public String afterMainDeclaration() {
      return "";
   }

   public abstract String getIncludeDeclaration() throws CodeGenerationException;

   public abstract String getForwardDeclaration() throws CodeGenerationException;

   public abstract void getReferences(Hashtable var1);

   public String getInheritKeyword(IDLType subtype) {
      return ":";
   }

   public String getOpeningDeclaration() throws CodeGenerationException {
      return "";
   }

   public final String generateMethods() throws CodeGenerationException {
      StringBuffer result = new StringBuffer();
      Hashtable methods = this.getMethods();
      Enumeration e = methods.elements();

      while(e.hasMoreElements()) {
         IDLMethod m = (IDLMethod)e.nextElement();
         if (m.isRequired()) {
            if (!this.checkInheritedMethod(this.getJavaClass(), this.getJavaClass(), m)) {
               result.append("    " + m.toIDL() + "\n");
            } else if (!IDLOptions.getIDLStrict()) {
               result.append("//  clash with parent on " + m.toIDL()).append("\n//");
            }
         }
      }

      return result.toString();
   }

   boolean checkInheritedMethod(Class start, Class c, IDLMethod m) {
      if (c != null && !c.equals(Object.class)) {
         if (c != start) {
            try {
               c.getMethod(m.getName(), m.getParameterTypes());
               return true;
            } catch (NoSuchMethodException var7) {
            }
         }

         Class sc = c.getSuperclass();
         if (this.checkInheritedMethod(start, sc, m)) {
            return true;
         } else {
            Class[] inf = c.getInterfaces();

            for(int i = 0; i < inf.length; ++i) {
               if (this.checkInheritedMethod(start, inf[i], m)) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public final String generateAttributes() throws CodeGenerationException {
      StringBuffer result = new StringBuffer();
      LinkedList attributes = new LinkedList(this.getAttributes().values());
      Collections.sort(attributes, new Comparator() {
         public int compare(Object o1, Object o2) {
            IDLField f1 = (IDLField)o1;
            IDLField f2 = (IDLField)o2;
            if (f1 instanceof IDLAttribute && !(f2 instanceof IDLAttribute)) {
               return 1;
            } else if (!(f1 instanceof IDLAttribute) && f2 instanceof IDLAttribute) {
               return -1;
            } else if (f1.isConst() && !f2.isConst()) {
               return -1;
            } else if (f2.isConst() && !f1.isConst()) {
               return 1;
            } else if (f1.isPrimitive() && !f2.isPrimitive()) {
               return -1;
            } else {
               return !f1.isPrimitive() && f2.isPrimitive() ? 1 : f1.getName().compareTo(f2.getName());
            }
         }
      });
      Iterator e = attributes.iterator();

      while(e.hasNext()) {
         IDLField a = (IDLField)e.next();
         result.append("    " + a.toIDL() + "\n");
      }

      return result.toString();
   }

   public final String generateExtraLines() {
      StringBuilder result = new StringBuilder();
      Hashtable attributes = this.getExtraLines();
      Enumeration e = attributes.elements();

      while(e.hasMoreElements()) {
         IDLExtraLine s = (IDLExtraLine)e.nextElement();
         result.append("    ").append(s.toIDL()).append("\n");
      }

      return result.toString();
   }

   public static String getOpeningDecl(IDLType c, Class enclosingClass, Class[] inh, String openingDecl, TypeTraits traits) throws CodeGenerationException {
      StringBuilder result = new StringBuilder("  " + openingDecl);
      String typeName = IDLUtils.stripPackage(IDLUtils.getIDLType(c.getJavaClass()), "::");
      result.append(" ").append(typeName);
      Vector colon = new Vector();
      Vector supports = new Vector();
      Class[] var9 = inh;
      int var10 = inh.length;

      int var11;
      for(var11 = 0; var11 < var10; ++var11) {
         Class vc = var9[var11];
         if (!IDLOptions.getNoAbstract() || !CorbaUtils.isAbstractInterface(vc)) {
            IDLType t = createType(vc, enclosingClass);
            if (c instanceof IDLTypeValueType && t instanceof IDLTypeEx) {
               t = ((IDLTypeEx)t).getEnclosed();
            }

            switch (t.getInheritKeyword(c)) {
               case ":":
                  colon.addElement(t);
                  break;
               case "supports":
                  supports.addElement(t);
                  break;
               default:
                  throw new CodeGenerationException("Unknown inheritance keyword for " + t.toString());
            }
         }
      }

      Collections.sort(colon, new Comparator() {
         public int compare(IDLType o1, IDLType o2) {
            if (o1 instanceof IDLTypeValueType && !(o2 instanceof IDLTypeValueType)) {
               return -1;
            } else if (o1 instanceof IDLTypeRemote && !(o2 instanceof IDLTypeRemote)) {
               return -1;
            } else if (!(o1 instanceof IDLTypeValueType) && o2 instanceof IDLTypeValueType) {
               return 1;
            } else {
               return !(o1 instanceof IDLTypeRemote) && o2 instanceof IDLTypeRemote ? 1 : 0;
            }
         }
      });
      Vector[] keywords = new Vector[]{colon, supports};
      Vector[] var19 = keywords;
      var11 = keywords.length;

      for(int var20 = 0; var20 < var11; ++var20) {
         Vector v = var19[var20];
         boolean first = true;
         Enumeration e = v.elements();

         while(e.hasMoreElements()) {
            IDLType t = (IDLType)e.nextElement();
            Class cl = t.getJavaClass();
            if (t.canHaveSubtype(c)) {
               if (first) {
                  first = false;
                  result.append(" ").append(t.getInheritKeyword(c));
               } else {
                  result.append(", ");
               }

               result.append(" ").append(IDLUtils.getIDLType(cl));
            }
         }
      }

      result.append(" ");
      return result.toString();
   }

   public String getPragmaID() {
      return IDLUtils.getPragmaID(this.getJavaClass());
   }

   public String getOpenBrace() {
      return "{\n";
   }

   public String getCloseBrace() {
      return "\n};\n";
   }

   public String getGuardName(String preprocessor) {
      return IDLUtils.generateGuard(this.m_class, preprocessor);
   }

   public String toString() {
      return this.getClass().getName() + "(" + this.m_class.getName() + ")";
   }

   static class TypeOverride {
      ArrayList typeOverrides = new ArrayList();
      HashMap methodOverrides = new HashMap();
      Class remoteInterface = null;

      void registerMethodOverride(String method, String args) {
         try {
            this.methodOverrides.put(method, StructureTokenizer.getParameterTypes(args));
         } catch (ClassNotFoundException var4) {
         }

      }

      boolean isClassRequired(Class c) {
         return true;
      }

      boolean isMethodRequired(Method method) {
         Object types = this.methodOverrides.get(method.getName());
         if (types != null) {
            Class[] args = (Class[])((Class[])types);
            Class[] margs = method.getParameterTypes();
            if (args.length == margs.length) {
               int i = 0;

               while(true) {
                  if (i >= args.length) {
                     return true;
                  }

                  if (!args[i].equals(margs[i])) {
                     break;
                  }

                  ++i;
               }
            }
         }

         return false;
      }

      boolean isEmpty() {
         return this.methodOverrides.size() == 0;
      }
   }
}
