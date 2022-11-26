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
import com.bea.security.xacml.TargetMatchEvaluator;
import java.util.Collection;
import java.util.Iterator;

public interface TargetEvaluatorFactory {
   TargetMatchEvaluator createTarget(Target var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createSubjects(Subjects var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createSubject(Subject var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createSubjectMatch(SubjectMatch var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createResources(Resources var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createResource(Resource var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createResourceMatch(ResourceMatch var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createActions(Actions var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createAction(Action var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createActionMatch(ActionMatch var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createEnvironments(Environments var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createEnvironment(Environment var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   TargetMatchEvaluator createEnvironmentMatch(EnvironmentMatch var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;
}
