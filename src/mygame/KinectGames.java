package mygame;

import kinecttcpclient.KinectTCPClient;

public class KinectGames {
    
    KinectTCPClient c1;
    int [][] joint;
    Main m;
    
    public KinectGames(Main m){
        this.m = m;
        String ipaddress = "127.0.0.1";
        int port = 8001;
        //c1 = new KinectTCPClient(ipaddress,port);
        //c1.sayHello();
    }
    
    public void getData(){
        //int[] skRaw = c1.readSkeleton();
        //joint = KinectTCPClient.getJointPositions(skRaw, 1);
        joint = m.moCap.getJoints();
    }
}
