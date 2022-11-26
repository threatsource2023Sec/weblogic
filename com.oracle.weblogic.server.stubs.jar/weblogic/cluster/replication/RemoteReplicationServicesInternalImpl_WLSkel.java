package weblogic.cluster.replication;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RemoteReplicationServicesInternalImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Lweblogic$cluster$replication$ROID;
   // $FF: synthetic field
   private static Class class$java$io$Serializable;
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$AsyncBatch;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$ROID;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$ROObject;
   // $FF: synthetic field
   private static Class class$weblogic$rmi$spi$HostID;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      ROID var5;
      int var14;
      Serializable var16;
      Object var17;
      int var46;
      ROID[] var47;
      Serializable var51;
      Object var53;
      switch (var1) {
         case 0:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (ROID)var9.readObject(class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
               var46 = var9.readInt();
               var51 = (Serializable)var9.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var53 = (Object)var9.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            } catch (ClassNotFoundException var44) {
               throw new UnmarshalException("error unmarshalling arguments", var44);
            }

            ((ReplicationServicesInternal)var4).copyUpdate(var5, var46, var51, var53);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (ROID)var10.readObject(class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
               var46 = var10.readInt();
               var51 = (Serializable)var10.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var53 = (Object)var10.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var41) {
               throw new UnmarshalException("error unmarshalling arguments", var41);
            } catch (ClassNotFoundException var42) {
               throw new UnmarshalException("error unmarshalling arguments", var42);
            }

            ((ReplicationServicesInternal)var4).copyUpdateOneWay(var5, var46, var51, var53);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            HostID var49;
            ROID var50;
            Serializable var54;
            try {
               MsgInput var11 = var2.getMsgInput();
               var49 = (HostID)var11.readObject(class$weblogic$rmi$spi$HostID == null ? (class$weblogic$rmi$spi$HostID = class$("weblogic.rmi.spi.HostID")) : class$weblogic$rmi$spi$HostID);
               var46 = var11.readInt();
               var50 = (ROID)var11.readObject(class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
               var54 = (Serializable)var11.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
            } catch (IOException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            } catch (ClassNotFoundException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            }

            Object var55 = ((ReplicationServicesInternal)var4).create(var49, var46, var50, var54);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var55, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var38) {
               throw new MarshalException("error marshalling return", var38);
            }
         case 3:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (ROID)var6.readObject(class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
            } catch (IOException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            } catch (ClassNotFoundException var37) {
               throw new UnmarshalException("error unmarshalling arguments", var37);
            }

            ROObject var48 = ((ReplicationServicesInternal)var4).fetch(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var48, class$weblogic$cluster$replication$ROObject == null ? (class$weblogic$cluster$replication$ROObject = class$("weblogic.cluster.replication.ROObject")) : class$weblogic$cluster$replication$ROObject);
               break;
            } catch (IOException var35) {
               throw new MarshalException("error marshalling return", var35);
            }
         case 4:
            try {
               MsgInput var7 = var2.getMsgInput();
               var47 = (ROID[])var7.readObject(array$Lweblogic$cluster$replication$ROID == null ? (array$Lweblogic$cluster$replication$ROID = class$("[Lweblogic.cluster.replication.ROID;")) : array$Lweblogic$cluster$replication$ROID);
            } catch (IOException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            } catch (ClassNotFoundException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            }

            ((ReplicationServicesInternal)var4).remove(var47);
            this.associateResponseData(var2, var3);
            break;
         case 5:
            try {
               MsgInput var12 = var2.getMsgInput();
               var47 = (ROID[])var12.readObject(array$Lweblogic$cluster$replication$ROID == null ? (array$Lweblogic$cluster$replication$ROID = class$("[Lweblogic.cluster.replication.ROID;")) : array$Lweblogic$cluster$replication$ROID);
               var53 = (Object)var12.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            } catch (ClassNotFoundException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            }

            ((ReplicationServicesInternal)var4).remove(var47, var53);
            this.associateResponseData(var2, var3);
            break;
         case 6:
            try {
               MsgInput var13 = var2.getMsgInput();
               var47 = (ROID[])var13.readObject(array$Lweblogic$cluster$replication$ROID == null ? (array$Lweblogic$cluster$replication$ROID = class$("[Lweblogic.cluster.replication.ROID;")) : array$Lweblogic$cluster$replication$ROID);
               var53 = (Object)var13.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var29) {
               throw new UnmarshalException("error unmarshalling arguments", var29);
            } catch (ClassNotFoundException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            }

            ((ReplicationServicesInternal)var4).removeOneWay(var47, var53);
            this.associateResponseData(var2, var3);
            break;
         case 7:
            int var52;
            Object var56;
            try {
               MsgInput var15 = var2.getMsgInput();
               var5 = (ROID)var15.readObject(class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
               var52 = var15.readInt();
               var56 = (Object)var15.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            } catch (ClassNotFoundException var28) {
               throw new UnmarshalException("error unmarshalling arguments", var28);
            }

            ((ReplicationServicesInternal)var4).removeOrphanedSessionOnCondition(var5, var52, var56);
            this.associateResponseData(var2, var3);
            break;
         case 8:
            AsyncBatch var45;
            try {
               MsgInput var8 = var2.getMsgInput();
               var45 = (AsyncBatch)var8.readObject(class$weblogic$cluster$replication$AsyncBatch == null ? (class$weblogic$cluster$replication$AsyncBatch = class$("weblogic.cluster.replication.AsyncBatch")) : class$weblogic$cluster$replication$AsyncBatch);
            } catch (IOException var25) {
               throw new UnmarshalException("error unmarshalling arguments", var25);
            } catch (ClassNotFoundException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            }

            ((ReplicationServicesInternal)var4).update(var45);
            this.associateResponseData(var2, var3);
            break;
         case 9:
            try {
               MsgInput var18 = var2.getMsgInput();
               var5 = (ROID)var18.readObject(class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
               var14 = var18.readInt();
               var16 = (Serializable)var18.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var17 = (Object)var18.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            } catch (ClassNotFoundException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            }

            ((ReplicationServicesInternal)var4).update(var5, var14, var16, var17);
            this.associateResponseData(var2, var3);
            break;
         case 10:
            try {
               MsgInput var19 = var2.getMsgInput();
               var5 = (ROID)var19.readObject(class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
               var14 = var19.readInt();
               var16 = (Serializable)var19.readObject(class$java$io$Serializable == null ? (class$java$io$Serializable = class$("java.io.Serializable")) : class$java$io$Serializable);
               var17 = (Object)var19.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            } catch (ClassNotFoundException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            }

            ((ReplicationServicesInternal)var4).updateOneWay(var5, var14, var16, var17);
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
            ((ReplicationServicesInternal)var3).copyUpdate((ROID)var2[0], (Integer)var2[1], (Serializable)var2[2], (Object)var2[3]);
            return null;
         case 1:
            ((ReplicationServicesInternal)var3).copyUpdateOneWay((ROID)var2[0], (Integer)var2[1], (Serializable)var2[2], (Object)var2[3]);
            return null;
         case 2:
            return ((ReplicationServicesInternal)var3).create((HostID)var2[0], (Integer)var2[1], (ROID)var2[2], (Serializable)var2[3]);
         case 3:
            return ((ReplicationServicesInternal)var3).fetch((ROID)var2[0]);
         case 4:
            ((ReplicationServicesInternal)var3).remove((ROID[])var2[0]);
            return null;
         case 5:
            ((ReplicationServicesInternal)var3).remove((ROID[])var2[0], (Object)var2[1]);
            return null;
         case 6:
            ((ReplicationServicesInternal)var3).removeOneWay((ROID[])var2[0], (Object)var2[1]);
            return null;
         case 7:
            ((ReplicationServicesInternal)var3).removeOrphanedSessionOnCondition((ROID)var2[0], (Integer)var2[1], (Object)var2[2]);
            return null;
         case 8:
            ((ReplicationServicesInternal)var3).update((AsyncBatch)var2[0]);
            return null;
         case 9:
            ((ReplicationServicesInternal)var3).update((ROID)var2[0], (Integer)var2[1], (Serializable)var2[2], (Object)var2[3]);
            return null;
         case 10:
            ((ReplicationServicesInternal)var3).updateOneWay((ROID)var2[0], (Integer)var2[1], (Serializable)var2[2], (Object)var2[3]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
