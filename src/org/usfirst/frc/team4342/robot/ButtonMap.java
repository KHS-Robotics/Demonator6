package org.usfirst.frc.team4342.robot;

public class ButtonMap 
{
	private ButtonMap() {}
	
	public static class Drive
	{
		public static final int SCALE = 1;
		public static int FLIP_ORIENTATION = 3;
		public static int SHIFT = 4;
	}
	
	public static class Shooter
	{
		public static final int ACCUMULATE = 5;
		public static final int AGITATE = 6;
		public static final int SHOOT = 7;
	}
	
	public static class GearPlacer
	{
		public static final int LOWER = 0;
	}
}
