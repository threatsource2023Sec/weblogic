package weblogic.jndi.internal;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.Hashtable;
import javax.naming.Context;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RemoteContextFactoryImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$Hashtable;
   // $FF: synthetic field
   private static Class class$javax$naming$Context;

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
            Hashtable var5;
            String var6;
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (Hashtable)var7.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
               var6 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            } catch (ClassNotFoundException var12) {
               throw new UnmarshalException("error unmarshalling arguments", var12);
            }

            Context var8 = ((RemoteContextFactory)var4).getContext(var5, var6);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var8, class$javax$naming$Context == null ? (class$javax$naming$Context = class$("javax.naming.Context")) : class$javax$naming$Context);
               return var3;
            } catch (IOException var10) {
               throw new MarshalException("error marshalling return", var10);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return ((RemoteContextFactory)var3).getContext((Hashtable)var2[0], (String)var2[1]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
