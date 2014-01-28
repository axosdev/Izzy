package engine.gfx.shader;

import engine.gfx.api.RenderUtil;
import engine.gfx.mesh.Material;
import engine.math.Matrix4f;
import engine.math.Vector2f;

public class BlurShader extends Shader {
    private static final BlurShader instance = new BlurShader();

    public static BlurShader getInstance() {
        return instance;
    }

    public float radius = 4f;
    public float resolution = 1024;
    public Vector2f dir = new Vector2f(1, 0);

    private BlurShader() {
        super();

        addVertexShaderFromFile("BlurVertex.glsl");
        addFragmentShaderFromFile("BlurFragment.glsl");
        compileShader();

        addUniform("transform");
        addUniform("color");
        addUniform("resolution");
        addUniform("radius");
        addUniform("dir");
    }

    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
        if (material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniform("transform", projectedMatrix);
        setUniform("color", material.getColor());

        setUniformf("resolution", resolution);
        setUniform2f("dir", dir);
        setUniformf("radius", radius);
    }

    public void setDir(Vector2f dir) {
        this.dir = dir;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setResolution(float resolution) {
        this.resolution = resolution;
    }
}
