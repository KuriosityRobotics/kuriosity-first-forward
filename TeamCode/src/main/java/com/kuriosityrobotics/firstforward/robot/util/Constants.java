package com.kuriosityrobotics.firstforward.robot.util;

import static com.kuriosityrobotics.firstforward.robot.util.Constants.Units.MM_PER_INCH;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Constants {

    public static class OpModes {
        public static final double JOYSTICK_EPSILON = 0.1;
    }

    public static class Dashboard {
        public static final double ROBOT_RADIUS = 6; // in
    }

    public static final class Units{
        public static final float MM_PER_INCH = 25.4f;
    }

    public static class Field{

        // Constants for perimeter targets
        public static final float TARGET_HEIGHT_MM = 5.75f * MM_PER_INCH;
        public static final float TILE_MEAT_MM = 23f * MM_PER_INCH;
        public static final float HALF_TILE_MEAT_MM = TILE_MEAT_MM * 0.5f;
        public static final float TILE_TAB_MM = 0.5f * MM_PER_INCH;
        public static final float FULL_FIELD_MM = 6f * TILE_MEAT_MM + 5f * TILE_TAB_MM;
        public static final float HALF_FIELD_MM = FULL_FIELD_MM * 0.5f;

        // Hub Positions
        public static final float RED_HUB_X_MM = 2f * TILE_MEAT_MM + 1.5f * TILE_TAB_MM;
        public static final float RED_HUB_Y_MM = 3.5f * TILE_MEAT_MM + 3f * TILE_TAB_MM;

        public static final float BLUE_HUB_X_MM = FULL_FIELD_MM - RED_HUB_X_MM;
        public static final float BLUE_HUB_Y_MM = RED_HUB_Y_MM;

        public static final float SHARE_HUB_X_MM = HALF_FIELD_MM;
        public static final float SHARE_HUB_Y_MM = HALF_TILE_MEAT_MM;
    }

    public static class Webcam {

        // Camera positions on robot (both front and left)
        // it is correct but vuforia sucks when it is too close to the wall target(2-3 inches off)
        public static final float SERVO_FORWARD_DISPLACEMENT_MM = 4.821f * MM_PER_INCH;
        public static final float SERVO_VERTICAL_DISPLACEMENT_MM = 16.404f * MM_PER_INCH;
        public static final float SERVO_LEFT_DISPLACEMENT_MM = 0.318f * MM_PER_INCH;

        // camera pos relative to the turret servo
        public static final float CAMERA_VARIABLE_DISPLACEMENT_MM = 3.365f * MM_PER_INCH;
        public static final float CAMERA_VERTICAL_DISPLACEMENT_MM = 0f * MM_PER_INCH;

        public static final String VUFORIA_LICENCE_KEY =
                "AWPSm1P/////AAABmfp26UJ0EUAui/y06avE/y84xKk68LTTAP3wBE75aIweAnuSt" +
                        "/zSSyaSoqeWdTFVB5eDsZZOP/N/ISBYhlSM4zrkb4q1YLVLce0aYvIrso" +
                        "GnQ4Iw/KT12StcpQsraoLewErwZwf3IZENT6aWUwODR7vnE4JhHU4+2Iy" +
                        "ftSR0meDfUO6DAb4VDVmXCYbxT//lPixaJK/rXiI4o8NQt59EIN/W0RqT" +
                        "ReAehAZ6FwBRGtZFyIkWNIWZiuAPXKvGI+YqqNdL7ufeGxITzc/iAuhJz" +
                        "NZOxGXfnW4sHGn6Tp+meZWHFwCYbkslYHvV5/Sii2hR5HGApDW0oDml6g" +
                        "OlDmy1Wmw6TwJTwzACYLKl43dLL35G";
    }

    public static class Detect {
        public static final Vector3D RED = new Vector3D(255, 0, 0);
    }
}
