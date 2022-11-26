package weblogic.diagnostics.instrumentation.engine.base;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.objectweb.asm.Type;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;

class ClassInfo implements Cloneable {
   private boolean valid;
   static final int NOT_BEING_INSTRUMENTED = 0;
   static final int BEING_INSTRUMENTED = 1;
   static final int POST_INSTRUMENTATION = 2;
   private volatile int instrumentationState = 0;
   private long instrumentationEndTime = 0L;
   private boolean is_interface;
   private String className;
   private String superClassName;
   private String[] interfaceNames;
   private String sourceFileName;
   private WeakReference classLoaderRef;
   private List classAnnotations;
   private String outerClassName;
   private String outerMethodName;
   private String outerMethodDesc;
   private InstrumentorEngineBase engine;
   private List matchEntries;
   private String urlSource;

   ClassInfo() {
   }

   ClassInfo(Class loadedClazz) {
      if (loadedClazz != null) {
         this.classLoaderRef = new WeakReference(loadedClazz.getClassLoader());
         this.is_interface = loadedClazz.isInterface();
         this.className = loadedClazz.getName().replace('.', '/');
         Class superClass = loadedClazz.getSuperclass();
         if (superClass != null) {
            this.superClassName = superClass.getName().replace('.', '/');
         } else if (this.is_interface) {
            this.superClassName = "java/lang/Object";
         }

         Class[] interfaces = loadedClazz.getInterfaces();
         if (interfaces != null) {
            this.interfaceNames = new String[interfaces.length];

            for(int i = 0; i < interfaces.length; ++i) {
               this.interfaceNames[i] = interfaces[i].getName().replace('.', '/');
            }
         }

         if (!this.is_interface) {
            Annotation[] annotations = loadedClazz.getAnnotations();
            if (annotations != null && annotations.length > 0) {
               this.classAnnotations = new ArrayList(annotations.length);

               for(int i = 0; i < annotations.length; ++i) {
                  this.classAnnotations.add(Type.getDescriptor(annotations[i].annotationType()));
               }
            }
         }

         Class enclosingClazz = loadedClazz.getEnclosingClass();
         if (enclosingClazz != null) {
            this.outerClassName = enclosingClazz.getName().replace('.', '/');
         }

         Method enclosingMethod = loadedClazz.getEnclosingMethod();
         if (enclosingMethod != null) {
            this.outerMethodName = enclosingMethod.getName();
            this.outerMethodDesc = Type.getMethodDescriptor(enclosingMethod);
         }

         this.valid = true;
      }
   }

   boolean isInterface() {
      return this.is_interface;
   }

   void setInterface(boolean is_interface) {
      this.is_interface = is_interface;
   }

   String getClassName() {
      return this.className;
   }

   void setClassName(String className) {
      this.className = className;
   }

   String getSuperClassName() {
      return this.superClassName;
   }

   void setSuperClassName(String superClassName) {
      this.superClassName = superClassName;
   }

   String[] getInterfaceNames() {
      return this.interfaceNames;
   }

   void setInterfaceNames(String[] interfaceNames) {
      this.interfaceNames = interfaceNames;
   }

   String getSourceFileName() {
      return this.sourceFileName;
   }

   void setSourceFileName(String sourceFileName) {
      this.sourceFileName = sourceFileName;
   }

   boolean isValid() {
      return this.valid;
   }

   void setValid(boolean valid) {
      this.valid = valid;
   }

   boolean isUnSafeToPurge(long timestamp, boolean ignoreGracePeriod) {
      if (this.instrumentationState == 0) {
         return false;
      } else if (this.instrumentationState == 1) {
         return true;
      } else {
         long elapsed = timestamp - this.instrumentationEndTime;
         if (elapsed >= InstrumentorEngineBase.getClassInfoCacheGracePeriodMillis()) {
            this.instrumentationState = 0;
            return false;
         } else {
            return !ignoreGracePeriod;
         }
      }
   }

   void markStartBeingInstrumented() {
      this.instrumentationState = 1;
   }

   void markEndBeingInstrumented() {
      this.instrumentationEndTime = System.currentTimeMillis();
      this.instrumentationState = 2;
   }

   ClassLoader getClassLoader() {
      ClassLoader cl = (ClassLoader)((ClassLoader)(this.classLoaderRef != null ? this.classLoaderRef.get() : null));
      return cl;
   }

   void setClassLoader(ClassLoader cl) {
      this.classLoaderRef = new WeakReference(cl);
   }

   List getClassAnnotations() {
      return this.classAnnotations;
   }

   void setClassAnnotations(List classAnnotations) {
      this.classAnnotations = classAnnotations;
   }

   public String getOuterClassName() {
      return this.outerClassName;
   }

   public void setOuterClassName(String outerClassName) {
      this.outerClassName = outerClassName;
   }

   public String getOuterMethodName() {
      return this.outerMethodName;
   }

   public void setOuterMethodName(String outerMethodName) {
      this.outerMethodName = outerMethodName;
   }

   public String getOuterMethodDesc() {
      return this.outerMethodDesc;
   }

   public void setOuterMethodDesc(String outerMethodDesc) {
      this.outerMethodDesc = outerMethodDesc;
   }

   synchronized void enterMatch(String pattern, boolean match) {
      if (this.matchEntries == null) {
         this.matchEntries = new ArrayList(4);
      }

      Iterator var3 = this.matchEntries.iterator();

      MatchEntry entry;
      do {
         if (!var3.hasNext()) {
            this.matchEntries.add(new MatchEntry(pattern, match));
            return;
         }

         entry = (MatchEntry)var3.next();
      } while(!pattern.equals(entry.pattern));

   }

   synchronized MatchEntry getMatchEntry(String pattern) {
      if (this.matchEntries == null) {
         return null;
      } else {
         Iterator var2 = this.matchEntries.iterator();

         MatchEntry entry;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            entry = (MatchEntry)var2.next();
         } while(!pattern.equals(entry.pattern));

         return entry;
      }
   }

   public boolean isAssignableFrom(ClassInfo clazzInfo, ClassLoader instrumentingLoader) {
      if (clazzInfo == null) {
         if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("isAssignableFrom was called with a null ClassInfo argument");
         }

         throw new NullPointerException("class supplied to isAssignableFrom may not be null");
      } else {
         ClassLoader cl1;
         ClassLoader cl2;
         if (ClassInstrumentor.isTargetedDebugEnabled() && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED: isAssignableFrom: " + this.className + ", " + clazzInfo.className);
            cl1 = this.getClassLoader();
            cl2 = clazzInfo.getClassLoader();
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED:     cl1: " + (cl1 == null ? "boostrap classloader" : cl1.getClass().getName() + "@" + cl1.hashCode()));
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED:     cl2: " + (cl2 == null ? "boostrap classloader" : cl2.getClass().getName() + "@" + cl2.hashCode()));
         }

         if (this.valid && clazzInfo.isValid()) {
            cl1 = this.getClassLoader();
            cl2 = clazzInfo.getClassLoader();
            if (!this.equals(clazzInfo) && (!this.className.equals(clazzInfo.getClassName()) || cl1 != cl2 && !this.engine.isParentClassloader(cl1, cl2) && !this.engine.isParentClassloader(cl2, cl1))) {
               if (clazzInfo.getSuperClassName() != null) {
                  ClassInfo clazzSuperClassInfo = clazzInfo.getSuperClassInfo();
                  if (ClassInstrumentor.isTargetedDebugEnabled() && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED: isAssignableFrom check superclass");
                  }

                  if (this.isAssignableFrom(clazzSuperClassInfo, instrumentingLoader)) {
                     return true;
                  }
               }

               String[] clazzInterfaces = clazzInfo.getInterfaceNames();
               if (clazzInterfaces != null) {
                  if (ClassInstrumentor.isTargetedDebugEnabled() && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED: isAssignableFrom checking interfaces (" + clazzInterfaces.length + ") for " + this.className);
                  }

                  for(int i = 0; i < clazzInterfaces.length; ++i) {
                     if (ClassInstrumentor.isTargetedDebugEnabled() && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                        InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED: isAssignableFrom get classinfo for interface to do nested isAssignableFrom: " + clazzInterfaces[i] + ", using CL " + (instrumentingLoader == null ? "boostrap classloader" : instrumentingLoader.getClass().getName() + "@" + instrumentingLoader.hashCode()));
                     }

                     ClassInfo interfaceInfo = this.engine.getClassInfo(clazzInterfaces[i], instrumentingLoader);
                     if (this.isAssignableFrom(interfaceInfo, instrumentingLoader)) {
                        return true;
                     }
                  }
               }

               return false;
            } else {
               if (ClassInstrumentor.isTargetedDebugEnabled() && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED: isAssignableFrom exact match");
               }

               return true;
            }
         } else {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("isAssignableFrom was called with at least one invalid ClassInfo, returning false:\n  " + this.toString() + "\n  :\n  " + clazzInfo.toString());
            }

            return false;
         }
      }
   }

   void setEngine(InstrumentorEngineBase engine) {
      this.engine = engine;
   }

   InstrumentorEngineBase getEngine() {
      return this.engine;
   }

   void setURLSource(String urlSource) {
      this.urlSource = urlSource;
   }

   String getURLSource() {
      return this.urlSource;
   }

   ClassInfo getSuperClassInfo() {
      if (this.superClassName == null) {
         throw new RuntimeException("No superclass for ClassInfo: " + this.className);
      } else {
         return this.engine.getClassInfo(this.superClassName, this.getClassLoader());
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("ClassInfo:").append("\n   valid=").append(this.valid).append("\n   name=").append(this.className).append("\n   super=").append(this.superClassName).append("\n   outerClass=").append(this.outerClassName).append("\n   outerMethod=").append(this.outerMethodName).append("\n   outerMethodDesc=").append(this.outerMethodDesc).append("\n   interfaces=");
      int i;
      if (this.interfaceNames == null) {
         buf.append("null");
      } else {
         for(i = 0; i < this.interfaceNames.length; ++i) {
            if (i > 0) {
               buf.append(",");
            }

            buf.append(this.interfaceNames[i]);
         }
      }

      buf.append("\n   annotations=");
      if (this.classAnnotations == null) {
         buf.append("null");
      } else {
         for(i = 0; i < this.classAnnotations.size(); ++i) {
            if (i > 0) {
               buf.append(",");
            }

            buf.append((String)this.classAnnotations.get(i));
         }
      }

      return buf.toString();
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   int getInstrumentationState() {
      return this.instrumentationState;
   }

   static boolean compareClassInfoFields(ClassInfo infoA, ClassInfo infoB) {
      if (infoA != null && infoB != null) {
         if (infoA.valid != infoB.valid) {
            return false;
         } else if (!compareStrings(infoA.getClassName(), infoB.getClassName())) {
            return false;
         } else if (infoA.isInterface() != infoB.isInterface()) {
            return false;
         } else if (!compareStrings(infoA.getSuperClassName(), infoB.getSuperClassName())) {
            return false;
         } else if (!compareStrings(infoA.getOuterClassName(), infoB.getOuterClassName())) {
            return false;
         } else if (!compareStrings(infoA.getOuterMethodName(), infoB.getOuterMethodName())) {
            return false;
         } else if (!compareStrings(infoA.getOuterMethodDesc(), infoB.getOuterMethodDesc())) {
            return false;
         } else if (!compareStringArray(infoA.getInterfaceNames(), infoB.getInterfaceNames())) {
            return false;
         } else {
            return compareListString(infoA.getClassAnnotations(), infoB.getClassAnnotations());
         }
      } else {
         return infoA == infoB;
      }
   }

   private static boolean compareStrings(String strA, String strB) {
      if (strA != null && strB != null) {
         return strA.equals(strB);
      } else {
         return strA == strB;
      }
   }

   private static boolean compareListString(List listA, List listB) {
      if (listA != null && listB != null) {
         if (listA.size() != listB.size()) {
            return false;
         } else {
            return listA.containsAll(listB) && listB.containsAll(listA);
         }
      } else {
         return listA == listB;
      }
   }

   private static boolean compareStringArray(String[] arrayA, String[] arrayB) {
      if (arrayA != null && arrayB != null) {
         return compareListString(Arrays.asList(arrayA), Arrays.asList(arrayB));
      } else {
         return arrayA == arrayB;
      }
   }

   static class MatchEntry {
      String pattern;
      boolean matched;

      MatchEntry(String pattern, boolean matched) {
         this.pattern = pattern;
         this.matched = matched;
      }
   }
}
