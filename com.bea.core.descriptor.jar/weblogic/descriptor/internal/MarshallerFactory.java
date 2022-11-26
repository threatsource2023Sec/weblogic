package weblogic.descriptor.internal;

import com.bea.staxb.runtime.BindingContext;
import com.bea.staxb.runtime.BindingContextFactory;
import com.bea.staxb.runtime.Marshaller;
import com.bea.staxb.runtime.ObjectFactory;
import com.bea.staxb.runtime.UnmarshalOptions;
import com.bea.staxb.runtime.Unmarshaller;
import com.bea.xml.XmlException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.descriptor.BeanCreationInterceptor;
import weblogic.descriptor.CheckValidationDescriptorFactory;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorException;
import weblogic.utils.Debug;

public class MarshallerFactory {
   BindingContext bc;
   ClassLoader bindingsClassloader;
   private static final boolean verbose = Debug.getCategory("weblogic.descriptor.verboseValidationEnabled").isEnabled();
   private static final boolean validationDisabled = Debug.getCategory("weblogic.descriptor.validationDisabled").isEnabled();

   public MarshallerFactory(ClassLoader bindingsClassloader) throws IOException {
      try {
         this.bindingsClassloader = bindingsClassloader;
         this.bc = BindingContextFactory.newInstance().createBindingContext(bindingsClassloader);
      } catch (XmlException var3) {
         throw (IOException)(new IOException()).initCause(var3);
      }
   }

   private void setCharacterEncoding(DescriptorBean root, String encoding) {
      if (encoding != null) {
         ((DescriptorImpl)root.getDescriptor()).setCharacterEncoding(encoding);
      }

   }

   private weblogic.descriptor.DescriptorFactory getDefaultFactory(List holder) {
      return this.getDefaultFactory(holder, true, (Map)null);
   }

   private weblogic.descriptor.DescriptorFactory getDefaultFactory(final List holder, boolean validate, final Map namespaceMapping) {
      return new CheckValidationDescriptorFactory() {
         boolean errorHolderProvided = holder != null;
         List errorHolder;

         {
            this.errorHolder = (List)(this.errorHolderProvided ? holder : new ArrayList());
         }

         public Object createDescriptor(InputStream document, ObjectFactory factory) throws IOException {
            return this.createDescriptor(document, factory, true);
         }

         public Object createDescriptor(InputStream document, ObjectFactory factory, boolean validate) throws IOException {
            try {
               if (document == null) {
                  throw new IllegalArgumentException("null inputStream");
               } else {
                  XMLInputFactory rFactory = XMLInputFactory.newInstance();
                  rFactory.setProperty("javax.xml.stream.supportDTD", Boolean.FALSE);
                  XMLStreamReader reader = rFactory.createXMLStreamReader(document);
                  List errors = new ArrayList() {
                     public boolean add(Object o) {
                        return errorHolder.add(o);
                     }
                  };
                  UnmarshalOptions uopts = new UnmarshalOptions(errors);
                  uopts.setValidation(validate);
                  uopts.setAttributeValidationCompatMode(true);
                  if (factory != null) {
                     uopts.setInitialObjectFactory(factory);
                  }

                  if (namespaceMapping != null) {
                     uopts.setNamespaceMapping(namespaceMapping);
                  }

                  DescriptorBean root = (DescriptorBean)MarshallerFactory.this.createUnmarshaller().unmarshal(reader, uopts);
                  MarshallerFactory.this.setCharacterEncoding(root, reader.getCharacterEncodingScheme());
                  return root;
               }
            } catch (XmlException var9) {
               throw new DescriptorException("Unmarshaller failed: " + var9, var9);
            } catch (XMLStreamException var10) {
               throw new DescriptorException("Unmarshaller failed: " + var10, var10);
            }
         }

         public Object createDescriptor(XMLStreamReader reader, ObjectFactory factory) throws IOException {
            return this.createDescriptor(reader, factory, true);
         }

         public Object createDescriptor(XMLStreamReader reader, ObjectFactory factory, boolean validate) throws IOException {
            try {
               List errors = new ArrayList() {
                  public boolean add(Object o) {
                     return errorHolder.add(o);
                  }
               };
               UnmarshalOptions uopts = new UnmarshalOptions(errors);
               uopts.setValidation(validate);
               uopts.setAttributeValidationCompatMode(true);
               if (factory != null) {
                  uopts.setInitialObjectFactory(factory);
               }

               if (namespaceMapping != null) {
                  uopts.setNamespaceMapping(namespaceMapping);
               }

               DescriptorBean root = (DescriptorBean)MarshallerFactory.this.createUnmarshaller().unmarshal(reader, uopts);
               MarshallerFactory.this.setCharacterEncoding(root, reader.getCharacterEncodingScheme());
               if (validate && !this.errorHolderProvided) {
                  this.evaluateResults(this.errorHolder, reader);
               }

               return root;
            } catch (XmlException var7) {
               throw new DescriptorException("Unmarshaller failed", var7);
            }
         }

         void evaluateResults(List eHolder, XMLStreamReader reader) throws DescriptorException {
            if (!eHolder.isEmpty()) {
               XMLStreamReader xmlReader = reader;
               if (reader instanceof XMLStreamReaderDelegate) {
                  BeanCreationInterceptor delegate = ((XMLStreamReaderDelegate)reader).getDelegate();
                  if (delegate instanceof Munger && delegate instanceof XMLStreamReader) {
                     xmlReader = (XMLStreamReader)delegate;
                  }
               }

               if (xmlReader instanceof Munger) {
                  Munger munger = (Munger)xmlReader;
                  if (munger.usingDTD() && !munger.supportsValidation()) {
                     if (MarshallerFactory.verbose && !eHolder.isEmpty()) {
                        Debug.say((new DescriptorException("VALIDATION PROBLEMS WERE FOUND", eHolder)).toString());
                     }

                     return;
                  }

                  if (MarshallerFactory.validationDisabled) {
                     if (!eHolder.isEmpty()) {
                        munger.logError(eHolder);
                     }

                     return;
                  }

                  if (!munger.isValidationEnabled()) {
                     if (!eHolder.isEmpty()) {
                        munger.logError(eHolder);
                     }

                     return;
                  }

                  this.evaluateResults(eHolder);
               }

               if (!MarshallerFactory.validationDisabled) {
                  this.evaluateResults(eHolder);
               }

            }
         }

         void evaluateResults(List eHolder) throws DescriptorException {
            if (!eHolder.isEmpty()) {
               if (MarshallerFactory.verbose) {
                  Debug.say((new DescriptorException("VALIDATION PROBLEMS WERE FOUND", eHolder)).toString());
               }

               throw new DescriptorException("VALIDATION PROBLEMS WERE FOUND", eHolder);
            }
         }
      };
   }

   public weblogic.descriptor.DescriptorFactory getDescriptorFactory(InputStream document, List holder) throws IOException {
      return this.getDefaultFactory(holder);
   }

   public weblogic.descriptor.DescriptorFactory getDescriptorFactory(InputStream document, List holder, Map namespaceMapping) throws IOException {
      return this.getDefaultFactory(holder, true, namespaceMapping);
   }

   public weblogic.descriptor.DescriptorFactory getDescriptorFactory(XMLStreamReader document, List holder) throws IOException {
      return this.getDefaultFactory(holder);
   }

   public weblogic.descriptor.DescriptorFactory getDescriptorFactory(XMLStreamReader document, List holder, Map namespaceMapping) throws IOException {
      return this.getDefaultFactory(holder, true, namespaceMapping);
   }

   public weblogic.descriptor.DescriptorFactory getDescriptorFactory(InputStream document) throws IOException {
      return this.getDefaultFactory((List)null);
   }

   public weblogic.descriptor.DescriptorFactory getDescriptorFactory(XMLStreamReader document) throws IOException {
      return this.getDefaultFactory((List)null);
   }

   public Marshaller createMarshaller() throws IOException {
      try {
         return this.bc.createMarshaller();
      } catch (XmlException var2) {
         throw new DescriptorException("Could not create marshaller", var2);
      }
   }

   public Unmarshaller createUnmarshaller() throws IOException {
      try {
         return this.bc.createUnmarshaller();
      } catch (XmlException var2) {
         throw new DescriptorException("Could not create unmarshaller", var2);
      }
   }
}
