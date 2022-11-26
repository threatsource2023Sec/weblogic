package com.octetstring.vde;

import java.util.HashSet;
import java.util.Iterator;

public class DoSTracker {
   private HashSet activeConnections = new HashSet();
   private int totalConnections = 0;
   private int currentConnections = 0;

   public void incrConnections() {
      ++this.totalConnections;
      ++this.currentConnections;
   }

   public void resetCounters() {
      this.currentConnections = 0;
   }

   public Iterator getConIterator() {
      return this.activeConnections.iterator();
   }

   public int getActiveConCount() {
      return this.activeConnections.size();
   }

   public int getConCount() {
      return this.currentConnections;
   }

   public int getConCountHist() {
      return this.totalConnections;
   }

   public void add(Connection con) {
      this.activeConnections.add(con);
      this.incrConnections();
   }

   public boolean contains(Connection con) {
      return this.activeConnections.contains(con);
   }

   public void remove(Connection con) {
      if (this.activeConnections.contains(con)) {
         synchronized(this.activeConnections) {
            this.activeConnections.remove(con);
            --this.totalConnections;
            --this.currentConnections;
         }
      }

   }

   public String toString() {
      return "Active : " + this.activeConnections.toString() + " / Current : " + this.currentConnections + " / Total : " + this.totalConnections;
   }
}
