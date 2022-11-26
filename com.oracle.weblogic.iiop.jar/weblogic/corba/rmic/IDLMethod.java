package weblogic.corba.rmic;

import weblogic.iiop.IDLUtils;
import weblogic.iiop.Utils;
import weblogic.utils.compiler.CodeGenerationException;

public final class IDLMethod implements Cloneable {
   Class m_class = null;
   String m_mangledName = null;
   String m_name = null;
   Class m_returnType = null;
   Class[] m_parameterTypes = null;
   Class[] m_exceptionTypes = null;

   public IDLMethod(Class cl, String name, String mangledName, Class returnType, Class[] parameterTypes, Class[] exceptionTypes) {
      this.m_class = cl;
      this.m_name = name;
      this.m_mangledName = mangledName;
      this.m_returnType = returnType;
      this.m_parameterTypes = parameterTypes;
      this.m_exceptionTypes = exceptionTypes;
   }

   public String getName() {
      return this.m_name;
   }

   public Class getReturnType() {
      return this.m_returnType;
   }

   public Class[] getExceptionTypes() {
      return this.m_exceptionTypes;
   }

   public Class[] getParameterTypes() {
      return this.m_parameterTypes;
   }

   public String getMangledName() {
      return this.m_mangledName;
   }

   public boolean isRequired() throws CodeGenerationException {
      int i;
      if (IDLOptions.getNoAbstract()) {
         if (Utils.isAbstractInterface(this.m_returnType)) {
            return false;
         }

         for(i = 0; i < this.m_parameterTypes.length; ++i) {
            if (Utils.isAbstractInterface(this.m_parameterTypes[i])) {
               return false;
            }
         }

         for(i = 0; i < this.m_exceptionTypes.length; ++i) {
            if (Utils.isAbstractInterface(this.m_exceptionTypes[i])) {
               return false;
            }
         }
      } else if (IDLOptions.getNoValueTypes()) {
         if (IDLUtils.isValueType(this.m_returnType)) {
            return false;
         }

         for(i = 0; i < this.m_parameterTypes.length; ++i) {
            if (IDLUtils.isValueType(this.m_parameterTypes[i])) {
               return false;
            }
         }

         for(i = 0; i < this.m_exceptionTypes.length; ++i) {
            if (IDLUtils.isValueType(this.m_exceptionTypes[i])) {
               return false;
            }
         }
      }

      return true;
   }

   public String toIDL() {
      String result = null;
      String methodName = this.m_mangledName;
      StringBuffer sb = new StringBuffer();
      if (null != this.m_returnType) {
         sb.append(IDLUtils.getIDLType(this.m_returnType)).append(" ");
      }

      sb.append(methodName).append("(");

      for(int i = 0; i < this.m_parameterTypes.length; ++i) {
         if (i > 0) {
            sb.append(", ");
         }

         sb.append(" in ").append(IDLUtils.getIDLType(this.m_parameterTypes[i])).append(" arg").append(i);
      }

      sb.append(")");
      Class[] ea = this.m_exceptionTypes;
      switch (ea.length) {
         case 0:
            break;
         case 1:
            if (IDLUtils.isACheckedException(ea[0])) {
               String exName = IDLUtils.exceptionToEx(IDLUtils.getIDLType(ea[0]));
               sb.append(" raises (").append(exName).append(")");
            }
            break;
         default:
            boolean addComma = false;

            for(int j = 0; j < ea.length; ++j) {
               if (IDLUtils.isACheckedException(ea[j])) {
                  if (addComma) {
                     sb.append(",");
                  } else {
                     sb.append(" raises (");
                  }

                  String exName = IDLUtils.exceptionToEx(IDLUtils.getIDLType(ea[j]));
                  sb.append(exName);
                  addComma = true;
               }
            }

            if (addComma) {
               sb.append(")");
            }
      }

      sb.append(";");
      result = sb.toString();
      return result;
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }
}
