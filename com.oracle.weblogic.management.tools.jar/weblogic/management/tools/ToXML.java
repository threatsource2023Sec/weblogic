package weblogic.management.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import weblogic.utils.Debug;

public class ToXML {
   public static final String TAG_DTD_ORDER = "@dtd-order";
   public static final String TAG_EMPTY = "@empty";
   public static final String TAG_CDATA = "@cdata";
   public static final String TAG_ATTRIBUTE = "@attribute";
   public static final String TAG_NAME = "@name";
   private static final int TAG_NONE = 0;
   private static final int TAG_OR = 1;
   private static final int TAG_OPTIONAL = 2;
   private static final int TAG_MANDATORY = 3;
   private static final String sp1 = "  ";
   private static final String sp2 = "    ";
   private static TagParser m_parser;
   private static boolean verbose = false;
   private static StringBuffer[] m_indent = new StringBuffer[]{new StringBuffer(""), new StringBuffer("  "), new StringBuffer("    "), new StringBuffer("      "), new StringBuffer("        "), new StringBuffer("          ")};

   public static String toXML(TagParser parser) {
      m_parser = parser;
      StringBuffer result = new StringBuffer("  public String toXML(int indentLevel) {");
      result.append("\n");
      result.append("    StringBuffer result = new StringBuffer();");
      result.append("\n");
      TaggedMethod[] allMethods = m_parser.getMethodsWithTag("@dtd-order");
      DTDMethod thisClass = new DTDMethod(m_parser.getCurrentClassName());
      result.append("    result.append(ToXML.indent(indentLevel)).append(\"").append("<" + thisClass.toElementName() + "\");\n");
      TaggedMethod[] attrMethods = m_parser.getMethodsWithTag("@attribute");

      int i;
      for(i = 0; i < attrMethods.length; ++i) {
         StringBuffer attribStr = new StringBuffer();
         String field = attrMethods[i].getTagValue("@name");
         if (field == null) {
            field = attrMethods[i].getFieldName();
         }

         String get_method = attrMethods[i].getGetMethodName();
         String get_prefix = attrMethods[i].getGetPrefix();
         attribStr.append("    if (isSet_" + DTDMethod.getFieldName(get_method.substring(get_prefix.length())) + ") {\n");
         get_method = get_method + "()";
         String ret_type = attrMethods[i].getReturnType();
         if (ret_type.indexOf("XMLName") != -1) {
            attribStr.append("      result.append(\" xmlns:\").append(" + get_method + ".getPrefix()).append(\"=\\\"\").append(" + get_method + ".getNamespaceUri()).append(\"\\\" ");
            attribStr.append(field).append("=\\\"\").append(" + get_method + ".getQualifiedName()).append(\"\\\"\");\n");
         } else {
            attribStr.append("      result.append(\" ").append(field).append("=\\\"\").append(String.valueOf(" + get_method + ")).append(\"\\\"\");\n");
         }

         attribStr.append("    }\n");
         result.append(attribStr);
      }

      result.append("    result.append(\">\\n\");\n");

      for(i = 0; i < allMethods.length; ++i) {
         Collection c = getMethodsWithDTDOrder(allMethods, i);
         Iterator tmpIt = c.iterator();
         if (tmpIt.hasNext()) {
            DTDMethod tmpDtdMethod = (DTDMethod)tmpIt.next();
            int type = tmpDtdMethod.getDTDOrder();
            Iterator it;
            DTDMethod dm;
            if (tmpDtdMethod.isMandatory()) {
               it = c.iterator();
               dm = (DTDMethod)it.next();
               if (dm.isBoolean()) {
                  result.append(dm.toXML());
               } else {
                  result.append(dm.toXMLIfNotNull("     "));
               }
            } else if (tmpDtdMethod.isOptional()) {
               Debug.assertion(c.size() > 0, "Not enough @dtd-order " + tmpDtdMethod.getDTDOrder() + " tags in " + parser.getFileName());
               it = c.iterator();
               dm = (DTDMethod)it.next();
               result.append(dm.toXMLIfNotNullAndNotDefault("     "));
            } else {
               Debug.assertion(c.size() > 0, "Not enough @dtd-order " + tmpDtdMethod.getDTDOrder() + " tags in " + parser.getFileName());
               it = c.iterator();
               int j = 0;

               DTDMethod dm;
               for(DTDMethod previousMethod = null; it.hasNext(); previousMethod = dm) {
                  dm = (DTDMethod)it.next();
                  if (0 != j++ && !previousMethod.isArray()) {
                     result.append("    else /*@*/ ");
                  }

                  result.append(dm.toXMLForOr("  "));
               }
            }
         } else if (verbose) {
            System.out.println("Warning:" + parser.getFileName() + ":  couldn't find a method with @dtd-order " + i);
         }
      }

      result.append("    result.append(ToXML.indent(indentLevel)).append(\"").append("</" + thisClass.toElementName() + ">\\n\");\n");
      result.append("    return result.toString();\n");
      result.append("  }\n");
      return result.toString();
   }

   public static StringBuffer indent(int n) {
      if (n < m_indent.length * 2) {
         return m_indent[n / 2];
      } else {
         StringBuffer result = new StringBuffer("");

         for(int i = 0; i < n / 2; ++i) {
            result.append("  ");
         }

         return result;
      }
   }

   public static String capitalize(String s) {
      return Character.toUpperCase(s.charAt(0)) + s.substring(1);
   }

   public static void main(String[] argv) {
      String[] m = new String[]{"public void getSmallIconFileName()", "get-small-icon-file-name", "public void EJBJar()", "ejb-jar", "public void EJBQl()", "ejb-ql", "public void EJBJarMBean()", "ejb-jar", "public void providerURL()", "provider-url"};

      for(int i = 0; i < m.length; i += 2) {
         DTDMethod dm = new DTDMethod((TaggedMethod)null, m[i], "", (DefaultValue)null);
         Debug.assertion(dm.toElementName().equals(m[i + 1]), dm.toElementName() + " != " + m[i + 1]);
         Debug.say(dm.toElementName());
      }

   }

   public static String toElementName(String name) {
      return DTDMethod.toElementName(name);
   }

   private static Collection getMethodsWithDTDOrder(TaggedMethod[] methods, int n) {
      Collection result = new ArrayList();

      for(int i = 0; i < methods.length; ++i) {
         TaggedMethod tm = methods[i];
         String tag = tm.getTagValue("@dtd-order");
         DefaultValue defaultValue = m_parser.getDefaultValue(tm.getMethodName());
         DTDMethod dm = new DTDMethod(methods[i], tm.getMethodSignature(), tag, defaultValue);
         if (n == dm.getDTDOrder()) {
            result.add(dm);
         }
      }

      return result;
   }
}
