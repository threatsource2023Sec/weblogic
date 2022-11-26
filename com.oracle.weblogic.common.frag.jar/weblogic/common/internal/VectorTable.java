package weblogic.common.internal;

import java.io.Serializable;
import weblogic.common.ParamValue;

public final class VectorTable implements Cloneable, Serializable {
   private static final long serialVersionUID = 4749091629973897117L;
   ParamValue[] pvs;
   int[] hints;
   int used;
   boolean verbose;
   public static final int NOTFOUND = -1;
   private boolean nohashing;

   public static void main(String[] argv) {
      System.out.println("testing the VectorTable");
      VectorTable vt = new VectorTable();
      vt.setVerbose(true);
      ParamValue pv1 = new ParamValue("foo", 1, 1, "testdescr foo");
      ParamValue pv2 = new ParamValue("bar", 1, 1, "testdescr bar");
      if (vt.get("FOO") != null) {
         System.out.println("Failed #1");
      } else {
         System.out.println("OK#1");
      }

      if (vt.put(pv1) != pv1) {
         System.out.println("Failed #2");
      } else {
         System.out.println("OK#2");
      }

      if (vt.get("FOO") != pv1) {
         System.out.println("Failed #3");
      } else {
         System.out.println("OK#3");
      }

      if (vt.put(pv2) != pv2) {
         System.out.println("Failed #4");
      } else {
         System.out.println("OK#4");
      }

      if (vt.get("FOO") != pv1) {
         System.out.println("Failed #5");
      } else {
         System.out.println("OK#5");
      }

      if (vt.get("bAr") != pv2) {
         System.out.println("Failed #6");
      } else {
         System.out.println("OK#6");
      }

      vt.clear();
      if (vt.get("bAr") != null) {
         System.out.println("Failed #7");
      } else {
         System.out.println("OK#7");
      }

   }

   public final void setVerbose(boolean b) {
      this.verbose = b;
   }

   public final int used() {
      return this.used;
   }

   public final int size() {
      return this.pvs.length;
   }

   public VectorTable() {
      this(1);
   }

   public VectorTable(int i) {
      this.nohashing = true;
      this.pvs = new ParamValue[i];
      this.hints = new int[i];
      this.used = 0;
   }

   private VectorTable(VectorTable that) {
      this.nohashing = true;
      this.pvs = new ParamValue[that.pvs.length];
      this.hints = new int[that.hints.length];
      this.used = that.used;
      this.verbose = that.verbose;
      System.arraycopy(that.hints, 0, this.hints, 0, that.hints.length);

      for(int i = 0; i < that.pvs.length; ++i) {
         if (that.pvs[i] != null) {
            this.pvs[i] = (ParamValue)that.pvs[i].clone();
         }
      }

   }

   public synchronized Object clone() {
      return new VectorTable(this);
   }

   private void realloc() {
      if (this.used() >= this.size()) {
         int oldcapacity = this.size();
         int newcapacity = 2 * oldcapacity;
         ParamValue[] oldpvs = this.pvs;
         this.pvs = new ParamValue[newcapacity];
         System.arraycopy(oldpvs, 0, this.pvs, 0, oldcapacity);
         int[] oldhints = this.hints;
         this.hints = new int[newcapacity];
         System.arraycopy(oldhints, 0, this.hints, 0, oldcapacity);
         if (this.verbose) {
            System.out.println(this.toString() + "Expanded from " + oldcapacity + " to " + newcapacity + "; size=" + this.size());
         }

      }
   }

   private int hashhint(String S) {
      if (this.nohashing) {
         return 0;
      } else if (S == null) {
         return 0;
      } else {
         return S.length() == 0 ? 0 : S.charAt(0);
      }
   }

   private void clearOne(int i) {
      if (this.verbose) {
         System.out.println(this.toString() + "cleared item " + i);
      }

      if (this.pvs[i] != null) {
         this.pvs[i] = null;
         this.hints[i] = 0;
         --this.used;
      }

   }

   private ParamValue replaceOne(int i, ParamValue pv) {
      if (this.verbose) {
         System.out.println(this.toString() + " replaced item " + i);
      }

      this.hints[i] = this.hashhint(pv.name());
      this.pvs[i] = pv;
      return pv;
   }

   private int getEmpty() {
      int hint = this.size();
      this.realloc();

      int i;
      for(i = hint; i < this.size(); ++i) {
         if (this.pvs[i] == null) {
            if (this.verbose) {
               System.out.println(this.toString() + " gotEmpty#1 " + i);
            }

            return i;
         }
      }

      for(i = 0; i < hint; ++i) {
         if (this.pvs[i] == null) {
            if (this.verbose) {
               System.out.println(this.toString() + " gotEmpty#2 " + i);
            }

            return i;
         }
      }

      if (this.verbose) {
         System.out.println(this.toString() + "failed miserably ");
      }

      i = this.size() * 2;
      ParamValue var10000 = this.pvs[i];
      return -1;
   }

   private int getOne(String key) {
      int hint = this.hashhint(key);
      int start = false;
      int end = this.size();

      int start;
      for(start = 0; start < end - 1 && this.hints[start] != hint; ++start) {
      }

      int i;
      for(i = start; i < end; ++i) {
         if (this.pvs[i] != null && key.equalsIgnoreCase(this.pvs[i].name())) {
            if (this.verbose) {
               System.out.println(this.toString() + " gotOne#1 " + i);
            }

            return i;
         }
      }

      for(i = 0; i < start; ++i) {
         if (this.pvs[i] != null && key.equalsIgnoreCase(this.pvs[i].name())) {
            if (this.verbose) {
               System.out.println(this.toString() + " gotOne#1 " + i);
            }

            return i;
         }
      }

      return -1;
   }

   public String toString() {
      return "[" + super.toString() + " used: " + this.used() + " size: " + this.size() + "]";
   }

   public synchronized ParamValue get(int which) {
      return which >= this.size() ? null : this.pvs[which];
   }

   public synchronized ParamValue get(String key) {
      int which = this.getOne(key);
      return which != -1 ? this.pvs[which] : null;
   }

   public synchronized ParamValue put(ParamValue value) {
      int which = this.getOne(value.name());
      if (which != -1) {
         return this.replaceOne(which, value);
      } else {
         ++this.used;
         return this.replaceOne(this.getEmpty(), value);
      }
   }

   public synchronized void remove(String key) {
      int which = this.getOne(key);
      if (which != -1) {
         this.clearOne(which);
      }

   }

   public boolean isEmpty() {
      return this.used() == 0;
   }

   public synchronized void clear() {
      for(int i = 0; i < this.size(); ++i) {
         this.clearOne(i);
      }

   }
}
