package com.sun.faces.facelets.tag;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

interface TagHandlerFactory {
   TagHandler createHandler(TagConfig var1) throws FacesException, ELException;
}
