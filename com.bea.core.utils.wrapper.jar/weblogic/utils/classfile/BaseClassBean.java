package weblogic.utils.classfile;

import java.lang.reflect.Modifier;

public abstract class BaseClassBean {
   protected int modifiers;

   public int getModifiers() {
      return this.modifiers;
   }

   public void setModifiers(int i) {
      this.modifiers = i;
   }

   public boolean isPublic() {
      return Modifier.isPublic(this.modifiers);
   }

   public boolean isProtected() {
      return this.hasBit(4);
   }

   public boolean isPrivate() {
      return this.hasBit(2);
   }

   public boolean isAbstract() {
      return this.hasBit(1024);
   }

   public boolean isFinal() {
      return this.hasBit(16);
   }

   public boolean hasBit(int mask) {
      return (this.modifiers & mask) != 0;
   }
}
