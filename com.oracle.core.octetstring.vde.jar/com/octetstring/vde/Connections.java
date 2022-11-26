package com.octetstring.vde;

import java.util.Vector;

public class Connections {
   private static Connections instance = null;
   private static Vector active = null;

   private Connections() {
   }

   public static Connections getInstance() {
      if (instance == null) {
         instance = new Connections();
         active = new Vector();
      }

      return instance;
   }

   public synchronized Connection getConnection() {
      if (active.isEmpty()) {
         return null;
      } else {
         Connection aCon = (Connection)active.elementAt(0);
         active.removeElementAt(0);
         return aCon;
      }
   }

   public boolean hasMore() {
      return !active.isEmpty();
   }

   public synchronized void notifyme() {
      this.notify();
   }

   public void registerConnection(Connection conn) {
      active.addElement(conn);
      this.notifyme();
   }

   public synchronized void waitme() {
      try {
         this.wait();
      } catch (InterruptedException var2) {
      }

   }
}
