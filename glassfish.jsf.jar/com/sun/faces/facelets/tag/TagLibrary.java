package com.sun.faces.facelets.tag;

import java.lang.reflect.Method;
import javax.faces.FacesException;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public interface TagLibrary {
   boolean containsNamespace(String var1, Tag var2);

   boolean containsTagHandler(String var1, String var2);

   TagHandler createTagHandler(String var1, String var2, TagConfig var3) throws FacesException;

   boolean containsFunction(String var1, String var2);

   Method createFunction(String var1, String var2);
}
