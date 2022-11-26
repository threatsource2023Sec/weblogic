package com.sun.faces.facelets.tag.composite;

import java.beans.FeatureDescriptor;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;

interface PropertyHandler {
   void apply(FaceletContext var1, String var2, FeatureDescriptor var3, TagAttribute var4);
}
