package com.oracle.weblogic.lifecycle.provisioning.core;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JavaBeanMap extends AbstractMap {
   private final Object bean;
   private final Collection propertyDescriptors;
   private final Map mapKeys = new HashMap();
   private final Map pds = new HashMap();

   public JavaBeanMap(Object bean) throws IntrospectionException {
      this.bean = bean;
      if (bean == null) {
         this.propertyDescriptors = Collections.emptySet();
      } else {
         Collection propertyDescriptors = this.introspect(bean);
         if (propertyDescriptors == null) {
            this.propertyDescriptors = Collections.emptySet();
         } else {
            this.propertyDescriptors = propertyDescriptors;
         }

         this.filter(this.propertyDescriptors);
      }

      Iterator var5 = this.propertyDescriptors.iterator();

      while(var5.hasNext()) {
         PropertyDescriptor pd = (PropertyDescriptor)var5.next();
         if (pd != null) {
            String mapKey = this.getMapKeyFor(pd);
            this.mapKeys.put(pd, mapKey);
            this.pds.put(mapKey, pd);
         }
      }

   }

   protected Collection introspect(Object bean) throws IntrospectionException {
      Object returnValue;
      if (bean == null) {
         returnValue = Collections.emptySet();
      } else {
         BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());

         assert beanInfo != null;

         returnValue = new ArrayList(Arrays.asList(beanInfo.getPropertyDescriptors()));
      }

      return (Collection)returnValue;
   }

   protected void filter(Collection propertyDescriptors) {
   }

   public final int size() {
      return this.propertyDescriptors.size();
   }

   public final boolean isEmpty() {
      return this.propertyDescriptors.isEmpty();
   }

   public final boolean containsKey(Object key) {
      return this.pds.containsKey(key);
   }

   public final Object put(String key, Object value) {
      Object returnValue = null;
      PropertyDescriptor propertyDescriptor = (PropertyDescriptor)this.pds.get(key);
      if (propertyDescriptor == null) {
         throw new IllegalArgumentException(key);
      } else {
         try {
            returnValue = this.setValue(this.bean, propertyDescriptor, value);
            return returnValue;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw new IllegalStateException(var7);
         }
      }
   }

   public final Set entrySet() {
      return new AbstractSet() {
         public final int size() {
            assert JavaBeanMap.this.propertyDescriptors != null;

            return JavaBeanMap.this.propertyDescriptors.size();
         }

         public final Iterator iterator() {
            Iterable pds = JavaBeanMap.this.propertyDescriptors;

            assert pds != null;

            final Iterator iterator = pds.iterator();

            assert iterator != null;

            return new Iterator() {
               private PropertyDescriptor pd;

               public final Map.Entry next() {
                  this.pd = (PropertyDescriptor)iterator.next();

                  assert this.pd != null;

                  return new Map.Entry() {
                     public final String getKey() {
                        return (String)JavaBeanMap.this.mapKeys.get(pd);
                     }

                     public final Object getValue() {
                        Object returnValue = null;

                        try {
                           returnValue = JavaBeanMap.this.getValue(JavaBeanMap.this.bean, pd);
                           return returnValue;
                        } catch (RuntimeException var3) {
                           throw var3;
                        } catch (Exception var4) {
                           throw new IllegalStateException(var4.getMessage(), var4);
                        }
                     }

                     public final Object setValue(Object value) {
                        try {
                           return JavaBeanMap.this.setValue(JavaBeanMap.this.bean, pd, value);
                        } catch (RuntimeException var3) {
                           throw var3;
                        } catch (Exception var4) {
                           throw new IllegalStateException(var4);
                        }
                     }

                     public final boolean equals(Object other) {
                        if (other == this) {
                           return true;
                        } else if (other instanceof Map.Entry) {
                           Map.Entry him = (Map.Entry)other;
                           Object key = this.getKey();
                           if (key == null) {
                              if (him.getKey() != null) {
                                 return false;
                              }
                           } else if (!key.equals(him.getKey())) {
                              return false;
                           }

                           Object value = this.getValue();
                           if (value == null) {
                              if (him.getValue() != null) {
                                 return false;
                              }
                           } else if (!value.equals(him.getValue())) {
                              return false;
                           }

                           return true;
                        } else {
                           return false;
                        }
                     }

                     public final int hashCode() {
                        Object key = this.getKey();
                        Object value = this.getValue();
                        return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
                     }

                     public final String toString() {
                        return this.getKey() + "=" + this.getValue();
                     }
                  };
               }

               public final boolean hasNext() {
                  return iterator.hasNext();
               }

               public final void remove() {
                  try {
                     JavaBeanMap.this.remove(JavaBeanMap.this.bean, this.pd);
                  } catch (RuntimeException var2) {
                     throw var2;
                  } catch (Exception var3) {
                     throw new IllegalStateException(var3.getMessage(), var3);
                  }
               }
            };
         }
      };
   }

   protected String getMapKeyFor(PropertyDescriptor propertyDescriptor) {
      String returnValue;
      if (propertyDescriptor == null) {
         returnValue = null;
      } else {
         returnValue = propertyDescriptor.getName();
      }

      return returnValue;
   }

   protected Object getValue(Object bean, PropertyDescriptor propertyDescriptor) throws Exception {
      Object returnValue;
      if (propertyDescriptor == null) {
         returnValue = null;
      } else {
         Method readMethod = propertyDescriptor.getReadMethod();
         if (readMethod != null && bean != null) {
            returnValue = readMethod.invoke(bean);
         } else {
            returnValue = null;
         }
      }

      return returnValue;
   }

   protected void remove(Object bean, PropertyDescriptor propertyDescriptor) throws Exception {
      throw new UnsupportedOperationException("remove");
   }

   protected Object setValue(Object bean, String unhandledPropertyName, Object value) throws Exception {
      throw new UnsupportedOperationException("setValue");
   }

   protected Object setValue(Object bean, PropertyDescriptor propertyDescriptor, Object value) throws Exception {
      throw new UnsupportedOperationException("setValue");
   }
}
