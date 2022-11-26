# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2011,2017, Oracle and/or its affiliates. All rights reserved.

"""
This script defines node manager commands can be run in both online and offline modes.
These commands don't need be redefined when WLST user switches between online
and offline mode.

"""

import sys
import java
import jarray
from jarray import array
from java import *
from weblogic import *
from java.lang import *
from javax.management import *
from weblogic.management.scripting import ScriptException
from org.python.core import PyArray
from org.python.core import PyClass
from org.python.core import PyComplex
from org.python.core import PyDictionary
from org.python.core import PyException
from org.python.core import PyFile
from org.python.core import PyFloat
from org.python.core import PyInteger
from org.python.core import PyList
from org.python.core import PyLong
from org.python.core import PyObject
from org.python.core import PyString
from java.util import Properties
from weblogic.management.scripting.core.utils import WLSTCoreUtil

### Define all the global variables here

global theInterpreter
if theInterpreter == None:
    theInterpreter=WLSTCoreUtil.ensureInterpreter();

WLS_CORE=WLSTCoreUtil.ensureWLCtx(theInterpreter)
nmService=WLS_CORE.getNodeManagerService();
fmtr=WLS_CORE.getWLSTMsgFormatter()

home=None
adminHome=None
cmo=None
CMO=None
mbs=None
cmgr=None
domainRuntimeService=None
runtimeService=None
editService=None
_editService=None
typeService=None
myps1="wls:/offline> "
sys.ps1=myps1
ncPrompt="wls:/offline> "
serverName = "";
domainName = "";
connected = "false"
lastPrompt = ""
domainType = ""
promptt = ""
hideProm = "false"
username = ""
isAdminServer = ""
recording = "false"
scriptMode = "false"
dcCalled = "false"
exitonerror = "true"
wlstPrompt = "true"
wlstVersion = "dev2dev version"
oldhook=sys.displayhook
true=1
false=0
LAST = None

### NodeManager

def nmConnect(username=None,
              password=None,
              host='localhost',
              port=-1,
              domainName=None,
              domainDir=None,
              nmType='ssl',
              verbose = 'false',
              timeout = 0,
              **useBootProperties):
  # connects to the node manager
  if nmService.isConnectedToNM():
    WLS_CORE.println(fmtr.getAlreadyConnected())
    return
  try:
    nmService.nmConnect(username,
                        password,
                        host,
                        port,
                        domainName,
                        domainDir,
                        nmType,
                        verbose,
                        timeout,
                        useBootProperties)
    updateCoreGlobals()
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def nmDisconnect():
  # disconnects from Node Manager
  try:
    nmService.nmDisconnect()
    updateCoreGlobals()
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def nm():
  global LAST
  # Indicated if the user is connected to Node Manager
  try:
    hideDisplay()
    LAST = nmService.nm()
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def nmKill(serverName='myserver', serverType='WebLogic', pluginProps=None):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmKill(serverName, serverType, pluginProps)
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def nmSoftRestart(serverName='myserver', serverType=None, pluginProps=None):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmSoftRestart(serverName, serverType, pluginProps)
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def nmStart(serverName='myserver',
            domainDir=None,
            props=None,
            writer=None,
            serverType = 'WebLogic',
            pluginProps=None):
  global LAST
  try:
    hideDisplay()
    result = nmService.nmStart(serverName,
                               serverType,
                               domainDir,
                               props,
                               pluginProps,
                               writer)
    updateCoreGlobals()
    LAST = result
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def nmServerStatus(serverName='myserver', serverType='WebLogic'):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmServerStatus(serverName, serverType)
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def nmServerLog(serverName='myserver', writer=None, serverType='WebLogic'):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmServerLog(serverName, writer, serverType)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def nmLog(writer=None):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmLog(writer)
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def nmDiagnostics(diagnosticsType=None, command=None, writer=None, serverType=None):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmDiagnostics(diagnosticsType, command, writer, serverType)
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

# Un-documented
# @exclude
def nmInvocation(invocationType=None, command=None, writer=None, serverType=None):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmInvocation(invocationType, command, writer, serverType)
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def nmVersion():
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmVersion()
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())


def nmRestart(timeout=0):
  global LAST
  try:
    hideDisplay()
    result = nmService.nmRestart(timeout)
    updateCoreGlobals()
    LAST = result
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())


def nmExecScript(scriptName=None,
            scriptDir=None,
            scriptProps=None,
            writer=None,
            timeout=0):
  global LAST
  try:
    hideDisplay()
    result = nmService.nmExecScript(scriptName,
                               scriptDir,
                               scriptProps,
                               writer,
                               timeout)
    updateCoreGlobals()
    LAST = result
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

# Un-documented
# @exclude
def hideDisplay():
  sys.displayhook=eatdisplay

# call the function here
def stopNodeManager():
    nmService.nmQuit()
    updateCoreGlobals()


def nmPrintThreadDump(serverName='myserver', serverType='WebLogic', pluginProps=None):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmPrintThreadDump(serverName, serverType, pluginProps)
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())


