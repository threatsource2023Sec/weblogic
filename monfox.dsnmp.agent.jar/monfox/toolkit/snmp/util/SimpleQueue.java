package monfox.toolkit.snmp.util;

import java.util.Enumeration;
import java.util.Vector;

public final class SimpleQueue {
   protected Vector _vector = new Vector();
   protected int _maxSize = -1;
   protected String _name = a("GS->8WY");
   private boolean a = false;
   private static final String b = "$Id: SimpleQueue.java,v 1.3 2010/02/15 22:21:15 sking Exp $";

   public SimpleQueue(String var1) {
      this._name = var1;
   }

   public SimpleQueue() {
   }

   public SimpleQueue(int var1) {
      this._maxSize = var1;
   }

   public SimpleQueue(String var1, int var2) {
      this._maxSize = var2;
      this._name = var1;
   }

   public synchronized boolean willBlock() {
      return this._vector.size() == 0;
   }

   public Enumeration elements() {
      return this._vector.elements();
   }

   public int size() {
      return this._vector.size();
   }

   public synchronized Object peekFront() throws InterruptedException {
      int var1 = WorkItem.d;

      int var10000;
      while(true) {
         if (!this.a) {
            var10000 = this._vector.size();
            if (var1 != 0) {
               break;
            }

            if (var10000 == 0) {
               this.wait(2000L);
               if (var1 == 0) {
                  continue;
               }
            }
         }

         var10000 = this.a;
         break;
      }

      if (var10000 != 0) {
         throw new InterruptedException(a("[S7:'@H3+0V"));
      } else {
         return this._vector.firstElement();
      }
   }

   public synchronized Object popFront(long var1) throws InterruptedException, QueueTimeoutException {
      int var9 = WorkItem.d;
      long var3 = System.currentTimeMillis();

      int var10000;
      while(true) {
         if (!this.a) {
            var10000 = this._vector.size();
            if (var9 != 0) {
               break;
            }

            if (var10000 == 0) {
               long var5 = System.currentTimeMillis();
               long var7 = var1 - (var5 - var3);
               if (var7 <= 0L) {
                  throw new QueueTimeoutException(a("fT.::GI"));
               }

               this.wait(var7);
               if (var9 == 0) {
                  continue;
               }
            }
         }

         var10000 = this.a;
         break;
      }

      if (var10000 != 0) {
         throw new InterruptedException(a("[S7:'@H3+0V"));
      } else {
         Object var10 = this._vector.firstElement();
         this._vector.removeElementAt(0);
         this.notifyAll();
         return var10;
      }
   }

   public synchronized Object popFront() throws InterruptedException {
      int var2 = WorkItem.d;

      int var10000;
      while(true) {
         if (!this.a) {
            var10000 = this._vector.size();
            if (var2 != 0) {
               break;
            }

            if (var10000 == 0) {
               this.wait(2000L);
               if (var2 == 0) {
                  continue;
               }
            }
         }

         var10000 = this.a;
         break;
      }

      if (var10000 != 0) {
         throw new InterruptedException(a("[S7:'@H3+0V"));
      } else {
         Object var1 = this._vector.firstElement();
         this._vector.removeElementAt(0);
         this.notifyAll();
         return var1;
      }
   }

   public synchronized void pushBack(Object var1) throws InterruptedException {
      int var2 = WorkItem.d;

      int var10000;
      while(true) {
         if (!this.a) {
            var10000 = this._maxSize;
            if (var2 != 0) {
               break;
            }

            if (var10000 > 0 && this._vector.size() >= this._maxSize) {
               this.wait();
               if (var2 == 0) {
                  continue;
               }
            }
         }

         var10000 = this.a;
         break;
      }

      if (var10000 != 0) {
         throw new InterruptedException(a("[S7:'@H3+0V"));
      } else {
         this._vector.addElement(var1);
         this.notifyAll();
      }
   }

   public synchronized void remove(Object var1) {
      this._vector.removeElement(var1);
      this.notifyAll();
   }

   public synchronized void remove(int var1) {
      this._vector.removeElementAt(var1);
      this.notifyAll();
   }

   public int getMaxSize() {
      return this._maxSize;
   }

   public void setMaxSize(int var1) {
      this._maxSize = var1;
   }

   public Vector getVector() {
      return this._vector;
   }

   public synchronized void shutdown() {
      this.a = true;
      this.notifyAll();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 50;
               break;
            case 1:
               var10003 = 61;
               break;
            case 2:
               var10003 = 67;
               break;
            case 3:
               var10003 = 95;
               break;
            default:
               var10003 = 85;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
