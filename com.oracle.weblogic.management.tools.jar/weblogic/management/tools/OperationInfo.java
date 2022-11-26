package weblogic.management.tools;

import java.lang.reflect.Method;
import java.util.Locale;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import weblogic.management.info.ExtendedOperationInfo;
import weblogic.utils.reflect.UniqueMethod;

class OperationInfo extends MBeanOperationInfo implements ExtendedOperationInfo {
   private static final long serialVersionUID = 6938333905828247476L;
   String methodClassName = null;
   private String parameterString = "";
   private String parameterNames = "";
   private String exceptionString = "";
   private String[] legalChecks = null;
   private String[] legalResponses = null;
   private transient Method method = null;
   private boolean dynamic = false;

   public OperationInfo(String description, Method method, boolean dynamic, String[] legalChecks, String[] legalResponses) {
      super(description, method);
      this.dynamic = dynamic;
      this.method = method;
      this.methodClassName = method.getDeclaringClass().getName();
      this.legalChecks = legalChecks;
      this.legalResponses = legalResponses;
      this.initialize();
   }

   private static String getType(String type) {
      if (type.startsWith("[L")) {
         StringBuffer buffer = new StringBuffer(type);
         buffer.setLength(buffer.length() + 1);
         buffer.setCharAt(buffer.length() - 2, '[');
         buffer.setCharAt(buffer.length() - 1, ']');
         buffer = buffer.reverse();
         buffer.setLength(buffer.length() - 2);
         buffer = buffer.reverse();
         return buffer.toString();
      } else {
         return type;
      }
   }

   private static String getParamName(String type, int count) {
      if (type.endsWith("[]")) {
         type = type.substring(0, type.length() - 2);
      }

      int index = type.lastIndexOf(46);
      if (index > -1) {
         type = type.substring(index + 1, type.length());
      }

      type = type.substring(0, 1).toLowerCase(Locale.US) + type.substring(1, type.length());
      return "_wl_" + type + "_" + count;
   }

   public Method getMethod() {
      if (this.method != null) {
         return this.method;
      } else {
         try {
            Class cls = Class.forName(this.methodClassName);
            MBeanParameterInfo[] signature = this.getSignature();
            Class[] params = new Class[signature.length];

            for(int i = 0; i < signature.length; ++i) {
               params[i] = AttributeInfo.Helper.findClass(signature[i].getType());
            }

            this.method = UniqueMethod.intern(cls.getMethod(this.name, params));
            return this.method;
         } catch (ClassNotFoundException var5) {
            var5.printStackTrace();
            throw new RuntimeException("error deserializing OperationInfo " + this.name);
         } catch (Exception var6) {
            var6.printStackTrace();
            throw new RuntimeException("error deserializing OperationInfo " + this.name);
         }
      }
   }

   public String getParameterString() {
      return this.parameterString;
   }

   public String getParameterNames() {
      return this.parameterNames;
   }

   public String getExceptionString() {
      return this.exceptionString;
   }

   public String getReturnType() {
      return getType(this.getMethod().getReturnType().getName());
   }

   public boolean isDynamic() {
      return this.dynamic;
   }

   public String getLegalCheck() {
      return this.legalChecks != null && this.legalChecks.length > 0 ? this.legalChecks[0] : null;
   }

   public String[] getLegalChecks() {
      return this.legalChecks;
   }

   public String[] getLegalResponses() {
      return this.legalResponses;
   }

   public String getLegalResponse() {
      return this.legalResponses != null && this.legalResponses.length > 0 ? this.legalResponses[0] : null;
   }

   public String toString() {
      String result = super.toString();
      result = result + ", dynamic=" + this.dynamic;
      return result;
   }

   private void initialize() {
      Class[] parameterTypes = this.getMethod().getParameterTypes();

      for(int i = 0; i < parameterTypes.length; ++i) {
         String parameterType = getType(parameterTypes[i].getName());
         String parameterName = "value";
         if (parameterTypes.length > 1) {
            parameterName = getParamName(parameterType, i);
         }

         if (this.parameterString.length() > 0) {
            this.parameterString = this.parameterString + ", ";
         }

         this.parameterString = this.parameterString + parameterType + " " + parameterName;
         this.parameterNames = this.parameterNames + parameterName;
         if (i < parameterTypes.length - 1) {
            this.parameterNames = this.parameterNames + ", ";
         }
      }

      Class[] exceptionTypes = this.getMethod().getExceptionTypes();

      for(int i = 0; i < exceptionTypes.length; ++i) {
         if (this.exceptionString.length() > 0) {
            this.exceptionString = this.exceptionString + ",";
         } else {
            this.exceptionString = "\n    throws ";
         }

         this.exceptionString = this.exceptionString + exceptionTypes[i].getName();
      }

   }
}
