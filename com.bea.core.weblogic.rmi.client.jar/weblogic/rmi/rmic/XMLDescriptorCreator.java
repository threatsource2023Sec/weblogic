package weblogic.rmi.rmic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.rmi.internal.DescriptorConstants;
import weblogic.utils.UnsyncStringBuffer;

public final class XMLDescriptorCreator implements DescriptorConstants {
   private static final boolean debug = false;
   private static final String INDENTSTRING = "    ";
   private static final String NEWLINE = System.getProperty("line.separator");
   private static final String CLUSTER_END;
   private static final String LIFECYCLE_END;
   private static final String RMI_END;
   private static final String METHOD_END;
   private static final String SECURITY_END;
   private UnsyncStringBuffer rmiElement = null;
   private UnsyncStringBuffer clusterElement = null;
   private UnsyncStringBuffer lifeCycleElement = null;
   private UnsyncStringBuffer defaultMethodElement = null;
   private UnsyncStringBuffer specificMethodElements = null;
   private UnsyncStringBuffer securityElement = null;
   private File outputFileName;
   private OutputStreamWriter writer;
   private OutputStream outputStream = null;
   private boolean methodsAreIdempotent = false;
   private boolean methodsAreNonTransactional = false;

   public XMLDescriptorCreator(String fileName, String rootDir) {
      String xmlFileName;
      String path;
      if (rootDir == null) {
         int ix = fileName.lastIndexOf(46);
         xmlFileName = fileName.substring(ix + 1) + "RTD.xml";
      } else {
         path = fileName.replace('.', File.separatorChar);
         xmlFileName = rootDir + File.separatorChar + path + "RTD.xml";
      }

      this.outputFileName = new File(xmlFileName);
      path = this.outputFileName.getParent();
      if (path != null) {
         File dir = new File(path);
         if (!dir.exists()) {
            dir.mkdirs();
         }
      }

      this.initializeRMIElement(fileName);
   }

   public XMLDescriptorCreator(OutputStream out, String fileName) {
      this.outputStream = out;
      this.initializeRMIElement(fileName);
   }

   private void initializeRMIElement(String fileName) {
      this.rmiElement = new UnsyncStringBuffer();
      this.rmiElement.append("<rmi xmlns=\"http://xmlns.oracle.com/weblogic/rmi\"" + NEWLINE);
      this.rmiElement.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + NEWLINE);
      this.rmiElement.append("    xsi:schemaLocation=\"http://xmlns.oracle.com/weblogic/rmi" + NEWLINE);
      this.rmiElement.append("    http://xmlns.oracle.com/weblogic/rmi/rmi.xsd\"" + NEWLINE);
      this.rmiElement.append("    version=\"1.0\"" + NEWLINE);
      this.rmiElement.append("    name=\"" + fileName + "\"");
      this.rmiElement.append(NEWLINE);
   }

   private void initializeClusterElement() {
      if (this.clusterElement == null) {
         this.clusterElement = new UnsyncStringBuffer();
         this.clusterElement.append("<cluster" + NEWLINE);
      }
   }

   private void initializeLifeCycleElement() {
      if (this.lifeCycleElement == null) {
         this.lifeCycleElement = new UnsyncStringBuffer();
         this.lifeCycleElement.append("<lifecycle" + NEWLINE);
      }
   }

   private void initializeDefaultMethodElement() {
      if (this.defaultMethodElement == null) {
         this.defaultMethodElement = new UnsyncStringBuffer();
         this.defaultMethodElement.append("<method" + NEWLINE);
         this.defaultMethodElement.append("     name=\"*\"");
         this.defaultMethodElement.append(NEWLINE);
      }
   }

   private void initializeSecurityElement() {
      if (this.securityElement == null) {
         this.securityElement = new UnsyncStringBuffer();
         this.securityElement.append("<security" + NEWLINE);
      }
   }

   public void useServerSideStubs() {
      this.rmiElement.append("    ");
      this.rmiElement.append("use-server-side-stubs=\"true\"");
      this.rmiElement.append(NEWLINE);
   }

   public void disableLocalCallsByReference() {
      this.rmiElement.append("    ");
      this.rmiElement.append("enable-call-by-reference=\"false\"");
      this.rmiElement.append(NEWLINE);
   }

   public void setRemoteRefClassName(String name) {
      this.rmiElement.append("    ");
      this.rmiElement.append("remote-ref-classname=\"");
      this.rmiElement.append(name);
      this.rmiElement.append("\"");
      this.rmiElement.append(NEWLINE);
   }

   public void setServerRefClassName(String name) {
      this.rmiElement.append("    ");
      this.rmiElement.append("server-ref-classname=\"");
      this.rmiElement.append(name);
      this.rmiElement.append("\"");
      this.rmiElement.append(NEWLINE);
   }

   public void setInitialReference(String name) {
      this.rmiElement.append("    ");
      this.rmiElement.append("initial-reference=\"");
      this.rmiElement.append(name);
      this.rmiElement.append("\"");
      this.rmiElement.append(NEWLINE);
   }

   public void setNetworkAccessPoint(String value) {
      this.rmiElement.append("    ");
      this.rmiElement.append("network-access-point");
      this.rmiElement.append("=\"");
      this.rmiElement.append(value);
      this.rmiElement.append("\"");
      this.rmiElement.append(NEWLINE);
   }

   public void setClusterable() {
      this.initializeClusterElement();
      this.clusterElement.append("    ");
      this.clusterElement.append("clusterable=\"true\"");
      this.clusterElement.append(NEWLINE);
   }

   public void setMethodsAreIdempotent() {
      this.initializeDefaultMethodElement();
      this.defaultMethodElement.append("    ");
      this.defaultMethodElement.append("idempotent=\"true\"");
      this.defaultMethodElement.append(NEWLINE);
      this.methodsAreIdempotent = true;
   }

   public void setMethodsAreNonTransactional() {
      this.initializeDefaultMethodElement();
      this.defaultMethodElement.append("    ");
      this.defaultMethodElement.append("transactional=\"false\"");
      this.defaultMethodElement.append(NEWLINE);
   }

   public void setDispatchPolicy(String policy) {
      this.initializeDefaultMethodElement();
      this.defaultMethodElement.append("    ");
      this.defaultMethodElement.append("dispatch-policy=\"");
      this.defaultMethodElement.append(policy);
      this.defaultMethodElement.append("\"");
      this.defaultMethodElement.append(NEWLINE);
   }

   public void setMethodsAreOneway() {
      this.initializeDefaultMethodElement();
      this.defaultMethodElement.append("    ");
      this.defaultMethodElement.append("oneway=\"true\"");
      this.defaultMethodElement.append(NEWLINE);
   }

   public void setMethodsAreAsynchronous() {
      this.initializeDefaultMethodElement();
      this.defaultMethodElement.append("    ");
      this.defaultMethodElement.append("asynchronous=\"true\"");
      this.defaultMethodElement.append(NEWLINE);
   }

   public void setTimeout(int timeout) {
      this.initializeDefaultMethodElement();
      this.defaultMethodElement.append("    ");
      this.defaultMethodElement.append("timeout=\"" + timeout + "\"");
      this.defaultMethodElement.append(NEWLINE);
   }

   public void setPropagateEnvironment() {
      this.initializeClusterElement();
      this.clusterElement.append("    ");
      this.clusterElement.append("propagate-environment=\"true\"");
      this.clusterElement.append(NEWLINE);
   }

   public void setLoadAlgorithm(String algorithm) {
      this.initializeClusterElement();
      this.clusterElement.append("    ");
      this.clusterElement.append("load-algorithm=\"");
      this.clusterElement.append(algorithm);
      this.clusterElement.append("\"");
      this.clusterElement.append(NEWLINE);
   }

   public void setCallRouter(String callRouter) {
      this.initializeClusterElement();
      this.clusterElement.append("    ");
      this.clusterElement.append("call-router-classname=\"");
      this.clusterElement.append(callRouter);
      this.clusterElement.append("\"");
      this.clusterElement.append(NEWLINE);
   }

   public void setReplicaHandler(String replicahandler) {
      this.initializeClusterElement();
      this.clusterElement.append("    ");
      this.clusterElement.append("replica-handler-classname=\"");
      this.clusterElement.append(replicahandler);
      this.clusterElement.append("\"");
      this.clusterElement.append(NEWLINE);
   }

   public void setStickToFirstServer() {
      this.initializeClusterElement();
      this.clusterElement.append("    ");
      this.clusterElement.append("stick-to-first-server=\"true\"");
      this.clusterElement.append(NEWLINE);
   }

   public void setDGCPolicy(String policy) {
      this.initializeLifeCycleElement();
      this.lifeCycleElement.append("    ");
      this.lifeCycleElement.append("dgc-policy=\"");
      this.lifeCycleElement.append(policy);
      this.lifeCycleElement.append("\"");
      this.lifeCycleElement.append(NEWLINE);
   }

   public void setActivationID(String id) {
      this.initializeLifeCycleElement();
      this.lifeCycleElement.append("    ");
      this.lifeCycleElement.append("activation-identifier-classname=\"");
      this.lifeCycleElement.append(id);
      this.lifeCycleElement.append("\"");
      this.lifeCycleElement.append(NEWLINE);
   }

   public void setConfidentiality(String confidentiality) {
      this.initializeSecurityElement();
      this.securityElement.append("    ");
      this.securityElement.append("confidentiality=\"");
      this.securityElement.append(confidentiality);
      this.securityElement.append("\"");
      this.securityElement.append(NEWLINE);
   }

   public void setClientCertAuthentication(String clientCertAuthentication) {
      this.initializeSecurityElement();
      this.securityElement.append("    ");
      this.securityElement.append("client-cert-authentication=\"");
      this.securityElement.append(clientCertAuthentication);
      this.securityElement.append("\"");
      this.securityElement.append(NEWLINE);
   }

   public void setClientAuthentication(String clientAuthentication) {
      this.initializeSecurityElement();
      this.securityElement.append("    ");
      this.securityElement.append("client-authentication=\"");
      this.securityElement.append(clientAuthentication);
      this.securityElement.append("\"");
      this.securityElement.append(NEWLINE);
   }

   public void setIdentityAssertion(String identityAssertion) {
      this.initializeSecurityElement();
      this.securityElement.append("    ");
      this.securityElement.append("identity-assertion=\"");
      this.securityElement.append(identityAssertion);
      this.securityElement.append("\"");
      this.securityElement.append(NEWLINE);
   }

   public void setIntegrity(String integrity) {
      this.initializeSecurityElement();
      this.securityElement.append("    ");
      this.securityElement.append("integrity=\"");
      this.securityElement.append(integrity);
      this.securityElement.append("\"");
      this.securityElement.append(NEWLINE);
   }

   public void setRmicMethodDescriptors(Collection mds) {
      if (mds != null) {
         this.specificMethodElements = new UnsyncStringBuffer();
         Iterator it = mds.iterator();

         while(it.hasNext()) {
            RmicMethodDescriptor md = (RmicMethodDescriptor)it.next();
            this.specificMethodElements.append("<method" + NEWLINE);
            this.specificMethodElements.append("    name=\"");
            this.specificMethodElements.append(md.getMethodSignature() + "\"");
            this.specificMethodElements.append(NEWLINE);
            if (md.getAsynchronousResult() != null || md.getOneway() != null) {
               this.setNonTransactionalForAsyncMethod(this.specificMethodElements);
            }

            if (md.isRequiresTransaction()) {
               this.specificMethodElements.append("    ");
               this.specificMethodElements.append("requires-transaction=\"true\"");
               this.specificMethodElements.append(NEWLINE);
            }

            if (md.isIdempotent() != null) {
               this.specificMethodElements.append("    ");
               this.specificMethodElements.append("idempotent=\"" + md.isIdempotent() + "\"");
               this.specificMethodElements.append(NEWLINE);
            } else if (this.methodsAreIdempotent) {
               this.specificMethodElements.append("    ");
               this.specificMethodElements.append("idempotent=\"true\"");
               this.specificMethodElements.append(NEWLINE);
            }

            if (md.getAsynchronousResult() != null) {
               this.specificMethodElements.append("    ");
               this.specificMethodElements.append("asynchronous=\"" + md.getAsynchronousResult() + "\"");
               this.specificMethodElements.append(NEWLINE);
            }

            if (md.getOneway() != null) {
               this.specificMethodElements.append("    ");
               this.specificMethodElements.append("oneway=\"" + md.getOneway() + "\"");
               this.specificMethodElements.append(NEWLINE);
            }

            if (md.getTimeout() != null) {
               this.specificMethodElements.append("    ");
               this.specificMethodElements.append("timeout=\"" + md.getTimeout() + "\"");
               this.specificMethodElements.append(NEWLINE);
            }

            if (md.getRemoteExceptionWrapperClassName() != null) {
               this.specificMethodElements.append("    ");
               this.specificMethodElements.append("remote-exception-wrapper-classname=\"" + md.getRemoteExceptionWrapperClassName() + "\"");
               this.specificMethodElements.append(NEWLINE);
            }

            this.specificMethodElements.append(METHOD_END);
            this.specificMethodElements.append(NEWLINE);
         }
      }

   }

   private void setNonTransactionalForAsyncMethod(UnsyncStringBuffer methodElement) {
      methodElement.append("    ");
      methodElement.append("transactional=\"false\"");
      methodElement.append(NEWLINE);
   }

   public void setMethodDescriptors(List listOfMethodAttributesMap) {
      if (listOfMethodAttributesMap != null) {
         Iterator itr = listOfMethodAttributesMap.iterator();

         while(itr.hasNext()) {
            Map methodAttributes = (Map)itr.next();
            boolean defaultMethodDescriptor = false;
            UnsyncStringBuffer methodElementBuffer = null;
            String name = (String)methodAttributes.get("name");
            if (name.equals("*")) {
               defaultMethodDescriptor = true;
               this.initializeDefaultMethodElement();
               methodElementBuffer = this.defaultMethodElement;
            } else {
               if (this.specificMethodElements == null) {
                  this.specificMethodElements = new UnsyncStringBuffer();
               }

               methodElementBuffer = this.specificMethodElements;
               methodElementBuffer.append("<method" + NEWLINE);
               methodElementBuffer.append("    name=\"");
               methodElementBuffer.append(name + "\"");
               methodElementBuffer.append(NEWLINE);
            }

            String temp = (String)methodAttributes.get("dispatch-context");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("dispatch-context=\"");
               methodElementBuffer.append(temp + "\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("transactional");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("transactional=\"false\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("oneway");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("oneway=\"true\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("oneway-transactional-request");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("oneway-transactional-request=\"true\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("oneway-transactional-response");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("oneway-transactional-response=\"true\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("oneway-transactional-response");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("oneway-transactional-response=\"true\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("oneway-transactional-response");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("oneway-transactional-response=\"true\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("idempotent");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("idempotent=\"true\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("requires-transaction");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("requires-transaction=\"true\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("timeout");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("timeout=\"");
               methodElementBuffer.append(temp + "\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("asynchronous");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("asynchronous=\"true\"");
               methodElementBuffer.append(NEWLINE);
            }

            temp = (String)methodAttributes.get("remote-exception-wrapper-classname");
            if (temp != null) {
               methodElementBuffer.append("    ");
               methodElementBuffer.append("remote-exception-wrapper-classname=\"");
               methodElementBuffer.append(temp + "\"");
               methodElementBuffer.append(NEWLINE);
            }

            if (!defaultMethodDescriptor) {
               this.specificMethodElements.append(METHOD_END);
               this.specificMethodElements.append(NEWLINE);
            }
         }

      }
   }

   public void createDescriptor() throws IOException {
      if (this.outputStream != null) {
         this.createDescriptor(this.outputStream);
      } else {
         this.createDescriptor(new FileOutputStream(this.outputFileName));
      }

   }

   private void createDescriptor(OutputStream out) throws IOException {
      this.writer = new OutputStreamWriter(out, "UTF-8");
      this.writer.write("<?xml version='1.0' encoding='UTF-8'?>", 0, "<?xml version='1.0' encoding='UTF-8'?>".length());
      this.writer.write(NEWLINE, 0, NEWLINE.length());
      this.writer.write(NEWLINE, 0, NEWLINE.length());
      this.writer.write(this.rmiElement.toString(), 0, this.rmiElement.length());
      this.writer.write(">", 0, 1);
      this.writer.write(NEWLINE, 0, NEWLINE.length());
      if (this.clusterElement != null) {
         this.writer.write(this.clusterElement.toString(), 0, this.clusterElement.length());
         this.writer.write(CLUSTER_END, 0, CLUSTER_END.length());
         this.writer.write(NEWLINE, 0, NEWLINE.length());
         this.writer.write(NEWLINE, 0, NEWLINE.length());
      }

      if (this.lifeCycleElement != null) {
         this.writer.write(this.lifeCycleElement.toString(), 0, this.lifeCycleElement.length());
         this.writer.write(LIFECYCLE_END, 0, LIFECYCLE_END.length());
         this.writer.write(NEWLINE, 0, NEWLINE.length());
         this.writer.write(NEWLINE, 0, NEWLINE.length());
      }

      if (this.defaultMethodElement != null) {
         this.writer.write(this.defaultMethodElement.toString(), 0, this.defaultMethodElement.length());
         this.writer.write(METHOD_END, 0, METHOD_END.length());
         this.writer.write(NEWLINE, 0, NEWLINE.length());
      }

      if (this.specificMethodElements != null) {
         this.writer.write(this.specificMethodElements.toString(), 0, this.specificMethodElements.length());
         this.writer.write(NEWLINE, 0, NEWLINE.length());
      }

      if (this.securityElement != null) {
         this.writer.write(this.securityElement.toString(), 0, this.securityElement.length());
         this.writer.write(SECURITY_END, 0, SECURITY_END.length());
         this.writer.write(NEWLINE, 0, NEWLINE.length());
      }

      this.writer.write(RMI_END, 0, RMI_END.length());
      this.writer.write(NEWLINE, 0, NEWLINE.length());
      this.writer.flush();
      this.writer.close();
   }

   static {
      CLUSTER_END = ">" + NEWLINE + "</cluster>";
      LIFECYCLE_END = ">" + NEWLINE + "</lifecycle>";
      RMI_END = NEWLINE + "</rmi>";
      METHOD_END = ">" + NEWLINE + "</method>";
      SECURITY_END = ">" + NEWLINE + "</security>";
   }
}
