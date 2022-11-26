package javax.faces.view.facelets;

public final class TagAttributeException extends FaceletException {
   private static final long serialVersionUID = 1L;

   public TagAttributeException(TagAttribute attr) {
      super(attr.toString());
   }

   public TagAttributeException(TagAttribute attr, String message) {
      super(attr + " " + message);
   }

   public TagAttributeException(TagAttribute attr, Throwable cause) {
      super(attr + " " + cause.getMessage(), cause);
   }

   public TagAttributeException(TagAttribute attr, String message, Throwable cause) {
      super(attr + " " + message, cause);
   }

   public TagAttributeException(Tag tag, TagAttribute attr) {
      super(print(tag, attr));
   }

   private static String print(Tag tag, TagAttribute attr) {
      return tag.getLocation() + " <" + tag.getQName() + " " + attr.getQName() + "=\"" + attr.getValue() + "\">";
   }

   public TagAttributeException(Tag tag, TagAttribute attr, String message) {
      super(print(tag, attr) + " " + message);
   }

   public TagAttributeException(Tag tag, TagAttribute attr, Throwable cause) {
      super(print(tag, attr) + " " + cause.getMessage(), cause);
   }

   public TagAttributeException(Tag tag, TagAttribute attr, String message, Throwable cause) {
      super(print(tag, attr) + " " + message, cause);
   }
}
