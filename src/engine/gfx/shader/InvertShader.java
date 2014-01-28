package engine.gfx.shader;

import engine.gfx.api.RenderUtil;
import engine.gfx.mesh.Material;
import engine.math.Matrix4f;

public class InvertShader extends Shader {
    private static final InvertShader instance = new InvertShader();

    public static InvertShader getInstance() {
        return instance;
    }

    private InvertShader() {
        super();

        addVertexShaderFromFile("invertVertex.glsl");
        addFragmentShaderFromFile("invertFragment.glsl");
        compileShader();

        addUniform("transform");
        addUniform("color");
    }

    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
        if (material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniform("transform", projectedMatrix);
        setUniform("color", material.getColor());
    }
}
