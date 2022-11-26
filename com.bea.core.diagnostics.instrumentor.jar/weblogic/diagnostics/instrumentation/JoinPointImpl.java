package weblogic.diagnostics.instrumentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.GenericClassLoader;

public class JoinPointImpl implements JoinPoint {
   protected String moduleName;
   protected String source;
   protected String className;
   protected String methodName;
   protected String methodDesc;
   protected int lineNum;
   protected String callerClassName;
   protected String callerMethodName;
   protected String callerMethodDesc;
   protected Map pointcutHandlingInfoMap;
   protected Map gatheredArgumentsMap;
   protected boolean isStatic;

   public JoinPointImpl() {
   }

   public JoinPointImpl(Class joinpointClass, String source, String className, String methodName, String methodDesc, int lineNum, Map pointcutHandlingInfoMap, boolean isStatic) {
      this((Class)joinpointClass, source, className, methodName, methodDesc, lineNum, (String)null, (String)null, (String)null, pointcutHandlingInfoMap, isStatic);
   }

   public JoinPointImpl(Class joinpointClass, String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map pointcutHandlingInfoMap, boolean isStatic) {
      this((ClassLoader)joinpointClass.getClassLoader(), source, className, methodName, methodDesc, lineNum, (String)null, (String)null, (String)null, pointcutHandlingInfoMap, isStatic);
   }

   public JoinPointImpl(ClassLoader loader, String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map pointcutHandlingInfoMap, boolean isStatic) {
      if (source == null) {
         source = "";
      }

      if (className == null) {
         className = "";
      }

      if (methodName == null) {
         methodName = "";
      }

      if (methodDesc == null) {
         methodDesc = "";
      }

      if (callerClassName == null) {
         callerClassName = "";
      }

      if (callerMethodName == null) {
         callerMethodName = "";
      }

      if (callerMethodDesc == null) {
         callerMethodDesc = "";
      }

      this.moduleName = this.identifyModuleName(loader);
      this.source = source;
      this.className = className;
      this.methodName = methodName;
      this.methodDesc = methodDesc;
      this.lineNum = lineNum;
      this.callerClassName = callerClassName;
      this.callerMethodName = callerMethodName;
      this.callerMethodDesc = callerMethodDesc;
      this.pointcutHandlingInfoMap = pointcutHandlingInfoMap;
      this.isStatic = isStatic;
   }

   private String identifyModuleName(ClassLoader loader) {
      if (loader == null) {
         return "";
      } else {
         for(ClassLoader cl = loader; cl != null; cl = cl.getParent()) {
            if (!(cl instanceof GenericClassLoader)) {
               return "";
            }

            GenericClassLoader gcl = (GenericClassLoader)cl;
            Annotation anno = gcl.getAnnotation();
            if (anno != null) {
               String modName = anno.getModuleName();
               if (modName != null) {
                  return modName;
               }
            }
         }

         return "";
      }
   }

   public JoinPointImpl(String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map pointcutHandlingInfoMap, boolean isStatic) {
      this((ClassLoader)null, source, className, methodName, methodDesc, lineNum, callerClassName, callerMethodName, callerMethodDesc, pointcutHandlingInfoMap, isStatic);
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getSourceFile() {
      return this.source;
   }

   public String getClassName() {
      return this.className;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public String getMethodDescriptor() {
      return this.methodDesc;
   }

   public int getLineNumber() {
      return this.lineNum;
   }

   public String getCallerClassName() {
      return this.callerClassName;
   }

   public String getCallerMethodName() {
      return this.callerMethodName;
   }

   public String getCallerMethodDescriptor() {
      return this.callerMethodDesc;
   }

   public boolean isReturnGathered(String monitorType) {
      if (this.pointcutHandlingInfoMap != null && monitorType != null) {
         PointcutHandlingInfo info = (PointcutHandlingInfo)this.pointcutHandlingInfoMap.get(monitorType);
         if (info == null) {
            return false;
         } else {
            ValueHandlingInfo returnInfo = info.getReturnValueHandlingInfo();
            return returnInfo == null ? false : returnInfo.isGathered();
         }
      } else {
         return false;
      }
   }

   public GatheredArgument[] getGatheredArguments(String monitorType) {
      if (this.pointcutHandlingInfoMap != null && monitorType != null) {
         GatheredArgument[] gatheredArgs;
         synchronized(this) {
            if (this.gatheredArgumentsMap != null) {
               gatheredArgs = (GatheredArgument[])this.gatheredArgumentsMap.get(monitorType);
               if (gatheredArgs != null) {
                  return gatheredArgs;
               }
            }
         }

         PointcutHandlingInfo info = (PointcutHandlingInfo)this.pointcutHandlingInfoMap.get(monitorType);
         if (info == null) {
            return null;
         } else {
            ValueHandlingInfo classInfo = info.getClassValueHandlingInfo();
            ValueHandlingInfo[] argInfos = info.getArgumentValueHandlingInfo();
            if (argInfos == null && classInfo == null) {
               return null;
            } else {
               ArrayList gatheredArray = new ArrayList();
               if (classInfo != null && classInfo.isGathered()) {
                  gatheredArray.add(new GatheredArgumentImpl(classInfo.getName(), 0));
               }

               if (argInfos != null) {
                  int offset = this.isStatic ? 0 : 1;

                  for(int i = 0; i < argInfos.length; ++i) {
                     if (argInfos[i] != null && argInfos[i].isGathered()) {
                        gatheredArray.add(new GatheredArgumentImpl(argInfos[i].getName(), i + offset));
                     }
                  }
               }

               if (gatheredArray.size() == 0) {
                  return null;
               } else {
                  gatheredArgs = new GatheredArgument[gatheredArray.size()];
                  gatheredArgs = (GatheredArgument[])gatheredArray.toArray(gatheredArgs);
                  synchronized(this) {
                     if (this.gatheredArgumentsMap == null) {
                        this.gatheredArgumentsMap = new HashMap();
                        this.gatheredArgumentsMap.put(monitorType, gatheredArgs);
                     } else if (!this.gatheredArgumentsMap.containsKey(monitorType)) {
                        this.gatheredArgumentsMap.put(monitorType, gatheredArgs);
                     }

                     return gatheredArgs;
                  }
               }
            }
         }
      } else {
         return null;
      }
   }

   public Map getPointcutHandlingInfoMap() {
      return this.pointcutHandlingInfoMap;
   }

   public boolean isStatic() {
      return this.isStatic;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && o instanceof JoinPointImpl) {
         JoinPointImpl oJp = (JoinPointImpl)o;
         return this.lineNum == oJp.lineNum && this.source.equals(oJp.source) && this.className.equals(oJp.className) && this.methodName.equals(oJp.methodName) && this.methodDesc.equals(oJp.methodDesc) && this.isStatic == oJp.isStatic && (this.pointcutHandlingInfoMap == oJp.pointcutHandlingInfoMap || this.pointcutHandlingInfoMap != null && this.pointcutHandlingInfoMap.equals(oJp.pointcutHandlingInfoMap));
      } else {
         return false;
      }
   }

   public int hashCode() {
      int retVal = this.lineNum;
      retVal += 31 * this.source.hashCode();
      retVal += 31 * this.className.hashCode();
      retVal += 31 * this.methodName.hashCode();
      retVal += 31 * this.methodDesc.hashCode();
      return retVal;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("className=");
      buf.append(this.className);
      buf.append(", methodName=");
      buf.append(this.methodName);
      buf.append(", methodDesc=");
      buf.append(this.methodDesc);
      buf.append(", source=");
      buf.append(this.source);
      buf.append(", lineNum=");
      buf.append(this.lineNum);
      buf.append(", pointcutHandlingInfoMap=");
      buf.append(PointcutHandlingInfoImpl.mapToString(this.pointcutHandlingInfoMap));
      return new String(buf);
   }
}
