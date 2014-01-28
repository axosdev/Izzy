package engine.gfx.api;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int id;
    private int width;
    private int height;

    public Texture(String fileName) {
        this(loadTexture(fileName));
    }

    public Texture(int width, int height) {
        this(generateTexture(width, height));
    }

    public Texture(int[] data) {
        id = data[0];
        width = data[1];
        height = data[2];
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getID() {
        return id;
    }

    private static int[] loadTexture(String fileName) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        try {
            int[] data = new int[3];
            org.newdawn.slick.opengl.Texture tempTex = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/textures/" + fileName)));
            data[0] = tempTex.getTextureID();
            data[1] = (int)tempTex.getWidth();
            data[2] = (int)tempTex.getHeight();

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return new int[] {0, 0, 0};
    }

    private static int[] generateTexture(int width, int height) {
        int id = glGenTextures();

        ByteBuffer buf = BufferUtils.createByteBuffer(width * height * 4);

        for(int i = 0; i < width * height; i++) {
            buf.put((byte) 255);
            buf.put((byte) 255);
            buf.put((byte) 255);
            buf.put((byte) 255);
        }

        buf.flip();

        // 'select' the new texture by it's handle
        glBindTexture(GL_TEXTURE_2D, id);
        // set texture parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR); //GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); //GL_NEAREST);
        // Create the texture from pixels
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        glBindTexture(GL_TEXTURE_2D, 0);

        return new int[] {id, width, height};
    }
}
