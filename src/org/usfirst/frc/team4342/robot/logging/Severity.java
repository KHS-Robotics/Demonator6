package org.usfirst.frc.team4342.robot.logging;

public enum Severity 
{
	ERROR(3), WARNING(4), INFO(6), DEBUG(7);
	
	private final int severity;
	
	private Severity(int severity) 
	{
		this.severity = severity;
	}

	public int value() 
	{
		return severity;
	}
}
