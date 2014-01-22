package engine.math;

public class IzzyMath {
    public static float approach(float g, float c, float dt) {
        float difference = g - c;

        if(difference > dt)
            return c + dt;
        if(difference < -dt)
            return c - dt;

        return 0;
    }
}
