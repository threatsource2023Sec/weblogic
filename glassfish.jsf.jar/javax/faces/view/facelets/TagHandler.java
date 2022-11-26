package javax.faces.view.facelets;

public abstract class TagHandler implements FaceletHandler {
   protected final String tagId;
   protected final Tag tag;
   protected final FaceletHandler nextHandler;

   public TagHandler(TagConfig config) {
      this.tagId = config.getTagId();
      this.tag = config.getTag();
      this.nextHandler = config.getNextHandler();
   }

   protected final TagAttribute getAttribute(String localName) {
      return this.tag.getAttributes().get(localName);
   }

   protected final TagAttribute getRequiredAttribute(String localName) throws TagException {
      TagAttribute attr = this.getAttribute(localName);
      if (attr == null) {
         throw new TagException(this.tag, "Attribute '" + localName + "' is required");
      } else {
         return attr;
      }
   }

   public String toString() {
      return this.tag.toString();
   }
}
