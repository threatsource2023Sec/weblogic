package javax.faces.view.facelets;

public final class TagException extends FaceletException {
   private static final long serialVersionUID = 1L;

   public TagException(Tag tag) {
      super(tag.toString());
   }

   public TagException(Tag tag, String message) {
      super(tag + " " + message);
   }

   public TagException(Tag tag, Throwable cause) {
      super(tag.toString(), cause);
   }

   public TagException(Tag tag, String message, Throwable cause) {
      super(tag + " " + message, cause);
   }
}
