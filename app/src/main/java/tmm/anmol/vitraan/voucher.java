package tmm.anmol.vitraan;

public class voucher {
    public double bs,bd,c,g,kd,ps;
    public String fn,mobile,srno;
    voucher(double a,double b,double c,double g,double d,double e,String n, String m,String srno)
    {
        bs=a;
        bd=b;
        this.c=c;
        this.g=g;
        kd=d;
        ps=e;
        mobile=m;
        fn=n;
        this.srno=srno;
    }
    voucher()
    {
    }
}
