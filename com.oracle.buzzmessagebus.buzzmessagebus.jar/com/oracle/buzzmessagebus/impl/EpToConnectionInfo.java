package com.oracle.buzzmessagebus.impl;

import com.oracle.common.net.exabus.EndPoint;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class EpToConnectionInfo {
   private final Map epToConnectionInfo = new ConcurrentHashMap();

   ConnectionInfo get(EndPoint endPoint) {
      return (ConnectionInfo)this.epToConnectionInfo.get(endPoint);
   }

   void put(EndPoint endPoint, ConnectionInfo connectionInfo) {
      this.epToConnectionInfo.put(endPoint, connectionInfo);
   }

   int size() {
      return this.epToConnectionInfo.size();
   }

   Set entrySet() {
      return this.epToConnectionInfo.entrySet();
   }

   ConnectionInfo remove(EndPoint endPoint) {
      return (ConnectionInfo)this.epToConnectionInfo.remove(endPoint);
   }

   EndPoint[] keys() {
      return (EndPoint[])this.epToConnectionInfo.keySet().toArray(new EndPoint[0]);
   }

   Iterator valueIterator() {
      return this.epToConnectionInfo.values().iterator();
   }
}
