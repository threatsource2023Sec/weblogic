package monfox.toolkit.snmp.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class OidTree implements Serializable {
   static final long serialVersionUID = 280734071502754238L;
   public static Node[] EMPTY = new Node[0];
   private Node _root = null;
   private Hashtable _nodesByName = new Hashtable(89);
   private int _arraySize = 30;
   private int _chunkSize = 1;

   public OidTree() {
      this.a();
   }

   public OidTree(int var1, int var2) {
      this._arraySize = var1;
      this._chunkSize = var2;
      this.a();
   }

   private void a() {
      this._root = new Node(-1L);
      this._root.add(0L).name = a("(/,\u001c0");
      this._root.add(1L).name = a("\"?*");
      this._root.add(2L).name = a("!#,\u00060f%6\u0007i(/,\u001c0");
   }

   public static long[] str2oid(String var0) throws NumberFormatException {
      int var5 = WorkItem.d;
      StringTokenizer var1 = new StringTokenizer(var0, a("ev"), false);
      int var2 = var1.countTokens();
      int var3 = 0;
      long[] var4 = new long[var2];

      long[] var10000;
      while(true) {
         if (var1.hasMoreTokens()) {
            var10000 = var4;
            if (var5 != 0) {
               break;
            }

            var4[var3++] = Long.parseLong(var1.nextToken());
            if (var5 == 0) {
               continue;
            }
         }

         var10000 = var4;
         break;
      }

      return var10000;
   }

   public Node add(String var1, String var2, Object var3) throws NumberFormatException, NoSuchElementException {
      return this.add((String)null, var1, var2, var3);
   }

   public Node add(String var1, String var2, String var3, Object var4) throws NumberFormatException, NoSuchElementException {
      int var11 = WorkItem.d;
      StringTokenizer var5 = new StringTokenizer(var2, a("ev"), false);
      String var6 = var5.nextToken();
      Node var7 = this._root;
      long var8;
      if (!Character.isDigit(var6.charAt(0))) {
         var7 = this.findByName(var6);
         if (var7 == null) {
            var7 = this._root.get(var6);
            if (var7 == null) {
               throw new NoSuchElementException(var6);
            }
         }
      } else {
         var8 = Long.parseLong(var6);
         var7 = this._root.get(var8);
         if (var7 == null) {
            throw new NoSuchElementException(var6);
         }
      }

      Node var10000;
      while(true) {
         if (var5.hasMoreTokens()) {
            var8 = Long.parseLong(var5.nextToken());
            Node var10 = var7.get(var8);
            var10000 = var10;
            if (var11 != 0) {
               break;
            }

            if (var10 == null) {
               var10 = var7.add(var8);
            }

            var7 = var10;
            if (var11 == 0) {
               continue;
            }
         }

         var7.name = var3;
         var10000 = var7;
         break;
      }

      var10000.info = var4;
      if (var3 != null) {
         if (var1 != null) {
            this._nodesByName.put(var3, var7);
            this._nodesByName.put(var1 + ":" + var3, var7);
            if (var11 == 0) {
               return var7;
            }
         }

         this._nodesByName.put(var3, var7);
      }

      return var7;
   }

   public Node get(String var1) throws NumberFormatException {
      int var7 = WorkItem.d;
      StringTokenizer var2 = new StringTokenizer(var1, a("ev"), false);
      String var3 = var2.nextToken();
      Node var4 = this._root;
      long var5;
      if (!Character.isDigit(var3.charAt(0))) {
         var4 = this.findByName(var3);
         if (var4 == null) {
            var4 = this._root.get(var3);
            if (var4 == null) {
               throw new NoSuchElementException(var3);
            }
         }
      } else {
         var5 = Long.parseLong(var3);
         var4 = this._root.get(var5);
      }

      Node var10000;
      while(true) {
         if (var2.hasMoreTokens()) {
            var10000 = var4;
            if (var7 != 0) {
               break;
            }

            if (var4 != null) {
               var5 = Long.parseLong(var2.nextToken());
               var4 = var4.get(var5);
               if (var7 == 0) {
                  continue;
               }
            }
         }

         var10000 = var4;
         break;
      }

      return var10000;
   }

   public Node getBase(String var1) throws NumberFormatException {
      int var8 = WorkItem.d;
      StringTokenizer var2 = new StringTokenizer(var1, a("ev"), false);
      String var3 = var2.nextToken();
      Node var4 = this._root;
      Node var5 = null;
      long var6;
      if (!Character.isDigit(var3.charAt(0))) {
         var4 = this.findByName(var3);
         if (var4 == null) {
            var4 = this._root.get(var3);
            if (var4 == null) {
               throw new NoSuchElementException(var3);
            }
         }
      } else {
         var6 = Long.parseLong(var3);
         var4 = this._root.get(var6);
      }

      if (var4 != null && var4.name != null) {
         var5 = var4;
      }

      Node var10000;
      while(true) {
         if (var2.hasMoreTokens()) {
            var10000 = var4;
            if (var8 != 0) {
               break;
            }

            if (var4 != null) {
               var6 = Long.parseLong(var2.nextToken());
               var4 = var4.get(var6);
               if (var4 != null && var4.name != null) {
                  var5 = var4;
               }

               if (var8 == 0) {
                  continue;
               }
            }
         }

         var10000 = var5;
         break;
      }

      return var10000;
   }

   public Node get(long[] var1) {
      return this.get(var1, var1.length);
   }

   public Node get(long[] var1, int var2) {
      int var5 = WorkItem.d;
      Node var3 = this._root;
      int var4 = 0;

      Node var10000;
      while(true) {
         if (var4 < var2) {
            var10000 = var3;
            if (var5 != 0) {
               break;
            }

            if (var3 != null) {
               var3 = var3.get(var1[var4]);
               ++var4;
               if (var5 == 0) {
                  continue;
               }
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public Node getBase(long[] var1) {
      return this.get(var1, var1.length);
   }

   public Node getBase(long[] var1, int var2) {
      int var6 = WorkItem.d;
      Node var3 = this._root;
      Node var4 = null;
      int var5 = 0;

      Node var10000;
      while(true) {
         if (var5 < var2) {
            var10000 = var3;
            if (var6 != 0) {
               break;
            }

            if (var3 != null) {
               var3 = var3.get(var1[var5]);
               if (var3 != null && var3.name != null) {
                  var4 = var3;
               }

               ++var5;
               if (var6 == 0) {
                  continue;
               }
            }
         }

         var10000 = var4;
         break;
      }

      return var10000;
   }

   public Node getClosest(long[] var1, int var2) {
      int var6 = WorkItem.d;
      Node var3 = this._root;
      int var4 = 0;

      Node var10000;
      while(true) {
         if (var4 < var2) {
            Node var5 = var3.get(var1[var4]);
            var10000 = var5;
            if (var6 != 0) {
               break;
            }

            if (var5 == null) {
               return var3;
            }

            var3 = var5;
            if (var6 != 0) {
               return var3;
            }

            ++var4;
            if (var6 == 0) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public Node getNext(long[] var1, int var2) {
      int var7 = WorkItem.d;
      Node var3 = this._root;
      int var4 = 0;
      int var5 = 0;

      Node var10000;
      while(true) {
         if (var5 < var2) {
            Node var6 = var3.get(var1[var5]);
            var10000 = var6;
            if (var7 != 0) {
               break;
            }

            if (var6 != null) {
               var3 = var6;
               ++var4;
               ++var5;
               if (var7 == 0) {
                  continue;
               }
            }
         }

         if (var4 == var2) {
            return var3.nextNode(false);
         }

         if (var4 < var2) {
            Node var8 = var3.childlist;

            while(var8 != null) {
               var10000 = var8;
               if (var7 != 0) {
                  return var10000;
               }

               if (var8.number > var1[var4]) {
                  return var8;
               }

               var8 = var8.next;
               if (var7 != 0) {
                  break;
               }
            }
         }

         var10000 = var3.nextNode(true);
         break;
      }

      return var10000;
   }

   public Node add(long[] var1, String var2, Object var3) {
      return this.add(var1, var1.length, var2, var3);
   }

   public Node add(long[] var1, int var2, String var3, Object var4) {
      return this.add(var1, var2, (String)null, var3, var4);
   }

   public Node add(long[] var1, int var2, String var3, String var4, Object var5) {
      int var11 = WorkItem.d;
      Node var6 = this._root;
      int var7 = 0;

      Node var10000;
      while(true) {
         if (var7 < var2) {
            long var8 = var1[var7];
            Node var10 = var6.get(var8);
            var10000 = var10;
            if (var11 != 0) {
               break;
            }

            if (var10 == null) {
               var10 = var6.add(var8);
            }

            var6 = var10;
            ++var7;
            if (var11 == 0) {
               continue;
            }
         }

         var6.name = var4;
         var10000 = var6;
         break;
      }

      var10000.info = var5;
      if (var4 != null) {
         if (var3 != null) {
            this._nodesByName.put(var3 + ":" + var4, var6);
            this._nodesByName.put(var4, var6);
            if (var11 == 0) {
               return var6;
            }
         }

         this._nodesByName.put(var4, var6);
      }

      return var6;
   }

   public Node findByName(String var1) {
      Node var2 = (Node)this._nodesByName.get(var1);
      return var2;
   }

   public void removeByName(String var1) {
      this._nodesByName.remove(var1);
   }

   public void optimize(int var1) {
      b var2 = new b(var1);
      this._root.walk(var2);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      c var2 = new c(var1);
      this._root.walk(var2);
      return var1.toString();
   }

   public Node getRoot() {
      return this._root;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 75;
               break;
            case 1:
               var10003 = 76;
               break;
            case 2:
               var10003 = 69;
               break;
            case 3:
               var10003 = 104;
               break;
            default:
               var10003 = 68;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public class Node implements Serializable {
      static final long serialVersionUID = 7280154152165527565L;
      public String name = null;
      public long number = 0L;
      public Object info = null;
      public int level = 0;
      public Node parent = null;
      public Node[] childarr;
      public Node childlist;
      public Node next;
      public Node prev;

      public Node(long var2) {
         this.childarr = OidTree.EMPTY;
         this.childlist = null;
         this.next = null;
         this.prev = null;
         this.number = var2;
      }

      public boolean walk(Walker var1) {
         int var3 = WorkItem.d;
         if (!var1.process(this)) {
            return false;
         } else {
            Node var2 = this.childlist;

            boolean var10000;
            while(true) {
               if (var2 != null) {
                  var10000 = var2.walk(var1);
                  if (var3 != 0) {
                     break;
                  }

                  if (!var10000) {
                     return false;
                  }

                  var2 = var2.next;
                  if (var3 == 0) {
                     continue;
                  }
               }

               var10000 = true;
               break;
            }

            return var10000;
         }
      }

      public synchronized Node getNext(Walker var1) {
         Node var2 = this.nextNode();

         while(var2 != null && !var1.process(this)) {
            var2 = var2.nextNode();
            if (WorkItem.d != 0) {
               break;
            }
         }

         return var2;
      }

      public Node nextNode() {
         return this.nextNode(false);
      }

      public Node nextNode(boolean var1) {
         Node var2 = this.childlist;
         if (!var1 && var2 != null) {
            return var2;
         } else {
            Node var3 = this.next;
            if (var3 != null) {
               return var3;
            } else {
               Node var4 = this.parent;
               return var4 != null ? var4.nextNode(true) : null;
            }
         }
      }

      public synchronized Node add(long var1) {
         int var8 = WorkItem.d;
         Node var3 = OidTree.this.new Node(var1);
         var3.parent = this;
         var3.level = this.level + 1;
         Node var4 = null;
         Node var5 = this.childlist;

         Node var10000;
         while(true) {
            if (var5 != null) {
               var10000 = var5;
               if (var8 != 0) {
                  break;
               }

               if (var5.number <= var1 || var8 != 0) {
                  var4 = var5;
                  var5 = var5.next;
                  if (var8 == 0) {
                     continue;
                  }
               }
            }

            var10000 = var4;
            break;
         }

         label49: {
            Node var6;
            if (var10000 == null) {
               var6 = this.childlist;
               this.childlist = var3;
               this.childlist.next = var6;
               this.childlist.prev = null;
               if (var6 != null) {
                  var6.prev = this.childlist;
               }

               if (var8 == 0) {
                  break label49;
               }
            }

            var6 = var4.next;
            var4.next = var3;
            var3.next = var6;
            var3.prev = var4;
            if (var6 != null) {
               var6.prev = var3;
            }
         }

         if (var1 > 0L && var1 < (long)OidTree.this._arraySize) {
            if (var1 >= (long)this.childarr.length) {
               label68: {
                  int var9 = this.childarr.length + OidTree.this._chunkSize;

                  while(var1 >= (long)var9) {
                     var9 += OidTree.this._chunkSize;
                     if (var8 != 0) {
                        break label68;
                     }

                     if (var8 != 0) {
                        break;
                     }
                  }

                  Node[] var7 = this.childarr;
                  this.childarr = new Node[var9];
                  System.arraycopy(var7, 0, this.childarr, 0, var7.length);
               }
            }

            this.childarr[(int)var1] = var3;
         }

         return var3;
      }

      public synchronized void remove(boolean var1) {
         if (var1) {
            this.childlist = null;
            this.childarr = OidTree.EMPTY;
            this.info = null;
            if (this.parent == null) {
               return;
            }

            this.parent.remove(this);
            if (WorkItem.d == 0) {
               return;
            }
         }

         if (this.info == null && this.name == null && this.childlist == null && this.parent != null) {
            this.parent.remove(this);
         }

      }

      public synchronized void remove(Node var1) {
         long var2;
         int var6;
         label40: {
            var6 = WorkItem.d;
            var2 = var1.number;
            Node var4;
            if (var1.prev == null) {
               var4 = var1.next;
               this.childlist = var4;
               if (var4 != null) {
                  var4.prev = null;
               }

               if (var6 == 0) {
                  break label40;
               }
            }

            var4 = var1.prev;
            Node var5 = var1.next;
            var4.next = var5;
            if (var5 != null) {
               var5.prev = var4;
            }
         }

         if (this.childlist == null) {
            this.childarr = OidTree.EMPTY;
            if (this.info != null || this.name != null || this.parent == null) {
               return;
            }

            this.parent.remove(this);
            if (var6 == 0) {
               return;
            }
         }

         if (this.childarr != null && var2 > 0L && var2 < (long)this.childarr.length) {
            this.childarr[(int)var2] = null;
         }

      }

      public synchronized void remove(long var1) {
         int var5 = WorkItem.d;
         Node var3 = null;
         Node var4 = this.childlist;

         int var10000;
         while(true) {
            if (var4 != null) {
               label56: {
                  long var6;
                  var10000 = (var6 = var4.number - var1) == 0L ? 0 : (var6 < 0L ? -1 : 1);
                  if (var5 != 0) {
                     break;
                  }

                  if (var10000 == 0) {
                     if (var3 == null) {
                        this.childlist = var4.next;
                        if (var5 == 0) {
                           break label56;
                        }
                     }

                     var3.next = var4.next;
                     if (var5 == 0) {
                        break label56;
                     }
                  }

                  var3 = var4;
                  var4 = var4.next;
                  if (var5 == 0) {
                     continue;
                  }
               }
            }

            if (this.childlist == null) {
               this.childarr = OidTree.EMPTY;
               if (this.info != null || this.name != null || this.parent == null) {
                  return;
               }

               this.parent.remove(this.number);
               if (var5 == 0) {
                  return;
               }
            }

            if (this.childarr == null) {
               return;
            }

            long var7;
            var10000 = (var7 = var1 - 0L) == 0L ? 0 : (var7 < 0L ? -1 : 1);
            break;
         }

         if (var10000 > 0 && var1 < (long)this.childarr.length) {
            this.childarr[(int)var1] = null;
         }

      }

      public synchronized Node get(long var1) {
         int var5 = WorkItem.d;
         Node[] var3 = this.childarr;
         if (var1 > 0L && var1 < (long)var3.length) {
            return var3[(int)var1];
         } else {
            Node var4 = this.childlist;

            Node var10000;
            while(true) {
               if (var4 != null) {
                  var10000 = var4;
                  if (var5 != 0) {
                     break;
                  }

                  if (var4.number == var1) {
                     return var4;
                  }

                  var4 = var4.next;
                  if (var5 == 0) {
                     continue;
                  }
               }

               var10000 = var4;
               break;
            }

            return var10000;
         }
      }

      public synchronized Node get(String var1) {
         int var3 = WorkItem.d;
         Node var2 = this.childlist;

         Node var10000;
         while(true) {
            if (var2 != null) {
               var10000 = var2;
               if (var3 != 0) {
                  break;
               }

               if (var2.name != null && var2.name.equals(var1)) {
                  return var2;
               }

               var2 = var2.next;
               if (var3 == 0) {
                  continue;
               }
            }

            var10000 = var2;
            break;
         }

         return var10000;
      }

      public long[] getLongArray() {
         int var3 = WorkItem.d;
         long[] var1 = new long[this.level];
         Node var2 = this;

         long[] var10000;
         while(true) {
            if (var2.parent != null) {
               var10000 = var1;
               if (var3 != 0) {
                  break;
               }

               var1[var2.level - 1] = var2.number;
               var2 = var2.parent;
               if (var3 == 0) {
                  continue;
               }
            }

            var10000 = var1;
            break;
         }

         return var10000;
      }

      public String toString() {
         StringBuffer var1 = new StringBuffer();
         var1.append("(").append(this.name).append(":");
         this.a(var1);
         var1.append(")");
         return var1.toString();
      }

      public String toOidString() {
         StringBuffer var1 = new StringBuffer();
         this.a(var1);
         return var1.toString();
      }

      private void a(StringBuffer var1) {
         if (this.level > 1) {
            this.parent.a(var1);
            var1.append(".");
         }

         var1.append(this.number);
      }
   }

   public interface Walker {
      boolean process(Node var1);
   }
}
