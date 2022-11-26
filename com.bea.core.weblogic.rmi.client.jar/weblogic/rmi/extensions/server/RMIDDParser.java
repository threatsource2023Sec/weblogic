package weblogic.rmi.extensions.server;

import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.InputSource;
import weblogic.rmi.internal.DescriptorConstants;
import weblogic.rmi.internal.GenericMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.utils.collections.ArrayMap;
import weblogic.utils.reflect.MethodSignatureBuilder;

final class RMIDDParser implements DescriptorConstants {
   private static final boolean debug = false;
   private static final String RMI = "rmi";
   private static final Set dgcPolicies = new HashSet();
   private static final Set securityOptions = new HashSet();
   private static final Set loadAlgorithms = new HashSet();
   private static final Set clusterDescriptorElements = new HashSet();
   private static final Set lifecycleDescriptorElements = new HashSet();
   private static final Set rmiDescriptorElements = new HashSet();
   private static final Set rmiDescriptorSkipElements = new HashSet();
   private static final Set methodDescriptorElements = new HashSet();
   private static final Set securityDescriptorElements = new HashSet();
   private static final String CLUSTER = "cluster";
   private static final String SECURITY = "security";
   private static final String LIFECYCLE = "lifecycle";
   private static final String METHOD = "method";
   private ArrayMap securityDescriptorMap;
   private ArrayMap methodDescriptorsMap;
   private ArrayMap clusterDescriptorMap;
   private ArrayMap lifecycleDescriptorMap;
   private ArrayMap rmiDescriptorMap;
   private int count;
   private boolean isDTD = false;
   private static final XMLInputFactory factory;
   private InputStream inputStream;
   private XMLStreamReader parser;
   private static boolean validatingParser;

   public RMIDDParser(InputStream stream) {
      this.inputStream = stream;
   }

   public ArrayMap getDescriptorAsMap() throws XMLStreamException {
      this.parser = factory.createXMLStreamReader(this.inputStream);

      try {
         this.parse();
      } finally {
         try {
            this.parser.close();
         } catch (XMLStreamException var7) {
         }

      }

      return this.getParsedDescriptorMap();
   }

   private ArrayMap getParsedDescriptorMap() {
      ArrayMap rtdDescriptorMap = new ArrayMap();
      if (this.rmiDescriptorMap != null && !this.rmiDescriptorMap.isEmpty()) {
         rtdDescriptorMap.put("rmidescriptor", this.rmiDescriptorMap);
      }

      if (this.methodDescriptorsMap != null && !this.methodDescriptorsMap.isEmpty()) {
         rtdDescriptorMap.put("methoddescriptor", this.methodDescriptorsMap);
      }

      if (this.clusterDescriptorMap != null && !this.clusterDescriptorMap.isEmpty()) {
         rtdDescriptorMap.put("clusterdescriptor", this.clusterDescriptorMap);
      }

      if (this.lifecycleDescriptorMap != null && !this.lifecycleDescriptorMap.isEmpty()) {
         rtdDescriptorMap.put("lifecycledescriptor", this.lifecycleDescriptorMap);
      }

      if (this.securityDescriptorMap != null && !this.securityDescriptorMap.isEmpty()) {
         rtdDescriptorMap.put("securitydescriptor", this.securityDescriptorMap);
      }

      return rtdDescriptorMap;
   }

   private void parse() throws XMLStreamException {
      int event = this.parser.getEventType();

      while(true) {
         switch (event) {
            case 1:
               this.parseElement();
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
               break;
            case 4:
               if (this.parser.isWhiteSpace()) {
               }
               break;
            case 11:
               this.isDTD = true;
         }

         if (!this.parser.hasNext()) {
            this.isDTD = false;
            return;
         }

         event = this.parser.next();
      }
   }

   private void parseElement() throws XMLStreamException {
      String name = this.parser.getName().getLocalPart();
      if (this.count == 0 && !"rmi".equals(name)) {
         throw new XMLStreamException("RMI Runtime Documents must be enclosed by a single <rmi></rmi> element");
      } else {
         if (name.equals("rmi")) {
            ++this.count;
            this.parseRMIAttributes();
         } else if (name.equals("cluster")) {
            this.parseCluster();
         } else if (name.equals("lifecycle")) {
            this.parseLifecycle();
         } else if (name.equals("method")) {
            this.parseMethod();
         } else {
            if (!name.equals("security")) {
               throw new XMLStreamException("An RMI Runtime document must match the following pattern: rmi (cluster?, lifecycle?, method*, eos?, security?), the element " + name + " was not expected in this context");
            }

            this.parseSecurity();
         }

      }
   }

   private void parseSecurity() throws XMLStreamException {
      this.securityDescriptorMap = new ArrayMap();
      int i = 0;

      for(int n = this.parser.getAttributeCount(); i < n; ++i) {
         if (!validatingParser || this.parser.isAttributeSpecified(i)) {
            String name = this.parser.getAttributeName(i).getLocalPart();
            String value = this.parser.getAttributeValue(i);
            if (!securityDescriptorElements.contains(name) || !securityOptions.contains(value)) {
               throw new XMLStreamException("The <security> element is allowed to have the following attributes: confidentiality, integrity, client-authentication, client-cert-authentication, identity-assertion");
            }

            this.securityDescriptorMap.put(name, value);
         }
      }

   }

   private void parseMethod() throws XMLStreamException {
      if (this.methodDescriptorsMap == null) {
         this.methodDescriptorsMap = new ArrayMap();
      }

      String key = null;
      ArrayMap h = new ArrayMap();
      int i = 0;

      for(int n = this.parser.getAttributeCount(); i < n; ++i) {
         if (!validatingParser || this.parser.isAttributeSpecified(i)) {
            String name = this.parser.getAttributeName(i).getLocalPart();
            String value = this.parser.getAttributeValue(i);
            if (name.equals("name")) {
               try {
                  key = MethodSignatureBuilder.compute(value);
               } catch (IllegalArgumentException var8) {
                  if (!MethodDescriptor.isGenericMethodSignatureModeEnabled()) {
                     throw new XMLStreamException("Found invalid method signature '" + value + "'", var8);
                  }

                  key = GenericMethodDescriptor.computeGenericMethodSignature(value);
               }
            } else {
               if (!methodDescriptorElements.contains(name)) {
                  throw new XMLStreamException("The <method> element is allowed to have the following attributes: name,future,dispatch-context,dispatch-policy,transactional,requires-transaction,oneway,oneway-transactional-request,oneway-transactional-response,timeout,idempotent,asynchronous,remote-exception-wrapper-classname");
               }

               h.put(name, value);
            }
         }
      }

      if (key != null) {
         this.methodDescriptorsMap.put(key, h);
      }

   }

   private void parseLifecycle() throws XMLStreamException {
      this.lifecycleDescriptorMap = new ArrayMap();
      int i = 0;

      for(int n = this.parser.getAttributeCount(); i < n; ++i) {
         if (!validatingParser || this.parser.isAttributeSpecified(i)) {
            String name = this.parser.getAttributeName(i).getLocalPart();
            String value = this.parser.getAttributeValue(i);
            if (!lifecycleDescriptorElements.contains(name)) {
               throw new XMLStreamException("The <lifecyle> element is allowed to have the following attributes: dgc-policy,activation-identifier-classname");
            }

            this.lifecycleDescriptorMap.put(name, value);
         }
      }

   }

   private void parseCluster() throws XMLStreamException {
      this.clusterDescriptorMap = new ArrayMap();
      int i = 0;

      for(int n = this.parser.getAttributeCount(); i < n; ++i) {
         if (!validatingParser || this.parser.isAttributeSpecified(i)) {
            String name = this.parser.getAttributeName(i).getLocalPart();
            String value = this.parser.getAttributeValue(i);
            if (!clusterDescriptorElements.contains(name)) {
               throw new XMLStreamException("The <cluster> element is allowed to have the following attributes: clusterable,load-algorithm,replica-handler-classname,call-router-classname,stick-to-first-server,propagate-environment");
            }

            this.clusterDescriptorMap.put(name, value);
         }
      }

   }

   private void parseRMIAttributes() throws XMLStreamException {
      this.rmiDescriptorMap = new ArrayMap();
      int i = 0;

      for(int n = this.parser.getAttributeCount(); i < n; ++i) {
         if (!validatingParser || this.parser.isAttributeSpecified(i)) {
            String name = this.parser.getAttributeName(i).getLocalPart();
            String value = this.parser.getAttributeValue(i);
            if (rmiDescriptorElements.contains(name)) {
               this.rmiDescriptorMap.put(name, value);
            } else if (!rmiDescriptorSkipElements.contains(name)) {
               throw new XMLStreamException("The <rmi> element is allowed to have the following attributes: name , use-server-side-stubs, enable-call-by-reference, remote-ref-classname, server-ref-classname, initial-reference " + name);
            }
         }
      }

   }

   static {
      loadAlgorithms.add("random");
      loadAlgorithms.add("round-robin");
      loadAlgorithms.add("weight-based");
      loadAlgorithms.add("server-affinity");
      loadAlgorithms.add("round-robin-affinity");
      loadAlgorithms.add("random-affinity");
      loadAlgorithms.add("weight-based-affinity");
      loadAlgorithms.add("default");
      dgcPolicies.add("leased");
      dgcPolicies.add("referenceCounted");
      dgcPolicies.add("managed");
      dgcPolicies.add("useItOrLoseIt");
      dgcPolicies.add("deactivateOnMethodBoundaries");
      securityOptions.add("none");
      securityOptions.add("supported");
      securityOptions.add("required");
      securityOptions.add("config");
      clusterDescriptorElements.add("clusterable");
      clusterDescriptorElements.add("replica-handler-classname");
      clusterDescriptorElements.add("call-router-classname");
      clusterDescriptorElements.add("propagate-environment");
      clusterDescriptorElements.add("load-algorithm");
      clusterDescriptorElements.add("stick-to-first-server");
      lifecycleDescriptorElements.add("dgc-policy");
      lifecycleDescriptorElements.add("activation-identifier-classname");
      rmiDescriptorElements.add("use-server-side-stubs");
      rmiDescriptorElements.add("remote-ref-classname");
      rmiDescriptorElements.add("server-ref-classname");
      rmiDescriptorElements.add("name");
      rmiDescriptorElements.add("initial-reference");
      rmiDescriptorElements.add("enable-call-by-reference");
      rmiDescriptorElements.add("network-access-point");
      rmiDescriptorSkipElements.add("schemaLocation");
      rmiDescriptorSkipElements.add("version");
      methodDescriptorElements.add("future");
      methodDescriptorElements.add("timeout");
      methodDescriptorElements.add("idempotent");
      methodDescriptorElements.add("oneway-transactional-request");
      methodDescriptorElements.add("oneway-transactional-response");
      methodDescriptorElements.add("dispatch-context");
      methodDescriptorElements.add("requires-transaction");
      methodDescriptorElements.add("transactional");
      methodDescriptorElements.add("dispatch-policy");
      methodDescriptorElements.add("oneway");
      methodDescriptorElements.add("asynchronous");
      methodDescriptorElements.add("remote-exception-wrapper-classname");
      securityDescriptorElements.add("confidentiality");
      securityDescriptorElements.add("client-cert-authentication");
      securityDescriptorElements.add("client-authentication");
      securityDescriptorElements.add("identity-assertion");
      securityDescriptorElements.add("integrity");
      validatingParser = false;
      factory = XMLInputFactory.newInstance();
      factory.setProperty("javax.xml.stream.supportDTD", Boolean.TRUE);
      factory.setProperty("javax.xml.stream.isCoalescing", Boolean.TRUE);

      try {
         Class.forName("com.ctc.wstx.stax.WstxInputFactory");
         validatingParser = true;
      } catch (Throwable var3) {
         try {
            Class.forName("org.codehaus.stax2.XMLInputFactory2");
            validatingParser = true;
         } catch (Throwable var2) {
         }
      }

      factory.setXMLResolver(new DefaultRMIEntityResolver());
   }

   private static class DefaultRMIEntityResolver implements XMLResolver {
      private DefaultRMIEntityResolver() {
      }

      public Object resolveEntity(String publicID, final String systemID, String baseURI, String namespace) throws XMLStreamException {
         try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  InputStream in;
                  if (systemID != null && systemID.equals("rmi.dtd")) {
                     in = this.getClass().getClassLoader().getResourceAsStream("weblogic/rmi/internal/rmi.dtd");
                     if (in != null) {
                        return (new InputSource(in)).getByteStream();
                     }
                  } else {
                     in = this.getClass().getClassLoader().getResourceAsStream("weblogic/rmi/internal/rmi.xsd");
                     if (in != null) {
                        return (new InputSource(in)).getByteStream();
                     }
                  }

                  return null;
               }
            });
         } catch (PrivilegedActionException var7) {
            Exception cause = var7.getException();
            if (cause instanceof XMLStreamException) {
               throw (XMLStreamException)cause;
            } else {
               throw new RuntimeException(cause);
            }
         }
      }

      // $FF: synthetic method
      DefaultRMIEntityResolver(Object x0) {
         this();
      }
   }
}
