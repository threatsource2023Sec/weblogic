package org.glassfish.hk2.xml.api;

public interface XmlRootCopy {
   XmlRootHandle getParent();

   Object getChildRoot();

   boolean isMergeable();

   void merge();
}
