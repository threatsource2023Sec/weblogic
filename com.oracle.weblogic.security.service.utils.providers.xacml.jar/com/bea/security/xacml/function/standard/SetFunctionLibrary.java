package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.GenericBag;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.AttributeEvaluatorWrapper;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SetFunctionLibrary extends SimpleFunctionLibraryBase {
   public SetFunctionLibrary() throws URISyntaxException {
      try {
         Collection types = Type.getScalarTypes();
         Iterator var2 = types.iterator();

         while(var2.hasNext()) {
            final Type t = (Type)var2.next();
            final URI inter = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-intersection");
            final URI un = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-union");
            final URI alomo = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-at-least-one-member-of");
            final URI sub = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-subset");
            final URI se = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-set-equals");
            this.register(inter, new SimpleFunctionFactoryBase(new Type(t, true), new Type[]{new Type(t, true), new Type(t, true)}) {
               protected AttributeEvaluator getFunctionImplementation(List arguments) {
                  final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                  final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                  return new AttributeEvaluatorWrapper(new Type(t, true)) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        final Bag p1 = param1.evaluateToBag(context);
                        final Bag p2 = param2.evaluateToBag(context);
                        Bag intersect = new GenericBag(t, new Collection() {
                           private Set inner = new HashSet();
                           private Iterator i1 = p1.iterator();
                           private Iterator i2 = p2.iterator();

                           private void init() {
                              AttributeValue n;
                              if (this.i1 != null) {
                                 while(true) {
                                    if (!this.i1.hasNext()) {
                                       this.i1 = null;
                                       break;
                                    }

                                    n = (AttributeValue)this.i1.next();
                                    if (p2.contains(n)) {
                                       this.inner.add(n);
                                    }
                                 }
                              }

                              if (this.i2 != null) {
                                 while(this.i2.hasNext()) {
                                    n = (AttributeValue)this.i2.next();
                                    if (p1.contains(n)) {
                                       this.inner.add(n);
                                    }
                                 }

                                 this.i2 = null;
                              }

                           }

                           public int size() {
                              this.init();
                              return this.inner.size();
                           }

                           public boolean isEmpty() {
                              if (!this.inner.isEmpty()) {
                                 return false;
                              } else {
                                 AttributeValue n;
                                 if (this.i1 != null) {
                                    while(this.i1.hasNext()) {
                                       n = (AttributeValue)this.i1.next();
                                       if (p2.contains(n)) {
                                          this.inner.add(n);
                                          return false;
                                       }
                                    }

                                    this.i1 = null;
                                 }

                                 if (this.i2 != null) {
                                    while(this.i2.hasNext()) {
                                       n = (AttributeValue)this.i2.next();
                                       if (p1.contains(n)) {
                                          this.inner.add(n);
                                          return false;
                                       }
                                    }

                                    this.i2 = null;
                                 }

                                 return true;
                              }
                           }

                           public boolean contains(Object o) {
                              return this.inner.contains(o) || p1.contains(o) && p2.contains(o);
                           }

                           public Iterator iterator() {
                              this.init();
                              return this.inner.iterator();
                           }

                           public Object[] toArray() {
                              this.init();
                              return this.inner.toArray();
                           }

                           public Object[] toArray(Object[] a) {
                              this.init();
                              return this.inner.toArray(a);
                           }

                           public boolean add(AttributeValue o) {
                              throw new UnsupportedOperationException();
                           }

                           public boolean remove(Object o) {
                              throw new UnsupportedOperationException();
                           }

                           public boolean containsAll(Collection c) {
                              if (c != null) {
                                 Iterator var2 = c.iterator();

                                 while(var2.hasNext()) {
                                    Object o = var2.next();
                                    if (!this.contains(o)) {
                                       return false;
                                    }
                                 }
                              }

                              return true;
                           }

                           public boolean addAll(Collection c) {
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
                        });
                        if (context.isDebugEnabled()) {
                           SetFunctionLibrary.this.debugEval(context, inter, intersect, new Bag[]{p1, p2});
                        }

                        return intersect;
                     }
                  };
               }
            });
            this.register(un, new SimpleFunctionFactoryBase(new Type(t, true), new Type[]{new Type(t, true), new Type(t, true)}) {
               protected AttributeEvaluator getFunctionImplementation(List arguments) {
                  final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                  final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                  return new AttributeEvaluatorWrapper(new Type(t, true)) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        final Bag p1 = param1.evaluateToBag(context);
                        final Bag p2 = param2.evaluateToBag(context);
                        Bag union = new GenericBag(t, new Collection() {
                           private Set inner = new HashSet();
                           private Iterator i1 = p1.iterator();
                           private Iterator i2 = p2.iterator();

                           private void init() {
                              if (this.i1 != null) {
                                 while(true) {
                                    if (!this.i1.hasNext()) {
                                       this.i1 = null;
                                       break;
                                    }

                                    this.inner.add(this.i1.next());
                                 }
                              }

                              if (this.i2 != null) {
                                 while(this.i2.hasNext()) {
                                    this.inner.add(this.i2.next());
                                 }

                                 this.i2 = null;
                              }

                           }

                           public int size() {
                              this.init();
                              return this.inner.size();
                           }

                           public boolean isEmpty() {
                              if (!this.inner.isEmpty()) {
                                 return false;
                              } else {
                                 return (this.i1 == null || !this.i1.hasNext()) && (this.i2 == null || !this.i2.hasNext());
                              }
                           }

                           public boolean contains(Object o) {
                              return this.inner.contains(o) || p1.contains(o) || p2.contains(o);
                           }

                           public Iterator iterator() {
                              this.init();
                              return this.inner.iterator();
                           }

                           public Object[] toArray() {
                              this.init();
                              return this.inner.toArray();
                           }

                           public Object[] toArray(Object[] a) {
                              this.init();
                              return this.inner.toArray(a);
                           }

                           public boolean add(AttributeValue o) {
                              throw new UnsupportedOperationException();
                           }

                           public boolean remove(Object o) {
                              throw new UnsupportedOperationException();
                           }

                           public boolean containsAll(Collection c) {
                              if (c != null) {
                                 Iterator var2 = c.iterator();

                                 while(var2.hasNext()) {
                                    Object o = var2.next();
                                    if (!this.contains(o)) {
                                       return false;
                                    }
                                 }
                              }

                              return true;
                           }

                           public boolean addAll(Collection c) {
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
                        });
                        if (context.isDebugEnabled()) {
                           SetFunctionLibrary.this.debugEval(context, un, union, new Bag[]{p1, p2});
                        }

                        return union;
                     }
                  };
               }
            });
            this.register(alomo, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{new Type(t, true), new Type(t, true)}) {
               protected AttributeEvaluator getFunctionImplementation(List arguments) {
                  final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                  final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                  return new BooleanAttributeEvaluatorBase() {
                     public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        Bag p1 = param1.evaluateToBag(context);
                        Bag p2 = param2.evaluateToBag(context);
                        Iterator var4 = p1.iterator();

                        AttributeValue av;
                        do {
                           if (!var4.hasNext()) {
                              if (context.isDebugEnabled()) {
                                 SetFunctionLibrary.this.debugEval(context, alomo, BooleanAttribute.FALSE, new Bag[]{p1, p2});
                              }

                              return BooleanAttribute.FALSE;
                           }

                           av = (AttributeValue)var4.next();
                        } while(!p2.contains(av));

                        if (context.isDebugEnabled()) {
                           SetFunctionLibrary.this.debugEval(context, alomo, BooleanAttribute.TRUE, new Bag[]{p1, p2});
                        }

                        return BooleanAttribute.TRUE;
                     }
                  };
               }
            });
            this.register(sub, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{new Type(t, true), new Type(t, true)}) {
               protected AttributeEvaluator getFunctionImplementation(List arguments) {
                  final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                  final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                  return new BooleanAttributeEvaluatorBase() {
                     public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        Bag p1 = param1.evaluateToBag(context);
                        Bag p2 = param2.evaluateToBag(context);
                        BooleanAttribute result = p2.containsAll(p1) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           SetFunctionLibrary.this.debugEval(context, sub, result, new Bag[]{p1, p2});
                        }

                        return result;
                     }
                  };
               }
            });
            this.register(se, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{new Type(t, true), new Type(t, true)}) {
               protected AttributeEvaluator getFunctionImplementation(List arguments) {
                  final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                  final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                  return new BooleanAttributeEvaluatorBase() {
                     public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        Bag p1 = param1.evaluateToBag(context);
                        Bag p2 = param2.evaluateToBag(context);
                        BooleanAttribute result = p1.containsAll(p2) && p2.containsAll(p1) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           SetFunctionLibrary.this.debugEval(context, se, result, new Bag[]{p1, p2});
                        }

                        return result;
                     }
                  };
               }
            });
         }

      } catch (java.net.URISyntaxException var9) {
         throw new URISyntaxException(var9);
      }
   }
}
