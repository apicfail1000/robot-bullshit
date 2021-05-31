package frc.robot.commands.auto.MoveTypes;

import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
//WPI imports
import edu.wpi.first.wpilibj2.command.CommandBase;
//RobotContainer import
import frc.robot.RobotContainer;
import frc.robot.commands.auto.MoveRobot;
import frc.robot.Globals;
//Subsystem imports
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Sensor;
/**
 * SimpleDrive class
 * <p>
 * This class drives a motor 
 */
public class MoveType2A extends CommandBase
{
    //Grab the subsystem instance from RobotContainer
    private static final OmniDrive m_drive = RobotContainer.m_omnidrive;
    private static final Sensor m_sensor = RobotContainer.m_sensor;
    private double dT = 0.02;
    private boolean m_endFlag = false;
    private int m_profType;
    private final TrapezoidProfile.Constraints m_constraints;
    private TrapezoidProfile.State m_goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_setpoint = new TrapezoidProfile.State();
    private final int m_dir;
    private double sensDist;

    /**
     * Constructor
     */
    //This move the robot a certain distance following a trapezoidal speed profile.
    public MoveType2A(int type, double dist, double startSpeed, double endSpeed, double maxSpeed, double setDist)
    {
        addRequirements(m_drive); // Adds the subsystem to the command
        sensDist = setDist;
        m_profType = type;
        if (type==2){
            m_constraints = new TrapezoidProfile.Constraints(maxSpeed, 2.0*Math.PI);
        }
        else{
            m_constraints = new TrapezoidProfile.Constraints(maxSpeed, 0.8);
        }
        m_setpoint = new TrapezoidProfile.State(0, startSpeed);
        
        //Negative distance don't seem to work with the library function????
        //Easier to make distance positive and use m_dir to keep track of negative speed.
        m_dir = (dist>0)?1:-1;
        dist *= m_dir;          
        
        m_goal = new TrapezoidProfile.State(dist, endSpeed);

    }

    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {
        
    }

    /**
     * Called continously until command is ended
     */
    @Override
    public void execute()
    {

        //Create a new profile to calculate the next setpoint(speed) for the profile
        var profile = new TrapezoidProfile(m_constraints, m_goal, m_setpoint);
        m_setpoint = profile.calculate(dT);
        
        if (m_sensor.getSonicDistance1(true) <= sensDist){

            m_drive.setRobotSpeedType(m_profType, m_setpoint.velocity*m_dir);
        }
        else {
            //distance reached. End the command
            //This class should be modified so that the profile can end on other conditions like
            //sensor value etc.
            m_drive.setRobotSpeedType(m_profType, m_goal.velocity*m_dir);
            m_endFlag = true;
        }
//
    }

    /**
     * Called when the command is told to end or is interrupted
     */


    /**
     * Creates an isFinished condition if needed
     */
    @Override
    public boolean isFinished()
    {
        return m_endFlag;
    }

    @Override
    public void end(boolean interrupted){
        MoveRobot.distMoved = MoveRobot.getDistMoved();
    }

}