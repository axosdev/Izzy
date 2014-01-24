import engine.gfx.api.*;
import engine.gfx.lights.*;
import engine.gfx.mesh.Material;
import engine.gfx.mesh.Mesh;
import engine.gfx.mesh.Vertex;
import engine.gfx.shader.BasicShader;
import engine.gfx.shader.PhongShader;
import engine.gfx.shader.Shader;
import engine.math.Transform;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.util.Time;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glClear;

public class Game {
    private Mesh mesh;
    private Shader shader;
    private Material material;
    private Texture tex;
    private Transform transform;
    private Camera camera;

    private FrameBuffer fbo;
    private Material fboMat;
    private Shader fboShader;

    PointLight pLight1 = new PointLight(new BaseLight(new Vector3f(1, 0.7f, 0), 0.8f), new Attenuation(0, 0, 1), new Vector3f(-2, 0, 5f), 10);
    PointLight pLight2 = new PointLight(new BaseLight(new Vector3f(0, 0.5f, 1), 0.8f), new Attenuation(0, 0, 1), new Vector3f(2, 0, 7f), 10);

    SpotLight sLight1 = new SpotLight(new PointLight(new BaseLight(new Vector3f(0, 1f, 1f), 0.8f), new Attenuation(0, 0, 0.1f), new Vector3f(-2, 0, 5f), 30),
            new Vector3f(1, 1, 1), 0.7f);

    public Game() {
        tex = new Texture("test.png");
        fboMat = new Material(tex, new Vector3f(1, 1, 1), -1, -1);
        material = new Material(tex, new Vector3f(1, 1, 1), 1, 8);
        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

        try {
            fbo = new FrameBuffer(new Texture("test.png"));
        } catch(LWJGLException e) {e.printStackTrace();}

        Vertex[] vertices = new Vertex[]{new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        int indices[] = {0, 1, 2,
                2, 1, 3};

        mesh = new Mesh(vertices, indices, true);
        shader = PhongShader.getInstance();
        fboShader = BasicShader.getInstance();
        camera = new Camera();
        transform = new Transform();

        Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
        Transform.setCamera(camera);

        PhongShader.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
        PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3f(1, 1, 1), 0.1f), new Vector3f(1, 1, 1)));

        PhongShader.setPointLight(new PointLight[]{pLight1, pLight2});
        PhongShader.setSpotLights(new SpotLight[]{sLight1});
    }

    public void input() {
        camera.input();
    }

    float temp = 0.0f;

    public void update() {
        temp += Time.getDelta();

        float sinTemp = (float) Math.sin(temp);

        transform.setTranslation(0, -1, 5);
        //transform.setRotation(0, sinTemp * 180, 0);

        pLight1.setPosition(new Vector3f(3, 0, 8.0f * (float) (Math.sin(temp) + 1.0 / 2.0) + 10));
        pLight2.setPosition(new Vector3f(7, 0, 8.0f * (float) (Math.cos(temp) + 1.0 / 2.0) + 10));

        //transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
        sLight1.getPointLight().setPosition(camera.getPos());
        sLight1.setDirection(camera.getForward());
    }

    public void render() {
        RenderUtil.setClearColor(new Vector3f(0, 0.5f, 0.5f));

        fbo.begin();

        glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        shader.bind();
        shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
        mesh.draw();

        fbo.end();

        fboMat.setTexture(fbo.getTexture());

        fboShader.bind();
        fboShader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), fboMat);

        mesh.draw();
    }
}
