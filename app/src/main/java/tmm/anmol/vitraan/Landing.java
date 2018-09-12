package tmm.anmol.vitraan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Landing extends AppCompatActivity {
    private double bs = 0, bd = 0, c = 0,g=0, kd = 0, ps = 0;
    private double dbs,dbd,dc,dg,dkd,dps;
    private EditText bsi, bdi, ci,gi, kdi, psi, ni, mi,vi;
    private TextView bstv,bdtv,ctv,gtv,kdtv,pstv,ttv;
    private String name, mobile,srno;
    private FirebaseDatabase database;
    private DatabaseReference myref,total,rate,totald;
    private String android_id;
    private double bst,bdt,ct,gt,kdt,pst,sum=0;
    private double bsr,bdr,cr,gr,kdr,psr;
    private boolean flag=false;
    private static final int MY_PERMISSIONS_REQUEST_INTERNET=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=getIntent();
        android_id=i.getStringExtra("MNO");

        setContentView(R.layout.activity_landing);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);

            bsi = (EditText) findViewById(R.id.bsqty);
            bdi = (EditText) findViewById(R.id.bdqty);
            ci = (EditText) findViewById(R.id.cqty);
            gi = (EditText) findViewById(R.id.gqty);
            kdi = (EditText) findViewById(R.id.kdqty);
            psi = (EditText) findViewById(R.id.psqty);
            ni = (EditText) findViewById(R.id.nameet);
            mi = (EditText) findViewById(R.id.mnumber);
            vi = (EditText) findViewById(R.id.srno);
            bstv = (TextView) findViewById(R.id.bsl);
            bdtv = (TextView) findViewById(R.id.bdl);
            ctv = (TextView) findViewById(R.id.cl);
            gtv = (TextView) findViewById(R.id.gl);
            kdtv = (TextView) findViewById(R.id.kdl);
            pstv = (TextView) findViewById(R.id.psl);
            ttv = (TextView) findViewById(R.id.totaltv);
            database = FirebaseDatabase.getInstance();
            myref = database.getReference(android_id);
            total = database.getReference("TotalAll");
            rate = database.getReference("rate");
            totald = database.getReference(android_id + "Total");
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

            totald.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    td temp = dataSnapshot.getValue(td.class);
                    if(temp==null)
                    {
                        td obj =new td(0,0,0,0,0,0);
                        Toast.makeText(Landing.this, "Added New Number", Toast.LENGTH_SHORT).show();
                        totald.setValue(obj);
                    }
                    else{
                        dbs=temp.bs;
                        dbd=temp.bd;
                        dc=temp.c;
                        dg=temp.g;
                        dkd=temp.kd;
                        dps=temp.ps;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            total.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    qty temp = dataSnapshot.getValue(qty.class);
                    bst = temp.bs;
                    bdt = temp.bd;
                    ct = temp.c;
                    gt = temp.g;
                    kdt = temp.kd;
                    pst = temp.ps;
                    bstv.setText("" + temp.bs);
                    bdtv.setText("" + temp.bd);
                    ctv.setText("" + temp.c);
                    gtv.setText("" + temp.g);
                    kdtv.setText("" + temp.kd);
                    pstv.setText("" + temp.ps);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    public boolean validate(){
        boolean fbool=true;
        if(bs*2!=((int)(bs*2)))
            fbool=false;
        else if(bd*2!=((int)(bd*2)))
            fbool=false;
        else if(c*2!=((int)(c*2)))
            fbool=false;
        else if(g*2!=((int)(g*2)))
            fbool=false;
        else if(kd*2!=((int)(kd*2)))
            fbool=false;
        else if(ps*2!=((int)(ps*2)))
            fbool=false;
        mobile = mi.getText().toString();
        srno=vi.getText().toString();
        int check=Integer.parseInt(srno);
        if(mobile.length()!=10)
            fbool=false;
        else if(srno.length()!=3)
            fbool=false;
        else if(check<100&&check>800)
            fbool=false;
        return fbool;
    }
    public void onClick_Landing(View v)
    {
        switch (v.getId()) {
            case R.id.submit:

                if (database != null && flag) {

                    name = ni.getText().toString();
                    mobile = mi.getText().toString();
                    srno=vi.getText().toString();
                    voucher obj = new voucher(bs, bd, c, g, kd, ps, name, mobile,srno);

                    qty obj1 = new qty(bst - bs, bdt - bd, ct - c, gt - g, kdt - kd, pst - ps);
                    td obj2 =new td(dbs+bs,dbd+bd,dc+c,dg+g,dkd+kd,dps+ps);
                    myref.push().setValue(obj);
                    total.setValue(obj1);
                    totald.setValue(obj2);

                    bsi.setText("");
                    bdi.setText("");
                    ci.setText("");
                    gi.setText("");
                    kdi.setText("");
                    psi.setText("");
                    ni.setText("");
                    mi.setText("");
                    vi.setText("");
                    ttv.setText("0");
                    Toast.makeText(this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                    flag=false;
                    break;

                }
            case R.id.TotalButton:
                try {
                    bs = Double.parseDouble(bsi.getText().toString());
                } catch (NumberFormatException e) {
                    bs=0;

                }
                try {
                    bd = Double.parseDouble(bdi.getText().toString());
                } catch (NumberFormatException e) {
                    bd=0;
                }
                try {
                    c = Double.parseDouble(ci.getText().toString());
                } catch (NumberFormatException e) {
                    c=0;
                }
                try {
                    g = Double.parseDouble(gi.getText().toString());
                } catch (NumberFormatException e) {
                    g=0;
                }
                try {
                    kd = Double.parseDouble(kdi.getText().toString());
                } catch (NumberFormatException e) {
                    kd=0;
                }
                try {
                    ps = Double.parseDouble(psi.getText().toString());
                } catch (NumberFormatException e) {
                    ps=0;
                }

                if(validate())
                {

                    sum = bs * bsr + bd * bdr + c * cr + g * gr + kd * kdr + ps * psr;
                    ttv.setText(sum+"");
                    flag=true;
                }
                else
                {
                    Toast.makeText(this, "Check Fields", Toast.LENGTH_SHORT).show();
                    ttv.setText("");
                    flag=false;
                }
                break;
        }
    }
}
