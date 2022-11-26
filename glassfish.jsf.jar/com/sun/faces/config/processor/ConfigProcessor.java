package com.sun.faces.config.processor;

import com.sun.faces.config.manager.documents.DocumentInfo;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public interface ConfigProcessor {
   void initializeClassMetadataMap(ServletContext var1, FacesContext var2);

   void process(ServletContext var1, FacesContext var2, DocumentInfo[] var3) throws Exception;

   void destroy(ServletContext var1, FacesContext var2);
}
