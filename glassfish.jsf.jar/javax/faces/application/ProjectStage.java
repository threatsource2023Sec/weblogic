package javax.faces.application;

public enum ProjectStage {
   Development,
   UnitTest,
   SystemTest,
   Production;

   public static final String PROJECT_STAGE_PARAM_NAME = "javax.faces.PROJECT_STAGE";
   public static final String PROJECT_STAGE_JNDI_NAME = "java:comp/env/jsf/ProjectStage";
}
