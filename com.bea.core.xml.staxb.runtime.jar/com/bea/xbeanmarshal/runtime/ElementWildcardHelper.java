package com.bea.xbeanmarshal.runtime;

import com.bea.xbeanmarshal.buildtime.internal.util.XmlBeanUtil;
import com.bea.xbeanmarshal.runtime.internal.util.Verbose;
import java.lang.reflect.Method;
import java.util.Iterator;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPFactory;

public class ElementWildcardHelper {
   public static final String SOAPFACTORY_CLASSNAME = "SOAPFACTORY_CLASSNAME";
   private static SOAPFactory soapFactory;
   private static String soapFactoryClassname = System.getProperty("SOAPFACTORY_CLASSNAME") != null ? System.getProperty("SOAPFACTORY_CLASSNAME") : "weblogic.wsee.util.WLSOAPFactory";

   private ElementWildcardHelper() {
   }

   public static SOAPElement createSyntheticWrappedArray(SOAPElement firstArrayItem, Iterator elements, boolean verbose) {
      SOAPElement synArrayWrapper = null;

      try {
         getSOAPFactory();
         synArrayWrapper = soapFactory.createElement(soapFactory.createName("syntheticArrayWrapper", "http://com.bea.anyElement", "BEA"));
         if (firstArrayItem == null) {
            return synArrayWrapper;
         } else {
            synArrayWrapper.addChildElement(firstArrayItem);

            for(SOAPElement arrayItem = getNextElement(elements); arrayItem != null; arrayItem = getNextElement(elements)) {
               synArrayWrapper.addChildElement(arrayItem);
            }

            if (verbose) {
               Verbose.log(" constructed a synthetic array of <any/> as: " + XmlBeanUtil.toXMLString(synArrayWrapper) + "'");
            }

            return synArrayWrapper;
         }
      } catch (Throwable var5) {
         throw new IllegalArgumentException(" Error while trying to create synthetic array element wrapper for extensibility elements.  Exception '" + var5.getMessage() + "'");
      }
   }

   private static SOAPElement getNextElement(Iterator elements) {
      while(true) {
         if (elements.hasNext()) {
            Object obj = elements.next();
            if (!(obj instanceof SOAPElement)) {
               continue;
            }

            return (SOAPElement)obj;
         }

         return null;
      }
   }

   private static SOAPFactory getSOAPFactory() {
      if (soapFactory != null) {
         return soapFactory;
      } else {
         Class soapFactoryClass = null;

         try {
            soapFactoryClass = Class.forName("weblogic.wsee.util.WLSOAPFactory");
            Method createSOAPFactoryMethod = soapFactoryClass.getMethod("createSOAPFactory");
            soapFactory = (SOAPFactory)createSOAPFactoryMethod.invoke((Object)null);
            return soapFactory;
         } catch (Throwable var2) {
            throw new IllegalArgumentException(" ElementWildcardHelper:  could not create SOAPFactory using '" + soapFactoryClassname + "'");
         }
      }
   }
}
