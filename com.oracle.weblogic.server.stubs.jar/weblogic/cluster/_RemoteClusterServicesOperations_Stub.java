package weblogic.cluster;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.List;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;

public final class _RemoteClusterServicesOperations_Stub extends Stub implements RemoteClusterServicesOperations {
   private static String[] _type_ids = new String[]{"RMI:weblogic.cluster.RemoteClusterServicesOperations:0000000000000000"};
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$List;

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

   public final void disableSessionStateQueryProtocolAfter(int var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = this._request("disableSessionStateQueryProtocolAfter__long", true);
            var3.write_long(var1);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.disableSessionStateQueryProtocolAfter(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final void disableSessionStateQueryProtocolAfter(String var1, int var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            org.omg.CORBA_2_3.portable.OutputStream var4 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("disableSessionStateQueryProtocolAfter__CORBA_WStringValue__long", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_long(var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.disableSessionStateQueryProtocolAfter(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void setCleanupOrphanedSessionsEnabled(String var1, boolean var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            org.omg.CORBA_2_3.portable.OutputStream var4 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("setCleanupOrphanedSessionsEnabled__CORBA_WStringValue__boolean", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_boolean(var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.setCleanupOrphanedSessionsEnabled(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void setCleanupOrphanedSessionsEnabled(boolean var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = this._request("setCleanupOrphanedSessionsEnabled__boolean", true);
            var3.write_boolean(var1);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.setCleanupOrphanedSessionsEnabled(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final void setFailoverServerGroups(String var1, List var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            org.omg.CORBA_2_3.portable.OutputStream var4 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("setFailoverServerGroups__CORBA_WStringValue__java_util_List", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$java$util$List == null ? (class$java$util$List = class$("java.util.List")) : class$java$util$List);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.setFailoverServerGroups(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void setFailoverServerGroups(List var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            org.omg.CORBA_2_3.portable.OutputStream var3 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("setFailoverServerGroups__java_util_List", true);
            var3.write_value(var1, class$java$util$List == null ? (class$java$util$List = class$("java.util.List")) : class$java$util$List);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.setFailoverServerGroups(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final void setSessionLazyDeserializationEnabled(String var1, boolean var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            org.omg.CORBA_2_3.portable.OutputStream var4 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("setSessionLazyDeserializationEnabled__CORBA_WStringValue__boolean", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_boolean(var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.setSessionLazyDeserializationEnabled(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void setSessionLazyDeserializationEnabled(boolean var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = this._request("setSessionLazyDeserializationEnabled__boolean", true);
            var3.write_boolean(var1);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.setSessionLazyDeserializationEnabled(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final void setSessionReplicationOnShutdownEnabled(String var1, boolean var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            org.omg.CORBA_2_3.portable.OutputStream var4 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("setSessionReplicationOnShutdownEnabled__CORBA_WStringValue__boolean", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_boolean(var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.setSessionReplicationOnShutdownEnabled(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void setSessionReplicationOnShutdownEnabled(boolean var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = this._request("setSessionReplicationOnShutdownEnabled__boolean", true);
            var3.write_boolean(var1);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.setSessionReplicationOnShutdownEnabled(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final void setSessionStateQueryProtocolEnabled(String var1, boolean var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            org.omg.CORBA_2_3.portable.OutputStream var4 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("setSessionStateQueryProtocolEnabled__CORBA_WStringValue__boolean", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_boolean(var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.setSessionStateQueryProtocolEnabled(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void setSessionStateQueryProtocolEnabled(boolean var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = this._request("setSessionStateQueryProtocolEnabled__boolean", true);
            var3.write_boolean(var1);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.setSessionStateQueryProtocolEnabled(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }
}
