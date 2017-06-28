package org.a3dgc.terraingeneration;


import android.opengl.GLSurfaceView;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static boolean BUTTON=false;
    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Request the absence of useless layout
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MainGLSurfaceView(this);
        setContentView(mGLView);


        final Button b = new Button(this);
        b.setText("Rotate");
        b.setTag(1);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BUTTON = !BUTTON;
                final int status =(Integer) v.getTag();
                if(status == 1) {
                    b.setText("Move");
                    v.setTag(0); //pause
                } else {
                    b.setText("Rotate");
                    v.setTag(1); //pause
                }
            }
        });



        this.addContentView(b,
                new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
    }



    @Override
    protected void onResume()
    {
        // The activity must call the
        // GL surface view's onResume() on activity onResume().
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onPause()
    {
        // The activity must call the GL surface view's onPause() on activity onPause().
        super.onPause();
        mGLView.onPause();
    }
}
