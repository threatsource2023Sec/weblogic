package weblogic.messaging.path;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.rmi.internal.AsyncResultImpl;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class AsyncMapImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$io$Serializable;
   // $FF: synthetic field
   private static Class class$java$lang$String;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      Serializable var5;
      Serializable var6;
      AsyncResultImpl var8;
      switch (var1) {
         case 0:
            AsyncResultImpl var23;
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (Serializable)var7.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var23 = new AsyncResultImpl(var2, var3);
            } catch (IOException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            } catch (ClassNotFoundException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            }

            ((AsyncMapImpl)var4).get(var5, var23, (FutureResponse)var3);
            break;
         case 1:
            String var22 = ((AsyncMapRemote)var4).getJndiName();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var22, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var19) {
               throw new MarshalException("error marshalling return", var19);
            }
         case 2:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (Serializable)var9.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var6 = (Serializable)var9.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var8 = new AsyncResultImpl(var2, var3);
            } catch (IOException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            } catch (ClassNotFoundException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            }

            ((AsyncMapImpl)var4).put(var5, var6, var8, (FutureResponse)var3);
            break;
         case 3:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (Serializable)var10.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var6 = (Serializable)var10.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var8 = new AsyncResultImpl(var2, var3);
            } catch (IOException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            } catch (ClassNotFoundException var16) {
               throw new UnmarshalException("error unmarshalling arguments", var16);
            }

            ((AsyncMapImpl)var4).putIfAbsent(var5, var6, var8, (FutureResponse)var3);
            break;
         case 4:
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = (Serializable)var11.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var6 = (Serializable)var11.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var8 = new AsyncResultImpl(var2, var3);
            } catch (IOException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            } catch (ClassNotFoundException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            }

            ((AsyncMapImpl)var4).remove(var5, var6, var8, (FutureResponse)var3);
            break;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            ((AsyncMapRemote)var3).get((Serializable)var2[0], (AsyncResult)var2[1]);
            return null;
         case 1:
            return ((AsyncMapRemote)var3).getJndiName();
         case 2:
            ((AsyncMapRemote)var3).put((Serializable)var2[0], (Serializable)var2[1], (AsyncResult)var2[2]);
            return null;
         case 3:
            ((AsyncMapRemote)var3).putIfAbsent((Serializable)var2[0], (Serializable)var2[1], (AsyncResult)var2[2]);
            return null;
         case 4:
            ((AsyncMapRemote)var3).remove((Serializable)var2[0], (Serializable)var2[1], (AsyncResult)var2[2]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
