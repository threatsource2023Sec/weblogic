package weblogic.server;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.Map;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.ResourceGroupLifecycleException;

public final class _RemoteLifeCycleOperations_Stub extends Stub implements RemoteLifeCycleOperations {
   private static String[] _type_ids = new String[]{"RMI:weblogic.server.RemoteLifeCycleOperations:0000000000000000"};
   // $FF: synthetic field
   private static Class array$Ljava$lang$String;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$Map;
   // $FF: synthetic field
   private static Class class$weblogic$management$PartitionLifeCycleException;
   // $FF: synthetic field
   private static Class class$weblogic$management$ResourceGroupLifecycleException;
   // $FF: synthetic field
   private static Class class$weblogic$server$ServerLifecycleException;

   public String[] _ids() {
      return _type_ids;
   }

   public final void bootPartition(String var1, String var2, String[] var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("bootPartition", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var3, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/management/PartitionLifeCycleEx:1.0")) {
               throw (PartitionLifeCycleException)((PartitionLifeCycleException)var4.read_value(class$weblogic$management$PartitionLifeCycleException == null ? (class$weblogic$management$PartitionLifeCycleException = class$("weblogic.management.PartitionLifeCycleException")) : class$weblogic$management$PartitionLifeCycleException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.bootPartition(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
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

   public final void forceRestartPartition(String var1, String var2, String[] var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("forceRestartPartition", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var3, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/management/PartitionLifeCycleEx:1.0")) {
               throw (PartitionLifeCycleException)((PartitionLifeCycleException)var4.read_value(class$weblogic$management$PartitionLifeCycleException == null ? (class$weblogic$management$PartitionLifeCycleException = class$("weblogic.management.PartitionLifeCycleException")) : class$weblogic$management$PartitionLifeCycleException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.forceRestartPartition(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void forceShutDownPartition(String var1, String[] var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("forceShutDownPartition", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:weblogic/management/PartitionLifeCycleEx:1.0")) {
               throw (PartitionLifeCycleException)((PartitionLifeCycleException)var3.read_value(class$weblogic$management$PartitionLifeCycleException == null ? (class$weblogic$management$PartitionLifeCycleException = class$("weblogic.management.PartitionLifeCycleException")) : class$weblogic$management$PartitionLifeCycleException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.forceShutDownPartition(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void forceShutDownResourceGroup(String var1, String var2, String[] var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("forceShutDownResourceGroup", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var3, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/management/ResourceGroupLifecycleEx:1.0")) {
               throw (ResourceGroupLifecycleException)((ResourceGroupLifecycleException)var4.read_value(class$weblogic$management$ResourceGroupLifecycleException == null ? (class$weblogic$management$ResourceGroupLifecycleException = class$("weblogic.management.ResourceGroupLifecycleException")) : class$weblogic$management$ResourceGroupLifecycleException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.forceShutDownResourceGroup(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void forceShutdown() throws RemoteException {
      try {
         InputStream var1 = null;

         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("forceShutdown", true);
            this._invoke(var2);
         } catch (ApplicationException var10) {
            var1 = (InputStream)var10.getInputStream();
            String var4 = var1.read_string();
            if (var4.equals("IDL:weblogic/server/ServerLifecycleEx:1.0")) {
               throw (ServerLifecycleException)((ServerLifecycleException)var1.read_value(class$weblogic$server$ServerLifecycleException == null ? (class$weblogic$server$ServerLifecycleException = class$("weblogic.server.ServerLifecycleException")) : class$weblogic$server$ServerLifecycleException));
            }

            throw new UnexpectedException(var4);
         } catch (RemarshalException var11) {
            this.forceShutdown();
         } finally {
            this._releaseReply(var1);
         }

      } catch (SystemException var13) {
         throw Util.mapSystemException(var13);
      }
   }

   public final void forceSuspend() throws RemoteException {
      try {
         InputStream var1 = null;

         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("forceSuspend", true);
            this._invoke(var2);
         } catch (ApplicationException var10) {
            var1 = (InputStream)var10.getInputStream();
            String var4 = var1.read_string();
            if (var4.equals("IDL:weblogic/server/ServerLifecycleEx:1.0")) {
               throw (ServerLifecycleException)((ServerLifecycleException)var1.read_value(class$weblogic$server$ServerLifecycleException == null ? (class$weblogic$server$ServerLifecycleException = class$("weblogic.server.ServerLifecycleException")) : class$weblogic$server$ServerLifecycleException));
            }

            throw new UnexpectedException(var4);
         } catch (RemarshalException var11) {
            this.forceSuspend();
         } finally {
            this._releaseReply(var1);
         }

      } catch (SystemException var13) {
         throw Util.mapSystemException(var13);
      }
   }

   public final void forceSuspendPartition(String var1, String[] var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("forceSuspendPartition", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:weblogic/management/PartitionLifeCycleEx:1.0")) {
               throw (PartitionLifeCycleException)((PartitionLifeCycleException)var3.read_value(class$weblogic$management$PartitionLifeCycleException == null ? (class$weblogic$management$PartitionLifeCycleException = class$("weblogic.management.PartitionLifeCycleException")) : class$weblogic$management$PartitionLifeCycleException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.forceSuspendPartition(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void forceSuspendResourceGroup(String var1, String var2, String[] var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("forceSuspendResourceGroup", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var3, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/management/ResourceGroupLifecycleEx:1.0")) {
               throw (ResourceGroupLifecycleException)((ResourceGroupLifecycleException)var4.read_value(class$weblogic$management$ResourceGroupLifecycleException == null ? (class$weblogic$management$ResourceGroupLifecycleException = class$("weblogic.management.ResourceGroupLifecycleException")) : class$weblogic$management$ResourceGroupLifecycleException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.forceSuspendResourceGroup(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final String getMiddlewareHome() throws RemoteException {
      try {
         InputStream var1 = null;

         String var5;
         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("_get_middlewareHome", true);
            var1 = (InputStream)this._invoke(var2);
            String var3 = (String)var1.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getMiddlewareHome();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final Map getRuntimeStates() throws RemoteException {
      try {
         InputStream var1 = null;

         Map var5;
         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("_get_runtimeStates", true);
            var1 = (InputStream)this._invoke(var2);
            Map var3 = (Map)var1.read_value(class$java$util$Map == null ? (class$java$util$Map = class$("java.util.Map")) : class$java$util$Map);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getRuntimeStates();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final String getState() throws RemoteException {
      try {
         InputStream var1 = null;

         String var5;
         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("_get_state", true);
            var1 = (InputStream)this._invoke(var2);
            String var3 = (String)var1.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getState();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final String getWeblogicHome() throws RemoteException {
      try {
         InputStream var1 = null;

         String var5;
         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("_get_weblogicHome", true);
            var1 = (InputStream)this._invoke(var2);
            String var3 = (String)var1.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getWeblogicHome();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void haltPartition(String var1, String var2, String[] var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("haltPartition", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var3, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/management/PartitionLifeCycleEx:1.0")) {
               throw (PartitionLifeCycleException)((PartitionLifeCycleException)var4.read_value(class$weblogic$management$PartitionLifeCycleException == null ? (class$weblogic$management$PartitionLifeCycleException = class$("weblogic.management.PartitionLifeCycleException")) : class$weblogic$management$PartitionLifeCycleException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.haltPartition(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void resume() throws RemoteException {
      try {
         InputStream var1 = null;

         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("resume", true);
            this._invoke(var2);
         } catch (ApplicationException var10) {
            var1 = (InputStream)var10.getInputStream();
            String var4 = var1.read_string();
            if (var4.equals("IDL:weblogic/server/ServerLifecycleEx:1.0")) {
               throw (ServerLifecycleException)((ServerLifecycleException)var1.read_value(class$weblogic$server$ServerLifecycleException == null ? (class$weblogic$server$ServerLifecycleException = class$("weblogic.server.ServerLifecycleException")) : class$weblogic$server$ServerLifecycleException));
            }

            throw new UnexpectedException(var4);
         } catch (RemarshalException var11) {
            this.resume();
         } finally {
            this._releaseReply(var1);
         }

      } catch (SystemException var13) {
         throw Util.mapSystemException(var13);
      }
   }

   public final void resumePartition(String var1, String[] var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("resumePartition", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:weblogic/management/PartitionLifeCycleEx:1.0")) {
               throw (PartitionLifeCycleException)((PartitionLifeCycleException)var3.read_value(class$weblogic$management$PartitionLifeCycleException == null ? (class$weblogic$management$PartitionLifeCycleException = class$("weblogic.management.PartitionLifeCycleException")) : class$weblogic$management$PartitionLifeCycleException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.resumePartition(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void resumeResourceGroup(String var1, String var2, String[] var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("resumeResourceGroup", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var3, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/management/ResourceGroupLifecycleEx:1.0")) {
               throw (ResourceGroupLifecycleException)((ResourceGroupLifecycleException)var4.read_value(class$weblogic$management$ResourceGroupLifecycleException == null ? (class$weblogic$management$ResourceGroupLifecycleException = class$("weblogic.management.ResourceGroupLifecycleException")) : class$weblogic$management$ResourceGroupLifecycleException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.resumeResourceGroup(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void setDesiredPartitionState(String var1, String var2, String[] var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("setDesiredPartitionState", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var3, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/management/PartitionLifeCycleEx:1.0")) {
               throw (PartitionLifeCycleException)((PartitionLifeCycleException)var4.read_value(class$weblogic$management$PartitionLifeCycleException == null ? (class$weblogic$management$PartitionLifeCycleException = class$("weblogic.management.PartitionLifeCycleException")) : class$weblogic$management$PartitionLifeCycleException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.setDesiredPartitionState(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void setDesiredResourceGroupState(String var1, String var2, String var3, String[] var4) throws RemoteException {
      try {
         InputStream var5 = null;

         try {
            OutputStream var6 = (OutputStream)this._request("setDesiredResourceGroupState", true);
            var6.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_value(var3, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_value(var4, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var6);
         } catch (ApplicationException var14) {
            var5 = (InputStream)var14.getInputStream();
            String var8 = var5.read_string();
            if (var8.equals("IDL:weblogic/management/ResourceGroupLifecycleEx:1.0")) {
               throw (ResourceGroupLifecycleException)((ResourceGroupLifecycleException)var5.read_value(class$weblogic$management$ResourceGroupLifecycleException == null ? (class$weblogic$management$ResourceGroupLifecycleException = class$("weblogic.management.ResourceGroupLifecycleException")) : class$weblogic$management$ResourceGroupLifecycleException));
            }

            throw new UnexpectedException(var8);
         } catch (RemarshalException var15) {
            this.setDesiredResourceGroupState(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void setInvalid(String var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = (OutputStream)this._request("setInvalid", false);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.setInvalid(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final void setState(String var1, String var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("setState", false);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.setState(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void shutDownPartition(String var1, int var2, boolean var3, boolean var4, String[] var5) throws RemoteException {
      try {
         InputStream var6 = null;

         try {
            OutputStream var7 = (OutputStream)this._request("shutDownPartition", true);
            var7.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var7.write_long(var2);
            var7.write_boolean(var3);
            var7.write_boolean(var4);
            var7.write_value(var5, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var7);
         } catch (ApplicationException var15) {
            var6 = (InputStream)var15.getInputStream();
            String var9 = var6.read_string();
            if (var9.equals("IDL:weblogic/management/PartitionLifeCycleEx:1.0")) {
               throw (PartitionLifeCycleException)((PartitionLifeCycleException)var6.read_value(class$weblogic$management$PartitionLifeCycleException == null ? (class$weblogic$management$PartitionLifeCycleException = class$("weblogic.management.PartitionLifeCycleException")) : class$weblogic$management$PartitionLifeCycleException));
            }

            throw new UnexpectedException(var9);
         } catch (RemarshalException var16) {
            this.shutDownPartition(var1, var2, var3, var4, var5);
         } finally {
            this._releaseReply(var6);
         }

      } catch (SystemException var18) {
         throw Util.mapSystemException(var18);
      }
   }

   public final void shutDownResourceGroup(String var1, String var2, int var3, boolean var4, boolean var5, String[] var6) throws RemoteException {
      try {
         InputStream var7 = null;

         try {
            OutputStream var8 = (OutputStream)this._request("shutDownResourceGroup", true);
            var8.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var8.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var8.write_long(var3);
            var8.write_boolean(var4);
            var8.write_boolean(var5);
            var8.write_value(var6, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var8);
         } catch (ApplicationException var16) {
            var7 = (InputStream)var16.getInputStream();
            String var10 = var7.read_string();
            if (var10.equals("IDL:weblogic/management/ResourceGroupLifecycleEx:1.0")) {
               throw (ResourceGroupLifecycleException)((ResourceGroupLifecycleException)var7.read_value(class$weblogic$management$ResourceGroupLifecycleException == null ? (class$weblogic$management$ResourceGroupLifecycleException = class$("weblogic.management.ResourceGroupLifecycleException")) : class$weblogic$management$ResourceGroupLifecycleException));
            }

            throw new UnexpectedException(var10);
         } catch (RemarshalException var17) {
            this.shutDownResourceGroup(var1, var2, var3, var4, var5, var6);
         } finally {
            this._releaseReply(var7);
         }

      } catch (SystemException var19) {
         throw Util.mapSystemException(var19);
      }
   }

   public final void shutdown() throws RemoteException {
      try {
         InputStream var1 = null;

         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("shutdown__", true);
            this._invoke(var2);
         } catch (ApplicationException var10) {
            var1 = (InputStream)var10.getInputStream();
            String var4 = var1.read_string();
            if (var4.equals("IDL:weblogic/server/ServerLifecycleEx:1.0")) {
               throw (ServerLifecycleException)((ServerLifecycleException)var1.read_value(class$weblogic$server$ServerLifecycleException == null ? (class$weblogic$server$ServerLifecycleException = class$("weblogic.server.ServerLifecycleException")) : class$weblogic$server$ServerLifecycleException));
            }

            throw new UnexpectedException(var4);
         } catch (RemarshalException var11) {
            this.shutdown();
         } finally {
            this._releaseReply(var1);
         }

      } catch (SystemException var13) {
         throw Util.mapSystemException(var13);
      }
   }

   public final void shutdown(int var1, boolean var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            org.omg.CORBA.portable.OutputStream var4 = this._request("shutdown__long__boolean", true);
            var4.write_long(var1);
            var4.write_boolean(var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:weblogic/server/ServerLifecycleEx:1.0")) {
               throw (ServerLifecycleException)((ServerLifecycleException)var3.read_value(class$weblogic$server$ServerLifecycleException == null ? (class$weblogic$server$ServerLifecycleException = class$("weblogic.server.ServerLifecycleException")) : class$weblogic$server$ServerLifecycleException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.shutdown(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void shutdown(int var1, boolean var2, boolean var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            org.omg.CORBA.portable.OutputStream var5 = this._request("shutdown__long__boolean__boolean", true);
            var5.write_long(var1);
            var5.write_boolean(var2);
            var5.write_boolean(var3);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:weblogic/server/ServerLifecycleEx:1.0")) {
               throw (ServerLifecycleException)((ServerLifecycleException)var4.read_value(class$weblogic$server$ServerLifecycleException == null ? (class$weblogic$server$ServerLifecycleException = class$("weblogic.server.ServerLifecycleException")) : class$weblogic$server$ServerLifecycleException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.shutdown(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void startPartition(String var1, String var2, boolean var3, String[] var4) throws RemoteException {
      try {
         InputStream var5 = null;

         try {
            OutputStream var6 = (OutputStream)this._request("startPartition", true);
            var6.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_boolean(var3);
            var6.write_value(var4, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var6);
         } catch (ApplicationException var14) {
            var5 = (InputStream)var14.getInputStream();
            String var8 = var5.read_string();
            if (var8.equals("IDL:weblogic/management/PartitionLifeCycleEx:1.0")) {
               throw (PartitionLifeCycleException)((PartitionLifeCycleException)var5.read_value(class$weblogic$management$PartitionLifeCycleException == null ? (class$weblogic$management$PartitionLifeCycleException = class$("weblogic.management.PartitionLifeCycleException")) : class$weblogic$management$PartitionLifeCycleException));
            }

            throw new UnexpectedException(var8);
         } catch (RemarshalException var15) {
            this.startPartition(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void startResourceGroup(String var1, String var2, boolean var3, String[] var4) throws RemoteException {
      try {
         InputStream var5 = null;

         try {
            OutputStream var6 = (OutputStream)this._request("startResourceGroup", true);
            var6.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_boolean(var3);
            var6.write_value(var4, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var6);
         } catch (ApplicationException var14) {
            var5 = (InputStream)var14.getInputStream();
            String var8 = var5.read_string();
            if (var8.equals("IDL:weblogic/management/ResourceGroupLifecycleEx:1.0")) {
               throw (ResourceGroupLifecycleException)((ResourceGroupLifecycleException)var5.read_value(class$weblogic$management$ResourceGroupLifecycleException == null ? (class$weblogic$management$ResourceGroupLifecycleException = class$("weblogic.management.ResourceGroupLifecycleException")) : class$weblogic$management$ResourceGroupLifecycleException));
            }

            throw new UnexpectedException(var8);
         } catch (RemarshalException var15) {
            this.startResourceGroup(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void suspend() throws RemoteException {
      try {
         InputStream var1 = null;

         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("suspend__", true);
            this._invoke(var2);
         } catch (ApplicationException var10) {
            var1 = (InputStream)var10.getInputStream();
            String var4 = var1.read_string();
            if (var4.equals("IDL:weblogic/server/ServerLifecycleEx:1.0")) {
               throw (ServerLifecycleException)((ServerLifecycleException)var1.read_value(class$weblogic$server$ServerLifecycleException == null ? (class$weblogic$server$ServerLifecycleException = class$("weblogic.server.ServerLifecycleException")) : class$weblogic$server$ServerLifecycleException));
            }

            throw new UnexpectedException(var4);
         } catch (RemarshalException var11) {
            this.suspend();
         } finally {
            this._releaseReply(var1);
         }

      } catch (SystemException var13) {
         throw Util.mapSystemException(var13);
      }
   }

   public final void suspend(int var1, boolean var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            org.omg.CORBA.portable.OutputStream var4 = this._request("suspend__long__boolean", true);
            var4.write_long(var1);
            var4.write_boolean(var2);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:weblogic/server/ServerLifecycleEx:1.0")) {
               throw (ServerLifecycleException)((ServerLifecycleException)var3.read_value(class$weblogic$server$ServerLifecycleException == null ? (class$weblogic$server$ServerLifecycleException = class$("weblogic.server.ServerLifecycleException")) : class$weblogic$server$ServerLifecycleException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.suspend(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void suspendPartition(String var1, int var2, boolean var3, String[] var4) throws RemoteException {
      try {
         InputStream var5 = null;

         try {
            OutputStream var6 = (OutputStream)this._request("suspendPartition", true);
            var6.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_long(var2);
            var6.write_boolean(var3);
            var6.write_value(var4, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var6);
         } catch (ApplicationException var14) {
            var5 = (InputStream)var14.getInputStream();
            String var8 = var5.read_string();
            if (var8.equals("IDL:weblogic/management/PartitionLifeCycleEx:1.0")) {
               throw (PartitionLifeCycleException)((PartitionLifeCycleException)var5.read_value(class$weblogic$management$PartitionLifeCycleException == null ? (class$weblogic$management$PartitionLifeCycleException = class$("weblogic.management.PartitionLifeCycleException")) : class$weblogic$management$PartitionLifeCycleException));
            }

            throw new UnexpectedException(var8);
         } catch (RemarshalException var15) {
            this.suspendPartition(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void suspendResourceGroup(String var1, String var2, int var3, boolean var4, String[] var5) throws RemoteException {
      try {
         InputStream var6 = null;

         try {
            OutputStream var7 = (OutputStream)this._request("suspendResourceGroup", true);
            var7.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var7.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var7.write_long(var3);
            var7.write_boolean(var4);
            var7.write_value(var5, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            this._invoke(var7);
         } catch (ApplicationException var15) {
            var6 = (InputStream)var15.getInputStream();
            String var9 = var6.read_string();
            if (var9.equals("IDL:weblogic/management/ResourceGroupLifecycleEx:1.0")) {
               throw (ResourceGroupLifecycleException)((ResourceGroupLifecycleException)var6.read_value(class$weblogic$management$ResourceGroupLifecycleException == null ? (class$weblogic$management$ResourceGroupLifecycleException = class$("weblogic.management.ResourceGroupLifecycleException")) : class$weblogic$management$ResourceGroupLifecycleException));
            }

            throw new UnexpectedException(var9);
         } catch (RemarshalException var16) {
            this.suspendResourceGroup(var1, var2, var3, var4, var5);
         } finally {
            this._releaseReply(var6);
         }

      } catch (SystemException var18) {
         throw Util.mapSystemException(var18);
      }
   }
}
