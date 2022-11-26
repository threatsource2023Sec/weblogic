package org.glassfish.hk2.configuration.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Named;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.utilities.NamedImpl;

public class DelegatingNamedActiveDescriptor implements ActiveDescriptor {
   private final ActiveDescriptor parent;
   private final Named name;
   private final HashSet qualifierNames;
   private final HashSet qualifiers;
   private int ranking = 0;
   private Object lock = new Object();
   private Object cache;
   private boolean isSet = false;

   DelegatingNamedActiveDescriptor(ActiveDescriptor parent, String name) {
      this.parent = parent;
      this.name = new NamedImpl(name);
      this.qualifierNames = new HashSet(parent.getQualifiers());
      this.qualifierNames.add(Named.class.getName());
      this.qualifiers = new HashSet(parent.getQualifierAnnotations());
      this.qualifiers.add(this.name);
   }

   public String getImplementation() {
      return this.parent.getImplementation();
   }

   public Set getAdvertisedContracts() {
      return this.parent.getAdvertisedContracts();
   }

   public String getScope() {
      return this.parent.getScope();
   }

   public String getName() {
      return this.name.value();
   }

   public Set getQualifiers() {
      return this.qualifierNames;
   }

   public DescriptorType getDescriptorType() {
      return this.parent.getDescriptorType();
   }

   public DescriptorVisibility getDescriptorVisibility() {
      return this.parent.getDescriptorVisibility();
   }

   public Map getMetadata() {
      return this.parent.getMetadata();
   }

   public HK2Loader getLoader() {
      return this.parent.getLoader();
   }

   public int getRanking() {
      return this.ranking;
   }

   public int setRanking(int ranking) {
      this.ranking = ranking;
      return ranking;
   }

   public Boolean isProxiable() {
      return this.parent.isProxiable();
   }

   public Boolean isProxyForSameScope() {
      return this.parent.isProxyForSameScope();
   }

   public String getClassAnalysisName() {
      return this.parent.getClassAnalysisName();
   }

   public Long getServiceId() {
      return null;
   }

   public Long getLocatorId() {
      return null;
   }

   public Object getCache() {
      synchronized(this.lock) {
         return this.cache;
      }
   }

   public boolean isCacheSet() {
      synchronized(this.lock) {
         return this.isSet;
      }
   }

   public void setCache(Object cacheMe) {
      synchronized(this.lock) {
         this.isSet = true;
         this.cache = cacheMe;
      }
   }

   public void releaseCache() {
      synchronized(this.lock) {
         this.cache = null;
         this.isSet = false;
      }
   }

   public boolean isReified() {
      return this.parent.isReified();
   }

   public Class getImplementationClass() {
      return this.parent.getImplementationClass();
   }

   public Type getImplementationType() {
      return this.parent.getImplementationType();
   }

   public Set getContractTypes() {
      return this.parent.getContractTypes();
   }

   public Annotation getScopeAsAnnotation() {
      return this.parent.getScopeAsAnnotation();
   }

   public Class getScopeAnnotation() {
      return this.parent.getScopeAnnotation();
   }

   public Set getQualifierAnnotations() {
      return this.qualifiers;
   }

   public List getInjectees() {
      return this.parent.getInjectees();
   }

   public Long getFactoryServiceId() {
      return this.parent.getFactoryServiceId();
   }

   public Long getFactoryLocatorId() {
      return this.parent.getFactoryLocatorId();
   }

   public Object create(ServiceHandle root) {
      return this.parent.create(root);
   }

   public void dispose(Object instance) {
      this.parent.dispose(instance);
   }
}
