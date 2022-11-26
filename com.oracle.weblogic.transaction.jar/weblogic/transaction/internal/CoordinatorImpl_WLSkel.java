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
import weblogic.transaction.CoordinatorService;

public final class CoordinatorImpl_WLSkel extends Skeleton {
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
      Xid var5;
      Xid[] var156;
      PropagationContext var157;
      String var158;
      Xid[] var159;
      String var160;
      String var164;
      NotificationListener var165;
      String var171;
      Object var173;
      short var176;
      String var177;
      Xid[] var180;
      String[] var182;
      String var184;
      String[] var185;
      String[] var186;
      int var188;
      boolean var189;
      boolean var190;
      AuthenticatedUser var191;
      AuthenticatedUser var192;
      Map var193;
      String[] var194;
      Map var195;
      switch (var1) {
         case 0:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (Xid)var7.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var158 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var153) {
               throw new UnmarshalException("error unmarshalling arguments", var153);
            } catch (ClassNotFoundException var154) {
               throw new UnmarshalException("error unmarshalling arguments", var154);
            }

            ((CoordinatorOneway)var4).ackCommit(var5, var158);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            String[] var163;
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (Xid)var9.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var158 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var163 = (String[])var9.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var151) {
               throw new UnmarshalException("error unmarshalling arguments", var151);
            } catch (ClassNotFoundException var152) {
               throw new UnmarshalException("error unmarshalling arguments", var152);
            }

            ((CoordinatorOneway2)var4).ackCommit(var5, var158, var163);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            try {
               MsgInput var6 = var2.getMsgInput();
               var157 = (PropagationContext)var6.readObject(class$weblogic$transaction$internal$PropagationContext == null ? (class$weblogic$transaction$internal$PropagationContext = class$("weblogic.transaction.internal.PropagationContext")) : class$weblogic$transaction$internal$PropagationContext);
            } catch (IOException var149) {
               throw new UnmarshalException("error unmarshalling arguments", var149);
            } catch (ClassNotFoundException var150) {
               throw new UnmarshalException("error unmarshalling arguments", var150);
            }

            ((CoordinatorOneway)var4).ackPrePrepare(var157);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            int var162;
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = (Xid)var11.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var160 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var162 = var11.readInt();
            } catch (IOException var147) {
               throw new UnmarshalException("error unmarshalling arguments", var147);
            } catch (ClassNotFoundException var148) {
               throw new UnmarshalException("error unmarshalling arguments", var148);
            }

            ((CoordinatorOneway)var4).ackPrepare(var5, var160, var162);
            this.associateResponseData(var2, var3);
            break;
         case 4:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (Xid)var10.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var160 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var145) {
               throw new UnmarshalException("error unmarshalling arguments", var145);
            } catch (ClassNotFoundException var146) {
               throw new UnmarshalException("error unmarshalling arguments", var146);
            }

            ((CoordinatorOneway)var4).ackRollback(var5, var160);
            this.associateResponseData(var2, var3);
            break;
         case 5:
            String[] var166;
            try {
               MsgInput var13 = var2.getMsgInput();
               var5 = (Xid)var13.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var160 = (String)var13.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var166 = (String[])var13.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var143) {
               throw new UnmarshalException("error unmarshalling arguments", var143);
            } catch (ClassNotFoundException var144) {
               throw new UnmarshalException("error unmarshalling arguments", var144);
            }

            ((CoordinatorOneway2)var4).ackRollback(var5, var160, var166);
            this.associateResponseData(var2, var3);
            break;
         case 6:
            Object var161;
            try {
               MsgInput var12 = var2.getMsgInput();
               var165 = (NotificationListener)var12.readObject(class$weblogic$transaction$internal$NotificationListener == null ? (class$weblogic$transaction$internal$NotificationListener = class$("weblogic.transaction.internal.NotificationListener")) : class$weblogic$transaction$internal$NotificationListener);
               var161 = (Object)var12.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var141) {
               throw new UnmarshalException("error unmarshalling arguments", var141);
            } catch (ClassNotFoundException var142) {
               throw new UnmarshalException("error unmarshalling arguments", var142);
            }

            ((NotificationBroadcaster)var4).addNotificationListener(var165, var161);
            this.associateResponseData(var2, var3);
            break;
         case 7:
            String var167;
            try {
               MsgInput var15 = var2.getMsgInput();
               var164 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var159 = (Xid[])var15.readObject(array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
               var167 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var139) {
               throw new UnmarshalException("error unmarshalling arguments", var139);
            } catch (ClassNotFoundException var140) {
               throw new UnmarshalException("error unmarshalling arguments", var140);
            }

            ((CoordinatorOneway3)var4).checkStatus(var164, var159, var167);
            this.associateResponseData(var2, var3);
            break;
         case 8:
            try {
               MsgInput var14 = var2.getMsgInput();
               var156 = (Xid[])var14.readObject(array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
               var160 = (String)var14.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var137) {
               throw new UnmarshalException("error unmarshalling arguments", var137);
            } catch (ClassNotFoundException var138) {
               throw new UnmarshalException("error unmarshalling arguments", var138);
            }

            ((CoordinatorOneway)var4).checkStatus(var156, var160);
            this.associateResponseData(var2, var3);
            break;
         case 9:
            try {
               MsgInput var16 = var2.getMsgInput();
               var164 = (String)var16.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var159 = (Xid[])var16.readObject(array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
            } catch (IOException var135) {
               throw new UnmarshalException("error unmarshalling arguments", var135);
            } catch (ClassNotFoundException var136) {
               throw new UnmarshalException("error unmarshalling arguments", var136);
            }

            ((SubCoordinator)var4).commit(var164, var159);
            this.associateResponseData(var2, var3);
            break;
         case 10:
            try {
               MsgInput var8 = var2.getMsgInput();
               var157 = (PropagationContext)var8.readObject(class$weblogic$transaction$internal$PropagationContext == null ? (class$weblogic$transaction$internal$PropagationContext = class$("weblogic.transaction.internal.PropagationContext")) : class$weblogic$transaction$internal$PropagationContext);
            } catch (IOException var133) {
               throw new UnmarshalException("error unmarshalling arguments", var133);
            } catch (ClassNotFoundException var134) {
               throw new UnmarshalException("error unmarshalling arguments", var134);
            }

            ((Coordinator)var4).commit(var157);
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var17 = var2.getMsgInput();
               var5 = (Xid)var17.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
            } catch (IOException var131) {
               throw new UnmarshalException("error unmarshalling arguments", var131);
            } catch (ClassNotFoundException var132) {
               throw new UnmarshalException("error unmarshalling arguments", var132);
            }

            ((Coordinator3)var4).forceGlobalCommit(var5);
            this.associateResponseData(var2, var3);
            break;
         case 12:
            try {
               MsgInput var18 = var2.getMsgInput();
               var5 = (Xid)var18.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
            } catch (IOException var129) {
               throw new UnmarshalException("error unmarshalling arguments", var129);
            } catch (ClassNotFoundException var130) {
               throw new UnmarshalException("error unmarshalling arguments", var130);
            }

            ((Coordinator3)var4).forceGlobalRollback(var5);
            this.associateResponseData(var2, var3);
            break;
         case 13:
            try {
               MsgInput var19 = var2.getMsgInput();
               var5 = (Xid)var19.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
            } catch (IOException var127) {
               throw new UnmarshalException("error unmarshalling arguments", var127);
            } catch (ClassNotFoundException var128) {
               throw new UnmarshalException("error unmarshalling arguments", var128);
            }

            ((SubCoordinatorOneway3)var4).forceLocalCommit(var5);
            this.associateResponseData(var2, var3);
            break;
         case 14:
            try {
               MsgInput var20 = var2.getMsgInput();
               var5 = (Xid)var20.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
            } catch (IOException var125) {
               throw new UnmarshalException("error unmarshalling arguments", var125);
            } catch (ClassNotFoundException var126) {
               throw new UnmarshalException("error unmarshalling arguments", var126);
            }

            ((SubCoordinatorOneway3)var4).forceLocalRollback(var5);
            this.associateResponseData(var2, var3);
            break;
         case 15:
            Map var169 = ((Coordinator2)var4).getProperties();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var169, class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
               break;
            } catch (IOException var124) {
               throw new MarshalException("error marshalling return", var124);
            }
         case 16:
            try {
               MsgInput var21 = var2.getMsgInput();
               var164 = (String)var21.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var122) {
               throw new UnmarshalException("error unmarshalling arguments", var122);
            } catch (ClassNotFoundException var123) {
               throw new UnmarshalException("error unmarshalling arguments", var123);
            }

            Map var170 = ((SubCoordinatorRM)var4).getProperties(var164);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var170, class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
               break;
            } catch (IOException var121) {
               throw new MarshalException("error marshalling return", var121);
            }
         case 17:
            try {
               MsgInput var22 = var2.getMsgInput();
               var164 = (String)var22.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var119) {
               throw new UnmarshalException("error unmarshalling arguments", var119);
            } catch (ClassNotFoundException var120) {
               throw new UnmarshalException("error unmarshalling arguments", var120);
            }

            Map var175 = ((SubCoordinator3)var4).getSubCoordinatorInfo(var164);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var175, class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
               break;
            } catch (IOException var118) {
               throw new MarshalException("error marshalling return", var118);
            }
         case 18:
            Notification var168;
            try {
               MsgInput var24 = var2.getMsgInput();
               var168 = (Notification)var24.readObject(class$weblogic$transaction$internal$Notification == null ? (class$weblogic$transaction$internal$Notification = class$("weblogic.transaction.internal.Notification")) : class$weblogic$transaction$internal$Notification);
               var173 = (Object)var24.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var116) {
               throw new UnmarshalException("error unmarshalling arguments", var116);
            } catch (ClassNotFoundException var117) {
               throw new UnmarshalException("error unmarshalling arguments", var117);
            }

            ((NotificationListener)var4).handleNotification(var168, var173);
            this.associateResponseData(var2, var3);
            break;
         case 19:
            try {
               MsgInput var25 = var2.getMsgInput();
               var164 = (String)var25.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var173 = (Object)var25.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var114) {
               throw new UnmarshalException("error unmarshalling arguments", var114);
            } catch (ClassNotFoundException var115) {
               throw new UnmarshalException("error unmarshalling arguments", var115);
            }

            Object var178 = ((CoordinatorService)var4).invokeCoordinatorService(var164, var173);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var178, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var113) {
               throw new MarshalException("error marshalling return", var113);
            }
         case 20:
            try {
               MsgInput var28 = var2.getMsgInput();
               var5 = (Xid)var28.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var171 = (String)var28.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var176 = var28.readShort();
               var177 = (String)var28.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var111) {
               throw new UnmarshalException("error unmarshalling arguments", var111);
            } catch (ClassNotFoundException var112) {
               throw new UnmarshalException("error unmarshalling arguments", var112);
            }

            ((CoordinatorOneway)var4).nakCommit(var5, var171, var176, var177);
            this.associateResponseData(var2, var3);
            break;
         case 21:
            String[] var179;
            try {
               MsgInput var31 = var2.getMsgInput();
               var5 = (Xid)var31.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var171 = (String)var31.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var176 = var31.readShort();
               var177 = (String)var31.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var179 = (String[])var31.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var182 = (String[])var31.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var109) {
               throw new UnmarshalException("error unmarshalling arguments", var109);
            } catch (ClassNotFoundException var110) {
               throw new UnmarshalException("error unmarshalling arguments", var110);
            }

            ((CoordinatorOneway2)var4).nakCommit(var5, var171, var176, var177, var179, var182);
            this.associateResponseData(var2, var3);
            break;
         case 22:
            try {
               MsgInput var29 = var2.getMsgInput();
               var5 = (Xid)var29.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var171 = (String)var29.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var176 = var29.readShort();
               var177 = (String)var29.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var107) {
               throw new UnmarshalException("error unmarshalling arguments", var107);
            } catch (ClassNotFoundException var108) {
               throw new UnmarshalException("error unmarshalling arguments", var108);
            }

            ((CoordinatorOneway)var4).nakRollback(var5, var171, var176, var177);
            this.associateResponseData(var2, var3);
            break;
         case 23:
            String[] var183;
            try {
               MsgInput var33 = var2.getMsgInput();
               var5 = (Xid)var33.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var171 = (String)var33.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var176 = var33.readShort();
               var177 = (String)var33.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var182 = (String[])var33.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var183 = (String[])var33.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var105) {
               throw new UnmarshalException("error unmarshalling arguments", var105);
            } catch (ClassNotFoundException var106) {
               throw new UnmarshalException("error unmarshalling arguments", var106);
            }

            ((CoordinatorOneway2)var4).nakRollback(var5, var171, var176, var177, var182, var183);
            this.associateResponseData(var2, var3);
            break;
         case 24:
            boolean var172;
            String var174;
            try {
               MsgInput var27 = var2.getMsgInput();
               var5 = (Xid)var27.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var172 = var27.readBoolean();
               var174 = (String)var27.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var103) {
               throw new UnmarshalException("error unmarshalling arguments", var103);
            } catch (ClassNotFoundException var104) {
               throw new UnmarshalException("error unmarshalling arguments", var104);
            }

            ((SubCoordinator2)var4).nonXAResourceCommit(var5, var172, var174);
            this.associateResponseData(var2, var3);
            break;
         case 25:
            try {
               MsgInput var26 = var2.getMsgInput();
               var164 = (String)var26.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var171 = (String)var26.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var101) {
               throw new UnmarshalException("error unmarshalling arguments", var101);
            } catch (ClassNotFoundException var102) {
               throw new UnmarshalException("error unmarshalling arguments", var102);
            }

            var180 = ((SubCoordinator)var4).recover(var164, var171);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var180, array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
               break;
            } catch (IOException var100) {
               throw new MarshalException("error marshalling return", var100);
            }
         case 26:
            String var181;
            try {
               MsgInput var32 = var2.getMsgInput();
               var164 = (String)var32.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var171 = (String)var32.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var181 = (String)var32.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var98) {
               throw new UnmarshalException("error unmarshalling arguments", var98);
            } catch (ClassNotFoundException var99) {
               throw new UnmarshalException("error unmarshalling arguments", var99);
            }

            ((SubCoordinatorOneway7)var4).recoveryServiceMigrated(var164, var171, var181);
            this.associateResponseData(var2, var3);
            break;
         case 27:
            try {
               MsgInput var23 = var2.getMsgInput();
               var165 = (NotificationListener)var23.readObject(class$weblogic$transaction$internal$NotificationListener == null ? (class$weblogic$transaction$internal$NotificationListener = class$("weblogic.transaction.internal.NotificationListener")) : class$weblogic$transaction$internal$NotificationListener);
            } catch (IOException var96) {
               throw new UnmarshalException("error unmarshalling arguments", var96);
            } catch (ClassNotFoundException var97) {
               throw new UnmarshalException("error unmarshalling arguments", var97);
            }

            ((NotificationBroadcaster)var4).removeNotificationListener(var165);
            this.associateResponseData(var2, var3);
            break;
         case 28:
            try {
               MsgInput var34 = var2.getMsgInput();
               var164 = (String)var34.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var180 = (Xid[])var34.readObject(array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
            } catch (IOException var94) {
               throw new UnmarshalException("error unmarshalling arguments", var94);
            } catch (ClassNotFoundException var95) {
               throw new UnmarshalException("error unmarshalling arguments", var95);
            }

            ((SubCoordinator)var4).rollback(var164, var180);
            this.associateResponseData(var2, var3);
            break;
         case 29:
            try {
               MsgInput var30 = var2.getMsgInput();
               var157 = (PropagationContext)var30.readObject(class$weblogic$transaction$internal$PropagationContext == null ? (class$weblogic$transaction$internal$PropagationContext = class$("weblogic.transaction.internal.PropagationContext")) : class$weblogic$transaction$internal$PropagationContext);
            } catch (IOException var92) {
               throw new UnmarshalException("error unmarshalling arguments", var92);
            } catch (ClassNotFoundException var93) {
               throw new UnmarshalException("error unmarshalling arguments", var93);
            }

            ((Coordinator)var4).rollback(var157);
            this.associateResponseData(var2, var3);
            break;
         case 30:
            try {
               MsgInput var39 = var2.getMsgInput();
               var5 = (Xid)var39.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var184 = (String)var39.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var185 = (String[])var39.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var190 = var39.readBoolean();
               var189 = var39.readBoolean();
            } catch (IOException var90) {
               throw new UnmarshalException("error unmarshalling arguments", var90);
            } catch (ClassNotFoundException var91) {
               throw new UnmarshalException("error unmarshalling arguments", var91);
            }

            ((SubCoordinatorOneway)var4).startCommit(var5, var184, var185, var190, var189);
            this.associateResponseData(var2, var3);
            break;
         case 31:
            try {
               MsgInput var41 = var2.getMsgInput();
               var5 = (Xid)var41.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var184 = (String)var41.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var185 = (String[])var41.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var190 = var41.readBoolean();
               var189 = var41.readBoolean();
               var191 = (AuthenticatedUser)var41.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
            } catch (IOException var88) {
               throw new UnmarshalException("error unmarshalling arguments", var88);
            } catch (ClassNotFoundException var89) {
               throw new UnmarshalException("error unmarshalling arguments", var89);
            }

            ((SubCoordinatorOneway2)var4).startCommit(var5, var184, var185, var190, var189, var191);
            this.associateResponseData(var2, var3);
            break;
         case 32:
            try {
               MsgInput var43 = var2.getMsgInput();
               var5 = (Xid)var43.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var184 = (String)var43.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var185 = (String[])var43.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var190 = var43.readBoolean();
               var189 = var43.readBoolean();
               var191 = (AuthenticatedUser)var43.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
               var193 = (Map)var43.readObject(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
            } catch (IOException var86) {
               throw new UnmarshalException("error unmarshalling arguments", var86);
            } catch (ClassNotFoundException var87) {
               throw new UnmarshalException("error unmarshalling arguments", var87);
            }

            ((SubCoordinatorOneway5)var4).startCommit(var5, var184, var185, var190, var189, var191, var193);
            this.associateResponseData(var2, var3);
            break;
         case 33:
            int var187;
            try {
               MsgInput var36 = var2.getMsgInput();
               var157 = (PropagationContext)var36.readObject(class$weblogic$transaction$internal$PropagationContext == null ? (class$weblogic$transaction$internal$PropagationContext = class$("weblogic.transaction.internal.PropagationContext")) : class$weblogic$transaction$internal$PropagationContext);
               var187 = var36.readInt();
            } catch (IOException var84) {
               throw new UnmarshalException("error unmarshalling arguments", var84);
            } catch (ClassNotFoundException var85) {
               throw new UnmarshalException("error unmarshalling arguments", var85);
            }

            ((SubCoordinatorOneway)var4).startPrePrepareAndChain(var157, var187);
            this.associateResponseData(var2, var3);
            break;
         case 34:
            try {
               MsgInput var40 = var2.getMsgInput();
               var5 = (Xid)var40.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var184 = (String)var40.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var186 = (String[])var40.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var188 = var40.readInt();
            } catch (IOException var82) {
               throw new UnmarshalException("error unmarshalling arguments", var82);
            } catch (ClassNotFoundException var83) {
               throw new UnmarshalException("error unmarshalling arguments", var83);
            }

            ((SubCoordinatorOneway)var4).startPrepare(var5, var184, var186, var188);
            this.associateResponseData(var2, var3);
            break;
         case 35:
            try {
               MsgInput var44 = var2.getMsgInput();
               var5 = (Xid)var44.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var184 = (String)var44.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var186 = (String[])var44.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var188 = var44.readInt();
               var193 = (Map)var44.readObject(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
            } catch (IOException var80) {
               throw new UnmarshalException("error unmarshalling arguments", var80);
            } catch (ClassNotFoundException var81) {
               throw new UnmarshalException("error unmarshalling arguments", var81);
            }

            ((SubCoordinatorOneway5)var4).startPrepare(var5, var184, var186, var188, var193);
            this.associateResponseData(var2, var3);
            break;
         case 36:
            try {
               MsgInput var38 = var2.getMsgInput();
               var5 = (Xid)var38.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var184 = (String)var38.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var186 = (String[])var38.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var78) {
               throw new UnmarshalException("error unmarshalling arguments", var78);
            } catch (ClassNotFoundException var79) {
               throw new UnmarshalException("error unmarshalling arguments", var79);
            }

            ((SubCoordinatorOneway)var4).startRollback(var5, var184, var186);
            this.associateResponseData(var2, var3);
            break;
         case 37:
            try {
               MsgInput var45 = var2.getMsgInput();
               var5 = (Xid)var45.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var184 = (String)var45.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var186 = (String[])var45.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var192 = (AuthenticatedUser)var45.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
            } catch (IOException var76) {
               throw new UnmarshalException("error unmarshalling arguments", var76);
            } catch (ClassNotFoundException var77) {
               throw new UnmarshalException("error unmarshalling arguments", var77);
            }

            ((SubCoordinatorOneway2)var4).startRollback(var5, var184, var186, var192);
            this.associateResponseData(var2, var3);
            break;
         case 38:
            try {
               MsgInput var47 = var2.getMsgInput();
               var5 = (Xid)var47.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var184 = (String)var47.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var186 = (String[])var47.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var192 = (AuthenticatedUser)var47.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
               var194 = (String[])var47.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var74) {
               throw new UnmarshalException("error unmarshalling arguments", var74);
            } catch (ClassNotFoundException var75) {
               throw new UnmarshalException("error unmarshalling arguments", var75);
            }

            ((SubCoordinatorOneway4)var4).startRollback(var5, var184, var186, var192, var194);
            this.associateResponseData(var2, var3);
            break;
         case 39:
            try {
               MsgInput var49 = var2.getMsgInput();
               var5 = (Xid)var49.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var184 = (String)var49.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var186 = (String[])var49.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var192 = (AuthenticatedUser)var49.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
               var194 = (String[])var49.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var195 = (Map)var49.readObject(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
            } catch (IOException var72) {
               throw new UnmarshalException("error unmarshalling arguments", var72);
            } catch (ClassNotFoundException var73) {
               throw new UnmarshalException("error unmarshalling arguments", var73);
            }

            ((SubCoordinatorOneway5)var4).startRollback(var5, var184, var186, var192, var194, var195);
            this.associateResponseData(var2, var3);
            break;
         case 40:
            boolean var197;
            try {
               MsgInput var51 = var2.getMsgInput();
               var5 = (Xid)var51.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
               var184 = (String)var51.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var186 = (String[])var51.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var192 = (AuthenticatedUser)var51.readObject(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
               var194 = (String[])var51.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               var195 = (Map)var51.readObject(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
               var197 = var51.readBoolean();
            } catch (IOException var70) {
               throw new UnmarshalException("error unmarshalling arguments", var70);
            } catch (ClassNotFoundException var71) {
               throw new UnmarshalException("error unmarshalling arguments", var71);
            }

            ((SubCoordinatorOneway6)var4).startRollback(var5, var184, var186, var192, var194, var195, var197);
            this.associateResponseData(var2, var3);
            break;
         case 41:
            try {
               MsgInput var35 = var2.getMsgInput();
               var157 = (PropagationContext)var35.readObject(class$weblogic$transaction$internal$PropagationContext == null ? (class$weblogic$transaction$internal$PropagationContext = class$("weblogic.transaction.internal.PropagationContext")) : class$weblogic$transaction$internal$PropagationContext);
            } catch (IOException var68) {
               throw new UnmarshalException("error unmarshalling arguments", var68);
            } catch (ClassNotFoundException var69) {
               throw new UnmarshalException("error unmarshalling arguments", var69);
            }

            ((CoordinatorOneway)var4).startRollback(var157);
            this.associateResponseData(var2, var3);
            break;
         case 42:
            try {
               MsgInput var37 = var2.getMsgInput();
               var156 = (Xid[])var37.readObject(array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
            } catch (IOException var66) {
               throw new UnmarshalException("error unmarshalling arguments", var66);
            } catch (ClassNotFoundException var67) {
               throw new UnmarshalException("error unmarshalling arguments", var67);
            }

            ((SubCoordinatorOneway)var4).startRollback(var156);
            this.associateResponseData(var2, var3);
            break;
         case 43:
            try {
               MsgInput var42 = var2.getMsgInput();
               var5 = (Xid)var42.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
            } catch (IOException var64) {
               throw new UnmarshalException("error unmarshalling arguments", var64);
            } catch (ClassNotFoundException var65) {
               throw new UnmarshalException("error unmarshalling arguments", var65);
            }

            ((Coordinator2)var4).xaCommit(var5);
            this.associateResponseData(var2, var3);
            break;
         case 44:
            try {
               MsgInput var46 = var2.getMsgInput();
               var5 = (Xid)var46.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
            } catch (IOException var62) {
               throw new UnmarshalException("error unmarshalling arguments", var62);
            } catch (ClassNotFoundException var63) {
               throw new UnmarshalException("error unmarshalling arguments", var63);
            }

            ((Coordinator2)var4).xaForget(var5);
            this.associateResponseData(var2, var3);
            break;
         case 45:
            try {
               MsgInput var48 = var2.getMsgInput();
               var157 = (PropagationContext)var48.readObject(class$weblogic$transaction$internal$PropagationContext == null ? (class$weblogic$transaction$internal$PropagationContext = class$("weblogic.transaction.internal.PropagationContext")) : class$weblogic$transaction$internal$PropagationContext);
            } catch (IOException var60) {
               throw new UnmarshalException("error unmarshalling arguments", var60);
            } catch (ClassNotFoundException var61) {
               throw new UnmarshalException("error unmarshalling arguments", var61);
            }

            int var196 = ((Coordinator2)var4).xaPrepare(var157);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var196);
               break;
            } catch (IOException var59) {
               throw new MarshalException("error marshalling return", var59);
            }
         case 46:
            var156 = ((Coordinator2)var4).xaRecover();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var156, array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
               break;
            } catch (IOException var58) {
               throw new MarshalException("error marshalling return", var58);
            }
         case 47:
            int var155;
            try {
               MsgInput var50 = var2.getMsgInput();
               var155 = var50.readInt();
            } catch (IOException var57) {
               throw new UnmarshalException("error unmarshalling arguments", var57);
            }

            Xid[] var198 = ((Coordinator2)var4).xaRecover(var155);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var198, array$Ljavax$transaction$xa$Xid == null ? (array$Ljavax$transaction$xa$Xid = class$("[Ljavax.transaction.xa.Xid;")) : array$Ljavax$transaction$xa$Xid);
               break;
            } catch (IOException var56) {
               throw new MarshalException("error marshalling return", var56);
            }
         case 48:
            try {
               MsgInput var52 = var2.getMsgInput();
               var5 = (Xid)var52.readObject(class$javax$transaction$xa$Xid == null ? (class$javax$transaction$xa$Xid = class$("javax.transaction.xa.Xid")) : class$javax$transaction$xa$Xid);
            } catch (IOException var54) {
               throw new UnmarshalException("error unmarshalling arguments", var54);
            } catch (ClassNotFoundException var55) {
               throw new UnmarshalException("error unmarshalling arguments", var55);
            }

            ((Coordinator2)var4).xaRollback(var5);
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
            ((CoordinatorOneway)var3).ackCommit((Xid)var2[0], (String)var2[1]);
            return null;
         case 1:
            ((CoordinatorOneway2)var3).ackCommit((Xid)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 2:
            ((CoordinatorOneway)var3).ackPrePrepare((PropagationContext)var2[0]);
            return null;
         case 3:
            ((CoordinatorOneway)var3).ackPrepare((Xid)var2[0], (String)var2[1], (Integer)var2[2]);
            return null;
         case 4:
            ((CoordinatorOneway)var3).ackRollback((Xid)var2[0], (String)var2[1]);
            return null;
         case 5:
            ((CoordinatorOneway2)var3).ackRollback((Xid)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 6:
            ((NotificationBroadcaster)var3).addNotificationListener((NotificationListener)var2[0], (Object)var2[1]);
            return null;
         case 7:
            ((CoordinatorOneway3)var3).checkStatus((String)var2[0], (Xid[])var2[1], (String)var2[2]);
            return null;
         case 8:
            ((CoordinatorOneway)var3).checkStatus((Xid[])var2[0], (String)var2[1]);
            return null;
         case 9:
            ((SubCoordinator)var3).commit((String)var2[0], (Xid[])var2[1]);
            return null;
         case 10:
            ((Coordinator)var3).commit((PropagationContext)var2[0]);
            return null;
         case 11:
            ((Coordinator3)var3).forceGlobalCommit((Xid)var2[0]);
            return null;
         case 12:
            ((Coordinator3)var3).forceGlobalRollback((Xid)var2[0]);
            return null;
         case 13:
            ((SubCoordinatorOneway3)var3).forceLocalCommit((Xid)var2[0]);
            return null;
         case 14:
            ((SubCoordinatorOneway3)var3).forceLocalRollback((Xid)var2[0]);
            return null;
         case 15:
            return ((Coordinator2)var3).getProperties();
         case 16:
            return ((SubCoordinatorRM)var3).getProperties((String)var2[0]);
         case 17:
            return ((SubCoordinator3)var3).getSubCoordinatorInfo((String)var2[0]);
         case 18:
            ((NotificationListener)var3).handleNotification((Notification)var2[0], (Object)var2[1]);
            return null;
         case 19:
            return ((CoordinatorService)var3).invokeCoordinatorService((String)var2[0], (Object)var2[1]);
         case 20:
            ((CoordinatorOneway)var3).nakCommit((Xid)var2[0], (String)var2[1], (Short)var2[2], (String)var2[3]);
            return null;
         case 21:
            ((CoordinatorOneway2)var3).nakCommit((Xid)var2[0], (String)var2[1], (Short)var2[2], (String)var2[3], (String[])var2[4], (String[])var2[5]);
            return null;
         case 22:
            ((CoordinatorOneway)var3).nakRollback((Xid)var2[0], (String)var2[1], (Short)var2[2], (String)var2[3]);
            return null;
         case 23:
            ((CoordinatorOneway2)var3).nakRollback((Xid)var2[0], (String)var2[1], (Short)var2[2], (String)var2[3], (String[])var2[4], (String[])var2[5]);
            return null;
         case 24:
            ((SubCoordinator2)var3).nonXAResourceCommit((Xid)var2[0], (Boolean)var2[1], (String)var2[2]);
            return null;
         case 25:
            return ((SubCoordinator)var3).recover((String)var2[0], (String)var2[1]);
         case 26:
            ((SubCoordinatorOneway7)var3).recoveryServiceMigrated((String)var2[0], (String)var2[1], (String)var2[2]);
            return null;
         case 27:
            ((NotificationBroadcaster)var3).removeNotificationListener((NotificationListener)var2[0]);
            return null;
         case 28:
            ((SubCoordinator)var3).rollback((String)var2[0], (Xid[])var2[1]);
            return null;
         case 29:
            ((Coordinator)var3).rollback((PropagationContext)var2[0]);
            return null;
         case 30:
            ((SubCoordinatorOneway)var3).startCommit((Xid)var2[0], (String)var2[1], (String[])var2[2], (Boolean)var2[3], (Boolean)var2[4]);
            return null;
         case 31:
            ((SubCoordinatorOneway2)var3).startCommit((Xid)var2[0], (String)var2[1], (String[])var2[2], (Boolean)var2[3], (Boolean)var2[4], (AuthenticatedUser)var2[5]);
            return null;
         case 32:
            ((SubCoordinatorOneway5)var3).startCommit((Xid)var2[0], (String)var2[1], (String[])var2[2], (Boolean)var2[3], (Boolean)var2[4], (AuthenticatedUser)var2[5], (Map)var2[6]);
            return null;
         case 33:
            ((SubCoordinatorOneway)var3).startPrePrepareAndChain((PropagationContext)var2[0], (Integer)var2[1]);
            return null;
         case 34:
            ((SubCoordinatorOneway)var3).startPrepare((Xid)var2[0], (String)var2[1], (String[])var2[2], (Integer)var2[3]);
            return null;
         case 35:
            ((SubCoordinatorOneway5)var3).startPrepare((Xid)var2[0], (String)var2[1], (String[])var2[2], (Integer)var2[3], (Map)var2[4]);
            return null;
         case 36:
            ((SubCoordinatorOneway)var3).startRollback((Xid)var2[0], (String)var2[1], (String[])var2[2]);
            return null;
         case 37:
            ((SubCoordinatorOneway2)var3).startRollback((Xid)var2[0], (String)var2[1], (String[])var2[2], (AuthenticatedUser)var2[3]);
            return null;
         case 38:
            ((SubCoordinatorOneway4)var3).startRollback((Xid)var2[0], (String)var2[1], (String[])var2[2], (AuthenticatedUser)var2[3], (String[])var2[4]);
            return null;
         case 39:
            ((SubCoordinatorOneway5)var3).startRollback((Xid)var2[0], (String)var2[1], (String[])var2[2], (AuthenticatedUser)var2[3], (String[])var2[4], (Map)var2[5]);
            return null;
         case 40:
            ((SubCoordinatorOneway6)var3).startRollback((Xid)var2[0], (String)var2[1], (String[])var2[2], (AuthenticatedUser)var2[3], (String[])var2[4], (Map)var2[5], (Boolean)var2[6]);
            return null;
         case 41:
            ((CoordinatorOneway)var3).startRollback((PropagationContext)var2[0]);
            return null;
         case 42:
            ((SubCoordinatorOneway)var3).startRollback((Xid[])var2[0]);
            return null;
         case 43:
            ((Coordinator2)var3).xaCommit((Xid)var2[0]);
            return null;
         case 44:
            ((Coordinator2)var3).xaForget((Xid)var2[0]);
            return null;
         case 45:
            return new Integer(((Coordinator2)var3).xaPrepare((PropagationContext)var2[0]));
         case 46:
            return ((Coordinator2)var3).xaRecover();
         case 47:
            return ((Coordinator2)var3).xaRecover((Integer)var2[0]);
         case 48:
            ((Coordinator2)var3).xaRollback((Xid)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
