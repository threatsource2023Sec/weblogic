package weblogic.wtc.jatmi;

import java.rmi.RemoteException;
import java.util.HashMap;

public class InvokeSvcDef implements InvokeSvc {
   private HashMap st = new HashMap();

   public void invoke(InvokeInfo invokeinfo, gwatmi gwobj) throws TPException {
      Reply rtn = null;
      TuxedoService svc = (TuxedoService)this.st.get(invokeinfo.getServiceName());
      if (svc == null) {
         throw new TPException(6);
      } else {
         TypedBuffer data;
         int mytpurcode;
         int myTPException;
         try {
            rtn = svc.service(invokeinfo);
            data = rtn.getReplyBuffer();
            myTPException = 0;
            mytpurcode = rtn.gettpurcode();
         } catch (RemoteException var10) {
            throw new TPException(12, "Remote exception: " + var10);
         } catch (TPReplyException var11) {
            data = var11.getExceptionReply().getReplyBuffer();
            myTPException = var11.gettperrno();
            mytpurcode = var11.getExceptionReply().gettpurcode();
         } catch (TPException var12) {
            throw var12;
         }

         tfmh tmmsg;
         if (data == null) {
            tmmsg = new tfmh(1);
         } else {
            tcm user = new tcm((short)0, new UserTcb(data));
            tmmsg = new tfmh(data.getHintIndex(), user, 1);
         }

         gwobj.send_success_return(invokeinfo.getReqid(), tmmsg, myTPException, mytpurcode, -1);
      }
   }

   public void advertise(String svc, TuxedoService func) throws TPException {
      if (svc == null) {
         throw new TPException(4, "null service name encounterd");
      } else if (func == null) {
         throw new TPException(4, "null function object encountered");
      } else {
         this.st.put(svc, func);
      }
   }

   public void unadvertise(String svc) throws TPException {
      if (svc == null) {
         throw new TPException(4, "null service name encountered");
      } else {
         this.st.remove(svc);
      }
   }

   public void shutdown() {
   }
}
