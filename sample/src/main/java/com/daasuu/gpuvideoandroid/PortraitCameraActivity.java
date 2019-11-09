package com.daasuu.gpuvideoandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;

import com.daasuu.gpuv.egl.filter.GlFilter;

public class PortraitCameraActivity extends BaseCameraActivity {

    private FilterAdjuster adjuster;
    private GlFilter filter;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, PortraitCameraActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_portrate);
        onCreateActivity();
        videoWidth = 720;
        videoHeight = 1280;
        cameraWidth = 1280;
        cameraHeight = 720;

        SeekBar filterSeekBar = findViewById(R.id.filterSeekBar);
        filterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (adjuster != null) {
                    adjuster.adjust(filter, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // do nothing
            }
        });

        lv.setOnItemClickListener((parent, view, position, id) -> {
            if (GPUCameraRecorder != null) {
                filter = FilterType.createGlFilter(filterTypes.get(position), getApplicationContext());
                adjuster = FilterType.createFilterAdjuster(filterTypes.get(position));
                GPUCameraRecorder.setFilter(filter);
            }
        });
    }

}
