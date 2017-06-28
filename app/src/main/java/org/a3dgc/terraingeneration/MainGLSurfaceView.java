package org.a3dgc.terraingeneration;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Gianmarco.
 */

class MainGLSurfaceView extends GLSurfaceView {

    private final float TOUCH_SCALE_FACTOR = 180.0f / 3200;
    private float mPreviousX;
    private float mPreviousY;
    private final MainGLRenderer mRenderer;

    public MainGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 3.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MainGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                if(MainActivity.BUTTON) {
                    mRenderer.setAngle(
                    //mRenderer.getAngle() +
                    ((dx + dy))); //* TOUCH_SCALE_FACTOR));
                    requestRender();
                }else if(!MainActivity.BUTTON){
                    mRenderer.setAngle(0);
                    mRenderer.setXYrot(dx*TOUCH_SCALE_FACTOR,dy*TOUCH_SCALE_FACTOR);
                    requestRender();}
                break;

            case MotionEvent.ACTION_UP:
                mRenderer.setXYrot(0,0);
                break;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
