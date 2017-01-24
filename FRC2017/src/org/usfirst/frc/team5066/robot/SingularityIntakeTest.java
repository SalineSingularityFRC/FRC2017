
package org.usfirst.frc.team5066.robot;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Saline Singularity 5066
 *
 */
public class SingularityIntakeTest {

	@Test
	public void inputOfSpeedAndReverseShouldSetMotorsToCorrectSpeed() {
		
		//SingularityIntake is tested.
		SingularityIntake tester = new SingularityIntake(1, 2, 3);
		
		tester.setSpeed(0.36, false);
		assertEquals("front wheel value should be 0.36", 0.36, tester.frontWheel);
		assertEquals("low conveyer value should be 0.36", 0.36, tester.lowConveyer);
		assertEquals("high conveyer value should be 0.36", 0.36, tester.highConveyer);
		
		tester.setSpeed(5.0, true);
		assertEquals("front wheel value should be -1", -1.0, tester.frontWheel);
		assertEquals("low conveyer value should be -1.0", -1.0, tester.lowConveyer);
		assertEquals("high conveyer value should be -1.0", -1.0, tester.highConveyer);
		
		
	}

}
