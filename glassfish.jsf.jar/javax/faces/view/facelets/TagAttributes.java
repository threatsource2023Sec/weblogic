package javax.faces.view.facelets;

public abstract class TagAttributes {
   public abstract TagAttribute[] getAll();

   public abstract TagAttribute get(String var1);

   public abstract TagAttribute get(String var1, String var2);

   public abstract TagAttribute[] getAll(String var1);

   public abstract String[] getNamespaces();

   public Tag getTag() {
      return null;
   }

   public void setTag(Tag tag) {
   }
}
