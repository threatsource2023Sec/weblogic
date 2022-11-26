@start rule: main
<project name="@project_name" default="help">

  <!-- This build file was automatically generated at @time on @date by @generator -->

  <!-- BUILD PROPERTIES ADJUST THESE FOR YOUR ENVIRONMENT -->
  <property name="tmp.dir" value="/tmp" />
  <property name="dist.dir" value="${tmp.dir}/dist"/>
  <property name="app.name" value="@project_name" />
  <property name="ear" value="${dist.dir}/${app.name}.ear"/>
  <property name="ear.exploded" value="${dist.dir}/${app.name}_exploded"/>
  <property name="verbose" value="true" />
  <property name="user" value="@user" />
  <property name="password" value="@password" />
  <property name="servername" value="myserver" />
  <property name="adminurl" value="@adminurl" />

  <property name="src.dir" value="@srcdir" />
  <property name="dest.dir" value="${tmp.dir}/${app.name}" />

  <!-- Print's out the project help -->
  <target name="help">
    <java fork="no" classname="org.apache.tools.ant.Main">
      <arg line="-projecthelp"/>
    </java>
  </target>

  <typedef resource="weblogic/ant/taskdefs/antlib.xml"/>

  <!-- Builds the entire @project_name application -->
  <target name="build" depends="compile, appc" description="Compiles @project_name application and runs appc" />

  <!-- Only compiles @project_name application, no appc -->
  <target name="compile" description="Only compiles @project_name application, no appc">
    <wlcompile srcdir="${src.dir}" destdir="${dest.dir}"@nested_libraries@wlcompile_end
  </target>

  <!-- Deletes the dest.dir to clean the build -->
  <target name="clean" description="Deletes the build directory">
    <delete dir="${dest.dir}" />
  </target>


  <!-- Runs weblogic.appc on your application -->
  <target name="appc" description="Runs weblogic.appc on your application">
    <wlappc source="${dest.dir}" verbose="${verbose}"@nested_libraries@appc_end
  </target>

   @build_components

  <!-- Deploys the entire @project_name application -->
  <target name="deploy" description="Deploys (and redeploys) the entire @project_name application">
    <wldeploy user="${user}" password="${password}" adminurl="${adminurl}"
              action="deploy" source="${dest.dir}"
              name="${app.name}" />
  </target>

  <!-- Undeploys the entire @project_name application -->
  <target name="undeploy" description="Undeploys the entire @project_name application">
    <wldeploy user="${user}" password="${password}" adminurl="${adminurl}"
              action="undeploy" name="${app.name}" />
  </target>

  @redeploy_components

 
  <!-- Package a standard Java EE ear for distribution  -->
  <target name="ear" depends="build" description="Package a standard Java EE EAR for distribution">
    <mkdir dir="${dist.dir}"/>
    <wlpackage srcdir="${src.dir}" destdir="${dest.dir}"
               toFile="${ear}" />
  </target>

  <!-- Package a standard exploded Java EE ear for distribution  -->
  <target name="ear.exploded" depends="build" description="Package a standard exploded Java EE EAR">
    <mkdir dir="${dist.dir}"/>
    <wlpackage srcdir="${src.dir}" destdir="${dest.dir}"
               toDir="${ear.exploded}" />
  </target>

  <!-- Configure WebLogic Server with resources required by application -->
  <!-- For example you can configure JDBCConnection Pools and Data Sources, and JMS Destinations etc -->
  <target name="config.server" description="Configure server with resources required by application">
     <echo message="You will need to customize this based on your application.  Medrec/setup/build.xml has examples..."/>
     <!--    
      <wlconfig url="${adminurl}" username="${user}"
                password="${password}">
      </wlconfig>
     -->
  </target>
@deploy_libraries@undeploy_libraries@deploy_all_libraries@undeploy_all_libraries

</project>

@end rule: main

@start rule: build_component

  <!-- Builds just @component_name of the application -->
  <target name="compile.@component_name" description="Compiles just the @component_name module of the application">
    <wlcompile srcdir="${src.dir}" destdir="${dest.dir}"
               includes="@component_name"@nested_libraries@wlcompile_end
  </target>
@end rule: build_component

@start rule: redeploy_component

  <!-- Redeploys just @component_name of the application -->
  <target name="redeploy.@component_name" description="Redeploys just the @component_name module of the application">
    <wldeploy user="${user}" password="${password}" adminurl="${adminurl}"
              action="redeploy" targets="@target_name"
              name="${app.name}" />
  </target>
@end rule: redeploy_component



#
# All rules below deal with app libraries
#



#
# Libraries as nested elements in wlcompile and appc
#



@start rule: app_libraries_rule
@app_libraries
@end rule: app_libraries_rule

@start rule: unresolved_applib_refs_rule
      <!-- These referenced libraries were not found -->
@unresolved_applib_refs
@end rule: unresolved_applib_refs_rule

@start rule: app_library
      <library file="@library_file" /> 
@end rule: app_library

@start rule: unresolved_applib_ref
      <!-- <library file="@unresolved_library_ref" /> -->
@end rule: unresolved_applib_ref



#
# Library deployment
#


@start rule: commented_library_target_comment
  <!-- Commented out because the reference for this library did not resolve -->
@end rule: commented_library_target_comment

@start rule: deploy_library_unresolved_ref

@deploy_library_comment@commented_library_target_comment  <!--
@deploy_library_rule  -->
@end rule: deploy_library_unresolved_ref

@start rule: deploy_library

@deploy_library_comment@deploy_library_rule
@end rule: deploy_library

@start rule: deploy_library_comment
  <!-- Deploys the app library @library_name -->
@end rule: deploy_library_comment

@start rule: deploy_library_rule
  <target name="deploy.lib.@library_target_name" description="Deploys the app library @library_name">
    <wldeploy user="${user}" password="${password}" adminurl="${adminurl}"
              action="deploy" library="true" source="@library_file" />
  </target>
@end rule: deploy_library_rule



#
# Library undeployment
#



@start rule: undeploy_library_unresolved_ref

@undeploy_library_comment@commented_library_target_comment  <!--
@undeploy_library_rule  -->
@end rule: undeploy_library_unresolved_ref

@start rule: undeploy_library

@undeploy_library_comment@undeploy_library_rule
@end rule: undeploy_library

@start rule: undeploy_library_comment
  <!-- Undeploys the app library @library_name -->
@end rule: undeploy_library_comment


@start rule: undeploy_library_rule
  <target name="undeploy.lib.@library_target_name" description="Undeploys the app library @library_name">
    <wldeploy user="${user}" password="${password}" adminurl="${adminurl}"
              action="undeploy" name="@library_name"@libspecver@libimplver />
  </target>
@end rule: undeploy_library_rule



#
# Targets for deploying/undeploying all required libraries.
#



@start rule: deploy_all_libraries_rule

  <!-- Deploy all libraries required by this app -->
  <target name="deploy.lib.all" description="Deploy all libraries required by this app" depends="@all_libraries_deploy_targets" />
@end rule: deploy_all_libraries_rule

@start rule: undeploy_all_libraries_rule

  <!-- Undeploy all libraries required by this app -->
  <target name="undeploy.lib.all" description="Undeploy all libraries required by this app" depends="@all_libraries_undeploy_targets" />
@end rule: undeploy_all_libraries_rule







