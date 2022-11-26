package javax.security.jacc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

final class HttpMethodSpec {
   private static final String comma = ",";
   private static final String emptyString = "";
   private static final String exclaimationPoint = "!";
   private static final char exclaimationPointChar = '!';
   private static String[] methodKeys = new String[]{"DELETE", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE"};
   private static int mapSize;
   private static HashMap methodHash;
   private static int allSet;
   private static HttpMethodSpec[] specArray;
   private static HttpMethodSpec[] exceptionSpecArray;
   private static HttpMethodSpec allSpec;
   private static List extensionMethods;
   HttpMethodSpec standardSpec;
   boolean exceptionList;
   int standardMap;
   BitSet extensionSet;
   String actions;

   static HttpMethodSpec getSpec(String actions) {
      HttpMethodSpec spec;
      if (actions != null && !actions.equals("")) {
         BitSet set = new BitSet();
         spec = getStandardSpec(actions, set);
         if (!set.isEmpty()) {
            spec = new HttpMethodSpec(spec, set);
         }
      } else {
         spec = allSpec;
      }

      return spec;
   }

   static HttpMethodSpec getSpec(String[] methods) {
      HttpMethodSpec spec;
      if (methods != null && methods.length != 0) {
         int map = 0;
         BitSet set = new BitSet();

         for(int i = 0; i < methods.length; ++i) {
            Integer bit = (Integer)methodHash.get(methods[i]);
            if (bit != null) {
               map |= bit;
            } else {
               setExtensionBit(methods[i], set);
            }
         }

         if (set.isEmpty()) {
            spec = specArray[map];
         } else {
            spec = new HttpMethodSpec(specArray[map], set);
         }
      } else {
         spec = allSpec;
      }

      return spec;
   }

   String getActions() {
      if (this.standardMap == 0 && this.extensionSet == null) {
         return null;
      } else {
         synchronized(this) {
            if (this.actions != null) {
               return this.actions;
            }

            if (this.standardSpec != null) {
               this.actions = this.getExtensionActions(this.standardSpec.getActions(), this.standardMap, this.extensionSet);
            } else {
               this.actions = this.getStandardActions(this.exceptionList, this.standardMap);
            }
         }

         return this.actions;
      }
   }

   boolean implies(HttpMethodSpec that) {
      boolean doesImplies;
      if (this.standardMap == 0 && this.extensionSet == null) {
         doesImplies = true;
      } else if (that.standardMap == 0 && that.extensionSet == null) {
         doesImplies = false;
      } else {
         BitSet clone;
         if (this.exceptionList && that.exceptionList) {
            doesImplies = (this.standardMap & that.standardMap) == this.standardMap;
            if (doesImplies && this.extensionSet != null) {
               if (that.extensionSet == null) {
                  doesImplies = false;
               } else {
                  clone = (BitSet)that.extensionSet.clone();
                  clone.and(this.extensionSet);
                  doesImplies = clone.equals(this.extensionSet);
               }
            }
         } else if (this.exceptionList == that.exceptionList) {
            doesImplies = (this.standardMap & that.standardMap) == that.standardMap;
            if (doesImplies && that.extensionSet != null) {
               if (this.extensionSet == null) {
                  doesImplies = false;
               } else {
                  clone = (BitSet)that.extensionSet.clone();
                  clone.and(this.extensionSet);
                  doesImplies = clone.equals(that.extensionSet);
               }
            }
         } else if (this.exceptionList) {
            doesImplies = (this.standardMap & that.standardMap) == 0;
            if (doesImplies && that.extensionSet != null) {
               if (this.extensionSet == null) {
                  doesImplies = true;
               } else {
                  doesImplies = !this.extensionSet.intersects(that.extensionSet);
               }
            }
         } else {
            doesImplies = false;
         }
      }

      return doesImplies;
   }

   public String toString() {
      return this.getActions();
   }

   public int hashCode() {
      return (this.exceptionList ? 1 : 0) + (this.standardMap << 1) + ((this.extensionSet == null ? 0 : this.extensionSet.hashCode()) << mapSize + 1);
   }

   public boolean equals(Object that) {
      boolean isEqual = false;
      if (that != null && that instanceof HttpMethodSpec) {
         if (that == this) {
            isEqual = true;
         } else {
            isEqual = this.hashCode() == ((HttpMethodSpec)that).hashCode();
         }
      }

      return isEqual;
   }

   private HttpMethodSpec(boolean isExceptionList, int map) {
      this.standardSpec = null;
      this.exceptionList = isExceptionList;
      this.standardMap = map;
      this.extensionSet = null;
      this.actions = null;
   }

   private HttpMethodSpec(HttpMethodSpec spec, BitSet set) {
      this.standardSpec = spec;
      this.exceptionList = spec.exceptionList;
      this.standardMap = spec.standardMap;
      this.extensionSet = set.isEmpty() ? null : set;
      this.actions = null;
   }

   private static void setExtensionBit(String method, BitSet set) {
      int bitPos;
      synchronized(extensionMethods) {
         bitPos = extensionMethods.indexOf(method);
         if (bitPos < 0) {
            bitPos = extensionMethods.size();
            extensionMethods.add(method);
         }
      }

      set.set(bitPos);
   }

   private static String getExtensionMethod(int bitPos) {
      synchronized(extensionMethods) {
         if (bitPos >= 0 && bitPos < extensionMethods.size()) {
            return (String)extensionMethods.get(bitPos);
         } else {
            throw new RuntimeException("invalid (extensionMethods) bit position: '" + bitPos + "' size: '" + extensionMethods.size() + " '");
         }
      }
   }

   private static HttpMethodSpec getStandardSpec(String actions, BitSet set) {
      boolean isExceptionList = false;
      if (actions.charAt(0) == '!') {
         isExceptionList = true;
         if (actions.length() < 2) {
            throw new IllegalArgumentException("illegal HTTP method Spec actions: '" + actions + "'");
         }

         actions = actions.substring(1);
      }

      int map = makeMethodSet(actions, set);
      return isExceptionList ? exceptionSpecArray[map] : specArray[map];
   }

   private static int makeMethodSet(String actions, BitSet set) {
      int i = 0;
      int mSet = 0;

      for(int commaPos = 0; commaPos >= 0 && i < actions.length(); i = commaPos + 1) {
         commaPos = actions.indexOf(",", i);
         if (commaPos == 0) {
            throw new IllegalArgumentException("illegal HTTP method Spec actions: '" + actions + "'");
         }

         String method;
         if (commaPos < 0) {
            method = actions.substring(i);
         } else {
            method = actions.substring(i, commaPos);
         }

         Integer bit = (Integer)methodHash.get(method);
         if (bit != null) {
            mSet |= bit;
         } else {
            setExtensionBit(method, set);
         }
      }

      return mSet;
   }

   private String getExtensionActions(String standardActions, int map, BitSet set) {
      List methods = null;

      for(int i = set.nextSetBit(0); i >= 0; i = set.nextSetBit(i + 1)) {
         if (methods == null) {
            methods = new ArrayList();
         }

         methods.add(getExtensionMethod(i));
      }

      if (methods == null) {
         return standardActions;
      } else {
         Collections.sort(methods);
         StringBuffer actions = new StringBuffer(standardActions == null ? (this.exceptionList ? "!" : "") : standardActions);

         for(int i = 0; i < methods.size(); ++i) {
            if (i > 0 || map > 0) {
               actions.append(",");
            }

            actions.append((String)methods.get(i));
         }

         return actions.toString();
      }
   }

   private String getStandardActions(boolean isExceptionList, int map) {
      int bitValue = 1;
      StringBuffer actBuf = null;

      for(int i = 0; i < mapSize; ++i) {
         if ((map & bitValue) == bitValue) {
            if (actBuf == null) {
               actBuf = new StringBuffer(isExceptionList ? "!" : "");
            } else {
               actBuf.append(",");
            }

            actBuf.append(methodKeys[i]);
         }

         bitValue *= 2;
      }

      if (actBuf == null) {
         return isExceptionList ? "!" : "";
      } else {
         return actBuf.toString();
      }
   }

   static {
      mapSize = methodKeys.length;
      methodHash = new HashMap();
      int i = 1;

      for(int i = 0; i < mapSize; ++i) {
         methodHash.put(methodKeys[i], i);
         i <<= 1;
      }

      allSet = 0;

      for(i = 0; i < mapSize; ++i) {
         allSet <<= 1;
         ++allSet;
      }

      specArray = new HttpMethodSpec[allSet + 1];

      for(i = 0; i < allSet + 1; ++i) {
         specArray[i] = new HttpMethodSpec(false, i);
      }

      exceptionSpecArray = new HttpMethodSpec[allSet + 1];

      for(i = 0; i < allSet + 1; ++i) {
         exceptionSpecArray[i] = new HttpMethodSpec(true, i);
      }

      allSpec = new HttpMethodSpec(false, 0);
      extensionMethods = new ArrayList();
   }
}
