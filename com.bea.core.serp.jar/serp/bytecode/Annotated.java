package serp.bytecode;

public abstract class Annotated extends Attributes {
   public Annotations getDeclaredAnnotations(boolean add) {
      Annotations ann = (Annotations)this.getAttribute("RuntimeInvisibleAnnotations");
      if (add && ann == null) {
         this.ensureBytecodeVersion();
         return (Annotations)this.addAttribute("RuntimeInvisibleAnnotations");
      } else {
         return ann;
      }
   }

   public boolean removeDeclaredAnnotations() {
      return this.removeAttribute("RuntimeInvisibleAnnotations");
   }

   public Annotations getDeclaredRuntimeAnnotations(boolean add) {
      Annotations ann = (Annotations)this.getAttribute("RuntimeVisibleAnnotations");
      if (add && ann == null) {
         this.ensureBytecodeVersion();
         return (Annotations)this.addAttribute("RuntimeVisibleAnnotations");
      } else {
         return ann;
      }
   }

   public boolean removeDeclaredRuntimeAnnotations() {
      return this.removeAttribute("RuntimeVisibleAnnotations");
   }

   private void ensureBytecodeVersion() {
      BCClass bc = this.getBCClass();
      if (bc.getMajorVersion() < 49) {
         bc.setMajorVersion(49);
         bc.setMinorVersion(0);
      }

   }

   abstract BCClass getBCClass();
}
