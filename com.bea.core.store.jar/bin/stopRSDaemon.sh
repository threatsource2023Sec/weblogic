#!/bin/sh

cmdLineHelp() {
cat << EOF

 $(basename $0) help

 This script is used to shutdown a WebLogic Replicated Store Daemon
 on an Exalogic machine.  All parameters are optional:

 -dir <path>        A Replicated Store Global Directory that exactly matches
  (Default=.)       (A) the shared directory specified for a Replicated Store 
                    in a WebLogic Server config.xml and (B) the shared
                    directory for a Daemon Cluster.  If a "." is specified
                    (the default), then the current directory is used.  The
                    directory must contain an "${RS_DAEMONS_CFG}" file.  It
                    is recommended to supply a full absolute path.  The
                    directory must be located on a specially tuned NFS mount.
                    See the Replicated Store admininstration documentation
                    for more information.

 -localindex <idx>  The local index determines which particular local Daemon 
  (Default=0)       is shutdown when more than one Daemon is configured to run
                    on the current node.  The following formula determines
                    which Daemon is picked:

                      ((idx) modulo (number-of-local-daemons))

                    By default (e.g. idx=0), this resolves to the first
                    entry in the ${RS_DAEMONS_CFG} with the same network
                    address as the current node.

 -mode <force|safe> The mode option controls whether the shutdown command
  (Default=safe)    guards against data loss.  
                    - Safe mode, the default, causes the affected Daemon
                      to try migrate all inactive regions to other Daemon(s)
                      that have enough memory to host the migrated regions.
                      This ensures that the affected regions continue to have 
                      at least two replicas in the Daemon cluster.
                      If there are no other Daemons that have enough memory
                      for the migrated regions, if any regions are active, or
                      the Daemon is the last Daemon in the cluster, then a
                      safe shutdown command will fail, and the Daemon will
                      keep running.  
                    - Force mode does not migrate any regions, and causes
                      the affected Daemon to shutdown regardless of whether
                      a region is active.
                    To make sure that a region is inactive, the corresponding
                    WebLogic Server Replicated Store must be shutdown or closed.

 -?|-h|-help        This help.

 This script expects either WL_HOME or MW_HOME to be set.  It also expects
 either DOMAIN_HOME or JAVA_HOME to be set.  

EOF
}
# *****************************************************************************

# TBD/TODO Ask Arun if safe mode works with second to last Daemon in
#          in the cluster.

# load the WL environment, including the LD_LIBRARY_PATH

quote(){
  echo "$1" | sed -e "s,','\\\\'',g"
}

SKIP_COMM_ENV=false
if [ -z "${JAVA_HOME}" ] && [ ! -z "${DOMAIN_HOME}" ]; then
  # get JAVA_HOME from the domain
  SKIP_COMM_ENV=true

  # save cur directory and args
  SAVEDIR="$(pwd)"
  SAVEARGS=""
  while [ $# -gt 0 ]; do
    SAVEARGS="${SAVEARGS:+$SAVEARGS }'$(quote "$1")'"
    shift
  done

  # load domain env
  JAVA_USE_64BIT=true
  . ${DOMAIN_HOME}/bin/setDomainEnv.sh 

  # restore cur directory and args
  cd "${SAVEDIR}"
  if [ ! -z "$SAVEARGS" ]; then
    eval set -- "$SAVEARGS"
  fi
fi

WL_HOME="@WL_HOME"
export WL_HOME

if [ "$WL_HOME" = "" ]; then
  if [ "$MW_HOME" = "" ]; then
    echo "ERROR: Neither MW_HOME nor WL_HOME are initialized."
    exit 1
  fi
  WL_HOME="$MW_HOME/wlserver"
fi

COMM_ENV="${WL_HOME}/../oracle_common/common/bin/commEnv.sh"

if [ ! -f "${COMM_ENV}" ]; then
  echo "ERROR: ${COMM_ENV} not found.  Check WL_HOME and MW_HOME."
  exit 1
fi

if [ ! "${SKIP_COMM_ENV}" = "true" ]; then
  unset JAVA_VM MEM_ARGS
  JAVA_USE_64BIT=true
  . "${WL_HOME}/../oracle_common/common/bin/commEnv.sh"
fi

# parse and check command line arguments

RS_DAEMONS_CFG="./rs_daemons.cfg"
RS_LOCAL_INDEX="0"
RS_GLOBAL_DIR="."
RS_SHUTDOWN_MODE="safe"
  
checkArg() {
  if [ -z "${2}" ]; then
    echo "ERROR: Syntax Error - ${1} specified without an argument."
    exit 1
  fi
}

checkInt() {
  checkArg ${1} ${2}
  if echo "${2}" | egrep -q '^[0-9]+$'; then
    return 0
  fi
  echo "ERROR: Syntax Error - ${1} expects a positive integer as an argument, but was given [${2}]."
  exit 1
}

while [ $# -gt 0 ]; do
  cParm="${1}"
  shift
  case "${cParm}" in 
  -dir)             checkArg ${cParm} ${1}
                    RS_GLOBAL_DIR="${1}"
                    shift ;;

  -localindex)      checkInt ${cParm} ${1}
                    RS_LOCAL_INDEX="${1}"
                    shift ;;

  -mode)            checkArg ${cParm} ${1}
                    RS_SHUTDOWN_MODE="${1}"
                    shift ;;

  -h|-help|-\?)     cmdLineHelp
                    exit 0 ;;

  *)                echo "ERROR: Syntax Error - Unrecognized parameter [${cParm}]."
                    exit 1 ;;
  esac
done

# cd to the Global Dir and make sure it's accessible

if ! cd ${RS_GLOBAL_DIR} 2>&1 > /dev/null
then
  echo ""
  echo "ERROR: Could not access the Global Directory [${RS_GLOBAL_DIR}].  Check if this directory exists, if the -dir parameter was specified correctly, and if the current user has permission to cd to this directory."
  exit 1
else
  RS_GLOBAL_DIR="`pwd`"
fi

# ensure the Global Dir has a Daemon cfg file

if [ ! -f ${RS_DAEMONS_CFG} ]; then
  echo ""
  echo "ERROR: No [${RS_DAEMONS_CFG}] file found in the Global Directory [${RS_GLOBAL_DIR}].  Check if the -dir parameter was specified correctly."
  exit 1
fi


# ensure the only either -force or -safe option is specified

if [ "x${RS_SHUTDOWN_MODE}" = "xforce" ]; then
  echo ""
  echo "WARNING:  The -mode is specified as [${RS_SHUTDOWN_MODE}]. The Daemon will shutdown even if it hosts the only copy of a region, or even if a region is in use. To help protect against data loss in a production environment, Oracle recommends specifying \"safe\" (which is the default) as the value for the \"-mode\" option."
fi


# Check for JDK
if [ ! -x "${JAVA_HOME}/bin/java" ]; then
  echo "ERROR:  A JVM wasn't found at ${JAVA_HOME}/bin/java please check your JAVA_HOME (JAVA_HOME=\"${JAVA_HOME}\")."
  exit 1
fi

if [ "${MEM_ARGS}" = "" ]
then
  MEM_ARGS="-Xms32m -Xmx200m"
fi

CLASSPATH="${WEBLOGIC_CLASSPATH}${CLASSPATHSEP}${CLASSPATH}${CLASSPATHSEP}"

# Get PRE and POST environment
if [ ! -z "${PRE_CLASSPATH}" ]; then
  CLASSPATH="${PRE_CLASSPATH}${CLASSPATHSEP}${CLASSPATH}"
fi
if [ ! -z "${POST_CLASSPATH}" ]; then
  CLASSPATH="${CLASSPATH}${CLASSPATHSEP}${POST_CLASSPATH}"
fi

export CLASSPATH
export PATH

# pipe a shutdown command into java web logic.store.Admin
#  (and strip "->" and help prompts from the output)

TMPROOT="$(basename $0).$$.tmp.XXXXXXXXXX"

TMPFILE=$(mktemp -t $TMPROOT) || {
  echo "ERROR:  Failed to create temporary file."
  exit 1
}

echo "INFO: About to use java weblogic.store.Admin to call \"rsattach -dir ${RS_GLOBAL_DIR} -localindex ${RS_LOCAL_INDEX}\" and \"shutdown -${RS_SHUTDOWN_MODE} -daemon local\"."

"${JAVA_HOME}/bin/java" ${JAVA_VM} ${MEM_ARGS} ${JAVA_OPTIONS} -Djava.security.policy="${WL_HOME}/server/lib/weblogic.policy" weblogic.store.Admin \
 << EOF 2>&1 | sed 's/^.*->//' | grep -v "^Type.*for available" | tee ${TMPFILE} 
rsattach -dir ${RS_GLOBAL_DIR} -localindex ${RS_LOCAL_INDEX} 
shutdown -${RS_SHUTDOWN_MODE} -daemon local
quit
EOF

if grep -i ERROR ${TMPFILE} 2>&1 > /dev/null
then
  TMPERROR=1
fi

rm ${TMPFILE}

exit $TMPERROR
