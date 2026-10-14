package io.github.gariestgary.cucumbass.client;

/** Model-space parameters (pixels) for the primitive installed-cucumber model. */
public final class CucumberRenderGeometry {
    public static final float THICKNESS = 2.0F;
    public static final float LENGTH = 8.0F;
    public static final float ATTACH_X = 0.0F;
    public static final float ATTACH_Y = 11.0F;
    public static final float ATTACH_Z = 2.1F;
    public static final float BODY_PENETRATION = 0.4F;
    private static final float[] SIDE_ANGLES = {0.0F, -25.0F, 25.0F, -42.0F, 42.0F};
    private static final float[] UP_ANGLES = {0.0F, 0.0F, 0.0F, 20.0F, 20.0F};

    private CucumberRenderGeometry() { }

    public static float sideAngle(int index) { return index < SIDE_ANGLES.length ? SIDE_ANGLES[index] : overflowSideAngle(index); }
    public static float upAngle(int index) { return index < UP_ANGLES.length ? UP_ANGLES[index] : 28.0F; }

    private static float overflowSideAngle(int index) {
        int overflow = index - SIDE_ANGLES.length;
        int magnitude = 55 + (overflow / 2) * 10;
        return (overflow & 1) == 0 ? -magnitude : magnitude;
    }
}
