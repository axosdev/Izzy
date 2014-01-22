package engine.math;

public class Vector2f {
    private float x, y;

    public Vector2f() {
        this(0, 0);
    }

    public Vector2f(Vector3f other) {
        this(other.getX(), other.getY());
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float length() {
        return (float)Math.sqrt(lengthSquared());
    }

    public float distance(Vector2f other) {
        return this.sub(other).length();
    }

    public float angleBetween(Vector2f other) {
        float cosTheta = this.dot(other)/(this.length() * other.length());

        return (float)Math.acos(cosTheta);
    }

    public float dot(Vector2f other) {
        return x * other.getX() + y * other.getY();
    }

    public float cross(Vector2f other) {
        return x * other.getY() - y * other.getX();
    }

    public Vector2f normalized() {
        float length = length();

        return new Vector2f(x / length, y / length);
    }

    public Vector2f min(Vector2f other) {
        return new Vector2f(Math.min(x, other.getX()), Math.min(y, other.getY()));
    }

    public Vector2f max(Vector2f other) {
        return new Vector2f(Math.max(x, other.getX()), Math.max(y, other.getY()));
    }

    public Vector2f clamp(float maxLength) {
        if(lengthSquared() <= maxLength * maxLength)
            return this;

        return this.normalized().mul(maxLength);
    }

    public Vector2f towards(Vector2f other, float amt) {
        Vector2f result = this.sub(other);

        if(result.length() < amt)
            return result;

        return result.normalized().mul(amt);
    }

    public Vector2f rotate(float angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float)(x * cos - y * sin),(float)(x * sin + y * cos));
    }

    public Vector2f add(Vector2f other) {
        return new Vector2f(x + other.getX(), y + other.getY());
    }

    public Vector2f add(float scalar) {
        return new Vector2f(x + scalar, y + scalar);
    }

    public Vector2f sub(Vector2f other) {
        return new Vector2f(x - other.getX(), y - other.getY());
    }

    public Vector2f sub(float scalar) {
        return new Vector2f(x - scalar, y - scalar);
    }

    public Vector2f mul(Vector2f other) {
        return new Vector2f(x * other.getX(), y * other.getY());
    }

    public Vector2f mul(float scalar) {
        return new Vector2f(x * scalar, y * scalar);
    }

    public Vector2f div(Vector2f other) {
        return new Vector2f(x / other.getX(), y / other.getY());
    }

    public Vector2f div(float other) {
        return new Vector2f(x / other, y / other);
    }

    public Vector2f abs() {
        return new Vector2f(Math.abs(x), Math.abs(y));
    }

    public Vector2f lerp(Vector2f newVector, float amt) {
        return this.sub(newVector).mul(amt).add(newVector);
    }

    public String toString() {
        return "(" + x + " " + y + ")";
    }

    public boolean equals(Vector2f other) {
        return other.getX() == x && other.getY() == y;
    }

    public Vector2f get() {
        return new Vector2f(x, y);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2f other) {
        this.x = other.x;
        this.y = other.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
