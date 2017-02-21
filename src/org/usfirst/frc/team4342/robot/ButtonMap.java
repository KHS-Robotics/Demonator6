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
			public static final int ALIGN_STRAIGHT = 2;
			public static final int ALIGN_HOOK_LEFT = 4;
			public static final int ALIGN_HOOK_MIDDLE = 3;
			public static final int ALIGN_HOOK_RIGHT = 5;
		}
		
		/**
		 * The right drive joystick button maps
		 */
		public static class Right
		{
			public static final int ACCUMULATE = 2;
			public static final int HOLD_CURRENT_YAW = 3;
			public static final int BOILER_YAW = 4;
			public static final int SHIFT = 5;
			public static final int NAVX_RESET = 7; // temporary
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
			public static final int MANUAL_SETPOINT = 8;
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
