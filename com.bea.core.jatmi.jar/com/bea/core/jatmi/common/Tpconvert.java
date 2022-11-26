package com.bea.core.jatmi.common;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import weblogic.wtc.jatmi.ClientId;
import weblogic.wtc.jatmi.TPException;

public final class Tpconvert {
   public static final ClientId getClientId(String strCltId) throws TPException {
      ClientId cltId = new ClientId();
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/Tpconvert/getClientId/0 " + strCltId);
      }

      StringTokenizer st = new StringTokenizer(strCltId);

      try {
         cltId.setTimestamp(Integer.parseInt(st.nextToken().substring(2), 16));
         cltId.setMchid(Integer.parseInt(st.nextToken().substring(2), 16));
         cltId.setSlot(Integer.parseInt(st.nextToken().substring(2), 16));
      } catch (NumberFormatException var5) {
         if (traceEnabled) {
            ntrace.doTrace("*]/Tpconvert/getClientId/10");
         }

         throw new TPException(12);
      } catch (NoSuchElementException var6) {
         if (traceEnabled) {
            ntrace.doTrace("*]/Tpconvert/getClientId/20");
         }

         throw new TPException(12);
      } catch (IndexOutOfBoundsException var7) {
         if (traceEnabled) {
            ntrace.doTrace("*]/Tpconvert/getClientId/30");
         }

         throw new TPException(12);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/Tpconvert/getClientId/40");
      }

      return cltId;
   }
}
