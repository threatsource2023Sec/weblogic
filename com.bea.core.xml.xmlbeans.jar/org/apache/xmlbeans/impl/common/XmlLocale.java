package org.apache.xmlbeans.impl.common;

public interface XmlLocale {
   boolean sync();

   boolean noSync();

   void enter();

   void exit();
}
