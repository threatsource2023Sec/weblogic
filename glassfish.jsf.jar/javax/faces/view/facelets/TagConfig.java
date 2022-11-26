package javax.faces.view.facelets;

public interface TagConfig {
   Tag getTag();

   FaceletHandler getNextHandler();

   String getTagId();
}
