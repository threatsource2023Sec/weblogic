package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class LocalTable extends Attribute implements InstructionPtr {
   private List _locals = new ArrayList();

   LocalTable(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   public Local[] getLocals() {
      return (Local[])((Local[])this._locals.toArray(this.newLocalArray(this._locals.size())));
   }

   public Local getLocal(int local) {
      for(int i = 0; i < this._locals.size(); ++i) {
         if (((Local)this._locals.get(i)).getLocal() == local) {
            return (Local)this._locals.get(i);
         }
      }

      return null;
   }

   public Local getLocal(String name) {
      for(int i = 0; i < this._locals.size(); ++i) {
         String loc = ((Local)this._locals.get(i)).getName();
         if (loc == null && name == null || loc != null && loc.equals(name)) {
            return (Local)this._locals.get(i);
         }
      }

      return null;
   }

   public Local[] getLocals(String name) {
      List matches = new LinkedList();

      for(int i = 0; i < this._locals.size(); ++i) {
         String loc = ((Local)this._locals.get(i)).getName();
         if (loc == null && name == null || loc != null && loc.equals(name)) {
            matches.add(this._locals.get(i));
         }
      }

      return (Local[])((Local[])matches.toArray(this.newLocalArray(matches.size())));
   }

   public void setLocals(Local[] locals) {
      this.clear();
      if (locals != null) {
         for(int i = 0; i < locals.length; ++i) {
            this.addLocal(locals[i]);
         }
      }

   }

   public Local addLocal(Local local) {
      Local newLocal = this.addLocal(local.getName(), local.getTypeName());
      newLocal.setStartPc(local.getStartPc());
      newLocal.setLength(local.getLength());
      return newLocal;
   }

   public Local addLocal() {
      Local local = this.newLocal();
      this._locals.add(local);
      return local;
   }

   protected abstract Local newLocal();

   protected abstract Local[] newLocalArray(int var1);

   public Local addLocal(String name, String type) {
      Local local = this.addLocal();
      local.setName(name);
      local.setType(type);
      return local;
   }

   public void clear() {
      for(int i = 0; i < this._locals.size(); ++i) {
         ((Local)this._locals.get(i)).invalidate();
      }

      this._locals.clear();
   }

   public boolean removeLocal(int local) {
      return this.removeLocal(this.getLocal(local));
   }

   public boolean removeLocal(String name) {
      return this.removeLocal(this.getLocal(name));
   }

   public boolean removeLocal(Local local) {
      if (local != null && this._locals.remove(local)) {
         local.invalidate();
         return true;
      } else {
         return false;
      }
   }

   public void updateTargets() {
      for(int i = 0; i < this._locals.size(); ++i) {
         ((Local)this._locals.get(i)).updateTargets();
      }

   }

   public void replaceTarget(Instruction oldTarget, Instruction newTarget) {
      for(int i = 0; i < this._locals.size(); ++i) {
         ((Local)this._locals.get(i)).replaceTarget(oldTarget, newTarget);
      }

   }

   public Code getCode() {
      return (Code)this.getOwner();
   }

   int getLength() {
      return 2 + 10 * this._locals.size();
   }

   void read(Attribute other) {
      this.setLocals(((LocalTable)other).getLocals());
   }

   void read(DataInput in, int length) throws IOException {
      this.clear();
      int numLocals = in.readUnsignedShort();

      for(int i = 0; i < numLocals; ++i) {
         Local Local = this.addLocal();
         Local.read(in);
      }

   }

   void write(DataOutput out, int length) throws IOException {
      out.writeShort(this._locals.size());

      for(int i = 0; i < this._locals.size(); ++i) {
         ((Local)this._locals.get(i)).write(out);
      }

   }
}
