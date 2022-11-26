package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.lowlevel.ClassEntry;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.visitor.BCVisitor;
import serp.bytecode.visitor.VisitAcceptor;
import serp.util.Strings;

public class ExceptionHandler implements InstructionPtr, BCEntity, VisitAcceptor {
   private int _catchIndex = 0;
   private Code _owner = null;
   private InstructionPtrStrategy _tryStart = new InstructionPtrStrategy(this);
   private InstructionPtrStrategy _tryEnd = new InstructionPtrStrategy(this);
   private InstructionPtrStrategy _tryHandler = new InstructionPtrStrategy(this);

   ExceptionHandler(Code owner) {
      this._owner = owner;
   }

   public Code getCode() {
      return this._owner;
   }

   public Instruction getTryStart() {
      return this._tryStart.getTargetInstruction();
   }

   public void setTryStart(Instruction instruction) {
      this._tryStart.setTargetInstruction(instruction);
   }

   public Instruction getTryEnd() {
      return this._tryEnd.getTargetInstruction();
   }

   public void setTryEnd(Instruction instruction) {
      this._tryEnd.setTargetInstruction(instruction);
   }

   public Instruction getHandlerStart() {
      return this._tryHandler.getTargetInstruction();
   }

   public void setHandlerStart(Instruction instruction) {
      this._tryHandler.setTargetInstruction(instruction);
   }

   public int getCatchIndex() {
      return this._catchIndex;
   }

   public void setCatchIndex(int catchTypeIndex) {
      this._catchIndex = catchTypeIndex;
   }

   public String getCatchName() {
      if (this._catchIndex == 0) {
         return null;
      } else {
         ClassEntry entry = (ClassEntry)this.getPool().getEntry(this._catchIndex);
         return this.getProject().getNameCache().getExternalForm(entry.getNameEntry().getValue(), false);
      }
   }

   public Class getCatchType() {
      String name = this.getCatchName();
      return name == null ? null : Strings.toClass(name, this.getClassLoader());
   }

   public BCClass getCatchBC() {
      String name = this.getCatchName();
      return name == null ? null : this.getProject().loadClass(name, this.getClassLoader());
   }

   public void setCatch(String name) {
      if (name == null) {
         this._catchIndex = 0;
      } else {
         this._catchIndex = this.getPool().findClassEntry(this.getProject().getNameCache().getInternalForm(name, false), true);
      }

   }

   public void setCatch(Class type) {
      if (type == null) {
         this.setCatch((String)null);
      } else {
         this.setCatch(type.getName());
      }

   }

   public void setCatch(BCClass type) {
      if (type == null) {
         this.setCatch((String)null);
      } else {
         this.setCatch(type.getName());
      }

   }

   public void updateTargets() {
      this._tryStart.updateTargets();
      this._tryEnd.updateTargets();
      this._tryHandler.updateTargets();
   }

   public void replaceTarget(Instruction oldTarget, Instruction newTarget) {
      this._tryStart.replaceTarget(oldTarget, newTarget);
      this._tryEnd.replaceTarget(oldTarget, newTarget);
      this._tryHandler.replaceTarget(oldTarget, newTarget);
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

   public void acceptVisit(BCVisitor visit) {
      visit.enterExceptionHandler(this);
      visit.exitExceptionHandler(this);
   }

   void read(ExceptionHandler orig) {
      this._tryStart.setByteIndex(orig._tryStart.getByteIndex());
      this._tryEnd.setByteIndex(orig._tryEnd.getByteIndex());
      this._tryHandler.setByteIndex(orig._tryHandler.getByteIndex());
      this.setCatch(orig.getCatchName());
   }

   void read(DataInput in) throws IOException {
      this.setTryStart(in.readUnsignedShort());
      this.setTryEnd(in.readUnsignedShort());
      this.setHandlerStart(in.readUnsignedShort());
      this.setCatchIndex(in.readUnsignedShort());
   }

   void write(DataOutput out) throws IOException {
      out.writeShort(this.getTryStartPc());
      out.writeShort(this.getTryEndPc());
      out.writeShort(this.getHandlerStartPc());
      out.writeShort(this.getCatchIndex());
   }

   public void setTryStart(int start) {
      this._tryStart.setByteIndex(start);
   }

   public int getTryStartPc() {
      return this._tryStart.getByteIndex();
   }

   public void setTryEnd(int end) {
      this.setTryEnd((Instruction)this._owner.getInstruction(end).prev);
   }

   public int getTryEndPc() {
      return this._tryEnd.getByteIndex() + this.getTryEnd().getLength();
   }

   public void setHandlerStart(int handler) {
      this._tryHandler.setByteIndex(handler);
   }

   public int getHandlerStartPc() {
      return this._tryHandler.getByteIndex();
   }

   void invalidate() {
      this._owner = null;
   }
}
