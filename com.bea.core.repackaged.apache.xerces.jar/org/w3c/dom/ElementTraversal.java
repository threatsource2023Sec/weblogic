package org.w3c.dom;

public interface ElementTraversal {
   Element getFirstElementChild();

   Element getLastElementChild();

   Element getPreviousElementSibling();

   Element getNextElementSibling();

   int getChildElementCount();
}
