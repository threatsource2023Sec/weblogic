package com.bea.security.xacml.target;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Action;
import com.bea.common.security.xacml.policy.ActionMatch;
import com.bea.common.security.xacml.policy.Actions;
import com.bea.common.security.xacml.policy.Environment;
import com.bea.common.security.xacml.policy.EnvironmentMatch;
import com.bea.common.security.xacml.policy.Environments;
import com.bea.common.security.xacml.policy.Resource;
import com.bea.common.security.xacml.policy.ResourceMatch;
import com.bea.common.security.xacml.policy.Resources;
import com.bea.common.security.xacml.policy.Subject;
import com.bea.common.security.xacml.policy.SubjectMatch;
import com.bea.common.security.xacml.policy.Subjects;
import com.bea.common.security.xacml.policy.Target;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.MissingFactoryException;
import com.bea.security.xacml.TargetMatchEvaluator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class TargetEvaluatorRegistry {
   private List factories = Collections.synchronizedList(new ArrayList());

   public TargetEvaluatorRegistry() throws URISyntaxException {
      this.register(new StandardTargetEvaluatorFactory());
   }

   public void register(TargetEvaluatorFactory factory) {
      this.factories.add(factory);
   }

   public TargetMatchEvaluator getEvaluator(Target target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createTarget(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(Subjects target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createSubjects(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(Subject target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createSubject(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(SubjectMatch target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createSubjectMatch(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(Resources target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createResources(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(Resource target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createResource(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(ResourceMatch target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createResourceMatch(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(Actions target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createActions(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(Action target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createAction(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(ActionMatch target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createActionMatch(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(Environments target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createEnvironments(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(Environment target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createEnvironment(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }

   public TargetMatchEvaluator getEvaluator(EnvironmentMatch target, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      TargetMatchEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for target: " + target);
         }

         TargetEvaluatorFactory pf = (TargetEvaluatorFactory)facIt.next();
         e = pf.createEnvironmentMatch(target, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }
}
