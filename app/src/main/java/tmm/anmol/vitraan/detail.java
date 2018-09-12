package tmm.anmol.vitraan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class detail extends AppCompatActivity {
    private String mnum;
    private EditText mtv;
    private FirebaseDatabase database;
    private DatabaseReference totald;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mtv=(EditText) findViewById(R.id.mno);
        database = FirebaseDatabase.getInstance();
        mnum=mtv.getText().toString();

        }

    public void onClick_detail(View v)
    {
        switch(v.getId()){
            case R.id.SDE:
                mnum=mtv.getText().toString();
                if(mnum.length()==10) {

                    Intent sdeintent = new Intent(this, Landing.class);
                    sdeintent.putExtra("MNO", mnum);
                    startActivity(sdeintent);
                }
                else{
                    Toast.makeText(this, "10 Digits Need to entered in the Mobile Entry", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Summary:
                mnum=mtv.getText().toString();
                if(mnum.length()==10) {

                    Intent sumintent = new Intent(this, summary.class);
                    sumintent.putExtra("MNO", mnum);
                    startActivity(sumintent);
                }
                else{
                    Toast.makeText(this, "10 Digits Need to entered in the Mobile Entry", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
