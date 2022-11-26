package com.bea.xbean.common;

public interface XmlLocale {
   boolean sync();

   boolean noSync();

   void enter();

   void exit();
}
