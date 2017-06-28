package org.a3dgc.terraingeneration.Geometry;

import org.a3dgc.terraingeneration.Utils.HeightMap;
/**
 * Created by Gianmarco.
 */
public class Plane extends Mesh {

    public float[] vertex;
    public float[] index;
    private static final int COORDS_PER_VERTEX = 7;
private int offset;
    private int vertexCount;
    private int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex


    public Plane(float width, float height, int widthSegments,
                 int heightSegments,float offsetY,float offsetX) {

        offset=widthSegments;
        HeightMap hp = new HeightMap(widthSegments);
        float[] vertices = new float[((widthSegments + 1) * (heightSegments + 1)) * (4 + 3)];
        short[] indices = new short[(widthSegments + 1) * (heightSegments + 1)
                * 6];
        vertexCount = vertices.length / COORDS_PER_VERTEX;
        float xOffset = width / -2;
        float yOffset = height / -2;
        float xWidth = width / (widthSegments);
        float yHeight = height / (heightSegments);
        int currentVertex = 0;
        int currentIndex = 0;
        float fromCenterX=offsetX*width;
        float fromCenterY=offsetY*height;
        float factor=offsetX/offsetX;
        double z0;
        double w0;
        short w = (short) (widthSegments + 1);
        for (int y = 0; y < heightSegments + 1; y++) {
            for (int x = 0; x < widthSegments + 1; x++) {
                vertices[currentVertex] = xOffset + x * xWidth +fromCenterX;
                vertices[currentVertex + 1] = yOffset + y * yHeight+fromCenterY;
                z0=HeightMap.noise((double)vertices[currentVertex],(double)vertices[currentVertex + 1]);
                w0=HeightMap.noise((double)vertices[currentVertex],(double)vertices[currentVertex + 1],z0);
                vertices[currentVertex + 2] = (float)HeightMap.noise((double)vertices[currentVertex],(double)vertices[currentVertex + 1],z0,w0);
                HeightMap.setColor(vertices[currentVertex + 2]);
                vertices[currentVertex + 3] =HeightMap.getR() ;
                vertices[currentVertex + 4] =HeightMap.getG() ;
                vertices[currentVertex + 5] =HeightMap.getB() ;
                vertices[currentVertex + 6] = 1;
                currentVertex += 7;

                int n = y * (widthSegments + 1) + x;

                if (y < heightSegments && x < widthSegments) {
                    // Face one
                    indices[currentIndex] = (short) n;
                    indices[currentIndex + 1] = (short) (n + 1);
                    indices[currentIndex + 2] = (short) (n + w);
                    // Face two
                    indices[currentIndex + 3] = (short) (n + 1);
                    indices[currentIndex + 4] = (short) (n + 1 + w);
                    indices[currentIndex + 5] = (short) (n + 1 + w - 1);

                    currentIndex += 6;
                }
            }
        }
        vertex = vertices;
        setIndices(indices);
        //setVertices(vertices);

    }


    public void wave(float factor) {

        int currentVertex = 2;
        for (int y = 0; y < offset + 1; y++) {
            for (int x = 0; x < offset + 1; x++) {
                vertex[currentVertex] = (float) Math.cos(vertex[currentVertex - 2] + factor);
                currentVertex += 6;
            }
        }
    }
}