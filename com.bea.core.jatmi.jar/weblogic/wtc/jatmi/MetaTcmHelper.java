package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCResourceHelper;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.xa.Xid;

public class MetaTcmHelper {
   public static final int MAX_AFFINITY_CTX_NUM = 16;
   public static final String AFFINITYSTR_HEAD = "Oracle_XA";

   public static Xid getImportedXid(MetaTcb metatcm, byte[] bqual) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/MetaTcmHelper/getImportedXid");
      }

      if (metatcm == null) {
         return null;
      } else {
         TypedFML32 metatcmdata = metatcm.getBuf();
         if (metatcmdata == null) {
            return null;
         } else {
            Integer importedFormatID = 0;
            byte[] importedgtrid = null;
            byte[] importedbqual = null;

            byte[] importedgtrid;
            try {
               importedFormatID = (Integer)metatcmdata.Fget(new FmlKey(63557348, 0));
               importedgtrid = (byte[])((byte[])metatcmdata.Fget(new FmlKey(231329509, 0)));
               if (traceEnabled) {
                  ntrace.doTrace(" /MetaTcmHelper/getImportedXid/formatID:" + importedFormatID + "/gtridlen:" + importedgtrid.length);
               }
            } catch (Ferror var9) {
               if (traceEnabled) {
                  ntrace.doTrace(" /MetaTcmHelper/getImportedXid/failed to get formatID or gtrid:" + var9.toString());
               }

               importedgtrid = null;
               importedFormatID = 0;
            }

            if (importedgtrid != null && importedgtrid.length > 0) {
               try {
                  importedbqual = (byte[])((byte[])metatcmdata.Fget(new FmlKey(231329510, 0)));
                  if (traceEnabled) {
                     ntrace.doTrace(" /MetaTcmHelper/getImportedXid/formatID:" + importedFormatID + "/bquallen:" + importedbqual.length);
                  }
               } catch (Ferror var8) {
                  if (traceEnabled) {
                     ntrace.doTrace(" /MetaTcmHelper/getImportedXid/no bqual:" + var8.toString());
                  }

                  importedbqual = null;
               }
            }

            if (importedgtrid == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/MetaTcmHelper/getImportedXid/null/not found");
               }

               return null;
            } else {
               return importedbqual == null ? TCResourceHelper.createWLSXid(importedFormatID, importedgtrid, bqual) : TCResourceHelper.createWLSXid(importedFormatID, importedgtrid, importedbqual);
            }
         }
      }
   }

   public static void setImportedXid(MetaTcb metatcm, Integer formatId, byte[] gtrid, byte[] bqual) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/MetaTcmHelper/setImportedXid");
      }

      if (metatcm != null) {
         TypedFML32 metatcmdata = metatcm.getBuf();
         if (metatcmdata != null) {
            try {
               metatcmdata.Fchg(new FmlKey(63557348, 0), formatId);
               metatcmdata.Fchg(new FmlKey(231329509, 0), gtrid);
               if (traceEnabled) {
                  ntrace.doTrace(" /MetaTcmHelper/setImportedXid/formatID:" + formatId + "/gtridlen:" + gtrid.length);
               }
            } catch (Ferror var7) {
               if (traceEnabled) {
                  ntrace.doTrace("] /MetaTcmHelper/setImportedXid/failed to set formatID or gtrid:" + var7.toString());
               }

               return;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/MetaTcmHelper/setImportedXid/100");
            }

         }
      }
   }

   public static List getAffinityCtxList(MetaTcb metatcm, boolean isTran) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/MetaTcmHelper/getAffinityCtxList");
      }

      if (metatcm == null) {
         return null;
      } else {
         TypedFML32 metatcmdata = metatcm.getBuf();
         if (metatcmdata == null) {
            return null;
         } else {
            List affinityctxlist = new ArrayList();

            try {
               int occur = 0;
               if (isTran) {
                  occur = metatcmdata.Foccur(197775086);
               }

               for(int i = occur - 1; i >= 0; --i) {
                  String affinityctx = null;
                  if (isTran) {
                     affinityctx = (String)metatcmdata.Fget(197775086, i);
                     metatcmdata.Fdel(197775086, i);
                  }

                  if (affinityctx != null && affinityctx.startsWith("Oracle_XA")) {
                     affinityctxlist.add(affinityctx);
                     if (traceEnabled) {
                        ntrace.doTrace(" /MetaTcmHelper/getAffinityCtxList/affinityctx:" + affinityctx);
                     }
                  } else if (traceEnabled) {
                     ntrace.doTrace(" /MetaTcmHelper/getAffinityCtxList/INVALID affinityctx");
                  }
               }

               return affinityctxlist;
            } catch (Ferror var8) {
               if (traceEnabled) {
                  ntrace.doTrace(" /MetaTcmHelper/getAffinityCtxList/failed to get affinityctx:" + var8.toString());
               }

               return null;
            }
         }
      }
   }

   public static void setAffintyCtxList(MetaTcb metatcm, List affintyctxlist, boolean isTran) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/MetaTcmHelper/setAffintyCtxList");
      }

      if (metatcm != null) {
         TypedFML32 metatcmdata = metatcm.getBuf();
         if (metatcmdata != null) {
            if (affintyctxlist != null) {
               for(int i = 0; i < affintyctxlist.size() && i < 16; ++i) {
                  try {
                     if (isTran) {
                        metatcmdata.Fchg(197775086, i, affintyctxlist.get(i));
                     }

                     if (traceEnabled) {
                        ntrace.doTrace(" /MetaTcmHelper/setAffintyCtxList/Affinity Context:" + (String)affintyctxlist.get(i));
                     }
                  } catch (Ferror var7) {
                     if (traceEnabled) {
                        ntrace.doTrace("] /MetaTcmHelper/setAffintyCtxList/failed to add Affinity Context:" + var7.toString());
                     }

                     return;
                  }
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/MetaTcmHelper/setAffintyCtxList/100");
               }

            }
         }
      }
   }
}
