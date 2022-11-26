package weblogic.cluster.singleton;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class _SingletonServicesManagerService_Stub extends Stub implements RemoteSingletonServicesControl {
   private static String[] _type_ids = new String[]{"RMI:weblogic.cluster.singleton.SingletonServicesManagerService:0000000000000000", "RMI:weblogic.cluster.singleton.RemoteSingletonServicesControl:0000000000000000"};
   // $FF: synthetic field
   private static Class class$java$lang$String;

   public String[] _ids() {
      return _type_ids;
   }

   public final void activateService(String var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = (OutputStream)this._request("activateService", true);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.activateService(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public final void deactivateService(String var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = (OutputStream)this._request("deactivateService", true);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.deactivateService(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final boolean isServiceActive(String var1) throws RemoteException {
      try {
         Object var2 = null;

         boolean var6;
         try {
            OutputStream var3 = (OutputStream)this._request("isServiceActive", true);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var2 = this._invoke(var3);
            boolean var4 = ((org.omg.CORBA.portable.InputStream)var2).read_boolean();
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = ((org.omg.CORBA.portable.InputStream)var2).read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.isServiceActive(var1);
         } finally {
            this._releaseReply((org.omg.CORBA.portable.InputStream)var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final boolean isServiceRegistered(String var1) throws RemoteException {
      try {
         Object var2 = null;

         boolean var6;
         try {
            OutputStream var3 = (OutputStream)this._request("isServiceRegistered", true);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var2 = this._invoke(var3);
            boolean var4 = ((org.omg.CORBA.portable.InputStream)var2).read_boolean();
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = ((org.omg.CORBA.portable.InputStream)var2).read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.isServiceRegistered(var1);
         } finally {
            this._releaseReply((org.omg.CORBA.portable.InputStream)var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void restartService(String var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = (OutputStream)this._request("restartService", true);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.restartService(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }
}
