package weblogic.management.tools;

import java.util.Locale;
import java.util.StringTokenizer;
import weblogic.utils.Debug;

class DTDMethod implements Comparable {
   private TaggedMethod m_taggedMethod;
   private String m_signature;
   private String m_methodName;
   private String m_dtdTag;
   private String m_returnType;
   private DefaultValue m_defaultValue;

   public DTDMethod(TaggedMethod tm, String signature, String dtdTag, DefaultValue defaultValue) {
      this.m_taggedMethod = tm;
      this.m_signature = signature;
      this.m_dtdTag = dtdTag;
      this.m_defaultValue = defaultValue;
      StringTokenizer st = new StringTokenizer(signature);
      String scope = st.nextToken();
      this.m_returnType = st.nextToken();
      this.m_methodName = st.nextToken();
      int index = this.m_methodName.indexOf("(");
      if (index != -1) {
         this.m_methodName = this.m_methodName.substring(0, index);
      }

   }

   public DTDMethod(String className) {
      this.m_methodName = className;
   }

   public static String toElementName(String mn) {
      String result = new String();
      int ind = mn.indexOf("MBean");
      if (-1 != ind) {
         mn = mn.substring(0, ind);
      }

      mn = Character.toUpperCase(mn.charAt(0)) + mn.substring(1);

      int j;
      for(int i = 0; i < mn.length(); result = result + mn.substring(j, i).toLowerCase(Locale.US)) {
         for(j = i; i < mn.length() && Character.isUpperCase(mn.charAt(i)); ++i) {
         }

         if (i - j <= 1) {
            while(i < mn.length() && !Character.isUpperCase(mn.charAt(i))) {
               ++i;
            }
         } else {
            --i;
         }

         if (j > 0 && j < mn.length() - 1) {
            result = result + "-";
         }
      }

      return result;
   }

   static String getFieldName(String name) {
      StringBuffer buf = new StringBuffer(name);
      buf.setCharAt(0, Character.toLowerCase(buf.charAt(0)));

      for(int i = 1; i < name.length() && !Character.isLowerCase(buf.charAt(i)) && (i + 1 >= name.length() || !Character.isLowerCase(buf.charAt(i + 1))); ++i) {
         buf.setCharAt(i, Character.toLowerCase(buf.charAt(i)));
      }

      return buf.toString();
   }

   private static String unprefixField(String prefix, String field) {
      if (0 != field.indexOf(prefix)) {
         throw new RuntimeException("Field '" + field + "' does not start with '" + prefix + "'");
      } else {
         int ind = prefix.length();
         char c = Character.toLowerCase(field.charAt(ind));
         return c + field.substring(ind + 1);
      }
   }

   public TaggedMethod getTaggedMethod() {
      return this.m_taggedMethod;
   }

   public boolean isBoolean() {
      return -1 != this.m_returnType.indexOf("boolean");
   }

   public boolean isString() {
      return -1 != this.m_returnType.indexOf("tring");
   }

   public boolean isPrimitiveStrict() {
      return this.isBoolean() || -1 != this.m_returnType.indexOf("int");
   }

   public boolean isPrimitiveOrString() {
      return this.isPrimitiveStrict() || this.isString();
   }

   public String getDTDTag() {
      return this.m_dtdTag;
   }

   public String getMethodName() {
      return this.m_methodName;
   }

   public String getSignature() {
      return this.m_signature;
   }

   public String getReturnType() {
      return this.m_returnType;
   }

   public boolean isMBean() {
      return -1 != this.getReturnType().indexOf("MBean");
   }

   public boolean isOr() {
      return -1 != this.getDTDTag().indexOf("|");
   }

   public boolean isOptional() {
      return -1 != this.getDTDTag().indexOf("?");
   }

   public boolean isMandatory() {
      return !this.isOr() && !this.isOptional();
   }

   public boolean isArray() {
      return -1 != this.getSignature().indexOf("[]");
   }

   public int getDTDOrder() {
      String tag = this.getDTDTag();
      int ind = tag.length();
      if (this.isOr()) {
         ind = tag.indexOf("|");
      } else if (this.isOptional()) {
         ind = tag.indexOf("?");
      }

      Debug.assertion(-1 != ind, this.getMethodName() + " tag:" + tag);
      return Integer.parseInt(tag.substring(0, ind));
   }

   public int compareTo(Object o) {
      DTDMethod other = (DTDMethod)o;
      return this.getDTDTag().compareTo(other.getDTDTag());
   }

   public String capitalizeIfBoolean(String methodName) {
      String result = methodName + "()";
      if (this.isBoolean()) {
         result = "ToXML.capitalize(Boolean.valueOf(" + result + ").toString())";
      }

      return result;
   }

   public StringBuffer toXMLIfNotNullAndNotDefault(String spacing) {
      StringBuffer result = new StringBuffer();
      if (!this.isBoolean() && this.isPrimitiveOrString()) {
         if (null != this.m_defaultValue) {
            result.append(spacing).append("if (" + this.testForDefault() + ") {\n");
         }

         result.append(spacing).append("  ").append(this.toXML());
         if (null != this.m_defaultValue) {
            result.append(spacing).append("}\n");
         }
      } else {
         if (!this.isPrimitiveOrString()) {
            result.append(spacing).append("if (" + this.testForNull() + ") {\n");
         }

         if (null != this.m_defaultValue) {
            result.append(spacing).append("  ").append("if (" + this.testForDefault() + ") {\n");
         }

         result.append(spacing).append("    ").append(this.toXML());
         if (null != this.m_defaultValue) {
            result.append(spacing).append("  ").append("}\n");
         }

         if (!this.isPrimitiveOrString()) {
            result.append(spacing).append("}\n");
         }
      }

      return result;
   }

   public StringBuffer toXMLForOr(String spacing) {
      StringBuffer result = new StringBuffer();
      result.append(spacing).append("  if (" + this.testForNotNullForOr() + ") {\n");
      result.append(spacing).append("    ").append(this.toXML());
      result.append(spacing).append("  }\n");
      return result;
   }

   public StringBuffer toXMLIfNotNull(String spacing) {
      StringBuffer result = new StringBuffer();
      if (!this.isBoolean() && this.isPrimitiveOrString()) {
         result.append(spacing).append(this.toXML());
      } else {
         result.append(spacing).append("if (" + this.testForNull() + ") {\n");
         result.append(spacing).append("    ").append(this.toXML());
         result.append(spacing).append("}\n");
      }

      return result;
   }

   public String toXML() {
      String result = "";
      boolean isCdata = this.getTaggedMethod().containsTag("@cdata");
      boolean isEmpty = this.getTaggedMethod().containsTag("@empty");
      if (this.getMethodName().indexOf("get") != 0 && this.getMethodName().indexOf("is") != 0) {
         throw new RuntimeException("Illegal @dtd-order found on a  method that is not a getter:" + this.getMethodName());
      } else {
         String attributeName = this.getMethodName().substring("get".length());
         if (0 == this.getMethodName().indexOf("is")) {
            attributeName = this.getMethodName().substring("is".length());
         }

         if (!this.isMBean()) {
            String elementName = toElementName(attributeName);
            if (this.isArray()) {
               if ('s' == elementName.charAt(elementName.length() - 1)) {
                  elementName = elementName.substring(0, elementName.length() - 1);
               }

               result = result + "for (int i = 0; i < " + this.getMethodName() + "().length; i++) {\n";
               result = result + "        result";
               result = result + ".append(ToXML.indent(indentLevel + 2))";
               if (!isEmpty) {
                  result = result + ".append(\"<" + elementName + ">\")";
                  result = result + ".append(" + this.wrap(isCdata, this.getMethodName() + "()[i]") + ")";
                  result = result + ".append(\"</" + elementName + ">\\n\");\n";
               } else {
                  result = result + ".append((" + this.getMethodName() + "() ? \"<" + elementName + "/>\\n\" : \"\"));\n";
               }

               result = result + "    }\n";
            } else {
               if (this.isString()) {
                  result = result + "if (" + this.testForNull() + ") {\n    ";
               }

               result = result + "  result";
               result = result + ".append(ToXML.indent(indentLevel + 2))";
               if (!isEmpty) {
                  String mName = this.capitalizeIfBoolean(this.getMethodName());
                  result = result + ".append(\"<" + elementName + ">\")";
                  result = result + ".append(" + this.wrap(isCdata, mName) + ")";
                  result = result + ".append(\"</" + elementName + ">\\n\");\n";
               } else {
                  result = result + ".append((" + this.getMethodName() + "() ? \"<" + elementName + "/>\\n\" : \"\"));\n";
               }

               if (this.isString()) {
                  result = result + "    }\n";
               }
            }
         } else if (this.isArray()) {
            result = result + "for (int i = 0; i < " + this.getMethodName() + "().length; i++) {\n";
            result = result + "          result.append(" + this.getMethodName() + "()[i].toXML(indentLevel + 2));\n";
            result = result + "}\n";
         } else {
            result = "result.append(" + this.getMethodName() + "().toXML(indentLevel + 2)).append(\"\\n\");\n";
         }

         return result;
      }
   }

   public String testForNull() {
      return this.isBoolean() ? this.getMethodName() + "()" : "null != " + this.getMethodName() + "()";
   }

   public String toElementName() {
      return toElementName(this.getMethodName());
   }

   public String toString() {
      return "[Method:" + this.getMethodName() + " dtd:" + this.getDTDTag() + "]";
   }

   private String wrap(boolean cdata, String s) {
      String result = s;
      if (cdata) {
         result = "\"<![CDATA[\" + " + s + " + \"]]>\"";
      }

      return result;
   }

   private String testForDefault() {
      String prefix = null;
      String fieldName = null;
      if (this.getMethodName().startsWith("get")) {
         prefix = "get";
      } else if (this.getMethodName().startsWith("is")) {
         prefix = "is";
      }

      fieldName = getFieldName(this.getMethodName().substring(prefix.length()));
      return null != this.m_defaultValue ? "((isSet_" + fieldName + ") ||  ! (" + this.m_defaultValue.testForEquality(this.getMethodName() + "())") + ")" : "";
   }

   private String testForNotNullForOr() {
      String prefix = null;
      String fieldName = null;
      if (this.getMethodName().startsWith("get")) {
         prefix = "get";
      } else if (this.getMethodName().startsWith("is")) {
         prefix = "is";
      }

      fieldName = getFieldName(this.getMethodName().substring(prefix.length()));
      if (this.isPrimitiveOrString()) {
         return null != this.m_defaultValue ? "((isSet_" + fieldName + ") ||  ! (" + this.m_defaultValue.testForEquality(this.getMethodName() + "())") + ")" : "isSet_" + fieldName;
      } else {
         return this.isBoolean() ? this.getMethodName() : this.testForNull();
      }
   }
}
