package engine.math;

public class Vector3f {
    public static final Vector3f UP      = new Vector3f(0, 1, 0);
    public static final Vector3f DOWN    = new Vector3f(0, -1, 0);
    public static final Vector3f LEFT    = new Vector3f(-1, 0, 0);
    public static final Vector3f RIGHT   = new Vector3f(1, 0, 0);
    public static final Vector3f FORWARD = new Vector3f(0, 0, 1);
    public static final Vector3f BACK    = new Vector3f(0, 0, -1);
    public static final Vector3f ZERO    = new Vector3f(0, 0, 0);
    public static final Vector3f ONE     = new Vector3f(1, 1, 1);

    private float x, y, z;

    public Vector3f() {
        this(0, 0, 0);
    }

    public Vector3f(Vector3f other) {
        this(other.getX(), other.getY(), other.getZ());
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public float length() {
        return (float)Math.sqrt(lengthSquared());
    }

    public float distance(Vector3f other) {
        return this.sub(other).length();
    }

    public float angleBetween(Vector3f other) {
        float cosTheta = this.dot(other)/(this.length() * other.length());

        return (float)Math.acos(cosTheta);
    }

    public float dot(Vector3f other) {
        return x * other.getX() + y * other.getY() + z * other.getZ();
    }

    public Vector3f cross(Vector3f other) {
        float xx = y * other.getZ() - z * other.getY();
        float yy = z * other.getX() - x * other.getZ();
        float zz = x * other.getY() - y * other.getX();

        return new Vector3f(xx, yy, zz);
    }

    public Vector3f normalized() {
        float length = length();

        return new Vector3f(x / length, y / length, z / length);
    }

    //TODO: Function to normalize _this_ vector?

    public Vector3f min(Vector3f other) {
        return new Vector3f(Math.min(x, other.getX()), Math.min(y, other.getY()), Math.min(z, other.getZ()));
    }

    public Vector3f max(Vector3f other) {
        return new Vector3f(Math.max(x, other.getX()), Math.max(y, other.getY()), Math.max(z, other.getZ()));
    }

    public Vector3f clamp(float maxLength) {
        if(lengthSquared() <= maxLength * maxLength)
            return this;

        return this.normalized().mul(maxLength);
    }

    public Vector3f towards(Vector3f other, float amt) {
        Vector3f res = other.sub(this);

        if(res.length() < amt)
            return this.add(res);

        return this.add(res.normalized().mul(amt));
    }

    public Vector3f rotate(float angle, Vector3f axis) {
        return this.cross(axis.mul((float)Math.sin(Math.toRadians(-angle)))).add(this.mul((float) Math.cos(Math.toRadians(-angle))));
    }

    public Vector3f rotate(Quaternion rotation) {
        float x1 = rotation.getY() * z - rotation.getZ() * y;
        float y1 = rotation.getZ() * x - rotation.getX() * z;
        float z1 = rotation.getX() * y - rotation.getY() * x;

        float x2 = rotation.getW() * x1 + rotation.getY() * z1 - rotation.getZ() * y1;
        float y2 = rotation.getW() * y1 + rotation.getZ() * x1 - rotation.getX() * z1;
        float z2 = rotation.getW() * z1 + rotation.getX() * y1 - rotation.getY() * x1;

        return new Vector3f(x + 2.0f * x2, y + 2.0f * y2, z + 2.0f * z2);
    }

    public Vector3f lerp(Vector3f newVector, float amt) {
        return this.sub(newVector).mul(amt).add(newVector);
    }

    public Vector3f add(Vector3f other) {
        return new Vector3f(x + other.getX(), y + other.getY(), z + other.getZ());
    }

    public Vector3f add(float scalar) {
        return new Vector3f(x + scalar, y + scalar, z + scalar);
    }

    public Vector3f sub(Vector3f other) {
        return new Vector3f(x - other.getX(), y - other.getY(), z - other.getZ());
    }

    public Vector3f sub(float scalar) {
        return new Vector3f(x - scalar, y - scalar, z - scalar);
    }

    public Vector3f mul(Vector3f other) {
        return new Vector3f(x * other.getX(), y * other.getY(), z * other.getZ());
    }

    public Vector3f mul(float scalar) {
        return new Vector3f(x * scalar, y * scalar, z * scalar);
    }

    public Vector3f div(Vector3f other) {
        return new Vector3f(x / other.getX(), y / other.getY(), z / other.getZ());
    }

    public Vector3f div(float scalar) {
        return new Vector3f(x / scalar, y / scalar, z / scalar);
    }

    public Vector3f abs() {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Quaternion rotationBetween(Vector3f other) {
        Vector3f temp = this.cross(other);
        float w = (float)Math.sqrt((this.length() * other.length())) + this.dot(other);

        return new Quaternion(temp.getX(), temp.getY(), temp.getZ(), w);
    }

    public String toString() {
        return "(" + x + " " + y + " " + z + ")";
    }

    public boolean equals(Vector3f other) {
        return other.getX() == x && other.getY() == y && other.getZ() == z;
    }

    public Vector3f get() {
        return new Vector3f(x, y, z);
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3f other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public Vector2f getXY() {
        return new Vector2f(x,y);
    }

    public Vector2f getXZ() {
        return new Vector2f(x,z);
    }

    public Vector2f getYZ() {
        return new Vector2f(y,z);
    }

    public Vector2f getYX() {
        return new Vector2f(y,x);
    }

    public Vector2f getZX() {
        return new Vector2f(z,x);
    }

    public Vector2f getZY() {
        return new Vector2f(z,y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
