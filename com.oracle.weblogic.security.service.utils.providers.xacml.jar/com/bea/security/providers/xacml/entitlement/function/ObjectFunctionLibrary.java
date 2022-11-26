package com.bea.security.providers.xacml.entitlement.function;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AnyURIAttribute;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.Base64BinaryAttribute;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.CharacterAttribute;
import com.bea.common.security.xacml.attr.DNSAddressAttribute;
import com.bea.common.security.xacml.attr.DateAttribute;
import com.bea.common.security.xacml.attr.DateTimeAttribute;
import com.bea.common.security.xacml.attr.DayTimeDurationAttribute;
import com.bea.common.security.xacml.attr.DecimalAttribute;
import com.bea.common.security.xacml.attr.DoubleAttribute;
import com.bea.common.security.xacml.attr.FloatAttribute;
import com.bea.common.security.xacml.attr.HexBinaryAttribute;
import com.bea.common.security.xacml.attr.IPAddressAttribute;
import com.bea.common.security.xacml.attr.IntegerAttribute;
import com.bea.common.security.xacml.attr.JavaClassAttribute;
import com.bea.common.security.xacml.attr.JavaObjectAttribute;
import com.bea.common.security.xacml.attr.JavaObjectAttributeBag;
import com.bea.common.security.xacml.attr.LongAttribute;
import com.bea.common.security.xacml.attr.RFC822NameAttribute;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.attr.TimeAttribute;
import com.bea.common.security.xacml.attr.X500NameAttribute;
import com.bea.common.security.xacml.attr.YearMonthDurationAttribute;
import com.bea.security.providers.xacml.entitlement.p13n.ClassForNameWrapper;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.AnyURIAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.Base64BinaryAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.CharacterAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DNSAddressAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DateAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DateTimeAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DayTimeDurationAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DecimalAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DoubleAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.FloatAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.HexBinaryAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.IPAddressAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.IntegerAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.JavaClassAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.JavaObjectAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.LongAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.RFC822NameAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.StringAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.TimeAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.X500NameAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.YearMonthDurationAttributeEvaluatorBase;
import com.bea.security.xacml.function.ConstantUtil;
import com.bea.security.xacml.function.MultipleArgumentType;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ObjectFunctionLibrary extends SimpleFunctionLibraryBase {
   private static final long NANOS_PER_MILLI = 1000000L;
   private static final ClassForNameWrapper cfnw = new ClassForNameWrapper();
   private final URI otdt;
   private final URI otda;
   private final URI ott;

   private Class forName(String cname) throws ClassNotFoundException {
      Class c = cfnw.forName(cname);
      if (c == null) {
         PrivilegedAction getThreadCCLAction = new PrivilegedAction() {
            public ClassLoader run() {
               return Thread.currentThread().getContextClassLoader();
            }
         };
         ClassLoader threadCCL = (ClassLoader)AccessController.doPrivileged(getThreadCCLAction);
         c = Class.forName(cname, true, threadCCL);
      }

      return c;
   }

   public ObjectFunctionLibrary() throws URISyntaxException {
      try {
         final URI stc = new URI("urn:bea:xacml:2.0:function:string-to-class");
         final URI ots = new URI("urn:bea:xacml:2.0:function:object-to-string");
         final URI otd = new URI("urn:bea:xacml:2.0:function:object-to-double");
         final URI oti = new URI("urn:bea:xacml:2.0:function:object-to-integer");
         final URI otf = new URI("urn:bea:xacml:2.0:function:object-to-float");
         final URI otl = new URI("urn:bea:xacml:2.0:function:object-to-long");
         this.otdt = new URI("urn:bea:xacml:2.0:function:object-to-dateTime");
         this.otda = new URI("urn:bea:xacml:2.0:function:object-to-date");
         this.ott = new URI("urn:bea:xacml:2.0:function:object-to-time");
         final URI otau = new URI("urn:bea:xacml:2.0:function:object-to-anyURI");
         final URI otb = new URI("urn:bea:xacml:2.0:function:object-to-boolean");
         final URI otdtd = new URI("urn:bea:xacml:2.0:function:object-to-dayTimeDuration");
         final URI otymd = new URI("urn:bea:xacml:2.0:function:object-to-yearMonthDuration");
         final URI othb = new URI("urn:bea:xacml:2.0:function:object-to-hexBinary");
         final URI otbb = new URI("urn:bea:xacml:2.0:function:object-to-base64Binary");
         final URI otr = new URI("urn:bea:xacml:2.0:function:object-to-rfc822Name");
         final URI otx5 = new URI("urn:bea:xacml:2.0:function:object-to-x500Name");
         final URI otip = new URI("urn:bea:xacml:2.0:function:object-to-ipAddress");
         final URI otdns = new URI("urn:bea:xacml:2.0:function:object-to-dnsName");
         final URI otc = new URI("urn:bea:xacml:2.0:function:object-to-character");
         final URI otx = new URI("urn:bea:xacml:2.0:function:object-to-decimal");
         final URI otcl = new URI("urn:bea:xacml:2.0:function:object-to-class");
         final URI oe = new URI("urn:bea:xacml:2.0:function:object-equal");
         final URI ogt = new URI("urn:bea:xacml:2.0:function:object-greater-than");
         final URI ogtoe = new URI("urn:bea:xacml:2.0:function:object-greater-than-or-equal");
         final URI olt = new URI("urn:bea:xacml:2.0:function:object-less-than");
         final URI oltoe = new URI("urn:bea:xacml:2.0:function:object-less-than-or-equal");
         final URI oin = new URI("urn:bea:xacml:2.0:function:object-is-null");
         final URI im = new URI("urn:bea:xacml:2.0:function:instance-method");
         final URI imm = new URI("urn:bea:xacml:2.0:function:instance-method-match");
         final URI im2 = new URI("urn:bea:xacml:2.0:function:instance-method-v2");
         final URI imm2 = new URI("urn:bea:xacml:2.0:function:instance-method-match-v2");
         final URI imm3 = new URI("urn:bea:xacml:2.0:function:instance-method-match-v3");
         final URI occ = new URI("urn:bea:xacml:2.0:function:object-collection-contains");
         final URI occa = new URI("urn:bea:xacml:2.0:function:object-collection-contains-all");
         final URI sm = new URI("urn:bea:xacml:2.0:function:static-method");
         Collection types = Type.getScalarTypes();
         Iterator var35 = types.iterator();

         while(var35.hasNext()) {
            Type t = (Type)var35.next();
            if (!Type.OBJECT.equals(t)) {
               final URI to = new URI("urn:bea:xacml:2.0:function:" + t.getShortName() + "-to-object");
               this.register(to, new SimpleFunctionFactoryBase(Type.OBJECT, new Type[]{t}) {
                  protected AttributeEvaluator getFunctionImplementation(List arguments) {
                     final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                     return new JavaObjectAttributeEvaluatorBase() {
                        public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           AttributeValue p1 = null;

                           try {
                              p1 = param1.evaluate(context);
                              JavaObjectAttribute result = new JavaObjectAttribute(p1.getValue());
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, to, result, new Bag[]{p1});
                              }

                              return result;
                           } catch (IndexOutOfBoundsException var5) {
                              IndeterminateEvaluationException th = new IndeterminateEvaluationException(var5);
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, to, th, new Bag[]{p1});
                              }

                              throw th;
                           }
                        }
                     };
                  }
               });
            }
         }

         this.register(stc, new SimpleFunctionFactoryBase(Type.CLASS, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new JavaClassAttributeEvaluatorBase() {
                  public JavaClassAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = null;

                     try {
                        p1 = (StringAttribute)param1.evaluate(context);
                        Class c = ObjectFunctionLibrary.this.forName(p1.getValue());
                        JavaClassAttribute result = new JavaClassAttribute(c);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, stc, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (ClassNotFoundException var5) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var5);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, stc, th, new Bag[]{p1});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(ots, new SimpleFunctionFactoryBase(Type.STRING, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new StringAttributeEvaluatorBase() {
                  public StringAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to string");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, ots, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        StringAttribute result = new StringAttribute(o.toString());
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, ots, result, new Bag[]{p1});
                        }

                        return result;
                     }
                  }
               };
            }
         });
         this.register(otau, new SimpleFunctionFactoryBase(Type.ANY_URI, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new AnyURIAttributeEvaluatorBase() {
                  public AnyURIAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to anyURI");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otau, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           AnyURIAttribute result = new AnyURIAttribute(new URI(o.toString()));
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otau, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (java.net.URISyntaxException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otau, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otb, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to boolean");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otb, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           BooleanAttribute result = new BooleanAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otb, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otb, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otdtd, new SimpleFunctionFactoryBase(Type.DAY_TIME_DURATION, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DayTimeDurationAttributeEvaluatorBase() {
                  public DayTimeDurationAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to dayTimeDuration");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otdtd, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           DayTimeDurationAttribute result = new DayTimeDurationAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otdtd, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otdtd, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otymd, new SimpleFunctionFactoryBase(Type.YEAR_MONTH_DURATION, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new YearMonthDurationAttributeEvaluatorBase() {
                  public YearMonthDurationAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to yearMonthDuration");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otymd, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           YearMonthDurationAttribute result = new YearMonthDurationAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otymd, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otymd, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(othb, new SimpleFunctionFactoryBase(Type.HEX_BINARY, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new HexBinaryAttributeEvaluatorBase() {
                  public HexBinaryAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to hexBinary");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, othb, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           HexBinaryAttribute result = new HexBinaryAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, othb, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, othb, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otbb, new SimpleFunctionFactoryBase(Type.BASE64_BINARY, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new Base64BinaryAttributeEvaluatorBase() {
                  public Base64BinaryAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to base64Binary");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otbb, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           Base64BinaryAttribute result = new Base64BinaryAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otbb, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otbb, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otr, new SimpleFunctionFactoryBase(Type.RFC822_NAME, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new RFC822NameAttributeEvaluatorBase() {
                  public RFC822NameAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to rfc822Name");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otr, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           RFC822NameAttribute result = new RFC822NameAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otr, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otr, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otx5, new SimpleFunctionFactoryBase(Type.X500_NAME, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new X500NameAttributeEvaluatorBase() {
                  public X500NameAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to x500Name");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otx5, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           X500NameAttribute result = new X500NameAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otx5, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otx5, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otip, new SimpleFunctionFactoryBase(Type.IP_ADDRESS, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IPAddressAttributeEvaluatorBase() {
                  public IPAddressAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to ipAddress");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otip, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           IPAddressAttribute result = new IPAddressAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otip, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otip, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otdns, new SimpleFunctionFactoryBase(Type.DNS_ADDRESS, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DNSAddressAttributeEvaluatorBase() {
                  public DNSAddressAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to dnsAddress");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otdns, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        DNSAddressAttribute result = new DNSAddressAttribute(o.toString());
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otdns, result, new Bag[]{p1});
                        }

                        return result;
                     }
                  }
               };
            }
         });
         this.register(otx, new SimpleFunctionFactoryBase(Type.DECIMAL, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DecimalAttributeEvaluatorBase() {
                  public DecimalAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to decimal");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otx, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           DecimalAttribute result = new DecimalAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otx, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otx, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otcl, new SimpleFunctionFactoryBase(Type.CLASS, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new JavaClassAttributeEvaluatorBase() {
                  public JavaClassAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to class");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otcl, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        JavaClassAttribute result;
                        if (o instanceof Class) {
                           result = new JavaClassAttribute((Class)o);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otcl, result, new Bag[]{p1});
                           }

                           return result;
                        } else {
                           try {
                              result = new JavaClassAttribute(ObjectFunctionLibrary.this.forName(o.toString()));
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, otcl, result, new Bag[]{p1});
                              }

                              return result;
                           } catch (ClassNotFoundException var6) {
                              IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, otcl, thx, new Bag[]{p1});
                              }

                              throw thx;
                           }
                        }
                     }
                  }
               };
            }
         });
         this.register(otc, new SimpleFunctionFactoryBase(Type.CHARACTER, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new CharacterAttributeEvaluatorBase() {
                  public CharacterAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to boolean");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otc, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           CharacterAttribute result = new CharacterAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otc, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otc, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otd, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     DoubleAttribute result;
                     if (o instanceof Double) {
                        result = new DoubleAttribute((Double)o);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otd, result, new Bag[]{p1});
                        }

                        return result;
                     } else if (o instanceof Number) {
                        result = new DoubleAttribute(((Number)o).doubleValue());
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otd, result, new Bag[]{p1});
                        }

                        return result;
                     } else if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to double");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otd, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           result = new DoubleAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otd, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otd, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(oti, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     IntegerAttribute result;
                     if (o instanceof Integer) {
                        result = new IntegerAttribute((Integer)o);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, oti, result, new Bag[]{p1});
                        }

                        return result;
                     } else if (o instanceof Number) {
                        result = new IntegerAttribute(((Number)o).intValue());
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, oti, result, new Bag[]{p1});
                        }

                        return result;
                     } else if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to integer");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, oti, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           result = new IntegerAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, oti, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, oti, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otf, new SimpleFunctionFactoryBase(Type.FLOAT, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new FloatAttributeEvaluatorBase() {
                  public FloatAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     FloatAttribute result;
                     if (o instanceof Number) {
                        result = new FloatAttribute(((Number)o).doubleValue());
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otf, result, new Bag[]{p1});
                        }

                        return result;
                     } else {
                        try {
                           if (o == null) {
                              IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to float");
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, otf, th, new Bag[]{p1});
                              }

                              throw th;
                           } else {
                              result = new FloatAttribute(o.toString());
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, otf, result, new Bag[]{p1});
                              }

                              return result;
                           }
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otf, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(otl, new SimpleFunctionFactoryBase(Type.LONG, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new LongAttributeEvaluatorBase() {
                  public LongAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     LongAttribute result;
                     if (o instanceof Number) {
                        result = new LongAttribute(((Number)o).longValue());
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otl, result, new Bag[]{p1});
                        }

                        return result;
                     } else if (o == null) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to long");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, otl, th, new Bag[]{p1});
                        }

                        throw th;
                     } else {
                        try {
                           result = new LongAttribute(o.toString());
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otl, result, new Bag[]{p1});
                           }

                           return result;
                        } catch (InvalidAttributeException var6) {
                           IndeterminateEvaluationException thx = new IndeterminateEvaluationException(var6);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, otl, thx, new Bag[]{p1});
                           }

                           throw thx;
                        }
                     }
                  }
               };
            }
         });
         this.register(this.otdt, new SimpleFunctionFactoryBase(Type.DATE_TIME, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DateTimeAttributeEvaluatorBase() {
                  public DateTimeAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     return ObjectFunctionLibrary.this.convertObjectToDateTime(context, p1);
                  }
               };
            }
         });
         this.register(this.otda, new SimpleFunctionFactoryBase(Type.DATE, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DateAttributeEvaluatorBase() {
                  public DateAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     return ObjectFunctionLibrary.this.convertObjectToDate(context, p1);
                  }
               };
            }
         });
         this.register(this.ott, new SimpleFunctionFactoryBase(Type.TIME, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new TimeAttributeEvaluatorBase() {
                  public TimeAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     return ObjectFunctionLibrary.this.convertObjectToTime(context, p1);
                  }
               };
            }
         });
         this.register(oe, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.OBJECT, Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     JavaObjectAttribute p2 = (JavaObjectAttribute)param2.evaluate(context);
                     Object o1 = p1.getValue();
                     Object o2 = p2.getValue();
                     BooleanAttribute result = o1 != o2 && (o1 == null || !o1.equals(o2)) ? BooleanAttribute.FALSE : BooleanAttribute.TRUE;
                     if (context.isDebugEnabled()) {
                        ObjectFunctionLibrary.this.debugEval(context, oe, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ogt, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.OBJECT, Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     JavaObjectAttribute p2 = (JavaObjectAttribute)param2.evaluate(context);
                     Object o1 = p1.getValue();
                     Object o2 = p2.getValue();
                     if (!(o1 instanceof Comparable)) {
                        IndeterminateEvaluationException thx = new IndeterminateEvaluationException("Object not comparable");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, ogt, thx, new Bag[]{p1, p2});
                        }

                        throw thx;
                     } else {
                        try {
                           BooleanAttribute result = ((Comparable)o1).compareTo(o2) > 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, ogt, result, new Bag[]{p1, p2});
                           }

                           return result;
                        } catch (ClassCastException var8) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException("Second argument not proper class type for comparison");
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, ogt, th, new Bag[]{p1, p2});
                           }

                           throw th;
                        }
                     }
                  }
               };
            }
         });
         this.register(ogtoe, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.OBJECT, Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     JavaObjectAttribute p2 = (JavaObjectAttribute)param2.evaluate(context);
                     Object o1 = p1.getValue();
                     Object o2 = p2.getValue();
                     if (!(o1 instanceof Comparable)) {
                        IndeterminateEvaluationException thx = new IndeterminateEvaluationException("Object not comparable");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, ogtoe, thx, new Bag[]{p1, p2});
                        }

                        throw thx;
                     } else {
                        try {
                           BooleanAttribute result = ((Comparable)o1).compareTo(o2) >= 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, ogtoe, result, new Bag[]{p1, p2});
                           }

                           return result;
                        } catch (ClassCastException var8) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException("Second argument not proper class type for comparison");
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, ogtoe, th, new Bag[]{p1, p2});
                           }

                           throw th;
                        }
                     }
                  }
               };
            }
         });
         this.register(olt, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.OBJECT, Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     JavaObjectAttribute p2 = (JavaObjectAttribute)param2.evaluate(context);
                     Object o1 = p1.getValue();
                     Object o2 = p2.getValue();
                     if (!(o1 instanceof Comparable)) {
                        IndeterminateEvaluationException thx = new IndeterminateEvaluationException("Object not comparable");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, olt, thx, new Bag[]{p1, p2});
                        }

                        throw thx;
                     } else {
                        try {
                           BooleanAttribute result = ((Comparable)o1).compareTo(o2) < 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, olt, result, new Bag[]{p1, p2});
                           }

                           return result;
                        } catch (ClassCastException var8) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException("Second argument not proper class type for comparison");
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, olt, th, new Bag[]{p1, p2});
                           }

                           throw th;
                        }
                     }
                  }
               };
            }
         });
         this.register(oltoe, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.OBJECT, Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     JavaObjectAttribute p2 = (JavaObjectAttribute)param2.evaluate(context);
                     Object o1 = p1.getValue();
                     Object o2 = p2.getValue();
                     if (!(o1 instanceof Comparable)) {
                        IndeterminateEvaluationException thx = new IndeterminateEvaluationException("Object not comparable");
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, oltoe, thx, new Bag[]{p1, p2});
                        }

                        throw thx;
                     } else {
                        try {
                           BooleanAttribute result = ((Comparable)o1).compareTo(o2) <= 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, oltoe, result, new Bag[]{p1, p2});
                           }

                           return result;
                        } catch (ClassCastException var8) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException("Second argument not proper class type for comparison");
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, oltoe, th, new Bag[]{p1, p2});
                           }

                           throw th;
                        }
                     }
                  }
               };
            }
         });
         this.register(oin, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     Object o = p1.getValue();
                     BooleanAttribute result = o == null ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                     if (context.isDebugEnabled()) {
                        ObjectFunctionLibrary.this.debugEval(context, oin, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(im, new SimpleFunctionFactoryBase(Type.OBJECT, new Type[]{Type.OBJECT, Type.STRING, Type.CLASS_BAG, new MultipleArgumentType(Type.OBJECT, 0)}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator target = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator methodName = (AttributeEvaluator)arguments.get(1);
               final AttributeEvaluator signature = (AttributeEvaluator)arguments.get(2);
               final List args = arguments.subList(3, arguments.size());
               final int argsSize = args.size();
               return argsSize > 0 ? new JavaObjectAttributeEvaluatorBase() {
                  public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute t1 = null;
                     StringAttribute m2 = null;
                     Bag classBag = null;
                     Bag a4 = null;

                     IndeterminateEvaluationException th;
                     try {
                        t1 = (JavaObjectAttribute)target.evaluate(context);
                        m2 = (StringAttribute)methodName.evaluate(context);
                        classBag = signature.evaluateToBag(context);
                        int bagSize = classBag.size();
                        if (argsSize != bagSize) {
                           th = new IndeterminateEvaluationException("Argument list size does not formal parameter signature");
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im, th, new Bag[]{t1, m2, classBag});
                           }

                           throw th;
                        } else {
                           Class[] classSig = new Class[bagSize];
                           int i = 0;

                           JavaClassAttribute jca;
                           for(Iterator var9 = classBag.iterator(); var9.hasNext(); classSig[i++] = jca.getValue()) {
                              jca = (JavaClassAttribute)var9.next();
                           }

                           Object o = t1.getValue();
                           Class c = o.getClass();
                           Method meth = c.getMethod(m2.getValue(), classSig);
                           Object[] oArgs = new Object[bagSize];
                           i = 0;
                           Iterator var13;
                           AttributeEvaluator joa;
                           if (context.isDebugEnabled()) {
                              a4 = new JavaObjectAttributeBag();

                              JavaObjectAttribute a;
                              for(var13 = args.iterator(); var13.hasNext(); oArgs[i++] = a.getValue()) {
                                 joa = (AttributeEvaluator)var13.next();
                                 a = (JavaObjectAttribute)joa.evaluate(context);
                                 a4.add(a);
                              }
                           } else {
                              for(var13 = args.iterator(); var13.hasNext(); oArgs[i++] = ((JavaObjectAttribute)joa.evaluate(context)).getValue()) {
                                 joa = (AttributeEvaluator)var13.next();
                              }
                           }

                           JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(o, oArgs));
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im, result, new Bag[]{t1, m2, classBag, a4});
                           }

                           return result;
                        }
                     } catch (NoSuchMethodException var16) {
                        th = new IndeterminateEvaluationException(var16);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, im, th, new Bag[]{t1, m2, classBag, a4});
                        }

                        throw th;
                     } catch (InvocationTargetException var17) {
                        th = new IndeterminateEvaluationException(var17);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, im, th, new Bag[]{t1, m2, classBag, a4});
                        }

                        throw th;
                     } catch (IllegalAccessException var18) {
                        th = new IndeterminateEvaluationException(var18);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, im, th, new Bag[]{t1, m2, classBag, a4});
                        }

                        throw th;
                     }
                  }
               } : new JavaObjectAttributeEvaluatorBase() {
                  public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute t1 = null;
                     StringAttribute m2 = null;
                     Bag classBag = null;

                     IndeterminateEvaluationException th;
                     try {
                        t1 = (JavaObjectAttribute)target.evaluate(context);
                        m2 = (StringAttribute)methodName.evaluate(context);
                        classBag = signature.evaluateToBag(context);
                        int bagSize = classBag.size();
                        if (0 != bagSize) {
                           th = new IndeterminateEvaluationException("Argument list size does not formal parameter signature");
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im, th, new Bag[]{t1, m2, classBag, null});
                           }

                           throw th;
                        } else {
                           Object o = t1.getValue();
                           Class c = o.getClass();
                           Method meth = c.getMethod(m2.getValue());
                           JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(o));
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im, result, new Bag[]{t1, m2, classBag, null});
                           }

                           return result;
                        }
                     } catch (NoSuchMethodException var10) {
                        th = new IndeterminateEvaluationException(var10);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, im, th, new Bag[]{t1, m2, classBag, null});
                        }

                        throw th;
                     } catch (InvocationTargetException var11) {
                        th = new IndeterminateEvaluationException(var11);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, im, th, new Bag[]{t1, m2, classBag, null});
                        }

                        throw th;
                     } catch (IllegalAccessException var12) {
                        th = new IndeterminateEvaluationException(var12);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, im, th, new Bag[]{t1, m2, classBag, null});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(imm, new SimpleFunctionFactoryBase(Type.OBJECT, new Type[]{Type.OBJECT, Type.STRING, new MultipleArgumentType(Type.OBJECT, 0)}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator target = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator methodName = (AttributeEvaluator)arguments.get(1);
               final List args = arguments.subList(2, arguments.size());
               final int argsSize = args.size();
               return argsSize > 0 ? new JavaObjectAttributeEvaluatorBase() {
                  public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute t1 = null;
                     StringAttribute m2 = null;
                     Bag a3 = null;

                     IndeterminateEvaluationException th;
                     try {
                        t1 = (JavaObjectAttribute)target.evaluate(context);
                        m2 = (StringAttribute)methodName.evaluate(context);
                        Class[] classSig = new Class[argsSize];
                        Object[] oArgs = new Object[argsSize];
                        int i = 0;
                        Iterator var8;
                        AttributeEvaluator joa;
                        Object oxx;
                        if (context.isDebugEnabled()) {
                           a3 = new JavaObjectAttributeBag();

                           Object o;
                           for(var8 = args.iterator(); var8.hasNext(); classSig[i++] = o.getClass()) {
                              joa = (AttributeEvaluator)var8.next();
                              JavaObjectAttribute a = (JavaObjectAttribute)joa.evaluate(context);
                              a3.add(a);
                              o = a.getValue();
                              oArgs[i] = o;
                           }
                        } else {
                           for(var8 = args.iterator(); var8.hasNext(); classSig[i++] = oxx.getClass()) {
                              joa = (AttributeEvaluator)var8.next();
                              oxx = ((JavaObjectAttribute)joa.evaluate(context)).getValue();
                              oArgs[i] = oxx;
                           }
                        }

                        Object ox = t1.getValue();
                        Class c = ox.getClass();
                        Method meth = c.getMethod(m2.getValue(), classSig);
                        JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(ox, oArgs));
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm, result, new Bag[]{t1, m2, a3});
                        }

                        return result;
                     } catch (NoSuchMethodException var12) {
                        th = new IndeterminateEvaluationException(var12);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm, th, new Bag[]{t1, m2, a3});
                        }

                        throw th;
                     } catch (InvocationTargetException var13) {
                        th = new IndeterminateEvaluationException(var13);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm, th, new Bag[]{t1, m2, a3});
                        }

                        throw th;
                     } catch (IllegalAccessException var14) {
                        th = new IndeterminateEvaluationException(var14);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm, th, new Bag[]{t1, m2, a3});
                        }

                        throw th;
                     }
                  }
               } : new JavaObjectAttributeEvaluatorBase() {
                  public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute t1 = null;
                     StringAttribute m2 = null;

                     IndeterminateEvaluationException th;
                     try {
                        t1 = (JavaObjectAttribute)target.evaluate(context);
                        m2 = (StringAttribute)methodName.evaluate(context);
                        Object o = t1.getValue();
                        Class c = o.getClass();
                        Method meth = c.getMethod(m2.getValue());
                        JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(o));
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm, result, new Bag[]{t1, m2, null});
                        }

                        return result;
                     } catch (NoSuchMethodException var8) {
                        th = new IndeterminateEvaluationException(var8);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm, th, new Bag[]{t1, m2, null});
                        }

                        throw th;
                     } catch (InvocationTargetException var9) {
                        th = new IndeterminateEvaluationException(var9);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm, th, new Bag[]{t1, m2, null});
                        }

                        throw th;
                     } catch (IllegalAccessException var10) {
                        th = new IndeterminateEvaluationException(var10);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm, th, new Bag[]{t1, m2, null});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(im2, new SimpleFunctionFactoryBase(Type.OBJECT, new Type[]{Type.CLASS, Type.OBJECT, Type.STRING, Type.CLASS_BAG, new MultipleArgumentType(Type.OBJECT, 0)}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               AttributeEvaluator clazz = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator target = (AttributeEvaluator)arguments.get(1);
               final AttributeEvaluator methodName = (AttributeEvaluator)arguments.get(2);
               final AttributeEvaluator signature = (AttributeEvaluator)arguments.get(3);
               final List args = arguments.subList(4, arguments.size());
               final int argsSize = args.size();
               final JavaClassAttribute c1 = (JavaClassAttribute)ConstantUtil.getConstantValue(clazz);
               final StringAttribute m2 = (StringAttribute)ConstantUtil.getConstantValue(methodName);
               final Bag s3 = ConstantUtil.getConstantBagValue(signature);
               if (c1 != null && m2 != null && s3 != null) {
                  Class c = c1.getValue();
                  String mn = m2.getValue();
                  final int bagSize = s3.size();
                  Class[] classSig = new Class[bagSize];
                  int i = 0;

                  JavaClassAttribute jca;
                  for(Iterator var16 = s3.iterator(); var16.hasNext(); classSig[i++] = jca.getValue()) {
                     jca = (JavaClassAttribute)var16.next();
                  }

                  if (argsSize != bagSize) {
                     return new JavaObjectAttributeEvaluatorBase() {
                        public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException("Argument list size does not formal parameter signature");
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im2, th, new Bag[]{c1, null, m2, s3, null});
                           }

                           throw th;
                        }
                     };
                  } else {
                     final Method meth;
                     try {
                        meth = c.getMethod(mn, classSig);
                     } catch (final NoSuchMethodException var18) {
                        return new JavaObjectAttributeEvaluatorBase() {
                           public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                              IndeterminateEvaluationException th = new IndeterminateEvaluationException(var18);
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, th, new Bag[]{c1, null, m2, s3, null});
                              }

                              throw th;
                           }
                        };
                     }

                     return bagSize > 0 ? new JavaObjectAttributeEvaluatorBase() {
                        public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           JavaObjectAttribute t2 = null;
                           Bag a5 = null;

                           IndeterminateEvaluationException th;
                           try {
                              t2 = (JavaObjectAttribute)target.evaluate(context);
                              Object o = t2.getValue();
                              Object[] oArgs = new Object[bagSize];
                              int i = 0;
                              Iterator var7;
                              AttributeEvaluator joa;
                              if (context.isDebugEnabled()) {
                                 a5 = new JavaObjectAttributeBag();

                                 JavaObjectAttribute a;
                                 for(var7 = args.iterator(); var7.hasNext(); oArgs[i++] = a.getValue()) {
                                    joa = (AttributeEvaluator)var7.next();
                                    a = (JavaObjectAttribute)joa.evaluate(context);
                                    a5.add(a);
                                 }
                              } else {
                                 for(var7 = args.iterator(); var7.hasNext(); oArgs[i++] = ((JavaObjectAttribute)joa.evaluate(context)).getValue()) {
                                    joa = (AttributeEvaluator)var7.next();
                                 }
                              }

                              JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(o, oArgs));
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, result, new Bag[]{c1, t2, m2, s3, a5});
                              }

                              return result;
                           } catch (InvocationTargetException var10) {
                              th = new IndeterminateEvaluationException(var10);
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, th, new Bag[]{c1, t2, m2, s3, a5});
                              }

                              throw th;
                           } catch (IllegalAccessException var11) {
                              th = new IndeterminateEvaluationException(var11);
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, th, new Bag[]{c1, t2, m2, s3, a5});
                              }

                              throw th;
                           }
                        }
                     } : new JavaObjectAttributeEvaluatorBase() {
                        public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           JavaObjectAttribute t2 = null;

                           IndeterminateEvaluationException th;
                           try {
                              t2 = (JavaObjectAttribute)target.evaluate(context);
                              Object o = t2.getValue();
                              JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(o));
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, result, new Bag[]{c1, t2, m2, s3, null});
                              }

                              return result;
                           } catch (InvocationTargetException var5) {
                              th = new IndeterminateEvaluationException(var5);
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, th, new Bag[]{c1, t2, m2, s3, null});
                              }

                              throw th;
                           } catch (IllegalAccessException var6) {
                              th = new IndeterminateEvaluationException(var6);
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, th, new Bag[]{c1, t2, m2, s3, null});
                              }

                              throw th;
                           }
                        }
                     };
                  }
               } else {
                  return argsSize > 0 ? new JavaObjectAttributeEvaluatorBase() {
                     public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        JavaObjectAttribute t2 = null;
                        StringAttribute m3 = null;
                        Bag classBag = null;
                        Class c = null;
                        Bag a5 = null;

                        IndeterminateEvaluationException th;
                        try {
                           t2 = (JavaObjectAttribute)target.evaluate(context);
                           m3 = (StringAttribute)methodName.evaluate(context);
                           classBag = signature.evaluateToBag(context);
                           Object o = t2.getValue();
                           c = o.getClass();
                           int bagSize = classBag.size();
                           if (argsSize != bagSize) {
                              IndeterminateEvaluationException thx = new IndeterminateEvaluationException("Argument list size does not formal parameter signature");
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, thx, new Bag[]{new JavaClassAttribute(c), t2, m2, classBag, null});
                              }

                              throw thx;
                           } else {
                              Class[] classSig = new Class[bagSize];
                              int i = 0;

                              JavaClassAttribute jca;
                              for(Iterator var11 = classBag.iterator(); var11.hasNext(); classSig[i++] = jca.getValue()) {
                                 jca = (JavaClassAttribute)var11.next();
                              }

                              Method meth = c.getMethod(m3.getValue(), classSig);
                              Object[] oArgs = new Object[bagSize];
                              i = 0;
                              Iterator var13;
                              AttributeEvaluator joa;
                              if (context.isDebugEnabled()) {
                                 a5 = new JavaObjectAttributeBag();

                                 JavaObjectAttribute a;
                                 for(var13 = args.iterator(); var13.hasNext(); oArgs[i++] = a.getValue()) {
                                    joa = (AttributeEvaluator)var13.next();
                                    a = (JavaObjectAttribute)joa.evaluate(context);
                                    a5.add(a);
                                 }
                              } else {
                                 for(var13 = args.iterator(); var13.hasNext(); oArgs[i++] = ((JavaObjectAttribute)joa.evaluate(context)).getValue()) {
                                    joa = (AttributeEvaluator)var13.next();
                                 }
                              }

                              JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(o, oArgs));
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, result, new Bag[]{new JavaClassAttribute(c), t2, m2, classBag, a5});
                              }

                              return result;
                           }
                        } catch (NoSuchMethodException var16) {
                           th = new IndeterminateEvaluationException(var16);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im2, th, new Bag[]{new JavaClassAttribute(c), t2, m2, classBag, a5});
                           }

                           throw th;
                        } catch (InvocationTargetException var17) {
                           th = new IndeterminateEvaluationException(var17);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im2, th, new Bag[]{new JavaClassAttribute(c), t2, m2, classBag, a5});
                           }

                           throw th;
                        } catch (IllegalAccessException var18) {
                           th = new IndeterminateEvaluationException(var18);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im2, th, new Bag[]{new JavaClassAttribute(c), t2, m2, classBag, a5});
                           }

                           throw th;
                        }
                     }
                  } : new JavaObjectAttributeEvaluatorBase() {
                     public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        JavaObjectAttribute t2 = null;
                        StringAttribute m3 = null;
                        Class c = null;
                        Bag classBag = null;

                        IndeterminateEvaluationException thx;
                        try {
                           t2 = (JavaObjectAttribute)target.evaluate(context);
                           m3 = (StringAttribute)methodName.evaluate(context);
                           classBag = signature.evaluateToBag(context);
                           Object o = t2.getValue();
                           c = o.getClass();
                           int bagSize = classBag.size();
                           if (0 != bagSize) {
                              IndeterminateEvaluationException th = new IndeterminateEvaluationException("Argument list size does not formal parameter signature");
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, th, new Bag[]{new JavaClassAttribute(c), t2, m2, classBag, null});
                              }

                              throw th;
                           } else {
                              Method meth = c.getMethod(m3.getValue());
                              JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(o));
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, im2, result, new Bag[]{new JavaClassAttribute(c), t2, m2, classBag, null});
                              }

                              return result;
                           }
                        } catch (NoSuchMethodException var10) {
                           thx = new IndeterminateEvaluationException(var10);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im2, thx, new Bag[]{new JavaClassAttribute(c), t2, m2, classBag, null});
                           }

                           throw thx;
                        } catch (InvocationTargetException var11) {
                           thx = new IndeterminateEvaluationException(var11);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im2, thx, new Bag[]{new JavaClassAttribute(c), t2, m2, classBag, null});
                           }

                           throw thx;
                        } catch (IllegalAccessException var12) {
                           thx = new IndeterminateEvaluationException(var12);
                           if (context.isDebugEnabled()) {
                              ObjectFunctionLibrary.this.debugEval(context, im2, thx, new Bag[]{new JavaClassAttribute(c), t2, m2, classBag, null});
                           }

                           throw thx;
                        }
                     }
                  };
               }
            }
         });
         this.register(imm2, new SimpleFunctionFactoryBase(Type.OBJECT, new Type[]{Type.CLASS, Type.OBJECT, Type.STRING, new MultipleArgumentType(Type.OBJECT, 0)}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments, LoggerSpi log) {
               AttributeEvaluator clazz = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator target = (AttributeEvaluator)arguments.get(1);
               final AttributeEvaluator methodName = (AttributeEvaluator)arguments.get(2);
               final List args = arguments.subList(3, arguments.size());
               final int argsSize = args.size();
               final JavaClassAttribute c1 = (JavaClassAttribute)ConstantUtil.getConstantValue(clazz);
               final StringAttribute m2 = (StringAttribute)ConstantUtil.getConstantValue(methodName);
               if (c1 != null && m2 != null) {
                  Class c = c1.getValue();
                  String mn = m2.getValue();
                  final Method m = null;
                  Method[] methods = c.getMethods();
                  Method[] var14 = methods;
                  int var15 = methods.length;

                  for(int var16 = 0; var16 < var15; ++var16) {
                     Method tm = var14[var16];
                     if (mn.equals(tm.getName()) && tm.getParameterTypes().length == argsSize) {
                        if (log.isDebugEnabled()) {
                           StringBuffer sb = new StringBuffer();
                           sb.append("Found matching method: ");
                           sb.append(tm.getReturnType().getName());
                           sb.append(" ");
                           sb.append(tm.getName());
                           sb.append("(");
                           Class[] params = tm.getParameterTypes();
                           if (params != null) {
                              for(int i = 0; i < params.length; ++i) {
                                 sb.append(params[i].getName());
                                 if (i + 1 < params.length) {
                                    sb.append(", ");
                                 }
                              }
                           }

                           sb.append(")");
                           log.debug(sb.toString());
                        }

                        if (m != null) {
                           if (log.isDebugEnabled()) {
                              log.debug("Multiple method signatures could match; reverting to v1 behavior");
                           }

                           m = null;
                           break;
                        }

                        m = tm;
                     }
                  }

                  if (m != null) {
                     if (argsSize > 0) {
                        return new JavaObjectAttributeEvaluatorBase() {
                           public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                              JavaObjectAttribute t2 = null;
                              Bag a4 = null;

                              IndeterminateEvaluationException th;
                              try {
                                 t2 = (JavaObjectAttribute)target.evaluate(context);
                                 Object o = t2.getValue();
                                 Object[] oArgs = new Object[argsSize];
                                 int i = 0;
                                 Iterator var7;
                                 AttributeEvaluator joa;
                                 if (context.isDebugEnabled()) {
                                    a4 = new JavaObjectAttributeBag();

                                    JavaObjectAttribute a;
                                    for(var7 = args.iterator(); var7.hasNext(); oArgs[i++] = a.getValue()) {
                                       joa = (AttributeEvaluator)var7.next();
                                       a = (JavaObjectAttribute)joa.evaluate(context);
                                       a4.add(a);
                                    }
                                 } else {
                                    for(var7 = args.iterator(); var7.hasNext(); oArgs[i++] = ((JavaObjectAttribute)joa.evaluate(context)).getValue()) {
                                       joa = (AttributeEvaluator)var7.next();
                                    }
                                 }

                                 JavaObjectAttribute result = new JavaObjectAttribute(m.invoke(o, oArgs));
                                 if (context.isDebugEnabled()) {
                                    ObjectFunctionLibrary.this.debugEval(context, imm2, result, new Bag[]{c1, t2, m2, a4});
                                 }

                                 return result;
                              } catch (InvocationTargetException var10) {
                                 th = new IndeterminateEvaluationException(var10);
                                 if (context.isDebugEnabled()) {
                                    ObjectFunctionLibrary.this.debugEval(context, imm2, th, new Bag[]{c1, t2, m2, a4});
                                 }

                                 throw th;
                              } catch (IllegalAccessException var11) {
                                 th = new IndeterminateEvaluationException(var11);
                                 if (context.isDebugEnabled()) {
                                    ObjectFunctionLibrary.this.debugEval(context, imm2, th, new Bag[]{c1, t2, m2, a4});
                                 }

                                 throw th;
                              }
                           }
                        };
                     }

                     return new JavaObjectAttributeEvaluatorBase() {
                        public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           JavaObjectAttribute t2 = null;

                           IndeterminateEvaluationException th;
                           try {
                              t2 = (JavaObjectAttribute)target.evaluate(context);
                              Object o = t2.getValue();
                              JavaObjectAttribute result = new JavaObjectAttribute(m.invoke(o, (Object[])null));
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, imm2, result, new Bag[]{c1, t2, m2, null});
                              }

                              return result;
                           } catch (InvocationTargetException var5) {
                              th = new IndeterminateEvaluationException(var5);
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, imm2, th, new Bag[]{c1, t2, m2, null});
                              }

                              throw th;
                           } catch (IllegalAccessException var6) {
                              th = new IndeterminateEvaluationException(var6);
                              if (context.isDebugEnabled()) {
                                 ObjectFunctionLibrary.this.debugEval(context, imm2, th, new Bag[]{c1, t2, m2, null});
                              }

                              throw th;
                           }
                        }
                     };
                  }
               }

               if (log.isDebugEnabled()) {
                  log.debug("Method not found during parse time checks, revert to v1 runtime behavior");
               }

               return argsSize > 0 ? new JavaObjectAttributeEvaluatorBase() {
                  public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute t2 = null;
                     StringAttribute m3 = null;
                     Bag a4 = null;

                     IndeterminateEvaluationException th;
                     try {
                        t2 = (JavaObjectAttribute)target.evaluate(context);
                        m3 = (StringAttribute)methodName.evaluate(context);
                        Class[] classSig = new Class[argsSize];
                        Object[] oArgs = new Object[argsSize];
                        int i = 0;
                        Iterator var8;
                        AttributeEvaluator joa;
                        Object oxx;
                        if (context.isDebugEnabled()) {
                           a4 = new JavaObjectAttributeBag();

                           Object o;
                           for(var8 = args.iterator(); var8.hasNext(); classSig[i++] = o.getClass()) {
                              joa = (AttributeEvaluator)var8.next();
                              JavaObjectAttribute a = (JavaObjectAttribute)joa.evaluate(context);
                              a4.add(a);
                              o = joa.evaluate(context).getValue();
                              oArgs[i] = o;
                           }
                        } else {
                           for(var8 = args.iterator(); var8.hasNext(); classSig[i++] = oxx.getClass()) {
                              joa = (AttributeEvaluator)var8.next();
                              oxx = ((JavaObjectAttribute)joa.evaluate(context)).getValue();
                              oArgs[i] = oxx;
                           }
                        }

                        Object ox = t2.getValue();
                        Class c = ox.getClass();
                        Method meth = c.getMethod(m3.getValue(), classSig);
                        JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(ox, oArgs));
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm2, result, new Bag[]{c1, t2, m3, a4});
                        }

                        return result;
                     } catch (NoSuchMethodException var12) {
                        th = new IndeterminateEvaluationException(var12);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm2, th, new Bag[]{c1, t2, m3, a4});
                        }

                        throw th;
                     } catch (InvocationTargetException var13) {
                        th = new IndeterminateEvaluationException(var13);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm2, th, new Bag[]{c1, t2, m3, a4});
                        }

                        throw th;
                     } catch (IllegalAccessException var14) {
                        th = new IndeterminateEvaluationException(var14);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm2, th, new Bag[]{c1, t2, m3, a4});
                        }

                        throw th;
                     }
                  }
               } : new JavaObjectAttributeEvaluatorBase() {
                  public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute t2 = null;
                     StringAttribute m3 = null;

                     IndeterminateEvaluationException th;
                     try {
                        t2 = (JavaObjectAttribute)target.evaluate(context);
                        m3 = (StringAttribute)methodName.evaluate(context);
                        Object o = t2.getValue();
                        Class c = o.getClass();
                        Method meth = c.getMethod(m3.getValue());
                        JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(o));
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm2, result, new Bag[]{c1, t2, m3, null});
                        }

                        return result;
                     } catch (NoSuchMethodException var8) {
                        th = new IndeterminateEvaluationException(var8);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm2, th, new Bag[]{c1, t2, m3, null});
                        }

                        throw th;
                     } catch (InvocationTargetException var9) {
                        th = new IndeterminateEvaluationException(var9);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm2, th, new Bag[]{c1, t2, m3, null});
                        }

                        throw th;
                     } catch (IllegalAccessException var10) {
                        th = new IndeterminateEvaluationException(var10);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm2, th, new Bag[]{c1, t2, m3, null});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(imm3, new SimpleFunctionFactoryBase(Type.OBJECT, new Type[]{Type.STRING, Type.OBJECT, Type.STRING, Type.STRING_BAG, new MultipleArgumentType(Type.OBJECT, 0)}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments, LoggerSpi log) {
               AttributeEvaluator clazz = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator target = (AttributeEvaluator)arguments.get(1);
               final AttributeEvaluator methodName = (AttributeEvaluator)arguments.get(2);
               AttributeEvaluator signature = (AttributeEvaluator)arguments.get(3);
               final List args = arguments.subList(4, arguments.size());
               final int argsSize = args.size();
               if (log.isDebugEnabled()) {
                  log.debug("Looking for instance method at parse time");
               }

               final StringAttribute c1 = (StringAttribute)ConstantUtil.getConstantValue(clazz);
               final StringAttribute m2 = (StringAttribute)ConstantUtil.getConstantValue(methodName);
               if (c1 != null && m2 != null) {
                  String cn = c1.getValue();
                  String mn = m2.getValue();
                  Class c = null;

                  try {
                     c = ObjectFunctionLibrary.this.forName(cn);
                  } catch (ClassNotFoundException var26) {
                     if (log.isDebugEnabled()) {
                        log.debug("Class not found: " + cn);
                     }
                  }

                  if (c != null) {
                     if (log.isDebugEnabled()) {
                        log.debug("Find method first by signature, then by argument count");
                     }

                     final Method m = null;
                     final Bag s1 = ConstantUtil.getConstantBagValue(signature);
                     if (s1 != null) {
                        label170: {
                           if (log.isDebugEnabled()) {
                              log.debug("Signature available; parse and locate method");
                           }

                           List sigClasses = new ArrayList();
                           Iterator var17 = s1.iterator();

                           while(var17.hasNext()) {
                              StringAttribute sa = (StringAttribute)var17.next();

                              try {
                                 sigClasses.add(ObjectFunctionLibrary.this.forName(sa.getValue()));
                              } catch (ClassNotFoundException var25) {
                                 if (log.isDebugEnabled()) {
                                    log.debug("Class not found: " + cn);
                                    log.debug("Revert to looking for method by argument count");
                                    break label170;
                                 }
                              }
                           }

                           if (sigClasses.size() != argsSize && sigClasses.size() != 0) {
                              if (log.isDebugEnabled()) {
                                 log.debug("Signature and argument lists inconsistent; returning runtime error");
                              }

                              return new JavaObjectAttributeEvaluatorBase() {
                                 public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                                    IndeterminateEvaluationException th = new IndeterminateEvaluationException("Argument list inconsistent length versus signature");
                                    if (context.isDebugEnabled()) {
                                       ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, null, m2, s1, null});
                                    }

                                    throw th;
                                 }
                              };
                           }

                           try {
                              if (argsSize == 0) {
                                 m = c.getMethod(mn);
                              } else {
                                 m = c.getMethod(mn, (Class[])sigClasses.toArray(new Class[sigClasses.size()]));
                              }
                           } catch (NoSuchMethodException var24) {
                              if (log.isDebugEnabled()) {
                                 log.debug("Method not found by signature; returning runtime error");
                              }

                              return new JavaObjectAttributeEvaluatorBase() {
                                 public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                                    IndeterminateEvaluationException th = new IndeterminateEvaluationException("Class does not contain matching member method");
                                    if (context.isDebugEnabled()) {
                                       ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, null, m2, s1, null});
                                    }

                                    throw th;
                                 }
                              };
                           }

                           if (log.isDebugEnabled()) {
                              if (m != null) {
                                 log.debug("Method found");
                              } else {
                                 log.debug("Method not found; reverting to finding method by argument list");
                              }
                           }
                        }
                     }

                     if (m == null) {
                        Method[] methods = c.getMethods();
                        Method[] var28 = methods;
                        int var29 = methods.length;

                        for(int var19 = 0; var19 < var29; ++var19) {
                           Method tm = var28[var19];
                           if (mn.equals(tm.getName()) && tm.getParameterTypes().length == argsSize) {
                              if (log.isDebugEnabled()) {
                                 StringBuffer sb = new StringBuffer();
                                 sb.append("Found matching method: ");
                                 sb.append(tm.getReturnType().getName());
                                 sb.append(" ");
                                 sb.append(tm.getName());
                                 sb.append("(");
                                 Class[] params = tm.getParameterTypes();
                                 if (params != null) {
                                    for(int i = 0; i < params.length; ++i) {
                                       sb.append(params[i].getName());
                                       if (i + 1 < params.length) {
                                          sb.append(", ");
                                       }
                                    }
                                 }

                                 sb.append(")");
                                 log.debug(sb.toString());
                              }

                              if (m != null) {
                                 if (log.isDebugEnabled()) {
                                    log.debug("Multiple method signatures could match; reverting to v1 behavior");
                                 }

                                 m = null;
                                 break;
                              }

                              m = tm;
                           }
                        }
                     }

                     if (m != null) {
                        if (log.isDebugEnabled()) {
                           log.debug("Using located method");
                        }

                        if (argsSize > 0) {
                           return new JavaObjectAttributeEvaluatorBase() {
                              public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                                 JavaObjectAttribute t2 = null;
                                 Bag a5 = null;

                                 IndeterminateEvaluationException th;
                                 try {
                                    t2 = (JavaObjectAttribute)target.evaluate(context);
                                    Object o = t2.getValue();
                                    Object[] oArgs = new Object[argsSize];
                                    int i = 0;
                                    Iterator var7;
                                    AttributeEvaluator joa;
                                    if (context.isDebugEnabled()) {
                                       a5 = new JavaObjectAttributeBag();

                                       JavaObjectAttribute a;
                                       for(var7 = args.iterator(); var7.hasNext(); oArgs[i++] = a.getValue()) {
                                          joa = (AttributeEvaluator)var7.next();
                                          a = (JavaObjectAttribute)joa.evaluate(context);
                                          a5.add(a);
                                       }
                                    } else {
                                       for(var7 = args.iterator(); var7.hasNext(); oArgs[i++] = ((JavaObjectAttribute)joa.evaluate(context)).getValue()) {
                                          joa = (AttributeEvaluator)var7.next();
                                       }
                                    }

                                    JavaObjectAttribute result = new JavaObjectAttribute(m.invoke(o, oArgs));
                                    if (context.isDebugEnabled()) {
                                       ObjectFunctionLibrary.this.debugEval(context, imm3, result, new Bag[]{c1, t2, m2, s1, a5});
                                    }

                                    return result;
                                 } catch (InvocationTargetException var10) {
                                    th = new IndeterminateEvaluationException(var10);
                                    if (context.isDebugEnabled()) {
                                       ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, t2, m2, s1, a5});
                                    }

                                    throw th;
                                 } catch (IllegalAccessException var11) {
                                    th = new IndeterminateEvaluationException(var11);
                                    if (context.isDebugEnabled()) {
                                       ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, t2, m2, s1, a5});
                                    }

                                    throw th;
                                 }
                              }
                           };
                        }

                        return new JavaObjectAttributeEvaluatorBase() {
                           public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                              JavaObjectAttribute t2 = null;

                              IndeterminateEvaluationException th;
                              try {
                                 t2 = (JavaObjectAttribute)target.evaluate(context);
                                 Object o = t2.getValue();
                                 JavaObjectAttribute result = new JavaObjectAttribute(m.invoke(o, (Object[])null));
                                 if (context.isDebugEnabled()) {
                                    ObjectFunctionLibrary.this.debugEval(context, imm3, result, new Bag[]{c1, t2, m2, s1, null});
                                 }

                                 return result;
                              } catch (InvocationTargetException var5) {
                                 th = new IndeterminateEvaluationException(var5);
                                 if (context.isDebugEnabled()) {
                                    ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, t2, m2, s1, null});
                                 }

                                 throw th;
                              } catch (IllegalAccessException var6) {
                                 th = new IndeterminateEvaluationException(var6);
                                 if (context.isDebugEnabled()) {
                                    ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, t2, m2, s1, null});
                                 }

                                 throw th;
                              }
                           }
                        };
                     }
                  }
               }

               if (log.isDebugEnabled()) {
                  log.debug("Method not found during parse time checks, revert to v1 runtime behavior");
               }

               return argsSize > 0 ? new JavaObjectAttributeEvaluatorBase() {
                  public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute t2 = null;
                     StringAttribute m3 = null;
                     Bag a5 = null;

                     IndeterminateEvaluationException th;
                     try {
                        t2 = (JavaObjectAttribute)target.evaluate(context);
                        m3 = (StringAttribute)methodName.evaluate(context);
                        Class[] classSig = new Class[argsSize];
                        Object[] oArgs = new Object[argsSize];
                        int i = 0;
                        Iterator var8;
                        AttributeEvaluator joa;
                        Object oxx;
                        if (context.isDebugEnabled()) {
                           a5 = new JavaObjectAttributeBag();

                           Object o;
                           for(var8 = args.iterator(); var8.hasNext(); classSig[i++] = o.getClass()) {
                              joa = (AttributeEvaluator)var8.next();
                              JavaObjectAttribute a = (JavaObjectAttribute)joa.evaluate(context);
                              a5.add(a);
                              o = a.getValue();
                              oArgs[i] = o;
                           }
                        } else {
                           for(var8 = args.iterator(); var8.hasNext(); classSig[i++] = oxx.getClass()) {
                              joa = (AttributeEvaluator)var8.next();
                              oxx = ((JavaObjectAttribute)joa.evaluate(context)).getValue();
                              oArgs[i] = oxx;
                           }
                        }

                        Object ox = t2.getValue();
                        Class c = ox.getClass();
                        Method meth = c.getMethod(m3.getValue(), classSig);
                        JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(ox, oArgs));
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm3, result, new Bag[]{c1, t2, m3, null, a5});
                        }

                        return result;
                     } catch (NoSuchMethodException var12) {
                        th = new IndeterminateEvaluationException(var12);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, t2, m3, null, a5});
                        }

                        throw th;
                     } catch (InvocationTargetException var13) {
                        th = new IndeterminateEvaluationException(var13);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, t2, m3, null, a5});
                        }

                        throw th;
                     } catch (IllegalAccessException var14) {
                        th = new IndeterminateEvaluationException(var14);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, t2, m3, null, a5});
                        }

                        throw th;
                     }
                  }
               } : new JavaObjectAttributeEvaluatorBase() {
                  public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute t2 = null;
                     StringAttribute m3 = null;

                     IndeterminateEvaluationException th;
                     try {
                        t2 = (JavaObjectAttribute)target.evaluate(context);
                        m3 = (StringAttribute)methodName.evaluate(context);
                        Object o = t2.getValue();
                        Class c = o.getClass();
                        Method meth = c.getMethod(m3.getValue());
                        JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke(o));
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm3, result, new Bag[]{c1, t2, m3, null, null});
                        }

                        return result;
                     } catch (NoSuchMethodException var8) {
                        th = new IndeterminateEvaluationException(var8);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, t2, m3, null, null});
                        }

                        throw th;
                     } catch (InvocationTargetException var9) {
                        th = new IndeterminateEvaluationException(var9);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, t2, m3, null, null});
                        }

                        throw th;
                     } catch (IllegalAccessException var10) {
                        th = new IndeterminateEvaluationException(var10);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, imm3, th, new Bag[]{c1, t2, m3, null, null});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(occ, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.OBJECT, Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     JavaObjectAttribute p2 = (JavaObjectAttribute)param2.evaluate(context);
                     Object col = p1.getValue();
                     Object item = p2.getValue();
                     BooleanAttribute result = col instanceof Collection && ((Collection)col).contains(item) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                     if (context.isDebugEnabled()) {
                        ObjectFunctionLibrary.this.debugEval(context, occ, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(occa, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.OBJECT, Type.OBJECT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     JavaObjectAttribute p1 = (JavaObjectAttribute)param1.evaluate(context);
                     JavaObjectAttribute p2 = (JavaObjectAttribute)param2.evaluate(context);
                     Object col = p1.getValue();
                     Object item = p2.getValue();
                     BooleanAttribute result = col instanceof Collection && item instanceof Collection && ((Collection)col).containsAll((Collection)item) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                     if (context.isDebugEnabled()) {
                        ObjectFunctionLibrary.this.debugEval(context, occa, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(sm, new SimpleFunctionFactoryBase(Type.OBJECT, new Type[]{Type.STRING, Type.STRING, Type.STRING_BAG, new MultipleArgumentType(Type.OBJECT, 0)}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments, LoggerSpi log) {
               final AttributeEvaluator clazz = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator methodName = (AttributeEvaluator)arguments.get(1);
               AttributeEvaluator signature = (AttributeEvaluator)arguments.get(2);
               final List args = arguments.subList(3, arguments.size());
               final int argsSize = args.size();
               if (log.isDebugEnabled()) {
                  log.debug("Looking for instance method at parse time");
               }

               final StringAttribute c1 = (StringAttribute)ConstantUtil.getConstantValue(clazz);
               final StringAttribute m2 = (StringAttribute)ConstantUtil.getConstantValue(methodName);
               if (c1 != null && m2 != null) {
                  String cn = c1.getValue();
                  String mn = m2.getValue();
                  Class c = null;

                  try {
                     c = ObjectFunctionLibrary.this.forName(cn);
                  } catch (ClassNotFoundException var25) {
                     if (log.isDebugEnabled()) {
                        log.debug("Class not found: " + cn);
                     }
                  }

                  if (c != null) {
                     if (log.isDebugEnabled()) {
                        log.debug("Find method first by signature, then by argument count");
                     }

                     final Method m = null;
                     final Bag s1 = ConstantUtil.getConstantBagValue(signature);
                     if (s1 != null) {
                        label170: {
                           if (log.isDebugEnabled()) {
                              log.debug("Signature available; parse and locate method");
                           }

                           List sigClasses = new ArrayList();
                           Iterator var16 = s1.iterator();

                           while(var16.hasNext()) {
                              StringAttribute sa = (StringAttribute)var16.next();

                              try {
                                 sigClasses.add(ObjectFunctionLibrary.this.forName(sa.getValue()));
                              } catch (ClassNotFoundException var24) {
                                 if (log.isDebugEnabled()) {
                                    log.debug("Class not found: " + cn);
                                    log.debug("Revert to looking for method by argument count");
                                    break label170;
                                 }
                              }
                           }

                           if (sigClasses.size() != argsSize && sigClasses.size() != 0) {
                              if (log.isDebugEnabled()) {
                                 log.debug("Signature and argument lists inconsistent; returning runtime error");
                              }

                              return new JavaObjectAttributeEvaluatorBase() {
                                 public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                                    IndeterminateEvaluationException th = new IndeterminateEvaluationException("Argument list inconsistent length versus signature");
                                    if (context.isDebugEnabled()) {
                                       ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{c1, null, m2, s1, null});
                                    }

                                    throw th;
                                 }
                              };
                           }

                           try {
                              if (argsSize == 0) {
                                 m = c.getMethod(mn);
                              } else {
                                 m = c.getMethod(mn, (Class[])sigClasses.toArray(new Class[sigClasses.size()]));
                              }
                           } catch (NoSuchMethodException var23) {
                              if (log.isDebugEnabled()) {
                                 log.debug("Method not found by signature; returning runtime error");
                              }

                              return new JavaObjectAttributeEvaluatorBase() {
                                 public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                                    IndeterminateEvaluationException th = new IndeterminateEvaluationException("Class does not contain matching member method");
                                    if (context.isDebugEnabled()) {
                                       ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{c1, null, m2, s1, null});
                                    }

                                    throw th;
                                 }
                              };
                           }

                           if (log.isDebugEnabled()) {
                              if (m != null) {
                                 log.debug("Method found");
                              } else {
                                 log.debug("Method not found; reverting to finding method by argument list");
                              }
                           }
                        }
                     }

                     if (m == null) {
                        Method[] methods = c.getMethods();
                        Method[] var27 = methods;
                        int var28 = methods.length;

                        for(int var18 = 0; var18 < var28; ++var18) {
                           Method tm = var27[var18];
                           if (mn.equals(tm.getName()) && tm.getParameterTypes().length == argsSize) {
                              if (log.isDebugEnabled()) {
                                 StringBuffer sb = new StringBuffer();
                                 sb.append("Found matching method: ");
                                 sb.append(tm.getReturnType().getName());
                                 sb.append(" ");
                                 sb.append(tm.getName());
                                 sb.append("(");
                                 Class[] params = tm.getParameterTypes();
                                 if (params != null) {
                                    for(int i = 0; i < params.length; ++i) {
                                       sb.append(params[i].getName());
                                       if (i + 1 < params.length) {
                                          sb.append(", ");
                                       }
                                    }
                                 }

                                 sb.append(")");
                                 log.debug(sb.toString());
                              }

                              if (m != null) {
                                 if (log.isDebugEnabled()) {
                                    log.debug("Multiple method signatures could match; reverting to v1 behavior");
                                 }

                                 m = null;
                                 break;
                              }

                              m = tm;
                           }
                        }
                     }

                     if (m != null) {
                        if (log.isDebugEnabled()) {
                           log.debug("Using located method");
                        }

                        if (argsSize > 0) {
                           return new JavaObjectAttributeEvaluatorBase() {
                              public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                                 Bag a5 = null;

                                 IndeterminateEvaluationException th;
                                 try {
                                    Object[] oArgs = new Object[argsSize];
                                    int i = 0;
                                    Iterator var5;
                                    AttributeEvaluator joa;
                                    if (context.isDebugEnabled()) {
                                       a5 = new JavaObjectAttributeBag();

                                       JavaObjectAttribute a;
                                       for(var5 = args.iterator(); var5.hasNext(); oArgs[i++] = a.getValue()) {
                                          joa = (AttributeEvaluator)var5.next();
                                          a = (JavaObjectAttribute)joa.evaluate(context);
                                          a5.add(a);
                                       }
                                    } else {
                                       for(var5 = args.iterator(); var5.hasNext(); oArgs[i++] = ((JavaObjectAttribute)joa.evaluate(context)).getValue()) {
                                          joa = (AttributeEvaluator)var5.next();
                                       }
                                    }

                                    JavaObjectAttribute result = new JavaObjectAttribute(m.invoke((Object)null, oArgs));
                                    if (context.isDebugEnabled()) {
                                       ObjectFunctionLibrary.this.debugEval(context, sm, result, new Bag[]{c1, m2, s1, a5});
                                    }

                                    return result;
                                 } catch (InvocationTargetException var8) {
                                    th = new IndeterminateEvaluationException(var8);
                                    if (context.isDebugEnabled()) {
                                       ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{c1, m2, s1, a5});
                                    }

                                    throw th;
                                 } catch (IllegalAccessException var9) {
                                    th = new IndeterminateEvaluationException(var9);
                                    if (context.isDebugEnabled()) {
                                       ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{c1, m2, s1, a5});
                                    }

                                    throw th;
                                 }
                              }
                           };
                        }

                        return new JavaObjectAttributeEvaluatorBase() {
                           public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                              IndeterminateEvaluationException th;
                              try {
                                 JavaObjectAttribute result = new JavaObjectAttribute(m.invoke((Object)null, (Object[])null));
                                 if (context.isDebugEnabled()) {
                                    ObjectFunctionLibrary.this.debugEval(context, sm, result, new Bag[]{c1, m2, s1, null});
                                 }

                                 return result;
                              } catch (InvocationTargetException var4) {
                                 th = new IndeterminateEvaluationException(var4);
                                 if (context.isDebugEnabled()) {
                                    ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{c1, m2, s1, null});
                                 }

                                 throw th;
                              } catch (IllegalAccessException var5) {
                                 th = new IndeterminateEvaluationException(var5);
                                 if (context.isDebugEnabled()) {
                                    ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{c1, m2, s1, null});
                                 }

                                 throw th;
                              }
                           }
                        };
                     }
                  }
               }

               if (log.isDebugEnabled()) {
                  log.debug("Method not found during parse time checks, revert to v1 runtime behavior");
               }

               return argsSize > 0 ? new JavaObjectAttributeEvaluatorBase() {
                  public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute cprime = null;
                     StringAttribute m3 = null;
                     Bag a5 = null;

                     IndeterminateEvaluationException th;
                     try {
                        cprime = (StringAttribute)clazz.evaluate(context);
                        m3 = (StringAttribute)methodName.evaluate(context);
                        Class[] classSig = new Class[argsSize];
                        Object[] oArgs = new Object[argsSize];
                        int i = 0;
                        Iterator var8;
                        AttributeEvaluator joa;
                        JavaObjectAttribute result;
                        Object ox;
                        if (context.isDebugEnabled()) {
                           a5 = new JavaObjectAttributeBag();

                           Object o;
                           for(var8 = args.iterator(); var8.hasNext(); classSig[i++] = o.getClass()) {
                              joa = (AttributeEvaluator)var8.next();
                              result = (JavaObjectAttribute)joa.evaluate(context);
                              a5.add(result);
                              o = result.getValue();
                              oArgs[i] = o;
                           }
                        } else {
                           for(var8 = args.iterator(); var8.hasNext(); classSig[i++] = ox.getClass()) {
                              joa = (AttributeEvaluator)var8.next();
                              ox = ((JavaObjectAttribute)joa.evaluate(context)).getValue();
                              oArgs[i] = ox;
                           }
                        }

                        Class c = ObjectFunctionLibrary.this.forName(cprime.getValue());
                        Method meth = c.getMethod(m3.getValue(), classSig);
                        result = new JavaObjectAttribute(meth.invoke((Object)null, oArgs));
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, sm, result, new Bag[]{cprime, m3, null, a5});
                        }

                        return result;
                     } catch (ClassNotFoundException var12) {
                        th = new IndeterminateEvaluationException(var12);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{cprime, m3, null, a5});
                        }

                        throw th;
                     } catch (NoSuchMethodException var13) {
                        th = new IndeterminateEvaluationException(var13);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{cprime, m3, null, a5});
                        }

                        throw th;
                     } catch (InvocationTargetException var14) {
                        th = new IndeterminateEvaluationException(var14);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{cprime, m3, null, a5});
                        }

                        throw th;
                     } catch (IllegalAccessException var15) {
                        th = new IndeterminateEvaluationException(var15);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{cprime, m3, null, a5});
                        }

                        throw th;
                     }
                  }
               } : new JavaObjectAttributeEvaluatorBase() {
                  public JavaObjectAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute cprime = null;
                     JavaObjectAttribute t2 = null;
                     StringAttribute m3 = null;

                     IndeterminateEvaluationException th;
                     try {
                        cprime = (StringAttribute)clazz.evaluate(context);
                        m3 = (StringAttribute)methodName.evaluate(context);
                        Class c = ObjectFunctionLibrary.this.forName(cprime.getValue());
                        Method meth = c.getMethod(m3.getValue());
                        JavaObjectAttribute result = new JavaObjectAttribute(meth.invoke((Object)null));
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, sm, result, new Bag[]{cprime, (Bag)t2, m3, null, null});
                        }

                        return result;
                     } catch (ClassNotFoundException var8) {
                        th = new IndeterminateEvaluationException(var8);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{cprime, (Bag)t2, m3, null, null});
                        }

                        throw th;
                     } catch (NoSuchMethodException var9) {
                        th = new IndeterminateEvaluationException(var9);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{cprime, (Bag)t2, m3, null, null});
                        }

                        throw th;
                     } catch (InvocationTargetException var10) {
                        th = new IndeterminateEvaluationException(var10);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{cprime, (Bag)t2, m3, null, null});
                        }

                        throw th;
                     } catch (IllegalAccessException var11) {
                        th = new IndeterminateEvaluationException(var11);
                        if (context.isDebugEnabled()) {
                           ObjectFunctionLibrary.this.debugEval(context, sm, th, new Bag[]{cprime, (Bag)t2, m3, null, null});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
      } catch (java.net.URISyntaxException var38) {
         throw new URISyntaxException(var38);
      }
   }

   public DateAttribute convertObjectToDate(EvaluationCtx context, JavaObjectAttribute p1) throws IndeterminateEvaluationException {
      Object o = p1.getValue();
      DateAttribute result;
      if (o instanceof Calendar) {
         result = new DateAttribute((Calendar)o);
         if (context.isDebugEnabled()) {
            this.debugEval(context, this.otda, result, new Bag[]{p1});
         }

         return result;
      } else if (o instanceof Date) {
         Calendar c = Calendar.getInstance();
         c.setTime((Date)o);
         DateAttribute result = new DateAttribute(c);
         if (context.isDebugEnabled()) {
            this.debugEval(context, this.otda, result, new Bag[]{p1});
         }

         return result;
      } else if (o == null) {
         IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to date");
         if (context.isDebugEnabled()) {
            this.debugEval(context, this.otda, th, new Bag[]{p1});
         }

         throw th;
      } else {
         try {
            result = new DateAttribute(o.toString(), true);
            if (context.isDebugEnabled()) {
               this.debugEval(context, this.otda, result, new Bag[]{p1});
            }

            return result;
         } catch (InvalidAttributeException var6) {
            IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
            if (context.isDebugEnabled()) {
               this.debugEval(context, this.otda, th, new Bag[]{p1});
            }

            throw th;
         }
      }
   }

   public DateTimeAttribute convertObjectToDateTime(EvaluationCtx context, JavaObjectAttribute p1) throws IndeterminateEvaluationException {
      Object o = p1.getValue();
      if (o instanceof Timestamp) {
         Timestamp ts = (Timestamp)o;
         Calendar c = Calendar.getInstance();
         c.setTimeInMillis(ts.getTime());
         DateTimeAttribute result = new DateTimeAttribute(c, (long)ts.getNanos() % 1000000L);
         if (context.isDebugEnabled()) {
            this.debugEval(context, this.otdt, result, new Bag[]{p1});
         }

         return result;
      } else {
         DateTimeAttribute result;
         if (o instanceof Calendar) {
            result = new DateTimeAttribute((Calendar)o);
            if (context.isDebugEnabled()) {
               this.debugEval(context, this.otdt, result, new Bag[]{p1});
            }

            return result;
         } else if (o instanceof Date) {
            Calendar c = Calendar.getInstance();
            c.setTime((Date)o);
            DateTimeAttribute result = new DateTimeAttribute(c);
            if (context.isDebugEnabled()) {
               this.debugEval(context, this.otdt, result, new Bag[]{p1});
            }

            return result;
         } else if (o == null) {
            IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to dateTime");
            if (context.isDebugEnabled()) {
               this.debugEval(context, this.otdt, th, new Bag[]{p1});
            }

            throw th;
         } else {
            try {
               result = new DateTimeAttribute(o.toString(), true);
               if (context.isDebugEnabled()) {
                  this.debugEval(context, this.otdt, result, new Bag[]{p1});
               }

               return result;
            } catch (InvalidAttributeException var7) {
               IndeterminateEvaluationException th = new IndeterminateEvaluationException(var7);
               if (context.isDebugEnabled()) {
                  this.debugEval(context, this.otdt, th, new Bag[]{p1});
               }

               throw th;
            }
         }
      }
   }

   public TimeAttribute convertObjectToTime(EvaluationCtx context, JavaObjectAttribute p1) throws IndeterminateEvaluationException {
      Object o = p1.getValue();
      TimeAttribute result;
      if (o instanceof Calendar) {
         result = new TimeAttribute((Calendar)o);
         if (context.isDebugEnabled()) {
            this.debugEval(context, this.ott, result, new Bag[]{p1});
         }

         return result;
      } else if (o instanceof Date) {
         Calendar c = Calendar.getInstance();
         c.setTime((Date)o);
         TimeAttribute result = new TimeAttribute(c);
         if (context.isDebugEnabled()) {
            this.debugEval(context, this.ott, result, new Bag[]{p1});
         }

         return result;
      } else if (o == null) {
         IndeterminateEvaluationException th = new IndeterminateEvaluationException("Cannot convert null object to time");
         if (context.isDebugEnabled()) {
            this.debugEval(context, this.ott, th, new Bag[]{p1});
         }

         throw th;
      } else {
         try {
            result = new TimeAttribute(o.toString(), true);
            if (context.isDebugEnabled()) {
               this.debugEval(context, this.ott, result, new Bag[]{p1});
            }

            return result;
         } catch (InvalidAttributeException var6) {
            IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
            if (context.isDebugEnabled()) {
               this.debugEval(context, this.ott, th, new Bag[]{p1});
            }

            throw th;
         }
      }
   }
}
