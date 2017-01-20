package org.usfirst.frc.team4342.robot.logging;

import java.io.PrintStream;

public class Logger 
{
	private Logger() {}
	
	private static Severity severity = Severity.DEBUG;
	
	public static void setSeverity(Severity sev)
	{
		severity = sev;
	}
	
	public static void log(Severity sev, String message, Throwable t, PrintStream stream)
	{
		if(sev.value() <= severity.value())
		{
			stream.println(sev.toString().toUpperCase() + ": " + message);
		
			if(t != null)
			{
				for(StackTraceElement ste : t.getStackTrace())
				{
					stream.println(ste.toString());
				}
			}
		}
	}
	
	public static void debug(String message)
	{
		log(Severity.DEBUG, message, null, System.out);
	}
	
	public static void info(String message)
	{
		log(Severity.INFO, message, null, System.out);
	}
	
	public static void warning(String message)
	{
		log(Severity.WARNING, message, null, System.err);
	}
	
	public static void error(String message, Throwable t)
	{
		log(Severity.ERROR, message, null, System.err);
	}
}
