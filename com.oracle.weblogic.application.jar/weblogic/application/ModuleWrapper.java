package weblogic.application;

public abstract class ModuleWrapper implements Module {
   public abstract Module getDelegate();

   public Module unwrap() {
      Module m;
      for(m = this.getDelegate(); m instanceof ModuleWrapper; m = ((ModuleWrapper)m).getDelegate()) {
      }

      return m;
   }
}
