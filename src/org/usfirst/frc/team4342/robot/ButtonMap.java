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
			public static int SHIFT = 1;
			public static int ACCUMULATE = 2;
			public static int NAVX_RESET = 7;
			public static int ALIGN_HOOK = 6;
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
			public static final int CAM_LIGHT = 7; //Might need to move to a driver stick after testing
		}
		
		/**
		 * The switch box buttons for the gear placer
		 */
		public static class GearPlacer
		{
			public static final int LOWER = 5;
		}
		
		/**
		 * The switch box buttons for the Scaler
		 */
		public static class Scaler
		{
			public static final int SCALE = 6;
		}
	}
}
