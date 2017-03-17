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
			public static final int GO_TO_LOAD_IN_YAW = 1;
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
			public static final int SHIFT_AND_HOLD_CURRENT_YAW = 1;
			public static final int ACCUMULATE = 2;
			public static final int HOLD_CURRENT_YAW = 3;
			public static final int BOILER_YAW = 4;
			public static final int SHIFT = 5;
		}
	}
	
	/**
	 * The switch box used for the shooter and gear placer
	 */
	public static class SwitchBox
	{
		/**
		 * Switches between two version of align hook
		 */
		public static final int USE_NEW_ALIGN_HOOK = 8;
		
		/**
		 * Reset Button to reset some sensors
		 */
		public static final int RESET = 9;
		
		/**
		 * The switch box buttons for the shooter
		 */
		public static class Shooter
		{
			public static final int ACCUMULATE = 1;
			public static final int AGITATE = 4;
			public static final int SHOOT_FAR = 11;
			public static final int SHOOT_CLOSE = 12;
		}
		
		/**
		 * The switch box buttons for the gear placer
		 */
		public static class GearPlacer
		{
			public static final int LOWER = 2;
		}
		
		/**
		 * The switch box buttons for the Scaler
		 */
		public static class Scaler
		{
			public static final int SCALE = 10;
		}
	}
}
