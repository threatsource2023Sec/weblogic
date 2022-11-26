package org.jboss.classfilewriter.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jboss.classfilewriter.InvalidBytecodeException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.DescriptorUtils;

public class StackState {
   private final List contents;
   private final ConstPool constPool;

   public StackState(ConstPool constPool) {
      this.contents = new ArrayList(0);
      this.constPool = constPool;
   }

   public StackState(String exceptionType, ConstPool constPool) {
      this.contents = new ArrayList(1);
      this.contents.add(new StackEntry(StackEntryType.OBJECT, DescriptorUtils.makeDescriptor(exceptionType), constPool));
      this.constPool = constPool;
   }

   private StackState(List contents, ConstPool constPool) {
      this.contents = contents;
      this.constPool = constPool;
   }

   public boolean isOnTop(String descriptor) {
      if (this.contents.isEmpty()) {
         return false;
      } else {
         StackEntry entry = StackEntry.of(descriptor, this.constPool);
         StackEntry top = this.top();
         if (entry.isWide()) {
            if (this.contents.size() == 1) {
               return false;
            } else {
               return this.top_1().getType() == entry.getType();
            }
         } else if (top.getType() == StackEntryType.NULL && entry.getType() == StackEntryType.OBJECT) {
            return true;
         } else {
            return top.getType() == entry.getType();
         }
      }
   }

   public int size() {
      return this.contents.size();
   }

   public StackState push(String type) {
      StackEntry entry = StackEntry.of(type, this.constPool);
      return entry.getType() != StackEntryType.DOUBLE && entry.getType() != StackEntryType.LONG ? this.newStack(entry) : this.newStack(entry, new StackEntry(StackEntryType.TOP, type));
   }

   public StackState push(StackEntry entry) {
      return entry.getType() != StackEntryType.DOUBLE && entry.getType() != StackEntryType.LONG ? this.newStack(entry) : this.newStack(entry, new StackEntry(StackEntryType.TOP, entry.getDescriptor()));
   }

   public StackState aconstNull() {
      StackEntry entry = new StackEntry(StackEntryType.NULL, (String)null);
      return this.newStack(entry);
   }

   public StackState pop(int no) {
      if (no == 0) {
         return this;
      } else if (this.contents.size() < no) {
         throw new InvalidBytecodeException("cannot pop" + no + ", only " + this.contents.size() + " on stack " + this.toString());
      } else {
         StackEntry type = (StackEntry)this.contents.get(this.contents.size() - no);
         if (type.getType() == StackEntryType.TOP) {
            throw new InvalidBytecodeException("Pop" + no + " would split wide type " + this.toString());
         } else {
            return new StackState(this.contents.subList(0, this.contents.size() - no), this.constPool);
         }
      }
   }

   public StackState dup() {
      if (this.contents.isEmpty()) {
         throw new InvalidBytecodeException("cannot dup empty stack");
      } else {
         StackEntry type = this.top();
         if (type.getType() == StackEntryType.TOP) {
            throw new InvalidBytecodeException("Cannot dup wide type");
         } else {
            return this.newStack(type);
         }
      }
   }

   public StackState dupX1() {
      if (this.contents.size() < 2) {
         throw new InvalidBytecodeException("cannot dup_x1, stack does not have enough items");
      } else {
         StackEntry type = this.top();
         if (type.getType() == StackEntryType.TOP) {
            throw new InvalidBytecodeException("Cannot dup_x1 wide type");
         } else {
            ArrayList ret = new ArrayList(1 + this.contents.size());

            for(int i = 0; i < this.contents.size(); ++i) {
               if (i == this.contents.size() - 2) {
                  ret.add(this.top());
               }

               ret.add(this.contents.get(i));
            }

            return new StackState(ret, this.constPool);
         }
      }
   }

   public StackState dupX2() {
      if (this.contents.size() < 3) {
         throw new InvalidBytecodeException("cannot dup_x1, stack does not have enough items");
      } else {
         StackEntry type = this.top();
         if (type.getType() == StackEntryType.TOP) {
            throw new InvalidBytecodeException("Cannot dup_x1 wide type");
         } else {
            ArrayList ret = new ArrayList(1 + this.contents.size());

            for(int i = 0; i < this.contents.size(); ++i) {
               if (i == this.contents.size() - 3) {
                  ret.add(this.top());
               }

               ret.add(this.contents.get(i));
            }

            return new StackState(ret, this.constPool);
         }
      }
   }

   public StackState dup2() {
      if (this.contents.size() < 2) {
         throw new InvalidBytecodeException("cannot dup2, stack size is " + this.contents.size() + " " + this.toString());
      } else {
         StackEntry t1 = this.top();
         StackEntry t2 = this.top_1();
         if (t2.getType() == StackEntryType.TOP) {
            throw new InvalidBytecodeException("Cannot dup2 when second type on stack is wide: " + this.toString());
         } else {
            return this.newStack(t2, t1);
         }
      }
   }

   public StackState dup2X1() {
      if (this.contents.size() < 3) {
         throw new InvalidBytecodeException("cannot dup2X1, stack size is " + this.contents.size() + " " + this.toString());
      } else {
         StackEntry t1 = this.top();
         StackEntry t2 = this.top_1();
         StackEntry t3 = this.top_2();
         if (t2.getType() == StackEntryType.TOP) {
            throw new InvalidBytecodeException("Cannot dup2X1 when second type on stack is wide: " + this.toString());
         } else if (t3.getType() == StackEntryType.TOP) {
            throw new InvalidBytecodeException("Cannot dup2X2 when third type on stack is wide: " + this.toString());
         } else {
            ArrayList ret = new ArrayList(2 + this.contents.size());

            for(int i = 0; i < this.contents.size(); ++i) {
               if (i == this.contents.size() - 3) {
                  ret.add(t2);
                  ret.add(t1);
               }

               ret.add(this.contents.get(i));
            }

            return new StackState(ret, this.constPool);
         }
      }
   }

   public StackState dup2X2() {
      if (this.contents.size() < 4) {
         throw new InvalidBytecodeException("cannot dup2X2, stack size is " + this.contents.size() + " " + this.toString());
      } else {
         StackEntry t1 = this.top();
         StackEntry t2 = this.top_1();
         StackEntry t4 = this.top_3();
         if (t2.getType() == StackEntryType.TOP) {
            throw new InvalidBytecodeException("Cannot dup2X2 when second type on stack is wide: " + this.toString());
         } else if (t4.getType() == StackEntryType.TOP) {
            throw new InvalidBytecodeException("Cannot dup2X2 when fourth type on stack is wide: " + this.toString());
         } else {
            ArrayList ret = new ArrayList(2 + this.contents.size());

            for(int i = 0; i < this.contents.size(); ++i) {
               if (i == this.contents.size() - 4) {
                  ret.add(t2);
                  ret.add(t1);
               }

               ret.add(this.contents.get(i));
            }

            return new StackState(ret, this.constPool);
         }
      }
   }

   private StackState newStack(StackEntry... pushValues) {
      ArrayList ret = new ArrayList(pushValues.length + this.contents.size());
      ret.addAll(this.contents);
      StackEntry[] var3 = pushValues;
      int var4 = pushValues.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         StackEntry s = var3[var5];
         ret.add(s);
      }

      return new StackState(ret, this.constPool);
   }

   public StackEntry top() {
      return (StackEntry)this.contents.get(this.contents.size() - 1);
   }

   public StackEntry top_1() {
      return (StackEntry)this.contents.get(this.contents.size() - 2);
   }

   public StackEntry top_2() {
      return (StackEntry)this.contents.get(this.contents.size() - 3);
   }

   public StackEntry top_3() {
      return (StackEntry)this.contents.get(this.contents.size() - 4);
   }

   public String toString() {
      return "Stack: " + this.contents.toString();
   }

   public List getContents() {
      return Collections.unmodifiableList(this.contents);
   }

   public StackState constructorCall(int initializedValueStackPosition, StackEntry entry) {
      List newContents = new ArrayList(this.contents.size());
      int i;
      StackEntry stackEntry;
      if (entry.getType() == StackEntryType.UNINITIALIZED_THIS) {
         for(i = 0; i < this.contents.size() - 1 - initializedValueStackPosition; ++i) {
            stackEntry = (StackEntry)this.contents.get(i);
            if (stackEntry.getType() == StackEntryType.UNINITIALIZED_THIS) {
               newContents.add(StackEntry.of(stackEntry.getDescriptor(), this.constPool));
            } else {
               newContents.add(stackEntry);
            }
         }

         return new StackState(newContents, this.constPool);
      } else if (entry.getType() != StackEntryType.UNITITIALIZED_OBJECT) {
         throw new InvalidBytecodeException("Object at position " + initializedValueStackPosition + " is not an unitialized object. " + this.toString());
      } else {
         for(i = 0; i < this.contents.size() - 1 - initializedValueStackPosition; ++i) {
            stackEntry = (StackEntry)this.contents.get(i);
            if (stackEntry.getType() == StackEntryType.UNITITIALIZED_OBJECT && stackEntry.getNewInstructionLocation() == entry.getNewInstructionLocation()) {
               newContents.add(StackEntry.of(stackEntry.getDescriptor(), this.constPool));
            } else {
               newContents.add(stackEntry);
            }
         }

         return new StackState(newContents, this.constPool);
      }
   }

   public StackState updateMerged(int pos, StackEntry frame) {
      List newContents = new ArrayList(this.contents);
      newContents.remove(pos);
      newContents.add(pos, frame);
      return new StackState(newContents, this.constPool);
   }

   public StackState swap() {
      int size = this.contents.size();
      List newContents = new ArrayList(this.contents.subList(0, size - 2));
      newContents.add(this.contents.get(size - 1));
      newContents.add(this.contents.get(size - 2));
      return new StackState(newContents, this.constPool);
   }
}
