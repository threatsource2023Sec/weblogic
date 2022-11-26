package org.apache.xml.security.stax.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.xml.security.configuration.TransformAlgorithmType;
import org.apache.xml.security.configuration.TransformAlgorithmsType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.utils.ClassLoaderUtils;

public class TransformerAlgorithmMapper {
   private static Map algorithmsClassMapInOut;
   private static Map algorithmsClassMapIn;
   private static Map algorithmsClassMapOut;

   private TransformerAlgorithmMapper() {
   }

   protected static synchronized void init(TransformAlgorithmsType transformAlgorithms, Class callingClass) throws Exception {
      List algorithms = transformAlgorithms.getTransformAlgorithm();
      algorithmsClassMapInOut = new HashMap();
      algorithmsClassMapIn = new HashMap();
      algorithmsClassMapOut = new HashMap();

      for(int i = 0; i < algorithms.size(); ++i) {
         TransformAlgorithmType algorithmType = (TransformAlgorithmType)algorithms.get(i);
         if (algorithmType.getINOUT() == null) {
            algorithmsClassMapInOut.put(algorithmType.getURI(), ClassLoaderUtils.loadClass(algorithmType.getJAVACLASS(), callingClass));
         } else if ("IN".equals(algorithmType.getINOUT().value())) {
            algorithmsClassMapIn.put(algorithmType.getURI(), ClassLoaderUtils.loadClass(algorithmType.getJAVACLASS(), callingClass));
         } else {
            if (!"OUT".equals(algorithmType.getINOUT().value())) {
               throw new IllegalArgumentException("INOUT parameter " + algorithmType.getINOUT().value() + " unsupported");
            }

            algorithmsClassMapOut.put(algorithmType.getURI(), ClassLoaderUtils.loadClass(algorithmType.getJAVACLASS(), callingClass));
         }
      }

   }

   public static Class getTransformerClass(String algoURI, XMLSecurityConstants.DIRECTION direction) throws XMLSecurityException {
      Class clazz = null;
      switch (direction) {
         case IN:
            clazz = (Class)algorithmsClassMapIn.get(algoURI);
            break;
         case OUT:
            clazz = (Class)algorithmsClassMapOut.get(algoURI);
      }

      if (clazz == null) {
         clazz = (Class)algorithmsClassMapInOut.get(algoURI);
      }

      if (clazz == null) {
         throw new XMLSecurityException("signature.Transform.UnknownTransform", new Object[]{algoURI});
      } else {
         return clazz;
      }
   }
}
