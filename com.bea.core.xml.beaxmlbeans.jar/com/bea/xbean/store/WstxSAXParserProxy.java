package com.bea.xbean.store;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.Locator2;

public class WstxSAXParserProxy implements XMLReader, OurWstxConfig, Locator2 {
   private Object wrapped;
   private Class wrappedParserClass;
   private Object wrappedConfig;
   private Class wrappedConfigClass;

   public static Object newInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      return new WstxSAXParserProxy();
   }

   public WstxSAXParserProxy() {
      try {
         this.wrappedParserClass = Class.forName("com.ctc.wstx.sax.WstxSAXParser");
         this.wrapped = (XMLReader)this.wrappedParserClass.newInstance();
         this.wrappedConfigClass = Class.forName("com.ctc.wstx.api.ReaderConfig");
         Method meth = this.wrappedParserClass.getDeclaredMethod("getStaxConfig");
         this.wrappedConfig = meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public void setMaxEntityCount(Integer woodstoxEntityCount) {
      try {
         Method meth = this.wrappedConfigClass.getDeclaredMethod("setMaxEntityCount", Long.TYPE);
         meth.invoke(this.wrappedConfig, woodstoxEntityCount.longValue());
      } catch (InvocationTargetException var3) {
         throw new RuntimeException(var3.getCause());
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public void configureForConvenience() {
      try {
         Method meth = this.wrappedConfigClass.getDeclaredMethod("configureForConvenience");
         meth.invoke(this.wrappedConfig);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public void configureForLowMemUsage() {
      try {
         Method meth = this.wrappedConfigClass.getDeclaredMethod("configureForLowMemUsage");
         meth.invoke(this.wrappedConfig);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public void configureForRoundTripping() {
      try {
         Method meth = this.wrappedConfigClass.getDeclaredMethod("configureForRoundTripping");
         meth.invoke(this.wrappedConfig);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public void configureForSpeed() {
      try {
         Method meth = this.wrappedConfigClass.getDeclaredMethod("configureForSpeed");
         meth.invoke(this.wrappedConfig);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public void configureForXmlConformance() {
      try {
         Method meth = this.wrappedConfigClass.getDeclaredMethod("configureForXmlConformance");
         meth.invoke(this.wrappedConfig);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getFeature", String.class);
         return (Boolean)meth.invoke(this.wrapped, name);
      } catch (InvocationTargetException var4) {
         Throwable cause = var4.getCause();
         if (cause instanceof SAXNotRecognizedException) {
            throw (SAXNotRecognizedException)cause;
         } else if (cause instanceof SAXNotSupportedException) {
            throw (SAXNotSupportedException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("setFeature", String.class, Boolean.TYPE);
         meth.invoke(this.wrapped, name, value);
      } catch (InvocationTargetException var5) {
         Throwable cause = var5.getCause();
         if (cause instanceof SAXNotRecognizedException) {
            throw (SAXNotRecognizedException)cause;
         } else if (cause instanceof SAXNotSupportedException) {
            throw (SAXNotSupportedException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getProperty", String.class);
         return meth.invoke(this.wrapped, name);
      } catch (InvocationTargetException var4) {
         Throwable cause = var4.getCause();
         if (cause instanceof SAXNotRecognizedException) {
            throw (SAXNotRecognizedException)cause;
         } else if (cause instanceof SAXNotSupportedException) {
            throw (SAXNotSupportedException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("setProperty", String.class, Object.class);
         meth.invoke(this.wrapped, name, value);
      } catch (InvocationTargetException var5) {
         Throwable cause = var5.getCause();
         if (cause instanceof SAXNotRecognizedException) {
            throw (SAXNotRecognizedException)cause;
         } else if (cause instanceof SAXNotSupportedException) {
            throw (SAXNotSupportedException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void setEntityResolver(EntityResolver resolver) {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("setEntityResolver", EntityResolver.class);
         meth.invoke(this.wrapped, resolver);
      } catch (InvocationTargetException var3) {
         throw new RuntimeException(var3.getCause());
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public EntityResolver getEntityResolver() {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getEntityResolver");
         return (EntityResolver)meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public void setDTDHandler(DTDHandler handler) {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("setDTDHandler", DTDHandler.class);
         meth.invoke(this.wrapped, handler);
      } catch (InvocationTargetException var3) {
         throw new RuntimeException(var3.getCause());
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public DTDHandler getDTDHandler() {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getDTDHandler");
         return (DTDHandler)meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public void setContentHandler(ContentHandler handler) {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("setContentHandler", ContentHandler.class);
         meth.invoke(this.wrapped, handler);
      } catch (InvocationTargetException var3) {
         throw new RuntimeException(var3.getCause());
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public ContentHandler getContentHandler() {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getContentHandler");
         return (ContentHandler)meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public void setErrorHandler(ErrorHandler handler) {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("setErrorHandler", ErrorHandler.class);
         meth.invoke(this.wrapped, handler);
      } catch (InvocationTargetException var3) {
         throw new RuntimeException(var3.getCause());
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public ErrorHandler getErrorHandler() {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getErrorHandler");
         return (ErrorHandler)meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public void parse(InputSource input) throws IOException, SAXException {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("parse", InputSource.class);
         meth.invoke(this.wrapped, input);
      } catch (InvocationTargetException var4) {
         Throwable cause = var4.getCause();
         if (cause instanceof IOException) {
            throw (IOException)cause;
         } else if (cause instanceof SAXException) {
            throw (SAXException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   public void parse(String systemId) throws IOException, SAXException {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("parse", String.class);
         meth.invoke(this.wrapped, systemId);
      } catch (InvocationTargetException var4) {
         Throwable cause = var4.getCause();
         if (cause instanceof IOException) {
            throw (IOException)cause;
         } else if (cause instanceof SAXException) {
            throw (SAXException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   public String getPublicId() {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getPublicId");
         return (String)meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public String getSystemId() {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getSystemId");
         return (String)meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public int getLineNumber() {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getLineNumber");
         return (Integer)meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public int getColumnNumber() {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getColumnNumber");
         return (Integer)meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public String getXMLVersion() {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getXMLVersion");
         return (String)meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public String getEncoding() {
      try {
         Method meth = this.wrappedParserClass.getDeclaredMethod("getEncoding");
         return (String)meth.invoke(this.wrapped);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2.getCause());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }
}
