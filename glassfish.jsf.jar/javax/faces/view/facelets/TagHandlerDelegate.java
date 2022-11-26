package javax.faces.view.facelets;

import java.io.IOException;
import javax.faces.component.UIComponent;

public abstract class TagHandlerDelegate {
   public abstract MetaRuleset createMetaRuleset(Class var1);

   public abstract void apply(FaceletContext var1, UIComponent var2) throws IOException;
}
