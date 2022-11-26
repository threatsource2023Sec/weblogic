# Copyright (c) 2015-2016, Oracle and/or its affiliates. All rights reserved.
#
# JRF MT WLST Command Line Interface - to provision partition for upper stack
# components, e.g. OPSS, OWSM, etc.
#
# This script contains commands to:
#
# 1. get the configurable attributes, required, to provision a partition, for
# a given component type;
#
# 2. provision a partition for a given component type, using the properties file;
#
# Use debug() to turn printing debug messages on/off
#
# Use dumpStack() when seeing error, to dump the entire current stacktrace on
# console
# -------------------------------------------------------------------------------

from com.oracle.weblogic.lifecycle.provisioning.api import ProvisioningException

from com.oracle.weblogic.lifecycle.provisioning.wlst import ProvisioningWLSTCLI

# In WLST, the name Exception refers to java.lang.Exception, not
# Python's exceptions.Exception type.  To refer to the latter, it is
# necessary to import it as an alias.
from exceptions import Exception as PythonException

from java.io import BufferedReader, \
                    File, \
                    FileOutputStream, \
                    FileReader, \
                    IOException, \
                    OutputStreamWriter, \
                    Reader, \
                    Writer

from java.lang import CharSequence

from java.nio.charset import Charset, StandardCharsets

from java.text import MessageFormat

from java.util import MissingResourceException, Properties

from java.util.logging import Handler, Level, Logger, LogManager, LogRecord

import warnings


#
# Global variables.
#


# An instance of
# com.oracle.weblogic.lifecycle.provisioning.wlst.ProvisioningWLSTCLI.
#
# Assigned by the connectToREST() and disconnectFromREST() functions
# below.
objProvisioningWLSTCLI = None


#
# Functions.
#


def disconnectFromREST(prior=None):
    """Disconnects from the provisioning endpoint and releases resources.

    prior - if True then a message indicating that the connection is a
    prior one may be printed

    """
    global objProvisioningWLSTCLI
    if objProvisioningWLSTCLI is not None:
        if prior:
            printIfInteractive('Closing prior connection...')
        else:
            printIfInteractive('Disconnecting from REST...')
            
        try:
            try:
                objProvisioningWLSTCLI.close()
            except Exception, e:
                if WLS_ON is not None:
                    WLS_ON.setDumpStackThrowable(e)
                raise
        finally:
            objProvisioningWLSTCLI = None
        printIfInteractive('Ok')

def connectToREST(*args, **kwargs):
    """Connects to the REST endpoint of the provisioning framework.

    host - the host to connect to

    port - the port to use 

    username - the username to use for the connection attempt

    password - the password to use for the connection attempt

    Note: A prior version of this method took three arguments, and
    invoking it this way is still supported, though deprecated:

    restURI - a string formatted like a URI identifying the host and
    port to connect to; e.g. 'http://localhost:7001/'

    username - as above

    password - as above

    """

    #
    # A prior version of this function accepted a minimum of three
    # arguments.  The first was treated as a string that was expected
    # to conform to the following underspecified grammar, with all
    # elements being optional:
    #
    #   [protocol:][//][host][:port][/][path]
    #
    # All such strings would be converted to:
    #
    #   http://[host][:port][/][path]
    #
    # It was additionally expected, but not enforced, that such
    # strings would not include a path component.
    #
    # The second and third arguments were taken to be the username and
    # password, and were required. No check was performed to see if
    # they were None.
    #
    # This version of this function now accepts variable arguments but
    # is fully backwards compatible with the prior requirements as
    # described above.
    #
    # When it is detected that there are exactly three arguments in
    # the args list, then the backwards compatibility logic comes into
    # play, resulting in potentially brittle states of affairs.  The
    # bulk of the code below is for maintaining backwards
    # compatibility.
    #
    # The preferred way of calling this method is with four arguments:
    #
    #   host, port, user, password
    #

    restURI = None
    host = None
    port = None
    username = None
    password = None

    argsLength = 0;
    if args is not None:
        argsLength = len(args)

    if argsLength == 1:
        host = args[0]
    elif argsLength == 2:
        host = args[0]
        port = args[1]
    elif argsLength == 3:
        restURI = args[0]
        username = args[1]
        password = args[2]
        
    if kwargs is not None and 'restURI' in kwargs:
        candidateRestURI = kwargs['restURI']
        if candidateRestURI is not None:
            restURI = candidateRestURI

    if argsLength >= 4:
        # Preferred, new-style connectivity
        host = args[0]
        port = args[1]
        username = args[2]
        password = args[3]

    if kwargs is not None:
        if 'host' in kwargs:
            host = kwargs['host']

        if 'port' in kwargs:
            port = kwargs['port']

        if 'user' in kwargs:
            username = kwargs['user']
        elif 'username' in kwargs:
            username = kwargs['username']

        if 'password' in kwargs:
            password = kwargs['password']

    #
    # End of argument parsing and backwards compatibility logic.
    #

    if port is None:
        port = 80 # remember, protocol is http

    global objProvisioningWLSTCLI

    # Disconnect if there was a prior connection in effect.
    if objProvisioningWLSTCLI is not None:
        disconnectFromREST(True)

    printIfInteractive('Connecting to REST...')
    try:
        if restURI is None:
            # Use the non-deprecated constructor.
            objProvisioningWLSTCLI = ProvisioningWLSTCLI(str(host), port, str(username), str(password))
        else:
            # Use the backwards-compatible constructor.
            warnings.warn('Please call this function passing host, port, user and password arguments instead.', DeprecationWarning)
            objProvisioningWLSTCLI = ProvisioningWLSTCLI(restURI, str(username), str(password), WLS_ON)
    except Exception, e:
        if WLS_ON is not None:
            WLS_ON.setDumpStackThrowable(e)
        raise
    printIfInteractive('Ok')

def getConfigurableAttributes(provisioningComponentName=None, properties=None):
    """Acquires and returns configurable attributes from the provisioning system for the supplied provisioning component name.

    Returns a java.util.Properties instance containing the
    configurable attributes.

    provisioningComponentName - the name of the provisioning component
    for which configurable attributes will be acquired

    properties - a java.util.Map (or string representing a file that
    will be read, or Python dict) of provisioning operation properties

    """
    if objProvisioningWLSTCLI is None:
        raise PythonException("Not connected. Please use connectToREST(restURI, username, password) to get connected.")

    properties = _toProperties(properties)
    assert isinstance(properties, Properties)

    returnValue = None
    try:        
        returnValue = objProvisioningWLSTCLI.getConfigurableAttributes(provisioningComponentName, properties)
    except Exception, e:
        if WLS_ON is not None:
            WLS_ON.setDumpStackThrowable(e)
        raise
    return returnValue

def writeConfigurableAttributes(fileName, provisioningComponentName=None, configurableAttributes=None, properties=None, charset=StandardCharsets.UTF_8):
    """Writes configurable attributes for the supplied provisioning component to disk.

    fileName - the absolute path to the file that will contain the
    configurable attributes; may be a string, a
    java.lang.CharSequence, or a java.io.Writer

    provisioningComponentName - the name of the provisioning component
    whose configurable attributes (and those of its dependencies)
    should be written

    configurableAttributes - usually None; a java.util.Map of
    key-value pairs (or a Python dict, or a string representing a file
    that will be read) that will be written to the supplied fileName;
    if None, then the getConfigurableAttributes() method will be
    called and its return value will be used

    properties - a java.util.Map (or a Python dict, or a string
    representing a file that will be read) of provisioning operation
    properties to use if configurable attributes need to be acquired
    from the provisioning system

    charset - the character set to use for file encoding, if
    necessary; UTF-8 by default

    """
    if fileName is None:
        raise ValueError('fileName is None')
    
    if configurableAttributes is None:
        configurableAttributes = getConfigurableAttributes(provisioningComponentName, properties)

    configurableAttributes = _configurableAttributesToProperties(configurableAttributes)
    assert isinstance(configurableAttributes, Properties)
    
    writer = None
    try:
        try:
            writer = _toWriter(fileName, charset)
            assert isinstance(writer, Writer)
            configurableAttributes.store(writer, 'Configurable attribute values for provisioning partitions with provisioning component %s' % provisioningComponentName)
            writer.flush()
        except IOException, e:
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(e)
            raise
    finally:
        if writer is not None:
            try:
                writer.close()
            except IOException, e:
                pass
    return configurableAttributes

def provisionPartition(partitionName, provisioningComponentName, configurableAttributeValues=None, properties=None):
    """Provisions the named partition using a provisioning component named by the supplied provisioning component name.

    partitionName - the name of the partition to provision

    provisioningComponentName - the name of the provisioning component
    that will be used to perform the provisioning operation

    configurableAttributeValues - a dict, java.io.File,
    java.util.Properties, java.lang.String instance or string
    identifying a properties file containing configurable attribute
    values

    properties - a dict, java.io.File, java.util.Properties,
    java.lang.String instance or string identifying a properties file
    containing provisioning operation properties

    """
    deprecatedInvocation = properties is None and isinstance(configurableAttributeValues, str)
    configFilename = None
    
    if deprecatedInvocation:
        configFilename = configurableAttributeValues
        # Make sure the error messages and exceptions look the way
        # they once did.
        e = None
        if partitionName is None or len(str(partitionName)) <= 0:
            e = IllegalArgumentException('partitionName cannot be null. Please provide a valid partitionName')
        if provisioningComponentName is None or len(str(provisioningComponentName)) <= 0:
            e = IllegalArgumentException('provisioningComponentName cannot be null. Please provide a valid provisioningComponentName')
        elif len(configurableAttributeValues) <= 0:
            e = IllegalArgumentException('configFilename cannot be null. Please provide a valid configFilename')
        elif not File(configurableAttributeValues).isFile():
            e = IllegalArgumentException("%s is not a legal file. configFilename should be a valid filename." % configurableAttributeValues)
        if e is not None:
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(e)
            raise e
    
    if provisioningComponentName is None:
        raise ValueError('provisioningComponentName is None')

    partitionName, properties = _mergePartitionName(partitionName, properties)
    assert partitionName is not None
    assert isinstance(properties, Properties)

    try:
        configurableAttributeValues = _configurableAttributesToProperties(configurableAttributeValues)
    except Exception, e:
        if deprecatedInvocation:
            runtimeException = RuntimeException("Error reading properties from file [%s]" % configFilename, e)
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(runtimeException)
            raise runtimeException
        else:
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(e)
            raise
        
    assert isinstance(configurableAttributeValues, Properties)

    if deprecatedInvocation and configurableAttributeValues.stringPropertyNames().isEmpty():
        e = RuntimeException("No properties found in file: %s" % configFilename)
        if WLS_ON is not None:
            WLS_ON.setDumpStackThrowable(e)
        raise e

    if objProvisioningWLSTCLI is None:
        raise PythonException('Not connected. Please use connectToREST(restURI, username, password) to get connected.')

    returnValue = None
    printIfInteractive('Provisioning partition "%s", for component "%s"' % (partitionName, provisioningComponentName))
    try:
        returnValue = objProvisioningWLSTCLI.provisionPartition(partitionName, provisioningComponentName, configurableAttributeValues, properties)
    except ProvisioningException, e:
        if deprecatedInvocation:
            runtimeException = ProvisioningWLSTCLI.toRuntimeException(None, e)
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(runtimeException)
            raise runtimeException
        else:
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(e)
            raise
    except Exception, e:
        if WLS_ON is not None:
            WLS_ON.setDumpStackThrowable(e)
        raise
    printIfInteractive('Done')
    return returnValue

def deprovisionPartition(partitionName, provisioningComponentName, configurableAttributeValues=None, properties=None):
    """Deprovisions the named partition.

    partitionName - the name of the partition

    provisioningComponentName - the name of the provisioning component
    doing the deprovisioning

    configurableAttributeValues - a dict, java.io.File,
    java.util.Properties, java.lang.String instance or string
    identifying a properties file containing configurable attribute
    values

    properties - a dict, java.io.File, java.util.Properties instance
    or string identifying a properties file containing provisioning
    operation properties

    """
    deprecatedInvocation = properties is None and isinstance(configurableAttributeValues, str)
    configFilename = None
    
    if deprecatedInvocation:
        configFilename = configurableAttributeValues
        # Make sure the error messages and exceptions look the way
        # they once did.
        e = None
        if partitionName is None or len(str(partitionName)) <= 0:
            e = IllegalArgumentException('partitionName cannot be null. Please provide a valid partitionName')
        if provisioningComponentName is None or len(str(provisioningComponentName)) <= 0:
            e = IllegalArgumentException('provisioningComponentName cannot be null. Please provide a valid provisioningComponentName')
        elif len(configurableAttributeValues) <= 0:
            e = IllegalArgumentException('configFilename cannot be null. Please provide a valid configFilename')
        elif not File(configurableAttributeValues).isFile():
            e = IllegalArgumentException("%s is not a legal file. configFilename should be a valid filename." % configurableAttributeValues)
        if e:
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(e)
            raise e
    
    if provisioningComponentName is None:
        raise ValueError('provisioningComponentName is None')

    partitionName, properties = _mergePartitionName(partitionName, properties)
    assert partitionName is not None
    assert isinstance(properties, Properties)

    try:
        configurableAttributeValues = _configurableAttributesToProperties(configurableAttributeValues)
    except Exception, e:
        if deprecatedInvocation:
            runtimeException = RuntimeException("Error reading properties from file [%s]" % configFilename, e)
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(runtimeException)
            raise runtimeException
        else:
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(e)
            raise

    assert isinstance(configurableAttributeValues, Properties)

    if deprecatedInvocation and configurableAttributeValues.stringPropertyNames().isEmpty():
        e = RuntimeException("No properties found in file: %s" % configFilename)
        if WLS_ON is not None:
            WLS_ON.setDumpStackThrowable(e)
        raise e
    
    if objProvisioningWLSTCLI is None:
        raise PythonException("Not connected. Please use connectToREST(restURI, username, password) to get connected.")

    msgFormatter = None
    if WLS_ON is not None:
        msgFormatter = WLS_ON.getWLSTMsgFormatter()

    if msgFormatter is not None:
        printIfInteractive(msgFormatter.getDeprovisionPartitionStartMsg(partitionName, provisioningComponentName))
    returnValue = None
    try:
        returnValue = objProvisioningWLSTCLI.deprovisionPartition(partitionName, provisioningComponentName, configurableAttributeValues, properties)
    except ProvisioningException, e:
        if deprecatedInvocation:
            runtimeException = ProvisioningWLSTCLI.toRuntimeException(None, e)
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(runtimeException)
            raise runtimeException
        else:
            if WLS_ON is not None:
                WLS_ON.setDumpStackThrowable(e)
            raise
    except Exception, e:
        if msgFormatter is not None:
            printIfInteractive(msgFormatter.getDeprovisionPartitionErrorMsg(partitionName, provisioningComponentName, e.getMessage()))
        if WLS_ON is not None:
            WLS_ON.setDumpStackThrowable(e)
        raise
    if msgFormatter is not None:
        printIfInteractive(msgFormatter.getDeprovisionPartitionCompletedMsg(partitionName, provisioningComponentName))
    return returnValue

def printIfInteractive(message):
    """Prints the supplied message if the interactive interpreter is currently active.

    Returns 1 if print was invoked, 0 otherwise.

    This function does nothing if the interactive interpreter is not
    currently active.

    See the _isInteractive() function documentation for more details.

    """
    if _isInteractive():
        print(message)
        return 1
    else:
        return 0


#
# Deprecated functions.
#


def getPartitionProvisioningConfigAttrs(provisioningComponentName, configFilename, properties=None):
    """Deprecated; calls the writeConfigurableAttributes() function.

    provisioningComponentName - the name of the provisioning component
    that will (later) be used to perform a provisioning operation

    configFilename - the name of the file to which to write the
    configurable attribute values

    properties - a java.util.Properties instance containing
    provisioning operation properties

    """
    warnings.warn('Please use the %s function instead.' % writeConfigurableAttributes, DeprecationWarning)

    # Make sure the errors coming out of this method are backwards-compatible.
    e = None
    if provisioningComponentName is None or len(str(provisioningComponentName)) <= 0:
        e = IllegalArgumentException('provisioningComponentName cannot be null. Please provide a valid provisioningComponentName')
    elif configFilename is None or len(str(configFilename)) <= 0:
        e = IllegalArgumentException('configFilename cannot be null. Please provide a valid configFilename')
    if e:
        if WLS_ON is not None:
            WLS_ON.setDumpStackThrowable(e)
        raise e
    configurableAttributeValues = getConfigurableAttributes(provisioningComponentName, properties)
    assert isinstance(configurableAttributeValues, Properties)
    found = False
    iterator = configurableAttributeValues.stringPropertyNames().iterator()
    while not found and iterator.hasNext():
        propertyName = iterator.next()
        assert isinstance(propertyName, str)
        if propertyName.startswith(provisioningComponentName.replace('.', '\.') + '.'):
            found = True
    if not found:
        e = IllegalArgumentException("Error = No such provisioning component: %s" % provisioningComponentName)
        if WLS_ON is not None:
            WLS_ON.setDumpStackThrowable(e)
        raise e
    return writeConfigurableAttributes(configFilename, provisioningComponentName, configurableAttributeValues, properties)


#
# Private functions.
#


def _toWriter(writer, charset=StandardCharsets.UTF_8):
    """
    Converts its writer argument to a java.io.Writer and returns it.

    If the supplied writer is None, a ValueError is raised.

    If the supplied writer is already a java.io.Writer object, then it
    is returned.

    If the supplied writer is a str, a java.io.File, or a
    java.lang.CharSequence, then a new java.io.OutputStreamWriter
    wrapping a java.io.FileOutputStream is returned and the supplied
    charset is used to specify the character encoding (UTF-8 by
    default).

    """
    if writer is None:
        raise ValueError('writer is None')

    if isinstance(writer, Writer):
        return writer

    if charset is None:
        charset = StandardCharsets.UTF_8
    elif isinstance(charset, (CharSequence, str)):
        charset = Charset.forName(str(charset))

    if isinstance(writer, (File, str, CharSequence)):
        return OutputStreamWriter(FileOutputStream(str(writer)), charset)

    raise ValueError('Could not convert %s to java.io.Writer' % writer)

def _toProperties(argument=None):
    """Converts its argument to a java.util.Properties object and returns it.

    If the supplied argument is None, a new Properties object is
    returned.

    If the supplied argument is already a Properties object, then it
    is returned.

    If the supplied argument is a dict, then it is converted to a
    Properties object and the Properties object is returned.

    If the supplied argument is a string or a java.io.File object,
    then it is read with a FileReader and an attempt is made to create
    a new Properties object with the file's contents as its values.

    """
    returnValue = None
    if argument is None:
        returnValue = Properties()
    elif isinstance(argument, Properties):
        returnValue = argument
    elif isinstance(argument, dict):
        returnValue = Properties()
        for key, value in argument.items():
            returnValue.setProperty(str(key), str(value))
    elif isinstance(argument, Map):
        returnValue = Properties()
        entries = argument.entrySet()
        if entries is not None and not entries.isEmpty():
            iterator = entries.iterator()
            if iterator is not None:
                while iterator.hasNext():
                    entry = iterator.next()
                    if entry is not None:
                        key = entry.getKey()
                        if key is not None:
                            value = entry.getValue()
                            if value is None:
                                returnValue.setProperty(str(key), None)
                            else:
                                returnValue.setProperty(str(key), str(value))
    elif isinstance(argument, (CharSequence, File, Reader, str)):
        close = True
        reader = None
        returnValue = Properties()
        try:
            try:
                # Blindly try to treat it as either a Reader, a File,
                # a (Java) String or a string.
                if isinstance(argument, BufferedReader):
                    close = False
                    reader = argument
                elif isinstance(argument, Reader):
                    close = False
                    reader = BufferedReader(argument)
                else:
                    reader = BufferedReader(FileReader(str(argument)))
                returnValue.load(reader)                
            except Exception, e:
                provisioningException = ProvisioningException(e.getMessage(), e)
                if WLS_ON is not None:
                    WLS_ON.setDumpStackThrowable(provisioningException)
                raise provisioningException
        finally:
            if close and reader is not None:
                try:
                    reader.close()
                except IOException, e:
                    pass
                reader = None
    else:
        raise ValueError('Could not convert %s to java.util.Properties' % argument)
                                              
    assert isinstance(returnValue, Properties)
    return returnValue

def _configurableAttributesToProperties(configurableAttributeValues=None):
    """Returns a java.util.Properties object containing configurable attribute values.

    The supplied configurableAttributeValues object is not modified.

    configurableAttributeValues - a value suitable for the first
    argument of the _toProperties() function

    """
    allowEmptyConfigurableAttributeValues = configurableAttributeValues is None or (isinstance(configurableAttributeValues, Properties) and configurableAttributeValues.stringPropertyNames().isEmpty())
    configurableAttributeValues = _toProperties(configurableAttributeValues)
    assert isinstance(configurableAttributeValues, Properties)
    if not allowEmptyConfigurableAttributeValues and configurableAttributeValues.stringPropertyNames().isEmpty():
        warnings.warn('Using no explicit configurable attribute values for this provisioning operation.')
    return configurableAttributeValues

def _mergePartitionName(partitionName, properties=None):
    """Returns a tuple of a partition name and a java.util.Properties object with the 'wlsPartitionName' property set appropriately.

    The supplied properties object is not modified.

    partitionName - the user-specified partitionName

    properties - a value suitable for the first argument of the
    _toProperties() function

    """
    properties = _toProperties(properties)
    assert isinstance(properties, Properties)

    if partitionName is None and properties.getProperty('wlsPartitionName') is None:
        raise ValueError('partitionName is None')

    properties = ProvisioningWLSTCLI.merge(partitionName, properties)
    return partitionName, properties
                                              
def _isInteractive():
    """Returns true if the interactive interpreter is currently active.

    The interpreter is deemed to be interactive if the primary and
    secondary system prompts are not None (are defined).

    See
    https://docs.python.org/release/2.2.1/lib/module-sys.html#l2h-262
    for details.

    """
    return sys.ps1 is not None and sys.ps2 is not None


#
# Exit handlers.
#


# Call disconnectFromREST if we exit normally to free up resources.
import atexit
atexit.register(disconnectFromREST)


#
# java.util.logging.Handler implementation.
#

class WLSTUtilsPrintDebugHandler(Handler):
    """A Handler that uses the WLSTUtils#printDebug(String) method.

    This class exactly emulates the way that debug messages have
    historically been printed by classes in the
    com.oracle.weblogic.lifecycle.provisioning.wlst package.

    """

    def publish(self, logRecord):
        """Publishes the supplied LogRecord by printing it with WLSTUtils#printDebug(String).
        
        logRecord - the java.util.logging.LogRecord to print
        """
        if WLS_ON is not None and self.isLoggable(logRecord):                       
            try:
                simpleClassName = logRecord.getSourceClassName()
                if simpleClassName is None:
                    simpleClassName = logRecord.getLoggerName()
                    if simpleClassName is None:
                        simpleClassName = 'null'
                else:
                    i = simpleClassName.rfind('.')
                    if i >= 0:
                        simpleClassName = simpleClassName[i + 1:]

                message = logRecord.getMessage();
                if message is not None:
                    rb = logRecord.getResourceBundle();
                    if rb is not None:
                        try:
                            message = rb.getString(message)
                        except MissingResourceException, e:
                            pass
                    if message is not None:
                        try:
                            parameters = logRecord.getParameters()
                            if parameters is not None and len(parameters) and ("{0" in message or "{1" in message or "{2" in message or "{3" in message):
                                message = MessageFormat.format(message, parameters)
                        except Exception, e:
                            pass

                methodNameAndMessage = logRecord.getSourceMethodName()
                if methodNameAndMessage is None:
                    methodNameAndMessage = message
                else:
                    methodNameAndMessage = '[%s]::%s' % (methodNameAndMessage, message)
                    
                WLS_ON.printDebug("[DEBUG]::[%s]::%s" % (simpleClassName, methodNameAndMessage))
            except Exception, e:
                self.reportError(e.getMessage(), e, 0)
                
    def isLoggable(self, logRecord):
        return WLS_ON is not None and WLS_ON.isDebug() and Handler.isLoggable(self, logRecord)

    def flush():
        pass

class ProvisioningWLSTLogger(Logger):
    """A Logger that translates java.util.logging statements into WLSTUtils.printDebug() calls.

    """
    def __init__(self):
        Logger.__init__(self, 'com.oracle.weblogic.lifecycle.provisioning.wlst', None)
        self.setLevel(Level.ALL)
        self.setUseParentHandlers(False)
        self.addHandler(WLSTUtilsPrintDebugHandler())

    def isLoggable(self, ignoredLevel):
        return WLS_ON is not None and WLS_ON.isDebug()

# It would be nice if we didn't have to keep a global reference to
# this Logger here, but if we don't have a strong reference, the
# underlying Logger in the LogManager "may be garbage collected at any
# time".  See
# http://docs.oracle.com/javase/8/docs/api/java/util/logging/LogManager.html#getLogger-java.lang.String-
# for details.
provisioningWLSTLogger = ProvisioningWLSTLogger()

# By calling LogManager#addLogger(Logger) here, and by maintaining a
# (global) strong reference to the Logger we're adding (see above), we
# atomically ensure either (a) that our Logger will be the one
# returned by Logger#getLogger(String), or that any previously
# registered Logger will be returned instead.
LogManager.getLogManager().addLogger(provisioningWLSTLogger)
