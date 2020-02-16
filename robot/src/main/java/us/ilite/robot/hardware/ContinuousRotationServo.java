package us.ilite.robot.hardware;

import edu.wpi.first.wpilibj.Servo;

public class ContinuousRotationServo extends Servo {
    /**
     * Constructor.<br>
     *
     * <p>By default {@value #kDefaultMaxServoPWM} ms is used as the maxPWM value<br> By default
     * {@value #kDefaultMinServoPWM} ms is used as the minPWM value<br>
     *
     * @param channel The PWM channel to which the servo is attached. 0-9 are on-board, 10-19 are on
     *                the MXP port
     */
    public ContinuousRotationServo(int channel) {
        super(channel);
    }

    /**
     * Set the servo position.
     *
     * <p>Servo values range from 0.0 to 1.0 corresponding to the range of full left to full right.
     *
     * @param value Position from 0.0 to 1.0.
     */
    public void setServo(double value) {
        // -1.0 --> 0
        // 0 --> 0.5
        // 1.0 --> 1.0
        set(value/2.0 + 0.5);
    }
}
