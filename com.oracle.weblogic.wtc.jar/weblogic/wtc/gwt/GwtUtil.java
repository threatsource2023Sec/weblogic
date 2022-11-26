package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.util.ArrayList;
import java.util.List;
import weblogic.jdbc.common.internal.AffinityContextException;
import weblogic.jdbc.common.internal.AffinityContextHelper;
import weblogic.wtc.jatmi.MetaTcb;
import weblogic.wtc.jatmi.MetaTcmHelper;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.tcm;
import weblogic.wtc.jatmi.tfmh;

public final class GwtUtil {
   public static String getLocalDomId(String remoteDomId) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/GwtUtil/getLocalDomId/0");
      }

      if (remoteDomId.equals("")) {
         throw new TPException(4);
      } else {
         if (traceEnabled) {
            ntrace.doTrace("/GwtUtil/getLocalDomId/10" + remoteDomId);
         }

         TDMRemoteTDomain rdom = WTCService.getWTCService().getRemoteTDomain(remoteDomId);
         if (null == rdom) {
            if (traceEnabled) {
               ntrace.doTrace("*]/GwtUtil/getLocalDomId/20");
            }

            throw new TPException(6);
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/GwtUtil/getLocalDomId/30");
            }

            return rdom.getLocalAccessPointObject().getAccessPoint();
         }
      }
   }

   public static void getInboundAffinityCtxFromMetaTCM(tfmh service_tmmsg, AffinityContextHelper affinityctxhelper, boolean isTran) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (affinityctxhelper != null && affinityctxhelper.isApplicationContextAvailable() && service_tmmsg.meta != null) {
         MetaTcb tcb = (MetaTcb)service_tmmsg.meta.body;
         List affinityctxlist = MetaTcmHelper.getAffinityCtxList(tcb, isTran);

         for(int i = 0; affinityctxlist != null && i < affinityctxlist.size(); ++i) {
            String straffinityctx = (String)affinityctxlist.get(i);
            if (traceEnabled) {
               ntrace.doTrace("*]/GwtUtil/getInboundAffinityCtxFromMetaTCM/affinityctxstr:" + straffinityctx);
            }

            String[] affinityhead = straffinityctx.split(":");
            if (traceEnabled) {
               ntrace.doTrace("*]/GwtUtil/getInboundAffinityCtxFromMetaTCM/affinityhead:" + affinityhead[0] + " ,affinitybody:" + affinityhead[1]);
            }

            if (affinityhead[0] != null && affinityhead[0].equalsIgnoreCase("Oracle_XA")) {
               String[] affinitybody = affinityhead[1].split("\\+");
               if (traceEnabled) {
                  ntrace.doTrace("*]/GwtUtil/getInboundAffinityCtxFromMetaTCM/rm:" + affinitybody[0] + " ,inst:" + affinitybody[1] + " ,srvc:" + affinitybody[2]);
               }

               if (affinitybody[0] != null && affinitybody[1] != null && affinitybody[2] != null) {
                  String affinitykey = affinityctxhelper.getKey(affinitybody[0], affinitybody[2]);
                  if (traceEnabled) {
                     ntrace.doTrace("*]/GwtUtil/getInboundAffinityCtxFromMetaTCM/affinitykey:" + affinitykey);
                  }

                  if (affinitykey != null) {
                     Object affinitycontext = affinityctxhelper.getAffinityContext(affinitykey);
                     if (affinitycontext == null) {
                        try {
                           affinitycontext = affinityctxhelper.createAffinityContext(affinitybody[0], affinitybody[2], affinitybody[1]);
                           affinityctxhelper.setAffinityContext(affinitykey, affinitycontext);
                           if (traceEnabled) {
                              ntrace.doTrace("/GwtUtil/getInboundAffinityCtxFromMetaTCM/affinitykey's num:" + affinityctxhelper.getKeys().length);
                           }
                        } catch (AffinityContextException var13) {
                           if (traceEnabled) {
                              ntrace.doTrace("*]/GwtUtil/getInboundAffinityCtxFromMetaTCM/Exception:" + var13.toString());
                           }
                        }
                     } else if (traceEnabled) {
                        ntrace.doTrace("/GwtUtil/getInboundAffinityCtxFromMetaTCM/affinitykey exist !");
                     }
                  }
               }
            }
         }
      }

   }

   public static void addOutboundAffinityCtxToMetaTCM(tfmh tmmsg, AffinityContextHelper affinityctxhelper, boolean isTran) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/GwtUtil/addOutboundAffinityCtxToMetaTCM/");
      }

      if (tmmsg != null && affinityctxhelper != null && affinityctxhelper.isApplicationContextAvailable()) {
         MetaTcb tmmsg_metatcm;
         if (tmmsg.meta == null) {
            tmmsg_metatcm = new MetaTcb();
            tmmsg.meta = new tcm((short)19, tmmsg_metatcm);
         } else if (tmmsg.meta.body == null) {
            tmmsg_metatcm = new MetaTcb();
            tmmsg.meta.body = tmmsg_metatcm;
         } else {
            tmmsg_metatcm = (MetaTcb)tmmsg.meta.body;
         }

         try {
            if (traceEnabled) {
               ntrace.doTrace("[/GwtUtil/addOutboundAffinityCtxToMetaTCM/isApplicationContextAvailable:" + affinityctxhelper.isApplicationContextAvailable());
            }

            if (affinityctxhelper.getKeys() != null) {
               List affinityctxlist = new ArrayList();
               String[] affinitykeys = affinityctxhelper.getKeys();
               if (traceEnabled) {
                  ntrace.doTrace("[/GwtUtil/addOutboundAffinityCtxToMetaTCM/affinitykey length/" + affinitykeys.length);
               }

               for(int i = 0; i < affinitykeys.length; ++i) {
                  if (traceEnabled) {
                     ntrace.doTrace("[/GwtUtil/addOutboundAffinityCtxToMetaTCM/affinitykey/" + affinitykeys[i]);
                  }

                  Object affctx = affinityctxhelper.getAffinityContext(affinitykeys[i]);
                  String dbname = affinityctxhelper.getDatabaseName(affctx);
                  String srvname = affinityctxhelper.getServiceName(affctx);
                  String instname = affinityctxhelper.getInstanceName(affctx);
                  String affinityctx = "Oracle_XA:" + dbname + "+" + instname + "+" + srvname;
                  if (traceEnabled) {
                     ntrace.doTrace("[/GwtUtil/addOutboundAffinityCtxToMetaTCM/AffinityContextStr/" + affinityctx);
                  }

                  affinityctxlist.add(affinityctx);
               }

               if (isTran) {
                  MetaTcmHelper.setAffintyCtxList(tmmsg_metatcm, affinityctxlist, isTran);
               }
            }
         } catch (AffinityContextException var13) {
            if (traceEnabled) {
               ntrace.doTrace("[/GwtUtil/AffinityContextException/" + var13.toString());
            }
         } catch (Exception var14) {
            if (traceEnabled) {
               ntrace.doTrace("[/GwtUtil/AffinityContextException/" + var14.toString());
            }
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/GwtUtil/addOutboundAffinityCtxToMetaTCM/no found AffinityContextHelper/");
         }

      }
   }
}
