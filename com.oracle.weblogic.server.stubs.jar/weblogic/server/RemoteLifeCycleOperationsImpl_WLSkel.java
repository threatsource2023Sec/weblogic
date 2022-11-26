package weblogic.server;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.Map;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RemoteLifeCycleOperationsImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Ljava$lang$String;
   // $FF: synthetic field
   private static Class class$java$lang$String;
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
      String var5;
      String var15;
      int var78;
      String var79;
      String[] var80;
      String[] var81;
      String[] var84;
      int var85;
      boolean var86;
      String[] var88;
      boolean var91;
      String[] var95;
      switch (var1) {
         case 0:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var79 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var80 = (String[])var8.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var76) {
               throw new UnmarshalException("error unmarshalling arguments", var76);
            } catch (ClassNotFoundException var77) {
               throw new UnmarshalException("error unmarshalling arguments", var77);
            }

            ((RemoteLifeCycleOperations)var4).bootPartition(var5, var79, var80);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var79 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var80 = (String[])var9.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var74) {
               throw new UnmarshalException("error unmarshalling arguments", var74);
            } catch (ClassNotFoundException var75) {
               throw new UnmarshalException("error unmarshalling arguments", var75);
            }

            ((RemoteLifeCycleOperations)var4).forceRestartPartition(var5, var79, var80);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var81 = (String[])var7.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var72) {
               throw new UnmarshalException("error unmarshalling arguments", var72);
            } catch (ClassNotFoundException var73) {
               throw new UnmarshalException("error unmarshalling arguments", var73);
            }

            ((RemoteLifeCycleOperations)var4).forceShutDownPartition(var5, var81);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            String[] var82;
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var79 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var82 = (String[])var11.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var70) {
               throw new UnmarshalException("error unmarshalling arguments", var70);
            } catch (ClassNotFoundException var71) {
               throw new UnmarshalException("error unmarshalling arguments", var71);
            }

            ((RemoteLifeCycleOperations)var4).forceShutDownResourceGroup(var5, var79, var82);
            this.associateResponseData(var2, var3);
            break;
         case 4:
            ((RemoteLifeCycleOperations)var4).forceShutdown();
            this.associateResponseData(var2, var3);
            break;
         case 5:
            ((RemoteLifeCycleOperations)var4).forceSuspend();
            this.associateResponseData(var2, var3);
            break;
         case 6:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var81 = (String[])var10.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var68) {
               throw new UnmarshalException("error unmarshalling arguments", var68);
            } catch (ClassNotFoundException var69) {
               throw new UnmarshalException("error unmarshalling arguments", var69);
            }

            ((RemoteLifeCycleOperations)var4).forceSuspendPartition(var5, var81);
            this.associateResponseData(var2, var3);
            break;
         case 7:
            try {
               MsgInput var13 = var2.getMsgInput();
               var5 = (String)var13.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var79 = (String)var13.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var84 = (String[])var13.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var66) {
               throw new UnmarshalException("error unmarshalling arguments", var66);
            } catch (ClassNotFoundException var67) {
               throw new UnmarshalException("error unmarshalling arguments", var67);
            }

            ((RemoteLifeCycleOperations)var4).forceSuspendResourceGroup(var5, var79, var84);
            this.associateResponseData(var2, var3);
            break;
         case 8:
            var5 = ((RemoteLifeCycleOperations)var4).getMiddlewareHome();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var65) {
               throw new MarshalException("error marshalling return", var65);
            }
         case 9:
            Map var83 = ((RemoteLifeCycleOperations)var4).getRuntimeStates();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var83, class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
               break;
            } catch (IOException var64) {
               throw new MarshalException("error marshalling return", var64);
            }
         case 10:
            var5 = ((RemoteLifeCycleOperations)var4).getState();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var63) {
               throw new MarshalException("error marshalling return", var63);
            }
         case 11:
            var5 = ((RemoteLifeCycleOperations)var4).getWeblogicHome();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var62) {
               throw new MarshalException("error marshalling return", var62);
            }
         case 12:
            try {
               MsgInput var14 = var2.getMsgInput();
               var5 = (String)var14.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var79 = (String)var14.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var84 = (String[])var14.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var60) {
               throw new UnmarshalException("error unmarshalling arguments", var60);
            } catch (ClassNotFoundException var61) {
               throw new UnmarshalException("error unmarshalling arguments", var61);
            }

            ((RemoteLifeCycleOperations)var4).haltPartition(var5, var79, var84);
            this.associateResponseData(var2, var3);
            break;
         case 13:
            ((RemoteLifeCycleOperations)var4).resume();
            this.associateResponseData(var2, var3);
            break;
         case 14:
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var81 = (String[])var12.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var58) {
               throw new UnmarshalException("error unmarshalling arguments", var58);
            } catch (ClassNotFoundException var59) {
               throw new UnmarshalException("error unmarshalling arguments", var59);
            }

            ((RemoteLifeCycleOperations)var4).resumePartition(var5, var81);
            this.associateResponseData(var2, var3);
            break;
         case 15:
            try {
               MsgInput var16 = var2.getMsgInput();
               var5 = (String)var16.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var79 = (String)var16.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var88 = (String[])var16.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var56) {
               throw new UnmarshalException("error unmarshalling arguments", var56);
            } catch (ClassNotFoundException var57) {
               throw new UnmarshalException("error unmarshalling arguments", var57);
            }

            ((RemoteLifeCycleOperations)var4).resumeResourceGroup(var5, var79, var88);
            this.associateResponseData(var2, var3);
            break;
         case 16:
            try {
               MsgInput var17 = var2.getMsgInput();
               var5 = (String)var17.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var79 = (String)var17.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var88 = (String[])var17.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var54) {
               throw new UnmarshalException("error unmarshalling arguments", var54);
            } catch (ClassNotFoundException var55) {
               throw new UnmarshalException("error unmarshalling arguments", var55);
            }

            ((RemoteLifeCycleOperations)var4).setDesiredPartitionState(var5, var79, var88);
            this.associateResponseData(var2, var3);
            break;
         case 17:
            String[] var87;
            try {
               MsgInput var19 = var2.getMsgInput();
               var5 = (String)var19.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var79 = (String)var19.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var15 = (String)var19.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var87 = (String[])var19.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var52) {
               throw new UnmarshalException("error unmarshalling arguments", var52);
            } catch (ClassNotFoundException var53) {
               throw new UnmarshalException("error unmarshalling arguments", var53);
            }

            ((RemoteLifeCycleOperations)var4).setDesiredResourceGroupState(var5, var79, var15, var87);
            this.associateResponseData(var2, var3);
            break;
         case 18:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (String)var6.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var50) {
               throw new UnmarshalException("error unmarshalling arguments", var50);
            } catch (ClassNotFoundException var51) {
               throw new UnmarshalException("error unmarshalling arguments", var51);
            }

            ((RemoteLifeCycleOperations)var4).setInvalid(var5);
            this.associateResponseData(var2, var3);
            break;
         case 19:
            try {
               MsgInput var18 = var2.getMsgInput();
               var5 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var15 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var48) {
               throw new UnmarshalException("error unmarshalling arguments", var48);
            } catch (ClassNotFoundException var49) {
               throw new UnmarshalException("error unmarshalling arguments", var49);
            }

            ((RemoteLifeCycleOperations)var4).setState(var5, var15);
            this.associateResponseData(var2, var3);
            break;
         case 20:
            boolean var90;
            String[] var93;
            try {
               MsgInput var23 = var2.getMsgInput();
               var5 = (String)var23.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var85 = var23.readInt();
               var90 = var23.readBoolean();
               var91 = var23.readBoolean();
               var93 = (String[])var23.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var46) {
               throw new UnmarshalException("error unmarshalling arguments", var46);
            } catch (ClassNotFoundException var47) {
               throw new UnmarshalException("error unmarshalling arguments", var47);
            }

            ((RemoteLifeCycleOperations)var4).shutDownPartition(var5, var85, var90, var91, var93);
            this.associateResponseData(var2, var3);
            break;
         case 21:
            int var89;
            boolean var92;
            try {
               MsgInput var25 = var2.getMsgInput();
               var5 = (String)var25.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var15 = (String)var25.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var89 = var25.readInt();
               var91 = var25.readBoolean();
               var92 = var25.readBoolean();
               var95 = (String[])var25.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var44) {
               throw new UnmarshalException("error unmarshalling arguments", var44);
            } catch (ClassNotFoundException var45) {
               throw new UnmarshalException("error unmarshalling arguments", var45);
            }

            ((RemoteLifeCycleOperations)var4).shutDownResourceGroup(var5, var15, var89, var91, var92, var95);
            this.associateResponseData(var2, var3);
            break;
         case 22:
            ((RemoteLifeCycleOperations)var4).shutdown();
            this.associateResponseData(var2, var3);
            break;
         case 23:
            try {
               MsgInput var20 = var2.getMsgInput();
               var78 = var20.readInt();
               var86 = var20.readBoolean();
            } catch (IOException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            }

            ((RemoteLifeCycleOperations)var4).shutdown(var78, var86);
            this.associateResponseData(var2, var3);
            break;
         case 24:
            try {
               MsgInput var22 = var2.getMsgInput();
               var78 = var22.readInt();
               var86 = var22.readBoolean();
               var91 = var22.readBoolean();
            } catch (IOException var42) {
               throw new UnmarshalException("error unmarshalling arguments", var42);
            }

            ((RemoteLifeCycleOperations)var4).shutdown(var78, var86, var91);
            this.associateResponseData(var2, var3);
            break;
         case 25:
            try {
               MsgInput var26 = var2.getMsgInput();
               var5 = (String)var26.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var15 = (String)var26.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var91 = var26.readBoolean();
               var95 = (String[])var26.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            } catch (ClassNotFoundException var41) {
               throw new UnmarshalException("error unmarshalling arguments", var41);
            }

            ((RemoteLifeCycleOperations)var4).startPartition(var5, var15, var91, var95);
            this.associateResponseData(var2, var3);
            break;
         case 26:
            try {
               MsgInput var27 = var2.getMsgInput();
               var5 = (String)var27.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var15 = (String)var27.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var91 = var27.readBoolean();
               var95 = (String[])var27.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var38) {
               throw new UnmarshalException("error unmarshalling arguments", var38);
            } catch (ClassNotFoundException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            }

            ((RemoteLifeCycleOperations)var4).startResourceGroup(var5, var15, var91, var95);
            this.associateResponseData(var2, var3);
            break;
         case 27:
            ((RemoteLifeCycleOperations)var4).suspend();
            this.associateResponseData(var2, var3);
            break;
         case 28:
            try {
               MsgInput var21 = var2.getMsgInput();
               var78 = var21.readInt();
               var86 = var21.readBoolean();
            } catch (IOException var37) {
               throw new UnmarshalException("error unmarshalling arguments", var37);
            }

            ((RemoteLifeCycleOperations)var4).suspend(var78, var86);
            this.associateResponseData(var2, var3);
            break;
         case 29:
            boolean var94;
            String[] var96;
            try {
               MsgInput var29 = var2.getMsgInput();
               var5 = (String)var29.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var85 = var29.readInt();
               var94 = var29.readBoolean();
               var96 = (String[])var29.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            } catch (ClassNotFoundException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            }

            ((RemoteLifeCycleOperations)var4).suspendPartition(var5, var85, var94, var96);
            this.associateResponseData(var2, var3);
            break;
         case 30:
            int var24;
            boolean var28;
            String[] var30;
            try {
               MsgInput var31 = var2.getMsgInput();
               var5 = (String)var31.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var15 = (String)var31.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var24 = var31.readInt();
               var28 = var31.readBoolean();
               var30 = (String[])var31.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            } catch (ClassNotFoundException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            }

            ((RemoteLifeCycleOperations)var4).suspendResourceGroup(var5, var15, var24, var28, var30);
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
            ((RemoteLifeCycleOperations)var3).bootPartition((String)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 1:
            ((RemoteLifeCycleOperations)var3).forceRestartPartition((String)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 2:
            ((RemoteLifeCycleOperations)var3).forceShutDownPartition((String)var2[0], (String[])var2[1]);
            return null;
         case 3:
            ((RemoteLifeCycleOperations)var3).forceShutDownResourceGroup((String)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 4:
            ((RemoteLifeCycleOperations)var3).forceShutdown();
            return null;
         case 5:
            ((RemoteLifeCycleOperations)var3).forceSuspend();
            return null;
         case 6:
            ((RemoteLifeCycleOperations)var3).forceSuspendPartition((String)var2[0], (String[])var2[1]);
            return null;
         case 7:
            ((RemoteLifeCycleOperations)var3).forceSuspendResourceGroup((String)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 8:
            return ((RemoteLifeCycleOperations)var3).getMiddlewareHome();
         case 9:
            return ((RemoteLifeCycleOperations)var3).getRuntimeStates();
         case 10:
            return ((RemoteLifeCycleOperations)var3).getState();
         case 11:
            return ((RemoteLifeCycleOperations)var3).getWeblogicHome();
         case 12:
            ((RemoteLifeCycleOperations)var3).haltPartition((String)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 13:
            ((RemoteLifeCycleOperations)var3).resume();
            return null;
         case 14:
            ((RemoteLifeCycleOperations)var3).resumePartition((String)var2[0], (String[])var2[1]);
            return null;
         case 15:
            ((RemoteLifeCycleOperations)var3).resumeResourceGroup((String)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 16:
            ((RemoteLifeCycleOperations)var3).setDesiredPartitionState((String)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 17:
            ((RemoteLifeCycleOperations)var3).setDesiredResourceGroupState((String)var2[0], (String)var2[1], (String)var2[2], (String[])var2[3]);
            return null;
         case 18:
            ((RemoteLifeCycleOperations)var3).setInvalid((String)var2[0]);
            return null;
         case 19:
            ((RemoteLifeCycleOperations)var3).setState((String)var2[0], (String)var2[1]);
            return null;
         case 20:
            ((RemoteLifeCycleOperations)var3).shutDownPartition((String)var2[0], (Integer)var2[1], (Boolean)var2[2], (Boolean)var2[3], (String[])var2[4]);
            return null;
         case 21:
            ((RemoteLifeCycleOperations)var3).shutDownResourceGroup((String)var2[0], (String)var2[1], (Integer)var2[2], (Boolean)var2[3], (Boolean)var2[4], (String[])var2[5]);
            return null;
         case 22:
            ((RemoteLifeCycleOperations)var3).shutdown();
            return null;
         case 23:
            ((RemoteLifeCycleOperations)var3).shutdown((Integer)var2[0], (Boolean)var2[1]);
            return null;
         case 24:
            ((RemoteLifeCycleOperations)var3).shutdown((Integer)var2[0], (Boolean)var2[1], (Boolean)var2[2]);
            return null;
         case 25:
            ((RemoteLifeCycleOperations)var3).startPartition((String)var2[0], (String)var2[1], (Boolean)var2[2], (String[])var2[3]);
            return null;
         case 26:
            ((RemoteLifeCycleOperations)var3).startResourceGroup((String)var2[0], (String)var2[1], (Boolean)var2[2], (String[])var2[3]);
            return null;
         case 27:
            ((RemoteLifeCycleOperations)var3).suspend();
            return null;
         case 28:
            ((RemoteLifeCycleOperations)var3).suspend((Integer)var2[0], (Boolean)var2[1]);
            return null;
         case 29:
            ((RemoteLifeCycleOperations)var3).suspendPartition((String)var2[0], (Integer)var2[1], (Boolean)var2[2], (String[])var2[3]);
            return null;
         case 30:
            ((RemoteLifeCycleOperations)var3).suspendResourceGroup((String)var2[0], (String)var2[1], (Integer)var2[2], (Boolean)var2[3], (String[])var2[4]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
