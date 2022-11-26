package com.bea.security.providers.xacml;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.DateAttribute;
import com.bea.common.security.xacml.attr.DateTimeAttribute;
import com.bea.common.security.xacml.attr.TimeAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.MissingAttributeException;
import com.bea.security.xacml.attr.AttributeEvaluatableFactory;
import com.bea.security.xacml.attr.SubjectAttributeEvaluatableFactory;
import com.bea.security.xacml.policy.VariableContext;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;

public class EvaluationCtxFactory {
   private final ContextConverterRegistry ccRegistry;
   private final DirectionConverterRegistry dcRegistry;
   private final ResourceConverterRegistry rcRegistry;
   private final RoleConverterRegistry rlcRegistry;
   private final SubjectConverterRegistry scRegistry;
   private final SubjectAttributeDesignatorFactory sadf;
   private final ResourceAttributeDesignatorFactory radf;
   private final ActionAttributeDesignatorFactory aadf;
   private final EnvironmentAttributeDesignatorFactory eadf;
   private final LoggerSpi log;
   private DateTimeEvaluators dateTimeEvaluators;

   public EvaluationCtxFactory(ContextConverterRegistry ccRegistry, DirectionConverterRegistry dcRegistry, ResourceConverterRegistry rcRegistry, RoleConverterRegistry rlcRegistry, SubjectConverterRegistry scRegistry, LoggerSpi log) throws URISyntaxException {
      this(ccRegistry, dcRegistry, rcRegistry, rlcRegistry, scRegistry, log, (DateTimeEvaluators)null);
   }

   public EvaluationCtxFactory(ContextConverterRegistry ccRegistry, DirectionConverterRegistry dcRegistry, ResourceConverterRegistry rcRegistry, RoleConverterRegistry rlcRegistry, SubjectConverterRegistry scRegistry, LoggerSpi log, DateTimeEvaluators dateTimeEvaluators) throws URISyntaxException {
      this.ccRegistry = ccRegistry;
      this.dcRegistry = dcRegistry;
      this.rcRegistry = rcRegistry;
      this.rlcRegistry = rlcRegistry;
      this.scRegistry = scRegistry;
      this.log = log;
      this.dateTimeEvaluators = dateTimeEvaluators;
      String subjectIssuer = "WebLogic";
      String resourceIssuer = "WebLogic";
      String actionIssuer = "WebLogic";
      String environmentIssuer = "WebLogic";
      this.sadf = new SubjectAttributeDesignatorFactory(subjectIssuer, this);
      this.radf = new ResourceAttributeDesignatorFactory(resourceIssuer);
      this.aadf = new ActionAttributeDesignatorFactory(actionIssuer);
      this.eadf = new EnvironmentAttributeDesignatorFactory(environmentIssuer);
   }

   public com.bea.security.xacml.EvaluationCtx create(Subject subject, List resources, ContextHandler context) {
      return new EvaluationCtx(subject, resources, context);
   }

   public com.bea.security.xacml.EvaluationCtx create(Subject subject, Map roles, Resource resource, ContextHandler context, Direction direction) {
      return new EvaluationCtx(subject, roles, resource, context, direction);
   }

   public SubjectConverter getSubjectConverter(Subject s2) {
      return this.scRegistry.getConverter(s2);
   }

   private class EvaluationCtx implements ExtendedEvaluationCtx {
      private Subject subject;
      private Resource resource;
      private List resources;
      private ContextHandler handler;
      private Map roles;
      private Direction direction;
      private VariableContext vc;
      private boolean isDebugEnabled;
      private long evaluationBeginTime;
      private Bag time;
      private Bag date;
      private Bag dateTime;
      private ContextConverter cc;
      private DirectionConverter dc;
      private ResourceConverter rc;
      private RoleConverter rlc;
      private SubjectConverter sc;
      private ConcurrentHashMap ed;

      public EvaluationCtx(Subject subject, List resources, ContextHandler handler) {
         this(subject, (Map)null, (Resource)null, handler, (Direction)null, resources);
      }

      public EvaluationCtx(Subject subject, Map roles, Resource resource, ContextHandler handler, Direction direction) {
         this(subject, roles, resource, handler, direction, (List)null);
      }

      public EvaluationCtx(Subject subject, Map roles, Resource resource, ContextHandler handler, Direction direction, List resources) {
         this.ed = new ConcurrentHashMap();
         this.subject = subject;
         this.roles = roles;
         this.resource = resource;
         this.handler = handler;
         this.direction = direction;
         this.resources = resources;
         this.vc = null;
         this.isDebugEnabled = EvaluationCtxFactory.this.log.isDebugEnabled();
         this.evaluationBeginTime = System.currentTimeMillis();
      }

      public Subject getSubject() {
         return this.subject;
      }

      public Resource getResource() {
         return this.resource;
      }

      public ContextHandler getContextHandler() {
         return this.handler;
      }

      public Map getRoles() {
         return this.roles;
      }

      public Direction getDirection() {
         return this.direction;
      }

      public List getResources() {
         return this.resources;
      }

      public ContextConverter getContextConverter() {
         if (this.cc == null) {
            this.cc = EvaluationCtxFactory.this.ccRegistry.getConverter(this.handler);
         }

         return this.cc;
      }

      public DirectionConverter getDirectionConverter() {
         if (this.dc == null) {
            this.dc = EvaluationCtxFactory.this.dcRegistry.getConverter(this.direction);
         }

         return this.dc;
      }

      public ResourceConverter getResourceConverter() {
         if (this.rc == null) {
            this.rc = EvaluationCtxFactory.this.rcRegistry.getConverter(this.resource);
         }

         return this.rc;
      }

      public RoleConverter getRoleConverter() {
         if (this.rlc == null) {
            this.rlc = EvaluationCtxFactory.this.rlcRegistry.getConverter(this.roles);
         }

         return this.rlc;
      }

      public SubjectConverter getSubjectConverter() {
         if (this.sc == null) {
            this.sc = EvaluationCtxFactory.this.scRegistry.getConverter(this.subject);
         }

         return this.sc;
      }

      public SubjectConverter getSubjectConverter(Subject s2) {
         return EvaluationCtxFactory.this.scRegistry.getConverter(s2);
      }

      public SubjectAttributeEvaluatableFactory getSubjectAttributes() {
         return EvaluationCtxFactory.this.sadf.getFactory();
      }

      public AttributeEvaluatableFactory getResourceAttributes() {
         return EvaluationCtxFactory.this.radf.getFactory();
      }

      public AttributeEvaluatableFactory getActionAttributes() {
         return EvaluationCtxFactory.this.aadf.getFactory();
      }

      public AttributeEvaluatableFactory getEnvironmentAttributes() {
         return EvaluationCtxFactory.this.eadf.getFactory();
      }

      public Bag getCurrentTime() throws IndeterminateEvaluationException {
         if (this.time == null) {
            if (EvaluationCtxFactory.this.dateTimeEvaluators != null) {
               AttributeEvaluator evalTime = EvaluationCtxFactory.this.dateTimeEvaluators.getTimeEvaluator();
               if (evalTime != null) {
                  try {
                     this.time = evalTime.evaluateToBag(this);
                  } catch (MissingAttributeException var3) {
                  }
               }
            }

            if (this.time == null || this.time.isEmpty()) {
               Calendar cal = Calendar.getInstance();
               cal.setTimeInMillis(this.evaluationBeginTime);
               this.time = new TimeAttribute(cal);
            }
         }

         return this.time;
      }

      public Bag getCurrentDate() throws IndeterminateEvaluationException {
         if (this.date == null) {
            if (EvaluationCtxFactory.this.dateTimeEvaluators != null) {
               AttributeEvaluator evalDate = EvaluationCtxFactory.this.dateTimeEvaluators.getDateEvaluator();
               if (evalDate != null) {
                  try {
                     this.date = evalDate.evaluateToBag(this);
                  } catch (MissingAttributeException var3) {
                  }
               }
            }

            if (this.date == null || this.date.isEmpty()) {
               Calendar cal = Calendar.getInstance();
               cal.setTimeInMillis(this.evaluationBeginTime);
               this.date = new DateAttribute(cal);
            }
         }

         return this.date;
      }

      public Bag getCurrentDateTime() throws IndeterminateEvaluationException {
         if (this.dateTime == null) {
            if (EvaluationCtxFactory.this.dateTimeEvaluators != null) {
               AttributeEvaluator evalDateTime = EvaluationCtxFactory.this.dateTimeEvaluators.getDateTimeEvaluator();
               if (evalDateTime != null) {
                  try {
                     this.dateTime = evalDateTime.evaluateToBag(this);
                  } catch (MissingAttributeException var3) {
                  }
               }
            }

            if (this.dateTime == null || this.dateTime.isEmpty()) {
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
   }
}
