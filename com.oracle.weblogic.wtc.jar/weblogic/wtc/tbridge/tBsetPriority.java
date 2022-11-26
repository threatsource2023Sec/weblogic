package weblogic.wtc.tbridge;

import com.bea.core.jatmi.common.ntrace;

final class tBsetPriority {
   private static final int TUXMINP = 1;
   private static final int TUXMAXP = 100;
   private static final int JMSMINP = 0;
   private static final int JMSMAXP = 9;

   public tBsetPriority() {
   }

   public boolean setPVmap(tBpvalueMap pv, tBstartArgs sArgs) {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBsetPriority/setPVmap/");
      }

      String waystr = new String("JMS2TUX");
      if (pv.Pway == 1) {
         waystr = "TUX2JMS";
      }

      if (traceEnabled) {
         ntrace.doTrace("/tBsetPriority/setPVmap/P:Pway=" + waystr + " Prange=" + pv.Prange + " Pval=" + pv.Pvalue);
      }

      int i = pv.Prange.indexOf("-");
      if (i == -1) {
         pv.Pstart = Integer.parseInt(pv.Prange);
         pv.Pend = pv.Pstart;
      } else {
         pv.Pstart = Integer.parseInt(pv.Prange.substring(0, i));
         pv.Pend = Integer.parseInt(pv.Prange.substring(i + 1, pv.Prange.length()));
      }

      if (traceEnabled) {
         ntrace.doTrace("/tBsetPriority/setPVmap/P:Pstart=" + pv.Pstart + " Pend:" + pv.Pend + " S:TmapLen=" + sArgs.pMapJmsToTux.length + " JmapLen=" + sArgs.pMapTuxToJms.length);
      }

      boolean retval;
      retval = true;
      int j;
      label101:
      switch (pv.Pway) {
         case 1:
            if (pv.Pstart > pv.Pend) {
               if (traceEnabled) {
                  ntrace.doTrace("/tBsetPriority/setPVmap/TUX2JMS: JMS range start greater than range end");
               }

               retval = false;
            } else if (pv.Pstart >= 0 && pv.Pend <= 9) {
               if (pv.Pvalue >= 1 && pv.Pvalue <= 100) {
                  j = pv.Pstart;

                  while(true) {
                     if (j > pv.Pend) {
                        break label101;
                     }

                     sArgs.pMapJmsToTux[j] = pv.Pvalue;
                     ++j;
                  }
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBsetPriority/setPVmap/TUX2JMS: TUX value out of bounds");
                  }

                  retval = false;
               }
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("/tBsetPriority/setPVmap/TUX2JMS: JMS range out of bounds");
               }

               retval = false;
            }
            break;
         case 2:
            if (pv.Pstart > pv.Pend) {
               if (traceEnabled) {
                  ntrace.doTrace("/tBsetPriority/setPVmap/JMS2TUX: TUX range start greater than range end");
               }

               retval = false;
            } else if (pv.Pstart >= 1 && pv.Pend <= 100) {
               if (pv.Pvalue >= 0 && pv.Pvalue <= 9) {
                  for(j = pv.Pstart; j <= pv.Pend; ++j) {
                     sArgs.pMapTuxToJms[j] = pv.Pvalue;
                  }
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBsetPriority/setPVmap/JMS2TUX: JMS value out of bounds");
                  }

                  retval = false;
               }
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("/tBsetPriority/setPVmap/JMS2TUX: TUX range out of bounds");
               }

               retval = false;
            }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBsetPriority/setPVmap/" + retval);
      }

      return retval;
   }
}
