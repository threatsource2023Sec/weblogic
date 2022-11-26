package com.oracle.buzzmessagebus.impl;

import com.oracle.common.net.exabus.EndPoint;
import java.util.concurrent.atomic.AtomicLong;

class ConnectionInfo {
   private final AtomicLong nextId = new AtomicLong(1L);
   private final IdToMsg ourIdToMessageMap = new IdToMsg("our");
   private final IdToMsg theirIdToMessageMap = new IdToMsg("their");
   private ConnectionState connectionState;
   private final EndPoint endPoint;

   ConnectionInfo(EndPoint endPoint, ConnectionState connectionState) {
      this.endPoint = endPoint;
      this.connectionState = connectionState;
   }

   IdToMsg getOurMessageIdToMessageInfoMap() {
      return this.ourIdToMessageMap;
   }

   IdToMsg getTheirMessageIdToMessageInfoMap() {
      return this.theirIdToMessageMap;
   }

   EndPoint getEndPoint() {
      return this.endPoint;
   }

   long getNextId() {
      return this.nextId.getAndIncrement();
   }

   ConnectionState state() {
      return this.connectionState;
   }

   void state(ConnectionState x) {
      this.connectionState = x;
   }

   void awaitInitReceiptReceived() {
      this.connectionState = ConnectionInfo.ConnectionState.CLOSED;
   }

   public String toString() {
      return "{ConnectionInfo " + this.ourIdToMessageMap.size() + ' ' + this.theirIdToMessageMap.size() + ' ' + this.nextId + ' ' + this.state() + ' ' + this.endPoint + '}';
   }

   static enum ConnectionState {
      PENDING_CONNECT,
      CONNECTED,
      INITIALIZED,
      CLOSED;
   }
}
