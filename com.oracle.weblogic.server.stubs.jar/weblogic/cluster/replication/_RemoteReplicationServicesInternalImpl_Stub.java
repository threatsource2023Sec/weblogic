package weblogic.cluster.replication;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.rmi.spi.HostID;

public final class _RemoteReplicationServicesInternalImpl_Stub extends Stub implements ReplicationServicesInternal {
   private static String[] _type_ids = new String[]{"RMI:weblogic.cluster.replication.RemoteReplicationServicesInternalImpl:0000000000000000", "RMI:weblogic.cluster.replication.ReplicationServicesInternal:0000000000000000"};
   // $FF: synthetic field
   private static Class array$Lweblogic$cluster$replication$ROID;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$AsyncBatch;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$NotFoundException;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$ROID;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$ROObject;
   // $FF: synthetic field
   private static Class class$weblogic$rmi$spi$HostID;

   public String[] _ids() {
      return _type_ids;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public final void copyUpdate(ROID var1, int var2, Serializable var3, Object var4) throws RemoteException {
      try {
         InputStream var5 = null;

         try {
            OutputStream var6 = (OutputStream)this._request("copyUpdate", true);
            var6.write_value(var1, class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
            var6.write_long(var2);
            Util.writeAny(var6, var3);
            Util.writeAny(var6, var4);
            this._invoke(var6);
         } catch (ApplicationException var14) {
            var5 = (InputStream)var14.getInputStream();
            String var8 = var5.read_string();
            if (var8.equals("IDL:weblogic/cluster/replication/NotFoundEx:1.0")) {
               throw (NotFoundException)((NotFoundException)var5.read_value(class$weblogic$cluster$replication$NotFoundException == null ? (class$weblogic$cluster$replication$NotFoundException = class$("weblogic.cluster.replication.NotFoundException")) : class$weblogic$cluster$replication$NotFoundException));
            }

            throw new UnexpectedException(var8);
         } catch (RemarshalException var15) {
            this.copyUpdate(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void copyUpdateOneWay(ROID var1, int var2, Serializable var3, Object var4) throws RemoteException {
      try {
         InputStream var5 = null;

         try {
            OutputStream var6 = (OutputStream)this._request("copyUpdateOneWay", false);
            var6.write_value(var1, class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
            var6.write_long(var2);
            Util.writeAny(var6, var3);
            Util.writeAny(var6, var4);
            this._invoke(var6);
         } catch (ApplicationException var14) {
            var5 = (InputStream)var14.getInputStream();
            String var8 = var5.read_string();
            throw new UnexpectedException(var8);
         } catch (RemarshalException var15) {
            this.copyUpdateOneWay(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final Object create(HostID var1, int var2, ROID var3, Serializable var4) throws RemoteException {
      try {
         InputStream var5 = null;

         Object var9;
         try {
            OutputStream var6 = (OutputStream)this._request("create", true);
            var6.write_value(var1, class$weblogic$rmi$spi$HostID == null ? (class$weblogic$rmi$spi$HostID = class$("weblogic.rmi.spi.HostID")) : class$weblogic$rmi$spi$HostID);
            var6.write_long(var2);
            var6.write_value(var3, class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
            Util.writeAny(var6, var4);
            var5 = (InputStream)this._invoke(var6);
            Object var7 = (Object)Util.readAny(var5);
            return var7;
         } catch (ApplicationException var16) {
            var5 = (InputStream)var16.getInputStream();
            String var8 = var5.read_string();
            throw new UnexpectedException(var8);
         } catch (RemarshalException var17) {
            var9 = this.create(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

         return var9;
      } catch (SystemException var19) {
         throw Util.mapSystemException(var19);
      }
   }

   public final ROObject fetch(ROID var1) throws RemoteException {
      try {
         InputStream var2 = null;

         ROObject var6;
         try {
            OutputStream var3 = (OutputStream)this._request("fetch", true);
            var3.write_value(var1, class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
            var2 = (InputStream)this._invoke(var3);
            ROObject var4 = (ROObject)var2.read_value(class$weblogic$cluster$replication$ROObject == null ? (class$weblogic$cluster$replication$ROObject = class$("weblogic.cluster.replication.ROObject")) : class$weblogic$cluster$replication$ROObject);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:weblogic/cluster/replication/NotFoundEx:1.0")) {
               throw (NotFoundException)((NotFoundException)var2.read_value(class$weblogic$cluster$replication$NotFoundException == null ? (class$weblogic$cluster$replication$NotFoundException = class$("weblogic.cluster.replication.NotFoundException")) : class$weblogic$cluster$replication$NotFoundException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.fetch(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void remove(ROID[] var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = (OutputStream)this._request("remove__org_omg_boxedRMI_weblogic_cluster_replication_seq1_ROID", false);
            var3.write_value(var1, array$Lweblogic$cluster$replication$ROID == null ? (array$Lweblogic$cluster$replication$ROID = class$("[Lweblogic.cluster.replication.ROID;")) : array$Lweblogic$cluster$replication$ROID);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.remove(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final void remove(ROID[] var1, Object var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("remove__org_omg_boxedRMI_weblogic_cluster_replication_seq1_ROID__java_lang_Object", true);
            var4.write_value(var1, array$Lweblogic$cluster$replication$ROID == null ? (array$Lweblogic$cluster$replication$ROID = class$("[Lweblogic.cluster.replication.ROID;")) : array$Lweblogic$cluster$replication$ROID);
            Util.writeAny(var4, var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.remove(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void removeOneWay(ROID[] var1, Object var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("removeOneWay", false);
            var4.write_value(var1, array$Lweblogic$cluster$replication$ROID == null ? (array$Lweblogic$cluster$replication$ROID = class$("[Lweblogic.cluster.replication.ROID;")) : array$Lweblogic$cluster$replication$ROID);
            Util.writeAny(var4, var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.removeOneWay(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void removeOrphanedSessionOnCondition(ROID var1, int var2, Object var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("removeOrphanedSessionOnCondition", true);
            var5.write_value(var1, class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
            var5.write_long(var2);
            Util.writeAny(var5, var3);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.removeOrphanedSessionOnCondition(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void update(AsyncBatch var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = (OutputStream)this._request("update__weblogic_cluster_replication_AsyncBatch", true);
            var3.write_value(var1, class$weblogic$cluster$replication$AsyncBatch == null ? (class$weblogic$cluster$replication$AsyncBatch = class$("weblogic.cluster.replication.AsyncBatch")) : class$weblogic$cluster$replication$AsyncBatch);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.update(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final void update(ROID var1, int var2, Serializable var3, Object var4) throws RemoteException {
      try {
         InputStream var5 = null;

         try {
            OutputStream var6 = (OutputStream)this._request("update__weblogic_cluster_replication_ROID__long__java_io_Serializable__java_lang_Object", true);
            var6.write_value(var1, class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
            var6.write_long(var2);
            Util.writeAny(var6, var3);
            Util.writeAny(var6, var4);
            this._invoke(var6);
         } catch (ApplicationException var14) {
            var5 = (InputStream)var14.getInputStream();
            String var8 = var5.read_string();
            if (var8.equals("IDL:weblogic/cluster/replication/NotFoundEx:1.0")) {
               throw (NotFoundException)((NotFoundException)var5.read_value(class$weblogic$cluster$replication$NotFoundException == null ? (class$weblogic$cluster$replication$NotFoundException = class$("weblogic.cluster.replication.NotFoundException")) : class$weblogic$cluster$replication$NotFoundException));
            }

            throw new UnexpectedException(var8);
         } catch (RemarshalException var15) {
            this.update(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void updateOneWay(ROID var1, int var2, Serializable var3, Object var4) throws RemoteException {
      try {
         InputStream var5 = null;

         try {
            OutputStream var6 = (OutputStream)this._request("updateOneWay", false);
            var6.write_value(var1, class$weblogic$cluster$replication$ROID == null ? (class$weblogic$cluster$replication$ROID = class$("weblogic.cluster.replication.ROID")) : class$weblogic$cluster$replication$ROID);
            var6.write_long(var2);
            Util.writeAny(var6, var3);
            Util.writeAny(var6, var4);
            this._invoke(var6);
         } catch (ApplicationException var14) {
            var5 = (InputStream)var14.getInputStream();
            String var8 = var5.read_string();
            throw new UnexpectedException(var8);
         } catch (RemarshalException var15) {
            this.updateOneWay(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }
}
