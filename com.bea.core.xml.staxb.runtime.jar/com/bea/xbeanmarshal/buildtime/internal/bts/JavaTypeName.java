package com.bea.xbeanmarshal.buildtime.internal.bts;

import com.bea.util.jam.JClass;
import com.bea.util.jam.internal.elements.PrimitiveClassImpl;
import com.bea.xml.XmlObject;
import java.io.Serializable;
import java.lang.reflect.Array;

public final class JavaTypeName implements Serializable {
   private static String XMLOBJECT_BEA_CLASSNAME = XmlObject.class.getName();
   private static final long serialVersionUID = 1L;
   private final String className;
   private final String arrayString;
   private final boolean isXmlObject;

   public static JavaTypeName forString(String className) {
      return new JavaTypeName(className);
   }

   public static JavaTypeName forArray(JavaTypeName itemType, int depth) {
      String arrayBrackets;
      for(arrayBrackets = ""; depth-- > 0; arrayBrackets = arrayBrackets + "[]") {
      }

      return forString(itemType.toString() + arrayBrackets);
   }

   public static JavaTypeName forJClass(JClass jClass) {
      if (jClass.isArrayType()) {
         return forArray(forJClass(jClass.getArrayComponentType()), jClass.getArrayDimensions());
      } else {
         JClass[] interfaces = jClass.getInterfaces();
         String xmlObject_apache_className = "NOT_APACHE_XML_BEAN_CLASSNAME";
         if (interfaces.length > 0) {
            JClass xmlObject = interfaces[0].forName("org.apache.xmlbeans.XmlObject");
            if (!xmlObject.isUnresolvedType()) {
               xmlObject_apache_className = xmlObject.getQualifiedName();
            }
         }

         for(int i = 0; i < interfaces.length; ++i) {
            if (interfaces[i].getQualifiedName().equals(xmlObject_apache_className) || interfaces[i].getQualifiedName().equals(XMLOBJECT_BEA_CLASSNAME)) {
               return forString("x=" + jClass.getQualifiedName());
            }
         }

         return forString(jClass.getQualifiedName());
      }
   }

   private JavaTypeName(String className) {
      if (className == null) {
         throw new IllegalArgumentException();
      } else {
         if (className.startsWith("x=")) {
            this.isXmlObject = true;
            className = className.substring(2);
         } else {
            this.isXmlObject = false;
         }

         int arrayDepth = 0;

         for(int i = className.length() - 2; i >= 0 && className.charAt(i) == '[' && className.charAt(i + 1) == ']'; i -= 2) {
            ++arrayDepth;
         }

         this.className = className.substring(0, className.length() - 2 * arrayDepth);
         this.arrayString = className.substring(className.length() - 2 * arrayDepth);
      }
   }

   private JavaTypeName(String nonArrayClassName, String arrayString, boolean isXmlObject) {
      assert !nonArrayClassName.startsWith("[");

      this.className = nonArrayClassName;
      this.arrayString = arrayString;
      this.isXmlObject = isXmlObject;
   }

   private void p(String s) {
      System.out.println(" [JavaTypeName] " + s);
   }

   public boolean isXmlObject() {
      return this.isXmlObject;
   }

   public int getArrayDepth() {
      return this.arrayString.length() / 2;
   }

   public JavaTypeName getArrayItemType(int depth) {
      if (this.arrayString.length() < depth * 2) {
         return null;
      } else {
         return depth == 1 ? forString(this.className) : forString(this.className + this.arrayString.substring(0, this.arrayString.length() - 2 * depth));
      }
   }

   public JavaTypeName getArrayTypeOfDimension(int dimension) {
      if (dimension <= 0) {
         return forString(this.className);
      } else {
         StringBuffer sb = new StringBuffer(this.className);

         for(int i = dimension; i > 0; --i) {
            sb.append("[]");
         }

         return forString(sb.toString());
      }
   }

   public JavaTypeName getArrayTypeMinus1Dim(int depth) {
      int arrayStringLen = this.arrayString.length();
      if (arrayStringLen > 0 && arrayStringLen >= depth * 2) {
         return depth == 1 ? forString(this.className) : forString(this.className + this.arrayString.substring(0, 2 * (depth - 1)));
      } else {
         return null;
      }
   }

   public String getClassName() {
      return this.className;
   }

   public String getPackage() {
      int index = this.className.lastIndexOf(46);
      return index <= 0 ? "" : this.className.substring(0, index);
   }

   public String getArrayString(int depth) {
      return this.arrayString.length() < depth * 2 ? null : this.arrayString.substring(2 * depth, this.arrayString.length());
   }

   public boolean isInnerClass() {
      return this.className.lastIndexOf(36) >= 0;
   }

   public JavaTypeName getContainingClass() {
      int index = this.className.lastIndexOf(36);
      return index < 0 ? null : forString(this.className.substring(0, index));
   }

   public String getShortClassName() {
      int index = this.className.lastIndexOf(36);
      int index2 = this.className.lastIndexOf(46);
      if (index2 > index) {
         index = index2;
      }

      return index < 0 ? this.className : this.className.substring(index + 1);
   }

   public boolean isNameForClass(Class c) {
      JavaTypeName cname = forClassName(c.getName());
      return this.equals(cname);
   }

   public String toString() {
      return this.isXmlObject ? "x=" + this.className + this.arrayString : this.className + this.arrayString;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof JavaTypeName)) {
         return false;
      } else {
         JavaTypeName javaName = (JavaTypeName)o;
         if (this.isXmlObject != javaName.isXmlObject) {
            return false;
         } else if (!this.className.equals(javaName.className)) {
            return false;
         } else {
            return this.arrayString.equals(javaName.arrayString);
         }
      }
   }

   public int hashCode() {
      return this.className.hashCode() + this.arrayString.length() + (this.isXmlObject ? 1 : 0);
   }

   /** @deprecated */
   @Deprecated
   public Class loadClassIn(ClassLoader loader) throws ClassNotFoundException {
      int d = this.getArrayDepth();
      if (d == 0) {
         String s = this.toString();
         Class out = PrimitiveClassImpl.getPrimitiveClass(s);
         return out != null ? out : loader.loadClass(s);
      } else {
         Class clazz = PrimitiveClassImpl.getPrimitiveClass(this.className);
         if (clazz == null) {
            clazz = loader.loadClass(this.className);
         }

         int[] dimensions = new int[d];
         return Array.newInstance(clazz, dimensions).getClass();
      }
   }

   public static JavaTypeName forClassName(String class_name) {
      if ('[' != class_name.charAt(0)) {
         return new JavaTypeName(class_name, "", false);
      } else {
         int first_bracket = class_name.indexOf(91);
         int last_bracket = class_name.lastIndexOf(91);
         int semi = class_name.indexOf(59, last_bracket);
         String compname;
         int dims;
         if (semi == -1) {
            dims = class_name.charAt(1 + last_bracket);
            compname = getPrimitiveArrayBase((char)dims);
         } else {
            compname = class_name.substring(last_bracket + 2, semi);
         }

         dims = 1 + last_bracket - first_bracket;

         assert compname.length() > 0;

         StringBuffer array_str = new StringBuffer(2 * dims);

         for(int i = 0; i < dims; ++i) {
            array_str.append("[]");
         }

         JavaTypeName jtn = new JavaTypeName(compname, array_str.toString(), false);
         return jtn;
      }
   }

   private static String getPrimitiveArrayBase(char array_type) {
      switch (array_type) {
         case 'B':
            return "byte";
         case 'C':
            return "char";
         case 'D':
            return "double";
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            throw new AssertionError("unknown array type: " + array_type);
         case 'F':
            return "float";
         case 'I':
            return "int";
         case 'J':
            return "long";
         case 'S':
            return "short";
         case 'Z':
            return "boolean";
      }
   }
}
