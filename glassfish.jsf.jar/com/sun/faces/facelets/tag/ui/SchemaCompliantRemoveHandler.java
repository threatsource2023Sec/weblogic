package com.sun.faces.facelets.tag.ui;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public class SchemaCompliantRemoveHandler extends TagHandler {
   public SchemaCompliantRemoveHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      throw new FaceletException("Error: The Facelet parser is responsible for handling the <ui:remove> element.  This TagHandler implementation is only provided to allow the ui.taglib.xml file to be compliant with web-facelettaglibrary_2_2.xsd.  If you are seeing this exception, there is something wrong with how the JSF runtime is configuring its Facelets compiler.");
   }
}
