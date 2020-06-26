package fun.aragaki.screw.data.entities;

import org.jetbrains.annotations.NotNull;

public class MiFiNetworkNameInfo {
    public String mNetworkNameStr;

    public MiFiNetworkNameInfo(MiFiInformation mifiInformation) {
        this.mNetworkNameStr = mifiInformation.mNetworkNameStr;
    }

    @NotNull
    @Override
    public String toString() {
        return "MiFiNetNameInfo{" +
                "mNetworkNameStr='" + mNetworkNameStr + '\'' +
                '}';
    }
}