package com.bea.security.providers.xacml.entitlement.function;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.CharacterAttribute;
import com.bea.common.security.xacml.attr.DateAttribute;
import com.bea.common.security.xacml.attr.DateTimeAttribute;
import com.bea.common.security.xacml.attr.DayTimeDurationAttribute;
import com.bea.common.security.xacml.attr.DecimalAttribute;
import com.bea.common.security.xacml.attr.DoubleAttribute;
import com.bea.common.security.xacml.attr.FloatAttribute;
import com.bea.common.security.xacml.attr.IntegerAttribute;
import com.bea.common.security.xacml.attr.LongAttribute;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.attr.TimeAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.CharacterAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DateAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DateTimeAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DayTimeDurationAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DecimalAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DoubleAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.FloatAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.IntegerAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.LongAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.StringAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.TimeAttributeEvaluatorBase;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class EntitlementFunctionLibrary extends SimpleFunctionLibraryBase {
   private static final long NANOS_PER_MILLI = 1000000L;
   private static final int SECONDS_PER_MINUTE = 60;
   private static final int MINUTES_PER_HOUR = 60;

   public EntitlementFunctionLibrary(final boolean inProductionMode, final boolean inSecureMode) throws URISyntaxException {
      try {
         final URI idm = new URI("urn:bea:xacml:2.0:function:in-development-mode");
         final URI ism = new URI("urn:bea:xacml:2.0:function:in-secure-mode");
         final URI dom = new URI("urn:bea:xacml:2.0:function:dateTime-dayOfMonth");
         final URI domm = new URI("urn:bea:xacml:2.0:function:dateTime-dayOfMonthMaximum");
         final URI dow = new URI("urn:bea:xacml:2.0:function:dateTime-dayOfWeek");
         final URI sod = new URI("urn:bea:xacml:2.0:function:dateTime-secondsOfDay");
         final URI tzo = new URI("urn:bea:xacml:2.0:function:dayTimeDuration-timeZoneOffset");
         final URI ftd = new URI("urn:bea:xacml:2.0:function:float-to-double");
         final URI ftx = new URI("urn:bea:xacml:2.0:function:float-to-decmial");
         final URI ftl = new URI("urn:bea:xacml:2.0:function:float-to-long");
         final URI fti = new URI("urn:bea:xacml:2.0:function:float-to-integer");
         final URI ltd = new URI("urn:bea:xacml:2.0:function:long-to-double");
         final URI ltx = new URI("urn:bea:xacml:2.0:function:long-to-decimal");
         final URI ltf = new URI("urn:bea:xacml:2.0:function:long-to-float");
         URI lti = new URI("urn:bea:xacml:2.0:function:long-to-integer");
         final URI itx = new URI("urn:bea:xacml:2.0:function:integer-to-decimal");
         final URI itf = new URI("urn:bea:xacml:2.0:function:integer-to-float");
         final URI itl = new URI("urn:bea:xacml:2.0:function:integer-to-long");
         final URI dtx = new URI("urn:bea:xacml:2.0:function:double-to-decimal");
         final URI dtf = new URI("urn:bea:xacml:2.0:function:double-to-float");
         final URI dtl = new URI("urn:bea:xacml:2.0:function:double-to-long");
         final URI xtd = new URI("urn:bea:xacml:2.0:function:decimal-to-double");
         final URI xtf = new URI("urn:bea:xacml:2.0:function:decimal-to-float");
         final URI xtl = new URI("urn:bea:xacml:2.0:function:decimal-to-long");
         final URI xti = new URI("urn:bea:xacml:2.0:function:decmial-to-integer");
         final URI std = new URI("urn:bea:xacml:2.0:function:string-to-double");
         final URI stl = new URI("urn:bea:xacml:2.0:function:string-to-long");
         final URI sti = new URI("urn:bea:xacml:2.0:function:string-to-integer");
         final URI stf = new URI("urn:bea:xacml:2.0:function:string-to-float");
         final URI stx = new URI("urn:bea:xacml:2.0:function:string-to-decimal");
         final URI stc = new URI("urn:bea:xacml:2.0:function:string-to-character");
         final URI cts = new URI("urn:bea:xacml:2.0:function:character-to-string");
         final URI stdt = new URI("urn:bea:xacml:2.0:function:string-to-dateTime");
         final URI stda = new URI("urn:bea:xacml:2.0:function:string-to-date");
         final URI stt = new URI("urn:bea:xacml:2.0:function:string-to-time");
         final URI tgt = new URI("urn:bea:xacml:2.0:function:time-calendar-greater-than");
         final URI tgtoe = new URI("urn:bea:xacml:2.0:function:time-calendar-greater-than-or-equal");
         final URI tlt = new URI("urn:bea:xacml:2.0:function:time-calendar-less-than");
         final URI tltoe = new URI("urn:bea:xacml:2.0:function:time-calendar-less-than-or-equal");
         this.register(idm, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[0]) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     BooleanAttribute result = inProductionMode ? BooleanAttribute.FALSE : BooleanAttribute.TRUE;
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, idm, result, new Bag[0]);
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ism, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[0]) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     BooleanAttribute result = inSecureMode ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, ism, result, new Bag[0]);
                     }

                     return result;
                  }
               };
            }
         });
         this.register(dom, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.DATE_TIME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DateTimeAttribute p1 = (DateTimeAttribute)param1.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getValue().get(5));
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, dom, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(domm, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.DATE_TIME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DateTimeAttribute p1 = (DateTimeAttribute)param1.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getValue().getActualMaximum(5));
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, domm, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(dow, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.DATE_TIME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DateTimeAttribute p1 = (DateTimeAttribute)param1.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getValue().get(7));
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, dow, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(sod, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.DATE_TIME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DateTimeAttribute p1 = (DateTimeAttribute)param1.evaluate(context);
                     Calendar cal = p1.getValue();
                     IntegerAttribute result = new IntegerAttribute((cal.get(11) * 60 + cal.get(12)) * 60 + cal.get(13));
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, sod, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(tzo, new SimpleFunctionFactoryBase(Type.DAY_TIME_DURATION, new Type[0]) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               return new DayTimeDurationAttributeEvaluatorBase() {
                  public DayTimeDurationAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        DayTimeDurationAttribute result = new DayTimeDurationAttribute(0, 0, 0, 0, (long)TimeZone.getDefault().getRawOffset() * 1000000L);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, tzo, result, new Bag[0]);
                        }

                        return result;
                     } catch (InvalidAttributeException var4) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var4);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, tzo, th, new Bag[0]);
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(ftd, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.FLOAT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     FloatAttribute p1 = (FloatAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(p1.getValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, ftd, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ftx, new SimpleFunctionFactoryBase(Type.DECIMAL, new Type[]{Type.FLOAT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DecimalAttributeEvaluatorBase() {
                  public DecimalAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     FloatAttribute p1 = (FloatAttribute)param1.evaluate(context);
                     DecimalAttribute result = new DecimalAttribute(p1.getValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, ftx, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ftl, new SimpleFunctionFactoryBase(Type.LONG, new Type[]{Type.FLOAT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new LongAttributeEvaluatorBase() {
                  public LongAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     FloatAttribute p1 = (FloatAttribute)param1.evaluate(context);
                     LongAttribute result = new LongAttribute(p1.getValue().longValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, ftl, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(fti, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.FLOAT}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     FloatAttribute p1 = (FloatAttribute)param1.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getValue().intValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, fti, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ltd, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.LONG}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     LongAttribute p1 = (LongAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute((double)p1.getValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, ltd, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ltx, new SimpleFunctionFactoryBase(Type.DECIMAL, new Type[]{Type.LONG}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DecimalAttributeEvaluatorBase() {
                  public DecimalAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     LongAttribute p1 = (LongAttribute)param1.evaluate(context);
                     DecimalAttribute result = new DecimalAttribute(new Double(p1.getValue().doubleValue()));
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, ltx, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ltf, new SimpleFunctionFactoryBase(Type.FLOAT, new Type[]{Type.LONG}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new FloatAttributeEvaluatorBase() {
                  public FloatAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     LongAttribute p1 = (LongAttribute)param1.evaluate(context);
                     FloatAttribute result = new FloatAttribute((double)p1.getValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, ltf, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(lti, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.LONG}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     LongAttribute p1 = (LongAttribute)param1.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getValue().intValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, ltf, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(itx, new SimpleFunctionFactoryBase(Type.DECIMAL, new Type[]{Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DecimalAttributeEvaluatorBase() {
                  public DecimalAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     IntegerAttribute p1 = (IntegerAttribute)param1.evaluate(context);
                     DecimalAttribute result = new DecimalAttribute(new Double(p1.getValue().doubleValue()));
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, itx, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(itf, new SimpleFunctionFactoryBase(Type.FLOAT, new Type[]{Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new FloatAttributeEvaluatorBase() {
                  public FloatAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     IntegerAttribute p1 = (IntegerAttribute)param1.evaluate(context);
                     FloatAttribute result = new FloatAttribute((double)p1.getValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, itf, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(itl, new SimpleFunctionFactoryBase(Type.LONG, new Type[]{Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new LongAttributeEvaluatorBase() {
                  public LongAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     IntegerAttribute p1 = (IntegerAttribute)param1.evaluate(context);
                     LongAttribute result = new LongAttribute((long)p1.getValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, itl, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(dtx, new SimpleFunctionFactoryBase(Type.DECIMAL, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DecimalAttributeEvaluatorBase() {
                  public DecimalAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DecimalAttribute result = new DecimalAttribute(p1.getValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, dtx, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(dtf, new SimpleFunctionFactoryBase(Type.FLOAT, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new FloatAttributeEvaluatorBase() {
                  public FloatAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     FloatAttribute result = new FloatAttribute(p1.getValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, dtf, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(dtl, new SimpleFunctionFactoryBase(Type.LONG, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new LongAttributeEvaluatorBase() {
                  public LongAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     LongAttribute result = new LongAttribute(p1.getValue().longValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, dtl, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(xtd, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DECIMAL}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DecimalAttribute p1 = (DecimalAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(p1.getValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, xtd, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(xtf, new SimpleFunctionFactoryBase(Type.FLOAT, new Type[]{Type.DECIMAL}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new FloatAttributeEvaluatorBase() {
                  public FloatAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DecimalAttribute p1 = (DecimalAttribute)param1.evaluate(context);
                     FloatAttribute result = new FloatAttribute(p1.getValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, xtf, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(xtl, new SimpleFunctionFactoryBase(Type.LONG, new Type[]{Type.DECIMAL}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new LongAttributeEvaluatorBase() {
                  public LongAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DecimalAttribute p1 = (DecimalAttribute)param1.evaluate(context);
                     LongAttribute result = new LongAttribute(p1.getValue().longValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, xtl, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(xti, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.DECIMAL}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DecimalAttribute p1 = (DecimalAttribute)param1.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getValue().intValue());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, xti, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(std, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                        DoubleAttribute result = new DoubleAttribute(p1.getValue());
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, std, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (InvalidAttributeException var4) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var4);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, std, th, new Bag[0]);
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(stx, new SimpleFunctionFactoryBase(Type.DECIMAL, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DecimalAttributeEvaluatorBase() {
                  public DecimalAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                        DecimalAttribute result = new DecimalAttribute(p1.getValue());
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stx, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (InvalidAttributeException var4) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var4);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stx, th, new Bag[0]);
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(stl, new SimpleFunctionFactoryBase(Type.LONG, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new LongAttributeEvaluatorBase() {
                  public LongAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                        LongAttribute result = new LongAttribute(p1.getValue());
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stl, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (InvalidAttributeException var4) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var4);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stl, th, new Bag[0]);
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(sti, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                        IntegerAttribute result = new IntegerAttribute(p1.getValue());
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, sti, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (InvalidAttributeException var4) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var4);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, sti, th, new Bag[0]);
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(stf, new SimpleFunctionFactoryBase(Type.FLOAT, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new FloatAttributeEvaluatorBase() {
                  public FloatAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                        FloatAttribute result = new FloatAttribute(p1.getValue());
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stf, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (InvalidAttributeException var4) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var4);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stf, th, new Bag[0]);
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(stc, new SimpleFunctionFactoryBase(Type.CHARACTER, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new CharacterAttributeEvaluatorBase() {
                  public CharacterAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                        CharacterAttribute result = new CharacterAttribute(p1.getValue());
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stc, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (InvalidAttributeException var4) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var4);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stc, th, new Bag[0]);
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(cts, new SimpleFunctionFactoryBase(Type.STRING, new Type[]{Type.CHARACTER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new StringAttributeEvaluatorBase() {
                  public StringAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     CharacterAttribute p1 = (CharacterAttribute)param1.evaluate(context);
                     StringAttribute result = new StringAttribute(p1.getValue().toString());
                     if (context.isDebugEnabled()) {
                        EntitlementFunctionLibrary.this.debugEval(context, cts, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(stdt, new SimpleFunctionFactoryBase(Type.DATE_TIME, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DateTimeAttributeEvaluatorBase() {
                  public DateTimeAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                        DateTimeAttribute result = new DateTimeAttribute(p1.getValue(), true);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stdt, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (InvalidAttributeException var4) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var4);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stdt, th, new Bag[0]);
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(stda, new SimpleFunctionFactoryBase(Type.DATE, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DateAttributeEvaluatorBase() {
                  public DateAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                        DateAttribute result = new DateAttribute(p1.getValue(), true);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stda, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (InvalidAttributeException var4) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var4);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stda, th, new Bag[0]);
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(stt, new SimpleFunctionFactoryBase(Type.TIME, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new TimeAttributeEvaluatorBase() {
                  public TimeAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                        TimeAttribute result = new TimeAttribute(p1.getValue(), true);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stt, result, new Bag[]{p1});
                        }

                        return result;
                     } catch (InvalidAttributeException var4) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var4);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, stt, th, new Bag[0]);
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(tgt, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.TIME, Type.TIME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     TimeAttribute p1 = (TimeAttribute)param1.evaluate(context);
                     TimeAttribute p2 = (TimeAttribute)param2.evaluate(context);

                     try {
                        BooleanAttribute result = p1.compareToCalendar(p2) > 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, tgt, result, new Bag[]{p1, p2});
                        }

                        return result;
                     } catch (RuntimeException var6) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, tgt, th, new Bag[]{p1, p2});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(tgtoe, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.TIME, Type.TIME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     TimeAttribute p1 = (TimeAttribute)param1.evaluate(context);
                     TimeAttribute p2 = (TimeAttribute)param2.evaluate(context);

                     try {
                        BooleanAttribute result = p1.compareToCalendar(p2) >= 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, tgtoe, result, new Bag[]{p1, p2});
                        }

                        return result;
                     } catch (RuntimeException var6) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, tgtoe, th, new Bag[]{p1, p2});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(tlt, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.TIME, Type.TIME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     TimeAttribute p1 = (TimeAttribute)param1.evaluate(context);
                     TimeAttribute p2 = (TimeAttribute)param2.evaluate(context);

                     try {
                        BooleanAttribute result = p1.compareToCalendar(p2) < 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, tlt, result, new Bag[]{p1, p2});
                        }

                        return result;
                     } catch (RuntimeException var6) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, tlt, th, new Bag[]{p1, p2});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
         this.register(tltoe, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.TIME, Type.TIME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     TimeAttribute p1 = (TimeAttribute)param1.evaluate(context);
                     TimeAttribute p2 = (TimeAttribute)param2.evaluate(context);

                     try {
                        BooleanAttribute result = p1.compareToCalendar(p2) <= 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, tltoe, result, new Bag[]{p1, p2});
                        }

                        return result;
                     } catch (RuntimeException var6) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                        if (context.isDebugEnabled()) {
                           EntitlementFunctionLibrary.this.debugEval(context, tltoe, th, new Bag[]{p1, p2});
                        }

                        throw th;
                     }
                  }
               };
            }
         });
      } catch (java.net.URISyntaxException var42) {
         throw new URISyntaxException(var42);
      }
   }
}
