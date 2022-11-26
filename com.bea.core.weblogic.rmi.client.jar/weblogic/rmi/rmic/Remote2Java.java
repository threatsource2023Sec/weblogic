package weblogic.rmi.rmic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Enumeration;
import weblogic.rmi.extensions.server.DescriptorHelper;
import weblogic.rmi.internal.DescriptorManager;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.SkelGenerator;
import weblogic.rmi.internal.StubGenerator;
import weblogic.rmi.internal.dgc.DGCPolicyConstants;
import weblogic.utils.Debug;
import weblogic.utils.Getopt2;
import weblogic.utils.classfile.BadBytecodesException;
import weblogic.utils.classfile.ClassFile;
import weblogic.utils.classloaders.ClasspathClassLoader;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class Remote2Java extends CodeGenerator implements DGCPolicyConstants, Remote2JavaConstants {
   private static boolean debug = false;
   private static boolean verbose = false;
   private ClassLoader cl;
   private GenericClassLoader userClassloader;
   private Collection rmicMethodDescriptors;
   CodeGenerator.Output currentOutput;
   private boolean declaresJavaRemoteException;
   private boolean usesJavaRemote;
   private boolean declaresWeblogicRemoteException;
   private boolean usesWeblogicRemote;
   private boolean declaresIOException;
   private boolean canThrowIOException;
   private boolean canThrowClassNotFoundException;
   private boolean asyncArgument;
   private String asyncArgumentName;
   private boolean asyncReturnType;
   private boolean clusterable;
   private boolean iiop;
   private boolean eos;
   public Class interfaceType;
   private boolean oneway;
   private boolean asynchronousResult;
   private int timeout;
   private int option_value;
   private int arg_value;
   private boolean transactional;
   private boolean nontransactional;
   private boolean mangleNames;
   private String verboseMethods;
   private boolean hasDescriptor;
   private String descriptorFileName;
   private boolean useStub2;
   private boolean useServerSideStubs;
   private boolean enforceCallByValue;
   private boolean setDgcPolicy;
   private String dgcPolicyStr;
   private boolean setDispatchPolicy;
   private String dispatchPolicyName;
   private String replicaHandler;
   private String loadAlgorithm;
   private String callRouter;
   private int replicaRefreshInterval;
   private boolean methodsAreIdempotent;
   private boolean propagateEnvironment;
   private boolean stickToFirstServer;
   private boolean hasSecurityOptions;
   private String methodSigName;
   private String remoteRef;
   private String serverRef;
   private boolean hasCustomRef;
   private String initialReference;
   private boolean hasInitialReference;
   private String clientAuthentication;
   private String clientCertAuthentication;
   private String identityAssertion;
   private String integrity;
   private String confidentiality;
   private boolean disableHotCodGen;
   private String networkAccessPoint;
   private boolean forceRTDGeneration;

   public Remote2Java(Getopt2 opts) {
      super(opts);
      this.cl = null;
      this.userClassloader = null;
      this.rmicMethodDescriptors = null;
      this.clusterable = false;
      this.iiop = false;
      this.eos = false;
      this.oneway = false;
      this.asynchronousResult = false;
      this.timeout = 0;
      this.option_value = -1;
      this.arg_value = -1;
      this.transactional = true;
      this.nontransactional = false;
      this.descriptorFileName = null;
      this.replicaRefreshInterval = 180;
      this.methodsAreIdempotent = false;
      this.propagateEnvironment = false;
      this.stickToFirstServer = false;
      this.hasSecurityOptions = false;
      this.methodSigName = null;
      this.remoteRef = null;
      this.serverRef = null;
      this.hasCustomRef = false;
      this.initialReference = null;
      this.hasInitialReference = false;
      this.disableHotCodGen = false;
      this.forceRTDGeneration = false;
      addRMIStubGeneratorOptions(opts);
      if (verbose) {
         Debug.say("opts ok");
      }

   }

   public Remote2Java(Getopt2 opts, ClassLoader cl, Collection rmicMethodDescriptors) {
      this(opts);
      this.cl = cl;
      this.rmicMethodDescriptors = rmicMethodDescriptors;
   }

   public static void addRMIStubGeneratorOptions(Getopt2 opts) {
      opts.addFlag("nontransactional", "Suspends the transaction before making the RMI call and resumes after");
      opts.addFlag("verbosemethods", "Instruments proxies to print debug info to std err.");
      opts.addOption("descriptor", "example", "Associates or creates a descriptor for each remote class.");
      opts.addFlag("nomanglednames", "Don't mangle the names of the stubs and skeletons.");
      opts.addFlag("v1.2", "Generate Java 1.2 style stubs");
      opts.markPrivate("v1.2");
      opts.addFlag("disableHotCodeGen", "Generate stubs/skels instead of doing hot-code-gen");
      opts.markPrivate("disableHotCodeGen");
      opts.addFlag("forceRTDGeneration", "Generate rtd.xml for implementation class. Use in conjunction with annotations");
      opts.markPrivate("forceRTDGeneration");
      opts.addFlag("serverSideStubs", "Force collocated calls to go through server-side stubs.");
      opts.markPrivate("serverSideStubs");
      opts.addFlag("enforceCallByValue", "Always use call-by-value semantics, suppresses optimization for collocated objects.");
      opts.markPrivate("enforceCallByValue");
      opts.addOption("dgcPolicy", "leased", "Use the specified for each remote class.");
      opts.markPrivate("dgcPolicy");
      opts.addOption("dispatchPolicy", "", "Specify the dispatch policy name for each remote class.");
      opts.addFlag("oneway", "All calls will be oneway calls");
      opts.addFlag("asynchronous", "Required to annonate a method asynchronous");
      opts.addOption("timeout", "ms", "All calls timeout in the specified ms");
      opts.addOption("remoteRefClassName", "", "Should use this custom ref on the client");
      opts.markPrivate("remoteRefClassName");
      opts.addOption("serverRefClassName", "", "Should use this custom server ref on the server");
      opts.markPrivate("serverRefClassName");
      opts.addFlag("activatable", "RMI object activation is activatable");
      opts.markPrivate("activatable");
      opts.addFlag("eos", "Marks as the rmi service as exactly once");
      opts.markPrivate("eos");
      opts.addOption("initialReference", "", "Specify the value of the OID for a weel known remote object.");
      opts.markPrivate("initialReference");
      opts.addOption("confidentiality", "supported", "for iiop security");
      opts.markPrivate("confidentiality");
      opts.addOption("clientAuthentication", "supported", "for iiop security");
      opts.markPrivate("clientAuthentication");
      opts.addOption("clientCertAuthentication", "supported", "for iiop security");
      opts.markPrivate("clientCertAuthentication");
      opts.addOption("integrity", "supported", "for iiop security");
      opts.markPrivate("integrity");
      opts.addOption("identityAssertion", "supported", "for iiop security");
      opts.markPrivate("identityAssertion");
      opts.addOption("classpath", (String)null, "use classpath specified as parameter ");
      opts.markPrivate("classpath");
      opts.addOption("networkAccessPoint", (String)null, "Use specified network access point ");
      opts.markPrivate("networkAccessPoint");
      addClusterOptions(opts);
   }

   public String getRootDirectoryName() {
      String name = super.getRootDirectoryName();
      return name != null && !name.equals("null") ? name : ".";
   }

   private static void addClusterOptions(Getopt2 opts) {
      opts.addFlag("clusterable", "Marks as clusterable.  A clusterable rmi service is one that can be hosted by multiple servers in a WebLogic cluster.  Each hosting object, or replica, is bound into the naming service under a common name.  When the service stub is retrieved from the naming service, it contains a replica-aware reference that maintains the list of replicas and performs load-balancing and fail-over between them.");
      opts.addAlias("replicaAware", "clusterable");
      opts.addOption("loadAlgorithm", "algorithm", "May only be used in conjuction with -clusterable.  Specifies a service specific algorithm to use for load-balancing and failover.  By default all clustered services use the algorithm specified by the weblogic.cluster.loadAlgorithm server property.  Use this flag to specify a specific algorithm for this service.  <algorithm> must be one of the following: round-robin, random, or weight-based, round-robin-affinity, weight-based-affinity, random-affinity.");
      opts.addOption("callRouter", "callRouterClass", "May only be used in conjuction with -clusterable.  Specifies the class to be used for routing method calls.  This class must implement weblogic.rmi.cluster.CallRouter.  If specified, an instance of this class will be called before each method call and be given the opportunity to choose a server to route to based on the method parameters.  It either returns a server name or null indicating that the current load algorithm should be used to pick the server.");
      opts.addOption("replicaHandler", "replicaHandlerClassName", "Specifies a custom replica handler class.  This class, which must implement the weblogic.service.ReplicaHandlerInterface, will be used by the stub to handle fail-over and load balancing.");
      opts.markPrivate("replicaHandler");
      opts.addFlag("propagateEnvironment", "May only be used in conjuction with -clusterable.  Enables propagation of JNDI environment to objects returned by this object.  This is useful for clusterable factories that return clusterable objects (like EJBHome).");
      opts.markPrivate("propagateEnvironment");
      opts.addFlag("stickToFirstServer", "May only be used in conjuction with -clusterable.  Enables 'sticky' load balancing.  The server chosen for servicing the first request will be used for all subsequent requests.");
      opts.addFlag("methodsAreIdempotent", "May only be used in conjuction with -clusterable.  Indicates that the methods on this class are idempotent.  This allows the stub to attempt recovery from any communication failure, even if it can not ensure that failure occurred before the remote method was invoked.  By default the stub will only retry on failures that are guaranteed to have occurred before the remote method was invoked.");
      opts.addOption("replicaListRefreshInterval", "seconds", "Deprecated.  A replica-aware stub now refreshes its list whenever detects a change in the cluster");
      opts.markPrivate("replicaListRefreshInterval");
   }

   public String writeLookup(String clazz) {
      if (clazz.equals("boolean")) {
         return "Boolean";
      } else if (clazz.equals("int")) {
         return "Int";
      } else if (clazz.equals("short")) {
         return "Short";
      } else if (clazz.equals("long")) {
         return "Long";
      } else if (clazz.equals("double")) {
         return "Double";
      } else if (clazz.equals("float")) {
         return "Float";
      } else if (clazz.equals("char")) {
         return "Char";
      } else {
         return clazz.equals("byte") ? "Byte" : "Object";
      }
   }

   protected void extractOptionValues(Getopt2 opts) {
      this.transactional = !opts.hasOption("nontransactional");
      this.nontransactional = opts.hasOption("nontransactional");
      this.mangleNames = !opts.hasOption("nomanglednames");
      this.verboseMethods = opts.hasOption("verbosemethods") ? "true" : "false";
      this.useStub2 = opts.hasOption("v1.2");
      this.useServerSideStubs = opts.hasOption("serverSideStubs");
      this.enforceCallByValue = opts.hasOption("enforceCallByValue");
      this.hasDescriptor = opts.hasOption("descriptor");
      this.oneway = opts.hasOption("oneway");
      this.asynchronousResult = opts.hasOption("asynchronous");
      this.timeout = opts.getIntegerOption("timeout");
      if (this.hasDescriptor) {
         this.descriptorFileName = opts.getOption("descriptor", "");
         if (verbose) {
            Debug.say("descriptorFileName = [" + this.descriptorFileName + "]");
         }
      }

      this.setDgcPolicy = opts.hasOption("dgcPolicy");
      if (this.setDgcPolicy) {
         this.dgcPolicyStr = opts.getOption("dgcPolicy", "");
         if (verbose) {
            Debug.say("dgcPolicyStr = [" + this.dgcPolicyStr + "]");
         }
      }

      this.setDispatchPolicy = opts.hasOption("dispatchPolicy");
      if (this.setDispatchPolicy) {
         this.dispatchPolicyName = opts.getOption("dispatchPolicy", "");
         if (verbose) {
            Debug.say("dispatchPolicyName = [" + this.dispatchPolicyName + "]");
         }
      }

      if (opts.hasOption("remoteRefClassName")) {
         this.remoteRef = opts.getOption("remoteRefClassName", "");
         this.hasCustomRef = true;
         if (verbose) {
            Debug.say("REMOTE REF " + this.remoteRef);
         }
      }

      if (opts.hasOption("serverRefClassName")) {
         this.serverRef = opts.getOption("serverRefClassName", "");
         this.hasCustomRef = true;
         if (verbose) {
            Debug.say("SERVER REF " + this.serverRef);
         }
      }

      if (opts.hasOption("initialReference")) {
         this.initialReference = opts.getOption("initialReference", "");
         this.hasInitialReference = true;
         if (verbose) {
            Debug.say("INITIAL_REFERENCE " + this.initialReference);
         }
      }

      if (opts.hasOption("confidentiality")) {
         this.confidentiality = opts.getOption("confidentiality");
         this.hasSecurityOptions = true;
      }

      if (opts.hasOption("integrity")) {
         this.hasSecurityOptions = true;
         this.integrity = opts.getOption("integrity");
      }

      if (opts.hasOption("identityAssertion")) {
         this.hasSecurityOptions = true;
         this.identityAssertion = opts.getOption("identityAssertion");
      }

      if (opts.hasOption("clientCertAuthentication")) {
         this.hasSecurityOptions = true;
         this.clientCertAuthentication = opts.getOption("clientCertAuthentication");
      }

      if (opts.hasOption("clientAuthentication")) {
         this.hasSecurityOptions = true;
         this.clientAuthentication = opts.getOption("clientAuthentication");
      }

      if (opts.hasOption("disableHotCodeGen")) {
         this.disableHotCodGen = true;
      }

      if (opts.hasOption("forceRTDGeneration")) {
         this.forceRTDGeneration = true;
      }

      this.eos = opts.hasOption("eos");
      if (this.eos) {
         this.hasCustomRef = true;
         this.serverRef = "weblogic.rmi.cluster.MigratableServerRef";
      }

      if (opts.hasOption("activatable")) {
         this.hasCustomRef = true;
         this.setDgcPolicy = true;
         this.dgcPolicyStr = "managed";
         this.remoteRef = "weblogic.rmi.internal.activation.ActivatableRemoteRef";
         this.serverRef = "weblogic.rmi.internal.activation.ActivatableServerRef";
      }

      if (opts.hasOption("iiop") || opts.hasOption("idl")) {
         this.iiop = true;
      }

      if (opts.hasOption("classpath")) {
         String path = opts.getOption("classpath");
         this.userClassloader = new ClasspathClassLoader(path);
      }

      if (opts.hasOption("networkAccessPoint")) {
         this.networkAccessPoint = opts.getOption("networkAccessPoint");
      }

      this.extractClusterOptionValues(opts);
   }

   private void extractClusterOptionValues(Getopt2 opts) {
      this.clusterable = opts.hasOption("clusterable") || opts.hasOption("replicaAware");
      if (this.clusterable) {
         this.useStub2 = true;
         this.replicaHandler = opts.getOption("replicaHandler");
         this.methodsAreIdempotent = opts.hasOption("methodsAreIdempotent");
         this.propagateEnvironment = opts.hasOption("propagateEnvironment");
         this.loadAlgorithm = opts.getOption("loadAlgorithm");
         this.stickToFirstServer = opts.hasOption("stickToFirstServer");
         this.callRouter = opts.getOption("callRouter");
         if ("none".equals(this.callRouter)) {
            this.callRouter = null;
         }
      }

   }

   private boolean createDescriptor() {
      return this.useServerSideStubs || this.enforceCallByValue || this.setDgcPolicy || this.setDispatchPolicy || this.clusterable || this.oneway || this.timeout > 0 || this.nontransactional || this.hasCustomRef || this.eos || this.hasSecurityOptions || this.asynchronousResult || this.networkAccessPoint != null;
   }

   public Enumeration outputs(Object[] inputs) throws Exception {
      String[] classes = (String[])((String[])inputs);

      for(int i = 0; i < classes.length; ++i) {
         if (verbose) {
            Debug.say("try " + classes[i]);
         }

         try {
            if (!this.iiop) {
               this.checkIsNotInterface(classes[i]);
            }

            if (this.hasDescriptor) {
               this.outputXMLDescriptor(classes[i], this.getRootDirectoryName(), this.descriptorFileName);
            } else if (this.createDescriptor()) {
               XMLDescriptorCreator xmldesc = new XMLDescriptorCreator(classes[i], this.getRootDirectoryName());
               this.setReplicaAwareDescriptorValues(xmldesc);
               if (this.useServerSideStubs) {
                  xmldesc.useServerSideStubs();
               }

               if (this.enforceCallByValue) {
                  xmldesc.disableLocalCallsByReference();
               }

               if (this.setDgcPolicy && this.dgcPolicyStr != null && !this.dgcPolicyStr.equals("")) {
                  xmldesc.setDGCPolicy(this.dgcPolicyStr);
               }

               if (this.setDispatchPolicy && this.dispatchPolicyName != null && !this.dispatchPolicyName.equals("")) {
                  xmldesc.setDispatchPolicy(this.dispatchPolicyName);
               }

               if (this.nontransactional) {
                  xmldesc.setMethodsAreNonTransactional();
               }

               if (this.oneway) {
                  xmldesc.setMethodsAreOneway();
               }

               if (this.asynchronousResult) {
                  xmldesc.setMethodsAreAsynchronous();
               }

               if (this.timeout > 0) {
                  xmldesc.setTimeout(this.timeout);
               }

               if (this.hasCustomRef) {
                  if (this.remoteRef != null) {
                     xmldesc.setRemoteRefClassName(this.remoteRef);
                  }

                  if (this.serverRef != null) {
                     xmldesc.setServerRefClassName(this.serverRef);
                  }
               }

               if (this.hasInitialReference) {
                  xmldesc.setInitialReference(this.initialReference);
               }

               if (this.networkAccessPoint != null) {
                  xmldesc.setNetworkAccessPoint(this.networkAccessPoint);
               }

               if (this.eos) {
                  this.setMigratableServiceOptions(xmldesc);
               }

               if (this.clientAuthentication != null) {
                  xmldesc.setClientAuthentication(this.clientAuthentication);
               }

               if (this.clientCertAuthentication != null) {
                  xmldesc.setClientCertAuthentication(this.clientCertAuthentication);
               }

               if (this.identityAssertion != null) {
                  xmldesc.setIdentityAssertion(this.identityAssertion);
               }

               if (this.confidentiality != null) {
                  xmldesc.setConfidentiality(this.confidentiality);
               }

               if (this.integrity != null) {
                  xmldesc.setIntegrity(this.integrity);
               }

               if (this.rmicMethodDescriptors != null) {
                  xmldesc.setRmicMethodDescriptors(this.rmicMethodDescriptors);
               }

               xmldesc.createDescriptor();
            }
         } catch (IllegalAccessError var9) {
            throw new CodeGenerationException(classes[i] + " or one of it's dependencies has changed in an incompatible way.", var9);
         }

         try {
            if (this.disableHotCodGen || this.forceRTDGeneration) {
               Class c = this.loadClass(classes[i]);
               RuntimeDescriptor rtd = DescriptorHelper.getDescriptorForRMIC(c);
               if (!this.createDescriptor() && this.forceRTDGeneration) {
                  rtd.generateXMLDescriptor(classes[i], this.getRootDirectoryName());
               }

               if (this.disableHotCodGen) {
                  this.generateStubAndSkel(classes[i], rtd);
               }
            }
         } finally {
            if (this.userClassloader != null) {
               this.userClassloader.close();
            }

         }
      }

      return null;
   }

   void generateStubAndSkel(String className, RuntimeDescriptor rtd) throws ClassNotFoundException, IOException, BadBytecodesException {
      File f = new File(this.getRootDirectoryName());
      if (!f.exists()) {
         f.mkdir();
      }

      this.generateArtifact(f, new StubGenerator(rtd, (String)null));
      this.generateArtifact(f, new SkelGenerator(rtd));
   }

   private void generateArtifact(File parentDir, ClassFile generator) throws IOException, BadBytecodesException {
      String fileName = generator.getClassName().replace('.', File.separatorChar) + ".class";
      File f = new File(parentDir, fileName);
      File dir = f.getParentFile();
      if (!dir.exists()) {
         dir.mkdirs();
      }

      int sizeWritten = Integer.MAX_VALUE;

      try {
         FileOutputStream fOutStream = new FileOutputStream(f);
         Throwable var8 = null;

         try {
            sizeWritten = generator.write(fOutStream);
         } catch (Throwable var18) {
            var8 = var18;
            throw var18;
         } finally {
            if (fOutStream != null) {
               if (var8 != null) {
                  try {
                     fOutStream.close();
                  } catch (Throwable var17) {
                     var8.addSuppressed(var17);
                  }
               } else {
                  fOutStream.close();
               }
            }

         }
      } catch (BadBytecodesException | IOException var20) {
         this.info("Exception occurred during generation of " + f.getAbsolutePath() + ": " + var20);
         throw var20;
      }

      long sizeOnDisk = f.length();
      if (sizeWritten != Integer.MAX_VALUE && (long)sizeWritten != sizeOnDisk) {
         this.info("Generated " + f.getAbsolutePath() + " size " + sizeOnDisk + " instead of " + sizeWritten);
      } else {
         this.info("Generated " + f.getAbsolutePath() + " size " + sizeOnDisk);
      }

   }

   private void info(String message) {
      if (debug) {
         System.out.println("INFO: " + message);
      }

   }

   void validateDescriptor(String className) throws ClassNotFoundException, RemoteException {
      Class c = this.loadClass(className);
      DescriptorManager.getDescriptor(c);
   }

   private int getDGCPolicy() {
      if (this.dgcPolicyStr != null && !this.dgcPolicyStr.equals("")) {
         if (this.dgcPolicyStr.equalsIgnoreCase("leased")) {
            return 0;
         } else if (this.dgcPolicyStr.equalsIgnoreCase("referenceCounted")) {
            return 1;
         } else if (this.dgcPolicyStr.equalsIgnoreCase("managed")) {
            return 2;
         } else if (this.dgcPolicyStr.equalsIgnoreCase("useItOrLoseIt")) {
            return 3;
         } else {
            return this.dgcPolicyStr.equalsIgnoreCase("deactivateOnMethodBoundries") ? 4 : -1;
         }
      } else {
         return -1;
      }
   }

   private void outputXMLDescriptor(String className, String rootDirectory, String descriptorFileName) throws CodeGenerationException {
      try {
         File inFile = new File(descriptorFileName);
         BufferedReader in = new BufferedReader(new FileReader(inFile));
         String outFileName = rootDirectory + File.separatorChar + className.replace('.', File.separatorChar) + "RTD.xml";
         File outFile = new File(outFileName);
         if (debug) {
            System.out.println(outFile);
         }

         String path = outFile.getParent();
         if (path != null) {
            File dir = new File(path);
            if (!dir.exists()) {
               dir.mkdirs();
            }
         }

         BufferedWriter out = new BufferedWriter(new FileWriter(outFile));

         for(String line = in.readLine(); line != null; line = in.readLine()) {
            out.write(line);
            out.write(System.getProperty("line.separator"));
         }

         out.flush();
         out.close();
         in.close();
      } catch (Exception var11) {
         var11.printStackTrace();
         throw new CodeGenerationException("Error writing rmi descriptor file", var11);
      }
   }

   private void setMigratableServiceOptions(XMLDescriptorCreator desc) {
      desc.setReplicaHandler("weblogic.rmi.cluster.MigratableReplicaHandler");
   }

   private void setReplicaAwareDescriptorValues(XMLDescriptorCreator desc) {
      if (this.clusterable) {
         desc.setClusterable();
         if (this.loadAlgorithm != null) {
            desc.setLoadAlgorithm(this.loadAlgorithm);
         }

         if (this.replicaHandler != null) {
            desc.setReplicaHandler(this.replicaHandler);
         }

         if (this.callRouter != null) {
            desc.setCallRouter(this.callRouter);
         }

         if (this.stickToFirstServer) {
            desc.setStickToFirstServer();
         }

         if (this.propagateEnvironment) {
            desc.setPropagateEnvironment();
         }

         if (this.methodsAreIdempotent) {
            desc.setMethodsAreIdempotent();
         }
      }

   }

   protected void prepare(CodeGenerator.Output output) throws Exception {
      this.currentOutput = output;
   }

   void checkIsNotInterface(String cName) throws Exception {
      try {
         Class c = this.loadClass(cName);
         if (c.isInterface()) {
            throw new Exception(cName + " must be a concrete class, not an interface.");
         }
      } catch (ClassNotFoundException var3) {
         var3.printStackTrace();
         throw new Exception(cName + " must be a remote interface implementation and should exist in the classpath", var3);
      }
   }

   public String getRootName(Class c) {
      int lastDot = c.getName().lastIndexOf(46);
      return lastDot == -1 ? c.getName() : c.getName().substring(lastDot + 1);
   }

   private Class loadClass(String name) throws ClassNotFoundException {
      return this.userClassloader != null ? this.userClassloader.loadClass(name) : this.loadClassUsingSystemClasspath(name);
   }

   private Class loadClassUsingSystemClasspath(String name) throws ClassNotFoundException {
      Class c = null;

      try {
         c = Class.forName(name);
      } catch (ClassNotFoundException var4) {
         if (this.cl == null) {
            throw var4;
         }

         c = this.cl.loadClass(name);
      }

      return c;
   }
}
