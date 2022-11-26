package weblogic.rmi.utils.collections;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.Map;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;

public final class _RemoteMapAdapter_Stub extends Stub implements RemoteMap {
   private static String[] _type_ids = new String[]{"RMI:weblogic.rmi.utils.collections.RemoteMapAdapter:0000000000000000", "RMI:weblogic.rmi.utils.collections.RemoteMap:0000000000000000"};
   // $FF: synthetic field
   private static Class class$java$io$IOException;
   // $FF: synthetic field
   private static Class class$java$util$Map;

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

   public final void clear() throws RemoteException {
      try {
         InputStream var1 = null;

         try {
            OutputStream var2 = this._request("clear", true);
            this._invoke(var2);
         } catch (ApplicationException var10) {
            var1 = (InputStream)var10.getInputStream();
            String var4 = var1.read_string();
            if (var4.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)var1.read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var4);
         } catch (RemarshalException var11) {
            this.clear();
         } finally {
            this._releaseReply(var1);
         }

      } catch (SystemException var13) {
         throw Util.mapSystemException(var13);
      }
   }

   public final boolean containsKey(Object var1) throws RemoteException {
      try {
         Object var2 = null;

         boolean var6;
         try {
            org.omg.CORBA_2_3.portable.OutputStream var3 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("containsKey", true);
            Util.writeAny(var3, var1);
            var2 = this._invoke(var3);
            boolean var4 = ((org.omg.CORBA.portable.InputStream)var2).read_boolean();
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = ((org.omg.CORBA.portable.InputStream)var2).read_string();
            if (var5.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)((InputStream)var2).read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.containsKey(var1);
         } finally {
            this._releaseReply((org.omg.CORBA.portable.InputStream)var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final boolean containsValue(Object var1) throws RemoteException {
      try {
         Object var2 = null;

         boolean var6;
         try {
            org.omg.CORBA_2_3.portable.OutputStream var3 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("containsValue", true);
            Util.writeAny(var3, var1);
            var2 = this._invoke(var3);
            boolean var4 = ((org.omg.CORBA.portable.InputStream)var2).read_boolean();
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = ((org.omg.CORBA.portable.InputStream)var2).read_string();
            if (var5.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)((InputStream)var2).read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.containsValue(var1);
         } finally {
            this._releaseReply((org.omg.CORBA.portable.InputStream)var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final Object get(Object var1) throws RemoteException {
      try {
         InputStream var2 = null;

         Object var6;
         try {
            org.omg.CORBA_2_3.portable.OutputStream var3 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("get", true);
            Util.writeAny(var3, var1);
            var2 = (InputStream)this._invoke(var3);
            Object var4 = (Object)Util.readAny(var2);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)var2.read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.get(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final boolean isEmpty() throws RemoteException {
      try {
         Object var1 = null;

         boolean var5;
         try {
            OutputStream var2 = this._request("isEmpty", true);
            var1 = this._invoke(var2);
            boolean var3 = ((org.omg.CORBA.portable.InputStream)var1).read_boolean();
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = ((org.omg.CORBA.portable.InputStream)var1).read_string();
            if (var4.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)((InputStream)var1).read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.isEmpty();
         } finally {
            this._releaseReply((org.omg.CORBA.portable.InputStream)var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final Object put(Object var1, Object var2) throws RemoteException {
      try {
         InputStream var3 = null;

         Object var7;
         try {
            org.omg.CORBA_2_3.portable.OutputStream var4 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("put", true);
            Util.writeAny(var4, var1);
            Util.writeAny(var4, var2);
            var3 = (InputStream)this._invoke(var4);
            Object var5 = (Object)Util.readAny(var3);
            return var5;
         } catch (ApplicationException var14) {
            var3 = (InputStream)var14.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)var3.read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.put(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void putAll(Map var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            org.omg.CORBA_2_3.portable.OutputStream var3 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("putAll", true);
            var3.write_value(var1, class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)var2.read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.putAll(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final Object remove(Object var1) throws RemoteException {
      try {
         InputStream var2 = null;

         Object var6;
         try {
            org.omg.CORBA_2_3.portable.OutputStream var3 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("remove", true);
            Util.writeAny(var3, var1);
            var2 = (InputStream)this._invoke(var3);
            Object var4 = (Object)Util.readAny(var2);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)var2.read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.remove(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final int size() throws RemoteException {
      try {
         Object var1 = null;

         int var5;
         try {
            OutputStream var2 = this._request("size", true);
            var1 = this._invoke(var2);
            int var3 = ((org.omg.CORBA.portable.InputStream)var1).read_long();
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = ((org.omg.CORBA.portable.InputStream)var1).read_string();
            if (var4.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)((InputStream)var1).read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.size();
         } finally {
            this._releaseReply((org.omg.CORBA.portable.InputStream)var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final Map snapshot() throws RemoteException {
      try {
         InputStream var1 = null;

         Map var5;
         try {
            OutputStream var2 = this._request("snapshot", true);
            var1 = (InputStream)this._invoke(var2);
            Map var3 = (Map)var1.read_value(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            if (var4.equals("IDL:java/io/IOEx:1.0")) {
               throw (IOException)((IOException)var1.read_value(class$java$io$IOException == null ? (class$java$io$IOException = class$("java.io.IOException")) : class$java$io$IOException));
            }

            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.snapshot();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }
}
