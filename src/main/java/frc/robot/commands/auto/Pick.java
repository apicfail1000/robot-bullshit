package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.OmniDrive;


public class Pick extends CommandGroupBase {

  public Pick() {

    super();


        

  }




  @Override
  public void addCommands(Command... commands) {
      parallel(new MoveArmXY(Globals.xTgt, Globals.yTgt, 0.0, 0.0, 0.5),
      new MoveRobot(0, Globals.zTgt,  0.0, 0.0, 0.5));

  }




}