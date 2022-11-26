package weblogic.xml.saaj.mime4j.message;

public abstract class AbstractBody implements Body {
   private Entity parent = null;

   public Entity getParent() {
      return this.parent;
   }

   public void setParent(Entity parent) {
      this.parent = parent;
   }
}
