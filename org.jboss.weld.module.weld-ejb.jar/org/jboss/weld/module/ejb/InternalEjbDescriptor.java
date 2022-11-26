package org.jboss.weld.module.ejb;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.jboss.weld.annotated.enhanced.jlr.MethodSignatureImpl;
import org.jboss.weld.ejb.spi.BusinessInterfaceDescriptor;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.ejb.spi.SubclassedComponentDescriptor;
import org.jboss.weld.ejb.spi.helpers.ForwardingEjbDescriptor;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

class InternalEjbDescriptor extends ForwardingEjbDescriptor {
   private final Class objectInterface;
   private final EjbDescriptor delegate;
   private final Collection removeMethodSignatures;
   private final Set localBusinessInterfaces;
   private final Set remoteBusinessInterfaces;

   static InternalEjbDescriptor of(EjbDescriptor ejbDescriptor) {
      return ejbDescriptor instanceof InternalEjbDescriptor ? (InternalEjbDescriptor)Reflections.cast(ejbDescriptor) : new InternalEjbDescriptor(ejbDescriptor);
   }

   private InternalEjbDescriptor(EjbDescriptor ejbDescriptor) {
      this.delegate = ejbDescriptor;
      this.objectInterface = findObjectInterface(ejbDescriptor.getLocalBusinessInterfaces());
      this.removeMethodSignatures = new ArrayList();
      if (ejbDescriptor.getRemoveMethods() != null) {
         Iterator var2 = ejbDescriptor.getRemoveMethods().iterator();

         while(var2.hasNext()) {
            Method method = (Method)var2.next();
            this.removeMethodSignatures.add(new MethodSignatureImpl(method));
         }
      }

      this.localBusinessInterfaces = transformToClasses(this.getLocalBusinessInterfaces());
      this.remoteBusinessInterfaces = transformToClasses(this.getRemoteBusinessInterfaces());
   }

   private static Set transformToClasses(Collection interfaceDescriptors) {
      return interfaceDescriptors == null ? Collections.emptySet() : (Set)interfaceDescriptors.stream().map((d) -> {
         return d.getInterface();
      }).collect(ImmutableSet.collector());
   }

   public EjbDescriptor delegate() {
      return this.delegate;
   }

   public Class getObjectInterface() {
      return this.objectInterface;
   }

   public Collection getRemoveMethodSignatures() {
      return this.removeMethodSignatures;
   }

   private static Class findObjectInterface(Collection interfaces) {
      return interfaces != null && !interfaces.isEmpty() ? ((BusinessInterfaceDescriptor)interfaces.iterator().next()).getInterface() : null;
   }

   public Set getLocalBusinessInterfacesAsClasses() {
      return this.localBusinessInterfaces;
   }

   public Set getRemoteBusinessInterfacesAsClasses() {
      return this.remoteBusinessInterfaces;
   }

   public Class getImplementationClass() {
      if (this.delegate instanceof SubclassedComponentDescriptor) {
         SubclassedComponentDescriptor descriptor = (SubclassedComponentDescriptor)Reflections.cast(this.delegate);
         Class implementationClass = descriptor.getComponentSubclass();
         if (implementationClass != null) {
            return implementationClass;
         }
      }

      return this.delegate.getBeanClass();
   }
}
