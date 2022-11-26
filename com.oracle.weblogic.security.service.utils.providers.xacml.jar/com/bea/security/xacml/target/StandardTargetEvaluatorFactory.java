package com.bea.security.xacml.target;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.policy.Action;
import com.bea.common.security.xacml.policy.ActionMatch;
import com.bea.common.security.xacml.policy.Actions;
import com.bea.common.security.xacml.policy.Apply;
import com.bea.common.security.xacml.policy.AttributeDesignator;
import com.bea.common.security.xacml.policy.AttributeSelector;
import com.bea.common.security.xacml.policy.Environment;
import com.bea.common.security.xacml.policy.EnvironmentMatch;
import com.bea.common.security.xacml.policy.Environments;
import com.bea.common.security.xacml.policy.Function;
import com.bea.common.security.xacml.policy.Resource;
import com.bea.common.security.xacml.policy.ResourceMatch;
import com.bea.common.security.xacml.policy.Resources;
import com.bea.common.security.xacml.policy.Subject;
import com.bea.common.security.xacml.policy.SubjectMatch;
import com.bea.common.security.xacml.policy.Subjects;
import com.bea.common.security.xacml.policy.Target;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.TargetMatchEvaluator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StandardTargetEvaluatorFactory implements TargetEvaluatorFactory {
   public TargetMatchEvaluator createTarget(Target target, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      Subjects s = target.getSubjects();
      Resources r = target.getResources();
      Actions a = target.getActions();
      Environments e = target.getEnvironments();
      List evals = new ArrayList();
      TargetMatchEvaluator match;
      if (s != null) {
         match = config.getTargetEvaluatorRegistry().getEvaluator(s, designatorMatches, config);
         if (match != null && match != NoOpTargetMatchEvaluator.getInstance()) {
            evals.add(match);
         }
      }

      if (r != null) {
         match = config.getTargetEvaluatorRegistry().getEvaluator(r, designatorMatches, config);
         if (match != null && match != NoOpTargetMatchEvaluator.getInstance()) {
            evals.add(match);
         }
      }

      if (a != null) {
         match = config.getTargetEvaluatorRegistry().getEvaluator(a, designatorMatches, config);
         if (match != null && match != NoOpTargetMatchEvaluator.getInstance()) {
            evals.add(match);
         }
      }

      if (e != null) {
         match = config.getTargetEvaluatorRegistry().getEvaluator(e, designatorMatches, config);
         if (match != null && match != NoOpTargetMatchEvaluator.getInstance()) {
            evals.add(match);
         }
      }

      if (evals.isEmpty()) {
         return NoOpTargetMatchEvaluator.getInstance();
      } else {
         Iterator var15 = evals.iterator();

         final TargetMatchEvaluator one;
         do {
            if (!var15.hasNext()) {
               int size = evals.size();
               if (size == 1) {
                  one = (TargetMatchEvaluator)evals.get(0);
                  return new TargetMatchEvaluator() {
                     public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        return one.evaluate(context);
                     }
                  };
               }

               final TargetMatchEvaluator two;
               if (size == 2) {
                  one = (TargetMatchEvaluator)evals.get(0);
                  two = (TargetMatchEvaluator)evals.get(1);
                  return new TargetMatchEvaluator() {
                     public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        if (!one.evaluate(context)) {
                           return false;
                        } else {
                           return two.evaluate(context);
                        }
                     }
                  };
               }

               final TargetMatchEvaluator three;
               if (size == 3) {
                  one = (TargetMatchEvaluator)evals.get(0);
                  two = (TargetMatchEvaluator)evals.get(1);
                  three = (TargetMatchEvaluator)evals.get(2);
                  return new TargetMatchEvaluator() {
                     public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        if (!one.evaluate(context)) {
                           return false;
                        } else if (!two.evaluate(context)) {
                           return false;
                        } else {
                           return three.evaluate(context);
                        }
                     }
                  };
               }

               one = (TargetMatchEvaluator)evals.get(0);
               two = (TargetMatchEvaluator)evals.get(1);
               three = (TargetMatchEvaluator)evals.get(2);
               final TargetMatchEvaluator four = (TargetMatchEvaluator)evals.get(3);
               return new TargetMatchEvaluator() {
                  public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     if (!one.evaluate(context)) {
                        return false;
                     } else if (!two.evaluate(context)) {
                        return false;
                     } else if (!three.evaluate(context)) {
                        return false;
                     } else {
                        return four.evaluate(context);
                     }
                  }
               };
            }

            one = (TargetMatchEvaluator)var15.next();
         } while(NoMatchTargetMatchEvaluator.getInstance() != one);

         return NoMatchTargetMatchEvaluator.getInstance();
      }
   }

   public TargetMatchEvaluator createSubjects(Subjects subjects, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      List ss = subjects.getSubjects();
      final List evals = new ArrayList();
      Iterator evalIt;
      if (ss != null) {
         evalIt = ss.iterator();

         while(evalIt.hasNext()) {
            TargetMatchEvaluator match = config.getTargetEvaluatorRegistry().getEvaluator((Subject)evalIt.next(), designatorMatches, config);
            if (match == NoOpTargetMatchEvaluator.getInstance()) {
               return match;
            }

            if (match != null) {
               evals.add(match);
            }
         }
      }

      if (evals.isEmpty()) {
         return NoOpTargetMatchEvaluator.getInstance();
      } else {
         evalIt = evals.iterator();

         while(evalIt.hasNext()) {
            if (NoMatchTargetMatchEvaluator.getInstance() == evalIt.next()) {
               evalIt.remove();
            }
         }

         if (evals.isEmpty()) {
            return NoMatchTargetMatchEvaluator.getInstance();
         } else {
            final int size = evals.size();
            return new TargetMatchEvaluator() {
               public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  for(int i = 0; i < size; ++i) {
                     if (((TargetMatchEvaluator)evals.get(i)).evaluate(context)) {
                        return true;
                     }
                  }

                  return false;
               }
            };
         }
      }
   }

   public TargetMatchEvaluator createSubject(Subject subject, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      List sms = subject.getMatches();
      final List evals = new ArrayList();
      Iterator it;
      TargetMatchEvaluator eval;
      if (sms != null) {
         it = sms.iterator();

         while(it.hasNext()) {
            eval = config.getTargetEvaluatorRegistry().getEvaluator((SubjectMatch)it.next(), designatorMatches, config);
            if (eval != null && eval != NoOpTargetMatchEvaluator.getInstance()) {
               evals.add(eval);
            }
         }
      }

      if (evals.isEmpty()) {
         return NoOpTargetMatchEvaluator.getInstance();
      } else {
         it = evals.iterator();

         do {
            if (!it.hasNext()) {
               final int size = evals.size();
               return new TargetMatchEvaluator() {
                  public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     for(int i = 0; i < size; ++i) {
                        if (!((TargetMatchEvaluator)evals.get(i)).evaluate(context)) {
                           return false;
                        }
                     }

                     return true;
                  }
               };
            }

            eval = (TargetMatchEvaluator)it.next();
         } while(NoMatchTargetMatchEvaluator.getInstance() != eval);

         return NoMatchTargetMatchEvaluator.getInstance();
      }
   }

   public TargetMatchEvaluator createSubjectMatch(SubjectMatch match, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      if (designatorMatches != null) {
         Iterator var5 = designatorMatches.iterator();

         while(var5.hasNext()) {
            KnownMatch km = (KnownMatch)var5.next();
            switch (km.filterMatch(match)) {
               case 1:
                  return NoOpTargetMatchEvaluator.getInstance();
               case 2:
                  return NoMatchTargetMatchEvaluator.getInstance();
            }
         }
      }

      Function f = new Function(match.getMatchId());
      AttributeSelector as = match.getAttributeSelector();
      AttributeDesignator ad = match.getDesignator();
      List expressions = new ArrayList();
      expressions.add(f);
      expressions.add(match.getAttributeValue());
      expressions.add(ad != null ? ad : as);

      Apply apply;
      try {
         apply = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:any-of"), expressions);
      } catch (java.net.URISyntaxException var11) {
         throw new URISyntaxException(var11);
      }

      final AttributeEvaluator eval = config.getExpressionRegistry().parse(apply, (Map)null, config);
      return new TargetMatchEvaluator() {
         public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
            return ((BooleanAttribute)eval.evaluate(context)).getBooleanValue();
         }
      };
   }

   public TargetMatchEvaluator createResources(Resources resources, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      List rs = resources.getResources();
      final List evals = new ArrayList();
      Iterator evalIt;
      if (rs != null) {
         evalIt = rs.iterator();

         while(evalIt.hasNext()) {
            TargetMatchEvaluator match = config.getTargetEvaluatorRegistry().getEvaluator((Resource)evalIt.next(), designatorMatches, config);
            if (match == NoOpTargetMatchEvaluator.getInstance()) {
               return match;
            }

            if (match != null) {
               evals.add(match);
            }
         }
      }

      if (evals.isEmpty()) {
         return NoOpTargetMatchEvaluator.getInstance();
      } else {
         evalIt = evals.iterator();

         while(evalIt.hasNext()) {
            if (NoMatchTargetMatchEvaluator.getInstance() == evalIt.next()) {
               evalIt.remove();
            }
         }

         if (evals.isEmpty()) {
            return NoMatchTargetMatchEvaluator.getInstance();
         } else {
            final int size = evals.size();
            return new TargetMatchEvaluator() {
               public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  for(int i = 0; i < size; ++i) {
                     if (((TargetMatchEvaluator)evals.get(i)).evaluate(context)) {
                        return true;
                     }
                  }

                  return false;
               }
            };
         }
      }
   }

   public TargetMatchEvaluator createResource(Resource resource, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      List rms = resource.getMatches();
      final List evals = new ArrayList();
      Iterator it;
      TargetMatchEvaluator eval;
      if (rms != null) {
         it = rms.iterator();

         while(it.hasNext()) {
            eval = config.getTargetEvaluatorRegistry().getEvaluator((ResourceMatch)it.next(), designatorMatches, config);
            if (eval != null && eval != NoOpTargetMatchEvaluator.getInstance()) {
               evals.add(eval);
            }
         }
      }

      if (evals.isEmpty()) {
         return NoOpTargetMatchEvaluator.getInstance();
      } else {
         it = evals.iterator();

         do {
            if (!it.hasNext()) {
               final int size = evals.size();
               return new TargetMatchEvaluator() {
                  public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     for(int i = 0; i < size; ++i) {
                        if (!((TargetMatchEvaluator)evals.get(i)).evaluate(context)) {
                           return false;
                        }
                     }

                     return true;
                  }
               };
            }

            eval = (TargetMatchEvaluator)it.next();
         } while(NoMatchTargetMatchEvaluator.getInstance() != eval);

         return NoMatchTargetMatchEvaluator.getInstance();
      }
   }

   public TargetMatchEvaluator createResourceMatch(ResourceMatch match, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      if (designatorMatches != null) {
         Iterator var5 = designatorMatches.iterator();

         while(var5.hasNext()) {
            KnownMatch km = (KnownMatch)var5.next();
            switch (km.filterMatch(match)) {
               case 1:
                  return NoOpTargetMatchEvaluator.getInstance();
               case 2:
                  return NoMatchTargetMatchEvaluator.getInstance();
            }
         }
      }

      Function f = new Function(match.getMatchId());
      AttributeSelector as = match.getAttributeSelector();
      AttributeDesignator ad = match.getDesignator();
      List expressions = new ArrayList();
      expressions.add(f);
      expressions.add(match.getAttributeValue());
      expressions.add(ad != null ? ad : as);

      Apply apply;
      try {
         apply = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:any-of"), expressions);
      } catch (java.net.URISyntaxException var11) {
         throw new URISyntaxException(var11);
      }

      final AttributeEvaluator eval = config.getExpressionRegistry().parse(apply, (Map)null, config);
      return new TargetMatchEvaluator() {
         public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
            return ((BooleanAttribute)eval.evaluate(context)).getBooleanValue();
         }
      };
   }

   public TargetMatchEvaluator createActions(Actions actions, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      List as = actions.getActions();
      final List evals = new ArrayList();
      Iterator evalIt;
      if (as != null) {
         evalIt = as.iterator();

         while(evalIt.hasNext()) {
            TargetMatchEvaluator match = config.getTargetEvaluatorRegistry().getEvaluator((Action)evalIt.next(), designatorMatches, config);
            if (match == NoOpTargetMatchEvaluator.getInstance()) {
               return match;
            }

            if (match != null) {
               evals.add(match);
            }
         }
      }

      if (evals.isEmpty()) {
         return NoOpTargetMatchEvaluator.getInstance();
      } else {
         evalIt = evals.iterator();

         while(evalIt.hasNext()) {
            if (NoMatchTargetMatchEvaluator.getInstance() == evalIt.next()) {
               evalIt.remove();
            }
         }

         if (evals.isEmpty()) {
            return NoMatchTargetMatchEvaluator.getInstance();
         } else {
            final int size = evals.size();
            return new TargetMatchEvaluator() {
               public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  for(int i = 0; i < size; ++i) {
                     if (((TargetMatchEvaluator)evals.get(i)).evaluate(context)) {
                        return true;
                     }
                  }

                  return false;
               }
            };
         }
      }
   }

   public TargetMatchEvaluator createAction(Action action, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      List ams = action.getMatches();
      final List evals = new ArrayList();
      Iterator it;
      TargetMatchEvaluator eval;
      if (ams != null) {
         it = ams.iterator();

         while(it.hasNext()) {
            eval = config.getTargetEvaluatorRegistry().getEvaluator((ActionMatch)it.next(), designatorMatches, config);
            if (eval != null && eval != NoOpTargetMatchEvaluator.getInstance()) {
               evals.add(eval);
            }
         }
      }

      if (evals.isEmpty()) {
         return NoOpTargetMatchEvaluator.getInstance();
      } else {
         it = evals.iterator();

         do {
            if (!it.hasNext()) {
               final int size = evals.size();
               return new TargetMatchEvaluator() {
                  public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     for(int i = 0; i < size; ++i) {
                        if (!((TargetMatchEvaluator)evals.get(i)).evaluate(context)) {
                           return false;
                        }
                     }

                     return true;
                  }
               };
            }

            eval = (TargetMatchEvaluator)it.next();
         } while(NoMatchTargetMatchEvaluator.getInstance() != eval);

         return NoMatchTargetMatchEvaluator.getInstance();
      }
   }

   public TargetMatchEvaluator createActionMatch(ActionMatch match, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      if (designatorMatches != null) {
         Iterator var5 = designatorMatches.iterator();

         while(var5.hasNext()) {
            KnownMatch km = (KnownMatch)var5.next();
            switch (km.filterMatch(match)) {
               case 1:
                  return NoOpTargetMatchEvaluator.getInstance();
               case 2:
                  return NoMatchTargetMatchEvaluator.getInstance();
            }
         }
      }

      Function f = new Function(match.getMatchId());
      AttributeSelector as = match.getAttributeSelector();
      AttributeDesignator ad = match.getDesignator();
      List expressions = new ArrayList();
      expressions.add(f);
      expressions.add(match.getAttributeValue());
      expressions.add(ad != null ? ad : as);

      Apply apply;
      try {
         apply = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:any-of"), expressions);
      } catch (java.net.URISyntaxException var11) {
         throw new URISyntaxException(var11);
      }

      final AttributeEvaluator eval = config.getExpressionRegistry().parse(apply, (Map)null, config);
      return new TargetMatchEvaluator() {
         public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
            return ((BooleanAttribute)eval.evaluate(context)).getBooleanValue();
         }
      };
   }

   public TargetMatchEvaluator createEnvironments(Environments envs, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      List es = envs.getEnvironments();
      final List evals = new ArrayList();
      Iterator evalIt;
      if (es != null) {
         evalIt = es.iterator();

         while(evalIt.hasNext()) {
            TargetMatchEvaluator match = config.getTargetEvaluatorRegistry().getEvaluator((Environment)evalIt.next(), designatorMatches, config);
            if (match == NoOpTargetMatchEvaluator.getInstance()) {
               return match;
            }

            if (match != null) {
               evals.add(match);
            }
         }
      }

      if (evals.isEmpty()) {
         return NoOpTargetMatchEvaluator.getInstance();
      } else {
         evalIt = evals.iterator();

         while(evalIt.hasNext()) {
            if (NoMatchTargetMatchEvaluator.getInstance() == evalIt.next()) {
               evalIt.remove();
            }
         }

         if (evals.isEmpty()) {
            return NoMatchTargetMatchEvaluator.getInstance();
         } else {
            final int size = evals.size();
            return new TargetMatchEvaluator() {
               public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  for(int i = 0; i < size; ++i) {
                     if (((TargetMatchEvaluator)evals.get(i)).evaluate(context)) {
                        return true;
                     }
                  }

                  return false;
               }
            };
         }
      }
   }

   public TargetMatchEvaluator createEnvironment(Environment env, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      List ems = env.getMatches();
      final List evals = new ArrayList();
      Iterator it;
      TargetMatchEvaluator eval;
      if (ems != null) {
         it = ems.iterator();

         while(it.hasNext()) {
            eval = config.getTargetEvaluatorRegistry().getEvaluator((EnvironmentMatch)it.next(), designatorMatches, config);
            if (eval != null && eval != NoOpTargetMatchEvaluator.getInstance()) {
               evals.add(eval);
            }
         }
      }

      if (evals.isEmpty()) {
         return NoOpTargetMatchEvaluator.getInstance();
      } else {
         it = evals.iterator();

         do {
            if (!it.hasNext()) {
               final int size = evals.size();
               return new TargetMatchEvaluator() {
                  public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     for(int i = 0; i < size; ++i) {
                        if (!((TargetMatchEvaluator)evals.get(i)).evaluate(context)) {
                           return false;
                        }
                     }

                     return true;
                  }
               };
            }

            eval = (TargetMatchEvaluator)it.next();
         } while(NoMatchTargetMatchEvaluator.getInstance() != eval);

         return NoMatchTargetMatchEvaluator.getInstance();
      }
   }

   public TargetMatchEvaluator createEnvironmentMatch(EnvironmentMatch match, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      if (designatorMatches != null) {
         Iterator var5 = designatorMatches.iterator();

         while(var5.hasNext()) {
            KnownMatch km = (KnownMatch)var5.next();
            switch (km.filterMatch(match)) {
               case 1:
                  return NoOpTargetMatchEvaluator.getInstance();
               case 2:
                  return NoMatchTargetMatchEvaluator.getInstance();
            }
         }
      }

      Function f = new Function(match.getMatchId());
      AttributeSelector as = match.getAttributeSelector();
      AttributeDesignator ad = match.getDesignator();
      List expressions = new ArrayList();
      expressions.add(f);
      expressions.add(match.getAttributeValue());
      expressions.add(ad != null ? ad : as);

      Apply apply;
      try {
         apply = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:any-of"), expressions);
      } catch (java.net.URISyntaxException var11) {
         throw new URISyntaxException(var11);
      }

      final AttributeEvaluator eval = config.getExpressionRegistry().parse(apply, (Map)null, config);
      return new TargetMatchEvaluator() {
         public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
            return ((BooleanAttribute)eval.evaluate(context)).getBooleanValue();
         }
      };
   }
}
