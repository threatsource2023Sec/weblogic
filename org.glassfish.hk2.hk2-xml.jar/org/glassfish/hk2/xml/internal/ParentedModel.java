package org.glassfish.hk2.xml.internal;

import java.io.Serializable;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.glassfish.hk2.utilities.general.GeneralUtilities;

public class ParentedModel implements Serializable {
   private static final long serialVersionUID = -2480798409414987937L;
   private final Object lock = new Object();
   private String childInterface;
   private String childXmlNamespace;
   private String childXmlTag;
   private String childXmlWrapperTag;
   private String childXmlAlias;
   private ChildType childType;
   private String givenDefault;
   private AliasType aliased;
   private String adapterClassName;
   private boolean required;
   private String originalMethodName;
   private ClassLoader myLoader;
   private transient JAUtilities jaUtilities;
   private ModelImpl childModel;

   public ParentedModel() {
   }

   public ParentedModel(String childInterface, String childXmlNamespace, String childXmlTag, String childXmlAlias, ChildType childType, String givenDefault, AliasType aliased, String childXmlWrapperTag, String adapterClassName, boolean required, String originalMethodName) {
      this.childInterface = childInterface;
      this.childXmlNamespace = childXmlNamespace;
      this.childXmlTag = childXmlTag;
      this.childXmlAlias = childXmlAlias;
      this.childType = childType;
      this.givenDefault = givenDefault;
      this.aliased = aliased;
      this.childXmlWrapperTag = childXmlWrapperTag;
      this.adapterClassName = adapterClassName;
      this.required = required;
      this.originalMethodName = originalMethodName;
   }

   public String getChildInterface() {
      return this.childInterface;
   }

   public String getChildXmlNamespace() {
      return this.childXmlNamespace;
   }

   public String getChildXmlTag() {
      return this.childXmlTag;
   }

   public String getChildXmlAlias() {
      return this.childXmlAlias;
   }

   public ChildType getChildType() {
      return this.childType;
   }

   public String getGivenDefault() {
      return this.givenDefault;
   }

   public String getXmlWrapperTag() {
      return this.childXmlWrapperTag;
   }

   public String getAdapter() {
      return this.adapterClassName;
   }

   public boolean isRequired() {
      return this.required;
   }

   public String getOriginalMethodName() {
      return this.originalMethodName;
   }

   public XmlAdapter getAdapterObject() {
      synchronized(this.lock) {
         if (this.myLoader == null) {
            throw new IllegalStateException("Cannot call getChildModel before the classloader has been determined");
         } else if (this.adapterClassName == null) {
            return null;
         } else {
            Class adapterClass = GeneralUtilities.loadClass(this.myLoader, this.adapterClassName);
            if (adapterClass == null) {
               throw new IllegalStateException("Adapter " + adapterClass + " could not be loaded by " + this.myLoader);
            } else {
               XmlAdapter var10000;
               try {
                  XmlAdapter xa = (XmlAdapter)adapterClass.newInstance();
                  var10000 = xa;
               } catch (RuntimeException var5) {
                  throw var5;
               } catch (Exception var6) {
                  throw new RuntimeException(var6);
               }

               return var10000;
            }
         }
      }
   }

   public ModelImpl getChildModel() {
      synchronized(this.lock) {
         if (this.myLoader == null) {
            throw new IllegalStateException("Cannot call getChildModel before the classloader has been determined");
         } else if (this.childModel != null) {
            return this.childModel;
         } else {
            Class beanClass = GeneralUtilities.loadClass(this.myLoader, this.childInterface);
            if (beanClass == null) {
               throw new IllegalStateException("Interface " + this.childInterface + " could not be loaded by " + this.myLoader);
            } else {
               try {
                  this.childModel = this.jaUtilities.getModel(beanClass);
               } catch (RuntimeException var5) {
                  throw new RuntimeException("Could not get model for " + beanClass.getName() + " in " + this, var5);
               }

               return this.childModel;
            }
         }
      }
   }

   public void setRuntimeInformation(JAUtilities jaUtilities, ClassLoader myLoader) {
      synchronized(this.lock) {
         this.jaUtilities = jaUtilities;
         this.myLoader = myLoader;
      }
   }

   public AliasType getAliasType() {
      return this.aliased;
   }

   public String toString() {
      return "ParentedModel(interface=" + this.childInterface + ",xmlNamespace=" + this.childXmlNamespace + ",xmlTag=" + this.childXmlTag + ",xmlAlias=" + this.childXmlAlias + ",xmlWrapperTag=" + this.childXmlWrapperTag + ",type=" + this.childType + ",givenDefault=" + Utilities.safeString(this.givenDefault) + ",aliased=" + this.aliased + ",adapter=" + this.adapterClassName + ",originalMethodName=" + this.originalMethodName + ")";
   }
}
