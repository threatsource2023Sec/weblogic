package javax.faces.view.facelets;

import java.io.IOException;
import javax.faces.component.UIComponent;

public interface FaceletHandler {
   void apply(FaceletContext var1, UIComponent var2) throws IOException;
}
