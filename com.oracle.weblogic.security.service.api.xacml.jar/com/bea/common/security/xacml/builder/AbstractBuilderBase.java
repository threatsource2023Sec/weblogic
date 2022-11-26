package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.XACMLException;
import com.bea.common.security.xacml.attr.StandardAttributes;
import com.bea.common.security.xacml.policy.Action;
import com.bea.common.security.xacml.policy.ActionAttributeDesignator;
import com.bea.common.security.xacml.policy.ActionMatch;
import com.bea.common.security.xacml.policy.Actions;
import com.bea.common.security.xacml.policy.AttributeValue;
import com.bea.common.security.xacml.policy.Environment;
import com.bea.common.security.xacml.policy.EnvironmentAttributeDesignator;
import com.bea.common.security.xacml.policy.EnvironmentMatch;
import com.bea.common.security.xacml.policy.Environments;
import com.bea.common.security.xacml.policy.Resource;
import com.bea.common.security.xacml.policy.ResourceAttributeDesignator;
import com.bea.common.security.xacml.policy.ResourceMatch;
import com.bea.common.security.xacml.policy.Resources;
import com.bea.common.security.xacml.policy.Subject;
import com.bea.common.security.xacml.policy.SubjectAttributeDesignator;
import com.bea.common.security.xacml.policy.SubjectMatch;
import com.bea.common.security.xacml.policy.Subjects;
import com.bea.common.security.xacml.policy.Target;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractBuilderBase implements BuilderBase {
   private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+\\.)*\\d+");
   private static final Pattern VERSION_MATCH_PATTERN = Pattern.compile("((\\d+|\\*)\\.)*(\\d+|\\*|\\+)");
   protected static final String DEFAULT_VERSION = "1.0";
   private static final String ACCESS_SUBJECT = "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
   private Map actions = new HashMap();
   private Map resources = new HashMap();
   private Map subjects = new HashMap();
   private Map environments = new HashMap();
   protected Actions xacmlActions;
   protected Resources xacmlResources;
   protected Subjects xacmlSubjects;
   protected Environments xacmlEnvironments;
   protected String description;

   protected abstract BuilderBase getInstance();

   protected AbstractBuilderBase() {
   }

   protected AbstractBuilderBase(Subjects subjects, Resources resources, Actions actions, Environments environments, String description) {
      this.init(subjects, resources, actions, environments, description);
   }

   protected void init(Subjects subjects, Resources resources, Actions actions, Environments environments, String description) {
      this.xacmlActions = actions;
      this.xacmlSubjects = subjects;
      this.xacmlResources = resources;
      this.xacmlEnvironments = environments;
      this.description = description;
   }

   public BuilderBase setDescription(String description) {
      this.description = description;
      return this.getInstance();
   }

   public BuilderBase addAction(int actionIndex, String value) throws InvalidParameterException {
      return this.addAction(actionIndex, "http://www.w3.org/2001/XMLSchema#string", value, "urn:oasis:names:tc:xacml:1.0:action:action-id", Functions.STRING_EQUAL, false, (String)null);
   }

   public BuilderBase addAction(int actionIndex, String dataType, String value, String attributeId, Function function) throws InvalidParameterException {
      return this.addAction(actionIndex, dataType, value, attributeId, function, false, (String)null);
   }

   public BuilderBase addAction(int actionIndex, String dataType, String value, String attributeId, Function function, boolean mustBePresent, String issuer) throws InvalidParameterException {
      if (dataType != null && dataType.trim().length() != 0) {
         if (attributeId != null && attributeId.trim().length() != 0) {
            if (function == null) {
               throw new InvalidParameterException("The function should not be null.");
            } else if (value != null && value.trim().length() != 0) {
               try {
                  ActionAttributeDesignator designator = new ActionAttributeDesignator(new URI(attributeId), new URI(dataType), mustBePresent, issuer);
                  AttributeValue attributeValue = new AttributeValue((new StandardAttributes()).createAttribute(new URI(dataType), value));
                  List actionMatchs = (List)this.actions.get(new Integer(actionIndex));
                  if (actionMatchs == null) {
                     actionMatchs = new ArrayList();
                     this.actions.put(new Integer(actionIndex), actionMatchs);
                  }

                  ((List)actionMatchs).add(new ActionMatch(function.getFunctionId(), attributeValue, designator));
               } catch (XACMLException var11) {
                  throw new InvalidParameterException(var11);
               } catch (URISyntaxException var12) {
                  throw new InvalidParameterException(var12);
               }

               return this.getInstance();
            } else {
               throw new InvalidParameterException("The value should not be null or empty.");
            }
         } else {
            throw new InvalidParameterException("The attribute id should not be null or empty.");
         }
      } else {
         throw new InvalidParameterException("The data type should not be null or empty.");
      }
   }

   public BuilderBase addEnvironment(int environmentIndex, String dataType, String value, String attributeId, Function function) throws InvalidParameterException {
      return this.addEnvironment(environmentIndex, dataType, value, attributeId, function, false, (String)null);
   }

   public BuilderBase addEnvironment(int environmentIndex, String dataType, String value, String attributeId, Function function, boolean mustBePresent, String issuer) throws InvalidParameterException {
      if (dataType != null && dataType.trim().length() != 0) {
         if (attributeId != null && attributeId.trim().length() != 0) {
            if (function == null) {
               throw new InvalidParameterException("The function should not be null.");
            } else if (value != null && value.trim().length() != 0) {
               try {
                  EnvironmentAttributeDesignator designator = new EnvironmentAttributeDesignator(new URI(attributeId), new URI(dataType), mustBePresent, issuer);
                  AttributeValue attributeValue = new AttributeValue((new StandardAttributes()).createAttribute(new URI(dataType), value));
                  List environmentMatchs = (List)this.environments.get(new Integer(environmentIndex));
                  if (environmentMatchs == null) {
                     environmentMatchs = new ArrayList();
                     this.environments.put(new Integer(environmentIndex), environmentMatchs);
                  }

                  ((List)environmentMatchs).add(new EnvironmentMatch(function.getFunctionId(), attributeValue, designator));
               } catch (XACMLException var11) {
                  throw new InvalidParameterException(var11);
               } catch (URISyntaxException var12) {
                  throw new InvalidParameterException(var12);
               }

               return this.getInstance();
            } else {
               throw new InvalidParameterException("The value should not be null or empty.");
            }
         } else {
            throw new InvalidParameterException("The attribute id should not be null or empty.");
         }
      } else {
         throw new InvalidParameterException("The data type should not be null or empty.");
      }
   }

   public BuilderBase addResource(int resourceIndex, String value) throws InvalidParameterException {
      return this.addResource(resourceIndex, "http://www.w3.org/2001/XMLSchema#string", value, "urn:oasis:names:tc:xacml:1.0:resource:resource-id", Functions.STRING_EQUAL, false, (String)null);
   }

   public BuilderBase addResource(int resourceIndex, String dataType, String value, String attributeId, Function function) throws InvalidParameterException {
      return this.addResource(resourceIndex, dataType, value, attributeId, function, false, (String)null);
   }

   public BuilderBase addResource(int resourceIndex, String dataType, String value, String attributeId, Function function, boolean mustBePresent, String issuer) throws InvalidParameterException {
      if (dataType != null && dataType.trim().length() != 0) {
         if (attributeId != null && attributeId.trim().length() != 0) {
            if (function == null) {
               throw new InvalidParameterException("The function should not be null.");
            } else if (value != null && value.trim().length() != 0) {
               try {
                  ResourceAttributeDesignator designator = new ResourceAttributeDesignator(new URI(attributeId), new URI(dataType), mustBePresent, issuer);
                  AttributeValue attributeValue = new AttributeValue((new StandardAttributes()).createAttribute(new URI(dataType), value));
                  List resourceMatchs = (List)this.resources.get(new Integer(resourceIndex));
                  if (resourceMatchs == null) {
                     resourceMatchs = new ArrayList();
                     this.resources.put(new Integer(resourceIndex), resourceMatchs);
                  }

                  ((List)resourceMatchs).add(new ResourceMatch(function.getFunctionId(), attributeValue, designator));
               } catch (URISyntaxException var11) {
                  throw new InvalidParameterException(var11);
               } catch (XACMLException var12) {
                  throw new InvalidParameterException(var12);
               }

               return this.getInstance();
            } else {
               throw new InvalidParameterException("The value should not be null or empty.");
            }
         } else {
            throw new InvalidParameterException("The attribute id should not be null or empty.");
         }
      } else {
         throw new InvalidParameterException("The data type should not be null or empty.");
      }
   }

   public BuilderBase addSubject(int subjectIndex, String value) throws InvalidParameterException {
      return this.addSubject(subjectIndex, "http://www.w3.org/2001/XMLSchema#string", value, "urn:oasis:names:tc:xacml:1.0:subject:subject-id", Functions.STRING_EQUAL, false, (String)null, "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject");
   }

   public BuilderBase addSubject(int subjectIndex, String dataType, String value, String attributeId, Function function) throws InvalidParameterException {
      return this.addSubject(subjectIndex, dataType, value, attributeId, function, false, (String)null, "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject");
   }

   public BuilderBase addSubject(int subjectIndex, String dataType, String value, String attributeId, Function function, boolean mustBePresent, String issuer, String category) throws InvalidParameterException {
      if (dataType != null && dataType.trim().length() != 0) {
         if (attributeId != null && attributeId.trim().length() != 0) {
            if (function == null) {
               throw new InvalidParameterException("The function should not be null.");
            } else if (value != null && value.trim().length() != 0) {
               try {
                  category = category == null ? "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject" : category;
                  SubjectAttributeDesignator designator = new SubjectAttributeDesignator(new URI(attributeId), new URI(dataType), mustBePresent, issuer, new URI(category));
                  AttributeValue attributeValue = new AttributeValue((new StandardAttributes()).createAttribute(new URI(dataType), value));
                  List subjectMatchs = (List)this.subjects.get(new Integer(subjectIndex));
                  if (subjectMatchs == null) {
                     subjectMatchs = new ArrayList();
                     this.subjects.put(new Integer(subjectIndex), subjectMatchs);
                  }

                  ((List)subjectMatchs).add(new SubjectMatch(function.getFunctionId(), attributeValue, designator));
               } catch (XACMLException var12) {
                  throw new InvalidParameterException(var12);
               } catch (URISyntaxException var13) {
                  throw new InvalidParameterException(var13);
               }

               return this.getInstance();
            } else {
               throw new InvalidParameterException("The value should not be null or empty.");
            }
         } else {
            throw new InvalidParameterException("The attribute id should not be null or empty.");
         }
      } else {
         throw new InvalidParameterException("The data type should not be null or empty.");
      }
   }

   public Actions removeActions() {
      Actions tmp = this.createXACMLActions();
      this.actions.clear();
      this.xacmlActions = null;
      return tmp;
   }

   public Environments removeEnvironments() {
      Environments tmp = this.createXACMLEnvironments();
      this.environments.clear();
      this.xacmlEnvironments = null;
      return tmp;
   }

   public Resources removeResources() {
      Resources tmp = this.createXACMLResources();
      this.resources.clear();
      this.xacmlResources = null;
      return tmp;
   }

   public Subjects removeSubjects() {
      Subjects tmp = this.createXACMLSubjects();
      this.subjects.clear();
      this.xacmlSubjects = null;
      return tmp;
   }

   protected Target getTarget() {
      Subjects subs = this.createXACMLSubjects();
      Environments envs = this.createXACMLEnvironments();
      Actions acts = this.createXACMLActions();
      Resources ress = this.createXACMLResources();
      return subs == null && envs == null && acts == null && ress == null ? null : new Target(subs, ress, acts, envs);
   }

   protected void checkVersion(String version) throws InvalidParameterException {
      Matcher matcher = VERSION_PATTERN.matcher(version);
      if (!matcher.matches()) {
         throw new InvalidParameterException("The version [" + version + "] is invalid.");
      }
   }

   protected void checkVersionMatch(String versionMatch) throws InvalidParameterException {
      if (versionMatch != null) {
         Matcher matcher = VERSION_MATCH_PATTERN.matcher(versionMatch);
         if (!matcher.matches()) {
            throw new InvalidParameterException("The version match string [" + versionMatch + "] is invalid.");
         }
      }
   }

   private Actions createXACMLActions() {
      if (this.actions.isEmpty() && this.xacmlActions == null) {
         return null;
      } else {
         List actionList = new ArrayList();
         Iterator ite = this.actions.values().iterator();

         while(ite.hasNext()) {
            actionList.add(new Action((List)ite.next()));
         }

         if (this.xacmlActions != null) {
            actionList.addAll(this.xacmlActions.getActions());
         }

         return new Actions(actionList);
      }
   }

   private Subjects createXACMLSubjects() {
      if (this.subjects.isEmpty() && this.xacmlSubjects == null) {
         return null;
      } else {
         List subjectList = new ArrayList();
         Iterator ite = this.subjects.values().iterator();

         while(ite.hasNext()) {
            subjectList.add(new Subject((List)ite.next()));
         }

         if (this.xacmlSubjects != null) {
            subjectList.addAll(this.xacmlSubjects.getSubjects());
         }

         return new Subjects(subjectList);
      }
   }

   private Environments createXACMLEnvironments() {
      if (this.environments.isEmpty() && this.xacmlEnvironments == null) {
         return null;
      } else {
         List environmentList = new ArrayList();
         Iterator ite = this.environments.values().iterator();

         while(ite.hasNext()) {
            environmentList.add(new Environment((List)ite.next()));
         }

         if (this.xacmlEnvironments != null) {
            environmentList.addAll(this.xacmlEnvironments.getEnvironments());
         }

         return new Environments(environmentList);
      }
   }

   private Resources createXACMLResources() {
      if (this.resources.isEmpty() && this.xacmlResources == null) {
         return null;
      } else {
         List resourceList = new ArrayList();
         Iterator ite = this.resources.values().iterator();

         while(ite.hasNext()) {
            resourceList.add(new Resource((List)ite.next()));
         }

         if (this.xacmlResources != null) {
            resourceList.addAll(this.xacmlResources.getResources());
         }

         return new Resources(resourceList);
      }
   }
}
