package weblogic.cluster;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.List;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RemoteClusterServiceOperationsImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$List;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      boolean var5;
      String var38;
      switch (var1) {
         case 0:
            int var42;
            try {
               MsgInput var6 = var2.getMsgInput();
               var42 = var6.readInt();
            } catch (IOException var37) {
               throw new UnmarshalException("error unmarshalling arguments", var37);
            }

            ((RemoteClusterServicesOperations)var4).disableSessionStateQueryProtocolAfter(var42);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            int var41;
            try {
               MsgInput var8 = var2.getMsgInput();
               var38 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var41 = var8.readInt();
            } catch (IOException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            } catch (ClassNotFoundException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            }

            ((RemoteClusterServicesOperations)var4).disableSessionStateQueryProtocolAfter(var38, var41);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            boolean var40;
            try {
               MsgInput var9 = var2.getMsgInput();
               var38 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var40 = var9.readBoolean();
            } catch (IOException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            } catch (ClassNotFoundException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            }

            ((RemoteClusterServicesOperations)var4).setCleanupOrphanedSessionsEnabled(var38, var40);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = var7.readBoolean();
            } catch (IOException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            }

            ((RemoteClusterServicesOperations)var4).setCleanupOrphanedSessionsEnabled(var5);
            this.associateResponseData(var2, var3);
            break;
         case 4:
            List var43;
            try {
               MsgInput var11 = var2.getMsgInput();
               var38 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var43 = (List)var11.readObject(class$java$util$List == null ? (class$java$util$List = class$("java.util.List")) : class$java$util$List);
            } catch (IOException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            } catch (ClassNotFoundException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            }

            ((RemoteClusterServicesOperations)var4).setFailoverServerGroups(var38, var43);
            this.associateResponseData(var2, var3);
            break;
         case 5:
            List var39;
            try {
               MsgInput var10 = var2.getMsgInput();
               var39 = (List)var10.readObject(class$java$util$List == null ? (class$java$util$List = class$("java.util.List")) : class$java$util$List);
            } catch (IOException var28) {
               throw new UnmarshalException("error unmarshalling arguments", var28);
            } catch (ClassNotFoundException var29) {
               throw new UnmarshalException("error unmarshalling arguments", var29);
            }

            ((RemoteClusterServicesOperations)var4).setFailoverServerGroups(var39);
            this.associateResponseData(var2, var3);
            break;
         case 6:
            boolean var44;
            try {
               MsgInput var13 = var2.getMsgInput();
               var38 = (String)var13.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var44 = var13.readBoolean();
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            } catch (ClassNotFoundException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            ((RemoteClusterServicesOperations)var4).setSessionLazyDeserializationEnabled(var38, var44);
            this.associateResponseData(var2, var3);
            break;
         case 7:
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = var12.readBoolean();
            } catch (IOException var25) {
               throw new UnmarshalException("error unmarshalling arguments", var25);
            }

            ((RemoteClusterServicesOperations)var4).setSessionLazyDeserializationEnabled(var5);
            this.associateResponseData(var2, var3);
            break;
         case 8:
            boolean var45;
            try {
               MsgInput var15 = var2.getMsgInput();
               var38 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var45 = var15.readBoolean();
            } catch (IOException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            } catch (ClassNotFoundException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            }

            ((RemoteClusterServicesOperations)var4).setSessionReplicationOnShutdownEnabled(var38, var45);
            this.associateResponseData(var2, var3);
            break;
         case 9:
            try {
               MsgInput var14 = var2.getMsgInput();
               var5 = var14.readBoolean();
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            }

            ((RemoteClusterServicesOperations)var4).setSessionReplicationOnShutdownEnabled(var5);
            this.associateResponseData(var2, var3);
            break;
         case 10:
            boolean var46;
            try {
               MsgInput var17 = var2.getMsgInput();
               var38 = (String)var17.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var46 = var17.readBoolean();
            } catch (IOException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            } catch (ClassNotFoundException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            }

            ((RemoteClusterServicesOperations)var4).setSessionStateQueryProtocolEnabled(var38, var46);
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var16 = var2.getMsgInput();
               var5 = var16.readBoolean();
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            }

            ((RemoteClusterServicesOperations)var4).setSessionStateQueryProtocolEnabled(var5);
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
            ((RemoteClusterServicesOperations)var3).disableSessionStateQueryProtocolAfter((Integer)var2[0]);
            return null;
         case 1:
            ((RemoteClusterServicesOperations)var3).disableSessionStateQueryProtocolAfter((String)var2[0], (Integer)var2[1]);
            return null;
         case 2:
            ((RemoteClusterServicesOperations)var3).setCleanupOrphanedSessionsEnabled((String)var2[0], (Boolean)var2[1]);
            return null;
         case 3:
            ((RemoteClusterServicesOperations)var3).setCleanupOrphanedSessionsEnabled((Boolean)var2[0]);
            return null;
         case 4:
            ((RemoteClusterServicesOperations)var3).setFailoverServerGroups((String)var2[0], (List)var2[1]);
            return null;
         case 5:
            ((RemoteClusterServicesOperations)var3).setFailoverServerGroups((List)var2[0]);
            return null;
         case 6:
            ((RemoteClusterServicesOperations)var3).setSessionLazyDeserializationEnabled((String)var2[0], (Boolean)var2[1]);
            return null;
         case 7:
            ((RemoteClusterServicesOperations)var3).setSessionLazyDeserializationEnabled((Boolean)var2[0]);
            return null;
         case 8:
            ((RemoteClusterServicesOperations)var3).setSessionReplicationOnShutdownEnabled((String)var2[0], (Boolean)var2[1]);
            return null;
         case 9:
            ((RemoteClusterServicesOperations)var3).setSessionReplicationOnShutdownEnabled((Boolean)var2[0]);
            return null;
         case 10:
            ((RemoteClusterServicesOperations)var3).setSessionStateQueryProtocolEnabled((String)var2[0], (Boolean)var2[1]);
            return null;
         case 11:
            ((RemoteClusterServicesOperations)var3).setSessionStateQueryProtocolEnabled((Boolean)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
