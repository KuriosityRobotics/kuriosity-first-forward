package com.kuriosityrobotics.firstforward.robot.modules;

import static com.kuriosityrobotics.firstforward.robot.math.MathUtil.max;

import com.kuriosityrobotics.firstforward.robot.Robot;
import com.kuriosityrobotics.firstforward.robot.telemetry.Telemeter;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;


public class DrivetrainModule implements Module, Telemeter {
    private final Robot robot;
    private final boolean isOn = true;

    //states
    public double xMov = 0;
    public double yMov = 0;
    public double turnMov = 0;

    //motors
    private DcMotor fLeft;
    private DcMotor fRight;
    private DcMotor bLeft;
    private DcMotor bRight;

    public DrivetrainModule(Robot robot) {
        this.robot = robot;
        init();

//        robot.telemetryDump.registerTelemeter(this);
    }

    //updates motor power
    public void update() {
        double fLPower = yMov + turnMov + xMov;
        double fRPower = yMov - turnMov - xMov;
        double bLPower = yMov + turnMov - xMov;
        double bRPower = yMov - turnMov + xMov;

        double scale = scaleDown(fLPower, fRPower, bLPower, bRPower);

        fLPower *= scale;
        fRPower *= scale;
        bLPower *= scale;
        bRPower *= scale;

        setMotorPowers(fLPower, fRPower, bLPower, bRPower);
    }

    //scale down motor power so largest/smallest is 1/-1
    public double scaleDown(double a, double b, double c, double d) {
        double max = max(a, b, c, d);
        if (max < 1) {
            return 1;
        }
        return max;
    }

    public void setMovements(double xMov, double yMov, double turnMov) {
        this.xMov = xMov;
        this.yMov = yMov;
        this.turnMov = turnMov;
        //Log.i("DrivetrainModule", "xMov " + xMov);
        //Log.i("DrivetrainModule", "yMov " + yMov);
        //Log.i("DrivetrainModule", "turnMov " + turnMov);
    }

    private void setMotorPowers(double fLPower, double fRPower, double bLPower, double bRPower) {
        setMotorPower(fLeft, fLPower);
        setMotorPower(fRight, fRPower);
        setMotorPower(bLeft, bLPower);
        setMotorPower(bRight, bRPower);
    }

    private void setMotorPower(DcMotor motor, double power) {
        if (Math.abs(power) < 0.06) {
            motor.setPower(0);
        } else {
            motor.setPower(power);
        }
    }

    public void init() {
        fLeft = robot.getDcMotor("fLeft");
        fRight = robot.getDcMotor("fRight");
        bLeft = robot.getDcMotor("bLeft");
        bRight = robot.getDcMotor("bRight");

        fLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        fRight.setDirection(DcMotorSimple.Direction.FORWARD);
        bLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        bRight.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public boolean isOn() {
        return isOn;
    }

    public String getName() {
        return "DrivetrainModule";
    }

    @Override
    public ArrayList<String> getTelemetryData() {
        ArrayList<String> data = new ArrayList<>();

        data.add("xMov: " + xMov);
        data.add("yMov: " + yMov);
        data.add("turnMov: " + turnMov);

        return data;
    }
}
