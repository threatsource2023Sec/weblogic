package weblogic.cluster.singleton;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.Set;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class _RemoteLeasingBasis_Stub extends Stub implements RemoteLeasingBasis {
   private static String[] _type_ids = new String[]{"RMI:weblogic.cluster.singleton.RemoteLeasingBasis:0000000000000000"};
   // $FF: synthetic field
   private static Class array$Ljava$lang$String;
   // $FF: synthetic field
   private static Class class$java$io$IOException;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$Set;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$singleton$MissedHeartbeatException;

   public String[] _ids() {
      return _type_ids;
   }

   public final boolean acquire(String var1, String var2, int var3) throws RemoteException {
      try {
         Object var4 = null;

         boolean var8;
         try {
            OutputStream var5 = (OutputStream)this._request("acquire", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_long(var3);
            var4 = this._invoke(var5);
            boolean var6 = ((InputStream)var4).read_boolean();
            return var6;
         } catch (ApplicationException var15) {
            var4 = (org.omg.CORBA_2_3.portable.InputStream)var15.getInputStream();
            String var7 = ((InputStream)var4).read_string();
            if (var7.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)((org.omg.CORBA_2_3.portable.InputStream)var4).read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var16) {
            var8 = this.acquire(var1, var2, var3);
         } finally {
            this._releaseReply((InputStream)var4);
         }

         return var8;
      } catch (SystemException var18) {
         throw Util.mapSystemException(var18);
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

   public final String[] findExpiredLeases(int var1) throws RemoteException {
      try {
         org.omg.CORBA_2_3.portable.InputStream var2 = null;

         String[] var6;
         try {
            org.omg.CORBA.portable.OutputStream var3 = this._request("findExpiredLeases", true);
            var3.write_long(var1);
            var2 = (org.omg.CORBA_2_3.portable.InputStream)this._invoke(var3);
            String[] var4 = (String[])var2.read_value(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (org.omg.CORBA_2_3.portable.InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)var2.read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.findExpiredLeases(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final String findOwner(String var1) throws RemoteException {
      try {
         org.omg.CORBA_2_3.portable.InputStream var2 = null;

         String var6;
         try {
            OutputStream var3 = (OutputStream)this._request("findOwner", true);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var2 = (org.omg.CORBA_2_3.portable.InputStream)this._invoke(var3);
            String var4 = (String)var2.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (org.omg.CORBA_2_3.portable.InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)var2.read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.findOwner(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final String findPreviousOwner(String var1) throws RemoteException {
      try {
         org.omg.CORBA_2_3.portable.InputStream var2 = null;

         String var6;
         try {
            OutputStream var3 = (OutputStream)this._request("findPreviousOwner", true);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var2 = (org.omg.CORBA_2_3.portable.InputStream)this._invoke(var3);
            String var4 = (String)var2.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (org.omg.CORBA_2_3.portable.InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)var2.read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.findPreviousOwner(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void release(String var1, String var2) throws RemoteException {
      try {
         org.omg.CORBA_2_3.portable.InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("release", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (org.omg.CORBA_2_3.portable.InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)var3.read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.release(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final int renewAllLeases(int var1, String var2) throws RemoteException {
      try {
         Object var3 = null;

         int var7;
         try {
            OutputStream var4 = (OutputStream)this._request("renewAllLeases", true);
            var4.write_long(var1);
            var4.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var3 = this._invoke(var4);
            int var5 = ((InputStream)var3).read_long();
            return var5;
         } catch (ApplicationException var14) {
            var3 = (org.omg.CORBA_2_3.portable.InputStream)var14.getInputStream();
            String var6 = ((InputStream)var3).read_string();
            if (var6.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)((org.omg.CORBA_2_3.portable.InputStream)var3).read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            if (var6.equals("IDL:weblogic/cluster/singleton/MissedHeartbeatEx:1.0")) {
               throw (MissedHeartbeatException)((MissedHeartbeatException)((org.omg.CORBA_2_3.portable.InputStream)var3).read_value(class$weblogic$cluster$singleton$MissedHeartbeatException == null ? (class$weblogic$cluster$singleton$MissedHeartbeatException = class$("weblogic.cluster.singleton.MissedHeartbeatException")) : class$weblogic$cluster$singleton$MissedHeartbeatException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.renewAllLeases(var1, var2);
         } finally {
            this._releaseReply((InputStream)var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final int renewLeases(String var1, Set var2, int var3) throws RemoteException {
      try {
         Object var4 = null;

         int var8;
         try {
            OutputStream var5 = (OutputStream)this._request("renewLeases", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$util$Set == null ? (class$java$util$Set = class$("java.util.Set")) : class$java$util$Set);
            var5.write_long(var3);
            var4 = this._invoke(var5);
            int var6 = ((InputStream)var4).read_long();
            return var6;
         } catch (ApplicationException var15) {
            var4 = (org.omg.CORBA_2_3.portable.InputStream)var15.getInputStream();
            String var7 = ((InputStream)var4).read_string();
            if (var7.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)((org.omg.CORBA_2_3.portable.InputStream)var4).read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            if (var7.equals("IDL:weblogic/cluster/singleton/MissedHeartbeatEx:1.0")) {
               throw (MissedHeartbeatException)((MissedHeartbeatException)((org.omg.CORBA_2_3.portable.InputStream)var4).read_value(class$weblogic$cluster$singleton$MissedHeartbeatException == null ? (class$weblogic$cluster$singleton$MissedHeartbeatException = class$("weblogic.cluster.singleton.MissedHeartbeatException")) : class$weblogic$cluster$singleton$MissedHeartbeatException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var16) {
            var8 = this.renewLeases(var1, var2, var3);
         } finally {
            this._releaseReply((InputStream)var4);
         }

         return var8;
      } catch (SystemException var18) {
         throw Util.mapSystemException(var18);
      }
   }
}
