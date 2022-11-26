package weblogic.iiop.protocol;

import org.jvnet.hk2.annotations.Contract;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.iiop.EndPoint;
import weblogic.protocol.ServerIdentity;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.io.Chunk;

@Contract
public abstract class IiopProtocolFacade {
   public static final int WLS_RANK = 10;

   public static boolean isServer() {
      return IiopProtocolFacade.Singleton.instance.doIsServer();
   }

   public static boolean fastEquals(Object key1, Object key2) {
      return IiopProtocolFacade.Singleton.instance.doFastEquals(key1, key2);
   }

   public static Object toObjectKey(Object storedKey) {
      return IiopProtocolFacade.Singleton.instance.doConvertToKey(storedKey);
   }

   public static ServerIdentity getTargetForRead(Object key) {
      return IiopProtocolFacade.Singleton.instance.doGetTargetForRead(key);
   }

   public static boolean mustReplaceAddress(Object key) {
      return IiopProtocolFacade.Singleton.instance.doMustReplaceAddress(key);
   }

   public static boolean isServerLocalObject(Object key) {
      return IiopProtocolFacade.Singleton.instance.doIsServerLocalObject(key);
   }

   public static void writeListenPoint(CorbaOutputStream out, Object objectKey, ListenPoint listenPoint) {
      IiopProtocolFacade.Singleton.instance.doWriteListenPoint(out, objectKey, listenPoint);
   }

   static ListenPoint getReplacement(ListenPoint listenPoint, InputStream in) {
      return IiopProtocolFacade.Singleton.instance.doGetReplacement(listenPoint, in);
   }

   public static ListenPoint getReplacement(ListenPoint listenPoint, OutputStream out, ServerIdentity target) {
      return IiopProtocolFacade.Singleton.instance.doGetReplacement(listenPoint, out, target);
   }

   public static CorbaInputStream createInputStream(EndPoint endPoint, byte[] buffer) {
      return IiopProtocolFacade.Singleton.instance.doCreateInputStream(endPoint, buffer);
   }

   public static CorbaInputStream createInputStream(byte[] buffer) {
      return IiopProtocolFacade.Singleton.instance.doCreateInputStream(buffer);
   }

   public static CorbaInputStream createInputStream(Chunk head) {
      return IiopProtocolFacade.Singleton.instance.doCreateInputStream(head);
   }

   public static CorbaOutputStream createOutputStream() {
      return IiopProtocolFacade.Singleton.instance.doCreateOutputStream();
   }

   protected abstract boolean doIsServer();

   protected abstract boolean doFastEquals(Object var1, Object var2);

   protected Object doConvertToKey(Object storedKey) {
      return storedKey;
   }

   protected abstract ServerIdentity doGetTargetForRead(Object var1);

   protected abstract boolean doMustReplaceAddress(Object var1);

   protected abstract boolean doIsServerLocalObject(Object var1);

   protected abstract void doWriteListenPoint(CorbaOutputStream var1, Object var2, ListenPoint var3);

   protected abstract ListenPoint doGetReplacement(ListenPoint var1, InputStream var2);

   protected abstract ListenPoint doGetReplacement(ListenPoint var1, OutputStream var2, ServerIdentity var3);

   protected abstract CorbaInputStream doCreateInputStream(EndPoint var1, byte[] var2);

   protected abstract CorbaInputStream doCreateInputStream(byte[] var1);

   protected abstract CorbaInputStream doCreateInputStream(Chunk var1);

   protected abstract CorbaOutputStream doCreateOutputStream();

   private static final class Singleton {
      private static final IiopProtocolFacade instance = (IiopProtocolFacade)LocatorUtilities.getService(IiopProtocolFacade.class);
   }
}
