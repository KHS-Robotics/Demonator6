package org.usfirst.frc.team4342.robot.logging;

public class Logger 
{
	private Logger() {}
	
	private static Severity severity;
	
	public static void setSeverity(Severity sev)
	{
		severity = sev;
	}
	
	public static void log(Severity sev, String message, Throwable t)
	{
		if(sev.value() <= severity.value())
		{
			System.out.println(sev.toString().toUpperCase() + ": " + message);
		
			if(t != null)
			{
				for(StackTraceElement ste : t.getStackTrace())
				{
					System.out.println(ste.toString());
				}
			}
		}
	}
	
	public static void debug(String message)
	{
		log(Severity.DEBUG, message, null);
	}
	
	public static void info(String message)
	{
		log(Severity.INFO, message, null);
	}
	
	public static void warning(String message)
	{
		log(Severity.WARNING, message, null);
	}
	
	public static void error(String message, Throwable t)
	{
		log(Severity.ERROR, message, t);
	}
}
