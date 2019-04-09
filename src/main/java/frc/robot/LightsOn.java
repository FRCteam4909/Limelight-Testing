package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class LightsOn extends Command{
    public LightsOn(){
        requires(Robot.vision);
    }
@Override
    protected void initialize() {
        Robot.vision.setLights(3);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
    
    @Override
    protected void end() {
        Robot.vision.setLights(1);
    }
}