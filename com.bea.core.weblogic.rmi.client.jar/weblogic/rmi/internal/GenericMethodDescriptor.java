package weblogic.rmi.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.utils.UnsyncStringBuffer;

/** @deprecated */
@Deprecated
public class GenericMethodDescriptor {
   private static final String ARRAY_REPRESENTATION = "[]";
   private static final String COMMA = ",";
   private static final String OPEN_BRACKET = "<";
   private static final String CLOSE_BRACKET = ">";
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugGenericMethodDescriptor");
   private static boolean DEBUG = false;

   static boolean isGenericMethod(Method method) {
      Type[] var1 = method.getGenericParameterTypes();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Type type = var1[var3];
         if (!(type instanceof Class)) {
            return true;
         }
      }

      return false;
   }

   static String computeGenericMethodSignature(Method method, GenericInfo gInfo) {
      if (gInfo == null) {
         return computeGenericMethodSignature(method);
      } else {
         String genericSignature = gInfo.getMethodSignature(method);
         return computeGenericMethodSignature(genericSignature);
      }
   }

   static String computeGenericMethodSignature(Method method) {
      String genericSignature = method.toGenericString();
      String parsedMethod = genericSignature.substring(genericSignature.indexOf(method.getName() + '('));
      return computeGenericMethodSignature(parsedMethod);
   }

   public static String computeGenericMethodSignature(String method) {
      UnsyncStringBuffer sb = new UnsyncStringBuffer();
      int index = method.indexOf(40);
      int index2 = method.indexOf(41);
      if (index <= 0) {
         sb.append(method);
         sb.append('(').append(')');
         return sb.toString();
      } else {
         sb.append(method.substring(0, index + 1));
         String parameters = null;
         if (index2 > 0) {
            parameters = method.substring(index + 1, index2);
         } else {
            parameters = method.substring(index + 1);
         }

         List paramsList = parseParams(parameters);
         Iterator itr = paramsList.iterator();

         while(itr.hasNext()) {
            TypeParameter param = (TypeParameter)itr.next();
            sb.append(param);
         }

         sb.append(')');
         debug("signature=" + sb.toString());
         return sb.toString().trim().intern();
      }
   }

   private static List parseParams(String paramStr) {
      ArrayList parameterList = new ArrayList();
      if (paramStr.length() == 0) {
         return parameterList;
      } else {
         TypeParameter paramType = null;
         int beginIndex = 0;
         int endIndex = paramStr.length();
         int index1 = paramStr.indexOf(",");
         int index2 = paramStr.indexOf("<");
         if (index1 >= 0 && index2 >= 0) {
            if (index1 < index2) {
               endIndex = index1;
               paramType = parseNonGenericParameter(paramStr, index1);
            } else {
               endIndex = computeEndofTypeParam(paramStr, beginIndex);
               paramType = parseGenericParameter(paramStr, endIndex);
            }
         } else if (index1 >= 0) {
            endIndex = index1;
            paramType = parseNonGenericParameter(paramStr, index1);
         } else if (index2 >= 0) {
            endIndex = computeEndofTypeParam(paramStr, beginIndex);
            paramType = parseGenericParameter(paramStr, endIndex);
         } else {
            paramType = new TypeParameter(paramStr);
         }

         if (paramType != null) {
            parameterList.add(paramType);
         }

         if (endIndex < paramStr.length()) {
            parameterList.addAll(parseParams(paramStr.substring(endIndex + 1).trim()));
         }

         debug("paramlist=" + parameterList);
         return parameterList;
      }
   }

   private static TypeParameter parseNonGenericParameter(String paramStr, int endIndex) {
      TypeParameter paramType = null;
      int beginIndex = 0;
      String param = paramStr.substring(beginIndex, endIndex).trim();
      if (param.length() > 0) {
         debug("parsed paramter=" + param);
         paramType = new TypeParameter(param);
      }

      return paramType;
   }

   private static TypeParameter parseGenericParameter(String paramStr, int endIndex) {
      int beginIndex = 0;
      int index2 = paramStr.indexOf("<");
      endIndex = computeEndofTypeParam(paramStr, beginIndex);
      String type = paramStr.substring(beginIndex, endIndex + 1);
      debug("parsed paramter=" + type);
      boolean isArray = type.endsWith("[]");
      String parameter = paramStr.substring(beginIndex, index2).trim();
      int nestedParamsEndIndex = endIndex;
      if (isArray) {
         parameter = parameter + "[]";
         nestedParamsEndIndex = endIndex - "[]".length();
      }

      TypeParameter paramType = new TypeParameter(parameter);
      List subTypes = parseParams(paramStr.substring(index2 + 1, nestedParamsEndIndex));
      Iterator itr = subTypes.iterator();

      while(itr.hasNext()) {
         TypeParameter subArg = (TypeParameter)itr.next();
         paramType.addArgument(subArg);
      }

      return paramType;
   }

   private static int computeEndofTypeParam(String str, int startIndex) {
      int[] depth = new int[]{0};
      return computeEndofTypeParam(str, startIndex, depth);
   }

   private static int computeEndofTypeParam(String str, int startIndex, int[] depth) {
      int index1 = str.indexOf("<", startIndex);

      int index2;
      int arrIndex;
      for(index2 = str.indexOf(">", startIndex); index1 >= 0 && index2 >= 0 && index1 < index2; index2 = str.indexOf(">", arrIndex)) {
         int var10002 = depth[0]++;
         arrIndex = computeEndofTypeParam(str, index1 + 1, depth);
         var10002 = depth[0]--;
         if (depth[0] > 0) {
            ++arrIndex;
         }

         index1 = str.indexOf("<", arrIndex);
      }

      if (depth[0] == 0) {
         arrIndex = str.indexOf("[]", index2);
         if (arrIndex > index2) {
            int commaIndex = str.indexOf(",", index2);
            if (commaIndex > 0) {
               if (arrIndex < commaIndex) {
                  index2 = arrIndex + 1;
               }
            } else {
               index2 = arrIndex + 1;
            }
         }
      }

      return index2;
   }

   private static void debug(String str) {
      if (DEBUG) {
         debugLogger.debug("[GenericMethodDescriptor] " + str);
      }

   }

   private static String getActualClassName(String name, ClassLoader classLoader) {
      UnsyncStringBuffer sb = new UnsyncStringBuffer();
      sb.append(name);
      Class c = null;
      if (classLoader != null) {
         try {
            c = classLoader.loadClass(name);
         } catch (ClassNotFoundException var6) {
         }
      }

      if (c == null) {
         try {
            c = Class.forName(name);
         } catch (ClassNotFoundException var5) {
         }
      }

      if (c != null) {
         for(Class dc = c.getDeclaringClass(); dc != null; dc = dc.getDeclaringClass()) {
            sb.setCharAt(dc.getName().length(), '.');
         }
      }

      return sb.toString();
   }

   public static void main(String[] args) {
      String s1 = "updateObject(java.lang.String,java.util.Map<de.quanteam.ejb.OurEntity,  java.util.Map<de.quanteam.ejb.OurEntity,de.quanteam.ejb.OurEntity>>)";
      String s2 = "updateObject(int, String,Map<K1,Map<K2,V2>>,Map<K3,V3>)";
      String s3 = "updateObject(int, String,Map<K1,Map<K2,int,Map<k3,V3>,String,Map<k4,V4>>,String,Map<>>,Map<K5,V5>,long)";
      String s4 = "updateObject(Map<k,? extends String>)";
      String s5 = "updateObject(List<List<String>[]>[], Map<K2,V2>)";
      print(s5);
      print(s1);
      print(s2);
      print(s3);
      print(s4);
   }

   public static void print(String s) {
      debugLogger.debug("Method=" + s + " signature=" + computeGenericMethodSignature(s));
   }

   static {
      if (!KernelStatus.isApplet()) {
         DEBUG = Boolean.getBoolean("weblogic.debug.DebugGenericMethodDescriptor") && debugLogger.isDebugEnabled();
      }

   }

   private static class TypeParameter {
      String name;
      ArrayList arguments = new ArrayList();

      TypeParameter(String type) {
         int index = type.indexOf("[]");
         if (index > 0) {
            String actualType = GenericMethodDescriptor.getActualClassName(type.substring(0, index), Thread.currentThread().getContextClassLoader());
            this.name = actualType + "[]";
         } else {
            this.name = GenericMethodDescriptor.getActualClassName(type, Thread.currentThread().getContextClassLoader());
         }

         GenericMethodDescriptor.debug("Added new parameter=" + this.name);
      }

      private void addArgument(TypeParameter arg) {
         this.arguments.add(arg);
         GenericMethodDescriptor.debug("Added new argument=" + arg + " to parameter=" + this.name);
      }

      private String computeParamSignature(String param) {
         UnsyncStringBuffer sb = new UnsyncStringBuffer();
         int index = param.indexOf("[]");
         boolean paramIsArray = index > 0;
         if (paramIsArray) {
            sb.append(this.computeArrayDimensions(param, index));
            param = param.substring(0, index);
         }

         if (param.equals("byte")) {
            sb.append('B');
         } else if (param.equals("char")) {
            sb.append('C');
         } else if (param.equals("double")) {
            sb.append('D');
         } else if (param.equals("float")) {
            sb.append('F');
         } else if (param.equals("int")) {
            sb.append('I');
         } else if (param.equals("long")) {
            sb.append('J');
         } else if (param.equals("short")) {
            sb.append('S');
         } else if (param.equals("boolean")) {
            sb.append('Z');
         } else {
            sb.append('L').append(param);
         }

         return sb.toString();
      }

      private String computeArrayDimensions(String paramType, int index) {
         UnsyncStringBuffer arrayParams = new UnsyncStringBuffer();
         String param = paramType.substring(index + 1);
         arrayParams.append('[');

         for(index = param.indexOf("[]"); index > 0; index = param.indexOf("[]")) {
            arrayParams.append('[');
            param = param.substring(index + 1);
         }

         return arrayParams.toString();
      }

      public String toString() {
         UnsyncStringBuffer sb = new UnsyncStringBuffer();
         boolean isObject = true;
         String sign = this.computeParamSignature(this.name);
         int index = sign.indexOf(76);
         if (index == 0) {
            isObject = true;
         } else if (index > 0) {
            for(int i = 0; i < index; ++i) {
               if (sign.charAt(i) != '[') {
                  isObject = false;
                  break;
               }
            }
         } else {
            isObject = false;
         }

         sb.append(sign);
         if (this.arguments.size() > 0) {
            sb.append("<");
            Iterator itr = this.arguments.iterator();

            while(itr.hasNext()) {
               TypeParameter arg = (TypeParameter)itr.next();
               sb.append(arg.toString());
            }

            sb.append(">");
         }

         if (isObject) {
            sb.append(";");
         }

         return sb.toString();
      }
   }
}
