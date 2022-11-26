package com.bea.staxb.buildtime.internal.facade;

import com.bea.util.jam.JElement;

public interface TypegenFacade {
   PropgenFacade createNextElement(JElement var1);

   PropgenFacade createAny(JElement var1);

   PropgenFacade createNextAttribute(JElement var1);

   PropgenFacade peekCurrentProp();

   void finish();

   void setDocumentation(String var1);
}
