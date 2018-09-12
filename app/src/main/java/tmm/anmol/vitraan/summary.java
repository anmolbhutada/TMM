package tmm.anmol.vitraan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class summary extends AppCompatActivity {
    private String mnum;
    private FirebaseDatabase database;
    private DatabaseReference totald,rate;
    private double dbs,dbd,dc,dg,dkd,dps,sum;
    private double bsr,bdr,cr,gr,kdr,psr;
    private TextView bstv,bdtv,ctv,gtv,kdtv,pstv,ttv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Intent i = getIntent();
        mnum = i.getStringExtra("MNO");
        bstv = (TextView) findViewById(R.id.bsq);
        bdtv = (TextView) findViewById(R.id.bdq);
        ctv = (TextView) findViewById(R.id.cq);
        gtv = (TextView) findViewById(R.id.gq);
        kdtv = (TextView) findViewById(R.id.kdq);
        pstv = (TextView) findViewById(R.id.psq);
        ttv = (TextView) findViewById(R.id.tq);
        database = FirebaseDatabase.getInstance();
        totald = database.getReference(mnum + "Total");
        rate = database.getReference("rate");
        rate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getrate temp = dataSnapshot.getValue(getrate.class);
                bsr = temp.bsr;
                bdr = temp.bdr;
                cr = temp.cr;
                gr = temp.gr;
                psr = temp.psr;
                kdr = temp.kdr;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        try {
            totald.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    td temp = dataSnapshot.getValue(td.class);

                    if(temp==null){
                        td obj =new td(0,0,0,0,0,0);
                        Toast.makeText(summary.this, "Added New Number", Toast.LENGTH_SHORT).show();
                        totald.setValue(obj);
                    }
                    else {
                        dbs = temp.bs;
                        dbd = temp.bd;
                        dc = temp.c;
                        dg = temp.g;
                        dkd = temp.kd;
                        dps = temp.ps;

                        bstv.setText(temp.bs + " kg");
                        bdtv.setText(temp.bd + " kg");
                        ctv.setText(temp.c + " kg");
                        gtv.setText(temp.g + " kg");
                        kdtv.setText(temp.kd + " kg");
                        pstv.setText(temp.ps + " kg");

                        sum = dbs * bsr + dbd * bdr + dc * cr + dg * gr + dkd * kdr + dps * psr;
                        ttv.setText(sum + "");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e){
            Toast.makeText(this, "No Records Found", Toast.LENGTH_SHORT).show();
        }
    }
}
