#!/bin/sh
# Copyright (c) 2013,2016, Oracle and/or its affiliates. All rights reserved.

cmdLineHelp() {
cat << EOF

 $(basename $0) help

 This script is used to start a WebLogic Replicated Store Daemon
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
  (Default=0)       to start when more than one Daemon is configured to run
                    on the current node.  The following formula determines
                    which Daemon is picked:

                     ((idx) modulo (number-of-local-daemons))

                    By default (e.g. idx=0), this resolves to the first
                    entry in the ${RS_DAEMONS_CFG} with the same network
                    address as the current node.

                    NOTE: This setting is not applicable to production
                    environments.  To ensure high availability, a production
                    environment will have an RS Daemon Cluster configured
                    to run on multiple nodes with one Daemon on each node.

 -loglevel <num>    The logging level for this Daemon.  0=None, 1=Error,
  (Default=2)       2=Info, and 3 or higher enables debugging/tracing.

 -logdir <path|->   The "logs" directory location for this Daemon.  When
  (Default=./)      specified as a relative path, the directory is created
                    relative to the '-dir' global directory.

 -logfilesize <num> Maximum size of an individual log file in MB.
  (Default=500)    

 -logfilemax <num>  Maximum number of log files.
  (Default=10)    

 -?|-h|-help       This help.

EOF
}

# *****************************************************************************


xHelp() {
  DAEMON_EXEC="${1}"

  echo ""
  echo "WARNING: The following help includes some hidden, undocumented, and/or normally unsupported parameters for WebLogic Replicated Store Daemons.  Such parameters should not be used unless directed to do so by Oracle customer support.  To use a potentially unsupported parameter in combination with this start script, specify a '-X' before the given parameter.  To see fully supported parameters, specify -? or -help on the command line of this start script."
  echo ""
  $DAEMON_EXEC -help
  res=$?
  echo ""
  return $res
}

postInstallHelp() {
  EXEC_FILE="${1}"
  echo ""
  echo
  echo "INFO: As a one-time post-installation step, a Daemon needs Set User ID and Set Group ID permissions so that it can boost its operating system process priority. To enable these permissions, run the following commands as a root user:"
  echo
  echo chown root "${EXEC_FILE}"
  echo chgrp root "${EXEC_FILE}"
  echo chmod 7555 "${EXEC_FILE}"
  echo
}

WL_HOME="@WL_HOME"
export WL_HOME

# load the WL environment, including the LD_LIBRARY_PATH

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

JAVA_USE_64BIT=true
. "${WL_HOME}/../oracle_common/common/bin/commEnv.sh"

# make sure that the Daemon binary and Daemon libraries are available

findInLibraryPath() {
  for lDir in dummydir `echo ${LD_LIBRARY_PATH} | sed 's/:/ /g'`
  do
    if [ -f ${lDir}/${1} ]; then echo "${lDir}/${1}"; 
      return 0; 
    fi
  done
  echo "nomatch"
  return 1
}

BIN_NAME=rs_daemon

SO_NAMES="libwlrepstore1.so libmql1.so libipc1.so libcloudstore1.so"

for DFILE in ${BIN_NAME} ${SO_NAMES}; do
  if ! findInLibraryPath "${DFILE}" 2>&1 > /dev/null 
  then
    echo "ERROR:  The [${BIN_NAME}] Replicated Store Daemon binary and/or a required library [${SO_NAMES}] was not found in any of the directories of LD_LIBRARY_PATH.  Check if running on a supported platform.  LD_LIBRARY_PATH=[${LD_LIBRARY_PATH}]."
    exit 1
  fi
done

EXEC_FILE="`findInLibraryPath ${BIN_NAME}`"

if [ ! -x ${EXEC_FILE} ]; then
  echo "ERROR:  The ${EXEC_FILE} Replicated Store Daemon binary is not an executable file or the current user doesn't have permission to execute this binary. Check the WebLogic Replicated Store installation instructions."
  exit 1
fi

# parse and check command line arguments

RS_DAEMONS_CFG="./rs_daemons.cfg"
RS_LOCAL_INDEX="0"
RS_LOG_LEVEL="2"
RS_GLOBAL_DIR="."
RS_LOG_DIR="./"
RS_LOG_FILE_MAX="10"
RS_LOG_FILE_SIZE="500"

# special handling vars for some of the extensions

RS_XPORT="0"
RS_XPARMS=""
RS_XTRACEFILE=""
xMode=false
safeMode=true

checkArg() {
  if [ -z "${2}" ]; then
    echo "ERROR:  Syntax Error - ${1} specified without an argument." 
    exit 1
  fi
}

checkInt() {
  checkArg ${1} ${2}
  if echo "${2}" | egrep '^[0-9]+$' > /dev/null 2>&1 ; then
    return 0
  fi
  echo "ERROR:  Syntax Error - ${1} expects a positive integer as an argument, but was given [${2}]." 
  exit 1
}

while [ $# -gt 0 ]; do
  cParm=${1}
  shift
  case "${cParm}" in 
  -dir)             checkArg ${cParm} ${1}
                    RS_GLOBAL_DIR="${1}"
                    shift ;;

  -localindex)      checkInt ${cParm} ${1}
                    RS_LOCAL_INDEX="${1}"
                    shift ;;

  -logdir)          checkArg ${cParm} ${1}
                    RS_LOG_DIR=${1}
                    shift ;;

  -loglevel)        checkInt ${cParm} ${1}
                    RS_LOG_LEVEL="${1}"
                    shift ;;

  -logfilesize)     checkInt ${cParm} ${1}
                    RS_LOG_FILE_SIZE="${1}"
                    shift ;;

  -logfilemax)      checkInt ${cParm} ${1}
                    RS_LOG_FILE_MAX="${1}"
                    shift ;;

  -h|-help|-\?)     cmdLineHelp
                    exit 0 ;;

  -postinstallhelp) postInstallHelp "${EXEC_FILE}"
                    exit 0 ;;

  -Xh|-Xhelp|-X\?)  xHelp $EXEC_FILE
                    exit $?  ;;

  -XS)              RS_XPARMS="${RS_XPARMS} -S"
                    xMode=true ;;

  -Xo)              checkArg ${cParm} ${1}
                    RS_XTRACEFILE="${1}"
                    xMode=true
                    shift ;;

  -Xp)              checkArg ${cParm} ${1}
                    RS_XPORT="${1}"
                    xMode=true
                    shift ;;

  -Xc)              checkArg ${cParm} ${1}
                    RS_DAEMONS_CFG="${1}"
                    xMode=true
                    shift ;;

  -Xunsafe)         safeMode=false
                    xMode=true ;;

  -X*)              checkArg ${cParm} ${1}
                    RS_XPARMS="${RS_XPARMS} -${cParm:2} ${1}"
                    xMode=true
                    shift ;;

  *)                echo "ERROR:  Syntax Error - Unrecognized parameter [${cParm}]."
                    exit 1 ;;
  esac
done

# check if the Daemon binary has elevated privileges, fail if doesn't
# unless -Xunsafe has been set

RS_ARGS=
eUser="`ls -dl ${EXEC_FILE} | awk '{ print $3 }'`"
eGroup="`ls -dl ${EXEC_FILE} | awk '{ print $4 }'`"
if [ ! -u "${EXEC_FILE}" ] \
   || [ ! -k "${EXEC_FILE}" ] \
   || [ ! -g "${EXEC_FILE}" ] \
   || [ ! "$eUser" = "root" ] \
   || [ ! "$eGroup" = "root" ] 
then
  if [ ${safeMode} = true ]; then
    echo
    echo "ERROR:  The [${EXEC_FILE}] Replicated Store Daemon binary does not have permission to raise its process priority. Check the WebLogic Replicated Store installation instructions.  To run without an elevated process priority, specify -Xunsafe as a parameter."
    echo
    postInstallHelp "${EXEC_FILE}"
    echo
    exit 1
  fi 
  echo ""
  echo "WARNING:  The Daemon does not have privileges to elevate its process priority, and this may lead to unstable behavior.  The script will still try to start the Daemon because -Xunsafe was specified as a start script parameter."
  RS_ARGS="${RS_ARGS} -Q"
fi

# cd to the Global Dir and make sure it's accessible

if ! cd ${RS_GLOBAL_DIR} 2>&1 > /dev/null
then
  echo ""
  echo "ERROR: Could not access Global Directory [${RS_GLOBAL_DIR}].  Check if this directory exists, if the -dir parameter was specified correctly, and if the current user has permission to cd to this directory."
  exit 1
else
  RS_GLOBAL_DIR="`pwd`"
fi

# ensure the Global Dir has a Daemon cfg file

if [ ! -f ${RS_DAEMONS_CFG} ]; then
  echo ""
  echo "ERROR: No [${RS_DAEMONS_CFG}] file found in the Global Directory [${RS_GLOBAL_DIR}].  A Replicated Store Daemon requires this file.  Check if the -dir parameter was specified correctly."
  exit 1
fi

# if local-index > 0, warn that it's not for production mode use

if [ ${RS_LOCAL_INDEX} -gt 0 ]; then
  echo ""
  echo "WARNING:  The specified -localindex value of [${RS_LOCAL_INDEX}] is greater than 0. This indicates that multiple Daemons from the same Replicated Store Daemon Cluster may be setup to run on the same node.  Running multiple Daemons on the same node is acceptable for test and demonstration purposes but increases the risk of data loss if the entire node fails.  If you are running in a production environment, Oracle recommends running a single Daemon per node."
fi

# warn if any unsupported parm is set

if [ ${xMode} = true ]; then
  echo ""
  echo "WARNING:  One or more command line parameters starts with a '-X'.  Such parameters should not be used unless directed to do so by Oracle customer support.  To see fully supported parameters, specify -? or -help on the command line of this start script."
fi

# append to argument list for the Daemon binary

RS_ARGS="${RS_ARGS} -c ${RS_GLOBAL_DIR}/${RS_DAEMONS_CFG} -t ${RS_LOG_LEVEL} -o ${RS_LOG_DIR} -n ${RS_LOG_FILE_MAX} -z ${RS_LOG_FILE_SIZE}"
RS_EXT=".${HOSTNAME}"

if [ ! "${RS_XPORT}" = "0" ]; then
  RS_ARGS="${RS_ARGS} -p ${RS_XPORT}"
  RS_EXT="${RS_EXT}.${RS_XPORT}"
fi

if [ ${RS_LOCAL_INDEX} -gt 0 ]; then
  RS_ARGS="${RS_ARGS} -L ${RS_LOCAL_INDEX}"
fi

RS_EXT="${RS_EXT}.${RS_LOCAL_INDEX}"

if [ ! ${RS_XTRACEFILE} = "" ]; then
  RS_ARGS="${RS_ARGS} -o ${RS_XTRACEFILE}.${RS_EXT}"
fi

RS_ARGS="${RS_ARGS} ${RS_XPARMS}"

# echo the final binary command line, then run it

echo
echo ${EXEC_FILE} ${RS_ARGS}
echo

${EXEC_FILE} ${RS_ARGS}

# *****************************************************************************
