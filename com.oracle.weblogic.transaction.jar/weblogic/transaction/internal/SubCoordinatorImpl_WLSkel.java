package weblogic.transaction.internal;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.Map;
import javax.transaction.xa.Xid;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.security.acl.internal.AuthenticatedUser;

public final class SubCoordinatorImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Ljava$lang$String;
   // $FF: synthetic field
   private static Class array$Ljavax$transaction$xa$Xid;
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$Map;
   // $FF: synthetic field
   private static Class class$javax$transaction$xa$Xid;
   // $FF: synthetic field
   private static Class class$weblogic$security$acl$internal$AuthenticatedUser;
   // $FF: synthetic field
   private static Class class$weblogic$transaction$internal$Notification;
   // $FF: synthetic field
   private static Class class$weblogic$transaction$internal$NotificationListener;
   // $FF: synthetic field
   private static Class class$weblogic$transaction$internal$PropagationContext;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      String[] var20;
      AuthenticatedUser var25;
      String[] var29;
      Map var31;
      Xid var87;
      String var91;
      NotificationListener var92;
      String var95;
      String var100;
      String[] var102;
      Xid[] var103;
      int var104;
      boolean var105;
      boolean var106;
      AuthenticatedUser var107;
      Map var108;
      switch (var1) {
         case 0:
            Object var89;
            try {
               MsgInput var7 = var2.getMsgInput();
               var92 = (NotificationListener)var7.readObject(class$weblogic$transaction$internal$NotificationListener == null ? (class$weblogic$transaction$internal$NotificationListener = class$("weblogic.transaction.internal.NotificationListener")) : class$weblogic$transaction$internal$NotificationListener);
               var89 = (Object)var7.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var85) {
               throw new UnmarshalException("error unmarshalling arguments", var85);
            } catch (ClassNotFoundException var86) {
               throw new UnmarshalException("error unmarshalling arguments", var86);
            }

            ((NotificationBroadcaster)var4).addNotificationListener(var92, var89);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            Xid[] var88;
            try {
               MsgInput var8 = var2.getMsgInput();
               var91 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var88 = (Xid[])var8.readObject(array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
            } catch (IOException var83) {
               throw new UnmarshalException("error unmarshalling arguments", var83);
            } catch (ClassNotFoundException var84) {
               throw new UnmarshalException("error unmarshalling arguments", var84);
            }

            ((SubCoordinator)var4).commit(var91, var88);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            try {
               MsgInput var6 = var2.getMsgInput();
               var87 = (Xid)var6.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
            } catch (IOException var81) {
               throw new UnmarshalException("error unmarshalling arguments", var81);
            } catch (ClassNotFoundException var82) {
               throw new UnmarshalException("error unmarshalling arguments", var82);
            }

            ((SubCoordinatorOneway3)var4).forceLocalCommit(var87);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            try {
               MsgInput var9 = var2.getMsgInput();
               var87 = (Xid)var9.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
            } catch (IOException var79) {
               throw new UnmarshalException("error unmarshalling arguments", var79);
            } catch (ClassNotFoundException var80) {
               throw new UnmarshalException("error unmarshalling arguments", var80);
            }

            ((SubCoordinatorOneway3)var4).forceLocalRollback(var87);
            this.associateResponseData(var2, var3);
            break;
         case 4:
            try {
               MsgInput var10 = var2.getMsgInput();
               var91 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var77) {
               throw new UnmarshalException("error unmarshalling arguments", var77);
            } catch (ClassNotFoundException var78) {
               throw new UnmarshalException("error unmarshalling arguments", var78);
            }

            Map var94 = ((SubCoordinatorRM)var4).getProperties(var91);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var94, class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
               break;
            } catch (IOException var76) {
               throw new MarshalException("error marshalling return", var76);
            }
         case 5:
            try {
               MsgInput var11 = var2.getMsgInput();
               var91 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var74) {
               throw new UnmarshalException("error unmarshalling arguments", var74);
            } catch (ClassNotFoundException var75) {
               throw new UnmarshalException("error unmarshalling arguments", var75);
            }

            Map var98 = ((SubCoordinator3)var4).getSubCoordinatorInfo(var91);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var98, class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
               break;
            } catch (IOException var73) {
               throw new MarshalException("error marshalling return", var73);
            }
         case 6:
            Notification var93;
            Object var97;
            try {
               MsgInput var13 = var2.getMsgInput();
               var93 = (Notification)var13.readObject(class$weblogic$transaction$internal$Notification == null ? (class$weblogic$transaction$internal$Notification = class$("weblogic.transaction.internal.Notification")) : class$weblogic$transaction$internal$Notification);
               var97 = (Object)var13.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var71) {
               throw new UnmarshalException("error unmarshalling arguments", var71);
            } catch (ClassNotFoundException var72) {
               throw new UnmarshalException("error unmarshalling arguments", var72);
            }

            ((NotificationListener)var4).handleNotification(var93, var97);
            this.associateResponseData(var2, var3);
            break;
         case 7:
            boolean var96;
            String var99;
            try {
               MsgInput var15 = var2.getMsgInput();
               var87 = (Xid)var15.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var96 = var15.readBoolean();
               var99 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var69) {
               throw new UnmarshalException("error unmarshalling arguments", var69);
            } catch (ClassNotFoundException var70) {
               throw new UnmarshalException("error unmarshalling arguments", var70);
            }

            ((SubCoordinator2)var4).nonXAResourceCommit(var87, var96, var99);
            this.associateResponseData(var2, var3);
            break;
         case 8:
            try {
               MsgInput var14 = var2.getMsgInput();
               var91 = (String)var14.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var95 = (String)var14.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var67) {
               throw new UnmarshalException("error unmarshalling arguments", var67);
            } catch (ClassNotFoundException var68) {
               throw new UnmarshalException("error unmarshalling arguments", var68);
            }

            var103 = ((SubCoordinator)var4).recover(var91, var95);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var103, array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
               break;
            } catch (IOException var66) {
               throw new MarshalException("error marshalling return", var66);
            }
         case 9:
            try {
               MsgInput var17 = var2.getMsgInput();
               var91 = (String)var17.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var95 = (String)var17.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var100 = (String)var17.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var64) {
               throw new UnmarshalException("error unmarshalling arguments", var64);
            } catch (ClassNotFoundException var65) {
               throw new UnmarshalException("error unmarshalling arguments", var65);
            }

            ((SubCoordinatorOneway7)var4).recoveryServiceMigrated(var91, var95, var100);
            this.associateResponseData(var2, var3);
            break;
         case 10:
            try {
               MsgInput var12 = var2.getMsgInput();
               var92 = (NotificationListener)var12.readObject(class$weblogic$transaction$internal$NotificationListener == null ? (class$weblogic$transaction$internal$NotificationListener = class$("weblogic.transaction.internal.NotificationListener")) : class$weblogic$transaction$internal$NotificationListener);
            } catch (IOException var62) {
               throw new UnmarshalException("error unmarshalling arguments", var62);
            } catch (ClassNotFoundException var63) {
               throw new UnmarshalException("error unmarshalling arguments", var63);
            }

            ((NotificationBroadcaster)var4).removeNotificationListener(var92);
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var18 = var2.getMsgInput();
               var91 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var103 = (Xid[])var18.readObject(array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
            } catch (IOException var60) {
               throw new UnmarshalException("error unmarshalling arguments", var60);
            } catch (ClassNotFoundException var61) {
               throw new UnmarshalException("error unmarshalling arguments", var61);
            }

            ((SubCoordinator)var4).rollback(var91, var103);
            this.associateResponseData(var2, var3);
            break;
         case 12:
            try {
               MsgInput var22 = var2.getMsgInput();
               var87 = (Xid)var22.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var100 = (String)var22.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var102 = (String[])var22.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var106 = var22.readBoolean();
               var105 = var22.readBoolean();
            } catch (IOException var58) {
               throw new UnmarshalException("error unmarshalling arguments", var58);
            } catch (ClassNotFoundException var59) {
               throw new UnmarshalException("error unmarshalling arguments", var59);
            }

            ((SubCoordinatorOneway)var4).startCommit(var87, var100, var102, var106, var105);
            this.associateResponseData(var2, var3);
            break;
         case 13:
            try {
               MsgInput var24 = var2.getMsgInput();
               var87 = (Xid)var24.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var100 = (String)var24.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var102 = (String[])var24.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var106 = var24.readBoolean();
               var105 = var24.readBoolean();
               var107 = (AuthenticatedUser)var24.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
            } catch (IOException var56) {
               throw new UnmarshalException("error unmarshalling arguments", var56);
            } catch (ClassNotFoundException var57) {
               throw new UnmarshalException("error unmarshalling arguments", var57);
            }

            ((SubCoordinatorOneway2)var4).startCommit(var87, var100, var102, var106, var105, var107);
            this.associateResponseData(var2, var3);
            break;
         case 14:
            try {
               MsgInput var26 = var2.getMsgInput();
               var87 = (Xid)var26.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var100 = (String)var26.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var102 = (String[])var26.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var106 = var26.readBoolean();
               var105 = var26.readBoolean();
               var107 = (AuthenticatedUser)var26.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
               var108 = (Map)var26.readObject(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
            } catch (IOException var54) {
               throw new UnmarshalException("error unmarshalling arguments", var54);
            } catch (ClassNotFoundException var55) {
               throw new UnmarshalException("error unmarshalling arguments", var55);
            }

            ((SubCoordinatorOneway5)var4).startCommit(var87, var100, var102, var106, var105, var107, var108);
            this.associateResponseData(var2, var3);
            break;
         case 15:
            PropagationContext var90;
            int var101;
            try {
               MsgInput var19 = var2.getMsgInput();
               var90 = (PropagationContext)var19.readObject(class$weblogic$transaction$internal$PropagationContext == null ? (class$weblogic$transaction$internal$PropagationContext = class$("weblogic.transaction.internal.PropagationContext")) : class$weblogic$transaction$internal$PropagationContext);
               var101 = var19.readInt();
            } catch (IOException var52) {
               throw new UnmarshalException("error unmarshalling arguments", var52);
            } catch (ClassNotFoundException var53) {
               throw new UnmarshalException("error unmarshalling arguments", var53);
            }

            ((SubCoordinatorOneway)var4).startPrePrepareAndChain(var90, var101);
            this.associateResponseData(var2, var3);
            break;
         case 16:
            try {
               MsgInput var23 = var2.getMsgInput();
               var87 = (Xid)var23.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var100 = (String)var23.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var20 = (String[])var23.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var104 = var23.readInt();
            } catch (IOException var50) {
               throw new UnmarshalException("error unmarshalling arguments", var50);
            } catch (ClassNotFoundException var51) {
               throw new UnmarshalException("error unmarshalling arguments", var51);
            }

            ((SubCoordinatorOneway)var4).startPrepare(var87, var100, var20, var104);
            this.associateResponseData(var2, var3);
            break;
         case 17:
            try {
               MsgInput var27 = var2.getMsgInput();
               var87 = (Xid)var27.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var100 = (String)var27.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var20 = (String[])var27.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var104 = var27.readInt();
               var108 = (Map)var27.readObject(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
            } catch (IOException var48) {
               throw new UnmarshalException("error unmarshalling arguments", var48);
            } catch (ClassNotFoundException var49) {
               throw new UnmarshalException("error unmarshalling arguments", var49);
            }

            ((SubCoordinatorOneway5)var4).startPrepare(var87, var100, var20, var104, var108);
            this.associateResponseData(var2, var3);
            break;
         case 18:
            try {
               MsgInput var21 = var2.getMsgInput();
               var87 = (Xid)var21.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var100 = (String)var21.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var20 = (String[])var21.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var46) {
               throw new UnmarshalException("error unmarshalling arguments", var46);
            } catch (ClassNotFoundException var47) {
               throw new UnmarshalException("error unmarshalling arguments", var47);
            }

            ((SubCoordinatorOneway)var4).startRollback(var87, var100, var20);
            this.associateResponseData(var2, var3);
            break;
         case 19:
            try {
               MsgInput var28 = var2.getMsgInput();
               var87 = (Xid)var28.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var100 = (String)var28.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var20 = (String[])var28.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var25 = (AuthenticatedUser)var28.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
            } catch (IOException var44) {
               throw new UnmarshalException("error unmarshalling arguments", var44);
            } catch (ClassNotFoundException var45) {
               throw new UnmarshalException("error unmarshalling arguments", var45);
            }

            ((SubCoordinatorOneway2)var4).startRollback(var87, var100, var20, var25);
            this.associateResponseData(var2, var3);
            break;
         case 20:
            try {
               MsgInput var30 = var2.getMsgInput();
               var87 = (Xid)var30.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var100 = (String)var30.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var20 = (String[])var30.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var25 = (AuthenticatedUser)var30.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
               var29 = (String[])var30.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var42) {
               throw new UnmarshalException("error unmarshalling arguments", var42);
            } catch (ClassNotFoundException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            }

            ((SubCoordinatorOneway4)var4).startRollback(var87, var100, var20, var25, var29);
            this.associateResponseData(var2, var3);
            break;
         case 21:
            try {
               MsgInput var32 = var2.getMsgInput();
               var87 = (Xid)var32.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var100 = (String)var32.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var20 = (String[])var32.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var25 = (AuthenticatedUser)var32.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
               var29 = (String[])var32.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var31 = (Map)var32.readObject(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
            } catch (IOException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            } catch (ClassNotFoundException var41) {
               throw new UnmarshalException("error unmarshalling arguments", var41);
            }

            ((SubCoordinatorOneway5)var4).startRollback(var87, var100, var20, var25, var29, var31);
            this.associateResponseData(var2, var3);
            break;
         case 22:
            boolean var33;
            try {
               MsgInput var34 = var2.getMsgInput();
               var87 = (Xid)var34.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var100 = (String)var34.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var20 = (String[])var34.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var25 = (AuthenticatedUser)var34.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
               var29 = (String[])var34.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var31 = (Map)var34.readObject(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
               var33 = var34.readBoolean();
            } catch (IOException var38) {
               throw new UnmarshalException("error unmarshalling arguments", var38);
            } catch (ClassNotFoundException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            }

            ((SubCoordinatorOneway6)var4).startRollback(var87, var100, var20, var25, var29, var31, var33);
            this.associateResponseData(var2, var3);
            break;
         case 23:
            Xid[] var5;
            try {
               MsgInput var16 = var2.getMsgInput();
               var5 = (Xid[])var16.readObject(array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
            } catch (IOException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            } catch (ClassNotFoundException var37) {
               throw new UnmarshalException("error unmarshalling arguments", var37);
            }

            ((SubCoordinatorOneway)var4).startRollback(var5);
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
            ((NotificationBroadcaster)var3).addNotificationListener((NotificationListener)var2[0], (Object)var2[1]);
            return null;
         case 1:
            ((SubCoordinator)var3).commit((String)var2[0], (Xid[])var2[1]);
            return null;
         case 2:
            ((SubCoordinatorOneway3)var3).forceLocalCommit((Xid)var2[0]);
            return null;
         case 3:
            ((SubCoordinatorOneway3)var3).forceLocalRollback((Xid)var2[0]);
            return null;
         case 4:
            return ((SubCoordinatorRM)var3).getProperties((String)var2[0]);
         case 5:
            return ((SubCoordinator3)var3).getSubCoordinatorInfo((String)var2[0]);
         case 6:
            ((NotificationListener)var3).handleNotification((Notification)var2[0], (Object)var2[1]);
            return null;
         case 7:
            ((SubCoordinator2)var3).nonXAResourceCommit((Xid)var2[0], (Boolean)var2[1], (String)var2[2]);
            return null;
         case 8:
            return ((SubCoordinator)var3).recover((String)var2[0], (String)var2[1]);
         case 9:
            ((SubCoordinatorOneway7)var3).recoveryServiceMigrated((String)var2[0], (String)var2[1], (String)var2[2]);
            return null;
         case 10:
            ((NotificationBroadcaster)var3).removeNotificationListener((NotificationListener)var2[0]);
            return null;
         case 11:
            ((SubCoordinator)var3).rollback((String)var2[0], (Xid[])var2[1]);
            return null;
         case 12:
            ((SubCoordinatorOneway)var3).startCommit((Xid)var2[0], (String)var2[1], (String[])var2[2], (Boolean)var2[3], (Boolean)var2[4]);
            return null;
         case 13:
            ((SubCoordinatorOneway2)var3).startCommit((Xid)var2[0], (String)var2[1], (String[])var2[2], (Boolean)var2[3], (Boolean)var2[4], (AuthenticatedUser)var2[5]);
            return null;
         case 14:
            ((SubCoordinatorOneway5)var3).startCommit((Xid)var2[0], (String)var2[1], (String[])var2[2], (Boolean)var2[3], (Boolean)var2[4], (AuthenticatedUser)var2[5], (Map)var2[6]);
            return null;
         case 15:
            ((SubCoordinatorOneway)var3).startPrePrepareAndChain((PropagationContext)var2[0], (Integer)var2[1]);
            return null;
         case 16:
            ((SubCoordinatorOneway)var3).startPrepare((Xid)var2[0], (String)var2[1], (String[])var2[2], (Integer)var2[3]);
            return null;
         case 17:
            ((SubCoordinatorOneway5)var3).startPrepare((Xid)var2[0], (String)var2[1], (String[])var2[2], (Integer)var2[3], (Map)var2[4]);
            return null;
         case 18:
            ((SubCoordinatorOneway)var3).startRollback((Xid)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 19:
            ((SubCoordinatorOneway2)var3).startRollback((Xid)var2[0], (String)var2[1], (String[])var2[2], (AuthenticatedUser)var2[3]);
            return null;
         case 20:
            ((SubCoordinatorOneway4)var3).startRollback((Xid)var2[0], (String)var2[1], (String[])var2[2], (AuthenticatedUser)var2[3], (String[])var2[4]);
            return null;
         case 21:
            ((SubCoordinatorOneway5)var3).startRollback((Xid)var2[0], (String)var2[1], (String[])var2[2], (AuthenticatedUser)var2[3], (String[])var2[4], (Map)var2[5]);
            return null;
         case 22:
            ((SubCoordinatorOneway6)var3).startRollback((Xid)var2[0], (String)var2[1], (String[])var2[2], (AuthenticatedUser)var2[3], (String[])var2[4], (Map)var2[5], (Boolean)var2[6]);
            return null;
         case 23:
            ((SubCoordinatorOneway)var3).startRollback((Xid[])var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
