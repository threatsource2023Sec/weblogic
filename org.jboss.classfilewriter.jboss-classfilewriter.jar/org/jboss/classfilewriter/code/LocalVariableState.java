package org.jboss.classfilewriter.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.InvalidBytecodeException;
import org.jboss.classfilewriter.constpool.ConstPool;

public class LocalVariableState {
   private final List contents;
   private final ConstPool constPool;

   public LocalVariableState(ClassMethod method) {
      this.constPool = method.getClassFile().getConstPool();
      this.contents = new ArrayList();
      if (!method.isStatic()) {
         if (method.isConstructor()) {
            this.contents.add(new StackEntry(StackEntryType.UNINITIALIZED_THIS, method.getClassFile().getDescriptor()));
         } else {
            this.contents.add(StackEntry.of(method.getClassFile().getDescriptor(), method.getClassFile().getConstPool()));
         }
      }

      String[] var2 = method.getParameters();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String param = var2[var4];
         StackEntry entry = StackEntry.of(param, method.getClassFile().getConstPool());
         this.contents.add(entry);
         if (entry.isWide()) {
            this.contents.add(new StackEntry(StackEntryType.TOP, param));
         }
      }

   }

   public LocalVariableState(ConstPool pool, String... entries) {
      this.constPool = pool;
      this.contents = new ArrayList();
      String[] var3 = entries;
      int var4 = entries.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String c = var3[var5];
         StackEntry entry = StackEntry.of(c, this.constPool);
         this.contents.add(entry);
      }

   }

   private LocalVariableState(List contents, ConstPool constPool) {
      this.contents = contents;
      this.constPool = constPool;
   }

   public List getContents() {
      return Collections.unmodifiableList(this.contents);
   }

   public StackEntry get(int index) {
      return (StackEntry)this.contents.get(index);
   }

   public LocalVariableState storeWide(int index, StackEntry entry) {
      ArrayList newContents = new ArrayList(this.contents.size());

      for(int i = 0; i <= index || i < this.contents.size(); ++i) {
         if (index == i) {
            newContents.add(entry);
            newContents.add(new StackEntry(StackEntryType.TOP, entry.getDescriptor()));
            ++i;
         } else if (i >= this.contents.size()) {
            newContents.add(new StackEntry(StackEntryType.NULL, (String)null));
         } else {
            newContents.add(this.contents.get(i));
         }
      }

      return new LocalVariableState(newContents, this.constPool);
   }

   public LocalVariableState store(int index, StackEntry entry) {
      ArrayList newContents = new ArrayList(this.contents.size());

      for(int i = 0; i <= index || i < this.contents.size(); ++i) {
         if (index == i) {
            newContents.add(entry);
         } else if (i >= this.contents.size()) {
            newContents.add(new StackEntry(StackEntryType.NULL, (String)null));
         } else {
            newContents.add(this.contents.get(i));
         }
      }

      return new LocalVariableState(newContents, this.constPool);
   }

   public int size() {
      return this.contents.size();
   }

   public String toString() {
      return "Local Variables: " + this.contents.toString();
   }

   public LocalVariableState constructorCall(StackEntry entry) {
      List newContents = new ArrayList(this.contents.size());
      int i;
      StackEntry stackEntry;
      if (entry.getType() == StackEntryType.UNINITIALIZED_THIS) {
         for(i = 0; i < this.contents.size(); ++i) {
            stackEntry = (StackEntry)this.contents.get(i);
            if (stackEntry.getType() == StackEntryType.UNINITIALIZED_THIS) {
               newContents.add(StackEntry.of(stackEntry.getDescriptor(), this.constPool));
            } else {
               newContents.add(stackEntry);
            }
         }

         return new LocalVariableState(newContents, this.constPool);
      } else if (entry.getType() != StackEntryType.UNITITIALIZED_OBJECT) {
         throw new InvalidBytecodeException("entry is not an unitialized object. " + this.toString());
      } else {
         for(i = 0; i < this.contents.size(); ++i) {
            stackEntry = (StackEntry)this.contents.get(i);
            if (stackEntry.getType() == StackEntryType.UNITITIALIZED_OBJECT && stackEntry.getNewInstructionLocation() == entry.getNewInstructionLocation()) {
               newContents.add(StackEntry.of(stackEntry.getDescriptor(), this.constPool));
            } else {
               newContents.add(stackEntry);
            }
         }

         return new LocalVariableState(newContents, this.constPool);
      }
   }

   public LocalVariableState updateMerged(int pos, StackEntry frame) {
      List newContents = new ArrayList(this.contents);
      newContents.remove(pos);
      newContents.add(pos, frame);
      return new LocalVariableState(newContents, this.constPool);
   }
}
