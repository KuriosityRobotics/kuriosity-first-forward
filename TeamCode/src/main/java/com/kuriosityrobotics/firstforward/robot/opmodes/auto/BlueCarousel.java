package com.kuriosityrobotics.firstforward.robot.opmodes.auto;

import com.kuriosityrobotics.firstforward.robot.Robot;
import com.kuriosityrobotics.firstforward.robot.modules.intake.IntakeModule;
import com.kuriosityrobotics.firstforward.robot.modules.outtake.OuttakeModule;
import com.kuriosityrobotics.firstforward.robot.pathfollow.PurePursuit;
import com.kuriosityrobotics.firstforward.robot.pathfollow.VelocityLock;
import com.kuriosityrobotics.firstforward.robot.pathfollow.WayPoint;
import com.kuriosityrobotics.firstforward.robot.pathfollow.motionprofiling.MotionProfile;
import com.kuriosityrobotics.firstforward.robot.util.Constants;
import com.kuriosityrobotics.firstforward.robot.util.math.Point;
import com.kuriosityrobotics.firstforward.robot.util.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class BlueCarousel extends LinearOpMode {
    public static final Pose START = Pose.fieldMirror(9.75, (23.5 * 5) - 0.5 - (11.5 / 2), Math.toRadians(-90));

    public static final Pose WOBBLE = Pose.fieldMirror(28, 100, Math.toRadians(-40));

    public static final Point PRE_CAROUSEL = Point.fieldMirror(17, 120);
    public static final Pose CAROUSEL = Pose.fieldMirror(16.75, 132, Math.toRadians(-75));

    public static final Pose PARK = Pose.fieldMirror(35, 5 * 23.5 + 12, Math.toRadians(-90));

    public void runOpMode() {
        Robot robot = null;
        try {
            robot = new Robot(hardwareMap, telemetry, this);
        } catch (Exception e) {
            this.stop();
            e.printStackTrace();
            return;
        }

        Robot.isBlue = true;
        Robot.isCarousel = true;

        robot.resetPose(START);

        OuttakeModule.VerticalSlideLevel detected = AutoPaths.delayedStartLogic(this, robot, START);

        PurePursuit toWobble = new PurePursuit(new WayPoint[]{
                new WayPoint(START, new VelocityLock(10, false), robot.outtakeModule.extendOuttakeAction(detected)),
                new WayPoint(START.between(WOBBLE), new VelocityLock(0.4 * MotionProfile.ROBOT_MAX_VEL, false)),
                new WayPoint(WOBBLE, 0, robot.outtakeModule.dumpOuttakeAction())
        }, true, 4);

        PurePursuit toCarousel = new PurePursuit(new WayPoint[]{
                new WayPoint(WOBBLE),
                new WayPoint(PRE_CAROUSEL, 13),
                new WayPoint(CAROUSEL, 0, robot.carouselModule.carouselAction())
        }, false, 4);

        if (detected == OuttakeModule.VerticalSlideLevel.DOWN_NO_EXTEND) {
            Pose drop = new Pose(Constants.Field.FULL_FIELD - 48, 108, Math.toRadians(5));
            toWobble = new PurePursuit(new WayPoint[]{
                    new WayPoint(START, new VelocityLock(10, false), robot.outtakeModule.extendOuttakeAction(OuttakeModule.VerticalSlideLevel.DOWN)),
                    new WayPoint(START.add(new Pose(-15, 0, 0)), new VelocityLock(0.4 * MotionProfile.ROBOT_MAX_VEL, false)),
                    new WayPoint(drop, 0, robot.outtakeModule.dumpOuttakeAction())
            }, true, 3);

            toCarousel = new PurePursuit(new WayPoint[]{
                    new WayPoint(drop),
                    new WayPoint(PRE_CAROUSEL, 13),
                    new WayPoint(CAROUSEL, 0, robot.carouselModule.carouselAction())
            }, false, 4);
        }

        PurePursuit toPark = new PurePursuit(new WayPoint[]{
                new WayPoint(CAROUSEL),
                new WayPoint(PARK, new VelocityLock(0))
        }, true, 4);

        robot.followPath(toWobble);
        robot.intakeModule.targetIntakePosition = IntakeModule.IntakePosition.STAY_RETRACTED;
        robot.followPath(toCarousel);
        robot.followPath(toPark);

        Robot.isCarousel = false;
    }
}