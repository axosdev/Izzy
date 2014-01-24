package engine.gfx.api;

import org.lwjgl.LWJGLException;

import static org.lwjgl.opengl.ARBFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;

public class FrameBuffer {
    private int id;
    private Texture texture;

    public FrameBuffer(Texture texture) throws LWJGLException {
        this.texture = texture;

        texture.bind();

        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.getID(), 0);

        int res = glCheckFramebufferStatus(GL_FRAMEBUFFER);

        if(res != GL_FRAMEBUFFER_COMPLETE) {
            glBindFramebuffer(GL_FRAMEBUFFER, 0);
            glDeleteFramebuffers(id);

            throw new LWJGLException("Fuck, " + res + " FBOS");
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public int getID() {
        return id;
    }

    public Texture getTexture() {
        return texture;
    }

    public void begin() {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void end() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
}
