package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.studio.amplify.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StopWatchActivity extends AppCompatActivity {

    @BindView(R.id.progressBarCircle)
    ProgressBar progressBarCircle;
    @BindView(R.id.editTextMinute)
    EditText editTextMinute;
    @BindView(R.id.textViewTime)
    TextView textViewTime;
    @BindView(R.id.imageViewReset)
    Button imageViewReset;
    @BindView(R.id.imageViewStartStop)
    Button imageViewStartStop;
    CountDownTimer countDownTimer;
    boolean isStart = false;

    private long timeCountInMilliSeconds = 1 * 60000;


    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        isStart = false;
        ButterKnife.bind(this);
        //isStart=true;

    }


    @OnClick({R.id.imageViewReset, R.id.imageViewStartStop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewReset:
                reset();
                break;
            case R.id.imageViewStartStop:
                if(editTextMinute.getText().toString().trim().equals("")) {
                    Toast.makeText(StopWatchActivity.this,  getString(R.string.minute_text), Toast.LENGTH_LONG).show();
                }
                else if(!editTextMinute.getText().toString().trim().equals("")&&(!isStart)) {
                    imageViewStartStop.setText("Stop");
                    editTextMinute.setVisibility(View.GONE);
                    startStop();
                }
                else if(isStart) {
                    editTextMinute.setVisibility(View.VISIBLE);
                    editTextMinute.setText("");
                    imageViewStartStop.setText("Start");
                    startStop();
                }

                break;
        }
    }

    private void reset() {
        stopCountDownTimer();
        startCountDownTimer();
    }

    private void startStop() {
        if (!isStart) {
                // call to initialize the timer values
                setTimerValues();
                // call to initialize the progress bar values
                setProgressBarValues();
                // showing the reset icon
                imageViewReset.setVisibility(View.VISIBLE);
                // changing play icon to stop icon
                // imageViewStartStop.setImageResource(R.drawable.icon_stop);
                // making edit text not editable
                editTextMinute.setEnabled(false);
                // changing the timer status to started
                timerStatus = TimerStatus.STARTED;
                // call to start the count down timer
                startCountDownTimer();
                isStart = true;

        } else {

            // hiding the reset icon
            imageViewReset.setVisibility(View.GONE);
            // changing stop icon to start icon
            // imageViewStartStop.setImageResource(R.drawable.icon_start);
            // making edit text editable
            editTextMinute.setEnabled(true);
            // changing the timer status to stopped
            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();
            isStart = false;


        }

    }

    private void setTimerValues() {
        int time = 0;
        if (!editTextMinute.getText().toString().isEmpty()) {
            // fetching value from edit text and type cast to integer
            time = Integer.parseInt(editTextMinute.getText().toString().trim());
        } else {
            // toast message to fill edit text
            Toast.makeText(getApplicationContext(), getString(R.string.message_minutes), Toast.LENGTH_LONG).show();
        }
        // assigning values after converting to milliseconds
        timeCountInMilliSeconds = time * 60 * 1000;
    }

    private void startCountDownTimer() {

         countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                // call to initialize the progress bar values
                setProgressBarValues();
                // hiding the reset icon
                imageViewReset.setVisibility(View.GONE);
                // changing stop icon to start icon
                //   imageViewStartStop.setImageResource(R.drawable.icon_start);
                // making edit text editable
                editTextMinute.setEnabled(true);
                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED;
            }


        }.start();
        countDownTimer.start();
    }

    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    private void setProgressBarValues() {
        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;

    }

}
