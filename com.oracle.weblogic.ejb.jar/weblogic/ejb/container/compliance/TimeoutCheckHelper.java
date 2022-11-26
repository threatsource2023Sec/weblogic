package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.ScheduleExpression;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.j2ee.descriptor.TimerBean;
import weblogic.utils.ErrorCollectionException;

public final class TimeoutCheckHelper {
   private static final String[] MONTHS_NAMES = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
   private static final String[] WEEK_DAYS_NAMES = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
   private static final String[] DAYS_OF_MONTH_NAMES = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "1st", "2nd", "3rd", "4th", "5th", "Last"};

   public static void validateTimeoutMethodsIdentical(Method timeoutInDD, Method timeoutInBC) throws ComplianceException {
      if (timeoutInDD != null && timeoutInBC != null && !timeoutInDD.equals(timeoutInBC)) {
         throw new ComplianceException(getFmt().TIMEOUT_IN_DD_NOT_COMPATIBLE_WITH_ANNOTATION());
      }
   }

   public static void validateOnlyOneTimeoutMethod(Collection ms) throws ComplianceException {
      if (ms != null && ms.size() > 1) {
         throw new ComplianceException(getFmt().BEAN_CAN_HAVE_ONE_TIMEOUT_METHOD());
      }
   }

   public static void validateTimeoutMethodIsejbTimeout(Class beanClass, Method m) throws ComplianceException {
      if (TimedObject.class.isAssignableFrom(beanClass) && m != null && !m.getName().equals("ejbTimeout")) {
         throw new ComplianceException(getFmt().TIMEOUT_CAN_ONLY_SPECIFY_EJBTIMEOUT_METHOD(beanClass.getSimpleName()));
      }
   }

   public static void validateTimeoutMethodExistsInBC(Method m, Class beanClass, String mnInDD) throws ComplianceException {
      if (m == null) {
         throw new ComplianceException(getFmt().EJB_TIMEOUT_METHOD_NOT_FOUND(beanClass.getSimpleName(), DDUtils.getMethodSignature(mnInDD, new String[]{"javax.ejb.Timer"})));
      }
   }

   public static void validateTimeoutMethod(BeanInfo beanInfo) throws ComplianceException, ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      if (beanInfo.isSessionBean() && ((SessionBeanInfo)beanInfo).isStateful() && (beanInfo.getTimeoutMethod() != null || !beanInfo.getAutomaticTimerMethods().isEmpty())) {
         errors.add(new ComplianceException(getFmt().STATEFULE_BEAN_CANNOT_IMPLEMENTS_TIMEOUT()));
      }

      String ejbName = beanInfo.getEJBName();
      List allTimeoutMethods = new ArrayList();
      if (beanInfo.getTimeoutMethod() != null) {
         validateTimeoutMethodIsejbTimeout(beanInfo.getBeanClass(), beanInfo.getTimeoutMethod());
         allTimeoutMethods.add(beanInfo.getTimeoutMethod());
      }

      allTimeoutMethods.addAll(beanInfo.getAutomaticTimerMethods());
      Iterator var4 = allTimeoutMethods.iterator();

      while(true) {
         Method m;
         do {
            if (!var4.hasNext()) {
               if (!errors.isEmpty()) {
                  throw errors;
               }

               return;
            }

            m = (Method)var4.next();
            int mod = m.getModifiers();
            if (Modifier.isFinal(mod) || Modifier.isStatic(mod)) {
               errors.add(new ComplianceException(getFmt().TIMEOUT_METHOD_CANNOT_BE_FINAL_OR_STATIC(ejbName)));
            }
         } while(!beanInfo.isEJB30());

         Class[] para = m.getParameterTypes();
         boolean complianceProblem = false;
         if (para.length > 1) {
            complianceProblem = true;
         } else if (para.length == 1 && !Timer.class.equals(para[0])) {
            complianceProblem = true;
         }

         if (!m.getReturnType().isAssignableFrom(Void.TYPE)) {
            complianceProblem = true;
         }

         if (complianceProblem) {
            errors.add(new ComplianceException(getFmt().TIMEOUT_METHOD_WITH_INVALID_SIGNATURE2(ejbName)));
         }

         Class[] var9 = m.getExceptionTypes();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            Class c = var9[var11];
            if (ComplianceUtils.isApplicationException(beanInfo, m, c)) {
               errors.add(new ComplianceException(getFmt().TIMEOUT_METHOD_CANNOT_THROW_APPLICATION_EXCEPTION(ejbName)));
            }
         }
      }
   }

   public static void validateTimeoutMethodsTransactionType(BeanInfo bi) throws ComplianceException {
      if (bi.isTimerDriven()) {
         Iterator var1 = bi.getAllTimerMethodInfos().iterator();

         short txAttr;
         do {
            if (!var1.hasNext()) {
               return;
            }

            MethodInfo mi = (MethodInfo)var1.next();
            txAttr = mi.getTransactionAttribute();
         } while(1 == txAttr || 3 == txAttr || 0 == txAttr);

         throw new ComplianceException(getFmt().EJB_TIMEOUT_BAD_TX_ATTRIBUTE(bi.getDisplayName()));
      }
   }

   public static void validateSingleScheduleExpressionSimply(ScheduleExpression se) throws ComplianceException {
      validateScheduleNumberExpInRanges(se.getYear(), new int[]{1000, 9999});
      validateScheduleExpInNumberRangesOrStringRange(se.getMonth(), MONTHS_NAMES, new int[]{1, 12});
      validateScheduleExpInNumberRangesOrStringRange(se.getDayOfWeek(), WEEK_DAYS_NAMES, new int[]{0, 7});
      validateScheduleExpInNumberRangesOrStringRange(se.getDayOfMonth(), DAYS_OF_MONTH_NAMES, new int[]{1, 31}, new int[]{-7, -1});
      validateScheduleNumberExpInRanges(se.getHour(), new int[]{0, 23});
      validateScheduleNumberExpInRanges(se.getMinute(), new int[]{0, 59});
      validateScheduleNumberExpInRanges(se.getSecond(), new int[]{0, 59});
   }

   private static void validateScheduleExpInNumberRangesOrStringRange(String exp, String[] stringRange, int[]... numberRanges) throws ComplianceException {
      if (exp != null && isSingleScheduleExpression(exp)) {
         try {
            Integer.parseInt(exp);
            validateScheduleNumberExpInRanges(exp, numberRanges);
         } catch (Exception var4) {
            validateScheduleStringExpInRange(exp, stringRange);
         }

      }
   }

   private static void validateScheduleStringExpInRange(String exp, String[] range) throws ComplianceException {
      List elements = Arrays.asList(exp.split(" "));
      int matched = 0;
      Iterator var4 = elements.iterator();

      while(true) {
         while(var4.hasNext()) {
            String element = (String)var4.next();
            String[] var6 = range;
            int var7 = range.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String r = var6[var8];
               if (r.equalsIgnoreCase(element)) {
                  ++matched;
                  break;
               }
            }
         }

         if (matched < elements.size()) {
            throw new ComplianceException(getFmt().INVALID_ENUM_IN_SCHEDULE_EXPRESSION(exp));
         }

         return;
      }
   }

   private static void validateScheduleNumberExpInRanges(String exp, int[]... ranges) throws ComplianceException {
      if (exp != null && isSingleScheduleExpression(exp)) {
         int value = getNumberFromScheduleExpression(exp);
         boolean isInRange = false;
         int[][] var4 = ranges;
         int var5 = ranges.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            int[] range = var4[var6];
            if (range[0] <= value && value <= range[1]) {
               isInRange = true;
            }
         }

         if (!isInRange) {
            throw new ComplianceException(getFmt().INVALID_NUMBER_IN_SCHEDULE_EXPRESSION(exp));
         }
      }
   }

   private static int getNumberFromScheduleExpression(String expression) throws ComplianceException {
      try {
         return Integer.parseInt(expression);
      } catch (NumberFormatException var2) {
         throw new ComplianceException(getFmt().INVALID_NUMBER_IN_SCHEDULE_EXPRESSION(expression));
      }
   }

   private static boolean isSingleScheduleExpression(String s) {
      return !isSpecialCharsInExp(s, "*", ",", "/") && s.trim().indexOf("-") <= 0;
   }

   private static boolean isSpecialCharsInExp(String expression, String... specialChars) {
      String[] var2 = specialChars;
      int var3 = specialChars.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String each = var2[var4];
         if (expression.indexOf(each) > -1) {
            return true;
         }
      }

      return false;
   }

   public static void validateAutocreatedClusteredTimerCount(TimerBean[] beans, Class beanClass) throws ComplianceException {
      Map map = new HashMap();
      TimerBean[] var3 = beans;
      int var4 = beans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TimerBean each = var3[var5];
         Method m = InterceptorHelper.getTimeoutMethodFromDD(each.getTimeoutMethod(), beanClass);
         if (map.put(m, each) != null) {
            throw new ComplianceException(getFmt().INVALID_AUTO_CREATED_CLUSTERED_TIMER_COUNT(m.getName(), beanClass.getName()));
         }
      }

   }

   private static EJBComplianceTextFormatter getFmt() {
      return EJBComplianceTextFormatter.getInstance();
   }
}
