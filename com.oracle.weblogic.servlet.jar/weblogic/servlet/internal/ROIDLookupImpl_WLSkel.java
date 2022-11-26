package weblogic.servlet.internal;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.cluster.replication.ROID;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class ROIDLookupImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$J;
   // $FF: synthetic field
   private static Class array$Ljava$lang$Object;
   // $FF: synthetic field
   private static Class array$Lweblogic$cluster$replication$ROID;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$ROID;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      String var9;
      long var12;
      String var32;
      String var36;
      String var37;
      switch (var1) {
         case 0:
            String var38;
            try {
               MsgInput var10 = var2.getMsgInput();
               var32 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var36 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var37 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var38 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var9 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            } catch (ClassNotFoundException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            }

            boolean var41 = ((ROIDLookup)var4).isAvailableInOtherCtx(var32, var36, var37, var38, var9);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var41);
               break;
            } catch (IOException var29) {
               throw new MarshalException("error marshalling return", var29);
            }
         case 1:
            try {
               MsgInput var8 = var2.getMsgInput();
               var32 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var36 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var37 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            } catch (ClassNotFoundException var28) {
               throw new UnmarshalException("error unmarshalling arguments", var28);
            }

            ROID var40 = ((ROIDLookup)var4).lookupROID(var32, var36, var37);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var40, class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
               break;
            } catch (IOException var26) {
               throw new MarshalException("error marshalling return", var26);
            }
         case 2:
            try {
               MsgInput var11 = var2.getMsgInput();
               var32 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var36 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var37 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var9 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            } catch (ClassNotFoundException var25) {
               throw new UnmarshalException("error unmarshalling arguments", var25);
            }

            ROID var42 = ((ROIDLookup)var4).lookupROID(var32, var36, var37, var9);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var42, class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
               break;
            } catch (IOException var23) {
               throw new MarshalException("error marshalling return", var23);
            }
         case 3:
            ROID var33;
            Object[] var35;
            try {
               MsgInput var7 = var2.getMsgInput();
               var33 = (ROID)var7.readObject(class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
               var35 = (Object[])var7.readObject(array$Ljava$lang$Object == null ? (array$Ljava$lang$Object = class$("[Ljava.lang.Object;")) : array$Ljava$lang$Object);
            } catch (IOException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            } catch (ClassNotFoundException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            }

            ((ROIDLookup)var4).unregister(var33, var35);
            this.associateResponseData(var2, var3);
            break;
         case 4:
            ROID[] var34;
            long[] var39;
            String var43;
            try {
               MsgInput var15 = var2.getMsgInput();
               var32 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var34 = (ROID[])var15.readObject(array$Lweblogic$cluster$replication$ROID == null ? (array$Lweblogic$cluster$replication$ROID = class$("[Lweblogic.cluster.replication.ROID;")) : array$Lweblogic$cluster$replication$ROID);
               var39 = (long[])var15.readObject(array$J == null ? (array$J = class$("[J")) : array$J);
               var12 = var15.readLong();
               var43 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            } catch (ClassNotFoundException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            }

            ((ROIDLookup)var4).updateLastAccessTimes(var32, var34, var39, var12, var43);
            this.associateResponseData(var2, var3);
            break;
         case 5:
            ROID[] var5;
            long[] var6;
            try {
               MsgInput var14 = var2.getMsgInput();
               var5 = (ROID[])var14.readObject(array$Lweblogic$cluster$replication$ROID == null ? (array$Lweblogic$cluster$replication$ROID = class$("[Lweblogic.cluster.replication.ROID;")) : array$Lweblogic$cluster$replication$ROID);
               var6 = (long[])var14.readObject(array$J == null ? (array$J = class$("[J")) : array$J);
               var12 = var14.readLong();
               var9 = (String)var14.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            } catch (ClassNotFoundException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            }

            ((ROIDLookup)var4).updateLastAccessTimes(var5, var6, var12, var9);
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
            return new Boolean(((ROIDLookup)var3).isAvailableInOtherCtx((String)var2[0], (String)var2[1], (String)var2[2], (String)var2[3], (String)var2[4]));
         case 1:
            return ((ROIDLookup)var3).lookupROID((String)var2[0], (String)var2[1], (String)var2[2]);
         case 2:
            return ((ROIDLookup)var3).lookupROID((String)var2[0], (String)var2[1], (String)var2[2], (String)var2[3]);
         case 3:
            ((ROIDLookup)var3).unregister((ROID)var2[0], (Object[])var2[1]);
            return null;
         case 4:
            ((ROIDLookup)var3).updateLastAccessTimes((String)var2[0], (ROID[])var2[1], (long[])var2[2], (Long)var2[3], (String)var2[4]);
            return null;
         case 5:
            ((ROIDLookup)var3).updateLastAccessTimes((ROID[])var2[0], (long[])var2[1], (Long)var2[2], (String)var2[3]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
