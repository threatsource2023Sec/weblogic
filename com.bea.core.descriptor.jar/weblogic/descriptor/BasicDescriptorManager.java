package weblogic.descriptor;

import com.bea.staxb.runtime.MarshalOptions;
import com.bea.staxb.runtime.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamReader;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.descriptor.internal.MarshallerFactory;
import weblogic.descriptor.internal.ProductionMode;
import weblogic.descriptor.internal.XMLStreamReaderDelegate;
import weblogic.diagnostics.debug.DebugLogger;

public class BasicDescriptorManager {
   private MarshallerFactory mf;
   private static final Map factoryMap = new WeakHashMap();
   private DescriptorCreationListener listener;
   private DescriptorMacroSubstitutor macroSubstitutor;
   private final ClassLoader bindingsClassloader;
   private boolean productionMode;
   private boolean editable;
   private SecurityService securityService;
   private Map initialNamespaces = new HashMap();
   private Map namespaceMapping = new HashMap();
   private String schemaLocation;
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugDescriptor");

   protected ClassLoader getBindingsClassLoader() {
      if (this.bindingsClassloader == null) {
         throw new AssertionError("Bindings ClassLoader is not set");
      } else {
         return this.bindingsClassloader;
      }
   }

   public BasicDescriptorManager(ClassLoader cl, boolean e, SecurityService ss) {
      if (cl == null) {
         throw new IllegalArgumentException("null cl");
      } else {
         this.editable = e;
         this.securityService = ss;
         this.bindingsClassloader = cl;
         this.productionMode = ProductionMode.instance().getProductionMode();
      }
   }

   public BasicDescriptorManager(File[] classpath, boolean ed, SecurityService ss) {
      if (classpath == null) {
         throw new IllegalArgumentException("null classpath");
      } else {
         this.editable = ed;
         this.securityService = ss;
         URL[] u = new URL[classpath.length];

         for(int i = 0; i < classpath.length; ++i) {
            try {
               u[i] = classpath[i].toURL();
            } catch (MalformedURLException var7) {
               throw new AssertionError(var7);
            }
         }

         this.bindingsClassloader = new URLClassLoader(u, BasicDescriptorManager.class.getClassLoader());
         this.productionMode = ProductionMode.instance().getProductionMode();
      }
   }

   protected MarshallerFactory getDefaultMF() {
      throw new AssertionError("This version of DescriptorManager does not support default Marshaller");
   }

   protected MarshallerFactory getMarshallerFactory() throws IOException {
      if (this.mf == null) {
         if (this.bindingsClassloader != null) {
            synchronized(factoryMap) {
               WeakReference value = (WeakReference)factoryMap.get(this.bindingsClassloader);
               if (value != null) {
                  this.mf = (MarshallerFactory)value.get();
               }

               if (this.mf == null) {
                  this.mf = new MarshallerFactory(this.bindingsClassloader);
                  factoryMap.put(this.bindingsClassloader, new WeakReference(this.mf));
               }
            }
         } else {
            this.mf = this.getDefaultMF();
         }
      }

      return this.mf;
   }

   protected DescriptorFactory getDescriptorFactory(InputStream document) throws IOException {
      return this.getMarshallerFactory().getDescriptorFactory((InputStream)document, (List)null);
   }

   protected DescriptorFactory getDescriptorFactory(XMLStreamReader document) throws IOException {
      return this.getMarshallerFactory().getDescriptorFactory((XMLStreamReader)document, (List)null);
   }

   protected DescriptorFactory getDescriptorFactory(InputStream document, List holder) throws IOException {
      return this.getMarshallerFactory().getDescriptorFactory(document, holder);
   }

   protected DescriptorFactory getDescriptorFactory(XMLStreamReader document, List holder) throws IOException {
      return this.getMarshallerFactory().getDescriptorFactory(document, holder);
   }

   protected DescriptorFactory getDescriptorFactory(XMLStreamReader document, List holder, Map namespaceMapping) throws IOException {
      return this.getMarshallerFactory().getDescriptorFactory(document, holder, namespaceMapping);
   }

   protected DescriptorFactory getDescriptorFactory(InputStream document, List holder, Map namespaceMapping) throws IOException {
      return this.getMarshallerFactory().getDescriptorFactory(document, holder, namespaceMapping);
   }

   protected boolean isEditable() {
      return this.editable;
   }

   public Descriptor createDescriptor(InputStream document) throws IOException {
      return this.createDescriptor(document, (List)null);
   }

   public Descriptor createDescriptor(InputStream document, List holder) throws IOException {
      return this.createDescriptor(document, holder, true);
   }

   public Descriptor createDescriptor(InputStream document, List holder, boolean validate) throws IOException {
      if (debug.isDebugEnabled()) {
         debug.debug("createDescriptor: in: " + document + " validate: " + validate);
      }

      ClassLoader cl = Thread.currentThread().getContextClassLoader();

      DescriptorImpl var9;
      try {
         Thread.currentThread().setContextClassLoader(this.getBindingsClassLoader());
         BeanCreationInterceptor beanInterceptor = document instanceof BeanCreationInterceptor ? (BeanCreationInterceptor)document : null;
         DescriptorImpl desc = DescriptorImpl.beginConstruction(this.isEditable(), this, this.listener, beanInterceptor);
         desc.setProductionMode(this.productionMode);
         DescriptorBean root = null;
         DescriptorFactory descFactory = this.getDescriptorFactory(document, holder, this.namespaceMapping);
         if (descFactory instanceof CheckValidationDescriptorFactory) {
            root = (DescriptorBean)((DescriptorBean)((CheckValidationDescriptorFactory)descFactory).createDescriptor((InputStream)document, desc, validate));
         } else {
            root = (DescriptorBean)descFactory.createDescriptor((InputStream)document, desc);
         }

         DescriptorImpl.endConstruction(root);
         if (validate) {
            desc.validate();
         } else {
            desc.resolveReferences();
         }

         var9 = desc;
      } finally {
         Thread.currentThread().setContextClassLoader(cl);
      }

      return var9;
   }

   public Descriptor createDescriptor(XMLStreamReader document, boolean validate) throws IOException {
      return this.createDescriptor((XMLStreamReader)document, (List)null, validate);
   }

   public Descriptor createDescriptor(XMLStreamReader document, List holder, boolean validate) throws IOException {
      return this.createDescriptor(document, holder, validate, (String)null);
   }

   public Descriptor createDescriptor(XMLStreamReader document, List holder, boolean validate, String preferredLoadingRoot) throws IOException {
      if (debug.isDebugEnabled()) {
         debug.debug("createDescriptor: doc: " + document + " validate: " + validate);
      }

      ClassLoader cl = Thread.currentThread().getContextClassLoader();

      DescriptorImpl var10;
      try {
         Thread.currentThread().setContextClassLoader(this.getBindingsClassLoader());
         BeanCreationInterceptor beanInterceptor = document instanceof BeanCreationInterceptor ? (BeanCreationInterceptor)document : null;
         DescriptorImpl desc = DescriptorImpl.beginConstruction(this.isEditable(), this, this.listener, beanInterceptor);
         desc.setPreferredRoot(preferredLoadingRoot);
         desc.setProductionMode(this.productionMode);
         DescriptorBean root = null;
         DescriptorFactory descFactory = this.getDescriptorFactory(document, holder, this.namespaceMapping);
         if (descFactory instanceof CheckValidationDescriptorFactory) {
            root = (DescriptorBean)((DescriptorBean)((CheckValidationDescriptorFactory)descFactory).createDescriptor((XMLStreamReader)document, desc, validate));
         } else {
            root = (DescriptorBean)descFactory.createDescriptor(document, desc, validate);
         }

         DescriptorImpl.endConstruction(root);
         if (validate) {
            desc.validate();
         } else {
            desc.resolveReferences();
         }

         var10 = desc;
      } finally {
         Thread.currentThread().setContextClassLoader(cl);
      }

      return var10;
   }

   public Descriptor createDescriptor(XMLStreamReader document) throws IOException {
      return !this.editable ? this.createDescriptor(document, true) : this.createDescriptor(new XMLStreamReaderDelegate(document), true);
   }

   public Descriptor createDescriptorRoot(Class ifc, String characterEncoding) {
      DescriptorImpl desc = (DescriptorImpl)this.createDescriptorRoot(ifc);
      desc.setCharacterEncoding(characterEncoding);
      return desc;
   }

   public Descriptor createDescriptorRoot(Class ifc) {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (debug.isDebugEnabled()) {
         debug.debug("createDescriptorRoo: class: " + ifc);
      }

      DescriptorImpl var5;
      try {
         Thread.currentThread().setContextClassLoader(this.getBindingsClassLoader());
         DescriptorImpl desc = DescriptorImpl.beginConstruction(this.isEditable(), this, this.listener, (BeanCreationInterceptor)null);
         desc.setProductionMode(this.productionMode);
         DescriptorBean root = desc.createRootBean(ifc);
         DescriptorImpl.endConstruction(root);
         desc.setModified(true);
         var5 = desc;
      } finally {
         Thread.currentThread().setContextClassLoader(cl);
      }

      return var5;
   }

   public void writeDescriptorAsXML(Descriptor descriptor, OutputStream out, String characterEncoding) throws IOException {
      DescriptorBean root = descriptor.getRootBean();
      this.writeDescriptorBeanAsXML(root, out, characterEncoding);
   }

   protected void writeDescriptorBeanAsXML(DescriptorBean root, OutputStream out, String characterEncoding) throws IOException {
      this.writeDescriptorBeanAsXML(root, out, characterEncoding, true);
   }

   public void writeDescriptorAsXML(Descriptor descriptor, OutputStream out) throws IOException {
      this.writeDescriptorAsXML(descriptor, out, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public void writeDescriptorBeanAsXML(DescriptorBean root, OutputStream out) throws IOException {
      this.writeDescriptorBeanAsXML(root, out, (String)null);
   }

   protected MarshalOptions createMarshalOptions(Descriptor descriptor, String characterEncoding) {
      MarshalOptions options = new MarshalOptions();
      options.setPrettyPrint(true);
      options.setPrettyPrintIndent(2);
      if (characterEncoding != null) {
         options.setCharacterEncoding(characterEncoding);
      } else if (((DescriptorImpl)descriptor).getCharacterEncoding() != null) {
         options.setCharacterEncoding(((DescriptorImpl)descriptor).getCharacterEncoding());
      }

      if (this.editable) {
         options.setUseDefaultNamespaceForRootElement(true);
         options.setInitialNamespaces(this.initialNamespaces);
         if (this.schemaLocation != null) {
            options.setSchemaLocation(this.schemaLocation);
         } else {
            Map schemaLocations = ((DescriptorImpl)descriptor).getSchemaLocations();
            if (schemaLocations != null) {
               StringBuffer sb = new StringBuffer();
               Set namespaces = schemaLocations.keySet();
               Iterator i = namespaces.iterator();

               while(i.hasNext()) {
                  String namespace = (String)i.next();
                  String location = (String)schemaLocations.get(namespace);
                  sb.append(namespace);
                  sb.append(" ");
                  sb.append(location);
                  sb.append(" ");
               }

               if (sb.length() > 0) {
                  options.setSchemaLocation(sb.toString().trim());
               }
            }
         }
      }

      return options;
   }

   /** @deprecated */
   @Deprecated
   public void writeDescriptorBeanAsXML(DescriptorBean root, OutputStream out, String characterEncoding, boolean includeMetadata) throws IOException {
      Descriptor descriptor = root.getDescriptor();
      MarshalOptions options = this.createMarshalOptions(descriptor, characterEncoding);
      if (!includeMetadata) {
         options.setCharacterEncoding((String)null);
         options.setUseDefaultNamespaceForRootElement(false);
         options.setInitialNamespaces((Map)null);
         options.setSchemaLocation((String)null);
         options.setNamespaceContext((NamespaceContext)null);
         options.setRootObj(false);
      }

      if (debug.isDebugEnabled()) {
         debug.debug("writeDescriptorBeanAsXML: bean root: " + root + " out: " + out + " encoding: " + characterEncoding);
      }

      if (!this.editable) {
         try {
            Marshaller marshaller = this.getMarshallerFactory().createMarshaller();
            marshaller.marshal(out, root, options);
            ((DescriptorImpl)descriptor).setModified(false);
         } catch (Exception var15) {
            throw new DescriptorException("Failed to marshal: " + root.getClass().getName(), var15);
         }
      } else {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();

         try {
            if (this.getBindingsClassLoader() != null) {
               Thread.currentThread().setContextClassLoader(this.getBindingsClassLoader());
            }

            Marshaller marshaller = this.getMarshallerFactory().createMarshaller();
            marshaller.marshal(out, root, options);
            ((DescriptorImpl)descriptor).setModified(false);
         } catch (Exception var13) {
            throw new DescriptorException("Failed to marshal: " + root.getClass().getName(), var13);
         } finally {
            Thread.currentThread().setContextClassLoader(cl);
         }
      }

      out.flush();
   }

   public void setDescriptorCreationListener(DescriptorCreationListener listener) {
      this.listener = listener;
   }

   public DescriptorCreationListener getDescriptorCreationListener() {
      return this.listener;
   }

   /** @deprecated */
   @Deprecated
   public DescriptorBean createDescriptorBean(Class ifc) {
      return this.createDescriptorRoot(ifc).getRootBean();
   }

   public void setProductionMode(boolean mode) {
      this.productionMode = mode;
   }

   public void setSchemaLocation(String location) {
      if (!this.editable) {
         throw new AssertionError("Not supported for non-editable descriptors");
      } else {
         this.schemaLocation = location;
      }
   }

   public void addInitialNamespace(String prefix, String uri) {
      if (!this.editable) {
         throw new AssertionError("Not supported for non-editable descriptors");
      } else {
         this.initialNamespaces.put(prefix, uri);
      }
   }

   public void addNamespaceMapping(String oldNamespace, String newNamespace) {
      this.namespaceMapping.put(oldNamespace, newNamespace);
   }

   public SecurityService getSecurityService() {
      return this.securityService;
   }

   public void setDescriptorMacroSubstitutor(DescriptorMacroSubstitutor substitutor) {
      this.macroSubstitutor = substitutor;
   }

   public DescriptorMacroSubstitutor getDescriptorMacroSubstitutor() {
      return this.macroSubstitutor;
   }

   public static void main(String[] args) throws IOException {
      if (args.length > 0) {
         BasicDescriptorManager manager = new BasicDescriptorManager(Thread.currentThread().getContextClassLoader(), false, (SecurityService)null);

         for(int count = 0; count < args.length; ++count) {
            Descriptor desc = manager.createDescriptor((InputStream)(new FileInputStream(new File(args[count]))));
            manager.writeDescriptorAsXML(desc, System.out);
         }
      } else {
         System.out.println("Usage: java weblogic.descriptor.BasicDescriptorManager [descriptor path]*");
      }

   }
}
