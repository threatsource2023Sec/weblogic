package com.sun.faces.config.processor;

import javax.servlet.ServletContext;
import org.w3c.dom.Document;

public interface ConfigProcessor {
   void setNext(ConfigProcessor var1);

   void process(ServletContext var1, Document[] var2) throws Exception;

   void invokeNext(ServletContext var1, Document[] var2) throws Exception;
}
