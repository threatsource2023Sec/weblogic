package weblogic.rmi.internal;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import weblogic.utils.collections.ArrayMap;
import weblogic.utils.reflect.MethodSignatureBuilder;

public final class DescriptorParser extends HandlerBase implements DescriptorConstants {
   private static final boolean DEBUG = false;
   private ArrayMap methodDescriptors = new ArrayMap();
   private ArrayMap rmiDescriptor;
   private ArrayMap clusterDescriptor;
   private ArrayMap lifeCycleDescriptor;
   private ArrayMap securityDescriptor;

   public void startDocument() throws SAXException {
   }

   public void endDocument() throws SAXException {
   }

   public void startElement(String name, AttributeList attrs) throws SAXException {
      if (name.equals("rmi")) {
         if (attrs != null) {
            this.processRMIElement(attrs);
         }
      } else if (name.equals("cluster")) {
         if (attrs != null) {
            this.processClusterElement(attrs);
         }
      } else if (name.equals("method")) {
         if (attrs != null) {
            this.processMethodElement(attrs);
         }
      } else if (name.equals("lifecycle")) {
         if (attrs != null) {
            this.processLifeCycleElement(attrs);
         }
      } else {
         if (!name.equals("security")) {
            throw new SAXException("Unrecognized element " + name);
         }

         if (attrs != null) {
            this.processSecurityElement(attrs);
         }
      }

   }

   public void endElement(String name) throws SAXException {
   }

   public void characters(char[] buf, int offset, int len) throws SAXException {
      String s = new String(buf, offset, len);
      if (!s.trim().equals("")) {
         System.out.println("## " + s);
      }

   }

   private void processRMIElement(AttributeList attrs) {
      this.rmiDescriptor = new ArrayMap();

      for(int i = 0; i < attrs.getLength(); ++i) {
         String name = attrs.getName(i);
         String value = attrs.getValue(i);
         this.rmiDescriptor.put(name, value);
      }

   }

   private void processClusterElement(AttributeList attrs) {
      this.clusterDescriptor = new ArrayMap();

      for(int i = 0; i < attrs.getLength(); ++i) {
         String name = attrs.getName(i);
         String value = attrs.getValue(i);
         this.clusterDescriptor.put(name, value);
      }

   }

   private void processMethodElement(AttributeList attrs) throws SAXException {
      String key = null;
      ArrayMap methodDescriptor = new ArrayMap();

      for(int i = 0; i < attrs.getLength(); ++i) {
         String name = attrs.getName(i);
         String value = attrs.getValue(i);
         if (name.equals("name")) {
            try {
               key = MethodSignatureBuilder.compute(value);
            } catch (IllegalArgumentException var8) {
               if (!MethodDescriptor.isGenericMethodSignatureModeEnabled()) {
                  throw new SAXException("Found invalid method signature '" + value + "'", var8);
               }

               key = GenericMethodDescriptor.computeGenericMethodSignature(value);
            }
         } else {
            methodDescriptor.put(name, value);
         }
      }

      this.methodDescriptors.put(key, methodDescriptor);
   }

   private void processLifeCycleElement(AttributeList attrs) {
      this.lifeCycleDescriptor = new ArrayMap();

      for(int i = 0; i < attrs.getLength(); ++i) {
         String name = attrs.getName(i);
         String value = attrs.getValue(i);
         this.lifeCycleDescriptor.put(name, value);
      }

   }

   private void processSecurityElement(AttributeList attrs) {
      this.securityDescriptor = new ArrayMap();

      for(int i = 0; i < attrs.getLength(); ++i) {
         String name = attrs.getName(i);
         String value = attrs.getValue(i);
         this.securityDescriptor.put(name, value);
      }

   }

   public boolean activationDefined() {
      return this.lifeCycleDescriptor != null && this.lifeCycleDescriptor.get("activation-identifier-classname") != null;
   }

   public ArrayMap getDescriptors() {
      ArrayMap h = new ArrayMap();
      h.put("rmidescriptor", this.rmiDescriptor);
      if (this.methodDescriptors != null) {
         h.put("methoddescriptor", this.methodDescriptors);
      }

      if (this.clusterDescriptor != null) {
         h.put("clusterdescriptor", this.clusterDescriptor);
      }

      if (this.lifeCycleDescriptor != null) {
         h.put("lifecycledescriptor", this.lifeCycleDescriptor);
      }

      if (this.securityDescriptor != null) {
         h.put("securitydescriptor", this.securityDescriptor);
      }

      return h;
   }
}
