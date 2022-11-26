import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PySet;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("subprocess.py")
public class subprocess$py extends PyFunctionTable implements PyRunnable {
   static subprocess$py self;
   static final PyCode f$0;
   static final PyCode CalledProcessError$1;
   static final PyCode __init__$2;
   static final PyCode __str__$3;
   static final PyCode STARTUPINFO$4;
   static final PyCode pywintypes$5;
   static final PyCode _cleanup$6;
   static final PyCode _eintr_retry_call$7;
   static final PyCode call$8;
   static final PyCode check_call$9;
   static final PyCode check_output$10;
   static final PyCode list2cmdline$11;
   static final PyCode _cmdline2list$12;
   static final PyCode _setup_platform$13;
   static final PyCode f$14;
   static final PyCode f$15;
   static final PyCode f$16;
   static final PyCode _CouplerThread$17;
   static final PyCode __init__$18;
   static final PyCode run$19;
   static final PyCode _setup_env$20;
   static final PyCode f$21;
   static final PyCode Popen$22;
   static final PyCode __init__$23;
   static final PyCode _translate_newlines$24;
   static final PyCode __del__$25;
   static final PyCode communicate$26;
   static final PyCode poll$27;
   static final PyCode _readerthread$28;
   static final PyCode _communicate$29;
   static final PyCode _get_handles$30;
   static final PyCode _make_inheritable$31;
   static final PyCode _find_w9xpopen$32;
   static final PyCode _execute_child$33;
   static final PyCode _internal_poll$34;
   static final PyCode wait$35;
   static final PyCode _get_handles$36;
   static final PyCode _stderr_is_stdout$37;
   static final PyCode _coupler_thread$38;
   static final PyCode _execute_child$39;
   static final PyCode f$40;
   static final PyCode _get_private_field$41;
   static final PyCode _get_pid$42;
   static final PyCode _get_pid$43;
   static final PyCode poll$44;
   static final PyCode _internal_poll$45;
   static final PyCode wait$46;
   static final PyCode terminate$47;
   static final PyCode kill$48;
   static final PyCode send_signal$49;
   static final PyCode send_signal$50;
   static final PyCode _get_handles$51;
   static final PyCode _set_cloexec_flag$52;
   static final PyCode _close_fds$53;
   static final PyCode _execute_child$54;
   static final PyCode _dup2$55;
   static final PyCode _handle_exitstatus$56;
   static final PyCode _internal_poll$57;
   static final PyCode wait$58;
   static final PyCode _communicate$59;
   static final PyCode _communicate_with_poll$60;
   static final PyCode register_and_append$61;
   static final PyCode close_unregister_and_remove$62;
   static final PyCode _communicate_with_select$63;
   static final PyCode send_signal$64;
   static final PyCode terminate$65;
   static final PyCode kill$66;
   static final PyCode _os_system$67;
   static final PyCode _demo_posix$68;
   static final PyCode f$69;
   static final PyCode _demo_windows$70;
   static final PyCode _demo_jython$71;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("subprocess - Subprocesses with accessible I/O streams\n\nThis module allows you to spawn processes, connect to their\ninput/output/error pipes, and obtain their return codes.  This module\nintends to replace several other, older modules and functions, like:\n\nos.system\nos.spawn*\nos.popen*\npopen2.*\ncommands.*\n\nInformation about how the subprocess module can be used to replace these\nmodules and functions can be found below.\n\n\n\nUsing the subprocess module\n===========================\nThis module defines one class called Popen:\n\nclass Popen(args, bufsize=0, executable=None,\n            stdin=None, stdout=None, stderr=None,\n            preexec_fn=None, close_fds=False, shell=False,\n            cwd=None, env=None, universal_newlines=False,\n            startupinfo=None, creationflags=0):\n\n\nArguments are:\n\nargs should be a string, or a sequence of program arguments.  The\nprogram to execute is normally the first item in the args sequence or\nstring, but can be explicitly set by using the executable argument.\n\nOn UNIX, with shell=False (default): In this case, the Popen class\nuses os.execvp() to execute the child program.  args should normally\nbe a sequence.  A string will be treated as a sequence with the string\nas the only item (the program to execute).\n\nOn UNIX, with shell=True: If args is a string, it specifies the\ncommand string to execute through the shell.  If args is a sequence,\nthe first item specifies the command string, and any additional items\nwill be treated as additional shell arguments.\n\nOn Windows: the Popen class uses CreateProcess() to execute the child\nprogram, which operates on strings.  If args is a sequence, it will be\nconverted to a string using the list2cmdline method.  Please note that\nnot all MS Windows applications interpret the command line the same\nway: The list2cmdline is designed for applications using the same\nrules as the MS C runtime.\n\nbufsize, if given, has the same meaning as the corresponding argument\nto the built-in open() function: 0 means unbuffered, 1 means line\nbuffered, any other positive value means use a buffer of\n(approximately) that size.  A negative bufsize means to use the system\ndefault, which usually means fully buffered.  The default value for\nbufsize is 0 (unbuffered).\n\nstdin, stdout and stderr specify the executed programs' standard\ninput, standard output and standard error file handles, respectively.\nValid values are PIPE, an existing file descriptor (a positive\ninteger), an existing file object, and None.  PIPE indicates that a\nnew pipe to the child should be created.  With None, no redirection\nwill occur; the child's file handles will be inherited from the\nparent.  Additionally, stderr can be STDOUT, which indicates that the\nstderr data from the applications should be captured into the same\nfile handle as for stdout.\n\nIf preexec_fn is set to a callable object, this object will be called\nin the child process just before the child is executed.\n\nIf close_fds is true, all file descriptors except 0, 1 and 2 will be\nclosed before the child process is executed.\n\nif shell is true, the specified command will be executed through the\nshell.\n\nIf cwd is not None, the current directory will be changed to cwd\nbefore the child is executed.\n\nIf env is not None, it defines the environment variables for the new\nprocess.\n\nIf universal_newlines is true, the file objects stdout and stderr are\nopened as a text files, but lines may be terminated by any of '\\n',\nthe Unix end-of-line convention, '\\r', the Macintosh convention or\n'\\r\\n', the Windows convention.  All of these external representations\nare seen as '\\n' by the Python program.  Note: This feature is only\navailable if Python is built with universal newline support (the\ndefault).  Also, the newlines attribute of the file objects stdout,\nstdin and stderr are not updated by the communicate() method.\n\nThe startupinfo and creationflags, if given, will be passed to the\nunderlying CreateProcess() function.  They can specify things such as\nappearance of the main window and priority for the new process.\n(Windows only)\n\n\nThis module also defines some shortcut functions:\n\ncall(*popenargs, **kwargs):\n    Run command with arguments.  Wait for command to complete, then\n    return the returncode attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    retcode = call([\"ls\", \"-l\"])\n\ncheck_call(*popenargs, **kwargs):\n    Run command with arguments.  Wait for command to complete.  If the\n    exit code was zero then return, otherwise raise\n    CalledProcessError.  The CalledProcessError object will have the\n    return code in the returncode attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    check_call([\"ls\", \"-l\"])\n\ncheck_output(*popenargs, **kwargs):\n    Run command with arguments and return its output as a byte string.\n\n    If the exit code was non-zero it raises a CalledProcessError.  The\n    CalledProcessError object will have the return code in the returncode\n    attribute and output in the output attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    output = check_output([\"ls\", \"-l\", \"/dev/null\"])\n\n\nExceptions\n----------\nExceptions raised in the child process, before the new program has\nstarted to execute, will be re-raised in the parent.  Additionally,\nthe exception object will have one extra attribute called\n'child_traceback', which is a string containing traceback information\nfrom the childs point of view.\n\nThe most common exception raised is OSError.  This occurs, for\nexample, when trying to execute a non-existent file.  Applications\nshould prepare for OSErrors.\n\nA ValueError will be raised if Popen is called with invalid arguments.\n\ncheck_call() and check_output() will raise CalledProcessError, if the\ncalled process returns a non-zero return code.\n\n\nSecurity\n--------\nUnlike some other popen functions, this implementation will never call\n/bin/sh implicitly.  This means that all characters, including shell\nmetacharacters, can safely be passed to child processes.\n\n\nPopen objects\n=============\nInstances of the Popen class have the following methods:\n\npoll()\n    Check if child process has terminated.  Returns returncode\n    attribute.\n\nwait()\n    Wait for child process to terminate.  Returns returncode attribute.\n\ncommunicate(input=None)\n    Interact with process: Send data to stdin.  Read data from stdout\n    and stderr, until end-of-file is reached.  Wait for process to\n    terminate.  The optional input argument should be a string to be\n    sent to the child process, or None, if no data should be sent to\n    the child.\n\n    communicate() returns a tuple (stdout, stderr).\n\n    Note: The data read is buffered in memory, so do not use this\n    method if the data size is large or unlimited.\n\nThe following attributes are also available:\n\nstdin\n    If the stdin argument is PIPE, this attribute is a file object\n    that provides input to the child process.  Otherwise, it is None.\n\nstdout\n    If the stdout argument is PIPE, this attribute is a file object\n    that provides output from the child process.  Otherwise, it is\n    None.\n\nstderr\n    If the stderr argument is PIPE, this attribute is file object that\n    provides error output from the child process.  Otherwise, it is\n    None.\n\npid\n    The process ID of the child process.\n\nreturncode\n    The child return code.  A None value indicates that the process\n    hasn't terminated yet.  A negative value -N indicates that the\n    child was terminated by signal N (UNIX only).\n\n\nReplacing older functions with the subprocess module\n====================================================\nIn this section, \"a ==> b\" means that b can be used as a replacement\nfor a.\n\nNote: All functions in this section fail (more or less) silently if\nthe executed program cannot be found; this module raises an OSError\nexception.\n\nIn the following examples, we assume that the subprocess module is\nimported with \"from subprocess import *\".\n\n\nReplacing /bin/sh shell backquote\n---------------------------------\noutput=`mycmd myarg`\n==>\noutput = Popen([\"mycmd\", \"myarg\"], stdout=PIPE).communicate()[0]\n\n\nReplacing shell pipe line\n-------------------------\noutput=`dmesg | grep hda`\n==>\np1 = Popen([\"dmesg\"], stdout=PIPE)\np2 = Popen([\"grep\", \"hda\"], stdin=p1.stdout, stdout=PIPE)\noutput = p2.communicate()[0]\n\n\nReplacing os.system()\n---------------------\nsts = os.system(\"mycmd\" + \" myarg\")\n==>\np = Popen(\"mycmd\" + \" myarg\", shell=True)\npid, sts = os.waitpid(p.pid, 0)\n\nNote:\n\n* Calling the program through the shell is usually not required.\n\n* It's easier to look at the returncode attribute than the\n  exitstatus.\n\nA more real-world example would look like this:\n\ntry:\n    retcode = call(\"mycmd\" + \" myarg\", shell=True)\n    if retcode < 0:\n        print >>sys.stderr, \"Child was terminated by signal\", -retcode\n    else:\n        print >>sys.stderr, \"Child returned\", retcode\nexcept OSError, e:\n    print >>sys.stderr, \"Execution failed:\", e\n\n\nReplacing os.spawn*\n-------------------\nP_NOWAIT example:\n\npid = os.spawnlp(os.P_NOWAIT, \"/bin/mycmd\", \"mycmd\", \"myarg\")\n==>\npid = Popen([\"/bin/mycmd\", \"myarg\"]).pid\n\n\nP_WAIT example:\n\nretcode = os.spawnlp(os.P_WAIT, \"/bin/mycmd\", \"mycmd\", \"myarg\")\n==>\nretcode = call([\"/bin/mycmd\", \"myarg\"])\n\n\nVector example:\n\nos.spawnvp(os.P_NOWAIT, path, args)\n==>\nPopen([path] + args[1:])\n\n\nEnvironment example:\n\nos.spawnlpe(os.P_NOWAIT, \"/bin/mycmd\", \"mycmd\", \"myarg\", env)\n==>\nPopen([\"/bin/mycmd\", \"myarg\"], env={\"PATH\": \"/usr/bin\"})\n\n\nReplacing os.popen*\n-------------------\npipe = os.popen(\"cmd\", mode='r', bufsize)\n==>\npipe = Popen(\"cmd\", shell=True, bufsize=bufsize, stdout=PIPE).stdout\n\npipe = os.popen(\"cmd\", mode='w', bufsize)\n==>\npipe = Popen(\"cmd\", shell=True, bufsize=bufsize, stdin=PIPE).stdin\n\n\n(child_stdin, child_stdout) = os.popen2(\"cmd\", mode, bufsize)\n==>\np = Popen(\"cmd\", shell=True, bufsize=bufsize,\n          stdin=PIPE, stdout=PIPE, close_fds=True)\n(child_stdin, child_stdout) = (p.stdin, p.stdout)\n\n\n(child_stdin,\n child_stdout,\n child_stderr) = os.popen3(\"cmd\", mode, bufsize)\n==>\np = Popen(\"cmd\", shell=True, bufsize=bufsize,\n          stdin=PIPE, stdout=PIPE, stderr=PIPE, close_fds=True)\n(child_stdin,\n child_stdout,\n child_stderr) = (p.stdin, p.stdout, p.stderr)\n\n\n(child_stdin, child_stdout_and_stderr) = os.popen4(\"cmd\", mode,\n                                                   bufsize)\n==>\np = Popen(\"cmd\", shell=True, bufsize=bufsize,\n          stdin=PIPE, stdout=PIPE, stderr=STDOUT, close_fds=True)\n(child_stdin, child_stdout_and_stderr) = (p.stdin, p.stdout)\n\nOn Unix, os.popen2, os.popen3 and os.popen4 also accept a sequence as\nthe command to execute, in which case arguments will be passed\ndirectly to the program without shell intervention.  This usage can be\nreplaced as follows:\n\n(child_stdin, child_stdout) = os.popen2([\"/bin/ls\", \"-l\"], mode,\n                                        bufsize)\n==>\np = Popen([\"/bin/ls\", \"-l\"], bufsize=bufsize, stdin=PIPE, stdout=PIPE)\n(child_stdin, child_stdout) = (p.stdin, p.stdout)\n\nReturn code handling translates as follows:\n\npipe = os.popen(\"cmd\", 'w')\n...\nrc = pipe.close()\nif rc is not None and rc % 256:\n    print \"There were some errors\"\n==>\nprocess = Popen(\"cmd\", 'w', shell=True, stdin=PIPE)\n...\nprocess.stdin.close()\nif process.wait() != 0:\n    print \"There were some errors\"\n\n\nReplacing popen2.*\n------------------\n(child_stdout, child_stdin) = popen2.popen2(\"somestring\", bufsize, mode)\n==>\np = Popen([\"somestring\"], shell=True, bufsize=bufsize\n          stdin=PIPE, stdout=PIPE, close_fds=True)\n(child_stdout, child_stdin) = (p.stdout, p.stdin)\n\nOn Unix, popen2 also accepts a sequence as the command to execute, in\nwhich case arguments will be passed directly to the program without\nshell intervention.  This usage can be replaced as follows:\n\n(child_stdout, child_stdin) = popen2.popen2([\"mycmd\", \"myarg\"], bufsize,\n                                            mode)\n==>\np = Popen([\"mycmd\", \"myarg\"], bufsize=bufsize,\n          stdin=PIPE, stdout=PIPE, close_fds=True)\n(child_stdout, child_stdin) = (p.stdout, p.stdin)\n\nThe popen2.Popen3 and popen2.Popen4 basically works as subprocess.Popen,\nexcept that:\n\n* subprocess.Popen raises an exception if the execution fails\n* the capturestderr argument is replaced with the stderr argument.\n* stdin=PIPE and stdout=PIPE must be specified.\n* popen2 closes all filedescriptors by default, but you have to specify\n  close_fds=True with subprocess.Popen.\n"));
      var1.setline(389);
      PyString.fromInterned("subprocess - Subprocesses with accessible I/O streams\n\nThis module allows you to spawn processes, connect to their\ninput/output/error pipes, and obtain their return codes.  This module\nintends to replace several other, older modules and functions, like:\n\nos.system\nos.spawn*\nos.popen*\npopen2.*\ncommands.*\n\nInformation about how the subprocess module can be used to replace these\nmodules and functions can be found below.\n\n\n\nUsing the subprocess module\n===========================\nThis module defines one class called Popen:\n\nclass Popen(args, bufsize=0, executable=None,\n            stdin=None, stdout=None, stderr=None,\n            preexec_fn=None, close_fds=False, shell=False,\n            cwd=None, env=None, universal_newlines=False,\n            startupinfo=None, creationflags=0):\n\n\nArguments are:\n\nargs should be a string, or a sequence of program arguments.  The\nprogram to execute is normally the first item in the args sequence or\nstring, but can be explicitly set by using the executable argument.\n\nOn UNIX, with shell=False (default): In this case, the Popen class\nuses os.execvp() to execute the child program.  args should normally\nbe a sequence.  A string will be treated as a sequence with the string\nas the only item (the program to execute).\n\nOn UNIX, with shell=True: If args is a string, it specifies the\ncommand string to execute through the shell.  If args is a sequence,\nthe first item specifies the command string, and any additional items\nwill be treated as additional shell arguments.\n\nOn Windows: the Popen class uses CreateProcess() to execute the child\nprogram, which operates on strings.  If args is a sequence, it will be\nconverted to a string using the list2cmdline method.  Please note that\nnot all MS Windows applications interpret the command line the same\nway: The list2cmdline is designed for applications using the same\nrules as the MS C runtime.\n\nbufsize, if given, has the same meaning as the corresponding argument\nto the built-in open() function: 0 means unbuffered, 1 means line\nbuffered, any other positive value means use a buffer of\n(approximately) that size.  A negative bufsize means to use the system\ndefault, which usually means fully buffered.  The default value for\nbufsize is 0 (unbuffered).\n\nstdin, stdout and stderr specify the executed programs' standard\ninput, standard output and standard error file handles, respectively.\nValid values are PIPE, an existing file descriptor (a positive\ninteger), an existing file object, and None.  PIPE indicates that a\nnew pipe to the child should be created.  With None, no redirection\nwill occur; the child's file handles will be inherited from the\nparent.  Additionally, stderr can be STDOUT, which indicates that the\nstderr data from the applications should be captured into the same\nfile handle as for stdout.\n\nIf preexec_fn is set to a callable object, this object will be called\nin the child process just before the child is executed.\n\nIf close_fds is true, all file descriptors except 0, 1 and 2 will be\nclosed before the child process is executed.\n\nif shell is true, the specified command will be executed through the\nshell.\n\nIf cwd is not None, the current directory will be changed to cwd\nbefore the child is executed.\n\nIf env is not None, it defines the environment variables for the new\nprocess.\n\nIf universal_newlines is true, the file objects stdout and stderr are\nopened as a text files, but lines may be terminated by any of '\\n',\nthe Unix end-of-line convention, '\\r', the Macintosh convention or\n'\\r\\n', the Windows convention.  All of these external representations\nare seen as '\\n' by the Python program.  Note: This feature is only\navailable if Python is built with universal newline support (the\ndefault).  Also, the newlines attribute of the file objects stdout,\nstdin and stderr are not updated by the communicate() method.\n\nThe startupinfo and creationflags, if given, will be passed to the\nunderlying CreateProcess() function.  They can specify things such as\nappearance of the main window and priority for the new process.\n(Windows only)\n\n\nThis module also defines some shortcut functions:\n\ncall(*popenargs, **kwargs):\n    Run command with arguments.  Wait for command to complete, then\n    return the returncode attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    retcode = call([\"ls\", \"-l\"])\n\ncheck_call(*popenargs, **kwargs):\n    Run command with arguments.  Wait for command to complete.  If the\n    exit code was zero then return, otherwise raise\n    CalledProcessError.  The CalledProcessError object will have the\n    return code in the returncode attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    check_call([\"ls\", \"-l\"])\n\ncheck_output(*popenargs, **kwargs):\n    Run command with arguments and return its output as a byte string.\n\n    If the exit code was non-zero it raises a CalledProcessError.  The\n    CalledProcessError object will have the return code in the returncode\n    attribute and output in the output attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    output = check_output([\"ls\", \"-l\", \"/dev/null\"])\n\n\nExceptions\n----------\nExceptions raised in the child process, before the new program has\nstarted to execute, will be re-raised in the parent.  Additionally,\nthe exception object will have one extra attribute called\n'child_traceback', which is a string containing traceback information\nfrom the childs point of view.\n\nThe most common exception raised is OSError.  This occurs, for\nexample, when trying to execute a non-existent file.  Applications\nshould prepare for OSErrors.\n\nA ValueError will be raised if Popen is called with invalid arguments.\n\ncheck_call() and check_output() will raise CalledProcessError, if the\ncalled process returns a non-zero return code.\n\n\nSecurity\n--------\nUnlike some other popen functions, this implementation will never call\n/bin/sh implicitly.  This means that all characters, including shell\nmetacharacters, can safely be passed to child processes.\n\n\nPopen objects\n=============\nInstances of the Popen class have the following methods:\n\npoll()\n    Check if child process has terminated.  Returns returncode\n    attribute.\n\nwait()\n    Wait for child process to terminate.  Returns returncode attribute.\n\ncommunicate(input=None)\n    Interact with process: Send data to stdin.  Read data from stdout\n    and stderr, until end-of-file is reached.  Wait for process to\n    terminate.  The optional input argument should be a string to be\n    sent to the child process, or None, if no data should be sent to\n    the child.\n\n    communicate() returns a tuple (stdout, stderr).\n\n    Note: The data read is buffered in memory, so do not use this\n    method if the data size is large or unlimited.\n\nThe following attributes are also available:\n\nstdin\n    If the stdin argument is PIPE, this attribute is a file object\n    that provides input to the child process.  Otherwise, it is None.\n\nstdout\n    If the stdout argument is PIPE, this attribute is a file object\n    that provides output from the child process.  Otherwise, it is\n    None.\n\nstderr\n    If the stderr argument is PIPE, this attribute is file object that\n    provides error output from the child process.  Otherwise, it is\n    None.\n\npid\n    The process ID of the child process.\n\nreturncode\n    The child return code.  A None value indicates that the process\n    hasn't terminated yet.  A negative value -N indicates that the\n    child was terminated by signal N (UNIX only).\n\n\nReplacing older functions with the subprocess module\n====================================================\nIn this section, \"a ==> b\" means that b can be used as a replacement\nfor a.\n\nNote: All functions in this section fail (more or less) silently if\nthe executed program cannot be found; this module raises an OSError\nexception.\n\nIn the following examples, we assume that the subprocess module is\nimported with \"from subprocess import *\".\n\n\nReplacing /bin/sh shell backquote\n---------------------------------\noutput=`mycmd myarg`\n==>\noutput = Popen([\"mycmd\", \"myarg\"], stdout=PIPE).communicate()[0]\n\n\nReplacing shell pipe line\n-------------------------\noutput=`dmesg | grep hda`\n==>\np1 = Popen([\"dmesg\"], stdout=PIPE)\np2 = Popen([\"grep\", \"hda\"], stdin=p1.stdout, stdout=PIPE)\noutput = p2.communicate()[0]\n\n\nReplacing os.system()\n---------------------\nsts = os.system(\"mycmd\" + \" myarg\")\n==>\np = Popen(\"mycmd\" + \" myarg\", shell=True)\npid, sts = os.waitpid(p.pid, 0)\n\nNote:\n\n* Calling the program through the shell is usually not required.\n\n* It's easier to look at the returncode attribute than the\n  exitstatus.\n\nA more real-world example would look like this:\n\ntry:\n    retcode = call(\"mycmd\" + \" myarg\", shell=True)\n    if retcode < 0:\n        print >>sys.stderr, \"Child was terminated by signal\", -retcode\n    else:\n        print >>sys.stderr, \"Child returned\", retcode\nexcept OSError, e:\n    print >>sys.stderr, \"Execution failed:\", e\n\n\nReplacing os.spawn*\n-------------------\nP_NOWAIT example:\n\npid = os.spawnlp(os.P_NOWAIT, \"/bin/mycmd\", \"mycmd\", \"myarg\")\n==>\npid = Popen([\"/bin/mycmd\", \"myarg\"]).pid\n\n\nP_WAIT example:\n\nretcode = os.spawnlp(os.P_WAIT, \"/bin/mycmd\", \"mycmd\", \"myarg\")\n==>\nretcode = call([\"/bin/mycmd\", \"myarg\"])\n\n\nVector example:\n\nos.spawnvp(os.P_NOWAIT, path, args)\n==>\nPopen([path] + args[1:])\n\n\nEnvironment example:\n\nos.spawnlpe(os.P_NOWAIT, \"/bin/mycmd\", \"mycmd\", \"myarg\", env)\n==>\nPopen([\"/bin/mycmd\", \"myarg\"], env={\"PATH\": \"/usr/bin\"})\n\n\nReplacing os.popen*\n-------------------\npipe = os.popen(\"cmd\", mode='r', bufsize)\n==>\npipe = Popen(\"cmd\", shell=True, bufsize=bufsize, stdout=PIPE).stdout\n\npipe = os.popen(\"cmd\", mode='w', bufsize)\n==>\npipe = Popen(\"cmd\", shell=True, bufsize=bufsize, stdin=PIPE).stdin\n\n\n(child_stdin, child_stdout) = os.popen2(\"cmd\", mode, bufsize)\n==>\np = Popen(\"cmd\", shell=True, bufsize=bufsize,\n          stdin=PIPE, stdout=PIPE, close_fds=True)\n(child_stdin, child_stdout) = (p.stdin, p.stdout)\n\n\n(child_stdin,\n child_stdout,\n child_stderr) = os.popen3(\"cmd\", mode, bufsize)\n==>\np = Popen(\"cmd\", shell=True, bufsize=bufsize,\n          stdin=PIPE, stdout=PIPE, stderr=PIPE, close_fds=True)\n(child_stdin,\n child_stdout,\n child_stderr) = (p.stdin, p.stdout, p.stderr)\n\n\n(child_stdin, child_stdout_and_stderr) = os.popen4(\"cmd\", mode,\n                                                   bufsize)\n==>\np = Popen(\"cmd\", shell=True, bufsize=bufsize,\n          stdin=PIPE, stdout=PIPE, stderr=STDOUT, close_fds=True)\n(child_stdin, child_stdout_and_stderr) = (p.stdin, p.stdout)\n\nOn Unix, os.popen2, os.popen3 and os.popen4 also accept a sequence as\nthe command to execute, in which case arguments will be passed\ndirectly to the program without shell intervention.  This usage can be\nreplaced as follows:\n\n(child_stdin, child_stdout) = os.popen2([\"/bin/ls\", \"-l\"], mode,\n                                        bufsize)\n==>\np = Popen([\"/bin/ls\", \"-l\"], bufsize=bufsize, stdin=PIPE, stdout=PIPE)\n(child_stdin, child_stdout) = (p.stdin, p.stdout)\n\nReturn code handling translates as follows:\n\npipe = os.popen(\"cmd\", 'w')\n...\nrc = pipe.close()\nif rc is not None and rc % 256:\n    print \"There were some errors\"\n==>\nprocess = Popen(\"cmd\", 'w', shell=True, stdin=PIPE)\n...\nprocess.stdin.close()\nif process.wait() != 0:\n    print \"There were some errors\"\n\n\nReplacing popen2.*\n------------------\n(child_stdout, child_stdin) = popen2.popen2(\"somestring\", bufsize, mode)\n==>\np = Popen([\"somestring\"], shell=True, bufsize=bufsize\n          stdin=PIPE, stdout=PIPE, close_fds=True)\n(child_stdout, child_stdin) = (p.stdout, p.stdin)\n\nOn Unix, popen2 also accepts a sequence as the command to execute, in\nwhich case arguments will be passed directly to the program without\nshell intervention.  This usage can be replaced as follows:\n\n(child_stdout, child_stdin) = popen2.popen2([\"mycmd\", \"myarg\"], bufsize,\n                                            mode)\n==>\np = Popen([\"mycmd\", \"myarg\"], bufsize=bufsize,\n          stdin=PIPE, stdout=PIPE, close_fds=True)\n(child_stdout, child_stdin) = (p.stdout, p.stdin)\n\nThe popen2.Popen3 and popen2.Popen4 basically works as subprocess.Popen,\nexcept that:\n\n* subprocess.Popen raises an exception if the execution fails\n* the capturestderr argument is replaced with the stderr argument.\n* stdin=PIPE and stdout=PIPE must be specified.\n* popen2 closes all filedescriptors by default, but you have to specify\n  close_fds=True with subprocess.Popen.\n");
      var1.setline(391);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(392);
      var3 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      var3 = var10000;
      var1.setlocal("mswindows", var3);
      var3 = null;
      var1.setline(393);
      var3 = var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java"));
      var1.setlocal("jython", var3);
      var3 = null;
      var1.setline(395);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(396);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(397);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(398);
      var3 = imp.importOne("signal", var1, -1);
      var1.setlocal("signal", var3);
      var3 = null;
      var1.setline(401);
      PyObject[] var7 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("CalledProcessError", var7, CalledProcessError$1);
      var1.setlocal("CalledProcessError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(415);
      String[] var8;
      if (var1.getname("mswindows").__nonzero__()) {
         var1.setline(416);
         var3 = imp.importOne("threading", var1, -1);
         var1.setlocal("threading", var3);
         var3 = null;
         var1.setline(417);
         var3 = imp.importOne("msvcrt", var1, -1);
         var1.setlocal("msvcrt", var3);
         var3 = null;
         var1.setline(418);
         var3 = imp.importOne("_subprocess", var1, -1);
         var1.setlocal("_subprocess", var3);
         var3 = null;
         var1.setline(419);
         var7 = Py.EmptyObjects;
         var4 = Py.makeClass("STARTUPINFO", var7, STARTUPINFO$4);
         var1.setlocal("STARTUPINFO", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
         var1.setline(425);
         var7 = Py.EmptyObjects;
         var4 = Py.makeClass("pywintypes", var7, pywintypes$5);
         var1.setlocal("pywintypes", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
      } else {
         var1.setline(427);
         if (var1.getname("jython").__nonzero__()) {
            var1.setline(428);
            var3 = imp.importOne("errno", var1, -1);
            var1.setlocal("errno", var3);
            var3 = null;
            var1.setline(429);
            var3 = imp.importOne("threading", var1, -1);
            var1.setlocal("threading", var3);
            var3 = null;
            var1.setline(430);
            var3 = imp.importOne("java.io.File", var1, -1);
            var1.setlocal("java", var3);
            var3 = null;
            var1.setline(431);
            var3 = imp.importOne("java.io.IOException", var1, -1);
            var1.setlocal("java", var3);
            var3 = null;
            var1.setline(432);
            var3 = imp.importOne("java.io.FileNotFoundException", var1, -1);
            var1.setlocal("java", var3);
            var3 = null;
            var1.setline(433);
            var3 = imp.importOne("java.lang.IllegalArgumentException", var1, -1);
            var1.setlocal("java", var3);
            var3 = null;
            var1.setline(434);
            var3 = imp.importOne("java.lang.IllegalThreadStateException", var1, -1);
            var1.setlocal("java", var3);
            var3 = null;
            var1.setline(435);
            var3 = imp.importOne("java.lang.ProcessBuilder", var1, -1);
            var1.setlocal("java", var3);
            var3 = null;
            var1.setline(436);
            var3 = imp.importOne("java.lang.System", var1, -1);
            var1.setlocal("java", var3);
            var3 = null;
            var1.setline(437);
            var3 = imp.importOne("java.lang.Thread", var1, -1);
            var1.setlocal("java", var3);
            var3 = null;
            var1.setline(438);
            var3 = imp.importOne("java.nio.ByteBuffer", var1, -1);
            var1.setlocal("java", var3);
            var3 = null;
            var1.setline(439);
            var3 = imp.importOne("org.python.core.io.RawIOBase", var1, -1);
            var1.setlocal("org", var3);
            var3 = null;
            var1.setline(440);
            var3 = imp.importOne("org.python.core.io.StreamIO", var1, -1);
            var1.setlocal("org", var3);
            var3 = null;
            var1.setline(441);
            var8 = new String[]{"fileSystemDecode"};
            var7 = imp.importFrom("org.python.core.Py", var8, var1, -1);
            var4 = var7[0];
            var1.setlocal("fileSystemDecode", var4);
            var4 = null;
         } else {
            var1.setline(443);
            var3 = imp.importOne("select", var1, -1);
            var1.setlocal("select", var3);
            var3 = null;
            var1.setline(444);
            var3 = var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("select"), (PyObject)PyString.fromInterned("poll"));
            var1.setlocal("_has_poll", var3);
            var3 = null;
            var1.setline(445);
            var3 = imp.importOne("errno", var1, -1);
            var1.setlocal("errno", var3);
            var3 = null;
            var1.setline(446);
            var3 = imp.importOne("fcntl", var1, -1);
            var1.setlocal("fcntl", var3);
            var3 = null;
            var1.setline(447);
            var3 = imp.importOne("gc", var1, -1);
            var1.setlocal("gc", var3);
            var3 = null;
            var1.setline(448);
            var3 = imp.importOne("pickle", var1, -1);
            var1.setlocal("pickle", var3);
            var3 = null;
            var1.setline(453);
            var3 = var1.getname("getattr").__call__((ThreadState)var2, var1.getname("select"), (PyObject)PyString.fromInterned("PIPE_BUF"), (PyObject)Py.newInteger(512));
            var1.setlocal("_PIPE_BUF", var3);
            var3 = null;
         }
      }

      var1.setline(456);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("Popen"), PyString.fromInterned("PIPE"), PyString.fromInterned("STDOUT"), PyString.fromInterned("call"), PyString.fromInterned("check_call"), PyString.fromInterned("check_output"), PyString.fromInterned("CalledProcessError")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(459);
      if (var1.getname("mswindows").__nonzero__()) {
         var1.setline(460);
         var8 = new String[]{"CREATE_NEW_CONSOLE", "CREATE_NEW_PROCESS_GROUP"};
         var7 = imp.importFrom("_subprocess", var8, var1, -1);
         var4 = var7[0];
         var1.setlocal("CREATE_NEW_CONSOLE", var4);
         var4 = null;
         var4 = var7[1];
         var1.setlocal("CREATE_NEW_PROCESS_GROUP", var4);
         var4 = null;
         var1.setline(461);
         var1.getname("__all__").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("CREATE_NEW_CONSOLE"), PyString.fromInterned("CREATE_NEW_PROCESS_GROUP")})));
      }

      try {
         var1.setline(463);
         var3 = var1.getname("os").__getattr__("sysconf").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SC_OPEN_MAX"));
         var1.setlocal("MAXFD", var3);
         var3 = null;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(465);
         PyInteger var6 = Py.newInteger(256);
         var1.setlocal("MAXFD", var6);
         var4 = null;
      }

      var1.setline(467);
      var9 = new PyList(Py.EmptyObjects);
      var1.setlocal("_active", var9);
      var3 = null;
      var1.setline(469);
      var7 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var7, _cleanup$6, (PyObject)null);
      var1.setlocal("_cleanup", var10);
      var3 = null;
      var1.setline(480);
      PyInteger var11 = Py.newInteger(-1);
      var1.setlocal("PIPE", var11);
      var3 = null;
      var1.setline(481);
      var11 = Py.newInteger(-2);
      var1.setlocal("STDOUT", var11);
      var3 = null;
      var1.setline(484);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, _eintr_retry_call$7, (PyObject)null);
      var1.setlocal("_eintr_retry_call", var10);
      var3 = null;
      var1.setline(494);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, call$8, PyString.fromInterned("Run command with arguments.  Wait for command to complete, then\n    return the returncode attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    retcode = call([\"ls\", \"-l\"])\n    "));
      var1.setlocal("call", var10);
      var3 = null;
      var1.setline(505);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, check_call$9, PyString.fromInterned("Run command with arguments.  Wait for command to complete.  If\n    the exit code was zero then return, otherwise raise\n    CalledProcessError.  The CalledProcessError object will have the\n    return code in the returncode attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    check_call([\"ls\", \"-l\"])\n    "));
      var1.setlocal("check_call", var10);
      var3 = null;
      var1.setline(524);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, check_output$10, PyString.fromInterned("Run command with arguments and return its output as a byte string.\n\n    If the exit code was non-zero it raises a CalledProcessError.  The\n    CalledProcessError object will have the return code in the returncode\n    attribute and output in the output attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    >>> check_output([\"ls\", \"-l\", \"/dev/null\"])\n    'crw-rw-rw- 1 root root 1, 3 Oct 18  2007 /dev/null\\n'\n\n    The stdout argument is not allowed as it is used internally.\n    To capture standard error in the result, use stderr=STDOUT.\n\n    >>> check_output([\"/bin/sh\", \"-c\",\n    ...               \"ls -l non_existent_file ; exit 0\"],\n    ...              stderr=STDOUT)\n    'ls: non_existent_file: No such file or directory\\n'\n    "));
      var1.setlocal("check_output", var10);
      var3 = null;
      var1.setline(557);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, list2cmdline$11, PyString.fromInterned("\n    Translate a sequence of arguments into a command line\n    string, using the same rules as the MS C runtime:\n\n    1) Arguments are delimited by white space, which is either a\n       space or a tab.\n\n    2) A string surrounded by double quotation marks is\n       interpreted as a single argument, regardless of white space\n       contained within.  A quoted string can be embedded in an\n       argument.\n\n    3) A double quotation mark preceded by a backslash is\n       interpreted as a literal double quotation mark.\n\n    4) Backslashes are interpreted literally, unless they\n       immediately precede a double quotation mark.\n\n    5) If backslashes immediately precede a double quotation mark,\n       every pair of backslashes is interpreted as a literal\n       backslash.  If the number of backslashes is odd, the last\n       backslash escapes the next double quotation mark as\n       described in rule 3.\n    "));
      var1.setlocal("list2cmdline", var10);
      var3 = null;
      var1.setline(627);
      if (var1.getname("jython").__nonzero__()) {
         var1.setline(629);
         var9 = new PyList(new PyObject[]{PyString.fromInterned("nt")});
         var1.setlocal("_win_oses", var9);
         var3 = null;
         var1.setline(631);
         var3 = var1.getname("None");
         var1.setlocal("_cmdline2listimpl", var3);
         var3 = null;
         var1.setline(632);
         var3 = var1.getname("None");
         var1.setlocal("_escape_args", var3);
         var3 = null;
         var1.setline(633);
         var3 = var1.getname("None");
         var1.setlocal("_shell_command", var3);
         var3 = null;
         var1.setline(635);
         var7 = Py.EmptyObjects;
         var10 = new PyFunction(var1.f_globals, var7, _cmdline2list$12, PyString.fromInterned("Build an argv list from a Microsoft shell style cmdline str\n\n        The reverse of list2cmdline that follows the same MS C runtime\n        rules.\n\n        Java's ProcessBuilder takes a List<String> cmdline that's joined\n        with a list2cmdline-like routine for Windows CreateProcess\n        (which takes a String cmdline). This process ruins String\n        cmdlines from the user with escapes or quotes. To avoid this we\n        first parse these cmdlines into an argv.\n\n        Runtime.exec(String) is too naive and useless for this case.\n        "));
         var1.setlocal("_cmdline2list", var10);
         var3 = null;
         var1.setline(691);
         var7 = Py.EmptyObjects;
         var10 = new PyFunction(var1.f_globals, var7, _setup_platform$13, PyString.fromInterned("Setup the shell command and the command line argument escape\n        function depending on the underlying platform\n        "));
         var1.setlocal("_setup_platform", var10);
         var3 = null;
         var1.setline(719);
         var1.getname("_setup_platform").__call__(var2);
         var1.setline(722);
         var7 = new PyObject[]{var1.getname("java").__getattr__("lang").__getattr__("Thread")};
         var4 = Py.makeClass("_CouplerThread", var7, _CouplerThread$17);
         var1.setlocal("_CouplerThread", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
      }

      var1.setline(772);
      if (var1.getname("jython").__nonzero__()) {
         var1.setline(773);
         var7 = Py.EmptyObjects;
         var10 = new PyFunction(var1.f_globals, var7, _setup_env$20, PyString.fromInterned("Carefully merge env with ProcessBuilder's only\n        overwriting key/values that differ\n\n        System.getenv (Map<String, String>) may be backed by\n        <byte[], byte[]> on UNIX platforms where these are really\n        bytes. ProcessBuilder's env inherits its contents and will\n        maintain those byte values (which may be butchered as\n        Strings) for the subprocess if they haven't been modified.\n        "));
         var1.setlocal("_setup_env", var10);
         var3 = null;
      }

      var1.setline(799);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Popen", var7, Popen$22);
      var1.setlocal("Popen", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1895);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, _os_system$67, PyString.fromInterned("system(command) -> exit_status\n\n    Execute the command (a string) in a subshell."));
      var1.setlocal("_os_system", var10);
      var3 = null;
      var1.setline(1920);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, _demo_posix$68, (PyObject)null);
      var1.setlocal("_demo_posix", var10);
      var3 = null;
      var1.setline(1961);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, _demo_windows$70, (PyObject)null);
      var1.setlocal("_demo_windows", var10);
      var3 = null;
      var1.setline(1978);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, _demo_jython$71, (PyObject)null);
      var1.setlocal("_demo_jython", var10);
      var3 = null;
      var1.setline(2007);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2008);
         if (var1.getname("mswindows").__nonzero__()) {
            var1.setline(2009);
            var1.getname("_demo_windows").__call__(var2);
         } else {
            var1.setline(2010);
            if (var1.getname("jython").__nonzero__()) {
               var1.setline(2011);
               var1.getname("_demo_jython").__call__(var2);
            } else {
               var1.setline(2013);
               var1.getname("_demo_posix").__call__(var2);
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CalledProcessError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This exception is raised when a process run by check_call() or\n    check_output() returns a non-zero exit status.\n    The exit status will be stored in the returncode attribute;\n    check_output() will also store the output in the output attribute.\n    "));
      var1.setline(406);
      PyString.fromInterned("This exception is raised when a process run by check_call() or\n    check_output() returns a non-zero exit status.\n    The exit status will be stored in the returncode attribute;\n    check_output() will also store the output in the output attribute.\n    ");
      var1.setline(407);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(411);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$3, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("returncode", var3);
      var3 = null;
      var1.setline(409);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("cmd", var3);
      var3 = null;
      var1.setline(410);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("output", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$3(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyObject var3 = PyString.fromInterned("Command '%s' returned non-zero exit status %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("cmd"), var1.getlocal(0).__getattr__("returncode")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject STARTUPINFO$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(420);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("dwFlags", var3);
      var3 = null;
      var1.setline(421);
      PyObject var4 = var1.getname("None");
      var1.setlocal("hStdInput", var4);
      var3 = null;
      var1.setline(422);
      var4 = var1.getname("None");
      var1.setlocal("hStdOutput", var4);
      var3 = null;
      var1.setline(423);
      var4 = var1.getname("None");
      var1.setlocal("hStdError", var4);
      var3 = null;
      var1.setline(424);
      var3 = Py.newInteger(0);
      var1.setlocal("wShowWindow", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject pywintypes$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(426);
      PyObject var3 = var1.getname("IOError");
      var1.setlocal("error", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _cleanup$6(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyObject var3 = var1.getglobal("_active").__getslice__((PyObject)null, (PyObject)null, (PyObject)null).__iter__();

      while(true) {
         PyObject var10000;
         do {
            var1.setline(470);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(0, var4);
            var1.setline(471);
            var10000 = var1.getlocal(0).__getattr__("_internal_poll");
            PyObject[] var5 = new PyObject[]{var1.getglobal("sys").__getattr__("maxint")};
            String[] var6 = new String[]{"_deadstate"};
            var10000 = var10000.__call__(var2, var5, var6);
            var5 = null;
            PyObject var8 = var10000;
            var1.setlocal(1, var8);
            var5 = null;
            var1.setline(472);
            var8 = var1.getlocal(1);
            var10000 = var8._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var8 = var1.getlocal(1);
               var10000 = var8._ge(Py.newInteger(0));
               var5 = null;
            }
         } while(!var10000.__nonzero__());

         try {
            var1.setline(474);
            var1.getglobal("_active").__getattr__("remove").__call__(var2, var1.getlocal(0));
         } catch (Throwable var7) {
            PyException var9 = Py.setException(var7, var1);
            if (!var9.match(var1.getglobal("ValueError"))) {
               throw var9;
            }

            var1.setline(478);
         }
      }
   }

   public PyObject _eintr_retry_call$7(PyFrame var1, ThreadState var2) {
      while(true) {
         var1.setline(485);
         if (!var1.getglobal("True").__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject var10000;
         try {
            var1.setline(487);
            var10000 = var1.getlocal(0);
            PyObject[] var3 = Py.EmptyObjects;
            String[] var8 = new String[0];
            var10000 = var10000._callextra(var3, var8, var1.getlocal(1), (PyObject)null);
            var3 = null;
            PyObject var7 = var10000;
            var1.f_lasti = -1;
            return var7;
         } catch (Throwable var6) {
            PyException var4 = Py.setException(var6, var1);
            if (var4.match(var1.getglobal("OSError"))) {
               PyObject var5 = var4.value;
               var1.setlocal(2, var5);
               var5 = null;
               var1.setline(489);
               var5 = var1.getlocal(2).__getattr__("errno");
               var10000 = var5._eq(var1.getglobal("errno").__getattr__("EINTR"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  continue;
               }

               var1.setline(491);
               throw Py.makeException();
            }

            throw var4;
         }
      }
   }

   public PyObject call$8(PyFrame var1, ThreadState var2) {
      var1.setline(501);
      PyString.fromInterned("Run command with arguments.  Wait for command to complete, then\n    return the returncode attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    retcode = call([\"ls\", \"-l\"])\n    ");
      var1.setline(502);
      PyObject var10000 = var1.getglobal("Popen");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), var1.getlocal(1));
      var3 = null;
      PyObject var5 = var10000.__getattr__("wait").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject check_call$9(PyFrame var1, ThreadState var2) {
      var1.setline(514);
      PyString.fromInterned("Run command with arguments.  Wait for command to complete.  If\n    the exit code was zero then return, otherwise raise\n    CalledProcessError.  The CalledProcessError object will have the\n    return code in the returncode attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    check_call([\"ls\", \"-l\"])\n    ");
      var1.setline(515);
      PyObject var10000 = var1.getglobal("call");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), var1.getlocal(1));
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(516);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(517);
         var5 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("args"));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(518);
         var5 = var1.getlocal(3);
         var10000 = var5._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(519);
            var5 = var1.getlocal(0).__getitem__(Py.newInteger(0));
            var1.setlocal(3, var5);
            var3 = null;
         }

         var1.setline(520);
         throw Py.makeException(var1.getglobal("CalledProcessError").__call__(var2, var1.getlocal(2), var1.getlocal(3)));
      } else {
         var1.setline(521);
         PyInteger var6 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject check_output$10(PyFrame var1, ThreadState var2) {
      var1.setline(543);
      PyString.fromInterned("Run command with arguments and return its output as a byte string.\n\n    If the exit code was non-zero it raises a CalledProcessError.  The\n    CalledProcessError object will have the return code in the returncode\n    attribute and output in the output attribute.\n\n    The arguments are the same as for the Popen constructor.  Example:\n\n    >>> check_output([\"ls\", \"-l\", \"/dev/null\"])\n    'crw-rw-rw- 1 root root 1, 3 Oct 18  2007 /dev/null\\n'\n\n    The stdout argument is not allowed as it is used internally.\n    To capture standard error in the result, use stderr=STDOUT.\n\n    >>> check_output([\"/bin/sh\", \"-c\",\n    ...               \"ls -l non_existent_file ; exit 0\"],\n    ...              stderr=STDOUT)\n    'ls: non_existent_file: No such file or directory\\n'\n    ");
      var1.setline(544);
      PyString var3 = PyString.fromInterned("stdout");
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(545);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("stdout argument not allowed, it will be overridden.")));
      } else {
         var1.setline(546);
         var10000 = var1.getglobal("Popen");
         PyObject[] var6 = new PyObject[]{var1.getglobal("PIPE")};
         String[] var4 = new String[]{"stdout"};
         var10000 = var10000._callextra(var6, var4, var1.getlocal(0), var1.getlocal(1));
         var3 = null;
         PyObject var7 = var10000;
         var1.setlocal(2, var7);
         var3 = null;
         var1.setline(547);
         var7 = var1.getlocal(2).__getattr__("communicate").__call__(var2);
         PyObject[] var8 = Py.unpackSequence(var7, 2);
         PyObject var5 = var8[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(548);
         var7 = var1.getlocal(2).__getattr__("poll").__call__(var2);
         var1.setlocal(5, var7);
         var3 = null;
         var1.setline(549);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(550);
            var7 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("args"));
            var1.setlocal(6, var7);
            var3 = null;
            var1.setline(551);
            var7 = var1.getlocal(6);
            var10000 = var7._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(552);
               var7 = var1.getlocal(0).__getitem__(Py.newInteger(0));
               var1.setlocal(6, var7);
               var3 = null;
            }

            var1.setline(553);
            var10000 = var1.getglobal("CalledProcessError");
            var6 = new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(3)};
            var4 = new String[]{"output"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            throw Py.makeException(var10000);
         } else {
            var1.setline(554);
            var7 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var7;
         }
      }
   }

   public PyObject list2cmdline$11(PyFrame var1, ThreadState var2) {
      var1.setline(581);
      PyString.fromInterned("\n    Translate a sequence of arguments into a command line\n    string, using the same rules as the MS C runtime:\n\n    1) Arguments are delimited by white space, which is either a\n       space or a tab.\n\n    2) A string surrounded by double quotation marks is\n       interpreted as a single argument, regardless of white space\n       contained within.  A quoted string can be embedded in an\n       argument.\n\n    3) A double quotation mark preceded by a backslash is\n       interpreted as a literal double quotation mark.\n\n    4) Backslashes are interpreted literally, unless they\n       immediately precede a double quotation mark.\n\n    5) If backslashes immediately precede a double quotation mark,\n       every pair of backslashes is interpreted as a literal\n       backslash.  If the number of backslashes is odd, the last\n       backslash escapes the next double quotation mark as\n       described in rule 3.\n    ");
      var1.setline(587);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(588);
      PyObject var8 = var1.getglobal("False");
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(589);
      var8 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(589);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(624);
            var8 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(3, var4);
         var1.setline(590);
         PyList var5 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(593);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(594);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
         }

         var1.setline(596);
         PyString var9 = PyString.fromInterned(" ");
         PyObject var10000 = var9._in(var1.getlocal(3));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var9 = PyString.fromInterned("\t");
            var10000 = var9._in(var1.getlocal(3));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(3).__not__();
            }
         }

         PyObject var10 = var10000;
         var1.setlocal(2, var10);
         var5 = null;
         var1.setline(597);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(598);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""));
         }

         var1.setline(600);
         var10 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(600);
            PyObject var6 = var10.__iternext__();
            if (var6 == null) {
               var1.setline(617);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(618);
                  var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(4));
               }

               var1.setline(620);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(621);
                  var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(4));
                  var1.setline(622);
                  var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""));
               }
               break;
            }

            var1.setlocal(5, var6);
            var1.setline(601);
            PyObject var7 = var1.getlocal(5);
            var10000 = var7._eq(PyString.fromInterned("\\"));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(603);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5));
            } else {
               var1.setline(604);
               var7 = var1.getlocal(5);
               var10000 = var7._eq(PyString.fromInterned("\""));
               var7 = null;
               PyList var11;
               if (var10000.__nonzero__()) {
                  var1.setline(606);
                  var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("\\")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(4)))._mul(Py.newInteger(2)));
                  var1.setline(607);
                  var11 = new PyList(Py.EmptyObjects);
                  var1.setlocal(4, var11);
                  var7 = null;
                  var1.setline(608);
                  var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\""));
               } else {
                  var1.setline(611);
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(612);
                     var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(4));
                     var1.setline(613);
                     var11 = new PyList(Py.EmptyObjects);
                     var1.setlocal(4, var11);
                     var7 = null;
                  }

                  var1.setline(614);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(5));
               }
            }
         }
      }
   }

   public PyObject _cmdline2list$12(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      PyString.fromInterned("Build an argv list from a Microsoft shell style cmdline str\n\n        The reverse of list2cmdline that follows the same MS C runtime\n        rules.\n\n        Java's ProcessBuilder takes a List<String> cmdline that's joined\n        with a list2cmdline-like routine for Windows CreateProcess\n        (which takes a String cmdline). This process ruins String\n        cmdlines from the user with escapes or quotes. To avoid this we\n        first parse these cmdlines into an argv.\n\n        Runtime.exec(String) is too naive and useless for this case.\n        ");
      var1.setline(649);
      PyString var3 = PyString.fromInterned(" \t");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(651);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(652);
      PyObject var7 = var1.getglobal("False");
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(653);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(654);
      var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(656);
      var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(656);
         PyObject var4 = var7.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(686);
            var10000 = var1.getlocal(4);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(3);
            }

            if (var10000.__nonzero__()) {
               var1.setline(687);
               var1.getlocal(5).__getattr__("append").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4)));
            }

            var1.setline(689);
            var7 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(6, var4);
         var1.setline(657);
         PyObject var5 = var1.getlocal(6);
         var10000 = var5._in(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__not__();
         }

         PyInteger var10;
         if (var10000.__nonzero__()) {
            var1.setline(658);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(660);
               var1.getlocal(5).__getattr__("append").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4)));
               var1.setline(661);
               PyList var9 = new PyList(Py.EmptyObjects);
               var1.setlocal(4, var9);
               var5 = null;
            }

            var1.setline(662);
            var10 = Py.newInteger(0);
            var1.setlocal(2, var10);
            var5 = null;
         } else {
            var1.setline(663);
            var5 = var1.getlocal(6);
            var10000 = var5._eq(PyString.fromInterned("\\"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(664);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
               var1.setline(665);
               var5 = var1.getlocal(2);
               var5 = var5._iadd(Py.newInteger(1));
               var1.setlocal(2, var5);
            } else {
               var1.setline(666);
               var5 = var1.getlocal(6);
               var10000 = var5._eq(PyString.fromInterned("\""));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(667);
                  if (var1.getlocal(2)._mod(Py.newInteger(2)).__not__().__nonzero__()) {
                     var1.setline(670);
                     if (var1.getlocal(2).__nonzero__()) {
                        var1.setline(671);
                        var1.getlocal(4).__delslice__(var1.getlocal(2)._div(Py.newInteger(2)).__neg__(), (PyObject)null, (PyObject)null);
                     }

                     var1.setline(672);
                     var5 = var1.getlocal(3).__not__();
                     var1.setlocal(3, var5);
                     var5 = null;
                  } else {
                     var1.setline(677);
                     var1.getlocal(4).__delslice__(var1.getlocal(2)._div(Py.newInteger(2))._add(Py.newInteger(1)).__neg__(), (PyObject)null, (PyObject)null);
                     var1.setline(678);
                     var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
                  }

                  var1.setline(679);
                  var10 = Py.newInteger(0);
                  var1.setlocal(2, var10);
                  var5 = null;
               } else {
                  var1.setline(682);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
                  var1.setline(683);
                  var10 = Py.newInteger(0);
                  var1.setlocal(2, var10);
                  var5 = null;
               }
            }
         }
      }
   }

   public PyObject _setup_platform$13(PyFrame var1, ThreadState var2) {
      var1.setline(694);
      PyString.fromInterned("Setup the shell command and the command line argument escape\n        function depending on the underlying platform\n        ");
      var1.setline(697);
      PyObject var3 = var1.getglobal("os").__getattr__("_name");
      PyObject var10000 = var3._in(var1.getglobal("_win_oses"));
      var3 = null;
      PyObject[] var6;
      PyFunction var7;
      if (var10000.__nonzero__()) {
         var1.setline(698);
         var3 = var1.getglobal("_cmdline2list");
         var1.setglobal("_cmdline2listimpl", var3);
         var3 = null;
         var1.setline(699);
         var1.setline(699);
         var6 = Py.EmptyObjects;
         var7 = new PyFunction(var1.f_globals, var6, f$14);
         var1.setglobal("_escape_args", var7);
         var3 = null;
      } else {
         var1.setline(701);
         var1.setline(701);
         var6 = Py.EmptyObjects;
         var7 = new PyFunction(var1.f_globals, var6, f$15);
         var1.setglobal("_cmdline2listimpl", var7);
         var3 = null;
         var1.setline(702);
         var1.setline(702);
         var6 = Py.EmptyObjects;
         var7 = new PyFunction(var1.f_globals, var6, f$16);
         var1.setglobal("_escape_args", var7);
         var3 = null;
      }

      var1.setline(704);
      var3 = var1.getglobal("os").__getattr__("_get_shell_commands").__call__(var2).__iter__();

      PyObject var5;
      do {
         var1.setline(704);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(715);
            if (var1.getglobal("_shell_command").__not__().__nonzero__()) {
               var1.setline(716);
               var3 = imp.importOne("warnings", var1, -1);
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(717);
               var1.getlocal(3).__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("Unable to determine _shell_command for underlying os: %s")._mod(var1.getglobal("os").__getattr__("_name")), (PyObject)var1.getglobal("RuntimeWarning"), (PyObject)Py.newInteger(3));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(0, var4);
         var1.setline(705);
         var5 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var1.setlocal(1, var5);
         var5 = null;
         var1.setline(706);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(707);
            var5 = imp.importOne("distutils.spawn", var1, -1);
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(708);
            var5 = var1.getlocal(2).__getattr__("spawn").__getattr__("find_executable").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var5);
            var5 = null;
         }

         var1.setline(709);
         var10000 = var1.getlocal(1).__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__not__();
         }
      } while(var10000.__nonzero__());

      var1.setline(711);
      var5 = var1.getlocal(1);
      var1.getlocal(0).__setitem__((PyObject)Py.newInteger(0), var5);
      var5 = null;
      var1.setline(712);
      var5 = var1.getlocal(0);
      var1.setglobal("_shell_command", var5);
      var5 = null;
      var1.setline(713);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$14(PyFrame var1, ThreadState var2) {
      var1.setline(699);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(699);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(699);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(699);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(699);
         var1.getlocal(1).__call__(var2, var1.getglobal("list2cmdline").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2)}))));
      }
   }

   public PyObject f$15(PyFrame var1, ThreadState var2) {
      var1.setline(701);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$16(PyFrame var1, ThreadState var2) {
      var1.setline(702);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _CouplerThread$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Couples a reader and writer RawIOBase.\n\n        Streams data from the reader's read_func (a RawIOBase readinto\n        method) to the writer's write_func (a RawIOBase write method) in\n        a separate thread. Optionally calls close_func when finished\n        streaming or an exception occurs.\n\n        This thread will fail safe when interrupted by Java's\n        Thread.interrupt.\n        "));
      var1.setline(733);
      PyString.fromInterned("Couples a reader and writer RawIOBase.\n\n        Streams data from the reader's read_func (a RawIOBase readinto\n        method) to the writer's write_func (a RawIOBase write method) in\n        a separate thread. Optionally calls close_func when finished\n        streaming or an exception occurs.\n\n        This thread will fail safe when interrupted by Java's\n        Thread.interrupt.\n        ");
      var1.setline(736);
      PyInteger var3 = Py.newInteger(4096);
      var1.setlocal("bufsize", var3);
      var3 = null;
      var1.setline(738);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(746);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, run$19, (PyObject)null);
      var1.setlocal("run", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(739);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("read_func", var3);
      var3 = null;
      var1.setline(740);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("write_func", var3);
      var3 = null;
      var1.setline(741);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("close_func", var3);
      var3 = null;
      var1.setline(742);
      var1.getlocal(0).__getattr__("setName").__call__(var2, PyString.fromInterned("%s-%s (%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("id").__call__(var2, var1.getlocal(0)), var1.getlocal(1)})));
      var1.setline(744);
      var1.getlocal(0).__getattr__("setDaemon").__call__(var2, var1.getglobal("True"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$19(PyFrame var1, ThreadState var2) {
      var1.setline(747);
      PyObject var3 = var1.getglobal("java").__getattr__("nio").__getattr__("ByteBuffer").__getattr__("allocate").__call__(var2, var1.getlocal(0).__getattr__("bufsize"));
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(748);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         PyObject var10000;
         try {
            var1.setline(750);
            var3 = var1.getlocal(0).__getattr__("read_func").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(751);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(752);
               if (var1.getlocal(0).__getattr__("close_func").__nonzero__()) {
                  var1.setline(753);
                  var1.getlocal(0).__getattr__("close_func").__call__(var2);
               }
               break;
            }

            var1.setline(755);
            var1.getlocal(1).__getattr__("flip").__call__(var2);
            var1.setline(756);
            var1.getlocal(0).__getattr__("write_func").__call__(var2, var1.getlocal(1));
            var1.setline(757);
            var1.getlocal(1).__getattr__("flip").__call__(var2);
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("IOError"))) {
               PyObject var4 = var7.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(759);
               if (var1.getlocal(0).__getattr__("close_func").__nonzero__()) {
                  try {
                     var1.setline(761);
                     var1.getlocal(0).__getattr__("close_func").__call__(var2);
                  } catch (Throwable var5) {
                     Py.setException(var5, var1);
                     var1.setline(763);
                  }
               }

               var1.setline(766);
               var4 = var1.getglobal("str").__call__(var2, var1.getlocal(3));
               var10000 = var4._eq(PyString.fromInterned("java.nio.channels.ClosedByInterruptException"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(768);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(769);
               throw Py.makeException();
            }

            throw var7;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _setup_env$20(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.setline(782);
      PyString.fromInterned("Carefully merge env with ProcessBuilder's only\n        overwriting key/values that differ\n\n        System.getenv (Map<String, String>) may be backed by\n        <byte[], byte[]> on UNIX platforms where these are really\n        bytes. ProcessBuilder's env inherits its contents and will\n        maintain those byte values (which may be butchered as\n        Strings) for the subprocess if they haven't been modified.\n        ");
      var1.setline(784);
      PyObject var10000 = var1.getglobal("dict");
      var1.setline(784);
      PyObject var10004 = var1.f_globals;
      PyObject[] var3 = Py.EmptyObjects;
      PyCode var10006 = f$21;
      PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var8 = new PyFunction(var10004, var3, var10006, (PyObject)null, var4);
      PyObject var10002 = var8.__call__(var2, var1.getlocal(0).__getattr__("iteritems").__call__(var2).__iter__());
      Arrays.fill(var3, (Object)null);
      PyObject var7 = var10000.__call__(var2, var10002);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(789);
      var7 = var1.getderef(0).__getattr__("entrySet").__call__(var2).__getattr__("iterator").__call__(var2);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(790);
      var7 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(790);
         PyObject var9 = var7.__iternext__();
         if (var9 == null) {
            var1.setline(794);
            var7 = var1.getlocal(2).__getattr__("iteritems").__call__(var2).__iter__();

            while(true) {
               var1.setline(794);
               var9 = var7.__iternext__();
               if (var9 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               PyObject[] var10 = Py.unpackSequence(var9, 2);
               PyObject var6 = var10[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var10[1];
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(796);
               var1.getderef(0).__getattr__("put").__call__(var2, var1.getlocal(6), var1.getglobal("fileSystemDecode").__call__(var2, var1.getlocal(7)));
            }
         }

         var1.setlocal(5, var9);
         var1.setline(791);
         PyObject var5 = var1.getlocal(5).__getattr__("getKey").__call__(var2);
         var10000 = var5._notin(var1.getlocal(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(792);
            var1.getlocal(4).__getattr__("remove").__call__(var2);
         }
      }
   }

   public PyObject f$21(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(784);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
      }

      PyObject[] var7;
      do {
         var1.setline(784);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var7 = Py.unpackSequence(var4, 2);
         PyObject var6 = var7[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var7[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(785);
         PyObject var8 = var1.getlocal(1);
         var9 = var8._notin(var1.getderef(0));
         var5 = null;
         if (!var9.__nonzero__()) {
            var8 = var1.getderef(0).__getattr__("get").__call__(var2, var1.getlocal(1));
            var9 = var8._ne(var1.getlocal(2));
            var5 = null;
         }
      } while(!var9.__nonzero__());

      var1.setline(784);
      var1.setline(784);
      var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
      PyTuple var10 = new PyTuple(var7);
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var10;
   }

   public PyObject Popen$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(800);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("False"), var1.getname("False"), var1.getname("None"), var1.getname("None"), var1.getname("False"), var1.getname("None"), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$23, PyString.fromInterned("Create new Popen instance."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(944);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _translate_newlines$24, (PyObject)null);
      var1.setlocal("_translate_newlines", var4);
      var3 = null;
      var1.setline(950);
      var3 = new PyObject[]{var1.getname("sys").__getattr__("maxint"), var1.getname("_active")};
      var4 = new PyFunction(var1.f_globals, var3, __del__$25, (PyObject)null);
      var1.setlocal("__del__", var4);
      var3 = null;
      var1.setline(961);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, communicate$26, PyString.fromInterned("Interact with process: Send data to stdin.  Read data from\n        stdout and stderr, until end-of-file is reached.  Wait for\n        process to terminate.  The optional input argument should be a\n        string to be sent to the child process, or None, if no data\n        should be sent to the child.\n\n        communicate() returns a tuple (stdout, stderr)."));
      var1.setlocal("communicate", var4);
      var3 = null;
      var1.setline(991);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, poll$27, (PyObject)null);
      var1.setlocal("poll", var4);
      var3 = null;
      var1.setline(995);
      PyObject var10000 = var1.getname("mswindows");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getname("jython");
      }

      if (var10000.__nonzero__()) {
         var1.setline(999);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, _readerthread$28, (PyObject)null);
         var1.setlocal("_readerthread", var4);
         var3 = null;
         var1.setline(1003);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, _communicate$29, (PyObject)null);
         var1.setlocal("_communicate", var4);
         var3 = null;
      }

      var1.setline(1050);
      if (var1.getname("mswindows").__nonzero__()) {
         var1.setline(1054);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, _get_handles$30, PyString.fromInterned("Construct and return tuple with IO objects:\n            p2cread, p2cwrite, c2pread, c2pwrite, errread, errwrite\n            "));
         var1.setlocal("_get_handles", var4);
         var3 = null;
         var1.setline(1111);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, _make_inheritable$31, PyString.fromInterned("Return a duplicate of handle, which is inheritable"));
         var1.setlocal("_make_inheritable", var4);
         var3 = null;
         var1.setline(1118);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, _find_w9xpopen$32, PyString.fromInterned("Find and return absolut path to w9xpopen.exe"));
         var1.setlocal("_find_w9xpopen", var4);
         var3 = null;
         var1.setline(1135);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, _execute_child$33, PyString.fromInterned("Execute program (MS Windows version)"));
         var1.setlocal("_execute_child", var4);
         var3 = null;
         var1.setline(1212);
         var3 = new PyObject[]{var1.getname("None"), var1.getname("_subprocess").__getattr__("WaitForSingleObject"), var1.getname("_subprocess").__getattr__("WAIT_OBJECT_0"), var1.getname("_subprocess").__getattr__("GetExitCodeProcess")};
         var4 = new PyFunction(var1.f_globals, var3, _internal_poll$34, PyString.fromInterned("Check if child process has terminated.  Returns returncode\n            attribute.\n\n            This method is called by __del__, so it can only refer to objects\n            in its local scope.\n\n            "));
         var1.setlocal("_internal_poll", var4);
         var3 = null;
         var1.setline(1229);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, wait$35, PyString.fromInterned("Wait for child process to terminate.  Returns returncode\n            attribute."));
         var1.setlocal("wait", var4);
         var3 = null;
      } else {
         var1.setline(1238);
         if (var1.getname("jython").__nonzero__()) {
            var1.setline(1242);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _get_handles$36, PyString.fromInterned("Construct and return tuple with IO objects:\n            p2cread, p2cwrite, c2pread, c2pwrite, errread, errwrite\n            "));
            var1.setlocal("_get_handles", var4);
            var3 = null;
            var1.setline(1286);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _stderr_is_stdout$37, PyString.fromInterned("Determine if the subprocess' stderr should be redirected\n            to stdout\n            "));
            var1.setlocal("_stderr_is_stdout", var4);
            var3 = null;
            var1.setline(1294);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _coupler_thread$38, PyString.fromInterned("Return a _CouplerThread"));
            var1.setlocal("_coupler_thread", var4);
            var3 = null;
            var1.setline(1301);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _execute_child$39, PyString.fromInterned("Execute program (Java version)"));
            var1.setlocal("_execute_child", var4);
            var3 = null;
            var1.setline(1378);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _get_private_field$41, (PyObject)null);
            var1.setlocal("_get_private_field", var4);
            var3 = null;
            var1.setline(1388);
            PyObject var5 = var1.getname("os").__getattr__("_name");
            var10000 = var5._notin(var1.getname("_win_oses"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1390);
               var3 = new PyObject[]{PyString.fromInterned("pid")};
               var4 = new PyFunction(var1.f_globals, var3, _get_pid$42, (PyObject)null);
               var1.setlocal("_get_pid", var4);
               var3 = null;
            } else {
               var1.setline(1398);
               var5 = imp.importOne("ctypes", var1, -1);
               var1.setlocal("ctypes", var5);
               var3 = null;
               var1.setline(1399);
               var5 = var1.getname("ctypes").__getattr__("cdll").__getattr__("kernel32").__getattr__("GetProcessId");
               var1.setlocal("_handle_to_pid", var5);
               var3 = null;
               var1.setline(1400);
               PyTuple var6 = new PyTuple(new PyObject[]{var1.getname("ctypes").__getattr__("c_long")});
               var1.getname("_handle_to_pid").__setattr__((String)"argtypes", var6);
               var3 = null;
               var1.setline(1402);
               var3 = new PyObject[]{PyString.fromInterned("handle")};
               var4 = new PyFunction(var1.f_globals, var3, _get_pid$43, (PyObject)null);
               var1.setlocal("_get_pid", var4);
               var3 = null;
            }

            var1.setline(1409);
            var3 = new PyObject[]{var1.getname("None")};
            var4 = new PyFunction(var1.f_globals, var3, poll$44, PyString.fromInterned("Check if child process has terminated.  Returns returncode\n            attribute."));
            var1.setlocal("poll", var4);
            var3 = null;
            var1.setline(1419);
            var3 = new PyObject[]{var1.getname("None")};
            var4 = new PyFunction(var1.f_globals, var3, _internal_poll$45, PyString.fromInterned("Check if child process has terminated.  Returns returncode\n            attribute. Called by __del__."));
            var1.setlocal("_internal_poll", var4);
            var3 = null;
            var1.setline(1433);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, wait$46, PyString.fromInterned("Wait for child process to terminate.  Returns returncode\n            attribute."));
            var1.setlocal("wait", var4);
            var3 = null;
            var1.setline(1447);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, terminate$47, PyString.fromInterned("Terminates the process\n            "));
            var1.setlocal("terminate", var4);
            var3 = null;
            var1.setline(1452);
            var5 = var1.getname("os").__getattr__("_name");
            var10000 = var5._notin(var1.getname("_win_oses"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1454);
               var3 = Py.EmptyObjects;
               var4 = new PyFunction(var1.f_globals, var3, kill$48, (PyObject)null);
               var1.setlocal("kill", var4);
               var3 = null;
               var1.setline(1460);
               var3 = Py.EmptyObjects;
               var4 = new PyFunction(var1.f_globals, var3, send_signal$49, PyString.fromInterned("Send a signal to the process\n                "));
               var1.setlocal("send_signal", var4);
               var3 = null;
            } else {
               var1.setline(1467);
               var5 = var1.getname("terminate");
               var1.setlocal("kill", var5);
               var3 = null;
               var1.setline(1469);
               var3 = Py.EmptyObjects;
               var4 = new PyFunction(var1.f_globals, var3, send_signal$50, PyString.fromInterned("Send a signal to the process\n                "));
               var1.setlocal("send_signal", var4);
               var3 = null;
            }
         } else {
            var1.setline(1482);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _get_handles$51, PyString.fromInterned("Construct and return tuple with IO objects:\n            p2cread, p2cwrite, c2pread, c2pwrite, errread, errwrite\n            "));
            var1.setlocal("_get_handles", var4);
            var3 = null;
            var1.setline(1527);
            var3 = new PyObject[]{var1.getname("True")};
            var4 = new PyFunction(var1.f_globals, var3, _set_cloexec_flag$52, (PyObject)null);
            var1.setlocal("_set_cloexec_flag", var4);
            var3 = null;
            var1.setline(1540);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _close_fds$53, (PyObject)null);
            var1.setlocal("_close_fds", var4);
            var3 = null;
            var1.setline(1554);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _execute_child$54, PyString.fromInterned("Execute program (POSIX version)"));
            var1.setlocal("_execute_child", var4);
            var3 = null;
            var1.setline(1689);
            var3 = new PyObject[]{var1.getname("os").__getattr__("WIFSIGNALED"), var1.getname("os").__getattr__("WTERMSIG"), var1.getname("os").__getattr__("WIFEXITED"), var1.getname("os").__getattr__("WEXITSTATUS")};
            var4 = new PyFunction(var1.f_globals, var3, _handle_exitstatus$56, (PyObject)null);
            var1.setlocal("_handle_exitstatus", var4);
            var3 = null;
            var1.setline(1703);
            var3 = new PyObject[]{var1.getname("None"), var1.getname("os").__getattr__("waitpid"), var1.getname("os").__getattr__("WNOHANG"), var1.getname("os").__getattr__("error")};
            var4 = new PyFunction(var1.f_globals, var3, _internal_poll$57, PyString.fromInterned("Check if child process has terminated.  Returns returncode\n            attribute.\n\n            This method is called by __del__, so it cannot reference anything\n            outside of the local scope (nor can any methods it calls).\n\n            "));
            var1.setlocal("_internal_poll", var4);
            var3 = null;
            var1.setline(1723);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, wait$58, PyString.fromInterned("Wait for child process to terminate.  Returns returncode\n            attribute."));
            var1.setlocal("wait", var4);
            var3 = null;
            var1.setline(1740);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _communicate$59, (PyObject)null);
            var1.setlocal("_communicate", var4);
            var3 = null;
            var1.setline(1773);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _communicate_with_poll$60, (PyObject)null);
            var1.setlocal("_communicate_with_poll", var4);
            var3 = null;
            var1.setline(1827);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, _communicate_with_select$63, (PyObject)null);
            var1.setlocal("_communicate_with_select", var4);
            var3 = null;
            var1.setline(1876);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, send_signal$64, PyString.fromInterned("Send a signal to the process\n            "));
            var1.setlocal("send_signal", var4);
            var3 = null;
            var1.setline(1881);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, terminate$65, PyString.fromInterned("Terminate the process with SIGTERM\n            "));
            var1.setlocal("terminate", var4);
            var3 = null;
            var1.setline(1886);
            var3 = Py.EmptyObjects;
            var4 = new PyFunction(var1.f_globals, var3, kill$66, PyString.fromInterned("Kill the process with SIGKILL\n            "));
            var1.setlocal("kill", var4);
            var3 = null;
         }
      }

      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(805);
      PyString.fromInterned("Create new Popen instance.");
      var1.setline(806);
      var1.getglobal("_cleanup").__call__(var2);
      var1.setline(808);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_child_created", var3);
      var3 = null;
      var1.setline(809);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
         var1.setline(810);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bufsize must be an integer")));
      } else {
         var1.setline(812);
         PyObject var10000;
         if (var1.getglobal("mswindows").__nonzero__()) {
            var1.setline(813);
            var3 = var1.getlocal(7);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(814);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("preexec_fn is not supported on Windows platforms")));
            }

            var1.setline(816);
            var10000 = var1.getlocal(8);
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(4);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(5);
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(6);
                     var10000 = var3._isnot(var1.getglobal("None"));
                     var3 = null;
                  }
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(818);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("close_fds is not supported on Windows platforms if you redirect stdin/stdout/stderr")));
            }
         } else {
            var1.setline(822);
            var3 = var1.getlocal(13);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(823);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("startupinfo is only supported on Windows platforms")));
            }

            var1.setline(825);
            var3 = var1.getlocal(14);
            var10000 = var3._ne(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(826);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("creationflags is only supported on Windows platforms")));
            }
         }

         var1.setline(828);
         if (var1.getglobal("jython").__nonzero__()) {
            var1.setline(829);
            var3 = var1.getlocal(7);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(830);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("preexec_fn is not supported on the Jython platform")));
            }
         }

         var1.setline(833);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("stdin", var3);
         var3 = null;
         var1.setline(834);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("stdout", var3);
         var3 = null;
         var1.setline(835);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("stderr", var3);
         var3 = null;
         var1.setline(836);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("pid", var3);
         var3 = null;
         var1.setline(837);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("returncode", var3);
         var3 = null;
         var1.setline(838);
         var3 = var1.getlocal(12);
         var1.getlocal(0).__setattr__("universal_newlines", var3);
         var3 = null;
         var1.setline(855);
         var3 = var1.getlocal(0).__getattr__("_get_handles").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(6));
         PyObject[] var4 = Py.unpackSequence(var3, 6);
         PyObject var5 = var4[0];
         var1.setlocal(15, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(16, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(17, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(18, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(19, var5);
         var5 = null;
         var5 = var4[5];
         var1.setlocal(20, var5);
         var5 = null;
         var3 = null;
         var1.setline(859);
         var10000 = var1.getlocal(0).__getattr__("_execute_child");
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(7), var1.getlocal(8), var1.getlocal(10), var1.getlocal(11), var1.getlocal(12), var1.getlocal(13), var1.getlocal(14), var1.getlocal(9), var1.getlocal(15), var1.getlocal(16), var1.getlocal(17), var1.getlocal(18), var1.getlocal(19), var1.getlocal(20), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
         var10000.__call__(var2, var6);
         var1.setline(867);
         if (var1.getglobal("mswindows").__nonzero__()) {
            var1.setline(868);
            var3 = var1.getlocal(16);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(869);
               var3 = var1.getglobal("msvcrt").__getattr__("open_osfhandle").__call__((ThreadState)var2, (PyObject)var1.getlocal(16).__getattr__("Detach").__call__(var2), (PyObject)Py.newInteger(0));
               var1.setlocal(16, var3);
               var3 = null;
            }

            var1.setline(870);
            var3 = var1.getlocal(17);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(871);
               var3 = var1.getglobal("msvcrt").__getattr__("open_osfhandle").__call__((ThreadState)var2, (PyObject)var1.getlocal(17).__getattr__("Detach").__call__(var2), (PyObject)Py.newInteger(0));
               var1.setlocal(17, var3);
               var3 = null;
            }

            var1.setline(872);
            var3 = var1.getlocal(19);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(873);
               var3 = var1.getglobal("msvcrt").__getattr__("open_osfhandle").__call__((ThreadState)var2, (PyObject)var1.getlocal(19).__getattr__("Detach").__call__(var2), (PyObject)Py.newInteger(0));
               var1.setlocal(19, var3);
               var3 = null;
            }
         }

         var1.setline(875);
         if (var1.getglobal("jython").__nonzero__()) {
            var1.setline(876);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_stdin_thread", var3);
            var3 = null;
            var1.setline(877);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_stdout_thread", var3);
            var3 = null;
            var1.setline(878);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_stderr_thread", var3);
            var3 = null;
            var1.setline(881);
            var3 = var1.getlocal(0).__getattr__("_process");
            var1.setlocal(21, var3);
            var3 = null;
            var1.setline(882);
            var3 = var1.getglobal("org").__getattr__("python").__getattr__("core").__getattr__("io").__getattr__("StreamIO").__call__(var2, var1.getlocal(21).__getattr__("getOutputStream").__call__(var2), var1.getglobal("True"));
            var1.setlocal(22, var3);
            var3 = null;
            var1.setline(884);
            var3 = var1.getglobal("org").__getattr__("python").__getattr__("core").__getattr__("io").__getattr__("StreamIO").__call__(var2, var1.getlocal(21).__getattr__("getInputStream").__call__(var2), var1.getglobal("True"));
            var1.setlocal(23, var3);
            var3 = null;
            var1.setline(885);
            var3 = var1.getglobal("org").__getattr__("python").__getattr__("core").__getattr__("io").__getattr__("StreamIO").__call__(var2, var1.getlocal(21).__getattr__("getErrorStream").__call__(var2), var1.getglobal("True"));
            var1.setlocal(24, var3);
            var3 = null;
            var1.setline(891);
            var3 = var1.getlocal(16);
            var10000 = var3._eq(var1.getglobal("PIPE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(892);
               var3 = var1.getlocal(22);
               var1.setlocal(16, var3);
               var3 = null;
            } else {
               var1.setline(894);
               var3 = var1.getlocal(15);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(900);
               } else {
                  var1.setline(902);
                  var3 = var1.getlocal(0).__getattr__("_coupler_thread").__call__(var2, PyString.fromInterned("stdin"), var1.getlocal(15).__getattr__("readinto"), var1.getlocal(22).__getattr__("write"), var1.getlocal(22).__getattr__("close"));
                  var1.getlocal(0).__setattr__("_stdin_thread", var3);
                  var3 = null;
                  var1.setline(906);
                  var1.getlocal(0).__getattr__("_stdin_thread").__getattr__("start").__call__(var2);
               }
            }

            var1.setline(908);
            var3 = var1.getlocal(17);
            var10000 = var3._eq(var1.getglobal("PIPE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(909);
               var3 = var1.getlocal(23);
               var1.setlocal(17, var3);
               var3 = null;
            } else {
               var1.setline(911);
               var3 = var1.getlocal(18);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(912);
                  var3 = var1.getglobal("org").__getattr__("python").__getattr__("core").__getattr__("io").__getattr__("StreamIO").__call__(var2, var1.getglobal("java").__getattr__("lang").__getattr__("System").__getattr__("out"), var1.getglobal("False"));
                  var1.setlocal(18, var3);
                  var3 = null;
               }

               var1.setline(914);
               var3 = var1.getlocal(0).__getattr__("_coupler_thread").__call__((ThreadState)var2, PyString.fromInterned("stdout"), (PyObject)var1.getlocal(23).__getattr__("readinto"), (PyObject)var1.getlocal(18).__getattr__("write"));
               var1.getlocal(0).__setattr__("_stdout_thread", var3);
               var3 = null;
               var1.setline(917);
               var1.getlocal(0).__getattr__("_stdout_thread").__getattr__("start").__call__(var2);
            }

            var1.setline(919);
            var3 = var1.getlocal(19);
            var10000 = var3._eq(var1.getglobal("PIPE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(920);
               var3 = var1.getlocal(24);
               var1.setlocal(19, var3);
               var3 = null;
            } else {
               var1.setline(921);
               if (var1.getlocal(0).__getattr__("_stderr_is_stdout").__call__(var2, var1.getlocal(20), var1.getlocal(18)).__not__().__nonzero__()) {
                  var1.setline(922);
                  var3 = var1.getlocal(20);
                  var10000 = var3._is(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(923);
                     var3 = var1.getglobal("org").__getattr__("python").__getattr__("core").__getattr__("io").__getattr__("StreamIO").__call__(var2, var1.getglobal("java").__getattr__("lang").__getattr__("System").__getattr__("err"), var1.getglobal("False"));
                     var1.setlocal(20, var3);
                     var3 = null;
                  }

                  var1.setline(925);
                  var3 = var1.getlocal(0).__getattr__("_coupler_thread").__call__((ThreadState)var2, PyString.fromInterned("stderr"), (PyObject)var1.getlocal(24).__getattr__("readinto"), (PyObject)var1.getlocal(20).__getattr__("write"));
                  var1.getlocal(0).__setattr__("_stderr_thread", var3);
                  var3 = null;
                  var1.setline(928);
                  var1.getlocal(0).__getattr__("_stderr_thread").__getattr__("start").__call__(var2);
               }
            }
         }

         var1.setline(930);
         var3 = var1.getlocal(16);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(931);
            var3 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, var1.getlocal(16), (PyObject)PyString.fromInterned("wb"), (PyObject)var1.getlocal(2));
            var1.getlocal(0).__setattr__("stdin", var3);
            var3 = null;
         }

         var1.setline(932);
         var3 = var1.getlocal(17);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(933);
            if (var1.getlocal(12).__nonzero__()) {
               var1.setline(934);
               var3 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, var1.getlocal(17), (PyObject)PyString.fromInterned("rU"), (PyObject)var1.getlocal(2));
               var1.getlocal(0).__setattr__("stdout", var3);
               var3 = null;
            } else {
               var1.setline(936);
               var3 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, var1.getlocal(17), (PyObject)PyString.fromInterned("rb"), (PyObject)var1.getlocal(2));
               var1.getlocal(0).__setattr__("stdout", var3);
               var3 = null;
            }
         }

         var1.setline(937);
         var3 = var1.getlocal(19);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(938);
            if (var1.getlocal(12).__nonzero__()) {
               var1.setline(939);
               var3 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, var1.getlocal(19), (PyObject)PyString.fromInterned("rU"), (PyObject)var1.getlocal(2));
               var1.getlocal(0).__setattr__("stderr", var3);
               var3 = null;
            } else {
               var1.setline(941);
               var3 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, var1.getlocal(19), (PyObject)PyString.fromInterned("rb"), (PyObject)var1.getlocal(2));
               var1.getlocal(0).__setattr__("stderr", var3);
               var3 = null;
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _translate_newlines$24(PyFrame var1, ThreadState var2) {
      var1.setline(945);
      PyObject var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"), (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(946);
      var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r"), (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(947);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __del__$25(PyFrame var1, ThreadState var2) {
      var1.setline(951);
      if (var1.getlocal(0).__getattr__("_child_created").__not__().__nonzero__()) {
         var1.setline(953);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(955);
         PyObject var10000 = var1.getlocal(0).__getattr__("_internal_poll");
         PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
         String[] var4 = new String[]{"_deadstate"};
         var10000.__call__(var2, var3, var4);
         var3 = null;
         var1.setline(956);
         PyObject var5 = var1.getlocal(0).__getattr__("returncode");
         var10000 = var5._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(2);
            var10000 = var5._isnot(var1.getglobal("None"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(958);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject communicate$26(PyFrame var1, ThreadState var2) {
      var1.setline(968);
      PyString.fromInterned("Interact with process: Send data to stdin.  Read data from\n        stdout and stderr, until end-of-file is reached.  Wait for\n        process to terminate.  The optional input argument should be a\n        string to be sent to the child process, or None, if no data\n        should be sent to the child.\n\n        communicate() returns a tuple (stdout, stderr).");
      var1.setline(972);
      PyObject var3 = (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("stdin"), var1.getlocal(0).__getattr__("stdout"), var1.getlocal(0).__getattr__("stderr")})).__getattr__("count").__call__(var2, var1.getglobal("None"));
      PyObject var10000 = var3._ge(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(973);
         var3 = var1.getglobal("None");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(974);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(975);
         if (var1.getlocal(0).__getattr__("stdin").__nonzero__()) {
            var1.setline(976);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(977);
               var1.getlocal(0).__getattr__("stdin").__getattr__("write").__call__(var2, var1.getlocal(1));
            }

            var1.setline(978);
            var1.getlocal(0).__getattr__("stdin").__getattr__("close").__call__(var2);
         } else {
            var1.setline(979);
            if (var1.getlocal(0).__getattr__("stdout").__nonzero__()) {
               var1.setline(980);
               var3 = var1.getlocal(0).__getattr__("stdout").__getattr__("read").__call__(var2);
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(981);
               var1.getlocal(0).__getattr__("stdout").__getattr__("close").__call__(var2);
            } else {
               var1.setline(982);
               if (var1.getlocal(0).__getattr__("stderr").__nonzero__()) {
                  var1.setline(983);
                  var3 = var1.getlocal(0).__getattr__("stderr").__getattr__("read").__call__(var2);
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(984);
                  var1.getlocal(0).__getattr__("stderr").__getattr__("close").__call__(var2);
               }
            }
         }

         var1.setline(985);
         var1.getlocal(0).__getattr__("wait").__call__(var2);
         var1.setline(986);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(988);
         var3 = var1.getlocal(0).__getattr__("_communicate").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject poll$27(PyFrame var1, ThreadState var2) {
      var1.setline(992);
      PyObject var3 = var1.getlocal(0).__getattr__("_internal_poll").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _readerthread$28(PyFrame var1, ThreadState var2) {
      var1.setline(1000);
      var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("read").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _communicate$29(PyFrame var1, ThreadState var2) {
      var1.setline(1004);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1005);
      var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1007);
      PyObject var10000;
      String[] var4;
      PyList var5;
      PyObject[] var6;
      if (var1.getlocal(0).__getattr__("stdout").__nonzero__()) {
         var1.setline(1008);
         var5 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var5);
         var3 = null;
         var1.setline(1009);
         var10000 = var1.getglobal("threading").__getattr__("Thread");
         var6 = new PyObject[]{var1.getlocal(0).__getattr__("_readerthread"), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("stdout"), var1.getlocal(2)})};
         var4 = new String[]{"target", "args"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1011);
         var1.getlocal(4).__getattr__("setDaemon").__call__(var2, var1.getglobal("True"));
         var1.setline(1012);
         var1.getlocal(4).__getattr__("start").__call__(var2);
      }

      var1.setline(1013);
      if (var1.getlocal(0).__getattr__("stderr").__nonzero__()) {
         var1.setline(1014);
         var5 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(1015);
         var10000 = var1.getglobal("threading").__getattr__("Thread");
         var6 = new PyObject[]{var1.getlocal(0).__getattr__("_readerthread"), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("stderr"), var1.getlocal(3)})};
         var4 = new String[]{"target", "args"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1017);
         var1.getlocal(5).__getattr__("setDaemon").__call__(var2, var1.getglobal("True"));
         var1.setline(1018);
         var1.getlocal(5).__getattr__("start").__call__(var2);
      }

      var1.setline(1020);
      if (var1.getlocal(0).__getattr__("stdin").__nonzero__()) {
         var1.setline(1021);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1022);
            var1.getlocal(0).__getattr__("stdin").__getattr__("write").__call__(var2, var1.getlocal(1));
         }

         var1.setline(1023);
         var1.getlocal(0).__getattr__("stdin").__getattr__("close").__call__(var2);
      }

      var1.setline(1025);
      if (var1.getlocal(0).__getattr__("stdout").__nonzero__()) {
         var1.setline(1026);
         var1.getlocal(4).__getattr__("join").__call__(var2);
      }

      var1.setline(1027);
      if (var1.getlocal(0).__getattr__("stderr").__nonzero__()) {
         var1.setline(1028);
         var1.getlocal(5).__getattr__("join").__call__(var2);
      }

      var1.setline(1031);
      var3 = var1.getlocal(2);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1032);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1033);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1034);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1040);
      var10000 = var1.getlocal(0).__getattr__("universal_newlines");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("file"), (PyObject)PyString.fromInterned("newlines"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(1041);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(1042);
            var3 = var1.getlocal(0).__getattr__("_translate_newlines").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(1043);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1044);
            var3 = var1.getlocal(0).__getattr__("_translate_newlines").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(1046);
      var1.getlocal(0).__getattr__("wait").__call__(var2);
      var1.setline(1047);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject _get_handles$30(PyFrame var1, ThreadState var2) {
      var1.setline(1057);
      PyString.fromInterned("Construct and return tuple with IO objects:\n            p2cread, p2cwrite, c2pread, c2pwrite, errread, errwrite\n            ");
      var1.setline(1058);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
         }
      }

      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(1059);
         var7 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(1061);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var4 = null;
         var1.setline(1062);
         var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(7, var6);
         var6 = null;
         var4 = null;
         var1.setline(1063);
         var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(8, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(9, var6);
         var6 = null;
         var4 = null;
         var1.setline(1065);
         PyObject var8 = var1.getlocal(1);
         var10000 = var8._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1066);
            var8 = var1.getglobal("_subprocess").__getattr__("GetStdHandle").__call__(var2, var1.getglobal("_subprocess").__getattr__("STD_INPUT_HANDLE"));
            var1.setlocal(4, var8);
            var4 = null;
            var1.setline(1067);
            var8 = var1.getlocal(4);
            var10000 = var8._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1068);
               var8 = var1.getglobal("_subprocess").__getattr__("CreatePipe").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(0));
               var5 = Py.unpackSequence(var8, 2);
               var6 = var5[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var4 = null;
            }
         } else {
            var1.setline(1069);
            var8 = var1.getlocal(1);
            var10000 = var8._eq(var1.getglobal("PIPE"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1070);
               var8 = var1.getglobal("_subprocess").__getattr__("CreatePipe").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(0));
               var5 = Py.unpackSequence(var8, 2);
               var6 = var5[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(5, var6);
               var6 = null;
               var4 = null;
            } else {
               var1.setline(1071);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("int")).__nonzero__()) {
                  var1.setline(1072);
                  var8 = var1.getglobal("msvcrt").__getattr__("get_osfhandle").__call__(var2, var1.getlocal(1));
                  var1.setlocal(4, var8);
                  var4 = null;
               } else {
                  var1.setline(1075);
                  var8 = var1.getglobal("msvcrt").__getattr__("get_osfhandle").__call__(var2, var1.getlocal(1).__getattr__("fileno").__call__(var2));
                  var1.setlocal(4, var8);
                  var4 = null;
               }
            }
         }

         var1.setline(1076);
         var8 = var1.getlocal(0).__getattr__("_make_inheritable").__call__(var2, var1.getlocal(4));
         var1.setlocal(4, var8);
         var4 = null;
         var1.setline(1078);
         var8 = var1.getlocal(2);
         var10000 = var8._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1079);
            var8 = var1.getglobal("_subprocess").__getattr__("GetStdHandle").__call__(var2, var1.getglobal("_subprocess").__getattr__("STD_OUTPUT_HANDLE"));
            var1.setlocal(7, var8);
            var4 = null;
            var1.setline(1080);
            var8 = var1.getlocal(7);
            var10000 = var8._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1081);
               var8 = var1.getglobal("_subprocess").__getattr__("CreatePipe").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(0));
               var5 = Py.unpackSequence(var8, 2);
               var6 = var5[0];
               var1.setlocal(10, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(7, var6);
               var6 = null;
               var4 = null;
            }
         } else {
            var1.setline(1082);
            var8 = var1.getlocal(2);
            var10000 = var8._eq(var1.getglobal("PIPE"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1083);
               var8 = var1.getglobal("_subprocess").__getattr__("CreatePipe").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(0));
               var5 = Py.unpackSequence(var8, 2);
               var6 = var5[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(7, var6);
               var6 = null;
               var4 = null;
            } else {
               var1.setline(1084);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("int")).__nonzero__()) {
                  var1.setline(1085);
                  var8 = var1.getglobal("msvcrt").__getattr__("get_osfhandle").__call__(var2, var1.getlocal(2));
                  var1.setlocal(7, var8);
                  var4 = null;
               } else {
                  var1.setline(1088);
                  var8 = var1.getglobal("msvcrt").__getattr__("get_osfhandle").__call__(var2, var1.getlocal(2).__getattr__("fileno").__call__(var2));
                  var1.setlocal(7, var8);
                  var4 = null;
               }
            }
         }

         var1.setline(1089);
         var8 = var1.getlocal(0).__getattr__("_make_inheritable").__call__(var2, var1.getlocal(7));
         var1.setlocal(7, var8);
         var4 = null;
         var1.setline(1091);
         var8 = var1.getlocal(3);
         var10000 = var8._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1092);
            var8 = var1.getglobal("_subprocess").__getattr__("GetStdHandle").__call__(var2, var1.getglobal("_subprocess").__getattr__("STD_ERROR_HANDLE"));
            var1.setlocal(9, var8);
            var4 = null;
            var1.setline(1093);
            var8 = var1.getlocal(9);
            var10000 = var8._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1094);
               var8 = var1.getglobal("_subprocess").__getattr__("CreatePipe").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(0));
               var5 = Py.unpackSequence(var8, 2);
               var6 = var5[0];
               var1.setlocal(10, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(9, var6);
               var6 = null;
               var4 = null;
            }
         } else {
            var1.setline(1095);
            var8 = var1.getlocal(3);
            var10000 = var8._eq(var1.getglobal("PIPE"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1096);
               var8 = var1.getglobal("_subprocess").__getattr__("CreatePipe").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(0));
               var5 = Py.unpackSequence(var8, 2);
               var6 = var5[0];
               var1.setlocal(8, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(9, var6);
               var6 = null;
               var4 = null;
            } else {
               var1.setline(1097);
               var8 = var1.getlocal(3);
               var10000 = var8._eq(var1.getglobal("STDOUT"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1098);
                  var8 = var1.getlocal(7);
                  var1.setlocal(9, var8);
                  var4 = null;
               } else {
                  var1.setline(1099);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("int")).__nonzero__()) {
                     var1.setline(1100);
                     var8 = var1.getglobal("msvcrt").__getattr__("get_osfhandle").__call__(var2, var1.getlocal(3));
                     var1.setlocal(9, var8);
                     var4 = null;
                  } else {
                     var1.setline(1103);
                     var8 = var1.getglobal("msvcrt").__getattr__("get_osfhandle").__call__(var2, var1.getlocal(3).__getattr__("fileno").__call__(var2));
                     var1.setlocal(9, var8);
                     var4 = null;
                  }
               }
            }
         }

         var1.setline(1104);
         var8 = var1.getlocal(0).__getattr__("_make_inheritable").__call__(var2, var1.getlocal(9));
         var1.setlocal(9, var8);
         var4 = null;
         var1.setline(1106);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9)});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject _make_inheritable$31(PyFrame var1, ThreadState var2) {
      var1.setline(1112);
      PyString.fromInterned("Return a duplicate of handle, which is inheritable");
      var1.setline(1113);
      PyObject var10000 = var1.getglobal("_subprocess").__getattr__("DuplicateHandle");
      PyObject[] var3 = new PyObject[]{var1.getglobal("_subprocess").__getattr__("GetCurrentProcess").__call__(var2), var1.getlocal(1), var1.getglobal("_subprocess").__getattr__("GetCurrentProcess").__call__(var2), Py.newInteger(0), Py.newInteger(1), var1.getglobal("_subprocess").__getattr__("DUPLICATE_SAME_ACCESS")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _find_w9xpopen$32(PyFrame var1, ThreadState var2) {
      var1.setline(1119);
      PyString.fromInterned("Find and return absolut path to w9xpopen.exe");
      var1.setline(1120);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("_subprocess").__getattr__("GetModuleFileName").__call__((ThreadState)var2, (PyObject)Py.newInteger(0))), (PyObject)PyString.fromInterned("w9xpopen.exe"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1123);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(1126);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("sys").__getattr__("exec_prefix")), (PyObject)PyString.fromInterned("w9xpopen.exe"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1128);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(1129);
            throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot locate w9xpopen.exe, which is needed for Popen to work with your shell or platform.")));
         }
      }

      var1.setline(1132);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _execute_child$33(PyFrame var1, ThreadState var2) {
      var1.setline(1141);
      PyString.fromInterned("Execute program (MS Windows version)");
      var1.setline(1143);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("types").__getattr__("StringTypes")).__not__().__nonzero__()) {
         var1.setline(1144);
         var3 = var1.getglobal("list2cmdline").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1147);
      var3 = var1.getlocal(8);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1148);
         var3 = var1.getglobal("STARTUPINFO").__call__(var2);
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(1149);
      var3 = var1.getglobal("None");
      var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(14), var1.getlocal(16)}));
      var3 = null;
      PyObject var4;
      PyObject var5;
      String var9;
      if (var10000.__nonzero__()) {
         var1.setline(1150);
         var10000 = var1.getlocal(8);
         var9 = "dwFlags";
         var4 = var10000;
         var5 = var4.__getattr__(var9);
         var5 = var5._ior(var1.getglobal("_subprocess").__getattr__("STARTF_USESTDHANDLES"));
         var4.__setattr__(var9, var5);
         var1.setline(1151);
         var3 = var1.getlocal(11);
         var1.getlocal(8).__setattr__("hStdInput", var3);
         var3 = null;
         var1.setline(1152);
         var3 = var1.getlocal(14);
         var1.getlocal(8).__setattr__("hStdOutput", var3);
         var3 = null;
         var1.setline(1153);
         var3 = var1.getlocal(16);
         var1.getlocal(8).__setattr__("hStdError", var3);
         var3 = null;
      }

      var1.setline(1155);
      if (var1.getlocal(10).__nonzero__()) {
         var1.setline(1156);
         var10000 = var1.getlocal(8);
         var9 = "dwFlags";
         var4 = var10000;
         var5 = var4.__getattr__(var9);
         var5 = var5._ior(var1.getglobal("_subprocess").__getattr__("STARTF_USESHOWWINDOW"));
         var4.__setattr__(var9, var5);
         var1.setline(1157);
         var3 = var1.getglobal("_subprocess").__getattr__("SW_HIDE");
         var1.getlocal(8).__setattr__("wShowWindow", var3);
         var3 = null;
         var1.setline(1158);
         var3 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("COMSPEC"), (PyObject)PyString.fromInterned("cmd.exe"));
         var1.setlocal(17, var3);
         var3 = null;
         var1.setline(1159);
         var3 = PyString.fromInterned("{} /c \"{}\"").__getattr__("format").__call__(var2, var1.getlocal(17), var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1160);
         var3 = var1.getglobal("_subprocess").__getattr__("GetVersion").__call__(var2);
         var10000 = var3._ge(Py.newLong("2147483648"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(17)).__getattr__("lower").__call__(var2);
            var10000 = var3._eq(PyString.fromInterned("command.com"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1166);
            var3 = var1.getlocal(0).__getattr__("_find_w9xpopen").__call__(var2);
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(1167);
            var3 = PyString.fromInterned("\"%s\" %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(1)}));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(1174);
            var3 = var1.getlocal(9);
            var3 = var3._ior(var1.getglobal("_subprocess").__getattr__("CREATE_NEW_CONSOLE"));
            var1.setlocal(9, var3);
         }
      }

      var3 = null;

      try {
         String[] var6;
         PyObject[] var13;
         try {
            var1.setline(1178);
            var10000 = var1.getglobal("_subprocess").__getattr__("CreateProcess");
            PyObject[] var12 = new PyObject[]{var1.getlocal(2), var1.getlocal(1), var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("int").__call__(var2, var1.getlocal(4).__not__()), var1.getlocal(9), var1.getlocal(6), var1.getlocal(5), var1.getlocal(8)};
            var4 = var10000.__call__(var2, var12);
            var13 = Py.unpackSequence(var4, 4);
            PyObject var10 = var13[0];
            var1.setlocal(19, var10);
            var6 = null;
            var10 = var13[1];
            var1.setlocal(20, var10);
            var6 = null;
            var10 = var13[2];
            var1.setlocal(21, var10);
            var6 = null;
            var10 = var13[3];
            var1.setlocal(22, var10);
            var6 = null;
            var4 = null;
         } catch (Throwable var7) {
            PyException var11 = Py.setException(var7, var1);
            if (var11.match(var1.getglobal("pywintypes").__getattr__("error"))) {
               var5 = var11.value;
               var1.setlocal(23, var5);
               var5 = null;
               var1.setline(1191);
               var10000 = var1.getglobal("WindowsError");
               var13 = Py.EmptyObjects;
               var6 = new String[0];
               var10000 = var10000._callextra(var13, var6, var1.getlocal(23).__getattr__("args"), (PyObject)null);
               var5 = null;
               throw Py.makeException(var10000);
            }

            throw var11;
         }
      } catch (Throwable var8) {
         Py.addTraceback(var8, var1);
         var1.setline(1199);
         var4 = var1.getlocal(11);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1200);
            var1.getlocal(11).__getattr__("Close").__call__(var2);
         }

         var1.setline(1201);
         var4 = var1.getlocal(14);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1202);
            var1.getlocal(14).__getattr__("Close").__call__(var2);
         }

         var1.setline(1203);
         var4 = var1.getlocal(16);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1204);
            var1.getlocal(16).__getattr__("Close").__call__(var2);
         }

         throw (Throwable)var8;
      }

      var1.setline(1199);
      var4 = var1.getlocal(11);
      var10000 = var4._isnot(var1.getglobal("None"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1200);
         var1.getlocal(11).__getattr__("Close").__call__(var2);
      }

      var1.setline(1201);
      var4 = var1.getlocal(14);
      var10000 = var4._isnot(var1.getglobal("None"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1202);
         var1.getlocal(14).__getattr__("Close").__call__(var2);
      }

      var1.setline(1203);
      var4 = var1.getlocal(16);
      var10000 = var4._isnot(var1.getglobal("None"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1204);
         var1.getlocal(16).__getattr__("Close").__call__(var2);
      }

      var1.setline(1207);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_child_created", var3);
      var3 = null;
      var1.setline(1208);
      var3 = var1.getlocal(19);
      var1.getlocal(0).__setattr__("_handle", var3);
      var3 = null;
      var1.setline(1209);
      var3 = var1.getlocal(21);
      var1.getlocal(0).__setattr__("pid", var3);
      var3 = null;
      var1.setline(1210);
      var1.getlocal(20).__getattr__("Close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _internal_poll$34(PyFrame var1, ThreadState var2) {
      var1.setline(1222);
      PyString.fromInterned("Check if child process has terminated.  Returns returncode\n            attribute.\n\n            This method is called by __del__, so it can only refer to objects\n            in its local scope.\n\n            ");
      var1.setline(1223);
      PyObject var3 = var1.getlocal(0).__getattr__("returncode");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1224);
         var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_handle"), (PyObject)Py.newInteger(0));
         var10000 = var3._eq(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1225);
            var3 = var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("_handle"));
            var1.getlocal(0).__setattr__("returncode", var3);
            var3 = null;
         }
      }

      var1.setline(1226);
      var3 = var1.getlocal(0).__getattr__("returncode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject wait$35(PyFrame var1, ThreadState var2) {
      var1.setline(1231);
      PyString.fromInterned("Wait for child process to terminate.  Returns returncode\n            attribute.");
      var1.setline(1232);
      PyObject var3 = var1.getlocal(0).__getattr__("returncode");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1233);
         var1.getglobal("_subprocess").__getattr__("WaitForSingleObject").__call__(var2, var1.getlocal(0).__getattr__("_handle"), var1.getglobal("_subprocess").__getattr__("INFINITE"));
         var1.setline(1235);
         var3 = var1.getglobal("_subprocess").__getattr__("GetExitCodeProcess").__call__(var2, var1.getlocal(0).__getattr__("_handle"));
         var1.getlocal(0).__setattr__("returncode", var3);
         var3 = null;
      }

      var1.setline(1236);
      var3 = var1.getlocal(0).__getattr__("returncode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_handles$36(PyFrame var1, ThreadState var2) {
      var1.setline(1245);
      PyString.fromInterned("Construct and return tuple with IO objects:\n            p2cread, p2cwrite, c2pread, c2pwrite, errread, errwrite\n            ");
      var1.setline(1246);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1247);
      var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(1248);
      var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(1250);
      PyObject var6 = var1.getlocal(1);
      PyObject var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1251);
      } else {
         var1.setline(1252);
         var6 = var1.getlocal(1);
         var10000 = var6._eq(var1.getglobal("PIPE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1253);
            var6 = var1.getglobal("PIPE");
            var1.setlocal(5, var6);
            var3 = null;
         } else {
            var1.setline(1254);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("org").__getattr__("python").__getattr__("core").__getattr__("io").__getattr__("RawIOBase")).__nonzero__()) {
               var1.setline(1255);
               var6 = var1.getlocal(1);
               var1.setlocal(4, var6);
               var3 = null;
            } else {
               var1.setline(1258);
               var6 = var1.getlocal(1).__getattr__("fileno").__call__(var2);
               var1.setlocal(4, var6);
               var3 = null;
            }
         }
      }

      var1.setline(1260);
      var6 = var1.getlocal(2);
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1261);
      } else {
         var1.setline(1262);
         var6 = var1.getlocal(2);
         var10000 = var6._eq(var1.getglobal("PIPE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1263);
            var6 = var1.getglobal("PIPE");
            var1.setlocal(6, var6);
            var3 = null;
         } else {
            var1.setline(1264);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("org").__getattr__("python").__getattr__("core").__getattr__("io").__getattr__("RawIOBase")).__nonzero__()) {
               var1.setline(1265);
               var6 = var1.getlocal(2);
               var1.setlocal(7, var6);
               var3 = null;
            } else {
               var1.setline(1268);
               var6 = var1.getlocal(2).__getattr__("fileno").__call__(var2);
               var1.setlocal(7, var6);
               var3 = null;
            }
         }
      }

      var1.setline(1270);
      var6 = var1.getlocal(3);
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1271);
      } else {
         var1.setline(1272);
         var6 = var1.getlocal(3);
         var10000 = var6._eq(var1.getglobal("PIPE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1273);
            var6 = var1.getglobal("PIPE");
            var1.setlocal(8, var6);
            var3 = null;
         } else {
            var1.setline(1274);
            var6 = var1.getlocal(3);
            var10000 = var6._eq(var1.getglobal("STDOUT"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("org").__getattr__("python").__getattr__("core").__getattr__("io").__getattr__("RawIOBase"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(1276);
               var6 = var1.getlocal(3);
               var1.setlocal(9, var6);
               var3 = null;
            } else {
               var1.setline(1279);
               var6 = var1.getlocal(3).__getattr__("fileno").__call__(var2);
               var1.setlocal(9, var6);
               var3 = null;
            }
         }
      }

      var1.setline(1281);
      var3 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _stderr_is_stdout$37(PyFrame var1, ThreadState var2) {
      var1.setline(1289);
      PyString.fromInterned("Determine if the subprocess' stderr should be redirected\n            to stdout\n            ");
      var1.setline(1290);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getglobal("STDOUT"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("PIPE")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(2);
            var10000 = var3._is(var1.getlocal(1));
            var3 = null;
         }
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _coupler_thread$38(PyFrame var1, ThreadState var2) {
      var1.setline(1295);
      PyString.fromInterned("Return a _CouplerThread");
      var1.setline(1296);
      PyObject var10000 = var1.getglobal("_CouplerThread");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _execute_child$39(PyFrame var1, ThreadState var2) {
      var1.setline(1308);
      PyString.fromInterned("Execute program (Java version)");
      var1.setline(1310);
      PyObject var3;
      PyFunction var4;
      PyObject var10000;
      PyObject var10002;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("types").__getattr__("StringTypes")).__nonzero__()) {
         var1.setline(1311);
         var3 = var1.getglobal("_cmdline2listimpl").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(1313);
         var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1314);
         var10000 = var1.getglobal("any");
         var1.setline(1314);
         PyObject[] var6 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var6, f$40, (PyObject)null);
         var10002 = var4.__call__(var2, var1.getlocal(1).__iter__());
         Arrays.fill(var6, (Object)null);
         if (var10000.__call__(var2, var10002).__nonzero__()) {
            var1.setline(1315);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("args must contain only strings")));
         }
      }

      var1.setline(1316);
      var3 = var1.getglobal("_escape_args").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1318);
      if (var1.getlocal(10).__nonzero__()) {
         var1.setline(1319);
         var3 = var1.getglobal("_shell_command")._add(var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1321);
      var3 = var1.getlocal(2);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1322);
         var3 = var1.getlocal(2);
         var1.getlocal(1).__setitem__((PyObject)Py.newInteger(0), var3);
         var3 = null;
      }

      var1.setline(1328);
      PyList var11 = new PyList();
      var3 = var11.__getattr__("append");
      var1.setlocal(21, var3);
      var3 = null;
      var1.setline(1328);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1328);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(1328);
            var1.dellocal(21);
            PyList var9 = var11;
            var1.setlocal(1, var9);
            var3 = null;
            var1.setline(1329);
            var3 = var1.getglobal("java").__getattr__("lang").__getattr__("ProcessBuilder").__call__(var2, var1.getlocal(1));
            var1.setlocal(23, var3);
            var3 = null;
            var1.setline(1331);
            var3 = var1.getlocal(17);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1332);
               var1.getlocal(23).__getattr__("redirectInput").__call__(var2, var1.getglobal("java").__getattr__("lang").__getattr__("ProcessBuilder").__getattr__("Redirect").__getattr__("INHERIT"));
            }

            var1.setline(1333);
            var3 = var1.getlocal(18);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1334);
               var1.getlocal(23).__getattr__("redirectOutput").__call__(var2, var1.getglobal("java").__getattr__("lang").__getattr__("ProcessBuilder").__getattr__("Redirect").__getattr__("INHERIT"));
            }

            var1.setline(1335);
            var3 = var1.getlocal(19);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1336);
               var1.getlocal(23).__getattr__("redirectError").__call__(var2, var1.getglobal("java").__getattr__("lang").__getattr__("ProcessBuilder").__getattr__("Redirect").__getattr__("INHERIT"));
            }

            var1.setline(1340);
            var10000 = var1.getglobal("_setup_env");
            var10002 = var1.getglobal("dict");
            var1.setline(1340);
            var3 = var1.getlocal(6);
            PyObject var10004 = var3._is(var1.getglobal("None"));
            var3 = null;
            var10000.__call__(var2, var10002.__call__(var2, var10004.__nonzero__() ? var1.getglobal("os").__getattr__("environ") : var1.getlocal(6)), var1.getlocal(23).__getattr__("environment").__call__(var2));
            var1.setline(1344);
            var3 = var1.getlocal(5);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1345);
               var3 = var1.getglobal("os").__getattr__("getcwdu").__call__(var2);
               var1.setlocal(5, var3);
               var3 = null;
            } else {
               var1.setline(1347);
               var3 = var1.getglobal("fileSystemDecode").__call__(var2, var1.getlocal(5));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(1348);
               if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(5)).__not__().__nonzero__()) {
                  var1.setline(1349);
                  throw Py.makeException(var1.getglobal("OSError").__call__(var2, var1.getglobal("errno").__getattr__("ENOENT"), var1.getglobal("os").__getattr__("strerror").__call__(var2, var1.getglobal("errno").__getattr__("ENOENT")), var1.getlocal(5)));
               }

               var1.setline(1350);
               if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(5)).__not__().__nonzero__()) {
                  var1.setline(1351);
                  throw Py.makeException(var1.getglobal("OSError").__call__(var2, var1.getglobal("errno").__getattr__("ENOTDIR"), var1.getglobal("os").__getattr__("strerror").__call__(var2, var1.getglobal("errno").__getattr__("ENOTDIR")), var1.getlocal(5)));
               }
            }

            var1.setline(1352);
            var1.getlocal(23).__getattr__("directory").__call__(var2, var1.getglobal("java").__getattr__("io").__getattr__("File").__call__(var2, var1.getlocal(5)));
            var1.setline(1358);
            if (var1.getlocal(0).__getattr__("_stderr_is_stdout").__call__(var2, var1.getlocal(16), var1.getlocal(14)).__nonzero__()) {
               var1.setline(1359);
               var1.getlocal(23).__getattr__("redirectErrorStream").__call__(var2, var1.getglobal("True"));
            }

            try {
               var1.setline(1362);
               var3 = var1.getlocal(23).__getattr__("start").__call__(var2);
               var1.getlocal(0).__setattr__("_process", var3);
               var3 = null;
            } catch (Throwable var5) {
               PyException var10 = Py.setException(var5, var1);
               if (var10.match(new PyTuple(new PyObject[]{var1.getglobal("java").__getattr__("io").__getattr__("IOException"), var1.getglobal("java").__getattr__("lang").__getattr__("IllegalArgumentException")}))) {
                  var7 = var10.value;
                  var1.setlocal(24, var7);
                  var4 = null;
                  var1.setline(1365);
                  var7 = var1.getlocal(24).__getattr__("getMessage").__call__(var2);
                  var1.setlocal(25, var7);
                  var4 = null;
                  var1.setline(1368);
                  var10000 = var1.getlocal(25);
                  if (var10000.__nonzero__()) {
                     PyString var8 = PyString.fromInterned("error=2,");
                     var10000 = var8._in(var1.getlocal(25));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1369);
                     throw Py.makeException(var1.getglobal("OSError").__call__(var2, var1.getglobal("errno").__getattr__("ENOENT"), var1.getglobal("os").__getattr__("strerror").__call__(var2, var1.getglobal("errno").__getattr__("ENOENT"))));
                  }

                  var1.setline(1370);
                  var10000 = var1.getglobal("OSError");
                  var10002 = var1.getlocal(25);
                  if (!var10002.__nonzero__()) {
                     var10002 = var1.getlocal(24);
                  }

                  throw Py.makeException(var10000.__call__(var2, var10002));
               }

               throw var10;
            }

            var1.setline(1372);
            var3 = var1.getlocal(0).__getattr__("_get_pid").__call__(var2);
            var1.getlocal(0).__setattr__("pid", var3);
            var3 = null;
            var1.setline(1373);
            var3 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("_child_created", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(22, var7);
         var1.setline(1328);
         var1.getlocal(21).__call__(var2, var1.getglobal("fileSystemDecode").__call__(var2, var1.getlocal(22)));
      }
   }

   public PyObject f$40(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1314);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
      }

      var1.setline(1314);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(1314);
         var1.setline(1314);
         var7 = var1.getglobal("isinstance");
         PyObject var10002 = var1.getlocal(1);
         PyObject[] var6 = new PyObject[]{var1.getglobal("str"), var1.getglobal("unicode")};
         PyTuple var10003 = new PyTuple(var6);
         Arrays.fill(var6, (Object)null);
         var7 = var7.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003).__not__();
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4, null};
         var1.f_savedlocals = var5;
         return var7;
      }
   }

   public PyObject _get_private_field$41(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      try {
         var1.setline(1380);
         PyObject var6 = var1.getlocal(1).__getattr__("getClass").__call__(var2).__getattr__("getDeclaredField").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(1381);
         var1.getlocal(3).__getattr__("setAccessible").__call__(var2, var1.getglobal("True"));
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("java").__getattr__("lang").__getattr__("NoSuchFieldException"), var1.getglobal("java").__getattr__("lang").__getattr__("SecurityException")}))) {
            var1.setline(1384);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(1386);
      var4 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _get_pid$42(PyFrame var1, ThreadState var2) {
      var1.setline(1391);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_private_field").__call__(var2, var1.getlocal(0).__getattr__("_process"), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1392);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1393);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1394);
         var3 = var1.getlocal(2).__getattr__("getInt").__call__(var2, var1.getlocal(0).__getattr__("_process"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _get_pid$43(PyFrame var1, ThreadState var2) {
      var1.setline(1403);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_private_field").__call__(var2, var1.getlocal(0).__getattr__("_process"), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1404);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1405);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1406);
         var3 = var1.getlocal(0).__getattr__("_handle_to_pid").__call__(var2, var1.getlocal(2).__getattr__("getLong").__call__(var2, var1.getlocal(0).__getattr__("_process")));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject poll$44(PyFrame var1, ThreadState var2) {
      var1.setline(1411);
      PyString.fromInterned("Check if child process has terminated.  Returns returncode\n            attribute.");
      var1.setline(1412);
      PyObject var3 = var1.getlocal(0).__getattr__("returncode");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(1414);
            var3 = var1.getlocal(0).__getattr__("_process").__getattr__("exitValue").__call__(var2);
            var1.getlocal(0).__setattr__("returncode", var3);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (!var5.match(var1.getglobal("java").__getattr__("lang").__getattr__("IllegalThreadStateException"))) {
               throw var5;
            }

            var1.setline(1416);
         }
      }

      var1.setline(1417);
      var3 = var1.getlocal(0).__getattr__("returncode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _internal_poll$45(PyFrame var1, ThreadState var2) {
      var1.setline(1421);
      PyString.fromInterned("Check if child process has terminated.  Returns returncode\n            attribute. Called by __del__.");
      var1.setline(1422);
      PyObject var3 = var1.getlocal(0).__getattr__("returncode");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(1424);
            var3 = var1.getlocal(0).__getattr__("_process").__getattr__("exitValue").__call__(var2);
            var1.getlocal(0).__setattr__("returncode", var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("java").__getattr__("lang").__getattr__("IllegalThreadStateException"))) {
               var1.setline(1427);
            } else {
               if (!var6.match(new PyTuple(new PyObject[]{var1.getglobal("java").__getattr__("io").__getattr__("IOException"), var1.getglobal("AttributeError")}))) {
                  throw var6;
               }

               PyObject var4 = var6.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(1430);
               var4 = var1.getlocal(1);
               var1.getlocal(0).__setattr__("returncode", var4);
               var4 = null;
            }
         }
      }

      var1.setline(1431);
      var3 = var1.getlocal(0).__getattr__("returncode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject wait$46(PyFrame var1, ThreadState var2) {
      var1.setline(1435);
      PyString.fromInterned("Wait for child process to terminate.  Returns returncode\n            attribute.");
      var1.setline(1436);
      PyObject var3 = var1.getlocal(0).__getattr__("returncode");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1437);
         var3 = var1.getlocal(0).__getattr__("_process").__getattr__("waitFor").__call__(var2);
         var1.getlocal(0).__setattr__("returncode", var3);
         var3 = null;
      }

      var1.setline(1438);
      var3 = (new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_stdout_thread"), var1.getlocal(0).__getattr__("_stderr_thread")})).__iter__();

      while(true) {
         var1.setline(1438);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1441);
            if (var1.getlocal(0).__getattr__("_stdin_thread").__nonzero__()) {
               var1.setline(1444);
               var1.getlocal(0).__getattr__("_stdin_thread").__getattr__("interrupt").__call__(var2);
            }

            var1.setline(1445);
            var3 = var1.getlocal(0).__getattr__("returncode");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(1, var4);
         var1.setline(1439);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(1440);
            var1.getlocal(1).__getattr__("join").__call__(var2);
         }
      }
   }

   public PyObject terminate$47(PyFrame var1, ThreadState var2) {
      var1.setline(1449);
      PyString.fromInterned("Terminates the process\n            ");
      var1.setline(1450);
      var1.getlocal(0).__getattr__("_process").__getattr__("destroy").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject kill$48(PyFrame var1, ThreadState var2) {
      var1.setline(1455);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_process"), (PyObject)PyString.fromInterned("destroyForcibly")).__nonzero__()) {
         var1.setline(1456);
         var1.getlocal(0).__getattr__("_process").__getattr__("destroyForcibly").__call__(var2);
      } else {
         var1.setline(1458);
         var1.getlocal(0).__getattr__("send_signal").__call__(var2, var1.getglobal("signal").__getattr__("SIGKILL"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_signal$49(PyFrame var1, ThreadState var2) {
      var1.setline(1462);
      PyString.fromInterned("Send a signal to the process\n                ");
      var1.setline(1463);
      var1.getglobal("os").__getattr__("kill").__call__(var2, var1.getlocal(0).__getattr__("pid"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_signal$50(PyFrame var1, ThreadState var2) {
      var1.setline(1471);
      PyString.fromInterned("Send a signal to the process\n                ");
      var1.setline(1472);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getglobal("signal").__getattr__("SIGTERM"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1473);
         var1.getlocal(0).__getattr__("terminate").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1475);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unsupported signal: {}").__getattr__("format").__call__(var2, var1.getlocal(1))));
      }
   }

   public PyObject _get_handles$51(PyFrame var1, ThreadState var2) {
      var1.setline(1485);
      PyString.fromInterned("Construct and return tuple with IO objects:\n            p2cread, p2cwrite, c2pread, c2pwrite, errread, errwrite\n            ");
      var1.setline(1486);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1487);
      var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(1488);
      var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(1490);
      PyObject var6 = var1.getlocal(1);
      PyObject var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1491);
      } else {
         var1.setline(1492);
         var6 = var1.getlocal(1);
         var10000 = var6._eq(var1.getglobal("PIPE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1493);
            var6 = var1.getglobal("os").__getattr__("pipe").__call__(var2);
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(1494);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("int")).__nonzero__()) {
               var1.setline(1495);
               var6 = var1.getlocal(1);
               var1.setlocal(4, var6);
               var3 = null;
            } else {
               var1.setline(1498);
               var6 = var1.getlocal(1).__getattr__("fileno").__call__(var2);
               var1.setlocal(4, var6);
               var3 = null;
            }
         }
      }

      var1.setline(1500);
      var6 = var1.getlocal(2);
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1501);
      } else {
         var1.setline(1502);
         var6 = var1.getlocal(2);
         var10000 = var6._eq(var1.getglobal("PIPE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1503);
            var6 = var1.getglobal("os").__getattr__("pipe").__call__(var2);
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(1504);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("int")).__nonzero__()) {
               var1.setline(1505);
               var6 = var1.getlocal(2);
               var1.setlocal(7, var6);
               var3 = null;
            } else {
               var1.setline(1508);
               var6 = var1.getlocal(2).__getattr__("fileno").__call__(var2);
               var1.setlocal(7, var6);
               var3 = null;
            }
         }
      }

      var1.setline(1510);
      var6 = var1.getlocal(3);
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1511);
      } else {
         var1.setline(1512);
         var6 = var1.getlocal(3);
         var10000 = var6._eq(var1.getglobal("PIPE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1513);
            var6 = var1.getglobal("os").__getattr__("pipe").__call__(var2);
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(8, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(9, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(1514);
            var6 = var1.getlocal(3);
            var10000 = var6._eq(var1.getglobal("STDOUT"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1515);
               var6 = var1.getlocal(7);
               var1.setlocal(9, var6);
               var3 = null;
            } else {
               var1.setline(1516);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("int")).__nonzero__()) {
                  var1.setline(1517);
                  var6 = var1.getlocal(3);
                  var1.setlocal(9, var6);
                  var3 = null;
               } else {
                  var1.setline(1520);
                  var6 = var1.getlocal(3).__getattr__("fileno").__call__(var2);
                  var1.setlocal(9, var6);
                  var3 = null;
               }
            }
         }
      }

      var1.setline(1522);
      var3 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_cloexec_flag$52(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var6;
      try {
         var1.setline(1529);
         var6 = var1.getglobal("fcntl").__getattr__("FD_CLOEXEC");
         var1.setlocal(3, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(1531);
         PyInteger var4 = Py.newInteger(1);
         var1.setlocal(3, var4);
         var4 = null;
      }

      var1.setline(1533);
      var6 = var1.getglobal("fcntl").__getattr__("fcntl").__call__(var2, var1.getlocal(1), var1.getglobal("fcntl").__getattr__("F_GETFD"));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1534);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1535);
         var1.getglobal("fcntl").__getattr__("fcntl").__call__(var2, var1.getlocal(1), var1.getglobal("fcntl").__getattr__("F_SETFD"), var1.getlocal(4)._or(var1.getlocal(3)));
      } else {
         var1.setline(1537);
         var1.getglobal("fcntl").__getattr__("fcntl").__call__(var2, var1.getlocal(1), var1.getglobal("fcntl").__getattr__("F_SETFD"), var1.getlocal(4)._and(var1.getlocal(3).__invert__()));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _close_fds$53(PyFrame var1, ThreadState var2) {
      var1.setline(1541);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("closerange")).__nonzero__()) {
         var1.setline(1542);
         var1.getglobal("os").__getattr__("closerange").__call__((ThreadState)var2, (PyObject)Py.newInteger(3), (PyObject)var1.getlocal(1));
         var1.setline(1543);
         var1.getglobal("os").__getattr__("closerange").__call__(var2, var1.getlocal(1)._add(Py.newInteger(1)), var1.getglobal("MAXFD"));
      } else {
         var1.setline(1545);
         PyObject var3 = var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(3), (PyObject)var1.getglobal("MAXFD")).__iter__();

         while(true) {
            var1.setline(1545);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(1546);
            PyObject var5 = var1.getlocal(2);
            PyObject var10000 = var5._eq(var1.getlocal(1));
            var5 = null;
            if (!var10000.__nonzero__()) {
               try {
                  var1.setline(1549);
                  var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(2));
               } catch (Throwable var6) {
                  Py.setException(var6, var1);
                  var1.setline(1551);
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _execute_child$54(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(1560);
      PyString.fromInterned("Execute program (POSIX version)");
      var1.setline(1562);
      PyList var3;
      PyObject var14;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("types").__getattr__("StringTypes")).__nonzero__()) {
         var1.setline(1563);
         var3 = new PyList(new PyObject[]{var1.getlocal(1)});
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(1565);
         var14 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var14);
         var3 = null;
      }

      var1.setline(1567);
      if (var1.getlocal(10).__nonzero__()) {
         var1.setline(1568);
         var14 = (new PyList(new PyObject[]{PyString.fromInterned("/bin/sh"), PyString.fromInterned("-c")}))._add(var1.getlocal(1));
         var1.setlocal(1, var14);
         var3 = null;
         var1.setline(1569);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(1570);
            var14 = var1.getlocal(2);
            var1.getlocal(1).__setitem__((PyObject)Py.newInteger(0), var14);
            var3 = null;
         }
      }

      var1.setline(1572);
      var14 = var1.getlocal(2);
      PyObject var10000 = var14._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1573);
         var14 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(2, var14);
         var3 = null;
      }

      var1.setline(1578);
      var14 = var1.getglobal("os").__getattr__("pipe").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var14, 2);
      PyObject var5 = var4[0];
      var1.setlocal(17, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(18, var5);
      var5 = null;
      var3 = null;
      var3 = null;

      PyObject var15;
      try {
         var4 = null;

         try {
            var1.setline(1581);
            var1.getderef(0).__getattr__("_set_cloexec_flag").__call__(var2, var1.getlocal(18));
            var1.setline(1583);
            var5 = var1.getglobal("gc").__getattr__("isenabled").__call__(var2);
            var1.setlocal(19, var5);
            var5 = null;
            var1.setline(1586);
            var1.getglobal("gc").__getattr__("disable").__call__(var2);

            try {
               var1.setline(1588);
               var5 = var1.getglobal("os").__getattr__("fork").__call__(var2);
               var1.getderef(0).__setattr__("pid", var5);
               var5 = null;
            } catch (Throwable var9) {
               Py.setException(var9, var1);
               var1.setline(1590);
               if (var1.getlocal(19).__nonzero__()) {
                  var1.setline(1591);
                  var1.getglobal("gc").__getattr__("enable").__call__(var2);
               }

               var1.setline(1592);
               throw Py.makeException();
            }

            var1.setline(1593);
            var5 = var1.getglobal("True");
            var1.getderef(0).__setattr__("_child_created", var5);
            var5 = null;
            var1.setline(1594);
            var5 = var1.getderef(0).__getattr__("pid");
            var10000 = var5._eq(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               PyObject var6;
               PyObject[] var7;
               try {
                  var1.setline(1598);
                  var5 = var1.getlocal(12);
                  var10000 = var5._isnot(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1599);
                     var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(12));
                  }

                  var1.setline(1600);
                  var5 = var1.getlocal(13);
                  var10000 = var5._isnot(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1601);
                     var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(13));
                  }

                  var1.setline(1602);
                  var5 = var1.getlocal(15);
                  var10000 = var5._isnot(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1603);
                     var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(15));
                  }

                  var1.setline(1604);
                  var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(17));
                  var1.setline(1607);
                  PyObject[] var19 = Py.EmptyObjects;
                  PyObject var10002 = var1.f_globals;
                  PyObject[] var10003 = var19;
                  PyCode var10004 = _dup2$55;
                  var19 = new PyObject[]{var1.getclosure(0)};
                  PyFunction var20 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var19);
                  var1.setlocal(20, var20);
                  var5 = null;
                  var1.setline(1615);
                  var1.getlocal(20).__call__((ThreadState)var2, (PyObject)var1.getlocal(11), (PyObject)Py.newInteger(0));
                  var1.setline(1616);
                  var1.getlocal(20).__call__((ThreadState)var2, (PyObject)var1.getlocal(14), (PyObject)Py.newInteger(1));
                  var1.setline(1617);
                  var1.getlocal(20).__call__((ThreadState)var2, (PyObject)var1.getlocal(16), (PyObject)Py.newInteger(2));
                  var1.setline(1621);
                  PySet var21 = new PySet(new PyObject[]{var1.getglobal("None")});
                  var1.setlocal(21, var21);
                  var5 = null;
                  var1.setline(1622);
                  var5 = (new PyList(new PyObject[]{var1.getlocal(11), var1.getlocal(14), var1.getlocal(16)})).__iter__();

                  while(true) {
                     var1.setline(1622);
                     var6 = var5.__iternext__();
                     if (var6 == null) {
                        var1.setline(1628);
                        if (var1.getlocal(4).__nonzero__()) {
                           var1.setline(1629);
                           var10000 = var1.getderef(0).__getattr__("_close_fds");
                           var19 = new PyObject[]{var1.getlocal(18)};
                           String[] var17 = new String[]{"but"};
                           var10000.__call__(var2, var19, var17);
                           var5 = null;
                        }

                        var1.setline(1631);
                        var5 = var1.getlocal(5);
                        var10000 = var5._isnot(var1.getglobal("None"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1632);
                           var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(5));
                        }

                        var1.setline(1634);
                        if (var1.getlocal(3).__nonzero__()) {
                           var1.setline(1635);
                           var1.getlocal(3).__call__(var2);
                        }

                        var1.setline(1637);
                        var5 = var1.getlocal(6);
                        var10000 = var5._is(var1.getglobal("None"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1638);
                           var1.getglobal("os").__getattr__("execvp").__call__(var2, var1.getlocal(2), var1.getlocal(1));
                        } else {
                           var1.setline(1640);
                           var1.getglobal("os").__getattr__("execvpe").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(6));
                        }
                        break;
                     }

                     var1.setlocal(22, var6);
                     var1.setline(1623);
                     PyObject var16 = var1.getlocal(22);
                     var10000 = var16._notin(var1.getlocal(21));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var16 = var1.getlocal(22);
                        var10000 = var16._gt(Py.newInteger(2));
                        var7 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(1624);
                        var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(22));
                        var1.setline(1625);
                        var1.getlocal(21).__getattr__("add").__call__(var2, var1.getlocal(22));
                     }
                  }
               } catch (Throwable var11) {
                  Py.setException(var11, var1);
                  var1.setline(1643);
                  var6 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
                  var7 = Py.unpackSequence(var6, 3);
                  PyObject var8 = var7[0];
                  var1.setlocal(23, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(24, var8);
                  var8 = null;
                  var8 = var7[2];
                  var1.setlocal(25, var8);
                  var8 = null;
                  var6 = null;
                  var1.setline(1645);
                  var6 = var1.getglobal("traceback").__getattr__("format_exception").__call__(var2, var1.getlocal(23), var1.getlocal(24), var1.getlocal(25));
                  var1.setlocal(26, var6);
                  var6 = null;
                  var1.setline(1648);
                  var6 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(26));
                  var1.getlocal(24).__setattr__("child_traceback", var6);
                  var6 = null;
                  var1.setline(1649);
                  var1.getglobal("os").__getattr__("write").__call__(var2, var1.getlocal(18), var1.getglobal("pickle").__getattr__("dumps").__call__(var2, var1.getlocal(24)));
               }

               var1.setline(1653);
               var1.getglobal("os").__getattr__("_exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(255));
            }

            var1.setline(1656);
            if (var1.getlocal(19).__nonzero__()) {
               var1.setline(1657);
               var1.getglobal("gc").__getattr__("enable").__call__(var2);
            }
         } catch (Throwable var12) {
            Py.addTraceback(var12, var1);
            var1.setline(1660);
            var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(18));
            throw (Throwable)var12;
         }

         var1.setline(1660);
         var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(18));
         var1.setline(1662);
         var15 = var1.getlocal(11);
         var10000 = var15._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var15 = var1.getlocal(12);
            var10000 = var15._isnot(var1.getglobal("None"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1663);
            var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(11));
         }

         var1.setline(1664);
         var15 = var1.getlocal(14);
         var10000 = var15._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var15 = var1.getlocal(13);
            var10000 = var15._isnot(var1.getglobal("None"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1665);
            var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(14));
         }

         var1.setline(1666);
         var15 = var1.getlocal(16);
         var10000 = var15._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var15 = var1.getlocal(15);
            var10000 = var15._isnot(var1.getglobal("None"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1667);
            var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(16));
         }

         var1.setline(1671);
         var15 = var1.getglobal("_eintr_retry_call").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("read"), (PyObject)var1.getlocal(17), (PyObject)Py.newInteger(1048576));
         var1.setlocal(27, var15);
         var4 = null;
      } catch (Throwable var13) {
         Py.addTraceback(var13, var1);
         var1.setline(1674);
         var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(17));
         throw (Throwable)var13;
      }

      var1.setline(1674);
      var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(17));
      var1.setline(1676);
      var14 = var1.getlocal(27);
      var10000 = var14._ne(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(1678);
            var1.getglobal("_eintr_retry_call").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("waitpid"), (PyObject)var1.getderef(0).__getattr__("pid"), (PyObject)Py.newInteger(0));
         } catch (Throwable var10) {
            PyException var18 = Py.setException(var10, var1);
            if (!var18.match(var1.getglobal("OSError"))) {
               throw var18;
            }

            var15 = var18.value;
            var1.setlocal(28, var15);
            var4 = null;
            var1.setline(1680);
            var15 = var1.getlocal(28).__getattr__("errno");
            var10000 = var15._ne(var1.getglobal("errno").__getattr__("ECHILD"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1681);
               throw Py.makeException();
            }
         }

         var1.setline(1682);
         var14 = var1.getglobal("pickle").__getattr__("loads").__call__(var2, var1.getlocal(27));
         var1.setlocal(29, var14);
         var3 = null;
         var1.setline(1683);
         var14 = (new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(13), var1.getlocal(15)})).__iter__();

         while(true) {
            var1.setline(1683);
            var15 = var14.__iternext__();
            if (var15 == null) {
               var1.setline(1686);
               throw Py.makeException(var1.getlocal(29));
            }

            var1.setlocal(22, var15);
            var1.setline(1684);
            var5 = var1.getlocal(22);
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1685);
               var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(22));
            }
         }
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _dup2$55(PyFrame var1, ThreadState var2) {
      var1.setline(1611);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1612);
         var1.getderef(0).__getattr__("_set_cloexec_flag").__call__(var2, var1.getlocal(0), var1.getglobal("False"));
      } else {
         var1.setline(1613);
         var3 = var1.getlocal(0);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1614);
            var1.getglobal("os").__getattr__("dup2").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _handle_exitstatus$56(PyFrame var1, ThreadState var2) {
      var1.setline(1694);
      PyObject var3;
      if (var1.getlocal(2).__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1695);
         var3 = var1.getlocal(3).__call__(var2, var1.getlocal(1)).__neg__();
         var1.getlocal(0).__setattr__("returncode", var3);
         var3 = null;
      } else {
         var1.setline(1696);
         if (!var1.getlocal(4).__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(1700);
            throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unknown child exit status!")));
         }

         var1.setline(1697);
         var3 = var1.getlocal(5).__call__(var2, var1.getlocal(1));
         var1.getlocal(0).__setattr__("returncode", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _internal_poll$57(PyFrame var1, ThreadState var2) {
      var1.setline(1711);
      PyString.fromInterned("Check if child process has terminated.  Returns returncode\n            attribute.\n\n            This method is called by __del__, so it cannot reference anything\n            outside of the local scope (nor can any methods it calls).\n\n            ");
      var1.setline(1712);
      PyObject var3 = var1.getlocal(0).__getattr__("returncode");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(1714);
            var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("pid"), var1.getlocal(3));
            PyObject[] var8 = Py.unpackSequence(var3, 2);
            PyObject var5 = var8[0];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var8[1];
            var1.setlocal(6, var5);
            var5 = null;
            var3 = null;
            var1.setline(1715);
            var3 = var1.getlocal(5);
            var10000 = var3._eq(var1.getlocal(0).__getattr__("pid"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1716);
               var1.getlocal(0).__getattr__("_handle_exitstatus").__call__(var2, var1.getlocal(6));
            }
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (!var7.match(var1.getlocal(4))) {
               throw var7;
            }

            var1.setline(1718);
            PyObject var4 = var1.getlocal(1);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1719);
               var4 = var1.getlocal(1);
               var1.getlocal(0).__setattr__("returncode", var4);
               var4 = null;
            }
         }
      }

      var1.setline(1720);
      var3 = var1.getlocal(0).__getattr__("returncode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject wait$58(PyFrame var1, ThreadState var2) {
      var1.setline(1725);
      PyString.fromInterned("Wait for child process to terminate.  Returns returncode\n            attribute.");
      var1.setline(1726);
      PyObject var3 = var1.getlocal(0).__getattr__("returncode");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(1728);
            var3 = var1.getglobal("_eintr_retry_call").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("waitpid"), (PyObject)var1.getlocal(0).__getattr__("pid"), (PyObject)Py.newInteger(0));
            PyObject[] var9 = Py.unpackSequence(var3, 2);
            PyObject var5 = var9[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var9[1];
            var1.setlocal(2, var5);
            var5 = null;
            var3 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (!var7.match(var1.getglobal("OSError"))) {
               throw var7;
            }

            PyObject var4 = var7.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1730);
            var4 = var1.getlocal(3).__getattr__("errno");
            var10000 = var4._ne(var1.getglobal("errno").__getattr__("ECHILD"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1731);
               throw Py.makeException();
            }

            var1.setline(1735);
            PyInteger var8 = Py.newInteger(0);
            var1.setlocal(2, var8);
            var4 = null;
         }

         var1.setline(1736);
         var1.getlocal(0).__getattr__("_handle_exitstatus").__call__(var2, var1.getlocal(2));
      }

      var1.setline(1737);
      var3 = var1.getlocal(0).__getattr__("returncode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _communicate$59(PyFrame var1, ThreadState var2) {
      var1.setline(1741);
      if (var1.getlocal(0).__getattr__("stdin").__nonzero__()) {
         var1.setline(1744);
         var1.getlocal(0).__getattr__("stdin").__getattr__("flush").__call__(var2);
         var1.setline(1745);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(1746);
            var1.getlocal(0).__getattr__("stdin").__getattr__("close").__call__(var2);
         }
      }

      var1.setline(1748);
      PyObject var3;
      PyObject[] var4;
      PyObject var5;
      if (var1.getglobal("_has_poll").__nonzero__()) {
         var1.setline(1749);
         var3 = var1.getlocal(0).__getattr__("_communicate_with_poll").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(1751);
         var3 = var1.getlocal(0).__getattr__("_communicate_with_select").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(1754);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1755);
         var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1756);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1757);
         var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1763);
      var10000 = var1.getlocal(0).__getattr__("universal_newlines");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("file"), (PyObject)PyString.fromInterned("newlines"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(1764);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(1765);
            var3 = var1.getlocal(0).__getattr__("_translate_newlines").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(1766);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1767);
            var3 = var1.getlocal(0).__getattr__("_translate_newlines").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(1769);
      var1.getlocal(0).__getattr__("wait").__call__(var2);
      var1.setline(1770);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _communicate_with_poll$60(PyFrame var1, ThreadState var2) {
      var1.setline(1774);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1775);
      var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1776);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setderef(1, var8);
      var3 = null;
      var1.setline(1777);
      var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(1779);
      var3 = var1.getglobal("select").__getattr__("poll").__call__(var2);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(1780);
      PyObject[] var10 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var10;
      PyCode var10004 = register_and_append$61;
      var10 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      PyFunction var11 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var10);
      var1.setlocal(5, var11);
      var3 = null;
      var1.setline(1784);
      var10 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var10;
      var10004 = close_unregister_and_remove$62;
      var10 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      var11 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var10);
      var1.setlocal(6, var11);
      var3 = null;
      var1.setline(1789);
      PyObject var10000 = var1.getlocal(0).__getattr__("stdin");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
      }

      if (var10000.__nonzero__()) {
         var1.setline(1790);
         var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("stdin"), var1.getglobal("select").__getattr__("POLLOUT"));
      }

      var1.setline(1792);
      var3 = var1.getglobal("select").__getattr__("POLLIN")._or(var1.getglobal("select").__getattr__("POLLPRI"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1793);
      PyList var12;
      if (var1.getlocal(0).__getattr__("stdout").__nonzero__()) {
         var1.setline(1794);
         var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("stdout"), var1.getlocal(7));
         var1.setline(1795);
         var12 = new PyList(Py.EmptyObjects);
         var1.getlocal(4).__setitem__((PyObject)var1.getlocal(0).__getattr__("stdout").__getattr__("fileno").__call__(var2), var12);
         var1.setlocal(2, var12);
      }

      var1.setline(1796);
      if (var1.getlocal(0).__getattr__("stderr").__nonzero__()) {
         var1.setline(1797);
         var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("stderr"), var1.getlocal(7));
         var1.setline(1798);
         var12 = new PyList(Py.EmptyObjects);
         var1.getlocal(4).__setitem__((PyObject)var1.getlocal(0).__getattr__("stderr").__getattr__("fileno").__call__(var2), var12);
         var1.setlocal(3, var12);
      }

      var1.setline(1800);
      PyInteger var13 = Py.newInteger(0);
      var1.setlocal(8, var13);
      var3 = null;

      while(true) {
         var1.setline(1801);
         if (!var1.getderef(1).__nonzero__()) {
            var1.setline(1824);
            PyTuple var15 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var15;
         }

         PyObject var4;
         try {
            var1.setline(1803);
            var3 = var1.getderef(0).__getattr__("poll").__call__(var2);
            var1.setlocal(9, var3);
            var3 = null;
         } catch (Throwable var7) {
            PyException var14 = Py.setException(var7, var1);
            if (var14.match(var1.getglobal("select").__getattr__("error"))) {
               var4 = var14.value;
               var1.setlocal(10, var4);
               var4 = null;
               var1.setline(1805);
               var4 = var1.getlocal(10).__getattr__("args").__getitem__(Py.newInteger(0));
               var10000 = var4._eq(var1.getglobal("errno").__getattr__("EINTR"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(1807);
                  throw Py.makeException();
               }
               continue;
            }

            throw var14;
         }

         var1.setline(1809);
         var3 = var1.getlocal(9).__iter__();

         while(true) {
            var1.setline(1809);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(11, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(12, var6);
            var6 = null;
            var1.setline(1810);
            PyObject var9;
            if (var1.getlocal(12)._and(var1.getglobal("select").__getattr__("POLLOUT")).__nonzero__()) {
               var1.setline(1811);
               var9 = var1.getlocal(1).__getslice__(var1.getlocal(8), var1.getlocal(8)._add(var1.getglobal("_PIPE_BUF")), (PyObject)null);
               var1.setlocal(13, var9);
               var5 = null;
               var1.setline(1812);
               var9 = var1.getlocal(8);
               var9 = var9._iadd(var1.getglobal("os").__getattr__("write").__call__(var2, var1.getlocal(11), var1.getlocal(13)));
               var1.setlocal(8, var9);
               var1.setline(1813);
               var9 = var1.getlocal(8);
               var10000 = var9._ge(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1814);
                  var1.getlocal(6).__call__(var2, var1.getlocal(11));
               }
            } else {
               var1.setline(1815);
               if (var1.getlocal(12)._and(var1.getlocal(7)).__nonzero__()) {
                  var1.setline(1816);
                  var9 = var1.getglobal("os").__getattr__("read").__call__((ThreadState)var2, (PyObject)var1.getlocal(11), (PyObject)Py.newInteger(4096));
                  var1.setlocal(14, var9);
                  var5 = null;
                  var1.setline(1817);
                  if (var1.getlocal(14).__not__().__nonzero__()) {
                     var1.setline(1818);
                     var1.getlocal(6).__call__(var2, var1.getlocal(11));
                  }

                  var1.setline(1819);
                  var1.getlocal(4).__getitem__(var1.getlocal(11)).__getattr__("append").__call__(var2, var1.getlocal(14));
               } else {
                  var1.setline(1822);
                  var1.getlocal(6).__call__(var2, var1.getlocal(11));
               }
            }
         }
      }
   }

   public PyObject register_and_append$61(PyFrame var1, ThreadState var2) {
      var1.setline(1781);
      var1.getderef(0).__getattr__("register").__call__(var2, var1.getlocal(0).__getattr__("fileno").__call__(var2), var1.getlocal(1));
      var1.setline(1782);
      PyObject var3 = var1.getlocal(0);
      var1.getderef(1).__setitem__(var1.getlocal(0).__getattr__("fileno").__call__(var2), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close_unregister_and_remove$62(PyFrame var1, ThreadState var2) {
      var1.setline(1785);
      var1.getderef(0).__getattr__("unregister").__call__(var2, var1.getlocal(0));
      var1.setline(1786);
      var1.getderef(1).__getitem__(var1.getlocal(0)).__getattr__("close").__call__(var2);
      var1.setline(1787);
      var1.getderef(1).__getattr__("pop").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _communicate_with_select$63(PyFrame var1, ThreadState var2) {
      var1.setline(1828);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1829);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1830);
      PyObject var7 = var1.getglobal("None");
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(1831);
      var7 = var1.getglobal("None");
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(1833);
      PyObject var10000 = var1.getlocal(0).__getattr__("stdin");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
      }

      if (var10000.__nonzero__()) {
         var1.setline(1834);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("stdin"));
      }

      var1.setline(1835);
      if (var1.getlocal(0).__getattr__("stdout").__nonzero__()) {
         var1.setline(1836);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("stdout"));
         var1.setline(1837);
         var3 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(1838);
      if (var1.getlocal(0).__getattr__("stderr").__nonzero__()) {
         var1.setline(1839);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("stderr"));
         var1.setline(1840);
         var3 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(1842);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(6, var9);
      var3 = null;

      while(true) {
         var1.setline(1843);
         var10000 = var1.getlocal(2);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(3);
         }

         if (!var10000.__nonzero__()) {
            var1.setline(1873);
            PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
            var1.f_lasti = -1;
            return var11;
         }

         try {
            var1.setline(1845);
            var7 = var1.getglobal("select").__getattr__("select").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(3), (PyObject)(new PyList(Py.EmptyObjects)));
            PyObject[] var8 = Py.unpackSequence(var7, 3);
            PyObject var5 = var8[0];
            var1.setlocal(7, var5);
            var5 = null;
            var5 = var8[1];
            var1.setlocal(8, var5);
            var5 = null;
            var5 = var8[2];
            var1.setlocal(9, var5);
            var5 = null;
            var3 = null;
         } catch (Throwable var6) {
            PyException var10 = Py.setException(var6, var1);
            if (var10.match(var1.getglobal("select").__getattr__("error"))) {
               PyObject var4 = var10.value;
               var1.setlocal(10, var4);
               var4 = null;
               var1.setline(1847);
               var4 = var1.getlocal(10).__getattr__("args").__getitem__(Py.newInteger(0));
               var10000 = var4._eq(var1.getglobal("errno").__getattr__("EINTR"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(1849);
                  throw Py.makeException();
               }
               continue;
            }

            throw var10;
         }

         var1.setline(1851);
         var7 = var1.getlocal(0).__getattr__("stdin");
         var10000 = var7._in(var1.getlocal(8));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1852);
            var7 = var1.getlocal(1).__getslice__(var1.getlocal(6), var1.getlocal(6)._add(var1.getglobal("_PIPE_BUF")), (PyObject)null);
            var1.setlocal(11, var7);
            var3 = null;
            var1.setline(1853);
            var7 = var1.getglobal("os").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("stdin").__getattr__("fileno").__call__(var2), var1.getlocal(11));
            var1.setlocal(12, var7);
            var3 = null;
            var1.setline(1854);
            var7 = var1.getlocal(6);
            var7 = var7._iadd(var1.getlocal(12));
            var1.setlocal(6, var7);
            var1.setline(1855);
            var7 = var1.getlocal(6);
            var10000 = var7._ge(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1856);
               var1.getlocal(0).__getattr__("stdin").__getattr__("close").__call__(var2);
               var1.setline(1857);
               var1.getlocal(3).__getattr__("remove").__call__(var2, var1.getlocal(0).__getattr__("stdin"));
            }
         }

         var1.setline(1859);
         var7 = var1.getlocal(0).__getattr__("stdout");
         var10000 = var7._in(var1.getlocal(7));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1860);
            var7 = var1.getglobal("os").__getattr__("read").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("stdout").__getattr__("fileno").__call__(var2), (PyObject)Py.newInteger(1024));
            var1.setlocal(13, var7);
            var3 = null;
            var1.setline(1861);
            var7 = var1.getlocal(13);
            var10000 = var7._eq(PyString.fromInterned(""));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1862);
               var1.getlocal(0).__getattr__("stdout").__getattr__("close").__call__(var2);
               var1.setline(1863);
               var1.getlocal(2).__getattr__("remove").__call__(var2, var1.getlocal(0).__getattr__("stdout"));
            }

            var1.setline(1864);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(13));
         }

         var1.setline(1866);
         var7 = var1.getlocal(0).__getattr__("stderr");
         var10000 = var7._in(var1.getlocal(7));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1867);
            var7 = var1.getglobal("os").__getattr__("read").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("stderr").__getattr__("fileno").__call__(var2), (PyObject)Py.newInteger(1024));
            var1.setlocal(13, var7);
            var3 = null;
            var1.setline(1868);
            var7 = var1.getlocal(13);
            var10000 = var7._eq(PyString.fromInterned(""));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1869);
               var1.getlocal(0).__getattr__("stderr").__getattr__("close").__call__(var2);
               var1.setline(1870);
               var1.getlocal(2).__getattr__("remove").__call__(var2, var1.getlocal(0).__getattr__("stderr"));
            }

            var1.setline(1871);
            var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(13));
         }
      }
   }

   public PyObject send_signal$64(PyFrame var1, ThreadState var2) {
      var1.setline(1878);
      PyString.fromInterned("Send a signal to the process\n            ");
      var1.setline(1879);
      var1.getglobal("os").__getattr__("kill").__call__(var2, var1.getlocal(0).__getattr__("pid"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject terminate$65(PyFrame var1, ThreadState var2) {
      var1.setline(1883);
      PyString.fromInterned("Terminate the process with SIGTERM\n            ");
      var1.setline(1884);
      var1.getlocal(0).__getattr__("send_signal").__call__(var2, var1.getglobal("signal").__getattr__("SIGTERM"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject kill$66(PyFrame var1, ThreadState var2) {
      var1.setline(1888);
      PyString.fromInterned("Kill the process with SIGKILL\n            ");
      var1.setline(1889);
      var1.getlocal(0).__getattr__("send_signal").__call__(var2, var1.getglobal("signal").__getattr__("SIGKILL"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _os_system$67(PyFrame var1, ThreadState var2) {
      var1.setline(1898);
      PyString.fromInterned("system(command) -> exit_status\n\n    Execute the command (a string) in a subshell.");
      var1.setline(1899);
      PyObject var3 = var1.getglobal("_cmdline2listimpl").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1900);
      var3 = var1.getglobal("_escape_args").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1901);
      var3 = var1.getglobal("_shell_command")._add(var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1902);
      var3 = var1.getglobal("os").__getattr__("getcwdu").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1905);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1905);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1905);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1905);
            var1.dellocal(3);
            PyList var8 = var10000;
            var1.setlocal(1, var8);
            var3 = null;
            var1.setline(1907);
            var3 = var1.getglobal("java").__getattr__("lang").__getattr__("ProcessBuilder").__call__(var2, var1.getlocal(1));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(1908);
            var1.getlocal(5).__getattr__("directory").__call__(var2, var1.getglobal("java").__getattr__("io").__getattr__("File").__call__(var2, var1.getlocal(2)));
            var1.setline(1909);
            var1.getlocal(5).__getattr__("redirectInput").__call__(var2, var1.getglobal("java").__getattr__("lang").__getattr__("ProcessBuilder").__getattr__("Redirect").__getattr__("INHERIT"));
            var1.setline(1910);
            var1.getlocal(5).__getattr__("redirectOutput").__call__(var2, var1.getglobal("java").__getattr__("lang").__getattr__("ProcessBuilder").__getattr__("Redirect").__getattr__("INHERIT"));
            var1.setline(1911);
            var1.getlocal(5).__getattr__("redirectError").__call__(var2, var1.getglobal("java").__getattr__("lang").__getattr__("ProcessBuilder").__getattr__("Redirect").__getattr__("INHERIT"));
            var1.setline(1912);
            var1.getglobal("_setup_env").__call__(var2, var1.getglobal("dict").__call__(var2, var1.getglobal("os").__getattr__("environ")), var1.getlocal(5).__getattr__("environment").__call__(var2));

            try {
               var1.setline(1914);
               var3 = var1.getlocal(5).__getattr__("start").__call__(var2).__getattr__("waitFor").__call__(var2);
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var6) {
               PyException var7 = Py.setException(var6, var1);
               if (var7.match(new PyTuple(new PyObject[]{var1.getglobal("java").__getattr__("io").__getattr__("IOException"), var1.getglobal("java").__getattr__("lang").__getattr__("IllegalArgumentException")}))) {
                  PyObject var5 = var7.value;
                  var1.setlocal(6, var5);
                  var5 = null;
                  var1.setline(1917);
                  PyObject var9 = var1.getglobal("OSError");
                  PyObject var10002 = var1.getlocal(6).__getattr__("getMessage").__call__(var2);
                  if (!var10002.__nonzero__()) {
                     var10002 = var1.getlocal(6);
                  }

                  throw Py.makeException(var9.__call__(var2, var10002));
               } else {
                  throw var7;
               }
            }
         }

         var1.setlocal(4, var4);
         var1.setline(1905);
         var1.getlocal(3).__call__(var2, var1.getglobal("fileSystemDecode").__call__(var2, var1.getlocal(4)));
      }
   }

   public PyObject _demo_posix$68(PyFrame var1, ThreadState var2) {
      var1.setline(1924);
      PyObject var10000 = var1.getglobal("Popen");
      PyObject[] var3 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("ps")}), var1.getglobal("PIPE")};
      String[] var4 = new String[]{"stdout"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000.__getattr__("communicate").__call__(var2).__getitem__(Py.newInteger(0));
      var1.setlocal(0, var6);
      var3 = null;
      var1.setline(1925);
      Py.println(PyString.fromInterned("Process list:"));
      var1.setline(1926);
      Py.println(var1.getlocal(0));
      var1.setline(1931);
      var6 = var1.getglobal("os").__getattr__("getuid").__call__(var2);
      var10000 = var6._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1932);
         var10000 = var1.getglobal("Popen");
         var3 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("id")}), null};
         var1.setline(1932);
         PyObject[] var7 = Py.EmptyObjects;
         var3[1] = new PyFunction(var1.f_globals, var7, f$69);
         var4 = new String[]{"preexec_fn"};
         var10000 = var10000.__call__(var2, var3, var4);
         var3 = null;
         var6 = var10000;
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(1933);
         var1.getlocal(1).__getattr__("wait").__call__(var2);
      }

      var1.setline(1938);
      Py.println(PyString.fromInterned("Looking for 'hda'..."));
      var1.setline(1939);
      var10000 = var1.getglobal("Popen");
      var3 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("dmesg")}), var1.getglobal("PIPE")};
      var4 = new String[]{"stdout"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var6 = var10000;
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1940);
      var10000 = var1.getglobal("Popen");
      var3 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("grep"), PyString.fromInterned("hda")}), var1.getlocal(2).__getattr__("stdout"), var1.getglobal("PIPE")};
      var4 = new String[]{"stdin", "stdout"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var6 = var10000;
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1941);
      Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(3).__getattr__("communicate").__call__(var2).__getitem__(Py.newInteger(0))));
      var1.setline(1946);
      Py.println();
      var1.setline(1947);
      Py.println(PyString.fromInterned("Trying a weird file..."));

      label25: {
         PyObject var8;
         try {
            var1.setline(1949);
            Py.println(var1.getglobal("Popen").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("/this/path/does/not/exist")}))).__getattr__("communicate").__call__(var2));
         } catch (Throwable var5) {
            PyException var9 = Py.setException(var5, var1);
            if (var9.match(var1.getglobal("OSError"))) {
               var8 = var9.value;
               var1.setlocal(4, var8);
               var4 = null;
               var1.setline(1951);
               var8 = var1.getlocal(4).__getattr__("errno");
               var10000 = var8._eq(var1.getglobal("errno").__getattr__("ENOENT"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1952);
                  Py.println(PyString.fromInterned("The file didn't exist.  I thought so..."));
                  var1.setline(1953);
                  Py.println(PyString.fromInterned("Child traceback:"));
                  var1.setline(1954);
                  Py.println(var1.getlocal(4).__getattr__("child_traceback"));
               } else {
                  var1.setline(1956);
                  Py.printComma(PyString.fromInterned("Error"));
                  Py.println(var1.getlocal(4).__getattr__("errno"));
               }
               break label25;
            }

            throw var9;
         }

         var1.setline(1958);
         var8 = var1.getglobal("sys").__getattr__("stderr");
         Py.println(var8, PyString.fromInterned("Gosh.  No error."));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$69(PyFrame var1, ThreadState var2) {
      var1.setline(1932);
      PyObject var3 = var1.getglobal("os").__getattr__("setuid").__call__((ThreadState)var2, (PyObject)Py.newInteger(100));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _demo_windows$70(PyFrame var1, ThreadState var2) {
      var1.setline(1965);
      Py.println(PyString.fromInterned("Looking for 'PROMPT' in set output..."));
      var1.setline(1966);
      PyObject var10000 = var1.getglobal("Popen");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("set"), var1.getglobal("PIPE"), var1.getglobal("True")};
      String[] var4 = new String[]{"stdout", "shell"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(0, var5);
      var3 = null;
      var1.setline(1967);
      var10000 = var1.getglobal("Popen");
      var3 = new PyObject[]{PyString.fromInterned("find \"PROMPT\""), var1.getlocal(0).__getattr__("stdout"), var1.getglobal("PIPE")};
      var4 = new String[]{"stdin", "stdout"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(1968);
      Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1).__getattr__("communicate").__call__(var2).__getitem__(Py.newInteger(0))));
      var1.setline(1973);
      Py.println(PyString.fromInterned("Executing calc..."));
      var1.setline(1974);
      var5 = var1.getglobal("Popen").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("calc"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1975);
      var1.getlocal(2).__getattr__("wait").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _demo_jython$71(PyFrame var1, ThreadState var2) {
      var1.setline(1982);
      Py.println(PyString.fromInterned("Running a jython subprocess to return the number of processors..."));
      var1.setline(1983);
      PyObject var3 = var1.getglobal("Popen").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("sys").__getattr__("executable"), PyString.fromInterned("-c"), PyString.fromInterned("import sys;from java.lang import Runtime;sys.exit(Runtime.getRuntime().availableProcessors())")})));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1987);
      Py.println(var1.getlocal(0).__getattr__("wait").__call__(var2));
      var1.setline(1992);
      Py.println(PyString.fromInterned("Connecting two jython subprocesses..."));
      var1.setline(1993);
      PyObject var10000 = var1.getglobal("Popen");
      PyObject[] var6 = new PyObject[]{new PyList(new PyObject[]{var1.getglobal("sys").__getattr__("executable"), PyString.fromInterned("-c"), PyString.fromInterned("import os;print os.environ[\"foo\"]")}), null, null};
      PyObject var10002 = var1.getglobal("dict");
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("bar")};
      String[] var5 = new String[]{"foo"};
      var10002 = var10002.__call__(var2, var4, var5);
      var4 = null;
      var6[1] = var10002;
      var6[2] = var1.getglobal("PIPE");
      String[] var7 = new String[]{"env", "stdout"};
      var10000 = var10000.__call__(var2, var6, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1997);
      var10000 = var1.getglobal("Popen");
      var6 = new PyObject[4];
      var6[0] = new PyList(new PyObject[]{var1.getglobal("sys").__getattr__("executable"), PyString.fromInterned("-c"), PyString.fromInterned("import os, sys;their_foo = sys.stdin.read().strip();my_foo = os.environ[\"foo\"];msg = \"Their env's foo: %r, My env's foo: %r\";print msg % (their_foo, my_foo)")});
      var10002 = var1.getglobal("dict");
      var4 = new PyObject[]{PyString.fromInterned("baz")};
      var5 = new String[]{"foo"};
      var10002 = var10002.__call__(var2, var4, var5);
      var4 = null;
      var6[1] = var10002;
      var6[2] = var1.getlocal(1).__getattr__("stdout");
      var6[3] = var1.getglobal("PIPE");
      var7 = new String[]{"env", "stdin", "stdout"};
      var10000 = var10000.__call__(var2, var6, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2004);
      Py.println(var1.getlocal(2).__getattr__("communicate").__call__(var2).__getitem__(Py.newInteger(0)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public subprocess$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CalledProcessError$1 = Py.newCode(0, var2, var1, "CalledProcessError", 401, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "returncode", "cmd", "output"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 407, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$3 = Py.newCode(1, var2, var1, "__str__", 411, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      STARTUPINFO$4 = Py.newCode(0, var2, var1, "STARTUPINFO", 419, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      pywintypes$5 = Py.newCode(0, var2, var1, "pywintypes", 425, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"inst", "res"};
      _cleanup$6 = Py.newCode(0, var2, var1, "_cleanup", 469, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func", "args", "e"};
      _eintr_retry_call$7 = Py.newCode(2, var2, var1, "_eintr_retry_call", 484, true, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"popenargs", "kwargs"};
      call$8 = Py.newCode(2, var2, var1, "call", 494, true, true, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"popenargs", "kwargs", "retcode", "cmd"};
      check_call$9 = Py.newCode(2, var2, var1, "check_call", 505, true, true, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"popenargs", "kwargs", "process", "output", "unused_err", "retcode", "cmd"};
      check_output$10 = Py.newCode(2, var2, var1, "check_output", 524, true, true, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"seq", "result", "needquote", "arg", "bs_buf", "c"};
      list2cmdline$11 = Py.newCode(1, var2, var1, "list2cmdline", 557, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmdline", "whitespace", "bs_count", "in_quotes", "arg", "argv", "ch"};
      _cmdline2list$12 = Py.newCode(1, var2, var1, "_cmdline2list", 635, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"shell_command", "executable", "distutils", "warnings"};
      _setup_platform$13 = Py.newCode(0, var2, var1, "_setup_platform", 691, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "_[699_41]", "arg"};
      f$14 = Py.newCode(1, var2, var1, "<lambda>", 699, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args"};
      f$15 = Py.newCode(1, var2, var1, "<lambda>", 701, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args"};
      f$16 = Py.newCode(1, var2, var1, "<lambda>", 702, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _CouplerThread$17 = Py.newCode(0, var2, var1, "_CouplerThread", 722, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "read_func", "write_func", "close_func"};
      __init__$18 = Py.newCode(5, var2, var1, "__init__", 738, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf", "count", "ioe"};
      run$19 = Py.newCode(1, var2, var1, "run", 746, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"env", "builder_env", "merge_env", "_(784_25)", "entries", "entry", "key", "value"};
      String[] var10001 = var2;
      subprocess$py var10007 = self;
      var2 = new String[]{"builder_env"};
      _setup_env$20 = Py.newCode(2, var10001, var1, "_setup_env", 773, false, false, var10007, 20, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "key", "value"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"builder_env"};
      f$21 = Py.newCode(1, var10001, var1, "<genexpr>", 784, false, false, var10007, 21, (String[])null, var2, 0, 4129);
      var2 = new String[0];
      Popen$22 = Py.newCode(0, var2, var1, "Popen", 799, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "bufsize", "executable", "stdin", "stdout", "stderr", "preexec_fn", "close_fds", "shell", "cwd", "env", "universal_newlines", "startupinfo", "creationflags", "p2cread", "p2cwrite", "c2pread", "c2pwrite", "errread", "errwrite", "proc", "ct2cwrite", "c2ctread", "cterrread"};
      __init__$23 = Py.newCode(15, var2, var1, "__init__", 800, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      _translate_newlines$24 = Py.newCode(2, var2, var1, "_translate_newlines", 944, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_maxint", "_active"};
      __del__$25 = Py.newCode(3, var2, var1, "__del__", 950, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "stdout", "stderr"};
      communicate$26 = Py.newCode(2, var2, var1, "communicate", 961, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      poll$27 = Py.newCode(1, var2, var1, "poll", 991, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fh", "buffer"};
      _readerthread$28 = Py.newCode(3, var2, var1, "_readerthread", 999, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "stdout", "stderr", "stdout_thread", "stderr_thread"};
      _communicate$29 = Py.newCode(2, var2, var1, "_communicate", 1003, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stdin", "stdout", "stderr", "p2cread", "p2cwrite", "c2pread", "c2pwrite", "errread", "errwrite", "_"};
      _get_handles$30 = Py.newCode(4, var2, var1, "_get_handles", 1054, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handle"};
      _make_inheritable$31 = Py.newCode(2, var2, var1, "_make_inheritable", 1111, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "w9xpopen"};
      _find_w9xpopen$32 = Py.newCode(1, var2, var1, "_find_w9xpopen", 1118, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "executable", "preexec_fn", "close_fds", "cwd", "env", "universal_newlines", "startupinfo", "creationflags", "shell", "p2cread", "p2cwrite", "c2pread", "c2pwrite", "errread", "errwrite", "comspec", "w9xpopen", "hp", "ht", "pid", "tid", "e"};
      _execute_child$33 = Py.newCode(17, var2, var1, "_execute_child", 1135, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_deadstate", "_WaitForSingleObject", "_WAIT_OBJECT_0", "_GetExitCodeProcess"};
      _internal_poll$34 = Py.newCode(5, var2, var1, "_internal_poll", 1212, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      wait$35 = Py.newCode(1, var2, var1, "wait", 1229, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stdin", "stdout", "stderr", "p2cread", "p2cwrite", "c2pread", "c2pwrite", "errread", "errwrite"};
      _get_handles$36 = Py.newCode(4, var2, var1, "_get_handles", 1242, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "errwrite", "c2pwrite"};
      _stderr_is_stdout$37 = Py.newCode(3, var2, var1, "_stderr_is_stdout", 1286, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwargs"};
      _coupler_thread$38 = Py.newCode(3, var2, var1, "_coupler_thread", 1294, true, true, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "executable", "preexec_fn", "close_fds", "cwd", "env", "universal_newlines", "startupinfo", "creationflags", "shell", "p2cread", "p2cwrite", "c2pread", "c2pwrite", "errread", "errwrite", "stdin", "stdout", "stderr", "_(1314_23)", "_[1328_20]", "arg", "builder", "e", "msg"};
      _execute_child$39 = Py.newCode(20, var2, var1, "_execute_child", 1301, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "arg"};
      f$40 = Py.newCode(1, var2, var1, "<genexpr>", 1314, false, false, self, 40, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "object", "field_name", "field"};
      _get_private_field$41 = Py.newCode(3, var2, var1, "_get_private_field", 1378, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pid_field", "field"};
      _get_pid$42 = Py.newCode(2, var2, var1, "_get_pid", 1390, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handle_field", "field"};
      _get_pid$43 = Py.newCode(2, var2, var1, "_get_pid", 1402, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_deadstate"};
      poll$44 = Py.newCode(2, var2, var1, "poll", 1409, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_deadstate", "e"};
      _internal_poll$45 = Py.newCode(2, var2, var1, "_internal_poll", 1419, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "coupler"};
      wait$46 = Py.newCode(1, var2, var1, "wait", 1433, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      terminate$47 = Py.newCode(1, var2, var1, "terminate", 1447, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      kill$48 = Py.newCode(1, var2, var1, "kill", 1454, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sig"};
      send_signal$49 = Py.newCode(2, var2, var1, "send_signal", 1460, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sig"};
      send_signal$50 = Py.newCode(2, var2, var1, "send_signal", 1469, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stdin", "stdout", "stderr", "p2cread", "p2cwrite", "c2pread", "c2pwrite", "errread", "errwrite"};
      _get_handles$51 = Py.newCode(4, var2, var1, "_get_handles", 1482, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fd", "cloexec", "cloexec_flag", "old"};
      _set_cloexec_flag$52 = Py.newCode(3, var2, var1, "_set_cloexec_flag", 1527, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "but", "i"};
      _close_fds$53 = Py.newCode(2, var2, var1, "_close_fds", 1540, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "executable", "preexec_fn", "close_fds", "cwd", "env", "universal_newlines", "startupinfo", "creationflags", "shell", "p2cread", "p2cwrite", "c2pread", "c2pwrite", "errread", "errwrite", "errpipe_read", "errpipe_write", "gc_was_enabled", "_dup2", "closed", "fd", "exc_type", "exc_value", "tb", "exc_lines", "data", "e", "child_exception"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      _execute_child$54 = Py.newCode(17, var10001, var1, "_execute_child", 1554, false, false, var10007, 54, var2, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      _dup2$55 = Py.newCode(2, var10001, var1, "_dup2", 1607, false, false, var10007, 55, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "sts", "_WIFSIGNALED", "_WTERMSIG", "_WIFEXITED", "_WEXITSTATUS"};
      _handle_exitstatus$56 = Py.newCode(6, var2, var1, "_handle_exitstatus", 1689, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_deadstate", "_waitpid", "_WNOHANG", "_os_error", "pid", "sts"};
      _internal_poll$57 = Py.newCode(5, var2, var1, "_internal_poll", 1703, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pid", "sts", "e"};
      wait$58 = Py.newCode(1, var2, var1, "wait", 1723, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "stdout", "stderr"};
      _communicate$59 = Py.newCode(2, var2, var1, "_communicate", 1740, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "stdout", "stderr", "fd2output", "register_and_append", "close_unregister_and_remove", "select_POLLIN_POLLPRI", "input_offset", "ready", "e", "fd", "mode", "chunk", "data", "poller", "fd2file"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"poller", "fd2file"};
      _communicate_with_poll$60 = Py.newCode(2, var10001, var1, "_communicate_with_poll", 1773, false, false, var10007, 60, var2, (String[])null, 2, 4097);
      var2 = new String[]{"file_obj", "eventmask"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"poller", "fd2file"};
      register_and_append$61 = Py.newCode(2, var10001, var1, "register_and_append", 1780, false, false, var10007, 61, (String[])null, var2, 0, 4097);
      var2 = new String[]{"fd"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"poller", "fd2file"};
      close_unregister_and_remove$62 = Py.newCode(1, var10001, var1, "close_unregister_and_remove", 1784, false, false, var10007, 62, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "input", "read_set", "write_set", "stdout", "stderr", "input_offset", "rlist", "wlist", "xlist", "e", "chunk", "bytes_written", "data"};
      _communicate_with_select$63 = Py.newCode(2, var2, var1, "_communicate_with_select", 1827, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sig"};
      send_signal$64 = Py.newCode(2, var2, var1, "send_signal", 1876, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      terminate$65 = Py.newCode(1, var2, var1, "terminate", 1881, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      kill$66 = Py.newCode(1, var2, var1, "kill", 1886, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"command", "args", "cwd", "_[1905_12]", "arg", "builder", "e"};
      _os_system$67 = Py.newCode(1, var2, var1, "_os_system", 1895, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"plist", "p", "p1", "p2", "e"};
      _demo_posix$68 = Py.newCode(0, var2, var1, "_demo_posix", 1920, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$69 = Py.newCode(0, var2, var1, "<lambda>", 1932, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p1", "p2", "p"};
      _demo_windows$70 = Py.newCode(0, var2, var1, "_demo_windows", 1961, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "p1", "p2"};
      _demo_jython$71 = Py.newCode(0, var2, var1, "_demo_jython", 1978, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new subprocess$py("subprocess$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(subprocess$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.CalledProcessError$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__str__$3(var2, var3);
         case 4:
            return this.STARTUPINFO$4(var2, var3);
         case 5:
            return this.pywintypes$5(var2, var3);
         case 6:
            return this._cleanup$6(var2, var3);
         case 7:
            return this._eintr_retry_call$7(var2, var3);
         case 8:
            return this.call$8(var2, var3);
         case 9:
            return this.check_call$9(var2, var3);
         case 10:
            return this.check_output$10(var2, var3);
         case 11:
            return this.list2cmdline$11(var2, var3);
         case 12:
            return this._cmdline2list$12(var2, var3);
         case 13:
            return this._setup_platform$13(var2, var3);
         case 14:
            return this.f$14(var2, var3);
         case 15:
            return this.f$15(var2, var3);
         case 16:
            return this.f$16(var2, var3);
         case 17:
            return this._CouplerThread$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this.run$19(var2, var3);
         case 20:
            return this._setup_env$20(var2, var3);
         case 21:
            return this.f$21(var2, var3);
         case 22:
            return this.Popen$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this._translate_newlines$24(var2, var3);
         case 25:
            return this.__del__$25(var2, var3);
         case 26:
            return this.communicate$26(var2, var3);
         case 27:
            return this.poll$27(var2, var3);
         case 28:
            return this._readerthread$28(var2, var3);
         case 29:
            return this._communicate$29(var2, var3);
         case 30:
            return this._get_handles$30(var2, var3);
         case 31:
            return this._make_inheritable$31(var2, var3);
         case 32:
            return this._find_w9xpopen$32(var2, var3);
         case 33:
            return this._execute_child$33(var2, var3);
         case 34:
            return this._internal_poll$34(var2, var3);
         case 35:
            return this.wait$35(var2, var3);
         case 36:
            return this._get_handles$36(var2, var3);
         case 37:
            return this._stderr_is_stdout$37(var2, var3);
         case 38:
            return this._coupler_thread$38(var2, var3);
         case 39:
            return this._execute_child$39(var2, var3);
         case 40:
            return this.f$40(var2, var3);
         case 41:
            return this._get_private_field$41(var2, var3);
         case 42:
            return this._get_pid$42(var2, var3);
         case 43:
            return this._get_pid$43(var2, var3);
         case 44:
            return this.poll$44(var2, var3);
         case 45:
            return this._internal_poll$45(var2, var3);
         case 46:
            return this.wait$46(var2, var3);
         case 47:
            return this.terminate$47(var2, var3);
         case 48:
            return this.kill$48(var2, var3);
         case 49:
            return this.send_signal$49(var2, var3);
         case 50:
            return this.send_signal$50(var2, var3);
         case 51:
            return this._get_handles$51(var2, var3);
         case 52:
            return this._set_cloexec_flag$52(var2, var3);
         case 53:
            return this._close_fds$53(var2, var3);
         case 54:
            return this._execute_child$54(var2, var3);
         case 55:
            return this._dup2$55(var2, var3);
         case 56:
            return this._handle_exitstatus$56(var2, var3);
         case 57:
            return this._internal_poll$57(var2, var3);
         case 58:
            return this.wait$58(var2, var3);
         case 59:
            return this._communicate$59(var2, var3);
         case 60:
            return this._communicate_with_poll$60(var2, var3);
         case 61:
            return this.register_and_append$61(var2, var3);
         case 62:
            return this.close_unregister_and_remove$62(var2, var3);
         case 63:
            return this._communicate_with_select$63(var2, var3);
         case 64:
            return this.send_signal$64(var2, var3);
         case 65:
            return this.terminate$65(var2, var3);
         case 66:
            return this.kill$66(var2, var3);
         case 67:
            return this._os_system$67(var2, var3);
         case 68:
            return this._demo_posix$68(var2, var3);
         case 69:
            return this.f$69(var2, var3);
         case 70:
            return this._demo_windows$70(var2, var3);
         case 71:
            return this._demo_jython$71(var2, var3);
         default:
            return null;
      }
   }
}
