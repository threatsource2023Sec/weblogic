package com.sun.faces.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSFVersionTracker implements Serializable {
   private static Version DEFAULT_VERSION = new Version(1, 2);
   private Map grammarToVersionMap = null;
   private List versionStack;
   private Map trackedClasses;

   private Map getGrammarToVersionMap() {
      if (null == this.grammarToVersionMap) {
         this.grammarToVersionMap = new HashMap(6);
         this.grammarToVersionMap.put("web-facesconfig_1_0.dtd", new Version(1, 0));
         this.grammarToVersionMap.put("web-facesconfig_1_1.dtd", new Version(1, 1));
         this.grammarToVersionMap.put("web-facesconfig_1_2.xsd", new Version(1, 2));
      }

      return this.grammarToVersionMap;
   }

   private List getVersionStack() {
      if (null == this.versionStack) {
         this.versionStack = new ArrayList() {
            public String toString() {
               StringBuilder result = new StringBuilder();
               Iterator i$ = this.iterator();

               while(i$.hasNext()) {
                  Version cur = (Version)i$.next();
                  if (null == cur) {
                     result.append("null\n");
                  } else {
                     result.append(cur.toString()).append('\n');
                  }
               }

               return result.toString();
            }
         };
      }

      return this.versionStack;
   }

   Version popJSFVersionNumber() {
      List stack = this.getVersionStack();

      assert null != stack;

      Version result = null;
      int end = stack.size() - 1;

      int nonNull;
      for(nonNull = end; nonNull >= 0 && null == (result = (Version)stack.get(nonNull)); --nonNull) {
      }

      if (null != result) {
         for(int j = end; j >= nonNull; --j) {
            stack.remove(stack.size() - 1);
         }
      }

      return result;
   }

   Version peekJSFVersionNumber() {
      List stack = this.getVersionStack();

      assert null != stack;

      Version result = null;

      for(int i = stack.size() - 1; i >= 0 && null == (result = (Version)stack.get(i)); --i) {
      }

      return result;
   }

   private Map getTrackedClassMap() {
      if (null == this.trackedClasses) {
         this.trackedClasses = new HashMap() {
            public String toString() {
               StringBuilder result = new StringBuilder();
               Iterator i$ = this.entrySet().iterator();

               while(i$.hasNext()) {
                  Map.Entry cur = (Map.Entry)i$.next();
                  Version curVersion = (Version)cur.getValue();
                  result.append(cur).append(": ");
                  result.append(curVersion.toString()).append('\n');
               }

               return result.toString();
            }
         };
      }

      return this.trackedClasses;
   }

   void startParse() {
   }

   void endParse() {
      this.popJSFVersionNumber();
   }

   String pushJSFVersionNumberFromGrammar(String grammar) {
      Map map = this.getGrammarToVersionMap();
      List stack = this.getVersionStack();

      assert null != map;

      assert null != stack;

      stack.add(map.get(grammar));
      return grammar;
   }

   void putTrackedClassName(String fqcn) {
      Version version = this.peekJSFVersionNumber();
      if (null == version) {
         version = DEFAULT_VERSION;
      }

      this.getTrackedClassMap().put(fqcn, version);
   }

   public Version getVersionForTrackedClassName(String fqcn) {
      return (Version)this.getTrackedClassMap().get(fqcn);
   }

   public Version getCurrentVersion() {
      return DEFAULT_VERSION;
   }

   public static final class Version implements Comparable {
      private int majorVersion;
      private int minorVersion;

      public int getMajorVersion() {
         return this.majorVersion;
      }

      public void setMajorVersion(int majorVersion) {
         this.majorVersion = majorVersion;
      }

      public int getMinorVersion() {
         return this.minorVersion;
      }

      public void setMinorVersion(int minorVersion) {
         this.minorVersion = minorVersion;
      }

      public int compareTo(Object obj) {
         Version other = (Version)obj;
         int result;
         int thisMajor;
         int otherMajor;
         if ((thisMajor = this.getMajorVersion()) < (otherMajor = other.getMajorVersion())) {
            result = -1;
         } else {
            assert thisMajor >= otherMajor;

            if (thisMajor == otherMajor) {
               int thisMinor;
               int otherMinor;
               if ((thisMinor = this.getMinorVersion()) < (otherMinor = other.getMinorVersion())) {
                  result = -1;
               } else {
                  assert thisMinor >= otherMinor;

                  result = thisMinor == otherMinor ? 1 : 0;
               }
            } else {
               assert thisMajor > otherMajor;

               result = 1;
            }
         }

         return result;
      }

      public Version(int majorVersion, int minorVersion) {
         this.setMajorVersion(majorVersion);
         this.setMinorVersion(minorVersion);
      }

      public String toString() {
         return String.valueOf(this.getMajorVersion()) + '.' + this.getMinorVersion();
      }
   }
}
