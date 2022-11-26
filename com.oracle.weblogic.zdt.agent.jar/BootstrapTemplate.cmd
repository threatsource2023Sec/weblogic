@echo off
rem *************************************************************************
rem Bootstrap script for UpdateOracleHome
rem ${TIMESTAMP}

set VERBOSE=${VERBOSE}
set NEW_JAVA_HOME=${NEW_JAVA_HOME}

rem call %SCRIPT_PATH%\UpdateOracleHome.cmd ^> %VERBOSE_OUTFILE% 2^>^&1

rem does this need to start with "call"?

rem Launch the update command
%JAVA_HOME%\bin\java -jar ${ZDT_AGENT_JAR_PATH}${ZDT_AGENT_JAR_NAME} update "${DOMAIN_DIR}" "${PATCHED}" "${BACKUP_DIR}" "${NEW_JAVA_HOME}" "${REVERT_FROM_ERROR}" "${VERBOSE}" "${WORKFLOW_ID}" "${BEFORE_UPDATE_EXTENSIONS}" "${AFTER_UPDATE_EXTENSIONS}" 2> "${SCRIPT_ERRFILE}" 1> "${SCRIPT_OUTFILE}"

if NOT ERRORLEVEL 1 (
  type "${SCRIPT_ERRFILE}" >> "${SCRIPT_OUTFILE}"
  type "${SCRIPT_OUTFILE}"

  if DEFINED NEW_JAVA_HOME (
     echo Setting JAVA_HOME to %NEW_JAVA_HOME%
     set JAVA_HOME=%NEW_JAVA_HOME%
     echo JAVA_HOME is now %JAVA_HOME%
     exit /b 42
  ) else (
     if exist "${JAVA_BACKUP_DIR}" (
       echo Unsetting JAVA_HOME because we are rolling back to a previous JAVA ver
       set JAVA_HOME=
       exit /b 42
     )
  )
  echo SUCCESS
 ) else (
  echo Error output from "java -jar ${ZDT_AGENT_JAR_PATH}${ZDT_AGENT_JAR_NAME}" >> "${SCRIPT_OUTFILE}"
  type "${SCRIPT_ERRFILE}" >> "${SCRIPT_OUTFILE}"
  echo Standard output from "java -jar ${ZDT_AGENT_JAR_PATH}${ZDT_AGENT_JAR_NAME}" >> "${LOG_ERRFILE}"
  type "${SCRIPT_OUTFILE}" >> "${LOG_ERRFILE}"
  type "${SCRIPT_OUTFILE}"
 )
