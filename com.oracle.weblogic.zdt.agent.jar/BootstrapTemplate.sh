#!/bin/sh
# 
# Bootstrap script for UpdateOracleHome
# ${TIMESTAMP}
#

# Launch the update command
${JAVA_HOME}/bin/java -jar ${ZDT_AGENT_JAR_PATH}${ZDT_AGENT_JAR_NAME} update "${DOMAIN_DIR}" "${PATCHED}" "${BACKUP_DIR}" "${NEW_JAVA_HOME}" "${REVERT_FROM_ERROR}" "${VERBOSE}" "${WORKFLOW_ID}" "${BEFORE_UPDATE_EXTENSIONS}" "${AFTER_UPDATE_EXTENSIONS}" 2> "${SCRIPT_ERRFILE}" 1> "${SCRIPT_OUTFILE}"
RESULT=$?

if [ $RESULT -eq 0 ]; then
  cat "${SCRIPT_ERRFILE}" >> "${SCRIPT_OUTFILE}"
  cat "${SCRIPT_OUTFILE}"
  if [ -n "${NEW_JAVA_HOME}" ]; then
    echo "Setting JAVA_HOME to ${NEW_JAVA_HOME}"
    JAVA_HOME=${NEW_JAVA_HOME}
    echo "JAVA_HOME is now $JAVA_HOME"
    export JAVA_HOME
    unset CLASSPATH
    # Calling script (startNM or startWL) will look for this value to reset JavaHome
    exit 42
    #TODO: remove JAVA_HOME from path
  elif [ -d "${JAVA_BACKUP_DIR}" ]; then  # if the java files were backed up, we might have changed the java home
    echo "Unsetting JAVA_HOME because we are rolling back to a previous JAVA version"
    unset JAVA_HOME
    unset CLASSPATH
    # Calling script (startNM or startWL) will look for this value to reset JavaHome
    exit 42
  fi
  echo "SUCCESS"
else
  echo "Error output from \"java -jar ${ZDT_AGENT_JAR_PATH}${ZDT_AGENT_JAR_NAME}\"" >> "${SCRIPT_OUTFILE}"
  cat "${SCRIPT_ERRFILE}" >> "${SCRIPT_OUTFILE}"
  echo "Standard output from \"java -jar ${ZDT_AGENT_JAR_PATH}${ZDT_AGENT_JAR_NAME}\"" >> "${LOG_ERRFILE}"
  cat "${SCRIPT_OUTFILE}" >> "${LOG_ERRFILE}"
  cat "${SCRIPT_OUTFILE}"
fi

