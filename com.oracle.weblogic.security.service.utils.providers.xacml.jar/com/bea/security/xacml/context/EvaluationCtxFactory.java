package com.bea.security.xacml.context;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.DateAttribute;
import com.bea.common.security.xacml.attr.DateTimeAttribute;
import com.bea.common.security.xacml.attr.GenericBag;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.attr.TimeAttribute;
import com.bea.common.security.xacml.context.Attribute;
import com.bea.common.security.xacml.context.AttributeValue;
import com.bea.common.security.xacml.context.Request;
import com.bea.common.security.xacml.context.Resource;
import com.bea.common.security.xacml.context.Subject;
import com.bea.security.providers.xacml.BasicEvaluationCtx;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.AttributeEvaluatorWrapper;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.AttributeEvaluatableFactory;
import com.bea.security.xacml.attr.SubjectAttributeEvaluatableFactory;
import com.bea.security.xacml.policy.VariableContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.security.providers.utils.ResourceUtils;

public class EvaluationCtxFactory {
   public static final String SUBJECTCATEGORY_ACCESSSSUBJECT = "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
   private static final String RESOURCE_ATTR = "urn:oasis:names:tc:xacml:2.0:resource:resource-id";
   public static final String TIME_ATTR = "urn:oasis:names:tc:xacml:1.0:environment:current-time";
   public static final String DATE_ATTR = "urn:oasis:names:tc:xacml:1.0:environment:current-date";
   public static final String DATETIME_ATTR = "urn:oasis:names:tc:xacml:1.0:environment:current-dateTime";
   private final URI TIME_ATTR_URI;
   private final URI DATE_ATTR_URI;
   private final URI DATETIME_ATTR_URI;
   private final URI TIME_TYPE;
   private final URI DATE_TYPE;
   private final URI DATETIME_TYPE;
   private final URI SUBJECTCATEGORY_ACCESSSUBJECT_URI;
   private final URI RESOURCE_ATTR_URI;
   private final URI STRING_TYPE;
   private LoggerSpi log;

   public EvaluationCtxFactory(LoggerSpi log) throws URISyntaxException {
      try {
         this.SUBJECTCATEGORY_ACCESSSUBJECT_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject-category:access-subject");
         this.RESOURCE_ATTR_URI = new URI("urn:oasis:names:tc:xacml:2.0:resource:resource-id");
         this.TIME_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:environment:current-time");
         this.DATE_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:environment:current-date");
         this.DATETIME_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:environment:current-dateTime");
      } catch (java.net.URISyntaxException var3) {
         throw new URISyntaxException(var3);
      }

      this.TIME_TYPE = Type.TIME.getDataType();
      this.DATE_TYPE = Type.DATE.getDataType();
      this.DATETIME_TYPE = Type.DATE_TIME.getDataType();
      this.STRING_TYPE = Type.STRING.getDataType();
      this.log = log;
   }

   public com.bea.security.xacml.EvaluationCtx create(Request request) {
      return new EvaluationCtx(request);
   }

   private static class CombinedCollection implements Collection {
      private Collection inner;

      public CombinedCollection(Collection lists) {
         this.inner = lists;
      }

      public int size() {
         int size = 0;

         Collection l;
         for(Iterator var2 = this.inner.iterator(); var2.hasNext(); size += l.size()) {
            l = (Collection)var2.next();
         }

         return size;
      }

      public boolean isEmpty() {
         Iterator var1 = this.inner.iterator();

         Collection l;
         do {
            if (!var1.hasNext()) {
               return true;
            }

            l = (Collection)var1.next();
         } while(l.isEmpty());

         return false;
      }

      public boolean contains(Object o) {
         Iterator var2 = this.inner.iterator();

         Collection l;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            l = (Collection)var2.next();
         } while(!l.contains(o));

         return true;
      }

      public Iterator iterator() {
         return new Iterator() {
            private Iterator outerIt;
            private Iterator innerIt;

            {
               this.outerIt = CombinedCollection.this.inner.iterator();
               this.innerIt = null;
            }

            public boolean hasNext() {
               if (this.innerIt != null && this.innerIt.hasNext()) {
                  return true;
               } else {
                  while(this.outerIt.hasNext()) {
                     Collection c = (Collection)this.outerIt.next();
                     if (c != null) {
                        this.innerIt = c.iterator();
                        if (this.innerIt.hasNext()) {
                           return true;
                        }
                     }
                  }

                  return false;
               }
            }

            public Object next() {
               if (this.hasNext()) {
                  return this.innerIt.next();
               } else {
                  throw new NoSuchElementException();
               }
            }

            public void remove() {
               throw new UnsupportedOperationException();
            }
         };
      }

      public Object[] toArray() {
         List out = new ArrayList();
         Iterator var2 = this.inner.iterator();

         while(var2.hasNext()) {
            Collection l = (Collection)var2.next();
            out.addAll(l);
         }

         return out.toArray();
      }

      public Object[] toArray(Object[] a) {
         List out = new ArrayList();
         Iterator var3 = this.inner.iterator();

         while(var3.hasNext()) {
            Collection l = (Collection)var3.next();
            out.addAll(l);
         }

         return out.toArray(a);
      }

      public boolean add(Object o) {
         throw new UnsupportedOperationException();
      }

      public boolean remove(Object o) {
         throw new UnsupportedOperationException();
      }

      public boolean containsAll(Collection c) {
         return false;
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(int index, Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }
   }

   private static class ContextAttributeEvaluatableFactory implements AttributeEvaluatableFactory {
      private Collection attributes;

      public ContextAttributeEvaluatableFactory(Collection attributes) {
         this.attributes = attributes;
      }

      public AttributeEvaluator getEvaluatable(URI id, URI type) {
         return this.getEvaluatable(id, type, (String)null);
      }

      public AttributeEvaluator getEvaluatable(final URI id, final URI type, final String issuer) {
         return new AttributeEvaluatorWrapper(type) {
            public Bag evaluateToBag(com.bea.security.xacml.EvaluationCtx context) throws IndeterminateEvaluationException {
               try {
                  Bag values = new GenericBag(this.getType());
                  Iterator var3 = ContextAttributeEvaluatableFactory.this.attributes.iterator();

                  while(true) {
                     List avs;
                     do {
                        Attribute a;
                        do {
                           do {
                              do {
                                 if (!var3.hasNext()) {
                                    return values;
                                 }

                                 a = (Attribute)var3.next();
                              } while(!id.equals(a.getAttributeId()));
                           } while(!type.equals(a.getDataType()));
                        } while(issuer != null && !issuer.equals(a.getIssuer()));

                        avs = a.getAttributeValues();
                     } while(avs == null);

                     Iterator var6 = avs.iterator();

                     while(var6.hasNext()) {
                        AttributeValue av = (AttributeValue)var6.next();
                        values.add(av.getValue());
                     }
                  }
               } catch (URISyntaxException var8) {
                  throw new IndeterminateEvaluationException(var8);
               }
            }
         };
      }
   }

   private class EvaluationCtx implements BasicEvaluationCtx, LoggerSpi {
      private Request request;
      private VariableContext vc;
      private long evaluationBeginTime;
      private Bag time;
      private Bag date;
      private Bag dateTime;
      private boolean isDebugEnabled;
      private ConcurrentHashMap ed = new ConcurrentHashMap();

      public EvaluationCtx(Request request) {
         this.request = request;
         this.vc = null;
         this.isDebugEnabled = EvaluationCtxFactory.this.log.isDebugEnabled();
         this.evaluationBeginTime = System.currentTimeMillis();
      }

      public SubjectAttributeEvaluatableFactory getSubjectAttributes() {
         final List subs = this.request.getSubjects();
         return new SubjectAttributeEvaluatableFactory() {
            public AttributeEvaluator getEvaluatable(URI id, URI type) {
               return this.getEvaluatable(id, type, (String)null, (URI)null);
            }

            public AttributeEvaluator getEvaluatable(URI id, URI type, String issuer) {
               return this.getEvaluatable(id, type, issuer, (URI)null);
            }

            public AttributeEvaluator getEvaluatable(URI id, URI type, URI category) {
               return this.getEvaluatable(id, type, (String)null, category);
            }

            public AttributeEvaluator getEvaluatable(final URI id, final URI type, final String issuer, final URI category) {
               return new AttributeEvaluatorWrapper(type) {
                  public Bag evaluateToBag(com.bea.security.xacml.EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        Bag values = new GenericBag(this.getType());
                        if (context.isDebugEnabled()) {
                           context.debug("Looking for subject attribute...  Id: " + id + " Type: " + type + " SC: " + category + " Issuer: " + issuer);
                        }

                        Iterator var3 = subs.iterator();

                        label81:
                        while(true) {
                           Subject s;
                           URI sc;
                           do {
                              if (!var3.hasNext()) {
                                 return values;
                              }

                              s = (Subject)var3.next();
                              sc = s.getSubjectCategory();
                              if ((category == null || EvaluationCtxFactory.this.SUBJECTCATEGORY_ACCESSSUBJECT_URI.equals(category)) && (sc == null || EvaluationCtxFactory.this.SUBJECTCATEGORY_ACCESSSUBJECT_URI.equals(sc))) {
                                 break;
                              }
                           } while(category == null || !category.equals(sc));

                           Iterator var6 = s.getAttributes().iterator();

                           while(true) {
                              Attribute a;
                              List avs;
                              do {
                                 do {
                                    do {
                                       do {
                                          if (!var6.hasNext()) {
                                             continue label81;
                                          }

                                          a = (Attribute)var6.next();
                                       } while(!id.equals(a.getAttributeId()));
                                    } while(!type.equals(a.getDataType()));
                                 } while(issuer != null && !issuer.equals(a.getIssuer()));

                                 avs = a.getAttributeValues();
                              } while(avs == null);

                              com.bea.common.security.xacml.attr.AttributeValue v;
                              for(Iterator var9 = avs.iterator(); var9.hasNext(); values.add(v)) {
                                 AttributeValue av = (AttributeValue)var9.next();
                                 v = av.getValue();
                                 if (context.isDebugEnabled()) {
                                    context.debug("Found attribute...  Id: " + a.getAttributeId() + " Type: " + a.getDataType() + " SC: " + s.getSubjectCategory() + " Issuer: " + a.getIssuer());
                                 }
                              }
                           }
                        }
                     } catch (URISyntaxException var12) {
                        throw new IndeterminateEvaluationException(var12);
                     }
                  }
               };
            }
         };
      }

      public AttributeEvaluatableFactory getResourceAttributes() {
         Collection c = new ArrayList();
         List res = this.request.getResources();
         Iterator var3 = res.iterator();

         while(var3.hasNext()) {
            Resource r = (Resource)var3.next();
            c.add(r.getAttributes());
         }

         return new ContextAttributeEvaluatableFactory(new CombinedCollection(c));
      }

      public AttributeEvaluatableFactory getActionAttributes() {
         return new ContextAttributeEvaluatableFactory(this.request.getAction().getAttributes());
      }

      public AttributeEvaluatableFactory getEnvironmentAttributes() {
         return new ContextAttributeEvaluatableFactory(this.request.getEnvironment().getAttributes());
      }

      public Bag getCurrentTime() {
         if (this.time == null) {
            List attributes = this.request.getEnvironment().getAttributes();
            Bag values = new GenericBag(Type.TIME);
            if (attributes != null) {
               Iterator var3 = attributes.iterator();

               label42:
               while(true) {
                  List avs;
                  do {
                     Attribute a;
                     do {
                        do {
                           if (!var3.hasNext()) {
                              break label42;
                           }

                           a = (Attribute)var3.next();
                        } while(!EvaluationCtxFactory.this.TIME_ATTR_URI.equals(a.getAttributeId()));
                     } while(!EvaluationCtxFactory.this.TIME_TYPE.equals(a.getDataType()));

                     avs = a.getAttributeValues();
                  } while(avs == null);

                  Iterator var6 = avs.iterator();

                  while(var6.hasNext()) {
                     AttributeValue av = (AttributeValue)var6.next();
                     values.add(av.getValue());
                  }
               }
            }

            int size = values.size();
            if (size > 0) {
               this.time = values;
            } else {
               AttributeEvaluatableFactory aef = this.getEnvironmentAttributes();
               aef.getEvaluatable(EvaluationCtxFactory.this.TIME_ATTR_URI, EvaluationCtxFactory.this.TIME_TYPE);
               Calendar cal = Calendar.getInstance();
               cal.setTimeInMillis(this.evaluationBeginTime);
               this.time = new TimeAttribute(cal);
            }
         }

         return this.time;
      }

      public Bag getCurrentDate() {
         if (this.date == null) {
            List attributes = this.request.getEnvironment().getAttributes();
            Bag values = new GenericBag(Type.DATE);
            if (attributes != null) {
               Iterator var3 = attributes.iterator();

               label42:
               while(true) {
                  List avs;
                  do {
                     Attribute a;
                     do {
                        do {
                           if (!var3.hasNext()) {
                              break label42;
                           }

                           a = (Attribute)var3.next();
                        } while(!EvaluationCtxFactory.this.DATE_ATTR_URI.equals(a.getAttributeId()));
                     } while(!EvaluationCtxFactory.this.DATE_TYPE.equals(a.getDataType()));

                     avs = a.getAttributeValues();
                  } while(avs == null);

                  Iterator var6 = avs.iterator();

                  while(var6.hasNext()) {
                     AttributeValue av = (AttributeValue)var6.next();
                     values.add(av.getValue());
                  }
               }
            }

            int size = values.size();
            if (size > 0) {
               this.date = values;
            } else {
               Calendar cal = Calendar.getInstance();
               cal.setTimeInMillis(this.evaluationBeginTime);
               this.date = new DateAttribute(cal);
            }
         }

         return this.date;
      }

      public Bag getCurrentDateTime() {
         if (this.dateTime == null) {
            List attributes = this.request.getEnvironment().getAttributes();
            Bag values = new GenericBag(Type.DATE_TIME);
            if (attributes != null) {
               Iterator var3 = attributes.iterator();

               label42:
               while(true) {
                  List avs;
                  do {
                     Attribute a;
                     do {
                        do {
                           if (!var3.hasNext()) {
                              break label42;
                           }

                           a = (Attribute)var3.next();
                        } while(!EvaluationCtxFactory.this.DATETIME_ATTR_URI.equals(a.getAttributeId()));
                     } while(!EvaluationCtxFactory.this.DATETIME_TYPE.equals(a.getDataType()));

                     avs = a.getAttributeValues();
                  } while(avs == null);

                  Iterator var6 = avs.iterator();

                  while(var6.hasNext()) {
                     AttributeValue av = (AttributeValue)var6.next();
                     values.add(av.getValue());
                  }
               }
            }

            int size = values.size();
            if (size > 0) {
               this.dateTime = values;
            } else {
               Calendar cal = Calendar.getInstance();
               cal.setTimeInMillis(this.evaluationBeginTime);
               this.dateTime = new DateTimeAttribute(cal);
            }
         }

         return this.dateTime;
      }

      public VariableContext getVariableContext() {
         return this.vc;
      }

      public void setVariableContext(VariableContext vc) {
         this.vc = vc;
      }

      public Map getEvaluationData(Object evaluator) {
         ConcurrentHashMap evalMap = (ConcurrentHashMap)this.ed.get(evaluator);
         if (evalMap == null) {
            evalMap = new ConcurrentHashMap();
            this.ed.put(evaluator, evalMap);
         }

         return evalMap;
      }

      public boolean isDebugEnabled() {
         return this.isDebugEnabled;
      }

      public void debug(Object msg) {
         EvaluationCtxFactory.this.log.debug(msg);
      }

      public void debug(Object msg, Throwable th) {
         EvaluationCtxFactory.this.log.debug(msg, th);
      }

      public void info(Object msg) {
         EvaluationCtxFactory.this.log.info(msg);
      }

      public void info(Object msg, Throwable th) {
         EvaluationCtxFactory.this.log.info(msg, th);
      }

      public void warn(Object msg) {
         EvaluationCtxFactory.this.log.warn(msg);
      }

      public void warn(Object msg, Throwable th) {
         EvaluationCtxFactory.this.log.warn(msg, th);
      }

      public void error(Object msg) {
         EvaluationCtxFactory.this.log.error(msg);
      }

      public void error(Object msg, Throwable th) {
         EvaluationCtxFactory.this.log.error(msg, th);
      }

      public void severe(Object msg) {
         EvaluationCtxFactory.this.log.severe(msg);
      }

      public void severe(Object msg, Throwable th) {
         EvaluationCtxFactory.this.log.severe(msg, th);
      }

      public weblogic.security.spi.Resource getResource() {
         AttributeEvaluator ae = this.getResourceAttributes().getEvaluatable(EvaluationCtxFactory.this.RESOURCE_ATTR_URI, EvaluationCtxFactory.this.STRING_TYPE);
         if (ae != null && ae instanceof Bag) {
            Bag b = (Bag)ae;
            if (b.size() > 0) {
               return ResourceUtils.getScopedResource(((StringAttribute)b.iterator().next()).getValue());
            }
         }

         return null;
      }
   }
}
