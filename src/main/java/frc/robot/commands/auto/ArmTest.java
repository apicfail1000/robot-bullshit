package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Sensor;


public class ArmTest extends AutoCommand {

    private final static Sensor m_sensor = RobotContainer.m_sensor;
    
    private static double spd = 0.3;
    public ArmTest(){
       
        super(
            new MoveRobot(0, 0.5, 0, 0, spd),
            new MoveRobotSense(1, 20, 0, 0, spd, ()-> m_sensor.getIRDistance1()<50),
            new MoveRobot(2, -Math.PI/2, 0, 0, spd),
            new MoveRobotSense(1, 5, 0, 0, spd, ()-> m_sensor.getCobraTotal()<5000),
            new MoveRobot(1, -0.2, 0, 0, spd),
            new MoveRobotSense(0, -5, 0, 0, spd, ()-> m_sensor.getCobraTotal()<5000),
            new MoveRobot(1, 0.2, 0, 0, spd)

        );
    }

}