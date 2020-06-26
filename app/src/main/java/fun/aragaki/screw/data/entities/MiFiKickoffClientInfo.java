package fun.aragaki.screw.data.entities;

import org.jetbrains.annotations.NotNull;

public class MiFiKickoffClientInfo {
    public int mKickOffClientType;
    public String mKickOffMacAddr;

    public MiFiKickoffClientInfo(MiFiInformation mifiInformation) {
        this.mKickOffClientType = mifiInformation.mKickOffClientType;
        this.mKickOffMacAddr = mifiInformation.mKickOffMacAddr;
    }

    @NotNull
    @Override
    public String toString() {
        return "MiFiKickoffClientInfo{" +
                "mKickOffClientType=" + mKickOffClientType +
                ", mKickOffMacAddr='" + mKickOffMacAddr + '\'' +
                '}';
    }
}