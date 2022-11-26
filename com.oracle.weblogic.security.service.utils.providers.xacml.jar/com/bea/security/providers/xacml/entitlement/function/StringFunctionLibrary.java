package com.bea.security.providers.xacml.entitlement.function;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AnyURIAttribute;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.CharacterAttribute;
import com.bea.common.security.xacml.attr.IntegerAttribute;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.attr.StringAttributeBag;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.AnyURIAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.CharacterAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.IntegerAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.StringAttributeEvaluatorBase;
import com.bea.security.xacml.function.MultipleArgumentType;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.Iterator;
import java.util.List;

public class StringFunctionLibrary extends SimpleFunctionLibraryBase {
   public StringFunctionLibrary() throws URISyntaxException {
      try {
         final URI sca = new URI("urn:bea:xacml:2.0:function:string-char-at");
         final URI stcic = new URI("urn:bea:xacml:2.0:function:string-compare-to-ignore-case");
         final URI sc = new URI("urn:bea:xacml:2.0:function:string-contains");
         final URI sew = new URI("urn:bea:xacml:2.0:function:string-ends-with");
         final URI sl = new URI("urn:bea:xacml:2.0:function:string-length");
         final URI sr = new URI("urn:bea:xacml:2.0:function:string-replace");
         final URI ssw = new URI("urn:bea:xacml:2.0:function:string-starts-with");
         final URI ss = new URI("urn:bea:xacml:2.0:function:string-substring");
         final URI sntuc = new URI("urn:bea:xacml:2.0:function:string-normalize-to-upper-case");
         final URI uscc = new URI("urn:bea:xacml:2.0:function:uri-string-concat");
         this.register(sca, new SimpleFunctionFactoryBase(Type.CHARACTER, new Type[]{Type.STRING, Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new CharacterAttributeEvaluatorBase() {
                  public CharacterAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = null;
                     IntegerAttribute p2 = null;

                     try {
                        p1 = (StringAttribute)param1.evaluate(context);
                        p2 = (IntegerAttribute)param2.evaluate(context);
                        CharacterAttribute result = new CharacterAttribute(new Character(p1.getValue().charAt(p2.getIntValue())));
                        if (context.isDebugEnabled()) {
                           StringFunctionLibrary.this.debugEval(context, sca, result, new Bag[]{p1, p2});
                        }

                        return result;
                     } catch (IndexOutOfBoundsException var6) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                        if (context.isDebugEnabled()) {
                           StringFunctionLibrary.this.debugEval(context, sca, th, new Bag[]{p1, p2});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(stcic, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.STRING, Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                     StringAttribute p2 = (StringAttribute)param2.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getValue().compareToIgnoreCase(p2.getValue()));
                     if (context.isDebugEnabled()) {
                        StringFunctionLibrary.this.debugEval(context, stcic, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(sc, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.STRING, Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                     StringAttribute p2 = (StringAttribute)param2.evaluate(context);
                     BooleanAttribute result = p1.getValue().indexOf(p2.getValue()) >= 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                     if (context.isDebugEnabled()) {
                        StringFunctionLibrary.this.debugEval(context, sc, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(sew, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.STRING, Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                     StringAttribute p2 = (StringAttribute)param2.evaluate(context);
                     BooleanAttribute result = p1.getValue().endsWith(p2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                     if (context.isDebugEnabled()) {
                        StringFunctionLibrary.this.debugEval(context, sew, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(sl, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getValue().length());
                     if (context.isDebugEnabled()) {
                        StringFunctionLibrary.this.debugEval(context, sl, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(sr, new SimpleFunctionFactoryBase(Type.STRING, new Type[]{Type.STRING, Type.CHARACTER, Type.CHARACTER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               final AttributeEvaluator param3 = (AttributeEvaluator)arguments.get(2);
               return new StringAttributeEvaluatorBase() {
                  public StringAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = null;
                     CharacterAttribute p2 = null;
                     CharacterAttribute p3 = null;

                     try {
                        p1 = (StringAttribute)param1.evaluate(context);
                        p2 = (CharacterAttribute)param2.evaluate(context);
                        p3 = (CharacterAttribute)param3.evaluate(context);
                        StringAttribute result = new StringAttribute(p1.getValue().replace(p2.getValue(), p3.getValue()));
                        if (context.isDebugEnabled()) {
                           StringFunctionLibrary.this.debugEval(context, sr, result, new Bag[]{p1, p2, p3});
                        }

                        return result;
                     } catch (IndexOutOfBoundsException var7) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var7);
                        if (context.isDebugEnabled()) {
                           StringFunctionLibrary.this.debugEval(context, sr, th, new Bag[]{p1, p2, p3});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(ssw, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.STRING, Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                     StringAttribute p2 = (StringAttribute)param2.evaluate(context);
                     BooleanAttribute result = p1.getValue().startsWith(p2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                     if (context.isDebugEnabled()) {
                        StringFunctionLibrary.this.debugEval(context, ssw, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ss, new SimpleFunctionFactoryBase(Type.STRING, new Type[]{Type.STRING, Type.INTEGER, Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               final AttributeEvaluator param3 = (AttributeEvaluator)arguments.get(2);
               return new StringAttributeEvaluatorBase() {
                  public StringAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = null;
                     IntegerAttribute p2 = null;
                     IntegerAttribute p3 = null;

                     try {
                        p1 = (StringAttribute)param1.evaluate(context);
                        p2 = (IntegerAttribute)param2.evaluate(context);
                        p3 = (IntegerAttribute)param3.evaluate(context);
                        StringAttribute result = new StringAttribute(p1.getValue().substring(p2.getValue(), p3.getValue()));
                        if (context.isDebugEnabled()) {
                           StringFunctionLibrary.this.debugEval(context, ss, result, new Bag[]{p1, p2, p3});
                        }

                        return result;
                     } catch (IndexOutOfBoundsException var7) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var7);
                        if (context.isDebugEnabled()) {
                           StringFunctionLibrary.this.debugEval(context, ss, th, new Bag[]{p1, p2, p3});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(sntuc, new SimpleFunctionFactoryBase(Type.STRING, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new StringAttributeEvaluatorBase() {
                  public StringAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = null;

                     try {
                        p1 = (StringAttribute)param1.evaluate(context);
                        StringAttribute result = new StringAttribute(p1.getValue().toUpperCase());
                        if (context.isDebugEnabled()) {
                           StringFunctionLibrary.this.debugEval(context, sntuc, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (IndexOutOfBoundsException var5) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var5);
                        if (context.isDebugEnabled()) {
                           StringFunctionLibrary.this.debugEval(context, sntuc, th, new Bag[]{p1});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(uscc, new SimpleFunctionFactoryBase(Type.ANY_URI, new Type[]{Type.STRING, new MultipleArgumentType(Type.STRING, 1)}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final List args = arguments.subList(1, arguments.size());
               return new AnyURIAttributeEvaluatorBase() {
                  public AnyURIAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                        StringBuffer sb = new StringBuffer(p1.getValue());
                        if (context.isDebugEnabled()) {
                           Bag a2 = new StringAttributeBag();
                           Iterator var10 = args.iterator();

                           while(var10.hasNext()) {
                              AttributeEvaluator aex = (AttributeEvaluator)var10.next();
                              StringAttribute s = (StringAttribute)aex.evaluate(context);
                              a2.add(s);
                              sb.append(s.getValue());
                           }

                           AnyURIAttribute result = new AnyURIAttribute(new URI(sb.toString()));
                           if (context.isDebugEnabled()) {
                              StringFunctionLibrary.this.debugEval(context, uscc, result, new Bag[]{p1, a2});
                           }

                           return result;
                        } else {
                           Iterator var4 = args.iterator();

                           while(var4.hasNext()) {
                              AttributeEvaluator ae = (AttributeEvaluator)var4.next();
                              sb.append(((StringAttribute)ae.evaluate(context)).getValue());
                           }

                           return new AnyURIAttribute(new URI(sb.toString()));
                        }
                     } catch (java.net.URISyntaxException var8) {
                        throw new IndeterminateEvaluationException(var8);
                     }
                  }
               };
            }
         });
      } catch (java.net.URISyntaxException var11) {
         throw new URISyntaxException(var11);
      }
   }
}
