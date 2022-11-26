package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AnyURIAttribute;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.DNSAddressAttribute;
import com.bea.common.security.xacml.attr.IPAddressAttribute;
import com.bea.common.security.xacml.attr.RFC822NameAttribute;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.attr.X500NameAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.Constant;
import com.bea.security.xacml.function.ConstantUtil;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegularExpressionFunctionLibrary extends SimpleFunctionLibraryBase {
   public RegularExpressionFunctionLibrary() throws URISyntaxException {
      try {
         final URI srm = new URI("urn:oasis:names:tc:xacml:1.0:function:string-regexp-match");
         final URI arm = new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-regexp-match");
         final URI irm = new URI("urn:oasis:names:tc:xacml:1.0:function:ipAddress-regexp-match");
         final URI drm = new URI("urn:oasis:names:tc:xacml:1.0:function:dnsName-regexp-match");
         final URI rrm = new URI("urn:oasis:names:tc:xacml:1.0:function:rfc822Name-regexp-match");
         final URI xrm = new URI("urn:oasis:names:tc:xacml:1.0:function:x500Name-regexp-match");
         this.register(srm, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.STRING, Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               final StringAttribute s1 = (StringAttribute)ConstantUtil.getConstantValue(param1);
               StringAttribute s2 = (StringAttribute)ConstantUtil.getConstantValue(param2);
               if (s1 != null) {
                  try {
                     if (s2 != null) {
                        return new Constant(Pattern.matches(s1.getValue(), s2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE);
                     } else {
                        final Pattern p = Pattern.compile(s1.getValue());
                        return new BooleanAttributeEvaluatorBase() {
                           public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                              StringAttribute p2 = (StringAttribute)param2.evaluate(context);
                              BooleanAttribute result = p.matcher(p2.getValue()).matches() ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                              if (context.isDebugEnabled()) {
                                 RegularExpressionFunctionLibrary.this.debugEval(context, srm, result, new Bag[]{s1, p2});
                              }

                              return result;
                           }
                        };
                     }
                  } catch (final PatternSyntaxException var7) {
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var7);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, srm, th, new Bag[]{s1, null});
                           }

                           throw th;
                        }
                     };
                  }
               } else {
                  return new BooleanAttributeEvaluatorBase() {
                     public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        StringAttribute p1 = null;
                        StringAttribute p2 = null;

                        try {
                           p1 = (StringAttribute)param1.evaluate(context);
                           p2 = (StringAttribute)param2.evaluate(context);
                           BooleanAttribute result = Pattern.matches(p1.getValue(), p2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, srm, result, new Bag[]{p1, p2});
                           }

                           return result;
                        } catch (PatternSyntaxException var6) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, srm, th, new Bag[]{p1, p2});
                           }

                           throw th;
                        }
                     }
                  };
               }
            }
         });
         this.register(arm, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.STRING, Type.ANY_URI}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               final StringAttribute s1 = (StringAttribute)ConstantUtil.getConstantValue(param1);
               AnyURIAttribute s2 = (AnyURIAttribute)ConstantUtil.getConstantValue(param2);
               if (s1 != null) {
                  try {
                     if (s2 != null) {
                        return new Constant(Pattern.matches(s1.getValue(), s2.getValue().toString()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE);
                     } else {
                        final Pattern p = Pattern.compile(s1.getValue());
                        return new BooleanAttributeEvaluatorBase() {
                           public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                              AnyURIAttribute p2 = (AnyURIAttribute)param2.evaluate(context);
                              BooleanAttribute result = p.matcher(p2.getValue().toString()).matches() ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                              if (context.isDebugEnabled()) {
                                 RegularExpressionFunctionLibrary.this.debugEval(context, arm, result, new Bag[]{s1, p2});
                              }

                              return result;
                           }
                        };
                     }
                  } catch (final PatternSyntaxException var7) {
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var7);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, arm, th, new Bag[]{s1, null});
                           }

                           throw th;
                        }
                     };
                  }
               } else {
                  return new BooleanAttributeEvaluatorBase() {
                     public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        StringAttribute p1 = null;
                        AnyURIAttribute p2 = null;

                        try {
                           p1 = (StringAttribute)param1.evaluate(context);
                           p2 = (AnyURIAttribute)param2.evaluate(context);
                           BooleanAttribute result = Pattern.matches(p1.getValue(), p2.getValue().toString()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, arm, result, new Bag[]{p1, p2});
                           }

                           return result;
                        } catch (PatternSyntaxException var6) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, arm, th, new Bag[]{p1, p2});
                           }

                           throw th;
                        }
                     }
                  };
               }
            }
         });
         this.register(irm, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.STRING, Type.IP_ADDRESS}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               final StringAttribute s1 = (StringAttribute)ConstantUtil.getConstantValue(param1);
               IPAddressAttribute s2 = (IPAddressAttribute)ConstantUtil.getConstantValue(param2);
               if (s1 != null) {
                  try {
                     if (s2 != null) {
                        return new Constant(Pattern.matches(s1.getValue(), s2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE);
                     } else {
                        final Pattern p = Pattern.compile(s1.getValue());
                        return new BooleanAttributeEvaluatorBase() {
                           public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                              IPAddressAttribute p2 = (IPAddressAttribute)param2.evaluate(context);
                              BooleanAttribute result = p.matcher(p2.getValue()).matches() ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                              if (context.isDebugEnabled()) {
                                 RegularExpressionFunctionLibrary.this.debugEval(context, irm, result, new Bag[]{s1, p2});
                              }

                              return result;
                           }
                        };
                     }
                  } catch (final PatternSyntaxException var7) {
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var7);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, irm, th, new Bag[]{s1, null});
                           }

                           throw th;
                        }
                     };
                  }
               } else {
                  return new BooleanAttributeEvaluatorBase() {
                     public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        StringAttribute p1 = null;
                        IPAddressAttribute p2 = null;

                        try {
                           p1 = (StringAttribute)param1.evaluate(context);
                           p2 = (IPAddressAttribute)param2.evaluate(context);
                           BooleanAttribute result = Pattern.matches(p1.getValue(), p2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, irm, result, new Bag[]{p1, p2});
                           }

                           return result;
                        } catch (PatternSyntaxException var6) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, irm, th, new Bag[]{p1, p2});
                           }

                           throw th;
                        }
                     }
                  };
               }
            }
         });
         this.register(drm, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.STRING, Type.DNS_ADDRESS}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               final StringAttribute s1 = (StringAttribute)ConstantUtil.getConstantValue(param1);
               DNSAddressAttribute s2 = (DNSAddressAttribute)ConstantUtil.getConstantValue(param2);
               if (s1 != null) {
                  try {
                     if (s2 != null) {
                        return new Constant(Pattern.matches(s1.getValue(), s2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE);
                     } else {
                        final Pattern p = Pattern.compile(s1.getValue());
                        return new BooleanAttributeEvaluatorBase() {
                           public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                              DNSAddressAttribute p2 = (DNSAddressAttribute)param2.evaluate(context);
                              BooleanAttribute result = p.matcher(p2.getValue()).matches() ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                              if (context.isDebugEnabled()) {
                                 RegularExpressionFunctionLibrary.this.debugEval(context, drm, result, new Bag[]{s1, p2});
                              }

                              return result;
                           }
                        };
                     }
                  } catch (final PatternSyntaxException var7) {
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var7);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, drm, th, new Bag[]{s1, null});
                           }

                           throw th;
                        }
                     };
                  }
               } else {
                  return new BooleanAttributeEvaluatorBase() {
                     public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        StringAttribute p1 = null;
                        DNSAddressAttribute p2 = null;

                        try {
                           p1 = (StringAttribute)param1.evaluate(context);
                           p2 = (DNSAddressAttribute)param2.evaluate(context);
                           BooleanAttribute result = Pattern.matches(p1.getValue(), p2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, drm, result, new Bag[]{p1, p2});
                           }

                           return result;
                        } catch (PatternSyntaxException var6) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, drm, th, new Bag[]{p1, p2});
                           }

                           throw th;
                        }
                     }
                  };
               }
            }
         });
         this.register(rrm, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.STRING, Type.RFC822_NAME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               final StringAttribute s1 = (StringAttribute)ConstantUtil.getConstantValue(param1);
               RFC822NameAttribute s2 = (RFC822NameAttribute)ConstantUtil.getConstantValue(param2);
               if (s1 != null) {
                  try {
                     if (s2 != null) {
                        return new Constant(Pattern.matches(s1.getValue(), s2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE);
                     } else {
                        final Pattern p = Pattern.compile(s1.getValue());
                        return new BooleanAttributeEvaluatorBase() {
                           public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                              RFC822NameAttribute p2 = (RFC822NameAttribute)param2.evaluate(context);
                              BooleanAttribute result = p.matcher(p2.getValue()).matches() ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                              if (context.isDebugEnabled()) {
                                 RegularExpressionFunctionLibrary.this.debugEval(context, rrm, result, new Bag[]{s1, p2});
                              }

                              return result;
                           }
                        };
                     }
                  } catch (final PatternSyntaxException var7) {
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var7);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, rrm, th, new Bag[]{s1, null});
                           }

                           throw th;
                        }
                     };
                  }
               } else {
                  return new BooleanAttributeEvaluatorBase() {
                     public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        StringAttribute p1 = null;
                        RFC822NameAttribute p2 = null;

                        try {
                           p1 = (StringAttribute)param1.evaluate(context);
                           p2 = (RFC822NameAttribute)param2.evaluate(context);
                           BooleanAttribute result = Pattern.matches(p1.getValue(), p2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, rrm, result, new Bag[]{p1, p2});
                           }

                           return result;
                        } catch (PatternSyntaxException var6) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, rrm, th, new Bag[]{p1, p2});
                           }

                           throw th;
                        }
                     }
                  };
               }
            }
         });
         this.register(xrm, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.STRING, Type.X500_NAME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               final StringAttribute s1 = (StringAttribute)ConstantUtil.getConstantValue(param1);
               X500NameAttribute s2 = (X500NameAttribute)ConstantUtil.getConstantValue(param2);
               if (s1 != null) {
                  try {
                     if (s2 != null) {
                        return new Constant(Pattern.matches(s1.getValue(), s2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE);
                     } else {
                        final Pattern p = Pattern.compile(s1.getValue());
                        return new BooleanAttributeEvaluatorBase() {
                           public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                              X500NameAttribute p2 = (X500NameAttribute)param2.evaluate(context);
                              BooleanAttribute result = p.matcher(p2.getValue()).matches() ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                              if (context.isDebugEnabled()) {
                                 RegularExpressionFunctionLibrary.this.debugEval(context, xrm, result, new Bag[]{s1, p2});
                              }

                              return result;
                           }
                        };
                     }
                  } catch (final PatternSyntaxException var7) {
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var7);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, xrm, th, new Bag[]{s1, null});
                           }

                           throw th;
                        }
                     };
                  }
               } else {
                  return new BooleanAttributeEvaluatorBase() {
                     public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        StringAttribute p1 = null;
                        X500NameAttribute p2 = null;

                        try {
                           p1 = (StringAttribute)param1.evaluate(context);
                           p2 = (X500NameAttribute)param2.evaluate(context);
                           BooleanAttribute result = Pattern.matches(p1.getValue(), p2.getValue()) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, xrm, result, new Bag[]{p1, p2});
                           }

                           return result;
                        } catch (PatternSyntaxException var6) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              RegularExpressionFunctionLibrary.this.debugEval(context, xrm, th, new Bag[]{p1, p2});
                           }

                           throw th;
                        }
                     }
                  };
               }
            }
         });
      } catch (java.net.URISyntaxException var7) {
         throw new URISyntaxException(var7);
      }
   }
}
