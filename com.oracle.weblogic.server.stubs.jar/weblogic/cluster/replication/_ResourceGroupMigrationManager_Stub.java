package weblogic.cluster.replication;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class _ResourceGroupMigrationManager_Stub extends Stub implements ResourceGroupMigrationManager {
   private static String[] _type_ids = new String[]{"RMI:weblogic.cluster.replication.ResourceGroupMigrationManager:0000000000000000"};
   // $FF: synthetic field
   private static Class class$java$lang$IllegalStateException;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$concurrent$Future;
   // $FF: synthetic field
   private static Class class$java$util$concurrent$TimeoutException;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$MigrationInProgressException;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$Status;

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

   public final Future initiateResourceGroupMigration(String var1, String var2, String var3) throws RemoteException {
      try {
         InputStream var4 = null;

         Future var8;
         try {
            OutputStream var5 = (OutputStream)this._request("initiateResourceGroupMigration__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var3, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4 = (InputStream)this._invoke(var5);
            Future var6 = (Future)var4.read_value(class$java$util$concurrent$Future == null ? (class$java$util$concurrent$Future = class$("java.util.concurrent.Future")) : class$java$util$concurrent$Future);
            return var6;
         } catch (ApplicationException var15) {
            var4 = (InputStream)var15.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/cluster/replication/MigrationInProgressEx:1.0")) {
               throw (MigrationInProgressException)((MigrationInProgressException)var4.read_value(class$weblogic$cluster$replication$MigrationInProgressException == null ? (class$weblogic$cluster$replication$MigrationInProgressException = class$("weblogic.cluster.replication.MigrationInProgressException")) : class$weblogic$cluster$replication$MigrationInProgressException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var16) {
            var8 = this.initiateResourceGroupMigration(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

         return var8;
      } catch (SystemException var18) {
         throw Util.mapSystemException(var18);
      }
   }

   public final Status initiateResourceGroupMigration(String var1, String var2, String var3, int var4) throws RemoteException {
      try {
         InputStream var5 = null;

         Status var9;
         try {
            OutputStream var6 = (OutputStream)this._request("initiateResourceGroupMigration__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue__long", true);
            var6.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_value(var3, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_long(var4);
            var5 = (InputStream)this._invoke(var6);
            Status var7 = (Status)var5.read_value(class$weblogic$cluster$replication$Status == null ? (class$weblogic$cluster$replication$Status = class$("weblogic.cluster.replication.Status")) : class$weblogic$cluster$replication$Status);
            return var7;
         } catch (ApplicationException var16) {
            var5 = (InputStream)var16.getInputStream();
            String var8 = var5.read_string();
            if (var8.equals("IDL:java/util/concurrent/TimeoutEx:1.0")) {
               throw (TimeoutException)((TimeoutException)var5.read_value(class$java$util$concurrent$TimeoutException == null ? (class$java$util$concurrent$TimeoutException = class$("java.util.concurrent.TimeoutException")) : class$java$util$concurrent$TimeoutException));
            }

            if (var8.equals("IDL:omg.org/CORBA/UNKNOWN:1.0")) {
               throw (IllegalStateException)((IllegalStateException)var5.read_value(class$java$lang$IllegalStateException == null ? (class$java$lang$IllegalStateException = class$("java.lang.IllegalStateException")) : class$java$lang$IllegalStateException));
            }

            if (var8.equals("IDL:weblogic/cluster/replication/MigrationInProgressEx:1.0")) {
               throw (MigrationInProgressException)((MigrationInProgressException)var5.read_value(class$weblogic$cluster$replication$MigrationInProgressException == null ? (class$weblogic$cluster$replication$MigrationInProgressException = class$("weblogic.cluster.replication.MigrationInProgressException")) : class$weblogic$cluster$replication$MigrationInProgressException));
            }

            throw new UnexpectedException(var8);
         } catch (RemarshalException var17) {
            var9 = this.initiateResourceGroupMigration(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

         return var9;
      } catch (SystemException var19) {
         throw Util.mapSystemException(var19);
      }
   }
}
