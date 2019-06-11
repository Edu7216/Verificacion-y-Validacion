package es.unizar.eina.notepadv3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Date;

public class NoteDatePicker extends AppCompatActivity {
    DatePicker picker;
    Button displayDate;
    TextView mCurrentDate;
    Date startDate;
    TextView mStartDate;
    Date endDate;
    String info;

    TextView mEndDate;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        mCurrentDate=(TextView)findViewById(R.id.currentDate);
        mStartDate=(TextView)findViewById(R.id.startDate);
        picker=(DatePicker)findViewById(R.id.datePicker);
        displayDate=(Button)findViewById(R.id.button1);

        info = (String) getIntent().getSerializableExtra("info");
        startDate = (Date) getIntent().getSerializableExtra("startDate");
        mCurrentDate.setText("Current Date: "+info);
        mStartDate.setText("Start Date: " + startDate.getMonth() + "/" +
                startDate.getDate() + "/" + startDate.getYear());

        if (info.equals("endDate")){
            mEndDate=(TextView)findViewById(R.id.endDate);
            endDate = (Date) getIntent().getSerializableExtra("endDate");
            mEndDate.setText("End Date: " + endDate.getMonth() + "/" +
                    endDate.getDate() + "/" + endDate.getYear());
        }

        Button changeButton = (Button) findViewById(R.id.button1);
        changeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (info.equals("startDate")) {
                    startDate = getDate(startDate);
                    Intent data = new Intent();
                    data.putExtra("info","startDate");
                    data.putExtra("startDate", startDate);
                    setResult(RESULT_OK, data);
                    finish();
                }
                if (info.equals("endDate")) {
                    endDate = getDate(endDate);
                    Intent data = new Intent();
                    data.putExtra("info","endDate");
                    data.putExtra("endDate", endDate);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });

    }
    public String getCurrentDate(){
        return ((picker.getMonth() + 1) + "/") +//month is 0 based
                picker.getDayOfMonth() + "/" +
                picker.getYear();
    }

    public Date getDate(Date date){
        date.setMonth(picker.getMonth() + 1);
        date.setDate(picker.getDayOfMonth());
        date.setYear(picker.getYear());
        return date;
    }
}cd