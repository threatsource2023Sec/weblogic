package weblogic.server.channels;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import weblogic.protocol.ChannelList;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;

public final class _RemoteChannelService_Stub extends Stub implements RemoteChannelService {
   private static String[] _type_ids = new String[]{"RMI:weblogic.server.channels.RemoteChannelService:0000000000000000"};
   // $FF: synthetic field
   private static Class array$Ljava$lang$String;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$weblogic$protocol$ChannelList;
   // $FF: synthetic field
   private static Class class$weblogic$protocol$ServerChannel;
   // $FF: synthetic field
   private static Class class$weblogic$protocol$ServerIdentity;

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

   public final String getAdministrationURL() throws RemoteException {
      try {
         InputStream var1 = null;

         String var5;
         try {
            OutputStream var2 = this._request("_get_administrationURL", true);
            var1 = (InputStream)this._invoke(var2);
            String var3 = (String)var1.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getAdministrationURL();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final ChannelList getChannelList(ServerIdentity var1) throws RemoteException {
      try {
         InputStream var2 = null;

         ChannelList var6;
         try {
            org.omg.CORBA_2_3.portable.OutputStream var3 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("getChannelList", true);
            var3.write_value(var1, class$weblogic$protocol$ServerIdentity == null ? (class$weblogic$protocol$ServerIdentity = class$("weblogic.protocol.ServerIdentity")) : class$weblogic$protocol$ServerIdentity);
            var2 = (InputStream)this._invoke(var3);
            ChannelList var4 = (ChannelList)var2.read_value(class$weblogic$protocol$ChannelList == null ? (class$weblogic$protocol$ChannelList = class$("weblogic.protocol.ChannelList")) : class$weblogic$protocol$ChannelList);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.getChannelList(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final String[] getConnectedServers() throws RemoteException {
      try {
         InputStream var1 = null;

         String[] var5;
         try {
            OutputStream var2 = this._request("_get_connectedServers", true);
            var1 = (InputStream)this._invoke(var2);
            String[] var3 = (String[])var1.read_value(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getConnectedServers();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final String getDefaultURL() throws RemoteException {
      try {
         InputStream var1 = null;

         String var5;
         try {
            OutputStream var2 = this._request("_get_defaultURL", true);
            var1 = (InputStream)this._invoke(var2);
            String var3 = (String)var1.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getDefaultURL();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final ServerChannel getServerChannel(String var1) throws RemoteException {
      try {
         InputStream var2 = null;

         ServerChannel var6;
         try {
            org.omg.CORBA_2_3.portable.OutputStream var3 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("getServerChannel", true);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var2 = (InputStream)this._invoke(var3);
            ServerChannel var4 = (ServerChannel)var2.read_value(class$weblogic$protocol$ServerChannel == null ? (class$weblogic$protocol$ServerChannel = class$("weblogic.protocol.ServerChannel")) : class$weblogic$protocol$ServerChannel);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.getServerChannel(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final ServerIdentity getServerIdentity() throws RemoteException {
      try {
         InputStream var1 = null;

         ServerIdentity var5;
         try {
            OutputStream var2 = this._request("_get_serverIdentity", true);
            var1 = (InputStream)this._invoke(var2);
            ServerIdentity var3 = (ServerIdentity)var1.read_value(class$weblogic$protocol$ServerIdentity == null ? (class$weblogic$protocol$ServerIdentity = class$("weblogic.protocol.ServerIdentity")) : class$weblogic$protocol$ServerIdentity);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getServerIdentity();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final String getURL(String var1) throws RemoteException {
      try {
         InputStream var2 = null;

         String var6;
         try {
            org.omg.CORBA_2_3.portable.OutputStream var3 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("getURL", true);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var2 = (InputStream)this._invoke(var3);
            String var4 = (String)var2.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.getURL(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final String registerServer(String var1, ChannelList var2) throws RemoteException {
      try {
         InputStream var3 = null;

         String var7;
         try {
            org.omg.CORBA_2_3.portable.OutputStream var4 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("registerServer", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$weblogic$protocol$ChannelList == null ? (class$weblogic$protocol$ChannelList = class$("weblogic.protocol.ChannelList")) : class$weblogic$protocol$ChannelList);
            var3 = (InputStream)this._invoke(var4);
            String var5 = (String)var3.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var5;
         } catch (ApplicationException var14) {
            var3 = (InputStream)var14.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.registerServer(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void removeChannelList(ServerIdentity var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            org.omg.CORBA_2_3.portable.OutputStream var3 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("removeChannelList", true);
            var3.write_value(var1, class$weblogic$protocol$ServerIdentity == null ? (class$weblogic$protocol$ServerIdentity = class$("weblogic.protocol.ServerIdentity")) : class$weblogic$protocol$ServerIdentity);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.removeChannelList(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final void updateServer(String var1, ChannelList var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            org.omg.CORBA_2_3.portable.OutputStream var4 = (org.omg.CORBA_2_3.portable.OutputStream)this._request("updateServer", false);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$weblogic$protocol$ChannelList == null ? (class$weblogic$protocol$ChannelList = class$("weblogic.protocol.ChannelList")) : class$weblogic$protocol$ChannelList);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.updateServer(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }
}
