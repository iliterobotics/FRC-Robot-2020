package us.ilite.robot.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import us.ilite.common.Data;
import us.ilite.common.config.Settings;
import us.ilite.common.types.sensor.EColorData;
import us.ilite.robot.Robot;
import us.ilite.robot.utils.ColorUtils;

import static us.ilite.robot.utils.ColorUtils.*;

public class DJBoothRotationControl extends Module {

    private ColorSensorV3 mColorSensorV3;
    private VictorSPX victorSPX;

    private final ColorMatch mColorMatcher = new ColorMatch();
    private EColorData.EColor eCurrentColorState;
    private EColorData.EColor eLastColorState;
    private int mColorChangeLocation;
    private int mInitialColorStateLocation;
    private final EColorData.EColor[] colorStates = {EColorData.EColor.RED, EColorData.EColor.YELLOW, EColorData.EColor.BLUE, EColorData.EColor.GREEN};
    private ILog mLog = Logger.createLog( this.getClass() );
    private EColorData.EMotorState eMotorState;
    private boolean mIsDone;


    @Override
    public void init(double pNow) {
        I2C.Port i2cPort = I2C.Port.kOnboard;
        mColorSensorV3 = new ColorSensorV3(i2cPort);
        victorSPX = new VictorSPX( 12 );
        victorSPX.set( ControlMode.PercentOutput, 0d );

        mColorMatcher.addColorMatch(kBlueTarget);
        mColorMatcher.addColorMatch(kGreenTarget);
        mColorMatcher.addColorMatch(kRedTarget);
        mColorMatcher.addColorMatch(kYellowTarget);

        setColorState( mColorMatcher.matchClosestColor( mColorSensorV3.getColor() ).color );
        eLastColorState = eCurrentColorState;
        mColorChangeLocation = getInitialStateLocation( eCurrentColorState );
    }

    @Override
    public boolean update(double pNow) {

        if ( EColorData.ROTATION_CONTROL_INPUT.equals( EColorData.EInput.POSITIVE ) ) {
            if ( (mColorChangeLocation - mInitialColorStateLocation <= 32) && EColorData.ROTATION_CONTROL_INPUT.equals(EColorData.EInput.POSITIVE) ) {

                Color mColor = mColorSensorV3.getColor();
                ColorMatchResult match = mColorMatcher.matchClosestColor( mColor );
                setColorState( match.color );

                if ( eCurrentColorState != eLastColorState ) {
                    mColorChangeLocation++;
                }
                if ( colorStates[ mColorChangeLocation % colorStates.length ] != eCurrentColorState ) {
                    mLog.warn( "Issue with Detecting Color. Actual: " + colorStates[mColorChangeLocation] + "Detected: " + eLastColorState );
                    mColorChangeLocation--;
                    eCurrentColorState = getStateAtLocation( mColorChangeLocation );
                }

//            String colorString = getColorStringForMatchResult(match);
//            SmartDashboard.putString( "Detected Color on Rotation: ", getColorStringForMatchResult( match ) );

                setOutput( EColorData.EMotorState.ON );
                mIsDone = false;
                return false;
            }
            else if ( eMotorState == EColorData.EMotorState.ON ) {
                mIsDone = true;
                setOutput( EColorData.EMotorState.ON );
                return true;
            }
            else {
                mIsDone = false;
                setOutput( EColorData.EMotorState.OFF );
                return false;
            }
        }
        return true;
    }

    private void setOutput(EColorData.EMotorState pDesiredMotorState) {
        Data mData = Robot.DATA;
        mData.color.set( EColorData.COLOR_WHEEL_MOTOR_STATE, pDesiredMotorState.ordinal() );
    }

    protected static String getColorStringForMatchResult(ColorMatchResult match) {
        String colorString = "NULL MATCH";
        if(match != null) {
            if (kBlueTarget.equals(match.color)) {
                colorString = "Blue";
            } else if (kRedTarget.equals(match.color)) {
                colorString = "Red";
            } else if (kGreenTarget.equals(match.color)) {
                colorString = "Green";
            } else if (kYellowTarget.equals(match.color)) {
                colorString = "Yellow";
            } else {
                colorString =
                        "Unknown: " + ((match.color != null) ?
                                "RGB={" + match.color.red + ", " + match.color.green + ", " + match.color.blue + "}" : "null");
            }
        }
        return colorString;
    }

    public boolean setColorState( Color color ) {
        if (kBlueTarget.equals(color)) {
            eCurrentColorState = EColorData.EColor.BLUE;
        } else if (kRedTarget.equals(color)) {
            eCurrentColorState = EColorData.EColor.RED;
        } else if (kGreenTarget.equals(color)) {
            eCurrentColorState = EColorData.EColor.GREEN;
        } else if (kYellowTarget.equals(color)) {
            eCurrentColorState = EColorData.EColor.YELLOW;
        } else {
            return false;
        }
        return true;
    }

    private int getInitialStateLocation( EColorData.EColor pColorState ){
        switch ( pColorState ) {
            case RED: return 0;
            case YELLOW: return 1;
            case BLUE: return 2;
            case GREEN: return 3;
            default: return -1;
        }
    }

    private EColorData.EColor getStateAtLocation( int i ) {
        switch ( i % colorStates.length ) {
            case 0: return EColorData.EColor.RED;
            case 1: return EColorData.EColor.YELLOW;
            case 2: return EColorData.EColor.BLUE;
            case 3: return EColorData.EColor.GREEN;
            default: return EColorData.EColor.NONE;
        }
    }

    public Color updateColor() {
        Color detectedColor = mColorSensorV3.getColor();
        ColorMatchResult match = mColorMatcher.matchClosestColor( detectedColor );
        eLastColorState = eCurrentColorState;
        setColorState( detectedColor );

        Data mData = Robot.DATA;
        if (match != null) {
            if (kBlueTarget.equals(match.color)) {
                mData.color.set( EColorData.SENSED_COLOR, (double)EColorData.EColor.BLUE.ordinal() );
                return kBlueTarget;
            } else if (kRedTarget.equals(match.color)) {
                mData.color.set(EColorData.SENSED_COLOR, (double)EColorData.EColor.RED.ordinal());
                return kRedTarget;
            } else if (kGreenTarget.equals(match.color)) {
                mData.color.set(EColorData.SENSED_COLOR, (double)EColorData.EColor.GREEN.ordinal());
                return kGreenTarget;
            } else if (kYellowTarget.equals(match.color)) {
                mData.color.set(EColorData.SENSED_COLOR, (double)EColorData.EColor.YELLOW.ordinal());
                return kYellowTarget;
            } else {
                mData.color.set(EColorData.SENSED_COLOR, (double)EColorData.EColor.NONE.ordinal());
                return null;
            }
        }
        return null;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void updateMotor( EColorData.EMotorState motorState ){
        eMotorState = motorState;
    }

    @Override
    public void readInputs(double pNow) {

    }

    @Override
    public void setOutputs(double pNow) {

    }

    @Override
    public void shutdown(double pNow) {
        victorSPX.set(ControlMode.PercentOutput, 0d);
    }
}