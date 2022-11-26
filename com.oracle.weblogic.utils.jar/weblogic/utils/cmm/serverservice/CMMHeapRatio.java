package weblogic.utils.cmm.serverservice;

import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.StringTokenizer;
import weblogic.utils.cmm.MemoryPressureListener;

public class CMMHeapRatio implements MemoryPressureListener {
   private static final String VM_MAX_HEAP_FREE_RATIO = "MaxHeapFreeRatio";
   private static final String MAX_HEAP_FREE_RATIO = "com.weblogic.cmm.heap.ratio.max";
   private static final String MAX_HEAP_FREE_DEFAULT = "5:50";
   private static final int TOP_LEVEL = 10;
   private static final int UNSET = -1;
   private static final HotSpotDiagnosticMXBean HOT_SPOT = getHotSpot();
   private final int[] levelRatios = new int[10];
   private final boolean isWriteable;
   private final int originalRatio;
   private int currentRatio = -1;
   private static final String PLATFORM_MX_METHOD = "getPlatformMXBean";

   public CMMHeapRatio() {
      if (HOT_SPOT == null) {
         this.isWriteable = false;
         this.originalRatio = -1;
      } else {
         VMOption option = HOT_SPOT.getVMOption("MaxHeapFreeRatio");
         if (!option.isWriteable()) {
            this.isWriteable = false;
            this.originalRatio = -1;
         } else {
            this.isWriteable = true;
            this.currentRatio = getCurrentMaxHeapFreeRatio();
            this.originalRatio = this.currentRatio;
            String maxRatios = System.getProperty("com.weblogic.cmm.heap.ratio.max", "5:50").trim();
            int lcv;
            if (maxRatios.isEmpty()) {
               for(lcv = 0; lcv < 10; ++lcv) {
                  this.levelRatios[lcv] = -1;
               }

            } else {
               for(lcv = 0; lcv < 10; ++lcv) {
                  this.levelRatios[lcv] = -1;
               }

               StringTokenizer st = new StringTokenizer(maxRatios, ";");

               int colonSeparator;
               while(st.hasMoreTokens()) {
                  String token = st.nextToken();
                  colonSeparator = token.indexOf(58);
                  if (colonSeparator <= 0) {
                     throw new IllegalArgumentException("MAX_HEAP_FREE_RATIO value \"" + maxRatios + "\" could not be parsed, no \":\" in entry " + token);
                  }

                  int level;
                  try {
                     level = Integer.parseInt(token.substring(0, colonSeparator));
                  } catch (NumberFormatException var11) {
                     throw new IllegalArgumentException("MAX_HEAP_FREE_RATIO value \"" + maxRatios + "\" had a bad level value in entry " + token, var11);
                  }

                  if (level >= 1 && level <= 10) {
                     int levelIndex = level - 1;
                     String ratioString = token.substring(colonSeparator + 1);
                     if (ratioString.isEmpty()) {
                        throw new IllegalArgumentException("MAX_HEAP_FREE_RATIO value \"" + maxRatios + "\" had no ratio value in entry " + token);
                     }

                     int levelRatio;
                     try {
                        levelRatio = Integer.parseInt(ratioString);
                     } catch (NumberFormatException var12) {
                        throw new IllegalArgumentException("MAX_HEAP_FREE_RATIO value \"" + maxRatios + "\" had a bad ratio value in entry " + token, var12);
                     }

                     if (levelRatio >= 0 && levelRatio <= 100) {
                        this.levelRatios[levelIndex] = levelRatio;
                        continue;
                     }

                     throw new IllegalArgumentException("MAX_HEAP_FREE_RATIO value \"" + maxRatios + "\" had a bad milliseoncd value for level " + level + " which was " + levelRatio + " in entry " + token);
                  }

                  throw new IllegalArgumentException("MAX_HEAP_FREE_RATIO value \"" + maxRatios + "\" had a bad level value " + level + " in entry " + token);
               }

               int previousSpecifiedValue = this.currentRatio;

               for(colonSeparator = 0; colonSeparator < 10; ++colonSeparator) {
                  if (this.levelRatios[colonSeparator] == -1) {
                     this.levelRatios[colonSeparator] = previousSpecifiedValue;
                  } else {
                     previousSpecifiedValue = this.levelRatios[colonSeparator];
                  }
               }

            }
         }
      }
   }

   public synchronized void handleCMMLevel(int newLevel) {
      if (this.isWriteable) {
         if (newLevel <= 0) {
            if (this.currentRatio != this.originalRatio) {
               setCurrentMaxHeapFreeRatio(this.originalRatio);
               this.currentRatio = this.originalRatio;
            }

         } else {
            int ratioToSetTo = this.levelRatios[newLevel - 1];
            if (this.currentRatio != ratioToSetTo) {
               setCurrentMaxHeapFreeRatio(ratioToSetTo);
               this.currentRatio = ratioToSetTo;
            }

         }
      }
   }

   private static HotSpotDiagnosticMXBean getHotSpot() {
      return (HotSpotDiagnosticMXBean)AccessController.doPrivileged(new PrivilegedAction() {
         public HotSpotDiagnosticMXBean run() {
            Method m;
            try {
               m = ManagementFactory.class.getMethod("getPlatformMXBean", Class.class);
            } catch (NoSuchMethodException var6) {
               return null;
            } catch (SecurityException var7) {
               return null;
            }

            try {
               return (HotSpotDiagnosticMXBean)m.invoke((Object)null, HotSpotDiagnosticMXBean.class);
            } catch (IllegalAccessException var3) {
               return null;
            } catch (IllegalArgumentException var4) {
               return null;
            } catch (InvocationTargetException var5) {
               return null;
            }
         }
      });
   }

   private static int getCurrentMaxHeapFreeRatio() {
      return HOT_SPOT == null ? -1 : (Integer)AccessController.doPrivileged(new PrivilegedAction() {
         public Integer run() {
            VMOption option = CMMHeapRatio.HOT_SPOT.getVMOption("MaxHeapFreeRatio");
            if (option == null) {
               return -1;
            } else {
               String vmValue = option.getValue();
               if (vmValue == null) {
                  return -1;
               } else {
                  try {
                     return Integer.parseInt(vmValue);
                  } catch (NumberFormatException var4) {
                     return -1;
                  }
               }
            }
         }
      });
   }

   private static void setCurrentMaxHeapFreeRatio(final int newRatio) {
      if (HOT_SPOT != null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               CMMHeapRatio.HOT_SPOT.setVMOption("MaxHeapFreeRatio", "" + newRatio);
               return null;
            }
         });
      }
   }
}
