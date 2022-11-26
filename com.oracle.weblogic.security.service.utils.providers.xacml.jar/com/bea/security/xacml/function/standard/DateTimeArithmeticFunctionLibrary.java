package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.DateAttribute;
import com.bea.common.security.xacml.attr.DateTimeAttribute;
import com.bea.common.security.xacml.attr.DayTimeDurationAttribute;
import com.bea.common.security.xacml.attr.YearMonthDurationAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.DateAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.DateTimeAttributeEvaluatorBase;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.Calendar;
import java.util.List;

public class DateTimeArithmeticFunctionLibrary extends SimpleFunctionLibraryBase {
   public DateTimeArithmeticFunctionLibrary() throws URISyntaxException {
      try {
         final URI adur = new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-add-dayTimeDuration");
         final URI ay = new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-add-yearMonthDuration");
         final URI sd = new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-subtract-dayTimeDuration");
         final URI sy = new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-subtract-yearMonthDuration");
         final URI day = new URI("urn:oasis:names:tc:xacml:1.0:function:date-add-yearMonthDuration");
         final URI dsy = new URI("urn:oasis:names:tc:xacml:1.0:function:date-subtract-yearMonthDuration");
         this.register(adur, new SimpleFunctionFactoryBase(Type.DATE_TIME, new Type[]{Type.DATE_TIME, Type.DAY_TIME_DURATION}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DateTimeAttributeEvaluatorBase() {
                  public DateTimeAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DateTimeAttribute dta = (DateTimeAttribute)param1.evaluate(context);
                     Calendar dtac = dta.getValue();
                     long dtan = dta.getNanoseconds();
                     Calendar result = (Calendar)dtac.clone();
                     DayTimeDurationAttribute dur = (DayTimeDurationAttribute)param2.evaluate(context);
                     result.add(13, dur.getSeconds());
                     DateTimeAttribute r = new DateTimeAttribute(result, dtan + dur.getNanoseconds());
                     if (context.isDebugEnabled()) {
                        DateTimeArithmeticFunctionLibrary.this.debugEval(context, adur, r, new Bag[]{dta, dur});
                     }

                     return r;
                  }
               };
            }
         });
         this.register(ay, new SimpleFunctionFactoryBase(Type.DATE_TIME, new Type[]{Type.DATE_TIME, Type.YEAR_MONTH_DURATION}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DateTimeAttributeEvaluatorBase() {
                  public DateTimeAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DateTimeAttribute dta = (DateTimeAttribute)param1.evaluate(context);
                     YearMonthDurationAttribute p2 = (YearMonthDurationAttribute)param2.evaluate(context);
                     Calendar result = (Calendar)dta.getValue().clone();
                     result.add(2, p2.getDuration());
                     DateTimeAttribute r = new DateTimeAttribute(result, dta.getNanoseconds());
                     if (context.isDebugEnabled()) {
                        DateTimeArithmeticFunctionLibrary.this.debugEval(context, ay, r, new Bag[]{dta, p2});
                     }

                     return r;
                  }
               };
            }
         });
         this.register(sd, new SimpleFunctionFactoryBase(Type.DATE_TIME, new Type[]{Type.DATE_TIME, Type.DAY_TIME_DURATION}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DateTimeAttributeEvaluatorBase() {
                  public DateTimeAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DateTimeAttribute dta = (DateTimeAttribute)param1.evaluate(context);
                     Calendar dtac = dta.getValue();
                     long dtan = dta.getNanoseconds();
                     Calendar result = (Calendar)dtac.clone();
                     DayTimeDurationAttribute dur = (DayTimeDurationAttribute)param2.evaluate(context);
                     result.add(13, -1 * dur.getSeconds());
                     DateTimeAttribute r = new DateTimeAttribute(result, dtan - dur.getNanoseconds());
                     if (context.isDebugEnabled()) {
                        DateTimeArithmeticFunctionLibrary.this.debugEval(context, sd, r, new Bag[]{dta, dur});
                     }

                     return r;
                  }
               };
            }
         });
         this.register(sy, new SimpleFunctionFactoryBase(Type.DATE_TIME, new Type[]{Type.DATE_TIME, Type.YEAR_MONTH_DURATION}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DateTimeAttributeEvaluatorBase() {
                  public DateTimeAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DateTimeAttribute dta = (DateTimeAttribute)param1.evaluate(context);
                     YearMonthDurationAttribute p2 = (YearMonthDurationAttribute)param2.evaluate(context);
                     Calendar result = (Calendar)dta.getValue().clone();
                     result.add(2, -1 * p2.getDuration());
                     DateTimeAttribute r = new DateTimeAttribute(result, dta.getNanoseconds());
                     if (context.isDebugEnabled()) {
                        DateTimeArithmeticFunctionLibrary.this.debugEval(context, sy, r, new Bag[]{dta, p2});
                     }

                     return r;
                  }
               };
            }
         });
         this.register(day, new SimpleFunctionFactoryBase(Type.DATE, new Type[]{Type.DATE, Type.YEAR_MONTH_DURATION}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DateAttributeEvaluatorBase() {
                  public DateAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DateAttribute p1 = (DateAttribute)param1.evaluate(context);
                     YearMonthDurationAttribute p2 = (YearMonthDurationAttribute)param2.evaluate(context);
                     Calendar result = (Calendar)p1.getValue().clone();
                     result.add(2, p2.getDuration());
                     DateAttribute r = new DateAttribute(result);
                     if (context.isDebugEnabled()) {
                        DateTimeArithmeticFunctionLibrary.this.debugEval(context, day, r, new Bag[]{p1, p2});
                     }

                     return r;
                  }
               };
            }
         });
         this.register(dsy, new SimpleFunctionFactoryBase(Type.DATE, new Type[]{Type.DATE, Type.YEAR_MONTH_DURATION}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DateAttributeEvaluatorBase() {
                  public DateAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DateAttribute p1 = (DateAttribute)param1.evaluate(context);
                     YearMonthDurationAttribute p2 = (YearMonthDurationAttribute)param2.evaluate(context);
                     Calendar result = (Calendar)p1.getValue().clone();
                     result.add(2, -1 * p2.getDuration());
                     DateAttribute r = new DateAttribute(result);
                     if (context.isDebugEnabled()) {
                        DateTimeArithmeticFunctionLibrary.this.debugEval(context, dsy, r, new Bag[]{p1, p2});
                     }

                     return r;
                  }
               };
            }
         });
      } catch (java.net.URISyntaxException var7) {
         throw new URISyntaxException(var7);
      }
   }
}
