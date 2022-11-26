package weblogic.diagnostics.instrumentation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PointcutHandlingInfoImpl implements PointcutHandlingInfo, Serializable {
   private static final long serialVersionUID = 1L;
   private ValueHandlingInfo classValueHandlingInfo;
   private ValueHandlingInfo returnValueHandlingInfo;
   private ValueHandlingInfo[] argumentValueHandlingInfo;

   public PointcutHandlingInfoImpl() {
   }

   public PointcutHandlingInfoImpl(ValueHandlingInfo classValueHandlingInfo, ValueHandlingInfo returnValueHandlingInfo, ValueHandlingInfo[] argumentValueHandlingInfo) {
      this.classValueHandlingInfo = classValueHandlingInfo;
      this.returnValueHandlingInfo = returnValueHandlingInfo;
      this.argumentValueHandlingInfo = argumentValueHandlingInfo;
   }

   public ValueHandlingInfo getClassValueHandlingInfo() {
      return this.classValueHandlingInfo;
   }

   public ValueHandlingInfo getReturnValueHandlingInfo() {
      return this.returnValueHandlingInfo;
   }

   public ValueHandlingInfo[] getArgumentValueHandlingInfo() {
      return this.argumentValueHandlingInfo;
   }

   public void setClassValueHandlingInfo(ValueHandlingInfo info) {
      this.classValueHandlingInfo = info;
   }

   public void setReturnValueHandlingInfo(ValueHandlingInfo info) {
      this.returnValueHandlingInfo = info;
   }

   public void setArgumentValueHandlingInfo(ValueHandlingInfo[] infos) {
      this.argumentValueHandlingInfo = infos;
   }

   public static boolean compareInfo(PointcutHandlingInfo info1, PointcutHandlingInfo info2) {
      if (info1 == info2) {
         return true;
      } else if (info1 != null && info2 != null) {
         if (ValueHandlingInfoImpl.compareInfo(info1.getClassValueHandlingInfo(), info2.getClassValueHandlingInfo()) && ValueHandlingInfoImpl.compareInfo(info1.getReturnValueHandlingInfo(), info2.getReturnValueHandlingInfo())) {
            ValueHandlingInfo[] info1Args = info1.getArgumentValueHandlingInfo();
            ValueHandlingInfo[] info2Args = info2.getArgumentValueHandlingInfo();
            if (info1Args == null && info2Args == null) {
               return true;
            } else if (info1Args != null && info2Args != null) {
               if (info1Args.length != info2Args.length) {
                  return false;
               } else {
                  for(int i = 0; i < info1Args.length; ++i) {
                     if (!ValueHandlingInfoImpl.compareInfo(info1Args[i], info2Args[i])) {
                        return false;
                     }
                  }

                  return true;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("ClassValueHandlingInfo=");
      buf.append(this.classValueHandlingInfo);
      buf.append(", ReturnValueHandlingInfo=");
      buf.append(this.returnValueHandlingInfo);
      if (this.argumentValueHandlingInfo == null) {
         buf.append(", argumentValueHandlingInfo=null");
      } else {
         for(int i = 0; i < this.argumentValueHandlingInfo.length; ++i) {
            buf.append(", argumentValueHandlingInfo[" + i + "] = " + this.argumentValueHandlingInfo[i]);
         }
      }

      return new String(buf);
   }

   public static Map makeMap(String[] names, PointcutHandlingInfo[] infos) {
      if (names != null && infos != null && (names.length != 0 || infos.length != 0)) {
         if (names.length != infos.length) {
            return null;
         } else {
            Map theMap = new HashMap();

            for(int i = 0; i < names.length; ++i) {
               if (names[i] == null) {
                  return null;
               }

               theMap.put(names[i], infos[i]);
               if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_CONFIG.debug("makeMap adding PointcutHandlingInfo for " + names[i] + " monitor:  info = " + infos[i]);
               }
            }

            return theMap;
         }
      } else {
         return null;
      }
   }

   public static String mapToString(Map infoMap) {
      if (infoMap == null) {
         return "null";
      } else {
         StringBuffer buf = new StringBuffer();
         Iterator iter = infoMap.entrySet().iterator();
         buf.append(" [");

         while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            buf.append(" Monitor=");
            buf.append(entry.getKey().toString());
            buf.append(" PointcutHandlingInfo=");
            buf.append(entry.getValue().toString());
         }

         buf.append("] ");
         return buf.toString();
      }
   }
}
