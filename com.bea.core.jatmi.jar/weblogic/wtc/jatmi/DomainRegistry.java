package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class DomainRegistry {
   private static HashMap myMap;

   public DomainRegistry() {
      if (myMap == null) {
         myMap = new HashMap();
      }

   }

   public static int getDuplicatedConnection(gwatmi domainSession) {
      gwatmi srcDomain = null;
      gwatmi desDomain = null;
      String srcdomainid = null;
      String desdomainid = null;
      Class dse = null;
      Method method_getRemoteDomain = null;
      int res = -1;
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("*]/DomainRegistry/getDuplicatedConnection/10/");
      }

      try {
         dse = Class.forName("weblogic.wtc.jatmi.dsession");
         method_getRemoteDomain = dse.getMethod("getRemoteDomainId");
      } catch (Exception var15) {
         return res;
      }

      if (myMap != null && domainSession != null) {
         if (!domainSession.getClass().getName().equals("weblogic.wtc.gwt.gwdsession") && !domainSession.getClass().getName().equals("weblogic.wtc.jatmi.dsession")) {
            return res;
         } else {
            desDomain = domainSession;
            synchronized(myMap) {
               Iterator ite = myMap.entrySet().iterator();

               label74:
               while(true) {
                  gwatmi ret;
                  do {
                     do {
                        if (!ite.hasNext()) {
                           break label74;
                        }

                        srcdomainid = null;
                        desdomainid = null;
                        Map.Entry entry = (Map.Entry)ite.next();
                        ret = (gwatmi)entry.getValue();
                     } while(ret == null);
                  } while(!ret.getClass().getName().equals("weblogic.wtc.gwt.gwdsession") && !ret.getClass().getName().equals("weblogic.wtc.jatmi.dsession"));

                  srcDomain = ret;

                  try {
                     srcdomainid = (String)method_getRemoteDomain.invoke(srcDomain);
                     desdomainid = (String)method_getRemoteDomain.invoke(desDomain);
                  } catch (Exception var16) {
                     continue;
                  }

                  if (srcdomainid != null && desdomainid != null && srcdomainid.equals(desdomainid) && ret.getUid() != desDomain.getUid()) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/DomainRegistry/getDuplicatedConnection/20/");
                     }

                     res = ret.getUid();
                     break;
                  }
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("*]/DomainRegistry/getDuplicatedConnection/30/");
            }

            return res;
         }
      } else {
         return res;
      }
   }

   public static gwatmi getDomainSession(int domainUid) {
      if (myMap == null) {
         return null;
      } else {
         Integer key = new Integer(domainUid);
         synchronized(myMap) {
            gwatmi ret = (gwatmi)myMap.get(key);
            return ret;
         }
      }
   }

   public static gwatmi addDomainSession(gwatmi domainSession) {
      if (myMap != null && domainSession != null) {
         Integer key = new Integer(domainSession.getUid());
         synchronized(myMap) {
            int uid = getDuplicatedConnection(domainSession);
            if (uid != -1) {
               return getDomainSession(uid);
            } else {
               myMap.put(key, domainSession);
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public static void removeDomainSession(gwatmi domainSession) {
      if (myMap != null && domainSession != null) {
         Integer key = new Integer(domainSession.getUid());
         synchronized(myMap) {
            myMap.remove(key);
         }
      }
   }
}
