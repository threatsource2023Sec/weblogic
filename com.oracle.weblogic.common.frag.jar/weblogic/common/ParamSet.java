package weblogic.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintStream;
import java.util.Date;
import weblogic.common.internal.VectorTable;

public final class ParamSet implements Cloneable, Externalizable {
   private VectorTable ht;
   private boolean readonly;
   private boolean verbose = false;
   public boolean trap = false;
   private boolean loose = true;

   public void initialize() {
   }

   public void destroy() {
      this.ht = null;
   }

   public int size() {
      return this.ht.size();
   }

   public int used() {
      return this.ht.used();
   }

   public boolean isEmpty() {
      return this.ht.isEmpty();
   }

   public final void private_setReadonly(boolean f) {
      this.readonly = f;
   }

   public final boolean private_isReadonly() {
      return this.readonly;
   }

   public final void private_setLoose(boolean f) {
      this.loose = f;
   }

   public final boolean private_isLoose() {
      return this.loose;
   }

   public ParamValue getValue(String key) {
      return this.ht.get(key);
   }

   public ParamValue get(int i) {
      return this.ht.get(i);
   }

   public ParamSet(int i) {
      if (i < 1) {
         i = 1;
      }

      this.ht = new VectorTable(i);
   }

   public ParamSet() {
      this.ht = new VectorTable();
   }

   private ParamSet(ParamSet that) {
      this.ht = (VectorTable)that.ht.clone();
      this.readonly = that.readonly;
      this.verbose = that.verbose;
      this.trap = that.trap;
   }

   public ParamValue declareParam(String name, int datatype, String desc) throws ParamSetException {
      ParamValue pv = new ParamValue(name, datatype, 42, desc);
      return this.ht.put(pv);
   }

   public ParamValue declareParam(String name, int datatype) throws ParamSetException {
      return this.declareParam(name, datatype, "");
   }

   public ParamValue getParam(String key) throws ParamSetException {
      ParamValue pv = this.getValue(key);
      if (pv == null) {
         if (!this.loose) {
            throw new ParamSetException("No such parameter: " + key);
         }

         pv = this.declareParam(key, 43, "");
      }

      return pv;
   }

   public ParamValue getParam(String key, int index) throws ParamSetException {
      return this.getParam(key).elementAt(index);
   }

   public void setParams(ParamSet that) throws ParamSetException {
      ParamValue pv = null;

      for(int i = 0; i < that.size(); ++i) {
         pv = that.get(i);
         if (pv != null) {
            this.setParam(pv.name(), pv);
         }
      }

   }

   public void setParam(String keyNames, String keyValues, ParamSet that) throws ParamSetException {
      ParamValue pv = null;
      int index = 0;

      for(int i = 0; i < that.size(); ++i) {
         pv = that.get(i);
         if (pv != null) {
            this.setParam(keyNames, index, pv.name());
            this.setParam(keyValues, index, pv);
            ++index;
         }
      }

   }

   public ParamValue setParam(String key, ParamValue v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, double v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, float v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, long v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, int v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, short v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, byte v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, boolean v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, char v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, String v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, Date v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, Object v) throws ParamSetException {
      return this.getParam(key).set(v);
   }

   public ParamValue setParam(String key, int i, ParamValue v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   public ParamValue setParam(String key, int i, double v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   public ParamValue setParam(String key, int i, float v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   public ParamValue setParam(String key, int i, long v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   public ParamValue setParam(String key, int i, int v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   public ParamValue setParam(String key, int i, short v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   public ParamValue setParam(String key, byte i, int v) throws ParamSetException {
      return this.getParam(key).set((int)v, i);
   }

   public ParamValue setParam(String key, int i, boolean v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   public ParamValue setParam(String key, int i, char v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   public ParamValue setParam(String key, int i, String v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   public ParamValue setParam(String key, int i, Date v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   public ParamValue setParam(String key, int i, Object v) throws ParamSetException {
      return this.getParam(key).set(v, i);
   }

   private void remove(String key) throws ParamSetException {
      if (this.ht.get(key) == null) {
         throw new ParamSetException("Cannot remove non-existent item: " + key + " .");
      } else {
         this.ht.remove(key);
      }
   }

   private void clear() throws ParamSetException {
      if (this.private_isReadonly()) {
         throw new ParamSetException("ParamSet is read only.");
      } else {
         this.ht.clear();
      }
   }

   public Object clone() {
      return new ParamSet(this);
   }

   public String toString() {
      return this.display();
   }

   public String dump() {
      String retStr = "";

      for(int i = 0; i < this.size(); ++i) {
         ParamValue pv = this.get(i);
         if (pv != null) {
            retStr = retStr + "\n" + pv.dump();
         }
      }

      return retStr;
   }

   public String display() {
      String retStr = "";

      for(int i = 0; i < this.size(); ++i) {
         ParamValue pv;
         if ((pv = this.get(i)) != null) {
            retStr = retStr + pv.name() + " = " + pv + "\n";
         }
      }

      return retStr;
   }

   public void print(PrintStream out) {
      out.println(this.display());
   }

   public String[] getNames() throws ParamSetException {
      String[] vals = new String[this.used()];
      int i = 0;

      for(int j = 0; i < this.size(); ++i) {
         ParamValue pv;
         if ((pv = this.get(i)) != null) {
            vals[j++] = pv.name();
         }
      }

      return vals;
   }

   public void readExternal(ObjectInput in) throws IOException {
      int numparams = in.readInt();
      if (this.verbose) {
         System.out.println("PS: readcount: " + numparams);
      }

      this.ht = new VectorTable(numparams + 1);

      for(int i = 0; i < numparams; ++i) {
         ParamValue pv = new ParamValue();
         pv.readExternal(in);

         try {
            this.setParam(pv.name(), pv);
         } catch (ParamSetException var6) {
            throw new IOException("" + var6);
         }
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int used = this.used();
      if (this.verbose) {
         System.out.println("PS: write count/size: " + used + "/" + this.size());
      }

      out.writeInt(used);

      try {
         int i = 0;

         for(int count = 0; i < this.size() && count < used; ++i) {
            ParamValue pv = this.get(i);
            if (this.verbose) {
               System.out.print("PS #" + i + ": writing:");
            }

            if (pv != null) {
               if (this.verbose) {
                  System.out.println(pv.toString());
               }

               pv.writeExternal(out);
               ++count;
            } else if (this.verbose) {
               System.out.println("null");
            }
         }
      } finally {
         if (this.verbose) {
            System.out.println("This.size() now " + this.size());
         }

      }

   }
}
