package weblogic.wtc.gwt;

import weblogic.wtc.jatmi.CallDescriptor;
import weblogic.wtc.jatmi.ReqOid;

public class CallDescriptorUtil {
   public static boolean isCallDescriptorEqual(CallDescriptor ct1, CallDescriptor ct2) {
      if (ct1 == ct2) {
         return true;
      } else if (ct1 != null && ct2 != null) {
         CallDescriptor internalCallDescriptor1;
         if (ct1 instanceof ReqOid) {
            internalCallDescriptor1 = ((ReqOid)ct1).getReqReturn();
            if (!(ct2 instanceof ReqOid)) {
               if (ct2 instanceof TuxedoCallDescriptor) {
                  return internalCallDescriptor1 == null ? false : internalCallDescriptor1.equals(((TuxedoCallDescriptor)ct2).getCallDescriptor());
               } else {
                  return false;
               }
            } else if (internalCallDescriptor1 == null) {
               return false;
            } else if (((ReqOid)ct1).getAtmiObject() == null) {
               return false;
            } else {
               return internalCallDescriptor1.equals(((ReqOid)ct2).getReqReturn()) && ((ReqOid)ct1).getAtmiObject().equals(((ReqOid)ct2).getAtmiObject());
            }
         } else if (ct1 instanceof TuxedoCallDescriptor) {
            internalCallDescriptor1 = ((TuxedoCallDescriptor)ct1).getCallDescriptor();
            if (ct2 instanceof ReqOid) {
               return internalCallDescriptor1 == null ? false : internalCallDescriptor1.equals(((ReqOid)ct2).getReqReturn());
            } else if (ct2 instanceof TuxedoCallDescriptor) {
               if (((TuxedoCallDescriptor)ct1).getIndex() != ((TuxedoCallDescriptor)ct2).getIndex()) {
                  return false;
               } else if (internalCallDescriptor1 == ((TuxedoCallDescriptor)ct2).getCallDescriptor()) {
                  return true;
               } else {
                  return internalCallDescriptor1 == null ? false : internalCallDescriptor1.equals(((TuxedoCallDescriptor)ct2).getCallDescriptor());
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
}
