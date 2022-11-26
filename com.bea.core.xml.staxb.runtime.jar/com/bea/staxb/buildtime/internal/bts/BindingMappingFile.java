package com.bea.staxb.buildtime.internal.bts;

import com.bea.xml.XmlRuntimeException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BindingMappingFile extends BindingFile implements Serializable {
   private transient BindingFile delegate;
   private transient URL delegateURL;
   private final Map namespaces = new HashMap();
   private static final long serialVersionUID = 1L;
   private static final boolean VERBOSE = false;
   private transient Object loadLock = new Object();
   private long lastAccess = 0L;

   public BindingMappingFile() {
   }

   public BindingMappingFile(BindingFile loader) {
      this.delegate = loader;
      Iterator i = loader.bindingTypes().iterator();

      while(i.hasNext()) {
         BindingType bType = (BindingType)i.next();
         BindingTypeName bName = bType.getName();
         if (bName != null) {
            this.addXmlTypeNameToMap(bName.getXmlName());
         }
      }

      i = loader.pojoMappedXmlTypes().iterator();

      while(i.hasNext()) {
         this.addXmlTypeNameToMap((XmlTypeName)i.next());
      }

      i = loader.xmlObjectMappedXmlTypes().iterator();

      while(i.hasNext()) {
         this.addXmlTypeNameToMap((XmlTypeName)i.next());
      }

   }

   public void setDelegateBindingLoaderURL(URL bindingURL) {
      this.delegateURL = bindingURL;
   }

   public void addBindingType(BindingType bType, boolean fromJavaDefault, boolean fromXmlDefault) {
      if (this.delegateURL != null) {
         BindingFile localDelegate = this.getDelegate();
         localDelegate.addBindingType(bType, fromJavaDefault, fromXmlDefault);
      }
   }

   public BindingType getBindingType(BindingTypeName btName) {
      XmlTypeName xtName = btName.getXmlName();
      if (this.namespaces.containsKey(xtName.getNamespace()) && this.delegateURL != null) {
         BindingFile localDelegate = this.getDelegate();
         return localDelegate.getBindingType(btName);
      } else {
         return null;
      }
   }

   public BindingTypeName lookupPojoFor(XmlTypeName xName) {
      if (this.namespaces.containsKey(xName.getNamespace()) && this.delegateURL != null) {
         BindingFile localDelegate = this.getDelegate();
         return localDelegate.lookupPojoFor(xName);
      } else {
         return null;
      }
   }

   public BindingTypeName lookupXmlObjectFor(XmlTypeName xName) {
      if (this.namespaces.containsKey(xName.getNamespace()) && this.delegateURL != null) {
         BindingFile localDelegate = this.getDelegate();
         return localDelegate.lookupXmlObjectFor(xName);
      } else {
         return null;
      }
   }

   public BindingTypeName lookupTypeFor(JavaTypeName jName) {
      if (this.delegateURL == null) {
         return null;
      } else {
         BindingFile localDelegate = this.getDelegate();
         return localDelegate.lookupTypeFor(jName);
      }
   }

   public BindingTypeName lookupElementFor(JavaTypeName jName) {
      if (this.delegateURL == null) {
         return null;
      } else {
         BindingFile localDelegate = this.getDelegate();
         return localDelegate.lookupElementFor(jName);
      }
   }

   public static BindingFile forSer(InputStream ser) throws IOException, ClassNotFoundException {
      ObjectInputStream ois = new ObjectInputStream(ser);
      Object obj = ois.readObject();
      BindingMappingFile bmf = (BindingMappingFile)obj;
      bmf.loadLock = new Object();
      ois.close();
      return bmf;
   }

   public long getLastAccess() {
      return this.lastAccess;
   }

   private BindingFile getDelegate() {
      BindingFile localDelegate = this.delegate;
      if (localDelegate == null) {
         localDelegate = this.loadDelegate();
      }

      this.lastAccess = System.currentTimeMillis();
      return localDelegate;
   }

   private BindingFile loadDelegate() {
      synchronized(this.loadLock) {
         long start = System.currentTimeMillis();
         InputStream in = null;

         try {
            in = this.delegateURL.openStream();
            this.delegate = BindingFile.forSer(in);
         } catch (IOException var16) {
            throw new XmlRuntimeException(var16);
         } catch (ClassNotFoundException var17) {
            throw new XmlRuntimeException(var17);
         } finally {
            try {
               if (in != null) {
                  in.close();
               }
            } catch (IOException var15) {
            }

         }

         return this.delegate;
      }
   }

   public void releaseDelegate() {
      synchronized(this.loadLock) {
         this.delegate = null;
      }
   }

   private void addXmlTypeNameToMap(XmlTypeName xName) {
      if (xName != null) {
         String namespace = xName.getNamespace();
         if (namespace != null) {
            this.namespaces.put(namespace, (Object)null);
         }
      }

   }
}
