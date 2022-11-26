package weblogic.servlet.cluster.wan;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.Set;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class PersistenceServiceImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$util$Set;
   // $FF: synthetic field
   private static Class class$weblogic$servlet$cluster$wan$BatchedSessionState;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      switch (var1) {
         case 0:
            Set var13;
            try {
               MsgInput var6 = var2.getMsgInput();
               var13 = (Set)var6.readObject(class$java$util$Set == null ? (class$java$util$Set = class$("java.util.Set")) : class$java$util$Set);
            } catch (IOException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            } catch (ClassNotFoundException var12) {
               throw new UnmarshalException("error unmarshalling arguments", var12);
            }

            ((PersistenceServiceInternal)var4).invalidateSessions(var13);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            BatchedSessionState var5;
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (BatchedSessionState)var7.readObject(class$weblogic$servlet$cluster$wan$BatchedSessionState == null ? (class$weblogic$servlet$cluster$wan$BatchedSessionState = class$("weblogic.servlet.cluster.wan.BatchedSessionState")) : class$weblogic$servlet$cluster$wan$BatchedSessionState);
            } catch (IOException var9) {
               throw new UnmarshalException("error unmarshalling arguments", var9);
            } catch (ClassNotFoundException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            }

            ((PersistenceServiceInternal)var4).persistState(var5);
            this.associateResponseData(var2, var3);
            break;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            ((PersistenceServiceInternal)var3).invalidateSessions((Set)var2[0]);
            return null;
         case 1:
            ((PersistenceServiceInternal)var3).persistState((BatchedSessionState)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
