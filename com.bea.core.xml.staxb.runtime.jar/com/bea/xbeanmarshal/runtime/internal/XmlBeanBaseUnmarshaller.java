package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.util.XmlBeanUtil;
import com.bea.xbeanmarshal.runtime.internal.util.Verbose;
import com.bea.xml.XmlException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.w3c.dom.Node;

public abstract class XmlBeanBaseUnmarshaller {
   private static final boolean VERBOSE = Verbose.isVerbose(XmlBeanBaseUnmarshaller.class);

   Object commonXmlBeanUnmarshal(Node sourceNode, Class javaClass) throws XmlException {
      if (VERBOSE) {
         Verbose.log(" unmarshalling source xml: " + XmlBeanUtil.toXMLString(sourceNode) + "\n");
      }

      try {
         Class[] c = javaClass.getDeclaredClasses();
         Class factory = null;

         for(int i = 0; i < c.length; ++i) {
            if (c[i].getName().endsWith("Factory")) {
               factory = c[i];
            }
         }

         if (factory == null) {
            throw new XmlException("Unable to find Factory inner class for class:" + javaClass);
         } else {
            Method m = factory.getDeclaredMethod("parse", Node.class);
            return m.invoke((Object)null, sourceNode);
         }
      } catch (NoSuchMethodException var6) {
         throw new XmlException("Unable to find parse method on XmlObject", var6);
      } catch (IllegalAccessException var7) {
         throw new XmlException("Unable to access parse method on XmlObject", var7);
      } catch (InvocationTargetException var8) {
         throw new XmlException("Unable to invoke parse method on XmlObject", var8);
      }
   }
}
