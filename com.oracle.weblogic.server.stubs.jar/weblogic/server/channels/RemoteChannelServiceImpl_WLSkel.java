package weblogic.server.channels;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.protocol.ChannelList;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RemoteChannelServiceImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Ljava$lang$String;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$weblogic$protocol$ChannelList;
   // $FF: synthetic field
   private static Class class$weblogic$protocol$ServerChannel;
   // $FF: synthetic field
   private static Class class$weblogic$protocol$ServerIdentity;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      String var5;
      ServerIdentity var34;
      switch (var1) {
         case 0:
            var5 = ((RemoteChannelService)var4).getAdministrationURL();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var33) {
               throw new MarshalException("error marshalling return", var33);
            }
         case 1:
            try {
               MsgInput var6 = var2.getMsgInput();
               var34 = (ServerIdentity)var6.readObject(class$weblogic$protocol$ServerIdentity == null ? (class$weblogic$protocol$ServerIdentity = class$("weblogic.protocol.ServerIdentity")) : class$weblogic$protocol$ServerIdentity);
            } catch (IOException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            } catch (ClassNotFoundException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            }

            ChannelList var36 = ((RemoteChannelService)var4).getChannelList(var34);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var36, class$weblogic$protocol$ChannelList == null ? (class$weblogic$protocol$ChannelList = class$("weblogic.protocol.ChannelList")) : class$weblogic$protocol$ChannelList);
               break;
            } catch (IOException var30) {
               throw new MarshalException("error marshalling return", var30);
            }
         case 2:
            String[] var35 = ((RemoteChannelService)var4).getConnectedServers();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var35, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               break;
            } catch (IOException var29) {
               throw new MarshalException("error marshalling return", var29);
            }
         case 3:
            var5 = ((RemoteChannelService)var4).getDefaultURL();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var28) {
               throw new MarshalException("error marshalling return", var28);
            }
         case 4:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            } catch (ClassNotFoundException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            ServerChannel var37 = ((RemoteChannelService)var4).getServerChannel(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var37, class$weblogic$protocol$ServerChannel == null ? (class$weblogic$protocol$ServerChannel = class$("weblogic.protocol.ServerChannel")) : class$weblogic$protocol$ServerChannel);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 5:
            var34 = ((RemoteChannelService)var4).getServerIdentity();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var34, class$weblogic$protocol$ServerIdentity == null ? (class$weblogic$protocol$ServerIdentity = class$("weblogic.protocol.ServerIdentity")) : class$weblogic$protocol$ServerIdentity);
               break;
            } catch (IOException var24) {
               throw new MarshalException("error marshalling return", var24);
            }
         case 6:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            } catch (ClassNotFoundException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            }

            String var39 = ((RemoteChannelService)var4).getURL(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var39, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var21) {
               throw new MarshalException("error marshalling return", var21);
            }
         case 7:
            ChannelList var38;
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var38 = (ChannelList)var10.readObject(class$weblogic$protocol$ChannelList == null ? (class$weblogic$protocol$ChannelList = class$("weblogic.protocol.ChannelList")) : class$weblogic$protocol$ChannelList);
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            } catch (ClassNotFoundException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            }

            String var40 = ((RemoteChannelService)var4).registerServer(var5, var38);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var40, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var18) {
               throw new MarshalException("error marshalling return", var18);
            }
         case 8:
            try {
               MsgInput var9 = var2.getMsgInput();
               var34 = (ServerIdentity)var9.readObject(class$weblogic$protocol$ServerIdentity == null ? (class$weblogic$protocol$ServerIdentity = class$("weblogic.protocol.ServerIdentity")) : class$weblogic$protocol$ServerIdentity);
            } catch (IOException var16) {
               throw new UnmarshalException("error unmarshalling arguments", var16);
            } catch (ClassNotFoundException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            }

            ((RemoteChannelService)var4).removeChannelList(var34);
            this.associateResponseData(var2, var3);
            break;
         case 9:
            ChannelList var11;
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var11 = (ChannelList)var12.readObject(class$weblogic$protocol$ChannelList == null ? (class$weblogic$protocol$ChannelList = class$("weblogic.protocol.ChannelList")) : class$weblogic$protocol$ChannelList);
            } catch (IOException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            } catch (ClassNotFoundException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            }

            ((RemoteChannelService)var4).updateServer(var5, var11);
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
            return ((RemoteChannelService)var3).getAdministrationURL();
         case 1:
            return ((RemoteChannelService)var3).getChannelList((ServerIdentity)var2[0]);
         case 2:
            return ((RemoteChannelService)var3).getConnectedServers();
         case 3:
            return ((RemoteChannelService)var3).getDefaultURL();
         case 4:
            return ((RemoteChannelService)var3).getServerChannel((String)var2[0]);
         case 5:
            return ((RemoteChannelService)var3).getServerIdentity();
         case 6:
            return ((RemoteChannelService)var3).getURL((String)var2[0]);
         case 7:
            return ((RemoteChannelService)var3).registerServer((String)var2[0], (ChannelList)var2[1]);
         case 8:
            ((RemoteChannelService)var3).removeChannelList((ServerIdentity)var2[0]);
            return null;
         case 9:
            ((RemoteChannelService)var3).updateServer((String)var2[0], (ChannelList)var2[1]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
