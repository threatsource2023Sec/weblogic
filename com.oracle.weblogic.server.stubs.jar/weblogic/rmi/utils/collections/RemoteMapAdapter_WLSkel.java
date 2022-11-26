package weblogic.rmi.utils.collections;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.Map;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RemoteMapAdapter_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$java$util$Map;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      Map var5;
      Object var35;
      Object var39;
      switch (var1) {
         case 0:
            ((RemoteMap)var4).clear();
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var6 = var2.getMsgInput();
               var35 = (Object)var6.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            } catch (ClassNotFoundException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            }

            boolean var37 = ((RemoteMap)var4).containsKey(var35);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var37);
               break;
            } catch (IOException var31) {
               throw new MarshalException("error marshalling return", var31);
            }
         case 2:
            try {
               MsgInput var7 = var2.getMsgInput();
               var35 = (Object)var7.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var29) {
               throw new UnmarshalException("error unmarshalling arguments", var29);
            } catch (ClassNotFoundException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            }

            boolean var38 = ((RemoteMap)var4).containsValue(var35);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var38);
               break;
            } catch (IOException var28) {
               throw new MarshalException("error marshalling return", var28);
            }
         case 3:
            try {
               MsgInput var8 = var2.getMsgInput();
               var35 = (Object)var8.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            } catch (ClassNotFoundException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            var39 = ((RemoteMap)var4).get(var35);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var39, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 4:
            boolean var36 = ((RemoteMap)var4).isEmpty();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var36);
               break;
            } catch (IOException var24) {
               throw new MarshalException("error marshalling return", var24);
            }
         case 5:
            try {
               MsgInput var10 = var2.getMsgInput();
               var35 = (Object)var10.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var39 = (Object)var10.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            } catch (ClassNotFoundException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            }

            Object var40 = ((RemoteMap)var4).put(var35, var39);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var40, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var21) {
               throw new MarshalException("error marshalling return", var21);
            }
         case 6:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (Map)var9.readObject(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            } catch (ClassNotFoundException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            }

            ((RemoteMap)var4).putAll(var5);
            this.associateResponseData(var2, var3);
            break;
         case 7:
            try {
               MsgInput var11 = var2.getMsgInput();
               var35 = (Object)var11.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            } catch (ClassNotFoundException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            }

            Object var12 = ((RemoteMap)var4).remove(var35);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var12, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var16) {
               throw new MarshalException("error marshalling return", var16);
            }
         case 8:
            int var34 = ((RemoteMap)var4).size();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var34);
               break;
            } catch (IOException var15) {
               throw new MarshalException("error marshalling return", var15);
            }
         case 9:
            var5 = ((RemoteMap)var4).snapshot();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
               break;
            } catch (IOException var14) {
               throw new MarshalException("error marshalling return", var14);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            ((RemoteMap)var3).clear();
            return null;
         case 1:
            return new Boolean(((RemoteMap)var3).containsKey((Object)var2[0]));
         case 2:
            return new Boolean(((RemoteMap)var3).containsValue((Object)var2[0]));
         case 3:
            return ((RemoteMap)var3).get((Object)var2[0]);
         case 4:
            return new Boolean(((RemoteMap)var3).isEmpty());
         case 5:
            return ((RemoteMap)var3).put((Object)var2[0], (Object)var2[1]);
         case 6:
            ((RemoteMap)var3).putAll((Map)var2[0]);
            return null;
         case 7:
            return ((RemoteMap)var3).remove((Object)var2[0]);
         case 8:
            return new Integer(((RemoteMap)var3).size());
         case 9:
            return ((RemoteMap)var3).snapshot();
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
