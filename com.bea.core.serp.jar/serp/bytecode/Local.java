package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.lowlevel.UTF8Entry;

public abstract class Local implements BCEntity, InstructionPtr {
   private LocalTable _owner = null;
   private InstructionPtrStrategy _target = new InstructionPtrStrategy(this);
   private Instruction _end = null;
   private int _length = 0;
   private int _nameIndex = 0;
   private int _descriptorIndex = 0;
   private int _index = 0;

   Local(LocalTable owner) {
      this._owner = owner;
   }

   public LocalTable getTable() {
      return this._owner;
   }

   void invalidate() {
      this._owner = null;
   }

   public int getLocal() {
      return this._index;
   }

   public void setLocal(int index) {
      this._index = index;
   }

   public int getParam() {
      return this.getCode().getParamsIndex(this.getLocal());
   }

   public void setParam(int param) {
      this.setLocal(this._owner.getCode().getLocalsIndex(param));
   }

   public int getStartPc() {
      return this._target.getByteIndex();
   }

   public Instruction getStart() {
      return this._target.getTargetInstruction();
   }

   public void setStartPc(int startPc) {
      this._target.setByteIndex(startPc);
   }

   public void setStart(Instruction instruction) {
      this._target.setTargetInstruction(instruction);
   }

   public Instruction getEnd() {
      if (this._end != null) {
         return this._end;
      } else {
         int idx = this._target.getByteIndex() + this._length;
         Instruction end = this.getCode().getInstruction(idx);
         return end != null && end.prev instanceof Instruction ? (Instruction)end.prev : this.getCode().getLastInstruction();
      }
   }

   public int getLength() {
      return this._end != null ? this._end.getByteIndex() + this._end.getLength() - this._target.getByteIndex() : this._length;
   }

   public void setEnd(Instruction end) {
      if (end.getCode() != this.getCode()) {
         throw new IllegalArgumentException("Instruction pointers and targets must be part of the same code block.");
      } else {
         this._end = end;
         this._length = -1;
      }
   }

   public void setLength(int length) {
      if (length < 0) {
         throw new IllegalArgumentException(String.valueOf(length));
      } else {
         this._length = length;
         this._end = null;
      }
   }

   public void updateTargets() {
      this._target.updateTargets();
      this._end = this.getEnd();
   }

   public void replaceTarget(Instruction oldTarget, Instruction newTarget) {
      this._target.replaceTarget(oldTarget, newTarget);
      if (this.getEnd() == oldTarget) {
         this.setEnd(newTarget);
      }

   }

   public int getNameIndex() {
      return this._nameIndex;
   }

   public void setNameIndex(int nameIndex) {
      this._nameIndex = nameIndex;
   }

   public String getName() {
      return this.getNameIndex() == 0 ? null : ((UTF8Entry)this.getPool().getEntry(this.getNameIndex())).getValue();
   }

   public void setName(String name) {
      if (name == null) {
         this.setNameIndex(0);
      } else {
         this.setNameIndex(this.getPool().findUTF8Entry(name, true));
      }

   }

   public int getTypeIndex() {
      return this._descriptorIndex;
   }

   public void setTypeIndex(int index) {
      this._descriptorIndex = index;
   }

   public String getTypeName() {
      if (this.getTypeIndex() == 0) {
         return null;
      } else {
         UTF8Entry entry = (UTF8Entry)this.getPool().getEntry(this.getTypeIndex());
         return this.getProject().getNameCache().getExternalForm(entry.getValue(), false);
      }
   }

   public void setType(String type) {
      if (type == null) {
         this.setTypeIndex(0);
      } else {
         type = this.getProject().getNameCache().getInternalForm(type, true);
         this.setTypeIndex(this.getPool().findUTF8Entry(type, true));
      }

   }

   public Project getProject() {
      return this._owner.getProject();
   }

   public ConstantPool getPool() {
      return this._owner.getPool();
   }

   public ClassLoader getClassLoader() {
      return this._owner.getClassLoader();
   }

   public boolean isValid() {
      return this._owner != null;
   }

   void read(DataInput in) throws IOException {
      this.setStartPc(in.readUnsignedShort());
      this.setLength(in.readUnsignedShort());
      this.setNameIndex(in.readUnsignedShort());
      this.setTypeIndex(in.readUnsignedShort());
      this.setLocal(in.readUnsignedShort());
   }

   void write(DataOutput out) throws IOException {
      out.writeShort(this.getStartPc());
      out.writeShort(this.getLength());
      out.writeShort(this.getNameIndex());
      out.writeShort(this.getTypeIndex());
      out.writeShort(this.getLocal());
   }

   public Code getCode() {
      return this._owner.getCode();
   }
}
