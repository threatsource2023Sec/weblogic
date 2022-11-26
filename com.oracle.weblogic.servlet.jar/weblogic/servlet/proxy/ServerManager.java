package weblogic.servlet.proxy;

import java.util.ArrayList;
import java.util.HashMap;

final class ServerManager {
   private final HashMap jvmidToIndexMap;
   private final ServiceUnavailableException exception;
   private final ArrayList serverList;
   private int index;
   private int size;

   private ServerManager() {
      this.index = 0;
      this.jvmidToIndexMap = new HashMap(5);
      this.serverList = new ArrayList();
      this.exception = new ServiceUnavailableException();
   }

   static ServerManager getServerManager() {
      return ServerManager.SingletonMaker.singleton;
   }

   synchronized ServerFactory getServerFactory(int jvmidHash) throws ServiceUnavailableException {
      if (this.size == 0) {
         throw this.exception;
      } else {
         Integer indexInt = (Integer)this.jvmidToIndexMap.get(new Integer(jvmidHash));
         int i = true;
         if (indexInt != null) {
            int i = indexInt;
            return (ServerFactory)this.serverList.get(i);
         } else {
            this.index = this.index++ % this.size;
            return (ServerFactory)this.serverList.get(this.index);
         }
      }
   }

   private void reset() {
      this.size = this.serverList.size();
      this.jvmidToIndexMap.clear();

      for(int i = 0; i < this.size; ++i) {
         ServerFactory factory = (ServerFactory)this.serverList.get(i);
         this.jvmidToIndexMap.put(new Integer(factory.hashCode()), new Integer(i));
      }

   }

   synchronized void addServer(int jvmidHash, String host, int port) {
      ServerFactory factory = new ServerFactory(jvmidHash, host, port);
      this.serverList.add(factory);
      this.size = this.serverList.size();
      this.jvmidToIndexMap.put(new Integer(jvmidHash), new Integer(this.size - 1));
   }

   synchronized void removeServer(int jvmidHash) {
      Integer hash = new Integer(jvmidHash);
      Integer indexInt = (Integer)this.jvmidToIndexMap.get(hash);
      this.jvmidToIndexMap.remove(hash);
      int index = indexInt;
      ServerFactory factory = (ServerFactory)this.serverList.remove(index);
      factory.cleanup();
      this.reset();
   }

   // $FF: synthetic method
   ServerManager(Object x0) {
      this();
   }

   static class SingletonMaker {
      static final ServerManager singleton = new ServerManager();
   }
}
