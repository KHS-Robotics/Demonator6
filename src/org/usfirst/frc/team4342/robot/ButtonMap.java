package org.usfirst.frc.team4342.robot;

/**
 * Class to keep track of the button maps for teleop
 */
public class ButtonMap 
{
	private ButtonMap() {}
	
	/**
	 * The drive joysticks
	 */
	public static class DriveStick
	{
		/**
		 * The left drive joystick button maps
		 */
		public static class Left
		{
			
		}
		
		/**
		 * The right drive joystick button maps
		 */
		public static class Right
		{
			public static final int SCALE = 7;
			public static int FLIP_ORIENTATION = 3;
			public static int SHIFT = 2;
		}
	}
	
	/**
	 * The switch box used for the shooter and gear placer
	 */
	public static class SwitchBox
	{
		/**
		 * The switch box buttons for the shooter
		 */
		public static class Shooter
		{
			public static final int ACCUMULATE = 1;
			public static final int AGITATE = 2;
			public static final int SHOOT_FAR = 3;
			public static final int SHOOT_CLOSE = 4;
		}
		
		/**
		 * The switch box buttons for the gear placer
		 */
		public static class GearPlacer
		{
			public static final int LOWER = 8;
		}
	}
}
