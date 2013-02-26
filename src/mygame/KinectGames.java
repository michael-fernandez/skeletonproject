package mygame;

import kinecttcpclient.KinectTCPClient;

public class KinectGames {

    boolean mocap = true;
    KinectTCPClient c1;
    int[][] joint;
    Main m;

    public KinectGames(Main m) {
        this.m = m;
        String ipaddress = "127.0.0.1";
        int port = 8001;
        if (mocap == false) {
            c1 = new KinectTCPClient(ipaddress, port);
            c1.sayHello();
        }
    }

    public void getData() {
        if (mocap == false) {
            int[] skRaw = c1.readSkeleton();
            joint = KinectTCPClient.getJointPositions(skRaw, 1);
        } else {
            joint = m.moCap.getJoints();
        }
    }
}
